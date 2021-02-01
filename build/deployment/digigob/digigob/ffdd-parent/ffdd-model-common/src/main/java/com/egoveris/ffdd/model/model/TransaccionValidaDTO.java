package com.egoveris.ffdd.model.model;

import java.io.Serializable;
import java.util.Map;


public class TransaccionValidaDTO implements Serializable{

	
	private static final long serialVersionUID = -5777127820376919732L;
	private String nombreFormulario;
	private String sistemaOrigen;
	private Map<String, String> mapaValores;	
	
	public String getNombreFormulario() {
		return nombreFormulario;
	}
	public void setNombreFormulario(String nombreFormulario) {
		this.nombreFormulario = nombreFormulario;
	}
	public Map<String, String> getMapaValores() {
		return mapaValores;
	}
	public void setMapaValores(Map<String, String> mapaValores) {
		this.mapaValores = mapaValores;
	}
	public String getSistemaOrigen() {
		return sistemaOrigen;
	}
	public void setSistemaOrigen(String sistemaOrigen) {
		this.sistemaOrigen = sistemaOrigen;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TransaccionValidaDTO [nombreFormulario=").append(nombreFormulario).append(", sistemaOrigen=")
				.append(sistemaOrigen).append(", mapaValores=").append(mapaValores).append("]");
		return builder.toString();
	}	
}