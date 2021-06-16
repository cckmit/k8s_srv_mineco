package com.egoveris.deo.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "GEDO_FIRMANTES")
public class Firmante {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "WORKFLOWID")
	private String workflowId;

	@Column(name = "ORDEN")
	private Integer orden;

	@Column(name = "USUARIOFIRMANTE")
	private String usuarioFirmante;

	@Column(name = "USUARIOREVISOR")
	private String usuarioRevisor;

	@Column(name = "APODERADO")
	private String apoderado;

	/**
	 * 1: Si el usuario indicado ya firmo el documento en proceso de producción.
	 * 0: Si el usuario aún no ha firmado el documento en proceso de producción.
	 */
	@Column(name = "ESTADOFIRMA")
	private boolean estadoFirma;

	/**
	 * 1: Si el usuario indicado ya verificó el documento en proceso de
	 * producción. 0: Si el usuario aún no ha verificado el documento en proceso
	 * de producción.
	 */
	@Column(name = "ESTADOREVISION")
	private boolean estadoRevision;

	public String getWorkflowId() {
		return workflowId;
	}

	public void setWorkflowId(String workflowId) {
		this.workflowId = workflowId;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}

	public String getUsuarioFirmante() {
		return usuarioFirmante;
	}

	public void setUsuarioFirmante(String usuarioFirmante) {
		this.usuarioFirmante = usuarioFirmante;
	}

	public boolean getEstadoFirma() {
		return estadoFirma;
	}

	public void setEstadoFirma(boolean estadoFirma) {
		this.estadoFirma = estadoFirma;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public String getUsuarioRevisor() {
		return usuarioRevisor;
	}

	public void setUsuarioRevisor(String usuarioRevisor) {
		this.usuarioRevisor = usuarioRevisor;
	}

	public boolean isEstadoRevision() {
		return estadoRevision;
	}

	public void setEstadoRevision(boolean estadoRevision) {
		this.estadoRevision = estadoRevision;
	}

	public String getApoderado() {
		return apoderado;
	}

	public void setApoderado(String apoderado) {
		this.apoderado = apoderado;
	}

}
