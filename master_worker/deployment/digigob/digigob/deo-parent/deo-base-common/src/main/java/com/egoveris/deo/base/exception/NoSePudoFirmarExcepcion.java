package com.egoveris.deo.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class NoSePudoFirmarExcepcion extends ApplicationException {


	/**
	 * 
	 */
	private static final long serialVersionUID = -3619083371768131991L;


	public NoSePudoFirmarExcepcion(final String message) {
		super(message);
	}


	public NoSePudoFirmarExcepcion(final String message, Throwable cause) {
		super(message, cause);
	}

}
