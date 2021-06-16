package com.egoveris.edt.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

/*
 * Exception de tipo checked que lanzara cualquier error 
 * ocurrido en los DAOs
 */
public class AccesoDatosException extends ApplicationException {

  private static final long serialVersionUID = 9047765419569355973L;



  public AccesoDatosException(String msg) {
    super(msg);
  }

  public AccesoDatosException(String message, Throwable cause) {
    super(message, cause);
  }

}
