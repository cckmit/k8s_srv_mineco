package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;
import java.util.Date;

public class RegimenSuspDTO extends AbstractCComplejoDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 424925470424738422L;

	protected String codigoAduanaControl;
	protected String codigoComunaAlmacen;
	protected String plazoVigencia;
	protected String indicadorParcialidad;
	protected Long numeroRegimenSus;
	protected Date fechaRegimenSus;
	protected String aduanaRegimenSus;
	protected Long numeroHojasAnexas;
	protected String plazo;
	protected String glosa;
	protected String direccionAlmacenamiento;
	protected String numeroPasaporte;
	protected String numeroHojasInsumos;
	protected String totalInsumos;
	protected Long cantidadSecuencias;
	protected String numeroTITV;
	protected String tipoReingreso;
	protected String razonReingreso;
	protected String indicadorBoletaPoliza;
	protected Date fechaBoletaPoliza;
	protected String codigoAlmacenParticular;
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
