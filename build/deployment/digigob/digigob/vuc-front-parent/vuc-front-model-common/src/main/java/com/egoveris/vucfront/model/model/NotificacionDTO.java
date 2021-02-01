package com.egoveris.vucfront.model.model;

import com.egoveris.shared.date.DateUtil;

import java.io.Serializable;
import java.util.Date;

public class NotificacionDTO implements Serializable {

  private static final long serialVersionUID = -6247303327564700210L;

  private Long id;
  private Boolean bajaLogica;
  private String codSade;
  private Date fechaNotificacion;
  private ExpedienteBaseDTO expediente;
  private PersonaDTO persona;
  private String motivo;
  private Boolean notificado;
  private String usuarioCreacion;
  private Long version;

  public String getFormattedDate() {
    return DateUtil.getFormattedDate(fechaNotificacion);
  }

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

  public ExpedienteBaseDTO getExpediente() {
    return expediente;
  }

  public void setExpediente(ExpedienteBaseDTO expediente) {
    this.expediente = expediente;
  }

  public PersonaDTO getPersona() {
    return persona;
  }

  public void setPersona(PersonaDTO persona) {
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

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("NotificacionDTO [id=").append(id).append(", bajaLogica=").append(bajaLogica)
        .append(", codSade=").append(codSade).append(", fechaNotificacion=")
        .append(fechaNotificacion).append(", expediente=").append(expediente).append(", persona=")
        .append(persona).append(", motivo=").append(motivo).append(", notificado=")
        .append(notificado).append(", usuarioCreacion=").append(usuarioCreacion)
        .append(", version=").append(version).append("]");
    return builder.toString();
  }

}