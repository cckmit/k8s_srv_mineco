package com.egoveris.te.base.model;

public class EstructuraBean {

	private String nombre;
	private Integer id;
	
	public String getNombre() {
		return nombre;
	}
	
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String toString(){
		return this.nombre;
	}
	
	
	
	
}
