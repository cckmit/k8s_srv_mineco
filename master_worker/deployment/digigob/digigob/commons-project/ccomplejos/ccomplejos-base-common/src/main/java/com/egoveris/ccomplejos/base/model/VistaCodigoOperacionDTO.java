package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;

public class VistaCodigoOperacionDTO extends AbstractCComplejoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3583946112168793729L;
	
	protected String codigoOperacion;

	public String getCodigoOperacion() {
		return codigoOperacion;
	}

	public void setCodigoOperacion(String codigoOperacion) {
		this.codigoOperacion = codigoOperacion;
	}


}
