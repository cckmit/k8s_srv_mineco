package com.egoveris.te.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Date;

@Entity
@Table(name="TE_REGISTRY_TRANSACTION")
public class RegistryTransaction {

	@Id
	@Column(name="ID")
	@GeneratedValue
	private Long id;
	
	@Column(name="ID_TRANSACTION")
	private String idTransaction;
	
	@Column(name="RESPONSE_DATE")
	private Date responseDate;
	
	@Column(name="REQUEST_DATE")
	private Date requestDate;
	
	@Column(name="MESSAGE")
	private String message;

	@Column(name="ID_MENSSAGE")
	private String idMessage;
	
	@Column(name="RECEPTION_CODE")
	private String receptionCode;
	
	@Column(name="RECEPTION_DESCRIPTION")
	private String receptionDescription;

	
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

	public Date getResponseDate() {
		return responseDate;
	}

	public void setResponseDate(Date responseDate) {
		this.responseDate = responseDate;
	}

	public Date getRequestDate() {
		return requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getIdMessage() {
		return idMessage;
	}

	public void setIdMessage(String idMessage) {
		this.idMessage = idMessage;
	}

	public String getReceptionCode() {
		return receptionCode;
	}

	public void setReceptionCode(String receptionCode) {
		this.receptionCode = receptionCode;
	}

	public String getReceptionDescription() {
		return receptionDescription;
	}

	public void setReceptionDescription(String receptionDescription) {
		this.receptionDescription = receptionDescription;
	}
	
	

	
	
}
