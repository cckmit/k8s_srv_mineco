package com.egoveris.te.model.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class ExpedienteNoDisponibleException extends ApplicationException{

	private static final long serialVersionUID = -7821525023464180425L;

 public ExpedienteNoDisponibleException(Throwable cause) {
   super("Expediente no disponible", cause);
  }

  public ExpedienteNoDisponibleException(String message, Throwable cause) {
   super(message, cause);
  }
}
