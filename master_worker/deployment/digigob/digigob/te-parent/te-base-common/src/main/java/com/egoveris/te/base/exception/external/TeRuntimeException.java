package com.egoveris.te.base.exception.external;

import org.terasoluna.plus.common.exception.ApplicationException;

/**
 * Exception for undesired behavior.
 * 
 * @author ggefaell
 *
 */
public class TeRuntimeException extends ApplicationException {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

 public TeRuntimeException(Throwable cause) {
   super("Runtime Exception", cause);
  }

  public TeRuntimeException(String message, Throwable cause) {
   super(message, cause);
  }
}
