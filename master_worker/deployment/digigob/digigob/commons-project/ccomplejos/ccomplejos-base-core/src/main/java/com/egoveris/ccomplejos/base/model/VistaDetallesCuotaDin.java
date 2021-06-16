package com.egoveris.ccomplejos.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "view_detallescuotadin")
public class VistaDetallesCuotaDin extends AbstractViewCComplejoJPA {
	
	@Column(name = "CODIGO_MONTO")
	protected String codigoMontoCuota;
	
	@Column(name = "FECHA_VENCIMIENTO")
	protected Date fechaVencimientoCuota;
	
	@Column(name = "MONTO_CUOTA")
	protected String montoCuota;

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