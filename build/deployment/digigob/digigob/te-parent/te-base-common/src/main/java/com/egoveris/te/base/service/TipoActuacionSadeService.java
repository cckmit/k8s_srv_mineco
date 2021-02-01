/**
 * 
 */
package com.egoveris.te.base.service;


import java.util.ArrayList;
import java.util.List;

import com.egoveris.te.base.model.TipoDocumentoDTO;

/**
 * The Interface TipoActuacionSadeService.
 *
 * @author jnorvert
 */
public interface TipoActuacionSadeService {
	
	/**
	 * Obtener tipos documento sade.
	 *
	 * @param tiposDocumentos the tipos documentos
	 * @return the list
	 */
	public List<TipoDocumentoDTO> obtenerTiposDocumentoSade(ArrayList<TipoDocumentoDTO> tiposDocumentos); 

}
