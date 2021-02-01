package com.egoveris.deo.ws.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class ErrorDepuracionDocumentoException extends ApplicationException {

  private static final long serialVersionUID = -5366512554924622158L;

  public ErrorDepuracionDocumentoException(final String mensajeErrorParametro) {
    super(mensajeErrorParametro);
  }

  public ErrorDepuracionDocumentoException(final String mensajeErrorParametro, Throwable cause) {
    super(mensajeErrorParametro, cause);
  }
}
