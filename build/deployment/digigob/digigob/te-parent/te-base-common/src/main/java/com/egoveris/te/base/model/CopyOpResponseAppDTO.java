package com.egoveris.te.base.model;

import com.egoveris.te.model.model.Status;

public class CopyOpResponseAppDTO {

	private String operationCode;
	private Status status;

	/**
   * @return the operationCode
   */
  public String getOperationCode() {
    return operationCode;
  }

  /**
   * @param operationCode the operationCode to set
   */
  public void setOperationCode(String operationCode) {
    this.operationCode = operationCode;
  }

  /**
	 * @return the status
	 */
	public Status getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Status status) {
		this.status = status;
	}

}