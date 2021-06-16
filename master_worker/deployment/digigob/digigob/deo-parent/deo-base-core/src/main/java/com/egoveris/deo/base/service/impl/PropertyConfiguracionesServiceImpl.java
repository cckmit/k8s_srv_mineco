package com.egoveris.deo.base.service.impl;

import com.egoveris.deo.base.model.PropertyConfiguration;
import com.egoveris.deo.base.repository.PropertyConfigurationRepository;
import com.egoveris.deo.base.service.PropertyConfiguracionesService;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PropertyConfiguracionesServiceImpl implements PropertyConfiguracionesService{

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PropertyConfiguracionesServiceImpl.class);
	
	private PropertyConfigurationRepository propertyConfigurationRepo;
	
	@Override
	public boolean modificarProperty(String clave, String valor) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("modificarProperty(String, String) - start"); //$NON-NLS-1$
    }

		int result = propertyConfigurationRepo.updateProperty(valor, clave);
		if(result != 0){
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("modificarProperty(String, String) - end"); //$NON-NLS-1$
      }
   			return true;
		}

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("modificarProperty(String, String) - end"); //$NON-NLS-1$
    }
  		return false;
	}
	
	@Override
	public String obtenerValorProperty(String clave) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerValorProperty(String) - start"); //$NON-NLS-1$
    }

		PropertyConfiguration result = propertyConfigurationRepo.findByClave(clave);
		if(result != null){
      String returnString = result.getValor();
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("obtenerValorProperty(String) - end"); //$NON-NLS-1$
      }
   			return returnString;
		}

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerValorProperty(String) - end"); //$NON-NLS-1$
    }
  		return null;
	}
	
}
