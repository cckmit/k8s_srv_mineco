package com.egoveris.deo.base.exception;

import java.io.Serializable;

import org.terasoluna.plus.common.exception.ApplicationException;

public class ParametroInvalidoException extends ApplicationException implements Serializable {



	/**
   * 
   */
  private static final long serialVersionUID = 1L;

  public ParametroInvalidoException(String message){
		super(message);
	}
	
	public ParametroInvalidoException(String message, Throwable cause){
		super(message, cause);
	}
}