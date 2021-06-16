package com.egoveris.te.base.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egoveris.sharedorganismo.base.model.ReparticionBean;
import com.egoveris.sharedorganismo.base.service.ReparticionServ;

@Service
@Transactional
public class ObtenerReparticionServicesImpl implements ObtenerReparticionServices {
  private static final Logger logger = LoggerFactory
      .getLogger(ObtenerReparticionServicesImpl.class);

  private List<ReparticionBean> listaReparticionSADEVigentesActivas;
  @Autowired
  private ReparticionServ reparticionServ;

  public List<ReparticionBean> buscarReparticionesVigentesActivas() {
    if (logger.isDebugEnabled()) {
      logger.debug("buscarReparticionesVigentesActivas() - start");
    }

    if (listaReparticionSADEVigentesActivas == null) {
      synchronized (this) {
        // TODO hvogelva: CORREGIR EN REPARTICIONSERV 
        reparticionServ.buscarTodasLasReparticiones(); // WORKAROUND
        List<ReparticionBean> tmpList = reparticionServ.buscarReparticionesVigentesActivas();
        Collections.sort(tmpList, new ReparticionComparatorPorIdEstructura());
        this.listaReparticionSADEVigentesActivas = tmpList;
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("buscarReparticionesVigentesActivas() - end - return value={}",
          listaReparticionSADEVigentesActivas);
    }
    return listaReparticionSADEVigentesActivas;
  }

  public boolean validarCodigoReparticion(String value) {
    if (logger.isDebugEnabled()) {
      logger.debug("validarCodigoReparticion(value={}) - start", value);
    }

    boolean returnboolean = this.reparticionServ.validarCodigoReparticion(value);
    if (logger.isDebugEnabled()) {
      logger.debug("validarCodigoReparticion(String) - end - return value={}", returnboolean);
    }
    return returnboolean;
  }

  public ReparticionBean buscarReparticionByUsuario(String userName) {
    if (logger.isDebugEnabled()) {
      logger.debug("buscarReparticionByUsuario(userName={}) - start", userName);
    }

    ReparticionBean returnReparticionBean = this.reparticionServ
        .buscarReparticionPorUsuario(userName);
    if (logger.isDebugEnabled()) {
      logger.debug("buscarReparticionByUsuario(String) - end - return value={}",
          returnReparticionBean);
    }
    return returnReparticionBean;
  }

  public ReparticionBean getReparticionBycodigoReparticion(String codigoReparticion) {
    if (logger.isDebugEnabled()) {
      logger.debug("getReparticionBycodigoReparticion(codigoReparticion={}) - start",
          codigoReparticion);
    }

    ReparticionBean returnReparticionBean = this.reparticionServ
        .buscarReparticionPorCodigo(codigoReparticion);
    if (logger.isDebugEnabled()) {
      logger.debug("getReparticionBycodigoReparticion(String) - end - return value={}",
          returnReparticionBean);
    }
    return returnReparticionBean;
  }

  public ReparticionBean getReparticionById(Long idReparticion) {
    if (logger.isDebugEnabled()) {
      logger.debug("getReparticionById(idReparticion={}) - start", idReparticion);
    }

    ReparticionBean returnReparticionBean = this.reparticionServ
        .buscarReparticionPorId(idReparticion);
    if (logger.isDebugEnabled()) {
      logger.debug("getReparticionById(int) - end - return value={}", returnReparticionBean);
    }
    return returnReparticionBean;
  }
}

class ReparticionComparatorPorIdEstructura implements Comparator<ReparticionBean> {
  private static final Logger logger = LoggerFactory
      .getLogger(ReparticionComparatorPorIdEstructura.class);

  public int compare(ReparticionBean o1, ReparticionBean o2) {
    if (logger.isDebugEnabled()) {
      logger.debug("compare(o1={}, o2={}) - start", o1, o2);
    }

    Integer idEstructuraReparticion1 = o1.getIdEstructura();
    Integer idEstructuraReparticion2 = o2.getIdEstructura();

    int returnint = idEstructuraReparticion1.compareTo(idEstructuraReparticion2);
    if (logger.isDebugEnabled()) {
      logger.debug("compare(ReparticionBean, ReparticionBean) - end - return value={}", returnint);
    }
    return returnint;
  }
}
