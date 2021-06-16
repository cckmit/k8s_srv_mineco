package com.egoveris.te.base.model.direccion;

import java.io.Serializable;

public class DataLocalidadDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String localidadName;
	private DataPartidaDTO partida;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLocalidadName() {
		return localidadName;
	}

	public void setLocalidadName(String localidadName) {
		this.localidadName = localidadName;
	}

	public DataPartidaDTO getPartida() {
		return partida;
	}

	public void setPartida(DataPartidaDTO partida) {
		this.partida = partida;
	}

}