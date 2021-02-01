package com.egoveris.te.base.model.rest;

import java.util.List;

import com.egoveris.te.model.model.Status;

public class DocumentTypesResponse {

	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public List<DocumentImportedTypesWsDTO> getTypes() {
		return types;
	}
	public void setTypes(List<DocumentImportedTypesWsDTO> types) {
		this.types = types;
	}
	private Status status;
	private List<DocumentImportedTypesWsDTO> types;
	
	
}
