package com.egoveris.trade.common.model;

import java.io.Serializable;

public class ExternalMessageDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 298244993238590619L;
	private String idMessage;
	private String idTransaction;
	private String typeForm;
	
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
	public String getTypeForm() {
		return typeForm;
	}
	public void setTypeForm(String typeForm) {
		this.typeForm = typeForm;
	}
	
	
	
	
}
