package com.egoveris.ffdd.web.adm;

import java.io.Serializable;

public class ComboboxDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String valor;
	private String descripcion;
	private Integer order;
	private Boolean mas;
	private Integer id;

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getOrder() {
		return order;
	}

	public void setOrder(Integer order) {
		this.order = order;
	}

	public Boolean getMas() {
		return mas;
	}

	public void setMas(Boolean mas) {
		this.mas = mas;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	
}
