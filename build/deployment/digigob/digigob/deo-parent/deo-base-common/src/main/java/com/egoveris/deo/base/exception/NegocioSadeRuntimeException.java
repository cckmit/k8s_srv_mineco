package com.egoveris.deo.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class NegocioSadeRuntimeException extends ApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1347587305134900788L;

	
	public NegocioSadeRuntimeException(final String message) {
		super(message);
	}
	
	public NegocioSadeRuntimeException(final String message, Throwable cause) {
		super(message, cause);
	}


}
