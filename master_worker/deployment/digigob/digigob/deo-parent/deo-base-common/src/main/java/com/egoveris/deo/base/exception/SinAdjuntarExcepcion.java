package com.egoveris.deo.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class SinAdjuntarExcepcion extends ApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1168588058116333774L;


	public SinAdjuntarExcepcion(final String message) {
		super(message);
	}


	public SinAdjuntarExcepcion(final String message, Throwable cause) {
		super(message, cause);
	}

}
