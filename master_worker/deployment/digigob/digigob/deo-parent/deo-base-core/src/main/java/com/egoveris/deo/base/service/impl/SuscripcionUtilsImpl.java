package com.egoveris.deo.base.service.impl;

import com.egoveris.deo.base.exception.InconsistenciaDeDatosException;
import com.egoveris.deo.base.model.ProcesoLog;
import com.egoveris.deo.base.model.Suscripcion;
import com.egoveris.deo.base.model.SuscripcionPK;
import com.egoveris.deo.base.repository.ErrorReintentoRepository;
import com.egoveris.deo.base.repository.ProcesoLogRepository;
import com.egoveris.deo.base.repository.SistemaOrigenRepository;
import com.egoveris.deo.base.repository.SuscripcionRepository;
import com.egoveris.deo.base.service.BuscarDocumentosGedoService;
import com.egoveris.deo.base.service.SuscripcionUtils;
import com.egoveris.deo.model.model.DocumentoDTO;
import com.egoveris.deo.model.model.ErrorReintentoDTO;
import com.egoveris.deo.model.model.SistemaOrigenDTO;
import com.egoveris.deo.model.model.SuscripcionDTO;
import com.egoveris.shared.map.ListMapper;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.terasoluna.plus.common.exception.ApplicationException;

@Service
@Transactional
public class SuscripcionUtilsImpl implements SuscripcionUtils {

  private static final Logger LOGGER = LoggerFactory.getLogger(SuscripcionUtilsImpl.class);

  @Autowired
  private ProcesoLogRepository procesoLogRepo;
  @Autowired
  private BuscarDocumentosGedoService buscarDocumentosGedoService;
  @Autowired
  private SuscripcionRepository suscripcionRepo;
  @Autowired
  private SistemaOrigenRepository sistemaOrigenRepo;
  @Autowired
  private ErrorReintentoRepository errorRenintentoRepo;
  @Autowired
  @Qualifier("deoCoreMapper")
  private Mapper mapper;

  /**
   * Persistencia de un determinado proceso en la tabla de logs para OK o para
   * ERROR.
   * 
   * @param workflowId
   * @param descripcion
   * @param proceso
   * @param estado
   * @param reintento
   */
  public void persistirLog(String workflowId, String descripcion, String proceso, String estado,
      String sistemaOrigen) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("persistirLog(String, String, String, String, String) - start"); //$NON-NLS-1$
    }

    ProcesoLog log = new ProcesoLog();
    log.setDescripcion(StringUtils.abbreviate(descripcion, 340));
    log.setEstado(estado);
    log.setFechaCreacion(new Date());
    log.setProceso(proceso);
    log.setSistemaOrigen(sistemaOrigen);
    log.setWorkflowId(workflowId);

    try {
      procesoLogRepo.save(log);
    } catch (Exception e) {
      LOGGER.error("Ha ocurrido un error al persistir el Log del WorflowId: " + workflowId + " - "
          + e.getMessage(), e);
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("persistirLog(String, String, String, String, String) - end"); //$NON-NLS-1$
    }
  }

  /**
   * Obtiene la lista de suscriptores en base a un determinado WorkflowId y
   * Estado.
   * 
   * @param workflowId
   * @param estado
   * @return
   * @throws Exception
   */
  public List<SuscripcionDTO> obtenerSuscripciones(String workflowId, String estado)
      throws ApplicationException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerSuscripciones(String, String) - start"); //$NON-NLS-1$
    }

    List<SuscripcionDTO> suscripciones = null;
    try {
      suscripciones = ListMapper.mapList(
          suscripcionRepo.findBySuscripcionPK(new SuscripcionPK(workflowId,  Integer.parseInt(estado))), this.mapper,
          SuscripcionDTO.class);
    } catch (Exception e) {
      LOGGER.error("Error al obtener suscripciones. " + e.getMessage(), e);
      throw e;
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerSuscripciones(String, String) - end"); //$NON-NLS-1$
    }
    return suscripciones;
  }

  /**
   * Obtiene un objeto Suscripcion en base a un workflowId y a un SistemaOrigen.
   * 
   * @param workflowId
   * @param sistemaOrigen
   * @return
   */
  public SuscripcionDTO obtenerSuscripcion(String workflowId, SistemaOrigenDTO sistemaOrigen) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerSuscripcion(String, SistemaOrigenDTO) - start"); //$NON-NLS-1$
    }

    SuscripcionDTO suscripcion = null;
    try {
      suscripcion = this.mapper.map(suscripcionRepo.findBySuscripcionPK(
          new SuscripcionPK(workflowId, Integer.parseInt(sistemaOrigen.getNombre()))), SuscripcionDTO.class);
    } catch (Exception e) {
      LOGGER.error("Error al obtener suscripcion. " + e.getMessage(), e);
      throw e;
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerSuscripcion(String, SistemaOrigenDTO) - end"); //$NON-NLS-1$
    }
    return suscripcion;
  }

  /**
   * Obtiene un objeto SistemaOrigen en base al nombre del mismo.
   * 
   * @param nombreSistemaOrigen
   * @return
   */
  public SistemaOrigenDTO obtenerSistemaOrigen(String nombreSistemaOrigen) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerSistemaOrigen(String) - start"); //$NON-NLS-1$
    }

    SistemaOrigenDTO sistemaOrigen = null;
    try {
      sistemaOrigen = this.mapper.map(sistemaOrigenRepo.findByNombreLike(nombreSistemaOrigen),
          SistemaOrigenDTO.class);
    } catch (Exception e) {
      LOGGER.error("Error al obtener sistema origen. " + e.getMessage(), e);
      throw e;
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerSistemaOrigen(String) - end"); //$NON-NLS-1$
    }
    return sistemaOrigen;
  }

  /**
   * Obtiene un objeto Documento en base a un workflowId.
   * 
   * @param workflowId
   * @return
   */
  public DocumentoDTO obtenerDocumento(String workflowId) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerDocumento(String) - start"); //$NON-NLS-1$
    }

    DocumentoDTO documento = null;
    try {
      documento = buscarDocumentosGedoService.buscarDocumentoPorProceso(workflowId);
    } catch (Exception e) {
      LOGGER.error("Error al obtener documento. " + e.getMessage(), e);
      throw e;
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerDocumento(String) - end"); //$NON-NLS-1$
    }
    return documento;
  }

  /**
   * Obtiene un objeto ErrorReintento en base a un id de error.
   * 
   * @param idError
   * @return
   */
  public ErrorReintentoDTO obtenerErrorReintento(int idError) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerErrorReintento(int) - start"); //$NON-NLS-1$
    }

    ErrorReintentoDTO errorReintento = null;
    try {
      errorReintento = this.mapper.map(errorRenintentoRepo.findOne(idError),
          ErrorReintentoDTO.class);
    } catch (Exception e) {
      LOGGER.error("Error al obtener error de reintento. " + e.getMessage(), e);
      throw e;
    }
    if (errorReintento == null) {
      throw new InconsistenciaDeDatosException(
          "No existe errorReintento para el idError: " + idError);
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("obtenerErrorReintento(int) - end"); //$NON-NLS-1$
    }
    return errorReintento;
  }

  /**
   * Persistencia de una suscripcion determinada.
   * 
   * @param suscripcion
   * @return
   */
  public void persistirSuscripcion(SuscripcionDTO suscripcion) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("persistirSuscripcion(SuscripcionDTO) - start"); //$NON-NLS-1$
    }

    try {
      suscripcionRepo.save(this.mapper.map(suscripcion, Suscripcion.class));
    } catch (Exception e) {
      LOGGER.error("Error al persistir suscripcion. " + e.getMessage(), e);
      throw e;
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("persistirSuscripcion(SuscripcionDTO) - end"); //$NON-NLS-1$
    }
  }
}
