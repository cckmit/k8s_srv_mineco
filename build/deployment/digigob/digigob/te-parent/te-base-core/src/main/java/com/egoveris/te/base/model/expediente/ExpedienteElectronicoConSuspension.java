package com.egoveris.te.base.model.expediente;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "EE_CON_SUSPENSION")
public class ExpedienteElectronicoConSuspension {

  @Id
  @Column(name = "ID_EE")
  private Long idEE;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "ID_EE")
  @MapsId
  private ExpedienteElectronico ee;

  @Column(name = "USUARIO_SUSP")
  private String usuarioSuspension;

  @Column(name = "FECHA_SUSP")
  private Date fechaSuspension;

  @Column(name = "COD_CARATULA")
  private String codigoCaratula;

  public Long getIdEE() {
    return idEE;
  }

  public void setIdEE(Long idEE) {
    this.idEE = idEE;
  }

  public ExpedienteElectronico getEe() {
    return ee;
  }

  public void setEe(ExpedienteElectronico ee) {
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