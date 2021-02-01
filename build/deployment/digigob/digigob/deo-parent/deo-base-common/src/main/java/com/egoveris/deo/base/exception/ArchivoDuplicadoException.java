package com.egoveris.deo.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class ArchivoDuplicadoException extends ApplicationException {

	private static final long serialVersionUID = 2091308418250647042L;

	public ArchivoDuplicadoException(String message) {
		super(message);

	}

	public ArchivoDuplicadoException(String message, Throwable cause) {
		super(message, cause);
		
	}

}
