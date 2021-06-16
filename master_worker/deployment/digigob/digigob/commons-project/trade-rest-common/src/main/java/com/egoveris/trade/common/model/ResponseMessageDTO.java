package com.egoveris.trade.common.model;

import java.io.Serializable;
import java.util.Map;

import com.egoveris.ffdd.model.model.FormularioWDDTO;

public class ResponseMessageDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String idMessage;
	private String idTransaction;
	private Long idOperation;
	private FormularioWDDTO form;
	private Map<String, Object> formValues;
	private AcknowledgeDTO acknowledge;
	
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
	public AcknowledgeDTO getAcknowledge() {
		return acknowledge;
	}
	public void setAcknowledge(AcknowledgeDTO acknowledge) {
		this.acknowledge = acknowledge;
	}	
}
