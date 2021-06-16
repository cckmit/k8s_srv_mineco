package com.egoveris.vucfront.base.service.impl;

import javax.transaction.Transactional;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.egoveris.vucfront.base.model.Estilos;
import com.egoveris.vucfront.base.repository.EstilosRepository;
import com.egoveris.vucfront.model.model.EstilosDTO;
import com.egoveris.vucfront.model.service.EstilosService;

@Service
@Transactional
public class EstilosServiceImpl implements EstilosService {

	@Autowired
	@Qualifier("vucMapper")
	private Mapper mapper;
	
	private EstilosRepository repositoy;

	@Autowired
	public EstilosServiceImpl(EstilosRepository repositoy) {
		this.repositoy = repositoy;
	}

	@Override
	public EstilosDTO getEstilosByCodigo(String codigo) {
		Estilos estilos = this.repositoy.findByCodigo(codigo);
		return mapper.map(estilos, EstilosDTO.class);
	}

}
