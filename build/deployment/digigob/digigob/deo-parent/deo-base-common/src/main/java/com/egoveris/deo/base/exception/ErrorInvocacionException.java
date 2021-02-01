package com.egoveris.deo.base.exception;

import java.io.Serializable;

import org.terasoluna.plus.common.exception.ApplicationException;

public class ErrorInvocacionException extends ApplicationException implements Serializable {	
	/**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * 
   */
  

  public ErrorInvocacionException(String message){
		super(message);
	}
	
	public ErrorInvocacionException(String message, Throwable cause){
		super(message, cause);
	}
}
