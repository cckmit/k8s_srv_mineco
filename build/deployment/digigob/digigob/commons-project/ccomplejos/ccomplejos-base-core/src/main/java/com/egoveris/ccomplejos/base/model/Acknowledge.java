package com.egoveris.ccomplejos.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CC_ACKNOWLEDGE")
public class Acknowledge extends AbstractCComplejoJPA {

	@Column(name = "ID_MENSAJE")
	String idMensaje;

	@Column(name = "ID_TRANSACCION")
	String idTransaccion;

	@Column(name = "CODIGO_RECEPCION")
	String codigoRecepcion;

	@Column(name = "DESCRIPCION_RECEPCION")
	String descripcionRecepcion;

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

}
