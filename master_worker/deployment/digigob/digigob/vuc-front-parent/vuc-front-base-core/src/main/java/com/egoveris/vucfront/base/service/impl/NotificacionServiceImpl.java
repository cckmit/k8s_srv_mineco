package com.egoveris.vucfront.base.service.impl;

import com.egoveris.shared.map.ListMapper;
import com.egoveris.vucfront.base.model.ExpedienteBase;
import com.egoveris.vucfront.base.model.Notificacion;
import com.egoveris.vucfront.base.model.Persona;
import com.egoveris.vucfront.base.repository.NotificacionRepository;
import com.egoveris.vucfront.model.model.DocumentoDTO;
import com.egoveris.vucfront.model.model.ExpedienteBaseDTO;
import com.egoveris.vucfront.model.model.NotificacionDTO;
import com.egoveris.vucfront.model.model.PersonaDTO;
import com.egoveris.vucfront.model.service.ExpedienteService;
import com.egoveris.vucfront.model.service.NotificacionService;
import com.egoveris.vucfront.model.util.BusinessFormatHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class NotificacionServiceImpl implements NotificacionService {
  @Autowired
  @Qualifier("vucMapper")
  private Mapper mapper;

  @Autowired
  private NotificacionRepository notificacionRepository;
  @Autowired
  private ExpedienteService expedienteService;

  @Override
  public void altaNotificacionTAD(String codSadeExpediente, DocumentoDTO documento,
      String motivo) {
    ExpedienteBaseDTO expediente = expedienteService.getExpedienteBaseByCodigo(codSadeExpediente);
    ExpedienteBase expedienteEntity = mapper.map(expediente, ExpedienteBase.class);
    /*TODO
     * 
     * REVISAR PARA SACAR CODSADE
     * */

    Notificacion notificacion = new Notificacion();
    notificacion.setCodSade(documento.getNumeroDocumento());
    notificacion.setMotivo(motivo);
    notificacion.setExpediente(expedienteEntity);
    notificacion.setPersona(expedienteEntity.getPersona());
    notificacion.setFechaNotificacion(new Date());
    notificacion.setUsuarioCreacion(documento.getUsuarioCreacion());
    notificacion.setNotificado(false);
    notificacion.setVersion(1l);

    notificacionRepository.save(notificacion);
  }

  @SuppressWarnings("unchecked")
  @Override
  public List<NotificacionDTO> getNotificacionesByPersona(PersonaDTO persona) {
    List<NotificacionDTO> retorno = new ArrayList<>();
    List<Notificacion> result = notificacionRepository
        .findByPersonaOrderByFechaNotificacionDesc(
            mapper.map(persona, Persona.class));
    if (result != null) {
      retorno = ListMapper.mapList(result, mapper, NotificacionDTO.class);
    }
    return retorno;
  }

  @Override
  public Boolean isDocumentNotified(String codSadeExpediente, String codSadeDocumento) {
    ExpedienteBaseDTO expediente = expedienteService.getExpedienteBaseByCodigo(codSadeExpediente);
    Notificacion result = notificacionRepository.findByExpedienteAndCodSade(
        mapper.map(expediente, ExpedienteBase.class), codSadeDocumento);
    if (result == null) {
      return false;
    }
    return true;
  }

  @Override
  public NotificacionDTO getNotificacionById(Long id) {
    Notificacion result = notificacionRepository.findOne(id);
    if (result != null) {
      return mapper.map(result, NotificacionDTO.class);
    }
    return null;
  }

  @Override
  public void setNotificacionAsViewed(NotificacionDTO notificacion) {
    notificacion.setNotificado(true);
    Notificacion updatedNotificacion = mapper.map(notificacion, Notificacion.class);
    notificacionRepository.save(updatedNotificacion);
  }

  @Override
  public Integer getUnseenNotifications(PersonaDTO persona) {
    Integer unseenNotifications = 0;
    List<NotificacionDTO> result = this.getNotificacionesByPersona(persona);
    for (NotificacionDTO aux : result) {
      if (!aux.getNotificado()) {
        unseenNotifications++;
      }
    }
    if (unseenNotifications > 0) {
      return unseenNotifications;
    }

    return null;
  }

}