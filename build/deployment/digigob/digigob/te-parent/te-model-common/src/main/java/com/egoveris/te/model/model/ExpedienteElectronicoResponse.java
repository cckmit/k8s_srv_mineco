package com.egoveris.te.model.model;

import java.io.Serializable;
import java.util.Date;




public class ExpedienteElectronicoResponse implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3352322932028167093L;
	private String codigoCaratula;
	private String estado;
	private String descripcion;
	private String codigoTrata;
	private String motivoSolicitud;
	private Date fechaModificacion;
	private Date fechaCreacion;
	private SolicitanteResponse solicitante;
	private String descripcionTrataElectronica;
	private String usuarioCreador;
	private String motivoSolicitudExterna;
	
	
	
	
	public String getDescripcionTrataElectronica() {
		return descripcionTrataElectronica;
	}
	public void setDescripcionTrataElectronica(String descripcion) {
		this.descripcionTrataElectronica = descripcion;
	}
	public String getCodigoCaratula() {
		return codigoCaratula;
	}
	public void setCodigoCaratula(String codigoCaratula) {
		this.codigoCaratula = codigoCaratula;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getCodigoTrata() {
		return codigoTrata;
	}
	public void setCodigoTrata(String codigoTrata) {
		this.codigoTrata = codigoTrata;
	}
	public String getMotivoSolicitud() {
		return motivoSolicitud;
	}
	public void setMotivoSolicitud(String motivoSolicitud) {
		this.motivoSolicitud = motivoSolicitud;
	}
	public Date getFechaModificacion() {
		return fechaModificacion;
	}
	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Date date) {
		this.fechaCreacion = date;
	}
	public SolicitanteResponse getSolicitante() {
		return solicitante;
	}
	public void setSolicitante(SolicitanteResponse solicitante) {
		this.solicitante = solicitante;
	}
	public String getUsuarioCreador() {
		return usuarioCreador;
	}
	public void setUsuarioCreador(String usuarioCreador) {
		this.usuarioCreador = usuarioCreador;
	}
	public void setMotivoSolicitudExterna(String motivoSolicitudExterna) {
		this.motivoSolicitudExterna = motivoSolicitudExterna;
	}
	public String getMotivoSolicitudExterna() {
		return motivoSolicitudExterna;
	}
	
	
	
	
	



}


