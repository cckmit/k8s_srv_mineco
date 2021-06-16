package com.egoveris.te.base.model.tipo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class TipoOperacionDocumentoPK implements Serializable {

	private static final long serialVersionUID = 5983684051998297331L;
	
	@Column(name = "ID_TIPO_OPERACION")
	private Long idTipoOperacion;
	
	@Column(name = "ID_GEDO_TIPODOCUMENTO")
	private Long idTipoDocumento;

	public Long getIdTipoOperacion() {
		return idTipoOperacion;
	}

	public void setIdTipoOperacion(Long idTipoOperacion) {
		this.idTipoOperacion = idTipoOperacion;
	}

	public Long getIdTipoDocumento() {
		return idTipoDocumento;
	}

	public void setIdTipoDocumento(Long idTipoDocumento) {
		this.idTipoDocumento = idTipoDocumento;
	}

}
