package com.egoveris.deo.model.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResponseImportarDocumento extends ResponseGenerarDocumento implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6913486984715830749L;
	private List<String> errores = new ArrayList<String>();
	private String descripcionTrata;
	
	
	public List<String> getErrores() {
		return errores;
	}

	public void setErrores(List<String> errores) {
		this.errores = errores;
	}

	public String getDescripcionTrata() {
		return descripcionTrata;
	}

	public void setDescripcionTrata(String descripcionTrata) {
		this.descripcionTrata = descripcionTrata;
	}

		
}
