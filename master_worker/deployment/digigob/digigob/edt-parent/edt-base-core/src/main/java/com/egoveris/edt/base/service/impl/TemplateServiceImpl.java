package com.egoveris.edt.base.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egoveris.edt.base.model.eu.TemplateDTO;
import com.egoveris.edt.base.repository.eu.TemplateRepository;
import com.egoveris.edt.base.service.TemplateService;

@Service
public class TemplateServiceImpl implements TemplateService {

	private TemplateRepository repository;
	private DozerBeanMapper mapper;
	@Autowired
	public TemplateServiceImpl(TemplateRepository repository) {
		this.repository = repository;
		this.mapper = new DozerBeanMapper();
	}

	@Override
	public String getByCodigo(String codigo) {
		return this.repository.findByCodigo(codigo)
						.map(t -> t.getTemplate())
						.orElse(null);
	}

	@Override
	public List<TemplateDTO> getByFormato(String formato) {
		return this.repository.findByFormato(formato)
						.stream()
						.map(t -> this.mapper.map(t, TemplateDTO.class))
						.collect(Collectors.toList());
	}

}
