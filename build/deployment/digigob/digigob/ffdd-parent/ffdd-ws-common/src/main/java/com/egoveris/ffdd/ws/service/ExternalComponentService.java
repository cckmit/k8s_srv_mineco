package com.egoveris.ffdd.ws.service;

import com.egoveris.ffdd.model.exception.DynFormException;
import com.egoveris.ffdd.model.model.ComponenteDTO;

public interface ExternalComponentService {

	/**
	 * Find a component by name.
	 * 
	 * @param name
	 * @return Component
	 * @throws DynFormException
	 */
	public ComponenteDTO buscarComponentePorNombre(String nombre);

}
