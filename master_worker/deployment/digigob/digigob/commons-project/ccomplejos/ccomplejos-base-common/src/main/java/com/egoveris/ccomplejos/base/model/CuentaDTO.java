package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;

public class CuentaDTO extends AbstractCComplejoDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	String montoCuentaOTR;
	String codigo;
	Long monto;
	String codigoCuentaOTR;
	Long porcentaje;
	String signo;
	
	
	public String getMontoCuentaOTR() {
		return montoCuentaOTR;
	}
	public void setMontoCuentaOTR(String montoCuentaOTR) {
		this.montoCuentaOTR = montoCuentaOTR;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public Long getMonto() {
		return monto;
	}
	public void setMonto(Long monto) {
		this.monto = monto;
	}
	public String getCodigoCuentaOTR() {
		return codigoCuentaOTR;
	}
	public void setCodigoCuentaOTR(String codigoCuentaOTR) {
		this.codigoCuentaOTR = codigoCuentaOTR;
	}
	public Long getPorcentaje() {
		return porcentaje;
	}
	public void setPorcentaje(Long porcentaje) {
		this.porcentaje = porcentaje;
	}
	public String getSigno() {
		return signo;
	}
	public void setSigno(String signo) {
		this.signo = signo;
	}
	
	

}
