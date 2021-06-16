package com.egoveris.shareddocument.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class WebDAVEmptyRelativeUriException extends ApplicationException {
  /**
  * 
  */
  private static final long serialVersionUID = -937264477581504598L;

  public WebDAVEmptyRelativeUriException(String msg) {
    super(msg);
  }

  public WebDAVEmptyRelativeUriException(String msg, Throwable t) {
    super(msg, t);
  }

  public WebDAVEmptyRelativeUriException() {
    super("webdav.error.emptyrelativeUri");
  }
}
