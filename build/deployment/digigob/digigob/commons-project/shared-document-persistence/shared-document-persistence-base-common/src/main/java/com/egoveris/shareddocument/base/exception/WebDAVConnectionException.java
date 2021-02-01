package com.egoveris.shareddocument.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class WebDAVConnectionException extends ApplicationException {

	private static final long serialVersionUID = 3906268659152928816L;

	public WebDAVConnectionException(String msg){
		super(msg);
	}
	
	public WebDAVConnectionException(String msg,Throwable t) {
		super(msg,t);
	}

}
