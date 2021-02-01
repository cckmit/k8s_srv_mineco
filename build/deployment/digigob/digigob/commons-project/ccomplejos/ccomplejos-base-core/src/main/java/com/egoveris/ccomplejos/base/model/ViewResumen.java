package com.egoveris.ccomplejos.base.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CC_VIEW_RESUMEN")
public class ViewResumen extends AbstractCComplejoJPA{
	
	
	@Column(name = "COD_OPERACION")
	protected String codOperacion;
	@Column(name = "TIPO_TRAMITE")
	protected String tipoTramite;
	@Column(name = "CREADO_POR")
	protected String creadoPor;
	@Column(name = "ACTUALIZADO_POR")
	protected String actualizadoPor;
	@Column(name = "BULTO_TOTAL")
	protected Integer bultoTotal;
	@Column(name = "PESO_NETO_EMBARQUE")
	protected BigDecimal pesoNetoEmbarque;
	@Column(name = "PROCESSING_STATUS")
	protected String processingStatus;
	@Column(name = "FECHA_CREACION")
	protected Date fechaCreacion;
	@Column(name = "FECHA_ACTUALIZACION")
	protected Date fechaActualizacion;
	@Column(name = "ITEM_TOTAL")
	protected Integer itemTotal;
	@Column(name = "PESO_BRUTO_ITEM")
	protected BigDecimal pesoBrutoItem;
	@Column(name = "VOLUMEN_TOTAL")
	protected BigDecimal volumenTotal;
	
	public String getCodOperacion() {
		return codOperacion;
	}
	public void setCodOperacion(String codOperacion) {
		this.codOperacion = codOperacion;
	}
	public String getTipoTramite() {
		return tipoTramite;
	}
	public void setTipoTramite(String tipoTramite) {
		this.tipoTramite = tipoTramite;
	}
	public String getCreadoPor() {
		return creadoPor;
	}
	public void setCreadoPor(String creadoPor) {
		this.creadoPor = creadoPor;
	}
	public String getActualizadoPor() {
		return actualizadoPor;
	}
	public void setActualizadoPor(String actualizadoPor) {
		this.actualizadoPor = actualizadoPor;
	}
	public Integer getBultoTotal() {
		return bultoTotal;
	}
	public void setBultoTotal(Integer bultoTotal) {
		this.bultoTotal = bultoTotal;
	}
	public BigDecimal getPesoNetoEmbarque() {
		return pesoNetoEmbarque;
	}
	public void setPesoNetoEmbarque(BigDecimal pesoNetoEmbarque) {
		this.pesoNetoEmbarque = pesoNetoEmbarque;
	}
	public String getProcessingStatus() {
		return processingStatus;
	}
	public void setProcessingStatus(String processingStatus) {
		this.processingStatus = processingStatus;
	}
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public Date getFechaActualizacion() {
		return fechaActualizacion;
	}
	public void setFechaActualizacion(Date fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}
	public Integer getItemTotal() {
		return itemTotal;
	}
	public void setItemTotal(Integer itemTotal) {
		this.itemTotal = itemTotal;
	}
	public BigDecimal getPesoBrutoItem() {
		return pesoBrutoItem;
	}
	public void setPesoBrutoItem(BigDecimal pesoBrutoItem) {
		this.pesoBrutoItem = pesoBrutoItem;
	}
	public BigDecimal getVolumenTotal() {
		return volumenTotal;
	}
	public void setVolumenTotal(BigDecimal volumenTotal) {
		this.volumenTotal = volumenTotal;
	}

}
