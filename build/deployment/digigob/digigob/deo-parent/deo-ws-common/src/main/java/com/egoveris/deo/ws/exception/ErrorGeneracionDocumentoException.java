package com.egoveris.deo.ws.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

/**
 * La excepción se genera cuando se han presentado errores en el proceso de
 * creación del documento, ya sea por fallas en la comunicación con otros
 * sistemas o por fallas propias del proceso de generación.
 *
 */

public class ErrorGeneracionDocumentoException extends ApplicationException {
  private static final long serialVersionUID = -885033084082354144L;

  public ErrorGeneracionDocumentoException(final String msg) {
    super(msg);
  }

  public ErrorGeneracionDocumentoException(final String msg, Throwable cause) {
    super(msg, cause);
  }
}
