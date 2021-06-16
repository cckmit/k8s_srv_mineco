package com.egoveris.deo.model.model;

import java.io.Serializable;

public class GedoADestinatariosMessageRequest implements Serializable {


	private static final long serialVersionUID = 1L;
	private String workflowId;
	private String estado;
	private String nombreSistemaOrigen;
	
	public String getWorkflowId() {
		return workflowId;
	}
	public void setWorkflowId(String workflowId) {
		this.workflowId = workflowId;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getNombreSistemaOrigen() {
		return nombreSistemaOrigen;
	}
	public void setNombreSistemaOrigen(String nombreSistemaOrigen) {
		this.nombreSistemaOrigen = nombreSistemaOrigen;
	}
}
