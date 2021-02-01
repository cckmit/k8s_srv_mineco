package com.egoveris.deo.model.model;

import java.io.Serializable;

public class GedoADestinatariosMessageResponse implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String respuesta;
	private String idError;
	private String motivo;

	public String getRespuesta() {
		return respuesta;
	}
	public void setRespuesta(String respuesta) {
		this.respuesta = respuesta;
	}
	public String getIdError() {
		return idError;
	}
	public void setIdError(String idError) {
		this.idError = idError;
	}
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
}
