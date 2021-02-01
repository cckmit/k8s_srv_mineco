package com.egoveris.ffdd.model.model;

import java.io.Serializable;

public class ComponenteMultilineaDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2964156702100033498L;

	private Integer id;
	private Integer idFormulario;
	private Integer idFormComp;
	private String texto;

	public Integer getId() {
		return id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public Integer getIdFormulario() {
		return idFormulario;
	}

	public void setIdFormulario(final Integer idFormulario) {
		this.idFormulario = idFormulario;
	}

	public Integer getIdFormComp() {
		return idFormComp;
	}

	public void setIdFormComp(final Integer idFormComp) {
		this.idFormComp = idFormComp;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(final String texto) {
		this.texto = texto;
	}
}