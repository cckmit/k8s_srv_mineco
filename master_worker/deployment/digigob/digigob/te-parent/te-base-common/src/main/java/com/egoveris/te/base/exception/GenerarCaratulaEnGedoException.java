package com.egoveris.te.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;


public class GenerarCaratulaEnGedoException extends ApplicationException {

  private static final long serialVersionUID = -2650039278697349216L;

  public GenerarCaratulaEnGedoException(Throwable cause) {
    super("Se ha producido un error en un archivo al generar cartula ", cause);
  }

  public GenerarCaratulaEnGedoException(String mensaje, Throwable cause) {
    super(mensaje, cause);
  }


}
