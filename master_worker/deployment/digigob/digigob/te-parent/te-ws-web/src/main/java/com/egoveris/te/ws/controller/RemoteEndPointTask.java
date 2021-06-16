package com.egoveris.te.ws.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.impl.InputElement;

import com.egoveris.deo.model.model.ProductionEnum;
import com.egoveris.deo.model.model.RequestExternalGenerarDocumento;
import com.egoveris.deo.model.model.ResponseExternalGenerarDocumento;
import com.egoveris.deo.model.model.ResponseTipoDocumento;
import com.egoveris.deo.ws.service.IExternalGenerarDocumentoService;
import com.egoveris.deo.ws.service.IExternalTipoDocumentoService;
import com.egoveris.ffdd.render.service.IFormManager;
import com.egoveris.ffdd.render.service.IFormManagerFactory;
import com.egoveris.ffdd.render.zk.comp.ext.ComboboxExt;
import com.egoveris.ffdd.render.zk.comp.ext.DateboxExt;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.te.base.model.AddDocumentoResponseDTO;
import com.egoveris.te.base.model.AdvanceTaskAppDTO;
import com.egoveris.te.base.model.CopyTaskAppDTO;
import com.egoveris.te.base.model.DocumentUnqtAppDTO;
import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.SaveFormRequestAppDTO;
import com.egoveris.te.base.model.TareaAppDTO;
import com.egoveris.te.base.model.TaskDocUserDTO;
import com.egoveris.te.base.model.TaskResponseAppDTO;
import com.egoveris.te.base.model.TaskUserAppDTO;
import com.egoveris.te.base.model.TipoDocumentoDTO;
import com.egoveris.te.base.model.rest.DocumentImportedTypesWsDTO;
import com.egoveris.te.base.model.rest.DocumentTypesResponse;
import com.egoveris.te.base.model.rest.FormWsDTO;
import com.egoveris.te.base.service.DocumentoGedoService;
import com.egoveris.te.base.service.RestAddDocumentService;
import com.egoveris.te.base.service.RestTaskService;
import com.egoveris.te.base.service.UsuariosSADEService;
import com.egoveris.te.base.service.WorkFlowService;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.service.iface.IExpedienteFormularioService;
import com.egoveris.te.base.service.iface.IGenerarExpedienteService;
import com.egoveris.te.base.service.iface.ITaskViewService;
import com.egoveris.te.base.util.ConstantesCommon;
import com.egoveris.te.model.model.CaratulacionExpedienteRequest;
import com.egoveris.te.model.model.ExpedienteFormularioDTO;
import com.egoveris.te.model.model.Status;
import com.egoveris.te.model.model.ValidateUser;

@RestController
@RequestMapping("/api/task")
public class RemoteEndPointTask {

	private Log logger = LogFactory.getLog(RemoteEndPointTask.class);

	@Autowired
	private ITaskViewService iTaskView;

	@Autowired
	private RestTaskService restTaskService;

	@Autowired
	private RestAddDocumentService getDocumentService;

	@Autowired
	private IExternalGenerarDocumentoService externalGenerarDocumentoService;

	@Autowired
	private IExternalTipoDocumentoService externalTipoDocumentoService;

	@Autowired
	private IGenerarExpedienteService generarExpedienteServiceImpl;

	@Autowired
	private ExpedienteElectronicoService expedienteService;

	@Autowired
	private DocumentoGedoService documentoGedoService;

	@Autowired
	private UsuariosSADEService usuariosSADEService;

	@Autowired
	private WorkFlowService workflowService;

	@Autowired
	private IExpedienteFormularioService expedienteFormularioService;

	@Autowired
	private IFormManagerFactory<IFormManager<Component>> formManagerFactory;

	private boolean sinResultado = false;

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/getTask", method = RequestMethod.POST)
	public TaskResponseAppDTO getTask(@RequestParam("user") String user, @RequestParam("organism") String organism,
			@RequestParam("idSector") String idSector, @RequestParam("taskCode") String taskCode,
			@RequestParam("taskStatus") String taskStatus, @RequestParam("myTask") boolean myTask,
			@RequestParam("grupalBox") boolean grupalBox) {

		TaskResponseAppDTO out = new TaskResponseAppDTO();
		// listTask
		List<TareaAppDTO> listTask = null;
		// instance status
		Status status = new Status();
		// filtros query
		Map<String, Object> filtrosQuery = new HashMap<>();
		// validacion
		if ((!grupalBox && myTask) || (grupalBox && !myTask)) {
			// add user
			filtrosQuery.put("user", user);
			// add organism
			filtrosQuery.put("organism", organism);
			// add idSector
			filtrosQuery.put("idSector", idSector);
			// add taskCode
			filtrosQuery.put("taskCode", taskCode);
			// add taskStatus
			filtrosQuery.put("taskStatus", taskStatus);
			// add grupal o task
			filtrosQuery.put("grupal", grupalBox ? true : false);
			try {
				// query (limited to first 10 results)
				listTask = (List<TareaAppDTO>) iTaskView.buscarAllTareasPorUsuario(filtrosQuery, 10);
			} catch (Exception e) {
				logger.debug("Error in buscarAllTareasPorUsuario(): ", e);
				// code 3 = Error
				status.setCode(3);
				status.setDesc(Status.ERROR);
			}
			// listTask != null
			if (null != listTask && !listTask.isEmpty()) {
				// add out
				out.setTask(listTask);
				// code 1 = OK
				status.setCode(1);
				status.setDesc(Status.OK);
			} else {
				// code 2 = NOK
				status.setCode(2);
				status.setDesc(Status.NOK);
			}
			// status
			out.setStatus(status);
		} else {
			// code 2 = NOK
			status.setCode(2);
			status.setDesc(Status.NOK);
			// status
			out.setStatus(status);
		}
		// out
		return out;
	}

	@RequestMapping(value = "/advance", method = RequestMethod.POST)
	public AdvanceTaskAppDTO advanceTask(@RequestBody TaskUserAppDTO taskUser) {
		AdvanceTaskAppDTO advanceTaskResult = new AdvanceTaskAppDTO();
		Status status = null;

		ExpedienteElectronicoDTO expediente;

		if (taskUser != null && taskUser.getTask() != null && taskUser.getUser() != null) {
			try {
				if (taskUser.getTask().getNextStatus().equalsIgnoreCase(ConstantesCommon.ESTADO_GUARDA_TEMPORAL)
						&& null == taskUser.getTask().getTaskResult()) {
					sinResultado = true;
				} else {
					restTaskService.advanceTaskState(taskUser);

					status = new Status(Status.OK);

					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dd-mm HH:mm:ss");
					taskUser.getTask().setLastModificationDate(sdf.format(new Date()));

					advanceTaskResult.setTask(taskUser.getTask());
					advanceTaskResult.setDestinyUser(taskUser.getUser().getIdUser());
				}

			} catch (Exception e) {
				logger.debug("Error in buscarExpedienteElectronicoByIdTask(): ", e);
				status = new Status(Status.ERROR);
			}
		}
		if (status == null) {
			status = new Status(Status.NOK);
			if (sinResultado) {
				status.setMotivo("El resultado no puede ser vacío, La tarea tiene está en estado: "
						+ taskUser.getTask().getNextStatus());
			}
		}
		advanceTaskResult.setStatus(status);

		return advanceTaskResult;
	}

	@RequestMapping(value = "/getDocument", method = RequestMethod.GET)
	public DocumentUnqtAppDTO getDocumentTask(@RequestParam("user") String user,
			@RequestParam("numeroSade") String numeroSade) {
		// instance out
		DocumentUnqtAppDTO out = new DocumentUnqtAppDTO();

		try {
			// getDocumento
			out = iTaskView.buscarDocument(user, numeroSade);
			out.setStatus(new Status(Status.OK));
			// validation != null
			if (null == out.getData()) {
				// error = 2
				out.setStatus(new Status(Status.NOK));
			}
		} catch (Exception e) {
			logger.debug("Error in buscarDocumentoByNumeroSade(): ", e);
			out.setStatus(new Status(Status.ERROR));
		}

		// validation no error
		if (out.getStatus() == null) {
			out.setStatus(new Status(Status.NOK));
		}

		// return
		return out;
	}

	@RequestMapping(value = "/addDocument", method = RequestMethod.POST)
	public AddDocumentoResponseDTO addDocument(@RequestBody TaskDocUserDTO taskDocUser) {

		ExpedienteElectronicoDTO expedienteElectronico = new ExpedienteElectronicoDTO();
		// // instance out
		AddDocumentoResponseDTO out = new AddDocumentoResponseDTO();
		try {
			// /Se busca el expediente electronico mediante el "taskCode"
			expedienteElectronico = expedienteService.buscarExpedienteElectronicoByIdTask(taskDocUser.getTaskCode());

			if (null != expedienteElectronico) {

				if (null != taskDocUser.getDataCode()) {

					byte[] dataCode = DatatypeConverter.parseBase64Binary(taskDocUser.getDataCode());

					ResponseTipoDocumento tipoDocumento = externalTipoDocumentoService
							.consultarTipoDocumentoPorAcronimo(taskDocUser.getDocumentImportedTypes().getAcronym());

					TipoDocumentoDTO tipodocumentoDTO = new TipoDocumentoDTO(tipoDocumento);

					Usuario usuario = usuariosSADEService.getDatosUsuario(taskDocUser.getUsuario());

					DocumentoDTO doc = documentoGedoService.generarPeticionGeneracionDocumentoGEDO(
							expedienteElectronico, taskDocUser.getUsuario(), usuario, "Motivo", tipodocumentoDTO);

					RequestExternalGenerarDocumento RequestGenerarDocumento = new RequestExternalGenerarDocumento();
					RequestGenerarDocumento
							.setAcronimoTipoDocumento(taskDocUser.getDocumentImportedTypes().getAcronym());
					RequestGenerarDocumento.setUsuario(taskDocUser.getUsuario());
					RequestGenerarDocumento.setData(dataCode);
					RequestGenerarDocumento.setSistemaOrigen("TE");
					RequestGenerarDocumento.setReferencia(taskDocUser.getObservacion());
					RequestGenerarDocumento.setTipoArchivo(taskDocUser.getDocumentImportedTypes().getAcronym());

					ResponseExternalGenerarDocumento responseExternalGenerarDocumento = externalGenerarDocumentoService
							.generarDocumentoGEDO(RequestGenerarDocumento);

					// Se agrega el documento al expediente electronico
					// encontrado
					expedienteService.vincularDocumentoGEDO(expedienteElectronico,
							responseExternalGenerarDocumento.getNumero(), taskDocUser.getUsuario());
					out.setStatus(new Status(Status.OK));
				}
			}else{
				logger.error("No existe el task "+taskDocUser.getTaskCode());
				out.setStatus(new Status(Status.NOK));
				out.getStatus().setDesc("No existe el taskCode "+taskDocUser.getTaskCode());
			}
				

		} catch (Exception e) {
			logger.error("Error in buscarDocumentoByNumeroSade(): ", e);
			out.setStatus(new Status(Status.ERROR));
		}

		return out;
	}

	@RequestMapping(value = "/copyTask", method = RequestMethod.POST)
	public CopyTaskAppDTO copyTask(@RequestBody TaskUserAppDTO taskUser) {
		String generatedEE = null;

		// instance status
		Status status = new Status(Status.OK);
		TareaAppDTO task = taskUser.getTask();
		ValidateUser user = taskUser.getUser();

		try {
			ExpedienteElectronicoDTO expediente = expedienteService
					.buscarExpedienteElectronicoByIdTask(task.getTaskCode());
			Map<String, Object> mapVars = workflowService.getVariables(task.getTaskCode());
			CaratulacionExpedienteRequest caratulacionRequest = new CaratulacionExpedienteRequest();

			String motivo = (mapVars != null && mapVars.containsKey(ConstantesCommon.MOTIVO))
					? mapVars.get(ConstantesCommon.MOTIVO).toString() : "Copy of Task";

			String descripcion = (expediente.getDescripcion() != null
					&& !StringUtils.isBlank(expediente.getDescripcion())) ? expediente.getDescripcion()
							: "Copy of Task";

			caratulacionRequest.setLoggeduser(user.getIdUser());
			caratulacionRequest.setSistema("TE");
			caratulacionRequest.setTaskApp(true);
			caratulacionRequest.setInterno(true);
			caratulacionRequest.setSelectTrataCod(expediente.getTrata().getCodigoTrata());
			caratulacionRequest.setDescripcion(descripcion);
			caratulacionRequest.setMotivo(motivo);
			generatedEE = generarExpedienteServiceImpl.generarExpedienteElectronicoCaratulacion(caratulacionRequest);

			if (null == generatedEE) {
				status = new Status(Status.NOK);
			}
		} catch (Exception e) {
			logger.debug("Error while copying task: " + e);
			status = new Status(Status.ERROR);
		}

		CopyTaskAppDTO out = new CopyTaskAppDTO();
		out.setStatus(status);
		out.setNewTaskCode(generatedEE);
		return out;
	}

	@RequestMapping(value = "/saveForm", method = RequestMethod.POST)
	public Status saveForm(@RequestBody SaveFormRequestAppDTO formTask) {
		Status status = null;

		ExpedienteElectronicoDTO expediente = null;
		List<ExpedienteFormularioDTO> listFormExpediente = null;
		ExpedienteFormularioDTO formExpediente = null;

		if (formTask != null && formTask.getFormInput() != null && formTask.getTask() != null) {
			try {
				// Obtain EE
				expediente = expedienteService.buscarExpedienteElectronicoByIdTask(formTask.getTask().getTaskCode());
			} catch (Exception e) {
				logger.debug("Error obtaning expedient of task, previous to saveForm: ", e);
				status = new Status(Status.ERROR);
			}
		}

		// With EE, obtain form list
		if (expediente != null) {
			ExpedienteFormularioDTO searchExpedienteFormDTO = new ExpedienteFormularioDTO();
			searchExpedienteFormDTO.setIdEeExpedient(expediente.getId());

			try {
				listFormExpediente = expedienteFormularioService
						.buscarFormulariosPorExpediente(searchExpedienteFormDTO);
			} catch (Exception e) {
				logger.debug("Error getting forms of expedient: ", e);
				status = new Status(Status.ERROR);
			}
		}

		// Obtain passed form ExpedienteFormularioDTO
		if (listFormExpediente != null) {
			for (ExpedienteFormularioDTO expedienteFormularioDTO : listFormExpediente) {
				// Match with input form
				if (expedienteFormularioDTO.getFormName() != null && formTask.getFormInput().getTitle() != null
						&& expedienteFormularioDTO.getFormName().equalsIgnoreCase(formTask.getFormInput().getTitle())) {
					formExpediente = expedienteFormularioDTO;
					break;
				}
			}
		}

		if (formExpediente != null) {
			try {
				// Get formManager
				IFormManager<Component> formManager = formManagerFactory.create(formExpediente.getFormName());
				formManager.fillCompValues(expediente.getId().intValue(), formExpediente.getIdDfTransaction());
				updateFormFields(formManager.getComponentsMap(), formTask.getFormInput().getForm());

				// Save values and save transaction
				Integer uuid = formManager.saveValues(expediente.getId().intValue());

				expedienteFormularioService.eliminarFormulario(formExpediente);
				formExpediente.setIdDfTransaction(uuid);
				expedienteFormularioService.guardarFormulario(formExpediente);

				status = new Status(Status.OK);
			} catch (Exception e) {
				logger.debug("Error while saving form: ", e);
				status = new Status(Status.ERROR);
			}
		}

		if (status == null) {
			status = new Status(Status.NOK);
		}

		return status;
	}

	@RequestMapping(value = "/getDocumentTypes", method = RequestMethod.GET)
	public DocumentTypesResponse getDocumentTypes() {
		DocumentTypesResponse retorno = new DocumentTypesResponse();
		try {
			List<ResponseTipoDocumento> lista = externalTipoDocumentoService
					.getDocumentTypeByProduction(ProductionEnum.IMPORT);
			List<DocumentImportedTypesWsDTO> types = new ArrayList<>();
			for (ResponseTipoDocumento responseTipoDocumento : lista) {
				types.add(new DocumentImportedTypesWsDTO(responseTipoDocumento.getAcronimo(),
						responseTipoDocumento.getDescripcion()));
			}
			retorno.setTypes(types);
			retorno.setStatus(new Status(Status.OK));
		} catch (Exception ex) {
			logger.error("error obteniendo lista de tipos de documentos REST", ex);
			retorno.setStatus(new Status(Status.ERROR));
		}
		return retorno;
	}

	// Private functions

	private void updateFormFields(Map<String, ?> formValues, List<FormWsDTO> componentList) {
		for (Map.Entry<String, ?> mapEntry : formValues.entrySet()) {
			for (FormWsDTO formWsDTO : componentList) {
				if (mapEntry.getKey().equals(formWsDTO.getKey())) {
					setValue(mapEntry.getValue(), formWsDTO.getReturnData());
				}
			}
		}
	}

	private void setValue(Object component, String value) {
		// Set value for date, value must be format
		if (component instanceof DateboxExt) {
			try {
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				Date date = sdf.parse(value);
				((DateboxExt) component).setRawValue(date);
			} catch (ParseException e) {
				((DateboxExt) component).setRawValue(null);
			}
		}
		// Set label for combobox, if element exists
		else if (component instanceof ComboboxExt) {
			ComboboxExt combobox = (ComboboxExt) component;

			if (combobox.getChildren() != null) {
				List<Comboitem> elements = combobox.getChildren();

				for (Comboitem element : elements) {
					if (element.getLabel().equalsIgnoreCase(value)) {
						combobox.setRawValue(element.getLabel());
					}
				}
			}
		}
		// Generic treatment for rest of elements
		else if (component instanceof InputElement) {
			InputElement inputElement = (InputElement) component;
			inputElement.setRawValue(value);
		}
	}

}
