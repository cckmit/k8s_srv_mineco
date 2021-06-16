package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;


public class VistaDocumentoIngresoOigDTO extends AbstractCComplejoDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  
  protected String oigDocumento;
  protected String nombreDocumento;
  protected String bodegaDestino;
  protected String numeroDocumento;
  protected String fechaDocumento;
  
  
  
  
  public String getOigDocumento() {
    return oigDocumento;
  }
  public void setOigDocumento(String oigDocumento) {
    this.oigDocumento = oigDocumento;
  }
  public String getNombreDocumento() {
    return nombreDocumento;
  }
  public void setNombreDocumento(String nombreDocumento) {
    this.nombreDocumento = nombreDocumento;
  }
  public String getBodegaDestino() {
    return bodegaDestino;
  }
  public void setBodegaDestino(String bodegaDestino) {
    this.bodegaDestino = bodegaDestino;
  }
  public String getNumeroDocumento() {
    return numeroDocumento;
  }
  public void setNumeroDocumento(String numeroDocumento) {
    this.numeroDocumento = numeroDocumento;
  }
  public String getFechaDocumento() {
    return fechaDocumento;
  }
  public void setFechaDocumento(String fechaDocumento) {
    this.fechaDocumento = fechaDocumento;
  }
	
}
