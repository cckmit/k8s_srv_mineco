package com.egoveris.te.model.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.annotation.XmlElementWrapper;

/**
 * Request Relacion de <code>DocumentoOficial</code> - <code>ExpedienteElectronico</code>
 */
public class RequestRelacionDocumentoOficialEE implements Serializable {

	private static final long serialVersionUID = 5249044949488803546L;
	
	private String sistemaUsuario;
	private String usuario;
	private String numeroExpedienteElectronico;
	private List<String> documentosOficiales;
	
	public String getSistemaUsuario() {
		return this.sistemaUsuario;
	}
	public void setSistemaUsuario(String sistemaUsuario) {
		this.sistemaUsuario = sistemaUsuario;
	}
	public String getUsuario() {
		return this.usuario;
	}
	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}
	public String getNumeroExpedienteElectronico() {
		return this.numeroExpedienteElectronico;
	}
	public void setNumeroExpedienteElectronico(String numeroExpedienteElectronico) {
		this.numeroExpedienteElectronico = numeroExpedienteElectronico;
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
