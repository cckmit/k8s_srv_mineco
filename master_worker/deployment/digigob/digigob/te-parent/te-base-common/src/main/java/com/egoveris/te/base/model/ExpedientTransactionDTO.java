package com.egoveris.te.base.model;

import java.io.Serializable;
import java.util.Date;

public class ExpedientTransactionDTO   implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6738567986021659702L;
	private Long id;
	private String idTransaction;
	private Long idExpedient;
	private Date dateCreation;
	private String code;
	private String message;
	private int status;
	private Long idOperation;
	private OperacionDTO operation;
	private ExpedienteElectronicoDTO expedient;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getIdTransaction() {
		return idTransaction;
	}
	public void setIdTransaction(String idTransaction) {
		this.idTransaction = idTransaction;
	}
	public Long getIdExpedient() {
		return idExpedient;
	}
	public void setIdExpedient(Long idExpedient) {
		this.idExpedient = idExpedient;
	}
	public Date getDateCreation() {
		return dateCreation;
	}
	public void setDateCreation(Date dateCreation) {
		this.dateCreation = dateCreation;
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
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Long getIdOperation() {
		return idOperation;
	}
	public void setIdOperation(Long idOperation) {
		this.idOperation = idOperation;
	}
	public OperacionDTO getOperation() {
		return operation;
	}
	public void setOperation(OperacionDTO operation) {
		this.operation = operation;
	}
	public ExpedienteElectronicoDTO getExpedient() {
		return expedient;
	}
	public void setExpedient(ExpedienteElectronicoDTO expedient) {
		this.expedient = expedient;
	}
}
