package com.egoveris.deo.model.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ResponseExternalConsultaDocumentoResponse implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 2537607045247555112L;

  private Boolean puedeVerDocumento;

  private String referencia;
  private String numeroSade;
  private String numeroEspecial;
  private String urlArchivo;
  private List<String> listaFirmantes;
  private Date fechaCreacion;
  private String tipoDocumento;
  private List<DocumentoMetadataResponse> datosPropios;
  private List<ArchivoDeTrabajoResponse> listaArchivosDeTrabajo;
  private List<HistorialResponse> listaHistorial;

  // Getters y Setters
  public String getReferencia() {
    return referencia;
  }

  public void setReferencia(String referencia) {
    this.referencia = referencia;
  }

  public String getNumeroSade() {
    return numeroSade;
  }

  public void setNumeroSade(String numeroSade) {
    this.numeroSade = numeroSade;
  }

  public String getNumeroEspecial() {
    return numeroEspecial;
  }

  public void setNumeroEspecial(String numeroEspecial) {
    this.numeroEspecial = numeroEspecial;
  }

  public List<String> getListaFirmantes() {
    return listaFirmantes;
  }

  public void setListaFirmantes(List<String> listaFirmantes) {
    this.listaFirmantes = listaFirmantes;
  }

  public Date getFechaCreacion() {
    return fechaCreacion;
  }

  public void setFechaCreacion(Date fechaCreacion) {
    this.fechaCreacion = fechaCreacion;
  }

  public String getTipoDocumento() {
    return tipoDocumento;
  }

  public void setTipoDocumento(String tipoDocumento) {
    this.tipoDocumento = tipoDocumento;
  }

  public List<DocumentoMetadataResponse> getDatosPropios() {
    return datosPropios;
  }

  public void setDatosPropios(List<DocumentoMetadataResponse> datosPropios) {
    this.datosPropios = datosPropios;
  }

  public void setListaArchivosDeTrabajo(List<ArchivoDeTrabajoResponse> listaArchivosDeTrabajo) {
    this.listaArchivosDeTrabajo = listaArchivosDeTrabajo;
  }

  public List<ArchivoDeTrabajoResponse> getListaArchivosDeTrabajo() {
    return listaArchivosDeTrabajo;
  }

  public void setPuedeVerDocumento(Boolean puedeVerDocumento) {
    this.puedeVerDocumento = puedeVerDocumento;
  }

  public Boolean getPuedeVerDocumento() {
    return puedeVerDocumento;
  }

  public void setListaHistorial(List<HistorialResponse> listaHistorial) {
    this.listaHistorial = listaHistorial;
  }

  public List<HistorialResponse> getListaHistorial() {
    return listaHistorial;
  }

  public String getUrlArchivo() {
    return urlArchivo;
  }

  public void setUrlArchivo(String urlArchivo) {
    this.urlArchivo = urlArchivo;
  }

}
