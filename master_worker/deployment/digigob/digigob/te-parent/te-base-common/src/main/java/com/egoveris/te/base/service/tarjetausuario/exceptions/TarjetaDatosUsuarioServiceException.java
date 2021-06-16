package com.egoveris.te.base.service.tarjetausuario.exceptions;

public class TarjetaDatosUsuarioServiceException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TarjetaDatosUsuarioServiceException() {
	}

	public TarjetaDatosUsuarioServiceException(String message) {
		super(message);
	}

	public TarjetaDatosUsuarioServiceException(String message, Throwable cause) {
		super(message, cause);
	}

}
