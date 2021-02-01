package com.egoveris.numerador.base.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="NUMERO_SISTEMAS_SADE")
public class NumeroSistema implements Serializable {

	/**
	 * @author cavazque
	 * Hace referencia a la tabla sistema_numerador_sade
	 * contiene los sistemas existentes que pueden solicitar un número
	 */
	private static final long serialVersionUID = 3044316507464356216L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="ID_SISTEMA")
	private Integer id;
	
	@Column(name="NOMBRE_SISTEMA")
	private String nombreSistema;
	
	@Column(name="LINK_SISTEMA")
	private String linkSistema;
	
	@Column(name="ACTIVO")
	private boolean activo;
	
	public Integer getid() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNombreSistema() {
		return nombreSistema;
	}
	public void setNombreSistema(String nombreSistema) {
		this.nombreSistema = nombreSistema;
	}
	public String getLinkSistema() {
		return linkSistema;
	}
	public void setLinkSistema(String linkSistema) {
		this.linkSistema = linkSistema;
	}
	public boolean isActivo() {
		return activo;
	}
	public void setActivo(boolean activo) {
		this.activo = activo;
	}
	
	
	
		

}
