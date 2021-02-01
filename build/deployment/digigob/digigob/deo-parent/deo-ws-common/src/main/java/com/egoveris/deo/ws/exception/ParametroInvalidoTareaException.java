package com.egoveris.deo.ws.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

/**
 * La excepción se genera en los siguientes casos: - Cuando se quiere utilizar
 * un tipo de documento en una tarea en la que no puede ser usado. - Paramatros
 * nulos o vacios, que son obligatorios en una tarea determinada.
 */

public class ParametroInvalidoTareaException extends ApplicationException {

  private static final long serialVersionUID = 5317839855047670993L;

  public ParametroInvalidoTareaException(final String mensajeErrorParametro) {
    super(mensajeErrorParametro);
  }

  public ParametroInvalidoTareaException(final String mensajeErrorParametro, Throwable cause) {
    super(mensajeErrorParametro, cause);
  }
}
