package com.egoveris.te.model.model;


import java.io.Serializable;

/**
 * La presente clase da forma a los datos que se retornan al logearse en la app.
 * 
 * @author everis
 */
public class UserRequest implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1358291145144128010L;

	/**
	 * user data.
	 */
	private ValidateUser user;
	
	/**
	 * Status.
	 */ 
	protected Status status;

	/**
	 * @return the user
	 */
	public ValidateUser getUser() {
		return user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser(ValidateUser user) {
		this.user = user;
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
