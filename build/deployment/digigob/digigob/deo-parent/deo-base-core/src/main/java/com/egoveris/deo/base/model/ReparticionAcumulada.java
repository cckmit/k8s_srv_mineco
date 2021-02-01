package com.egoveris.deo.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "GEDO_REPARTICION_ACUMULADA")
public class ReparticionAcumulada {

  @EmbeddedId
  private ReparticionAcumuladaPK reparticionAcumuladaPK;

  @Column(name = "fechaModificacion")
  private Date fechaModificacion;

  @Column(name = "tipoOperacion")
  private String tipoOperacion;

  @Column(name = "userName")
  private String userName;

  public ReparticionAcumuladaPK getReparticionAcumuladaPK() {
    return reparticionAcumuladaPK;
  }

  public void setReparticionAcumuladaPK(ReparticionAcumuladaPK reparticionAcumuladaPK) {
    this.reparticionAcumuladaPK = reparticionAcumuladaPK;
  }

  public Date getFechaModificacion() {
    return fechaModificacion;
  }

  public void setFechaModificacion(Date fechaModificacion) {
    this.fechaModificacion = fechaModificacion;
  }

  public String getTipoOperacion() {
    return tipoOperacion;
  }

  public void setTipoOperacion(String tipoOperacion) {
    this.tipoOperacion = tipoOperacion;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }
}
