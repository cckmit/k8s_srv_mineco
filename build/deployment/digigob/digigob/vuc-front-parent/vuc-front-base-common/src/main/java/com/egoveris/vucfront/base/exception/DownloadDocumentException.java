package com.egoveris.vucfront.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

/**
 * Excepcion que se lanza cuando ocurre un error al intentar descargar un
 * documento desde VUC.
 * 
 * @author rgodoylo
 *
 */
public class DownloadDocumentException extends ApplicationException {
	private static final long serialVersionUID = 4383897197665654760L;

	public DownloadDocumentException(String message) {
		super(message);
	}

	public DownloadDocumentException(String message, Throwable cause) {
		super(message, cause);
	}
}
