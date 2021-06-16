package com.egoveris.te.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

@SuppressWarnings("serial")
public class ValidacionDeTramitacionConjuntaException extends ApplicationException {

  public ValidacionDeTramitacionConjuntaException(Throwable cause) {
    super("Error en valdación de tramitación con junta", cause);
  }

  public ValidacionDeTramitacionConjuntaException(String mensaje, Throwable cause) {
    super(mensaje, cause); 
  }
}
