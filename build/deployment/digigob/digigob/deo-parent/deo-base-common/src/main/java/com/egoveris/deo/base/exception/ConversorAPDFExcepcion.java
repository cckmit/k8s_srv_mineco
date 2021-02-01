package com.egoveris.deo.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class ConversorAPDFExcepcion extends ApplicationException{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2310698179202785359L;

	public ConversorAPDFExcepcion( final String message) {
		super(message);
	}

	public ConversorAPDFExcepcion(final String message, Throwable cause) {
		super(message, cause);
	}

}
