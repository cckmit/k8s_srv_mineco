package com.egoveris.ccomplejos.base.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "view_otrdin")
public class VistaOTRDin extends AbstractViewCComplejoJPA {
	
	@Column(name = "SECUENCIA_CUENTA")
	protected Integer secuencialCuenta;
	
	@Column(name = "CODIGO_CUENTA")
	protected String codigoCuentaOTR;
	
	@Column(name = "MONTO_CUENTA")
	protected BigDecimal montoCuentaOTR;

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