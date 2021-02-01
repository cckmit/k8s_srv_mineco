package com.egoveris.shareddocument.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class WebDAVNotImplementedMethodException extends ApplicationException {
  /**
  * 
  */
  private static final long serialVersionUID = -7719201355363373907L;

  public WebDAVNotImplementedMethodException(String msg, Throwable t) {
    super(msg, t);
  }

  public WebDAVNotImplementedMethodException(String location) {
    super("webdav.error.notimplemented for " + location + " see WebDAV server log for details");
  }
}
