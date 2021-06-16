package com.egoveris.ffdd.model.model;

import java.io.Serializable;
import java.util.Date;

public class FormTriggerDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7045032584417347591L;

	private Integer id;
	private Integer idForm;
	private String json;
	private String type;
	private Date fechaCreacion;

	public Integer getId() {
		return id;
	}

	public void setId(final Integer id) {
		this.id = id;
	}

	public String getJson() {
		return json;
	}

	public void setJson(final String json) {
		this.json = json;
	}

	public Integer getIdForm() {
		return idForm;
	}

	public void setIdForm(final Integer idFormComp) {
		this.idForm = idFormComp;
	}

	public String getType() {
		return type;
	}

	public void setType(final String type) {
		this.type = type;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(final Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FormTriggerDTO [id=").append(id).append(", idForm=").append(idForm).append(", json=")
				.append(json).append(", type=").append(type).append(", fechaCreacion=").append(fechaCreacion)
				.append("]");
		return builder.toString();
	}
}
