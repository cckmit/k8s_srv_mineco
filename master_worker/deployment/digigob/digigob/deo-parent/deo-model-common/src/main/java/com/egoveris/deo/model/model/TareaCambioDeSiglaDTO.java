package com.egoveris.deo.model.model;

import java.io.Serializable;
import java.util.Date;

public class TareaCambioDeSiglaDTO implements Serializable {

	private static final long serialVersionUID = 7906721943937251301L;

	private Integer id;
	
	private String tarea;
	
	private String token;
	
	private String codigoReparticionOrigen;
	
	private String codigoReparticionDestino;
	
	private String codigoSectorOrigen;
	
	private String codigoSectorDestino;
	
	private String usuarioBaja;
	
	private Date fecha;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTarea() {
		return tarea;
	}

	public void setTarea(String tarea) {
		this.tarea = tarea;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getCodigoReparticionOrigen() {
		return codigoReparticionOrigen;
	}

	public void setCodigoReparticionOrigen(String codigReparticionOrigen) {
		this.codigoReparticionOrigen = codigReparticionOrigen;
	}

	public String getCodigoReparticionDestino() {
		return codigoReparticionDestino;
	}

	public void setCodigoReparticionDestino(String codigoReparticionDestino) {
		this.codigoReparticionDestino = codigoReparticionDestino;
	}

	public String getCodigoSectorOrigen() {
		return codigoSectorOrigen;
	}

	public void setCodigoSectorOrigen(String codigoSectorOrigen) {
		this.codigoSectorOrigen = codigoSectorOrigen;
	}

	public String getCodigoSectorDestino() {
		return codigoSectorDestino;
	}

	public void setCodigoSectorDestino(String codigoSectorDestino) {
		this.codigoSectorDestino = codigoSectorDestino;
	}

	public String getUsuarioBaja() {
		return usuarioBaja;
	}

	public void setUsuarioBaja(String usuarioBaja) {
		this.usuarioBaja = usuarioBaja;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	
	
}
