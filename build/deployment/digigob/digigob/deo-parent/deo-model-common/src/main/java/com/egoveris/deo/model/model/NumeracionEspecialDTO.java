package com.egoveris.deo.model.model;

import java.io.Serializable;

public class NumeracionEspecialDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6809869511066205060L;
	private Integer id;
	private String codigoReparticion;
	private Integer idTipoDocumento;
	private String anio;
	private Integer numero;
	private Integer numeroInicial;
	private String codigoEcosistema;

	public Integer getNumeroInicial() {
		return numeroInicial;
	}

	public void setNumeroInicial(Integer numeroInicial) {
		this.numeroInicial = numeroInicial;
	}

	public String getAnio() {
		return anio;
	}

	public void setAnio(String anio) {
		this.anio = anio;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	public String getCodigoReparticion() {
		return codigoReparticion;
	}

	public void setCodigoReparticion(String codigoReparticion) {
		this.codigoReparticion = codigoReparticion;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setIdTipoDocumento(Integer idTipoDocumento) {
		this.idTipoDocumento = idTipoDocumento;
	}

	public Integer getIdTipoDocumento() {
		return idTipoDocumento;
	}

	public void setCodigoEcosistema(String codigoEcosistema) {
		this.codigoEcosistema = codigoEcosistema;
	}

	public String getCodigoEcosistema() {
		return codigoEcosistema;
	}
}