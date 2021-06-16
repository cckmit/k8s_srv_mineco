package com.egoveris.te.model.model;

import java.io.Serializable;
import java.util.Date;

public class ExpedienteAsociadoResponse implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 6178506150621226651L;
	/**
	 * 
	 */
	
	private String codigoExpediente;
	private Date fechaAsociacion;
	private String usuarioAsociador;
	private String estadoExpedienteAsociado;
	private String trataExpedienteASociado;
	private String descTrataExAsociado;
	public String getCodigoExpediente() {
		return codigoExpediente;
	}
	public Date getFechaAsociacion() {
		return fechaAsociacion;
	}
	public String getUsuarioAsociador() {
		return usuarioAsociador;
	}
	public String getEstadoExpedienteAsociado() {
		return estadoExpedienteAsociado;
	}
	public void setCodigoExpediente(String codigoExpediente) {
		this.codigoExpediente = codigoExpediente;
	}
	public void setFechaAsociacion(Date fechaAsociacion) {
		this.fechaAsociacion = fechaAsociacion;
	}
	public void setUsuarioAsociador(String usuarioAsociador) {
		this.usuarioAsociador = usuarioAsociador;
	}
	public void setEstadoExpedienteAsociado(String estadoExpedienteAsociado) {
		this.estadoExpedienteAsociado = estadoExpedienteAsociado;
	}
	public String getTrataExpedienteASociado() {
		return trataExpedienteASociado;
	}
	public String getDescTrataExAsociado() {
		return descTrataExAsociado;
	}
	public void setTrataExpedienteASociado(String trataExpedienteASociado) {
		this.trataExpedienteASociado = trataExpedienteASociado;
	}
	public void setDescTrataExAsociado(String descTrataExAsociado) {
		this.descTrataExAsociado = descTrataExAsociado;
	}
	
	
}
