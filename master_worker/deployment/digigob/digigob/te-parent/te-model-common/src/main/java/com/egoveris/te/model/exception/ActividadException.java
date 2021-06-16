package com.egoveris.te.model.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class ActividadException extends ApplicationException {

	private static final long serialVersionUID = -9127822132944025726L;

	public ActividadException(Throwable cause) {
		super("Error en Actividad", cause);
	}

	public ActividadException(String message, Throwable cause) {
		super(message, cause);
	}
}
