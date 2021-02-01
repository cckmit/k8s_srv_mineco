package com.egoveris.te.ws.model;

import java.io.Serializable;
import java.util.Date;

public class DocumentosVinculadosResponse implements Serializable{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8397660493886013412L;
	/**
	 * 
	 */
	
	private String numeroSadeDocumento;
	private String numeroEspecialDocumento;
	private Date fechavinculacionDefinitiva;
	private String usuarioAsociacion;
	private String tipodeDocumento;
	private String referencia;
	private Date fechaCreacion;
	private String usuarioGenerador;
	
	
	public String getNumeroSadeDocumento() {
		return numeroSadeDocumento;
	}
	public String getNumeroEspecialDocumento() {
		return numeroEspecialDocumento;
	}
	public Date getFechavinculacionDefinitiva() {
		return fechavinculacionDefinitiva;
	}
	public String getUsuarioAsociacion() {
		return usuarioAsociacion;
	}
	public String getTipodeDocumento() {
		return tipodeDocumento;
	}
	public String getReferencia() {
		return referencia;
	}
	public Date getFechaCreacion() {
		return fechaCreacion;
	}
	public void setNumeroSadeDocumento(String numeroSadeDocumento) {
		this.numeroSadeDocumento = numeroSadeDocumento;
	}
	public void setNumeroEspecialDocumento(String numeroEspecialDocumento) {
		this.numeroEspecialDocumento = numeroEspecialDocumento;
	}
	public void setFechavinculacionDefinitiva(Date fechavinculacionDefinitiva) {
		this.fechavinculacionDefinitiva = fechavinculacionDefinitiva;
	}
	public void setUsuarioAsociacion(String usuarioAsociacion) {
		this.usuarioAsociacion = usuarioAsociacion;
	}
	public void setTipodeDocumento(String tipodeDocumento) {
		this.tipodeDocumento = tipodeDocumento;
	}
	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}
	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	public void setUsuarioGenerador(String usuarioGenerador) {
		this.usuarioGenerador = usuarioGenerador;
	}
	public String getUsuarioGenerador() {
		return usuarioGenerador;
	}

	
	
}
