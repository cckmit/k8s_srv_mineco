package com.egoveris.sharedsecurity.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class SecurityNegocioException extends ApplicationException {

  /**
  * 
  */
  private static final long serialVersionUID = 6277782752425562532L;

  public SecurityNegocioException(String message) {
    super(message);
  }

  public SecurityNegocioException(String message, Throwable cause) {
    super(message, cause);
  }

}