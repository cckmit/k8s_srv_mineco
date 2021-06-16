package com.egoveris.ccomplejos.base.model;

public class VistaHojaAnexaDinDTO extends AbstractCComplejoDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5170166788082573082L;

	Integer numeroDeSecuencia;
	Integer numeroDeInsumo;
	String nombreInsumo;
	Integer numeroDeItemAbona;
	String nombreDeLaMercancia;
	Integer cantidad;
	String codigoUnidadDeMedidaAbona;
	String factorDeConsumo;
	String insumosUtilizados;

	/**
	 * @return the numeroDeSecuencia
	 */
	public Integer getNumeroDeSecuencia() {
		return numeroDeSecuencia;
	}

	/**
	 * @param numeroDeSecuencia
	 *            the numeroDeSecuencia to set
	 */
	public void setNumeroDeSecuencia(Integer numeroDeSecuencia) {
		this.numeroDeSecuencia = numeroDeSecuencia;
	}

	/**
	 * @return the numeroDeInsumo
	 */
	public Integer getNumeroDeInsumo() {
		return numeroDeInsumo;
	}

	/**
	 * @param numeroDeInsumo
	 *            the numeroDeInsumo to set
	 */
	public void setNumeroDeInsumo(Integer numeroDeInsumo) {
		this.numeroDeInsumo = numeroDeInsumo;
	}

	/**
	 * @return the nombreInsumo
	 */
	public String getNombreInsumo() {
		return nombreInsumo;
	}

	/**
	 * @param nombreInsumo
	 *            the nombreInsumo to set
	 */
	public void setNombreInsumo(String nombreInsumo) {
		this.nombreInsumo = nombreInsumo;
	}

	/**
	 * @return the numeroDeItemAbona
	 */
	public Integer getNumeroDeItemAbona() {
		return numeroDeItemAbona;
	}

	/**
	 * @param numeroDeItemAbona
	 *            the numeroDeItemAbona to set
	 */
	public void setNumeroDeItemAbona(Integer numeroDeItemAbona) {
		this.numeroDeItemAbona = numeroDeItemAbona;
	}

	/**
	 * @return the nombreDeLaMercancia
	 */
	public String getNombreDeLaMercancia() {
		return nombreDeLaMercancia;
	}

	/**
	 * @param nombreDeLaMercancia
	 *            the nombreDeLaMercancia to set
	 */
	public void setNombreDeLaMercancia(String nombreDeLaMercancia) {
		this.nombreDeLaMercancia = nombreDeLaMercancia;
	}

	/**
	 * @return the cantidad
	 */
	public Integer getCantidad() {
		return cantidad;
	}

	/**
	 * @param cantidad
	 *            the cantidad to set
	 */
	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	/**
	 * @return the codigoUnidadDeMedidaAbona
	 */
	public String getCodigoUnidadDeMedidaAbona() {
		return codigoUnidadDeMedidaAbona;
	}

	/**
	 * @param codigoUnidadDeMedidaAbona
	 *            the codigoUnidadDeMedidaAbona to set
	 */
	public void setCodigoUnidadDeMedidaAbona(String codigoUnidadDeMedidaAbona) {
		this.codigoUnidadDeMedidaAbona = codigoUnidadDeMedidaAbona;
	}

	/**
	 * @return the factorDeConsumo
	 */
	public String getFactorDeConsumo() {
		return factorDeConsumo;
	}

	/**
	 * @param factorDeConsumo
	 *            the factorDeConsumo to set
	 */
	public void setFactorDeConsumo(String factorDeConsumo) {
		this.factorDeConsumo = factorDeConsumo;
	}

	/**
	 * @return the insumosUtilizados
	 */
	public String getInsumosUtilizados() {
		return insumosUtilizados;
	}

	/**
	 * @param insumosUtilizados
	 *            the insumosUtilizados to set
	 */
	public void setInsumosUtilizados(String insumosUtilizados) {
		this.insumosUtilizados = insumosUtilizados;
	}


}