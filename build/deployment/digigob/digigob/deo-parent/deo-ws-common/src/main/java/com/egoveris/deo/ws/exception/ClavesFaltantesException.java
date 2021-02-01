package com.egoveris.deo.ws.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class ClavesFaltantesException extends ApplicationException {

  private static final long serialVersionUID = -7825312176392804024L;

  public ClavesFaltantesException(final String mensajeErrorParametro) {
    super(mensajeErrorParametro);
  }

  public ClavesFaltantesException(final String mensajeErrorParametro, Throwable cause) {
    super(mensajeErrorParametro, cause);
  }
}
