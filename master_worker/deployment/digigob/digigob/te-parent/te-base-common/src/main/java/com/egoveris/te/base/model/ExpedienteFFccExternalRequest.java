package com.egoveris.te.base.model;

import java.io.Serializable;

public class ExpedienteFFccExternalRequest implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8248866248138301848L;
	private String clave;
	private transient Object valor;
	public void setClave(String clave) {
		this.clave = clave;
	}
	public String getClave() {
		return clave;
	}
	public void setValor(Object valor) {
		this.valor = valor;
	}
	public Object getValor() {
		return valor;
	}

}
