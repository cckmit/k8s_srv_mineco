package com.egoveris.ffdd.base.service.impl;

import java.util.List;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.ffdd.base.model.FormTrigger;
import com.egoveris.ffdd.base.repository.FormTriggerRepository;
import com.egoveris.ffdd.base.service.IFormDynamicService;
import com.egoveris.ffdd.model.model.FormTriggerDTO;
import com.egoveris.shared.map.ListMapper;

@Service
@Transactional
public class FormDynamicServiceImpl implements IFormDynamicService {

	@Autowired
	private FormTriggerRepository formTriggerRepository;
	@Autowired
	@Qualifier("mapperCore")
	private Mapper mapper;

	@Override
	public void guardar(final FormTriggerDTO constraint) {
		formTriggerRepository.save(mapper.map(constraint,FormTrigger.class));
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FormTriggerDTO> obtenerConstraintsPorComponente(final Integer id) {
		return ListMapper.mapList(formTriggerRepository.findByIdForm(id), mapper, FormTriggerDTO.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FormTriggerDTO> obtenerDynamicComponentPorTipo(final Integer id, final String type) {
		return ListMapper.mapList(formTriggerRepository.findByIdFormAndType(id, type), mapper, FormTriggerDTO.class);
	}

	@Override
	public void eliminarDynamicConstraintComponent(final FormTriggerDTO dynamicConstraintComponent) {
		formTriggerRepository.delete(dynamicConstraintComponent.getId());
	}

	@Override
	public void modificarDynamicConstraintComponente(final FormTriggerDTO constraint) {
		formTriggerRepository.save(mapper.map(constraint,FormTrigger.class));
	}
	
}
