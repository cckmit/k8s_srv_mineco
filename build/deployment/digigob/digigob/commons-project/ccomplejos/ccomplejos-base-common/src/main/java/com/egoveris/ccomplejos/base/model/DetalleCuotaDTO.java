package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class DetalleCuotaDTO extends AbstractCComplejoDTO implements Serializable{

	private static final long serialVersionUID = -3900817132296992382L;
	protected String codigoUno;
	protected String codigoDos;
	protected Date fecha;
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
