package com.egoveris.te.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TE_LOG_ESB")
public class LogESB {

	@Id
	@Column(name="ID")
	@GeneratedValue
	private Long id;
	
	@Column(name="ID_MENSAJE")
	private String idMensaje;
	
	@Column(name="ID_TRANSACCION")
	private String idTransaccion;
	
	@Column(name="CODIGO_RECEPCION")
	private String codigoRecepcion;
	
	@Column(name="DESCRIPCION_RECEPCION")
	private String descripcionRecepcion;
	
	@Column(name="ID_EXPEDIENTE")
	private Long idExpediente;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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

	public Long getIdExpediente() {
		return idExpediente;
	}

	public void setIdExpediente(Long idExpediente) {
		this.idExpediente = idExpediente;
	}
	
	
	
	
}
