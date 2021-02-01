package com.egoveris.deo.ws.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

/**
 * La excepción se genera en los siguientes casos: - El usuario no se encuentra
 * registrado en la base de datos de comunicaciones oficiales. - Al menos uno de
 * los parámetros obligatorios es nulo.
 */

public class ParametroInvalidoConsultaException extends ApplicationException {

  /**
  * 
  */
  private static final long serialVersionUID = -1666392449447587074L;

  public ParametroInvalidoConsultaException(final String mensajeErrorParametro) {
    super(mensajeErrorParametro);
  }

  public ParametroInvalidoConsultaException(final String mensajeErrorParametro, Throwable cause) {
    super(mensajeErrorParametro, cause);
  }
}
