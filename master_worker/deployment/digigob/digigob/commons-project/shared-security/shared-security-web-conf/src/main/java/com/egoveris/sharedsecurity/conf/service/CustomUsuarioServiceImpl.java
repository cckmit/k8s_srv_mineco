package com.egoveris.sharedsecurity.conf.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.sharedsecurity.base.service.IUsuarioService;

@Service("usuarioService")
public final class CustomUsuarioServiceImpl implements UserDetailsService {

  @Autowired
  private IUsuarioService userService;
  
  @Override
  public Usuario loadUserByUsername(String userName) {
      return userService.obtenerUsuario(userName);
  }
}