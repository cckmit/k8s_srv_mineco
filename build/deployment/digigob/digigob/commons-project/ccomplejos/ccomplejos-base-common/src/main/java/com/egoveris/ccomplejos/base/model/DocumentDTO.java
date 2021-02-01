package com.egoveris.ccomplejos.base.model;

import java.util.Date;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;


public class DocumentDTO extends AbstractCComplejoDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 805372668177518159L;

	Long idDocumento;

	String codigoDocumento;

	String nombreDocumento;

	String tipoDocumento;

	ParticipanteSecundarioDTO participante;

	String paisDocumento;

	Date fechaDocumento;

	/**
	 * @return the idDocumento
	 */
	public Long getIdDocumento() {
		return idDocumento;
	}

	/**
	 * @param idDocumento
	 *            the idDocumento to set
	 */
	public void setIdDocumento(Long idDocumento) {
		this.idDocumento = idDocumento;
	}

	/**
	 * @return the codigoDocumento
	 */
	public String getCodigoDocumento() {
		return codigoDocumento;
	}

	/**
	 * @param codigoDocumento
	 *            the codigoDocumento to set
	 */
	public void setCodigoDocumento(String codigoDocumento) {
		this.codigoDocumento = codigoDocumento;
	}

	/**
	 * @return the nombreDocumento
	 */
	public String getNombreDocumento() {
		return nombreDocumento;
	}

	/**
	 * @param nombreDocumento
	 *            the nombreDocumento to set
	 */
	public void setNombreDocumento(String nombreDocumento) {
		this.nombreDocumento = nombreDocumento;
	}

	/**
	 * @return the tipoDocumento
	 */
	public String getTipoDocumento() {
		return tipoDocumento;
	}

	/**
	 * @param tipoDocumento
	 *            the tipoDocumento to set
	 */
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	/**
	 * @return the participante
	 */
	public ParticipanteSecundarioDTO getParticipante() {
		return participante;
	}

	/**
	 * @param participante
	 *            the participante to set
	 */
	public void setParticipante(ParticipanteSecundarioDTO participante) {
		this.participante = participante;
	}

	/**
	 * @return the paisDocumento
	 */
	public String getPaisDocumento() {
		return paisDocumento;
	}

	/**
	 * @param paisDocumento
	 *            the paisDocumento to set
	 */
	public void setPaisDocumento(String paisDocumento) {
		this.paisDocumento = paisDocumento;
	}

	/**
	 * @return the fechaDocumento
	 */
	public Date getFechaDocumento() {
		return fechaDocumento;
	}

	/**
	 * @param fechaDocumento
	 *            the fechaDocumento to set
	 */
	public void setFechaDocumento(Date fechaDocumento) {
		this.fechaDocumento = fechaDocumento;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
	
}
