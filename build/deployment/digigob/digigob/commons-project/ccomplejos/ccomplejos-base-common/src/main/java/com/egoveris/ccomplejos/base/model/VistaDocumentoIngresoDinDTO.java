package com.egoveris.ccomplejos.base.model;

import java.util.Date;

public class VistaDocumentoIngresoDinDTO extends AbstractCComplejoDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5170166788082573082L;


	String numeroDocumento;
	Date fechaResolucion;
	String numeroItem;

	/**
	 * @return the numeroDocumento
	 */
	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	/**
	 * @param numeroDocumento
	 *            the numeroDocumento to set
	 */
	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	/**
	 * @return the fechaResolucion
	 */
	public Date getFechaResolucion() {
		return fechaResolucion;
	}

	/**
	 * @param fechaResolucion
	 *            the fechaResolucion to set
	 */
	public void setFechaResolucion(Date fechaResolucion) {
		this.fechaResolucion = fechaResolucion;
	}

	/**
	 * @return the numeroItem
	 */
	public String getNumeroItem() {
		return numeroItem;
	}

	/**
	 * @param numeroItem
	 *            the numeroItem to set
	 */
	public void setNumeroItem(String numeroItem) {
		this.numeroItem = numeroItem;
	}



}
