package com.egoveris.shareddocument.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class AlreadyLockedException extends ApplicationException {

  private static final long serialVersionUID = 8212565055015221397L;

  public AlreadyLockedException(String msg, Throwable t) {
    super(msg, t);
  }

  public AlreadyLockedException(String msg) {
    super(msg);
  }

}
