package com.egoveris.te.base.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.egoveris.te.base.model.ActividadDTO;
import com.egoveris.te.base.model.ActividadSirAprobDocTad;
import com.egoveris.te.base.service.iface.IActivAsociarDocsSIRService;
import com.egoveris.te.base.util.ConstParamActividad;
import com.egoveris.te.base.util.ConstTipoActividad;
import com.egoveris.te.model.exception.ProcesoFallidoException;

@Service
@Transactional
public class ActivAsociarDocsSIRServiceImpl implements IActivAsociarDocsSIRService {

  private static final Logger logger = LoggerFactory
      .getLogger(ActivAsociarDocsSIRServiceImpl.class);
  @Autowired
  private IActividadService actividadService;
//  @Autowired(required=false)
//  private ExpedienteElectronicoService expedienteElectronicoService;

  @Override
  public void aprobarActividad(final ActividadSirAprobDocTad actAprob, final String codigoExp,
      final String usuarioAprob) throws ServiceException, ProcesoFallidoException {
    if (logger.isDebugEnabled()) {
      logger.debug("aprobarActividad(actAprob={}, codigoExp={}, usuarioAprob={}) - start",
          actAprob, codigoExp, usuarioAprob);
    }

    ActividadDTO act = null;

    try {
//      final ExpedienteElectronicoDTO exp = expedienteElectronicoService
//          .obtenerExpedienteElectronicoPorCodigo(codigoExp);

      try {
        for (final String nroDocumento : actAprob.getNrosDocumento()) {
//          expedienteElectronicoService.vincularDocumentoGEDO_Definitivo(exp, nroDocumento,
//              usuarioAprob);
        }
        act = actividadService.aprobarActividad(actAprob.getId(), usuarioAprob);
        // procesoFallidoException = el expediente ya contiene el
        // documento
      } catch (final ProcesoFallidoException pe) {
        logger.error("aprobarActividad(ActividadSirAprobDocTad, String, String)", pe);

        throw new ServiceException("Error al vincular el de documento al expediente.", pe);
      } finally {
        if (act != null) {
          actividadService.cerrarActividad(act.getId(), usuarioAprob);
        } else {
          logger.info("No se pudo cerrar la actividad");
        }
      }
    } catch (final Exception e) {
      logger.error("aprobarActividad(ActividadSirAprobDocTad, String, String)", e);

      if (e instanceof ProcesoFallidoException) {
        throw (ProcesoFallidoException) e;
      } else {
        throw new ServiceException("Error al vincular el de documento al exp", e);
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("aprobarActividad(ActividadSirAprobDocTad, String, String) - end");
    }
  }

  @Override
  public ActividadSirAprobDocTad buscarActividad(final Long idAct) {
    if (logger.isDebugEnabled()) {
      logger.debug("buscarActividad(idAct={}) - start", idAct);
    }

    final List<ActividadSirAprobDocTad> result = new ArrayList<ActividadSirAprobDocTad>();
    final ActividadDTO act = actividadService.buscarActividad(idAct);

    ActividadSirAprobDocTad actAprob = null;

    if (act != null) {
      actAprob = new ActividadSirAprobDocTad();
      actAprob.setId(act.getId());
      actAprob.setEstado(act.getEstado());
      actAprob.setFechaBaja(act.getFechaCierre());
      final String documentosPorComa = act.getParametros()
          .get(ConstParamActividad.PARAM_LISTA_NRO_DOC).toString();

      final String[] documetos = documentosPorComa.split(",");

      final List<String> listaDocumento = Arrays.asList(documetos);

      actAprob.setNrosDocumento(listaDocumento);

      result.add(actAprob);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("buscarActividad(int) - end - return value={}", actAprob);
    }
    return actAprob;
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
        ConstTipoActividad.ACTIVIDAD_TIPO_SOLICITUD_PAGO_SIR);
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
  public void rechazarActividad(final ActividadSirAprobDocTad actAprob,
      final String usuarioRechazo) {
    if (logger.isDebugEnabled()) {
      logger.debug("rechazarActividad(actAprob={}, usuarioRechazo={}) - start", actAprob,
          usuarioRechazo);
    }

    final ActividadDTO act = actividadService.rechazarActividad(actAprob.getId(), usuarioRechazo);
    if (act.getParentId() != null && isTodasSubActCerradas(act.getParentId())) {
      actividadService.cerrarActividad(act.getParentId(), usuarioRechazo);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("rechazarActividad(ActividadSirAprobDocTad, String) - end");
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
