package com.egoveris.deo.model.model;

import java.io.Serializable;

public class ResponseExternalCancelarTarea implements Serializable {

  private static final long serialVersionUID = 3984342990863388926L;

  private String estado;

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

}
