package com.egoveris.edt.base.model;

import java.io.Serializable;

public class Status implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -967722749988574138L;
	
	private int code;
	private String desc;
	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}
	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}
	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	} 
	
	
	 	
}
