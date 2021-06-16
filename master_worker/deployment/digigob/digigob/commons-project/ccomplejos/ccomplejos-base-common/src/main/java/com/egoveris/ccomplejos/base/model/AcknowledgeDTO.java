package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;

public class AcknowledgeDTO extends AbstractCComplejoDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String idMensaje;
	protected String idTransaccion;
	protected String codigoRecepcion;
	protected String descripcionRecepcion;
	
	public String getIdMensaje() {
		return idMensaje;
	}
	public void setIdMensaje(String idMensaje) {
		this.idMensaje = idMensaje;
	}
	public String getIdTransaccion() {
		return idTransaccion;
	}
	public void setIdTransaccion(String idTransaccion) {
		this.idTransaccion = idTransaccion;
	}
	public String getCodigoRecepcion() {
		return codigoRecepcion;
	}
	public void setCodigoRecepcion(String codigoRecepcion) {
		this.codigoRecepcion = codigoRecepcion;
	}
	public String getDescripcionRecepcion() {
		return descripcionRecepcion;
	}
	public void setDescripcionRecepcion(String descripcionRecepcion) {
		this.descripcionRecepcion = descripcionRecepcion;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
