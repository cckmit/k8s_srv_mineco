package com.egoveris.edt.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class NovedadException extends ApplicationException {

  private static final long serialVersionUID = 4266622552294997770L;

  public NovedadException(String msg, Throwable cause) {
    super(msg, cause);
  }

  public NovedadException(String msg) {
    super(msg);
  }

}
