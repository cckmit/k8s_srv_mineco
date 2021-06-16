package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;
import java.util.Date;

public class VistaDestinoDocIngEnvioDTO extends AbstractCComplejoDTO implements Serializable {


  private static final long serialVersionUID = 1L;
  
  
  protected String patente;
  protected String ruta;
  protected String tipoMedioTransporte;
  protected Date fechaEstimadaLlegada;
  protected String nombreTransportista;
  protected String tipoNombre;
  protected String rutTransportista;
  protected String pasaporte;
  
  
  
  
  public String getPatente() {
    return patente;
  }
  public void setPatente(String patente) {
    this.patente = patente;
  }
  public String getRuta() {
    return ruta;
  }
  public void setRuta(String ruta) {
    this.ruta = ruta;
  }
  public String getTipoMedioTransporte() {
    return tipoMedioTransporte;
  }
  public void setTipoMedioTransporte(String tipoMedioTransporte) {
    this.tipoMedioTransporte = tipoMedioTransporte;
  }
  public Date getFechaEstimadaLlegada() {
    return fechaEstimadaLlegada;
  }
  public void setFechaEstimadaLlegada(Date fechaEstimadaLlegada) {
    this.fechaEstimadaLlegada = fechaEstimadaLlegada;
  }
  public String getNombreTransportista() {
    return nombreTransportista;
  }
  public void setNombreTransportista(String nombreTransportista) {
    this.nombreTransportista = nombreTransportista;
  }
  public String getTipoNombre() {
    return tipoNombre;
  }
  public void setTipoNombre(String tipoNombre) {
    this.tipoNombre = tipoNombre;
  }
  public String getRutTransportista() {
    return rutTransportista;
  }
  public void setRutTransportista(String rutTransportista) {
    this.rutTransportista = rutTransportista;
  }
  public String getPasaporte() {
    return pasaporte;
  }
  public void setPasaporte(String pasaporte) {
    this.pasaporte = pasaporte;
  }

}
