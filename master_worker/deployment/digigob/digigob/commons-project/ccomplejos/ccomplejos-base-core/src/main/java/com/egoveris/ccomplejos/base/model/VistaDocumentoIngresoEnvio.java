package com.egoveris.ccomplejos.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CC_VISTA_DOCUMENTO_INGRESO_ENVIO")
public class VistaDocumentoIngresoEnvio extends AbstractCComplejoJPA {

	
 @Column(name = "NUMERO_DOCUMENTO_INGRESO")
 protected String numeroDocumentoIngreso;
 
 @Column(name = "TIPO_SOLICITUD")
 protected String tipoSolicitud;
 
 @Column(name = "FECHA_SOLICITUD")
 protected Date fechaSolicitud;
 
 @Column(name = "FECHA_DOC_INGRESO")
 protected Date fechaDocIngreso;
 
 @Column(name = "ID_SOLICITUD")
 protected String idSolicitud;
 
 @Column(name = "CODI_SERVICIOSPUBLICOS_RELACIONADOS")
 protected String codiserviciosPublicosRelacionados;
 
 @Column(name = "TOTAL_BULTOS")
 protected Integer totalBultos;
 
 @Column(name = "ID_BULTOS")
 protected String idBultos;
 
 
 
 

  public String getNumeroDocumentoIngreso() {
    return numeroDocumentoIngreso;
  }
  
  public void setNumeroDocumentoIngreso(String numeroDocumentoIngreso) {
    this.numeroDocumentoIngreso = numeroDocumentoIngreso;
  }
  
  public String getTipoSolicitud() {
    return tipoSolicitud;
  }
  
  public void setTipoSolicitud(String tipoSolicitud) {
    this.tipoSolicitud = tipoSolicitud;
  }
  
  public Date getFechaSolicitud() {
    return fechaSolicitud;
  }
  
  public void setFechaSolicitud(Date fechaSolicitud) {
    this.fechaSolicitud = fechaSolicitud;
  }
  
  public Date getFechaDocIngreso() {
    return fechaDocIngreso;
  }
  
  public void setFechaDocIngreso(Date fechaDocIngreso) {
    this.fechaDocIngreso = fechaDocIngreso;
  }
  
  public String getIdSolicitud() {
    return idSolicitud;
  }
  
  public void setIdSolicitud(String idSolicitud) {
    this.idSolicitud = idSolicitud;
  }
  
  public String getCodiserviciosPublicosRelacionados() {
    return codiserviciosPublicosRelacionados;
  }
  
  public void setCodiserviciosPublicosRelacionados(String codiserviciosPublicosRelacionados) {
    this.codiserviciosPublicosRelacionados = codiserviciosPublicosRelacionados;
  }
  
  public Integer getTotalBultos() {
    return totalBultos;
  }
  
  public void setTotalBultos(Integer totalBultos) {
    this.totalBultos = totalBultos;
  }
  
  public String getIdBultos() {
    return idBultos;
  }
  
  public void setIdBultos(String idBultos) {
    this.idBultos = idBultos;
  }
  

}
