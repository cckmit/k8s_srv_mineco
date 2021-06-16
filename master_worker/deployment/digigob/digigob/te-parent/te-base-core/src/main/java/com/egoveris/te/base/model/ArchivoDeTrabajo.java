package com.egoveris.te.base.model;

import com.egoveris.te.base.model.tipo.TipoArchivoTrabajo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "EE_ARCHIVO_DE_TRABAJO")
public class ArchivoDeTrabajo {

	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "NOMBRE_ARCHIVO")
	private String nombreArchivo;

	@Column(name = "DEFINITIVO")
	private boolean definitivo = false;

	@Column(name = "USUARIOASOCIADOR")
	private String usuarioAsociador;

	@Column(name = "FECHAASOCIACION")
	private Date fechaAsociacion;

	@Column(name = "ID_TASK")
	private String idTask;

	@Column(name = "ID_EXP_CABECERA_TC")
	private Integer idExpCabeceraTC;

	@Column(name = "TIPO_RESERVA")
	private Integer tipoReserva;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ID_TIPO_ARCHIVO_TRABAJO")
	private TipoArchivoTrabajo tipoArchivoTrabajo;

	@Column(name = "ID_GUARDA_DOCUMENTAL")
	private String idGuardaDocumental;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombreArchivo() {
		return nombreArchivo;
	}

	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}

	public boolean isDefinitivo() {
		return definitivo;
	}

	public void setDefinitivo(boolean definitivo) {
		this.definitivo = definitivo;
	}

	public String getUsuarioAsociador() {
		return usuarioAsociador;
	}

	public void setUsuarioAsociador(String usuarioAsociador) {
		this.usuarioAsociador = usuarioAsociador;
	}

	public Date getFechaAsociacion() {
		return fechaAsociacion;
	}

	public void setFechaAsociacion(Date fechaAsociacion) {
		this.fechaAsociacion = fechaAsociacion;
	}

	public String getIdTask() {
		return idTask;
	}

	public void setIdTask(String idTask) {
		this.idTask = idTask;
	}

	public Integer getIdExpCabeceraTC() {
		return idExpCabeceraTC;
	}

	public void setIdExpCabeceraTC(Integer idExpCabeceraTC) {
		this.idExpCabeceraTC = idExpCabeceraTC;
	}

	public Integer getTipoReserva() {
		return tipoReserva;
	}

	public void setTipoReserva(Integer tipoReserva) {
		this.tipoReserva = tipoReserva;
	}

	public TipoArchivoTrabajo getTipoArchivoTrabajo() {
		return tipoArchivoTrabajo;
	}

	public void setTipoArchivoTrabajo(TipoArchivoTrabajo tipoArchivoTrabajo) {
		this.tipoArchivoTrabajo = tipoArchivoTrabajo;
	}

	public String getIdGuardaDocumental() {
		return idGuardaDocumental;
	}

	public void setIdGuardaDocumental(String idGuardaDocumental) {
		this.idGuardaDocumental = idGuardaDocumental;
	}

}
