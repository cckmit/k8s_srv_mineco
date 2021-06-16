package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class ViewDestinosAsociadosDTO extends AbstractCComplejoDTO implements Serializable{
	
	
	private static final long serialVersionUID = -6054205914038127769L;
	
	protected String codigoProducto;
	protected String usoPrevisto;
	protected String nombreInstalacion;
	protected BigDecimal cantidad;
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
