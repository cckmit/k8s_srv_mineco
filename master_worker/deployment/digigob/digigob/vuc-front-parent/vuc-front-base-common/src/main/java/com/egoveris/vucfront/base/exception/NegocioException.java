package com.egoveris.vucfront.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class NegocioException extends ApplicationException {

	private static final long serialVersionUID = 2091308418250647042L;

	public NegocioException(String message) {
		super(message);

	}

	public NegocioException(String message, Throwable cause) {
		super(message, cause);
		
	}

}
