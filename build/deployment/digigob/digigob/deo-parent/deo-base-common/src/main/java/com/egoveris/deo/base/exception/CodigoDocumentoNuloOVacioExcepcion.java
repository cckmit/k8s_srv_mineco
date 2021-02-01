package com.egoveris.deo.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class CodigoDocumentoNuloOVacioExcepcion extends ApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2670446523879867830L;


	public CodigoDocumentoNuloOVacioExcepcion(final String message) {
		super(message);
	}


	public CodigoDocumentoNuloOVacioExcepcion(final String message, Throwable cause) {
		super(message, cause);
	}

}
