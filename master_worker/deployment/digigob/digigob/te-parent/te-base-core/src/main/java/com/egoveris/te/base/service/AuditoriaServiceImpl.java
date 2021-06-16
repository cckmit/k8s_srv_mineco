package com.egoveris.te.base.service;

import java.util.List;

import javax.transaction.Transactional;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egoveris.shared.map.ListMapper;
import com.egoveris.te.base.model.AuditoriaDeConsulta;
import com.egoveris.te.base.model.AuditoriaDeConsultaDTO;
import com.egoveris.te.base.model.AuditoriaNotificacion;
import com.egoveris.te.base.model.AuditoriaNotificacionDTO;
import com.egoveris.te.base.model.TrataAuditoriaDTO;
import com.egoveris.te.base.model.TrataDTO;
import com.egoveris.te.base.model.trata.TrataAuditoria;
import com.egoveris.te.base.repository.AuditoriaDeConsultaRepository;
import com.egoveris.te.base.repository.AuditoriaNotificacionRepository;
import com.egoveris.te.base.repository.trata.TrataAuditoriaRepository;
import com.egoveris.te.base.service.iface.IAuditoriaService;

@Service
@Transactional
public class AuditoriaServiceImpl implements IAuditoriaService {
  private static final Logger logger = LoggerFactory.getLogger(AuditoriaServiceImpl.class);

  @Autowired
  private AuditoriaDeConsultaRepository auditoriaDeConsultaRepository;

  @Autowired
  private AuditoriaNotificacionRepository auditoriaNotificacionRepository;

  @Autowired
  private TrataAuditoriaRepository auditoriaTrataRepository;

  private DozerBeanMapper mapper = new DozerBeanMapper();

  public void grabarAuditoriaDeConsulta(AuditoriaDeConsultaDTO auditoriaDeConsulta) {
    if (logger.isDebugEnabled()) {
      logger.debug("grabarAuditoriaDeConsulta(auditoriaDeConsulta={}) - start",
          auditoriaDeConsulta);
    }
    
    AuditoriaDeConsulta entity = mapper.map(auditoriaDeConsulta, AuditoriaDeConsulta.class);
    auditoriaDeConsultaRepository.save(entity);

    if (logger.isDebugEnabled()) {
      logger.debug("grabarAuditoriaDeConsulta(AuditoriaDeConsulta) - end");
    }
  }

  public AuditoriaDeConsultaRepository getAuditoriaDeConsultaDAO() {
    return auditoriaDeConsultaRepository;
  }

  public void setAuditoriaDeConsultaDAO(AuditoriaDeConsultaRepository auditoriaDeConsultaDAO) {
    this.auditoriaDeConsultaRepository = auditoriaDeConsultaDAO;
  }

  public void grabarAuditoriaNotificacion(AuditoriaNotificacionDTO auditoriaNotificacion) {
    if (logger.isDebugEnabled()) {
      logger.debug("grabarAuditoriaNotificacion(auditoriaNotificacion={}) - start",
          auditoriaNotificacion);
    }
    
    AuditoriaNotificacion entity = mapper.map(auditoriaNotificacion, AuditoriaNotificacion.class);
    auditoriaNotificacionRepository.save(entity);

    if (logger.isDebugEnabled()) {
      logger.debug("grabarAuditoriaNotificacion(AuditoriaNotificacion) - end");
    }
  }

  public AuditoriaNotificacionRepository getAuditoriaNotificacionDAO() {
    return auditoriaNotificacionRepository;
  }

  public void setAuditoriaNotificacionDAO(
      AuditoriaNotificacionRepository auditoriaNotificacionDAO) {
    this.auditoriaNotificacionRepository = auditoriaNotificacionDAO;
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<TrataAuditoriaDTO> buscarHistorialTrata(TrataDTO trata) {
    if (logger.isDebugEnabled()) {
      logger.debug("buscarHistorialTrata(trata={}) - start", trata);
    }
   
    List<TrataAuditoria> returnList = auditoriaTrataRepository
        .findByCodigoTrata(trata.getCodigoTrata());

    if (logger.isDebugEnabled()) {
      logger.debug("buscarHistorialTrata(Trata) - end - return value={}", returnList);
    }
    
    return ListMapper.mapList(returnList, mapper, TrataAuditoriaDTO.class);
  }
  
}