package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;


public class VistaParticipantesDaDTO extends AbstractCComplejoDTO implements Serializable {

  private static final long serialVersionUID = 1L;
  
  protected String nombreParticipante;
  protected String codigoDespacho;
  protected String despachadorCodigo;
  protected String indicaTipoDocumentoIdentificacion;
  protected String rut;
  protected String porcentaje;
  protected String email;
  protected String direccion;
  protected String comuna;
	// protected ParticipantesDTO participante;
  protected String region;
  protected String numeroTelefonoFijo;
  protected String numeroTelefonoMovil;
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

	// public ParticipantesDTO getParticipante() {
	// return participante;
	// }
	// public void setParticipante(ParticipantesDTO participante) {
	// this.participante = participante;
	// }
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
