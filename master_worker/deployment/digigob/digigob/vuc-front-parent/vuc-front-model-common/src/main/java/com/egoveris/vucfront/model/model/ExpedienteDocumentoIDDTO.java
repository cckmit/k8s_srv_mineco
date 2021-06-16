package com.egoveris.vucfront.model.model;

import java.io.Serializable;

public class ExpedienteDocumentoIDDTO implements Serializable {

	private static final long serialVersionUID = 2006740933790410551L;

	private Long idExpediente;
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