package com.egoveris.ccomplejos.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CC_VISTA_BULTO_DA")
public class VistaBultoDA  extends AbstractCComplejoJPA {

	@Column(name = "CANTIDAD_ENVASE")
	protected Integer cantidadEnvase;
	@Column(name = "CANTIDAD_BULTOS")
	protected Integer cantidadBultos;
	@Column(name = "TIPO_ENVASE")
	protected String tipoEnvase;
	@Column(name = "TIPO_BULTO")
	protected String tipoBulto;
	@Column(name = "TIPO_SUB_CONTINENTE")
	protected String tipoSubcontinente;
	@Column(name = "SECUENCIAL_BULTO")
	protected Integer secuencialBulto;
	@Column(name = "IDENTIFICACION_BULTO")
	protected String identificacionBulto;
	
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
