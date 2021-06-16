package com.egoveris.deo.ws.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

/**
 * La excepción se genera cuando el número del documento no existe en GEDO.
 * 
 */

public class DocumentoNoExisteException extends ApplicationException {

  /**
  * 
  */
  private static final long serialVersionUID = -1666392449447587074L;

  private static final String mensajeError = "Error no se ha generado en GEDO un documento con número: ";

  public DocumentoNoExisteException(final String numeroDocumento) {
    super(mensajeError + numeroDocumento);
  }

  public DocumentoNoExisteException(final String numeroDocumento, Throwable cause) {
    super(mensajeError + numeroDocumento, cause);
  }
}
