package com.egoveris.te.base.model;

import java.util.List;

public class OperationAppDTO {
	
	private String code;
	private String initDate;
	private String endDate;
	private String operationType;
	private String isBloked;
	private String status;
	private String jbpmCode;
	private List<SubprocessOpAppDTO> subprocess;
	private List<SubprocessAppDTO> availableSubprocess;

  /**
   * @return the endDate
   */
  public String getEndDate() {
    return endDate;
  }

  /**
   * @param endDate
   *          the endDate to set
   */
  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  /**
   * @return the operationType
   */
  public String getOperationType() {
    return operationType;
  }

  /**
   * @param operationType
   *          the operationType to set
   */
  public void setOperationType(String operationType) {
    this.operationType = operationType;
  }

  /**
   * @return the code
   */
  public String getCode() {
    return code;
  }

  /**
   * @param code
   *          the code to set
   */
  public void setCode(String code) {
    this.code = code;
  }

  /**
   * @return the initDate
   */
  public String getInitDate() {
    return initDate;
  }

  /**
   * @param initDate
   *          the initDate to set
   */
  public void setInitDate(String initDate) {
    this.initDate = initDate;
  }

  /**
   * @return the isBloked
   */
  public String getIsBloked() {
    return isBloked;
  }

  /**
   * @param isBloked
   *          the isBloked to set
   */
  public void setIsBloked(String isBloked) {
    this.isBloked = isBloked;
  }

  /**
   * @return the status
   */
  public String getStatus() {
    return status;
  }

  /**
   * @param status
   *          the status to set
   */
  public void setStatus(String status) {
    this.status = status;
  }

  /**
   * @return the jbpmCode
   */
  public String getJbpmCode() {
    return jbpmCode;
  }

  /**
   * @param jbpmCode
   *          the jbpmCode to set
   */
  public void setJbpmCode(String jbpmCode) {
    this.jbpmCode = jbpmCode;
  }

  /**
   * @return the subprocees
   */
  public List<SubprocessOpAppDTO> getSubprocess() {
    return subprocess;
  }

  /**
   * @param subprocees
   *          the subprocees to set
   */
  public void setSubprocess(List<SubprocessOpAppDTO> subprocess) {
    this.subprocess = subprocess;
  }

  /**
   * @return the availableSubprocess
   */
  public List<SubprocessAppDTO> getAvailableSubprocess() {
    return availableSubprocess;
  }

  /**
   * @param availableSubprocess the availableSubprocess to set
   */
  public void setAvailableSubprocess(List<SubprocessAppDTO> availableSubprocess) {
    this.availableSubprocess = availableSubprocess;
  }
		
}