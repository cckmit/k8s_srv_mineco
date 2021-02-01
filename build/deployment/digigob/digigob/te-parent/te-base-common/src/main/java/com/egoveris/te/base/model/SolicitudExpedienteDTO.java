package com.egoveris.te.base.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Solicitud de alta de expediente.
 * 
 * @author rgalloci
 *
 */

public class SolicitudExpedienteDTO implements Serializable{
	
	private static final long serialVersionUID = -7109795626396712896L;
	private Long id;
	private String motivo;
	private String motivoExterno;
	private String motivoDeRechazo;
	private SolicitanteDTO solicitante;
	private Boolean esSolicitudInterna=true;
	private String usuarioCreacion;
	private Date fechaCreacion;
	private Long idTrataSugerida;
	private Long idOperacion;
	
	public SolicitudExpedienteDTO(){
		
	}
	public boolean isEsSolicitudInterna() {
		return esSolicitudInterna;
	}

	public void setEsSolicitudInterna(boolean esSolicitudInterna) {
		this.esSolicitudInterna = esSolicitudInterna;
	}
	
	public String getMotivo() {
		return motivo;
	}
	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}
	
	public String getMotivoExterno() {
		return motivoExterno;
	}
	public void setMotivoExterno(String motivoExterno) {
		this.motivoExterno = motivoExterno;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getId() {
		return id;
	}

	public SolicitanteDTO getSolicitante() {
		return solicitante;
	}

	public void setSolicitante(SolicitanteDTO solicitante) {
		this.solicitante = solicitante;
	}

	public String getMotivoDeRechazo() {
		return motivoDeRechazo;
	}

	public void setMotivoDeRechazo(String motivoDeRechazo) {
		this.motivoDeRechazo = motivoDeRechazo;
	}

	public String getUsuarioCreacion() {
		return usuarioCreacion;
	}

	public void setUsuarioCreacion(String usuarioCreacion) {
		this.usuarioCreacion = usuarioCreacion;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((esSolicitudInterna == null) ? 0 : esSolicitudInterna
						.hashCode());
		result = prime * result
				+ ((fechaCreacion == null) ? 0 : fechaCreacion.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((motivo == null) ? 0 : motivo.hashCode());
		result = prime * result
				+ ((motivoDeRechazo == null) ? 0 : motivoDeRechazo.hashCode());
		result = prime * result
				+ ((usuarioCreacion == null) ? 0 : usuarioCreacion.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SolicitudExpedienteDTO other = (SolicitudExpedienteDTO) obj;
		if (esSolicitudInterna == null) {
			if (other.esSolicitudInterna != null)
				return false;
		} else if (!esSolicitudInterna.equals(other.esSolicitudInterna))
			return false;
		if (fechaCreacion == null) {
			if (other.fechaCreacion != null)
				return false;
		} else if (!fechaCreacion.equals(other.fechaCreacion))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (motivo == null) {
			if (other.motivo != null)
				return false;
		} else if (!motivo.equals(other.motivo))
			return false;
		if (motivoDeRechazo == null) {
			if (other.motivoDeRechazo != null)
				return false;
		} else if (!motivoDeRechazo.equals(other.motivoDeRechazo))
			return false;
		if (usuarioCreacion == null) {
			if (other.usuarioCreacion != null)
				return false;
		} else if (!usuarioCreacion.equals(other.usuarioCreacion))
			return false;
		return true;
	}
	public Long getIdTrataSugerida() {
		return idTrataSugerida;
	}
	public void setIdTrataSugerida(Long idTrataSugerida) {
		this.idTrataSugerida = idTrataSugerida;
	}
	public Long getIdOperacion() {
		return idOperacion;
	}
	public void setIdOperacion(Long idOperacion) {
		this.idOperacion = idOperacion;
	}
	
	
	
	
}
