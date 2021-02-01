package com.egoveris.te.model.model;

import java.io.Serializable;

/**
 * The Class Status.
 */
public class Status implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -967722749988574138L;
	
	/** The Constant OK. */
	public static final String OK = "OK";

	/** The Constant NOK. */
	public static final String NOK = "NOK";

	/** The Constant ERROR. */
	public static final String ERROR = "ERROR";
	
	// code
	private int code;

	// desc
	private String desc;

	// motivo
	private String motivo;

	/**
	 * Instantiates a new status.
	 */
	public Status() {
    // Default constructor
  }
  
	/**
	 * Instantiates a new status.
	 *
	 * @param status
	 *            the status
	 */
  public Status(String status) {
    switch (status) {
    case OK:
      this.code = 1;
      this.desc = OK;
			this.motivo = "";
      break;
    case NOK:
      this.code = 2;
      this.desc = NOK;
			this.motivo = "";
      break;
    case ERROR:
      this.code = 3;
      this.desc = NOK;
			this.motivo = "";
      break;
    default:
      break;
    }
  }
  
  	/**
	 * Gets the code.
	 *
	 * @return the code
	 */
  public int getCode() {
    return code;
  }

  	/**
	 * Sets the code.
	 *
	 * @param code
	 *            the code to set
	 */
  public void setCode(int code) {
    this.code = code;
  }

  	/**
	 * Gets the desc.
	 *
	 * @return the desc
	 */
  public String getDesc() {
    return desc;
  }

  	/**
	 * Sets the desc.
	 *
	 * @param desc
	 *            the desc to set
	 */
  public void setDesc(String desc) {
    this.desc = desc;
  }

	/**
	 * Gets the motivo.
	 *
	 * @return the motivo
	 */
	public String getMotivo() {
		return motivo;
	}

	/**
	 * Sets the motivo.
	 *
	 * @param motivo
	 *            the new motivo
	 */
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
  
}
