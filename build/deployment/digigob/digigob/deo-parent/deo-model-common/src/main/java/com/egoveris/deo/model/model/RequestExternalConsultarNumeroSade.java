package com.egoveris.deo.model.model;

import java.io.Serializable;

public class RequestExternalConsultarNumeroSade implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = -40932681037642301L;
  private String origen;
  private String id;

  public String getOrigen() {
    return origen;
  }

  public void setOrigen(String origen) {
    this.origen = origen;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
