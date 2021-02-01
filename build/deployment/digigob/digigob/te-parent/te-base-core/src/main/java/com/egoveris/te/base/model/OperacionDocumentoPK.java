package com.egoveris.te.base.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class OperacionDocumentoPK implements Serializable {

	private static final long serialVersionUID = 5943684051298297331L;

	@Column(name = "ID_OPERACION")
	private Long idOperacion;

	@Column(name = "ID_GEDO_TIPODOCUMENTO")
	private Long idTipoDocumento;

	public Long getIdOperacion() {
		return idOperacion;
	}

	public void setIdOperacion(Long idOperacion) {
		this.idOperacion = idOperacion;
	}

	public Long getIdTipoDocumento() {
		return idTipoDocumento;
	}

	public void setIdTipoDocumento(Long idTipoDocumento) {
		this.idTipoDocumento = idTipoDocumento;
	}

}
