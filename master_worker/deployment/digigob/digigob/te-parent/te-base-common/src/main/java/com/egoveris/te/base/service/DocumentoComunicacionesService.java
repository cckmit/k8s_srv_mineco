package com.egoveris.te.base.service;

import com.egoveris.te.base.model.DocumentoDTO;

/**
 * Operaciones posibles sobre los documentos propiedad
 * de Comunicaciones Oficiales
 * 
 * @author rgalloci
 *
 */
public interface DocumentoComunicacionesService {

	/**
	 * Busca documentos en comunicacion oficial por su numero SADE
	 * 
	 * @param numeroSADE
	 * @return
	 */
	public DocumentoDTO buscarDocumento(String numeroSADE);
	
	
	
}
