package com.egoveris.ffdd.model.model;

import java.io.Serializable;

public class AtributoComponenteWDDTO implements Serializable {

	private static final long serialVersionUID = 7689423665610098336L;

	private Integer id;
	private String valor;
	private String key;
	private transient ComponenteDTO componente;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public String toString() {
		return this.valor;
	}

	public ComponenteDTO getComponente() {
		return componente;
	}

	public void setComponente(ComponenteDTO componente) {
		this.componente = componente;
	}
}