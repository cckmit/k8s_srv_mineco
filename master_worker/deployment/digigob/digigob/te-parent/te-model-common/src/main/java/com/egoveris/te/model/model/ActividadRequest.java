package com.egoveris.te.model.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlElementWrapper;

public class ActividadRequest implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5775043419079007919L;
	private String numeroExpediente;
	
	private List<String> documentosOficiales;
	
	private String motivo;

	public String getNumeroExpediente() {
		return numeroExpediente;
	}

	public void setNumeroExpediente(String numeroExpediente) {
		this.numeroExpediente = numeroExpediente;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getMotivo() {
		return motivo;
	}
	
	@XmlElementWrapper(
	name = "documentosOficiales",
	required = true,
	nillable = false
	)
	public List<String> getDocumentosOficiales() {
		if(this.documentosOficiales==null) this.documentosOficiales = new LinkedList<String>(); 
		return this.documentosOficiales;
}
	public void setDocumentosOficiales(List<String> documentosOficiales) {
		this.documentosOficiales = documentosOficiales;
	}	
}
