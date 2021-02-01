package com.egoveris.numerador.base.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.numerador.base.model.NumeroSistema;
import com.egoveris.numerador.base.repository.NumeroSistemaRepository;
import com.egoveris.numerador.base.service.SistemaService;
import com.egoveris.numerador.model.exception.SistemaInvalidoException;

@Service
@Transactional
public class SistemaServiceImpl implements SistemaService {
	private static final Logger logger = LoggerFactory.getLogger(SistemaServiceImpl.class);
	
	@Autowired 
	private NumeroSistemaRepository numeroSistemaRepo;
	
	@Override
	public List<String> buscarNombreSistemasByEstado(boolean activo)
			throws  SistemaInvalidoException {
		if(logger.isDebugEnabled()){
			logger.debug("buscarNombreSistemasByEstado(activo={}) - start", activo);
		}
		
		List<String> nombreSistema = new ArrayList<>();
		List<NumeroSistema> numerosistema = this.numeroSistemaRepo.findByActivo(activo);
		for(NumeroSistema ns : numerosistema){
			nombreSistema.add(ns.getNombreSistema());
		}
		
		if(logger.isDebugEnabled()){
			logger.debug("buscarNombreSistemasByEstado(List<String>) - end", nombreSistema);
		}
		return nombreSistema;
 
	}

	@Override
	public String obtenerUrlSistema(String sistema) {
		if(logger.isDebugEnabled()){
			logger.debug("obtenerUrlSistema(sistema={}) - start", sistema);
		}
		
		NumeroSistema numeroSistema = numeroSistemaRepo.findByNombreSistema(sistema);
		
		if(logger.isDebugEnabled()){
			logger.debug("obtenerUrlSistema() - end");
		}
		
		return numeroSistema.getLinkSistema();
	}
}
