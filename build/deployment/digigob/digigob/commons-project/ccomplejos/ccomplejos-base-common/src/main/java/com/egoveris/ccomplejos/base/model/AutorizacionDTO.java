
package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class AutorizacionDTO extends AbstractCComplejoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	protected Long idAutorizacion;
	protected Date submittedDate;
	protected String responseStatus;
	protected Date vigenciaDate;
	protected String uRL;
	protected String submittedBy;
	protected Date respDate;
	protected String responseCode;
	protected DocumentDTO documento;
	protected List<ObservacionDTO> listObservaciones;
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
	public DocumentDTO getDocumento() {
		return documento;
	}
	public void setDocumento(DocumentDTO documento) {
		this.documento = documento;
	}
	public List<ObservacionDTO> getListObservaciones() {
		return listObservaciones;
	}
	public void setListObservaciones(List<ObservacionDTO> listObservaciones) {
		this.listObservaciones = listObservaciones;
	}
	public String getSsppEnum() {
		return ssppEnum;
	}
	public void setSsppEnum(String ssppEnum) {
		this.ssppEnum = ssppEnum;
	}
	
}

