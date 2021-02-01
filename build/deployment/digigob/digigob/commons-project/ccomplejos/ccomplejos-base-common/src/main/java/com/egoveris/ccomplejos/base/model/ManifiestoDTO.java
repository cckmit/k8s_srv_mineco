package com.egoveris.ccomplejos.base.model;

import java.util.Date;

public class ManifiestoDTO extends AbstractCComplejoDTO{
	
	private static final long serialVersionUID = -117601307316139549L;
	Date fechaManifiesto;
	String numeroManifiesto;
	
	
	public Date getFechaManifiesto() {
		return fechaManifiesto;
	}
	public void setFechaManifiesto(Date fechaManifiesto) {
		this.fechaManifiesto = fechaManifiesto;
	}
	public String getNumeroManifiesto() {
		return numeroManifiesto;
	}
	public void setNumeroManifiesto(String numeroManifiesto) {
		this.numeroManifiesto = numeroManifiesto;
	}
	
	

}
