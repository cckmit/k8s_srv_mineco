package com.egoveris.te.base.service;

import javax.transaction.Transactional;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egoveris.deo.model.exception.DataAccessLayerException;
import com.egoveris.te.base.exception.NegocioException;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.expediente.ExpedienteElectronico;
import com.egoveris.te.base.repository.expediente.ExpedienteElectronicoRepository;
import com.egoveris.te.base.service.iface.ISolrService;

@Service
@Transactional
public class GuardaExpedienteElectronicoIndexSolrStrategyImpl
    implements GuardaExpedienteElectronicoStrategy {

  private static transient Logger logger = LoggerFactory
      .getLogger(GuardaExpedienteElectronicoIndexSolrStrategyImpl.class);

  @Autowired
  private ExpedienteElectronicoRepository expedienteElectronicoRepository;
  @Autowired
  private ISolrService solrService;

  private DozerBeanMapper mapper = new DozerBeanMapper();

  private Exception externalException = null;

  @Override
  public ExpedienteElectronicoDTO guardar(ExpedienteElectronicoDTO o) throws DataAccessLayerException {
    if (logger.isDebugEnabled()) {
      logger.debug("guardar(o={}) - start", o);
    }

    ExpedienteElectronicoDTO expedienteElectronicoDto = (ExpedienteElectronicoDTO) o;

    ExpedienteElectronico expedienteElectronico = mapper.map(expedienteElectronicoDto,
        ExpedienteElectronico.class);
    expedienteElectronicoRepository.save(expedienteElectronico);
    o = (ExpedienteElectronicoDTO) mapper.map(expedienteElectronico, ExpedienteElectronicoDTO.class);
    // Se guarda en sesion el ID
   
    if (logger.isDebugEnabled()) {
      logger.debug("saveOrUpdate", expedienteElectronico);
    }
    indexarExpedienteElectronico(o);
    if (logger.isDebugEnabled()) {
      logger.debug("index", expedienteElectronico);
    }
    if (externalException != null) {
    }

    if (logger.isDebugEnabled()) {
      logger.debug("guardar(Object) - end");
    }
    return o;
  }

  public void indexarExpedienteElectronico(final Object o) {
    if (logger.isDebugEnabled()) {
      logger.debug("indexarExpedienteElectronico(o={}) - start", o);
    }

    new Thread(new Runnable() {
      @Override
      public void run() {
        if (logger.isDebugEnabled()) {
          logger.debug("$Runnable.run() - start");
        }

        try {
          solrService.indexar((ExpedienteElectronicoDTO) o);
        } catch (NegocioException e) {
          logger.info("Error al indexar el expediente eletrónico.", e);
          externalException = new Exception(e);
        }

        if (logger.isDebugEnabled()) {
          logger.debug("$Runnable.run() - end");
        }
      }
    }).start();

    if (logger.isDebugEnabled()) {
      logger.debug("indexarExpedienteElectronico(Object) - end");
    }
  }

}
