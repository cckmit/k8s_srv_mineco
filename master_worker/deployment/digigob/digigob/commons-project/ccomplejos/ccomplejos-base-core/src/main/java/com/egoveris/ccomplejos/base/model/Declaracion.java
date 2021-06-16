package com.egoveris.ccomplejos.base.model;

import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "CC_DECLARACION")
public class Declaracion extends AbstractCComplejoJPA {

	@Column(name = "DECLARATION_ID")
	Integer declarationId;

	@Column(name = "DECLARACION_ENUM")
	String declaracionEnum;

	@Column(name = "SUBMITTED_BY")
	String submittedBy;

	@Column(name = "SUBMITTED_DATE")
	Date submittedDate;

	@Column(name = "RESP_DATE")
	Date respDate;

	@Column(name = "RESPONSE_STATUS")
	String responseStatus;

	@OneToMany(orphanRemoval = true, cascade = CascadeType.ALL, mappedBy = "declaracion")
	List<Observacion> listObservaciones;
	
	@Column(name = "RESPONSE_CODE")
	String responseCode;

	@Column(name = "URL")
	String uRL;
	
	@Column(name = "FECHA_VENCIMIENTO")
	Date fechaVencimiento;
	
	@Column(name = "TIPO_FORMULARIO")
	String tipoFormulario;

	@Column(name = "CODIGO_DECLARACION")
	String codigoDeclaracion;

	@Column(name = "NUMERO_IDENTIFICACION")
	String numeroIdentificacion;
	
	@Column(name = "DOCUMENTO_PARCIAL")
	String indicaDocumentoParcial;

	public Integer getDeclarationId() {
		return declarationId;
	}

	public void setDeclarationId(Integer declarationId) {
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
	 * @return the listObservaciones
	 */
	public List<Observacion> getListObservaciones() {
		return listObservaciones;
	}

	/**
	 * @param listObservaciones
	 *            the listObservaciones to set
	 */
	public void setListObservaciones(List<Observacion> listObservaciones) {
		this.listObservaciones = listObservaciones;
	}
	

}
