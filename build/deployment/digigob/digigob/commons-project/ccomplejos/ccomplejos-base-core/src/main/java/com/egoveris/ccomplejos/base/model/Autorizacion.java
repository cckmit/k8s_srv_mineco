package com.egoveris.ccomplejos.base.model;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CC_AUTORIZACION")
public class Autorizacion extends AbstractCComplejoJPA {

	@Column(name="ID_AUTORIZACION")
	protected Long idAutorizacion;
	
	@Column(name="SUBMIT_DATE")
	protected Date submittedDate;
	
	@Column(name="RESPONSE_STATUS")
	protected String responseStatus;
	
	@Column(name="VIGENCIA_DATE")
	protected Date vigenciaDate;
	
	@Column(name="URL")
	protected String uRL;
	
	@Column(name="SUBMIT_BY")
	protected String submittedBy;
	
	@Column(name="RESPUESTA_DATE")
	protected Date respDate;
	
	@Column(name="RESPONSE_CODE")
	protected String responseCode;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ID_DOCUMENTO")
	protected Document documento;
	
	@ManyToOne
	@JoinColumn(name = "OPERACION_ID", referencedColumnName = "id")
	Operation operation;
	
	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "autorizacion")
	protected List<Observacion> listObservaciones;
	
	@Column(name="SSPP")
	protected String ssppEnum;

	public Long getIdAutorizacion() {
		return idAutorizacion;
	}

	public void setIdAutorizacion(Long idAutorizacion) {
		this.idAutorizacion = idAutorizacion;
	}

	public Date getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(Date submittedDate) {
		this.submittedDate = submittedDate;
	}

	public String getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(String responseStatus) {
		this.responseStatus = responseStatus;
	}

	public Date getVigenciaDate() {
		return vigenciaDate;
	}

	public void setVigenciaDate(Date vigenciaDate) {
		this.vigenciaDate = vigenciaDate;
	}

	public String getuRL() {
		return uRL;
	}

	public void setuRL(String uRL) {
		this.uRL = uRL;
	}

	public String getSubmittedBy() {
		return submittedBy;
	}

	public void setSubmittedBy(String submittedBy) {
		this.submittedBy = submittedBy;
	}

	public Date getRespDate() {
		return respDate;
	}

	public void setRespDate(Date respDate) {
		this.respDate = respDate;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public Document getDocumento() {
		return documento;
	}

	public void setDocumento(Document documento) {
		this.documento = documento;
	}

	public List<Observacion> getListObservaciones() {
		return listObservaciones;
	}

	public void setListObservaciones(List<Observacion> listObservaciones) {
		this.listObservaciones = listObservaciones;
	}

	public String getSsppEnum() {
		return ssppEnum;
	}

	public void setSsppEnum(String ssppEnum) {
		this.ssppEnum = ssppEnum;
	}
	
}