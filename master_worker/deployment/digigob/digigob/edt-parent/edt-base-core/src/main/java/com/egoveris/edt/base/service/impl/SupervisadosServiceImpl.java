package com.egoveris.edt.base.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.edt.base.service.ISupervisadosService;
import com.egoveris.sharedsecurity.base.model.DatosUsuario;
import com.egoveris.sharedsecurity.base.repository.impl.DatosUsuarioRepository;

@Service
@Transactional
public class SupervisadosServiceImpl implements ISupervisadosService {

  @Autowired
  DatosUsuarioRepository datsUsrRepository;

  private static Logger logger = LoggerFactory.getLogger(SupervisadosServiceImpl.class);

  @Override
  public List<String> obtenerSupervisadosParaUsuario(String userName) {
    logger.debug("{} - Iniciando", userName);
    List<DatosUsuario> datosUsuarios = datsUsrRepository.findByUserSuperior(userName);
    List<String> usuariosSubordinados = new ArrayList<>();
    if (!datosUsuarios.isEmpty()) {
      for (DatosUsuario dato : datosUsuarios) {
        usuariosSubordinados.add(dato.getUsuario());
      }
    }
    logger.debug("{} - Fin - cantidad: {}", userName, usuariosSubordinados.size());
    return usuariosSubordinados;
  }

}