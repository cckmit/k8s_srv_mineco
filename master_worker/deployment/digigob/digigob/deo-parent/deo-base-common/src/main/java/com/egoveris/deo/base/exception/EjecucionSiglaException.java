package com.egoveris.deo.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class EjecucionSiglaException extends ApplicationException {

	private static final long serialVersionUID = -1305546695059108436L;

	
	public EjecucionSiglaException(final String msg) {
		super(msg);
	}

	public EjecucionSiglaException(final String msg, Throwable cause) {
		super(msg, cause);
	}
}
