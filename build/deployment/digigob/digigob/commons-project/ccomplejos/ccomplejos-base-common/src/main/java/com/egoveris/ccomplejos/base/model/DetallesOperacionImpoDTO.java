package com.egoveris.ccomplejos.base.model;

import java.util.Date;

public class DetallesOperacionImpoDTO extends AbstractCComplejoDTO{
	
	private static final long serialVersionUID = 3406981211289430792L;
	String estadoOp;
	String numeroOp;
	Date fechaCreacion;
	String tipoOperacion;
	String tipoIngreso;
	
	public String getEstadoOp() {
		return estadoOp;
	}
	public void setEstadoOp(String estadoOp) {
		this.estadoOp = estadoOp;
	}
	public String getNumeroOp() {
		return numeroOp;
	}
	public void setNumeroOp(String numeroOp) {
		this.numeroOp = numeroOp;
	}
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public String getTipoOperacion() {
		return tipoOperacion;
	}
	public void setTipoOperacion(String tipoOperacion) {
		this.tipoOperacion = tipoOperacion;
	}
	public String getTipoIngreso() {
		return tipoIngreso;
	}
	public void setTipoIngreso(String tipoIngreso) {
		this.tipoIngreso = tipoIngreso;
	}

	
}
