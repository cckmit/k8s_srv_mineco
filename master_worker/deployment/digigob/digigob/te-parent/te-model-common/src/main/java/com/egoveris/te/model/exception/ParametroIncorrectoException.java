package com.egoveris.te.model.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class ParametroIncorrectoException extends ApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1434619521809446991L;

	public ParametroIncorrectoException(Throwable cause) {
		super("Wrong Parameter", cause);
	}

	public ParametroIncorrectoException(String message) {
		super(message);
	}

	public ParametroIncorrectoException(String message, Throwable cause) {
		super(message, cause);
	}
}
