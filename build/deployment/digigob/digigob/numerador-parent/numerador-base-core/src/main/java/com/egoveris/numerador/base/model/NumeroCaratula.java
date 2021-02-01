package com.egoveris.numerador.base.model;

import java.io.Serializable;
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
@Table(name="NUMERO_SADE_CARATULA")
public class NumeroCaratula implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1359422832110719496L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_NUMERO_SADE_CARATULA")
	private Integer id;
	
	@ManyToOne (fetch=FetchType.LAZY)
	@JoinColumn(name = "ID_NUMERO_SADE")
	private Numero numeroSade;
	
	@Column(name="SISTEMA")
	private String sistema;
	
	@Column(name="USUARIO")
	private String usuario;
	
	@Column(name="FECHA_CREACION_CARATULA")
	private Date fechaCreacionCaratula;
	
	@Column(name="REPARTICION_ACTUACION")
	private String reparticionActuacion;
	
	@Column(name="REPARTICION_USUARIO")
	private String reparticionUsuario;
	
	@Column(name="TIPO_ACTUACION")
	private String tipoActuacion;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	
	public Numero getNumeroSade() {
		return numeroSade;
	}
	public void setNumeroSade(Numero numeroSade) {
		this.numeroSade = numeroSade;
	}
	public String getSistema() {
		return sistema;
	}
	public void setSistema(String sistema) {
		this.sistema = sistema;
	}
	public String getUsuario() {
		return usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public Date getFechaCreacionCaratula() {
		return fechaCreacionCaratula;
	}
	public void setFechaCreacionCaratula(Date fechaCreacionCaratula) {
		this.fechaCreacionCaratula = fechaCreacionCaratula;
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
	public String getTipoActuacion() {
		return tipoActuacion;
	}
	public void setTipoActuacion(String tipoActuacion) {
		this.tipoActuacion = tipoActuacion;
	}
	
	
	
	
}
