package com.egoveris.te.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class NegocioException extends ApplicationException {

  private static final long serialVersionUID = -1769162056148995474L;
  
  public NegocioException(String message) {
	    super(message);
  }
  
  public NegocioException(Throwable cause) {
    super("Error en el negocio", cause);
  }

  public NegocioException(String message, Throwable cause) {
    super(message, cause); 
  }

}
