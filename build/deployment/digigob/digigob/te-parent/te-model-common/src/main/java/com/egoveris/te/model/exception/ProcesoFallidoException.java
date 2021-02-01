package com.egoveris.te.model.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class ProcesoFallidoException extends ApplicationException{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5761634755576731686L;

 public ProcesoFallidoException(Throwable cause) {
   super("Fail process Exception", cause);
  }

  public ProcesoFallidoException(String message, Throwable cause) {
   super(message, cause);
  }
}
