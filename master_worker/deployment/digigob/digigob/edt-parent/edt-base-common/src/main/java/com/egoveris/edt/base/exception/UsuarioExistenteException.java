package com.egoveris.edt.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class UsuarioExistenteException extends ApplicationException {

  private static final long serialVersionUID = -4080251353033017344L;

  public UsuarioExistenteException(String message) {
    super(message);
  }

  public UsuarioExistenteException(String message, Throwable cause) {
    super(message, cause);
  }

}