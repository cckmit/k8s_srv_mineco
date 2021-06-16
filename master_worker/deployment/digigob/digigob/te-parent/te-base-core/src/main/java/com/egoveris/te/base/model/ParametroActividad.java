package com.egoveris.te.base.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ACTIVIDAD_PARAM")
public class ParametroActividad implements Serializable {

	private static final long serialVersionUID = -2037353043609831629L;

	@GeneratedValue(strategy=GenerationType.AUTO)
	@Id
	@Column(name = "ID")
	private Long id; 
	
	@Column(name = "valor")
	private String valor;
	
	@Column(name = "CAMPO")
	private String campo;
	  
	@OneToOne
	@JoinColumn(name = "ID_ACTIVIDAD")
	private Actividad idActividad;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}
	
	@Override
	public String toString() {
		return valor;
	}

	/**
	 * @return the campo
	 */
	public String getCampo() {
		return campo;
	}

	/**
	 * @param campo the campo to set
	 */
	public void setCampo(String campo) {
		this.campo = campo;
	}

	/**
	 * @return the idActividad
	 */
	public Actividad getIdActividad() {
		return idActividad;
	}

	/**
	 * @param idActividad the idActividad to set
	 */
	public void setIdActividad(Actividad idActividad) {
		this.idActividad = idActividad;
	}

 

	 
	
	
}
