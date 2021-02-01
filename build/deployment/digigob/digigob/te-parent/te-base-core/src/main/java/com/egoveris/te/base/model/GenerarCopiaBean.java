package com.egoveris.te.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="GENERAR_COPIA")
public class GenerarCopiaBean {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="USERNAME")
	private String username;
	
	@Column(name="ID_WORKFLOW")
	private String idWorkflow;
	
	@Column(name="CODIGO_EXPEDIENTE")
	private String codigoExpediente;
	
	@Column(name="REINTENTOS")
	private Integer reintentos;
	
	@Column(name="ID_ACTIVIDAD")
	private Integer idActividad;
	
	@Column(name="ESTADO_DE_REINTENTO")
	private String estadoDeReintento;

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the idWorkflow
	 */
	public String getIdWorkflow() {
		return idWorkflow;
	}

	/**
	 * @param idWorkflow the idWorkflow to set
	 */
	public void setIdWorkflow(String idWorkflow) {
		this.idWorkflow = idWorkflow;
	}

	/**
	 * @return the codigoExpediente
	 */
	public String getCodigoExpediente() {
		return codigoExpediente;
	}

	/**
	 * @param codigoExpediente the codigoExpediente to set
	 */
	public void setCodigoExpediente(String codigoExpediente) {
		this.codigoExpediente = codigoExpediente;
	}

	/**
	 * @return the reintentos
	 */
	public Integer getReintentos() {
		return reintentos;
	}

	/**
	 * @param reintentos the reintentos to set
	 */
	public void setReintentos(Integer reintentos) {
		this.reintentos = reintentos;
	}

	/**
	 * @return the idActividad
	 */
	public Integer getIdActividad() {
		return idActividad;
	}

	/**
	 * @param idActividad the idActividad to set
	 */
	public void setIdActividad(Integer idActividad) {
		this.idActividad = idActividad;
	}

	/**
	 * @return the estadoDeReintento
	 */
	public String getEstadoDeReintento() {
		return estadoDeReintento;
	}

	/**
	 * @param estadoDeReintento the estadoDeReintento to set
	 */
	public void setEstadoDeReintento(String estadoDeReintento) {
		this.estadoDeReintento = estadoDeReintento;
	}
	
	 
}
