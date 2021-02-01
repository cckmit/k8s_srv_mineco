package com.egoveris.vucfront.model.model;

import java.io.Serializable;

public class ExpedienteDocumentoDTO implements Serializable {

	private static final long serialVersionUID = 2006740933790410551L;

	private ExpedienteDocumentoIDDTO id;

	public ExpedienteDocumentoIDDTO getId() {
		return id;
	}

	public void setId(ExpedienteDocumentoIDDTO id) {
		this.id = id;
	}

}