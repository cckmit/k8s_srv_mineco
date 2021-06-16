package com.egoveris.te.base.model;

import java.util.Date;

public class ExpedienteRemision {
	
	private Integer id;
	private Integer idExpediente;
	private Integer idExpedienteRemision;
	private Date fechaSolicitud;
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getIdExpediente() {
		return idExpediente;
	}
	public void setIdExpediente(Integer idExpediente) {
		this.idExpediente = idExpediente;
	}
	public Integer getIdExpedienteRemision() {
		return idExpedienteRemision;
	}
	public void setIdExpedienteRemision(Integer idExpedienteRemision) {
		this.idExpedienteRemision = idExpedienteRemision;
	}
	public Date getFechaSolicitud() {
		return fechaSolicitud;
	}
	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}
	
	
	
	
	

}
