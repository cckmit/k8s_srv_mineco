package com.egoveris.deo.model.model;

import java.io.Serializable;

public class RequestExternalCancelarTarea implements Serializable {

  private static final long serialVersionUID = 3505702105442135829L;

  private String workflowID;

  public String getWorkflowID() {
    return workflowID;
  }

  public void setWorkflowID(String workflowID) {
    this.workflowID = workflowID;
  }

}
