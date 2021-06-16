package com.egoveris.sharedorganismo.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class SectorInternoGenericDAOException extends ApplicationException {

  /**
   * Tratamiento de excepciones para los DAO de los sectores.
   */
  private static final long serialVersionUID = -937655318285247325L;

  public SectorInternoGenericDAOException(String message) {
    super(message);
  }

  public SectorInternoGenericDAOException(String message, Throwable cause) {
    super(message, cause);
  }

}