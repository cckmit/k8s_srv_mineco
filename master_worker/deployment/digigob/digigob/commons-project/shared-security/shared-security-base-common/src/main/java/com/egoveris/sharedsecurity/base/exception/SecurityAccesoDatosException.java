package com.egoveris.sharedsecurity.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class SecurityAccesoDatosException extends ApplicationException {

  /**
  * 
  */
  private static final long serialVersionUID = 560646199522408165L;

  public SecurityAccesoDatosException(String message) {
    super(message);
  }

  public SecurityAccesoDatosException(String message, Throwable cause) {
    super(message, cause);
  }

}