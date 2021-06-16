package com.egoveris.deo.model.model;

import java.io.Serializable;
import java.util.List;

public class DocumentoTemporalDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private String rutaDocumentoSade;
	private String rutaDocumentoDeTrabajo;
	private String rutaDocumentoAdjunto;
	private String idGuardaDocumentalSade;
	private String idGuardaDocumentalAdjunto;
	private List<String> listaDocumentosDeTrabajo;
	
	public List<String> getListaDocumentosDeTrabajo() {
		return listaDocumentosDeTrabajo;
	}
	public void setListaDocumentosDeTrabajo(List<String> listaDocumentosDeTrabajo) {
		this.listaDocumentosDeTrabajo = listaDocumentosDeTrabajo;
	}
	
	public String getIdGuardaDocumentalSade() {
		return idGuardaDocumentalSade;
	}
	public void setIdGuardaDocumentalSade(String idGuardaDocumentalSade) {
		this.idGuardaDocumentalSade = idGuardaDocumentalSade;
	}
	public String getIdGuardaDocumentalAdjunto() {
		return idGuardaDocumentalAdjunto;
	}
	public void setIdGuardaDocumentalAdjunto(String idGuardaDocumentalDeTrabajo) {
		this.idGuardaDocumentalAdjunto = idGuardaDocumentalDeTrabajo;
	}
	public String getRutaDocumentoSade() {
		return rutaDocumentoSade;
	}
	public void setRutaDocumentoSade(String rutaDocumentoSade) {
		this.rutaDocumentoSade = rutaDocumentoSade;
	}
	public String getRutaDocumentoDeTrabajo() {
		return rutaDocumentoDeTrabajo;
	}
	public void setRutaDocumentoDeTrabajo(String rutaDocumentoDeTrabajo) {
		this.rutaDocumentoDeTrabajo = rutaDocumentoDeTrabajo;
	}
	public String getRutaDocumentoAdjunto() {
		return rutaDocumentoAdjunto;
	}
	public void setRutaDocumentoAdjunto(String rutaDocumentoAdjunto) {
		this.rutaDocumentoAdjunto = rutaDocumentoAdjunto;
	}
}
