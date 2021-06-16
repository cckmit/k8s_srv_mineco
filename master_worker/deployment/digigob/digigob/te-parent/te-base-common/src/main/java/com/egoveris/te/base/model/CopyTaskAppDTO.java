package com.egoveris.te.base.model;

import com.egoveris.te.model.model.Status;

public class CopyTaskAppDTO {

	// newTaskCode
	private String newTaskCode;
	// status
	private Status status;
	
	/**
	 * @return the newTaskCode
	 */
	public String getNewTaskCode() {
		return newTaskCode;
	}
	/**
	 * @param newTaskCode the newTaskCode to set
	 */
	public void setNewTaskCode(String newTaskCode) {
		this.newTaskCode = newTaskCode;
	}
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
	 
} 
