package com.egoveris.edt.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class NegocioException extends ApplicationException {

  private static final long serialVersionUID = 7002125081118092312L;

  public NegocioException(String msg, Throwable cause) {
    super(msg, cause);
  }

  public NegocioException(String msg) {
    super(msg);
  }
}