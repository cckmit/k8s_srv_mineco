package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;
import java.util.Date;

public class ViewResumenImpoDTO extends AbstractCComplejoDTO implements Serializable{

	private static final long serialVersionUID = -1052791298829990613L;
	
	protected String processingStatus;
	protected String creadoPor;
	protected String tipoTramite;
	protected String codOperacion;
	protected Integer totalItems;
	protected Integer totalBultos;
	protected Date fechaActualizacion;
	protected Date profechaCreacion;
	
	public String getProcessingStatus() {
		return processingStatus;
	}
	public void setProcessingStatus(String processingStatus) {
		this.processingStatus = processingStatus;
	}
	public String getCreadoPor() {
		return creadoPor;
	}
	public void setCreadoPor(String creadoPor) {
		this.creadoPor = creadoPor;
	}
	public String getTipoTramite() {
		return tipoTramite;
	}
	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}
	public String getCodOperacion() {
		return codOperacion;
	}
	public void setCodOperacion(String codOperacion) {
		this.codOperacion = codOperacion;
	}
	public Integer getTotalItems() {
		return totalItems;
	}
	public void setTotalItems(Integer totalItems) {
		this.totalItems = totalItems;
	}
	public Integer getTotalBultos() {
		return totalBultos;
	}
	public void setTotalBultos(Integer totalBultos) {
		this.totalBultos = totalBultos;
	}
	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}
	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}
	public Date getProfechaCreacion() {
		return profechaCreacion;
	}
	public void setProfechaCreacion(Date profechaCreacion) {
		this.profechaCreacion = profechaCreacion;
	}
	
}
