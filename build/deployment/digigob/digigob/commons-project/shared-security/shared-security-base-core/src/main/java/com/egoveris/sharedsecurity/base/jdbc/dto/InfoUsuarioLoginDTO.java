package com.egoveris.sharedsecurity.base.jdbc.dto;

import java.io.Serializable;

public class InfoUsuarioLoginDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5311997079137484934L;
	
	String usuario;
	
	String primerNombre;

	String segundoNombre;

	String tercerNombre;

	
	String primerApellido;

	String segundoApellido;

	String tercerApellido;

	String cargo;
	
	String identificador;
	
	String reparticion;
	
	String sector;
	
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public String getReparticion() {
		return reparticion;
	}

	public void setReparticion(String reparticion) {
		this.reparticion = reparticion;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
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

	public String getTercerNombre() {
		return tercerNombre;
	}

	public void setTercerNombre(String tercerNombre) {
		this.tercerNombre = tercerNombre;
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

	public String getTercerApellido() {
		return tercerApellido;
	}

	public void setTercerApellido(String tercerApellido) {
		this.tercerApellido = tercerApellido;
	}

	public String getCargo() {
		return cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}
	
	

}
