package com.egoveris.vucfront.model.model;

import java.io.Serializable;

public class TipoDocumentoVucDTO implements Serializable {

	private static final long serialVersionUID = -5535003242688372379L;

	private String nombre;
	private String descripcion;
	private String acronimoTad;
	private String acronimoGedo;

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

	public String getAcronimoTad() {
		return acronimoTad;
	}

	public void setAcronimoTad(String acronimoTad) {
		this.acronimoTad = acronimoTad;
	}

	public String getAcronimoGedo() {
		return acronimoGedo;
	}

	public void setAcronimoGedo(String acronimoGedo) {
		this.acronimoGedo = acronimoGedo;
	}

}