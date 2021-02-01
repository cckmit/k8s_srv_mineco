package com.egoveris.deo.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class DocumentoDigitalExistenteException extends ApplicationException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8956698376811298326L;

	public DocumentoDigitalExistenteException(final String msg){
		super(msg);
	}
	
	public DocumentoDigitalExistenteException(final String msg,Throwable cause){
		super(msg,cause);
	}
}
