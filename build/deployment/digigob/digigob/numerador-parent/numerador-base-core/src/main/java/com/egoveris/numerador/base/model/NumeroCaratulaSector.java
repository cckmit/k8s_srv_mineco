package com.egoveris.numerador.base.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="NUM_CARATULA_SECTOR_USU")
public class NumeroCaratulaSector implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7050465041874978181L;

	/**
	 * 
	 */
	@Id
	@Column(name="ID_NUMERO_SADE_CARATULA")
	private Integer id;

	@Column(name="USUARIO")
	private String usuario;
	
	@Column(name="CODIGO_SECTOR_INTERNO")
	private String sectorInternoCaratula;

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setSectorInternoCaratula(String sectorInternoCaratula) {
		this.sectorInternoCaratula = sectorInternoCaratula;
	}

	public String getSectorInternoCaratula() {
		return sectorInternoCaratula;
	}

}
