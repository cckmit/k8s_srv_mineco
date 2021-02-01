package com.egoveris.sharedsecurity.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "edt_sade_usr_repa_habilitada")
public class UsuarioReparticionHabilitada {

	@Id
	@Column(name = "ID_USR_REPA_HABILITADA")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "ID_REPARTICION")
	Reparticion reparticion;

	@Column(name = "NOMBRE_USUARIO")
	String nombreUsuario;

	@ManyToOne
	@JoinColumn(name = "ID_SECTOR_INTERNO")
	Sector sector;

	@Transient
	private Date fechaRevision;

	@Column(name = "CARGO_ID")
	private Integer cargoId;

	@Transient
	private Cargo cargo;

	public Date getFechaRevision() {
		return fechaRevision;
	}

	public void setFechaRevision(Date fechaRevision) {
		this.fechaRevision = fechaRevision;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Reparticion getReparticion() {
		return reparticion;
	}

	public void setReparticion(Reparticion reparticion) {
		this.reparticion = reparticion;
	}

	public String getNombreUsuario() {
		return nombreUsuario;
	}

	public void setNombreUsuario(String nombreUsuario) {
		this.nombreUsuario = nombreUsuario;
	}

	public Sector getSector() {
		return sector;
	}

	public void setSector(Sector sector) {
		this.sector = sector;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public Integer getCargoId() {
		return cargoId;
	}

	public void setCargoId(Integer cargoId) {
		this.cargoId = cargoId;
	}
}