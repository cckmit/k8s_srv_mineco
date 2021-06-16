package com.egoveris.shareddocument.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class WebDAVEmptyFilenameException extends ApplicationException {
  /**
  * 
  */
  private static final long serialVersionUID = -937264477581504598L;

  public WebDAVEmptyFilenameException(String msg, Throwable t) {
    super(msg, t);
  }

  public WebDAVEmptyFilenameException(String msg) {
    super(msg);
  }

  public WebDAVEmptyFilenameException() {
    super("webdav.error.emptyfilename");
  }
}
