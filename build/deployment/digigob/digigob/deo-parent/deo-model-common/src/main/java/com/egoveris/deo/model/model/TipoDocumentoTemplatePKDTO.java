package com.egoveris.deo.model.model;

import java.io.Serializable;

public class TipoDocumentoTemplatePKDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	private Integer idTipoDocumento;
	private double version;
	
	public Integer getIdTipoDocumento() {
		return idTipoDocumento;
	}
	public void setIdTipoDocumento(Integer idTipoDocumento) {
		this.idTipoDocumento = idTipoDocumento;
	}
	public double getVersion() {
		return version;
	}
	public void setVersion(double version) {
		this.version = version;
	}
}