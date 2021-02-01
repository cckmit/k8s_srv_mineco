package com.egoveris.ffdd.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class AccesoDatoException extends ApplicationException {

  private static final long serialVersionUID = -1937627808217784804L;

  public AccesoDatoException(String message) {
    super(message);
  }

  public AccesoDatoException(String message, Throwable cause) {
    super(message, cause);
  }

}
