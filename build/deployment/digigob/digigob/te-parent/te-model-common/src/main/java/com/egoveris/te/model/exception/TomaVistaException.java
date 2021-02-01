package com.egoveris.te.model.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class TomaVistaException extends ApplicationException {

	private static final long serialVersionUID = 2436916509420434473L;

 public TomaVistaException(Throwable cause) {
   super("Toma vista exception", cause);
  }

  public TomaVistaException(String message, Throwable cause) {
   super(message, cause);
  }
	
}
