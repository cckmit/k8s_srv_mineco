package com.egoveris.ccomplejos.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CC_HOJA_ANEXA")
public class HojaAnexa extends AbstractCComplejoJPA {

	@Column(name = "NRO_SECUENCIA")
	protected Long numeroSecuencia;
	
	@Column(name = "NRO_DAPEX")
	protected Long numeroDAPEX;
	
	@Column(name = "FECHA_DAPEX")
	protected Date fechaDAPEX;
	
	@Column(name = "ADUANA_TRAMITACION")
	protected String aduanaTramitacion;
	
	@Column(name = "NRO_ITEM")
	protected Long numeroItem;
	
	@Column(name = "NRO_INSUMO")
	protected Long numeroInsumo;
	
	@Column(name = "NOMBRE_INSUMO")
	protected String nombreInsumo;
	
	@Column(name = "COD_UNIDAD_MEDIDA")
	protected String codigoUnidadMedida;
	
	@Column(name = "NOMBRE_MERCANCIA")
	protected String nombreMercancia;
	
	@Column(name = "CANTIDAD")
	protected Long cantidad;
	
	@Column(name = "UNIDAD_MEDIDA_CANT")
	protected String unidadMedidaCantidad;
	
	@Column(name = "FACTOR_CONSUMO")
	protected String factorConsumo;
	
	@Column(name = "INSUMOS_UTILIZADOS")
	protected String insumosUtilizados;
	
	@Column(name = "NRO_HOJA")
	protected Long numeroHoja;
	
	@Column(name = "TOTAL_INSUMOS_HOJA")
	protected Long totalInsumoHoja;
	
	@Column(name = "TOTAL_INSUMOS_HOJAS_ANT")
	protected Long totalInsumosHojasAnteriores;
	
	@Column(name = "TOTAL_FINAL_INSUMOS")
	protected Long totalFinalInsumos;
	
	@Column(name = "FECHA_CONTROL_VB")
	protected Date fechaControlVB;
	
	@Column(name = "FECHA_FIRMA_DESPACHADOR")
	protected Date fechaFirmaDespachador;
	
	public Long getNumeroSecuencia() {
		return numeroSecuencia;
	}
	public void setNumeroSecuencia(Long numeroSecuencia) {
		this.numeroSecuencia = numeroSecuencia;
	}
	public Long getNumeroDAPEX() {
		return numeroDAPEX;
	}
	public void setNumeroDAPEX(Long numeroDAPEX) {
		this.numeroDAPEX = numeroDAPEX;
	}
	public Date getFechaDAPEX() {
		return fechaDAPEX;
	}
	public void setFechaDAPEX(Date fechaDAPEX) {
		this.fechaDAPEX = fechaDAPEX;
	}
	public String getAduanaTramitacion() {
		return aduanaTramitacion;
	}
	public void setAduanaTramitacion(String aduanaTramitacion) {
		this.aduanaTramitacion = aduanaTramitacion;
	}
	public Long getNumeroItem() {
		return numeroItem;
	}
	public void setNumeroItem(Long numeroItem) {
		this.numeroItem = numeroItem;
	}
	public Long getNumeroInsumo() {
		return numeroInsumo;
	}
	public void setNumeroInsumo(Long numeroInsumo) {
		this.numeroInsumo = numeroInsumo;
	}
	public String getNombreInsumo() {
		return nombreInsumo;
	}
	public void setNombreInsumo(String nombreInsumo) {
		this.nombreInsumo = nombreInsumo;
	}
	public String getCodigoUnidadMedida() {
		return codigoUnidadMedida;
	}
	public void setCodigoUnidadMedida(String codigoUnidadMedida) {
		this.codigoUnidadMedida = codigoUnidadMedida;
	}
	public String getNombreMercancia() {
		return nombreMercancia;
	}
	public void setNombreMercancia(String nombreMercancia) {
		this.nombreMercancia = nombreMercancia;
	}
	public Long getCantidad() {
		return cantidad;
	}
	public void setCantidad(Long cantidad) {
		this.cantidad = cantidad;
	}
	public String getUnidadMedidaCantidad() {
		return unidadMedidaCantidad;
	}
	public void setUnidadMedidaCantidad(String unidadMedidaCantidad) {
		this.unidadMedidaCantidad = unidadMedidaCantidad;
	}
	public String getFactorConsumo() {
		return factorConsumo;
	}
	public void setFactorConsumo(String factorConsumo) {
		this.factorConsumo = factorConsumo;
	}
	public String getInsumosUtilizados() {
		return insumosUtilizados;
	}
	public void setInsumosUtilizados(String insumosUtilizados) {
		this.insumosUtilizados = insumosUtilizados;
	}
	public Long getNumeroHoja() {
		return numeroHoja;
	}
	public void setNumeroHoja(Long numeroHoja) {
		this.numeroHoja = numeroHoja;
	}
	public Long getTotalInsumoHoja() {
		return totalInsumoHoja;
	}
	public void setTotalInsumoHoja(Long totalInsumoHoja) {
		this.totalInsumoHoja = totalInsumoHoja;
	}
	public Long getTotalInsumosHojasAnteriores() {
		return totalInsumosHojasAnteriores;
	}
	public void setTotalInsumosHojasAnteriores(Long totalInsumosHojasAnteriores) {
		this.totalInsumosHojasAnteriores = totalInsumosHojasAnteriores;
	}
	public Long getTotalFinalInsumos() {
		return totalFinalInsumos;
	}
	public void setTotalFinalInsumos(Long totalFinalInsumos) {
		this.totalFinalInsumos = totalFinalInsumos;
	}
	public Date getFechaControlVB() {
		return fechaControlVB;
	}
	public void setFechaControlVB(Date fechaControlVB) {
		this.fechaControlVB = fechaControlVB;
	}
	public Date getFechaFirmaDespachador() {
		return fechaFirmaDespachador;
	}
	public void setFechaFirmaDespachador(Date fechaFirmaDespachador) {
		this.fechaFirmaDespachador = fechaFirmaDespachador;
	}


}
