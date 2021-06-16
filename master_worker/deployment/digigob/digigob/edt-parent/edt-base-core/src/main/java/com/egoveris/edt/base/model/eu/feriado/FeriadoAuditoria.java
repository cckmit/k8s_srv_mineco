package com.egoveris.edt.base.model.eu.feriado;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "EU_FERIADOS_AUDI")
public class FeriadoAuditoria {

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(name = "IDFERIADO")
  private Integer idFeriado;

  @Column(name = "MOTIVO")
  private String motivo;

  @Column(name = "FECHAFERIADO")
  private Date fechaFeriado;

  @Column(name = "FECHAAUDITORIA")
  private Date fechaAuditoria;

  @Column(name = "OPERACION")
  private String operacion = "ALTA";

  @Column(name = "USUARIO")
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