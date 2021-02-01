package com.egoveris.deo.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class NotificacionMailException extends ApplicationException {

	
	private static final long serialVersionUID = 877837510165394484L;

	public NotificacionMailException(final String message) {
		super(message);
	}


	public NotificacionMailException(final String message, Throwable cause) {
		super(message, cause);
	}

}
