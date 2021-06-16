package com.egoveris.ffdd.ws.service;

import java.util.List;

import com.egoveris.ccomplejos.base.model.AbstractCComplejoDTO;

public interface ExternalCComplejosService {

	/**
	 * Permite guardar los valores de una lista de componentes complejos en base
	 * de datos.
	 * 
	 * @param cComplejoDTOs
	 */
	public void guardarDatosComponentes(List<AbstractCComplejoDTO> cComplejoDTOs);

	/**
	 * Permite recuperar los valores de un componentes complejos.
	 * 
	 * @param cComplejoDTO
	 * @return
	 */
	public List<AbstractCComplejoDTO> buscarDatosComponente(AbstractCComplejoDTO cComplejoDTO);

}
