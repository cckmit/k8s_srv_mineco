package com.egoveris.ccomplejos.base.model;

import java.util.Date;

public class DocumentoApoyoDTO extends AbstractCComplejoDTO{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -149444694149257389L;

	protected Long documentoApoyoId;
	protected Long secuenciaNro;
	protected String idDocumento;
	protected Date fechaDocumento;
	protected String comentarios;
	protected byte adjunto;
	protected String numeroDocumento;
	protected String tipoDocumento;
	protected String emisorDocumento;
	
	
	public Long getDocumentoApoyoId() {
		return documentoApoyoId;
	}
	public void setDocumentoApoyoId(Long documentoApoyoId) {
		this.documentoApoyoId = documentoApoyoId;
	}
	public Long getSecuenciaNro() {
		return secuenciaNro;
	}
	public void setSecuenciaNro(Long secuenciaNro) {
		this.secuenciaNro = secuenciaNro;
	}
	public String getIdDocumento() {
		return idDocumento;
	}
	public void setIdDocumento(String idDocumento) {
		this.idDocumento = idDocumento;
	}
	public Date getFechaDocumento() {
		return fechaDocumento;
	}
	public void setFechaDocumento(Date fechaDocumento) {
		this.fechaDocumento = fechaDocumento;
	}
	public String getComentarios() {
		return comentarios;
	}
	public void setComentarios(String comentarios) {
		this.comentarios = comentarios;
	}
	public byte getAdjunto() {
		return adjunto;
	}
	public void setAdjunto(byte adjunto) {
		this.adjunto = adjunto;
	}
	public String getNumeroDocumento() {
		return numeroDocumento;
	}
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
	public String getTipoDocumento() {
		return tipoDocumento;
	}
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	public String getEmisorDocumento() {
		return emisorDocumento;
	}
	public void setEmisorDocumento(String emisorDocumento) {
		this.emisorDocumento = emisorDocumento;
	}

}
