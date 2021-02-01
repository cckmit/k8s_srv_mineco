package com.egoveris.te.ws.service;

import java.util.List;

import com.egoveris.te.ws.model.ExternalHistorialOperacionDTO;

/**
 * Interfaz que expone los métodos asociados a las operaciones de un expediente.
 * 
 * @author rgodoylo
 *
 */
public interface ExternalHistorialOperacionService {

	/**
	 * Obtiene el historial de pases de un expediente.
	 * 
	 * @param codigoExpediente
	 * @return
	 */
	List<ExternalHistorialOperacionDTO> getHistorialByExpediente(String codigoExpediente);

}
