package com.egoveris.ccomplejos.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "view_participantesda")
public class VistaParticipantesDA extends AbstractViewCComplejoJPA {


 @Column(name="NOMBRE_PARTICIPANTE")
 protected String nombreParticipante;
  
 @Column(name="CODIGO_DESPACHO")
 protected String codigoDespacho;
  
 @Column(name="DESPACHADOR_CODIGO")
 protected String despachadorCodigo;
  
 @Column(name="INDICA_TIPO_DOCUMENTO")
 protected String indicaTipoDocumentoIdentificacion;
  
 @Column(name="RUT")
 protected String rut;
  
 @Column(name="PORCENTAJE")
 protected String porcentaje;
  
 @Column(name="EMAIL")
 protected String email;
  
 @Column(name="DIRECCION")
 protected String direccion;
  
 @Column(name="COMUNA")
 protected String comuna;
 
 @Column(name="REGION")
 protected String region;
 
 @Column(name="NUMERO_FIJO")
 protected String numeroTelefonoFijo;
 
 @Column(name="NUMERO_MOVIL")
 protected String numeroTelefonoMovil;
 
 @Column(name="PAIS")
 protected String pais;

 

  public String getNombre() {
    return nombreParticipante;
  }
  
  public void setNombre(String nombreParticipante) {
    this.nombreParticipante = nombreParticipante;
  }
  
  public String getCodigoDespacho() {
    return codigoDespacho;
  }
  
  public void setCodigoDespacho(String codigoDespacho) {
    this.codigoDespacho = codigoDespacho;
  }
  
  public String getDespachadorCodigo() {
    return despachadorCodigo;
  }
  
  public void setDespachadorCodigo(String despachadorCodigo) {
    this.despachadorCodigo = despachadorCodigo;
  }
  
  public String getIndicaTipoDocumentoIdentificacion() {
    return indicaTipoDocumentoIdentificacion;
  }
  
  public void setIndicaTipoDocumentoIdentificacion(String indicaTipoDocumentoIdentificacion) {
    this.indicaTipoDocumentoIdentificacion = indicaTipoDocumentoIdentificacion;
  }
  
  public String getRut() {
    return rut;
  }
  
  public void setRut(String rut) {
    this.rut = rut;
  }
  
  public String getPorcentaje() {
    return porcentaje;
  }
  
  public void setPorcentaje(String porcentaje) {
    this.porcentaje = porcentaje;
  }
  
  public String getEmail() {
    return email;
  }
  
  public void setEmail(String email) {
    this.email = email;
  }
  
  public String getDireccion() {
    return direccion;
  }
  
  public void setDireccion(String direccion) {
    this.direccion = direccion;
  }
  
  public String getComuna() {
    return comuna;
  }
  
  public void setComuna(String comuna) {
    this.comuna = comuna;
  }

  public String getRegion() {
    return region;
  }
  
  public void setRegion(String region) {
    this.region = region;
  }
  
  public String getNumeroTelefonoFijo() {
    return numeroTelefonoFijo;
  }
  
  public void setNumeroTelefonoFijo(String numeroTelefonoFijo) {
    this.numeroTelefonoFijo = numeroTelefonoFijo;
  }
  
  public String getNumeroTelefonoMovil() {
    return numeroTelefonoMovil;
  }
  
  public void setNumeroTelefonoMovil(String numeroTelefonoMovil) {
    this.numeroTelefonoMovil = numeroTelefonoMovil;
  }
  
  public String getPais() {
    return pais;
  }
  
  public void setPais(String pais) {
    this.pais = pais;
  }
 
}