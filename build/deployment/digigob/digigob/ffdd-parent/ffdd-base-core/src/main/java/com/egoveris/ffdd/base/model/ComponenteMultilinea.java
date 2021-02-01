package com.egoveris.ffdd.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "DF_CLOB_TEXT")
public class ComponenteMultilinea {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;

	@Column(name = "ID_FORM")
	private Integer idFormulario;

	@Column(name = "ID_FORM_COMPONENT")
	private Integer idFormComp;

	@Column(name = "TEXTO")
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
