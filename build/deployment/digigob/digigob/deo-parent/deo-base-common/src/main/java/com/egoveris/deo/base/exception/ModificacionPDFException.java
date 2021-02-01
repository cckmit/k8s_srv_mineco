package com.egoveris.deo.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class ModificacionPDFException extends ApplicationException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1159651943210852003L;

	public ModificacionPDFException(final String message) {
		super(message);
	}

	public ModificacionPDFException(final String message, Throwable cause) {
		super(message, cause);
	}

}
