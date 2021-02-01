package com.egoveris.te.base.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.dozer.DozerBeanMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.te.base.dao.ActividadDAO;
import com.egoveris.te.base.model.Actividad;
import com.egoveris.te.base.model.ActividadDTO;
import com.egoveris.te.base.model.ParametroActividad;
import com.egoveris.te.base.model.ParametroActividadDTO;
import com.egoveris.te.base.model.TipoActividad;
import com.egoveris.te.base.model.TipoActividadDTO;
import com.egoveris.te.base.repository.tipo.IActividadRepository;
import com.egoveris.te.base.repository.tipo.TipoActividadRepository;
import com.egoveris.te.base.util.ConstEstadoActividad;
import com.egoveris.te.base.util.ConstTipoActividad;

@Service
@Transactional
public class ActividadServiceImpl implements IActividadService {
  private static final Logger logger = LoggerFactory.getLogger(ActividadServiceImpl.class);
  private DozerBeanMapper mapper = new DozerBeanMapper();

  @Autowired
  private ActividadDAO actividadDAO;
  @Autowired
  private IActividadRepository actiRepo;
  @Autowired
  private TipoActividadRepository tipoActividadDAO;

  @Transactional(propagation = Propagation.REQUIRED)
  public List<ActividadDTO> buscarActividadesVigentes(String idObjetivo) {
    logger.debug("buscarActividadesVigentes params - idObjetivo: " + idObjetivo);
    List<ActividadDTO> retorno = new ArrayList<>();
    List<Actividad> result = actividadDAO.buscarActividadesVigentes(idObjetivo);

    if (result != null && !result.isEmpty()) {
      for (Actividad aux : result) {
        retorno.add(convertActividadFromEntityToDto(aux));
      }
    }

    return retorno;
  }

  public List<ActividadDTO> buscarActividadesPorEstado(String estado) {
    List<ActividadDTO> retorno = new ArrayList<>();
    List<Actividad> result = actividadDAO.buscarActividadPorEstado(estado);

    if (result != null && !result.isEmpty()) {
      for (Actividad aux : result) {
        retorno.add(convertActividadFromEntityToDto(aux));
      }
    }

    return retorno;
  }

  public List<ActividadDTO> buscarActividadesVigentes(List<String> idObjetivos) {
    logger.debug("buscarActividadesVigentes params - idObjetivos: " + idObjetivos.toString());
    List<ActividadDTO> retorno = new ArrayList<>();
    List<Actividad> result = actividadDAO.buscarActividadesVigentes(idObjetivos);

    if (result != null && !result.isEmpty()) {
      for (Actividad aux : result) {
        retorno.add(convertActividadFromEntityToDto(aux));
      }
    }

    return retorno;
  }

  public List<ActividadDTO> buscarHistoricoActividades(String idObj) {
    logger.debug("buscarHistoricoActividades params - idObjetivo: " + idObj);
    List<ActividadDTO> retorno = new ArrayList<>();
    List<Actividad> result = actiRepo.buscarHistoricoActividades(idObj);

    if (result != null && !result.isEmpty()) {
      for (Actividad aux : result) {
        retorno.add(convertActividadFromEntityToDto(aux));
      }
    }

    return retorno;
  }

  public List<ActividadDTO> buscarSubActividades(ActividadDTO actividad) {
    logger.debug("buscarSubActividades params - actividad: " + actividad.getId());
    List<ActividadDTO> retorno = new ArrayList<>();
    List<Actividad> result = actividadDAO.buscarSubActividades(actividad.getId());

    if (result != null && !result.isEmpty()) {
      for (Actividad aux : result) {
        retorno.add(convertActividadFromEntityToDto(aux));
      }
    }

    return retorno;
  }

  public List<ActividadDTO> buscarSubActividades(Long actividadId) {
    logger.debug("buscarSubActividades params - id actividad: " + actividadId);
    List<ActividadDTO> retorno = new ArrayList<>();
    List<Actividad> result = actividadDAO.buscarSubActividades(actividadId);

    if (result != null && !result.isEmpty()) {
      for (Actividad aux : result) {
        retorno.add(convertActividadFromEntityToDto(aux));
      }
    }

    return retorno;
  }

  public List<ActividadDTO> buscarSubActividadesPorTipo(ActividadDTO actividad, String tipo) {
    logger.debug(
        "buscarSubActividadesPorTipo params - actividad: " + actividad.getId() + " tipo: " + tipo);
    return buscarSubActividadesPorTipo(actividad.getId(), tipo);
  }

  public List<ActividadDTO> buscarSubActividadesPorTipo(Long actividadId, String tipo) {
    logger.debug(
        "buscarSubActividadesPorTipo params - id actividad: " + actividadId + " tipo: " + tipo);
    List<ActividadDTO> retorno = new ArrayList<>();
    List<Actividad> result = actividadDAO.buscarSubActividadesPorTipo(actividadId, tipo);

    if (result != null && !result.isEmpty()) {
      for (Actividad aux : result) {
        retorno.add(convertActividadFromEntityToDto(aux));
      }
    }

    return retorno;
  }

  public TipoActividadDTO buscarTipoActividad(String tipoAct) {
    logger.debug("buscarTipoActividad params - tipo: " + tipoAct);

    TipoActividadDTO out = null;
    List<TipoActividad> enti = (List<TipoActividad>) tipoActividadDAO.findByNombre(tipoAct);
    if (null != enti) {
      out = mapper.map(enti.get(0), TipoActividadDTO.class);
    }
    return out;
  }

  @Transactional
  public Long generarActividad(ActividadDTO obj) {
    Actividad actividadToBeSaved = convertActividadFromDtoToEntity(obj);
    actiRepo.save(actividadToBeSaved);
    logger.debug("generarActividad params - actividad: " + actividadToBeSaved.getId());
    return actividadToBeSaved.getId();
  }

  @Transactional
  public void eliminarActividad(ActividadDTO obj) {
    logger.debug("eliminarActividad params - actividad: " + obj.getId());
    actividadDAO.deleteActividad(mapper.map(obj, Actividad.class));
  }

  @Transactional
  public ActividadDTO cerrarActividad(ActividadDTO act, String userBaja) {
    logger
        .debug("cerrando la actividad params - actividad: " + act.getId() + " user: " + userBaja);
    return bajaActividad(act, userBaja, ConstEstadoActividad.ESTADO_CERRADA);
  }

  @Transactional
  public ActividadDTO cerrarActividad(Long actId, String userBaja) {
    logger.debug("cerrando la actividad params - id actividad: " + actId + " user: " + userBaja);
    return bajaActividad(actId, userBaja, ConstEstadoActividad.ESTADO_CERRADA);
  }

  @Transactional
  public ActividadDTO aprobarActividad(ActividadDTO act, String userBaja) {
    logger
        .debug("aprobando la actividad params - actividad: " + act.getId() + " user: " + userBaja);
    return bajaActividad(act, userBaja, ConstEstadoActividad.ESTADO_APROBADA);
  }

  @Transactional
  public ActividadDTO aprobarActividad(Long actId, String userBaja) {
    logger.debug("aprobando la actividad params - id actividad: " + actId + " user: " + userBaja);
    return bajaActividad(actId, userBaja, ConstEstadoActividad.ESTADO_APROBADA);
  }

  @Transactional
  public ActividadDTO rechazarActividad(ActividadDTO act, String userBaja) {
    logger.debug("rechazando la actividad params - id actividad: " + act.getIdObjetivo()
        + " user: " + userBaja);
    return bajaActividad(act, userBaja, ConstEstadoActividad.ESTADO_RECHAZADA);
  }

  @Transactional
  public ActividadDTO rechazarActividad(Long actId, String userBaja) {
    logger.debug("rechazando la actividad params - id actividad: " + actId + " user: " + userBaja);
    return bajaActividad(actId, userBaja, ConstEstadoActividad.ESTADO_RECHAZADA);
  }

  @Transactional
  public void actualizarActividad(ActividadDTO obj) {
    logger.debug("actualizarActividad params - actividad: " + obj.getId());
    Actividad actividadToBeUpdated = convertActividadFromDtoToEntity(obj);
    actiRepo.save(actividadToBeUpdated);
  }

  @Transactional
  public void updateActividad(Actividad act) {
    actividadDAO.actualizarActividad(act);
  }

  public TipoActividadRepository getTipoActividadDAO() {
    return tipoActividadDAO;
  }

  public void setTipoActividadDAO(TipoActividadRepository tipoActividadDAO) {
    this.tipoActividadDAO = tipoActividadDAO;
  }

  public ActividadDTO buscarActividad(Long actividadId) {
    logger.debug("buscarActividad params - id actividad: " + actividadId);
    ActividadDTO retorno = null;

    Actividad result = actividadDAO.findActividad(actividadId);
    if (result != null) {
      retorno = this.convertActividadFromEntityToDto(result);
    }

    return retorno;
  }

  private ActividadDTO bajaActividad(ActividadDTO act, String userBaja, String estado) {
    return bajaActividad(act.getId(), userBaja, estado);
  }

  private ActividadDTO bajaActividad(Long actId, String userBaja, String estado) {
    Actividad act = actividadDAO.findActividad(actId);
    act.setFechaCierre(fechaActual());
    act.setEstado(estado);
    act.setUsuarioCierre(userBaja);
    updateActividad(act);

    return convertActividadFromEntityToDto(act);
  }

  private Date fechaActual() {
    // Fecha actual
    Calendar cal = Calendar.getInstance();
    return cal.getTime();
  }

  @Override
  public List<ActividadDTO> buscarActividadesPendientes(TipoActividadDTO tipo) {
    logger.debug("buscarActividadesPendientes");
    List<ActividadDTO> retorno = new ArrayList<>();
    TipoActividad tipoActividadEntity = mapper.map(tipo, TipoActividad.class);
    List<Actividad> result = actividadDAO.buscarActividadesPendientes(tipoActividadEntity);

    if (result != null && !result.isEmpty()) {
      for (Actividad aux : result) {
        retorno.add(convertActividadFromEntityToDto(aux));
      }
    }

    return retorno;
  }

  @Override
  public List<ActividadDTO> buscarActividadesVigentesPorEstado(List<String> idObj,
      List<String> estados) {
    logger.debug("buscarActividadesVigentesPorEstado");
    List<ActividadDTO> retorno = new ArrayList<>();
    List<Actividad> result = actividadDAO.buscarActividadesVigentesPorEstado(idObj, estados);

    if (result != null && !result.isEmpty()) {
      for (Actividad aux : result) {
        retorno.add(convertActividadFromEntityToDto(aux));
      }
    }

    return retorno;
  }

  // El mapeo de esta entidad es un dolor de cabeza con su mapa de parametros.
  private ActividadDTO convertActividadFromEntityToDto(Actividad entity) {
    ActividadDTO retorno = null;
    Map<String, ParametroActividadDTO> mapToBeInserted = null;

    if (entity != null) {
      // Mapea manualmente el mapa de parametros hacia una variable que será
      // posteriormente añadida al DTO (mapToBeInserted).
      if (entity.getParametros() != null && !entity.getParametros().isEmpty()) {
        mapToBeInserted = new HashMap<>();
        for (Entry<String, ParametroActividad> entry : entity.getParametros().entrySet()) {
          ParametroActividadDTO paramTemp = new ParametroActividadDTO();
          paramTemp.setId(entry.getValue().getId());
          paramTemp.setValor(entry.getValue().getValor());
          paramTemp.setCampo(entry.getValue().getCampo());
          mapToBeInserted.put(entry.getKey(), paramTemp);
        }
      }
      // Deja en null los parametros de la entidad para que el Dozer no se
      // caiga.
      entity.setParametros(null);
      // Se mapea el resto de campos de la entidad mediante Dozer, ya que no dan
      // problemas y ahorra trabajo.
      retorno = mapper.map(entity, ActividadDTO.class);

      // Si la variable de parámetros fue completada, a cada valor se le asigna
      // como su padre al DTO de retorno.
      if (mapToBeInserted != null) {
        for (Entry<String, ParametroActividadDTO> entry : mapToBeInserted.entrySet()) {
          entry.getValue().setIdActividad(retorno);
        }
        // Y se agrega al DTO de retorno.
        retorno.setParametros(mapToBeInserted);
      }
    }

    return retorno;
  }

  // Idem anterior, pero viceversa
  private Actividad convertActividadFromDtoToEntity(ActividadDTO dto) {
    Actividad retorno = null;
    Map<String, ParametroActividad> mapToBeInserted = null;

    if (dto != null) {
      // Mapea manualmente el mapa de parametros hacia una variable que será
      // posteriormente añadida a la entity (mapToBeInserted).
      if (dto.getParametros() != null && !dto.getParametros().isEmpty()) {
        mapToBeInserted = new HashMap<>();
        for (Entry<String, ParametroActividadDTO> entry : dto.getParametros().entrySet()) {
          ParametroActividad paramTemp = new ParametroActividad();
          paramTemp.setId(entry.getValue().getId());
          paramTemp.setValor(entry.getValue().getValor());
          paramTemp.setCampo(entry.getValue().getCampo());
          mapToBeInserted.put(entry.getKey(), paramTemp);
        }
      }
      // Deja en null los parametros del DTO para que el Dozer no se
      // caiga.
      dto.setParametros(null);
      // Se mapea el resto de campos del DTO mediante Dozer, ya que no dan
      // problemas y ahorra trabajo.
      retorno = mapper.map(dto, Actividad.class);

      // Si la variable de parámetros fue completada, a cada valor se le asigna
      // como su padre al entity de retorno.
      if (mapToBeInserted != null) {
        for (Entry<String, ParametroActividad> entry : mapToBeInserted.entrySet()) {
          entry.getValue().setIdActividad(retorno);
        }
        // Y se agrega al DTO de retorno.
        retorno.setParametros(mapToBeInserted);
      }
    }

    return retorno;
  }

  /**
	 * Buscar actividades subsanacion pendiente.
	 *
	 * @param fecha the fecha
	 * @return the list
	 */
	public List<ActividadDTO> buscarActividadesSubsanacionPendiente(Date fecha) {
		List<ActividadDTO> retorno = new ArrayList<>();
		TipoActividadDTO tipoActividadSub = this.buscarTipoActividad(ConstTipoActividad.ACTIVIDAD_TIPO_SOLICITUD_SUBSANACION);
		TipoActividad tipoActividadEntity = mapper.map(tipoActividadSub, TipoActividad.class);
		List<Actividad> result = actiRepo.obtenerActividadSubsanacionPendiente(tipoActividadEntity, "PENDIENTE", fecha);

		if (result != null && !result.isEmpty()) {
			for (Actividad aux : result) {
				retorno.add(convertActividadFromEntityToDto(aux));
			}
		}

		return retorno;
	}
  
  
  
  
  public ActividadDAO getActividadDAO() {
    return actividadDAO;
  }

  public void setActividadDAO(ActividadDAO actividadDAO) {
    this.actividadDAO = actividadDAO;
  }

}
