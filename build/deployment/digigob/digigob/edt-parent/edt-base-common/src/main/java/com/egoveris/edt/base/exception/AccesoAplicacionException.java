package com.egoveris.edt.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class AccesoAplicacionException extends ApplicationException {

  private static final long serialVersionUID = -5739900613643072961L;

  public AccesoAplicacionException(Throwable cause) {
    super("Se ha producido un error al acceder a la aplicación", cause);
  }

  public AccesoAplicacionException(String mensaje, Throwable cause) {
    super(mensaje, cause); 
  }
}
