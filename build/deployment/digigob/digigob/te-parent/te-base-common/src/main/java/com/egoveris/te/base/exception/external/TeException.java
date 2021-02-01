package com.egoveris.te.base.exception.external;

import org.terasoluna.plus.common.exception.ApplicationException;

public class TeException extends ApplicationException {

	private static final long serialVersionUID = 1L;


 public TeException(Throwable cause) {
   super("TE Exception", cause);
  }

  public TeException(String message, Throwable cause) {
   super(message, cause);
  }

}
