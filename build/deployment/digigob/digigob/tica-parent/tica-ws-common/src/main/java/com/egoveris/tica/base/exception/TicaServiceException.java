package com.egoveris.tica.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class TicaServiceException extends ApplicationException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7495711094541617023L;

	public TicaServiceException(String msg){
		super(msg);
	}
	
	public TicaServiceException(String msg,Throwable t){
		super(msg,t);
	}

}
