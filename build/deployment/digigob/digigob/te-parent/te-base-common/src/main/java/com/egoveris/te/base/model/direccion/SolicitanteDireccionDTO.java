package com.egoveris.te.base.model.direccion;

import java.io.Serializable;

public class SolicitanteDireccionDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idSolicitante;
	private String direccion;
	private DataProvinciaDTO provincia;
	private DataPartidaDTO partida;
	private DataLocalidadDTO localidad;

	public Integer getIdSolicitante() {
		return idSolicitante;
	}

	public void setIdSolicitante(Integer idSolicitante) {
		this.idSolicitante = idSolicitante;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public DataProvinciaDTO getProvincia() {
		return provincia;
	}

	public void setProvincia(DataProvinciaDTO provincia) {
		this.provincia = provincia;
	}

	public DataPartidaDTO getPartida() {
		return partida;
	}

	public void setPartida(DataPartidaDTO partida) {
		this.partida = partida;
	}

	public DataLocalidadDTO getLocalidad() {
		return localidad;
	}

	public void setLocalidad(DataLocalidadDTO localidad) {
		this.localidad = localidad;
	}

}