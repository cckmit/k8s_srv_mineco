package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ViewResumenDTO  extends AbstractCComplejoDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1083797138340846335L;
	protected String codOperacion;
	protected String tipoTramite;
	protected String creadoPor;
	protected String actualizadoPor;
	protected Integer bultoTotal;
	protected BigDecimal pesoNetoEmbarque;
	protected String processingStatus;
	protected Date fechaCreacion;
	protected Date fechaActualizacion;
	protected Integer itemTotal;
	protected BigDecimal pesoBrutoItem;
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
