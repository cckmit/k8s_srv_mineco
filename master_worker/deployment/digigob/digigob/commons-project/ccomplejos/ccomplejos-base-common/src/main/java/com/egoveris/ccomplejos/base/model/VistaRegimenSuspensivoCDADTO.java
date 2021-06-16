package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;

public class VistaRegimenSuspensivoCDADTO extends AbstractCComplejoDTO implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -2710071403223952315L;
	
	protected String tipoReingreso;
	protected String razonReingreso;
	
	public String getTipoReingreso() {
		return tipoReingreso;
	}
	public void setTipoReingreso(String tipoReingreso) {
		this.tipoReingreso = tipoReingreso;
	}
	public String getRazonReingreso() {
		return razonReingreso;
	}
	public void setRazonReingreso(String razonReingreso) {
		this.razonReingreso = razonReingreso;
	}
	
}
