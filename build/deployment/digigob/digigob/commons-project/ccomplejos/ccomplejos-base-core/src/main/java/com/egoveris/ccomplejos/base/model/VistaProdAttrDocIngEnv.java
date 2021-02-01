package com.egoveris.ccomplejos.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "view_prodattrdocingenv")
public class VistaProdAttrDocIngEnv extends AbstractViewCComplejoJPA {

	@Column(name = "SECUENCIA_ATRIBUTO")
	protected Integer secuenciaAtributo;
	
	@Column(name = "NOMBRE_ATRIBUTO")
	protected String nombreAtributo;
	
	@Column(name = "VALOR_ATRIBUTO")
	protected String valorAtributo;
	
	@Column(name = "NUMERO")
	protected String esFijo;
	
	public Integer getSecuenciaAtributo() {
		return secuenciaAtributo;
	}
	public void setSecuenciaAtributo(Integer secuenciaAtributo) {
		this.secuenciaAtributo = secuenciaAtributo;
	}
	public String getNombreAtributo() {
		return nombreAtributo;
	}
	public void setNombreAtributo(String nombreAtributo) {
		this.nombreAtributo = nombreAtributo;
	}
	public String getValorAtributo() {
		return valorAtributo;
	}
	public void setValorAtributo(String valorAtributo) {
		this.valorAtributo = valorAtributo;
	}

	public String isEsFijo() {
		return esFijo;
	}

	public void setEsFijo(String esFijo) {
		this.esFijo = esFijo;
	}
	
}
