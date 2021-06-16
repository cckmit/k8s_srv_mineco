package com.egoveris.edt.model.model;

import java.io.Serializable;

public class ActuacionSadeDTO implements Serializable {

  private static final long serialVersionUID = -3717453847214832034L;

  private Integer id;
  private String codigoActuacion;
  private String nombreActuacion;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getCodigoActuacion() {
    return codigoActuacion;
  }

  public void setCodigoActuacion(String codigoActuacion) {
    this.codigoActuacion = codigoActuacion;
  }

  public String getNombreActuacion() {
    return nombreActuacion;
  }

  public void setNombreActuacion(String nombreActuacion) {
    this.nombreActuacion = nombreActuacion;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("ActuacionSadeDTO [id=").append(id).append(", codigoActuacion=")
        .append(codigoActuacion).append(", nombreActuacion=").append(nombreActuacion).append("]");
    return builder.toString();
  }

}