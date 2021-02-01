package com.egoveris.deo.model.model;

import java.io.Serializable;

public class ResponseExternalConsultaNumeroSade implements Serializable {

  /**
  * 
  */
  private static final long serialVersionUID = 1L;
  private String numeroSade;
  private String idError;
  private String motivo;
  private String estadoTarea;

  public String getEstadoTarea() {
    return estadoTarea;
  }

  public void setEstadoTarea(String estadoTarea) {
    this.estadoTarea = estadoTarea;
  }

  public String getNumeroSade() {
    return numeroSade;
  }

  public void setNumeroSade(String numeroSade) {
    this.numeroSade = numeroSade;
  }

  public String getIdError() {
    return idError;
  }

  public void setIdError(String idError) {
    this.idError = idError;
  }

  public String getMotivo() {
    return motivo;
  }

  public void setMotivo(String motivo) {
    this.motivo = motivo;
  }
}
