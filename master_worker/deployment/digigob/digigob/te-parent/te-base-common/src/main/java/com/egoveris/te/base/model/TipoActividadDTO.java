package com.egoveris.te.base.model;

import java.io.Serializable;

public class TipoActividadDTO implements Serializable {

	private static final long serialVersionUID = 5140541324409273890L;

	private Long id;
	private String nombre;
	private String descripcion;
	private String form;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getForm() {
		return form;
	}

	public void setForm(String form) {
		this.form = form;
	}
	
}
