package com.egoveris.te.base.util;

import org.springframework.beans.factory.annotation.Autowired;

public class ConstantesDB {
	
	@Autowired
	private String nombreReparticionActuacion;

	@Autowired
	private String nombreEntorno;
	
	public String getNombreReparticionActuacion() {
		return nombreReparticionActuacion;
	}
	public void setNombreReparticionActuacion(String nombreReparticionActuacion) {
		this.nombreReparticionActuacion = nombreReparticionActuacion;
	}

	public String getNombreEntorno() {
		return nombreEntorno;
	}
	public void setNombreEntorno(String nombreEntorno) {
		this.nombreEntorno = nombreEntorno;
	}
	
}