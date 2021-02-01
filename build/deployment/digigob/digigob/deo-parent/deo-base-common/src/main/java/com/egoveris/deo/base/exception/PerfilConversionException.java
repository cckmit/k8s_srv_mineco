package com.egoveris.deo.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class PerfilConversionException extends ApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8031508062453157356L;
	
	public PerfilConversionException(final String message) {
		super(message);
	}

	public PerfilConversionException(final String message, Throwable cause) {
		super(message, cause);
	}

}
