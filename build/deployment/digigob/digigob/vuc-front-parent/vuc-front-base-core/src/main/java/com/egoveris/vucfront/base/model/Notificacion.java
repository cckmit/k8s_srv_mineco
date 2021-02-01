package com.egoveris.vucfront.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TAD_NOTIFICACION")
public class Notificacion {

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "BAJA_LOGICA")
  private Boolean bajaLogica;

  @Column(name = "CODSADE")
  private String codSade;

  @Column(name = "FECHA_NOTIFICACION")
  private Date fechaNotificacion;

  @ManyToOne
  @JoinColumn(name = "ID_EXPEDIENTE_BASE")
  private ExpedienteBase expediente;

  @OneToOne
  @JoinColumn(name = "ID_PERSONA")
  private Persona persona;

  @Column(name = "MOTIVO")
  private String motivo;

  @Column(name = "NOTIFICADO")
  private Boolean notificado;

  @Column(name = "USUARIO_CREACION")
  private String usuarioCreacion;

  @Column(name = "VERSION")
  private Long version;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Boolean getBajaLogica() {
    return bajaLogica;
  }

  public void setBajaLogica(Boolean bajaLogica) {
    this.bajaLogica = bajaLogica;
  }

  public String getCodSade() {
    return codSade;
  }

  public void setCodSade(String codSade) {
    this.codSade = codSade;
  }

  public Date getFechaNotificacion() {
    return fechaNotificacion;
  }

  public void setFechaNotificacion(Date fechaNotificacion) {
    this.fechaNotificacion = fechaNotificacion;
  }

  public ExpedienteBase getExpediente() {
    return expediente;
  }

  public void setExpediente(ExpedienteBase expediente) {
    this.expediente = expediente;
  }

  public Persona getPersona() {
    return persona;
  }

  public void setPersona(Persona persona) {
    this.persona = persona;
  }

  public String getMotivo() {
    return motivo;
  }

  public void setMotivo(String motivo) {
    this.motivo = motivo;
  }

  public Boolean getNotificado() {
    return notificado;
  }

  public void setNotificado(Boolean notificado) {
    this.notificado = notificado;
  }

  public String getUsuarioCreacion() {
    return usuarioCreacion;
  }

  public void setUsuarioCreacion(String usuarioCreacion) {
    this.usuarioCreacion = usuarioCreacion;
  }

  public Long getVersion() {
    return version;
  }

  public void setVersion(Long version) {
    this.version = version;
  }

}