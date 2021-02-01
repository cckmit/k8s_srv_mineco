package com.egoveris.edt.base.service.impl;

import java.util.List;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.edt.base.model.eu.JurisdiccionDTO;
import com.egoveris.edt.base.repository.eu.JurisdiccionRepository;
import com.egoveris.edt.base.service.IJurisdiccionService;
import com.egoveris.shared.map.ListMapper;

@Service("jurisdiccionService")
@Transactional
public class JurisdiccionServiceImpl implements IJurisdiccionService {

  private DozerBeanMapper mapper = new DozerBeanMapper();

  @Autowired
  private JurisdiccionRepository jurisdiccionrepository;

  @SuppressWarnings("unchecked")
  @Override
  public List<JurisdiccionDTO> getAll() {
    return ListMapper.mapList(jurisdiccionrepository.findAll(), mapper, JurisdiccionDTO.class);
  }

}
