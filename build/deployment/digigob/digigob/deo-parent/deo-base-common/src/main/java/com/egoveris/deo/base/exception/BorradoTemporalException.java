package com.egoveris.deo.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class BorradoTemporalException extends ApplicationException {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = -465069833771752576L;

	public BorradoTemporalException(final String message) {
		super(message);
	}

	public BorradoTemporalException(final String message,Throwable cause) {
		super(message,cause);
	}

}
