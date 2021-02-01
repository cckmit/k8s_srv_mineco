package com.egoveris.deo.base.service.impl;

import com.egoveris.deo.base.service.ObtenerReparticionServices;
import com.egoveris.sharedorganismo.base.model.ReparticionBean;
import com.egoveris.sharedorganismo.base.service.ReparticionServ;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ObtenerReparticionServicesImpl implements ObtenerReparticionServices {
  /**
   * Logger for this class
   */
  private static final Logger LOGGER = LoggerFactory
      .getLogger(ObtenerReparticionServicesImpl.class);

  private List<ReparticionBean> listaReparticionSADECompleta;
  private List<ReparticionBean> listaReparticionSADEVigentesActivas;
  @Autowired
  private ReparticionServ reparticionServ;

  public List<ReparticionBean> buscarReparticiones() {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarReparticiones() - start"); //$NON-NLS-1$
    }

    synchronized (this) {
      if (listaReparticionSADECompleta == null || listaReparticionSADECompleta.isEmpty()) {
        List<ReparticionBean> tmpList = reparticionServ.buscarTodasLasReparticiones();
        Collections.sort(tmpList, new ReparticionComparator());
        this.listaReparticionSADECompleta = tmpList;
      }
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarReparticiones() - end"); //$NON-NLS-1$
    }
    return listaReparticionSADECompleta;
  }

  public List<ReparticionBean> buscarReparticionesVigentesActivas() {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarReparticionesVigentesActivas() - start"); //$NON-NLS-1$
    }

    synchronized (this) {
      if (listaReparticionSADEVigentesActivas == null) {
        List<ReparticionBean> tmpList = reparticionServ.buscarReparticionesVigentesActivas();
        Collections.sort(tmpList, new ReparticionComparatorPorIdEstructura());
        this.listaReparticionSADEVigentesActivas = tmpList;
      }
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarReparticionesVigentesActivas() - end"); //$NON-NLS-1$
    }
    return listaReparticionSADEVigentesActivas;
  }

  public boolean validarCodigoReparticion(String value) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("validarCodigoReparticion(String) - start"); //$NON-NLS-1$
    }

    boolean returnboolean = this.reparticionServ.validarCodigoReparticion(value);
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("validarCodigoReparticion(String) - end"); //$NON-NLS-1$
    }
    return returnboolean;
  }

  public ReparticionBean buscarReparticionByUsuario(String userName) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarReparticionByUsuario(String) - start"); //$NON-NLS-1$
    }

    ReparticionBean returnReparticionBean = this.reparticionServ
        .buscarReparticionPorUsuario(userName);
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("buscarReparticionByUsuario(String) - end"); //$NON-NLS-1$
    }
    return returnReparticionBean;
  }

  public ReparticionBean getReparticionBycodigoReparticion(String codigoReparticion) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("getReparticionBycodigoReparticion(String) - start"); //$NON-NLS-1$
    }

    ReparticionBean returnReparticionBean = this.reparticionServ
        .buscarReparticionPorCodigo(codigoReparticion);
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("getReparticionBycodigoReparticion(String) - end"); //$NON-NLS-1$
    }
    return returnReparticionBean;
  }

  public ReparticionBean getReparticionById(Long idReparticion) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("getReparticionById(int) - start"); //$NON-NLS-1$
    }

    ReparticionBean returnReparticionBean = this.reparticionServ
        .buscarReparticionPorId(idReparticion);
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("getReparticionById(int) - end"); //$NON-NLS-1$
    }
    return returnReparticionBean;
  }
}

class ReparticionComparator implements Comparator<ReparticionBean> {
  /**
   * Logger for this class
   */
  private static final Logger logger = LoggerFactory.getLogger(ReparticionComparator.class);

  @Override
  public int compare(ReparticionBean o1, ReparticionBean o2) {
    if (logger.isDebugEnabled()) {
      logger.debug("compare(ReparticionBean, ReparticionBean) - start"); //$NON-NLS-1$
    }

    String codigoReparticion1 = o1.getCodigo();
    String codigoReparticion2 = o2.getCodigo();

    int returnint = codigoReparticion1.compareTo(codigoReparticion2);
    if (logger.isDebugEnabled()) {
      logger.debug("compare(ReparticionBean, ReparticionBean) - end"); //$NON-NLS-1$
    }
    return returnint;
  }
}

class ReparticionComparatorPorIdEstructura implements Comparator<ReparticionBean> {
  /**
   * Logger for this class
   */
  private static final Logger logger = LoggerFactory
      .getLogger(ReparticionComparatorPorIdEstructura.class);

  @Override
  public int compare(ReparticionBean o1, ReparticionBean o2) {
    if (logger.isDebugEnabled()) {
      logger.debug("compare(ReparticionBean, ReparticionBean) - start"); //$NON-NLS-1$
    }

    Integer idEstructuraReparticion1 = o1.getIdEstructura();
    Integer idEstructuraReparticion2 = o2.getIdEstructura();

    int returnint = idEstructuraReparticion1.compareTo(idEstructuraReparticion2);
    if (logger.isDebugEnabled()) {
      logger.debug("compare(ReparticionBean, ReparticionBean) - end"); //$NON-NLS-1$
    }
    return returnint;
  }
}
