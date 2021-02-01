
package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;
import java.util.Date;

public class DetallesPuertoDTO extends AbstractCComplejoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	protected Long idDetallesPuerto;
	protected String pais;
	protected String codPuerto;
	protected String tipoPuerto;
	protected String nombrePuerto;
	protected String locacionPuerto;
	protected Date fecha;
	protected String region;
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
