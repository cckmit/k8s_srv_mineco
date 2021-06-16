package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;

public class VistaProdAttrDocIngEnvDTO extends AbstractCComplejoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8495142258198747886L;
	
	protected Integer secuenciaAtributo;
	protected String nombreAtributo;
	protected String valorAtributo;
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
