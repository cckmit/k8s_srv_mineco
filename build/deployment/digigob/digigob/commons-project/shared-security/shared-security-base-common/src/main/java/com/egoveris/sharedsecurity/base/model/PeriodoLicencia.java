package com.egoveris.sharedsecurity.base.model;

import java.io.Serializable;
import java.util.Date;

public class PeriodoLicencia implements Serializable {

  private static final long serialVersionUID = 2467508226383406312L;

  private Long idPeriodoLicencia;
  private Date fechaHoraDesde;
  private Date fechaHoraHasta;
  private String apoderado;
  private String condicionPeriodo;
  private String usuario;
  private Date fechaCancelacion;

  public Long getIdPeriodoLicencia() {
    return idPeriodoLicencia;
  }

  public void setIdPeriodoLicencia(Long idPeriodoLicencia) {
    this.idPeriodoLicencia = idPeriodoLicencia;
  }

  public Date getFechaHoraDesde() {
    return fechaHoraDesde;
  }

  public void setFechaHoraDesde(Date fechaHoraDesde) {
    this.fechaHoraDesde = fechaHoraDesde;
  }

  public Date getFechaHoraHasta() {
    return fechaHoraHasta;
  }

  public void setFechaHoraHasta(Date fechaHoraHasta) {
    this.fechaHoraHasta = fechaHoraHasta;
  }

  public String getApoderado() {
    return apoderado;
  }

  public void setApoderado(String apoderado) {
    this.apoderado = apoderado;
  }

  public String getCondicionPeriodo() {
    return condicionPeriodo;
  }

  public void setCondicionPeriodo(String condicionPeriodo) {
    this.condicionPeriodo = condicionPeriodo;
  }

  public String getUsuario() {
    return usuario;
  }

  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }

  public Date getFechaCancelacion() {
    return fechaCancelacion;
  }

  public void setFechaCancelacion(Date fechaCancelacion) {
    this.fechaCancelacion = fechaCancelacion;
  }

}
