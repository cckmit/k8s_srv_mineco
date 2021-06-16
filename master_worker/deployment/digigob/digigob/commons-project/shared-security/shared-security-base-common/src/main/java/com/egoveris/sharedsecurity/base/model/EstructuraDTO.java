package com.egoveris.sharedsecurity.base.model;

import java.io.Serializable;
import java.util.Date;

public class EstructuraDTO implements Serializable {

  private static final long serialVersionUID = 574912191966483549L;

  private Integer id;
  private Integer codigoEstructura;
  private String nombreEstructura;
  private Date vigenciaDesde;
  private Date vigenciaHasta;
  private String generaAls;
  private Integer version;
  private Date fechaCreacion;
  private String usuarioCreacion;
  private Date fechaModificacion;
  private String usuarioModificacion;
  private Boolean estadoRegistro;
  private String estructuraPoderEjecutivo;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getCodigoEstructura() {
    return codigoEstructura;
  }

  public void setCodigoEstructura(Integer codigoEstructura) {
    this.codigoEstructura = codigoEstructura;
  }

  public String getNombreEstructura() {
    return nombreEstructura;
  }

  public void setNombreEstructura(String nombreEstructura) {
    this.nombreEstructura = nombreEstructura;
  }

  public Date getVigenciaDesde() {
    return vigenciaDesde;
  }

  public void setVigenciaDesde(Date vigenciaDesde) {
    this.vigenciaDesde = vigenciaDesde;
  }

  public Date getVigenciaHasta() {
    return vigenciaHasta;
  }

  public void setVigenciaHasta(Date vigenciaHasta) {
    this.vigenciaHasta = vigenciaHasta;
  }

  public String getGeneraAls() {
    return generaAls;
  }

  public void setGeneraAls(String generaAls) {
    this.generaAls = generaAls;
  }

  public Integer getVersion() {
    return version;
  }

  public void setVersion(Integer version) {
    this.version = version;
  }

  public Date getFechaCreacion() {
    return fechaCreacion;
  }

  public void setFechaCreacion(Date fechaCreacion) {
    this.fechaCreacion = fechaCreacion;
  }

  public String getUsuarioCreacion() {
    return usuarioCreacion;
  }

  public void setUsuarioCreacion(String usuarioCreacion) {
    this.usuarioCreacion = usuarioCreacion;
  }

  public Date getFechaModificacion() {
    return fechaModificacion;
  }

  public void setFechaModificacion(Date fechaModificacion) {
    this.fechaModificacion = fechaModificacion;
  }

  public String getUsuarioModificacion() {
    return usuarioModificacion;
  }

  public void setUsuarioModificacion(String usuarioModificacion) {
    this.usuarioModificacion = usuarioModificacion;
  }

  public Boolean getEstadoRegistro() {
    return estadoRegistro;
  }

  public void setEstadoRegistro(Boolean estadoRegistro) {
    this.estadoRegistro = estadoRegistro;
  }

  public String getEstructuraPoderEjecutivo() {
    return estructuraPoderEjecutivo;
  }

  public void setEstructuraPoderEjecutivo(String estructuraPoderEjecutivo) {
    this.estructuraPoderEjecutivo = estructuraPoderEjecutivo;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("EstructuraDTO [id=").append(id).append(", codigoEstructura=")
        .append(codigoEstructura).append(", nombreEstructura=").append(nombreEstructura)
        .append(", vigenciaDesde=").append(vigenciaDesde).append(", vigenciaHasta=")
        .append(vigenciaHasta).append(", generaAls=").append(generaAls).append(", version=")
        .append(version).append(", fechaCreacion=").append(fechaCreacion)
        .append(", usuarioCreacion=").append(usuarioCreacion).append(", fechaModificacion=")
        .append(fechaModificacion).append(", usuarioModificacion=").append(usuarioModificacion)
        .append(", estadoRegistro=").append(estadoRegistro).append(", estructuraPoderEjecutivo=")
        .append(estructuraPoderEjecutivo).append("]");
    return builder.toString();
  }
}