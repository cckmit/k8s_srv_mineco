package com.egoveris.ffdd.ws.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egoveris.ffdd.base.service.IItemService;
import com.egoveris.ffdd.model.model.ItemDTO;
import com.egoveris.ffdd.ws.service.ExternalItemService;

@Service("externalItemServiceImpl")
public class ExternalItemServiceImpl implements ExternalItemService {

	@Autowired
	private IItemService itemServiceImpl;

	@Override
	public List<ItemDTO> obtenerDependencia(Integer idItem, String componentName) {
		return itemServiceImpl.obtenerDependencia(idItem, componentName);
	}

}
