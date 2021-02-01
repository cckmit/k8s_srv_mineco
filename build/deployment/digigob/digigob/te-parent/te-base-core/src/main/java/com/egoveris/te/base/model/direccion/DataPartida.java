package com.egoveris.te.base.model.direccion;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.OrderBy;

@Entity
@Table(name = "DATA_PARTIDA")
public class DataPartida implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "PARTIDA_NAME")
	private String partidaName;

	@ManyToOne
	@JoinColumn(name = "ID_PROVINCIA", referencedColumnName = "ID")
	private DataPartida provincia;
	
	@OneToMany(mappedBy = "partida", fetch = FetchType.EAGER)
	@OrderBy(clause = "localidadName ASC")
	public Set<DataLocalidad> localidades;

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

	public DataPartida getProvincia() {
		return provincia;
	}

	public void setProvincia(DataPartida provincia) {
		this.provincia = provincia;
	}
	
	public Set<DataLocalidad> getLocalidades() {
		return localidades;
	}

	public void setLocalidades(Set<DataLocalidad> localidades) {
		this.localidades = localidades;
	}
	
}