package com.egoveris.deo.base.service;

public interface PropertyConfiguracionesService {

	/**
	 * Actualiza el valor de la clave que se recibe por parametro, TABLA
	 * PROPERTY_CONFIGURATION.Devuelve true si se actualizo y false si no se
	 * actualizo.
	 */
	public boolean modificarProperty(String clave, String valor);

	/**
	 * Permite obtener el valor de la property a traves de la clave que se
	 * recibe por parametro, TABLA PROPERTY_CONFIGURATION.
	 */
	public String obtenerValorProperty(String clave);
}
