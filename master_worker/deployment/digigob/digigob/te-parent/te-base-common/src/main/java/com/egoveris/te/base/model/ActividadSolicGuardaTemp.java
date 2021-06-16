package com.egoveris.te.base.model;

import java.io.Serializable;

public class ActividadSolicGuardaTemp extends ActividadCommon implements Serializable {

	private static final long serialVersionUID = 5597414494030925779L;

	private String workFlowId;
	private String nroExpediente;
	private String motivo;
	private String usuarioAlta;
	private String mailDestino;

	public String getWorkFlowId() {
		return workFlowId;
	}

	public void setWorkFlowId(String workFlowId) {
		this.workFlowId = workFlowId;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getUsuarioAlta() {
		return usuarioAlta;
	}

	public void setUsuarioAlta(String usuarioAlta) {
		this.usuarioAlta = usuarioAlta;
	}

	public String getNroExpediente() {
		return nroExpediente;
	}

	public void setNroExpediente(String nroExpediente) {
		this.nroExpediente = nroExpediente;
	}

	public void setMailDestino(String mailDestino) {
		this.mailDestino = mailDestino;
	}

	public String getMailDestino() {
		return mailDestino;
	}
}
