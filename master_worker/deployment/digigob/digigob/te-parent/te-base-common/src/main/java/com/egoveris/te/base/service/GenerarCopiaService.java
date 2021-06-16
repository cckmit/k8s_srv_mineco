package com.egoveris.te.base.service;

import java.util.List;

import com.egoveris.te.base.model.GenerarCopiaBeanDTO;

/**
 * The Interface GenerarCopiaService.
 */
public interface GenerarCopiaService {
	
	/**
	 * Save.
	 *
	 * @param bean the bean
	 */
	public void save(GenerarCopiaBeanDTO bean);
	
	/**
	 * Buscar beans validos para reintento.
	 *
	 * @param reintentos the reintentos
	 * @return the list
	 */
	public List<GenerarCopiaBeanDTO> buscarBeansValidosParaReintento(Integer reintentos);

}
