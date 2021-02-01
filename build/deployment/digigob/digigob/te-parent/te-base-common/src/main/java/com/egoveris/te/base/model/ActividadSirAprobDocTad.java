package com.egoveris.te.base.model;


import com.egoveris.te.base.util.ConstEstadoActividad;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActividadSirAprobDocTad extends ActividadCommon implements Serializable {
	private static final Logger logger = LoggerFactory.getLogger(ActividadSirAprobDocTad.class);

	private static final String RECHAZADO = "RECHAZADO";

	private static final String VINCULADO = "VINCULADO";

	private static final long serialVersionUID = 8453449629461266796L;

	private List<String> nrosDocumento;
	private String tipoDocTad;
	private String estado;

	public String getTipoDocTad() {
		return tipoDocTad;
	}

	public void setTipoDocTad(String tipoDocTad) {
		this.tipoDocTad = tipoDocTad;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getEstadoAlt() {
		if (logger.isDebugEnabled()) {
			logger.debug("getEstadoAlt() - start");
		}

		if (estado != null) {
			if (estado.equals(ConstEstadoActividad.ESTADO_APROBADA)) {
				if (logger.isDebugEnabled()) {
					logger.debug("getEstadoAlt() - end - return value={}", VINCULADO);
				}
				return VINCULADO;
			} else if (estado.equals(ConstEstadoActividad.ESTADO_RECHAZADA)) {
				if (logger.isDebugEnabled()) {
					logger.debug("getEstadoAlt() - end - return value={}", RECHAZADO);
				}
				return RECHAZADO;
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("getEstadoAlt() - end - return value={}", estado);
		}
		return estado;
	}
	
	public boolean isDocRechazado() {
		if (logger.isDebugEnabled()) {
			logger.debug("isDocRechazado() - start");
		}

		boolean returnboolean = estado != null && estado.equals(ConstEstadoActividad.ESTADO_RECHAZADA);
		if (logger.isDebugEnabled()) {
			logger.debug("isDocRechazado() - end - return value={}", returnboolean);
		}
		return returnboolean;
	}

	public void setNrosDocumento(List<String> nrosDocumento) {
		this.nrosDocumento = nrosDocumento;
	}

	public List<String> getNrosDocumento() {
		return nrosDocumento;
	}
}
