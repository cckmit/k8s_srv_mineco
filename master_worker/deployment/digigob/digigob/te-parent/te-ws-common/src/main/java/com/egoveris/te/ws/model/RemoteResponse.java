package com.egoveris.te.ws.model;

import java.io.Serializable;

public class RemoteResponse implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8508892016350248504L;
	private int httpStatus;
	private String message;
	private int statusApp;
	
	public int getHttpStatus() {
		return httpStatus;
	}
	public void setHttpStatus(int httpStatus) {
		this.httpStatus = httpStatus;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getStatusApp() {
		return statusApp;
	}
	public void setStatusApp(int statusApp) {
		this.statusApp = statusApp;
	}
	
	
	
	
	
}
