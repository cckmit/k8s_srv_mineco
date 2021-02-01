package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;

public class ObservacionDTO extends AbstractCComplejoDTO implements Serializable {
  private static final long serialVersionUID = 1L;

  protected String observacionCode;
  protected String observacionDesc;

  public String getObservacionCode() {
    return observacionCode;
  }

  public void setObservacionCode(String observacionCode) {
    this.observacionCode = observacionCode;
  }

  public String getObservacionDesc() {
    return observacionDesc;
  }

  public void setObservacionDesc(String observacionDesc) {
    this.observacionDesc = observacionDesc;
  }

}
