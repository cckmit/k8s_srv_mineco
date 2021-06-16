package com.egoveris.deo.model.model;

import java.io.Serializable;
import java.util.Date;



public class DocumentosExcelDTO implements Serializable {

	
	private static final long serialVersionUID = 166885553673942743L;

	private String codigoSade;
	private Date fechaCreacion;
	private String usuarioGenerador;
	private String referencia;
	
	
	public DocumentosExcelDTO(DocumentoSolr doc) {
		this.codigoSade = doc.getNroSade();
		this.fechaCreacion = doc.getFechaCreacion();
		this.usuarioGenerador = doc.getUsuarioGenerador();
		this.referencia = doc.getReferencia();
	}
	
	public String getCodigoSade() {
		return codigoSade;
	}
	public void setCodigoSade(String codigoSade) {
		this.codigoSade = codigoSade;
	}
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public String getUsuarioGenerador() {
		return usuarioGenerador;
	}
	public void setUsuarioGenerador(String usuarioGenerador) {
		this.usuarioGenerador = usuarioGenerador;
	}
	public String getReferencia() {
		return referencia;
	}
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	
	
	

}
