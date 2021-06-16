package com.egoveris.te.base.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dozer.Mapper;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.TaskQuery;
import org.jbpm.api.task.Participation;
import org.jbpm.api.task.Task;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;

import com.egoveris.commons.databaseconfiguration.propiedades.AppProperty;
import com.egoveris.deo.model.model.RequestExternalActualizarReservaReparticionDocumento;
import com.egoveris.deo.ws.exception.DocumentoNoExisteException;
import com.egoveris.deo.ws.exception.ErrorConsultaDocumentoException;
import com.egoveris.deo.ws.exception.ParametroInvalidoConsultaException;
import com.egoveris.deo.ws.exception.TipoReservaNoExisteException;
import com.egoveris.deo.ws.service.IExternalTipoReservaService;
import com.egoveris.shared.map.ListMapper;
import com.egoveris.shareddocument.base.service.IWebDavService;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.te.base.dao.ExpedienteElectronicoDAO;
import com.egoveris.te.base.exception.ArchivoUploadException;
import com.egoveris.te.base.exception.NegocioException;
import com.egoveris.te.base.exception.external.TeException;
import com.egoveris.te.base.model.ActividadDTO;
import com.egoveris.te.base.model.ArchivoDeTrabajoDTO;
import com.egoveris.te.base.model.ArchivoDeTrabajoVisualizacionDTO;
import com.egoveris.te.base.model.DatosDeBusqueda;
import com.egoveris.te.base.model.DatosEnvioParalelo;
import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.model.ExpedienteAsociadoEntDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoIndex;
import com.egoveris.te.base.model.ExpedienteMetadataDTO;
import com.egoveris.te.base.model.PropertyConfigurationDTO;
import com.egoveris.te.base.model.ReparticionParticipanteDTO;
import com.egoveris.te.base.model.SolicitudExpedienteDTO;
import com.egoveris.te.base.model.SubProcesoOperacionDTO;
import com.egoveris.te.base.model.TareaParaleloDTO;
import com.egoveris.te.base.model.TrataDTO;
import com.egoveris.te.base.model.TrataTipoResultadoDTO;
import com.egoveris.te.base.model.expediente.ExpedienteElectronico;
import com.egoveris.te.base.repository.LogESBRepository;
import com.egoveris.te.base.repository.SolicitudExpedienteRepository;
import com.egoveris.te.base.repository.SubProcesoOperacionRepository;
import com.egoveris.te.base.repository.expediente.ExpedienteElectronicoRepository;
import com.egoveris.te.base.service.expediente.ExpedienteAsociadoService;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.service.expediente.constants.ConstantesPase;
import com.egoveris.te.base.service.iface.IActividadExpedienteService;
import com.egoveris.te.base.service.iface.IAdministracionDeDocumentosOficialesService;
import com.egoveris.te.base.service.iface.IExpedienteFormularioService;
import com.egoveris.te.base.service.iface.ISolrService;
import com.egoveris.te.base.service.iface.ITaskViewService;
import com.egoveris.te.base.util.BusinessFormatHelper;
import com.egoveris.te.base.util.ConstantesCore;
import com.egoveris.te.base.util.ConstantesServicios;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.ExpedienteElectronicoUtils;
import com.egoveris.te.base.util.WorkFlow;
import com.egoveris.te.base.util.WorkFlowScriptUtils;
import com.egoveris.te.model.exception.ExpedienteNoDisponibleException;
import com.egoveris.te.model.exception.ParametroIncorrectoException;
import com.egoveris.te.model.exception.ProcesoFallidoException;
import com.egoveris.vucfront.ws.service.ExternalTareaService;

@Service
@Transactional
public class ExpedienteElectronicoServiceImpl implements ExpedienteElectronicoService, ApplicationContextAware {

	private static Logger logger = LoggerFactory.getLogger(ExpedienteElectronicoServiceImpl.class);

	private static final String RECHAZADA = "Rechazada";
	private static final String MOTIVO_DESVINCULACION_TRAMITACION_CONJUNTA = "Desvinculación en Tramitacion Conjunta";
	private static final String TRAMITACION_EN_PARALELO = "Paralelo";
	private static final String INICIAR_EXPEDIENTE = "Iniciar Expediente";
	private static final String INICIACION = "Iniciacion";
	private static final String GUARDA_TEMPORAL = "Guarda Temporal";
	private static final String SISTEMA_EXPEDIENTE_ELECTRONICO = "EE";
	private static final String RESERVA_PARCIAL = "PARCIAL";
	private static final String RESERVA_TOTAL = "TOTAL";

	private static final String LISTA_DOCS_NULA = "La lista de documentos a vincular es nula.";

	private static ApplicationContext applicationContext = null;
	private Boolean consultaBd;
	private String motivoParaleloAnterior = "";
	private String sistemaSolicitanteDeAdjuntarDocAEEGuardaTemporal;

	@Autowired
	private AppProperty dBProperty;

	@Autowired
	private IAdministracionDeDocumentosOficialesService externalAdminDocumentOficialService;

	// Please use the repository below
	@Deprecated
	@Autowired
	private ExpedienteElectronicoDAO expedienteElectronicoDAO;
	@Autowired
	ExpedienteElectronicoRepository expedienteElectronicoRepository;
	@Autowired
	private ArchivoDeTrabajoService archivoService;
	@Autowired
	private AppProperty properties;
	@Autowired
	private TrataService trataService;
	@Autowired
	private PermisoVisualizacionService permisoVisualizacion;
	@Autowired
	private IWebDavService webDavService;
	@Autowired
	private CaratulacionService caratulador;
	@Autowired
	private HistorialOperacionService historialOperacionService;
	@Autowired
	private DocumentoGedoService documentoGedoService;
	@Autowired
	private TareaParaleloService tareaParaleloService;
	@Autowired
	private UsuariosSADEService usuariosSADEService;
	@Autowired
	private TramitacionConjuntaService tramitacionConjuntaService;
	@Autowired
	private IExternalTipoReservaService tipoReservaGedoService;
	@Autowired
	private ProcessEngine processEngine;
	@Autowired
	private ISolrService solrService;
	@Autowired
	private String reparticionGT;
	@Autowired
	private IExpedienteFormularioService formularioService;
	@Autowired
	private DocumentoManagerService documentoManagerService;
	@Autowired
	private IActividadService actividadService;
	@Autowired
	private IActividadExpedienteService actividadExpedienteService;
	@Autowired
	private ITaskViewService taskViewService;
	@Autowired
	private LogESBRepository logESBRepository;
	@Autowired
	private SubProcesoOperacionRepository subProcesoOperacionRepository;
	@Autowired
	private PaseExpedienteService paseExpedienteService;
	@Autowired
	private SubprocessService subprocessService;
	@Autowired
	private FusionService fusionService;
	@Autowired
	private ExpedienteAsociadoService expedienteAsociadoService;
	@Autowired
	private ControlTransaccionService controlTransaccionService;
	@Autowired
	private ExternalTareaService externalTareaService;

	@Autowired
	private SolicitudExpedienteRepository solicitudExpedienteRepository;
	@Autowired
	@Qualifier("teCoreMapper")
	private Mapper mapper;
	@Value("${app.fuse.demo.url}")
	private String IP_FUSE;

	private String sistemasSolicitanteArchivo;

	public static WorkFlowService getWorkFlowService() {
		if (logger.isDebugEnabled()) {
			logger.debug("getWorkFlowService() - start");
		}

		WorkFlowService returnWorkFlowService = (WorkFlowService) getService(ConstantesServicios.WORKFLOW_SERVICE);

		if (logger.isDebugEnabled()) {
			logger.debug("getWorkFlowService() - end - return value={}", returnWorkFlowService);
		}

		return returnWorkFlowService;
	}

	public static Object getService(String serviceName) {
		if (logger.isDebugEnabled()) {
			logger.debug("getService(serviceName={}) - start", serviceName);
		}

		Object returnObject = applicationContext.getBean(serviceName);

		if (logger.isDebugEnabled()) {
			logger.debug("getService(String) - end - return value={}", returnObject);
		}

		return returnObject;
	}

	public ExpedienteElectronicoDTO obtenerExpedienteElectronicoPorCodigo(String codigoEE)
			throws ParametroIncorrectoException {
		if (StringUtils.isBlank(codigoEE)) {
			throw new ParametroIncorrectoException("codigoEE is BLANK");
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerExpedienteElectronicoPorCodigo(codigoEE={}) - start", codigoEE);
		}

		List<String> listDesgloseCodigoEE = BusinessFormatHelper.obtenerDesgloseCodigoEE(codigoEE);

		ExpedienteElectronicoDTO expedienteElectronicoDTO = this.obtenerExpedienteElectronico(
				listDesgloseCodigoEE.get(0), Integer.valueOf(listDesgloseCodigoEE.get(1)),
				Integer.valueOf(listDesgloseCodigoEE.get(2)), listDesgloseCodigoEE.get(5));

		if (logger.isDebugEnabled()) {
			logger.debug("obtenerExpedienteElectronicoPorCodigo(String) - end - return value={}",
					expedienteElectronicoDTO);
		}

		return expedienteElectronicoDTO;
	}

	/**
	 * 
	 * Metodo encargado de vincular los documentos generados a Expediente de TE
	 * 
	 * @param solicitudDTO
	 */
	@Override
	public void vinculacionDocumentosAExpedienteTe(String acronimoGedo, String codigoExp, String usuario) {
		List<String> codigoDocumentos = new ArrayList<>();

		codigoDocumentos.add(acronimoGedo);

		externalAdminDocumentOficialService.vincularDocumentosOficiales("TE",usuario, codigoExp, codigoDocumentos);
	}

	public ExpedienteElectronicoDTO obtenerSolicitudExpediente(Long idSolicitudExpediente) {
		ExpedienteElectronico entity = expedienteElectronicoRepository
				.findBySolicitudIniciadoraId(idSolicitudExpediente);
		ExpedienteElectronicoDTO expedienteElectronicoDTO = null;

		if (entity != null) {
			expedienteElectronicoDTO = mapper.map(entity, ExpedienteElectronicoDTO.class);
		}

		return expedienteElectronicoDTO;
	}

	public void vincularDocumentoGEDO(ExpedienteElectronicoDTO expedienteElectronicoDTO, String docGEDO, String usuario)
			throws ProcesoFallidoException {
		if (logger.isDebugEnabled()) {
			logger.debug("vincularDocumentoGEDO(expedienteElectronico={}, docGEDO={}, usuario={}) - start",
					expedienteElectronicoDTO, docGEDO, usuario);
		}

		List<String> docsGedo = new ArrayList<>();
		docsGedo.add(docGEDO);

		this.vincularDocumentoGEDO(expedienteElectronicoDTO, docsGedo, usuario);

		if (logger.isDebugEnabled()) {
			logger.debug("vincularDocumentoGEDO(ExpedienteElectronicoDTO, String, String) - end");
		}
	}

	public void vincularDocumentoGEDO_Definitivo(ExpedienteElectronicoDTO expedienteElectronicoDTO, String docGEDO,
			String usuario) throws ParametroIncorrectoException, ProcesoFallidoException {
		if (logger.isDebugEnabled()) {
			logger.debug("vincularDocumentoGEDO_Definitivo(expedienteElectronico={}, docGEDO={}, usuario={}) - start",
					expedienteElectronicoDTO, docGEDO, usuario);
		}

		List<String> docsGedo = new ArrayList<>();
		docsGedo.add(docGEDO);
		this.vincularDocumentoGEDO(expedienteElectronicoDTO, docsGedo, usuario, true);

		if (logger.isDebugEnabled()) {
			logger.debug("vincularDocumentoGEDO_Definitivo(ExpedienteElectronicoDTO, String, String) - end");
		}
	}

	public void vincularDocumentoGEDO(ExpedienteElectronicoDTO expedienteElectronicoDTO, List<String> docsGEDO,
			String usuario) throws ParametroIncorrectoException, ProcesoFallidoException {
		if (logger.isDebugEnabled()) {
			logger.debug("vincularDocumentoGEDO(expedienteElectronico={}, docsGEDO={}, usuario={}) - start",
					expedienteElectronicoDTO, docsGEDO, usuario);
		}

		vincularDocumentoGEDO(expedienteElectronicoDTO, docsGEDO, usuario, false);

		if (logger.isDebugEnabled()) {
			logger.debug("vincularDocumentoGEDO(ExpedienteElectronicoDTO, List<String>, String) - end");
		}
	}

	@Override
	@Deprecated
	public void actualizarWorkflowsEnGuardaTemporalCallaBleStatement() {
		if (logger.isDebugEnabled()) {
			logger.debug("actualizarWorkflowsEnGuardaTemporalCallaBleStatement() - start");
		}

		this.expedienteElectronicoDAO.actualizarWorkflowsEnGuardaTemporalCallaBleStatement();

		if (logger.isDebugEnabled()) {
			logger.debug("actualizarWorkflowsEnGuardaTemporalCallaBleStatement() - end");
		}
	}

	@Override
	@Deprecated
	public void regularizarActividadesGEDO() {
		if (logger.isDebugEnabled()) {
			logger.debug("regularizarActividadesGEDO() - start");
		}

		this.expedienteElectronicoDAO.regularizarActividadesStatement();

		if (logger.isDebugEnabled()) {
			logger.debug("regularizarActividadesGEDO() - end");
		}
	}

	@Deprecated
	public void executeDeltaImport() {
		if (logger.isDebugEnabled()) {
			logger.debug("executeDeltaImport() - start");
		}

		expedienteElectronicoDAO.executeDeltaImport();

		if (logger.isDebugEnabled()) {
			logger.debug("executeDeltaImport() - end");
		}
	}

	public void vincularDocumentoGedo(ExpedienteElectronicoDTO expedienteElectronicoDTO, DocumentoDTO doc,
			String usuario) throws ParametroIncorrectoException {
		if (logger.isDebugEnabled()) {
			logger.debug("vincularDocumentoGedo(expediente={}, doc={}, usuario={}) - start", expedienteElectronicoDTO,
					doc, usuario);
		}

		boolean contieneDoc = documentoEnExp(expedienteElectronicoDTO.getDocumentos(), doc);

		String relativeUri = doc.getNumeroSade() + ".pdf";
		doc.setNombreArchivo(relativeUri);

		if (!contieneDoc) {
			doc.setFechaAsociacion(new Date());
			doc.setUsuarioAsociador(usuario);
			doc.setIdTask(obtenerWorkingTask(expedienteElectronicoDTO).getExecutionId());
			doc.setDefinitivo(true);
			expedienteElectronicoDTO.getDocumentos().add(doc);
		}

		this.modificarExpedienteElectronico(expedienteElectronicoDTO);

		if (logger.isDebugEnabled()) {
			logger.debug("vincularDocumentoGedo(ExpedienteElectronicoDTO, DocumentoDTO, String) - end");
		}
	}

	private void vincularDocumentoGEDO(ExpedienteElectronicoDTO expedienteElectronicoDTO, List<String> docsGEDO,
			String usuario, boolean definitivo) throws ParametroIncorrectoException, ProcesoFallidoException {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"vincularDocumentoGEDO(expedienteElectronico={}, docsGEDO={}, usuario={}, definitivo={}) - start",
					expedienteElectronicoDTO, docsGEDO, usuario, definitivo);
		}

		if (docsGEDO == null || docsGEDO.isEmpty()) {
			throw new ParametroIncorrectoException(LISTA_DOCS_NULA, null);
		}

		for (String codigoDocumento : docsGEDO) {
			DocumentoDTO documentoGEDO = documentoGedoService.obtenerDocumentoPorNumeroEstandar(codigoDocumento);

			if (documentoGEDO != null) {
				boolean contieneDoc;

				if (definitivo) {
					contieneDoc = documentoEnExp(expedienteElectronicoDTO.getDocumentos(), documentoGEDO);
					String relativeUri = documentoGEDO.getNumeroSade() + ".pdf";
					documentoGEDO.setNombreArchivo(relativeUri);
				} else {
					contieneDoc = expedienteElectronicoDTO.getDocumentos().contains(documentoGEDO);
				}

				if (!contieneDoc) {
					documentoGEDO.setFechaAsociacion(new Date());
					documentoGEDO.setUsuarioAsociador(usuario);
					documentoGEDO.setIdTask(obtenerWorkingTask(expedienteElectronicoDTO).getExecutionId());
					documentoGEDO.setDefinitivo(definitivo);
					expedienteElectronicoDTO.getDocumentos().add(documentoGEDO);
				} else {
					throw new ProcesoFallidoException("El documento ya se encuentra vinculado al expediente.", null);
				}
			} else {
				throw new ParametroIncorrectoException("No fue posible encontrar el documento.", null);
			}
		}

		this.modificarExpedienteElectronico(expedienteElectronicoDTO);

		if (logger.isDebugEnabled()) {
			logger.debug("vincularDocumentoGEDO(ExpedienteElectronicoDTO, List<String>, String, boolean) - end");
		}
	}

	private boolean documentoEnExp(List<DocumentoDTO> documentos, DocumentoDTO documentoGEDO) {
		if (logger.isDebugEnabled()) {
			logger.debug("documentoEnExp(documentos={}, documentoGEDO={}) - start", documentos, documentoGEDO);
		}

		for (DocumentoDTO documento : documentos) {
			if (documentosIguales(documento, documentoGEDO)) {
				if (logger.isDebugEnabled()) {
					logger.debug("documentoEnExp(List<DocumentoDTO>, DocumentoDTO) - end - return value={}", true);
				}

				return true;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("documentoEnExp(List<DocumentoDTO>, DocumentoDTO) - end - return value={}", false);
		}

		return false;
	}

	private boolean documentosIguales(DocumentoDTO documento, DocumentoDTO documentoGEDO) {
		if (logger.isDebugEnabled()) {
			logger.debug("documentosIguales(documento={}, documentoGEDO={}) - start", documento, documentoGEDO);
		}

		if (documento == documentoGEDO) {
			return true;
		} else if (documentoGEDO == null) {
			return false;
		} else if (documento.getNumeroSade() == null) {
			if (documentoGEDO.getNumeroSade() != null) {
				return false;
			}
		} else if (!documento.getNumeroSade().equals(documentoGEDO.getNumeroSade())) {

			if (logger.isDebugEnabled()) {
				logger.debug("documentosIguales(DocumentoDTO, DocumentoDTO) - end - return value={}", false);
			}

			return false;
		}

		if (logger.isDebugEnabled()) {
			logger.debug("documentosIguales(DocumentoDTO, DocumentoDTO) - end - return value={}", true);
		}

		return true;
	}

	/**
	 * Obtiene la tarea del expediente electrónico otorgado por parámetro.
	 * 
	 * @param expedienteElectronicoDTO
	 * @return la tarea correspondiente al expediente electrónico otorgado por
	 *         parámetro.
	 * @throws ParametroIncorrectoException
	 */
	public Task obtenerWorkingTask(ExpedienteElectronicoDTO expedienteElectronicoDTO)
			throws ParametroIncorrectoException {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerWorkingTask(expedienteElectronico={}) - start", expedienteElectronicoDTO);
		}

		if (expedienteElectronicoDTO != null) {
			// obtengo la task del expediente electronico.
			TaskQuery taskQuery = processEngine.getTaskService().createTaskQuery()
					.executionId(expedienteElectronicoDTO.getIdWorkflow());
			Task task = taskQuery.uniqueResult();
			if (task == null) { // FIX para tareas que se generan como hijas.
				try {
					String id = processEngine.getExecutionService().createProcessInstanceQuery()
							.processInstanceId(expedienteElectronicoDTO.getIdWorkflow()).uniqueResult().getExecutions()
							.iterator().next().getId();
					TaskQuery taskQuery2 = processEngine.getTaskService().createTaskQuery().executionId(id);
					task = taskQuery2.uniqueResult();
				} catch (NullPointerException e) {
					logger.error("Se ha producido un error al obtenerWorkingTask:", e);
				}
			}
			
			if (logger.isDebugEnabled()) {
				logger.debug("obtenerWorkingTask(ExpedienteElectronicoDTO) - end - return value={}", task);
			}

			return task;
		} else {
			throw new ParametroIncorrectoException("El expediente electrónico es nulo.", null);
		}
	}

	public Task obtenerWorkingTask(String idTask) throws ParametroIncorrectoException {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerWorkingTask(idTask={}) - start", idTask);
		}

		if (idTask != null) {
			// obtengo la task del expediente electronico.
			TaskQuery taskQuery = processEngine.getTaskService().createTaskQuery().executionId(idTask);
			Task returnTask = taskQuery.uniqueResult();

			if (logger.isDebugEnabled()) {
				logger.debug("obtenerWorkingTask(String) - end - return value={}", returnTask);
			}

			return returnTask;
		} else {
			throw new ParametroIncorrectoException("El expediente electrónico es nulo.", null);
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public void generarPaseExpedienteElectronico(Task workingTask, ExpedienteElectronicoDTO expedienteElectronicoDTO,
			String loggedUsername, String estadoSeleccionado, String motivoExpediente, Map<String, String> detalles,
			TransactionStatus status) throws ArchivoUploadException {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"generarPaseExpedienteElectronico(workingTask={}, expedienteElectronico={}, loggedUsername={}, estadoSeleccionado={}, motivoExpediente={}, detalles={}, status={}) - start",
					workingTask, expedienteElectronicoDTO, loggedUsername, estadoSeleccionado, motivoExpediente,
					detalles, status);
		}

		final StringBuilder txERROR = new StringBuilder();
		txERROR.delete(0, txERROR.length());

		this.generarPaseExpedienteElectronico(workingTask, expedienteElectronicoDTO, loggedUsername, estadoSeleccionado,
				motivoExpediente, null, detalles, null, true);
		WorkFlow workFlow = getWorkFlowService().createWorkFlow(expedienteElectronicoDTO.getId(), estadoSeleccionado);
		workFlow.initParameters(detalles);

		try {
			workFlow.execute(workingTask, processEngine);
		} catch (InterruptedException e) {
			logger.error(
					"generarPaseExpedienteElectronico(Task, ExpedienteElectronicoDTO, String, String, String, Map<String,String>, TransactionStatus)",
					e);
			Thread.currentThread().interrupt();

			status.setRollbackOnly();
			txERROR.delete(0, txERROR.length());
			txERROR.append(e.getMessage());
		}

		if (txERROR.length() > 0) {
			throw new TeException(txERROR.toString(), null);
		}

		if (logger.isDebugEnabled()) {
			logger.debug(
					"generarPaseExpedienteElectronico(Task, ExpedienteElectronicoDTO, String, String, String, Map<String,String>, TransactionStatus) - end");
		}
	}

	/**
	 * Genera pase bla bla. Persiste los documentos.
	 * 
	 * @param workingTask
	 * @param processEngine
	 * @param expedienteElectronicoDTO
	 * @param loggedUsername
	 * @param estadoSeleccionado
	 * @param motivoExpediente
	 * @param detalles
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void generarPaseExpedienteElectronico(Task workingTask, ExpedienteElectronicoDTO expedienteElectronicoDTO,
			String loggedUsername, String estadoSeleccionado, String motivoExpediente, Map<String, String> detalles) {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"generarPaseExpedienteElectronico(workingTask={}, expedienteElectronico={}, loggedUsername={}, estadoSeleccionado={}, motivoExpediente={}, detalles={}) - start",
					workingTask, expedienteElectronicoDTO, loggedUsername, estadoSeleccionado, motivoExpediente,
					detalles);
		}

		if (detalles == null) {
			detalles = new HashMap<>();
		}
		if (detalles.get(ConstantesPase.DESTINATARIO) == null) {
			detalles.put(ConstantesPase.DESTINATARIO, loggedUsername);
		}

		boolean generarDocPase = false;

		if (dBProperty.getString("GENERAR_DOC_PASE") != null && "1".equals(dBProperty.getString("GENERAR_DOC_PASE"))) {
			generarDocPase = true;
		}

		this.generarPaseExpedienteElectronico(workingTask, expedienteElectronicoDTO, loggedUsername, estadoSeleccionado,
				motivoExpediente, null, detalles, null, generarDocPase);

		if (logger.isDebugEnabled()) {
			logger.debug(
					"generarPaseExpedienteElectronico(Task, ExpedienteElectronicoDTO, String, String, String, Map<String,String>) - end");
		}
	}

	public void generarPaseExpedienteElectronico(Task workingTask, ExpedienteElectronicoDTO expedienteElectronicoDTO,
			String loggedUsername, String estadoSeleccionado, String motivoExpediente, String aclaracion,
			Map<String, String> detalles, String numeroSadePase, Boolean generarDoc) {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"generarPaseExpedienteElectronico(workingTask={}, expedienteElectronico={}, loggedUsername={}, estadoSeleccionado={}, motivoExpediente={}, aclaracion={}, detalles={}, numeroSadePase={}, generarDoc={}) - start",
					workingTask, expedienteElectronicoDTO, loggedUsername, estadoSeleccionado, motivoExpediente,
					aclaracion, detalles, numeroSadePase, generarDoc);
		}

		Usuario usuario = usuariosSADEService.getDatosUsuario(loggedUsername);
		generarPaseExpedienteElectronico(workingTask, expedienteElectronicoDTO, usuario, estadoSeleccionado,
				motivoExpediente, aclaracion, detalles, true, numeroSadePase, generarDoc);

		if (logger.isDebugEnabled()) {
			logger.debug(
					"generarPaseExpedienteElectronico(Task, ExpedienteElectronicoDTO, String, String, String, String, Map<String,String>, String, Boolean) - end");
		}
	}

	public void generarPaseExpedienteElectronicoAdministrador(Task workingTask,
			ExpedienteElectronicoDTO expedienteElectronicoDTO, String loggedUsername, String estadoSeleccionado,
			String motivoExpediente, Map<String, String> detalles) {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"generarPaseExpedienteElectronicoAdministrador(workingTask={}, expedienteElectronico={}, loggedUsername={}, estadoSeleccionado={}, motivoExpediente={}, detalles={}) - start",
					workingTask, expedienteElectronicoDTO, loggedUsername, estadoSeleccionado, motivoExpediente,
					detalles);
		}

		Usuario usuario = usuariosSADEService.getDatosUsuario(loggedUsername);
		generarPaseExpedienteElectronico(workingTask, expedienteElectronicoDTO, usuario, estadoSeleccionado,
				motivoExpediente, null, detalles, true, null, true);

		if (logger.isDebugEnabled()) {
			logger.debug(
					"generarPaseExpedienteElectronicoAdministrador(Task, ExpedienteElectronicoDTO, String, String, String, Map<String,String>) - end");
		}
	}

	public String formatoMotivo(String motivo) {
		if (logger.isDebugEnabled()) {
			logger.debug("formatoMotivo(motivo={}) - start", motivo);
		}

		String salida = null;

		if (motivo != null) {
			salida = Jsoup.parse((String) motivo).text();

			if (salida.length() > 3500) {
				salida = salida.substring(0, 3500) + "...";
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("formatoMotivo(String) - end - return value={}", salida);
		}

		return salida;
	}

	public synchronized void generarPaseExpedienteElectronicoSinDocumentoMigracion(Task workingTask,
			ExpedienteElectronicoDTO expedienteElectronicoDTO, Usuario user, String estadoSeleccionado,
			String motivoExpediente, Map<String, String> detalles) {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"generarPaseExpedienteElectronicoSinDocumentoMigracion(workingTask={}, expedienteElectronico={}, user={}, estadoSeleccionado={}, motivoExpediente={}, detalles={}) - start",
					workingTask, expedienteElectronicoDTO, user, estadoSeleccionado, motivoExpediente, detalles);
		}

		Usuario usuario = usuariosSADEService.getDatosUsuario(user.getUsername());

		if (usuario == null) {
			// si el usuario no esta quiere decir que el usuario que recibo es
			// un
			// usuarioMigracion transformado al objeto usuario
			// dieron de baja al usuario
			usuario = new Usuario();
			usuario.setUsername(user.getUsername());
			usuario.setCodigoReparticionOriginal(user.getCodigoReparticionOriginal());
			usuario.setCodigoSectorInterno(user.getCodigoSectorInterno());
			usuario.setCodigoReparticion(user.getCodigoReparticionOriginal());
		}

		generarPaseExpedienteElectronico(workingTask, expedienteElectronicoDTO, usuario, estadoSeleccionado,
				motivoExpediente, null, detalles, true, null, false);

		if (logger.isDebugEnabled()) {
			logger.debug(
					"generarPaseExpedienteElectronicoSinDocumentoMigracion(Task, ExpedienteElectronicoDTO, Usuario, String, String, Map<String,String>) - end");
		}
	}

	/**
	 * Genera pase.
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	public void generarPaseExpedienteElectronico(Task workingTask, ExpedienteElectronicoDTO expedienteElectronicoDTO,
			String loggedUsername, String estadoSeleccionado, String motivoExpediente, Map<String, String> detalles,
			boolean persistirCambios) {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"generarPaseExpedienteElectronico(workingTask={}, expedienteElectronico={}, loggedUsername={}, estadoSeleccionado={}, motivoExpediente={}, detalles={}, persistirCambios={}) - start",
					workingTask, expedienteElectronicoDTO, loggedUsername, estadoSeleccionado, motivoExpediente,
					detalles, persistirCambios);
		}

		Usuario usuario = usuariosSADEService.getDatosUsuario(loggedUsername);

		this.generarPaseExpedienteElectronico(workingTask, expedienteElectronicoDTO, usuario, estadoSeleccionado,
				motivoExpediente, null, detalles, persistirCambios, null, true);

		if (logger.isDebugEnabled()) {
			logger.debug(
					"generarPaseExpedienteElectronico(Task, ExpedienteElectronicoDTO, String, String, String, Map<String,String>, boolean) - end");
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	private void generarPaseExpedienteElectronico(Task workingTask, ExpedienteElectronicoDTO expedienteElectronicoDTO,
			Usuario usuario, String estadoSeleccionado, String motivoExpediente, String aclaracion,
			Map<String, String> detalles, boolean persistirCambios, String numeroSadePase, boolean generarDocGedo) {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"generarPaseExpedienteElectronico(workingTask={}, expedienteElectronico={}, usuario={}, estadoSeleccionado={}, motivoExpediente={}, aclaracion={}, detalles={}, persistirCambios={}, numeroSadePase={}, generarDocGedo={}) - start",
					workingTask, expedienteElectronicoDTO, usuario, estadoSeleccionado, motivoExpediente, aclaracion,
					detalles, persistirCambios, numeroSadePase, generarDocGedo);
		}

		try {
			detalles.put(ConstantesWeb.ESTADO, expedienteElectronicoDTO.getEstado());
			detalles.put(ConstantesWeb.MOTIVO, formatoMotivo(motivoExpediente));

			if (estadoSeleccionado != null) {
				detalles.put(ConstantesWeb.ESTADO, estadoSeleccionado);

				if (estadoSeleccionado.equals(GUARDA_TEMPORAL)) {
					detalles.put(ConstantesWeb.DESTINATARIO, "REGCIVIL");
				}
			}

			String loggedUsername = usuario.getUsername();
			detalles.put(ConstantesWeb.REPARTICION_USUARIO, usuario.getCodigoReparticion());
			detalles.put(ConstantesWeb.SECTOR_USUARIO, usuario.getCodigoSectorInterno());

			String codigoTrata = expedienteElectronicoDTO.getTrata().getCodigoTrata();
			List<ReparticionParticipanteDTO> listRepRect = obtenerReparticionesRectoras(expedienteElectronicoDTO,
					loggedUsername, codigoTrata);

			// Se setea el atributo definivo de Archivos de Trabajo, Documentos
			// y Expediente Asociado
			if (persistirCambios) {
				hacerExpedienteAsociadoDefinitivo(expedienteElectronicoDTO.getListaExpedientesAsociados());
				hacerDocumentosDefinitivo(expedienteElectronicoDTO.getDocumentos());
				hacerArchivoDefinitivo(expedienteElectronicoDTO.getArchivosDeTrabajo());

				if (expedienteElectronicoDTO.getEsReservado()) {
					asignarPermisosVisualizacionArchivo(expedienteElectronicoDTO, usuario, listRepRect);
				}
			}

			/*
			 * Se realiza esta modificacion para que en el documento pdf se
			 * visualice el Destinatario debajo del motivo.
			 */
			String destinatario = detalles.get(ConstantesPase.DESTINATARIO);
			Usuario datosUsuario = usuariosSADEService.getDatosUsuario(destinatario);
			String motivoDestinatario;

			if (datosUsuario != null) {
				motivoDestinatario = motivoExpediente + "\n" + "\n" + " Destinatario: "
						+ datosUsuario.getNombreApellido();
			} else {
				// Si es nulo es por que no es un usuario, por lo tanto es una
				// reparticion
				motivoDestinatario = motivoExpediente + "\n" + "\n" + " Destinatario: " + destinatario;
				String[] campos = destinatario.split("-");
				destinatario = campos[0];
			}

			if (generarDocGedo) {
				DocumentoDTO documentoMotivo;

				if (numeroSadePase == null) {
					if (detalles.get("idListDestinatarios") != null) {
						documentoMotivo = documentoGedoService.generarDocumentoGEDOConComunicacion(
								expedienteElectronicoDTO, loggedUsername, motivoExpediente,
								detalles.get("idListDestinatarios"));
					} else {
						documentoMotivo = documentoGedoService.generarDocumentoGEDO(expedienteElectronicoDTO,
								motivoDestinatario, aclaracion, loggedUsername);
					}
				} else {
					documentoMotivo = new DocumentoDTO();
					documentoMotivo.setFechaCreacion(new Date());
					documentoMotivo.setFechaAsociacion(new Date());
					documentoMotivo.setNombreUsuarioGenerador(loggedUsername);
					documentoMotivo.setDefinitivo(true);
					documentoMotivo.setNombreArchivo(numeroSadePase + ".pdf");
					documentoMotivo.setNumeroSade(numeroSadePase);
					documentoMotivo.setMotivo(ConstantesWeb.MOTIVO_PASE);
					documentoMotivo.setTipoDocGenerado(ConstantesWeb.TIPO_DOCUMENTO_PASE);
					documentoMotivo.setTipoDocAcronimo(this.dBProperty.getString("acronimoPaseEE"));
				}

				documentoMotivo.setUsuarioAsociador(loggedUsername);
				documentoMotivo.setIdTask(workingTask.getExecutionId());

				if (expedienteElectronicoDTO.getEsCabeceraTC() != null && expedienteElectronicoDTO.getEsCabeceraTC()) {
					documentoMotivo.setIdExpCabeceraTC(expedienteElectronicoDTO.getId());
				}

				expedienteElectronicoDTO.getDocumentos().add(documentoMotivo);
			}

			Date fechaModificacion = new Date();
			expedienteElectronicoDTO.setFechaModificacion(fechaModificacion);
			expedienteElectronicoDTO.setUsuarioModificacion(loggedUsername);
			expedienteElectronicoDTO.setEstado(estadoSeleccionado);

			// 13-06-2017: Result type
			if (detalles.containsKey("resultValue")) {
				expedienteElectronicoDTO.setResultado((String) detalles.get("resultValue"));
			}

			if (detalles.containsKey("resultLabel")) {
				expedienteElectronicoDTO.setPropertyResultado(new PropertyConfigurationDTO());
				expedienteElectronicoDTO.getPropertyResultado().setValor(detalles.get("resultLabel"));
			}

			actualizaArchivosDeTrabajo(expedienteElectronicoDTO, loggedUsername, datosUsuario, destinatario,
					listRepRect);
			formularioService.makeDefinitive(expedienteElectronicoDTO.getId());

			if (expedienteElectronicoDTO.getEsCabeceraTC() != null && !expedienteElectronicoDTO.getEsCabeceraTC()) {
				this.modificarExpedienteElectronicoPorTramitacionConjuntaOFusion(expedienteElectronicoDTO);

			} else {
				this.grabarExpedienteElectronico(expedienteElectronicoDTO);
			}

			if (expedienteElectronicoDTO.getEsCabeceraTC() != null && expedienteElectronicoDTO.getEsCabeceraTC()) {
				tramitacionConjuntaService.actulizarDocumentosEnTramitacionConjunta(expedienteElectronicoDTO);
			}

			if (expedienteElectronicoDTO.getEsReservado()) {
				Usuario userLogeado = usuariosSADEService.getDatosUsuario(loggedUsername);
				actualizarDocumentosEnGedo(expedienteElectronicoDTO, destinatario, userLogeado, codigoTrata);
				documentoGedoService.asignarPermisosVisualizacionGEDO(expedienteElectronicoDTO, userLogeado,
						listRepRect);
			}

			actualizoWorkFlow(workingTask, loggedUsername, detalles, motivoExpediente);

			guardarDatosEnHistorialOperacion(expedienteElectronicoDTO, usuario, detalles);
		} catch (RemoteAccessException e) {
			String error = Labels.getLabel("ee.servicio.gedo.errorComunicacionTSA");
			logger.error(error, e);
			throw e;
		} catch (RuntimeException rte) {
			try {
				logger.error("Se deben eliminar los documentos de pase generados", rte);
				setVariableWorkFlow(workingTask, ConstantesWeb.MOTIVO, this.motivoParaleloAnterior);

			} catch (Exception e) {
				logger.error("No se pudo eliminar el documento generado", e);
				setVariableWorkFlow(workingTask, ConstantesWeb.MOTIVO, this.motivoParaleloAnterior);
			}
			throw rte;
		}

		if (logger.isDebugEnabled()) {
			logger.debug(
					"generarPaseExpedienteElectronico(Task, ExpedienteElectronicoDTO, Usuario, String, String, String, Map<String,String>, boolean, String, boolean) - end");
		}
	}

	public void generarPaseASolicitudArchivo(ExpedienteElectronicoDTO expedienteElectronicoDTO,
			Map<String, String> detalles) throws ParametroIncorrectoException {
		if (logger.isDebugEnabled()) {
			logger.debug("generarPaseASolicitudArchivo(expediente={}, detalles={}) - start", expedienteElectronicoDTO,
					detalles);
		}

		generarPaseGenerico(expedienteElectronicoDTO, detalles);
		expedienteElectronicoDTO.setFechaSolicitudArchivo(new Date());

		if (logger.isDebugEnabled()) {
			logger.debug("generarPaseASolicitudArchivo(ExpedienteElectronicoDTO, Map<String,String>) - end");
		}
	}

	public void generarPaseAArchivo(ExpedienteElectronicoDTO expedienteElectronicoDTO, Map<String, String> detalles)
			throws ParametroIncorrectoException {
		if (logger.isDebugEnabled()) {
			logger.debug("generarPaseAArchivo(expediente={}, detalles={}) - start", expedienteElectronicoDTO, detalles);
		}

		generarPaseGenerico(expedienteElectronicoDTO, detalles);
		expedienteElectronicoDTO.setFechaArchivo(new Date());

		if (logger.isDebugEnabled()) {
			logger.debug("generarPaseAArchivo(ExpedienteElectronicoDTO, Map<String,String>) - end");
		}
	}

	public void generarPaseGenerico(ExpedienteElectronicoDTO expedienteElectronicoDTO, Map<String, String> detalles)
			throws ParametroIncorrectoException {
		if (logger.isDebugEnabled()) {
			logger.debug("generarPaseGenerico(expediente={}, detalles={}) - start", expedienteElectronicoDTO, detalles);
		}

		Usuario user = usuariosSADEService.getDatosUsuario(detalles.get(ConstantesWeb.LOGGED_USERNAME));

		/**
		 * SM: se mueve a lo ultimo el guardado del historial, en caso de fallo
		 * no queda el historialGrabado
		 * 
		 */
		// guardarDatosEnHistorialOperacion(expediente, user, detalles);

		this.ValidarLicenciaUsuarioOrigen(user.getNombreApellido());

		hacerExpedienteAsociadoDefinitivo(expedienteElectronicoDTO.getListaExpedientesAsociados());
		hacerDocumentosDefinitivo(expedienteElectronicoDTO.getDocumentos());
		hacerArchivoDefinitivo(expedienteElectronicoDTO.getArchivosDeTrabajo());

		Date fechaModificacion = new Date();
		expedienteElectronicoDTO.setFechaModificacion(fechaModificacion);
		expedienteElectronicoDTO.setUsuarioModificacion(detalles.get(ConstantesWeb.LOGGED_USERNAME));
		expedienteElectronicoDTO.setEstado(detalles.get(ConstantesWeb.ESTADO_SELECCIONADO));

		String codigoTrata = expedienteElectronicoDTO.getTrata().getCodigoTrata();

		List<ReparticionParticipanteDTO> listRepRect = obtenerReparticionesRectoras(expedienteElectronicoDTO,
				user.getUsername(), codigoTrata);

		if (expedienteElectronicoDTO.getEsReservado()) {
			asignarPermisosVisualizacionArchivo(expedienteElectronicoDTO, user, listRepRect);
		}

		actualizaArchivosDeTrabajo(expedienteElectronicoDTO, detalles.get(ConstantesWeb.LOGGED_USERNAME), null,
				detalles.get(ConstantesWeb.DESTINATARIO), listRepRect);

		// consultar si es necesario este bloque >>>>>>>>>
		if (expedienteElectronicoDTO.getEsCabeceraTC() != null && !expedienteElectronicoDTO.getEsCabeceraTC()) {
			this.modificarExpedienteElectronicoPorTramitacionConjuntaOFusion(expedienteElectronicoDTO);
		} else {
			this.grabarExpedienteElectronico(expedienteElectronicoDTO);
		}

		if (expedienteElectronicoDTO.getEsCabeceraTC() != null && expedienteElectronicoDTO.getEsCabeceraTC()) {
			tramitacionConjuntaService.actulizarDocumentosEnTramitacionConjunta(expedienteElectronicoDTO);
		}

		if (expedienteElectronicoDTO.getEsReservado()) {
			actualizarDocumentosEnGedo(expedienteElectronicoDTO, detalles.get(ConstantesWeb.DESTINATARIO), user,
					codigoTrata);
			documentoGedoService.asignarPermisosVisualizacionGEDO(expedienteElectronicoDTO, user, listRepRect);
		}

		guardarDatosEnHistorialOperacion(expedienteElectronicoDTO, user, detalles);

		if (logger.isDebugEnabled()) {
			logger.debug("generarPaseGenerico(ExpedienteElectronicoDTO, Map<String,String>) - end");
		}
	}

	public void actualizoWorkFlowIdEnCaratulacion(Task workingTask, Long idExpediente) {
		if (logger.isDebugEnabled()) {
			logger.debug("actualizoWorkFlowIdEnCaratulacion(workingTask={}, idExpediente={}) - start", workingTask,
					idExpediente);
		}

		Map<String, Object> mapVariables = new HashMap<>();

		mapVariables.put(ConstantesWeb.ID_EXPEDIENTE_ELECTRONICO, idExpediente);
		setVariablesWorkFlow(workingTask.getExecutionId(), mapVariables);

		if (logger.isDebugEnabled()) {
			logger.debug("actualizoWorkFlowIdEnCaratulacion(Task, Integer) - end");
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	private void actualizoWorkFlow(Task workingTask, String loggedUsername, Map<String, String> detalles,
			String motivoExpediente) {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"actualizoWorkFlow(workingTask={}, loggedUsername={}, detalles={}, motivoExpediente={}) - start",
					workingTask, loggedUsername, detalles, motivoExpediente);
		}

		Date fechaModificacion = new Date();

		Map<String, Object> mapVariables = new HashMap<>();

		/**
		 * Guardo el usuario Actual para que quede como usuario Anterior al
		 * terminar de enviar la tarea. Si es una tramitacion en paralelo no
		 * debe modificarse el usuario anterior.
		 */

		if (!workingTask.getActivityName().equals(TRAMITACION_EN_PARALELO)) {
			// DEBERIA GUARDAR ESTAS VARIABLES EN DETALLE
			mapVariables.put(ConstantesWeb.USUARIO_ANTERIOR, loggedUsername);
			mapVariables.put(ConstantesWeb.MOTIVO, detalles.get(ConstantesWeb.MOTIVO));

		} else {
			// DEBERIA GUARDAR ESTAS VARIABLES EN DETALLE
			this.motivoParaleloAnterior = (String) getVariableWorkFlow(workingTask, ConstantesWeb.MOTIVO);
			String motivoParalelo = ((this.motivoParaleloAnterior != null) ? this.motivoParaleloAnterior + " | " : "")
					+ loggedUsername + ": " + formatoMotivo(motivoExpediente) + " | ";
			mapVariables.put(ConstantesWeb.MOTIVO, motivoParalelo);
		}

		mapVariables.put(ConstantesWeb.ULTIMA_MODIFICACION, fechaModificacion);
		setVariablesWorkFlow(workingTask.getExecutionId(), mapVariables);

		if (logger.isDebugEnabled()) {
			logger.debug("actualizoWorkFlow(Task, String, Map<String,String>, String) - end");
		}
	}

	private void actualizaArchivosDeTrabajo(ExpedienteElectronicoDTO expedienteElectronicoDTO, String loggedUsername,
			Usuario datosUsuario, String destinatario, List<ReparticionParticipanteDTO> listRepRect) {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"actualizaArchivosDeTrabajo(expedienteElectronico={}, loggedUsername={}, datosUsuario={}, destinatario={}, listRepRect={}) - start",
					expedienteElectronicoDTO, loggedUsername, datosUsuario, destinatario, listRepRect);
		}

		for (int h = 0; h < expedienteElectronicoDTO.getArchivosDeTrabajo().size(); h++) {
			List<ReparticionParticipanteDTO> reparticionesParticipantesYRectoras = new ArrayList<>();

			ReparticionParticipanteDTO rp = obtenerReparticionParticipante(loggedUsername, datosUsuario, destinatario);

			boolean estaEnlista = false;
			boolean estaEnListaActual = false;

			for (int k = 0; k < expedienteElectronicoDTO.getArchivosDeTrabajo().get(h).getReparticionesParticipantes()
					.size(); k++) {
				for (int i = 0; i < listRepRect.size(); i++) {
					if (!expedienteElectronicoDTO.getArchivosDeTrabajo().get(h).isDefinitivo()
							|| (expedienteElectronicoDTO.getArchivosDeTrabajo().get(h).getReparticionesParticipantes()
									.get(k).getReparticion().equals(listRepRect.get(i).getReparticion()))) {
						estaEnListaActual = true;
						break;
					}

					if (!estaEnListaActual
							&& expedienteElectronicoDTO.getArchivosDeTrabajo().get(h).getReparticionesParticipantes()
									.get(k).getReparticion().equals(listRepRect.get(i).getReparticion())) {
						reparticionesParticipantesYRectoras.add(listRepRect.get(i));
					}
				}
			}

			for (int i = 0; i < expedienteElectronicoDTO.getArchivosDeTrabajo().get(h).getReparticionesParticipantes()
					.size(); i++) {
				if (expedienteElectronicoDTO.getArchivosDeTrabajo().get(h).getReparticionesParticipantes().get(i)
						.getReparticion().equals(rp.getReparticion())) {
					estaEnlista = true;
					break;
				}
			}

			if (!estaEnlista) {
				if (!reparticionesParticipantesYRectoras.contains(rp)) {
					reparticionesParticipantesYRectoras.add(rp);
				}
			}

			expedienteElectronicoDTO.getArchivosDeTrabajo().get(h)
					.addReparticionesParticipantes(reparticionesParticipantesYRectoras);
		}

		if (logger.isDebugEnabled()) {
			logger.debug(
					"actualizaArchivosDeTrabajo(ExpedienteElectronicoDTO, String, Usuario, String, List<ReparticionParticipante>) - end");
		}
	}

	/***
	 * Obtiene la reparticion del loggedUser
	 * 
	 * @param loggedUsername
	 * @param datosUsuario
	 * @return
	 */
	private ReparticionParticipanteDTO obtenerReparticionParticipante(String loggedUsername, Usuario datosUsuario,
			String destinatario) {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerReparticionParticipante(loggedUsername={}, datosUsuario={}, destinatario={}) - start",
					loggedUsername, datosUsuario, destinatario);
		}

		ReparticionParticipanteDTO rp = new ReparticionParticipanteDTO();

		if (datosUsuario != null) {
			rp.setReparticion(datosUsuario.getCodigoReparticion());
		} else {
			String[] campos = destinatario.split("-");
			rp.setReparticion(campos[0]);
		}

		rp.setTipoOperacion("ALTA");
		rp.setFechaModificacion(new Date());
		rp.setUsuario(loggedUsername);

		if (logger.isDebugEnabled()) {
			logger.debug("obtenerReparticionParticipante(String, Usuario, String) - end - return value={}", rp);
		}

		return rp;
	}

	/***
	 * Obtiene las reparticiones rectoras de la trata del expediente
	 * 
	 * @param expedienteElectronicoDTO
	 * @param loggedUsername
	 * @return
	 */
	public List<ReparticionParticipanteDTO> obtenerReparticionesRectoras(
			ExpedienteElectronicoDTO expedienteElectronicoDTO, String loggedUsername, String codigoTrata) {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"obtenerReparticionesRectoras(expedienteElectronico={}, loggedUsername={}, codigoTrata={}) - start",
					expedienteElectronicoDTO, loggedUsername, codigoTrata);
		}

		List<ReparticionParticipanteDTO> returnList = new ArrayList<>();

		if (logger.isDebugEnabled()) {
			logger.debug(
					"obtenerReparticionesRectoras(ExpedienteElectronicoDTO, String, String) - end - return value={}",
					returnList);
		}

		return returnList;
	}

	public void actualizarDocumentosEnGedo(ExpedienteElectronicoDTO expedienteElectronicoDTO, String destinatario,
			Usuario user, String codigoTrata) {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"actualizarDocumentosEnGedo(expedienteElectronico={}, destinatario={}, user={}, codigoTrata={}) - start",
					expedienteElectronicoDTO, destinatario, user, codigoTrata);
		}

		String trataAux = codigoTrata;

		TrataDTO objTrata = this.trataService.buscarTrataByCodigo(trataAux);

		for (int i = 0; i < expedienteElectronicoDTO.getDocumentos().size(); i++) {
			DocumentoDTO documento = expedienteElectronicoDTO.getDocumentos().get(i);

			// Se actualiza solo si la fecha de asociacion es posterior a la
			// fecha de
			// reserva
			if (documento.getDefinitivo() && (expedienteElectronicoDTO.getEsReservado()
					&& expedienteElectronicoDTO.getFechaReserva() == null
					|| expedienteElectronicoDTO.getEsReservado()
							&& !expedienteElectronicoDTO.getFechaReserva().after(documento.getFechaAsociacion()))) {

				// Se actualiza GEDO para todos los documentos del expediente
				RequestExternalActualizarReservaReparticionDocumento request = new RequestExternalActualizarReservaReparticionDocumento();

				request.setNumeroDocumento(expedienteElectronicoDTO.getDocumentos().get(i).getNumeroSade());
				request.setUsuarioOReparticionConsulta(destinatario);
				String tipoReserva = objTrata.getTipoReserva().getTipoReserva().equals("SIN RESERVA") ? "TRAMITACION"
						: objTrata.getTipoReserva().getTipoReserva();
				request.setReservaDocumento(tipoReserva);

				// Se envia vacia la lista de reparticiones rectoras debido a
				// que se
				// sigue utilizando el servicio de
				// GEDO para actualizar el tipo de reserva del documento
				request.setReparticionesRectoras(new ArrayList<String>());

				try {
					String rta = this.tipoReservaGedoService.actualizarReservaReparticionDocumento(request);
					logger.info("ExpedienteElectronicoServiceImpl.class"
							+ " actualizarReservaReparticionDocumento - Respuesta " + rta);
				} catch (ParametroInvalidoConsultaException e) {
					logger.error("actualizarDocumentosEnGedo ParametroInvalidoConsultaException " + e);
				} catch (DocumentoNoExisteException e) {
					logger.error("actualizarDocumentosEnGedo DocumentoNoExisteException " + e);
				} catch (ErrorConsultaDocumentoException e) {
					logger.error("actualizarDocumentosEnGedo ErrorConsultaDocumentoException " + e);
				} catch (TipoReservaNoExisteException e) {
					logger.error("actualizarDocumentosEnGedo TipoReservaNoExisteException " + e);
				}

			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("actualizarDocumentosEnGedo(ExpedienteElectronicoDTO, String, Usuario, String) - end");
		}
	}

	public void generarPaseParaleloExpedienteElectronico(Task workingTask,
			ExpedienteElectronicoDTO expedienteElectronicoDTO, String loggedUsername, String estadoSeleccionado,
			String estadoAnterior, String motivoExpediente, List<DatosEnvioParalelo> datosEnvioParalelo)
			throws RuntimeException {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"generarPaseParaleloExpedienteElectronico(workingTask={}, expedienteElectronico={}, loggedUsername={}, estadoSeleccionado={}, estadoAnterior={}, motivoExpediente={}, datosEnvioParalelo={}) - start",
					workingTask, expedienteElectronicoDTO, loggedUsername, estadoSeleccionado, estadoAnterior,
					motivoExpediente, datosEnvioParalelo);
		}

		try {
			for (DatosEnvioParalelo motivosPorDestino : datosEnvioParalelo) {
				String motivo;

				/**
				 * Si tiene un motivo propio debe quedar el motivo propio y no
				 * el motivo del pase del expediente.
				 */

				if (motivosPorDestino.getMotivo() != null && !motivosPorDestino.getMotivo().equals("")) {
					motivo = motivosPorDestino.getMotivo();
				} else {
					motivo = motivoExpediente;
				}

				// CAMBIO DE NOMBRE DE DETALLES A DETALLESPARALELOS
				Map<String, String> detallesParalelos = new HashMap<>();
				detallesParalelos.put(ConstantesWeb.ESTADO, estadoAnterior);
				detallesParalelos.put(ConstantesWeb.MOTIVO, this.formatoMotivo(motivo));

				if (motivosPorDestino.getReparticionesSectores() == null) {
					if (motivosPorDestino.getUserApoderado() == null) {
						detallesParalelos.put(ConstantesWeb.DESTINATARIO, motivosPorDestino.getUser());
					} else {
						detallesParalelos.put(ConstantesWeb.DESTINATARIO, motivosPorDestino.getUserApoderado());
					}
				} else {
					detallesParalelos.put(ConstantesWeb.DESTINATARIO, motivosPorDestino.getReparticionesSectores());
				}

				if (estadoSeleccionado != null) {
					detallesParalelos.put(ConstantesWeb.ESTADO, estadoSeleccionado);
				}

				// Carga de datos en el historial
				Usuario user = usuariosSADEService.getDatosUsuario(loggedUsername);

				Date fechaModificacion = new Date();
				expedienteElectronicoDTO.setFechaModificacion(fechaModificacion);
				expedienteElectronicoDTO.setUsuarioModificacion(loggedUsername);
				expedienteElectronicoDTO.setCantidadSubsanar(null);
				setVariableWorkFlow(workingTask, ConstantesWeb.ULTIMA_MODIFICACION, fechaModificacion);

				// Se realiza la modificacion del String motivoDestinatario para
				// que
				// en el documento pdf se visualice el destinatario.
				String motivoDestinatario;

				// Debe obtener el destino. La reparticion o el usuario o el
				// apoderado si corresponde
				String destino = motivosPorDestino.getReparticionesSectores();
				if (destino == null) {
					destino = motivosPorDestino.getUser();
					motivoDestinatario = motivo + "\n" + "\n" + " Destinatario: "
							+ motivosPorDestino.getApellidoYNombre();

					if (motivosPorDestino.getUserApoderado() != null) {
						destino = motivosPorDestino.getUserApoderado();

						// Si debe recibirlo un apoderado, busco los datos en el
						// LDAP para mostrar nombre y apellido del apoderado.
						Usuario datosUsuario = usuariosSADEService.getDatosUsuario(destino);
						motivoDestinatario = motivo + "\n" + "\n" + " Destinatario: "
								+ datosUsuario.getNombreApellido();
					}
				} else {
					// Si no es nulo es es una reparticion
					motivoDestinatario = motivo + "\n" + "\n" + " Destinatario: " + destino;
				}

				/**
				 * Se realiza esta modificacion para que en el documento pdf se
				 * visualice el Destinatario debajo del motivo.
				 */

				// Guarda en tabla de tareas paralelas.
				guardarTareaParalelo(expedienteElectronicoDTO, loggedUsername, motivo, estadoAnterior, destino);

				/**
				 * Seteo el motivo en el workflow en vacio por que los motivos
				 * de las tareas en paralelo estan persistidos. Ademas los
				 * motivos de respuesta se concatenaran en la pantalla de
				 * integrarTareaParalelo. Pero debo guardar en otra variable el
				 * motivo principal del ultimo pase para obtenerlo en la
				 * pantalla de caratula.
				 */
				setVariableWorkFlow(workingTask, ConstantesWeb.PASE_DE_PASE, this.formatoMotivo(motivoExpediente));
				setVariableWorkFlow(workingTask, ConstantesWeb.MOTIVO, "");

				// Se setea el atributo definivo de Archivos de Trabajo,
				// Documentos y Expediente Asociado
				hacerExpedienteAsociadoDefinitivo(expedienteElectronicoDTO.getListaExpedientesAsociados());
				hacerDocumentosDefinitivo(expedienteElectronicoDTO.getDocumentos());
				hacerArchivoDefinitivo(expedienteElectronicoDTO.getArchivosDeTrabajo());
				String codigoTrata = expedienteElectronicoDTO.getTrata().getCodigoTrata();
				List<ReparticionParticipanteDTO> listRepRect = obtenerReparticionesRectoras(expedienteElectronicoDTO,
						loggedUsername, codigoTrata);

				// archivos de trabajo - resreva
				if (expedienteElectronicoDTO.getEsReservado()) {
					actualizarArchivosDeTrabajoEnLaReservaPorTramitacion(expedienteElectronicoDTO, loggedUsername,
							listRepRect, user);
				}

				// AQUI SE PUEDEN GENERAR PROBLEMAS CON GEDO Y LOS
				// SETVARIABLEWORKFLOW

				DocumentoDTO documentoMotivo = documentoGedoService.generarDocumentoGEDO(expedienteElectronicoDTO,
						motivoDestinatario, null, loggedUsername);
				logger.debug("documentoGedoService.generarDocumentoGEDO", documentoMotivo.toString());

				documentoMotivo.setUsuarioAsociador(loggedUsername);
				documentoMotivo.setIdTask(workingTask.getExecutionId());
				expedienteElectronicoDTO.getDocumentos().add(documentoMotivo);
				expedienteElectronicoDTO.hacerDocsDefinitivos();

				// documentos GEDO - reserva
				if (expedienteElectronicoDTO.getEsReservado()) {
					actualizarDocumentosEnGedo(expedienteElectronicoDTO, loggedUsername, user, codigoTrata);
					documentoGedoService.asignarPermisosVisualizacionGEDO(expedienteElectronicoDTO, user, listRepRect);
				}

				// Guardamos los documentos generados en una lista para que en
				// el caso de que falle la creación de alguno,
				// se puedan borrar en Gedo aquellos que se generaron
				// documentosGenerados.add(documentoMotivo.getNumeroSade());

				// se quitan los 0000 nuevamente para guardar el expediente

				this.grabarExpedienteElectronico(expedienteElectronicoDTO);
				guardarDatosEnHistorialOperacion(expedienteElectronicoDTO, user, detallesParalelos);
			}

		} catch (RuntimeException rte) {
			try {
				logger.error("Se deben eliminar los documentos de pase generados", rte);

			} catch (Exception e) {
				logger.error("No se pudo eliminar el documento generado", e);
			}

			throw rte;
		}

		if (logger.isDebugEnabled()) {
			logger.debug(
					"generarPaseParaleloExpedienteElectronico(Task, ExpedienteElectronicoDTO, String, String, String, String, List<DatosEnvioParalelo>) - end");
		}
	}

	public void guardarDatosEnHistorialOperacionP(ExpedienteElectronicoDTO expedienteElectronicoDTO,
			String loggedUsername, Map<String, String> detalles) {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"guardarDatosEnHistorialOperacionP(expedienteElectronico={}, loggedUsername={}, detalles={}) - start",
					expedienteElectronicoDTO, loggedUsername, detalles);
		}

		Usuario user = usuariosSADEService.getDatosUsuario(loggedUsername);
		this.guardarDatosEnHistorialOperacion(expedienteElectronicoDTO, user, detalles);

		if (logger.isDebugEnabled()) {
			logger.debug(
					"guardarDatosEnHistorialOperacionP(ExpedienteElectronicoDTO, String, Map<String,String>) - end");
		}
	}

	private void guardarDatosEnHistorialOperacion(ExpedienteElectronicoDTO expedienteElectronicoDTO, Usuario usuario,
			Map<String, String> detalles) {
		if (logger.isDebugEnabled()) {
			logger.debug("guardarDatosEnHistorialOperacion(expedienteElectronico={}, usuario={}, detalles={}) - start",
					expedienteElectronicoDTO, usuario, detalles);
		}

		historialOperacionService.guardarDatosEnHistorialOperacion(expedienteElectronicoDTO, usuario, detalles);

		if (logger.isDebugEnabled()) {
			logger.debug(
					"guardarDatosEnHistorialOperacion(ExpedienteElectronicoDTO, Usuario, Map<String,String>) - end");
		}
	}

	private void guardarTareaParalelo(ExpedienteElectronicoDTO expedienteElectronicoDTO, String loggedUsername,
			String motivo, String estadoAnterior, String destino) {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"guardarTareaParalelo(expedienteElectronico={}, loggedUsername={}, motivo={}, estadoAnterior={}, destino={}) - start",
					expedienteElectronicoDTO, loggedUsername, motivo, estadoAnterior, destino);
		}

		TareaParaleloDTO tareaParalelo = new TareaParaleloDTO();
		tareaParalelo.setIdExp(expedienteElectronicoDTO.getId());
		tareaParalelo.setFechaPase(expedienteElectronicoDTO.getFechaModificacion());
		tareaParalelo.setUsuarioOrigen(loggedUsername);
		tareaParalelo.setEstado(ConstantesWeb.PENDIENTE);
		tareaParalelo.setMotivo(this.formatoMotivo(motivo));
		tareaParalelo.setEstadoAnterior(estadoAnterior);
		tareaParalelo.setUsuarioGrupoDestino(destino);
		tareaParalelo.setUsuarioGrupoAnterior(loggedUsername);
		this.tareaParaleloService.guardar(tareaParalelo);

		/**
		 * El executionId se almacena al momento de crear la task y realizar la
		 * asignacion en AsignarParalelo.java
		 */

		if (logger.isDebugEnabled()) {
			logger.debug("guardarTareaParalelo(ExpedienteElectronicoDTO, String, String, String, String) - end");
		}
	}

	public void indexarExpedienteElectronico(final ExpedienteElectronicoDTO expedienteElectronico) {
		if (logger.isDebugEnabled()) {
			logger.debug("indexarExpedienteElectronico(expedienteElectronico={}) - start", expedienteElectronico);
		}

		new Thread(new Runnable() {
			@Override
			public void run() {
				if (logger.isDebugEnabled()) {
					logger.debug("$Runnable.run() - start");
				}

				try {
					solrService.indexar(expedienteElectronico);
				} catch (NegocioException e) {
					logger.error("Error al indexar el registro", e);
				}

				if (logger.isDebugEnabled()) {
					logger.debug("$Runnable.run() - end");
				}
			}
		}).start();

		if (logger.isDebugEnabled()) {
			logger.debug("indexarExpedienteElectronico(ExpedienteElectronicoDTO) - end");
		}
	}

	@Transactional
	public ExpedienteElectronicoDTO modificarExpedienteElectronico2(ExpedienteElectronicoDTO expedienteElectronicoDTO) {
		if (logger.isDebugEnabled()) {
			logger.debug("modificarExpedienteElectronico(expedienteElectronico={}) - start", expedienteElectronicoDTO);
		}

		ExpedienteElectronico entity = mapper.map(expedienteElectronicoDTO, ExpedienteElectronico.class);

		entity.setPropertyResultado(null);
		entity = expedienteElectronicoRepository.save(entity);
		solicitudExpedienteRepository.save(entity.getSolicitudIniciadora());

		indexarExpedienteElectronico(expedienteElectronicoDTO);

		if (logger.isDebugEnabled()) {
			logger.debug("modificarExpedienteElectronico(ExpedienteElectronicoDTO) - end");
		}
		return mapper.map(entity, ExpedienteElectronicoDTO.class);
	}
	
	@Transactional
	public void modificarExpedienteElectronico(ExpedienteElectronicoDTO expedienteElectronicoDTO) {
		if (logger.isDebugEnabled()) {
			logger.debug("modificarExpedienteElectronico(expedienteElectronico={}) - start", expedienteElectronicoDTO);
		}

		ExpedienteElectronico entity = mapper.map(expedienteElectronicoDTO, ExpedienteElectronico.class);

		entity.setPropertyResultado(null);
		expedienteElectronicoRepository.save(entity);
		solicitudExpedienteRepository.save(entity.getSolicitudIniciadora());

		indexarExpedienteElectronico(expedienteElectronicoDTO);

		if (logger.isDebugEnabled()) {
			logger.debug("modificarExpedienteElectronico(ExpedienteElectronicoDTO) - end");
		}
	}

	@Override
	public void mergeExpedienteElectronico(ExpedienteElectronicoDTO expedienteElectronicoDTO) {
		if (logger.isDebugEnabled()) {
			logger.debug("mergeExpedienteElectronico(expedienteElectronico={}) - start", expedienteElectronicoDTO);
		}

		ExpedienteElectronico entity = mapper.map(expedienteElectronicoDTO, ExpedienteElectronico.class);

		entity.setPropertyResultado(null);
		expedienteElectronicoRepository.save(entity);
		this.indexarExpedienteElectronico(expedienteElectronicoDTO);

		if (logger.isDebugEnabled()) {
			logger.debug("mergeExpedienteElectronico(ExpedienteElectronicoDTO) - end");
		}
	}

	public void guardarArchivoDeTrabajoEnExpedienteElectronico(ExpedienteElectronicoDTO expedienteElectronicoDTO) {
		if (logger.isDebugEnabled()) {
			logger.debug("guardarArchivoDeTrabajoEnExpedienteElectronico(expedienteElectronico={}) - start",
					expedienteElectronicoDTO);
		}

		ExpedienteElectronico entity = mapper.map(expedienteElectronicoDTO, ExpedienteElectronico.class);
		logger.debug("guardarArchivoDeTrabajoEnExpedienteElectronico ", expedienteElectronicoDTO.toString());

		entity.setPropertyResultado(null);
		expedienteElectronicoRepository.save(entity);

		if (logger.isDebugEnabled()) {
			logger.debug("guardarArchivoDeTrabajoEnExpedienteElectronico(ExpedienteElectronicoDTO) - end");
		}
	}

	public void guardarDocumentoEnExpedienteElectronico(ExpedienteElectronicoDTO expedienteElectronicoDTO) {
		if (logger.isDebugEnabled()) {
			logger.debug("guardarDocumentoEnExpedienteElectronico(expedienteElectronico={}) - start",
					expedienteElectronicoDTO);
		}

		ExpedienteElectronico entity = mapper.map(expedienteElectronicoDTO, ExpedienteElectronico.class);
		logger.debug("guardarDocumentoEnExpedienteElectronico ", expedienteElectronicoDTO.toString());

		entity.setPropertyResultado(null);
		expedienteElectronicoRepository.save(entity);

		if (logger.isDebugEnabled()) {
			logger.debug("guardarDocumentoEnExpedienteElectronico(ExpedienteElectronicoDTO) - end");
		}
	}

	private void crearEspacioPorWebDav(String codigoExpedienteElectronico) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearEspacioPorWebDav(codigoExpedienteElectronico={}) - start", codigoExpedienteElectronico);
		}

		if (ConstantesWeb.GUARDA_DOCUMENTAL_WEBDAV.equalsIgnoreCase(properties.getString("guarda.documental"))) {
			Integer anio = BusinessFormatHelper.obtenerAnio(codigoExpedienteElectronico);
			String reparticionUsuario = BusinessFormatHelper.obtenerReparticionUsuario(codigoExpedienteElectronico);
			String primerosDosNumeroSade = BusinessFormatHelper
					.completarConCerosNumActuacion(BusinessFormatHelper.obtenerNumeroSade(codigoExpedienteElectronico))
					.substring(0, 2);
			String segundosTresNumeroSade = BusinessFormatHelper
					.completarConCerosNumActuacion(BusinessFormatHelper.obtenerNumeroSade(codigoExpedienteElectronico))
					.substring(2, 5);

			createSpace(null, "Documentos_De_Trabajo", "", "");
			createSpace("Documentos_De_Trabajo", anio.toString(), "", "");
			createSpace("Documentos_De_Trabajo/" + anio, reparticionUsuario, "", "");
			createSpace("Documentos_De_Trabajo/" + anio.toString() + "/" + reparticionUsuario + "/",
					primerosDosNumeroSade, "", "");
			createSpace("Documentos_De_Trabajo/" + anio.toString() + "/" + reparticionUsuario + "/"
					+ primerosDosNumeroSade + "/", segundosTresNumeroSade, "", "");
			createSpace("Documentos_De_Trabajo/" + anio.toString() + "/" + reparticionUsuario + "/"
					+ primerosDosNumeroSade + "/" + segundosTresNumeroSade + "/", codigoExpedienteElectronico.trim(),
					"", "");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("crearEspacioPorWebDav(String) - end");
		}
	}

	private void createSpace(String arg0, String arg1, String arg2, String arg3) {
		if (logger.isDebugEnabled()) {
			logger.debug("createSpace(arg0={}, arg1={}, arg2={}, arg3={}) - start", arg0, arg1, arg2, arg3);
		}

		try {
			webDavService.createSpace(arg0, arg1, arg2, arg3);
		} catch (Exception e) {
			logger.error("createSpace Exception " + e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("createSpace(String, String, String, String) - end");
		}
	}

	public List<?> buscarId() {
		if (logger.isDebugEnabled()) {
			logger.debug("buscarId() - start");
		}

		List<?> returnList = this.expedienteElectronicoDAO.buscarId();

		if (logger.isDebugEnabled()) {
			logger.debug("buscarId() - end - return value={}", returnList);
		}

		return returnList;
	}

	public List<ExpedienteAsociadoEntDTO> buscarExpedientesAsociados(Integer idExpedienteElectronico) {
		if (logger.isDebugEnabled()) {
			logger.debug("buscarExpedientesAsociados(idExpedienteElectronico={}) - start", idExpedienteElectronico);
		}

		List<ExpedienteAsociadoEntDTO> returnList = this.expedienteElectronicoDAO
				.buscarExpedientesAsociados(idExpedienteElectronico);

		if (logger.isDebugEnabled()) {
			logger.debug("buscarExpedientesAsociados(Integer) - end - return value={}", returnList);
		}

		return returnList;
	}

	public ExpedienteElectronicoDTO generarExpedienteElectronico(Task workingTask, SolicitudExpedienteDTO solicitud,
			TrataDTO selectedTrata, String descripcion, List<ExpedienteMetadataDTO> metaDatosCargados, String username,
			String motivoExpediente, String usuarioSolicitante) {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"generarExpedienteElectronico(workingTask={}, solicitud={}, selectedTrata={}, descripcion={}, metaDatosCargados={}, username={}, motivoExpediente={}, usuarioSolicitante={}) - start",
					workingTask, solicitud, selectedTrata, descripcion, metaDatosCargados, username, motivoExpediente,
					usuarioSolicitante);
		}

		ExpedienteElectronicoDTO expedienteElectronicoDTO = new ExpedienteElectronicoDTO();
		expedienteElectronicoDTO.setIdWorkflow(workingTask.getExecutionId());
		expedienteElectronicoDTO.setSolicitudIniciadora(solicitud);

		TrataDTO trata = selectedTrata;

		expedienteElectronicoDTO.setTrata(trata);

		if (trata.getTipoReserva().getTipoReserva().equals(RESERVA_PARCIAL)
				|| trata.getTipoReserva().getTipoReserva().equals(RESERVA_TOTAL)) {
			expedienteElectronicoDTO.setEsReservado(true);
		} else {
			expedienteElectronicoDTO.setEsReservado(false);
		}

		expedienteElectronicoDTO.setDescripcion(descripcion);
		expedienteElectronicoDTO.setFechaCreacion(new Date());

		if (!CollectionUtils.isEmpty(metaDatosCargados)) {
			Iterator<?> itmap = metaDatosCargados.iterator();

			while (itmap.hasNext()) {
				ExpedienteMetadataDTO e = (ExpedienteMetadataDTO) itmap.next();
				expedienteElectronicoDTO.agregarMetadatosDeTrata(e);
			}
		}

		expedienteElectronicoDTO.setUsuarioCreador(username);
		expedienteElectronicoDTO = caratulador.caratular(expedienteElectronicoDTO, solicitud, username);

		String codigoExpedienteElectronico = expedienteElectronicoDTO.getCodigoCaratula();

		// Quitamos los 0000 del numero de expediente
		expedienteElectronicoDTO.setEsElectronico(true);
		expedienteElectronicoDTO.setEstado(INICIACION);

		// ACA SE DEBE GENERAR EL ROLLBACK EN CASO DE FALLA AL GRABAR.
		// this.grabarExpedienteElectronico(expedienteElectronico);
		this.crearEspacioPorWebDav(codigoExpedienteElectronico);

		Map<String, String> detalles = new HashMap<>();
		detalles.put(ConstantesWeb.ESTADO, INICIAR_EXPEDIENTE);
		detalles.put(ConstantesWeb.MOTIVO, this.formatoMotivo(motivoExpediente));
		detalles.put(ConstantesWeb.DESTINATARIO, usuarioSolicitante);

		// Guarda en el historial
		Usuario user = usuariosSADEService.getDatosUsuario(username);
		/**
		 * SM: se mueve a lo ultimo el guardado del historial, en caso de fallo
		 * no queda el historialGrabado
		 * 
		 */
		// guardarDatosEnHistorialOperacion(expedienteElectronico, user,
		// detalles);

		Map<String, Object> mapVariables = new HashMap<>();
		/**
		 * Guardo el usuario Actual para que quede como usuario Anterior al
		 * terminar de enviar la tarea
		 */
		mapVariables.put(ConstantesWeb.USUARIO_ANTERIOR, username);
		mapVariables.put(ConstantesWeb.USUARIO_CANDIDATO, username);
		mapVariables.put(ConstantesWeb.CODIGO_EXPEDIENTE, codigoExpedienteElectronico);
		mapVariables.put(ConstantesWeb.CODIGO_TRATA, selectedTrata.getCodigoTrata());
		mapVariables.put(ConstantesWeb.DESCRIPCION, descripcion);
		mapVariables.put(ConstantesWeb.TAREA_GRUPAL, "noEsTareaGrupal");
		mapVariables.put(ConstantesWeb.MOTIVO, this.formatoMotivo(motivoExpediente));
		mapVariables.put(ConstantesWeb.ESTADO, INICIACION);
		mapVariables.put(ConstantesWeb.USUARIO_SOLICITANTE, usuarioSolicitante);
		mapVariables.put(ConstantesWeb.USUARIO_SELECCIONADO, usuarioSolicitante);
		mapVariables.put(ConstantesWeb.GRUPO_SELECCIONADO, null);
		mapVariables.put(ConstantesWeb.ID_EXPEDIENTE_ELECTRONICO, expedienteElectronicoDTO.getId());

		// El signal se tiene que hacer en el último paso para evitar que se
		// avance en el WF por si hubo algun problema en alguno de los servicios
		// anteriores
		if (workingTask.getExecutionId().toLowerCase().contains("solicitud")) {
			signalExecution(workingTask, INICIACION, mapVariables);
		} else {
			detalles.put(ConstantesWeb.ESTADO, workingTask.getActivityName());
			expedienteElectronicoDTO.setEstado(workingTask.getActivityName());
			setVariablesWorkFlow(workingTask.getExecutionId(), mapVariables);

			String asignacionUsuario = (String) mapVariables.get(ConstantesWeb.USUARIO_SELECCIONADO);
			String asignacionGrupo = (String) mapVariables.get(ConstantesWeb.GRUPO_SELECCIONADO);
			Task task = workingTask;

			if (StringUtils.isNotEmpty(asignacionUsuario)) {
				processEngine.getTaskService().assignTask(task.getId(), asignacionUsuario);
			} else if (StringUtils.isNotEmpty(asignacionGrupo)) {
				processEngine.getTaskService().addTaskParticipatingGroup(task.getId(), asignacionGrupo,
						Participation.CANDIDATE);
			}
		}

		this.grabarExpedienteElectronico(expedienteElectronicoDTO);
		guardarDatosEnHistorialOperacion(expedienteElectronicoDTO, user, detalles);

		if (logger.isDebugEnabled()) {
			logger.debug(
					"generarExpedienteElectronico(Task, SolicitudExpediente, Trata, String, List<ExpedienteMetadata>, String, String, String) - end - return value={}",
					expedienteElectronicoDTO);
		}

		return expedienteElectronicoDTO;
	}

	/*
	 * Se depreca debido a que generaba bloqueos en JBPM, se genera el metodo
	 * Map<String, Object> generarExpedienteElectronicoCaratulacionDirecta que
	 * lo reemplaza
	 */
	@Deprecated
	public ExpedienteElectronicoDTO generarExpedienteElectronicoCaratulacionDirecta(SolicitudExpedienteDTO solicitud,
			TrataDTO selectedTrata, String descripcion, List<ExpedienteMetadataDTO> metaDatosCargados, String username,
			String motivoExpediente, String estadoExpediente, String estadoVariable) {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"generarExpedienteElectronicoCaratulacionDirecta(solicitud={}, selectedTrata={}, descripcion={}, metaDatosCargados={}, username={}, motivoExpediente={}, estadoExpediente={}, estadoVariable={}) - start",
					solicitud, selectedTrata, descripcion, metaDatosCargados, username, motivoExpediente,
					estadoExpediente, estadoVariable);
		}

		ExpedienteElectronicoDTO expedienteElectronicoDTO = new ExpedienteElectronicoDTO();
		expedienteElectronicoDTO.setSolicitudIniciadora(solicitud);

		TrataDTO trata = selectedTrata;

		expedienteElectronicoDTO.setTrata(trata);

		if (trata.getTipoReserva().getTipoReserva().equals(RESERVA_PARCIAL)
				|| trata.getTipoReserva().getTipoReserva().equals(RESERVA_TOTAL)) {
			expedienteElectronicoDTO.setEsReservado(true);
		} else {
			expedienteElectronicoDTO.setEsReservado(false);
		}

		expedienteElectronicoDTO.setDescripcion(descripcion);
		expedienteElectronicoDTO.setFechaCreacion(new Date());

		if (metaDatosCargados != null && metaDatosCargados.size() != 0) {
			Iterator<?> itmap = metaDatosCargados.iterator();

			while (itmap.hasNext()) {
				ExpedienteMetadataDTO e = (ExpedienteMetadataDTO) itmap.next();
				expedienteElectronicoDTO.agregarMetadatosDeTrata(e);

			}
		}

		expedienteElectronicoDTO.setUsuarioCreador(username);
		expedienteElectronicoDTO = caratulador.caratular(expedienteElectronicoDTO, solicitud, username);
		String codigoExpedienteElectronico = expedienteElectronicoDTO.getCodigoCaratula();

		// Quitamos los 0000 del numero de expediente
		expedienteElectronicoDTO.setEsElectronico(true);
		expedienteElectronicoDTO.setEstado(estadoExpediente);

		this.grabarExpedienteElectronico(expedienteElectronicoDTO);
		this.crearEspacioPorWebDav(codigoExpedienteElectronico);

		Map<String, String> detalles = new HashMap<>();
		detalles.put(ConstantesWeb.ESTADO, estadoVariable);
		detalles.put(ConstantesWeb.MOTIVO, this.formatoMotivo(motivoExpediente));
		detalles.put(ConstantesWeb.DESTINATARIO, username);

		// Se graban los datos del PASE ELECTRONICO en el Historial
		Usuario user = usuariosSADEService.getDatosUsuario(username);
		guardarDatosEnHistorialOperacion(expedienteElectronicoDTO, user, detalles);

		if (logger.isDebugEnabled()) {
			logger.debug(
					"generarExpedienteElectronicoCaratulacionDirecta(SolicitudExpediente, Trata, String, List<ExpedienteMetadata>, String, String, String, String) - end - return value={}",
					expedienteElectronicoDTO);
		}

		return expedienteElectronicoDTO;
	}

	public Map<String, Object> generarExpedienteElectronicoCaratulacionDirecta(SolicitudExpedienteDTO solicitud,
			TrataDTO selectedTrata, String descripcion, List<ExpedienteMetadataDTO> metaDatosCargados, String username,
			String motivoExpediente, String estadoExpediente, String estadoVariable, boolean origen) {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"generarExpedienteElectronicoCaratulacionDirecta(solicitud={}, selectedTrata={}, descripcion={}, metaDatosCargados={}, username={}, motivoExpediente={}, estadoExpediente={}, estadoVariable={}, origen={}) - start",
					solicitud, selectedTrata, descripcion, metaDatosCargados, username, motivoExpediente,
					estadoExpediente, estadoVariable, origen);
		}

		ExpedienteElectronicoDTO expedienteElectronicoDTO = new ExpedienteElectronicoDTO();
		expedienteElectronicoDTO.setSolicitudIniciadora(solicitud);
		if (solicitud.getIdOperacion() != null)
			expedienteElectronicoDTO.setIdOperacion(solicitud.getIdOperacion().intValue());
		TrataDTO trata = selectedTrata;

		expedienteElectronicoDTO.setTrata(trata);

		if (trata.getTipoReserva().getTipoReserva().equals(RESERVA_PARCIAL)
				|| trata.getTipoReserva().getTipoReserva().equals(RESERVA_TOTAL)) {
			expedienteElectronicoDTO.setEsReservado(true);
		} else {
			expedienteElectronicoDTO.setEsReservado(false);
		}

		expedienteElectronicoDTO.setDescripcion(descripcion);
		expedienteElectronicoDTO.setFechaCreacion(new Date());

		if (metaDatosCargados != null && metaDatosCargados.size() != 0) {
			Iterator<?> itmap = metaDatosCargados.iterator();

			while (itmap.hasNext()) {
				ExpedienteMetadataDTO e = (ExpedienteMetadataDTO) itmap.next();
				expedienteElectronicoDTO.agregarMetadatosDeTrata(e);

			}
		}

		expedienteElectronicoDTO.setUsuarioCreador(username);
		expedienteElectronicoDTO = caratulador.caratular(expedienteElectronicoDTO, solicitud, username);
		String codigoExpedienteElectronico = expedienteElectronicoDTO.getCodigoCaratula();

		// Quitamos los 0000 del numero de expediente
		expedienteElectronicoDTO.setEsElectronico(true);
		expedienteElectronicoDTO.setEstado(estadoExpediente);

		// this.grabarExpedienteElectronico(expedienteElectronico);
		this.crearEspacioPorWebDav(codigoExpedienteElectronico);

		Map<String, Object> respuesta = new HashMap<>();
		respuesta.put(ConstantesWeb.ESTADO, estadoVariable);
		respuesta.put(ConstantesWeb.MOTIVO, this.formatoMotivo(motivoExpediente));
		respuesta.put(ConstantesWeb.DESTINATARIO, username);

		/**
		 * SM: se mueve a lo ultimo el guardado del historial, en caso de fallo
		 * no queda el historialGrabado, en esta parte no se guarda... pues
		 * todavia no se sabe si se va generar el EE o no
		 * 
		 */
		// Se graban los datos del PASE ELECTRONICO en el Historial
		// guardarDatosEnHistorialOperacion(expedienteElectronico, username,
		// detalles);
		respuesta.put("expediente", expedienteElectronicoDTO);

		if (logger.isDebugEnabled()) {
			logger.debug(
					"generarExpedienteElectronicoCaratulacionDirecta(SolicitudExpediente, Trata, String, List<ExpedienteMetadata>, String, String, String, String, boolean) - end - return value={}",
					respuesta);
		}

		return respuesta;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.egoveris.te.core.api.ee.services.ExpedienteElectronicoService#
	 * generarExpedienteElectronicoCaratulacionDirecta(com.egoveris.te.core.api.
	 * entidades.tramitacion.expediente.SolicitudExpediente,
	 * com.egoveris.te.core.api.entidades.tramitacion.expediente.Trata,
	 * java.lang.String, java.util.List, java.lang.String, java.lang.String)
	 *//* se depreca debido a que genera loqueo en JBPM */
	@Override
	@Deprecated
	public ExpedienteElectronicoDTO generarExpedienteElectronicoCaratulacionDirecta(SolicitudExpedienteDTO solicitud,
			TrataDTO selectedTrata, String descripcion, List<ExpedienteMetadataDTO> metaDatosCargados, String username,
			String motivoExpediente) {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"generarExpedienteElectronicoCaratulacionDirecta(solicitud={}, selectedTrata={}, descripcion={}, metaDatosCargados={}, username={}, motivoExpediente={}) - start",
					solicitud, selectedTrata, descripcion, metaDatosCargados, username, motivoExpediente);
		}

		Map<String, Object> respuesta = generarExpedienteElectronicoCaratulacionDirecta(solicitud, selectedTrata,
				descripcion, metaDatosCargados, username, motivoExpediente, INICIACION, INICIAR_EXPEDIENTE, true);

		ExpedienteElectronicoDTO expedienteElectronicoDTO = (ExpedienteElectronicoDTO) respuesta.get("expediente");

		if (logger.isDebugEnabled()) {
			logger.debug(
					"generarExpedienteElectronicoCaratulacionDirecta(SolicitudExpediente, Trata, String, List<ExpedienteMetadata>, String, String) - end - return value={}",
					expedienteElectronicoDTO);
		}

		return expedienteElectronicoDTO;
	}

	public Map<String, Object> generarExpedienteElectronicoCaratulacionDirecta(SolicitudExpedienteDTO solicitud,
			TrataDTO selectedTrata, String descripcion, List<ExpedienteMetadataDTO> metaDatosCargados, String username,
			String motivoExpediente, boolean nuevo) {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"generarExpedienteElectronicoCaratulacionDirecta(solicitud={}, selectedTrata={}, descripcion={}, metaDatosCargados={}, username={}, motivoExpediente={}, nuevo={}) - start",
					solicitud, selectedTrata, descripcion, metaDatosCargados, username, motivoExpediente, nuevo);
		}

		Map<String, Object> respuesta = generarExpedienteElectronicoCaratulacionDirecta(solicitud, selectedTrata,
				descripcion, metaDatosCargados, username, motivoExpediente, INICIACION, INICIAR_EXPEDIENTE, true);

		if (logger.isDebugEnabled()) {
			logger.debug(
					"generarExpedienteElectronicoCaratulacionDirecta(SolicitudExpediente, Trata, String, List<ExpedienteMetadata>, String, String, boolean) - end - return value={}",
					respuesta);
		}

		return respuesta;
	}

	private Object getVariableWorkFlow(Task workingTask, String name) {
		if (logger.isDebugEnabled()) {
			logger.debug("getVariableWorkFlow(workingTask={}, name={}) - start", workingTask, name);
		}

		Object obj = processEngine.getExecutionService().getVariable(workingTask.getExecutionId(), name);

		if (logger.isDebugEnabled()) {
			logger.debug("getVariableWorkFlow(Task, String) - end - return value={}", obj);
		}

		return obj;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	private void setVariableWorkFlow(Task workingTask, String name, Object value) {
		if (logger.isDebugEnabled()) {
			logger.debug("setVariableWorkFlow(workingTask={}, name={}, value={}) - start", workingTask, name, value);
		}

		processEngine.getExecutionService().setVariable(workingTask.getExecutionId(), name, value);

		if (logger.isDebugEnabled()) {
			logger.debug("setVariableWorkFlow(Task, String, Object) - end");
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	private void setVariablesWorkFlow(String excutionId, Map<String, Object> mapVariables) {
		if (logger.isDebugEnabled()) {
			logger.debug("setVariablesWorkFlow(excutionId={}, mapVariables={}) - start", excutionId, mapVariables);
		}

		processEngine.getExecutionService().setVariables(excutionId, mapVariables);

		if (logger.isDebugEnabled()) {
			logger.debug("setVariablesWorkFlow(String, Map<String,Object>) - end");
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	private void signalExecution(Task workingTask, String nameTransition, Map<String, Object> mapVar) {
		if (logger.isDebugEnabled()) {
			logger.debug("signalExecution(workingTask={}, nameTransition={}, mapVar={}) - start", workingTask,
					nameTransition, mapVar);
		}

		processEngine.getExecutionService().signalExecutionById(workingTask.getExecutionId(), nameTransition, mapVar);

		if (logger.isDebugEnabled()) {
			logger.debug("signalExecution(Task, String, Map<String,Object>) - end");
		}
	}

	// METODS PRIVADOS DEL PASE
	private void hacerExpedienteAsociadoDefinitivo(List<ExpedienteAsociadoEntDTO> expedienteAsociadoList) {
		if (logger.isDebugEnabled()) {
			logger.debug("hacerExpedienteAsociadoDefinitivo(expedienteAsociadoList={}) - start",
					expedienteAsociadoList);
		}

		if (expedienteAsociadoList != null) {
			for (ExpedienteAsociadoEntDTO ea : expedienteAsociadoList) {
				ea.setDefinitivo(true);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("hacerExpedienteAsociadoDefinitivo(List<ExpedienteAsociado>) - end");
		}
	}

	private void hacerDocumentosDefinitivo(List<DocumentoDTO> documentoList) {
		if (logger.isDebugEnabled()) {
			logger.debug("hacerDocumentosDefinitivo(documentoList={}) - start", documentoList);
		}

		if (documentoList != null) {
			for (DocumentoDTO documento : documentoList) {
				if (documento != null) {
					documento.setDefinitivo(true);
				}
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("hacerDocumentosDefinitivo(List<DocumentoDTO>) - end");
		}
	}

	public void actualizarReservaEnLaTramitacion(ExpedienteElectronicoDTO expedienteElectronicoDTO,
			String loggedUsername) {
		if (logger.isDebugEnabled()) {
			logger.debug("actualizarReservaEnLaTramitacion(expedienteElectronico={}, loggedUsername={}) - start",
					expedienteElectronicoDTO, loggedUsername);
		}

		String codigoTrata = expedienteElectronicoDTO.getTrata().getCodigoTrata();
		Usuario datosUsuario = usuariosSADEService.getDatosUsuario(loggedUsername);
		List<ReparticionParticipanteDTO> listRepRect = obtenerReparticionesRectoras(expedienteElectronicoDTO,
				loggedUsername, codigoTrata);
		actualizarArchivosDeTrabajoEnLaReservaPorTramitacion(expedienteElectronicoDTO, loggedUsername, listRepRect,
				datosUsuario);
		actualizarDocumentosGedoEnLaReservaPorTramitacion(expedienteElectronicoDTO, loggedUsername, codigoTrata,
				listRepRect, datosUsuario);

		if (logger.isDebugEnabled()) {
			logger.debug("actualizarReservaEnLaTramitacion(ExpedienteElectronicoDTO, String) - end");
		}
	}

	@Override
	public void actualizarArchivosDeTrabajoEnLaReservaPorTramitacion(ExpedienteElectronicoDTO expedienteElectronicoDTO,
			String loggedUsername, List<ReparticionParticipanteDTO> listRepRect, Usuario datosUsuario) {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"actualizarArchivosDeTrabajoEnLaReservaPorTramitacion(expedienteElectronico={}, loggedUsername={}, listRepRect={}, datosUsuario={}) - start",
					expedienteElectronicoDTO, loggedUsername, listRepRect, datosUsuario);
		}

		actualizaArchivosDeTrabajo(expedienteElectronicoDTO, loggedUsername, datosUsuario, loggedUsername, listRepRect);
		asignarPermisosVisualizacionArchivo(expedienteElectronicoDTO, datosUsuario, listRepRect);

		if (logger.isDebugEnabled()) {
			logger.debug(
					"actualizarArchivosDeTrabajoEnLaReservaPorTramitacion(ExpedienteElectronicoDTO, String, List<ReparticionParticipante>, Usuario) - end");
		}
	}

	private void actualizarDocumentosGedoEnLaReservaPorTramitacion(ExpedienteElectronicoDTO expedienteElectronicoDTO,
			String loggedUsername, String codigoTrata, List<ReparticionParticipanteDTO> listRepRect,
			Usuario datosUsuario) {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"actualizarDocumentosGedoEnLaReservaPorTramitacion(expedienteElectronico={}, loggedUsername={}, codigoTrata={}, listRepRect={}, datosUsuario={}) - start",
					expedienteElectronicoDTO, loggedUsername, codigoTrata, listRepRect, datosUsuario);
		}

		actualizarDocumentosEnGedo(expedienteElectronicoDTO, loggedUsername, datosUsuario, codigoTrata);
		documentoGedoService.asignarPermisosVisualizacionGEDO(expedienteElectronicoDTO, datosUsuario, listRepRect);

		if (logger.isDebugEnabled()) {
			logger.debug(
					"actualizarDocumentosGedoEnLaReservaPorTramitacion(ExpedienteElectronicoDTO, String, String, List<ReparticionParticipante>, Usuario) - end");
		}
	}

	public void asignarPermisosVisualizacionArchivo(ExpedienteElectronicoDTO expedienteElectronicoDTO, Usuario usuario,
			List<ReparticionParticipanteDTO> reparticionesRectora) {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"asignarPermisosVisualizacionArchivo(expedienteElectronico={}, usuario={}, reparticionesRectora={}) - start",
					expedienteElectronicoDTO, usuario, reparticionesRectora);
		}

		List<ArchivoDeTrabajoDTO> archivoDeTrabajoList = expedienteElectronicoDTO.getArchivosDeTrabajo();

		if (archivoDeTrabajoList != null) {
			for (ArchivoDeTrabajoDTO archivoDeTrabajo : archivoDeTrabajoList) {
				if (archivoDeTrabajo.getId() == null) {
					archivoService.grabarArchivoDeTrabajo(archivoDeTrabajo);
				}

				List<ArchivoDeTrabajoVisualizacionDTO> permisosVisualizacion = permisoVisualizacion
						.buscarPermisosByIdArchivo(archivoDeTrabajo.getId());
				if (archivoDeTrabajo.isDefinitivo() && (expedienteElectronicoDTO.getEsReservado()
						&& expedienteElectronicoDTO.getFechaReserva() == null
						|| expedienteElectronicoDTO.getEsReservado() && expedienteElectronicoDTO.getFechaReserva()
								.before(archivoDeTrabajo.getFechaAsociacion()))) {

					archivoDeTrabajo.setTipoReserva(ConstantesWeb.ARCHIVO_TRABAJO_RESERVADO);

					for (ReparticionParticipanteDTO reparticionRectora : reparticionesRectora) {
						ArchivoDeTrabajoVisualizacionDTO permisoVisualizacion = crearPermisoDeVisualizacion(
								archivoDeTrabajo, usuario, reparticionRectora);
						if (!existePermisoVisualizacion(archivoDeTrabajo, permisoVisualizacion, permisosVisualizacion))
							permisosVisualizacion.add(permisoVisualizacion);
					}
				}

				if ((reparticionesRectora == null || reparticionesRectora.isEmpty()) && archivoDeTrabajo.isDefinitivo()
						&& ((expedienteElectronicoDTO.getEsReservado()
								&& expedienteElectronicoDTO.getFechaReserva() == null)
								|| expedienteElectronicoDTO.getEsReservado() && !expedienteElectronicoDTO
										.getFechaReserva().after(archivoDeTrabajo.getFechaAsociacion()))) {
					ArchivoDeTrabajoVisualizacionDTO permisoVisualizacion = crearPermisoDeVisualizacion(
							archivoDeTrabajo, usuario, null);
					if (!existePermisoVisualizacion(archivoDeTrabajo, permisoVisualizacion, permisosVisualizacion)) {
						permisosVisualizacion.add(permisoVisualizacion);
					}
				}

				if (permisosVisualizacion != null && permisosVisualizacion.size() > 0)
					permisoVisualizacion.guardarPermisos(permisosVisualizacion);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug(
					"asignarPermisosVisualizacionArchivo(ExpedienteElectronicoDTO, Usuario, List<ReparticionParticipante>) - end");
		}
	}

	private ArchivoDeTrabajoVisualizacionDTO crearPermisoDeVisualizacion(ArchivoDeTrabajoDTO archivo, Usuario usuario,
			ReparticionParticipanteDTO rep) {
		if (logger.isDebugEnabled()) {
			logger.debug("crearPermisoDeVisualizacion(archivo={}, usuario={}, rep={}) - start", archivo, usuario, rep);
		}

		ArchivoDeTrabajoVisualizacionDTO permisoVisualizacion = new ArchivoDeTrabajoVisualizacionDTO();
		permisoVisualizacion.setArchivoDeTrabajo(archivo);
		permisoVisualizacion.setCodigoReparticion(usuario.getCodigoReparticion());
		permisoVisualizacion.setCodigoSector(usuario.getCodigoSectorInterno());
		permisoVisualizacion.setFechaAlta(new Date());
		permisoVisualizacion.setCodigoUsuario(usuario.getUsername());
		permisoVisualizacion.setIdArchivoDeTrabajo(archivo.getId());

		if (rep != null) {
			permisoVisualizacion.setCodigoReparticionRectora(rep.getReparticion());
		}

		if (logger.isDebugEnabled()) {
			logger.debug(
					"crearPermisoDeVisualizacion(ArchivoDeTrabajoDTO, Usuario, ReparticionParticipante) - end - return value={}",
					permisoVisualizacion);
		}

		return permisoVisualizacion;
	}

	private boolean existePermisoVisualizacion(ArchivoDeTrabajoDTO archivoOrigen,
			ArchivoDeTrabajoVisualizacionDTO permisoVisualizacion,
			List<ArchivoDeTrabajoVisualizacionDTO> permisosVisualizacion) {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"existePermisoVisualizacion(archivoOrigen={}, permisoVisualizacion={}, permisosVisualizacion={}) - start",
					archivoOrigen, permisoVisualizacion, permisosVisualizacion);
		}

		for (ArchivoDeTrabajoVisualizacionDTO permiso : permisosVisualizacion) {
			if (permiso.getIdArchivoDeTrabajo().equals(permisoVisualizacion.getIdArchivoDeTrabajo())
					&& permiso.getCodigoReparticion().equals(permisoVisualizacion.getCodigoReparticion())
					&& permiso.getCodigoUsuario().equals(permisoVisualizacion.getCodigoUsuario())
					&& ((permiso.getCodigoReparticionRectora() == null
							&& permisoVisualizacion.getCodigoReparticionRectora() == null)
							|| (permiso.getCodigoReparticionRectora()
									.equals(permisoVisualizacion.getCodigoReparticionRectora())))
					&& permiso.getCodigoSector().equals(permisoVisualizacion.getCodigoSector())) {
				if (logger.isDebugEnabled()) {
					logger.debug(
							"existePermisoVisualizacion(ArchivoDeTrabajo, ArchivoDeTrabajoVisualizacion, List<ArchivoDeTrabajoVisualizacion>) - end - return value={}",
							true);
				}

				return true;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug(
					"existePermisoVisualizacion(ArchivoDeTrabajo, ArchivoDeTrabajoVisualizacion, List<ArchivoDeTrabajoVisualizacion>) - end - return value={}",
					false);
		}

		return false;
	}

	private void hacerArchivoDefinitivo(List<ArchivoDeTrabajoDTO> archivoDeTrabajoList) {
		if (logger.isDebugEnabled()) {
			logger.debug("hacerArchivoDefinitivo(archivoDeTrabajoList={}) - start", archivoDeTrabajoList);
		}

		if (archivoDeTrabajoList != null) {
			for (ArchivoDeTrabajoDTO archivoDeTrabajo : archivoDeTrabajoList) {
				archivoDeTrabajo.setDefinitivo(true);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("hacerArchivoDefinitivo(List<ArchivoDeTrabajoDTO>) - end");
		}
	}

	public ExpedienteElectronicoDTO buscarExpedienteElectronico(Long idExpedienteElectronico) {
		if (logger.isDebugEnabled()) {
			logger.debug("buscarExpedienteElectronico(idExpedienteElectronico={}) - start", idExpedienteElectronico);
		}

		ExpedienteElectronicoDTO expedienteElectronicoDTO = null;
		ExpedienteElectronico entity = expedienteElectronicoRepository.findOne(idExpedienteElectronico);

		if (entity != null) {
			expedienteElectronicoDTO = mapper.map(entity, ExpedienteElectronicoDTO.class);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("buscarExpedienteElectronico(Integer) - end - return value={}", expedienteElectronicoDTO);
		}

		return expedienteElectronicoDTO;
	}

	public ExpedienteElectronicoDTO buscarExpedienteElectronicoByIdTask(String idTask) { // Done
		if (logger.isDebugEnabled()) {
			logger.debug("buscarExpedienteElectronicoByIdTask(idTask={}) - start", idTask);
		}

		ExpedienteElectronicoDTO expedienteElectronicoDTO = null;
		ExpedienteElectronico eeEntity = expedienteElectronicoRepository.findByidWorkflow(idTask);

		if (eeEntity != null) {
			expedienteElectronicoDTO = mapper.map(eeEntity, ExpedienteElectronicoDTO.class);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("buscarExpedienteElectronicoByIdTask(String) - end - return value={}",
					expedienteElectronicoDTO);
		}

		return expedienteElectronicoDTO;
	}

	public List<String> buscarNumerosExpedientesPorAssignee(String usuario) {
		if (logger.isDebugEnabled()) {
			logger.debug("buscarNumerosExpedientesPorAssignee(usuario={}) - start", usuario);
		}

		List<String> returnList = expedienteElectronicoDAO.buscarNumerosExpedientesPorAssignee(usuario);

		if (logger.isDebugEnabled()) {
			logger.debug("buscarNumerosExpedientesPorAssignee(String) - end - return value={}", returnList);
		}

		return returnList;
	}

	public List<String> buscarNumerosExpedientesBloqueadosPorAssignee(String usuario) {
		if (logger.isDebugEnabled()) {
			logger.debug("buscarNumerosExpedientesBloqueadosPorAssignee(usuario={}) - start", usuario);
		}

		List<String> returnList = expedienteElectronicoDAO.buscarNumerosExpedientesBloqueadosPorAssignee(usuario);

		if (logger.isDebugEnabled()) {
			logger.debug("buscarNumerosExpedientesBloqueadosPorAssignee(String) - end - return value={}", returnList);
		}

		return returnList;
	}

	public ExpedienteElectronicoDTO obtenerExpedienteElectronico(String tipoExpediente, Integer anio, Integer numero,
			String reparticion) {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerExpedienteElectronico(tipoExpediente={}, anio={}, numero={}, reparticion={}) - start",
					tipoExpediente, anio, numero, reparticion);
		}

		ExpedienteElectronicoDTO expedienteElectronicoDTO = null;
		ExpedienteElectronico expedienteElectronico = this.expedienteElectronicoRepository
				.obtenerExpedienteElectronico(tipoExpediente, anio, numero, reparticion);

		if (expedienteElectronico != null) {
			expedienteElectronicoDTO = mapper.map(expedienteElectronico, ExpedienteElectronicoDTO.class);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("obtenerExpedienteElectronico(String, Integer, Integer, String) - end - return value={}",
					expedienteElectronicoDTO);
		}

		return expedienteElectronicoDTO;
	}

	public ExpedienteElectronicoDTO obtenerExpedienteElectronicoSolr(String tipoExpediente, Integer anio,
			Integer numero, String reparticion) {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"obtenerExpedienteElectronicoSolr(tipoExpediente={}, anio={}, numero={}, reparticion={}) - start",
					tipoExpediente, anio, numero, reparticion);
		}

		ExpedienteElectronicoDTO expedienteElectronicoDTO = null;

		try {
			ExpedienteElectronicoIndex expedienteElectronicoIndex = solrService
					.obtenerExpedientesElectronicosSolr(tipoExpediente, anio, numero, reparticion);
			if (expedienteElectronicoIndex != null && expedienteElectronicoIndex.getIdWorkflow() != null)
				expedienteElectronicoDTO = this.convertirSolrDocument(expedienteElectronicoIndex);

		} catch (NegocioException e) {
			logger.error("obtenerExpedienteElectronicoSolr NegocioException " + e);

			if (consultaBd) {
				ExpedienteElectronicoDTO retorno = null;
				ExpedienteElectronico result = expedienteElectronicoRepository
						.findByTipoDocumentoAndAnioAndNumeroAndCodigoReparticionUsuario(tipoExpediente, anio, numero,
								reparticion.trim());
				if (result != null) {
					retorno = mapper.map(result, ExpedienteElectronicoDTO.class);
				}
				return retorno;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("obtenerExpedienteElectronicoSolr(String, Integer, Integer, String) - end - return value={}",
					expedienteElectronicoDTO);
		}

		return expedienteElectronicoDTO;
	}

	public boolean buscarIdTrata(TrataDTO trata) {
		if (logger.isDebugEnabled()) {
			logger.debug("buscarIdTrata(trata={}) - start", trata);
		}

		boolean returnboolean = this.expedienteElectronicoDAO.buscarIdTrata(trata);

		if (logger.isDebugEnabled()) {
			logger.debug("buscarIdTrata(Trata) - end - return value={}", returnboolean);
		}

		return returnboolean;
	}

	public List<ExpedienteElectronicoDTO> buscarExpedienteElectronicoPorUsuario(String username, TrataDTO trata,
			List<ExpedienteMetadataDTO> expedienteMetaDataList, List<DatosDeBusqueda> metaDatos, Date desde, Date hasta,
			String tipoDocumento, String numeroDocumento, String cuitCuil, String estado) {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"buscarExpedienteElectronicoPorUsuario(username={}, trata={}, expedienteMetaDataList={}, metaDatos={}, desde={}, hasta={}, tipoDocumento={}, numeroDocumento={}, cuitCuil={}, estado={}) - start",
					username, trata, expedienteMetaDataList, metaDatos, desde, hasta, tipoDocumento, numeroDocumento,
					cuitCuil, estado);
		}

		List<ExpedienteElectronicoDTO> resultado = null;

		try {
			List<ExpedienteElectronicoIndex> expedientesElectronicosIndex = solrService
					.obtenerExpedientesElectronicosSolr(username, null, trata, expedienteMetaDataList, metaDatos, desde,
							hasta, tipoDocumento, numeroDocumento, cuitCuil, estado);
			resultado = convertirSolrDocument(expedientesElectronicosIndex);
		} catch (NegocioException e) {
			logger.error("buscarExpedienteElectronicoPorUsuario NegocioException " + e);

			if (consultaBd)
				resultado = buscarEEPorUsuario(username, trata, expedienteMetaDataList, desde, hasta, tipoDocumento,
						numeroDocumento, cuitCuil, estado);
		}

		if (logger.isDebugEnabled()) {
			logger.debug(
					"buscarExpedienteElectronicoPorUsuario(String, Trata, List<ExpedienteMetadata>, List<DatosDeBusqueda>, Date, Date, String, String, String, String) - end - return value={}",
					resultado);
		}

		return resultado;
	}

	private List<ExpedienteElectronicoDTO> buscarEEPorUsuario(String username, TrataDTO trata,
			List<ExpedienteMetadataDTO> expedienteMetaDataList, Date desde, Date hasta, String tipoDocumento,
			String numeroDocumento, String cuitCuil, String estado) {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"buscarEEPorUsuario(username={}, trata={}, expedienteMetaDataList={}, desde={}, hasta={}, tipoDocumento={}, numeroDocumento={}, cuitCuil={}, estado={}) - start",
					username, trata, expedienteMetaDataList, desde, hasta, tipoDocumento, numeroDocumento, cuitCuil,
					estado);
		}

		List<ExpedienteElectronicoDTO> resultado;

		if ((trata == null || trata.getCodigoTrata() == " ") && expedienteMetaDataList.isEmpty()) {
			resultado = expedienteElectronicoDAO.buscarExpedienteElectronicoPorUsuario(username, desde, hasta,
					tipoDocumento, numeroDocumento, cuitCuil, estado);
		} else {
			resultado = expedienteElectronicoDAO.buscarExpedienteElectronicoPorMetadatos(username, null, trata,
					expedienteMetaDataList, desde, hasta, tipoDocumento, numeroDocumento, cuitCuil, estado);
		}

		if (logger.isDebugEnabled()) {
			logger.debug(
					"buscarEEPorUsuario(String, Trata, List<ExpedienteMetadata>, Date, Date, String, String, String, String) - end - return value={}",
					resultado);
		}

		return resultado;
	}

	private List<ExpedienteElectronicoDTO> convertirSolrDocument(
			List<ExpedienteElectronicoIndex> expedientesElectronicosIndex) {
		if (logger.isDebugEnabled()) {
			logger.debug("convertirSolrDocument(expedientesElectronicosIndex={}) - start",
					expedientesElectronicosIndex);
		}

		List<ExpedienteElectronicoDTO> resultado = new ArrayList<>();

		for (ExpedienteElectronicoIndex expElec : expedientesElectronicosIndex) {
			if (expElec.getIdWorkflow() != null) {
				ExpedienteElectronicoDTO ee = convertirSolrDocument(expElec);
				resultado.add(ee);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("convertirSolrDocument(List<ExpedienteElectronicoIndex>) - end - return value={}", resultado);
		}

		return resultado;
	}

	private ExpedienteElectronicoDTO convertirSolrDocument(ExpedienteElectronicoIndex expElec) {
		if (logger.isDebugEnabled()) {
			logger.debug("convertirSolrDocument(expElec={}) - start", expElec);
		}

		ExpedienteElectronicoDTO ee = new ExpedienteElectronicoDTO();
		ee.setId(expElec.getId());
		ee.setIdWorkflow(expElec.getIdWorkflow().toString());
		ee.setEstado(expElec.getEstado());
		ee.setFechaCreacion(expElec.getFechaCreacion());
		ee.setUsuarioCreador(expElec.getUsuarioCreador());
		ee.setSistemaCreador(expElec.getSistemaOrigen());
		ee.setTipoDocumento(expElec.getTipoDocumento());
		ee.setAnio(expElec.getAnio());
		ee.setNumero(expElec.getNumero());
		ee.setCodigoReparticionActuacion(expElec.getCodigoReparticionActuacion());
		ee.setCodigoReparticionUsuario(expElec.getCodigoReparticionUsuario());

		TrataDTO t = new TrataDTO();
		t.setCodigoTrata(expElec.getCodigoTrata());
		ee.setTrata(t);

		SolicitudExpedienteDTO solicitudExpediente = new SolicitudExpedienteDTO();
		ee.setSolicitudIniciadora(solicitudExpediente);
		ee.getSolicitudIniciadora().setMotivo(expElec.getMotivo());
		solicitudExpediente.setMotivoExterno(expElec.getMotivoExternoSolicitud());

		if (logger.isDebugEnabled()) {
			logger.debug("convertirSolrDocument(ExpedienteElectronicoIndex) - end - return value={}", ee);
		}

		return ee;
	}

	@Override
	public List<ExpedienteElectronicoDTO> buscarExpedienteElectronicoPorUsuarioTramitacion(String username,
			TrataDTO trata, List<ExpedienteMetadataDTO> expedienteMetaDataList, List<DatosDeBusqueda> metaDatos,
			Date desde, Date hasta, String tipoDocumento, String numeroDocumento, String cuitCuil, String estado) {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"buscarExpedienteElectronicoPorUsuarioTramitacion(username={}, trata={}, expedienteMetaDataList={}, metaDatos={}, desde={}, hasta={}, tipoDocumento={}, numeroDocumento={}, cuitCuil={}, estado={}) - start",
					username, trata, expedienteMetaDataList, metaDatos, desde, hasta, tipoDocumento, numeroDocumento,
					cuitCuil, estado);
		}

		List<ExpedienteElectronicoDTO> resultado = null;

		try {
			List<ExpedienteElectronicoIndex> expedientesElectronicosIndex = solrService
					.obtenerExpedientesElectronicosUsuarioTramitacionSolr(username, trata, expedienteMetaDataList,
							metaDatos, desde, hasta, tipoDocumento, numeroDocumento, cuitCuil, estado);
			resultado = convertirSolrDocument(expedientesElectronicosIndex);
		} catch (NegocioException e) {
			logger.error("buscarExpedienteElectronicoPorUsuarioTramitacion NegocioException " + e);

			if (consultaBd)
				resultado = buscarEEPorUsuarioTramitacion(username, trata, expedienteMetaDataList, desde, hasta,
						tipoDocumento, numeroDocumento, cuitCuil, estado);
		}

		if (logger.isDebugEnabled()) {
			logger.debug(
					"buscarExpedienteElectronicoPorUsuarioTramitacion(String, Trata, List<ExpedienteMetadata>, List<DatosDeBusqueda>, Date, Date, String, String, String, String) - end - return value={}",
					resultado);
		}

		return resultado;
	}

	private List<ExpedienteElectronicoDTO> buscarEEPorUsuarioTramitacion(String username, TrataDTO trata,
			List<ExpedienteMetadataDTO> expedienteMetaDataList, Date desde, Date hasta, String tipoDocumento,
			String numeroDocumento, String cuitCuil, String estado) {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"buscarEEPorUsuarioTramitacion(username={}, trata={}, expedienteMetaDataList={}, desde={}, hasta={}, tipoDocumento={}, numeroDocumento={}, cuitCuil={}, estado={}) - start",
					username, trata, expedienteMetaDataList, desde, hasta, tipoDocumento, numeroDocumento, cuitCuil,
					estado);
		}

		List<ExpedienteElectronicoDTO> resultado;

		if ((trata == null || trata.getCodigoTrata() == " ") && expedienteMetaDataList.isEmpty()) {
			logger.debug("buscarExpedienteElectronicoPorUsuarioTramitacion ",
					username + " " + desde + " " + hasta + " " + tipoDocumento + " " + numeroDocumento);
			resultado = expedienteElectronicoDAO.buscarExpedienteElectronicoPorUsuarioTramitacion(username, desde,
					hasta, tipoDocumento, numeroDocumento, cuitCuil, estado);
		} else {
			logger.debug("buscarExpedienteElectronicoPorMetadatosTramitacion ", username + " " + trata + " "
					+ expedienteMetaDataList + " " + desde + " " + hasta + " " + tipoDocumento + " " + numeroDocumento);
			resultado = expedienteElectronicoDAO.buscarExpedienteElectronicoPorMetadatosTramitacion(username, trata,
					expedienteMetaDataList, desde, hasta, tipoDocumento, numeroDocumento, cuitCuil, estado);
		}

		if (logger.isDebugEnabled()) {
			logger.debug(
					"buscarEEPorUsuarioTramitacion(String, Trata, List<ExpedienteMetadata>, Date, Date, String, String, String, String) - end - return value={}",
					resultado);
		}

		return resultado;
	}

	@Override
	public List<ExpedienteElectronicoDTO> buscarExpedienteElectronicoPorReparticion(String username, TrataDTO trata,
			List<ExpedienteMetadataDTO> expedienteMetaDataList, List<DatosDeBusqueda> metadatos, Date desde, Date hasta,
			String tipoDocumento, String numeroDocumento, String cuitCuil, String estado) {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"buscarExpedienteElectronicoPorReparticion(username={}, trata={}, expedienteMetaDataList={}, metadatos={}, desde={}, hasta={}, tipoDocumento={}, numeroDocumento={}, cuitCuil={}, estado={}) - start",
					username, trata, expedienteMetaDataList, metadatos, desde, hasta, tipoDocumento, numeroDocumento,
					cuitCuil, estado);
		}

		Usuario datosUsuario = this.usuariosSADEService.getDatosUsuario(username);

		List<ExpedienteElectronicoDTO> resultado = null;

		try {
			username = null;
			List<ExpedienteElectronicoIndex> expedientesElectronicosIndex = solrService
					.obtenerExpedientesElectronicosSolr(username, datosUsuario.getCodigoReparticion(), trata,
							expedienteMetaDataList, metadatos, desde, hasta, tipoDocumento, numeroDocumento, cuitCuil,
							estado);

			resultado = convertirSolrDocument(expedientesElectronicosIndex);

		} catch (NegocioException e) {
			logger.error("buscarExpedienteElectronicoPorReparticion NegocioException " + e);

			if (consultaBd)
				resultado = buscarEEPorReparticion(trata, expedienteMetaDataList, desde, hasta, tipoDocumento,
						numeroDocumento, datosUsuario, cuitCuil, estado);
		}

		if (logger.isDebugEnabled()) {
			logger.debug(
					"buscarExpedienteElectronicoPorReparticion(String, Trata, List<ExpedienteMetadata>, List<DatosDeBusqueda>, Date, Date, String, String, String, String) - end - return value={}",
					resultado);
		}

		return resultado;
	}

	private List<ExpedienteElectronicoDTO> buscarEEPorDomicilio(Date desde, Date hasta, String domicilio, String piso,
			String departamento, String codigoPostal) {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"buscarEEPorDomicilio(desde={}, hasta={}, domicilio={}, piso={}, departamento={}, codigoPostal={}) - start",
					desde, hasta, domicilio, piso, departamento, codigoPostal);
		}

		List<ExpedienteElectronicoDTO> returnList = expedienteElectronicoDAO
				.buscarExpedienteElectronicoPorDireccion(desde, hasta, domicilio, piso, departamento, codigoPostal);
		if (logger.isDebugEnabled()) {
			logger.debug("buscarEEPorDomicilio(Date, Date, String, String, String, String) - end - return value={}",
					returnList);
		}

		return returnList;
	}

	private List<ExpedienteElectronicoDTO> buscarEEPorReparticion(TrataDTO trata,
			List<ExpedienteMetadataDTO> expedienteMetaDataList, Date desde, Date hasta, String tipoDocumento,
			String numeroDocumento, Usuario datosUsuario, String cuitCuil, String estado) {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"buscarEEPorReparticion(trata={}, expedienteMetaDataList={}, desde={}, hasta={}, tipoDocumento={}, numeroDocumento={}, datosUsuario={}, cuitCuil={}, estado={}) - start",
					trata, expedienteMetaDataList, desde, hasta, tipoDocumento, numeroDocumento, datosUsuario, cuitCuil,
					estado);
		}

		String username;
		List<ExpedienteElectronicoDTO> resultado;

		if ((trata == null || trata.getCodigoTrata() == " ") && expedienteMetaDataList.isEmpty()) {
			resultado = expedienteElectronicoDAO.buscarExpedienteElectronicoPorReparticion(
					datosUsuario.getCodigoReparticion(), desde, hasta, tipoDocumento, numeroDocumento, cuitCuil,
					estado);
		} else {
			username = null;
			resultado = expedienteElectronicoDAO.buscarExpedienteElectronicoPorMetadatos(username,
					datosUsuario.getCodigoReparticion(), trata, expedienteMetaDataList, desde, hasta, tipoDocumento,
					numeroDocumento, cuitCuil, estado);
		}

		if (logger.isDebugEnabled()) {
			logger.debug(
					"buscarEEPorReparticion(Trata, List<ExpedienteMetadata>, Date, Date, String, String, Usuario, String, String) - end - return value={}",
					resultado);
		}

		return resultado;
	}

	public List<ExpedienteAsociadoEntDTO> obtenerListaEnFusionados(Long idExpedienteElectronico) {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerListaEnFusionados(idExpedienteElectronico={}) - start", idExpedienteElectronico);
		}

		List<ExpedienteAsociadoEntDTO> returnList = expedienteElectronicoDAO
				.obtenerListaEnFusionados(idExpedienteElectronico);

		if (logger.isDebugEnabled()) {
			logger.debug("obtenerListaEnFusionados(Integer) - end - return value={}", returnList);
		}

		return returnList;
	}

	public Boolean isExpedienteElectronicoBloqueado(String tipoExpediente, Integer anio, Integer numero,
			String reparticion) {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"isExpedienteElectronicoBloqueado(tipoExpediente={}, anio={}, numero={}, reparticion={}) - start",
					tipoExpediente, anio, numero, reparticion);
		}

		ExpedienteElectronico expedienteElectronico = expedienteElectronicoRepository
				.findByTipoDocumentoAndAnioAndNumeroAndCodigoReparticionUsuario(tipoExpediente, anio, numero,
						reparticion.trim());

		if (expedienteElectronico == null) {
			throw new NegocioException("No se encontró el expediente solicitado.", null);
		}

		Boolean returnBoolean = expedienteElectronico.getBloqueado();

		if (logger.isDebugEnabled()) {
			logger.debug("isExpedienteElectronicoBloqueado(String, Integer, Integer, String) - end - return value={}",
					returnBoolean);
		}

		return returnBoolean;
	}

	public List<ExpedienteAsociadoEntDTO> obtenerListaEnTramitacionConjunta(Long idExpedienteElectronico) {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerListaEnTramitacionConjunta(idExpedienteElectronico={}) - start",
					idExpedienteElectronico);
		}

		List<ExpedienteAsociadoEntDTO> returnList = this.expedienteElectronicoDAO
				.obtenerListaEnTramitacionConjunta(idExpedienteElectronico);
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerListaEnTramitacionConjunta(Integer) - end - return value={}", returnList);
		}

		return returnList;
	}

	public void moverArchivoTrabajoPorWebDav(String relativeUriOri, String relativeUriDest, String filenameOri,
			String filenameDest) {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"moverArchivoTrabajoPorWebDav(relativeUriOri={}, relativeUriDest={}, filenameOri={}, filenameDest={}) - start",
					relativeUriOri, relativeUriDest, filenameOri, filenameDest);
		}

		webDavService.copyFile(relativeUriOri, relativeUriDest, filenameOri, filenameDest, "", "");

		if (logger.isDebugEnabled()) {
			logger.debug("moverArchivoTrabajoPorWebDav(String, String, String, String) - end");
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED)
	public void modificarExpedienteElectronicoPorTramitacionConjuntaOFusion(
			ExpedienteElectronicoDTO expedienteElectronicoDto) {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"modificarExpedienteElectronicoPorTramitacionConjuntaOFusion(expedienteElectronico={}) - start",
					expedienteElectronicoDto);
		}

		logger.debug("modificarExpedienteElectronicoPorTramitacionConjuntaOFusion ",
				expedienteElectronicoDto.toString());

		ExpedienteElectronico entity = mapper.map(expedienteElectronicoDto, ExpedienteElectronico.class);

		entity.setPropertyResultado(null);
		expedienteElectronicoRepository.save(entity);

		this.indexarExpedienteElectronico(expedienteElectronicoDto);

		if (logger.isDebugEnabled()) {
			logger.debug("modificarExpedienteElectronicoPorTramitacionConjuntaOFusion(ExpedienteElectronicoDTO) - end");
		}
	}

	public void bloquearExpedienteElectronico(String sistemaBloqueador, String tipoExpediente, Integer anio,
			Integer numero, String reparticion) throws ParametroIncorrectoException, ExpedienteNoDisponibleException {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"bloquearExpedienteElectronico(sistemaBloqueador={}, tipoExpediente={}, anio={}, numero={}, reparticion={}) - start",
					sistemaBloqueador, tipoExpediente, anio, numero, reparticion);
		}

		if (sistemaBloqueador == null || sistemaBloqueador.trim().equals("")) {
			throw new ParametroIncorrectoException("El sistema bloqueador, no es un sistema válido.", null);
		}

		ExpedienteElectronico result = this.expedienteElectronicoRepository
				.findByTipoDocumentoAndAnioAndNumeroAndCodigoReparticionUsuario(tipoExpediente, anio, numero,
						reparticion.trim());

		if (result == null) {
			throw new NegocioException("No se encontró el expediente solicitado.", null);
		}

		if (result.getSistemaApoderado().trim().equals(sistemaBloqueador)
				|| result.getSistemaApoderado().trim().equals(SISTEMA_EXPEDIENTE_ELECTRONICO)) {

			result.setSistemaApoderado(sistemaBloqueador);
			result.setBloqueado(true);
			ExpedienteElectronicoUtils.agregarOquitarSufijoBloqueado(mapper.map(result, ExpedienteElectronicoDTO.class),
					processEngine);
			logger.debug("bloquearExpedienteElectronico ", result.toString());

			result.setPropertyResultado(null);
			expedienteElectronicoRepository.save(result);
		} else {
			throw new ExpedienteNoDisponibleException("El expediente se encuentra bloqueado por otro sistema externo.",
					null);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("bloquearExpedienteElectronico(String, String, Integer, Integer, String) - end");
		}
	}

	public void desbloquearExpedienteElectronico(String sistemaSolicitante, String tipoExpediente, Integer anio,
			Integer numero, String reparticion) throws ParametroIncorrectoException, ExpedienteNoDisponibleException {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"desbloquearExpedienteElectronico(sistemaSolicitante={}, tipoExpediente={}, anio={}, numero={}, reparticion={}) - start",
					sistemaSolicitante, tipoExpediente, anio, numero, reparticion);
		}

		if (sistemaSolicitante == null || sistemaSolicitante.trim().equals("")) {
			throw new ParametroIncorrectoException("El sistema solicitante del desbloqueo, no es un sistema válido.",
					null);
		}

		ExpedienteElectronico result = expedienteElectronicoRepository
				.findByTipoDocumentoAndAnioAndNumeroAndCodigoReparticionUsuario(tipoExpediente, anio, numero,
						reparticion.trim());

		if (result == null) {
			throw new NegocioException("No se encontró el expediente solicitado.", null);
		}

		if (result.getSistemaApoderado().trim().equals(sistemaSolicitante)
				|| result.getSistemaApoderado().trim().equals(SISTEMA_EXPEDIENTE_ELECTRONICO)) {

			result.setSistemaApoderado(SISTEMA_EXPEDIENTE_ELECTRONICO);
			result.setBloqueado(false);

			// Si el expediente está en guarda temporal, el workflow se cierra y
			// no es
			// necesario quitar los sufijos.
			if (!result.getEstado().equals(GUARDA_TEMPORAL)) {
				ExpedienteElectronicoUtils.agregarOquitarSufijoBloqueado(
						mapper.map(result, ExpedienteElectronicoDTO.class), processEngine);
			}

			logger.debug("modificar ", result.toString());

			ExpedienteElectronico entity = mapper.map(result, ExpedienteElectronico.class);

			entity.setPropertyResultado(null);
			expedienteElectronicoRepository.save(entity);
		} else {
			throw new ExpedienteNoDisponibleException("El expediente se encuentra bloqueado por otro sistema externo.",
					null);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("desbloquearExpedienteElectronico(String, String, Integer, Integer, String) - end");
		}
	}

	/**
	 * Esté método obtiene el número SADE qué una vez firmado, el documento
	 * tendrá una numeración SADE del siguiente tipo: Acrónimo SADE – año –
	 * número SADE – repartición del último usuario firmante En el caso que el
	 * último usuario firmante sea un apoderado, deberá quedar la repartición
	 * del usuario originalmente asignado a la tarea de firma
	 * 
	 * @param <code>java.lang.String</code>expedienteCodigo
	 * @return <code>java.lang.String</code>número sade
	 *         <codigoDocCaratula>AA-2012-00002844- -APRA</codigoDocCaratula>
	 */
	public String obtenerCodigoCaratulaPorNumeroExpediente(String expedienteCodigo) throws ProcesoFallidoException {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerCodigoCaratulaPorNumeroExpediente(expedienteCodigo={}) - start", expedienteCodigo);
		}

		ExpedienteElectronicoDTO expedienteElectronicoDTO = null;

		try {
			expedienteElectronicoDTO = obtenerExpedienteElectronicoPorCodigo(expedienteCodigo);
			DocumentoDTO documento = null;

			if (expedienteElectronicoDTO.getDocumentos().size() > 0) {
				Object[] docsArray = (Object[]) expedienteElectronicoDTO.getDocumentos().toArray();
				sortMe(docsArray, docsArray.length);
				for (int i = 0; i < docsArray.length; i++) {
					if ("Modificación Carátula".equalsIgnoreCase(((DocumentoDTO) docsArray[i]).getMotivo()))
						documento = (DocumentoDTO) docsArray[i];
					break;
				}
			}

			if (documento != null) {
				String returnString = documento.getNumeroSade();
				if (logger.isDebugEnabled()) {
					logger.debug("obtenerCodigoCaratulaPorNumeroExpediente(String) - end - return value={}",
							returnString);
				}
				return returnString;
			} else {
				String mensaje = "No se encuentra el documento asociado";

				if (logger.isDebugEnabled()) {
					logger.debug("obtenerCodigoCaratulaPorNumeroExpediente(String) - end - return value={}", mensaje);
				}
				return mensaje;
			}
		} catch (ParametroIncorrectoException pie) {
			logger.error("obtenerCodigoCaratulaPorNumeroExpediente(String)", pie);

			throw new ProcesoFallidoException(
					"Hubo un error en el método obtenerCodigoCaratulaPorNumeroExpediente [error=" + pie.getMessage()
							+ "]",
					pie);
		} catch (Exception e) {
			logger.error("obtenerCodigoCaratulaPorNumeroExpediente(String)", e);

			throw new ProcesoFallidoException(
					"Hubo un error en el método obtenerCodigoCaratulaPorNumeroExpediente [error=" + e.getMessage()
							+ "]",
					e);
		}
	}

	/* método auxiliar */
	private static void sortMe(Object[] docsArray, int ultimo) {
		if (logger.isDebugEnabled()) {
			logger.debug("sortMe(docsArray={}, ultimo={}) - start", docsArray, ultimo);
		}

		try {
			for (int i = 0; i < ultimo; i++) {
				for (int j = ultimo - 1; j > i; j--) {
					if (((DocumentoDTO) docsArray[j - 1]).getFechaCreacion().getTime() <= ((DocumentoDTO) docsArray[j])
							.getFechaCreacion().getTime()) { // asi
																// me
																// devuelve
																// el
																// primero
																// que
																// sea
																// mayor
																// a
																// todos
						intercambiar(docsArray, j - 1, j);
					}
				}
			}
		} catch (Exception e) {
			throw new NegocioException(e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("sortMe(Object[], int) - end");
		}
	}

	/* método auxiliar */
	private static void intercambiar(Object[] docsArray, int primero, int ultimo) {
		if (logger.isDebugEnabled()) {
			logger.debug("intercambiar(docsArray={}, primero={}, ultimo={}) - start", docsArray, primero, ultimo);
		}

		Object temp = docsArray[primero];
		docsArray[primero] = docsArray[ultimo];
		docsArray[ultimo] = temp;

		if (logger.isDebugEnabled()) {
			logger.debug("intercambiar(Object[], int, int) - end");
		}
	}

	@Override
	public String PasarFormatoStringSinHTML(String motivo) {
		if (logger.isDebugEnabled()) {
			logger.debug("PasarFormatoStringSinHTML(motivo={}) - start", motivo);
		}

		String salida = Jsoup.parse((String) motivo).text();

		if (logger.isDebugEnabled()) {
			logger.debug("PasarFormatoStringSinHTML(String) - end - return value={}", salida);
		}

		return salida;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public List<ExpedienteElectronicoDTO> buscarExpedientesElectronicosPorAnioReparticion(Integer anio,
			String reparticion) {
		List<ExpedienteElectronicoDTO> retorno = new ArrayList<>();

		List<ExpedienteElectronico> result = expedienteElectronicoRepository
				.findByCodigoReparticionUsuarioAndAnioAndSistemaCreadorOrderByIdDesc(reparticion, anio,
						ConstantesWeb.NOMBRE_APLICACION);
		if (result != null) {
			retorno = ListMapper.mapList(result, mapper, ExpedienteElectronicoDTO.class);
		}

		return retorno;
	}

	@Override
	public void grabarExpedienteElectronico(ExpedienteElectronicoDTO expedienteElectronico) throws RuntimeException {
		if (logger.isDebugEnabled()) {
			logger.debug("grabarExpedienteElectronico(expedienteElectronico={}) - start", expedienteElectronico);
		}

		ExpedienteElectronico e = mapper.map(expedienteElectronico, ExpedienteElectronico.class);
		ExpedienteElectronico savedExpediente = expedienteElectronicoRepository.save(e);
		expedienteElectronico.setId(savedExpediente.getId());

		// Se guarda en sesion el ID
		// * condición heredada *
		if (Executions.getCurrent() != null) {
			Executions.getCurrent().getSession().getAttributes().put(ConstantesCore.ID_EE_GUARDADO,
					expedienteElectronico.getId());
		}

		/*
		 * * Comentario heredado. * Sólo se deberá indexar en solr en aquellos
		 * expedientes que no esten en "Guarda Temporal", ean identificables y
		 * esten asociados a un workflow.
		 */

		if ("Guarda Temporal".equalsIgnoreCase(expedienteElectronico.getEstado())
				|| expedienteElectronico.getId() != null
				|| (expedienteElectronico.getIdWorkflow() != null
						&& !"".equalsIgnoreCase(expedienteElectronico.getIdWorkflow().trim()))
						&& !"null".equalsIgnoreCase(expedienteElectronico.getIdWorkflow().trim())) {
			indexarExpedienteElectronico(expedienteElectronico);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("grabarExpedienteElectronico(ExpedienteElectronicoDTO) - end");
		}
	}

	@Override
	public void modificarExpedienteElectronico(ExpedienteElectronicoDTO expedienteElectronicoDTO,
			Map<String, Object> camposFFCC) {
		if (logger.isDebugEnabled()) {
			logger.debug("modificarExpedienteElectronico(ee={}, camposFFCC={}) - start", expedienteElectronicoDTO,
					camposFFCC);
		}

		ExpedienteElectronico entity = mapper.map(expedienteElectronicoDTO, ExpedienteElectronico.class);

		entity.setPropertyResultado(null);
		expedienteElectronicoRepository.save(entity);

		solrService.indexarFormularioControlado(expedienteElectronicoDTO, camposFFCC);

		if (logger.isDebugEnabled()) {
			logger.debug("modificarExpedienteElectronico(ExpedienteElectronicoDTO, Map<String,Object>) - end");
		}
	}

	@Transactional
	public void vincularDocumentosOficiales(String sistemaUsuario, String usuario,
			ExpedienteElectronicoDTO expedienteElectronico, List<String> listaDocumentos)
			throws ProcesoFallidoException, ParametroIncorrectoException, ExpedienteNoDisponibleException {
		logger.debug("vincularDocumentosOficiales - Sistema Usuario: " + sistemaUsuario + " - usuario: " + usuario
				+ " - codigoEE: " + expedienteElectronico.getCodigoCaratula());

		if (listaDocumentos == null) {
			throw new ParametroIncorrectoException(LISTA_DOCS_NULA, null);
		}

		for (String codigoDocumento : listaDocumentos) {
			List<String> listDesgloseCodigoDocumento = BusinessFormatHelper
					.obtenerDesgloseCodigoDocumento(codigoDocumento);

			DocumentoDTO documentoGEDO = documentoManagerService.buscarDocumentoEstandar(
					listDesgloseCodigoDocumento.get(0), Integer.valueOf(listDesgloseCodigoDocumento.get(1)),
					Integer.valueOf(listDesgloseCodigoDocumento.get(2)), listDesgloseCodigoDocumento.get(4));

			if (documentoGEDO != null) {
				List<String> arrayDocumentos = new ArrayList<>();

				if (expedienteElectronico.getDocumentos().contains(documentoGEDO)) {
					arrayDocumentos.add(codigoDocumento);

					try {
						this.desvincularDocumentosOficiales(sistemaUsuario, usuario, expedienteElectronico,
								arrayDocumentos);
					} catch (Exception e) {
						if (logger.isErrorEnabled()) {
							logger.error("Hubo un error en el método desvincularDocumentosOficiales - Sistema Usuario: "
									+ sistemaUsuario + " - usuario: " + usuario + " - codigoEE: "
									+ expedienteElectronico.getCodigoCaratula() + " Doc: " + codigoDocumento
									+ " Vinculado anteriormente ", e);
						}
					}

					arrayDocumentos.remove(codigoDocumento);
				}

				if (!arrayDocumentos.contains(codigoDocumento)) {
					documentoGEDO.setFechaAsociacion(new Date());
					documentoGEDO.setUsuarioAsociador(usuario);
					if (!expedienteElectronico.getEstado().equals(ConstantesWeb.ESTADO_GUARDA_TEMPORAL)
							&& !expedienteElectronico.getEstado().equals(ConstantesWeb.ESTADO_SOLICITUD_ARCHIVO)) {
						documentoGEDO.setIdTask(obtenerWorkingTask(expedienteElectronico).getExecutionId());
					}
					// documentoGEDO.setIdTask(obtenerWorkingTask(expedienteElectronico).getExecutionId());
					documentoGEDO.setDefinitivo(false); // se hace definitivo al
					// efectuarse el pase
					if ((expedienteElectronico.getEsCabeceraTC() != null) && expedienteElectronico.getEsCabeceraTC()) {
						documentoGEDO.setIdExpCabeceraTC(expedienteElectronico.getId());
					}

					expedienteElectronico.getDocumentos().add(documentoGEDO);
					this.modificarExpedienteElectronico(expedienteElectronico);
				} else {
					logger.info("vincularDocumentosOficiales - Sistema Usuario: " + sistemaUsuario + " - usuario: "
							+ usuario + " - codigoEE: " + expedienteElectronico.getCodigoCaratula() + " Doc: "
							+ codigoDocumento + " Vinculado anteriormente ");

					throw new ProcesoFallidoException("El documento ya se encuentra vinculado al expediente.", null);
				}
			} else {
				throw new ParametroIncorrectoException("No fue posible encontrar el documento.", null);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("vincularDocumentosOficiales(String, String, ExpedienteElectronicoDTO, List<String>) - end");
		}
	}

	@Transactional
	public void desvincularDocumentosOficiales(String sistemaUsuario, String usuario,
			ExpedienteElectronicoDTO expedienteElectronico, List<String> listaDocumentos)
			throws ProcesoFallidoException, ParametroIncorrectoException, ExpedienteNoDisponibleException {

		logger.debug("desvincularDocumentosOficiales - Sistema Usuario: " + sistemaUsuario + " - usuario: " + usuario
				+ " - codigoEE: " + expedienteElectronico.getCodigoCaratula());

		if (listaDocumentos == null) {
			throw new ParametroIncorrectoException("La lista de documentos a desvincular, es nula.", null);
		}

		try {
			for (String codigoDocumento : listaDocumentos) {
				List<String> listDesgloseCodigoDocumento = BusinessFormatHelper
						.obtenerDesgloseCodigoDocumento(codigoDocumento);

				DocumentoDTO documentoGEDO = documentoManagerService.buscarDocumentoEstandar(
						listDesgloseCodigoDocumento.get(0), Integer.valueOf(listDesgloseCodigoDocumento.get(1)),
						Integer.valueOf(listDesgloseCodigoDocumento.get(2)), listDesgloseCodigoDocumento.get(4));

				// Validación para ver si el doc es definitivo y ver si hay que
				// desvincularlo o no
				expedienteElectronico.removeDoc(documentoGEDO);
			}

			this.modificarExpedienteElectronico(expedienteElectronico);
		} catch (Exception exception) {
			logger.error("desvincularDocumentosOficiales(String, String, ExpedienteElectronicoDTO, List<String>)",
					exception);

			throw new ProcesoFallidoException(exception.getMessage(), null);
		}
		if (logger.isDebugEnabled()) {
			logger.debug(
					"desvincularDocumentosOficiales(String, String, ExpedienteElectronicoDTO, List<String>) - end");
		}
	}

	@Override
	public List<ExpedienteElectronicoDTO> buscarExpedienteElectronicoPorDireccion(Date desde, Date hasta,
			String domicilio, String piso, String departamento, String codigoPostal, String estado) {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"buscarExpedienteElectronicoPorDireccion(desde={}, hasta={}, domicilio={}, piso={}, departamento={}, codigoPostal={}, estado={}) - start",
					desde, hasta, domicilio, piso, departamento, codigoPostal, estado);
		}

		List<ExpedienteElectronicoDTO> resultado = null;

		try {
			List<ExpedienteElectronicoIndex> expedientesElectronicosIndex = solrService
					.obtenerExpedientesElectronicosSolr(desde, hasta, domicilio, piso, departamento, codigoPostal,
							estado);
			resultado = convertirSolrDocument(expedientesElectronicosIndex);

		} catch (NegocioException e) {
			logger.error("buscarExpedienteElectronicoPorDireccion NegocioException " + e);
			if (consultaBd)
				resultado = this.buscarEEPorDomicilio(desde, hasta, domicilio, piso, departamento, codigoPostal);
		}

		if (logger.isDebugEnabled()) {
			logger.debug(
					"buscarExpedienteElectronicoPorDireccion(Date, Date, String, String, String, String, String) - end - return value={}",
					resultado);
		}

		return resultado;
	}

	public boolean realizarVinculacionDefinitivaVinculadosPor(ExpedienteElectronicoDTO expedienteElectronicoDTO,
			String destinatario) {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"realizarVinculacionDefinitivaVinculadosPor(expedienteElectronico={}, destinatario={}) - start",
					expedienteElectronicoDTO, destinatario);
		}

		boolean returnboolean = realizarVinculacionDefinitiva(expedienteElectronicoDTO, destinatario, true);

		if (logger.isDebugEnabled()) {
			logger.debug(
					"realizarVinculacionDefinitivaVinculadosPor(ExpedienteElectronicoDTO, String) - end - return value={}",
					returnboolean);
		}
		return returnboolean;
	}

	private boolean realizarVinculacionDefinitiva(ExpedienteElectronicoDTO ee, String destinatario,
			boolean VinculadosPorMiRamaParalela) {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"realizarVinculacionDefinitiva(ee={}, destinatario={}, VinculadosPorMiRamaParalela={}) - start", ee,
					destinatario, VinculadosPorMiRamaParalela);
		}

		List<DocumentoDTO> documentos = ee.getDocumentos();

		int cantidadDocu = documentos.size();
		int cantidadDocDefinitivos = 0;

		for (DocumentoDTO documento : documentos) {
			if (documento.getDefinitivo()) {
				cantidadDocDefinitivos++;
			}
		}

		if (cantidadDocu == cantidadDocDefinitivos) {
			ee.setFechaModificacion(new Date());
			ee.setUsuarioModificacion(destinatario);
			this.modificarExpedienteElectronico(ee);

			if (logger.isDebugEnabled()) {
				logger.debug(
						"realizarVinculacionDefinitiva(ExpedienteElectronicoDTO, String, boolean) - end - return value={}",
						false);
			}
			return false;
		}

		if (VinculadosPorMiRamaParalela) {
			this.hacerDocumentosDefinitivoVinculadosPor(ee.getDocumentos(), destinatario);
		} else {
			this.hacerDocumentosDefinitivo(ee.getDocumentos());

		}

		if (ee.getEsReservado()) {
			String codigoTrata = ee.getTrata().getCodigoTrata();
			Usuario usuario = usuariosSADEService.getDatosUsuario(destinatario);
			actualizarDocumentosEnGedo(ee, destinatario, usuario, codigoTrata);
			List<ReparticionParticipanteDTO> listRepRect = obtenerReparticionesRectoras(ee, destinatario, codigoTrata);
			documentoGedoService.asignarPermisosVisualizacionGEDO(ee, usuario, listRepRect);
		}

		ee.setFechaModificacion(new Date());
		ee.setUsuarioModificacion(destinatario);
		this.modificarExpedienteElectronico(ee);

		if (logger.isDebugEnabled()) {
			logger.debug(
					"realizarVinculacionDefinitiva(ExpedienteElectronicoDTO, String, boolean) - end - return value={}",
					true);
		}

		return true;
	}

	private void hacerDocumentosDefinitivoVinculadosPor(List<DocumentoDTO> documentoList, String usuario) {
		if (logger.isDebugEnabled()) {
			logger.debug("hacerDocumentosDefinitivoVinculadosPor(documentoList={}, usuario={}) - start", documentoList,
					usuario);
		}

		if (documentoList != null) {
			for (DocumentoDTO documento : documentoList) {
				if (documento != null && !documento.getDefinitivo()
						&& documento.getUsuarioAsociador().equals(usuario)) {
					documento.setDefinitivo(true);
				}
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("hacerDocumentosDefinitivoVinculadosPor(List<DocumentoDTO>, String) - end");
		}
	}

	public boolean realizarVinculacionDefinitiva(ExpedienteElectronicoDTO expedienteElectronicoDTO,
			String destinatario) {
		if (logger.isDebugEnabled()) {
			logger.debug("realizarVinculacionDefinitiva(expedienteElectronico={}, destinatario={}) - start",
					expedienteElectronicoDTO, destinatario);
		}

		boolean returnboolean = realizarVinculacionDefinitiva(expedienteElectronicoDTO, destinatario, false);

		if (logger.isDebugEnabled()) {
			logger.debug("realizarVinculacionDefinitiva(ExpedienteElectronicoDTO, String) - end - return value={}",
					returnboolean);
		}

		return returnboolean;
	}

	private void validacionesParaAdjuntarDocumentoAEE(String sistemaUsuario, String usuario,
			ExpedienteElectronicoDTO expedienteElectronico, List<String> listaDocumentos)
			throws ParametroIncorrectoException {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"validacionesParaAdjuntarDocumentoAEE(sistemaUsuario={}, usuario={}, expedienteElectronico={}, listaDocumentos={}) - start",
					sistemaUsuario, usuario, expedienteElectronico, listaDocumentos);
		}

		if (listaDocumentos == null || listaDocumentos.isEmpty()) {
			throw new ParametroIncorrectoException(LISTA_DOCS_NULA, null);
		}

		if (expedienteElectronico.getEstado().equals(ConstantesWeb.ESTADO_GUARDA_TEMPORAL)) {
			throw new ParametroIncorrectoException(
					"No se pueden adjuntar documentos a un EE que se encuentra en estado 'Guarda Temporal'", null);
		}

		if (logger.isDebugEnabled()) {
			logger.debug(
					"validacionesParaAdjuntarDocumentoAEE(String, String, ExpedienteElectronicoDTO, List<String>) - end");
		}
	}

	private void validacionesParaAdjuntarDocumentosAEEGuardaTemporal(String sistemaUsuario, String usuario,
			ExpedienteElectronicoDTO expedienteElectronicoDTO, List<String> listaDocumentos)
			throws ParametroIncorrectoException {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"validacionesParaAdjuntarDocumentosAEEGuardaTemporal(sistemaUsuario={}, usuario={}, expedienteElectronico={}, listaDocumentos={}) - start",
					sistemaUsuario, usuario, expedienteElectronicoDTO, listaDocumentos);
		}

		if (listaDocumentos == null || listaDocumentos.isEmpty()) {
			throw new ParametroIncorrectoException(LISTA_DOCS_NULA, null);
		}

		String[] sistemasSolicitantes = sistemasSolicitanteArchivo.split(",");

		if (!contieneSistema(sistemasSolicitantes, sistemaUsuario)) {
			throw new ParametroIncorrectoException(
					"El sistema solicitante no tiene permisos para adjuntar documentos a un EE con estado 'Guarda Temporal'",
					null);
		}

		if (!expedienteElectronicoDTO.getEstado().equals(ConstantesWeb.ESTADO_GUARDA_TEMPORAL)
				|| !expedienteElectronicoDTO.getEstado().equals(ConstantesWeb.ESTADO_SOLICITUD_ARCHIVO)) {
			throw new ParametroIncorrectoException(
					"El expediente no se encuentra en estado 'Guarda Temporal' o 'Solicitud Archivo'", null);
		}

		if (logger.isDebugEnabled()) {
			logger.debug(
					"validacionesParaAdjuntarDocumentosAEEGuardaTemporal(String, String, ExpedienteElectronicoDTO, List<String>) - end");
		}
	}

	private boolean contieneSistema(String[] sistemasSolicitantes, String sistemaUsuario) {
		if (logger.isDebugEnabled()) {
			logger.debug("contieneSistema(sistemasSolicitantes={}, sistemaUsuario={}) - start", sistemasSolicitantes,
					sistemaUsuario);
		}

		for (int i = 0; i < sistemasSolicitantes.length; i++) {
			if (sistemasSolicitantes[i].equals(sistemaUsuario)) {
				if (logger.isDebugEnabled()) {
					logger.debug("contieneSistema(String[], String) - end - return value={}", true);
				}
				return true;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("contieneSistema(String[], String) - end - return value={}", false);
		}

		return false;
	}

	@Transactional
	public void vincularDocumentosOficialesAEEGuardaTemporal(String sistemaUsuario, String usuario,
			ExpedienteElectronicoDTO expedienteElectronico, List<String> listaDocumentos)
			throws ParametroIncorrectoException, ProcesoFallidoException {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"vincularDocumentosOficialesAEEGuardaTemporal(sistemaUsuario={}, usuario={}, expedienteElectronico={}, listaDocumentos={}) - start",
					sistemaUsuario, usuario, expedienteElectronico, listaDocumentos);
		}

		validacionesParaAdjuntarDocumentosAEEGuardaTemporal(sistemaUsuario, usuario, expedienteElectronico,
				listaDocumentos);
		try {
			vincularDocumentosOficiales(sistemaUsuario, usuario, expedienteElectronico, listaDocumentos);
		} catch (NegocioException e) {
			logger.error(
					"vincularDocumentosOficialesAEEGuardaTemporal(String, String, ExpedienteElectronicoDTO, List<String>)",
					e);

			throw new ParametroIncorrectoException(e.getMessage(), e);
		} catch (ExpedienteNoDisponibleException e) {
			logger.error(
					"vincularDocumentosOficialesAEEGuardaTemporal(String, String, ExpedienteElectronicoDTO, List<String>)",
					e);

			throw new ProcesoFallidoException(e.getMessage(), e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug(
					"vincularDocumentosOficialesAEEGuardaTemporal(String, String, ExpedienteElectronicoDTO, List<String>) - end");
		}
	}

	@Override
	public List<ExpedienteElectronicoDTO> obtenerExpedientesEnGuardaTemporalMayorA24Meses(Integer cantidadDeDias) {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerExpedientesEnGuardaTemporalMayorA24Meses(cantidadDeDias={}) - start", cantidadDeDias);
		}

		List<ExpedienteElectronicoDTO> returnList = expedienteElectronicoDAO
				.buscarExpedientesGuardaTemporalMayorA24Meses(cantidadDeDias);

		if (logger.isDebugEnabled()) {
			logger.debug("obtenerExpedientesEnGuardaTemporalMayorA24Meses(Integer) - end - return value={}",
					returnList);
		}

		return returnList;
	}

	@Override
	public List<ExpedienteElectronicoDTO> obtenerExpedientesEnGuardaTemporalPorCodigoExpediente(String codExpediente) {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerExpedientesEnGuardaTemporalPorCodigoExpediente(codExpediente={}) - start",
					codExpediente);
		}

		List<ExpedienteElectronicoDTO> rsp = new ArrayList<>();
		ExpedienteElectronicoDTO expedienteElectronicoDTO = null;

		try {
			expedienteElectronicoDTO = this.obtenerExpedienteElectronicoPorCodigo(codExpediente);
		} catch (ParametroIncorrectoException e1) {
			logger.error("Error al consultar Expediente , revise los parametros", e1);
		}

		rsp.add(expedienteElectronicoDTO);

		if (logger.isDebugEnabled()) {
			logger.debug("obtenerExpedientesEnGuardaTemporalPorCodigoExpediente(String) - end - return value={}", rsp);
		}

		return rsp;
	}

	@Override
	public List<ExpedienteAsociadoEntDTO> buscarExpedientesAsociados(Long idExpedienteElectronico,
			String sistemaOrigen) {
		if (logger.isDebugEnabled()) {
			logger.debug("buscarExpedientesAsociados(idExpedienteElectronico={}, sistemaOrigen={}) - start",
					idExpedienteElectronico, sistemaOrigen);
		}

		List<ExpedienteAsociadoEntDTO> returnList = expedienteElectronicoDAO
				.buscarExpedientesAsociados(idExpedienteElectronico, sistemaOrigen);

		if (logger.isDebugEnabled()) {
			logger.debug("buscarExpedientesAsociados(Integer, String) - end - return value={}", returnList);
		}

		return returnList;
	}

	public void generarPaseExpedienteElectronicoSinDocumentoSinTocarDefinitivos(Task t,
			ExpedienteElectronicoDTO expedienteElectronicoDTO, Usuario user, String estado, String motivo,
			Map<String, String> variables) {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"generarPaseExpedienteElectronicoSinDocumentoSinTocarDefinitivos(t={}, ee={}, user={}, estado={}, motivo={}, variables={}) - start",
					t, expedienteElectronicoDTO, user, estado, motivo, variables);
		}
		if (variables == null) {
			variables = new HashMap<>();
		}
		Usuario usuario = usuariosSADEService.getDatosUsuario(user.getUsername());

		if (usuario == null) {
			// si el usuario no esta quiere decir que el usuario que recibo es
			// un
			// usuarioMigracion transformado al objeto usuario
			// dieron de baja al usuario
			usuario = new Usuario();
			usuario.setUsername(user.getUsername());
			usuario.setCodigoReparticionOriginal(user.getCodigoReparticionOriginal());
			usuario.setCodigoSectorInterno(user.getCodigoSectorInterno());
			usuario.setCodigoReparticion(user.getCodigoReparticionOriginal());
		}

		generarPaseExpedienteElectronicoDejandoDefinitivo(t, expedienteElectronicoDTO, usuario, estado, motivo, null,
				variables, true, null, false);

		if (logger.isDebugEnabled()) {
			logger.debug(
					"generarPaseExpedienteElectronicoSinDocumentoSinTocarDefinitivos(Task, ExpedienteElectronicoDTO, Usuario, String, String, Map<String,String>) - end");
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.egoveris.te.base.service.expediente.ExpedienteElectronicoService#
	 * guardadoTemporalExpedientes()
	 */
	@Override
	public void guardadoTemporalExpedientes() throws InterruptedException {

		String numDayExpired = dBProperty.getString("num.expired.subsanacion");

		Integer diasSubsanacion;

		if (numDayExpired != null && !StringUtils.EMPTY.equals(numDayExpired)) {
			diasSubsanacion = Integer.parseInt(numDayExpired);
			// Suma los días recibidos a la fecha
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date()); // Configuramos la fecha que se recibe
			calendar.add(Calendar.DAY_OF_YEAR, -diasSubsanacion); // numero de
																	// días a
																	// añadir, o
																	// restar en
																	// caso de
																	// días<0
			List<ActividadDTO> listDto = actividadService.buscarActividadesSubsanacionPendiente(calendar.getTime());
			if (org.apache.commons.collections.CollectionUtils.isNotEmpty(listDto)) {
				for (ActividadDTO act : listDto) {
					generarPaseGuardaTemporal(act);
				}
			}
		}
	}

	/**
	 * Generar pase guarda temporal.
	 *
	 * @param act
	 *            the act
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	private void generarPaseGuardaTemporal(ActividadDTO act) throws InterruptedException {
		// Obtenemos el expediente y lo cerramos (guardado temporal)
		ExpedienteElectronicoDTO dto = this.buscarExpedienteElectronicoByIdTask(act.getIdObjetivo());
		if (dto != null && !GUARDA_TEMPORAL.equals(dto.getEstado())) {
			this.closedActividadesSubsanacion(dto, act);
			this.generarPaseGuardaTemporal(dto);
			// externalTareaService.cambioEstadoCron(dto.getNumero().longValue());
		}
	}

	/**
	 * Generar pase expediente electronico guarda temporal.
	 *
	 * @param expedienteElectronico
	 *            the expediente electronico
	 * @throws InterruptedException
	 */
	private void generarPaseGuardaTemporal(ExpedienteElectronicoDTO expedienteElectronico) throws InterruptedException {
		Map<String, String> detalles;
		// Traemos el task correspondiente al expediente
		Task taskExp = taskViewService.obtenerTaskById(expedienteElectronico.getIdWorkflow());
		String estadoAnterior = taskExp.getActivityName();

		String loggedUsername = expedienteElectronico.getUsuarioCreador();
		detalles = new HashMap<>();
		detalles.put("estadoAnterior", estadoAnterior);
		// este es obtenible también desde el estado actual del expediente
		// electrónico.
		detalles.put(ConstantesWeb.ESTADO_ANTERIOR_PARALELO, null);
		detalles.put(ConstantesWeb.DESTINATARIO, null);
		detalles.put(ConstantesWeb.LOGGED_USERNAME, loggedUsername);

		if (expedienteElectronico.getTrata() != null) {
			detailTipoResult(expedienteElectronico, detalles);
		}
		// Cerramos todas las actividades para el pase del expediente
		invocaServicoRest(expedienteElectronico);
		if ((expedienteElectronico.getEsCabeceraTC() != null) && !expedienteElectronico.getEsCabeceraTC()) {
			archivar(expedienteElectronico, taskExp, loggedUsername, detalles, estadoAnterior);
		} else {
			generarPaseTramitacionConjunta(expedienteElectronico, taskExp, RECHAZADA, detalles, estadoAnterior,
					loggedUsername);
		}

	}

	/**
	 * Generar pase tramitacion conjunta.
	 *
	 * @param expedienteElectronico
	 *            the expediente electronico
	 * @param taskExp
	 *            the task exp
	 * @param estadoSeleccionado
	 *            the estado seleccionado
	 * @param detalles
	 *            the detalles
	 * @param estadoAnterior
	 *            the estado anterior
	 * @param loggedUsername
	 *            the logged username
	 * @throws WrongValueException
	 *             the wrong value exception
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	private void generarPaseTramitacionConjunta(ExpedienteElectronicoDTO expedienteElectronico, Task taskExp,
			String estadoSeleccionado, Map<String, String> detalles, String estadoAnterior, String loggedUsername)
			throws WrongValueException, InterruptedException {
		try {
			String destino = null;

			boolean bandera = false;
			if (estadoSeleccionado.equals(ConstantesWeb.ESTADO_GUARDA_TEMPORAL) && !this.fusionService
					.esExpedienteFusion(expedienteElectronico.getListaExpedientesAsociados().get(0).getNumero())) {
				DocumentoDTO documentoTCDesvinculacion;
				expedienteElectronico.setEsCabeceraTC(false);

				TaskQuery taskQuery = this.processEngine.getTaskService().createTaskQuery()
						.executionId(expedienteElectronico.getIdWorkflow());
				Task task = taskQuery.uniqueResult();
				ArrayList<ExpedienteAsociadoEntDTO> aux = new ArrayList<>(
						expedienteElectronico.getListaExpedientesAsociados());
				String motivo = MOTIVO_DESVINCULACION_TRAMITACION_CONJUNTA;
				documentoTCDesvinculacion = this.tramitacionConjuntaService
						.generarDocumentoDeDesvinculacionEnTramitacionConjunta(expedienteElectronico, motivo,
								loggedUsername, task);
				expedienteElectronico = this.tramitacionConjuntaService.desvincularExpedientesTramitacionConjunta(
						expedienteElectronico, loggedUsername, documentoTCDesvinculacion);
				this.modificarExpedienteElectronico(expedienteElectronico);

				for (ExpedienteAsociadoEntDTO expedienteAsociado : aux) {
					this.expedienteAsociadoService.deleteExpedienteAsociado(expedienteAsociado);
				}
			} else {
				// Genero el pase y el avance de la task de cada expediente
				// adjunto
				expedienteElectronico.hacerDefinitivosArchivosDeTrabajo();
				expedienteElectronico.hacerDocsDefinitivos();
				expedienteElectronico.hacerDefinitivosAsociados();
				this.tramitacionConjuntaService.actualizarArchivosDeTrabajoEnTramitacionConjunta(expedienteElectronico,
						loggedUsername);
				this.tramitacionConjuntaService
						.actualizarExpedientesAsociadosEnTramitacionConjunta(expedienteElectronico);
				this.tramitacionConjuntaService.actulizarDocumentosEnTramitacionConjunta(expedienteElectronico);
				bandera = true;
			}

			// Genero el pase y el avance de la task del expediente
			// cabecera.
			// Obtenemos el expediente nuevamente
			ExpedienteElectronicoDTO dto = this
					.buscarExpedienteElectronicoByIdTask(expedienteElectronico.getIdWorkflow());

			generarPaseExpedienteElectronico(dto, taskExp, estadoSeleccionado, detalles, estadoAnterior,
					loggedUsername);

			if (bandera) {
				this.tramitacionConjuntaService.movimientoTramitacionConjunta(expedienteElectronico, loggedUsername,
						detalles, estadoAnterior, estadoSeleccionado, StringUtils.EMPTY, destino);
			}
		} catch (RemoteAccessException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error("Error al realizar el pase de la tramitacion conjunta.", e);
		}
	}

	/**
	 * Generar pase expediente electronico.
	 *
	 * @param expedienteElectronico
	 *            the expediente electronico
	 * @param taskExp
	 *            the task exp
	 * @param estadoSeleccionado
	 *            the estado seleccionado
	 * @param detalles
	 *            the detalles
	 * @param estadoAnterior
	 *            the estado anterior
	 * @param loggedUsername
	 *            the logged username
	 * @throws WrongValueException
	 *             the wrong value exception
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	private void generarPaseExpedienteElectronico(ExpedienteElectronicoDTO expedienteElectronico, Task taskExp,
			String estadoSeleccionado, Map<String, String> detalles, String estadoAnterior, String loggedUsername)
			throws WrongValueException, InterruptedException {
		try {

			detalles.put("WEBCONTEXTPASE", "SI");
			this.controlTransaccionService.save(this.processEngine, taskExp, expedienteElectronico, loggedUsername,
					RECHAZADA, StringUtils.EMPTY, detalles);

		} catch (RemoteAccessException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error("Error al realizar el pase del expediente electrónico.", e);
		}
	}

	/**
	 * Invoca servico rest.
	 *
	 * @param expedienteElectronico
	 *            the expediente electronico
	 */
	private void invocaServicoRest(ExpedienteElectronicoDTO expedienteElectronico) {
		// TODO
	}

	/**
	 * Archivar.
	 *
	 * @param expedienteElectronico
	 *            the expediente electronico
	 * @param taskExp
	 *            the task exp
	 * @param loggedUsername
	 *            the logged username
	 * @param detalles
	 *            the detalles
	 * @param estadoAnterior
	 *            the estado anterior
	 * @throws InterruptedException
	 *             the interrupted exception
	 */
	private synchronized void archivar(ExpedienteElectronicoDTO expedienteElectronico, Task taskExp,
			String loggedUsername, Map<String, String> detalles, String estadoAnterior) throws InterruptedException {
		try {
			// Servicio de Pase de Guarda Temporal
			this.paseExpedienteService.paseGuardaTemporal(expedienteElectronico, taskExp, loggedUsername, detalles,
					estadoAnterior, StringUtils.EMPTY);

			expedienteElectronico.setEstado(GUARDA_TEMPORAL);
			WorkFlow workFlow = getWorkFlowService().createWorkFlow(expedienteElectronico.getId(), GUARDA_TEMPORAL);
			workFlow.initParameters(detalles);
			workFlow.execute(workFlow.getWorkingTask(), this.processEngine);

			// Previo al cierre, si este expediente corresponde a un subproceso
			// se ejecutara el script de cierre, si lo posee
			SubProcesoOperacionDTO subProcesoDTO = subprocessService.getSubprocessFromEE(expedienteElectronico.getId());

			if (subProcesoDTO != null) {
				WorkFlowScriptUtils.getInstance().executeSubprocessScript(subProcesoDTO.getSubproceso().getScriptEnd(),
						expedienteElectronico.getId(), null, Executions.getCurrent().getRemoteUser());
			}
			// 11-05-2020: No ejecutar el cierre, o desaparece la tarea
			//processEngine.getExecutionService().signalExecutionById(workFlow.getWorkingTask().getExecutionId(),
			//		"Cierre");

		} catch (RemoteAccessException e) {
			logger.error(e.getMessage(), e);
		} catch (Exception e) {
			logger.error("Error al archivar el expediente", e);
		}
	}

	/**
	 * Detail tipo result.
	 *
	 * @param expedienteElectronico
	 *            the expediente electronico
	 * @param detalles
	 *            the detalles
	 */
	private void detailTipoResult(ExpedienteElectronicoDTO expedienteElectronico, Map<String, String> detalles) {
		for (TrataTipoResultadoDTO trataTipoResultadoDTO : expedienteElectronico.getTrata().getTipoResultadosTrata()) {
			if (trataTipoResultadoDTO.getProperty() != null) {
				if ((!trataTipoResultadoDTO.getProperty().getValor().equalsIgnoreCase("aprobada"))) {
					detalles.put("resultLabel", trataTipoResultadoDTO.getProperty().getValor());
					detalles.put("resultValue", trataTipoResultadoDTO.getProperty().getClave());
				}
			}
		}
	}

	/**
	 * Cerrar actvidades asociadas al expediente.
	 *
	 * @param dto
	 *            the dto
	 */
	private void closedActividadesSubsanacion(ExpedienteElectronicoDTO dto, ActividadDTO dtoAct) {
		String userName = dto.getUsuarioCreador();
		ActividadDTO act = actividadService.buscarActividad(dtoAct.getId());
		if (act.getEstado().equals(ConstantesWeb.ESTADO_PENDIENTE)
				|| act.getEstado().equals(ConstantesWeb.ESTADO_ABIERTA)) {
			actividadExpedienteService.eliminacionActividadVUC(dto, act, userName);
			actividadService.cerrarActividad(act, userName);
		}
	}

	private void generarPaseExpedienteElectronicoDejandoDefinitivo(Task workingTask,
			ExpedienteElectronicoDTO expedienteElectronico, Usuario usuario, String estadoSeleccionado,
			String motivoExpediente, String numeroSadePase, Map<String, String> detalles, boolean persistirCambios,
			String numeroSADEPase, boolean generarDocGedo) {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"generarPaseExpedienteElectronicoDejandoDefinitivo(workingTask={}, expedienteElectronico={}, usuario={}, estadoSeleccionado={}, motivoExpediente={}, numeroSadePase={}, detalles={}, persistirCambios={}, numeroSADEPase={}, generarDocGedo={}) - start",
					workingTask, expedienteElectronico, usuario, estadoSeleccionado, motivoExpediente, numeroSadePase,
					detalles, persistirCambios, numeroSADEPase, generarDocGedo);
		}

		try {
			detalles.put(ConstantesWeb.ESTADO, expedienteElectronico.getEstado());
			detalles.put(ConstantesWeb.MOTIVO, formatoMotivo(motivoExpediente));

			if (estadoSeleccionado != null) {
				detalles.put(ConstantesWeb.ESTADO, estadoSeleccionado);
				if (estadoSeleccionado.equals(GUARDA_TEMPORAL)) {
					detalles.put(ConstantesWeb.DESTINATARIO, reparticionGT);
				}
			}

			String loggedUsername = usuario.getUsername();
			detalles.put(ConstantesWeb.REPARTICION_USUARIO, usuario.getCodigoReparticion());
			detalles.put(ConstantesWeb.SECTOR_USUARIO, usuario.getCodigoSectorInterno());

			/**
			 * SM: se mueve a lo ultimo el guardado del historial, en caso de
			 * fallo no queda el historialGrabado
			 * 
			 */
			// guardarDatosEnHistorialOperacion(expedienteElectronico, usuario,
			// detalles);

			String codigoTrata = expedienteElectronico.getTrata().getCodigoTrata();
			List<ReparticionParticipanteDTO> listRepRect = obtenerReparticionesRectoras(expedienteElectronico,
					loggedUsername, codigoTrata);

			// Se setea el atributo definivo de Archivos de Trabajo, Documentos
			// y Expediente Asociado

			/**
			 * Se realiza esta modificacion para que en el documento pdf se
			 * visualice el Destinatario debajo del motivo.
			 */
			String destinatario = detalles.get(ConstantesWeb.DESTINATARIO);
			Usuario datosUsuario = usuariosSADEService.getDatosUsuario(destinatario);

			Date fechaModificacion = new Date();
			expedienteElectronico.setFechaModificacion(fechaModificacion);
			expedienteElectronico.setUsuarioModificacion(loggedUsername);
			expedienteElectronico.setEstado(estadoSeleccionado);
			expedienteElectronico.setCantidadSubsanar(null);

			actualizaArchivosDeTrabajo(expedienteElectronico, loggedUsername, datosUsuario, destinatario, listRepRect);

			if (expedienteElectronico.getEsCabeceraTC() != null && !expedienteElectronico.getEsCabeceraTC()) {
				this.modificarExpedienteElectronicoPorTramitacionConjuntaOFusion(expedienteElectronico);

			} else {
				this.grabarExpedienteElectronico(expedienteElectronico);
			}

			if (expedienteElectronico.getEsCabeceraTC() != null && expedienteElectronico.getEsCabeceraTC()) {
				tramitacionConjuntaService.actulizarDocumentosEnTramitacionConjunta(expedienteElectronico);
			}

			if (expedienteElectronico.getEsReservado()) {
				Usuario userLogeado = usuariosSADEService.getDatosUsuario(loggedUsername);
				actualizarDocumentosEnGedo(expedienteElectronico, destinatario, userLogeado, codigoTrata);
				documentoGedoService.asignarPermisosVisualizacionGEDO(expedienteElectronico, userLogeado, listRepRect);
			}
			actualizoWorkFlow(workingTask, loggedUsername, detalles, motivoExpediente);
			guardarDatosEnHistorialOperacion(expedienteElectronico, usuario, detalles);
		} catch (RuntimeException rte) {
			try {
				logger.error("Se deben eliminar los documentos de pase generados", rte);
				setVariableWorkFlow(workingTask, ConstantesWeb.MOTIVO, this.motivoParaleloAnterior);

			} catch (Exception e) {
				logger.error("No se pudo eliminar el documento generado", e);
				setVariableWorkFlow(workingTask, ConstantesWeb.MOTIVO, this.motivoParaleloAnterior);
			}
			throw rte;
		}

		if (logger.isDebugEnabled()) {
			logger.debug(
					"generarPaseExpedienteElectronicoDejandoDefinitivo(Task, ExpedienteElectronicoDTO, Usuario, String, String, String, Map<String,String>, boolean, String, boolean) - end");
		}
	}

	@Override
	public List<ExpedienteElectronicoDTO> buscarExpedienteElectronicoporTrataEnSolr(String expedienteEstado,
			List<String> codigosTrataList) {
		if (logger.isDebugEnabled()) {
			logger.debug("buscarExpedienteElectronicoporTrataEnSolr(expedienteEstado={}, codigosTrataList={}) - start",
					expedienteEstado, codigosTrataList);
		}

		List<ExpedienteElectronicoDTO> returnList = this.expedienteElectronicoDAO
				.buscarExpedienteElectronicoporTrata(expedienteEstado, null, codigosTrataList);

		if (logger.isDebugEnabled()) {
			logger.debug("buscarExpedienteElectronicoporTrataEnSolr(String, List<String>) - end - return value={}",
					returnList);
		}

		return returnList;
	}

	public List<String> obtenerIdsDeTaskAsociadasAGrupo(String repSec) {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerIdsDeTaskAsociadasAGrupo(repSec={}) - start", repSec);
		}

		List<String> returnList = expedienteElectronicoDAO.obtenerIdsDeTaskEnGrupo(repSec);
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerIdsDeTaskAsociadasAGrupo(String) - end - return value={}", returnList);
		}

		return returnList;
	}

	public List<String> obenerIdsDeTaskAsociadasAGrupoBloqueado(String repSec) {
		if (logger.isDebugEnabled()) {
			logger.debug("obenerIdsDeTaskAsociadasAGrupoBloqueado(repSec={}) - start", repSec);
		}

		List<String> returnList = expedienteElectronicoDAO.obtenerIdsDeTaskEnGrupo(repSec + ".bloqueado");

		if (logger.isDebugEnabled()) {
			logger.debug("obenerIdsDeTaskAsociadasAGrupoBloqueado(String) - end - return value={}", returnList);
		}

		return returnList;
	}

	@Override
	public void actualizarReservas(String repVieja, String repNueva) {
		if (logger.isDebugEnabled()) {
			logger.debug("actualizarReservas(repVieja={}, repNueva={}) - start", repVieja, repNueva);
		}

		expedienteElectronicoDAO.actualizarReserva(repVieja, repNueva);

		if (logger.isDebugEnabled()) {
			logger.debug("actualizarReservas(String, String) - end");
		}
	}

	public void actualizarReservaSector(String repOrigen, String secOrigen, String repDestino, String secDestino) {
		if (logger.isDebugEnabled()) {
			logger.debug("actualizarReservaSector(repOrigen={}, secOrigen={}, repDestino={}, secDestino={}) - start",
					repOrigen, secOrigen, repDestino, secDestino);
		}

		expedienteElectronicoDAO.actualizarReservaEnSectores(repOrigen, secOrigen, repDestino, secDestino);

		if (logger.isDebugEnabled()) {
			logger.debug("actualizarReservaSector(String, String, String, String) - end");
		}
	}

	@Override
	public HashMap<String, ExpedienteElectronicoDTO> obtenerExpedienteElectronicoEnvioAutomaticoGT() {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerExpedienteElectronicoEnvioAutomaticoGT() - start");
		}

		HashMap<String, ExpedienteElectronicoDTO> returnHashMap = expedienteElectronicoDAO
				.obtenerExpedienteElectronicoEnvioAutomaticoGT();

		if (logger.isDebugEnabled()) {
			logger.debug("obtenerExpedienteElectronicoEnvioAutomaticoGT() - end - return value={}", returnHashMap);
		}

		return returnHashMap;
	}

	@Override
	public List<ExpedienteElectronicoDTO> obtenerExpedienteElectronicoConEstadoArchivo() {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerExpedienteElectronicoConEstadoArchivo() - start");
		}

		List<ExpedienteElectronicoDTO> returnList = expedienteElectronicoDAO
				.obtenerExpedienteElectronicoConEstadoArchivo();

		if (logger.isDebugEnabled()) {
			logger.debug("obtenerExpedienteElectronicoConEstadoArchivo() - end - return value={}", returnList);
		}

		return returnList;
	}

	public List<String> consultaExpedientesPorSistemaOrigenReparticion(String sistemaOrigen, String reparticion)
			throws ParametroIncorrectoException {
		if (logger.isDebugEnabled()) {
			logger.debug("consultaExpedientesPorSistemaOrigenReparticion(sistemaOrigen={}, reparticion={}) - start",
					sistemaOrigen, reparticion);
		}

		List<String> returnList = this.expedienteElectronicoDAO
				.consultaExpedientesPorSistemaOrigenReparticion(sistemaOrigen, reparticion);

		if (logger.isDebugEnabled()) {
			logger.debug("consultaExpedientesPorSistemaOrigenReparticion(String, String) - end - return value={}",
					returnList);
		}

		return returnList;
	}

	public List<String> consultaExpedientesPorSistemaOrigenUsuario(String sistemaOrigen, String usuario)
			throws ParametroIncorrectoException {
		if (logger.isDebugEnabled()) {
			logger.debug("consultaExpedientesPorSistemaOrigenUsuario(sistemaOrigen={}, usuario={}) - start",
					sistemaOrigen, usuario);
		}

		List<String> returnList = this.expedienteElectronicoDAO
				.consultaExpedientesPorSistemaOrigenUsuario(sistemaOrigen, usuario);

		if (logger.isDebugEnabled()) {
			logger.debug("consultaExpedientesPorSistemaOrigenUsuario(String, String) - end - return value={}",
					returnList);
		}

		return returnList;
	}

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		ExpedienteElectronicoServiceImpl.applicationContext = applicationContext;
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public void ValidarLicenciaUsuarioOrigen(String nombre) throws ParametroIncorrectoException {
		if (logger.isDebugEnabled()) {
			logger.debug("ValidarLicenciaUsuarioOrigen(nombre={}) - start", nombre);
		}

		String usuarioApoderado = null;

		if (usuariosSADEService.licenciaActiva(nombre)) {
			usuarioApoderado = this.validarLicencia(nombre.toUpperCase());
		}

		if (usuarioApoderado != null) {
			throw new ParametroIncorrectoException(
					"El usuario seleccionado no puede generar el pase ya que está de licencia.", null);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("ValidarLicenciaUsuarioOrigen(String) - end");
		}
	}

	private String validarLicencia(String username) {
		if (logger.isDebugEnabled()) {
			logger.debug("validarLicencia(username={}) - start", username);
		}

		String apoderado = this.usuariosSADEService.getDatosUsuario(username).getApoderado();

		while (apoderado != null) {
			String usuario = this.usuariosSADEService.getDatosUsuario(apoderado).getApoderado();
			if (null != usuario) {
				apoderado = usuario;
			} else {
				if (logger.isDebugEnabled()) {
					logger.debug("validarLicencia(String) - end - return value={}", apoderado);
				}
				return apoderado;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("validarLicencia(String) - end - return value={null}");
		}

		return null;
	}

	public Boolean getConsultaBd() {
		return consultaBd;
	}

	public void setConsultaBd(Boolean consultaBd) {
		this.consultaBd = consultaBd;
	}

	public String getMotivoParaleloAnterior() {
		return motivoParaleloAnterior;
	}

	public void setMotivoParaleloAnterior(String motivoParaleloAnterior) {
		this.motivoParaleloAnterior = motivoParaleloAnterior;
	}

	public String getSistemaSolicitanteDeAdjuntarDocAEEGuardaTemporal() {
		return sistemaSolicitanteDeAdjuntarDocAEEGuardaTemporal;
	}

	public void setSistemaSolicitanteDeAdjuntarDocAEEGuardaTemporal(
			String sistemaSolicitanteDeAdjuntarDocAEEGuardaTemporal) {
		this.sistemaSolicitanteDeAdjuntarDocAEEGuardaTemporal = sistemaSolicitanteDeAdjuntarDocAEEGuardaTemporal;
	}


}
