package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;

public class VistaProductAttributeDADTO extends AbstractCComplejoDTO implements Serializable {
	
	private static final long serialVersionUID = -3295917137597389964L;
	
	Integer secuenciaAtributo;
	String nombreAtributo;
	String esFijo;
	String valorAtributo;
	
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
	public String getEsFijo() {
		return esFijo;
	}
	public void setEsFijo(String esFijo) {
		this.esFijo = esFijo;
	}
	public String getValorAtributo() {
		return valorAtributo;
	}
	public void setValorAtributo(String valorAtributo) {
		this.valorAtributo = valorAtributo;
	}
	
	

}
