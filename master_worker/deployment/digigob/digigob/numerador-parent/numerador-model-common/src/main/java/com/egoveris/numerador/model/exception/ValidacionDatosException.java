package com.egoveris.numerador.model.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class ValidacionDatosException extends ApplicationException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ValidacionDatosException(String msg){
		super(msg);
	}
	
	public ValidacionDatosException(String msg,Throwable t){
		super(msg,t);
	}

	
}
