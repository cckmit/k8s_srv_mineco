package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;

public class VistaDecDocIngEnvDTO  extends AbstractCComplejoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2424897530450739230L;
	
	protected String codigoAduana;
	protected String codigoTipoOperacion;
	protected String tipoDestinacion;
	protected String regionIngreso;
	
	public String getCodigoAduana() {
		return codigoAduana;
	}
	public void setCodigoAduana(String codigoAduana) {
		this.codigoAduana = codigoAduana;
	}
	public String getCodigoTipoOperacion() {
		return codigoTipoOperacion;
	}
	public void setCodigoTipoOperacion(String codigoTipoOperacion) {
		this.codigoTipoOperacion = codigoTipoOperacion;
	}
	public String getTipoDestinacion() {
		return tipoDestinacion;
	}
	public void setTipoDestinacion(String tipoDestinacion) {
		this.tipoDestinacion = tipoDestinacion;
	}
	public String getRegionIngreso() {
		return regionIngreso;
	}
	public void setRegionIngreso(String regionIngreso) {
		this.regionIngreso = regionIngreso;
	}

}
