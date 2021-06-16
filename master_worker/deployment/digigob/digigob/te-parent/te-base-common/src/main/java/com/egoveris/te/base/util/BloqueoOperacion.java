package com.egoveris.te.base.util;

/**
 * Como los estados de Bloqueo Operacion en BDD corresponden
 * a un String, se utiliza esta enum
 * 
 * @author everis
 */
public enum BloqueoOperacion {
	NINGUNO("Ninguno"),
	PARCIAL("Parcial"),
	TOTAL("Total");
	
	private String value;
	
	BloqueoOperacion(final String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
}
