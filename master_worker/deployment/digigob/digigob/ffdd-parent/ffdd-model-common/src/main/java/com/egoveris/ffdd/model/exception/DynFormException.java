package com.egoveris.ffdd.model.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class DynFormException extends ApplicationException {

	private static final long serialVersionUID = -6634221861203084822L;

	public DynFormException(String message) {
		super(message);
	}

	public DynFormException(String message, Throwable cause) {
		super(message, cause);
	}


}
