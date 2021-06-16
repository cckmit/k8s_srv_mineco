package com.egoveris.deo.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class ExistenDocumentosDelTipoException extends ApplicationException{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2321978534666187684L;

	public ExistenDocumentosDelTipoException() {
		super("El tipo de documento tiene documentos creados");
	}
}
