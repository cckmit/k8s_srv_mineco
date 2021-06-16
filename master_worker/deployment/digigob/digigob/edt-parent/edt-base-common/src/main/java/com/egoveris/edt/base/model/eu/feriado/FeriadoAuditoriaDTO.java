package com.egoveris.edt.base.model.eu.feriado;

import java.util.Date;

public class FeriadoAuditoriaDTO {

  private Integer id;
  private Integer idFeriado;
  private String motivo;
  private Date fechaFeriado;
  private Date fechaAuditoria;
  private String operacion;
  private String usuario;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getIdFeriado() {
    return idFeriado;
  }

  public void setIdFeriado(Integer idFeriado) {
    this.idFeriado = idFeriado;
  }

  public String getMotivo() {
    return motivo;
  }

  public void setMotivo(String motivo) {
    this.motivo = motivo;
  }

  public Date getFechaFeriado() {
    return fechaFeriado;
  }

  public void setFechaFeriado(Date fechaFeriado) {
    this.fechaFeriado = fechaFeriado;
  }

  public Date getFechaAuditoria() {
    return fechaAuditoria;
  }

  public void setFechaAuditoria(Date fechaAuditoria) {
    this.fechaAuditoria = fechaAuditoria;
  }

  public String getOperacion() {
    return operacion;
  }

  public void setOperacion(String operacion) {
    this.operacion = operacion;
  }

  public String getUsuario() {
    return usuario;
  }

  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }

}