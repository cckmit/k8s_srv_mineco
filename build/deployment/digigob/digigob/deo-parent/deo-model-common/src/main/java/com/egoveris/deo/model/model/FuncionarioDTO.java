package com.egoveris.deo.model.model;

import java.io.Serializable;

public class FuncionarioDTO implements Serializable{

	private static final long serialVersionUID = -7388550699637782511L;
	
	private String nombre;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
}
