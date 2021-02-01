package com.egoveris.ccomplejos.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CC_DETALLESPUERTO")
public class DetallesPuerto extends AbstractCComplejoJPA {

	@Column(name = "ID_DETALLESPUERTO")
	protected Long idDetallesPuerto;

	@Column(name = "PAIS")
	protected String pais;

	@Column(name = "COD_PUERTO")
	protected String codPuerto;

	@Column(name = "TIPO_PUERTO")
	protected String tipoPuerto;

	@Column(name = "NOMBRE_PUERTO")
	protected String nombrePuerto;

	@Column(name = "LOCACION_PUERTO")
	protected String locacionPuerto;

	@Column(name = "FECHA")
	protected Date fecha;

	@Column(name = "REGION")
	protected String region;

	@Column(name = "FECHA_ESTIMADA")
	protected Date fechaEstimada;

	public Long getIdDetallesPuerto() {
		return idDetallesPuerto;
	}

	public void setIdDetallesPuerto(Long idDetallesPuerto) {
		this.idDetallesPuerto = idDetallesPuerto;
	}

	public String getPais() {
		return pais;
	}

	public void setPais(String pais) {
		this.pais = pais;
	}

	public String getCodPuerto() {
		return codPuerto;
	}

	public void setCodPuerto(String codPuerto) {
		this.codPuerto = codPuerto;
	}

	public String getTipoPuerto() {
		return tipoPuerto;
	}

	public void setTipoPuerto(String tipoPuerto) {
		this.tipoPuerto = tipoPuerto;
	}

	public String getNombrePuerto() {
		return nombrePuerto;
	}

	public void setNombrePuerto(String nombrePuerto) {
		this.nombrePuerto = nombrePuerto;
	}

	public String getLocacionPuerto() {
		return locacionPuerto;
	}

	public void setLocacionPuerto(String locacionPuerto) {
		this.locacionPuerto = locacionPuerto;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public Date getFechaEstimada() {
		return fechaEstimada;
	}

	public void setFechaEstimada(Date fechaEstimada) {
		this.fechaEstimada = fechaEstimada;
	}
	
	
}
