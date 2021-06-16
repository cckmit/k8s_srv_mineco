package com.egoveris.te.base.model;

import java.io.Serializable;

public class MetadataDTO implements Serializable{

	private static final long serialVersionUID = -1507000568714143273L;
	private String nombre;
	private boolean obligatoriedad;
	private Integer tipo;
	private int orden;
	 
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public boolean isObligatoriedad() {
		return obligatoriedad;
	}
	public void setObligatoriedad(boolean obligatoriedad) {
		this.obligatoriedad = obligatoriedad;
	}
	public Integer getTipo() {
		return tipo;
	}
	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}
	public int getOrden() {
		return orden;
	}
	public void setOrden(int orden) {
		this.orden = orden;
	}
	
	public String toString(){
		return nombre;
	}
}
