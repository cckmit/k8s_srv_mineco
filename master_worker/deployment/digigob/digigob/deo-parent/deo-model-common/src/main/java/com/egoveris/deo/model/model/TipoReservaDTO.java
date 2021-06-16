package com.egoveris.deo.model.model;

import java.io.Serializable;

public class TipoReservaDTO implements Serializable {

	private static final long serialVersionUID = 3800490290385308491L;
	private Integer id;
	private String reserva;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getReserva() {
		return reserva;
	}
	public void setReserva(String reserva) {
		this.reserva = reserva;
	}

}
