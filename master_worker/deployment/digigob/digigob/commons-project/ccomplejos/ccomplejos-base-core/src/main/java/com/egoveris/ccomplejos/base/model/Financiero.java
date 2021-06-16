package com.egoveris.ccomplejos.base.model;
 
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "CC_FINANCIERO")
public class Financiero extends AbstractCComplejoJPA {

	@Column(name = "VALOR_LIQUIDO_RETORNO")
	protected Long valorLiquidoRetorno;
	@Column(name = "VALOR_FLETE")
	protected Long valorFlete;
	@Column(name = "VALOR_EXFABRICA")
	protected Long valorExFabrica;
	@Column(name = "VALOR_CLAUSULA_VENTA")
	protected Long valorClausulaVenta;
	@Column(name = "VALOR_CIF")
	protected Long valorCIF;
	@Column(name = "TOTAL_VALOR_FOB")
	protected Long totalValorFOB;
	@Column(name = "TOTAL_DIFERIDO")
	protected Long totalDiferido;
	@Column(name = "TASA_INTERES")
	protected Long tasaInteres;
	@Column(name = "TASA_CAMBIO")
	protected String tasaCambio;
	@Column(name = "SEGURO_TEORICO")
	protected String seguroTeorico;
	@Column(name = "REGIMEN_IMPORTACION")
	protected String regimenImportacion;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="PARTICIPANTE_ID")
	protected Participantes proveedorOfabricante;
	
	@Column(name = "PLAZO_PAGO")
	protected String plazoPago;
	@Column(name = "PAIS_ADQUISICION")
	protected String paisAdquisicion;
	@Column(name = "OTROS_GASTOS_DEDUCIBLES")
	protected Long otrosGastosDeducibles;
	@Column(name = "ORIGEN_DIVISAS")
	protected String origenDivisas;
	@Column(name = "OBS_BANCOS_NA")
	protected String obsBancoSNA;
	@Column(name = "NUM_FACTURA")
	protected String numFactura;
	@Column(name = "NUMERO_CUOTAS_PAGO_DIFERIDO")
	protected String numeroCuotasPagoDiferido;
	@Column(name = "MONTO_TOTAL_ADVALOREM")
	protected Long montoTotalAdvalorem;
	@Column(name = "MONTO_TOTAL")
	protected Long montoTotal;
	@Column(name = "MONTO_SEGURO")
	protected Long montoSeguro;
	@Column(name = "MONEDA")
	protected String moneda;
	@Column(name = "MODALIDAD_VENTA")
	protected String modalidadVenta;
	@Column(name = "IVA")
	protected String iva;
	@Column(name = "ID_PAGO_DIFERIDO")
	protected String idPagoDiferido;
	@Column(name = "GASTOS_HASTA_FOB")
	protected Long gastosHastaFOB;
	@Column(name = "FORMA_PAGO_GRAVAMENES")
	protected String formaPagoGravamenes;
	@Column(name = "FORMA_PAGO")
	protected String formaPago;
	@Column(name = "FLETE_TEORICO")
	protected String fleteTeorico;
	@Column(name = "FECHA_PAGO_DIFERIDO")
	protected Date fechaPagoDiferido;
	@Column(name = "FECHA_PAGO")
	protected Date fechaPago;
	@Column(name = "FEC_FACTURA")
	protected Date fecFactura;
	@Column(name = "FACTURA_ID")
	protected Long facturaId;
	@Column(name = "FACTURA_COMERCIAL_DEFINITIVA")
	protected Boolean facturaComercialDefinitiva;
	@Column(name = "DESCUENTO")
	protected Long descuento;
	@Column(name = "DECLARACION_ID")
	protected Long declaracionId;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="CODIGO_UNO")
	protected DetalleCuota cuotas;
	
	@Column(name = "CUOTA_CONTADO")
	protected Long cuotaContado;
	@Column(name = "COMISIONES_EXTERIOR")
	protected Long comisionesExterior;
	@Column(name = "CODIGO_TOTAL_ADVALOREM")
	protected String codigoTotalAdvalorem;
	@Column(name = "CODIGO_SEGURO")
	protected String codigoSeguro;
	@Column(name = "CODIGO_FLETE")
	protected String codigoFlete;
	@Column(name = "CODIGO_BANCO_COMERCIAL")
	protected String codigoBancoComercial;
	@Column(name = "CLAUSULA_VENTA")
	protected String clausulaVenta;
	@Column(name = "CLAUSULA_COMPRA")
	protected String clausulaCompra;
	@Column(name = "ADUANA_DIPAGODIF")
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

	public Participantes getProveedorOfabricante() {
		return proveedorOfabricante;
	}

	public void setProveedorOfabricante(Participantes proveedorOfabricante) {
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

	public DetalleCuota getCuotas() {
		return cuotas;
	}

	public void setCuotas(DetalleCuota cuotas) {
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
