package com.egoveris.te.model.model;

import java.io.Serializable;

public class CampoFFCCDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8248866248138301555L;
	private String campo;
	private String valor;
	public String getCampo() {
		return campo;
	}
	public void setCampo(String campo) {
		this.campo = campo;
	}
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	
	
	

}
