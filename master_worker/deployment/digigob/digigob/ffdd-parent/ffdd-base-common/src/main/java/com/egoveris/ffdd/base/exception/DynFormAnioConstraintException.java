package com.egoveris.ffdd.base.exception;

import com.egoveris.ffdd.model.exception.DynFormValorComponente;

public class DynFormAnioConstraintException extends DynFormValorComponente {

  private static final long serialVersionUID = -6233552081878044437L;

  public DynFormAnioConstraintException(String message) {
    super(message);
  }

  public DynFormAnioConstraintException(String message, Throwable cause) {
    super(message, cause);
  }
}
