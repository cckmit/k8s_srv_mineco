package com.egoveris.deo.model.model;

import java.io.Serializable;

public class FirmanteDTO implements Serializable{

	private static final long serialVersionUID = 5648927009078025114L;
	
	private Integer id;
	private String workflowId;
	private Integer orden;
	private String usuarioFirmante;
	private String usuarioRevisor;
	private String apoderado;
	
	/**
	 * 1: Si el usuario indicado ya firmo el documento en proceso  de producción.
	 * 0: Si el usuario aún no ha firmado el documento en proceso de producción.
	 */
	private boolean estadoFirma;
	
	/**
	 * 1: Si el usuario indicado ya verificó el documento en proceso de producción.
	 * 0: Si el usuario aún no ha verificado el documento en proceso de producción.
	 */
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

  @Override
  public String toString() {
    return "FirmanteDTO [id=" + id + ", workflowId=" + workflowId + ", orden=" + orden
        + ", usuarioFirmante=" + usuarioFirmante + ", usuarioRevisor=" + usuarioRevisor
        + ", apoderado=" + apoderado + ", estadoFirma=" + estadoFirma + ", estadoRevision="
        + estadoRevision + "]";
  }
	
	
	
}
