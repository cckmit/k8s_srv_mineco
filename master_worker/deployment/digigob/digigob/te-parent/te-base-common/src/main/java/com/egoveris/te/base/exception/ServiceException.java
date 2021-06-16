package com.egoveris.te.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class ServiceException extends ApplicationException {

  private static final long serialVersionUID = -1769162056148995474L;

  public ServiceException(Throwable cause) {
    super("Error en el negocio", cause);
  }

  public ServiceException(String mensaje, Throwable cause) {
    super(mensaje, cause); 
  }

}
