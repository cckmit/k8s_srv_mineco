package com.egoveris.deo.model.model;

import java.io.Serializable;

public class UsuarioPlantillaDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8858789841324574909L;
	
	private Integer id;
	private String usuario;
	private PlantillaDTO plantilla;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public void setPlantilla(PlantillaDTO plantilla) {
		this.plantilla = plantilla;
	}
	public PlantillaDTO getPlantilla() {
		return plantilla;
	}
	
}
