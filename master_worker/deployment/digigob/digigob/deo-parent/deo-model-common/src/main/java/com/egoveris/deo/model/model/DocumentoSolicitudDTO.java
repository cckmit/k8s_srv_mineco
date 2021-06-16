package com.egoveris.deo.model.model;

import java.io.Serializable;

public class DocumentoSolicitudDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String numeroSade;
	private String workflowId;
	private Integer idTransaccion;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNumeroSade() {
		return numeroSade;
	}
	public void setNumeroSade(String numeroSade) {
		this.numeroSade = numeroSade;
	}
	public String getWorkflowId() {
		return workflowId;
	}
	public void setWorkflowId(String workflowId) {
		this.workflowId = workflowId;
	}
	public Integer getIdTransaccion() {
		return idTransaccion;
	}
	public void setIdTransaccion(Integer idTransaccion) {
		this.idTransaccion = idTransaccion;
	}
	
	


}
