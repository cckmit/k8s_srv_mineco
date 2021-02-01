package com.egoveris.shareddocument.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class WebDAVEmptySpacenameException extends ApplicationException {
  /**
  * 
  */
  private static final long serialVersionUID = -937264477581504598L;

  public WebDAVEmptySpacenameException(String msg, Throwable t) {
    super(msg, t);
  }

  public WebDAVEmptySpacenameException(String msg) {
    super(msg);
  }

  public WebDAVEmptySpacenameException() {
    super("webdav.error.emptyspacename");
  }
}
