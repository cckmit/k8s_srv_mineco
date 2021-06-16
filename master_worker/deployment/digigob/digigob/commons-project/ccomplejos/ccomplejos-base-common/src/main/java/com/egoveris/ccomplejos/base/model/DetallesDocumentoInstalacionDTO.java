package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;


public class DetallesDocumentoInstalacionDTO extends AbstractCComplejoDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  protected String nombreSSPP;
  protected String codigoDocumento;
  protected String nombreDocumento;
  protected String idSolicitud;
  protected String nombreInstalacion;
  protected String regionInstalacion;
  protected String comunaInstalacion;
  
  
  
  
  public String getNombreSSPP() {
    return nombreSSPP;
  }
  public void setNombreSSPP(String nombreSSPP) {
    this.nombreSSPP = nombreSSPP;
  }
  public String getCodigoDocumento() {
    return codigoDocumento;
  }
  public void setCodigoDocumento(String codigoDocumento) {
    this.codigoDocumento = codigoDocumento;
  }
  public String getNombreDocumento() {
    return nombreDocumento;
  }
  public void setNombreDocumento(String nombreDocumento) {
    this.nombreDocumento = nombreDocumento;
  }
  public String getIdSolicitud() {
    return idSolicitud;
  }
  public void setIdSolicitud(String idSolicitud) {
    this.idSolicitud = idSolicitud;
  }
  public String getNombreInstalacion() {
    return nombreInstalacion;
  }
  public void setNombreInstalacion(String nombreInstalacion) {
    this.nombreInstalacion = nombreInstalacion;
  }
  public String getRegionInstalacion() {
    return regionInstalacion;
  }
  public void setRegionInstalacion(String regionInstalacion) {
    this.regionInstalacion = regionInstalacion;
  }
  public String getComunaInstalacion() {
    return comunaInstalacion;
  }
  public void setComunaInstalacion(String comunaInstalacion) {
    this.comunaInstalacion = comunaInstalacion;
  }

}
