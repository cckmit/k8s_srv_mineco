package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;
import java.util.Date;

public class VistaDetallesPuertoDADTO extends AbstractCComplejoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3996758698632524239L;
	
	// protected String region;
	protected String codigoPais;
	protected Date fecha;
	protected Integer secuencial;
	
	// public String getRegion() {
	// return region;
	// }
	//
	// public void setRegion(String region) {
	// this.region = region;
	// }

	public String getCodigoPais() {
		return codigoPais;
	}
	public void setCodigoPais(String codigoPais) {
		this.codigoPais = codigoPais;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Integer getSecuencial() {
		return secuencial;
	}
	public void setSecuencial(Integer secuencial) {
		this.secuencial = secuencial;
	}

	
	
	

}
