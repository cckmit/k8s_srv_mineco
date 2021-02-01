package com.egoveris.deo.model.model;

import java.io.Serializable;
import java.util.Date;

public class AvisoDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2160344301518309129L;

	private Integer id;
	
	private String usuarioReceptor;
	
	private String usuarioAccion;
	
	private String redirigidoPor;
	
	private String motivo;
	
	private String motivoRechazo;
	
	private Date fechaAccion;
	
	private Date fechaEnvio;
	
	private DocumentoDTO documento;

	private String referenciaDocumento;
	
	private String numeroSadePapel;
	
	private String numeroEspecial;
	
	public String getUsuarioReceptor() {
		return usuarioReceptor;
	}

	public void setUsuarioReceptor(String usuarioReceptor) {
		this.usuarioReceptor = usuarioReceptor;
	}

	public String getRedirigidoPor() {
		return redirigidoPor;
	}

	public void setRedirigidoPor(String redirigidoPor) {
		this.redirigidoPor = redirigidoPor;
	}

	public Date getFechaEnvio() {
		return fechaEnvio;
	}

	public void setFechaEnvio(Date fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
	}

	public DocumentoDTO getDocumento() {
		return documento;
	}

	public void setDocumento(DocumentoDTO documento) {
		this.documento = documento;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsuarioAccion() {
		return usuarioAccion;
	}

	public void setUsuarioAccion(String usuarioAccion) {
		this.usuarioAccion = usuarioAccion;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public String getMotivoRechazo() {
		return motivoRechazo;
	}

	public void setMotivoRechazo(String motivoRechazo) {
		this.motivoRechazo = motivoRechazo;
	}

	public Date getFechaAccion() {
		return fechaAccion;
	}

	public void setFechaAccion(Date fechaAccion) {
		this.fechaAccion = fechaAccion;
	}

	public String getReferenciaDocumento() {
		return referenciaDocumento;
	}

	public void setReferenciaDocumento(String referenciaDocumento) {
		this.referenciaDocumento = referenciaDocumento;
	}

	public String getNumeroSadePapel() {
		return numeroSadePapel;
	}

	public void setNumeroSadePapel(String numeroSadePapel) {
		this.numeroSadePapel = numeroSadePapel;
	}

	public String getNumeroEspecial() {
		return numeroEspecial;
	}

	public void setNumeroEspecial(String numeroEspecial) {
		this.numeroEspecial = numeroEspecial;
	}
	
}
