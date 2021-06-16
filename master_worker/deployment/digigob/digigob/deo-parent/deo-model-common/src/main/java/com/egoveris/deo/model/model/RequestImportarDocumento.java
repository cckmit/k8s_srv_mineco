package com.egoveris.deo.model.model;

import java.io.Serializable;

public class RequestImportarDocumento extends RequestGenerarDocumento implements Serializable{

	private static final long serialVersionUID = 4154886936808988952L;
	private String codigo;
	private byte[] dataDocumento;
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public byte[] getDataDocumento() {
		return dataDocumento;
	}
	public void setDataDocumento(byte[] dataDocumento) {
		this.dataDocumento = dataDocumento;
	}
	
}
