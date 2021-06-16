package com.egoveris.deo.model.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author MAGARCES
 *
 */
public class DocumentoSolrResponse implements Serializable {

  private static final long serialVersionUID = 1950342275852241260L;

  private Long id;
  private Date fechaCreacion;
  private String actuacionAcr;
  private String nroSade;
  private String nroSadePapel;
  private String nroEspecialSade;
  private String usuarioGenerador;
  private String reparticion;
  private String referencia;
  private String tipoDocAcr;
  private String tipoDocNombre;
  private Integer anioDoc;
  private Long nroDoc;
  private String nombre;

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Date getFechaCreacion() {
    return fechaCreacion;
  }

  public void setFechaCreacion(Date fechaCreacion) {
    this.fechaCreacion = fechaCreacion;
  }

  public String getActuacionAcr() {
    return actuacionAcr;
  }

  public void setActuacionAcr(String actuacionAcr) {
    this.actuacionAcr = actuacionAcr;
  }

  public String getNroSade() {
    return nroSade;
  }

  public void setNroSade(String nroSade) {
    this.nroSade = nroSade;
  }

  public String getNroSadePapel() {
    return nroSadePapel;
  }

  public void setNroSadePapel(String nroSadePapel) {
    this.nroSadePapel = nroSadePapel;
  }

  public String getNroEspecialSade() {
    return nroEspecialSade;
  }

  public void setNroEspecialSade(String nroEspecialSade) {
    this.nroEspecialSade = nroEspecialSade;
  }

  public String getUsuarioGenerador() {
    return usuarioGenerador;
  }

  public void setUsuarioGenerador(String usuarioGenerador) {
    this.usuarioGenerador = usuarioGenerador;
  }

  public String getReparticion() {
    return reparticion;
  }

  public void setReparticion(String reparticion) {
    this.reparticion = reparticion;
  }

  public String getReferencia() {
    return referencia;
  }

  public void setReferencia(String referencia) {
    this.referencia = referencia;
  }

  public String getTipoDocAcr() {
    return tipoDocAcr;
  }

  public void setTipoDocAcr(String tipoDocAcr) {
    this.tipoDocAcr = tipoDocAcr;
  }

  public Integer getAnioDoc() {
    return anioDoc;
  }

  public void setAnioDoc(Integer anioDoc) {
    this.anioDoc = anioDoc;
  }

  public Long getNroDoc() {
    return nroDoc;
  }

  public void setNroDoc(Long nroDoc) {
    this.nroDoc = nroDoc;
  }

  public String getTipoDocNombre() {
    return tipoDocNombre;
  }

  public void setTipoDocNombre(String tipoDocNombre) {
    this.tipoDocNombre = tipoDocNombre;
  }
}
