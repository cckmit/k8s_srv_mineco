package com.egoveris.edt.base.model.eu.actuacion;

import java.io.Serializable;
import java.util.Date;

public class ActuacionAudiDTO implements Serializable {

  private static final long serialVersionUID = 4161160097689381071L;

  // id_actuacion_audi
  private Integer id;

  // id_actuacion
  private Integer idActuacion;

  // codigo_actuacion
  private String codigoActuacion;

  // nombre_actuacion
  private String nombreActuacion;

  // vigencia_desde
  private Date vigenciaDesde;

  // vigencia_hasta
  private Date vigenciaHasta;

  // inicia_actuacion
  private char iniciaActuacion;

  // jerarquia
  private Integer jerarquia;

  // incorporado
  private char incorporado;

  // agregado
  private char agregado;

  // anulado
  private char anulado;

  // desglosado
  private char desaglosado;

  // version
  private Integer version;

  // fecha_a
  private Date fechaActualizacion;

  // funcion_a
  private char funcion_a;

  // usuario_a
  private String usuarioActualizacion;

  // id_sector_interno_a
  private Integer idSectorInternoA;

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getIdActuacion() {
    return idActuacion;
  }

  public void setIdActuacion(Integer idActuacion) {
    this.idActuacion = idActuacion;
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

  public Date getFechaActualizacion() {
    return fechaActualizacion;
  }

  public void setFechaActualizacion(Date fechaActualizacion) {
    this.fechaActualizacion = fechaActualizacion;
  }

  public char getFuncion_a() {
    return funcion_a;
  }

  public void setFuncion_a(char funcion_a) {
    this.funcion_a = funcion_a;
  }

  public String getUsuarioActualizacion() {
    return usuarioActualizacion;
  }

  public void setUsuarioActualizacion(String usuarioActualizacion) {
    this.usuarioActualizacion = usuarioActualizacion;
  }

  public Integer getIdSectorInternoA() {
    return idSectorInternoA;
  }

  public void setIdSectorInternoA(Integer idSectorInternoA) {
    this.idSectorInternoA = idSectorInternoA;
  }

}
