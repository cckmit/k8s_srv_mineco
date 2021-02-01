package com.egoveris.sharedorganismo.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class SectorInternoServException extends ApplicationException {

  /**
   * Tratamiento de excepciones para los servicios de las reparticiones.
   */
  private static final long serialVersionUID = 1L;

  public SectorInternoServException(String message) {
    super(message);
  }

  public SectorInternoServException(String message, Throwable cause) {
    super(message, cause);
  }
}
