package com.egoveris.ffdd.model.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class DynFormValorComponente extends ApplicationException{

	private static final long serialVersionUID = 3300341713048695768L;


	public DynFormValorComponente(String message) {
		super(message);
	}

	public DynFormValorComponente(String message, Throwable cause) {
		super(message, cause);
	}
}
