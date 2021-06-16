package com.egoveris.te.ws.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class ExpedienteInexistenteException extends ApplicationException {

	private static final long serialVersionUID = -1123289126221850031L;

	public ExpedienteInexistenteException(Throwable cause) {
		super("No existe el expediente solicitado", cause);
	}

	public ExpedienteInexistenteException(String codigoExpediente) {
		super("No existe el expediente solicitado: ".concat(codigoExpediente));
	}

	public ExpedienteInexistenteException(String message, Throwable cause) {
		super(message, cause);
	}
}
