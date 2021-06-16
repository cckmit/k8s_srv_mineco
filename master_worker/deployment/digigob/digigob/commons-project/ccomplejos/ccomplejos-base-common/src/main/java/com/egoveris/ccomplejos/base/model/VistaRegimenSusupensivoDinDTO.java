package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class VistaRegimenSusupensivoDinDTO extends AbstractCComplejoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	protected String direccionAlmacenamiento;
	protected String codigoComunaAlmacen;
	protected String codigoAduanaDeControl;
	protected String plazoVigencia;
	protected Integer numeroHojasInsumos;
	protected BigDecimal totalInsumos;
	protected String numeroDeRegimenSuspensivo;
	protected Date fechaRegimenSuspensivo;
	protected String aduanaRegimenSuspensivo;
	protected String numeroHojasAnexas;
	protected Integer cantidadSecuencias;
	protected String plazo;

	/**
	 * @return the direccionAlmacenamiento
	 */
	public String getDireccionAlmacenamiento() {
		return direccionAlmacenamiento;
	}

	/**
	 * @param direccionAlmacenamiento
	 *            the direccionAlmacenamiento to set
	 */
	public void setDireccionAlmacenamiento(String direccionAlmacenamiento) {
		this.direccionAlmacenamiento = direccionAlmacenamiento;
	}

	/**
	 * @return the codigoComunaAlmacen
	 */
	public String getCodigoComunaAlmacen() {
		return codigoComunaAlmacen;
	}

	/**
	 * @param codigoComunaAlmacen
	 *            the codigoComunaAlmacen to set
	 */
	public void setCodigoComunaAlmacen(String codigoComunaAlmacen) {
		this.codigoComunaAlmacen = codigoComunaAlmacen;
	}

	/**
	 * @return the codigoAduanaDeControl
	 */
	public String getCodigoAduanaDeControl() {
		return codigoAduanaDeControl;
	}

	/**
	 * @param codigoAduanaDeControl
	 *            the codigoAduanaDeControl to set
	 */
	public void setCodigoAduanaDeControl(String codigoAduanaDeControl) {
		this.codigoAduanaDeControl = codigoAduanaDeControl;
	}

	/**
	 * @return the plazoVigencia
	 */
	public String getPlazoVigencia() {
		return plazoVigencia;
	}

	/**
	 * @param plazoVigencia
	 *            the plazoVigencia to set
	 */
	public void setPlazoVigencia(String plazoVigencia) {
		this.plazoVigencia = plazoVigencia;
	}

	/**
	 * @return the numeroHojasInsumos
	 */
	public Integer getNumeroHojasInsumos() {
		return numeroHojasInsumos;
	}

	/**
	 * @param numeroHojasInsumos
	 *            the numeroHojasInsumos to set
	 */
	public void setNumeroHojasInsumos(Integer numeroHojasInsumos) {
		this.numeroHojasInsumos = numeroHojasInsumos;
	}

	/**
	 * @return the totalInsumos
	 */
	public BigDecimal getTotalInsumos() {
		return totalInsumos;
	}

	/**
	 * @param totalInsumos
	 *            the totalInsumos to set
	 */
	public void setTotalInsumos(BigDecimal totalInsumos) {
		this.totalInsumos = totalInsumos;
	}

	/**
	 * @return the numeroDeRegimenSuspensivo
	 */
	public String getNumeroDeRegimenSuspensivo() {
		return numeroDeRegimenSuspensivo;
	}

	/**
	 * @param numeroDeRegimenSuspensivo
	 *            the numeroDeRegimenSuspensivo to set
	 */
	public void setNumeroDeRegimenSuspensivo(String numeroDeRegimenSuspensivo) {
		this.numeroDeRegimenSuspensivo = numeroDeRegimenSuspensivo;
	}

	/**
	 * @return the fechaRegimenSuspensivo
	 */
	public Date getFechaRegimenSuspensivo() {
		return fechaRegimenSuspensivo;
	}

	/**
	 * @param fechaRegimenSuspensivo
	 *            the fechaRegimenSuspensivo to set
	 */
	public void setFechaRegimenSuspensivo(Date fechaRegimenSuspensivo) {
		this.fechaRegimenSuspensivo = fechaRegimenSuspensivo;
	}

	/**
	 * @return the aduanaRegimenSuspensivo
	 */
	public String getAduanaRegimenSuspensivo() {
		return aduanaRegimenSuspensivo;
	}

	/**
	 * @param aduanaRegimenSuspensivo
	 *            the aduanaRegimenSuspensivo to set
	 */
	public void setAduanaRegimenSuspensivo(String aduanaRegimenSuspensivo) {
		this.aduanaRegimenSuspensivo = aduanaRegimenSuspensivo;
	}

	/**
	 * @return the numeroHojasAnexas
	 */
	public String getNumeroHojasAnexas() {
		return numeroHojasAnexas;
	}

	/**
	 * @param numeroHojasAnexas
	 *            the numeroHojasAnexas to set
	 */
	public void setNumeroHojasAnexas(String numeroHojasAnexas) {
		this.numeroHojasAnexas = numeroHojasAnexas;
	}

	/**
	 * @return the cantidadSecuencias
	 */
	public Integer getCantidadSecuencias() {
		return cantidadSecuencias;
	}

	/**
	 * @param cantidadSecuencias
	 *            the cantidadSecuencias to set
	 */
	public void setCantidadSecuencias(Integer cantidadSecuencias) {
		this.cantidadSecuencias = cantidadSecuencias;
	}

	/**
	 * @return the plazo
	 */
	public String getPlazo() {
		return plazo;
	}

	/**
	 * @param plazo
	 *            the plazo to set
	 */
	public void setPlazo(String plazo) {
		this.plazo = plazo;
	}

}
