package com.egoveris.ffdd.base.model;

import java.sql.Blob;

public class FormDynamic {

	private Integer id;
	private Integer idFormulario;
	private Blob json;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdFormulario() {
		return idFormulario;
	}

	public void setIdFormulario(Integer idFormulario) {
		this.idFormulario = idFormulario;
	}

	public Blob getJson() {
		return json;
	}

	public void setJson(Blob json) {
		this.json = json;
	}

	

}
