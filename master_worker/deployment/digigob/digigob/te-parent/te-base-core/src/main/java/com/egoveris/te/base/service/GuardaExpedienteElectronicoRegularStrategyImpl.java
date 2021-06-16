package com.egoveris.te.base.service;

import javax.transaction.Transactional;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egoveris.deo.model.exception.DataAccessLayerException;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.expediente.ExpedienteElectronico;
import com.egoveris.te.base.repository.expediente.ExpedienteElectronicoRepository;

@Service
@Transactional
public class GuardaExpedienteElectronicoRegularStrategyImpl
    implements GuardaExpedienteElectronicoStrategy {

  private static transient Logger logger = LoggerFactory
      .getLogger(GuardaExpedienteElectronicoRegularStrategyImpl.class);

  @Autowired
  ExpedienteElectronicoRepository expedienteElectronicoRepository;

  private DozerBeanMapper mapper = new DozerBeanMapper();

  @Override
  public ExpedienteElectronicoDTO guardar(ExpedienteElectronicoDTO o) throws DataAccessLayerException {
    if (logger.isDebugEnabled()) {
      logger.debug("guardar(o={}) - start", o);
    }

    ExpedienteElectronico entidad = mapper.map(o, ExpedienteElectronico.class);
    expedienteElectronicoRepository.save(entidad);
    o = mapper.map(entidad, ExpedienteElectronicoDTO.class);
    // Se guarda en sesion el ID
    //Executions.getCurrent().getSession().getAttributes().put(Constantes.ID_EE_GUARDADO, entidad.getId());

    if (logger.isDebugEnabled()) {
      logger.debug("saveOrUpdate", o);
    }
    return o;
  }

}