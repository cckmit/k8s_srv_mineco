package com.egoveris.te.base.model;

import java.io.Serializable;

public class TrataReparticionDTO implements Serializable {
	
	private static final long serialVersionUID = -6002997424131983993L;
	private String codigoReparticion;
	private Boolean habilitacion;
	private Long idTrata;
	private int orden;
	private Boolean reserva;
	
	public static final String TODAS = "--TODAS--";

	public int getOrden() {
		return orden;
	}
	public void setOrden(int orden) {
		this.orden = orden;
	}
	public String getCodigoReparticion() {
		return codigoReparticion;
	}
	public void setCodigoReparticion(String codigoReparticion) {
		this.codigoReparticion = codigoReparticion;
	}
	public Boolean getHabilitacion() {
		return habilitacion;
	}
	public void setHabilitacion(Boolean habilitacion) {
		this.habilitacion = habilitacion;
	}
	public Long getIdTrata() {
		return idTrata;
	}
	public void setIdTrata(Long idTrata) {
		this.idTrata = idTrata;
	}
	public Boolean getReserva() {
		return reserva;
	}
	public void setReserva(Boolean reserva) {
		this.reserva = reserva;
	}
}
