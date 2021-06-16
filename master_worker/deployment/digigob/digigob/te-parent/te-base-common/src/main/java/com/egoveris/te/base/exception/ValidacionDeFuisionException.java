package com.egoveris.te.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

@SuppressWarnings("serial")
public class ValidacionDeFuisionException extends ApplicationException {
	
  public ValidacionDeFuisionException(Throwable cause) {
    super("Error en validación de fusión", cause);
  }

  public ValidacionDeFuisionException(String mensaje, Throwable cause) {
    super(mensaje, cause); 
  }
}
