package com.egoveris.ccomplejos.base.model;

import java.math.BigDecimal;

public class VistaOTRDinDTO extends AbstractCComplejoDTO{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5170166788082573082L;

	Integer secuencialCuenta;
	String codigoCuentaOTR;
	BigDecimal montoCuentaOTR;

	/**
	 * @return the secuencialCuenta
	 */
	public Integer getSecuencialCuenta() {
		return secuencialCuenta;
	}

	/**
	 * @param secuencialCuenta
	 *            the secuencialCuenta to set
	 */
	public void setSecuencialCuenta(Integer secuencialCuenta) {
		this.secuencialCuenta = secuencialCuenta;
	}

	/**
	 * @return the codigoCuentaOTR
	 */
	public String getCodigoCuentaOTR() {
		return codigoCuentaOTR;
	}

	/**
	 * @param codigoCuentaOTR
	 *            the codigoCuentaOTR to set
	 */
	public void setCodigoCuentaOTR(String codigoCuentaOTR) {
		this.codigoCuentaOTR = codigoCuentaOTR;
	}

	/**
	 * @return the montoCuentaOTR
	 */
	public BigDecimal getMontoCuentaOTR() {
		return montoCuentaOTR;
	}

	/**
	 * @param montoCuentaOTR
	 *            the montoCuentaOTR to set
	 */
	public void setMontoCuentaOTR(BigDecimal montoCuentaOTR) {
		this.montoCuentaOTR = montoCuentaOTR;
	}

}