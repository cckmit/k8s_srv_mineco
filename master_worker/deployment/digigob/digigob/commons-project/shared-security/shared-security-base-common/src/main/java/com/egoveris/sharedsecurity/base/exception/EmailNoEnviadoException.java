package com.egoveris.sharedsecurity.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class EmailNoEnviadoException extends ApplicationException {

  private static final long serialVersionUID = 7002123281118092312L;

  public EmailNoEnviadoException(String msg, Throwable cause) {
    super(msg, cause);
  }

  public EmailNoEnviadoException(String msg) {
    super(msg);
  }
}