package com.egoveris.ccomplejos.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
@Entity
@Table(name = "CC_VIEW_RESUMEN_IMPO")
public class ViewResumenImpo extends AbstractCComplejoJPA{
	
	@Column(name = "PROCESSING_STATUS")
	protected String processingStatus;
	@Column(name = "CREADO_POR")
	protected String creadoPor;
	@Column(name = "TIPO_TRAMITE")
	protected String tipoTramite;
	@Column(name = "COD_OPERACION")
	protected String codOperacion;
	@Column(name = "TOTAL_ITEMS")
	protected Integer totalItems;
	@Column(name = "TOTAL_BULTOS")
	protected Integer totalBultos;
	@Column(name = "FECHA_ACTUALIZACION")
	protected Date fechaActualizacion;
	@Column(name = "FECHA_CREACION")
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
