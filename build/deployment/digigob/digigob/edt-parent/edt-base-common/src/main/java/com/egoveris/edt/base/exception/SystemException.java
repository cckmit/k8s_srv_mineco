package com.egoveris.edt.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class SystemException extends ApplicationException {

  private static final long serialVersionUID = -3970909901193664071L;

  public SystemException(String msg, Throwable cause) {
    super(msg, cause);
  }

  public SystemException(String msg) {
    super(msg);
  }

}
