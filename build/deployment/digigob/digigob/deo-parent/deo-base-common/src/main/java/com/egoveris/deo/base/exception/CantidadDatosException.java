package com.egoveris.deo.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

/**
 * Indica que el contenido del documento, supera en tamaño al soportado por
 * GEDO.
 *
 */
public class CantidadDatosException extends ApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6829309356929546647L;

	public CantidadDatosException(final String message) {
		super(message);
	}

	public CantidadDatosException(final String message, Throwable cause) {
		super(message, cause);
	}

}
