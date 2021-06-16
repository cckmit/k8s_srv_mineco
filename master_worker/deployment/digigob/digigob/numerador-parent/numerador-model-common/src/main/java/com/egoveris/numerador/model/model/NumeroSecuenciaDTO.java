package com.egoveris.numerador.model.model;

import java.io.Serializable;


public class NumeroSecuenciaDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2470659919470719740L;
	private Integer anio;
	private Integer numero;
	private String secuencia;
	
	public Integer getAnio() {
		return anio;
	}
	public void setAnio(Integer anio) {
		this.anio = anio;
	}
	public Integer getNumero() {
		return numero;
	}
	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	public String getSecuencia() {
		return secuencia;
	}
	public void setSecuencia(String secuencia) {
		this.secuencia = secuencia;
	}
	
	

}
