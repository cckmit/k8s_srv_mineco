package com.egoveris.deo.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

/**
 * Permite identificar errores de validación sobre campos de firma en documentos pdf, como
 * pueden ser:
 * - No existencia de campos de firma.
 * - Existencia de campos de firma en blanco.
 */
public class ValidacionCampoFirmaException extends ApplicationException {

		/**
	 * 
	 */
	private static final long serialVersionUID = 310985853693960534L;


	public ValidacionCampoFirmaException(final String message) {
		super(message);
	}

	public ValidacionCampoFirmaException(final String message, Throwable cause) {
		super(message, cause);
	}

}
