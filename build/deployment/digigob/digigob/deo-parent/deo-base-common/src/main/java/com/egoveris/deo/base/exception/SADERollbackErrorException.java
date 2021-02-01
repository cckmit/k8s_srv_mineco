package com.egoveris.deo.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class SADERollbackErrorException extends ApplicationException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8206680563040616632L;

	public SADERollbackErrorException(final String msg, Throwable cause) {
		super(msg, cause);
	}
	
}
