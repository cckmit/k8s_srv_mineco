package com.egoveris.deo.base.service.impl;

import com.egoveris.deo.base.repository.SistemaOrigenRepository;
import com.egoveris.deo.base.service.SistemaOrigenService;
import com.egoveris.deo.model.model.SistemaOrigenDTO;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class SistemaOrigenServiceImpl implements SistemaOrigenService {

  @Autowired
  private SistemaOrigenRepository sistemaOrigenRepo;

  @Autowired
  @Qualifier("deoCoreMapper")
  private Mapper mapper;

  @Override
  public SistemaOrigenDTO findByNombreLike(String sistema) {
    return this.mapper.map(this.sistemaOrigenRepo.findByNombreLike(sistema), SistemaOrigenDTO.class);
  }

}
