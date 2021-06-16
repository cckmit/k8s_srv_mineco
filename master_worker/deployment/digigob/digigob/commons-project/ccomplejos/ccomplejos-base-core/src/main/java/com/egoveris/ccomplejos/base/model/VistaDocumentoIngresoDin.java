package com.egoveris.ccomplejos.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "view_documentoingresodin")
public class VistaDocumentoIngresoDin extends AbstractViewCComplejoJPA {


	@Column(name = "NUMERO_DOCUMENTO")
	String numeroDocumento;

	@Column(name = "FECHA_RESOLUCION")
	Date fechaResolucion;

	@Column(name = "NUMERO_ITEM")
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
