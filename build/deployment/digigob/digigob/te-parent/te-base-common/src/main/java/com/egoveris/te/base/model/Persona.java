package com.egoveris.te.base.model;

import java.io.Serializable;
/**
 * Datos relevantes de una persona para el negocio
 * 
 * @author rgalloci
 *
 */
public class Persona implements Serializable{
	private static final long serialVersionUID = 7600488218666839992L;
	private Integer id;	
	private String nombre;
	private String apellido;
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
}
