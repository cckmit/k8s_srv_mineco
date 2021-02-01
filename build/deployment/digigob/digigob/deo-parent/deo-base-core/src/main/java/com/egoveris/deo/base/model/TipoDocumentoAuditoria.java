package com.egoveris.deo.base.model;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.tika.metadata.Metadata;

@Entity
@Table(name = "GEDO_TIPODOCUMENTO_AUD")
public class TipoDocumentoAuditoria {

  @Column(name = "tipoOperacion")
  private String tipoOperacion;

  @Column(name = "fechaOperacion")
  private Date fechaModificacion;

  @Column(name = "userName")
  private String userName;

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Integer id;

  @Column(name = "nombre")
  private String nombre;

  @Column(name = "acronimo")
  private String acronimo;

  @Column(name = "version")
  private String version;

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

  @Column(name = "estado")
  private String estado;

  @Column(name = "idTipoDocumentoSade")
  private Integer idTipoDocumentoSade;

  @Column(name = "codigoTipoDocumentoSade")
  private String codigoTipoDocumentoSade;

  @Column(name = "esFirmaConjunta")
  private Boolean esFirmaConjunta;

  @Column(name = "esConfidencial")
  private Boolean esConfidencial;

//  @ElementCollection
//  @CollectionTable(name = "GEDO_TIPODOCUMENTO_REP_AUD", joinColumns = @JoinColumn(name = "tipoDocumento"))
//  private Set<ReparticionHabilitada> listaReparticiones;
//
//  @ElementCollection
//  @CollectionTable(name = "DATOS_VARIABLES_TIPO_DOC_AUD", joinColumns = @JoinColumn(name = "id"))
//  private List<Metadata> listaDatosVariables;

  @Column(name = "esFirmaExterna")
  private Boolean esFirmaExterna;

  @OneToOne(cascade = CascadeType.DETACH)
  @JoinColumn(name = "familia")
  private FamiliaTipoDocumento familiaTipoDocumento;

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
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

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  public Integer getIdTipoDocumentoSade() {
    return idTipoDocumentoSade;
  }

  public void setIdTipoDocumentoSade(Integer idTipoDocumentoSade) {
    this.idTipoDocumentoSade = idTipoDocumentoSade;
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

//  public List<Metadata> getListaDatosVariables() {
//    return listaDatosVariables;
//  }
//
//  public void setListaDatosVariables(List<Metadata> listaDatosVariables) {
//    this.listaDatosVariables = listaDatosVariables;
//  }

  public Date getFechaModificacion() {
    return fechaModificacion;
  }

  public void setFechaModificacion(Date fechaModificacion) {
    this.fechaModificacion = fechaModificacion;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getTipoOperacion() {
    return tipoOperacion;
  }

  public void setTipoOperacion(String tipoOperacion) {
    this.tipoOperacion = tipoOperacion;
  }

//  public Set<ReparticionHabilitada> getListaReparticiones() {
//    return listaReparticiones;
//  }
//
//  public void setListaReparticiones(Set<ReparticionHabilitada> listaReparticiones) {
//    this.listaReparticiones = listaReparticiones;
//  }

  public Boolean getEsFirmaExterna() {
    return esFirmaExterna;
  }

  public void setEsFirmaExterna(Boolean esFirmaExterna) {
    this.esFirmaExterna = esFirmaExterna;
  }

  public FamiliaTipoDocumento getFamiliaTipoDocumento() {
    return familiaTipoDocumento;
  }

  public void setFamiliaTipoDocumento(FamiliaTipoDocumento familiaTipoDocumento) {
    this.familiaTipoDocumento = familiaTipoDocumento;
  }

  public Integer getTipoProduccion() {
    return tipoProduccion;
  }

  public void setTipoProduccion(Integer tipoProduccion) {
    this.tipoProduccion = tipoProduccion;
  }

}
