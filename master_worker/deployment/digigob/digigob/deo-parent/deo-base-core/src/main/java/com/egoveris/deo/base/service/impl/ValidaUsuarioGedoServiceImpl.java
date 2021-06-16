package com.egoveris.deo.base.service.impl;

import com.egoveris.deo.base.service.IValidaUsuarioGedoService;
import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.sharedsecurity.base.service.IUsuarioService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ValidaUsuarioGedoServiceImpl implements IValidaUsuarioGedoService{
		
  @Autowired
		private IUsuarioService usuarioService;
		
		private static final Logger LOGGER = LoggerFactory.getLogger(ValidaUsuarioGedoServiceImpl.class);
	
		public boolean validaUsuarioGedo(String username){
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("validaUsuarioGedo(String) - start"); //$NON-NLS-1$
    }
				
				Usuario usuario = null;
				try {
					usuario = getUsuarioService().obtenerUsuario(username);
				} catch (SecurityNegocioException e) {
					LOGGER.error("Mensaje de error", e);
				}
				if (usuario==null) {
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("validaUsuarioGedo(String) - end"); //$NON-NLS-1$
      }
 					return false;
				}
				if (usuario.getCodigoReparticion() == null) {
      if (LOGGER.isDebugEnabled()) {
        LOGGER.debug("validaUsuarioGedo(String) - end"); //$NON-NLS-1$
      }
 					return false;
				}

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("validaUsuarioGedo(String) - end"); //$NON-NLS-1$
    }
				return true; 
		}
		
		public IUsuarioService getUsuarioService() {
			return usuarioService;
		}

		public void setUsuarioService(IUsuarioService usuarioService) {
			this.usuarioService = usuarioService;
		}

}