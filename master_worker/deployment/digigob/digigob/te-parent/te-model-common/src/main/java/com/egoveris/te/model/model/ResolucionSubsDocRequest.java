package com.egoveris.te.model.model;

import java.io.Serializable;

public class ResolucionSubsDocRequest implements Serializable {

	private static final long serialVersionUID = 7252085548995570793L;

	private String tipoDocTad;
	private String numeroDocumento;

	public String getTipoDocTad() {
		return tipoDocTad;
	}

	public void setTipoDocTad(String tipoDocTad) {
		this.tipoDocTad = tipoDocTad;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}
}
