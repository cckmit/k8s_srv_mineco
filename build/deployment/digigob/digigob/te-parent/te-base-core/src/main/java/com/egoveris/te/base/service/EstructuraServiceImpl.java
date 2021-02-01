package com.egoveris.te.base.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egoveris.te.base.dao.IEstructuraDAO;
import com.egoveris.te.base.model.EstructuraBean;
import com.egoveris.te.base.service.iface.IEstructuraService;

@Service
@Transactional
public class EstructuraServiceImpl implements IEstructuraService {
  private static final Logger logger = LoggerFactory.getLogger(EstructuraServiceImpl.class);

  @Autowired
  private IEstructuraDAO estructuraDAO;

  private List<EstructuraBean> listaEstructurasVigentesActivas;

  public List<EstructuraBean> buscarEstructurasVigentesActivas() {
    if (logger.isDebugEnabled()) {
      logger.debug("buscarEstructurasVigentesActivas() - start");
    }

    if (listaEstructurasVigentesActivas == null) {
      synchronized (this) {
        //this.listaEstructurasVigentesActivas = estructuraDAO.buscarEstructurasVigentesActivas();
        this.listaEstructurasVigentesActivas = new ArrayList<>();
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("buscarEstructurasVigentesActivas() - end - return value={}",
          listaEstructurasVigentesActivas);
    }
    return listaEstructurasVigentesActivas;
  }

  public EstructuraBean buscarEstructuraPorId(int idEstructura) {
    if (logger.isDebugEnabled()) {
      logger.debug("buscarEstructuraPorId(idEstructura={}) - start", idEstructura);
    }

    EstructuraBean actual;

    Iterator<EstructuraBean> iterator = this.buscarEstructurasVigentesActivas().iterator();
    while (iterator.hasNext()) {
      actual = iterator.next();
      if (actual.getId().compareTo(idEstructura) == 0)
        return actual;
    }

    if (logger.isDebugEnabled()) {
      logger.debug("buscarEstructuraPorId(int) - end - return value={null}");
    }
    return null;
  }

}
