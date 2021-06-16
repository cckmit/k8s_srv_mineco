package com.egoveris.deo.base.service;

public interface GedoADestinatarios {

	public static final String NOMBRE_PROCESO = "GedoADestinatarios";
	public static final String CERRAR_DOCUMENTO = "CIERRE";
	public static final String CANCELAR_DOCUMENTO = "CANCELAR";
	
	 

	public void notificarADestinatarios(String indicador, String numero, String executionId, String usuario);
	
}
