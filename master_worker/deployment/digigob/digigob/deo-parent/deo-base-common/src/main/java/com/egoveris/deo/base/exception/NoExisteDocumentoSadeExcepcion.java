package com.egoveris.deo.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class NoExisteDocumentoSadeExcepcion extends ApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2008072902381536396L;

	public NoExisteDocumentoSadeExcepcion(final String msg){
		super(msg);
	}
}
