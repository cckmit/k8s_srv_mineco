package com.egoveris.ffdd.base.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.ffdd.base.model.Item;
import com.egoveris.ffdd.base.repository.DynamicComponenteRepository;
import com.egoveris.ffdd.base.service.IItemService;
import com.egoveris.ffdd.model.model.ComponenteDTO;
import com.egoveris.ffdd.model.model.ItemDTO;

@Service("itemServiceImpl")
@Transactional
public class ItemServiceImpl implements IItemService {
	private static final Logger logger = LoggerFactory.getLogger(ItemServiceImpl.class);

	@Autowired
	private DynamicComponenteRepository dynamicComponenteRepository;
	
	@Autowired
	@Qualifier("mapperCore")
	private Mapper mapper;

	@Override
	public List<ItemDTO> obtenerDependencia(Integer idItem, String componentName) {
		List<ItemDTO> salida = new ArrayList<>();

		List<Item> aux = this.dynamicComponenteRepository.findMultivaloresComponente(componentName);

		for (Item item : aux) {
			if (null != item && null != item.getMultivalor()) {
				if (item.getMultivalor().contains(idItem.toString())) {
					// FIXME mapper.map(item, ItemDTO.class)
					ItemDTO itemDTO = new ItemDTO();
					itemDTO.setComponente(new ComponenteDTO());
					itemDTO.getComponente().setId(item.getComponente().getId());
					itemDTO.getComponente().setNombre(item.getComponente().getNombre());
					itemDTO.setDescripcion(item.getDescripcion());
					itemDTO.setId(item.getId());
					itemDTO.setMultivalor(item.getMultivalor());
					itemDTO.setOrden(item.getOrden());
					itemDTO.setValor(item.getValor());
					salida.add(itemDTO);
				}
			}
		}		

		return salida;
	}

}
