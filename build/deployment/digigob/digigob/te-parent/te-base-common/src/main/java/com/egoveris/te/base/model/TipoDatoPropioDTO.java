
package com.egoveris.te.base.model;

import java.io.Serializable;

public class TipoDatoPropioDTO implements Serializable {

	private static final long serialVersionUID = 7358136192913860265L;
	
	private Long id;
	private String descripcion;
	private String abreviacion;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getAbreviacion() {
		return abreviacion;
	}
	public void setAbreviacion(String abreviacion) {
		this.abreviacion = abreviacion;
	}
}
