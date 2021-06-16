package com.egoveris.te.base.model;

import java.io.Serializable;

public class TarjetaDatosUsuarioBean implements Serializable {

	private String user;
	private String apellidoNombre;
	private String ocupacion;
	// private String mail;
	private String codigoReparticion;
	private String descripcionReparticion;
	private String codigoSector;
	private String descripcionSector;

	public void setUser(final String user) {
		this.user = user;
	}

	public String getUser() {
		return user;
	}

	public void setApellidoNombre(final String apellidoNombre) {
		this.apellidoNombre = apellidoNombre;
	}

	public String getApellidoNombre() {
		return apellidoNombre;
	}

	public void setOcupacion(final String ocupacion) {
		this.ocupacion = ocupacion;
	}

	public String getOcupacion() {
		return ocupacion;
	}

	// public void setMail(String mail) {
	// this.mail = mail;
	// }
	// public String getMail() {
	// return mail;
	// }
	public void setCodigoReparticion(final String codigoReparticion) {
		this.codigoReparticion = codigoReparticion;
	}

	public String getCodigoReparticion() {
		return codigoReparticion;
	}

	public void setDescripcionReparticion(final String descripcionReparticion) {
		this.descripcionReparticion = descripcionReparticion;
	}

	public String getDescripcionReparticion() {
		return descripcionReparticion;
	}

	public void setCodigoSector(final String codigoSector) {
		this.codigoSector = codigoSector;
	}

	public String getCodigoSector() {
		return codigoSector;
	}

	public void setDescripcionSector(final String descripcionSector) {
		this.descripcionSector = descripcionSector;
	}

	public String getDescripcionSector() {
		return descripcionSector;
	}

}
