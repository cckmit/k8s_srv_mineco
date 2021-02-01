package com.egoveris.shareddocument.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class WevDAVMovedPermanentlyException extends ApplicationException {

  /**
  * 
  */
  private static final long serialVersionUID = -7067370909628882127L;

  public WevDAVMovedPermanentlyException(String msg, Throwable t) {
    super(msg, t);
  }

  public WevDAVMovedPermanentlyException(String location) {
    super("webdav.error.movedpermanently for " + location + " see WebDAV server log for details");
  }
}
