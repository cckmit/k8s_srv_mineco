package com.egoveris.vucfront.base.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "TAD_EXPEDIENTE_DOCUMENTO")
public class ExpedienteDocumento {

	@EmbeddedId
	private ExpedienteDocumentoID id;

	public ExpedienteDocumentoID getId() {
		return id;
	}

	public void setId(ExpedienteDocumentoID id) {
		this.id = id;
	}
}