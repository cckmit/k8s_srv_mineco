package com.egoveris.te.base.service;

import java.util.List;

import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;


/**
 * 
 * Interface que posee la definicion de metodos 
 * para su implementacion.
 * @author eduavega
 */

public interface DocumentoService {
	
	/**
	 * Buscar documento.
	 *
	 * @param numeroSade the numero sade
	 * @return the documento
	 */
	public DocumentoDTO buscarDocumento(String numeroSade);
	
	/**
	 * Guardar.
	 *
	 * @param documento the documento
	 */
	public void guardar(DocumentoDTO documento);
	
	/**
	 * Obtener tipo doc depuracion.
	 *
	 * @return the list
	 */
	public List<String> obtenerTipoDocDepuracion();
	
	/**
	 * Buscar expedientes por numero documento no archivo.
	 *
	 * @param numeroSade the numero sade
	 * @return the list
	 */
	public List<ExpedienteElectronicoDTO> buscarExpedientesPorNumeroDocumentoNoArchivo(String numeroSade);
}
