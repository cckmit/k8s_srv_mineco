package com.egoveris.ffdd.base.service;

import java.util.List;

import com.egoveris.ffdd.model.model.ItemDTO;

public interface IItemService {

	public List<ItemDTO> obtenerDependencia(Integer iditem, String componentName);

}