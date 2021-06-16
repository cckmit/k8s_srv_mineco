package com.egoveris.tica.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

/**
 * The Class SignException.
 */
public class SignException extends ApplicationException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1159651943210852003L;

	/**
	 * Instantiates a new sign exception.
	 *
	 * @param arg0
	 *            the arg 0
	 */
	public SignException(final String arg0) {
		super(arg0);
	}

	/**
	 * Instantiates a new sign exception.
	 *
	 * @param arg0
	 *            the arg 0
	 * @param arg1
	 *            the arg 1
	 */
	public SignException(final String arg0, final Throwable arg1) {
		super(arg0, arg1);
	}

}
