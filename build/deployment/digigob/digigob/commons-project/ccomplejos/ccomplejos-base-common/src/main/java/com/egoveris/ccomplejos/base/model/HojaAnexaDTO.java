package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;
import java.util.Date;

public class HojaAnexaDTO extends AbstractCComplejoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -155588669432734633L;
	
	
	protected Long numeroSecuencia;
	protected Long numeroDAPEX;
	protected Date fechaDAPEX;
	protected String aduanaTramitacion;
	protected Long numeroItem;
	protected Long numeroInsumo;
	protected String nombreInsumo;
	protected String codigoUnidadMedida;
	protected String nombreMercancia;
	protected Long cantidad;
	protected String unidadMedidaCantidad;
	protected String factorConsumo;
	protected String insumosUtilizados;
	protected Long numeroHoja;
	protected Long totalInsumoHoja;
	protected Long totalInsumosHojasAnteriores;
	protected Long totalFinalInsumos;
	protected Date fechaControlVB;
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
