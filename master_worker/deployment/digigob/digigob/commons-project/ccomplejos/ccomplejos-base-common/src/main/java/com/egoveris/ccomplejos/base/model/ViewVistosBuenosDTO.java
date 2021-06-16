package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;
import java.util.Date;

public class ViewVistosBuenosDTO extends AbstractCComplejoDTO implements Serializable {

	private static final long serialVersionUID = 7654862327121732000L;
	
	protected String destinacionAduanera;
	protected String pais;
	protected String codOperacion;
	protected Date createdDate;
	protected String processingStatus;
	
	public String getDestinacionAduanera() {
		return destinacionAduanera;
	}
	public void setDestinacionAduanera(String destinacionAduanera) {
		this.destinacionAduanera = destinacionAduanera;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	public String getCodOperacion() {
		return codOperacion;
	}
	public void setCodOperacion(String codOperacion) {
		this.codOperacion = codOperacion;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public String getProcessingStatus() {
		return processingStatus;
	}
	public void setProcessingStatus(String processingStatus) {
		this.processingStatus = processingStatus;
	}
	
	

}
