package com.egoveris.te.model.model;

import java.io.Serializable;

public class ExpedientesFusionResponse implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1205550199388620456L;
	/**
	 * 
	 */
	
	private String codigoExpediente;
	private String codigoTrata;
	private String descripcionTrata;
	
	public void setCodigoTrata(String codigoTrata) {
		this.codigoTrata = codigoTrata;
	}
	public String getCodigoTrata() {
		return codigoTrata;
	}
	public void setCodigoExpediente(String codigoExpediente) {
		this.codigoExpediente = codigoExpediente;
	}
	public String getCodigoExpediente() {
		return codigoExpediente;
	}
	public void setDescripcionTrata(String descripcionTrata) {
		this.descripcionTrata = descripcionTrata;
	}
	public String getDescripcionTrata() {
		return descripcionTrata;
	}
	
	
}
