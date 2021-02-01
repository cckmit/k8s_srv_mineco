package com.egoveris.deo.model.model;

import java.io.Serializable;
import java.util.Date;

public class TareaBusquedaDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7771015212060905196L;
	
	private String workfloworigen;
	private String usuario;
	private String usuarioIniciador;
	private Date fechaAlta;
	private Date fechaParticipacion;
	private String tipoDocumento;
	private String referencia;
	private String tipoTarea;
	private String usuarioDestino;
	private boolean esComunicable;
	
	public String getWorkfloworigen() {
		return workfloworigen;
	}
	public void setWorkfloworigen(String workfloworigen) {
		this.workfloworigen = workfloworigen;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
		
	
	
	public Date getFechaParticipacion() {
		return fechaParticipacion;
	}
	public void setFechaParticipacion(Date fechaParticipacion) {
		this.fechaParticipacion = fechaParticipacion;
	}
	public String getUsuarioIniciador() {
		return usuarioIniciador;
	}
	public void setUsuarioIniciador(String usuarioIniciador) {
		this.usuarioIniciador = usuarioIniciador;
	}
	public Date getFechaAlta() {
		return fechaAlta;
	}
	public void setFechaAlta(Date fechaAlta) {
		this.fechaAlta = fechaAlta;
	}
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public String getReferencia() {
		return referencia;
	}
	public void setReferencia(String motivo) {
		this.referencia = motivo;
	}
	public String getTipoTarea() {
		return tipoTarea;
	}
	public void setTipoTarea(String tipoTarea) {
		this.tipoTarea = tipoTarea;
	}
	public String getUsuarioDestino() {
		return usuarioDestino;
	}
	public void setUsuarioDestino(String usuarioDestino) {
		this.usuarioDestino = usuarioDestino;
	}
	public boolean isEsComunicable() {
		return esComunicable;
	}
	public void setEsComunicable(boolean esComunicable) {
		this.esComunicable = esComunicable;
	}
	
}
