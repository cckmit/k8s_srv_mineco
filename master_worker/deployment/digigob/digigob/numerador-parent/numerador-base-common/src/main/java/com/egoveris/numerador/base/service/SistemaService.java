package com.egoveris.numerador.base.service;

import java.util.List;

import com.egoveris.numerador.model.exception.SistemaInvalidoException;

public interface SistemaService {

	/**
	 * 
	 * @param activo
	 * @return
	 * @throws SistemaInvalidoException
	 */
	public List<String> buscarNombreSistemasByEstado(boolean activo)
			throws SistemaInvalidoException;
	
	/**
	 * 
	 * @param sistema
	 * @return
	 */
	public String obtenerUrlSistema(String sistema);
	
}
