package com.egoveris.numerador.model.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class SistemaInvalidoException extends ApplicationException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SistemaInvalidoException(String msg){
		super(msg);
	}
	
	public SistemaInvalidoException(String msg,Throwable t){
		super(msg,t);
	}
}
