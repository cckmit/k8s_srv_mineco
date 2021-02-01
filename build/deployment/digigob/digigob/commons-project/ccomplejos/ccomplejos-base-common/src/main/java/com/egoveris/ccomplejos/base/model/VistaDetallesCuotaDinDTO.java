package com.egoveris.ccomplejos.base.model;

import java.util.Date;

public class VistaDetallesCuotaDinDTO extends AbstractCComplejoDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5170166788082573082L;

	String codigoMontoCuota;
	Date fechaVencimientoCuota;
	String montoCuota;

	/**
	 * @return the codigoMontoCuota
	 */
	public String getCodigoMontoCuota() {
		return codigoMontoCuota;
	}

	/**
	 * @param codigoMontoCuota
	 *            the codigoMontoCuota to set
	 */
	public void setCodigoMontoCuota(String codigoMontoCuota) {
		this.codigoMontoCuota = codigoMontoCuota;
	}

	/**
	 * @return the fechaVencimientoCuota
	 */
	public Date getFechaVencimientoCuota() {
		return fechaVencimientoCuota;
	}

	/**
	 * @param fechaVencimientoCuota
	 *            the fechaVencimientoCuota to set
	 */
	public void setFechaVencimientoCuota(Date fechaVencimientoCuota) {
		this.fechaVencimientoCuota = fechaVencimientoCuota;
	}

	/**
	 * @return the montoCuota
	 */
	public String getMontoCuota() {
		return montoCuota;
	}

	/**
	 * @param montoCuota
	 *            the montoCuota to set
	 */
	public void setMontoCuota(String montoCuota) {
		this.montoCuota = montoCuota;
	}

}