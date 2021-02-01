package com.egoveris.ccomplejos.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "VIEW_HOJAINSUMODATPA")
public class HojaInsumosDATPA extends AbstractViewCComplejoJPA {
	
	@Column(name = "ADUANA_TRAMITACION")
	protected String aduana;
	
	@Column(name = "DISPATCHER_CODE")
	protected String codigoDespachador;
	
	@Column(name = "CONTACTO")
	protected String consignatario;
	
	@Column(name = "DOC_PERSONA_NUM")
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
