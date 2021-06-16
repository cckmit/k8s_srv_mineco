package com.egoveris.deo.base.exception;

import java.io.Serializable;

import org.terasoluna.plus.common.exception.ApplicationException;

public class ValoresIncorrectosException extends ApplicationException implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public ValoresIncorrectosException(String message){
		super(message);
	}
	
	public ValoresIncorrectosException(String message, Throwable cause){
		super(message, cause);
	}
}
