package com.egoveris.deo.ws.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

/**
 * La excepción se genera cuando el número del documento no existe en GEDO.
 * 
 */

public class TipoDocumentoNoExisteException extends ApplicationException {

  /**
  * 
  */
  private static final long serialVersionUID = -1666392449447587074L;

  private static final String mensajeError = "Error: No existe el tipo de documento con el acrónimo ";

  public TipoDocumentoNoExisteException(final String acronimo) {
    super(mensajeError + acronimo);
  }

  public TipoDocumentoNoExisteException(final String acronimo, Throwable cause) {
    super(mensajeError + acronimo, cause);
  }
}
