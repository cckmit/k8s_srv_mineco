package com.egoveris.deo.ws.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class ErrorCancelarTareaException extends ApplicationException {

  private static final long serialVersionUID = -7219875999170496383L;

  public ErrorCancelarTareaException(final String mensajeErrorParametro) {
    super(mensajeErrorParametro);
  }

  public ErrorCancelarTareaException(final String mensajeErrorParametro, Throwable cause) {
    super(mensajeErrorParametro, cause);
  }

}
