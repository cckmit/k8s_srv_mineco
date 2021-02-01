package com.egoveris.te.model.model;

import java.io.Serializable;

public class DatosTareaBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -967722749988574138L;
	/*https://quark.everis.com/jira/browse/BISADE-3624*/	
	private String estado;
	private String fechaModificacion;
	private String codigoExpediente;	
	private String codigoTrata;
	private String descripcionTrata;
	private String motivo;
	private String usuarioAnterior;
	
	public DatosTareaBean() {}
	
	public DatosTareaBean(String estado, String codigoExpediente, String codigoTrata, String descripcionTrata, String motivo, String fechaModificacion, String usuarioAnterior) {
		this.estado = estado;
		this.codigoExpediente = codigoExpediente;	
		this.codigoTrata = codigoTrata;
		this.descripcionTrata = descripcionTrata;
		this.motivo = motivo;
		this.fechaModificacion = fechaModificacion;
		this.usuarioAnterior = usuarioAnterior;		
	}
	
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getFechaModificacion() {
		return fechaModificacion;
	}
	public void setFechaModificacion(String fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}
	public String getCodigoExpediente() {
		return codigoExpediente;
	}
	public void setCodigoExpediente(String codigoExpediente) {
		this.codigoExpediente = codigoExpediente;
	}
	public String getCodigoTrata() {
		return codigoTrata;
	}
	public void setCodigoTrata(String codigoTrata) {
		this.codigoTrata = codigoTrata;
	}
	public String getDescripcionTrata() {
		return descripcionTrata;
	}
	public void setDescripcionTrata(String descripcionTrata) {
		this.descripcionTrata = descripcionTrata;
	}
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	public String getUsuarioAnterior() {
		return usuarioAnterior;
	}
	public void setUsuarioAnterior(String usuarioAnterior) {
		this.usuarioAnterior = usuarioAnterior;
	}		
}
