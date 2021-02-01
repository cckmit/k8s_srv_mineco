package com.egoveris.te.ws.model;

import com.egoveris.shared.date.DateUtil;

import java.io.Serializable;
import java.util.Date;

public class ExternalHistorialOperacionDTO implements Serializable {

  private static final long serialVersionUID = -5921818096908790909L;

  private Date fechaOperacion;
  private String organismoDestino;

  public String getFormattedDate() {
    if (fechaOperacion != null) {
      return DateUtil.getFormattedDate(fechaOperacion);
    }
    return "";
  }

  public Date getFechaOperacion() {
    return fechaOperacion;
  }

  public void setFechaOperacion(Date fechaOperacion) {
    this.fechaOperacion = fechaOperacion;
  }

  public String getOrganismoDestino() {
    return organismoDestino;
  }

  public void setOrganismoDestino(String organismoDestino) {
    this.organismoDestino = organismoDestino;
  }

}