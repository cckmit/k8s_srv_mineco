package com.egoveris.ccomplejos.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CC_INSUMOS_DATPA")
public class InsumosDATPA extends AbstractCComplejoJPA {
	
	@Column(name = "NUMERO")
	protected String numeroInsumo;
	
	@Column(name = "DESCRIPCION")
	protected String descripcionInsumo;
	
	@Column(name = "CANTIDAD")
	protected Long cantidadInsumo;
	
	@Column(name = "UNIDAD_MEDIDA_CANT")
	protected String unidadMedidaCantidad;
	
	@Column(name = "NRO_HOJA")
	protected Long numeroHoja;
	
	@Column(name = "NROI_ITEM")
	protected Long numeroItem;
	
	@Column(name = "VALOR_CIF_UNITARIO")
	protected String valorCIFUnitario;
	
	@Column(name = "UNIDAD_MEDIDA_CIF")
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
