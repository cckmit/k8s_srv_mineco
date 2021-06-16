package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class DeclaracionDTO extends AbstractCComplejoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	protected long declarationId;
	protected String declaracionEnum;
	protected String submittedBy;
	protected Date submittedDate;
	protected Date respDate;
	protected String responseStatus;
	protected String responseCode;
	protected List<ObservacionDTO> listObservaciones;
	protected String uRL;
	protected Date fechaVencimiento;
	protected String tipoFormulario;
	protected String codigoDeclaracion;
	protected String numeroIdentificacion;
	protected String indicaDocumentoParcial;

	public long getDeclarationId() {
		return declarationId;
	}

	public void setDeclarationId(long declarationId) {
		this.declarationId = declarationId;
	}

	public String getDeclaracionEnum() {
		return declaracionEnum;
	}

	public void setDeclaracionEnum(String declaracionEnum) {
		this.declaracionEnum = declaracionEnum;
	}

	public String getSubmittedBy() {
		return submittedBy;
	}

	public void setSubmittedBy(String submittedBy) {
		this.submittedBy = submittedBy;
	}

	public Date getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(Date submittedDate) {
		this.submittedDate = submittedDate;
	}

	public Date getRespDate() {
		return respDate;
	}

	public void setRespDate(Date respDate) {
		this.respDate = respDate;
	}

	public String getResponseStatus() {
		return responseStatus;
	}

	public void setResponseStatus(String responseStatus) {
		this.responseStatus = responseStatus;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public List<ObservacionDTO> getListObservaciones() {
		return listObservaciones;
	}

	public void setListObservaciones(List<ObservacionDTO> listObservaciones) {
		this.listObservaciones = listObservaciones;
	}

	public String getuRL() {
		return uRL;
	}

	public void setuRL(String uRL) {
		this.uRL = uRL;
	}

	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}

	public String getTipoFormulario() {
		return tipoFormulario;
	}

	public void setTipoFormulario(String tipoFormulario) {
		this.tipoFormulario = tipoFormulario;
	}

	public String getCodigoDeclaracion() {
		return codigoDeclaracion;
	}

	public void setCodigoDeclaracion(String codigoDeclaracion) {
		this.codigoDeclaracion = codigoDeclaracion;
	}

	/**
	 * @return the numeroIdentificacion
	 */
	public String getNumeroIdentificacion() {
		return numeroIdentificacion;
	}

	/**
	 * @param numeroIdentificacion
	 *            the numeroIdentificacion to set
	 */
	public void setNumeroIdentificacion(String numeroIdentificacion) {
		this.numeroIdentificacion = numeroIdentificacion;
	}

	/**
	 * @return the indicaDocumentoParcial
	 */
	public String getIndicaDocumentoParcial() {
		return indicaDocumentoParcial;
	}

	/**
	 * @param indicaDocumentoParcial
	 *            the indicaDocumentoParcial to set
	 */
	public void setIndicaDocumentoParcial(String indicaDocumentoParcial) {
		this.indicaDocumentoParcial = indicaDocumentoParcial;
	}
	
	

}
