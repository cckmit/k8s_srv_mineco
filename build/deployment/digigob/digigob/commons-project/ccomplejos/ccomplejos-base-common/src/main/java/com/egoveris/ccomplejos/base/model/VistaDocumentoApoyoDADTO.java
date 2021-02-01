package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;
import java.util.Date;

public class VistaDocumentoApoyoDADTO extends AbstractCComplejoDTO implements Serializable{
	
	private static final long serialVersionUID = 4264690467013305315L;
	
	String descripcion;
	String nombreDocumento;
	String nombreEmisor;
	String tipoDocumentoAdjunto;
	Integer secuenciaDocumento;
	Integer numeroDocumento;
	Date fechaDocumento;
	byte adjunto;
	
	
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getNombreDocumento() {
		return nombreDocumento;
	}
	public void setNombreDocumento(String nombreDocumento) {
		this.nombreDocumento = nombreDocumento;
	}
	public String getNombreEmisor() {
		return nombreEmisor;
	}
	public void setNombreEmisor(String nombreEmisor) {
		this.nombreEmisor = nombreEmisor;
	}
	public String getTipoDocumentoAdjunto() {
		return tipoDocumentoAdjunto;
	}
	public void setTipoDocumentoAdjunto(String tipoDocumentoAdjunto) {
		this.tipoDocumentoAdjunto = tipoDocumentoAdjunto;
	}
	public Integer getSecuenciaDocumento() {
		return secuenciaDocumento;
	}
	public void setSecuenciaDocumento(Integer secuenciaDocumento) {
		this.secuenciaDocumento = secuenciaDocumento;
	}
	public Integer getNumeroDocumento() {
		return numeroDocumento;
	}
	public void setNumeroDocumento(Integer numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	public Date getFechaDocumento() {
		return fechaDocumento;
	}
	public void setFechaDocumento(Date fechaDocumento) {
		this.fechaDocumento = fechaDocumento;
	}
	public byte getAdjunto() {
		return adjunto;
	}
	public void setAdjunto(byte adjunto) {
		this.adjunto = adjunto;
	}

}
