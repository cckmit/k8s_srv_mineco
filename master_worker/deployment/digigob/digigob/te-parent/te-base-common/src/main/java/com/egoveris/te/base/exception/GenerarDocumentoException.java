package com.egoveris.te.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;


public class GenerarDocumentoException extends ApplicationException {

  private static final long serialVersionUID = -2650039278697349216L;

  public GenerarDocumentoException(Throwable cause) {
    super("Se ha producido un error al cargar un archivo", cause);
  }

  public GenerarDocumentoException(String mensaje, Throwable cause) {
    super(mensaje, cause);
  }


}
