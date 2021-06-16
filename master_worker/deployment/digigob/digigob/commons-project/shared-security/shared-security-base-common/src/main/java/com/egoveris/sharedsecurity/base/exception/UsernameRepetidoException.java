package com.egoveris.sharedsecurity.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class UsernameRepetidoException extends ApplicationException {

  private static final long serialVersionUID = 78745843028603088L;

 
  public UsernameRepetidoException(String message) {
    super(message);
  }

  public UsernameRepetidoException(String message, Throwable cause) {
    super(message, cause);
  }

}
