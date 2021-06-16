package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;
import java.util.Date;

public class ResumenOpDusDTO extends AbstractCComplejoDTO implements Serializable {

 
  private static final long serialVersionUID = 1L;

  protected String codOperacion;
  protected String destinacionAduanera;
  protected Date fechaCreacion;
  protected String processingStatus;
  
  
  
  
  
  public String getCodOperacion() {
    return codOperacion;
  }
  public void setCodOperacion(String codOperacion) {
    this.codOperacion = codOperacion;
  }
  public String getDestinacionAduanera() {
    return destinacionAduanera;
  }
  public void setDestinacionAduanera(String destinacionAduanera) {
    this.destinacionAduanera = destinacionAduanera;
  }
  public Date getFechaCreacion() {
    return fechaCreacion;
  }
  public void setFechaCreacion(Date fechaCreacion) {
    this.fechaCreacion = fechaCreacion;
  }
  public String getProcessingStatus() {
    return processingStatus;
  }
  public void setProcessingStatus(String processingStatus) {
    this.processingStatus = processingStatus;
  }

}
