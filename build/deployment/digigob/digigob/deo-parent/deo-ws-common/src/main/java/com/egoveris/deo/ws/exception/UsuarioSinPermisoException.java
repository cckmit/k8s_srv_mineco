package com.egoveris.deo.ws.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

/**
 * La excepción se genera en los siguientes casos: - El usuario Emisor, no tiene
 * permiso de Iniciar el tipo de documento seleciconado. - El usuario Firmante,
 * no tiene permiso de Firmar el tipo de documento seleccionado.
 *
 */
public class UsuarioSinPermisoException extends ApplicationException {

  private static final long serialVersionUID = -7825312176392804024L;

  public UsuarioSinPermisoException(final String mensajeErrorParametro) {
    super(mensajeErrorParametro);
  }

  public UsuarioSinPermisoException(final String mensajeErrorParametro, Throwable cause) {
    super(mensajeErrorParametro, cause);
  }
}
