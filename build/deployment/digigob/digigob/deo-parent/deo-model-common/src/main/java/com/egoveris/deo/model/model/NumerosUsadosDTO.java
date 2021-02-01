package com.egoveris.deo.model.model;

import java.io.Serializable;

public class NumerosUsadosDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4786285782301341857L;
	private Integer id;
	private TipoDocumentoDTO tipoDocumento;
	private String codigoReparticion;
	private String anio;
	private String numeroSADE;
	private String numeroEspecial;
	private String estado = "INVALIDO";
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public TipoDocumentoDTO getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(TipoDocumentoDTO tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public String getCodigoReparticion() {
		return codigoReparticion;
	}
	public void setCodigoReparticion(String codigoReparticion) {
		this.codigoReparticion = codigoReparticion;
	}
	public String getAnio() {
		return anio;
	}
	public void setAnio(String anio) {
		this.anio = anio;
	}
	public String getNumeroSADE() {
		return numeroSADE;
	}
	public void setNumeroSADE(String numeroSADE) {
		this.numeroSADE = numeroSADE;
	}
	public String getNumeroEspecial() {
		return numeroEspecial;
	}
	public void setNumeroEspecial(String numeroEspecial) {
		this.numeroEspecial = numeroEspecial;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	@Override
	public String toString() {
		return tipoDocumento + "-" + anio + "-" + numeroSADE + "-"
				+ numeroEspecial + "-" + codigoReparticion;
	}
	
	

	
}
