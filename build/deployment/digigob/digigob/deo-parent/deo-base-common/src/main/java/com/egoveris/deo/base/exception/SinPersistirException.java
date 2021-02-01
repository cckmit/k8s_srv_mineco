package com.egoveris.deo.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

/**
 * Indica que la entidad que se deseaba persistir no pudo ser persistida.
 * 
 * @author eumolina
 *
 */
public class SinPersistirException extends ApplicationException {

	private static final long serialVersionUID = 5377904919550727751L;


	public SinPersistirException(final String message) {
		super(message);
	}


	public SinPersistirException(final String message, Throwable cause) {
		super(message, cause);
	}

}
