package com.egoveris.deo.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

/**
 * Indica que el contenido de la información para la generación del documento,
 * no es coherente con el tipo de operación que se está solicitando.
 * 
 * 
 *
 */
public class ValidacionContenidoException extends ApplicationException {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 394591984050314389L;
	

	public ValidacionContenidoException(final String message) {
		super(message);
	}

	public ValidacionContenidoException(final String message, Throwable cause) {
		super(message, cause);
	}

}
