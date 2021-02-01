package com.egoveris.te.model.model;

import java.io.Serializable;
import java.util.Date;

public class ExpedientesVinculadosResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7734693510522769074L;
	/**
	 * 
	 */
	
	
	private String codigoExpediente;
	private Date fechaVinculacion;
	private String usuarioVinculador;
	private String estadoExpedienteVinculado;
	private String trataExpedienteVinculado;
	private String descTrataExVinculado;
	public String getCodigoExpediente() {
		return codigoExpediente;
	}
	public Date getFechaVinculacion() {
		return fechaVinculacion;
	}
	public String getUsuarioVinculador() {
		return usuarioVinculador;
	}
	public String getEstadoExpedienteVinculado() {
		return estadoExpedienteVinculado;
	}
	public String getTrataExpedienteVinculado() {
		return trataExpedienteVinculado;
	}
	public String getDescTrataExVinculado() {
		return descTrataExVinculado;
	}
	public void setCodigoExpediente(String codigoExpediente) {
		this.codigoExpediente = codigoExpediente;
	}
	public void setFechaVinculacion(Date fechaVinculacion) {
		this.fechaVinculacion = fechaVinculacion;
	}
	public void setUsuarioVinculador(String usuarioVinculador) {
		this.usuarioVinculador = usuarioVinculador;
	}
	public void setEstadoExpedienteVinculado(String estadoExpedienteVinculado) {
		this.estadoExpedienteVinculado = estadoExpedienteVinculado;
	}
	public void setTrataExpedienteVinculado(String trataExpedienteVinculado) {
		this.trataExpedienteVinculado = trataExpedienteVinculado;
	}
	public void setDescTrataExVinculado(String descTrataExVinculado) {
		this.descTrataExVinculado = descTrataExVinculado;
	}
}
