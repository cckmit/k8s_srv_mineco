package com.egoveris.te.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class ValidarTrataException extends ApplicationException {

  private static final long serialVersionUID = -2650039278697349216L;

  public ValidarTrataException(Throwable cause) {
    super("Se ha producido un error al cargar un archivo", cause);
  }

  public ValidarTrataException(String mensaje, Throwable cause) {
    super(mensaje, cause);
  }


}
