package com.egoveris.te.base.model;

import java.io.Serializable;
import java.util.List;



public class AuditoriaPaseResult implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7138915846690590220L;
	private String accion;
	private String codigoSectorInterno;
	private String fechaAuditoria;
	private String usuarioAuditoria;
	private String codigoPermanencia;
	private boolean interna;
	private String repActuacion;
	private String repUsuario;
	private String origen;
	private String destino;
	private String codigoReparticionOrigen;
	private String codigoReparticionDestino;
	private Integer fojas;
	private List<AuditoriaPaseDetalleResult> detalles;
	
	public String getCodigoReparticionOrigen() {
		return codigoReparticionOrigen;
	}
	public void setCodigoReparticionOrigen(String codigoReparticionOrigen) {
		this.codigoReparticionOrigen = codigoReparticionOrigen;
	}
	public String getCodigoReparticionDestino() {
		return codigoReparticionDestino;
	}
	public void setCodigoReparticionDestino(String codigoReparticionDestino) {
		this.codigoReparticionDestino = codigoReparticionDestino;
	}
	

	public String getAccion() {
		return accion;
	}
	public void setAccion(String accion) {
		this.accion = accion;
	}
	public String getCodigoSectorInterno() {
		return codigoSectorInterno;
	}
	public void setCodigoSectorInterno(String codigoSectorInterno) {
		this.codigoSectorInterno = codigoSectorInterno;
	}
	public String getFechaAuditoria() {
		return fechaAuditoria;
	}
	public void setFechaAuditoria(String fechaAuditoria) {
		this.fechaAuditoria = fechaAuditoria;
	}
	public String getUsuarioAuditoria() {
		return usuarioAuditoria;
	}
	public void setUsuarioAuditoria(String usuarioAuditoria) {
		this.usuarioAuditoria = usuarioAuditoria;
	}
	public String getCodigoPermanencia() {
		return codigoPermanencia;
	}
	public void setCodigoPermanencia(String codigoPermanencia) {
		this.codigoPermanencia = codigoPermanencia;
	}
	public boolean isInterna() {
		return interna;
	}
	public void setInterna(boolean interna) {
		this.interna = interna;
	}
	public String getRepActuacion() {
		return repActuacion;
	}
	public void setRepActuacion(String repActuacion) {
		this.repActuacion = repActuacion;
	}
	public String getRepUsuario() {
		return repUsuario;
	}
	public void setRepUsuario(String repUsuario) {
		this.repUsuario = repUsuario;
	}
	public String getOrigen() {
		return origen;
	}
	public void setOrigen(String origen) {
		this.origen = origen;
	}
	public String getDestino() {
		return destino;
	}
	public void setDestino(String destino) {
		this.destino = destino;
	}
	public Integer getFojas() {
		return fojas;
	}
	public void setFojas(Integer fojas) {
		this.fojas = fojas;
	}
	public List<AuditoriaPaseDetalleResult> getDetalles() {
		return detalles;
	}
	public void setDetalles(List<AuditoriaPaseDetalleResult> detalles) {
		this.detalles = detalles;
	}	

}
