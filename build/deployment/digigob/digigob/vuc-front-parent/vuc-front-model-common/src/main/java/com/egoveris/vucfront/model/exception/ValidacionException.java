package com.egoveris.vucfront.model.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

/**
 * Exception de tipo checked que lanzara cualquier error ocurrido en las
 * validaciones
 * 
 * @author paGarcia
 */
public class ValidacionException extends ApplicationException {

	private static final long serialVersionUID = 2276185873259522371L;

	public ValidacionException(String message) {
		super(message);
	}

	public ValidacionException(String message, Throwable cause) {
		super(message, cause);
	}
}
