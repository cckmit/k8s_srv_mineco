package com.egoveris.deo.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class FormatoInvalidoException extends ApplicationException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2498972083439837596L;

	public FormatoInvalidoException(final String message) {
		super(message);

	}

	public FormatoInvalidoException(final String message, Throwable cause) {
		super(message, cause);

	}

}
