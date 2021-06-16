package com.egoveris.tica.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

/**
 * The Class TicaHtmlToPdfException.
 */
public class TicaHtmlToPdfException extends ApplicationException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -1159651943210852003L;

	/**
	 * Instantiates a new tica html to pdf exception.
	 *
	 * @param arg0
	 *            the arg 0
	 */
	public TicaHtmlToPdfException(final String arg0) {
		super(arg0);
	}

	/**
	 * Instantiates a new tica html to pdf exception.
	 *
	 * @param arg0
	 *            the arg 0
	 * @param arg1
	 *            the arg 1
	 */
	public TicaHtmlToPdfException(final String arg0, final Throwable arg1) {
		super(arg0, arg1);
	}

}
