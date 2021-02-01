package com.egoveris.te.base.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class BuzonBean {
	
	String grupoId;
	String reparticionId;
	String sectorId;
	String workflowId;
	BigDecimal expedienteId;
	Timestamp fechaModificacion;
	String nombreActividad;
	String cadenaExpediente;
	String estado;
	
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getCadenaExpediente() {
		return cadenaExpediente;
	}
	public void setCadenaExpediente(String cadenaExpediente) {
		this.cadenaExpediente = cadenaExpediente;
	}
	public String getGrupoId() {
		return grupoId;
	}
	public void setGrupoId(String grupoId) {
		this.grupoId = grupoId;
	}
	public String getWorkflowId() {
		return workflowId;
	}
	public void setWorkflowId(String workflowId) {
		this.workflowId = workflowId;
	}
	public BigDecimal getExpedienteId() {
		return expedienteId;
	}
	public void setExpedienteId(BigDecimal expedienteId) {
		this.expedienteId = expedienteId;
	}
	public String getReparticionId() {
		return reparticionId;
	}
	public void setReparticionId(String reparticionId) {
		this.reparticionId = reparticionId;
	}
	public String getSectorId() {
		return sectorId;
	}
	public void setSectorId(String sectorId) {
		this.sectorId = sectorId;
	}
	public Timestamp getFechaModificacion() {
		return fechaModificacion;
	}
	public void setFechaModificacion(Timestamp fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}
	public String getNombreActividad() {
		return nombreActividad;
	}
	public void setNombreActividad(String nombreActividad) {
		this.nombreActividad = nombreActividad;
	}
}
