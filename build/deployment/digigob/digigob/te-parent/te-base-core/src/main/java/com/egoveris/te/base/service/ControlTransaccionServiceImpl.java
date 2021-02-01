package com.egoveris.te.base.service;

import java.util.Map;

import org.jbpm.api.ProcessEngine;
import org.jbpm.api.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.deo.model.exception.DataAccessLayerException;
import com.egoveris.te.base.model.BuzonBean;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.Grupo;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.util.ConstantesServicios;
import com.egoveris.te.base.util.WorkFlow;

@Service
@Transactional
public class ControlTransaccionServiceImpl
    implements ControlTransaccionService, ApplicationContextAware {
  private static final Logger logger = LoggerFactory
      .getLogger(ControlTransaccionServiceImpl.class);

  @Autowired
  private ExpedienteElectronicoService expedienteElectronicoService;
  @Autowired
  private ProcessEngine processEngineControl;

  @Autowired
  private WorkFlowService workFlowService;

  @Autowired
  private HistorialOperacionService historialOperacionService;

  public void setExpedienteElectronicoService(
      ExpedienteElectronicoService expedienteElectronicoService) {
    this.expedienteElectronicoService = expedienteElectronicoService;
  }

  public void setProcessEngineControl(ProcessEngine processEngineControl) {
    this.processEngineControl = processEngineControl;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public void save(final ProcessEngine processEngine, final Task workingTask,
      final ExpedienteElectronicoDTO expedienteElectronico, final String loggedUsername,
      final String estado, final String motivo, final Map<String, String> detalles)
      throws DataAccessLayerException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "save(processEngine={}, workingTask={}, expedienteElectronico={}, loggedUsername={}, estado={}, motivo={}, detalles={}) - start",
          processEngine, workingTask, expedienteElectronico, loggedUsername, estado, motivo,
          detalles);
    }

    expedienteElectronicoService.generarPaseExpedienteElectronico(workingTask,
        expedienteElectronico, loggedUsername, estado, motivo, detalles);
    WorkFlow workFlow = getWorkFlowService().createWorkFlow(expedienteElectronico.getId(), estado);
    workFlow.initParameters(detalles);
    try {
      workFlow.execute(workingTask, processEngine);
    } catch (InterruptedException e) {
        logger.error("error al ejecutar la workingTask", e);
    }

    this.historialOperacionService.guardarDatosEnHistorialOperacionSinUsuarioObject(
        expedienteElectronico, loggedUsername, detalles);

    if (logger.isDebugEnabled()) {
      logger.debug(
          "save(ProcessEngine, Task, ExpedienteElectronico, String, String, String, Map<String,String>) - end");
    }
  }

  private WorkFlowService getWorkFlowService() {
    if (logger.isDebugEnabled()) {
      logger.debug("getWorkFlowService() - start");
    }

    workFlowService = (WorkFlowService) getCtx().getBean(ConstantesServicios.WORKFLOW_SERVICE);

    if (logger.isDebugEnabled()) {
      logger.debug("getWorkFlowService() - end - return value={}", workFlowService);
    }
    return workFlowService;
  }

  @Override
  public void save(BuzonBean buzonBean, Map<String, Grupo> roundRobin) throws DataAccessLayerException {

  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.ctx = applicationContext;
  }

  /**
   * @return the ctx
   */
  public ApplicationContext getCtx() {
    return ctx;
  }

  public void setHistorialOperacionService(HistorialOperacionService historialOperacionService) {
    this.historialOperacionService = historialOperacionService;
  }

  public HistorialOperacionService getHistorialOperacionService() {
    return historialOperacionService;
  }

  private ApplicationContext ctx;

}
