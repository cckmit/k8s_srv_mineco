package com.egoveris.edt.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class DateException extends ApplicationException {

  private static final long serialVersionUID = 7897074714849837414L;

  public DateException(String msg, Throwable cause) {
    super(msg, cause);
  }

  public DateException(String msg) {
    super(msg);
  }

}
