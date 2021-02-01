package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class VistaFinancieroDADTO extends AbstractCComplejoDTO implements Serializable {
	
	private static final long serialVersionUID = -1441681035377357839L;
	String descuento;
	String clausulaVentaTransaccion;
	String comisionesAlExterior;
	String otrosGastos;
	String modalidadVentaTransaccion;
	String numeroFactura;
	String formaPagoTransaccion;
	String tipoMonedaTransaccion;
	String indicaFacturaComercialDefinitiva;
	String codigoPaisAdquisicion;
	String tasaSeguro;
	Integer numeroCuotasPagoDiferido;
	Date fechaEmision;
	BigDecimal montoTotalFactura;
	BigDecimal montoTotalCIF;
	BigDecimal valorLiquidoRetorno;
	BigDecimal valorFlete;
	

	
	public String getDescuento() {
		return descuento;
	}
	public void setDescuento(String descuento) {
		this.descuento = descuento;
	}
	public String getClausulaVentaTransaccion() {
		return clausulaVentaTransaccion;
	}
	public void setClausulaVentaTransaccion(String clausulaVentaTransaccion) {
		this.clausulaVentaTransaccion = clausulaVentaTransaccion;
	}
	public String getComisionesAlExterior() {
		return comisionesAlExterior;
	}
	public void setComisionesAlExterior(String comisionesAlExterior) {
		this.comisionesAlExterior = comisionesAlExterior;
	}
	public String getOtrosGastos() {
		return otrosGastos;
	}
	public void setOtrosGastos(String otrosGastos) {
		this.otrosGastos = otrosGastos;
	}
	public String getModalidadVentaTransaccion() {
		return modalidadVentaTransaccion;
	}
	public void setModalidadVentaTransaccion(String modalidadVentaTransaccion) {
		this.modalidadVentaTransaccion = modalidadVentaTransaccion;
	}
	public String getNumeroFactura() {
		return numeroFactura;
	}
	public void setNumeroFactura(String numeroFactura) {
		this.numeroFactura = numeroFactura;
	}
	public String getFormaPagoTransaccion() {
		return formaPagoTransaccion;
	}
	public void setFormaPagoTransaccion(String formaPagoTransaccion) {
		this.formaPagoTransaccion = formaPagoTransaccion;
	}
	public String getTipoMonedaTransaccion() {
		return tipoMonedaTransaccion;
	}
	public void setTipoMonedaTransaccion(String tipoMonedaTransaccion) {
		this.tipoMonedaTransaccion = tipoMonedaTransaccion;
	}
	public String getIndicaFacturaComercialDefinitiva() {
		return indicaFacturaComercialDefinitiva;
	}
	public void setIndicaFacturaComercialDefinitiva(String indicaFacturaComercialDefinitiva) {
		this.indicaFacturaComercialDefinitiva = indicaFacturaComercialDefinitiva;
	}
	public String getCodigoPaisAdquisicion() {
		return codigoPaisAdquisicion;
	}
	public void setCodigoPaisAdquisicion(String codigoPaisAdquisicion) {
		this.codigoPaisAdquisicion = codigoPaisAdquisicion;
	}
	public String getTasaSeguro() {
		return tasaSeguro;
	}
	public void setTasaSeguro(String tasaSeguro) {
		this.tasaSeguro = tasaSeguro;
	}
	public Integer getNumeroCuotasPagoDiferido() {
		return numeroCuotasPagoDiferido;
	}
	public void setNumeroCuotasPagoDiferido(Integer numeroCuotasPagoDiferido) {
		this.numeroCuotasPagoDiferido = numeroCuotasPagoDiferido;
	}
	public Date getFechaEmision() {
		return fechaEmision;
	}
	public void setFechaEmision(Date fechaEmision) {
		this.fechaEmision = fechaEmision;
	}
	public BigDecimal getMontoTotalFactura() {
		return montoTotalFactura;
	}
	public void setMontoTotalFactura(BigDecimal montoTotalFactura) {
		this.montoTotalFactura = montoTotalFactura;
	}
	public BigDecimal getMontoTotalCIF() {
		return montoTotalCIF;
	}
	public void setMontoTotalCIF(BigDecimal montoTotalCIF) {
		this.montoTotalCIF = montoTotalCIF;
	}
	public BigDecimal getValorLiquidoRetorno() {
		return valorLiquidoRetorno;
	}
	public void setValorLiquidoRetorno(BigDecimal valorLiquidoRetorno) {
		this.valorLiquidoRetorno = valorLiquidoRetorno;
	}
	public BigDecimal getValorFlete() {
		return valorFlete;
	}
	public void setValorFlete(BigDecimal valorFlete) {
		this.valorFlete = valorFlete;
	}
	
}
