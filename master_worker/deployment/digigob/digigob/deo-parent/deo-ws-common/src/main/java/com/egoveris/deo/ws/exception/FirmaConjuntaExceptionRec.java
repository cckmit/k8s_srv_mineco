package com.egoveris.deo.ws.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

/**
 * La excepción se genera en los siguientes casos: - La excepción se genera al
 * querer generar el Documento 2 por firma conjunta teniendo asociado un
 * Documento 1
 */

public class FirmaConjuntaExceptionRec extends ApplicationException {

  private static final long serialVersionUID = -2005732742322825164L;

  public FirmaConjuntaExceptionRec(final String mensajeErrorFirmaConjuntaRec) {
    super(mensajeErrorFirmaConjuntaRec);
  }

  public FirmaConjuntaExceptionRec(final String mensajeErrorFirmaConjuntaRec, Throwable cause) {
    super(mensajeErrorFirmaConjuntaRec, cause);
  }

}
