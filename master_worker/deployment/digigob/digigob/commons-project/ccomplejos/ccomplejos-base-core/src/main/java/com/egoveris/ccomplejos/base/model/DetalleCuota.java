package com.egoveris.ccomplejos.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CC_DETALLE_CUOTA")
public class DetalleCuota extends AbstractCComplejoJPA {
	
	@Column(name = "CODIGO_UNO")
	protected String codigoUno;
	@Column(name = "CODIGO_DOS")
	protected String codigoDos;
	@Column(name = "FECHA")
	protected Date fecha;
	@Column(name = "MONTO")
	protected Long monto;

	
	public String getCodigoUno() {
		return codigoUno;
	}
	public void setCodigoUno(String codigoUno) {
		this.codigoUno = codigoUno;
	}
	public String getCodigoDos() {
		return codigoDos;
	}
	public void setCodigoDos(String codigoDos) {
		this.codigoDos = codigoDos;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public Long getMonto() {
		return monto;
	}
	public void setMonto(Long monto) {
		this.monto = monto;
	}
} 