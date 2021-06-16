package com.egoveris.deo.model.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class DataAccessLayerException extends ApplicationException {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2952864383860927555L;

	public DataAccessLayerException(final String message) {
        super(message);
    }
    
    public DataAccessLayerException(final String message, Throwable cause) {
        super(message, cause);
    }
}
