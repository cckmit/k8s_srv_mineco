package com.egoveris.deo.model.model;

import java.io.Serializable;

public class DocumentoTemplatePKDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer idTipoDocumento;
	private double version;
	private String workflowId;

	public DocumentoTemplatePKDTO(){}
	
	public Integer getIdTipoDocumento() {
		return idTipoDocumento;
	}
	public void setIdTipoDocumento(Integer idTipoDocumento) {
		this.idTipoDocumento = idTipoDocumento;
	}
	public double getVersion() {
		return version;
	}
	public void setVersion(double version) {
		this.version = version;
	}
	public String getWorkflowId() {
		return workflowId;
	}
	public void setWorkflowId(String workflowId) {
		this.workflowId = workflowId;
	}
}
