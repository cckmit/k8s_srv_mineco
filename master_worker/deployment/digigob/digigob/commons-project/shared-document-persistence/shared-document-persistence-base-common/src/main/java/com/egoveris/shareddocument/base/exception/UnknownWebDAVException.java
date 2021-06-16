package com.egoveris.shareddocument.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

public class UnknownWebDAVException extends ApplicationException {

  /**
  * 
  */
  private static final long serialVersionUID = 2988132451600594062L;

  public UnknownWebDAVException(String arg0, Throwable arg1) {
    super(arg0, arg1);

  }

  public UnknownWebDAVException(int statusCode, String location) {
    super("Unknown error status: HTTP STATUS: " + String.valueOf(statusCode) + " for " + location);
  }

}