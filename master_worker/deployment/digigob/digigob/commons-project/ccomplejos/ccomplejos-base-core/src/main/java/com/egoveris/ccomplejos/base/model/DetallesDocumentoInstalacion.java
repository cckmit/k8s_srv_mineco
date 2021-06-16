package com.egoveris.ccomplejos.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CC_DETALLES_DOCUMENTO_INSTALACION")
public class DetallesDocumentoInstalacion extends AbstractCComplejoJPA {

 @Column(name = "NOMBRE_SSPP")
 protected String nombreSSPP;
  
 @Column(name = "CODIGO_DOCUMENTO")
 protected String codigoDocumento;
  
 @Column(name = "NOMBRE_DOCUMENTO")
 protected String nombreDocumento;
  
 @Column(name = "ID_SOLICITUD")
 protected String idSolicitud;
  
 @Column(name = "NOMBRE_INSTALACION")
 protected String nombreInstalacion;
  
 @Column(name = "REGION_INSTALACION")
 protected String regionInstalacion;
  
 @Column(name = "COMUNA_INSTALACION")
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
