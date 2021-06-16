package com.egoveris.deo.ws.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

/**
 * La excepción se genera en los siguientes casos: - La excepción se genera al
 * querer ingresar un texto de rectificacion mayor al estipulado en constantes
 */
public class LongitudTextoExceptionRec extends ApplicationException {

  private static final long serialVersionUID = 3592642498842082421L;

  public LongitudTextoExceptionRec(final String mensajeErrorTextoRec) {
    super(mensajeErrorTextoRec);
  }

  public LongitudTextoExceptionRec(final String mensajeErrorTextoRec, Throwable cause) {
    super(mensajeErrorTextoRec, cause);
  }
}
