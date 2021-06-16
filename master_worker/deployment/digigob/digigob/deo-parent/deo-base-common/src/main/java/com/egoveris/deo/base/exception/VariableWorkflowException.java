package com.egoveris.deo.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class VariableWorkflowException extends ApplicationException {


	/**
	 * 
	 */
	private static final long serialVersionUID = 7194391804637824766L;


	public VariableWorkflowException(final String message) {
		super(message);
	}


	public VariableWorkflowException(final String message, Throwable cause) {
		super(message, cause);
	}

}
