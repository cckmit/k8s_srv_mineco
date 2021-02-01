package com.egoveris.deo.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class ConnectionProblemException extends ApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7442376089783928349L;

	public ConnectionProblemException(final String message, Throwable cause) {
		super(message, cause);
	}

}
