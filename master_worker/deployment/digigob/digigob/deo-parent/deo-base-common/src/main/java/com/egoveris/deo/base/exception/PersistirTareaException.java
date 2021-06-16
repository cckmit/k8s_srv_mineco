package com.egoveris.deo.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class PersistirTareaException extends ApplicationException {

	private static final long serialVersionUID = -3554359053303393598L;


	public PersistirTareaException(final String msg) {
		super(msg);
	}

	public PersistirTareaException(final String msg, Throwable cause) {
		super(msg, cause);
	}

}
