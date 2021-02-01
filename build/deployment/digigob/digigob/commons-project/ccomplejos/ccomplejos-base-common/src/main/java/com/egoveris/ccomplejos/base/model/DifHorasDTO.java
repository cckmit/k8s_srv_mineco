package com.egoveris.ccomplejos.base.model;

import java.io.Serializable;
import java.util.Date;

public class DifHorasDTO extends AbstractCComplejoDTO implements Serializable {

	private static final long serialVersionUID = -3706117431377625725L;

	Date horaInicio;
	
	Date horaFin;
	
	String diferencia;
	

	public Date getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(Date horaInicio) {
		this.horaInicio = horaInicio;
	}

	public Date getHoraFin() {
		return horaFin;
	}

	public void setHoraFin(Date horaFin) {
		this.horaFin = horaFin;
	}

	public String getDiferencia() {
		return diferencia;
	}

	public void setDiferencia(String diferencia) {
		this.diferencia = diferencia;
	}

}
