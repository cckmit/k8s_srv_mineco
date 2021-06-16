package com.egoveris.deo.ws.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

/**
 * La excepción se genera cuando el tamaño del archivo a importar o el contenido
 * de los datos supera el tamaño soportado por GEDO.
 * 
 * @author kmarroqu
 *
 */
public class CantidadDatosNoSoportadaException extends ApplicationException {

  private static final long serialVersionUID = 2877607576536571081L;

  public CantidadDatosNoSoportadaException(final String mensaje) {
    super(mensaje);
  }

  public CantidadDatosNoSoportadaException(final String mensaje, Throwable cause) {
    super(mensaje, cause);
  }
}
