package com.egoveris.numerador.base.service.impl;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.egoveris.numerador.base.repository.NumeroRepository;
import com.egoveris.numerador.base.service.NumeroGeneradoService;
import com.egoveris.numerador.model.model.NumeroGeneradoDTO;
import com.egoveris.numerador.util.Constantes;

@Service
@Transactional
public class NumeroGeneradoServiceImpl implements NumeroGeneradoService {
	private static final Logger logger = LoggerFactory.getLogger(NumeroGeneradoServiceImpl.class);
	
	@Autowired
	private NumeroRepository numeroRepository;
	@Autowired
	@Qualifier("mapperCore")
	private Mapper mapper;
	
	@Override
	public NumeroGeneradoDTO consultarNumero(int anio, int numero, String secuencia) {
		String secuenciaTemp = secuencia;
	  if(StringUtils.isEmpty(secuencia)){
	    secuenciaTemp = Constantes.CODIGO_SECUENCIA_ORIGINAL;
		}
		if(logger.isDebugEnabled()){
			logger.debug("consultarNumero(anio={}, numero={}, secuencia={}) - start", anio, numero, secuenciaTemp);
		}
		
		NumeroGeneradoDTO numeroGenerado = this.mapper.map(this.numeroRepository.findByAnioAndNumeroAndSecuencia(anio, numero, secuenciaTemp), NumeroGeneradoDTO.class);
		
		if(logger.isDebugEnabled()){
			logger.debug("consultarNumero(NumeroGeneradoDTO) - end", numeroGenerado);
		}
		
		return numeroGenerado;
	}
}
