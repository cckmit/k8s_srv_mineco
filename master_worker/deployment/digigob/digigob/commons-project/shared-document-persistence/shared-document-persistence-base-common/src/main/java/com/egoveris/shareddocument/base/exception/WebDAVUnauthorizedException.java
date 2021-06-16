package com.egoveris.shareddocument.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class WebDAVUnauthorizedException extends ApplicationException {
  /**
  * 
  */
  private static final long serialVersionUID = -937264477581504598L;

  public WebDAVUnauthorizedException(String msg, Throwable t) {
    super(msg, t);
  }

  public WebDAVUnauthorizedException(String location) {
    super("webdav.error.unauthorized for " + location);
  }
}
