package com.egoveris.deo.model.model;

import java.io.Serializable;

public class ResponseExternalDepuracionDocumento implements Serializable {

  private static final long serialVersionUID = 1242470788261971400L;

  private String estado;

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }
}
