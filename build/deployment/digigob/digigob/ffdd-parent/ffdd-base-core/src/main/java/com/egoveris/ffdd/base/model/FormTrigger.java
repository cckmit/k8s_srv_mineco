package com.egoveris.ffdd.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DF_DYNAMIC_FORM")
public class FormTrigger {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "ID_FORM")
	private Integer idForm;
	
	@Column(name = "JSON")
	private String json;
	
	@Column(name = "TYPE")
	private String type;
	
	@Column(name = "FECHA_ALTA")
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

}
