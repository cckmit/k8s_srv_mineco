package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;

public class VistaObservacionDADTO extends AbstractCComplejoDTO implements Serializable {
	
	private static final long serialVersionUID = 6099005125317252189L;
	String valorObservacion;
	String codigoObservacion;
	String glosaObservacion;
	
	public String getValorObservacion() {
		return valorObservacion;
	}
	public void setValorObservacion(String valorObservacion) {
		this.valorObservacion = valorObservacion;
	}
	public String getCodigoObservacion() {
		return codigoObservacion;
	}
	public void setCodigoObservacion(String codigoObservacion) {
		this.codigoObservacion = codigoObservacion;
	}
	public String getGlosaObservacion() {
		return glosaObservacion;
	}
	public void setGlosaObservacion(String glosaObservacion) {
		this.glosaObservacion = glosaObservacion;
	}
	
}
