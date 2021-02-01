package com.egoveris.ccomplejos.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CC_VISTA_DOCUMENTO_INGRESO_OIG")
public class VistaDocumentoIngresoOIG extends AbstractCComplejoJPA {



 @Column(name = "OIG_DOCUMENTO")
 protected String oigDocumento;
  
 @Column(name = "NOMBRE_DOCUMENTO")
 protected String nombreDocumento;
  
 @Column(name = "BODEGA_DESTINO")
 protected String bodegaDestino;
  
 @Column(name = "NUMERO_DOCUMENTO")
 protected String numeroDocumento;
  
 @Column(name = "FECHA_DOCUMENTO")
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
