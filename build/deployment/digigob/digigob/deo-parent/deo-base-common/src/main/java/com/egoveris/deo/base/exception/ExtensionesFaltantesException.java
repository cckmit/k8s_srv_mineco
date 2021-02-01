package com.egoveris.deo.base.exception;

import java.lang.reflect.InvocationTargetException;

import org.terasoluna.plus.common.exception.ApplicationException;

public class ExtensionesFaltantesException extends ApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5208923920646191875L;

	public ExtensionesFaltantesException(final String message) {
	  super(message);
	 }

	public ExtensionesFaltantesException(final String message, Throwable cause) {
	  super(message, cause);

	 }

}
