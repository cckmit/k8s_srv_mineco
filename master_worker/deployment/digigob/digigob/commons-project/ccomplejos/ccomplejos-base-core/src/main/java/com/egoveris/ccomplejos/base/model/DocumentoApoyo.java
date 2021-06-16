package com.egoveris.ccomplejos.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CC_DOCUMENTO_APOYO")
public class DocumentoApoyo extends AbstractCComplejoJPA {

	@Column(name = "ID_DOCUMENTO_APOYO")
	protected Long documentoApoyoId;

	@Column(name = "SECUENCIA_NRO")
	protected Long secuenciaNro;

	@Column(name = "IDDOCUMENTO")
	protected String idDocumento;

	@Column(name = "FECHA_DOCUMENTO")
	protected Date fechaDocumento;

	@Column(name = "COMENTARIOS")
	protected String comentarios;

	@Column(name = "ADJUNTO")
	protected byte adjunto;

	@Column(name = "NUMERO_DOCUMENTO")
	protected String numeroDocumento;

	@Column(name = "TIPO_DOCUMENTO")
	protected String tipoDocumento;

	@Column(name = "EMISOR_DOCUMENTO")
	protected String emisorDocumento;

	@ManyToOne
	@JoinColumn(name = "ID_ITEM", referencedColumnName = "id")
	protected ItemJPA item;

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

	/**
	 * @return the item
	 */
	public ItemJPA getItem() {
		return item;
	}

	/**
	 * @param item
	 *            the item to set
	 */
	public void setItem(ItemJPA item) {
		this.item = item;
	}

}
