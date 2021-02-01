/**
 *
 */
package com.egoveris.te.base.service;

import java.util.Date;
import java.util.List;

import org.jbpm.api.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.te.base.model.ExpedienteAsociadoEntDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.service.expediente.ExpedienteSadeService;
import com.egoveris.te.base.service.iface.IAdministracionDeExpedientesAsociadosService;
import com.egoveris.te.base.util.BusinessFormatHelper;
import com.egoveris.te.model.exception.ExpedienteNoDisponibleException;
import com.egoveris.te.model.exception.ParametroIncorrectoException;
//TODO Comentado lo de VUC ExpedienteInexistenteException
//import com.egoveris.vuc.external.service.client.service.external.exception.ExpedienteInexistenteException;
import com.egoveris.te.model.exception.ProcesoFallidoException;

@Service
@Transactional
public class AdministracionDeExpedientesAsociadosServiceImpl extends ExternalServiceAbstract
    implements IAdministracionDeExpedientesAsociadosService {
  private static final Logger logger = LoggerFactory
      .getLogger(AdministracionDeExpedientesAsociadosServiceImpl.class);

  @Autowired
  private ExpedienteSadeService expedienteSadeService;
  @Autowired
  public ExpedienteElectronicoService expedienteElectronicoService;
  @Autowired
  private ServicioAdministracion servicioAdministracionFactory;

  @Transactional
  public void asociarExpediente(String sistemaUsuario, String usuario, String codigoEE,
      String codigoEEAsociado) throws ProcesoFallidoException,
      /* ExpedienteInexistenteException, */ ParametroIncorrectoException,
      ExpedienteNoDisponibleException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "asociarExpediente(sistemaUsuario={}, usuario={}, codigoEE={}, codigoEEAsociado={}) - start",
          sistemaUsuario, usuario, codigoEE, codigoEEAsociado);
    }

    ExpedienteElectronicoDTO expedienteElectronico;
    ExpedienteElectronicoDTO expedienteElectronicoParaAsociar;
    ExpedienteAsociadoEntDTO expedienteAsociado = new ExpedienteAsociadoEntDTO();
    Task workingTask;

    if (codigoEE != null && codigoEE.equals(codigoEEAsociado)) {
      throw new ParametroIncorrectoException("No se puede vincular un expediente a sí mismo. ",
          null);
    }

    // ************************************************************************
    // ** TODO: REFACTOR REFACTOR (ISSUE) BISADE-1805 Excepciones de negocio se
    // muestran en el log de EE (Martes 15-Marzo-2014) grinberg.
    // ** Desc: Validación Documentacion de EE (Business Service)
    // ** En la clase ExternServiceAbstract en vez verificarAsignación en la
    // ejecución del servicio,
    // ** se arma con la referencia ya validada desde el principio del CU.
    // ** Asi al invocar la llama desde un factory de vínculacion, ya está
    // referenciada
    // ** la asociación y no se permite su ejecución sino antes de a ver llegado
    // a la invocación del servicio.
    // ** Caso de Uso afectado: Asociacion Documentacion al Expediente
    // Electrónico
    // ************************************************************************
    expedienteElectronico = servicioAdministracionFactory
        .crearExpedienteElectronicoAsociados(codigoEE, sistemaUsuario, usuario, false);

    expedienteElectronicoParaAsociar = obtenerExpedienteElectronico(codigoEEAsociado);

    if (expedienteElectronicoParaAsociar != null) {
      workingTask = obtenerWorkingTask(expedienteElectronicoParaAsociar);
      expedienteAsociado.setTipoDocumento(expedienteElectronicoParaAsociar.getTipoDocumento());
      expedienteAsociado.setAnio(expedienteElectronicoParaAsociar.getAnio());
      expedienteAsociado.setNumero(expedienteElectronicoParaAsociar.getNumero());
      expedienteAsociado.setSecuencia(expedienteElectronicoParaAsociar.getSecuencia());
      expedienteAsociado.setDefinitivo(false); // Se hará definitivo
                                               // en el próximo
                                               // pase
                                               // this.expedienteAsociado.setDefinitivo(expedienteElectronicoParaAsociar.getDefinitivo());

      expedienteAsociado.setCodigoReparticionActuacion(
          expedienteElectronicoParaAsociar.getCodigoReparticionActuacion());
      expedienteAsociado.setCodigoReparticionUsuario(
          expedienteElectronicoParaAsociar.getCodigoReparticionUsuario());
      expedienteAsociado.setEsElectronico(expedienteElectronicoParaAsociar.getEsElectronico());
      expedienteAsociado.setIdCodigoCaratula(expedienteElectronicoParaAsociar.getId());

      expedienteAsociado.setFechaAsociacion(new Date());
      expedienteAsociado.setUsuarioAsociador(usuario);

      if (workingTask != null && workingTask.getExecutionId() != null) {
        expedienteAsociado.setIdTask(workingTask.getExecutionId());
      }

      if (!expedienteElectronico.getListaExpedientesAsociados().contains(expedienteAsociado)) {
        expedienteElectronico.getListaExpedientesAsociados().add(expedienteAsociado);
        expedienteElectronicoService.modificarExpedienteElectronico(expedienteElectronico);
      } else {
        throw new ProcesoFallidoException("El expediente ya se encuentra asociado.", null);
      }
    } else {
      List<String> listDesgloseCodigoEEAsociado;
      listDesgloseCodigoEEAsociado = BusinessFormatHelper
          .obtenerDesgloseCodigoEE(codigoEEAsociado);

      expedienteAsociado = expedienteSadeService.obtenerExpedienteSade(
          listDesgloseCodigoEEAsociado.get(0),
          Integer.valueOf(listDesgloseCodigoEEAsociado.get(1)),
          Integer.valueOf(listDesgloseCodigoEEAsociado.get(2)),
          listDesgloseCodigoEEAsociado.get(5));

      if ((expedienteAsociado != null)
          && !expedienteElectronico.getListaExpedientesAsociados().contains(expedienteAsociado)) {
        workingTask = obtenerWorkingTask(expedienteElectronico);

        String trata = expedienteSadeService.obtenerCodigoTrataSADE(expedienteAsociado);
        expedienteAsociado.setTrata(trata);
        expedienteAsociado.setEsElectronico(false);
        expedienteAsociado.setFechaAsociacion(new Date());
        expedienteAsociado.setUsuarioAsociador("SISTEMA EXTERNO");
        expedienteAsociado.setIdTask(workingTask.getExecutionId());

        expedienteElectronico.getListaExpedientesAsociados().add(expedienteAsociado);
        expedienteElectronicoService.modificarExpedienteElectronico(expedienteElectronico);
      } else {
        // throw new ExpedienteInexistenteException("El expediente no existe.",
        // null);
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("asociarExpediente(String, String, String, String) - end");
    }
  }

  @Transactional
  public void desasociarExpediente(String sistemaUsuario, String usuario, String codigoEE,
      String codigoEEDesasociado) throws ProcesoFallidoException,
      /* ExpedienteInexistenteException, */ ParametroIncorrectoException,
      ExpedienteNoDisponibleException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "desasociarExpediente(sistemaUsuario={}, usuario={}, codigoEE={}, codigoEEDesasociado={}) - start",
          sistemaUsuario, usuario, codigoEE, codigoEEDesasociado);
    }

    ExpedienteElectronicoDTO expedienteElectronico = null;
    ExpedienteElectronicoDTO expedienteElectronicoParaDesasociar = null;

    try {
      // ************************************************************************
      // ** TODO: REFACTOR REFACTOR (ISSUE) BISADE-1805 Excepciones de negocio
      // se muestran en el log de EE (Martes 15-Marzo-2014) grinberg.
      // ** Desc: Validación Documentacion de EE (Business Service)
      // ** En la clase ExternServiceAbstract en vez verificarAsignación en la
      // ejecución del servicio,
      // ** se arma con la referencia ya validada desde el principio del CU.
      // ** Asi al invocar la llama desde un factory de vínculacion, ya está
      // referenciada
      // ** la asociación y no se permite su ejecución sino antes de a ver
      // llegado a la invocación del servicio.
      // ** Caso de Uso afectado: Asociacion Documentacion al Expediente
      // Electrónico
      // ************************************************************************
      expedienteElectronico = servicioAdministracionFactory
          .crearExpedienteElectronicoAsociados(codigoEE, sistemaUsuario, usuario, false);
      // Expediente Hijo
      expedienteElectronicoParaDesasociar = obtenerExpedienteElectronico(codigoEEDesasociado);
      expedienteElectronico
          .desasociarPorNumeroDeDoc(expedienteElectronicoParaDesasociar.getNumero());
      expedienteElectronicoService.modificarExpedienteElectronico(expedienteElectronico);
    } catch (Exception exception) {
      logger.error("desasociarExpediente(String, String, String, String)", exception);

      throw new ProcesoFallidoException(exception.getMessage(), null);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("desasociarExpediente(String, String, String, String) - end");
    }
  }
}