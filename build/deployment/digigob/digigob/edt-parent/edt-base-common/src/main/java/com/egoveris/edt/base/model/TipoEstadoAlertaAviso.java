package com.egoveris.edt.base.model;

import java.io.Serializable;

public enum TipoEstadoAlertaAviso implements Serializable {

  LEIDO("LEIDO"), NO_LEIDO("NO_LEIDO");

  private final String estado;

  TipoEstadoAlertaAviso(String estado) {
    this.estado = estado;
  }

  public String getEstado() {
    return estado;
  }

}