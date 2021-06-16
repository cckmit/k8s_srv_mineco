package com.egoveris.numerador.base.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="NUMERO_SADE_SECUENCIA")
public class NumeroSecuencia implements Serializable{

	/**
	 * @author cavazque
	 * Hace referencia a la tabla secuencia
	 * contiene el último número de secuencia para un año
	 * 
	 */
	
	private static final long serialVersionUID = -260819204403064163L;
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_SECUENCIA")
	private Integer id;
	
	@Column(name="ANIO")
	private Integer anio;
	
	@Column(name="NUMERO")
	private Integer  numero;
	
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
	
	

}
