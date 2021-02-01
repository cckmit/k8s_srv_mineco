package com.egoveris.ccomplejos.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CC_DETALLES_OPERACION_IMPO")
public class DetallesOperacionImpo extends AbstractCComplejoJPA{
	
	@Column(name = "ESTADO_OP")
	protected String estadoOp;
	@Column(name = "NUMERO_OP")
	protected String numeroOp;
	@Column(name = "FECHA_CREACION")
	protected Date fechaCreacion;
	@Column(name = "TIPO_OPERACION")
	protected String tipoOperacion;
	@Column(name = "TIPO_INGRESO")
	protected String tipoIngreso;
	
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
