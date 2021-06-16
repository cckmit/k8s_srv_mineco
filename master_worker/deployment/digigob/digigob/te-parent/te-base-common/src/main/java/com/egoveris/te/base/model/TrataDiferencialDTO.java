package com.egoveris.te.base.model;

import java.io.Serializable;

public class TrataDiferencialDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String codigoTrata;
	private String estado;
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCodigoTrata() {
		return codigoTrata;
	}
	
	public void setCodigoTrata(String codigoTrata) {
		this.codigoTrata = codigoTrata;
	}
	
	public String getEstado() {
		return estado;
	}
	
	public void setEstado(String estado) {
		this.estado = estado;
	}	
}
