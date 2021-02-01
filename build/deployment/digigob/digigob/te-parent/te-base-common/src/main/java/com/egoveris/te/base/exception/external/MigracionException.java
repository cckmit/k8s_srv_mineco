package com.egoveris.te.base.exception.external;

import org.terasoluna.plus.common.exception.ApplicationException;

public class MigracionException extends ApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
 public MigracionException(Throwable cause) {
   super("Error en la migración", cause);
  }

  public MigracionException(String message, Throwable cause) {
   super(message, cause);
  }

}
