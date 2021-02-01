package com.egoveris.te.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;


public class GenerarPdfException extends ApplicationException {

  private static final long serialVersionUID = -2650039278697349216L;

  public GenerarPdfException(Throwable cause) {
    super("Se ha producido un error al cargar un archivo", cause);
  }

  public GenerarPdfException(String mensaje, Throwable cause) {
    super(mensaje, cause);
  }


}
