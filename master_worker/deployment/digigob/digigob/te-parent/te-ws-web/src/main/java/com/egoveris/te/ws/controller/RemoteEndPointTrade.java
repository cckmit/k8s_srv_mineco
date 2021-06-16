package com.egoveris.te.ws.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.impl.InputElement;

import com.egoveris.ccomplejos.base.model.OperationDTO;
import com.egoveris.deo.ws.service.IExternalGenerarDocumentoService;
import com.egoveris.deo.ws.service.IExternalTipoDocumentoService;
import com.egoveris.ffdd.render.service.IFormManager;
import com.egoveris.ffdd.render.service.IFormManagerFactory;
import com.egoveris.ffdd.render.zk.comp.ext.ComboboxExt;
import com.egoveris.ffdd.render.zk.comp.ext.DateboxExt;
import com.egoveris.te.base.model.OperacionDTO;
import com.egoveris.te.base.model.TareaAppDTO;
import com.egoveris.te.base.model.TaskResponseAppDTO;
import com.egoveris.te.base.model.rest.FormWsDTO;
import com.egoveris.te.base.service.DocumentoGedoService;
import com.egoveris.te.base.service.OperacionService;
import com.egoveris.te.base.service.RestAddDocumentService;
import com.egoveris.te.base.service.RestTaskService;
import com.egoveris.te.base.service.SubprocessService;
import com.egoveris.te.base.service.UsuariosSADEService;
import com.egoveris.te.base.service.WorkFlowService;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.service.iface.IExpedienteFormularioService;
import com.egoveris.te.base.service.iface.IGenerarExpedienteService;
import com.egoveris.te.base.service.iface.ITaskViewService;
import com.egoveris.te.model.model.Status;

@RestController
@RequestMapping("/api/trade")
public class RemoteEndPointTrade {

	private Log logger = LogFactory.getLog(RemoteEndPointTrade.class);

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
	private OperacionService operacionService;
	
	@Autowired
	private SubprocessService subprocessService;
	

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
