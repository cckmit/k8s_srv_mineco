package com.egoveris.deo.base.service.impl;

import com.egoveris.deo.base.service.IEcosistemaService;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
@Service
public class EcosistemaServiceImpl implements IEcosistemaService{

	private static final Logger LOGGER = LoggerFactory.getLogger(EcosistemaServiceImpl.class);
	
	@Value("${app.ecosistema}")
	private String codigoEcosistema;
	/**
	 * Retorna el Ecosistema en el que se encuentra la aplicacion*/
	public String obtenerEcosistema() {
		return codigoEcosistema;
	}

	/**
     * Retorna todos los Ecosistemas*/
    public List<String> obtenerTodosLosEcosistemas() {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerTodosLosEcosistemas() - start"); //$NON-NLS-1$
    }

            throw new UnsupportedOperationException("Implementar el obtenerTodosLosEcosistemas");
    }



}
