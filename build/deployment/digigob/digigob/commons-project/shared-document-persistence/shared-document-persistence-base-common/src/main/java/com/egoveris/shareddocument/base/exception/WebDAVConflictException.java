package com.egoveris.shareddocument.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class WebDAVConflictException extends ApplicationException {

	private static final long serialVersionUID = 8530412560536706747L;

	public WebDAVConflictException() {
		super("webdav.error.conflict");
	}
	
	public WebDAVConflictException(String msg){
		super(msg);
	}
	
	public WebDAVConflictException(String msg,Throwable t) {
		super(msg,t);
	}

}
