package com.egoveris.te.base.model;

import com.egoveris.te.base.model.rest.DocumentImportedTypesWsDTO;

public class TaskDocUserDTO {
	

	private String taskCode;
	private String dataCode;
	private String nombreArchivo;
	private String observacion;
	private String usuario;
	private DocumentImportedTypesWsDTO documentImportedTypes;
	
	public TaskDocUserDTO(){
		
	}
	public String getTaskCode() {
		return taskCode;
	}
	public void setTaskCode(String taskCode) {
		this.taskCode = taskCode;
	}
	public String getDataCode() {
		return dataCode;
	}
	public void setDataCode(String dataCode) {
		this.dataCode = dataCode;
	}
	public String getNombreArchivo() {
		return nombreArchivo;
	}
	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public DocumentImportedTypesWsDTO getDocumentImportedTypes() {
		return documentImportedTypes;
	}
	public void setDocumentImportedTypes(DocumentImportedTypesWsDTO documentImportedTypes) {
		this.documentImportedTypes = documentImportedTypes;
	}

	
} 