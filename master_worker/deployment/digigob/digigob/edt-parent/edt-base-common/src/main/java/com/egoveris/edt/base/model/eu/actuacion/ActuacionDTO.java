package com.egoveris.edt.base.model.eu.actuacion;

import java.io.Serializable;
import java.util.Date;

public class ActuacionDTO implements Serializable {
  private static final long serialVersionUID = 8149488493496557533L;

  private Integer id;
  private String codigoActuacion;
  private String nombreActuacion;
  private Date vigenciaDesde;
  private Date vigenciaHasta;
  private char iniciaActuacion;
  private Integer jerarquia;
  private char incorporado;
  private char agregado;
  private char anulado;
  private char desaglosado;
  private Integer version;
  private Date fechaCreacion;
  private String usuarioCreacion;
  private Date fechaModificacion;
  private String usuarioModificacion;
  private Boolean estadoRegistro;
  private Integer esDocumento;
  private boolean deshabilitadoPapel;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getCodigoActuacion() {
    return codigoActuacion;
  }

  public void setCodigoActuacion(String codigoActuacion) {
    this.codigoActuacion = codigoActuacion;
  }

  public String getNombreActuacion() {
    return nombreActuacion;
  }

  public void setNombreActuacion(String nombreActuacion) {
    this.nombreActuacion = nombreActuacion;
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

  public char getIniciaActuacion() {
    return iniciaActuacion;
  }

  public void setIniciaActuacion(char iniciaActuacion) {
    this.iniciaActuacion = iniciaActuacion;
  }

  public Integer getJerarquia() {
    return jerarquia;
  }

  public void setJerarquia(Integer jerarquia) {
    this.jerarquia = jerarquia;
  }

  public char getIncorporado() {
    return incorporado;
  }

  public void setIncorporado(char incorporado) {
    this.incorporado = incorporado;
  }

  public char getAgregado() {
    return agregado;
  }

  public void setAgregado(char agregado) {
    this.agregado = agregado;
  }

  public char getAnulado() {
    return anulado;
  }

  public void setAnulado(char anulado) {
    this.anulado = anulado;
  }

  public char getDesaglosado() {
    return desaglosado;
  }

  public void setDesaglosado(char desaglosado) {
    this.desaglosado = desaglosado;
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

  public Integer getEsDocumento() {
    return esDocumento;
  }

  public void setEsDocumento(Integer esDocumento) {
    this.esDocumento = esDocumento;
  }

  public boolean isDeshabilitadoPapel() {
    return deshabilitadoPapel;
  }

  public void setDeshabilitadoPapel(boolean deshabilitadoPapel) {
    this.deshabilitadoPapel = deshabilitadoPapel;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("ActuacionDTO [id=").append(id).append(", codigoActuacion=")
        .append(codigoActuacion).append(", nombreActuacion=").append(nombreActuacion)
        .append(", vigenciaDesde=").append(vigenciaDesde).append(", vigenciaHasta=")
        .append(vigenciaHasta).append(", iniciaActuacion=").append(iniciaActuacion)
        .append(", jerarquia=").append(jerarquia).append(", incorporado=").append(incorporado)
        .append(", agregado=").append(agregado).append(", anulado=").append(anulado)
        .append(", desaglosado=").append(desaglosado).append(", version=").append(version)
        .append(", fechaCreacion=").append(fechaCreacion).append(", usuarioCreacion=")
        .append(usuarioCreacion).append(", fechaModificacion=").append(fechaModificacion)
        .append(", usuarioModificacion=").append(usuarioModificacion).append(", estadoRegistro=")
        .append(estadoRegistro).append(", esDocumento=").append(esDocumento)
        .append(", deshabilitadoPapel=").append(deshabilitadoPapel).append("]");
    return builder.toString();
  }

}