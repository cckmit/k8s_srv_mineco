package com.egoveris.te.base.model;

import java.io.Serializable;

public class PaseDetalleResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1083227555414345196L;


	// podria ser un objeto
//	private String actuacion;
//	private String tipoActuacion;
	
	private Integer idPase;
	private Integer fojas;
	private String 	trataDescripcion;
	private String  extraEjecutiva;
	private String  cuerpoAnexo;
	private String 	observaciones;
	private Integer opAnio;
	private Integer opNro;
	private String compTipo;
	private Integer compAnio;
	private Double compNro;
	private Double compImporte;
	private String legajo;
	private String estado;
	private String fechaCreacion;
	private Integer idRemito;
	private String origen;
	private String destino;
	private Integer zAnio;
	private Integer zLegajo;
	private Integer zEstante;
	
	
	
	
	
	public Integer getzAnio() {
		return zAnio;
	}
	public void setzAnio(Integer zAnio) {
		this.zAnio = zAnio;
	}
	public Integer getzLegajo() {
		return zLegajo;
	}
	public void setzLegajo(Integer zLegajo) {
		this.zLegajo = zLegajo;
	}
	public Integer getzEstante() {
		return zEstante;
	}
	public void setzEstante(Integer zEstante) {
		this.zEstante = zEstante;
	}
	public String getFechaCreacion() {
		return fechaCreacion;
	}
	public void setFechaCreacion(String fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public Integer getIdRemito() {
		return idRemito;
	}
	public void setIdRemito(Integer idRemito) {
		this.idRemito = idRemito;
	}
	public String getOrigen() {
		return origen;
	}
	public void setOrigen(String sectorOrigen) {
		this.origen = sectorOrigen;
	}
	public String getDestino() {
		return destino;
	}
	public void setDestino(String sectorDestino) {
		this.destino = sectorDestino;
	}
	public Integer getFojas() {
		return fojas;
	}
	public void setFojas(Integer fojas) {
		this.fojas = fojas;
	}
	public String getTrataDescripcion() {
		return trataDescripcion;
	}
	public void setTrataDescripcion(String trataDescripcion) {
		this.trataDescripcion = trataDescripcion;
	}
	public String getExtraEjecutiva() {
		return extraEjecutiva;
	}
	public void setExtraEjecutiva(String extraEjecutiva) {
		this.extraEjecutiva = extraEjecutiva;
	}
	public String getCuerpoAnexo() {
		return cuerpoAnexo;
	}
	public void setCuerpoAnexo(String cuerpoAnexo) {
		this.cuerpoAnexo = cuerpoAnexo;
	}
	public String getObservaciones() {
		return observaciones;
	}
	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	public Integer getOpAnio() {
		return opAnio;
	}
	public void setOpAnio(Integer opAnio) {
		this.opAnio = opAnio;
	}
	public Integer getOpNro() {
		return opNro;
	}
	public void setOpNro(Integer opNro) {
		this.opNro = opNro;
	}
	public String getCompTipo() {
		return compTipo;
	}
	public void setCompTipo(String compTipo) {
		this.compTipo = compTipo;
	}
	public Integer getCompAnio() {
		return compAnio;
	}
	public void setCompAnio(Integer compAnio) {
		this.compAnio = compAnio;
	}
	public Double getCompNro() {
		return compNro;
	}
	public void setCompNro(Double compNro) {
		this.compNro = compNro;
	}
	public Double getCompImporte() {
		return compImporte;
	}
	public void setCompImporte(Double compImporte) {
		this.compImporte = compImporte;
	}
	public String getLegajo() {
		return legajo;
	}
	public void setLegajo(String legajo) {
		this.legajo = legajo;
	}
	public String getEstado() {
		return estado;
	}
	public void setEstado(String estado) {
		this.estado = estado;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Integer getIdPase() {
		return idPase;
	}
	public void setIdPase(Integer idPase) {
		this.idPase = idPase;
	}
	
	
	
	
	
}
