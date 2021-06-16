package com.egoveris.te.base.service;

/**
 * The Interface ConsultaExpedienteEnArchService.
 */
public interface ConsultaExpedienteEnArchService {

	
	/**
	 * Consulta numero digitalizado.
	 *
	 * @param numeroSadeTrack the numero sade track
	 * @return the string
	 */
	public String consultaNumeroDigitalizado(String numeroSadeTrack);
	
	/**
	 * Enviar A solicitud archivo.
	 *
	 * @param codExpediente the cod expediente
	 * @param usuarioFirmante the usuario firmante
	 * @param usuarioSolicitante the usuario solicitante
	 * @return true, if successful
	 */
	public boolean enviarASolicitudArchivo(String codExpediente, String usuarioFirmante,String usuarioSolicitante);
	
	
}
