package com.egoveris.edt.base.exception;

import org.terasoluna.plus.common.exception.ApplicationException;

/*
 * Exception de tipo checked que lanzara cualquier error 
 * ocurrido en los DAOs de LDAP
 */
public class AccesoLdapException extends ApplicationException {

  private static final long serialVersionUID = 2752242573483524994L;


  public AccesoLdapException(String message) {
    super(message);
  }

  public AccesoLdapException(String message, Throwable cause) {
    super(message, cause);
  }

}
