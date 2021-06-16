package com.egoveris.te.base.model;

import java.io.Serializable;

public class TipoDocumentoTemplatePK implements Serializable{
	private static final long serialVersionUID = 1L;
	private Long idTipoDocumento;
	private Integer version;
	
	public TipoDocumentoTemplatePK (){}
	
	public Long getIdTipoDocumento() {
		return idTipoDocumento;
	}
	public void setIdTipoDocumento(Long idTipoDocumento) {
		this.idTipoDocumento = idTipoDocumento;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
}
