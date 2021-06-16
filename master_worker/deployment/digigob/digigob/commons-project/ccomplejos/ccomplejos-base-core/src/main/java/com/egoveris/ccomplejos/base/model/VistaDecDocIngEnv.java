package com.egoveris.ccomplejos.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "view_decdocingresoenvio")
public class VistaDecDocIngEnv extends AbstractViewCComplejoJPA {
	
	@Column(name = "COD_ADUANA")
	protected String codigoAduana;
	
	@Column(name = "COD_TIPO_OPER")
	protected String codigoTipoOperacion;
	
	@Column(name = "TIPO_DESTINACION")
	protected String tipoDestinacion;
	
	@Column(name = "REGION_INGRESO")
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
