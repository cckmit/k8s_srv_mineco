package com.egoveris.te.model.model;

import com.egoveris.te.model.model.DTODatosCaratula;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlType;

@XmlType(name="ObtenerCaratulaPorCodigoEEResponse", namespace="http://com.egoveris.te.external.service.client.services.external/")
public class ObtenerCaratulaPorCodigoEEResponse implements Serializable {

	private static final long serialVersionUID = -5939279406356891106L;
	
	private DTODatosCaratula dtoDatosCaratula;

	public DTODatosCaratula getDtoDatosCaratula() {
		return dtoDatosCaratula;
	}

	public void setDtoDatosCaratula(DTODatosCaratula dtoDatosCaratula) {
		this.dtoDatosCaratula = dtoDatosCaratula;
	}
	
}
