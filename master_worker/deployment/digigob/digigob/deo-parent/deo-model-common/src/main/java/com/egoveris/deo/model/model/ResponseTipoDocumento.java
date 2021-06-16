package com.egoveris.deo.model.model;

import java.io.Serializable;
import java.util.List;

public class ResponseTipoDocumento implements Serializable {
  private static final long serialVersionUID = 912064965791638338L;
  private Integer id;
  private String nombre;
  private String acronimo;
  private String descripcion;
  private Boolean esEspecial;
  private Boolean esManual;
  private Boolean esAutomatica;
  private Boolean tieneToken;
  private Boolean tieneTemplate;
  private Boolean esEmbebido;
  private Boolean esComunicable;
  private String tipoProduccion;
  private String codigoTipoDocumentoSade;
  private Boolean esFirmaConjunta;
  private Boolean esConfidencial;
  private List<ResponseMetadata> listaDatosVariables;
  private String estado;
  private Boolean esFirmaExterna;
  private Boolean esNotificable;
  private String familia;
  private String idFormulario;
  private Integer idTipoDocumentoSade;
  private Boolean resultado;

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

  public Boolean getEsFirmaExterna() {
    return esFirmaExterna;
  }

  public void setEsFirmaExterna(Boolean esFirmaExterna) {
    this.esFirmaExterna = esFirmaExterna;
  }

  public Boolean getEsNotificable() {
    return esNotificable;
  }

  public void setEsNotificable(Boolean esNotificable) {
    this.esNotificable = esNotificable;
  }

  public String getFamilia() {
    return familia;
  }

  public void setFamilia(String familia) {
    this.familia = familia;
  }

  public List<ResponseMetadata> getListaDatosVariables() {
    return listaDatosVariables;
  }

  public void setListaDatosVariables(List<ResponseMetadata> listaDatosVariables) {
    this.listaDatosVariables = listaDatosVariables;
  }

  public String getEstado() {
    return estado;
  }

  public void setEstado(String estado) {
    this.estado = estado;
  }

  // public Integer getTipoProduccion() {
  // return tipoProduccion;
  // }
  // public void setTipoProduccion(Integer tipoProduccion) {
  // this.tipoProduccion = tipoProduccion;
  // }
  public String getIdFormulario() {
    return idFormulario;
  }

  public void setIdFormulario(String idFormulario) {
    this.idFormulario = idFormulario;
  }

  public String getTipoProduccion() {
    return tipoProduccion;
  }

  public void setTipoProduccion(String tipoProduccion) {
    this.tipoProduccion = tipoProduccion;
  }

  public Boolean getEsEmbebido() {
    return esEmbebido;
  }

  public void setEsEmbebido(Boolean esEmbebido) {
    this.esEmbebido = esEmbebido;
  }

  public Boolean getEsComunicable() {
    return esComunicable;
  }

  public void setEsComunicable(Boolean esComunicable) {
    this.esComunicable = esComunicable;
  }

  public Integer getIdTipoDocumentoSade() {
    return idTipoDocumentoSade;
  }

  public void setIdTipoDocumentoSade(Integer idTipoDocumentoSade) {
    this.idTipoDocumentoSade = idTipoDocumentoSade;
  }

  public Boolean getResultado() {
    return resultado;
  }

  public void setResultado(Boolean resultado) {
    this.resultado = resultado;
  }

}