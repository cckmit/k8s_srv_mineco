package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;
import java.util.Date;

public class VistaManifiestoDinDTO extends AbstractCComplejoDTO implements Serializable {

  private static final long serialVersionUID = 1L;

  
  
	protected String secuencialManifiesto;
	protected String numeroManifiesto;
	protected Date fechaManifiesto;

	/**
	 * @return the secuencialManifiesto
	 */
	public String getSecuencialManifiesto() {
		return secuencialManifiesto;
	}

	/**
	 * @param secuencialManifiesto
	 *            the secuencialManifiesto to set
	 */
	public void setSecuencialManifiesto(String secuencialManifiesto) {
		this.secuencialManifiesto = secuencialManifiesto;
	}

	/**
	 * @return the numeroManifiesto
	 */
	public String getNumeroManifiesto() {
		return numeroManifiesto;
	}

	/**
	 * @param numeroManifiesto
	 *            the numeroManifiesto to set
	 */
	public void setNumeroManifiesto(String numeroManifiesto) {
		this.numeroManifiesto = numeroManifiesto;
	}

	/**
	 * @return the fechaManifiesto
	 */
	public Date getFechaManifiesto() {
		return fechaManifiesto;
	}

	/**
	 * @param fechaManifiesto
	 *            the fechaManifiesto to set
	 */
	public void setFechaManifiesto(Date fechaManifiesto) {
		this.fechaManifiesto = fechaManifiesto;
	}

}
