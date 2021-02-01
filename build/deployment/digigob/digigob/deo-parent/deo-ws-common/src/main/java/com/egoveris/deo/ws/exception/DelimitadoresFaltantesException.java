package com.egoveris.deo.ws.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

@SuppressWarnings("serial")
public class DelimitadoresFaltantesException extends ApplicationException {

  public DelimitadoresFaltantesException(final String mensajeErrorParametro) {
    super(mensajeErrorParametro);
  }
}
