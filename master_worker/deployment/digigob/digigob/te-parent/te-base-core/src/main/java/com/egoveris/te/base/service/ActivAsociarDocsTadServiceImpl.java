package com.egoveris.te.base.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egoveris.te.base.model.ActividadAprobDocTad;
import com.egoveris.te.base.model.ActividadDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.service.iface.IActivAsociarDocsTadService;
import com.egoveris.te.base.util.ConstParamActividad;
import com.egoveris.te.base.util.ConstTipoActividad;
import com.egoveris.te.model.exception.ParametroIncorrectoException;
import com.egoveris.te.model.exception.ProcesoFallidoException;
import com.egoveris.vucfront.ws.service.DocumentosClient;
import com.egoveris.vucfront.ws.service.NotificacionesClient;

@Service
@Transactional
public class ActivAsociarDocsTadServiceImpl implements IActivAsociarDocsTadService {
  private static final Logger logger = LoggerFactory
      .getLogger(ActivAsociarDocsTadServiceImpl.class);
  @Autowired
  private IActividadService actividadService;
  @Autowired
  private ExpedienteElectronicoService expedienteElectronicoService;
  @Autowired
  private DocumentosClient documentoVucService;

  @Override
  public void aprobarAsociarDocTad(final ActividadAprobDocTad actAprob, final String codigoExp,
      final String usuarioAprob) throws ServiceException, ProcesoFallidoException {
    if (logger.isDebugEnabled()) {
      logger.debug("aprobarAsociarDocTad(actAprob={}, codigoExp={}, usuarioAprob={}) - start",
          actAprob, codigoExp, usuarioAprob);
    }

    ActividadDTO act = null;

    try {
      final ExpedienteElectronicoDTO exp = expedienteElectronicoService
        .obtenerExpedienteElectronicoPorCodigo(codigoExp);

      expedienteElectronicoService.vincularDocumentoGEDO_Definitivo(exp, actAprob.getNroDocumento(),
        usuarioAprob);
      documentoVucService.vincularDocVuc(codigoExp, actAprob.getNroDocumento());
      act = actividadService.aprobarActividad(actAprob.getId(), usuarioAprob);
      // procesoFallidoException = el expediente ya contiene el
      // documento

    } catch (ProcesoFallidoException | ParametroIncorrectoException e) {
      logger.error("aprobarAsociarDocTad(ActividadAprobDocTad, String, String)", e);
      act = actividadService.rechazarActividad(actAprob.getId(), usuarioAprob);
      throw new ProcesoFallidoException("Error en el proceso", null);
    } finally {
      if (act != null && act.getParentId() != null && isTodasSubActCerradas(act.getParentId())) {
        actividadService.cerrarActividad(act.getParentId(), usuarioAprob);
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("aprobarAsociarDocTad(ActividadAprobDocTad, String, String) - end");
    }
  }

  @Override
  public List<ActividadAprobDocTad> buscarActividadesAprobDoc(final Long idAct) {
    if (logger.isDebugEnabled()) {
      logger.debug("buscarActividadesAprobDoc(idAct={}) - start", idAct);
    }

    final List<ActividadAprobDocTad> result = new ArrayList<>();
    final List<ActividadDTO> actList = actividadService.buscarSubActividadesPorTipo(idAct,
        ConstTipoActividad.ACTIVIDAD_TIPO_APROB_DOC);
    if (actList != null) {
      for (final ActividadDTO actividad : actList) {
        final ActividadAprobDocTad actAprob = new ActividadAprobDocTad();
        actAprob.setId(actividad.getId());
        actAprob.setEstado(actividad.getEstado());
        actAprob.setFechaBaja(actividad.getFechaCierre());
        actAprob.setTipoDocTad(
            actividad.getParametros().get(ConstParamActividad.PARAM_TIPO_DOC_TAD).toString());
        actAprob.setNroDocumento(
            actividad.getParametros().get(ConstParamActividad.PARAM_NRO_DOC).toString());
        result.add(actAprob);
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("buscarActividadesAprobDoc(int) - end - return value={}", result);
    }
    return result;
  }

  public IActividadService getActividadService() {
    return actividadService;
  }

//  public ExpedienteElectronicoService getExpedienteElectronicoService() {
//    return expedienteElectronicoService;
//  }

  /**
   * Por actividades cerradas se entiende con fecha de baja del tipo aprobacion
   * de documento
   *
   * @param parentId
   */
  private boolean isTodasSubActCerradas(final Long parentId) {
    if (logger.isDebugEnabled()) {
      logger.debug("isTodasSubActCerradas(parentId={}) - start", parentId);
    }

    final List<ActividadDTO> actList = actividadService.buscarSubActividadesPorTipo(parentId,
        ConstTipoActividad.ACTIVIDAD_TIPO_APROB_DOC);
    // evaluo si estan cerradas todas las actividades
    boolean todasSubActCerradas = true;
    for (final ActividadDTO actividad : actList) {
      if (actividad.getFechaCierre() == null) {
        todasSubActCerradas = false;
        break;
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("isTodasSubActCerradas(Integer) - end - return value={}", todasSubActCerradas);
    }
    return todasSubActCerradas;
  }

  @Override
  public void rechazarActividadAprobDoc(final ActividadAprobDocTad actAprob,
      final String usuarioRechazo) {
    if (logger.isDebugEnabled()) {
      logger.debug("rechazarActividadAprobDoc(actAprob={}, usuarioRechazo={}) - start", actAprob,
          usuarioRechazo);
    }

    final ActividadDTO act = actividadService.rechazarActividad(actAprob.getId(), usuarioRechazo);
    if (act.getParentId() != null && isTodasSubActCerradas(act.getParentId())) {
      actividadService.cerrarActividad(act.getParentId(), usuarioRechazo);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("rechazarActividadAprobDoc(ActividadAprobDocTad, String) - end");
    }
  }

  public void setActividadService(final IActividadService actividadService) {
    this.actividadService = actividadService;
  }

//  public void setExpedienteElectronicoService(
//      final ExpedienteElectronicoService expedienteElectronicoService) {
//    this.expedienteElectronicoService = expedienteElectronicoService;
//  }

}
