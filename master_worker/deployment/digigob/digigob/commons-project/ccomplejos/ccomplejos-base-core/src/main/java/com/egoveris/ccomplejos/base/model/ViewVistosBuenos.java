package com.egoveris.ccomplejos.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CC_VIEWVISTOSBUENOS")
public class ViewVistosBuenos  extends AbstractCComplejoJPA {

	private static final long serialVersionUID = 7654862327121732000L;
	
	@Column(name = "DESTINACIONADUANERA")
	protected String destinacionAduanera;
	@Column(name = "PAIS")
	protected String pais;
	@Column(name = "CODOPERACION")
	protected String codOperacion;
	@Column(name = "CREATEDDATE")
	protected Date createdDate;
	@Column(name = "PROCESSINGSTATUS")
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
