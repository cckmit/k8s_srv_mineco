package com.egoveris.te.base.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jbpm.api.ProcessEngine;
import org.jbpm.pvm.internal.task.TaskImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.TaskUserAppDTO;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.service.expediente.constants.ConstantesPase;
import com.egoveris.te.base.util.ConstantesCommon;
import com.egoveris.te.model.exception.ProcesoFallidoException;

@Service
public class RestTaskServiceImpl implements RestTaskService {

	@Autowired
	private ProcessEngine processEngine;

	@Autowired
	private ExpedienteElectronicoService expedienteService;

	@Autowired
	private ControlTransaccionService controlTransaccionService;
	
	

	@Override
	public ExpedienteElectronicoDTO advanceTaskState(TaskUserAppDTO taskUser)
			throws ProcesoFallidoException, InterruptedException {
		// Searchs expedient and task from given taskCode
		ExpedienteElectronicoDTO expediente;
		TaskImpl task;

		expediente = expedienteService.buscarExpedienteElectronicoByIdTask(taskUser.getTask().getTaskCode());
		task = (TaskImpl) processEngine.getTaskService().createTaskQuery().executionId(taskUser.getTask().getTaskCode())
				.uniqueResult();
		Map<String, String> detalles = new HashMap<>();
		if (taskUser.getTask().getGeoLocationInfo() != null
				&& !StringUtils.isEmpty(taskUser.getTask().getGeoLocationInfo().getLatitude())) {
			detalles.put(ConstantesPase.LATITUDE, taskUser.getTask().getGeoLocationInfo().getLatitude());
			detalles.put(ConstantesPase.LONGITUDE, taskUser.getTask().getGeoLocationInfo().getLongitude());
		}
		detalles.put("usuarioSeleccionado",taskUser.getUser().getIdUser());
		this.controlTransaccionService.save(processEngine, task, expediente, taskUser.getUser().getIdUser(),
				taskUser.getTask().getNextStatus(), taskUser.getTask().getReason(), detalles);

		return expediente;
	}

	// PRIVATE FUNCTIONS
	private Map<String, Object> initAndGetVarsMap(String username, ExpedienteElectronicoDTO expediente) {
		Map<String, Object> variables = new HashMap<>();
		variables.put(ConstantesCommon.TAREA_GRUPAL, "noEsTareaGrupal");
		variables.put(ConstantesCommon.USUARIO_ANTERIOR, username);
		variables.put(ConstantesCommon.USUARIO_SELECCIONADO, username);
		variables.put(ConstantesCommon.USUARIO_CANDIDATO, username);
		variables.put(ConstantesCommon.ID_EXPEDIENTE_ELECTRONICO, expediente.getId());
		variables.put(ConstantesCommon.CODIGO_EXPEDIENTE, expediente.getCodigoCaratula());
		variables.put(ConstantesCommon.CODIGO_TRATA, expediente.getTrata().getCodigoTrata());
		variables.put(ConstantesCommon.DESCRIPCION, expediente.getDescripcion());
		variables.put(ConstantesCommon.TIPO_DOCUMENTO, expediente.getTipoDocumento());

		return variables;
	}

}
