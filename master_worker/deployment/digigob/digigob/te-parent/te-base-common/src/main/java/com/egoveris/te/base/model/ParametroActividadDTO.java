package com.egoveris.te.base.model;

import java.io.Serializable;

public class ParametroActividadDTO implements Serializable {

	private static final long serialVersionUID = -2237353043609831629L;

	private Long id;
	private String valor;
	private String campo;
	private ActividadDTO idActividad;
	 
	
	
	/**
	 * @return the idActividad
	 */
	public ActividadDTO getIdActividad() {
		return idActividad;
	}

	/**
	 * @param idActividad the idActividad to set
	 */
	public void setIdActividad(ActividadDTO idActividad) {
		this.idActividad = idActividad;
	}

	/**
	 * @return the campo
	 */
	public String getCampo() {
		return campo;
	}

	/**
	 * @param campo the campo to set
	 */
	public void setCampo(String campo) {
		this.campo = campo;
	}

	public ParametroActividadDTO() {
		
	}
	
	public ParametroActividadDTO(String valor) {
		this.valor = valor;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}
	
	@Override
	public String toString() {
		return valor;
	}
	
}
