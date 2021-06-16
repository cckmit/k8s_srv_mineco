package com.egoveris.edt.base.service.impl.actuacion;

import javax.transaction.Transactional;

import org.dozer.DozerBeanMapper;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egoveris.edt.base.exception.NegocioException;
import com.egoveris.edt.base.model.eu.actuacion.ActuacionAudi;
import com.egoveris.edt.base.model.eu.actuacion.ActuacionAudiDTO;
import com.egoveris.edt.base.repository.eu.actuacion.ActuacionAudiRepository;
import com.egoveris.edt.base.service.actuacion.IActuacionAudiService;

@Service("actuacionAudiService")
@Transactional
public class ActuacionAudiServiceImpl implements IActuacionAudiService {

  private DozerBeanMapper mapper = new DozerBeanMapper();

  @Autowired
  private ActuacionAudiRepository actuacionRepository;

  @Override
  public void guardarActuacionAudi(ActuacionAudiDTO actuacionAudi) throws NegocioException {

    try {
      actuacionRepository.save(mapper.map(actuacionAudi, ActuacionAudi.class));
    } catch (ConstraintViolationException ex) {
      throw new NegocioException(
          "Error al generar el ID de la actuacion a guardar. Ya existe el mismo", ex);
    }

  }
}