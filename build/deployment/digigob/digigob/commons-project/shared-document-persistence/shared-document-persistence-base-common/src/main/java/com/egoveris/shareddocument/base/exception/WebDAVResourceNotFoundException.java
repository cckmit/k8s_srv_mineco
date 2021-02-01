package com.egoveris.shareddocument.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class WebDAVResourceNotFoundException extends ApplicationException {
  /**
  * 
  */
  private static final long serialVersionUID = 8879783996734243646L;

  public WebDAVResourceNotFoundException() {
    super("webdav.error.resourcenotfound");
  }

  public WebDAVResourceNotFoundException(String msg) {
    super(msg);
  }

  public WebDAVResourceNotFoundException(String msg, Throwable t) {
    super(msg, t);
  }

}
