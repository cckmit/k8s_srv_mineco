package com.egoveris.ccomplejos.base.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CC_VISTA_FINANCIERODA")
public class VistaFinancieroDA extends AbstractCComplejoJPA{
	
	@Column(name = "DESCUENTO")
	protected String descuento;
	@Column(name = "CLAUSULA_VENTA_TRANSACCION")
	protected String clausulaVentaTransaccion;
	@Column(name = "COMISIONES_AL_EXTERIOR")
	protected String comisionesAlExterior;
	@Column(name = "OTROS_GASTOS")
	protected String otrosGastos;
	@Column(name = "MODALIDAD_VENTA_TRANSACCION")
	protected String modalidadVentaTransaccion;
	@Column(name = "NUMERO_FACTURA")
	protected String numeroFactura;
	@Column(name = "FORMA_PAGO_TRANSACCION")
	protected String formaPagoTransaccion;
	@Column(name = "TIPO_MONEDA_TRANSACCION")
	protected String tipoMonedaTransaccion;
	@Column(name = "INDICA_FACTURA_COMERCIAL_DEFINITIVA")
	protected String indicaFacturaComercialDefinitiva;
	@Column(name = "CODIGO_PAIS_ADQUISICION")
	protected String codigoPaisAdquisicion;
	@Column(name = "TASA_SEGURO")
	protected String tasaSeguro;
	@Column(name = "NUMEROCUOTAS_PAGO_DIFERIDO")
	protected Integer numeroCuotasPagoDiferido;
	@Column(name = "FECHA_EMISION")
	protected Date fechaEmision;
	@Column(name = "MONTO_TOTAL_FACTURA")
	protected BigDecimal montoTotalFactura;
	@Column(name = "MONTO_TOTAL_CIF")
	protected BigDecimal montoTotalCIF;
	@Column(name = "VALOR_LIQUIDO_RETORNO")
	protected BigDecimal valorLiquidoRetorno;
	@Column(name = "VALOR_FLETE")
	protected BigDecimal valorFlete;
	
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
