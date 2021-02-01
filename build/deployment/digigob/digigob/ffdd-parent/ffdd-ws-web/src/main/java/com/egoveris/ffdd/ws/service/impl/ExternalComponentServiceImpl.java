package com.egoveris.ffdd.ws.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egoveris.ffdd.base.service.IComponenteService;
import com.egoveris.ffdd.model.model.ComponenteDTO;
import com.egoveris.ffdd.ws.service.ExternalComponentService;

@Service
public class ExternalComponentServiceImpl implements ExternalComponentService {

	@Autowired
	private IComponenteService componenteService;

	@Override
	public ComponenteDTO buscarComponentePorNombre(String nombre) {
		return componenteService.buscarComponentePorNombre(nombre);
	}

}
