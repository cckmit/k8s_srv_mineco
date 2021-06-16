package com.egoveris.deo.model.model;

import java.io.Serializable;

public class ResponseGenerarDocumento implements Serializable{

	private static final long serialVersionUID = 6739406563842269604L;

	private boolean resultado;
	private String numero;
	private NumeracionEspecialDTO numeroEspecial;
	private String codigoReparticion;
	private String urlArchivoGenerado;
	
	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public boolean isResultado() {
		return resultado;
	}

	public void setResultado(boolean resultado) {
		this.resultado = resultado;
	}

	public String getCodigoReparticion() {
		return codigoReparticion;
	}

	public void setCodigoReparticion(String codigoReparticion) {
		this.codigoReparticion = codigoReparticion;
	}

	public NumeracionEspecialDTO getNumeroEspecial() {
		return numeroEspecial;
	}

	public void setNumeroEspecial(NumeracionEspecialDTO numeroEspecial) {
		this.numeroEspecial = numeroEspecial;
	}
	
	
	public void setUrlArchivoGenerado(String urlArchivoGenerado) {
		this.urlArchivoGenerado = urlArchivoGenerado;
	}

	public String getUrlArchivoGenerado() {
		return urlArchivoGenerado;
	}
}
