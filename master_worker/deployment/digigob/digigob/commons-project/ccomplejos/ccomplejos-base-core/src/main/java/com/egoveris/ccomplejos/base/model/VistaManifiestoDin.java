package com.egoveris.ccomplejos.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "view_manifiestodin")
public class VistaManifiestoDin extends AbstractViewCComplejoJPA {

	@Column(name = "SECUENCIA_MANIFIESTO")
	protected String secuencialManifiesto;

	@Column(name = "NUMERO_MANIFIESTO")
	protected String numeroManifiesto;

	@Column(name = "FECHA_MANIFIESTO")
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
