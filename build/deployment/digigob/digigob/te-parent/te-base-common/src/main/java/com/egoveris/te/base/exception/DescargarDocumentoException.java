package com.egoveris.te.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

/**
 * @author eduavega Esta clase tiene como responsabilidad personalizar el
 *         mensaje de error al subir un archivo que excede el maximo permitido.
 *
 */

public class DescargarDocumentoException extends ApplicationException {

  private static final long serialVersionUID = -2650039278697349216L;

  public DescargarDocumentoException(Throwable cause) {
    super("Se ha producido un error al descargar un archivo", cause);
  }

  public DescargarDocumentoException(String mensaje, Throwable cause) {
    super(mensaje, cause);
  }


}
