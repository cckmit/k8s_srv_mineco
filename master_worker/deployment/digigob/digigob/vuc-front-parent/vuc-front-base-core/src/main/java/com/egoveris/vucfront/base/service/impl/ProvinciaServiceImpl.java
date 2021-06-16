package com.egoveris.vucfront.base.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egoveris.vucfront.base.model.Provincia;
import com.egoveris.vucfront.base.repository.ProvinciaRepository;
import com.egoveris.vucfront.base.service.ProvinciaService;

@Service
public class ProvinciaServiceImpl implements ProvinciaService {

	@Autowired
	private ProvinciaRepository repository;
	
	@Override
	public List<Provincia> getAll() {
		return this.repository.findAll();
	}

}
