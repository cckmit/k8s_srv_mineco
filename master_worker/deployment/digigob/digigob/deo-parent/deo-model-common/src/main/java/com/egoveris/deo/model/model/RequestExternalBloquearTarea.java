package com.egoveris.deo.model.model;

import java.io.Serializable;

public class RequestExternalBloquearTarea implements Serializable {

  private static final long serialVersionUID = 1840369175082173281L;

  private String workflowID;

  public enum Operacion {
    BLOQUEAR, DESBLOQUEAR
  };

  private Operacion operacion;

  public String getWorkflowID() {
    return workflowID;
  }

  public void setWorkflowID(String workflowID) {
    this.workflowID = workflowID;
  }

  public Operacion getOperacion() {
    return operacion;
  }

  public void setOperacion(Operacion operacion) {
    this.operacion = operacion;
  }

}
