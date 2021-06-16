package com.egoveris.ccomplejos.base.model;

public class VistaInsumosDATPADinDTO extends AbstractCComplejoDTO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5170166788082573082L;

	Integer numeroItem;
	Integer numeroInsumo;
	String descripcionInsumo;
	Integer cantidadDeInsumo;
	String codigoUnidadDeMedida;
	String valorCifUnitario;

	/**
	 * @return the numeroItem
	 */
	public Integer getNumeroItem() {
		return numeroItem;
	}

	/**
	 * @param numeroItem
	 *            the numeroItem to set
	 */
	public void setNumeroItem(Integer numeroItem) {
		this.numeroItem = numeroItem;
	}

	/**
	 * @return the numeroInsumo
	 */
	public Integer getNumeroInsumo() {
		return numeroInsumo;
	}

	/**
	 * @param numeroInsumo
	 *            the numeroInsumo to set
	 */
	public void setNumeroInsumo(Integer numeroInsumo) {
		this.numeroInsumo = numeroInsumo;
	}

	/**
	 * @return the descripcionInsumo
	 */
	public String getDescripcionInsumo() {
		return descripcionInsumo;
	}

	/**
	 * @param descripcionInsumo
	 *            the descripcionInsumo to set
	 */
	public void setDescripcionInsumo(String descripcionInsumo) {
		this.descripcionInsumo = descripcionInsumo;
	}

	/**
	 * @return the cantidadDeInsumo
	 */
	public Integer getCantidadDeInsumo() {
		return cantidadDeInsumo;
	}

	/**
	 * @param cantidadDeInsumo
	 *            the cantidadDeInsumo to set
	 */
	public void setCantidadDeInsumo(Integer cantidadDeInsumo) {
		this.cantidadDeInsumo = cantidadDeInsumo;
	}

	/**
	 * @return the codigoUnidadDeMedida
	 */
	public String getCodigoUnidadDeMedida() {
		return codigoUnidadDeMedida;
	}

	/**
	 * @param codigoUnidadDeMedida
	 *            the codigoUnidadDeMedida to set
	 */
	public void setCodigoUnidadDeMedida(String codigoUnidadDeMedida) {
		this.codigoUnidadDeMedida = codigoUnidadDeMedida;
	}

	/**
	 * @return the valorCifUnitario
	 */
	public String getValorCifUnitario() {
		return valorCifUnitario;
	}

	/**
	 * @param valorCifUnitario
	 *            the valorCifUnitario to set
	 */
	public void setValorCifUnitario(String valorCifUnitario) {
		this.valorCifUnitario = valorCifUnitario;
	}


}