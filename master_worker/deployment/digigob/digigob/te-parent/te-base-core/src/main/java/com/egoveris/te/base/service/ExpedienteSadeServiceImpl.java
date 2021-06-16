package com.egoveris.te.base.service;

import java.util.List;

import javax.transaction.Transactional;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egoveris.shared.map.ListMapper;
import com.egoveris.te.base.dao.ExpedienteSadeDAO;
import com.egoveris.te.base.model.ExpedienteAsociadoEntDTO;
import com.egoveris.te.base.model.Reparticion;
import com.egoveris.te.base.model.ReparticionDTO;
import com.egoveris.te.base.repository.IReparticionesRepository;
import com.egoveris.te.base.service.expediente.ExpedienteSadeService;

@Service
@Transactional
public class ExpedienteSadeServiceImpl implements ExpedienteSadeService {

  @Autowired
  private IReparticionesRepository reparticionesRepo;

  @Autowired
  ExpedienteSadeDAO expedienteSadeDAO;

  private DozerBeanMapper mapper = new DozerBeanMapper();

  private static final Logger logger = LoggerFactory.getLogger(ExpedienteSadeServiceImpl.class);

  public ExpedienteAsociadoEntDTO obtenerExpedienteSade(String tipoDocumento, Integer anio,
      Integer numero, String reparticionUsuario) {

    ExpedienteAsociadoEntDTO expedienteAsocDTO = expedienteSadeDAO
        .obtenerExpedienteSade(tipoDocumento, anio, numero, reparticionUsuario);

    return expedienteAsocDTO;
  }

  public String obtenerCodigoTrataSADE(ExpedienteAsociadoEntDTO expedienteAsociado) {

    String codigoTrataSade = expedienteSadeDAO.obtenerCodigoTrataSADE(expedienteAsociado);

    return codigoTrataSade;

  }

  @Override
  public List<ReparticionDTO> buscarReparticiones(int idEstructura) {
    if (logger.isDebugEnabled()) {
      logger.debug("buscarReparticiones(idEstructura={}) - start", idEstructura);
    }

    List<Reparticion> entidad = reparticionesRepo.findById(idEstructura);
    List<ReparticionDTO> returnList = ListMapper.mapList(entidad, mapper, ReparticionDTO.class);

    if (logger.isDebugEnabled()) {
      logger.debug("buscarReparticiones(int) - end - return value={}", returnList);
    }
    return returnList;
  }

}
