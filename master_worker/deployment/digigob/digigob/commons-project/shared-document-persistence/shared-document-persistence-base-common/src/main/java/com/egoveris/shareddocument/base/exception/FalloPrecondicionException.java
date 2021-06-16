package com.egoveris.shareddocument.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class FalloPrecondicionException extends ApplicationException {

  private static final long serialVersionUID = -1914573550274390341L;

  public FalloPrecondicionException(String msg, Throwable t) {
    super(msg, t);
  }

  public FalloPrecondicionException(String msg) {
    super(msg);
  }
}
