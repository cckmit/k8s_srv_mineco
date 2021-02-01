package com.egoveris.te.model.model;

import java.io.Serializable;

public class ExpedienteAsociadoDTO implements Serializable { 
	/**
	 * Objeto que con tiene el numero Sade de Expediente Asociada 
	 * mas la informacion si es cabecera o no
	 * */
	private static final long serialVersionUID = 1L;
	private String numeroSade;
	private Boolean esCabecera;
	
	public String getNumeroSade() {
		return numeroSade;
	}
	public void setNumeroSade(String numeroSade) {
		this.numeroSade = numeroSade;
	}
	public Boolean getEsCabecera() {
		return esCabecera;
	}
	public void setEsCabecera(Boolean esCabecera) {
		this.esCabecera = esCabecera;
	}
}
