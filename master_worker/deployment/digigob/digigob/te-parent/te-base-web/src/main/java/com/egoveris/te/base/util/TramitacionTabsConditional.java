package com.egoveris.te.base.util;

import com.egoveris.te.base.model.ExpedienteElectronicoDTO;

import org.jbpm.api.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Executions;

public class TramitacionTabsConditional {

  final static Logger logger = LoggerFactory.getLogger(TramitacionTabsConditional.class);

  private ExpedienteElectronicoDTO expedienteElectronico;
  private Task workingTask;

  private Boolean isHabilitarTabTC = new Boolean(true);
  private Boolean isHabilitarTabFusion = new Boolean(true);

  private Boolean isTramitacionConjunta = new Boolean(false);
  private Boolean isFunsion = new Boolean(false);

  public static final String ESTADO_TRAMITACION = "Tramitacion";
  public static final String ESTADO_EJECUCION = "Ejecucion";

  public TramitacionTabsConditional() {
  }

  /**
   * Constructor
   * 
   * @param <code>TramitacionComposer</code>tramitacionComposer
   * @param <code>org.jbpm.api.task.Task</code>workingTask
   */
  public TramitacionTabsConditional(Task workingTask) {
    this.workingTask = workingTask;
    this.expedienteElectronico = expedienteElectronico;
  }

  /**
   * Evalúa que se cumpla un businessRule.
   * 
   * @param <code>java.lang.Object</code>arg0
   * @return <code>java.lang.Boolean</code> TRUE, si se cumple el businessRule
   */
  public TramitacionTabsConditional condition(Object arg0) {
    this.expedienteElectronico = (ExpedienteElectronicoDTO) arg0;

    Boolean fusionador = (Boolean) Executions.getCurrent().getDesktop().getSession()
        .getAttribute(ConstantesWeb.SESSION_FUSIONADOR) == null ? false : true;
    Boolean permiso = false;

    if (fusionador) {
      permiso = true;
    } else { // no es usuario fusionador,

      if (this.expedienteElectronico.existenExpedientesDefinitivos()) { // pero
                                                                        // el
                                                                        // expedinte
                                                                        // ya
                                                                        // cuenta
                                                                        // con
                                                                        // una
        // fusión confirmada.
        permiso = true;
      } else {
        permiso = false;
      }
    }

    if ((this.workingTask.getActivityName().equals(ESTADO_TRAMITACION)
        || this.workingTask.getActivityName().equals(ESTADO_EJECUCION))) {
      this.isHabilitarTabFusion = permiso;
      this.isHabilitarTabTC = true;

      if (this.expedienteElectronico.existenExpedientesFusionados()) {
        this.isHabilitarTabFusion = permiso;
        this.isHabilitarTabTC = false;
      } else if (this.expedienteElectronico.existenExpedientesTramitacion()) {
        this.isHabilitarTabFusion = false;
        this.isHabilitarTabTC = true;
      }
    }

    return this;
  }

  public Boolean getIsTramitacionConjunta() {
    StringBuffer sb = new StringBuffer();
    sb.append("[ThreadId=" + Thread.currentThread().getId() + "], toString="
        + this.expedienteElectronico.toString() + ", \n");
    sb.append(", Trata.toString=" + this.expedienteElectronico.getTrata().toString() + ", \n");
    sb.append(", isHabilitarTabTC=" + this.isHabilitarTabTC + ", \n");
    sb.append(", isTramitacionConjunta=" + this.isTramitacionConjunta + ", \n");
    logger.debug(sb.toString());
    return this.isTramitacionConjunta;
  }

  public void setIsTramitacionConjunta(Boolean isTramitacionConjunta) {
    this.isTramitacionConjunta = isTramitacionConjunta;
  }

  public Boolean getIsFunsion() {
    StringBuffer sb = new StringBuffer();
    sb.append("[ThreadId=" + Thread.currentThread().getId() + "], toString="
        + this.expedienteElectronico.toString() + ", \n");
    sb.append(", Trata.toString=" + this.expedienteElectronico.getTrata().toString() + ", \n");
    sb.append(", isHabilitarTabTC=" + this.isHabilitarTabTC + ", \n");
    sb.append(", isFunsion=" + this.isFunsion + ", \n");
    logger.debug(sb.toString());
    return this.isFunsion;
  }

  public void setIsFunsion(Boolean isFunsion) {
    this.isFunsion = isFunsion;
  }

  public Boolean getIsHabilitarTabTC() {
    return this.isHabilitarTabTC;
  }

  public void setIsHabilitarTabTC(Boolean isHabilitarTabTC) {
    this.isHabilitarTabTC = isHabilitarTabTC;
  }

  public Boolean getIsHabilitarTabFusion() {
    return this.isHabilitarTabFusion;
  }

  public void setIsHabilitarTabFusion(Boolean isHabilitarTabFusion) {
    this.isHabilitarTabFusion = isHabilitarTabFusion;
  }
}
