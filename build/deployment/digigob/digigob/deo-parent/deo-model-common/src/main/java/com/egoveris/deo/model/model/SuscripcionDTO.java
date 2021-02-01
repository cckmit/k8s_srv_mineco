package com.egoveris.deo.model.model;

import java.io.Serializable;
import java.util.Date;

public class SuscripcionDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String workflowId;
	private SistemaOrigenDTO sistemaOrigen;
	private String estado;
	private Integer reintento;
	private Date fechaCreacion;
	private String usuarioAlta;
	private SuscripcionPKDTO suscripcionPK;

	/**
	 * @return the pkDTO
	 */
	public SuscripcionPKDTO getSuscripcionPK() {
		return suscripcionPK;
	}

	/**
	 * @param pkDTO
	 *            the pkDTO to set
	 */
	public void setSuscripcionPK(SuscripcionPKDTO suscripcionPK) {
		this.suscripcionPK = suscripcionPK;
	}

	public String getWorkflowId() {
		return workflowId;
	}

	public void setWorkflowId(String workflowId) {
		this.workflowId = workflowId;
	}

	public SistemaOrigenDTO getSistemaOrigen() {
		return sistemaOrigen;
	}

	public void setSistemaOrigen(SistemaOrigenDTO sistemaOrigen) {
		this.sistemaOrigen = sistemaOrigen;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Integer getReintento() {
		return reintento;
	}

	public void setReintento(Integer reintento) {
		this.reintento = reintento;
	}

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public String getUsuarioAlta() {
		return usuarioAlta;
	}

	public void setUsuarioAlta(String usuarioAlta) {
		this.usuarioAlta = usuarioAlta;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String toString() {
		StringBuffer buffer = new StringBuffer();
		buffer.append("workflow id = ");
		buffer.append(workflowId);
		buffer.append(" sistema origen = ");
		buffer.append(sistemaOrigen);
		buffer.append(" estado = ");
		buffer.append(estado);
		buffer.append(" reintento = ");
		buffer.append(reintento);
		buffer.append(" fecha creacion = ");
		buffer.append(fechaCreacion);
		buffer.append(" usuario alta = ");
		buffer.append(usuarioAlta);
		return buffer.toString();
	}
}
