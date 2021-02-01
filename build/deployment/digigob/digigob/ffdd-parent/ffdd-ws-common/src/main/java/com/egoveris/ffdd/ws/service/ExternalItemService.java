package com.egoveris.ffdd.ws.service;

import java.util.List;

import com.egoveris.ffdd.model.model.ItemDTO;

public interface ExternalItemService {

	public List<ItemDTO> obtenerDependencia(Integer idItem, String componentName);
  

}