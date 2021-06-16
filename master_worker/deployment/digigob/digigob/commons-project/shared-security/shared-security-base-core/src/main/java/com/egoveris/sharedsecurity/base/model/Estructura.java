package com.egoveris.sharedsecurity.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "edt_sade_estructura")
public class Estructura {

  @Id
  @Column(name = "id_estructura")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(name = "codigo_estructura")
  private Integer codigoEstructura;

  @Column(name = "nombre_estructura")
  private String nombreEstructura;

  @Column(name = "vigencia_desde")
  private Date vigenciaDesde;

  @Column(name = "vigencia_Hasta")
  private Date vigenciaHasta;

  @Column(name = "genera_Als")
  private String generaAls;

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

  @Column(name = "estructura_poder_ejecutivo")
  private String estructuraPoderEjecutivo;

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

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

}