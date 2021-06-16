package com.egoveris.ccomplejos.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CC_VISTA_DESTINO_DOC_ING_ENVIO")
public class VistaDestinoDocIngEnvio extends AbstractCComplejoJPA {

  

  @Column(name = "PATENTE")
  protected String patente;
  
  @Column(name = "RUTA")
  protected String ruta;
  
  @Column(name = "TIPO_MEDIO_TRANSPORTE")
  protected String tipoMedioTransporte;
  
  @Column(name = "FECHA_ESTIMADA_LLEGADA")
  protected Date fechaEstimadaLlegada;
  
  @Column(name = "NOMBRE_TRANSPORTISTA")
  protected String nombreTransportista;
  
  @Column(name = "TIPO_NOMBRE")
  protected String tipoNombre;
  
  @Column(name = "RUT_TRANSPORTISTA")
  protected String rutTransportista;
  
  @Column(name = "PASAPORTE")
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
