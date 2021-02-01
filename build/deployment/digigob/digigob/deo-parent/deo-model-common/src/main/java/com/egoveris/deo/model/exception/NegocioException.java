package com.egoveris.deo.model.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class NegocioException extends ApplicationException{

  public NegocioException(String code) {
    super(code);
  }

  public NegocioException(String code,Throwable w) {
    super(code,w);
  }
}
