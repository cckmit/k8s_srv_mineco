package com.egoveris.te.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;


public class VerificarExisteTareaException extends ApplicationException {

  private static final long serialVersionUID = -2650039278697349216L;

  public VerificarExisteTareaException(Throwable cause) {
    super("No existe la tarea", cause);
  }

  public VerificarExisteTareaException(String mensaje, Throwable cause) {
    super(mensaje, cause);
  }


}
