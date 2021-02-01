package com.egoveris.edt.base.model.eu.actuacion;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "edt_sade_actuacion")
public class Actuacion {

  @Id
  @Column(name = "id_actuacion")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

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

  @Column(name = "fecha_creacion")
  private Date fechaCreacion;

  @Column(name = "usuario_creacion")
  private String usuarioCreacion;

  @Column(name = "fecha_modificacion")
  private Date fechaModificacion;

  @Column(name = "usuario_modificacion")
  private String usuarioModificacion;

  @Column(name = "estado_registro")
  private Boolean estadoRegistro;

  @Column(name = "es_documento")
  private Integer esDocumento;

  @Column(name = "deshabilitado_papel")
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

}