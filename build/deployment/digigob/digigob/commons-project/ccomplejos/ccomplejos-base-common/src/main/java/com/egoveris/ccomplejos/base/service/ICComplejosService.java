package com.egoveris.ccomplejos.base.service;

import java.util.List;

import com.egoveris.ccomplejos.base.model.AbstractCComplejoDTO;

/**
 * Permite el guardado y obtención de los valores asociados a un componente
 * complejo y una operación.
 * 
 * @author everis
 *
 */
public interface ICComplejosService {

	/**
	 * Permite guardar los valores de una lista de componentes complejos en base
	 * de datos.
	 * 
	 * @param cComplejoDTOs
	 */
	void guardarDatosComponentes(List<AbstractCComplejoDTO> cComplejoDTOs);


	/**
	 * Permite recuperar los valores de un componentes complejos.
	 * 
	 * @param cComplejoDTO
	 * @return
	 */
	List<AbstractCComplejoDTO> buscarDatosComponente(AbstractCComplejoDTO cComplejoDTO);

}
