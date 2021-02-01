package com.egoveris.deo.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class ArchivoFirmadoException extends ApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2434508668951984163L;

	public ArchivoFirmadoException(final String message) {
		super(message);
	}

	public ArchivoFirmadoException(final String message, Throwable cause) {
		super(message, cause);
	}
}
