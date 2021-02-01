package com.egoveris.te.base.service;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egoveris.te.base.model.TareaMigracion;
import com.egoveris.te.base.repository.TareaMigracionRepository;

@Service
@Transactional
public class TareaMigracionServiceImple {
  private static final Logger logger = LoggerFactory.getLogger(TareaMigracionServiceImple.class);

  @Autowired
  private TareaMigracionRepository tareaDAO;

  public void guardarTarea(TareaMigracion tareaMigracion) {
    if (logger.isDebugEnabled()) {
      logger.debug("guardarTarea(tareaMigracion={}) - start", tareaMigracion);
    }

    tareaDAO.save(tareaMigracion);

    if (logger.isDebugEnabled()) {
      logger.debug("guardarTarea(TareaMigracion) - end");
    }
  }

  public boolean existeTarea(String token) {
    if (logger.isDebugEnabled()) {
      logger.debug("existeTarea(token={}) - start", token);
    }

    List<TareaMigracion> tareaEnt = tareaDAO.findByTarea(token);

    boolean returnboolean = true;

    if (tareaEnt == null || tareaEnt.size() == 0) {
      returnboolean = false;
    }

    if (logger.isDebugEnabled()) {
      logger.debug("existeTarea(String) - end - return value={}", returnboolean);
    }
    return returnboolean;
  }

}
