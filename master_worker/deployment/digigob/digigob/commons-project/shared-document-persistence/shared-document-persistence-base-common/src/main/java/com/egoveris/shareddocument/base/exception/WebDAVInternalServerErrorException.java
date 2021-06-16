package com.egoveris.shareddocument.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class WebDAVInternalServerErrorException extends ApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6816863814946209141L;

	public WebDAVInternalServerErrorException(String msg, Throwable t) {
		super(msg, t);
	}
	
	public WebDAVInternalServerErrorException(String location) {
		super("webdav.error.internalservererror for "+location+" see WebDAV server log for details");
	}
}
