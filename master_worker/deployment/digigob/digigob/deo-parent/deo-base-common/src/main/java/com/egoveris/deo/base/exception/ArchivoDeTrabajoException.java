package com.egoveris.deo.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class ArchivoDeTrabajoException extends ApplicationException {

	private static final long serialVersionUID = 2091308418250647042L;

	public ArchivoDeTrabajoException(String message) {
		super(message);

	}

	public ArchivoDeTrabajoException(String message, Throwable cause) {
		super(message, cause);
		
	}

}
