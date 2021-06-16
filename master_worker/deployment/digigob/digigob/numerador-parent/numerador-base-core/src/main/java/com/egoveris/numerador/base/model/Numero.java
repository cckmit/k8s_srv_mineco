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
@Table(name="NUMERO_SADE")
public class Numero implements Serializable {

	/**
	 * @author cavazque
	 * Hace referencia a la tabla numero_sade_usado
	 * contiene todos los números_sade cuyo estado
	 * sea usado o baja.
	 */
	private static final long serialVersionUID = -3551399622194198719L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_NUMERO_SADE")
	private Integer id;
	
	@Column(name="ANIO")
	private Integer anio;
	
	@Column(name="NUMERO")
	private Integer numero;
	
	@Column(name="SECUENCIA")
	private String secuencia;
	
	@Column(name="ESTADO")
	private String estado;
	
	@Column(name="FECHA_CREACION")
	private Date fechaCreacion;
	
	@Column(name="FECHA_MODIFICACION")
	private Date fechaModificacion;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public Date getFechaModificacion() {
		return fechaModificacion;
	}
	public void setFechaModificacion(Date fechaModificacion) {
		this.fechaModificacion = fechaModificacion;
	}
	
		
}
