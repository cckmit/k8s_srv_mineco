package com.egoveris.te.base.model.direccion;

import java.io.Serializable;
import java.util.List;

public class DataPartidaDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Long id;
	private String partidaName;
	private DataPartidaDTO provincia;
	private List<DataLocalidadDTO> localidades;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPartidaName() {
		return partidaName;
	}

	public void setPartidaName(String partidaName) {
		this.partidaName = partidaName;
	}

	public DataPartidaDTO getProvincia() {
		return provincia;
	}

	public void setProvincia(DataPartidaDTO provincia) {
		this.provincia = provincia;
	}

	public List<DataLocalidadDTO> getLocalidades() {
		return localidades;
	}

	public void setLocalidades(List<DataLocalidadDTO> localidades) {
		this.localidades = localidades;
	}

}