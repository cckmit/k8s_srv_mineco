package com.egoveris.te.base.model.direccion;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.OrderBy;

@Entity
@Table(name = "DATA_PROVINCIA")
public class DataProvincia implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "PROVINCIA_NAME")
	private String provinciaName;

	@OneToMany(mappedBy = "provincia", fetch = FetchType.EAGER)
	@OrderBy(clause = "partidaName ASC")
	public Set<DataPartida> partidas;

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

	public Set<DataPartida> getPartidas() {
		return partidas;
	}

	public void setPartidas(Set<DataPartida> partidas) {
		this.partidas = partidas;
	}

}