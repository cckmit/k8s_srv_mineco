package com.egoveris.ffdd.base.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import javax.transaction.Transactional;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.egoveris.ffdd.base.exception.AccesoDatoException;
import com.egoveris.ffdd.base.model.Componente;
import com.egoveris.ffdd.base.repository.ComponenteRepository;
import com.egoveris.ffdd.base.service.IComponenteService;
import com.egoveris.ffdd.model.exception.DynFormException;
import com.egoveris.ffdd.model.model.ComponenteDTO;

@Service("componenteService")
@Transactional
public class ComponenteServiceImpl implements IComponenteService {
	private static final Logger logger = LoggerFactory.getLogger(ComponenteServiceImpl.class);

	@Autowired
	private ComponenteRepository componenteRepository;
	@Autowired
	private ConverterServiceImpl converter;
	@Autowired
	@Qualifier("mapperCore")
	private Mapper mapper;

	@Override
	public ComponenteDTO guardarComponente(final ComponenteDTO componente) throws DynFormException {
		if (logger.isDebugEnabled()) {
			logger.debug("guardarComponente(componente={}) - start", componente);
		}
		componente.setFechaCreacion(null);
		componente.setFechaModificacion(null);
		final Componente comp = this.componenteRepository.save(converter.dTOToComp(componente));
				
		if (logger.isDebugEnabled()) {
			logger.debug("guardarComponente(Componente) - end - return value={}", comp);
		}
		return converter.compToDTO(comp);
	}

	@Override
	public List<ComponenteDTO> obtenerComponentesABMLongBoxYTextBox() throws DynFormException {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerComponentesDTOABMLongBoxYTextBox() - start");
		}
		List<ComponenteDTO> returnList = new ArrayList<>();
		List<Componente> comps = this.componenteRepository.obtenerComponentesABMLongBoxYTextBox();
		for(Componente comp : comps){
			returnList.add(converter.compToDTO(comp));
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerComponentesDTOABMLongBoxYTextBox() - end - return value={}", returnList);
		}
		return returnList;
	}

	@Override
	public void eliminarComponente(final String nombre) throws DynFormException {
		if (logger.isDebugEnabled()) {
			logger.debug("eliminarComponente(nombre={}) - start", nombre);
		}

		try {
			this.componenteRepository.delete(componenteRepository.findOneByNombre(nombre));
		} catch (AccesoDatoException e) {
			throw new DynFormException("Error deleting components", e);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("eliminarComponente(String) - end");
		}
	}

	@Override
	public void guardarComponenteDTO(final ComponenteDTO componente) throws DynFormException {
		if (logger.isDebugEnabled()) {
			logger.debug("guardarComponenteDTO(componente={}) - start", componente);
		}
		
		Componente entity = converter.dTOToComp(componente);
		
		if (entity.getDynamicFields() == null) {
			entity.setDynamicFields(new HashSet<>());
		}
		
		this.componenteRepository.save(entity);

		if (logger.isDebugEnabled()) {
			logger.debug("guardarComponenteDTO(ComponenteDTO) - end");
		}
	}

	@Override
	public void eliminarComponente(final ComponenteDTO comp) throws DynFormException {
		if (logger.isDebugEnabled()) {
			logger.debug("eliminarComponente(comp={}) - start", comp);
		}

		this.componenteRepository.delete(converter.dTOToComp(comp));

		if (logger.isDebugEnabled()) {
			logger.debug("eliminarComponente(Componente) - end");
		}
	}

	@Override
	public List<ComponenteDTO> obtenerTodosLosCombobox() throws DynFormException {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerTodosLosCombobox() - start");
		}

		final List<ComponenteDTO> listaCombos = new ArrayList<>();
		final List<Componente> listaComponentes = (List<Componente>) componenteRepository.findAll();
		for (final Componente componente : listaComponentes) {
			if ("COMBOBOX".equals(componente.getTipoComponenteEnt().getNombre())) {
				listaCombos.add(converter.compToDTO(componente));
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("obtenerTodosLosCombobox() - end - return value={}", listaCombos);
		}
		return listaCombos;
	}
	
	/**
	 * Return a mapped Components list with their items mapped.
	 */
	@Override
	public List<ComponenteDTO> obtenerTodosLosComponentesMultivalores() throws DynFormException {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerTodosLosComponentesMultivalores() - start");
		}
		final List<ComponenteDTO> returnList = new ArrayList<>(); 
		for(Componente comp : componenteRepository.obtenerTodosLosComponentesMultivalores()){
			if(comp != null){
				returnList.add(converter.compToDTO(comp));
			}
		}
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerTodosLosComponentesMultivalores() - end - return value={}", returnList);
		}
		return returnList;
	}
	
	@Override
	public ComponenteDTO buscarComponentePorNombre(final String nombre) throws DynFormException {
		if (logger.isDebugEnabled()) {
			logger.debug("buscarComponentePorNombre(nombre={}) - start", nombre);
		}
		final Componente comp = this.componenteRepository.findOneByNombre(nombre);
		if(comp != null){
			final ComponenteDTO returnComponente = converter.compToDTO(comp);
			
			if (logger.isDebugEnabled()) {
				logger.debug("buscarComponentePorNombre(String) - end - return value={}", returnComponente);
			}
			
			return returnComponente;
		}else{
			if (logger.isDebugEnabled()) {
				logger.debug("buscarComponentePorNombre(String) - end - return value={null}");
			}
			return null;
		}
	}

	@Override
	public List<ComponenteDTO> obtenerTodosLosBandBox() throws DynFormException {
		if (logger.isDebugEnabled()) {
			logger.debug("obtenerTodosLosBandBox() - start");
		}

		final List<ComponenteDTO> listaBandBox = new ArrayList<>();
		final List<Componente> listaComponentes = (List<Componente>) componenteRepository.findAll();
		for (final Componente componente : listaComponentes) {
			if ("COMPLEXBANDBOX".equals(componente.getTipoComponenteEnt().getNombre())) {
				listaBandBox.add(converter.compToDTO(componente));
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("obtenerTodosLosBandBox() - end - return value={}", listaBandBox);
		}
		return listaBandBox;
	}

	@Override
	public List<String> findDistinctNombreXml() {
		return componenteRepository.findDistinctNombreXml();
	}

	@Override
	public List<ComponenteDTO> findComplexComponents() {
		final List<Componente> listComponent = componenteRepository.findByNombreXmlNotNull();
		final List<ComponenteDTO> listComplexComponent = new ArrayList<>();
		for(Componente comp: listComponent){
			listComplexComponent.add(converter.compToDTO(comp));
		}
		return listComplexComponent;
	}

	@Override
	public List<String> findComponentsByXml(String xmlFile) {
		return componenteRepository.findComponentNameByXml(xmlFile);
	}

	@Override
	public Boolean findIfComponentIsUsed(String nombre) {
		return null != componenteRepository.findUsedFormComponent(nombre)
				|| null != componenteRepository.findUsedSubComponent(nombre);
	}

	@Override
	public List<String> findComponentsByFactory(String factory) {
		return componenteRepository.findComponentNameByFactory(factory);
	}

	public ConverterServiceImpl getConverter() {
		return converter;
	}

	public void setConverter(final ConverterServiceImpl converter) {
		this.converter = converter;
	}



}
