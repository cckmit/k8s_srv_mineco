package com.egoveris.te.model.model;

import java.io.Serializable;

public class GuardaTempRequest implements Serializable {

	private static final long serialVersionUID = -5812966925123740873L;

	private String motivo;
	private String numeroExpediente;
	private String tipo;
	private String valor;
	private String userMailDestino;

	public String getNumeroExpediente() {
		return numeroExpediente;
	}

	public void setNumeroExpediente(String numeroExpediente) {
		this.numeroExpediente = numeroExpediente;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
}

	public String getTipo() {
		return tipo;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getValor() {
		return valor;
	}
	
	public void setUserMailDestino(String userMailDestino) {
		this.userMailDestino = userMailDestino;
	}

	public String getUserMailDestino() {
		return userMailDestino;
	}
}
