package com.egoveris.edt.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class StatusServiceException extends ApplicationException {

  private static final long serialVersionUID = -3960186586231292274L;

  public StatusServiceException(String mesage) {
    super(mesage);
  }
  
  public StatusServiceException(String mesage, Throwable cause) {
    super(mesage, cause);
  }
}
