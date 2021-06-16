package com.egoveris.trade.common.model;

import java.io.Serializable;
import java.util.Map;

import com.egoveris.ffdd.model.model.FormularioWDDTO;

public class RequestMessageDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String idMessage;
	private String idTransaction;
	private Long idOperation;
	private FormularioWDDTO form;
	private String code;
	private String message;
	private Map<String, Object> formValues;
	private String typeCall;
	
	public String getIdMessage() {
		return idMessage;
	}
	public void setIdMessage(String idMessage) {
		this.idMessage = idMessage;
	}
	public String getIdTransaction() {
		return idTransaction;
	}
	public void setIdTransaction(String idTransaction) {
		this.idTransaction = idTransaction;
	}
	public Long getIdOperation() {
		return idOperation;
	}
	public void setIdOperation(Long idOperation) {
		this.idOperation = idOperation;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public FormularioWDDTO getForm() {
		return form;
	}
	public void setForm(FormularioWDDTO form) {
		this.form = form;
	}
	public Map<String, Object> getFormValues() {
		return formValues;
	}
	public void setFormValues(Map<String, Object> formValues) {
		this.formValues = formValues;
	}
	public String getTypeCall() {
		return typeCall;
	}
	public void setTypeCall(String typeCall) {
		this.typeCall = typeCall;
	}
	
}
