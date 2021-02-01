package com.egoveris.te.base.service;

/**
 * The Interface ValidaUsuarioExpedientesService.
 */
public interface ValidaUsuarioExpedientesService {
	
	/**
	 * Valida usuario expedientes.
	 *
	 * @param username the username
	 * @return true, if successful
	 * @throws InterruptedException the interrupted exception
	 */
	public boolean validaUsuarioExpedientes(String username ) throws InterruptedException;

}
