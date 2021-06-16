package com.egoveris.vucfront.model.model;

import java.util.Date;

public class ExpedienteFamiliaSolicitudDTO extends ExpedienteBaseDTO {

	private static final long serialVersionUID = 1788599785007032852L;

	private String detalleSolicitud;

	private Long step;

	public ExpedienteFamiliaSolicitudDTO() {
		setFechaCreacion(new Date());
	}

	public String getDetalleSolicitud() {
		return detalleSolicitud;
	}

	public void setDetalleSolicitud(String detalleSolicitud) {
		this.detalleSolicitud = detalleSolicitud;
	}

	public Long getStep() {
		return step;
	}

	public void setStep(Long step) {
		this.step = step;
	}

}