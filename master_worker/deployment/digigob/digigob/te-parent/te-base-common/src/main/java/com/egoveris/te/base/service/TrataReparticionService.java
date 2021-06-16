package com.egoveris.te.base.service;
import java.util.List;

import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.te.base.model.TrataDTO;
import com.egoveris.te.base.model.TrataReparticionDTO;


/**
 * The Interface TrataReparticionService.
 *
 * @author matalvar
 */

public interface TrataReparticionService {
	
	/**
	 * Cargar reparticiones habilitadas.
	 *
	 * @param trata the trata
	 * @return the list
	 */
	public List<TrataReparticionDTO> cargarReparticionesHabilitadas(TrataDTO trata);
	
	/**
	 * Modificar list reparticion.
	 *
	 * @param Trata the trata
	 */
	public void modificarListReparticion(TrataDTO Trata);
	
	/**
	 * Validar permiso reparticion.
	 *
	 * @param trata the trata
	 * @param reparticion the reparticion
	 * @param usuario the usuario
	 * @return true, if successful
	 */
	public boolean validarPermisoReparticion(TrataDTO trata, String reparticion, Usuario usuario);
}
