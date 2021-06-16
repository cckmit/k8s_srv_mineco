package com.egoveris.te.base.service;

import java.util.List;
import java.util.Map;

import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.HistorialOperacionDTO;

/**
 * The Interface HistorialOperacionService.
 */
public interface HistorialOperacionService {
	
	/**
	 * Guardar operacion.
	 *
	 * @param operacion the operacion
	 */
	public void guardarOperacion(HistorialOperacionDTO operacion);
	
	/**
	 * Buscar historialpor expediente.
	 *
	 * @param idExpediente the id expediente
	 * @return the list
	 */
	public List<HistorialOperacionDTO> buscarHistorialporExpediente(Long idExpediente);
	
	/**
	 * Guardar datos en historial operacion.
	 *
	 * @param expedienteElectronico the expediente electronico
	 * @param usuario the usuario
	 * @param detalles the detalles
	 */
	public void guardarDatosEnHistorialOperacion  (ExpedienteElectronicoDTO expedienteElectronico, Usuario usuario,
			Map<String, String> detalles);
	
	/**
	 * Guardar datos en historial operacion sin usuario object.
	 *
	 * @param expedienteElectronico the expediente electronico
	 * @param usuario the usuario
	 * @param detalles the detalles
	 */
	public void guardarDatosEnHistorialOperacionSinUsuarioObject  (ExpedienteElectronicoDTO expedienteElectronico, String usuario,
			Map<String, String> detalles);
	
	

}
