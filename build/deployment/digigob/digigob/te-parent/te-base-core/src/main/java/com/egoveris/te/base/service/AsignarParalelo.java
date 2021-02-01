package com.egoveris.te.base.service;

import javax.transaction.Transactional;

import org.jbpm.api.model.OpenExecution;
import org.jbpm.api.task.Assignable;
import org.jbpm.api.task.AssignmentHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.TareaParaleloDTO;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;

@Service
@Transactional
@SuppressWarnings("serial")
public class AsignarParalelo implements AssignmentHandler {
  private static final Logger logger = LoggerFactory.getLogger(AsignarParalelo.class);

  @Autowired
  private TareaParaleloService tareaParaleloService;
  @Autowired
  private UsuariosSADEService usuariosSADEService;
  @Autowired
  private ExpedienteElectronicoService expedienteElectronicoService;

  public void assign(Assignable assignable, OpenExecution execution) throws Exception {
    if (logger.isDebugEnabled()) {
      logger.debug("assign(assignable={}, execution={}) - start", assignable, execution);
    }

    String asignado = (String) execution.getVariable("asignado");
    ExpedienteElectronicoDTO expedienteElectronico;

    Long idExpedienteElectronico = (Long) execution.getVariable("idExpedienteElectronico");
    if (idExpedienteElectronico != null) {
      expedienteElectronico = expedienteElectronicoService
          .buscarExpedienteElectronico(idExpedienteElectronico);

    } else {
      expedienteElectronico = (ExpedienteElectronicoDTO) execution
          .getVariable("expedienteElectronico");
      execution.setVariable("expedienteElectronico", null);
      execution.setVariable("idExpedienteElectronico", expedienteElectronico.getId());
    }

    Usuario datosUsuario = usuariosSADEService.getDatosUsuario(asignado);
    TareaParaleloDTO tareaParalelo = this.tareaParaleloService
        .buscarTareaEnParaleloByExpedienteYDestino(expedienteElectronico.getId(), asignado);

    if (datosUsuario != null) {
      assignable.setAssignee(asignado);
      tareaParalelo.setUsuarioGrupoDestino(asignado);
      tareaParalelo.setTareaGrupal(false);
    } else {
      assignable.addCandidateGroup(asignado);
      tareaParalelo.setUsuarioGrupoDestino(asignado);
      tareaParalelo.setTareaGrupal(true);
    }

    tareaParalelo.setIdTask(execution.getId());
    this.tareaParaleloService.modificar(tareaParalelo);

    if (logger.isDebugEnabled()) {
      logger.debug("assign(Assignable, OpenExecution) - end");
    }
  }

  public TareaParaleloService getTareaParaleloService() {
    return tareaParaleloService;
  }

  public void setTareaParaleloService(TareaParaleloService tareaParaleloService) {
    this.tareaParaleloService = tareaParaleloService;
  }

  public UsuariosSADEService getUsuariosSADEService() {
    return usuariosSADEService;
  }

  public void setUsuariosSADEService(UsuariosSADEService usuariosSADEService) {
    this.usuariosSADEService = usuariosSADEService;
  }

  public ExpedienteElectronicoService getExpedienteElectronicoService() {
    return expedienteElectronicoService;
  }

  public void setExpedienteElectronicoService(
      ExpedienteElectronicoService expedienteElectronicoService) {
    this.expedienteElectronicoService = expedienteElectronicoService;
  }
}