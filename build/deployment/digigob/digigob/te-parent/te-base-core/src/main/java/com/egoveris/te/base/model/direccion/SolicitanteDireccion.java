package com.egoveris.te.base.model.direccion;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "SOLICITANTE_DIRECCION")
public class SolicitanteDireccion implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_SOLICITANTE")
	private Integer idSolicitante;
	
	@Column(name = "DIRECCION")
	private String direccion;

	@ManyToOne
	@JoinColumn(name = "ID_PROVINCIA", referencedColumnName = "ID")
	private DataProvincia provincia;

	@ManyToOne
	@JoinColumn(name = "ID_PARTIDA", referencedColumnName = "ID")
	private DataPartida partida;

	@ManyToOne
	@JoinColumn(name = "ID_LOCALIDAD", referencedColumnName = "ID")
	private DataLocalidad localidad;

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
	
	public DataProvincia getProvincia() {
		return provincia;
	}

	public void setProvincia(DataProvincia provincia) {
		this.provincia = provincia;
	}

	public DataPartida getPartida() {
		return partida;
	}

	public void setPartida(DataPartida partida) {
		this.partida = partida;
	}

	public DataLocalidad getLocalidad() {
		return localidad;
	}

	public void setLocalidad(DataLocalidad localidad) {
		this.localidad = localidad;
	}

}