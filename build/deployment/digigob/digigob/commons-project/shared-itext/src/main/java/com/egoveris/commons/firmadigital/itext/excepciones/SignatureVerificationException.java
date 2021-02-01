package com.egoveris.commons.firmadigital.itext.excepciones;

import org.terasoluna.plus.common.exception.ApplicationException;

public class SignatureVerificationException extends ApplicationException {
	private static final long serialVersionUID = 8692706681299088789L;

	public SignatureVerificationException(final String message) {
		super(message);
	}

	public SignatureVerificationException(final String message, final Throwable cause) {
		super(message, cause);
	}
}
