package com.egoveris.deo.ws.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

/**
 * La excepción se genera en los siguientes casos: - El tipo de documento no
 * está configurado en GEDO. - Los atributos del tipo de documento como son
 * generación manual, o generación de documentos importados no son coherentes
 * con el content-type de los datos enviados. - El tipo de documento es para
 * importación, y el tipo de archivo no es soportado por el servicio de
 * conversión a pdf. - El tipo de documento tiene datos propios obligatorios. -
 * Al menos uno de los parámetros obligatorios es nulo.
 */

public class ParametroInvalidoException extends ApplicationException {

  private static final long serialVersionUID = -2149778340757142516L;

  public ParametroInvalidoException(final String mensajeErrorParametro) {
    super(mensajeErrorParametro);
  }

  public ParametroInvalidoException(final String mensajeErrorParametro, Throwable cause) {
    super(mensajeErrorParametro, cause);
  }
}
