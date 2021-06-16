package com.egoveris.te.base.model.tipo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class TipoOperacionOrganismoPK implements Serializable {

	private static final long serialVersionUID = 5983684051998297331L;

	@Column(name = "ID_TIPO_OPERACION")
	private Long idTipoOperacion;

	@Column(name = "ID_ORGANISMO")
	private Long idReparticion;

	public Long getIdTipoOperacion() {
		return idTipoOperacion;
	}

	public void setIdTipoOperacion(Long idTipoOperacion) {
		this.idTipoOperacion = idTipoOperacion;
	}

	public Long getIdReparticion() {
		return idReparticion;
	}

	public void setIdReparticion(Long idReparticion) {
		this.idReparticion = idReparticion;
	}

}
