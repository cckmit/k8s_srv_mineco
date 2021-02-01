package com.egoveris.te.model.model;

import java.io.Serializable;
import java.util.List;

// DTO Resolucion subsanacion
// Utilizado desde el servicio IActividadExpService
public class ResolucionSubsRequest implements Serializable {

	private static final long serialVersionUID = -8934395080919309692L;

	private String numeroExpediente;
	private List<ResolucionSubsDocRequest> listDocAct;

	public String getNumeroExpediente() {
		return numeroExpediente;
	}

	public void setNumeroExpediente(String numeroExpediente) {
		this.numeroExpediente = numeroExpediente;
	}

	public List<ResolucionSubsDocRequest> getListDocAct() {
		return listDocAct;
	}

	public void setListDocAct(List<ResolucionSubsDocRequest> listDocAct) {
		this.listDocAct = listDocAct;
	}
}
