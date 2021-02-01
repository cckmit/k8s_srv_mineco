package com.egoveris.vucfront.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

/**
 * Excepción que se lanza cuando ocurre un error en el envío de una solicitud
 * desde VUC.
 * 
 * @author rgodoylo
 *
 */
public class EnviarSolicitudException extends ApplicationException {
	private static final long serialVersionUID = 5578729707239609813L;

	public EnviarSolicitudException(String message) {
		super(message);
	}

	public EnviarSolicitudException(String message, Throwable cause) {
		super(message, cause);
	}

}
