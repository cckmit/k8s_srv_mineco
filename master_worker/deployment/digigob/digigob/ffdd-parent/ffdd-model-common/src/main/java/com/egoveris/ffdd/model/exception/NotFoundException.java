package com.egoveris.ffdd.model.exception;

public class NotFoundException extends DynFormException {

	private static final long serialVersionUID = -4714375001406996022L;


	public NotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotFoundException(String message) {
		super(message);
	}

}
