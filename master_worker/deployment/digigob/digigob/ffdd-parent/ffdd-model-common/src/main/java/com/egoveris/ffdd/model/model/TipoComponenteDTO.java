package com.egoveris.ffdd.model.model;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

public class TipoComponenteDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 905898220823167545L;

	private Integer id;
	private String nombre;
	private String descripcion;
	private String factory;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	/**
	 * @return the factory
	 */
	public String getFactory() {
		return factory;
	}

	/**
	 * @param factory
	 *            the factory to set
	 */
	public void setFactory(String factory) {
		this.factory = factory;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}