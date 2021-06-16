package com.egoveris.sharedorganismo.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class ReparticionGenericDAOException extends ApplicationException {

	/**
	 * Tratamiento de excepciones para los DAO de las reparticiones.
	 */
	private static final long serialVersionUID = -937655318285247325L;


	public ReparticionGenericDAOException(String message) {
		super(message);
	}

  public ReparticionGenericDAOException(String message, Throwable cause) {
    super(message, cause);
	}

}
