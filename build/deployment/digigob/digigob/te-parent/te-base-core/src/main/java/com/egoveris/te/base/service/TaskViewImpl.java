package com.egoveris.te.base.service;

import java.sql.SQLDataException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.TaskQuery;
import org.jbpm.api.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egoveris.deo.model.model.RequestExternalConsultaDocumento;
import com.egoveris.deo.ws.service.IExternalConsultaDocumentoService;
import com.egoveris.te.base.dao.TareaViewDAO;
import com.egoveris.te.base.exception.BuscarTareaException;
import com.egoveris.te.base.exception.external.TeException;
import com.egoveris.te.base.model.DocumentUnqtAppDTO;
import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.model.DocumentoWsDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.Tarea;
import com.egoveris.te.base.model.TareaAppDTO;
import com.egoveris.te.base.model.rest.DocumentTypeWsDTO;
import com.egoveris.te.base.model.rest.FormWsDTO;
import com.egoveris.te.base.model.rest.FormsWsDTO;
import com.egoveris.te.base.model.rest.MaskWsDTO;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.service.iface.ITaskViewService;
import com.egoveris.te.base.util.ConstantesCommon;
import com.egoveris.te.base.util.Task2TareaTransformer;

@Service
@Transactional
public class TaskViewImpl implements ITaskViewService {
	private static final String NULL_VALUE = "null";

  private transient static Logger logger = LoggerFactory.getLogger(TaskViewImpl.class);

	public static final String NOMBRE_TAREA = "nombreTarea";
	@Autowired
	private TareaParaleloService tareaParaleloService;
	@Autowired
	private TrataService trataService;
	@Autowired
	private ProcessEngine processEngine;
	@Autowired
	private ExpedienteElectronicoService expediente;
	@Autowired
	private TareaViewDAO tareaViewDAO;
	@Autowired
	private HistorialOperacionService historialOperacionService;
	@Autowired
	private TipoDocumentoService tipoDocumentoService;

	private Task2TareaTransformer task2TareaTransformer = null;

	@Autowired
	private IExternalConsultaDocumentoService consultaDocumentoServiceExt;

	/**
	 * Se obtienen las Task del Assignee, tranforma en TareaRowMapper con cache
	 * de la consulta.
	 */
	public List<Tarea> buscarTareasPorUsuario(Map<String, Object> parametrosConsulta,
			Map<String, String> parametrosOrden) {
		if (logger.isDebugEnabled()) {
			logger.debug("buscarTareasPorUsuario(parametrosConsulta={}, parametrosOrden={}) - start",
					parametrosConsulta, parametrosOrden);
		}

		TaskQuery tq = this.processEngine.getTaskService().createTaskQuery();
		String usuario = (String) parametrosConsulta.get("usuario");
		String inicioCarga;
		String tamanoPaginacion;
		String criterio;
		String orden;

		inicioCarga = (String) parametrosOrden.get("inicioCarga");
		tamanoPaginacion = (String) parametrosOrden.get("tamanoPaginacion");
		criterio = (String) parametrosOrden.get("criterio");

		if (NOMBRE_TAREA.equalsIgnoreCase(criterio)) {
			criterio = TaskQuery.PROPERTY_NAME;
		} else {
			criterio = TaskQuery.PROPERTY_CREATEDATE;
		}

		orden = (String) parametrosOrden.get("orden");

		tq = this.processEngine.getTaskService().createTaskQuery();
		tq.assignee(usuario);

		if (orden.compareTo("ascending") == 0) {
			tq.orderAsc(criterio);
		} else {
			tq.orderDesc(criterio);
		}

		tq.page(Integer.parseInt(inicioCarga), Integer.parseInt(tamanoPaginacion));

		/*
		 * JIRAs: BISADE-5005: https://quark.everis.com/jira/browse/BISADE-5005,
		 * BISADE-2515: https://quark.everis.com/jira/browse/BISADE-2515 - Se
		 * quita la funcionalidad de cacheQueries - Se transforman las Task en
		 * TareaRowMapper
		 */
		task2TareaTransformer = new Task2TareaTransformer(trataService, tareaParaleloService,
				processEngine, null, null);
		List<Tarea> returnList = ((List<Tarea>) CollectionUtils.collect(tq.list(), task2TareaTransformer));
		if (logger.isDebugEnabled()) {
			logger.debug("buscarTareasPorUsuario(Map<String,Object>, Map<String,String>) - end - return value={}",
					returnList);
		}
		return returnList;
	}

	public List<Task> buscarTareasPorUsuario(String user) {
		if (logger.isDebugEnabled()) {
			logger.debug("buscarTareasPorUsuario(user={}) - start", user);
		}

		TaskQuery tq = this.processEngine.getTaskService().createTaskQuery();
		tq.assignee(user);
		List<Task> returnList = tq.list();
		if (logger.isDebugEnabled()) {
			logger.debug("buscarTareasPorUsuario(String) - end - return value={}", returnList);
		}
		return returnList;
	}

	public List<Task> buscarSolicitudesDeCaratulacionPorUsuario(String user) {
		if (logger.isDebugEnabled()) {
			logger.debug("buscarSolicitudesDeCaratulacionPorUsuario(user={}) - start", user);
		}

		TaskQuery tq = processEngine.getTaskService().createTaskQuery();
		tq.assignee(user);
		tq.activityName("Iniciar Expediente");
		List<Task> returnList = tq.list();
		if (logger.isDebugEnabled()) {
			logger.debug("buscarSolicitudesDeCaratulacionPorUsuario(String) - end - return value={}", returnList);
		}
		return returnList;
	}

	public List<Task> buscarSolicitudesDeAnularModificar(String user) {
		if (logger.isDebugEnabled()) {
			logger.debug("buscarSolicitudesDeAnularModificar(user={}) - start", user);
		}

		TaskQuery tq = processEngine.getTaskService().createTaskQuery();
		tq.assignee(user);
		tq.activityName("Anular/Modificar Solicitud");
		List<Task> returnList = tq.list();
		if (logger.isDebugEnabled()) {
			logger.debug("buscarSolicitudesDeAnularModificar(String) - end - return value={}", returnList);
		}
		return returnList;
	}

	public List<Task> buscarSolicitudesParalelasPorUsuario(String user) {
		if (logger.isDebugEnabled()) {
			logger.debug("buscarSolicitudesParalelasPorUsuario(user={}) - start", user);
		}

		TaskQuery tq = processEngine.getTaskService().createTaskQuery();
		tq.assignee(user);
		tq.activityName("Paralelo");
		List<Task> returnList = tq.list();
		if (logger.isDebugEnabled()) {
			logger.debug("buscarSolicitudesParalelasPorUsuario(String) - end - return value={}", returnList);
		}
		return returnList;
	}

	public int numeroTotalTareasPorUsuario(String usuario) {
		if (logger.isDebugEnabled()) {
			logger.debug("numeroTotalTareasPorUsuario(usuario={}) - start", usuario);
		}

		Long numero;
		TaskQuery tq = this.processEngine.getTaskService().createTaskQuery();
		tq.assignee(usuario);
		numero = tq.count();

		int returnint = numero.intValue();
		if (logger.isDebugEnabled()) {
			logger.debug("numeroTotalTareasPorUsuario(String) - end - return value={}", returnint);
		}
		return returnint;
	}

	// Por cambio de PaginSort Group
	public List<Task> buscarTareasPorGrupo(Map<String, Object> parametrosConsulta,
			Map<String, String> parametrosOrden) {
		if (logger.isDebugEnabled()) {
			logger.debug("buscarTareasPorGrupo(parametrosConsulta={}, parametrosOrden={}) - start", parametrosConsulta,
					parametrosOrden);
		}

		TaskQuery tq = this.processEngine.getTaskService().createTaskQuery();
		String usuario = (String) parametrosConsulta.get("usuario");

		String inicioCarga;
		String tamanoPaginacion;
		String criterio;
		String orden;
		String estadoTarea;

		inicioCarga = (String) parametrosOrden.get("inicioCarga");
		tamanoPaginacion = (String) parametrosOrden.get("tamanoPaginacion");
		criterio = (String) parametrosOrden.get("criterio");
		estadoTarea = (String) parametrosConsulta.get("filtro");

		if (criterio.equals(NOMBRE_TAREA)) {
			criterio = TaskQuery.PROPERTY_NAME;
		} else {
			criterio = TaskQuery.PROPERTY_CREATEDATE;
		}

		orden = (String) parametrosOrden.get("orden");
		tq.candidate(usuario);

		if (!StringUtils.isEmpty(estadoTarea)) {
			tq.activityName(estadoTarea);
		}

		if (orden.compareTo("ascending") == 0) {
			tq.orderAsc(criterio);
		} else {
			tq.orderDesc(criterio);
		}

		tq.page(Integer.parseInt(inicioCarga), Integer.parseInt(tamanoPaginacion));

		// 07-06-2017: Si parametroConsulta sin asignar (unnasigned) existe y es
		// true
		// solo se obtendran las tareas sin asignar
		if (parametrosConsulta.get("unnasigned") != null && (Boolean) parametrosConsulta.get("unnasigned") == true) {
			tq.unassigned();
		}

		List<Task> returnList = tq.list();
		if (logger.isDebugEnabled()) {
			logger.debug("buscarTareasPorGrupo(Map<String,Object>, Map<String,String>) - end - return value={}",
					returnList);
		}
		return returnList;
	}

	public int numeroTotalTareasGrupoPorUsuario(String usuario, String activityName) {
		if (logger.isDebugEnabled()) {
			logger.debug("numeroTotalTareasGrupoPorUsuario(usuario={}, activityName={}) - start", usuario,
					activityName);
		}

		Long numero;
		TaskQuery tq = this.processEngine.getTaskService().createTaskQuery();
		tq.candidate(usuario);

		if (!StringUtils.isEmpty(activityName)) {
			tq.activityName(activityName);
		}

		// Para que el buzon grupal obtenga el total de tareas sin asignar
		tq.unassigned();

		numero = tq.count();

		int returnint = numero.intValue();
		if (logger.isDebugEnabled()) {
			logger.debug("numeroTotalTareasGrupoPorUsuario(String, String) - end - return value={}", returnint);
		}
		return returnint;
	}

	/**
	 * Esté método busca por algun criterio <code>org.jbpm.api.TaskQuery</code>
	 * en aquellas <code>org.jbpm.api.task.Task</code> asignadas. Transformando
	 * la <code>org.jbpm.api.task.Task</code> en objecto <code>Tarea</code>
	 * 
	 * @param <code>Map
	 *            <String, Object></code>parametrosConsulta
	 *            <ul>
	 *            <li><code>java.lang.String</code>assignee.</li>
	 *            </ul>
	 * @param <code>Map<String,
	 *            String></code>parametrosOrden
	 *            <ul>
	 *            <li><code>java.lang.String</code>inicioCarga</li>
	 *            <li><code>java.lang.String</code>tamanoPaginacion</li>
	 *            <li><code>java.lang.String</code>criterio
	 *            <code>TaskQuery.PROPERTY_NAME<code> ó <code>TaskQuery.PROPERTY_CREATEDATE<code></li>
	 *  <li><code>java.lang.String</code>orden los valores esperados son
	 *            ascending ó descending</li>
	 *            </ul>
	 * @return <code>List<?></code> Lista de Tareas.
	 */

	public List<?> buscarTask2TareaPorGrupo(Map<String, Object> parametrosConsulta,
			Map<String, String> parametrosOrden) {
		if (logger.isDebugEnabled()) {
			logger.debug("buscarTask2TareaPorGrupo(parametrosConsulta={}, parametrosOrden={}) - start",
					parametrosConsulta, parametrosOrden);
		}

		Task2TareaTransformer task2TareaTransformer = new Task2TareaTransformer(trataService, tareaParaleloService,
				this.processEngine, null, null);
		List<Task> tasks = buscarTareasPorGrupo(parametrosConsulta, parametrosOrden);
		List<?> returnList = ((List<Tarea>) CollectionUtils.collect(tasks, task2TareaTransformer));
		if (logger.isDebugEnabled()) {
			logger.debug("buscarTask2TareaPorGrupo(Map<String,Object>, Map<String,String>) - end - return value={}",
					returnList);
		}
		return returnList;
	}

	public Task obtenerTaskById(String idTask) {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerTaskById(idTask={}) - start", idTask);
		}

		TaskQuery tq = processEngine.getTaskService().createTaskQuery();
		tq.executionId(idTask);
		Task returnTask = tq.uniqueResult();
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerTaskById(String) - end - return value={}", returnTask);
		}
		return returnTask;
	}

	public List<?> buscarTareasDistPorCriterio(Map<String, Object> parametrosConsulta,
			Map<String, String> parametrosOrden) {
		if (logger.isDebugEnabled()) {
			logger.debug("buscarTareasDistPorCriterio(parametrosConsulta={}, parametrosOrden={}) - start",
					parametrosConsulta, parametrosOrden);
		}

		if (logger.isDebugEnabled()) {
			logger.debug(
					"buscarTareasDistPorCriterio(Map<String,Object>, Map<String,String>) - end - return value={null}");
		}
		return null;
	}

	/* método auxiliar */
	private List<Object> getParameterValues(Map parameters) throws SQLDataException {
		if (logger.isDebugEnabled()) {
			logger.debug("getParameterValues(parameters={}) - start", parameters);
		}

		List<Object> parametersValues = new LinkedList<Object>();

		String usuario = (String) parameters.get("usuario");
		String criterio = (String) parameters.get("criterio");
		String orden = (String) parameters.get("orden");

		// Integer iPageNumber = Integer.parseInt((String)
		// parameters.get("iPageNumber"));
		// Integer iPageSize = Integer.parseInt((String)
		// parameters.get("iPageSize"));
		Integer iTotalSize = (Integer) parameters.get("iTotalSize");

		TaskQuery tq = this.processEngine.getTaskService().createTaskQuery();
		tq.assignee(usuario);

		Integer iTotalSizeAux = (int) tq.count();

		parametersValues.add(usuario);
		parametersValues.add((Integer) parameters.get("inicioCarga"));
		parametersValues.add((Integer) parameters.get("tamanoPaginacion"));
		parametersValues.add(criterio);
		parametersValues.add(orden);
		parametersValues.add("nombreTarea");

		// parametersValues.add(iPageNumber);
		// parametersValues.add(iPageSize);
		if (!iTotalSize.equals(iTotalSizeAux)) {
			parametersValues.add(iTotalSizeAux);
			parametersValues.add(new Boolean(true));
		} else {
			parametersValues.add(iTotalSize);
			parametersValues.add(new Boolean(false));
		}

		parametersValues.add((Boolean) parameters.get("cacheQueries"));

		if (logger.isDebugEnabled()) {
			logger.debug("getParameterValues(Map) - end - return value={}", parametersValues);
		}
		return parametersValues;
	}

	public List<?> buscarTareasDistPorGrupo(Map<String, Object> parametrosConsulta,
			Map<String, String> parametrosOrden) {
		if (logger.isDebugEnabled()) {
			logger.debug("buscarTareasDistPorGrupo(parametrosConsulta={}, parametrosOrden={}) - start",
					parametrosConsulta, parametrosOrden);
		}

		List<?> returnList = new ArrayList<Tarea>();
		if (logger.isDebugEnabled()) {
			logger.debug("buscarTareasDistPorGrupo(Map<String,Object>, Map<String,String>) - end - return value={}",
					returnList);
		}
		return returnList;
	}

	public int numeroTotalTareaDistPorGrupo(String candidate, String estado) {
		if (logger.isDebugEnabled()) {
			logger.debug("numeroTotalTareaDistPorGrupo(candidate={}, estado={}) - start", candidate, estado);
		}

		Integer numeroTotal = Integer.valueOf(0);
		try {
			numeroTotal = tareaViewDAO.numeroTotalTareaDistPorGrupo(candidate, estado);
		} catch (SQLDataException sqlde) {
			logger.error("[ThreadId=" + Thread.currentThread().getId()
					+ "] Hubo un error en el método numeroTotalTareaDistPorGrupo " + sqlde.getMessage(), sqlde);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("numeroTotalTareaDistPorGrupo(String, String) - end - return value={}", numeroTotal);
		}
		return numeroTotal;
	}

	public int numeroTotalTareaDistPorCriterio(String assignee, String estado) {
		if (logger.isDebugEnabled()) {
			logger.debug("numeroTotalTareaDistPorCriterio(assignee={}, estado={}) - start", assignee, estado);
		}

		Integer numeroTotal = Integer.valueOf(0);
		try {
			numeroTotal = tareaViewDAO.numeroTotalTareaDistPorCriterio(assignee, estado);
		} catch (SQLDataException sqlde) {
			logger.error(
					"[ThreadId=" + Thread.currentThread().getId()
							+ "] Hubo un error en el método numeroTotalTareaDistPorCriterio " + sqlde.getMessage(),
					sqlde);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("numeroTotalTareaDistPorCriterio(String, String) - end - return value={}", numeroTotal);
		}
		return numeroTotal;
	}

	@Override
	public List<Tarea> buscarTareaDistPorTrata(final String expedienteEstado, final String expedienteUsuarioAsignado,
			final List<String> codigosDeTrataList) throws BuscarTareaException {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"buscarTareaDistPorTrata(expedienteEstado={}, expedienteUsuarioAsignado={}, codigosDeTrataList={}) - start",
					expedienteEstado, expedienteUsuarioAsignado, codigosDeTrataList);
		}

		List<Tarea> tareasList = new ArrayList<Tarea>();

		try {
			tareasList = tareaViewDAO.buscarTareaDistPorTrata(expedienteEstado, expedienteUsuarioAsignado,
					codigosDeTrataList);
		} catch (SQLDataException sqlde) {
			logger.error(
					"[ThreadId=" + Thread.currentThread().getId()
							+ "] Hubo un error en el método numeroTotalTareaDistPorCriterio " + sqlde.getMessage(),
					sqlde);
			throw new TeException(sqlde.getMessage(), sqlde);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("buscarTareaDistPorTrata(String, String, List<String>) - end - return value={}", tareasList);
		}
		return tareasList;
	}

	public Boolean verificarNoExisteTarea(final String idWorkflow) throws SQLDataException {
		if (logger.isDebugEnabled()) {
			logger.debug("verificarNoExisteTarea(idWorkflow={}) - start", idWorkflow);
		}
		Boolean returnBoolean;
		returnBoolean = tareaViewDAO.verificarNoExisteTarea(idWorkflow);
		if (logger.isDebugEnabled()) {
			logger.debug("verificarNoExisteTarea(String) - end - return value={}", returnBoolean);
		}
		return returnBoolean;
	}

	/**
	 * Se obtienen las Task del Assignee, tranforma en TareaRowMapper con cache
	 * de la consulta.
	 */
	public List<TareaAppDTO> buscarAllTareasPorUsuario(Map<String, Object> parametrosConsulta, Integer pageSize) {
	  if (logger.isDebugEnabled()) {
			logger.debug("buscarTareasPorUsuario(parametrosConsulta={}- start", parametrosConsulta);
		}

		String usuario = (String) parametrosConsulta.get("user");
		String taskCode = (String) parametrosConsulta.get("taskCode");
		String taskStatus = (String) parametrosConsulta.get("taskStatus");
		String organism = (String) parametrosConsulta.get("organism");
		boolean grupal = (boolean) parametrosConsulta.get("grupal");
		
		if (organism != null && NULL_VALUE.equalsIgnoreCase(organism)) {
		  organism = null;
		}
		
		if (taskCode != null && NULL_VALUE.equalsIgnoreCase(taskCode)) {
		  taskCode = null;
		}
		
		if (taskStatus != null && NULL_VALUE.equalsIgnoreCase(taskStatus)) {
		  taskStatus = null;
		}
		
		TaskQuery tq = this.processEngine.getTaskService().createTaskQuery();
		
		if (grupal) {
			tq.candidate(usuario);
			tq.unassigned();
		} else {
			tq.assignee(usuario);
		}
		
		if (taskCode != null) {
		  tq.executionId(taskCode);
		}
		
		task2TareaTransformer = new Task2TareaTransformer(trataService, tareaParaleloService,
				processEngine, historialOperacionService, tipoDocumentoService);
		tq.orderDesc(TaskQuery.PROPERTY_CREATEDATE);
		
		List<Task> taskQuery = tq.list();
		List<TareaAppDTO> tareas = new ArrayList<>();
		
    if (taskQuery != null && !taskQuery.isEmpty()) {
      for (Task task : taskQuery) {
        if (taskStatus == null && !task.getActivityName().equalsIgnoreCase(ConstantesCommon.ESTADO_GUARDA_TEMPORAL)
            || (taskStatus != null && task.getActivityName().equalsIgnoreCase(taskStatus))) {
          // instance TareaAppDTO
          TareaAppDTO tareaApp = new TareaAppDTO();
          // instance documentoWs
          List<DocumentoWsDTO> documentoWs = new ArrayList<>();
          tareaApp = (TareaAppDTO) task2TareaTransformer.transformTask(task);
          // validation
          if (tareaApp.getTaskCode() != null && !tareaApp.getTaskCode().isEmpty()) {
            // get id document
            Object obj = this.processEngine.getExecutionService().getVariable(tareaApp.getTaskCode(), "idExpedienteElectronico");
            // type number
            Number id = (Number) obj;
            // get expediente
            ExpedienteElectronicoDTO codigoExp = null;
            if (null != id) {
              // get expediente
              codigoExp = expediente.buscarExpedienteElectronico(id.longValue());
              if (null != codigoExp) {
                // assign mask
                assignMask(tareaApp, codigoExp);
                task2TareaTransformer.llenaListadePases(codigoExp.getId(), tareaApp);
                // iterate document
                for (DocumentoDTO documenMap : codigoExp.getDocumentos()) {
                  // instance DocumentoWsDTO
                  DocumentoWsDTO documenLi = new DocumentoWsDTO();
                  // type document instance
                  DocumentTypeWsDTO typeDocument = new DocumentTypeWsDTO();

                  // set name
                  documenLi.setName(documenMap.getNombreArchivo());
                  // set code
                  documenLi.setCode(documenMap.getNumeroSade());
                  // set typeAcronym
                  typeDocument.setAcronym(documenMap.getTipoDocAcronimo());
                  // set typeDocument
									documenLi.setDocumentType(typeDocument);
                  // add to list
                  documentoWs.add(documenLi);
                }
              }
            }
          }
          // set list document
          tareaApp.setDocumentoWs(documentoWs);
          // add Forms
          listForms(tareaApp);
          // add tarea
          tareas.add(tareaApp);

          // pageSize
          if (tareas.size() == pageSize) {
            break;
          }
        }
      }
    }
    
    // return tarea
    return tareas;
  }

	/**
	 * @param t1
	 * @param codigoExp
	 */
	private void assignMask(TareaAppDTO t1, ExpedienteElectronicoDTO codigoExp) {
		// mask
		MaskWsDTO mask = new MaskWsDTO();
		try {
			if (null != codigoExp && null != codigoExp.getTrata()) {
				// type
				mask.setType(codigoExp.getTrata().getTipoTramite());
				// desc
				mask.setDescription(codigoExp.getTrata().getDescripcion());
				// user
				mask.setUser(codigoExp.getSolicitudIniciadora().getUsuarioCreacion());
				// user request
				mask.setUserRequest(codigoExp.getSolicitudIniciadora().getSolicitante().getNombreSolicitante() + " "
						+ codigoExp.getSolicitudIniciadora().getSolicitante().getApellidoSolicitante());
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				String fechaCreacion = sdf.format(codigoExp.getFechaCreacion());
				// fechaCreacion
				mask.setDate(fechaCreacion);
				// motivo
				mask.setReason(codigoExp.getSolicitudIniciadora().getMotivo());
			}
		} catch (Exception e) {
		  logger.debug("Error in assigMaks: ", e);
		}
		// set mask
		t1.setMask(mask);
	}
	
	public DocumentUnqtAppDTO buscarDocument(String user, String numeroSade) { 
		// byte[] to String
		String sByte = null;
		// instance to data
		RequestExternalConsultaDocumento request = new RequestExternalConsultaDocumento();  
		// documentoList
		DocumentUnqtAppDTO documentList = new DocumentUnqtAppDTO();
		// id
		request.setNumeroDocumento(numeroSade);
		// user
		request.setUsuarioConsulta(user);
		try {
			// get data
			byte[] response = consultaDocumentoServiceExt
					.consultarDocumentoPdf(request);
			// byte[] to String
			sByte = Base64.encodeBase64String(response);
			// set data
			documentList.setData(sByte);
			// set numeroSade
			documentList.setNumeroSade(numeroSade); 
		} catch (Exception e) {
			logger.debug("Error getting document: ", e);
		}
		// return list 
		return documentList;
	}

	private void listForms(TareaAppDTO t1) {
		// forms
		FormsWsDTO formsWS = new FormsWsDTO();
		// title form
		formsWS.setTitle("Titulos formularios");
		// instace type forms
		List<FormWsDTO> listForm = new ArrayList<>();
		// forms types
		FormWsDTO formType = new FormWsDTO();
		// type
		formType.setType("textBox");
		// readonly
		formType.setReadonly(true);
		// key
		formType.setKey("name");
		// label
		formType.setLabel("label");
		// returnData
		formType.setReturnData(null);
		// add to list
		listForm.add(formType);
		// list form
		formsWS.setForm(listForm);
		// setForms
		t1.setForms(formsWS);
	}

}
