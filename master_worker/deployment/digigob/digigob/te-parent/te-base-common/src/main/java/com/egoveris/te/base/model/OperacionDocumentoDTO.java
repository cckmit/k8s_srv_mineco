package com.egoveris.te.base.model;

import java.io.Serializable;

public class OperacionDocumentoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private OperacionDTO operacion;
	private TipoDocumentoGedoDTO tipoDocumentoGedo;
	private DocumentoGedoDTO documentoGedo;
	private String numeroDocumento;
	private String nombreArchivo;

	public OperacionDTO getOperacion() {
		return operacion;
	}

	public void setOperacion(OperacionDTO operacion) {
		this.operacion = operacion;
	}

	public TipoDocumentoGedoDTO getTipoDocumentoGedo() {
		return tipoDocumentoGedo;
	}

	public void setTipoDocumentoGedo(TipoDocumentoGedoDTO tipoDocumentoGedo) {
		this.tipoDocumentoGedo = tipoDocumentoGedo;
	}

	public DocumentoGedoDTO getDocumentoGedo() {
		return documentoGedo;
	}

	public void setDocumentoGedo(DocumentoGedoDTO documentoGedo) {
		this.documentoGedo = documentoGedo;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

}
