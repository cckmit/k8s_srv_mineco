package com.egoveris.vucfront.model.model;


public class ResponseDTO {

	public ResponseDTO() {
		super();
	}
	

	public ResponseDTO(int status, String message) {
		super();
		this.status = status;
		this.message = message;
	}


	private int status;
	private String message;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
}
