package com.egoveris.te.base.model;

import java.io.Serializable;
import java.util.Date;

public class TrataReparticionAuditoriaDTO implements Serializable {
    private static final long serialVersionUID = -2032497240542598591L;
	private Long id;
	private Date fechaOperacion;
	private Long id_trata;
	private String usuario;
	private String tipoOperacion;
	private boolean habilitacion;	
	private String  codigoReparticion;
	private boolean reserva;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getFechaOperacion() {
		return fechaOperacion;
	}
	public void setFechaOperacion(Date fechaOperacion) {
		this.fechaOperacion = fechaOperacion;
	}
	public Long getId_trata() {
		return id_trata;
	}
	public void setId_trata(Long id_trata) {
		this.id_trata = id_trata;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getTipoOperacion() {
		return tipoOperacion;
	}
	public void setTipoOperacion(String tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}
	public boolean isHabilitacion() {
		return habilitacion;
	}
	public void setHabilitacion(boolean habilitacion) {
		this.habilitacion = habilitacion;
	}
	public String getCodigoReparticion() {
		return codigoReparticion;
	}
	public void setCodigoReparticion(String codigoReparticion) {
		this.codigoReparticion = codigoReparticion;
	}
	public boolean isReserva() {
		return reserva;
	}
	public void setReserva(boolean reserva) {
		this.reserva = reserva;
	}
}
