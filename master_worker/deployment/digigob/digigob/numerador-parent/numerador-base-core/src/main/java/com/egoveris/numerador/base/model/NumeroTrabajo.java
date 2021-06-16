package com.egoveris.numerador.base.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="NUMERO_SADE_TRABAJO")
public class NumeroTrabajo implements Serializable {

	/**
	 * @author cavazque
	 * Hace referencia a la tabla numero_sade_trabajo
	 * Contiene todos los números_sade que no fueron migrados a la tabla numero_sade_usado
	 * 
	 */
	private static final long serialVersionUID = 2121145742122485629L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_SECUENCIA_USADA_DIARIA")
	private Integer id;
	
	@Column(name="SISTEMA")
	private String sistema;
	
	@Column(name="USUARIO")
	private String usuario;
	
	@Column(name="TIPO_ACTUACION")
	private String tipoActuacion;
	
	@Column(name="ANIO")
	private Integer anio;
	
	@Column(name="NUMERO")
	private Integer numero;
	
	@Column(name="SECUENCIA")
	private String secuencia;
	
	@Column(name="REPARTICION_ACTUACION")
	private String reparticionActuacion;
	
	@Column(name="REPARTICION_USUARIO")
	private String reparticionUsuario;
	
	@Column(name="FECHA_CREACION")
	private Date fechaCreacion;
	
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public String getTipoActuacion() {
		return tipoActuacion;
	}
	public void setTipoActuacion(String tipoActuacion) {
		this.tipoActuacion = tipoActuacion;
	}
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
	
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
			
}
