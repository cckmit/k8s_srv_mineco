package com.egoveris.deo.ws.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

/**
 * La excepción se genera cuando se han presentado errores al acceder a los
 * datos del documento.
 *
 */

public class ErrorConsultaNumeroSadeException extends ApplicationException {

  /**
  * 
  */
  private static final long serialVersionUID = 3349999081203398834L;

  private static final String mensajeError = "Error en la búsqueda del idWorkflow ";

  public ErrorConsultaNumeroSadeException(final String msg) {
    super(mensajeError + msg);
  }

  public ErrorConsultaNumeroSadeException(final String msg, Throwable cause) {
    super(mensajeError + msg, cause);
  }
}
