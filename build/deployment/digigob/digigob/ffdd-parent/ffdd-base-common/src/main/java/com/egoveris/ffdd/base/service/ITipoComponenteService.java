package com.egoveris.ffdd.base.service;

import java.util.List;

import com.egoveris.ffdd.model.model.ComponenteDTO;
import com.egoveris.ffdd.model.model.ItemDTO;
import com.egoveris.ffdd.model.model.TipoComponenteDTO;

public interface ITipoComponenteService {
	/**
	 * Find a component type by name.
	 * 
	 * @param tipoComponente
	 * @return TipoComponente
	 */
	public TipoComponenteDTO obtenerTipoComponentePorNombre(String tipoCompononente);

	/**
	 * 
	 * @return
	 */
	public List<TipoComponenteDTO> obtenerTodos();

	/**
	 * 
	 * @param tipo
	 */
	public TipoComponenteDTO saveComponentType(TipoComponenteDTO tipo);

	/**
	 * 
	 * @param selectedType
	 * @return
	 */
	public Boolean findIfTypeIsUsed(TipoComponenteDTO selectedType);

	/**
	 * 
	 * @param selectedType
	 */
	public void deleteType(TipoComponenteDTO selectedType);

	/**
	 * 
	 * @param factory
	 * @return
	 */
	public List<TipoComponenteDTO> findByFactory(String factory);
	
	/**
	 * 
	 * @return
	 */
	public List<TipoComponenteDTO> findNombreComponente();
	
	/**
	 * 
	 * @param Multivalores
	 * @return
	 */
	public List<ItemDTO> obtenerMultivalores(Integer id);
	
	/**
	 * 
	 * @param Multivalores
	 * @return
	 */
	public void saveMultivalores(List<ItemDTO> listMultivalue, Integer elementoSeleccionado, List<ItemDTO> listaItemsMultivalue);
	
	/**
	 * 
	 * @param Multivalores
	 * @return
	 */
	public List<ItemDTO> obtenerMultivalue(Integer elementoSeleccionado);
	
	/**
	 * 
	 * @param Multivalores
	 * @return
	 */
	public List<ItemDTO> obtenerMultivaloresAsociados(Integer idComponente);
	
}
