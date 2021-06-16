package com.egoveris.te.base.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.TaskQuery;
import org.jbpm.api.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egoveris.deo.util.Constantes;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.service.iface.IRehabilitarExpedienteService;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.model.exception.ExpedienteNoDisponibleException;
import com.egoveris.te.model.exception.ParametroIncorrectoException;
import com.egoveris.te.model.exception.ProcesoFallidoException;
import com.egoveris.te.model.model.RehabilitacionExpedienteRequest;

@Service
// Comentado por problemas con transaccionalidad al rehabilitar
//@Transactional
public class RehabilitarExpedienteServiceImpl implements IRehabilitarExpedienteService {
  private static final Logger logger = LoggerFactory
      .getLogger(RehabilitarExpedienteServiceImpl.class);

  @Autowired
  ValidaUsuarioExpedientesService validacionUsuario;

  @Autowired
  ExpedienteElectronicoService expedienteElectronicoService;
  
	@Autowired
	private ProcessEngine processEngine;

  @Override
  public void rehabilitarExpediente(RehabilitacionExpedienteRequest datosExpediente)
      throws ProcesoFallidoException, ParametroIncorrectoException,
      ExpedienteNoDisponibleException {
    if (logger.isDebugEnabled()) {
      logger.debug("rehabilitarExpediente(datosExpediente={}) - start", datosExpediente);
    }

    ExpedienteElectronicoDTO e = new ExpedienteElectronicoDTO();
    
    try {
      e = expedienteElectronicoService
          .obtenerExpedienteElectronicoPorCodigo(datosExpediente.getCodExpediente());
    } catch (Exception e1) {
      logger.error("rehabilitarExpediente(RehabilitacionExpedienteRequest)", e1);

      throw new ExpedienteNoDisponibleException("No se ha encontrado el Expediente Solicitado",
          e1);
    }

    if (e != null && !e.getEstado().equals(ConstantesWeb.ESTADO_GUARDA_TEMPORAL)) {
      throw new ExpedienteNoDisponibleException("El expediente no se encuentra en Guarda temporal",
          null);
    }
    try {
      if (!validacionUsuario.validaUsuarioExpedientes(datosExpediente.getLoggeduser())) {

      }
    } catch (InterruptedException i) {
      logger.error("rehabilitarExpediente(RehabilitacionExpedienteRequest)", i);
      Thread.currentThread().interrupt();
      throw new ParametroIncorrectoException(
          "El usuario " + datosExpediente.getLoggeduser() + "  no es un usuario Valido de CCOO",
          i);
    }

    try {
      if (!validacionUsuario.validaUsuarioExpedientes(datosExpediente.getDestinatario())) {

      }
    } catch (InterruptedException i) {
      logger.error("rehabilitarExpediente(RehabilitacionExpedienteRequest)", i);
      Thread.currentThread().interrupt();
      throw new ParametroIncorrectoException(
          "El usuario " + datosExpediente.getDestinatario() + "  no es un usuario Valido de CCOO",
          i);
    }

    if (!this.desarchivarExpediente(e, datosExpediente.getLoggeduser(),
        datosExpediente.getDestinatario(), ConstantesWeb.ESTADO_TRAMITACION_PARA_REHABILITACION,
        datosExpediente.getMotivo(), null)) {
      throw new ProcesoFallidoException("Error desconocido al Desarchivar en EE", null);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("rehabilitarExpediente(RehabilitacionExpedienteRequest) - end");
    }
  }
  
	/**
	 * Servicio que desarchiva el expediente, devuelve true en caso de que lo haya
	 * desarchivado exitosamente, y retorna false en caso de que haya pasado algo.
	 */
	public Boolean desarchivarExpediente(ExpedienteElectronicoDTO expedienteElectronicoDTO, String loggedUsername,
			String destinatario, String estado, String motivoExpediente, Map<String, String> detalle) {
		if (logger.isDebugEnabled()) {
			logger.debug(
					"desarchivarExpediente(expedienteElectronico={}, loggedUsername={}, destinatario={}, estado={}, motivoExpediente={}) - start",
					expedienteElectronicoDTO, loggedUsername, destinatario, estado, motivoExpediente);
		}

		boolean retorno = true;
		
		if (detalle == null) {
			detalle = new HashMap<>();
		}
		
		destinatario = detalle.containsKey(ConstantesWeb.DESTINATARIO) ? detalle.get(ConstantesWeb.DESTINATARIO) : destinatario;
		detalle.put(ConstantesWeb.DESTINATARIO, destinatario);
		
		Map<String, Object> variables = new HashMap<>();
		variables.put(ConstantesWeb.ID_SOLUCITUD, expedienteElectronicoDTO.getSolicitudIniciadora().getId());
		variables.put(ConstantesWeb.MOTIVO, expedienteElectronicoService.formatoMotivo(motivoExpediente));
		variables.put(ConstantesWeb.USUARIO_SOLICITANTE, loggedUsername);
		variables.put(ConstantesWeb.ID_EXPEDIENTE_ELECTRONICO, expedienteElectronicoDTO.getId());
		variables.put(ConstantesWeb.CODIGO_EXPEDIENTE, expedienteElectronicoDTO.getCodigoCaratula());
		variables.put(ConstantesWeb.CODIGO_TRATA, expedienteElectronicoDTO.getTrata().getCodigoTrata());
		variables.put(ConstantesWeb.DESCRIPCION, "Rehabilitación");
		variables.put(ConstantesWeb.USUARIO_ANTERIOR, loggedUsername);
		variables.put(ConstantesWeb.USUARIO_CANDIDATO, loggedUsername);
		//variables.put(ConstantesWeb.ESTADO, estado); // Se setea el primer estado en el pase
		variables.put(ConstantesWeb.DESTINATARIO, destinatario);
		variables.put(ConstantesWeb.INICIO, "Caratular");
		
		if (detalle.containsKey(ConstantesWeb.USUARIO_SELECCIONADO)) {
			variables.put(ConstantesWeb.USUARIO_SELECCIONADO, detalle.get(ConstantesWeb.USUARIO_SELECCIONADO));
		}
		
		if (detalle.containsKey(ConstantesWeb.GRUPO_SELECCIONADO)) {
			variables.put(ConstantesWeb.GRUPO_SELECCIONADO, detalle.get(ConstantesWeb.GRUPO_SELECCIONADO));
		}
		
		if (detalle.containsKey(ConstantesWeb.TAREA_GRUPAL)) {
			variables.put(ConstantesWeb.TAREA_GRUPAL, detalle.get(ConstantesWeb.TAREA_GRUPAL));
		}
		else {
			variables.put(ConstantesWeb.TAREA_GRUPAL, "noEsTareaGrupal");
		}

		try {
			// Revisa si el expediente tiene tarea JBPM y la elimina (cierre)
			// porque luego se generará una nueva (en esto consiste la rehabilitación)
			TaskQuery taskQueryOld = processEngine.getTaskService().createTaskQuery()
					.executionId(expedienteElectronicoDTO.getIdWorkflow());
			Task oldTask = taskQueryOld.uniqueResult();
			
			if (oldTask != null) {
				processEngine.getExecutionService().deleteProcessInstance(oldTask.getExecutionId());
			}
			
			ProcessInstance processInstance = processEngine.getExecutionService().startProcessInstanceByKey(expedienteElectronicoDTO.getTrata().getWorkflow(),
					variables);
			expedienteElectronicoDTO.setIdWorkflow(processInstance.getId());
			
			
			// Se realiza el update del Expediente Electronico por que se seteo
			// el campo idWorkflow.
			// Se Quitan los 0000 nuevamente del numero de expediente para
			// guardarlo en la base. -> No es asi (lo de 0000)

			TaskQuery taskQuery = processEngine.getTaskService().createTaskQuery().executionId(processInstance.getId());
			Task task = taskQuery.uniqueResult();
			
			asociarTaskConElDocumentoVinculado(expedienteElectronicoDTO, task);
			
			String estadoInicial = task.getActivityName();
			expedienteElectronicoDTO.setEstado(estadoInicial);
			
			expedienteElectronicoService.generarPaseExpedienteElectronico(task, expedienteElectronicoDTO,
					loggedUsername, estadoInicial, motivoExpediente, detalle);
			
			expedienteElectronicoService.modificarExpedienteElectronico(expedienteElectronicoDTO);

			String taskId = task.getId();
			String destValue = destinatario;
			
			if (!"noEsTareaGrupal".equals(variables.get(ConstantesWeb.TAREA_GRUPAL))) {
				destValue = null;
			}
			
			processEngine.getTaskService().assignTask(taskId, destValue);
		} catch (Exception a) {
			logger.error("desarchivarExpediente(ExpedienteElectronicoDTO, String, String, String, String)", a);

			retorno = false;
		}

		if (logger.isDebugEnabled()) {
			logger.debug(
					"desarchivarExpediente(ExpedienteElectronicoDTO, String, String, String, String) - end - return value={}",
					retorno);
		}

		return retorno;
	}

	private void asociarTaskConElDocumentoVinculado(ExpedienteElectronicoDTO expedienteElectronicoDTO, Task task) {
	 expedienteElectronicoDTO.getDocumentos()
	 .forEach(doc -> {
		 if(doc.getIdTask()!=null && doc.getIdTask().equals("rehabilitar")) {
			 doc.setDefinitivo(true);
			 doc.setIdTask(task.getExecutionId());
		 }
	 });
	}

}
