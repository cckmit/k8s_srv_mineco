package com.egoveris.te.base.service;

import java.util.List;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.ffdd.model.exception.DynFormException;
import com.egoveris.shared.map.ListMapper;
import com.egoveris.te.base.model.expediente.ExpedienteFormulario;
import com.egoveris.te.base.repository.IExpedienteFormularioRepository;
import com.egoveris.te.base.service.iface.IExpedienteFormularioService;
import com.egoveris.te.model.model.ExpedienteFormularioDTO;

@Service("expedienteFormularioService")
public class ExpedienteFormularioServiceImpl implements IExpedienteFormularioService {

  private static transient Logger logger = LoggerFactory
      .getLogger(ExpedienteFormularioServiceImpl.class);

  @Autowired
  private IExpedienteFormularioRepository expedienteFormularioRepository;

  private DozerBeanMapper mapper = new DozerBeanMapper();

  @Override
  public List<ExpedienteFormularioDTO> buscarFormulariosPorExpediente(
      final ExpedienteFormularioDTO expedienteFormulario) throws DynFormException {
    if (logger.isDebugEnabled()) {
      logger.debug("buscarFormulariosPorExpediente(expedienteFormulario={}) - start",
          expedienteFormulario);
    }

    List<ExpedienteFormulario> entidades = expedienteFormularioRepository
        .findByIdEeExpedientOrderByDateCrationDesc(expedienteFormulario.getIdEeExpedient());
    List<ExpedienteFormularioDTO> forms = ListMapper.mapList(entidades, mapper,
        ExpedienteFormularioDTO.class);
    if (logger.isDebugEnabled()) {
      logger.debug("buscarFormulariosPorExpediente(ExpedienteFormulario) - end - return value={}",
          forms);
    }
    return forms;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void eliminarFormulario(final ExpedienteFormularioDTO expedienteFormulario)
      throws DynFormException {
    if (logger.isDebugEnabled()) {
      logger.debug("eliminarFormulario(expedienteFormulario={}) - start", expedienteFormulario);
    }

    ExpedienteFormulario entidad = mapper.map(expedienteFormulario, ExpedienteFormulario.class);
    expedienteFormularioRepository.delete(entidad);

    if (logger.isDebugEnabled()) {
      logger.debug("eliminarFormulario(ExpedienteFormulario) - end");
    }
  }

  @Override
  @Transactional
  public Boolean guardarFormulario(final ExpedienteFormularioDTO expedienteFormulario)
      throws DynFormException {
    if (logger.isDebugEnabled()) {
      logger.debug("guardarFormulario(expedienteFormulario={}) - start", expedienteFormulario);
    }

    Boolean susses = false;
    try {
      ExpedienteFormulario entidad = mapper.map(expedienteFormulario, ExpedienteFormulario.class);
      expedienteFormularioRepository.save(entidad);
      susses = true;
    } catch (final Exception e) {
      logger.error("Error guardando el formulario", e);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("guardarFormulario(ExpedienteFormulario) - end - return value={}", susses);
    }
    return susses;
  }

  /**
   * Marca como definitivos todos los ExpedienteFormulario del id pasado por
   * parametro.
   *
   * @param idExpediente
   */
  @Override
  public void makeDefinitive(final Long idExpediente) {
    if (logger.isDebugEnabled()) {
      logger.debug("makeDefinitive(idExpediente={}) - start", idExpediente);
    }
    
    expedienteFormularioRepository.makeDefinitive(1, idExpediente);

    if (logger.isDebugEnabled()) {
      logger.debug("makeDefinitive(Integer) - end");
    }
  }

}
