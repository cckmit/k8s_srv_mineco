package com.egoveris.te.base.service;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.te.base.model.ExpedienteAsociadoEntDTO;
import com.egoveris.te.base.model.expediente.ExpedienteAsociado;
import com.egoveris.te.base.repository.expediente.ExpedienteAsociadoRepository;
import com.egoveris.te.base.service.expediente.ExpedienteAsociadoService;

@Service
@Transactional
public class ExpedienteAsociadoServiceImpl implements ExpedienteAsociadoService {

  private static final Logger logger = LoggerFactory
      .getLogger(ExpedienteAsociadoServiceImpl.class);
  private DozerBeanMapper mapper = new DozerBeanMapper();

  @Autowired
  private ExpedienteAsociadoRepository expedienteAsociadoRepository;

  @Transactional
  public void deleteExpedienteAsociado(ExpedienteAsociadoEntDTO expedienteAsociado) {

    ExpedienteAsociado entidad = mapper.map(expedienteAsociado, ExpedienteAsociado.class);
    this.expedienteAsociadoRepository.delete(entidad);
  }

  public ExpedienteAsociadoEntDTO obtenerExpedienteAsociadoPorTC(Integer numero, Integer anio,
      boolean asociadoTC) {
    ExpedienteAsociadoEntDTO expedienteAsociadoDto = null;
    try {
      ExpedienteAsociado expedienteAsociadoEnt = expedienteAsociadoRepository
          .findByNumeroAndAnioAndEsExpedienteAsociadoTC(numero, anio, asociadoTC);
      expedienteAsociadoDto = mapper.map(expedienteAsociadoEnt, ExpedienteAsociadoEntDTO.class);

    } catch (Exception e) {
      logger.error("Error al buscar expediente asociado", e);
    }

    return expedienteAsociadoDto;

  }

  @Override
  public ExpedienteAsociadoEntDTO obtenerExpedienteAsociadoPorFusion(Integer stNumeroSADE,
      Integer stAnioSADE, boolean asociadoFusion) {
    try {
      ExpedienteAsociado expedienteAsociadoEnt = expedienteAsociadoRepository
          .findByNumeroAndAnioAndEsExpedienteAsociadoFusion(stNumeroSADE, stAnioSADE,
              asociadoFusion);
      if (null != expedienteAsociadoEnt) {
        return mapper.map(expedienteAsociadoEnt, ExpedienteAsociadoEntDTO.class);
      }
    } catch (Exception e) {
      logger.error("Error al buscar expediente asociado", e);
    }
    return null;
  }

  public ExpedienteAsociadoRepository getExpedienteAsociadoRepository() {
    return expedienteAsociadoRepository;
  }

  public void setExpedienteAsociadoRepository(
      ExpedienteAsociadoRepository expedienteAsociadoRepository) {
    this.expedienteAsociadoRepository = expedienteAsociadoRepository;
  }

  @Override
  public void actualizarExpedienteAsociado(ExpedienteAsociadoEntDTO expedienteAsociado) {

    ExpedienteAsociado entidad = mapper.map(expedienteAsociado, ExpedienteAsociado.class);
    this.expedienteAsociadoRepository.save(entidad);
  }

}
