package com.egoveris.ffdd.model.model;

import java.io.Serializable;
import java.util.Arrays;

public class ArchivoDTO implements Serializable {

	private static final long serialVersionUID = 2L;
	
	
	private String nombre; 
	private byte[] data;
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ArchivoDTO [nombre=");
		builder.append(nombre);
		builder.append(", data=");
		builder.append(Arrays.toString(data));
		builder.append("]");
		return builder.toString();
	}
	
	
}