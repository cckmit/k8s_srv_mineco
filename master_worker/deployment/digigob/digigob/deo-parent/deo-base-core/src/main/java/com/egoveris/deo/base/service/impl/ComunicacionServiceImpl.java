package com.egoveris.deo.base.service.impl;

import com.egoveris.deo.base.model.Comunicacion;
import com.egoveris.deo.base.repository.ComunicacionRepository;
import com.egoveris.deo.base.service.ComunicacionService;
import com.egoveris.deo.model.model.ComunicacionDTO;
import com.egoveris.shared.map.ListMapper;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ComunicacionServiceImpl implements ComunicacionService {
  /**
   * Logger for this class
   */
  private static final Logger logger = LoggerFactory.getLogger(ComunicacionServiceImpl.class);

  @Autowired
  private ComunicacionRepository comunicacionRepo;
  @Autowired
  @Qualifier("deoCoreMapper")
  private Mapper mapper;

  @Override
  public void guardarComunicacion(ComunicacionDTO comunicacion) {
    if (logger.isDebugEnabled()) {
      logger.debug("guardarComunicacion(ComunicacionDTO) - start"); //$NON-NLS-1$
    }
    Comunicacion comuEnti = mapper.map(comunicacion, Comunicacion.class);
    this.comunicacionRepo.save(comuEnti);

    if (logger.isDebugEnabled()) {
      logger.debug("guardarComunicacion(ComunicacionDTO) - end"); //$NON-NLS-1$
    }
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<ComunicacionDTO> buscarComunicacionesEnviadasPorUsuario(
      Map<String, Object> parametrosConsulta) {
    if (logger.isDebugEnabled()) {
      logger.debug("buscarComunicacionesEnviadasPorUsuario(Map<String,Object>) - start"); //$NON-NLS-1$
    }

    List<ComunicacionDTO> returnList = ListMapper.mapList(this.comunicacionRepo.findByUsuarioCreadorAndFechaEliminacionIsNullOrderByFechaCreacion((String) parametrosConsulta.get("usuario")), mapper, ComunicacionDTO.class);
    if (logger.isDebugEnabled()) {
      logger.debug("buscarComunicacionesEnviadasPorUsuario(Map<String,Object>) - end"); //$NON-NLS-1$
    }
    return returnList;
  }

  @Override
  public Boolean validarComunicacionContinuada(Integer id, String usuario) {
    if (logger.isDebugEnabled()) {
      logger.debug("validarComunicacionContinuada(Integer, String) - start"); //$NON-NLS-1$
    }

    Comunicacion result = comunicacionRepo.findByUsuarioCreadorAndIdComunicacion(usuario, id);
    if (result != null) {
      if (logger.isDebugEnabled()) {
        logger.debug("validarComunicacionContinuada(Integer, String) - end"); //$NON-NLS-1$
      }
      return true;
    }

    if (logger.isDebugEnabled()) {
      logger.debug("validarComunicacionContinuada(Integer, String) - end"); //$NON-NLS-1$
    }
    return false;
  }

  @Override
  public Integer numeroComunicacionesEnviadasPorUsuario(String usuario) {
    if (logger.isDebugEnabled()) {
      logger.debug("numeroComunicacionesEnviadasPorUsuario(String) - start"); //$NON-NLS-1$
    }

    Integer returnInteger = this.comunicacionRepo.findByUsuarioCreadorAndFechaEliminacionIsNull(usuario).size();
    if (logger.isDebugEnabled()) {
      logger.debug("numeroComunicacionesEnviadasPorUsuario(String) - end"); //$NON-NLS-1$
    }
    return returnInteger;
  }

  @Override
  public Integer numeroComunicacionesEnviadasPorFecha(Date fechaDesde, Date fechaHasta,
      String usuarioActual) {
    if (logger.isDebugEnabled()) {
      logger.debug("numeroComunicacionesEnviadasPorFecha(Date, Date, String) - start"); //$NON-NLS-1$
    }

    Integer returnInteger = this.comunicacionRepo.findByUsuarioCreadorAndFechaCreacionBetween(usuarioActual, fechaDesde, fechaHasta).size();
    if (logger.isDebugEnabled()) {
      logger.debug("numeroComunicacionesEnviadasPorFecha(Date, Date, String) - end"); //$NON-NLS-1$
    }
    return returnInteger;
  }

  @Override
  public Integer numeroComunicacionesEnviadasPorReferencia(String referencia,
      String usuarioActual) {
    if (logger.isDebugEnabled()) {
      logger.debug("numeroComunicacionesEnviadasPorReferencia(String, String) - start"); //$NON-NLS-1$
    }

    Integer returnInteger = this.comunicacionRepo.buscarComunicacionesEnviadasPorReferencia(referencia, usuarioActual).size();
    if (logger.isDebugEnabled()) {
      logger.debug("numeroComunicacionesEnviadasPorReferencia(String, String) - end"); //$NON-NLS-1$
    }
    return returnInteger;
  }

  @Override
  public ComunicacionDTO buscarComunicacionPorId(Integer id) {
    if (logger.isDebugEnabled()) {
      logger.debug("buscarComunicacionPorId(Integer) - start"); //$NON-NLS-1$
    }

    ComunicacionDTO returnComunicacionDTO = this.mapper.map(this.comunicacionRepo.findById(id), ComunicacionDTO.class);
    if (logger.isDebugEnabled()) {
      logger.debug("buscarComunicacionPorId(Integer) - end"); //$NON-NLS-1$
    }
    return returnComunicacionDTO;
  }

  @Override
  public void eliminarComunicacionesEnviadas(String usuario) {
    if (logger.isDebugEnabled()) {
      logger.debug("eliminarComunicacionesEnviadas(String) - start"); //$NON-NLS-1$
    }

    this.comunicacionRepo.eliminarComunicacionesEnviadas(Calendar.getInstance().getTime(),
        usuario);

    if (logger.isDebugEnabled()) {
      logger.debug("eliminarComunicacionesEnviadas(String) - end"); //$NON-NLS-1$
    }
  }

  @Override
  public void actualizarComunicacion(ComunicacionDTO comunicacion) {
    if (logger.isDebugEnabled()) {
      logger.debug("actualizarComunicacion(ComunicacionDTO) - start"); //$NON-NLS-1$
    }

    this.comunicacionRepo.save(this.mapper.map(comunicacion, Comunicacion.class));

    if (logger.isDebugEnabled()) {
      logger.debug("actualizarComunicacion(ComunicacionDTO) - end"); //$NON-NLS-1$
    }
  }

  @Override
  public List<ComunicacionDTO> buscarComunicacionPorFechaEnviados(Date fechaDesde, Date fechaHasta,
      String usuarioActual, Map<String, Object> parametrosConsulta) {
    if (logger.isDebugEnabled()) {
      logger.debug("buscarComunicacionPorFechaEnviados(Date, Date, String, Map<String,Object>) - start"); //$NON-NLS-1$
    }

    List<ComunicacionDTO> returnList = ListMapper.mapList(this.comunicacionRepo.findByUsuarioCreadorAndFechaCreacionBetweenOrderByFechaCreacion(usuarioActual, fechaDesde, fechaHasta), this.mapper, ComunicacionDTO.class);
    if (logger.isDebugEnabled()) {
      logger.debug("buscarComunicacionPorFechaEnviados(Date, Date, String, Map<String,Object>) - end"); //$NON-NLS-1$
    }
    return returnList;
  }

  @Override
  public ComunicacionDTO buscarComunicacionPorCaratula(String numeroDocumento) {
    if (logger.isDebugEnabled()) {
      logger.debug("buscarComunicacionPorCaratula(String) - start"); //$NON-NLS-1$
    }

    ComunicacionDTO returnComunicacionDTO = this.mapper.map(this.comunicacionRepo.buscarComunicacionPorCaratula(numeroDocumento), ComunicacionDTO.class);
    if (logger.isDebugEnabled()) {
      logger.debug("buscarComunicacionPorCaratula(String) - end"); //$NON-NLS-1$
    }
    return returnComunicacionDTO;

  }

  @Override
  public List<ComunicacionDTO> buscarComunicacionPorReferenciaEnviados(String referencia,
      String usuario, Map<String, Object> parametrosConsulta) {
    if (logger.isDebugEnabled()) {
      logger.debug("buscarComunicacionPorReferenciaEnviados(String, String, Map<String,Object>) - start"); //$NON-NLS-1$
    }

    List<ComunicacionDTO> returnList = ListMapper.mapList(this.comunicacionRepo.buscarComunicacionesPorReferenciaEnviados(referencia, usuario), this.mapper, ComunicacionDTO.class);
    if (logger.isDebugEnabled()) {
      logger.debug("buscarComunicacionPorReferenciaEnviados(String, String, Map<String,Object>) - end"); //$NON-NLS-1$
    }
    return returnList;

  }

}
