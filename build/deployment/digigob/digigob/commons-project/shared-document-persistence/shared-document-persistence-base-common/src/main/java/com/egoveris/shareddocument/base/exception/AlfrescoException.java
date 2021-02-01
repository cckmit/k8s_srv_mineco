package com.egoveris.shareddocument.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class AlfrescoException extends ApplicationException {
  private static final long serialVersionUID = -344047297835198504L;

  public AlfrescoException(String msg) {
    super(msg);
  }

  public AlfrescoException(String msg, Throwable t) {
    super(msg, t);
  }

}
