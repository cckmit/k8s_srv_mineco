package com.egoveris.numerador.model.model;

import java.util.Date;
import java.io.Serializable;


public class NumeroGeneradoDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3312480522521095617L;
	/**
	 * 
	 */
	private Integer anio;
	private Integer numero;
	private String secuencia;
	private String tipoActuacion;
	private String sistema;
	private String estado;
	private String reparticionActuacion;
	private String reparticionUsuario;
	private Date fechaCreacionCaratula;
	
	public Integer getAnio() {
		return anio;
	}
	public void setAnio(Integer anio) {
		this.anio = anio;
	}
	public Integer getNumero() {
		return numero;
	}
	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	public String getSecuencia() {
		return secuencia;
	}
	public void setSecuencia(String secuencia) {
		this.secuencia = secuencia;
	}
	public String getTipoActuacion() {
		return tipoActuacion;
	}
	public void setTipoActuacion(String tipoActuacion) {
		this.tipoActuacion = tipoActuacion;
	}
	public String getSistema() {
		return sistema;
	}
	public void setSistema(String sistema) {
		this.sistema = sistema;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getReparticionActuacion() {
		return reparticionActuacion;
	}
	public void setReparticionActuacion(String reparticionActuacion) {
		this.reparticionActuacion = reparticionActuacion;
	}
	public String getReparticionUsuario() {
		return reparticionUsuario;
	}
	public void setReparticionUsuario(String reparticionUsuario) {
		this.reparticionUsuario = reparticionUsuario;
	}
	public Date getFechaCreacionCaratula() {
		return fechaCreacionCaratula;
	}
	public void setFechaCreacionCaratula(Date fechaCreacionCaratula) {
		this.fechaCreacionCaratula = fechaCreacionCaratula;
	}
	
	

}
