package com.egoveris.ccomplejos.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CC_Vista_DOCUMENTO")
public class VistaDocumento extends AbstractCComplejoJPA {


 @Column(name = "EMISOR_DOCUMENTO_COMERCIAL")
 protected String emisorDocumentoComercial;

 @Column(name = "NUMERO_DOCUMENTO_COMERCIAL")
 protected String numeroDocumentoComercial;
 
 @Column(name = "CODIGO_PAIS_EMISOR_DOCUMENTO_COMERCIAL")
 protected String codigoPaisEmisorDocumentoComercial;
 
 @Column(name = "TIPO_DOCUMENTO_COMERCIAL")
 protected String tipoDocumentoComercial;
 
 @Column(name = "FECHA_DOCUMENTO_COMERCIAL")
 protected String fechaDocumentoComercial;
 
 
 
 
 

  public String getEmisorDocumentoComercial() {
    return emisorDocumentoComercial;
  }
  
  public void setEmisorDocumentoComercial(String emisorDocumentoComercial) {
    this.emisorDocumentoComercial = emisorDocumentoComercial;
  }
  
  public String getNumeroDocumentoComercial() {
    return numeroDocumentoComercial;
  }
  
  public void setNumeroDocumentoComercial(String numeroDocumentoComercial) {
    this.numeroDocumentoComercial = numeroDocumentoComercial;
  }
  
  public String getCodigoPaisEmisorDocumentoComercial() {
    return codigoPaisEmisorDocumentoComercial;
  }
  
  public void setCodigoPaisEmisorDocumentoComercial(String codigoPaisEmisorDocumentoComercial) {
    this.codigoPaisEmisorDocumentoComercial = codigoPaisEmisorDocumentoComercial;
  }
  
  public String getTipoDocumentoComercial() {
    return tipoDocumentoComercial;
  }
  
  public void setTipoDocumentoComercial(String tipoDocumentoComercial) {
    this.tipoDocumentoComercial = tipoDocumentoComercial;
  }
  
  public String getFechaDocumentoComercial() {
    return fechaDocumentoComercial;
  }
  
  public void setFechaDocumentoComercial(String fechaDocumentoComercial) {
    this.fechaDocumentoComercial = fechaDocumentoComercial;
  }
 
}
