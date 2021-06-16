package com.egoveris.vucfront.base.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ExpedienteDocumentoID implements Serializable {

	private static final long serialVersionUID = -3209823626224323937L;

	@Column(name = "ID_EXPEDIENTE")
	private Long idExpediente;
	@Column(name = "ID_DOCUMENTO")
	private Long idDocumento;

	public Long getIdExpediente() {
		return idExpediente;
	}

	public void setIdExpediente(Long idExpediente) {
		this.idExpediente = idExpediente;
	}

	public Long getIdDocumento() {
		return idDocumento;
	}

	public void setIdDocumento(Long idDocumento) {
		this.idDocumento = idDocumento;
	}

}