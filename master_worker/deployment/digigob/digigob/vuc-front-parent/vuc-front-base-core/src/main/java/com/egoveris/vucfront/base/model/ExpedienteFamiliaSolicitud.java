package com.egoveris.vucfront.base.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@PrimaryKeyJoinColumn(name = "ID_EXPEDIENTE")
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "TAD_EXPEDIENTE_FAM_SOLICITUD")
public class ExpedienteFamiliaSolicitud extends ExpedienteBase {

	@Column(name = "DETALLE_SOLICITUD")
	private String detalleSolicitud;

	@Column(name = "STEP")
	private Long step;

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