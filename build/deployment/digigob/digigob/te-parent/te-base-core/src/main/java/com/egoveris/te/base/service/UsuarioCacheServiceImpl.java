package com.egoveris.te.base.service;

import java.util.List;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.UsuarioReducido;
import com.egoveris.sharedsecurity.base.service.IUsuarioService;
import com.egoveris.te.base.service.iface.IUsuarioCacheService;

@Service("usuarioCacheService")
public class UsuarioCacheServiceImpl implements IUsuarioCacheService {

  @Autowired
  private IUsuarioService usuarioService;
  
  private List<UsuarioReducido> listaUsuarios;
  
  private static final Logger LOGGER = LoggerFactory.getLogger(UsuarioCacheServiceImpl.class);

  
  @PostConstruct
  public void cargarLista() {
  	cachearListaUsuarios();
  }
  
  @Override
  @Scheduled(fixedRate = 30000)
  public void cachearListaUsuarios() {
    try {
      listaUsuarios = usuarioService.obtenerUsuariosDeSolr();
    } catch (SecurityNegocioException ex) {
      LOGGER.error("Error al obtener usuarios solr", ex);
    }
  }
	
	@Override
	public List<UsuarioReducido> obtenerTodosUsuarios() {
		if(listaUsuarios==null) {
			LOGGER.info("La cache de usuario se encuentra vacia");
			listaUsuarios = usuarioService.obtenerUsuariosDeSolr();
		}
		return listaUsuarios;
	}

}
