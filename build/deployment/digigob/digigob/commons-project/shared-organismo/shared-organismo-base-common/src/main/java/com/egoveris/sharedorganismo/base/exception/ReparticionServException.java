package com.egoveris.sharedorganismo.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class ReparticionServException extends ApplicationException {

  /**
   * Tratamiento de excepciones para los servicios de los sectores.
   */
  private static final long serialVersionUID = 1L;

  public ReparticionServException(String message) {
    super(message);
  }

  public ReparticionServException(String message, Throwable cause) {
    super(message, cause);
  }

}
