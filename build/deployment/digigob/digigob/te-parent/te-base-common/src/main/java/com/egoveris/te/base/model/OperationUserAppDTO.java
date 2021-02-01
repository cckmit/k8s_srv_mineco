package com.egoveris.te.base.model;

import com.egoveris.te.model.model.ValidateUser;

public class OperationUserAppDTO {

  private OperationAppDTO operation;
  private ValidateUser user;
  private SubprocessAppDTO subprocessToStart;

  public OperationAppDTO getOperation() {
    return operation;
  }

  public void setOperation(OperationAppDTO operation) {
    this.operation = operation;
  }

  public ValidateUser getUser() {
    return user;
  }

  public void setUser(ValidateUser user) {
    this.user = user;
  }

  public SubprocessAppDTO getSubprocessToStart() {
    return subprocessToStart;
  }

  public void setSubprocessToStart(SubprocessAppDTO subprocessToStart) {
    this.subprocessToStart = subprocessToStart;
  }

}