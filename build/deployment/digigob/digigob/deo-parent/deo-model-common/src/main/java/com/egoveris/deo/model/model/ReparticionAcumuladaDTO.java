package com.egoveris.deo.model.model;

import java.io.Serializable;
import java.util.Date;

public class ReparticionAcumuladaDTO implements Serializable {

	private static final long serialVersionUID = 1150236782204385696L;
	
	private DocumentoDTO idDocumento;
	private String reparticion;
	private Date fechaModificacion;
	private String tipoOperacion;
	private String userName;
	
	public String getReparticion() {
		return reparticion;
	}
	public void setReparticion(String reparticiones) {
		this.reparticion = reparticiones;
	}
	
	public Date getFechaModificacion() {
		return fechaModificacion;
	}
	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}
	public String getTipoOperacion() {
		return tipoOperacion;
	}
	public void setTipoOperacion(String tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public DocumentoDTO getIdDocumento() {
		return idDocumento;
	}
	public void setIdDocumento(DocumentoDTO idDocumento) {
		this.idDocumento = idDocumento;
	}
}
