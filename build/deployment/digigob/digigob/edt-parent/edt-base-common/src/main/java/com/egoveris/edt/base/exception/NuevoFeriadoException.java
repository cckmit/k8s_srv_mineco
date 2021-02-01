package com.egoveris.edt.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class NuevoFeriadoException extends ApplicationException {

  private static final long serialVersionUID = 4266622552294997770L;

  public NuevoFeriadoException(String msg, Throwable cause) {
    super(msg, cause);
  }

  public NuevoFeriadoException(String msg) {
    super(msg);
  }

}
