package com.egoveris.te.base.model.expediente;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="EE_ARCHIVO_REMISION")
public class ExpedienteRemision {
	
	@Id
	@Column(name="ID")
	@GeneratedValue(strategy=GenerationType.AUTO) 
	private Long id;
	
	@Column(name="ID_EXPEDIENTE")
	private Integer idExpediente;
	
	@Column(name="ID_EXPEDIENTE_REMISION")
	private Integer idExpedienteRemision;
	
	@Column(name="FECHA_DE_SOLICITUD")
	private Date fechaSolicitud;

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
	 * @return the idExpediente
	 */
	public Integer getIdExpediente() {
		return idExpediente;
	}

	/**
	 * @param idExpediente the idExpediente to set
	 */
	public void setIdExpediente(Integer idExpediente) {
		this.idExpediente = idExpediente;
	}

	/**
	 * @return the idExpedienteRemision
	 */
	public Integer getIdExpedienteRemision() {
		return idExpedienteRemision;
	}

	/**
	 * @param idExpedienteRemision the idExpedienteRemision to set
	 */
	public void setIdExpedienteRemision(Integer idExpedienteRemision) {
		this.idExpedienteRemision = idExpedienteRemision;
	}

	/**
	 * @return the fechaSolicitud
	 */
	public Date getFechaSolicitud() {
		return fechaSolicitud;
	}

	/**
	 * @param fechaSolicitud the fechaSolicitud to set
	 */
	public void setFechaSolicitud(Date fechaSolicitud) {
		this.fechaSolicitud = fechaSolicitud;
	}
	
	 
}
