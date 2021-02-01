package com.egoveris.ccomplejos.base.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CC_REGIMEN_SUSP")
public class RegimenSusp extends AbstractCComplejoJPA {
	
	@Column(name = "COD_ADUANA_CTRL")
	protected String codigoAduanaControl;
	
	@Column(name = "COD_COMUNA_ALMACEN")
	protected String codigoComunaAlmacen;
	
	@Column(name = "PLAZO_VIGENCIA")
	protected String plazoVigencia;
	
	@Column(name = "INDICADOR_PARCIALIDAD")
	protected String indicadorParcialidad;
	
	@Column(name = "NRO_REGIMEN_SUS")
	protected Long numeroRegimenSus;
	
	@Column(name = "FECHA_REGIMEN_SUSP")
	protected Date fechaRegimenSus;
	
	@Column(name = "ADUANA_REGIMEN_SUSP")
	protected String aduanaRegimenSus;
	
	@Column(name = "NRO_HOJAS_ANEXAS")
	protected Long numeroHojasAnexas;
	
	@Column(name = "PLAZO")
	protected String plazo;
	
	@Column(name = "GLOSA")
	protected String glosa;
	
	@Column(name = "DIR_ALMACENAMIENTO")
	protected String direccionAlmacenamiento;
	
	@Column(name = "NRO_PASAPORTE")
	protected String numeroPasaporte;
	
	@Column(name = "NRO_HOJAS_INSUMO")
	protected String numeroHojasInsumos;
	
	@Column(name = "TOTAL_INSUMOS")
	protected String totalInsumos;
	
	@Column(name = "CANT_SECUENCIA")
	protected Long cantidadSecuencias;
	
	@Column(name = "NRO_TITV")
	protected String numeroTITV;
	
	@Column(name = "TIPO_REINGRESO")
	protected String tipoReingreso;
	
	@Column(name = "RAZON_REINGRESO")
	protected String razonReingreso;
	
	@Column(name = "INDICADOR_BOLETA_POLIZA")
	protected String indicadorBoletaPoliza;
	
	@Column(name = "FECHA_BOLETA_POLIZA")
	protected Date fechaBoletaPoliza;
	
	@Column(name = "COD_ALMACEN_PARTICULAR")
	protected String codigoAlmacenParticular;
	
	@Column(name = "NRO_EMISION_BOLETA")
	protected String numeroEmisionBoleta;

	
	public String getCodigoAduanaControl() {
		return codigoAduanaControl;
	}
	public void setCodigoAduanaControl(String codigoAduanaControl) {
		this.codigoAduanaControl = codigoAduanaControl;
	}
	public String getCodigoComunaAlmacen() {
		return codigoComunaAlmacen;
	}
	public void setCodigoComunaAlmacen(String codigoComunaAlmacen) {
		this.codigoComunaAlmacen = codigoComunaAlmacen;
	}
	public String getPlazoVigencia() {
		return plazoVigencia;
	}
	public void setPlazoVigencia(String plazoVigencia) {
		this.plazoVigencia = plazoVigencia;
	}
	public String getIndicadorParcialidad() {
		return indicadorParcialidad;
	}
	public void setIndicadorParcialidad(String indicadorParcialidad) {
		this.indicadorParcialidad = indicadorParcialidad;
	}
	public Long getNumeroRegimenSus() {
		return numeroRegimenSus;
	}
	public void setNumeroRegimenSus(Long numeroRegimenSus) {
		this.numeroRegimenSus = numeroRegimenSus;
	}
	public Date getFechaRegimenSus() {
		return fechaRegimenSus;
	}
	public void setFechaRegimenSus(Date fechaRegimenSus) {
		this.fechaRegimenSus = fechaRegimenSus;
	}
	public String getAduanaRegimenSus() {
		return aduanaRegimenSus;
	}
	public void setAduanaRegimenSus(String aduanaRegimenSus) {
		this.aduanaRegimenSus = aduanaRegimenSus;
	}
	public Long getNumeroHojasAnexas() {
		return numeroHojasAnexas;
	}
	public void setNumeroHojasAnexas(Long numeroHojasAnexas) {
		this.numeroHojasAnexas = numeroHojasAnexas;
	}
	public String getPlazo() {
		return plazo;
	}
	public void setPlazo(String plazo) {
		this.plazo = plazo;
	}
	public String getGlosa() {
		return glosa;
	}
	public void setGlosa(String glosa) {
		this.glosa = glosa;
	}
	public String getDireccionAlmacenamiento() {
		return direccionAlmacenamiento;
	}
	public void setDireccionAlmacenamiento(String direccionAlmacenamiento) {
		this.direccionAlmacenamiento = direccionAlmacenamiento;
	}
	public String getNumeroPasaporte() {
		return numeroPasaporte;
	}
	public void setNumeroPasaporte(String numeroPasaporte) {
		this.numeroPasaporte = numeroPasaporte;
	}
	public String getNumeroHojasInsumos() {
		return numeroHojasInsumos;
	}
	public void setNumeroHojasInsumos(String numeroHojasInsumos) {
		this.numeroHojasInsumos = numeroHojasInsumos;
	}
	public String getTotalInsumos() {
		return totalInsumos;
	}
	public void setTotalInsumos(String totalInsumos) {
		this.totalInsumos = totalInsumos;
	}
	public Long getCantidadSecuencias() {
		return cantidadSecuencias;
	}
	public void setCantidadSecuencias(Long cantidadSecuencias) {
		this.cantidadSecuencias = cantidadSecuencias;
	}
	public String getNumeroTITV() {
		return numeroTITV;
	}
	public void setNumeroTITV(String numeroTITV) {
		this.numeroTITV = numeroTITV;
	}
	public String getTipoReingreso() {
		return tipoReingreso;
	}
	public void setTipoReingreso(String tipoReingreso) {
		this.tipoReingreso = tipoReingreso;
	}
	public String getRazonReingreso() {
		return razonReingreso;
	}
	public void setRazonReingreso(String razonReingreso) {
		this.razonReingreso = razonReingreso;
	}
	public String getIndicadorBoletaPoliza() {
		return indicadorBoletaPoliza;
	}
	public void setIndicadorBoletaPoliza(String indicadorBoletaPoliza) {
		this.indicadorBoletaPoliza = indicadorBoletaPoliza;
	}
	public Date getFechaBoletaPoliza() {
		return fechaBoletaPoliza;
	}
	public void setFechaBoletaPoliza(Date fechaBoletaPoliza) {
		this.fechaBoletaPoliza = fechaBoletaPoliza;
	}
	public String getCodigoAlmacenParticular() {
		return codigoAlmacenParticular;
	}
	public void setCodigoAlmacenParticular(String codigoAlmacenParticular) {
		this.codigoAlmacenParticular = codigoAlmacenParticular;
	}
	public String getNumeroEmisionBoleta() {
		return numeroEmisionBoleta;
	}
	public void setNumeroEmisionBoleta(String numeroEmisionBoleta) {
		this.numeroEmisionBoleta = numeroEmisionBoleta;
	}
	

}
