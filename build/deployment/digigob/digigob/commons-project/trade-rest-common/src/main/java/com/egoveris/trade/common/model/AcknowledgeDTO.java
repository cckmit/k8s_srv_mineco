package com.egoveris.trade.common.model;

import java.io.Serializable;

public class AcknowledgeDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3531707058723178552L;
	private String receptionCode;
	private String receptionDescription;
	private String idMessage;
	private String idTransaction;
	
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
	
	
	
}
