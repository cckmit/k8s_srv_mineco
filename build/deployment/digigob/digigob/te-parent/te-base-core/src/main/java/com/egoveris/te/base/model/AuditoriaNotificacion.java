package com.egoveris.te.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
         
@Entity
@Table(name="EE_AUDITORIA_NOTIFICACION")
public class AuditoriaNotificacion {

	@Id
	@Column(name="ID_AUDITORIA")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long idAuditoria;
	
	@Column(name="USUARIO")
	private String usuario;
	
	@Column(name="ACTUACION")
	private String actuacion;
	
	@Column(name="ANIO")
	private Integer anio;
	
	@Column(name="NUMERO")
	private Integer numero;
	
	@Column(name="REPARTICION")
	private String reparticion;
	
	@Column(name="FECHA_OPERACION")
	private Date fechaOperacion;
	
	@Column(name="ID_DOCUMENTO")
	private Long idDocumento;
	
	@Column(name="TIPO_DOCUMENTO")
	private String tipoDocumento;

	/**
	 * @return the idAuditoria
	 */
	public Long getIdAuditoria() {
		return idAuditoria;
	}

	/**
	 * @param idAuditoria the idAuditoria to set
	 */
	public void setIdAuditoria(Long idAuditoria) {
		this.idAuditoria = idAuditoria;
	}

	/**
	 * @return the usuario
	 */
	public String getUsuario() {
		return usuario;
	}

	/**
	 * @param usuario the usuario to set
	 */
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	/**
	 * @return the actuacion
	 */
	public String getActuacion() {
		return actuacion;
	}

	/**
	 * @param actuacion the actuacion to set
	 */
	public void setActuacion(String actuacion) {
		this.actuacion = actuacion;
	}

	/**
	 * @return the anio
	 */
	public Integer getAnio() {
		return anio;
	}

	/**
	 * @param anio the anio to set
	 */
	public void setAnio(Integer anio) {
		this.anio = anio;
	}

	/**
	 * @return the numero
	 */
	public Integer getNumero() {
		return numero;
	}

	/**
	 * @param numero the numero to set
	 */
	public void setNumero(Integer numero) {
		this.numero = numero;
	}

	/**
	 * @return the reparticion
	 */
	public String getReparticion() {
		return reparticion;
	}

	/**
	 * @param reparticion the reparticion to set
	 */
	public void setReparticion(String reparticion) {
		this.reparticion = reparticion;
	}

	/**
	 * @return the fechaOperacion
	 */
	public Date getFechaOperacion() {
		return fechaOperacion;
	}

	/**
	 * @param fechaOperacion the fechaOperacion to set
	 */
	public void setFechaOperacion(Date fechaOperacion) {
		this.fechaOperacion = fechaOperacion;
	}

	/**
	 * @return the idDocumento
	 */
	public Long getIdDocumento() {
		return idDocumento;
	}

	/**
	 * @param idDocumento the idDocumento to set
	 */
	public void setIdDocumento(Long idDocumento) {
		this.idDocumento = idDocumento;
	}

	/**
	 * @return the tipoDocumento
	 */
	public String getTipoDocumento() {
		return tipoDocumento;
	}

	/**
	 * @param tipoDocumento the tipoDocumento to set
	 */
	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}
	
	
	 
}
