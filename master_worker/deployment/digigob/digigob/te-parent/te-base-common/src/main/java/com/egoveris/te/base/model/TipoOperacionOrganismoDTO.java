package com.egoveris.te.base.model;

import java.io.Serializable;

public class TipoOperacionOrganismoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private TipoOperacionDTO tipoOperacion;
	private ReparticionDTO reparticion;

	public TipoOperacionDTO getTipoOperacion() {
		return tipoOperacion;
	}

	public void setTipoOperacion(TipoOperacionDTO tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}

	public ReparticionDTO getReparticion() {
		return reparticion;
	}

	public void setReparticion(ReparticionDTO reparticion) {
		this.reparticion = reparticion;
	}

}
