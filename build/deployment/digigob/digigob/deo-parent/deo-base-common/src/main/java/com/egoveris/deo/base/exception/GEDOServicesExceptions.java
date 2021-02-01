package com.egoveris.deo.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class GEDOServicesExceptions extends ApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -891570516902071809L;
	
	public GEDOServicesExceptions(final String msg){
		super(msg);
	}
	
    public GEDOServicesExceptions(final String message, Throwable cause) {
        super(message, cause);
    }
	
}
