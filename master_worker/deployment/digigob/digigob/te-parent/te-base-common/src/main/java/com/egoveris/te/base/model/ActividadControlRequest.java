package com.egoveris.te.base.model;

import java.io.Serializable;

public class ActividadControlRequest implements Serializable {

	private static final long serialVersionUID = -5812966925123740873L;
	
	private String estado;
	private String nombreUsuario;
	private String numeroExpediente;
	private String idObjetivo;
	private String tipoActividad;

	public String getNumeroExpediente() {
		return numeroExpediente;
	}

	public void setNumeroExpediente(String numeroExpediente) {
		this.numeroExpediente = numeroExpediente;
	}

	public void setIdObjetivo(String idObjetivo) {
		this.idObjetivo = idObjetivo;
	}

	public String getIdObjetivo() {
		return idObjetivo;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getEstado() {
		return estado;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}
	
	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setTipoActividad(String tipoActividad) {
		this.tipoActividad = tipoActividad;
	}

	public String getTipoActividad() {
		return tipoActividad;
	}
}
