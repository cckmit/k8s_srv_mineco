package com.egoveris.te.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class DocumentoOArchivoSinPermisoDeVisualizacionException extends ApplicationException {

  private static final long serialVersionUID = 5600290676561803161L;

  public DocumentoOArchivoSinPermisoDeVisualizacionException(Throwable cause) {
    super("No tiene permisos para visualizar el documento", cause);
  }

  public DocumentoOArchivoSinPermisoDeVisualizacionException(String mensaje, Throwable cause) {
    super(mensaje, cause);
  }
}
