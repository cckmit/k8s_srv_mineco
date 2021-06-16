package com.egoveris.te.base.model;

import com.egoveris.te.model.model.Status;

public class DocumentUnqtAppDTO {

	// data
	private String data; 
	// numeroSade
	private String numeroSade;
	// status
	private Status status;
	
	/**
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(Status status) {
		this.status = status;
	}
	/**
	 * @return the data
	 */
	public String getData() {
		return data;
	}
	/**
	 * @param data the data to set
	 */
	public void setData(String data) {
		this.data = data;
	}
	/**
	 * @return the numeroSade
	 */
	public String getNumeroSade() {
		return numeroSade;
	}
	/**
	 * @param numeroSade the numeroSade to set
	 */
	public void setNumeroSade(String numeroSade) {
		this.numeroSade = numeroSade;
	}
	 
} 
