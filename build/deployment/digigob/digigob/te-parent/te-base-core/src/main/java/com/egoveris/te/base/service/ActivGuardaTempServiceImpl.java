package com.egoveris.te.base.service;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.TaskQuery;
import org.jbpm.api.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egoveris.te.base.model.ActividadDTO;
import com.egoveris.te.base.model.ActividadInbox;
import com.egoveris.te.base.model.ActividadSolicGuardaTemp;
import com.egoveris.te.base.service.iface.IActivGuardaTempService;
import com.egoveris.te.base.util.ConstParamActividad;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.model.exception.ParametroIncorrectoException;

@Service
@Transactional
public class ActivGuardaTempServiceImpl implements IActivGuardaTempService {
  private static final Logger logger = LoggerFactory.getLogger(ActivGuardaTempServiceImpl.class);
  @Autowired
  private PaseExpedienteService paseExpedienteService;
  @Autowired
  private IActividadService actividadService;
  @Autowired
  private ProcessEngine processEngine;
//  @Autowired
//  private ExpedienteElectronicoService expedienteElectronicoService;

  private static final String ESTADO_GUARDA_TEMPORAL = "Guarda Temporal";
  private static final String ESTADO_CIERRE = "Cierre";

  @Override
  public ActividadSolicGuardaTemp buscarActividadSolicitudGuardaTemp(
      ActividadInbox actividadInbox) {
    if (logger.isDebugEnabled()) {
      logger.debug("buscarActividadSolicitudGuardaTemp(actividadInbox={}) - start",
          actividadInbox);
    }

    ActividadSolicGuardaTemp result = new ActividadSolicGuardaTemp();
    ActividadDTO act = actividadService.buscarActividad(actividadInbox.getId());

    result.setUsuarioAlta(act.getUsuarioAlta());
    result.setWorkFlowId(act.getIdObjetivo());
    result.setId(act.getId());

    if (act.getParametros().get(ConstParamActividad.PARAM_NRO_EXP) != null)
      result
          .setNroExpediente(act.getParametros().get(ConstParamActividad.PARAM_NRO_EXP).toString());

    if (act.getParametros().get(ConstParamActividad.PARAM_MOTIVO_TAD) != null)
      result.setMotivo(act.getParametros().get(ConstParamActividad.PARAM_MOTIVO_TAD).toString());

    if (act.getParametros().get(ConstParamActividad.PARAM_MOTIVO_EE) != null)
      result.setMotivo(act.getParametros().get(ConstParamActividad.PARAM_MOTIVO_EE).toString());

    if (act.getParametros().get(ConstParamActividad.PARAM_USER_MAIL) != null) {
      result
          .setMailDestino(act.getParametros().get(ConstParamActividad.PARAM_USER_MAIL).toString());
    }

    if (logger.isDebugEnabled()) {
      logger.debug("buscarActividadSolicitudGuardaTemp(ActividadInbox) - end - return value={}",
          result);
    }
    return result;
  }

  @Override
  public void aprobarPaseGuardaTemporal(ActividadSolicGuardaTemp solicitudGuardaTemp,
      String username) throws ParametroIncorrectoException {
    if (logger.isDebugEnabled()) {
      logger.debug("aprobarPaseGuardaTemporal(solicitudGuardaTemp={}, username={}) - start",
          solicitudGuardaTemp, username);
    }

    Task task = buscarTask(solicitudGuardaTemp.getWorkFlowId());
//    ExpedienteElectronicoDTO expedienteElectronico = expedienteElectronicoService
//        .obtenerExpedienteElectronicoPorCodigo(solicitudGuardaTemp.getNroExpediente());

    Map<String, String> detalles = new HashMap<String, String>();
   // detalles.put("estadoAnterior", expedienteElectronico.getEstado());
    detalles.put("estadoAnteriorParalelo", null);

//    this.paseExpedienteService.paseGuardaTemporal(expedienteElectronico, task, username, detalles,
//        "", solicitudGuardaTemp.getMotivo());

    setearVariablesAlWorkflow(task.getExecutionId(), detalles);

    // Avanza la tarea en el workflow
    processEngine.getExecutionService().signalExecutionById(task.getExecutionId(),
        ESTADO_GUARDA_TEMPORAL);
    // Vuelve a avanzar la tarea en el workflow para cerrar la misma.
    
    // 11-05-2020: No ejecutar el cierre, o desaparece la tarea
    //processEngine.getExecutionService().signalExecutionById(task.getExecutionId(), ESTADO_CIERRE);

    actividadService.aprobarActividad(solicitudGuardaTemp.getId(), username);

    if (logger.isDebugEnabled()) {
      logger.debug("aprobarPaseGuardaTemporal(ActividadSolicGuardaTemp, String) - end");
    }
  }

  private Task buscarTask(String executionId) {
    if (logger.isDebugEnabled()) {
      logger.debug("buscarTask(executionId={}) - start", executionId);
    }

    TaskQuery tq = processEngine.getTaskService().createTaskQuery();
    tq.executionId(executionId);
    Task returnTask = tq.uniqueResult();
    if (logger.isDebugEnabled()) {
      logger.debug("buscarTask(String) - end - return value={}", returnTask);
    }
    return returnTask;
  }

  private void setearVariablesAlWorkflow(String workFlowId, Map<String, String> detalles) {
    if (logger.isDebugEnabled()) {
      logger.debug("setearVariablesAlWorkflow(workFlowId={}, detalles={}) - start", workFlowId,
          detalles);
    }

    Map<String, Object> mapVariables = new HashMap<String, Object>();

    mapVariables.put(ConstantesWeb.ESTADO_ANTERIOR, detalles.get(ConstantesWeb.ESTADO_ANTERIOR));

    mapVariables.put(ConstantesWeb.ESTADO_ANTERIOR_PARALELO,
        detalles.get(ConstantesWeb.ESTADO_ANTERIOR_PARALELO));

    mapVariables.put(ConstantesWeb.TAREA_GRUPAL, detalles.get(ConstantesWeb.TAREA_GRUPAL));

    mapVariables.put(ConstantesWeb.ID_EXPEDIENTE_ELECTRONICO,
        Integer.parseInt(detalles.get(ConstantesWeb.ID_EXPEDIENTE_ELECTRONICO)));

    if (StringUtils.isEmpty((String) detalles.get(ConstantesWeb.GRUPO_SELECCIONADO))) {
      mapVariables.put(ConstantesWeb.GRUPO_SELECCIONADO, null);
    } else {
      mapVariables.put(ConstantesWeb.GRUPO_SELECCIONADO,
          mapVariables.get(ConstantesWeb.GRUPO_SELECCIONADO));
    }
    if (StringUtils.isEmpty((String) mapVariables.get(ConstantesWeb.USUARIO_SELECCIONADO))) {
      mapVariables.put(ConstantesWeb.USUARIO_SELECCIONADO, null);
    } else {
      mapVariables.put(ConstantesWeb.USUARIO_SELECCIONADO,
          mapVariables.get(ConstantesWeb.USUARIO_SELECCIONADO));
    }

    this.processEngine.getExecutionService().setVariables(workFlowId, mapVariables);

    if (logger.isDebugEnabled()) {
      logger.debug("setearVariablesAlWorkflow(String, Map<String,String>) - end");
    }
  }

  @Override
  public void rechazarPaseGuardaTemporal(ActividadSolicGuardaTemp solicitud,
      String loggedUsername) {
    if (logger.isDebugEnabled()) {
      logger.debug("rechazarPaseGuardaTemporal(solicitud={}, loggedUsername={}) - start",
          solicitud, loggedUsername);
    }

    actividadService.rechazarActividad(solicitud.getId(), loggedUsername);

    if (logger.isDebugEnabled()) {
      logger.debug("rechazarPaseGuardaTemporal(ActividadSolicGuardaTemp, String) - end");
    }
  }

  public PaseExpedienteService getPaseExpedienteService() {
    return paseExpedienteService;
  }

  public void setPaseExpedienteService(PaseExpedienteService paseExpedienteService) {
    this.paseExpedienteService = paseExpedienteService;
  }

  public IActividadService getActividadService() {
    return actividadService;
  }

  public void setActividadService(IActividadService actividadService) {
    this.actividadService = actividadService;
  }

  public ProcessEngine getProcessEngine() {
    return processEngine;
  }

  public void setProcessEngine(ProcessEngine processEngine) {
    this.processEngine = processEngine;
  }

//  public ExpedienteElectronicoService getExpedienteElectronicoService() {
//    return expedienteElectronicoService;
//  }
//
//  public void setExpedienteElectronicoService(
//      ExpedienteElectronicoService expedienteElectronicoService) {
//    this.expedienteElectronicoService = expedienteElectronicoService;
//  }
}