package com.egoveris.ffdd.model.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class UnexpectedFormError extends ApplicationException {

	private static final long serialVersionUID = 1L;


	public UnexpectedFormError(final String message) {
		super(message);
	}

	public UnexpectedFormError(final String message, final Throwable cause) {
		super(message, cause);
	}

	public UnexpectedFormError(final String message, final Throwable cause, final boolean enableSuppression,
			final boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}


}
