package com.egoveris.commons.databaseconfiguration.exceptions;

public class DatabaseConfigurationException extends Exception{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -7016429779497685085L;

	public DatabaseConfigurationException() {
	}

	public DatabaseConfigurationException(String message) {
		super(message);
	}

	public DatabaseConfigurationException(Throwable cause) {
		super(cause);
	}

	public DatabaseConfigurationException(String message, Throwable cause) {
		super(message, cause);
	}


}
