package com.egoveris.deo.model.model;

import java.io.Serializable;

public class ResponseExternalBloquearTarea implements Serializable {

  private static final long serialVersionUID = -4209389635369987921L;

  private String estado;

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

}
