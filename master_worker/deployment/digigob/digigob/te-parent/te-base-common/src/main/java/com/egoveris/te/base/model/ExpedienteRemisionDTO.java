package com.egoveris.te.base.model;

import java.util.Date;

public class ExpedienteRemisionDTO {
	
	private Long id;
	private Long idExpediente;
	private Long idExpedienteRemision;
	private Date fechaSolicitud;
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getIdExpediente() {
		return idExpediente;
	}
	public void setIdExpediente(Long idExpediente) {
		this.idExpediente = idExpediente;
	}
	public Long getIdExpedienteRemision() {
		return idExpedienteRemision;
	}
	public void setIdExpedienteRemision(Long idExpedienteRemision) {
		this.idExpedienteRemision = idExpedienteRemision;
	}
	public Date getFechaSolicitud() {
		return fechaSolicitud;
	}
	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}
	
	
	
	
	

}
