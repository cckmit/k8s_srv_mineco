package com.egoveris.edt.base.model.eu.actuacion;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "edt_sade_actuacion_audi")
public class ActuacionAudi {

  @Id
  @Column(name = "id_actuacion_audi")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(name = "id_actuacion")
  private Integer idActuacion;

  @Column(name = "codigo_actuacion")
  private String codigoActuacion;

  @Column(name = "nombre_actuacion")
  private String nombreActuacion;

  @Column(name = "vigencia_desde")
  private Date vigenciaDesde;

  @Column(name = "vigencia_hasta")
  private Date vigenciaHasta;

  @Column(name = "inicia_actuacion")
  private char iniciaActuacion;

  @Column(name = "jerarquia")
  private Integer jerarquia;

  @Column(name = "incorporado")
  private char incorporado;

  @Column(name = "agregado")
  private char agregado;

  @Column(name = "anulado")
  private char anulado;

  @Column(name = "desglosado")
  private char desaglosado;

  @Column(name = "version")
  private Integer version;

  @Column(name = "fecha_a")
  private Date fechaActualizacion;

  @Column(name = "funcion_a")
  private char funcion_a;

  @Column(name = "usuario_a")
  private String usuarioActualizacion;

  @Column(name = "id_sector_interno_a")
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