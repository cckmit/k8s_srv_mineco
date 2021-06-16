package com.egoveris.deo.base.exception;

import java.io.Serializable;

import org.terasoluna.plus.common.exception.ApplicationException;

public class InconsistenciaDeDatosException extends ApplicationException implements Serializable {

	private static final long serialVersionUID = 1L;

	public InconsistenciaDeDatosException(String message){
		super(message);
	}
	
	public InconsistenciaDeDatosException(String message, Throwable cause){
		super(message, cause);
	}
}