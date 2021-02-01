package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;

public class VistaDocumentoDTO  extends AbstractCComplejoDTO implements Serializable{


  private static final long serialVersionUID = 1L;
	

  protected String emisorDocumentoComercial;
  protected String numeroDocumentoComercial;
  protected String codigoPaisEmisorDocumentoComercial;
  protected String tipoDocumentoComercial;
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
