package com.egoveris.te.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;


public class PermisoException extends ApplicationException {

  private static final long serialVersionUID = -2650039278697349216L;

  public PermisoException(Throwable cause) {
    super("Se ha producido un error al cargar un permiso", cause);
  }

  public PermisoException(String mensaje, Throwable cause) {
    super(mensaje, cause);
  }


}
