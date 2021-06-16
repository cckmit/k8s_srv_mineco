package com.egoveris.numerador.base.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.numerador.base.model.Numero;
import com.egoveris.numerador.base.repository.NumeroRepository;
import com.egoveris.numerador.base.repository.NumeroSecuenciaRepository;
import com.egoveris.numerador.base.service.NumeroSecuenciaService;
import com.egoveris.numerador.model.model.NumeroSecuenciaDTO;
import com.egoveris.shared.map.ListMapper;

@Service
@Transactional
public class NumeroSecuenciaServiceImpl implements NumeroSecuenciaService {
	private static final Logger logger = LoggerFactory.getLogger(NumeroSecuenciaServiceImpl.class);
	
	@Autowired
	private NumeroSecuenciaRepository numeroSecuenciaRepository;
	@Autowired
	private NumeroRepository numeroRepository;
	@Autowired
	@Qualifier("mapperCore")
	private Mapper mapper;
	
	@Override
	public List<NumeroSecuenciaDTO> buscarCaratulas(int anio, int numero) {
		List<NumeroSecuenciaDTO> numSecuResultList = new ArrayList<NumeroSecuenciaDTO>();
		
		if(logger.isDebugEnabled()){
			logger.debug("buscarCaratulas(anio={}, numero={}) - start", anio, numero);
		}
		
		List<Numero> numeroList = numeroRepository.findByAnioAndNumero(anio, numero);
		
		if (numeroList == null) {
			numeroList = new ArrayList<Numero>();
		}

		for (Numero numeroAux : numeroList) {
			numSecuResultList.add(this.mapper.map(numeroAux, NumeroSecuenciaDTO.class));
		}
		
		if(logger.isDebugEnabled()){
			logger.debug("buscarCaratulas(List<NumeroSecuenciaDTO>) - end", numSecuResultList);
		}
		
		return numSecuResultList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<NumeroSecuenciaDTO> obtenerCaratulas(int anio, int numero) {
		
		if(logger.isDebugEnabled()){
			logger.debug("obtenerCaratulas(anio={}, numero={}) - start", anio, numero);
		}
		
		List<NumeroSecuenciaDTO> returnList = ListMapper.mapList(this.numeroSecuenciaRepository.findByAnioAndNumero(anio, numero), this.mapper, NumeroSecuenciaDTO.class); 
		
		if(logger.isDebugEnabled()){
			logger.debug("obtenerCaratulas(List<NumeroSecuenciaDTO>) - end", returnList);
		}
		
		return returnList;
	}

}
