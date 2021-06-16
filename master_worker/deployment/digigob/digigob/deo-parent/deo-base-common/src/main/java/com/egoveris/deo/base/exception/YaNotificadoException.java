package com.egoveris.deo.base.exception;

import java.io.Serializable;

import org.terasoluna.plus.common.exception.ApplicationException;

public class YaNotificadoException extends ApplicationException implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public YaNotificadoException(String message){
		super(message);
	}
	
	public YaNotificadoException(String message, Throwable cause){
		super(message, cause);
	}
}
