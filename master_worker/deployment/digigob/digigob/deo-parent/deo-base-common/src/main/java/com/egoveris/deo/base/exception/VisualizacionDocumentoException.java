package com.egoveris.deo.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class VisualizacionDocumentoException extends ApplicationException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 318688850491100822L;
	
	public VisualizacionDocumentoException(String message){
		super(message);
	}
	
	public VisualizacionDocumentoException(String message, Throwable cause){
		super(message, cause);
	}
}
