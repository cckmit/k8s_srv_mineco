package com.egoveris.te.base.model;


import com.egoveris.te.base.util.ConstEstadoActividad;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActividadInbox extends ActividadCommon implements Serializable {
	private static final Logger logger = LoggerFactory.getLogger(ActividadInbox.class);

	private static final long serialVersionUID = 911911701533232753L;

	private String nroExpediente;
	private String form;
	private String tipoActividadDecrip;
	private String idObjetivo;
	private String estado;
	private String usuarioCierre;
	private String trata;
	private String nroPedidoGEDO;
	private String mailCreador;
	private String usuarioActual;
	
	
	 
	/**
	 * @return the usuarioActual
	 */
	public String getUsuarioActual() {
		return usuarioActual;
	}

	/**
	 * @param usuarioActual the usuarioActual to set
	 */
	public void setUsuarioActual(String usuarioActual) {
		this.usuarioActual = usuarioActual;
	}

	/**
	 * @return the mailCreador
	 */
	public String getMailCreador() {
		return mailCreador;
	}

	/**
	 * @param mailCreador the mailCreador to set
	 */
	public void setMailCreador(String mailCreador) {
		this.mailCreador = mailCreador;
	}

	/**
	 * @return the nroPedidoGEDO
	 */
	public String getNroPedidoGEDO() {
		return nroPedidoGEDO;
	}

	/**
	 * @param nroPedidoGEDO the nroPedidoGEDO to set
	 */
	public void setNroPedidoGEDO(String nroPedidoGEDO) {
		this.nroPedidoGEDO = nroPedidoGEDO;
	}

	public String getTipoActividadDecrip() {
		return tipoActividadDecrip;
	}

	public void setTipoActividadDecrip(String tipoActividadDecrip) {
		this.tipoActividadDecrip = tipoActividadDecrip;
	}

	public String getForm() {
		return form;
	}

	public void setForm(String form) {
		this.form = form;
	}

	public String getNroExpediente() {
		return nroExpediente;
	}

	public void setNroExpediente(String nroExpediente) {
		this.nroExpediente = nroExpediente;
	}

	public String getIdObjetivo() {
		return idObjetivo;
	}

	public void setIdObjetivo(String idObjetivo) {
		this.idObjetivo = idObjetivo;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public boolean isModificable() {
		if (logger.isDebugEnabled()) {
			logger.debug("isModificable() - start");
		}

		boolean returnboolean = getFechaBaja() == null && !getEstado().equals(ConstEstadoActividad.ESTADO_PENDIENTE);
		if (logger.isDebugEnabled()) {
			logger.debug("isModificable() - end - return value={}", returnboolean);
		}
		return returnboolean;
	}

	public String getUsuarioCierre() {
		return usuarioCierre;
	}

	public void setUsuarioCierre(String usuarioCierre) {
		this.usuarioCierre = usuarioCierre;
	}

	public String getTrata() {
		return trata;
	}

	public void setTrata(String trata) {
		this.trata = trata;
	}
}
