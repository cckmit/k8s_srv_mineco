package com.egoveris.ffdd.model.model;

import java.io.Serializable;

public class ComplexData implements Serializable {

	private static final long serialVersionUID = 6963359492970115780L;

	private String codigo;
	private String nombre;
	
	public ComplexData() {
		
	}

	public ComplexData(String codigo, String nombre) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ComplexData [codigo=").append(codigo).append(", nombre=").append(nombre).append("]");
		return builder.toString();
	}

}
