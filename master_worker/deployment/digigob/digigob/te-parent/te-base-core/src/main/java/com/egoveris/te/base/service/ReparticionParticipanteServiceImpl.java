package com.egoveris.te.base.service;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.egoveris.te.base.model.ReparticionParticipante;
import com.egoveris.te.base.model.ReparticionParticipanteDTO;
import com.egoveris.te.base.repository.ReparticionParticipanteRepository;

/**
 * @author dpadua
 */
public class ReparticionParticipanteServiceImpl implements ReparticionParticipanteService {
  private static final Logger logger = LoggerFactory
      .getLogger(ReparticionParticipanteServiceImpl.class);

  @Autowired
  private ReparticionParticipanteRepository reparticionParticipanteDao;
  private DozerBeanMapper mapper = new DozerBeanMapper();

  public void grabarReparticionParticipante(ReparticionParticipanteDTO reparticion) {
    if (logger.isDebugEnabled()) {
      logger.debug("grabarReparticionParticipante(reparticion={}) - start", reparticion);
    }

    ReparticionParticipante reparEnty = mapper.map(reparticion, ReparticionParticipante.class);

    reparticionParticipanteDao.save(reparEnty);

    if (logger.isDebugEnabled()) {
      logger.debug("grabarReparticionParticipante(ReparticionParticipante) - end");
    }
  }

  public void eliminarReparticionParticipante(ReparticionParticipanteDTO reparticion) {
    if (logger.isDebugEnabled()) {
      logger.debug("eliminarReparticionParticipante(reparticion={}) - start", reparticion);
    }

    ReparticionParticipante reparEnty = mapper.map(reparticion, ReparticionParticipante.class);

    reparticionParticipanteDao.delete(reparEnty);

    if (logger.isDebugEnabled()) {
      logger.debug("eliminarReparticionParticipante(ReparticionParticipante) - end");
    }
  }

}