package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;
import java.util.Date;

public class VistaDocumentoIngresoEnvioDTO extends AbstractCComplejoDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  
  
  protected String numeroDocumentoIngreso;
  protected String tipoSolicitud;
  protected Date fechaSolicitud;
  protected Date fechaDocIngreso;
  protected String idSolicitud;
  protected String codiserviciosPublicosRelacionados;
  protected Integer totalBultos;
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
