package com.egoveris.te.base.model.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MaskWsDTO {

	private static final Logger logger = LoggerFactory.getLogger(MaskWsDTO.class);
	
	// date
	private String date;
	// user
	private String user;
	// userRequest
	private String userRequest;
	// type
	private String type;
	// description
	private String description;
	// reason
	private String reason;
	 
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}
	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}
	/**
	 * @return the userRequest
	 */
	public String getUserRequest() {
		return userRequest;
	}
	/**
	 * @param userRequest the userRequest to set
	 */
	public void setUserRequest(String userRequest) {
		this.userRequest = userRequest;
	}
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	/**
	 * @return the reason
	 */
	public String getReason() {
		return reason;
	}
	/**
	 * @param reason the reason to set
	 */
	public void setReason(String reason) {
		this.reason = reason;
	}
	 
	
}
