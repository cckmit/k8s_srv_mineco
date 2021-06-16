package com.egoveris.deo.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class DocumentoExistenteGEDOCaratulaExcepcion extends ApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1781623084677824443L;


	public DocumentoExistenteGEDOCaratulaExcepcion(final String message) {
		super(message);
	}

	public DocumentoExistenteGEDOCaratulaExcepcion(final String message, Throwable cause) {
		super(message, cause);
	}

}
