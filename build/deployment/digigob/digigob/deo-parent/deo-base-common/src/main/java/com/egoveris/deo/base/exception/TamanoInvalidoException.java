package com.egoveris.deo.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class TamanoInvalidoException extends ApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8750226303394499403L;


	public TamanoInvalidoException(final String message) {
		super(message);
	}


	public TamanoInvalidoException(final String message, Throwable cause) {
		super(message, cause);
	}

}
