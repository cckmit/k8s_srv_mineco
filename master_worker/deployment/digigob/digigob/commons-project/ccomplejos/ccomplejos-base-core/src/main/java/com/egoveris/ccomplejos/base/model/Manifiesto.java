package com.egoveris.ccomplejos.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "CC_MANIFIESTO")
public class Manifiesto extends AbstractCComplejoJPA{
	
	@Column(name = "FECHA_MANIFIESTO")
	Date fechaManifiesto;
	@Column(name = "NUMERO_MANIFIESTO")
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
