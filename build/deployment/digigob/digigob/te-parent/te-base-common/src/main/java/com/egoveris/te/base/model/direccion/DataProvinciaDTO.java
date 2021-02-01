package com.egoveris.te.base.model.direccion;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataProvinciaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String provinciaName;
	public List<DataPartidaDTO> partidas;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getProvinciaName() {
		return provinciaName;
	}

	public void setProvinciaName(String provinciaName) {
		this.provinciaName = provinciaName;
	}

	public List<DataPartidaDTO> getPartidas() {
		if (partidas == null) {
			partidas = new ArrayList<>();
		}
		
		return partidas;
	}

	public void setPartidas(List<DataPartidaDTO> partidas) {
		this.partidas = partidas;
	}

}