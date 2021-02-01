package com.egoveris.te.model.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class HistorialDeOperacionResponse implements Serializable{



	/**
	 * 
	 */
	private static final long serialVersionUID = -6893397277662868768L;

	/**
	 * 
	 */
	

private String tipoOperacion;
	
	private Date fechaOperacion;
	
	private String usuario;
	
	private String expediente;
	
	private Long idExpediente;
	
	private String motivo;
	
	private String destinatario;
	
	private String estado;
	
	


	


	public Date getFechaOperacion() {
		return fechaOperacion;
	}

	public void setFechaOperacion(Date fechaOperacion) {
		this.fechaOperacion = fechaOperacion;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}




	public String getTipoOperacion() {
		return tipoOperacion;
	}
	
	public void setTipoOperacion(String tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	public String getExpediente() {
		return expediente;
	}

	public void setExpediente(String expediente) {
		this.expediente = expediente;
	}

	public Long getIdExpediente() {
		return idExpediente;
	}

	public void setIdExpediente(Long idExpediente) {
		this.idExpediente = idExpediente;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setDestinatario(String destinatario) {
		this.destinatario = destinatario;
	}

	public String getDestinatario() {
		return destinatario;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getEstado() {
		return estado;
	}

	
}
