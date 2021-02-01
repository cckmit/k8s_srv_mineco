package com.egoveris.te.model.model;

import java.io.Serializable;

public class TipoReservaDTO implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 1282425798189767872L;

  private Integer id;
  private String tipoReserva;
  private String descripcion;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getTipoReserva() {
    return tipoReserva;
  }

  public void setTipoReserva(String tipoReserva) {
    this.tipoReserva = tipoReserva;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

}
