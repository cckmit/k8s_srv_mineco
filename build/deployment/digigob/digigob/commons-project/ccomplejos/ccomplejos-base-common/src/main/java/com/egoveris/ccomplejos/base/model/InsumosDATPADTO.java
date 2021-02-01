package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;

public class InsumosDATPADTO extends AbstractCComplejoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2099777801075106775L;
	
	protected String numeroInsumo;
	protected String descripcionInsumo;
	protected Long cantidadInsumo;
	protected String unidadMedidaCantidad;
	protected Long numeroHoja;
	protected Long numeroItem;
	protected String valorCIFUnitario;
	protected String unidadMedidaCIF;
	
	public String getNumeroInsumo() {
		return numeroInsumo;
	}
	public void setNumeroInsumo(String numeroInsumo) {
		this.numeroInsumo = numeroInsumo;
	}
	public String getDescripcionInsumo() {
		return descripcionInsumo;
	}
	public void setDescripcionInsumo(String descripcionInsumo) {
		this.descripcionInsumo = descripcionInsumo;
	}
	public Long getCantidadInsumo() {
		return cantidadInsumo;
	}
	public void setCantidadInsumo(Long cantidadInsumo) {
		this.cantidadInsumo = cantidadInsumo;
	}
	public String getUnidadMedidaCantidad() {
		return unidadMedidaCantidad;
	}
	public void setUnidadMedidaCantidad(String unidadMedidaCantidad) {
		this.unidadMedidaCantidad = unidadMedidaCantidad;
	}
	public Long getNumeroHoja() {
		return numeroHoja;
	}
	public void setNumeroHoja(Long numeroHoja) {
		this.numeroHoja = numeroHoja;
	}
	public Long getNumeroItem() {
		return numeroItem;
	}
	public void setNumeroItem(Long numeroItem) {
		this.numeroItem = numeroItem;
	}
	public String getValorCIFUnitario() {
		return valorCIFUnitario;
	}
	public void setValorCIFUnitario(String valorCIFUnitario) {
		this.valorCIFUnitario = valorCIFUnitario;
	}
	public String getUnidadMedidaCIF() {
		return unidadMedidaCIF;
	}
	public void setUnidadMedidaCIF(String unidadMedidaCIF) {
		this.unidadMedidaCIF = unidadMedidaCIF;
	}

}
