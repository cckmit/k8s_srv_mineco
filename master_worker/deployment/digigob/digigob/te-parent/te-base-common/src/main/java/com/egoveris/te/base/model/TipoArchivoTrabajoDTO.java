package com.egoveris.te.base.model;

import java.io.Serializable;

/**
 * Tipos de Archivo de Trabajo
 *
 * @author everis
 *
 */
public class TipoArchivoTrabajoDTO implements Serializable {

	/**
	* 
	*/
	private static final long serialVersionUID = -7429272917576863238L;
	private Long id;
	private String nombre;
	private String descripcion;
	private boolean repetible;

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

	public boolean isRepetible() {
		return repetible;
	}

	public void setRepetible(boolean repetible) {
		this.repetible = repetible;
	}

}
