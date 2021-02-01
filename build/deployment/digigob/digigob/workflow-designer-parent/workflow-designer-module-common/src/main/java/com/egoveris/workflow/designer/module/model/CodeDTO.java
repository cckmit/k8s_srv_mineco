package com.egoveris.workflow.designer.module.model;

import java.io.Serializable;

public class CodeDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1604361248467475430L;
	private Long id;
	private String code;
	private String message;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	
	
	
	
}
