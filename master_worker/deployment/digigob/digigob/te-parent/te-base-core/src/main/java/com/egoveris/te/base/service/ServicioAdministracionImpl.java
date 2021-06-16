package com.egoveris.te.base.service;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.egoveris.sharedorganismo.base.service.ReparticionServ;
import com.egoveris.sharedorganismo.base.service.SectorInternoServ;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.service.helper.ValidacionServiciosAdministracionHelper;
import com.egoveris.te.base.service.iface.IActividadExpedienteService;
import com.egoveris.te.base.util.BusinessFormatHelper;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.WorkFlow;
import com.egoveris.te.model.exception.ExpedienteNoDisponibleException;
import com.egoveris.te.model.exception.ParametroIncorrectoException;
import com.egoveris.te.model.exception.ProcesoFallidoException;

@Service
@Transactional
public class ServicioAdministracionImpl implements ServicioAdministracion {
  private static Logger logger = LoggerFactory.getLogger(ServicioAdministracionImpl.class);

  @Autowired
  private IActividadExpedienteService actividadExpedienteService;
  @Autowired
  private ExpedienteElectronicoService expedienteElectronicoService;
  @Autowired
  private HistorialOperacionService historialOperacionService;
  @Autowired
  private UsuariosSADEService usuariosSADEService;
  @Autowired
  private ValidaUsuarioExpedientesService validaUsuarioExpedientesService;
  @Autowired
  private SectorInternoServ sectorInternoServ;
  @Autowired
  private ReparticionServ reparticionServ;
  @Autowired
  private WorkFlowService workflowService;

  @Autowired
  @Qualifier("sistemasSolicitantesArchivo")
  private String sistemasSolicitantesArchivo;
  @Autowired
  @Qualifier("sistemasCaratuladoresExternos")
  private String sistemasCaratuladoresExternos;

  private ValidacionServiciosAdministracionHelper helper = ValidacionServiciosAdministracionHelper
      .getInstance();

  @Override
  public ExpedienteElectronicoDTO crearEESolicitudArchivoYGTemporalParaAdjuntarDoc(
      final String codigoExpediente, final String sistemaOrigen, final String usuarioOrigen)
      throws ParametroIncorrectoException, ProcesoFallidoException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "crearEESolicitudArchivoYGTemporalParaAdjuntarDoc(codigoExpediente={}, sistemaOrigen={}, usuarioOrigen={}) - start",
          codigoExpediente, sistemaOrigen, usuarioOrigen);
    }

    final ExpedienteElectronicoDTO e = obtenerExpedienteElectronico(codigoExpediente);

    validacionesParaArchivoYSolicitudArchivo(sistemaOrigen, usuarioOrigen);
    if (!e.getEstado().equals(ConstantesWeb.ESTADO_GUARDA_TEMPORAL)
        && !e.getEstado().equals(ConstantesWeb.ESTADO_SOLICITUD_ARCHIVO)) {
      throw new ParametroIncorrectoException(
          "El expediente no tiene el estado adecuando para realizar la operacion", null);
    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "crearEESolicitudArchivoYGTemporalParaAdjuntarDoc(String, String, String) - end - return value={}",
          e);
    }
    return e;
  }

  @Override
  @SuppressWarnings("static-access")
  public ExpedienteElectronicoDTO crearExpedienteElectronicoAdministrador(
      final String codigoExpedienteElectronico, final String usuarioOrigen,
      final String usuarioDestino, final String sistemaOrigen, final String motivoDePase,
      final String reparticionDestino, final String sectorDestino, final Boolean isMesaDestino,
      final Boolean isSectorDestino, final Boolean isReparticionDestino,
      final Boolean isUsuarioDestino, final String estado, final Boolean isOperacionBloqueante)
      throws ParametroIncorrectoException, ExpedienteNoDisponibleException,
      ProcesoFallidoException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "crearExpedienteElectronicoAdministrador(codigoExpedienteElectronico={}, usuarioOrigen={}, usuarioDestino={}, sistemaOrigen={}, motivoDePase={}, reparticionDestino={}, sectorDestino={}, isMesaDestino={}, isSectorDestino={}, isReparticionDestino={}, isUsuarioDestino={}, estado={}, isOperacionBloqueante={}) - start",
          codigoExpedienteElectronico, usuarioOrigen, usuarioDestino, sistemaOrigen, motivoDePase,
          reparticionDestino, sectorDestino, isMesaDestino, isSectorDestino, isReparticionDestino,
          isUsuarioDestino, estado, isOperacionBloqueante);
    }

    final ExpedienteElectronicoDTO expedienteElectronico = obtenerExpedienteElectronico(
        codigoExpedienteElectronico);

    helper.verificarEstadoSeleccionado(estado);
    helper.validarTransicion(estado, expedienteElectronico.getEstado());
    helper.verificarExistenciaDeUsuarioParaSistemaExterno(validaUsuarioExpedientesService,
        usuariosSADEService, usuarioOrigen, expedienteElectronico,
        sistemasCaratuladoresExternos.split(","));
    helper.validarMotivoDePase(motivoDePase);
    helper.validarActividadesPendientesDeResolucion(actividadExpedienteService,
        expedienteElectronico, usuarioDestino);
    helper.validarDestinoParaCCOO(validaUsuarioExpedientesService, usuarioDestino,
        isUsuarioDestino, estado);
    helper.validarRepaticion(reparticionServ, sectorInternoServ, reparticionDestino, sectorDestino,
        isUsuarioDestino, isMesaDestino, isSectorDestino, isReparticionDestino, estado);

    if (logger.isDebugEnabled()) {
      logger.debug(
          "crearExpedienteElectronicoAdministrador(String, String, String, String, String, String, String, Boolean, Boolean, Boolean, Boolean, String, Boolean) - end - return value={}",
          expedienteElectronico);
    }
    return expedienteElectronico;
  }

  @Override
  @SuppressWarnings("static-access")
  public ExpedienteElectronicoDTO crearExpedienteElectronicoAsociados(
      final String codigoExpedienteElectronico, final String sistemaUsuario, final String usuario,
      final Boolean isOperacionBloqueante) throws ParametroIncorrectoException,
      ExpedienteNoDisponibleException, ProcesoFallidoException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "crearExpedienteElectronicoAsociados(codigoExpedienteElectronico={}, sistemaUsuario={}, usuario={}, isOperacionBloqueante={}) - start",
          codigoExpedienteElectronico, sistemaUsuario, usuario, isOperacionBloqueante);
    }

    final ExpedienteElectronicoDTO expedienteElectronico = obtenerExpedienteElectronico(
        codigoExpedienteElectronico);

    helper.verificarExistenciaDeUsuario(validaUsuarioExpedientesService, usuariosSADEService,
        usuario);
    validarAsignacionClaseExpedienteElectrico(expedienteElectronico, historialOperacionService,
        sistemaUsuario, usuario, isOperacionBloqueante, true);

    if (logger.isDebugEnabled()) {
      logger.debug(
          "crearExpedienteElectronicoAsociados(String, String, String, Boolean) - end - return value={}",
          expedienteElectronico);
    }
    return expedienteElectronico;
  }

  @Override
  public ExpedienteElectronicoDTO crearExpedienteElectronicoConDocumentos(
      final String codigoExpedienteElectronico, final String sistemaUsuario, final String usuario,
      final Boolean isOperacionBloqueante) throws ParametroIncorrectoException,
      ExpedienteNoDisponibleException, ProcesoFallidoException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "crearExpedienteElectronicoConDocumentos(codigoExpedienteElectronico={}, sistemaUsuario={}, usuario={}, isOperacionBloqueante={}) - start",
          codigoExpedienteElectronico, sistemaUsuario, usuario, isOperacionBloqueante);
    }

    final ExpedienteElectronicoDTO expedienteElectronico = obtenerExpedienteElectronico(
        codigoExpedienteElectronico);

    validarAsignacionClaseExpedienteElectrico(expedienteElectronico, historialOperacionService,
        sistemaUsuario, usuario, isOperacionBloqueante, true);

    if (logger.isDebugEnabled()) {
      logger.debug(
          "crearExpedienteElectronicoConDocumentos(String, String, String, Boolean) - end - return value={}",
          expedienteElectronico);
    }
    return expedienteElectronico;
  }

  @Override
  public ExpedienteElectronicoDTO crearExpedienteElectronicoConDocumentosNoDefinitivos(
      final String codigoExpedienteElectronico, final String sistemaUsuario, final String usuario,
      final String estadoSeleccionado, final Boolean isOperacionBloqueante)
      throws ParametroIncorrectoException, ExpedienteNoDisponibleException,
      ProcesoFallidoException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "crearExpedienteElectronicoConDocumentosNoDefinitivos(codigoExpedienteElectronico={}, sistemaUsuario={}, usuario={}, estadoSeleccionado={}, isOperacionBloqueante={}) - start",
          codigoExpedienteElectronico, sistemaUsuario, usuario, estadoSeleccionado,
          isOperacionBloqueante);
    }

    final ExpedienteElectronicoDTO expedienteElectronico = obtenerExpedienteElectronico(
        codigoExpedienteElectronico);

    validarAsignacionClaseExpedienteElectrico(expedienteElectronico, historialOperacionService,
        sistemaUsuario, usuario, isOperacionBloqueante, true);

    if (logger.isDebugEnabled()) {
      logger.debug(
          "crearExpedienteElectronicoConDocumentosNoDefinitivos(String, String, String, String, Boolean) - end - return value={}",
          expedienteElectronico);
    }
    return expedienteElectronico;
  }

  @Override
  @SuppressWarnings("static-access")
  public ExpedienteElectronicoDTO crearExpedienteElectronicoParaDerivacionDeOwner(
      final String codigoExpedienteElectronico, final String usuarioOrigen,
      final String usuarioDestino, final String sistemaOrigen, final String motivoDePase,
      final String reparticionDestino, final String sectorDestino, final Boolean isMesaDestino,
      final Boolean isSectorDestino, final Boolean isReparticionDestino,
      final Boolean isUsuarioDestino, final String estado, final Boolean isOperacionBloqueante)
      throws ParametroIncorrectoException, ExpedienteNoDisponibleException,
      ProcesoFallidoException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "crearExpedienteElectronicoParaDerivacionDeOwner(codigoExpedienteElectronico={}, usuarioOrigen={}, usuarioDestino={}, sistemaOrigen={}, motivoDePase={}, reparticionDestino={}, sectorDestino={}, isMesaDestino={}, isSectorDestino={}, isReparticionDestino={}, isUsuarioDestino={}, estado={}, isOperacionBloqueante={}) - start",
          codigoExpedienteElectronico, usuarioOrigen, usuarioDestino, sistemaOrigen, motivoDePase,
          reparticionDestino, sectorDestino, isMesaDestino, isSectorDestino, isReparticionDestino,
          isUsuarioDestino, estado, isOperacionBloqueante);
    }

    final ExpedienteElectronicoDTO expedienteElectronico = obtenerExpedienteElectronico(
        codigoExpedienteElectronico);

    helper.validarDestino(isMesaDestino, isSectorDestino, isReparticionDestino, isUsuarioDestino,
        estado, expedienteElectronico.getEstado());
    // helper.verificarEstadoSeleccionado(estado);
    // helper.validarTransicion(estado,expedienteElectronico.getEstado());
    final List<String> proximosEstados = workflowService
        .nextTasksName(expedienteElectronico.getIdWorkflow());
    helper.validarTransicion(expedienteElectronico.getEstado(), estado, proximosEstados);

    helper.verificarExistenciaDeUsuarioParaSistemaExterno(validaUsuarioExpedientesService,
        usuariosSADEService, usuarioOrigen, expedienteElectronico,
        sistemasCaratuladoresExternos.split(","));
    // helper.verificarExistenciaDeUsuario(validaUsuarioExpedientesService,
    // usuariosSADEService, usuarioOrigen);
    helper.validarMotivoDePase(motivoDePase);
    helper.validarActividadesPendientesDeResolucion(actividadExpedienteService,
        expedienteElectronico, usuarioDestino);
    helper.validarDestinoParaCCOO(validaUsuarioExpedientesService, usuarioDestino,
        isUsuarioDestino, estado);

    final WorkFlow workFlow = workflowService.createWorkFlow(expedienteElectronico.getId());

    helper.validarAsignacion(workFlow, historialOperacionService, sistemaOrigen, usuarioOrigen,
        isOperacionBloqueante);
    helper.validarApoderado(usuariosSADEService, expedienteElectronico, usuarioDestino, estado);
    helper.validarRepaticion(reparticionServ, sectorInternoServ, reparticionDestino, sectorDestino,
        isUsuarioDestino, isMesaDestino, isSectorDestino, isReparticionDestino, estado);

    if (logger.isDebugEnabled()) {
      logger.debug(
          "crearExpedienteElectronicoParaDerivacionDeOwner(String, String, String, String, String, String, String, Boolean, Boolean, Boolean, Boolean, String, Boolean) - end - return value={}",
          expedienteElectronico);
    }
    return expedienteElectronico;
  }

  @Override
  public ExpedienteElectronicoDTO crearExpedienteElectronicoParaDocumentosDeTrabajo(
      final String codigoExpedienteElectronico, final String sistemaUsuario, final String usuario,
      final Boolean isOperacionBloqueante) throws ParametroIncorrectoException,
      ExpedienteNoDisponibleException, ProcesoFallidoException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "crearExpedienteElectronicoParaDocumentosDeTrabajo(codigoExpedienteElectronico={}, sistemaUsuario={}, usuario={}, isOperacionBloqueante={}) - start",
          codigoExpedienteElectronico, sistemaUsuario, usuario, isOperacionBloqueante);
    }

    final ExpedienteElectronicoDTO expedienteElectronico = obtenerExpedienteElectronico(
        codigoExpedienteElectronico);

    validarAsignacionClaseExpedienteElectrico(expedienteElectronico, historialOperacionService,
        sistemaUsuario, usuario, isOperacionBloqueante, true);

    if (logger.isDebugEnabled()) {
      logger.debug(
          "crearExpedienteElectronicoParaDocumentosDeTrabajo(String, String, String, Boolean) - end - return value={}",
          expedienteElectronico);
    }
    return expedienteElectronico;
  }

  @Override
  @SuppressWarnings("static-access")
  public ExpedienteElectronicoDTO crearExpedienteParaEnvioAArchivo(
      final String codigoExpedienteElectronico, final String usuarioOrigen,
      final String sistemaOrigen, final String motivoDePase, final String estado,
      final Boolean isMesaDestino, final Boolean isSectorDestino,
      final Boolean isReparticionDestino, final Boolean isUsuarioDestino)
      throws ParametroIncorrectoException, ProcesoFallidoException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "crearExpedienteParaEnvioAArchivo(codigoExpedienteElectronico={}, usuarioOrigen={}, sistemaOrigen={}, motivoDePase={}, estado={}, isMesaDestino={}, isSectorDestino={}, isReparticionDestino={}, isUsuarioDestino={}) - start",
          codigoExpedienteElectronico, usuarioOrigen, sistemaOrigen, motivoDePase, estado,
          isMesaDestino, isSectorDestino, isReparticionDestino, isUsuarioDestino);
    }

    final ExpedienteElectronicoDTO expediente = obtenerExpedienteElectronico(
        codigoExpedienteElectronico);

    helper.validarDestino(isMesaDestino, isSectorDestino, isReparticionDestino, isUsuarioDestino,
        estado, expediente.getEstado());
    helper.verificarEstadoParaEnvioAArchivo(expediente.getEstado(), estado);
    validacionesParaArchivoYSolicitudArchivo(sistemaOrigen, usuarioOrigen);
    helper.validarMotivoDePase(motivoDePase);

    if (logger.isDebugEnabled()) {
      logger.debug(
          "crearExpedienteParaEnvioAArchivo(String, String, String, String, String, Boolean, Boolean, Boolean, Boolean) - end - return value={}",
          expediente);
    }
    return expediente;
  }

  @Override
  @SuppressWarnings("static-access")
  public ExpedienteElectronicoDTO crearExpedienteParaEnvioASolicitudArchivo(
      final String codigoExpedienteElectronico, final String usuarioOrigen,
      final String sistemaOrigen, final String motivoDePase, final String estado,
      final Boolean isMesaDestino, final Boolean isSectorDestino,
      final Boolean isReparticionDestino, final Boolean isUsuarioDestino)
      throws ParametroIncorrectoException, ProcesoFallidoException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "crearExpedienteParaEnvioASolicitudArchivo(codigoExpedienteElectronico={}, usuarioOrigen={}, sistemaOrigen={}, motivoDePase={}, estado={}, isMesaDestino={}, isSectorDestino={}, isReparticionDestino={}, isUsuarioDestino={}) - start",
          codigoExpedienteElectronico, usuarioOrigen, sistemaOrigen, motivoDePase, estado,
          isMesaDestino, isSectorDestino, isReparticionDestino, isUsuarioDestino);
    }

    final ExpedienteElectronicoDTO expediente = obtenerExpedienteElectronico(
        codigoExpedienteElectronico);

    helper.validarDestino(isMesaDestino, isSectorDestino, isReparticionDestino, isUsuarioDestino,
        estado, expediente.getEstado());
    helper.verificarEstadoParaEnvioASolicitudArchivo(expediente.getEstado(), estado);
    helper.validarMotivoDePase(motivoDePase);
    validacionesParaArchivoYSolicitudArchivo(sistemaOrigen, usuarioOrigen);

    if (logger.isDebugEnabled()) {
      logger.debug(
          "crearExpedienteParaEnvioASolicitudArchivo(String, String, String, String, String, Boolean, Boolean, Boolean, Boolean) - end - return value={}",
          expediente);
    }
    return expediente;
  }

  @Override
  public ExpedienteElectronicoDTO obtenerExpedienteElectronico(
      final String codigoExpedienteElectronico)
      throws ParametroIncorrectoException, ProcesoFallidoException {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerExpedienteElectronico(codigoExpedienteElectronico={}) - start",
          codigoExpedienteElectronico);
    }

    List<String> listDesgloseCodigoEE;

    listDesgloseCodigoEE = BusinessFormatHelper
        .obtenerDesgloseCodigoEE(codigoExpedienteElectronico);

    ExpedienteElectronicoDTO expedienteElectronico = null;

    try {
      expedienteElectronico = expedienteElectronicoService.obtenerExpedienteElectronico(
          listDesgloseCodigoEE.get(0), Integer.valueOf(listDesgloseCodigoEE.get(1)),
          Integer.valueOf(listDesgloseCodigoEE.get(2)), listDesgloseCodigoEE.get(5));
      if (expedienteElectronico == null) {
        throw new ProcesoFallidoException(
            "No se ha podido obtener el expediente electrónico " + codigoExpedienteElectronico,
            null);
      }
    } catch (final Exception e) {
      logger.error("obtenerExpedienteElectronico(String)", e);

      throw new ProcesoFallidoException(
          "No se ha podido obtener el expediente electrónico " + codigoExpedienteElectronico, e);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("obtenerExpedienteElectronico(String) - end - return value={}",
          expedienteElectronico);
    }
    return expedienteElectronico;
  }

  @SuppressWarnings("static-access")
  private void validacionesParaArchivoYSolicitudArchivo(final String sistemaOrigen,
      final String usuarioOrigen) throws ParametroIncorrectoException, ProcesoFallidoException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "validacionesParaArchivoYSolicitudArchivo(sistemaOrigen={}, usuarioOrigen={}) - start",
          sistemaOrigen, usuarioOrigen);
    }

    helper.verificarExistenciaDeUsuario(validaUsuarioExpedientesService, usuariosSADEService,
        usuarioOrigen);
    helper.validarSistemaSolicitante(sistemaOrigen, sistemasSolicitantesArchivo);

    if (logger.isDebugEnabled()) {
      logger.debug("validacionesParaArchivoYSolicitudArchivo(String, String) - end");
    }
  }

  @Override
  @SuppressWarnings("static-access")
  public void validarAsignacionClaseExpedienteElectrico(
      final ExpedienteElectronicoDTO expedienteElectronico,
      final HistorialOperacionService historialOperacionService, final String sistemaUsuario,
      final String usuario, final Boolean isOperacionBloqueante, final boolean validarAsignacion)
      throws ParametroIncorrectoException, ExpedienteNoDisponibleException,
      ProcesoFallidoException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "validarAsignacionClaseExpedienteElectrico(expedienteElectronico={}, historialOperacionService={}, sistemaUsuario={}, usuario={}, isOperacionBloqueante={}, validarAsignacion={}) - start",
          expedienteElectronico, historialOperacionService, sistemaUsuario, usuario,
          isOperacionBloqueante, validarAsignacion);
    }

    helper.verificarExistenciaDeUsuario(validaUsuarioExpedientesService, usuariosSADEService,
        usuario);

    final WorkFlow workFlow = workflowService.createWorkFlow(expedienteElectronico.getId());
    if (validarAsignacion) {
      helper.validarAsignacion(workFlow, historialOperacionService, sistemaUsuario, usuario,
          isOperacionBloqueante);
    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "validarAsignacionClaseExpedienteElectrico(ExpedienteElectronico, HistorialOperacionService, String, String, Boolean, boolean) - end");
    }
  }

  @Override
  public ExpedienteElectronicoDTO obtenerExpedienteParaHacerDocsDefinitivos(final String codigoEE,
      final String sistemaUsuario, final String usuario) throws ParametroIncorrectoException,
      ProcesoFallidoException, ExpedienteNoDisponibleException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "obtenerExpedienteParaHacerDocsDefinitivos(codigoEE={}, sistemaUsuario={}, usuario={}) - start",
          codigoEE, sistemaUsuario, usuario);
    }

    final ExpedienteElectronicoDTO ee = obtenerExpedienteElectronico(codigoEE);
    validarAsignacionClaseExpedienteElectrico(ee, historialOperacionService, sistemaUsuario,
        usuario, false, true);

    if (logger.isDebugEnabled()) {
      logger.debug(
          "obtenerExpedienteParaHacerDocsDefinitivos(String, String, String) - end - return value={}",
          ee);
    }
    return ee;
  }

}
