package com.egoveris.deo.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class VariableWorkFlowNoExisteException extends ApplicationException {


	/**
	 * 
	 */
	private static final long serialVersionUID = -5203381378585663447L;

	public VariableWorkFlowNoExisteException(final String message) {
		super(message);
	}

	public VariableWorkFlowNoExisteException(final String message, Throwable cause) {
		super(message, cause);
	}

}
