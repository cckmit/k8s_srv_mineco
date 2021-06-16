package com.egoveris.deo.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class ExistenDocumentosDelTipoJbpmException extends ApplicationException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2321978534666187684L;

	public ExistenDocumentosDelTipoJbpmException() {
		super("El tipo de documento esta asociado a un documento en proceso");
	}
}
