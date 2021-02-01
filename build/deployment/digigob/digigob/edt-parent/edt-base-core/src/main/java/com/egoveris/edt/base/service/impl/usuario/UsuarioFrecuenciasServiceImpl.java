package com.egoveris.edt.base.service.impl.usuario;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egoveris.edt.base.model.eu.usuario.UsuarioFrecuencia;
import com.egoveris.edt.base.model.eu.usuario.UsuarioFrecuenciaDTO;
import com.egoveris.edt.base.repository.eu.usuario.UsuarioFrecuenciasRepository;
import com.egoveris.edt.base.service.usuario.IUsuarioFrecuenciasService;

@Service
public class UsuarioFrecuenciasServiceImpl implements IUsuarioFrecuenciasService {

  private static Logger logger = LoggerFactory.getLogger(UsuarioFrecuenciasServiceImpl.class);
  private DozerBeanMapper mapper = new DozerBeanMapper();

  @Autowired
  UsuarioFrecuenciasRepository usuarioFrecuenciasRepository;

  @Override
  public void insertarUsuarioFrecuencias(UsuarioFrecuenciaDTO usuarioFrecuencia) {
    logger.debug("Iniciando - usuarioFrecuencia: {}", usuarioFrecuencia.getUsuario());

    this.usuarioFrecuenciasRepository.save(mapper.map(usuarioFrecuencia, UsuarioFrecuencia.class));

    logger.debug("Fin - usuarioFrecuencia: {}", usuarioFrecuencia.getUsuario());
  }

  public void setUsuarioFrecuenciasDAO(UsuarioFrecuenciasRepository usuarioFrecuenciasDAO) {
    this.usuarioFrecuenciasRepository = usuarioFrecuenciasDAO;
  }

  @Override
  public UsuarioFrecuenciaDTO buscarFrecuenciasPorUsuario(String userName) {
    UsuarioFrecuenciaDTO retorno = null;
    final UsuarioFrecuencia resultado = this.usuarioFrecuenciasRepository.findByUsuario(userName);
    if (resultado != null) {
      retorno = mapper.map(resultado, UsuarioFrecuenciaDTO.class);
    }
    return retorno;
  }

}