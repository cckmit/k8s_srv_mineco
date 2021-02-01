package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;
import java.util.Date;

public class DateInicioDTO extends AbstractCComplejoDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	Date fecha;

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

}
