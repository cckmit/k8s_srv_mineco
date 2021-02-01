package com.egoveris.deo.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class DataDocumentoNulaExcepcion extends ApplicationException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2596008653664430748L;


	public DataDocumentoNulaExcepcion(final String message) {
		super(message);
	}

	public DataDocumentoNulaExcepcion(final String message, Throwable cause) {
		super(message, cause);
	}

}
