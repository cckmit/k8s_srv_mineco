package com.egoveris.deo.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "GEDO_TASK_BY_USER")
public class TareaBusqueda {

  @Id
  @Column(name = "WORKFLOWORIGEN", nullable = false)
  private String workfloworigen;

  @Column(name = "USUARIO")
  private String usuario;

  @Column(name = "PRIMER_USUARIO")
  private String usuarioIniciador;

  @Column(name = "FECHA_ALTA_TAREA")
  private Date fechaAlta;

  @Column(name = "FECHA_PARTICIPACION")
  private Date fechaParticipacion;

  @Column(name = "TIPO_DOCUMENTO")
  private String tipoDocumento;

  @Column(name = "MOTIVO")
  private String referencia;

  @Column(name = "TIPO_TAREA")
  private String tipoTarea;

  @Column(name = "ULTIMO_USUARIO")
  private String usuarioDestino;

  @Column(name = "ES_COMUNICABLE")
  private boolean esComunicable;

  public String getWorkfloworigen() {
    return workfloworigen;
  }

  public void setWorkfloworigen(String workfloworigen) {
    this.workfloworigen = workfloworigen;
  }

  public String getUsuario() {
    return usuario;
  }

  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }

  public Date getFechaParticipacion() {
    return fechaParticipacion;
  }

  public void setFechaParticipacion(Date fechaParticipacion) {
    this.fechaParticipacion = fechaParticipacion;
  }

  public String getUsuarioIniciador() {
    return usuarioIniciador;
  }

  public void setUsuarioIniciador(String usuarioIniciador) {
    this.usuarioIniciador = usuarioIniciador;
  }

  public Date getFechaAlta() {
    return fechaAlta;
  }

  public void setFechaAlta(Date fechaAlta) {
    this.fechaAlta = fechaAlta;
  }

  public String getTipoDocumento() {
    return tipoDocumento;
  }

  public void setTipoDocumento(String tipoDocumento) {
    this.tipoDocumento = tipoDocumento;
  }

  public String getReferencia() {
    return referencia;
  }

  public void setReferencia(String motivo) {
    this.referencia = motivo;
  }

  public String getTipoTarea() {
    return tipoTarea;
  }

  public void setTipoTarea(String tipoTarea) {
    this.tipoTarea = tipoTarea;
  }

  public String getUsuarioDestino() {
    return usuarioDestino;
  }

  public void setUsuarioDestino(String usuarioDestino) {
    this.usuarioDestino = usuarioDestino;
  }

  public boolean isEsComunicable() {
    return esComunicable;
  }

  public void setEsComunicable(boolean esComunicable) {
    this.esComunicable = esComunicable;
  }

}
