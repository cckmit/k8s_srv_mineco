package com.egoveris.te.model.model;

import java.io.Serializable;

public class SolicitanteResponse implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 931495802624503647L;
	private String tipoDeIniciador;
	private String primerNombre;
	private String segundoNombre;
	private String primerApellido;
	private String segundoApellido;
	private String domicilio;
	private String numero;
	private String tipoDocumento;
	private String numeroDocumento;
	private String razonSocial;
	
	private String cuitCuil;
	private String piso;
	private String departamento;
	private String codigoPostal;
	private String email;
	private String telefono;
	
	
	
	public String getTipoDeIniciador() {
		return tipoDeIniciador;
	}
	public void setTipoDeIniciador(String tipoDeIniciador) {
		this.tipoDeIniciador = tipoDeIniciador;
	}
	public String getPrimerNombre() {
		return primerNombre;
	}
	public void setPrimerNombre(String primerNombre) {
		this.primerNombre = primerNombre;
	}
	public String getSegundoNombre() {
		return segundoNombre;
	}
	public void setSegundoNombre(String segundoNombre) {
		this.segundoNombre = segundoNombre;
	}
	public String getPrimerApellido() {
		return primerApellido;
	}
	public void setPrimerApellido(String primerApellido) {
		this.primerApellido = primerApellido;
	}
	public String getSegundoApellido() {
		return segundoApellido;
	}
	public void setSegundoApellido(String segundoApellido) {
		this.segundoApellido = segundoApellido;
	}
	
	
	
	public String getDomicilio() {
		return domicilio;
	}
	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public String getNumeroDocumento() {
		return numeroDocumento;
	}
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}
	public String getRazonSocial() {
		return razonSocial;
	}
	public void setCuitCuil(String cuitCuil) {
		this.cuitCuil = cuitCuil;
	}
	public String getCuitCuil() {
		return cuitCuil;
	}
	public void setPiso(String piso) {
		this.piso = piso;
	}
	public String getPiso() {
		return piso;
	}
	public void setDepartamento(String departamento) {
		this.departamento = departamento;
	}
	public String getDepartamento() {
		return departamento;
	}
	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
	public String getCodigoPostal() {
		return codigoPostal;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmail() {
		return email;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getTelefono() {
		return telefono;
	}
	
	
	
}
