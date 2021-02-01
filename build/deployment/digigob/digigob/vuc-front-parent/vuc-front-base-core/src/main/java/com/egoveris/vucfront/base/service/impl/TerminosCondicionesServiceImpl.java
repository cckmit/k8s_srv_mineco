package com.egoveris.vucfront.base.service.impl;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.egoveris.vucfront.base.model.TerminosCondiciones;
import com.egoveris.vucfront.base.repository.TerminosYCondicionesRepository;
import com.egoveris.vucfront.base.service.TerminosCondicionesService;
import com.egoveris.vucfront.model.model.TerminosCondicionesDTO;

@Service
public class TerminosCondicionesServiceImpl implements TerminosCondicionesService {

	@Autowired
	@Qualifier("vucMapper")
	private Mapper mapper;
	
	@Autowired
	private TerminosYCondicionesRepository repository;

	@Override
	public TerminosCondicionesDTO getUltimoTerminosYCondiciones() {
		TerminosCondicionesDTO result = null;
		TerminosCondiciones terminosCondiciones = this.repository.findTopByOrderByIdDesc();
		if (terminosCondiciones != null) {
			result = this.mapper.map(terminosCondiciones, TerminosCondicionesDTO.class);
		}
		return result;
	}

}
