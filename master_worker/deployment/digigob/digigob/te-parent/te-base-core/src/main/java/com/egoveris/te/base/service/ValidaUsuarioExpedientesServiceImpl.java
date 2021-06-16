package com.egoveris.te.base.service;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egoveris.sharedsecurity.base.model.Usuario;

@Service
@Transactional
public class ValidaUsuarioExpedientesServiceImpl implements ValidaUsuarioExpedientesService {
  private static final Logger logger = LoggerFactory
      .getLogger(ValidaUsuarioExpedientesServiceImpl.class);

  @Autowired
  private UsuariosSADEService usuariosSADEService;

  public boolean validaUsuarioExpedientes(String username) {
    if (logger.isDebugEnabled()) {
      logger.debug("validaUsuarioExpedientes(username={}) - start", username);
    }

    Usuario usuario = getUsuariosSADEService().getDatosUsuario(username);
    if (usuario == null) {
      if (logger.isDebugEnabled()) {
        logger.debug("validaUsuarioExpedientes(String) - end - return value={}", false);
      }
      return false;
    }
    if (usuario.getCodigoReparticion() == null) {
      if (logger.isDebugEnabled()) {
        logger.debug("validaUsuarioExpedientes(String) - end - return value={}", false);
      }
      return false;
    }

    if (logger.isDebugEnabled()) {
      logger.debug("validaUsuarioExpedientes(String) - end - return value={}", true);
    }
    return true;
  }

  public UsuariosSADEService getUsuariosSADEService() {
    return usuariosSADEService;
  }

  public void setUsuariosSADEService(UsuariosSADEService usuariosSADEService) {
    this.usuariosSADEService = usuariosSADEService;
  }

}