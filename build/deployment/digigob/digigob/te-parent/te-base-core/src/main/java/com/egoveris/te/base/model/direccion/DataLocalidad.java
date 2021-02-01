package com.egoveris.te.base.model.direccion;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "DATA_LOCALIDAD")
public class DataLocalidad implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "LOCALIDAD_NAME")
	private String localidadName;

	@ManyToOne
	@JoinColumn(name = "ID_PARTIDA", referencedColumnName = "ID")
	private DataPartida partida;

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

	public DataPartida getPartida() {
		return partida;
	}

	public void setPartida(DataPartida partida) {
		this.partida = partida;
	}

}