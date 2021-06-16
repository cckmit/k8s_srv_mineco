package com.egoveris.shareddocument.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class WebDAVWrongRequestException extends ApplicationException {
  /**
  * 
  */
  private static final long serialVersionUID = -937264477581504598L;

  public WebDAVWrongRequestException(String msg, Throwable t) {
    super(msg, t);
  }

  public WebDAVWrongRequestException(String location) {
    super("webdav.error.wrongrequest for" + location + " see WebDAV server log for details");
  }
}
