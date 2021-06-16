package com.egoveris.commons.databaseconfiguration.exceptions;

public class ParseDateException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2369731868908013188L;

	public ParseDateException() {
	}

	public ParseDateException(String message) {
		super(message);
	}

	public ParseDateException(Throwable cause) {
		super(cause);
	}

	public ParseDateException(String message, Throwable cause) {
		super(message, cause);
	}

}
