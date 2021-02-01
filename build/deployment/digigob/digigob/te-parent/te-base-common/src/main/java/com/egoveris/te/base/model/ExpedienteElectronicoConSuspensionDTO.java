package com.egoveris.te.base.model;

import java.io.Serializable;
import java.util.Date;

public class ExpedienteElectronicoConSuspensionDTO implements Serializable {

  private static final long serialVersionUID = -4099335598695501341L;
  
  private ExpedienteElectronicoDTO ee;
  private String usuarioSuspension;
  private Date fechaSuspension;
  private String codigoCaratula;

  public ExpedienteElectronicoDTO getEe() {
    return ee;
  }

  public void setEe(ExpedienteElectronicoDTO ee) {
    this.ee = ee;
  }

  public String getUsuarioSuspension() {
    return usuarioSuspension;
  }

  public void setUsuarioSuspension(String usuarioSuspension) {
    this.usuarioSuspension = usuarioSuspension;
  }

  public Date getFechaSuspension() {
    return fechaSuspension;
  }

  public void setFechaSuspension(Date fechaSuspension) {
    this.fechaSuspension = fechaSuspension;
  }

  public String getCodigoCaratula() {
    return codigoCaratula;
  }

  public void setCodigoCaratula(String codigoCaratula) {
    this.codigoCaratula = codigoCaratula;
  }

}