package com.egoveris.te.base.model;

import java.io.Serializable;

public class TipoOperacionDocDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private TipoOperacionDTO tipoOperacion;
	private TipoDocumentoGedoDTO tipoDocumentoGedo;
	private boolean opcional;
	private boolean obligatorio;

	public TipoOperacionDTO getTipoOperacion() {
		return tipoOperacion;
	}

	public void setTipoOperacion(TipoOperacionDTO tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	public TipoDocumentoGedoDTO getTipoDocumentoGedo() {
		return tipoDocumentoGedo;
	}

	public void setTipoDocumentoGedo(TipoDocumentoGedoDTO tipoDocumentoGedo) {
		this.tipoDocumentoGedo = tipoDocumentoGedo;
	}

	public boolean isOpcional() {
		return opcional;
	}

	public void setOpcional(boolean opcional) {
		this.opcional = opcional;
	}

	public boolean isObligatorio() {
		return obligatorio;
	}

	public void setObligatorio(boolean obligatorio) {
		this.obligatorio = obligatorio;
	}

}
