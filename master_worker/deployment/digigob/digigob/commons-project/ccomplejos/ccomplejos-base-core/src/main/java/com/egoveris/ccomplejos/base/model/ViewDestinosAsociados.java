package com.egoveris.ccomplejos.base.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CC_VIEW_DESTINOS_ASOCIADOS")
public class ViewDestinosAsociados extends AbstractCComplejoJPA{
	
	@Column(name = "CODIGO_PRODUCTO")
	protected String codigoProducto;
	@Column(name = "USO_PREVISTO")
	protected String usoPrevisto;
	@Column(name = "NOMBRE_INSTALACION")
	protected String nombreInstalacion;
	@Column(name = "CANTIDAD")
	protected BigDecimal cantidad;
	@Column(name = "NOMBRE_PRODUCTO")
	protected String nombreProducto;
	
	
	public String getCodigoProducto() {
		return codigoProducto;
	}
	public void setCodigoProducto(String codigoProducto) {
		this.codigoProducto = codigoProducto;
	}
	public String getUsoPrevisto() {
		return usoPrevisto;
	}
	public void setUsoPrevisto(String usoPrevisto) {
		this.usoPrevisto = usoPrevisto;
	}
	public String getNombreInstalacion() {
		return nombreInstalacion;
	}
	public void setNombreInstalacion(String nombreInstalacion) {
		this.nombreInstalacion = nombreInstalacion;
	}
	public BigDecimal getCantidad() {
		return cantidad;
	}
	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}
	public String getNombreProducto() {
		return nombreProducto;
	}
	public void setNombreProducto(String nombreProducto) {
		this.nombreProducto = nombreProducto;
	}
	
	

}
