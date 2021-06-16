package com.egoveris.deo.ws.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

/**
 * La excepción se genera en los siguientes casos: - Cuando los usuarios que
 * intervienen en la tarea seleccionada, no existen. - Cuando la tarea ingresada
 * no existe.
 */
public class ParametroNoExisteException extends ApplicationException {

  private static final long serialVersionUID = 9067375475955959933L;

  public ParametroNoExisteException(final String mensajeErrorParametro) {
    super(mensajeErrorParametro);
  }

  public ParametroNoExisteException(final String mensajeErrorParametro, Throwable cause) {
    super(mensajeErrorParametro, cause);
  }
}
