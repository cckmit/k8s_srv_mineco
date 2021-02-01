package com.egoveris.te.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "GEDO_TIPODOCUMENTO")
public class TipoDocumentoGedo {

  @Id
  @Column(name = "ID")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "nombre")
  private String nombre;

  @Column(name = "acronimo")
  private String acronimo;

  @Column(name = "descripcion")
  private String descripcion;

  @Column(name = "esEspecial")
  private Boolean esEspecial;

  @Column(name = "esManual")
  private Boolean esManual;

  @Column(name = "esAutomatica")
  private Boolean esAutomatica;

  @Column(name = "tieneToken")
  private Boolean tieneToken;

  @Column(name = "tieneTemplate")
  private Boolean tieneTemplate;

  @Column(name = "tipoProduccion")
  private Integer tipoProduccion;

  @Column(name = "permite_Embebidos")
  private Boolean permiteEmbebidos;

  @Column(name = "es_notificable")
  private Boolean esNotificable;

  @Column(name = "estado")
  private String estado;

  @Column(name = "esFirmaExterna")
  private Boolean esFirmaExterna;

  @Column(name = "idTipoDocumentoSade")
  private Integer idTipoDocumentoSade;

  @Column(name = "version")
  private String version;

  @Column(name = "codigoTipoDocumentoSade")
  private String codigoTipoDocumentoSade;

  @Column(name = "esFirmaConjunta")
  private Boolean esFirmaConjunta;

  @Column(name = "esConfidencial")
  private Boolean esConfidencial;

  @Column(name = "tieneAviso")
  private Boolean tieneAviso;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getAcronimo() {
    return acronimo;
  }

  public void setAcronimo(String acronimo) {
    this.acronimo = acronimo;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public Boolean getEsEspecial() {
    return esEspecial;
  }

  public void setEsEspecial(Boolean esEspecial) {
    this.esEspecial = esEspecial;
  }

  public Boolean getEsManual() {
    return esManual;
  }

  public void setEsManual(Boolean esManual) {
    this.esManual = esManual;
  }

  public Boolean getEsAutomatica() {
    return esAutomatica;
  }

  public void setEsAutomatica(Boolean esAutomatica) {
    this.esAutomatica = esAutomatica;
  }

  public Boolean getTieneToken() {
    return tieneToken;
  }

  public void setTieneToken(Boolean tieneToken) {
    this.tieneToken = tieneToken;
  }

  public Boolean getTieneTemplate() {
    return tieneTemplate;
  }

  public void setTieneTemplate(Boolean tieneTemplate) {
    this.tieneTemplate = tieneTemplate;
  }

  public Integer getTipoProduccion() {
    return tipoProduccion;
  }

  public void setTipoProduccion(Integer tipoProduccion) {
    this.tipoProduccion = tipoProduccion;
  }

  public Boolean getPermiteEmbebidos() {
    return permiteEmbebidos;
  }

  public void setPermiteEmbebidos(Boolean permiteEmbebidos) {
    this.permiteEmbebidos = permiteEmbebidos;
  }

  public Boolean getEsNotificable() {
    return esNotificable;
  }

  public void setEsNotificable(Boolean esNotificable) {
    this.esNotificable = esNotificable;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public Boolean getEsFirmaExterna() {
    return esFirmaExterna;
  }

  public void setEsFirmaExterna(Boolean esFirmaExterna) {
    this.esFirmaExterna = esFirmaExterna;
  }

  public Integer getIdTipoDocumentoSade() {
    return idTipoDocumentoSade;
  }

  public void setIdTipoDocumentoSade(Integer idTipoDocumentoSade) {
    this.idTipoDocumentoSade = idTipoDocumentoSade;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getCodigoTipoDocumentoSade() {
    return codigoTipoDocumentoSade;
  }

  public void setCodigoTipoDocumentoSade(String codigoTipoDocumentoSade) {
    this.codigoTipoDocumentoSade = codigoTipoDocumentoSade;
  }

  public Boolean getEsFirmaConjunta() {
    return esFirmaConjunta;
  }

  public void setEsFirmaConjunta(Boolean esFirmaConjunta) {
    this.esFirmaConjunta = esFirmaConjunta;
  }

  public Boolean getEsConfidencial() {
    return esConfidencial;
  }

  public void setEsConfidencial(Boolean esConfidencial) {
    this.esConfidencial = esConfidencial;
  }

  public Boolean getTieneAviso() {
    return tieneAviso;
  }

  public void setTieneAviso(Boolean tieneAviso) {
    this.tieneAviso = tieneAviso;
  }

}