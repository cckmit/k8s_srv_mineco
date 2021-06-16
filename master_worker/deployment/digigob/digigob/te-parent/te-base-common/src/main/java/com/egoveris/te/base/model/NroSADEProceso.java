package com.egoveris.te.base.model;

import java.io.Serializable;

public class NroSADEProceso implements Serializable {

	
	/**
	 * @author cavazque  Contiene
	 *         todos los números_sade que han sido usados o no en alguna caratula 
	 *         
	 * 
	 */
	
	private static final long serialVersionUID = 347016760771004504L;

	private Integer anio;
	private Integer numero;
	private String estado;

	public NroSADEProceso(int anioProceso, Integer numeroTransitorio, String estadoNro) {
		this.anio=anioProceso;
		this.numero=numeroTransitorio;
		this.estado=estadoNro;
	}

	public Integer getAnio() {
		return anio;
	}

	public void setAnio(Integer anio) {
		this.anio = anio;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

}