package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;

public class VistaBultoDADTO extends AbstractCComplejoDTO implements Serializable{

	private static final long serialVersionUID = 5325485743160262793L;
	
	Integer cantidadEnvase;
	Integer cantidadBultos;
	String tipoEnvase;
	String tipoBulto;
	String tipoSubcontinente;
	Integer secuencialBulto;
	String identificacionBulto;
	
	public Integer getCantidadEnvase() {
		return cantidadEnvase;
	}
	public void setCantidadEnvase(Integer cantidadEnvase) {
		this.cantidadEnvase = cantidadEnvase;
	}
	public Integer getCantidadBultos() {
		return cantidadBultos;
	}
	public void setCantidadBultos(Integer cantidadBultos) {
		this.cantidadBultos = cantidadBultos;
	}
	public String getTipoEnvase() {
		return tipoEnvase;
	}
	public void setTipoEnvase(String tipoEnvase) {
		this.tipoEnvase = tipoEnvase;
	}
	public String getTipoBulto() {
		return tipoBulto;
	}
	public void setTipoBulto(String tipoBulto) {
		this.tipoBulto = tipoBulto;
	}
	public String getTipoSubcontinente() {
		return tipoSubcontinente;
	}
	public void setTipoSubcontinente(String tipoSubcontinente) {
		this.tipoSubcontinente = tipoSubcontinente;
	}
	public Integer getSecuencialBulto() {
		return secuencialBulto;
	}
	public void setSecuencialBulto(Integer secuencialBulto) {
		this.secuencialBulto = secuencialBulto;
	}
	public String getIdentificacionBulto() {
		return identificacionBulto;
	}
	public void setIdentificacionBulto(String identificacionBulto) {
		this.identificacionBulto = identificacionBulto;
	}
	
	
}
