package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;

public class HojaInsumosDATPADTO extends AbstractCComplejoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1040343983933578599L;
	
	
	protected String aduana;
	protected String codigoDespachador;
	protected String consignatario;
	protected String rutImportador;

	public String getAduana() {
		return aduana;
	}
	public void setAduana(String aduana) {
		this.aduana = aduana;
	}
	public String getCodigoDespachador() {
		return codigoDespachador;
	}
	public void setCodigoDespachador(String codigoDespachador) {
		this.codigoDespachador = codigoDespachador;
	}
	public String getConsignatario() {
		return consignatario;
	}
	public void setConsignatario(String consignatario) {
		this.consignatario = consignatario;
	}
	public String getRutImportador() {
		return rutImportador;
	}
	public void setRutImportador(String rutImportador) {
		this.rutImportador = rutImportador;
	}

	

}
