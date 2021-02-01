package com.egoveris.deo.ws.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

/**
 * Esta excepción se genera cuando el usuario que consulta no cumple ninguna de
 * las siguientes condiciones:
 * 
 * - Que sea el usuario generador del documento. - Que haya participado en las
 * tareas de producción, revisión y/o firma, durante el proceso de generación
 * del documento. - Que pertenezca a la repartición del usuario que genero el
 * documento, y tenga habilitado el permiso en el LDAP para ver documentos
 * confidenciales.
 */

public class SinPrivilegiosException extends ApplicationException {

  /**
  * 
  */
  private static final long serialVersionUID = -1252279170171110069L;

  private static final String mensajeError = "Error, el usuario no cuenta con privilegios de consulta sobre el documento con número: ";

  public SinPrivilegiosException(final String numeroDocumento) {
    super(mensajeError + numeroDocumento);
  }

  public SinPrivilegiosException(final String numeroDocumento, Throwable cause) {
    super(mensajeError + numeroDocumento, cause);
  }
}
