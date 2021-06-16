package com.egoveris.deo.ws.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class ErrorBloquearTareaException extends ApplicationException {

  private static final long serialVersionUID = 8497180841919318511L;

  public ErrorBloquearTareaException(final String mensajeErrorParametro) {
    super(mensajeErrorParametro);
  }

  public ErrorBloquearTareaException(final String mensajeErrorParametro, Throwable cause) {
    super(mensajeErrorParametro, cause);
  }
}
