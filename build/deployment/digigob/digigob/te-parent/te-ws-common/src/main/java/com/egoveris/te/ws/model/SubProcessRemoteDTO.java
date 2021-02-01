package com.egoveris.te.ws.model;

import java.io.Serializable;

public class SubProcessRemoteDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6819375123083675751L;
	private Long idSubProcess;
	private Long idOperation;
	private String procedureName;
	private String username;
	
	public Long getIdSubProcess() {
		return idSubProcess;
	}
	public void setIdSubProcess(Long idSubProcess) {
		this.idSubProcess = idSubProcess;
	}
	public Long getIdOperation() {
		return idOperation;
	}
	public void setIdOperation(Long idOperation) {
		this.idOperation = idOperation;
	}
	public String getProcedureName() {
		return procedureName;
	}
	public void setProcedureName(String procedureName) {
		this.procedureName = procedureName;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
	

	
	
}
