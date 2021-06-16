package com.egoveris.ccomplejos.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
@Entity
@Table(name="CC_CUENTA")
public class Cuenta extends AbstractCComplejoJPA{
	
	@Column(name="MONTO_CUENTA_OTR")
	protected String montoCuentaOTR;
	@Column(name="CODIGO")
	protected String codigo;
	@Column(name="MONTO")
	protected Long monto;
	@Column(name="CODIGO_CUENTA_OTR")
	protected String codigoCuentaOTR;
	@Column(name="PORCENTAJE")
	protected Long porcentaje;
	@Column(name="SIGNO")
	protected String signo;
	
	
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
