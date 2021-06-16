package com.egoveris.ffdd.base.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zkoss.util.resource.Labels;

import com.egoveris.ffdd.base.exception.AccesoDatoException;
import com.egoveris.ffdd.base.model.Item;
import com.egoveris.ffdd.base.model.TipoComponente;
import com.egoveris.ffdd.base.repository.ItemsRepository;
import com.egoveris.ffdd.base.repository.TipoComponenteRepository;
import com.egoveris.ffdd.base.service.ITipoComponenteService;
import com.egoveris.ffdd.model.exception.DynFormException;
import com.egoveris.ffdd.model.model.ItemDTO;
import com.egoveris.ffdd.model.model.TipoComponenteDTO;
import com.egoveris.shared.map.ListMapper;

@Service("tipoComponenteService")
public class TipoComponenteServiceImpl implements ITipoComponenteService {

	private static final Logger logger = LoggerFactory.getLogger(TipoComponenteServiceImpl.class);

	@Autowired
	private TipoComponenteRepository tipoComponenteRepository;
	
	@Autowired
	private ItemsRepository itemRepository;
	
	@Autowired
	private ConverterServiceImpl converter;

	@Autowired
	@Qualifier("mapperCore")
	private Mapper mapper;

	@Override
	public TipoComponenteDTO obtenerTipoComponentePorNombre(final String tipoCompononente) throws DynFormException {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerTipoComponentePorNombre(tipoCompononente={}) - start", tipoCompononente);
		}
		TipoComponenteDTO returnTipoComponente = null;
		try {
			TipoComponente tipoComponente = tipoComponenteRepository.findOneByNombre(tipoCompononente);
			if (null != tipoComponente) {
				returnTipoComponente = mapper.map(tipoComponente, TipoComponenteDTO.class);
			}
		} catch (final AccesoDatoException e) {
			throw new DynFormException("ERROR retrieving component type by name.", e);
		}
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerTipoComponentePorNombre(String) - end - return value={}", returnTipoComponente);
		}
		return returnTipoComponente;
	}

	@Override
	public List<TipoComponenteDTO> obtenerTodos() {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerTodos() - start");
		}

		try {
			final List<TipoComponente> tiposComponente = tipoComponenteRepository.findAll();
			final List<TipoComponenteDTO> returnTipoComponente = new ArrayList<>();
			if (CollectionUtils.isNotEmpty(tiposComponente)) {
				for (TipoComponente tipoComponente : tiposComponente) {
					returnTipoComponente.add(mapper.map(tipoComponente, TipoComponenteDTO.class));
				}
			}
			if (logger.isDebugEnabled()) {
				logger.debug("obtenerTodos() - end - return value={}", returnTipoComponente);
			}
			return returnTipoComponente;
		} catch (final AccesoDatoException e) {
			throw new DynFormException("ERROR retrieving component types.", e);
		}
	}

	@Override
	public TipoComponenteDTO saveComponentType(TipoComponenteDTO tipo) {
		final TipoComponente type = tipoComponenteRepository.save(mapper.map(tipo, TipoComponente.class));
		return mapper.map(type, TipoComponenteDTO.class);
	}

	@Override
	public Boolean findIfTypeIsUsed(TipoComponenteDTO selectedType) {
		return null != tipoComponenteRepository.findIfTypeIsUsed(selectedType.getId());
	}
	
	@Override
	public List<TipoComponenteDTO> findNombreComponente() {
		List<TipoComponenteDTO> returnTipoComponente = null;
		List<TipoComponente> listTipoComponente = tipoComponenteRepository.findAll();
		
		if (null != listTipoComponente) {
			returnTipoComponente = ListMapper.mapList(listTipoComponente, mapper, TipoComponenteDTO.class);
		}
		
		return returnTipoComponente;
	}

	@Override
	public void deleteType(TipoComponenteDTO selectedType) {
		tipoComponenteRepository.delete(mapper.map(selectedType, TipoComponente.class));
	}

	@Override
	public List<TipoComponenteDTO> findByFactory(String factory) {
		final List<TipoComponente> tiposComponente = tipoComponenteRepository.findByFactory(factory);
		final List<TipoComponenteDTO> returnTipoComponente = new ArrayList<>();
		
		if (CollectionUtils.isNotEmpty(tiposComponente)) {
			for (TipoComponente tipoComponente : tiposComponente) {
				returnTipoComponente.add(mapper.map(tipoComponente, TipoComponenteDTO.class));
			}
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("findByFactory() - end - return value={}", returnTipoComponente);
		}
		return returnTipoComponente;

	}
	
	@Override
	@Transactional
	public List<ItemDTO> obtenerMultivalores(Integer id) {
		List<ItemDTO> salida = new ArrayList<>();
		
		try {
			final List<Item> multivalores = itemRepository.findByValor(id);
			salida = ListMapper.mapList(multivalores, mapper, ItemDTO.class);
			
		} catch (Exception e) {
			logger.error("Error" + e.getMessage());
		}
		
		return salida;
	}
	
	@Override
	@Transactional
	public void saveMultivalores(List<ItemDTO> listSelecMultivalues, Integer elementoSeleccionado,
			List<ItemDTO> listTodosMultivalues) {
		List<ItemDTO> listEliminar = new ArrayList<>();
		List<ItemDTO> listIngresar = new ArrayList<>();
		
		for (ItemDTO itemDTO : listTodosMultivalues) {
			Set<String> multivalues = getAllMultivaluesForItem(itemDTO);
			
			if (multivalues.contains(elementoSeleccionado.toString())) {
				multivalues.remove(elementoSeleccionado.toString());
			}
			
			// Se crea copia
			ItemDTO itemEliminar = mapper.map(itemDTO, ItemDTO.class);
			itemEliminar.setMultivalor(formatMultivalues(multivalues));
			listEliminar.add(itemEliminar);
		}
		
		for (ItemDTO itemDTO : listSelecMultivalues) {
			Set<String> multivalues = getAllMultivaluesForItem(itemDTO);
			
			if (!multivalues.contains(elementoSeleccionado.toString())) {
				multivalues.add(elementoSeleccionado.toString());
			}
			
			// Se crea copia
			ItemDTO itemIngresar = mapper.map(itemDTO, ItemDTO.class);
			itemIngresar.setMultivalor(formatMultivalues(multivalues));
			listIngresar.add(itemIngresar);
		}
		
		try {
			// Se quitan los eliminados
			for (ItemDTO itemDTO : listEliminar) {
				itemRepository.updateMultivalor(itemDTO.getMultivalor(), itemDTO.getId());
			}
			
			// Se ingresan los cambios
			for (ItemDTO itemDTO : listIngresar) {
				itemRepository.updateMultivalor(itemDTO.getMultivalor(), itemDTO.getId());
			}
		} catch (Exception e) {
			logger.error(Labels.getLabel("tipoComponenteServiceImpl.error.errorGuardado") + e.getMessage());
		}
	}
	
	@Override
	@Transactional
	public List<ItemDTO> obtenerMultivalue(Integer elementoSeleccionado) {
		
		List<ItemDTO> salida = new ArrayList<>();
		try {
			String paramElemento = "%" + elementoSeleccionado + "%";
			final List<Item> multivalue = itemRepository.findByMultivalor(paramElemento);
			salida = ListMapper.mapList(multivalue, mapper, ItemDTO.class);
			
		} catch (Exception e) {
			logger.error("Error" + e.getMessage());
		}
		
		return salida;
	}

	@Override
	@Transactional
	public List<ItemDTO> obtenerMultivaloresAsociados(Integer idComponente) {
		
		List<ItemDTO> salida = new ArrayList<>();
		try {
			final List<Item> valor = itemRepository.findByComponenteId(idComponente);
			salida = ListMapper.mapList(valor, mapper, ItemDTO.class);
			
		} catch (Exception e) {
			logger.error("Error" + e.getMessage());
		}
		
		return salida;
	}

	private Set<String> getAllMultivaluesForItem(ItemDTO itemDTO) {
		Set<String> multivalues = new HashSet<>();
		
		if (itemDTO != null && itemDTO.getMultivalor() != null) {
			String values[] = itemDTO.getMultivalor().split(",");
			
			for (int i = 0; i < values.length; i++) {
				multivalues.add(values[i].trim());
			}
		}
		
		return multivalues;
	}
	
	private String formatMultivalues(Set<String> multivalues) {
		String values = "";
		multivalues.remove("");
		
		int count = 1;
		int size = multivalues.size();
		
		for (String value : multivalues) {
			if (count != size) {
				values += value + ", ";
			}
			else {
				values += value;
			}
			
			count++;
		}
		
		return values;
	}
}
