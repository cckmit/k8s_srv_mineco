package com.egoveris.te.model.model;

import java.io.Serializable;

public class DocumentoTrabajo  implements Serializable{
	
	private static final long serialVersionUID = 8495776573230135555L;
	
	/**
	 * Nombre del documento de trabajo que se requiere adjuntar.
	 */
	private String nombreArchivo;
	/**
	 * Contenido en formato byte[] del documento de trabajo a adjuntar.
	 */
	private byte[] dataArchivo;
	
	private String tipoDocumentoTrabajo;
	
	//Getters and Setters
	public String getNombreArchivo() {
		return nombreArchivo;
	}
	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}
	public byte[] getDataArchivo() {
		return dataArchivo;
	}
	public void setDataArchivo(byte[] dataArchivo) {
		this.dataArchivo = dataArchivo;
	}
	public void setTipoDocumentoTrabajo(String tipoDocumentoTrabajo) {
		this.tipoDocumentoTrabajo = tipoDocumentoTrabajo;
}
	public String getTipoDocumentoTrabajo() {
		return tipoDocumentoTrabajo;
	}
}
