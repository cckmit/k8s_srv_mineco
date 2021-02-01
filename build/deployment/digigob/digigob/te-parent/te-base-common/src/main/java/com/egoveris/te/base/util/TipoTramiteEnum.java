package com.egoveris.te.base.util;

public enum TipoTramiteEnum {
	SUBPROCESO("SUBPROCESO"),
	EXPEDIENTE("EXPEDIENTE"),
	AMBOS("AMBOS");
	
	private String value;
	
	TipoTramiteEnum(final String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}	
	
}
