package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;
import java.util.Date;

public class FinancieroDTO extends AbstractCComplejoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	protected Long valorLiquidoRetorno;
	protected Long valorFlete;
	protected Long valorExFabrica;
	protected Long valorClausulaVenta;
	protected Long valorCIF;
	protected Long totalValorFOB;
	protected Long totalDiferido;
	protected Long tasaInteres;
	protected String tasaCambio;
	protected String seguroTeorico;
	protected String regimenImportacion;
	protected ParticipantesDTO proveedorOfabricante;
	protected String plazoPago;
	protected String paisAdquisicion;
	protected Long otrosGastosDeducibles;
	protected String origenDivisas;
	protected String obsBancoSNA;
	protected String numFactura;
	protected String numeroCuotasPagoDiferido;
	protected Long montoTotalAdvalorem;
	protected Long montoTotal;
	protected Long montoSeguro;
	protected String moneda;
	protected String modalidadVenta;
	protected String iva;
	protected String idPagoDiferido;
	protected Long gastosHastaFOB;
	protected String formaPagoGravamenes;
	protected String formaPago;
	protected String fleteTeorico;
	protected Date fechaPagoDiferido;
	protected Date fechaPago;
	protected Date fecFactura;
	protected Long facturaId;
	protected Boolean facturaComercialDefinitiva;
	protected Long descuento;
	protected Long declaracionId;
	protected DetalleCuotaDTO cuotas;
	protected Long cuotaContado;
	protected Long comisionesExterior;
	protected String codigoTotalAdvalorem;
	protected String codigoSeguro;
	protected String codigoFlete;
	protected String codigoBancoComercial;
	protected String clausulaVenta;
	protected String clausulaCompra;
	protected String aduanaDIPagoDif;

	public Long getValorLiquidoRetorno() {
		return valorLiquidoRetorno;
	}

	public void setValorLiquidoRetorno(Long valorLiquidoRetorno) {
		this.valorLiquidoRetorno = valorLiquidoRetorno;
	}

	public Long getValorFlete() {
		return valorFlete;
	}

	public void setValorFlete(Long valorFlete) {
		this.valorFlete = valorFlete;
	}

	public Long getValorExFabrica() {
		return valorExFabrica;
	}

	public void setValorExFabrica(Long valorExFabrica) {
		this.valorExFabrica = valorExFabrica;
	}

	public Long getValorClausulaVenta() {
		return valorClausulaVenta;
	}

	public void setValorClausulaVenta(Long valorClausulaVenta) {
		this.valorClausulaVenta = valorClausulaVenta;
	}

	public Long getValorCIF() {
		return valorCIF;
	}

	public void setValorCIF(Long valorCIF) {
		this.valorCIF = valorCIF;
	}

	public Long getTotalValorFOB() {
		return totalValorFOB;
	}

	public void setTotalValorFOB(Long totalValorFOB) {
		this.totalValorFOB = totalValorFOB;
	}

	public Long getTotalDiferido() {
		return totalDiferido;
	}

	public void setTotalDiferido(Long totalDiferido) {
		this.totalDiferido = totalDiferido;
	}

	public Long getTasaInteres() {
		return tasaInteres;
	}

	public void setTasaInteres(Long tasaInteres) {
		this.tasaInteres = tasaInteres;
	}

	public String getTasaCambio() {
		return tasaCambio;
	}

	public void setTasaCambio(String tasaCambio) {
		this.tasaCambio = tasaCambio;
	}

	public String getSeguroTeorico() {
		return seguroTeorico;
	}

	public void setSeguroTeorico(String seguroTeorico) {
		this.seguroTeorico = seguroTeorico;
	}

	public String getRegimenImportacion() {
		return regimenImportacion;
	}

	public void setRegimenImportacion(String regimenImportacion) {
		this.regimenImportacion = regimenImportacion;
	}

	public ParticipantesDTO getProveedorOfabricante() {
		return proveedorOfabricante;
	}

	public void setProveedorOfabricante(ParticipantesDTO proveedorOfabricante) {
		this.proveedorOfabricante = proveedorOfabricante;
	}

	public String getPlazoPago() {
		return plazoPago;
	}

	public void setPlazoPago(String plazoPago) {
		this.plazoPago = plazoPago;
	}

	public String getPaisAdquisicion() {
		return paisAdquisicion;
	}

	public void setPaisAdquisicion(String paisAdquisicion) {
		this.paisAdquisicion = paisAdquisicion;
	}

	public Long getOtrosGastosDeducibles() {
		return otrosGastosDeducibles;
	}

	public void setOtrosGastosDeducibles(Long otrosGastosDeducibles) {
		this.otrosGastosDeducibles = otrosGastosDeducibles;
	}

	public String getOrigenDivisas() {
		return origenDivisas;
	}

	public void setOrigenDivisas(String origenDivisas) {
		this.origenDivisas = origenDivisas;
	}

	public String getObsBancoSNA() {
		return obsBancoSNA;
	}

	public void setObsBancoSNA(String obsBancoSNA) {
		this.obsBancoSNA = obsBancoSNA;
	}

	public String getNumFactura() {
		return numFactura;
	}

	public void setNumFactura(String numFactura) {
		this.numFactura = numFactura;
	}

	public String getNumeroCuotasPagoDiferido() {
		return numeroCuotasPagoDiferido;
	}

	public void setNumeroCuotasPagoDiferido(String numeroCuotasPagoDiferido) {
		this.numeroCuotasPagoDiferido = numeroCuotasPagoDiferido;
	}

	public Long getMontoTotalAdvalorem() {
		return montoTotalAdvalorem;
	}

	public void setMontoTotalAdvalorem(Long montoTotalAdvalorem) {
		this.montoTotalAdvalorem = montoTotalAdvalorem;
	}

	public Long getMontoTotal() {
		return montoTotal;
	}

	public void setMontoTotal(Long montoTotal) {
		this.montoTotal = montoTotal;
	}

	public Long getMontoSeguro() {
		return montoSeguro;
	}

	public void setMontoSeguro(Long montoSeguro) {
		this.montoSeguro = montoSeguro;
	}

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public String getModalidadVenta() {
		return modalidadVenta;
	}

	public void setModalidadVenta(String modalidadVenta) {
		this.modalidadVenta = modalidadVenta;
	}

	public String getIva() {
		return iva;
	}

	public void setIva(String iva) {
		this.iva = iva;
	}

	public String getIdPagoDiferido() {
		return idPagoDiferido;
	}

	public void setIdPagoDiferido(String idPagoDiferido) {
		this.idPagoDiferido = idPagoDiferido;
	}

	public Long getGastosHastaFOB() {
		return gastosHastaFOB;
	}

	public void setGastosHastaFOB(Long gastosHastaFOB) {
		this.gastosHastaFOB = gastosHastaFOB;
	}

	public String getFormaPagoGravamenes() {
		return formaPagoGravamenes;
	}

	public void setFormaPagoGravamenes(String formaPagoGravamenes) {
		this.formaPagoGravamenes = formaPagoGravamenes;
	}

	public String getFormaPago() {
		return formaPago;
	}

	public void setFormaPago(String formaPago) {
		this.formaPago = formaPago;
	}

	public String getFleteTeorico() {
		return fleteTeorico;
	}

	public void setFleteTeorico(String fleteTeorico) {
		this.fleteTeorico = fleteTeorico;
	}

	public Date getFechaPagoDiferido() {
		return fechaPagoDiferido;
	}

	public void setFechaPagoDiferido(Date fechaPagoDiferido) {
		this.fechaPagoDiferido = fechaPagoDiferido;
	}

	public Date getFechaPago() {
		return fechaPago;
	}

	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}

	public Date getFecFactura() {
		return fecFactura;
	}

	public void setFecFactura(Date fecFactura) {
		this.fecFactura = fecFactura;
	}

	public Long getFacturaId() {
		return facturaId;
	}

	public void setFacturaId(Long facturaId) {
		this.facturaId = facturaId;
	}

	public Boolean getFacturaComercialDefinitiva() {
		return facturaComercialDefinitiva;
	}

	public void setFacturaComercialDefinitiva(Boolean facturaComercialDefinitiva) {
		this.facturaComercialDefinitiva = facturaComercialDefinitiva;
	}

	public Long getDescuento() {
		return descuento;
	}

	public void setDescuento(Long descuento) {
		this.descuento = descuento;
	}

	public Long getDeclaracionId() {
		return declaracionId;
	}

	public void setDeclaracionId(Long declaracionId) {
		this.declaracionId = declaracionId;
	}

	public DetalleCuotaDTO getCuotas() {
		return cuotas;
	}

	public void setCuotas(DetalleCuotaDTO cuotas) {
		this.cuotas = cuotas;
	}

	public Long getCuotaContado() {
		return cuotaContado;
	}

	public void setCuotaContado(Long cuotaContado) {
		this.cuotaContado = cuotaContado;
	}

	public Long getComisionesExterior() {
		return comisionesExterior;
	}

	public void setComisionesExterior(Long comisionesExterior) {
		this.comisionesExterior = comisionesExterior;
	}

	public String getCodigoTotalAdvalorem() {
		return codigoTotalAdvalorem;
	}

	public void setCodigoTotalAdvalorem(String codigoTotalAdvalorem) {
		this.codigoTotalAdvalorem = codigoTotalAdvalorem;
	}

	public String getCodigoSeguro() {
		return codigoSeguro;
	}

	public void setCodigoSeguro(String codigoSeguro) {
		this.codigoSeguro = codigoSeguro;
	}

	public String getCodigoFlete() {
		return codigoFlete;
	}

	public void setCodigoFlete(String codigoFlete) {
		this.codigoFlete = codigoFlete;
	}

	public String getCodigoBancoComercial() {
		return codigoBancoComercial;
	}

	public void setCodigoBancoComercial(String codigoBancoComercial) {
		this.codigoBancoComercial = codigoBancoComercial;
	}

	public String getClausulaVenta() {
		return clausulaVenta;
	}

	public void setClausulaVenta(String clausulaVenta) {
		this.clausulaVenta = clausulaVenta;
	}

	public String getClausulaCompra() {
		return clausulaCompra;
	}

	public void setClausulaCompra(String clausulaCompra) {
		this.clausulaCompra = clausulaCompra;
	}

	public String getAduanaDIPagoDif() {
		return aduanaDIPagoDif;
	}

	public void setAduanaDIPagoDif(String aduanaDIPagoDif) {
		this.aduanaDIPagoDif = aduanaDIPagoDif;
	}

}
