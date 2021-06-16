package com.egoveris.edt.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class ServicioWebException extends ApplicationException {

  private static final long serialVersionUID = -6425108695609825235L;

  public ServicioWebException(String msg, Throwable th) {
    super(msg, th);
  }

  public ServicioWebException(String string) {
    super(string);
  }
  
  public static long getSerialversionuid() {
    return serialVersionUID;
  }

}
