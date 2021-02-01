package com.egoveris.commons.databaseconfiguration.exceptions;

public class ParametroInvalidoException extends Exception{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -7016429779497685085L;

	public ParametroInvalidoException() {
	}

	public ParametroInvalidoException(String message) {
		super(message);
	}

	public ParametroInvalidoException(Throwable cause) {
		super(cause);
	}

	public ParametroInvalidoException(String message, Throwable cause) {
		super(message, cause);
	}


}
