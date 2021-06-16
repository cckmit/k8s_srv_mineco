package com.egoveris.te.base.service;

import java.util.List;

import javax.transaction.Transactional;

import org.jbpm.api.identity.Group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egoveris.te.base.dao.ReparticionSadeDAO;

@Service
@Transactional
public class ReparticionSadeServicesImpl {

  @Autowired
  ReparticionSadeDAO repartiSadeDao;

  public List<Group> buscarReparticionesPorUsuario(String userName) {

    return repartiSadeDao.buscarReparticionesPorUsuario(userName);
  }

  public boolean validarCodigoReparticion(String value) {

    return repartiSadeDao.validarCodigoReparticion(value);
  }

  public Group buscarReparticionesByUsuario(String userName) {

    return repartiSadeDao.buscarReparticionesByUsuario(userName);
  }

}
