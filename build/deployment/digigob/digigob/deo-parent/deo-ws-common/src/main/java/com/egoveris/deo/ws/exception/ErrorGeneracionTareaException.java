package com.egoveris.deo.ws.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

/**
 * La excepción se genera cuando se han presentado errores en el proceso de
 * creación de la tarea, ya sea por fallas en la comunicación con otros sistemas
 * o por fallas propias del proceso de generación.
 *
 */

public class ErrorGeneracionTareaException extends ApplicationException {

  private static final long serialVersionUID = -2274163514470137022L;

  public ErrorGeneracionTareaException(final String msg) {
    super(msg);
  }

  public ErrorGeneracionTareaException(final String msg, Throwable cause) {
    super(msg, cause);
  }
}
