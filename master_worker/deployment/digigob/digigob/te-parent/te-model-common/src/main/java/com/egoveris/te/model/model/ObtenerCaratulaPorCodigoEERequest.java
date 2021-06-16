package com.egoveris.te.model.model;

import java.io.Serializable;

public class ObtenerCaratulaPorCodigoEERequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1358291145144128010L;

	
	private String codigoEE;


	public String getCodigoEE() {
		return codigoEE;
	}


	public void setCodigoEE(String codigoEE) {
		this.codigoEE = codigoEE;
	}
}
