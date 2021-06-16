package com.egoveris.te.base.service;

import javax.transaction.Transactional;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egoveris.te.base.model.tipo.TipoReserva;
import com.egoveris.te.base.repository.tipo.TipoReservaRepository;
import com.egoveris.te.model.model.TipoReservaDTO;

@Service
@Transactional
public class TipoReservaServiceImpl implements TipoReservaService {
  private static final Logger logger = LoggerFactory.getLogger(TipoReservaServiceImpl.class);

  @Autowired
  private TipoReservaRepository tipoReservaDAO;

  private DozerBeanMapper mapper = new DozerBeanMapper();

  public TipoReservaDTO buscarTipoReserva(String tipoReserva) {
    if (logger.isDebugEnabled()) {
      logger.debug("buscarTipoReserva(tipoReserva={}) - start", tipoReserva);
    }

    TipoReserva tipoEnt = tipoReservaDAO.findByTipoReserva(tipoReserva);

    TipoReservaDTO returnTipoReserva = mapper.map(tipoEnt, TipoReservaDTO.class);

    if (logger.isDebugEnabled()) {
      logger.debug("buscarTipoReserva(String) - end - return value={}", returnTipoReserva);
    }
    return returnTipoReserva;
  }
}
