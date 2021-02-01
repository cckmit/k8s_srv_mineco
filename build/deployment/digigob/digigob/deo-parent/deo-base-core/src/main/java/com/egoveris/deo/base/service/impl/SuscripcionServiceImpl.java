package com.egoveris.deo.base.service.impl;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.deo.base.model.Suscripcion;
import com.egoveris.deo.base.repository.SuscripcionRepository;
import com.egoveris.deo.base.service.SuscripcionService;
import com.egoveris.deo.model.model.SuscripcionDTO;

@Service
@Transactional
public class SuscripcionServiceImpl implements SuscripcionService {

  @Autowired
  private SuscripcionRepository suscripcionRepo;

  private DozerBeanMapper mapper = new DozerBeanMapper();

  
  @Override
  public void save(SuscripcionDTO suscripcion) {
    Suscripcion entSubcr =  this.mapper.map(suscripcion, Suscripcion.class);
    this.suscripcionRepo.save(entSubcr);
  }

  
}
