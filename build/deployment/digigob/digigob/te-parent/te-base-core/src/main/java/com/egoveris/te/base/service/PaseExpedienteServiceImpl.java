package com.egoveris.te.base.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jbpm.api.ProcessEngine;
import org.jbpm.api.TaskQuery;
import org.jbpm.api.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.te.base.model.ActividadDTO;
import com.egoveris.te.base.model.ActividadInbox;
import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.model.ExpedienteAsociadoEntDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.service.expediente.ExpedienteAsociadoService;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.service.iface.IActividadExpedienteService;
import com.egoveris.te.base.util.BusinessFormatHelper;
import com.egoveris.te.base.util.ConstEstadoActividad;
import com.egoveris.te.base.util.ConstTipoActividad;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.model.exception.ParametroIncorrectoException;

@Service
@Transactional
public class PaseExpedienteServiceImpl implements PaseExpedienteService {

  private static final Logger logger = LoggerFactory.getLogger(PaseExpedienteServiceImpl.class);
  private static final String ESTADO_GUARDA_TEMPORAL = "Guarda Temporal";
  private static final String MOTIVO_DESVINCULACION_TRAMITACION_CONJUNTA = "Desvinculación en Tramitación Conjunta";
  private static final String SIGNAL_CIERRE = "Cierre";
  
  private static final String KEY_ID_EXPEDIENTE_ELECTRONICO = "idExpedienteElectronico";
  private static final String KEY_GRUPO_SELECCIONADO = "grupoSeleccionado";
  private static final String KEY_USUARIO_SELECCIONADO = "usuarioSeleccionado";
  private static final String KEY_DESTINATARIO = "destinatario";
  
  private static final String MSG_LOGGER_ERROR_ADQUIRIR = "Error al adquirir la tarea del Expediente: ";
  private static final String MSG_LOGGER_NO_PUDO_CERRAR = "No se pudo realizar el cierre";
  private static final String MSG_LOGGER_ENVIADO_GUARDA = "Se ha enviado a guarda temporal el expediente: ";

  @Autowired
  private ExpedienteElectronicoService expedienteService;
  @Autowired
  private String usuarioEnvioAutomaticoGT;
  @Autowired
  ProcessEngine processEngine;
  @Autowired
  private WorkFlowService workFlowService;
  @Autowired
  private TramitacionConjuntaService tramitacionConjuntaService;
  @Autowired
  private ExpedienteElectronicoService expedienteElectronicoService;
  @Autowired
  private ExpedienteAsociadoService expedienteAsociadoService;
  @Autowired
  private UsuariosSADEService usuarioSadeService;
  @Autowired
  private IActividadExpedienteService actividadExpediente;
  @Autowired
  private IActividadService actividadService;
  
  public void paseGuardaTemporal(ExpedienteElectronicoDTO expedienteElectronico, Task workingTask,
      String loggedUsername, Map<String, String> detalles, String estadoAnterior, String motivo) {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "paseGuardaTemporal(expedienteElectronico={}, workingTask={}, loggedUsername={}, detalles={}, estadoAnterior={}, motivo={}) - start",
          expedienteElectronico, workingTask, loggedUsername, detalles, estadoAnterior, motivo);
    }

    if (expedienteElectronico.getEsCabeceraTC() != null
        && expedienteElectronico.getEsCabeceraTC()) {

      // Genero documento de desvinculación

      DocumentoDTO documentoTC = tramitacionConjuntaService
          .generarDocumentoDeDesvinculacionEnTramitacionConjunta(expedienteElectronico,
              MOTIVO_DESVINCULACION_TRAMITACION_CONJUNTA, loggedUsername, workingTask);

      // Guardo la lista de expedientes asociados en un auxiliar para
      // luego poder borrar los objetos de la tabla.
      List<ExpedienteAsociadoEntDTO> listaExpAsociados = new ArrayList<>();
      List<ExpedienteAsociadoEntDTO> listaExpAsociadosAUX = expedienteElectronico
          .getListaExpedientesAsociados();
      for (ExpedienteAsociadoEntDTO expedienteAsociado : listaExpAsociadosAUX) {
        if (expedienteAsociado.getEsExpedienteAsociadoTC() != null
            && expedienteAsociado.getEsExpedienteAsociadoTC()) {
          listaExpAsociados.add(expedienteAsociado);
        }
      }

      // Desvinculo todos los expedientes hijos y los borro de la
      // lista del expediente.
      expedienteElectronico = this.tramitacionConjuntaService
          .desvincularExpedientesTramitacionConjunta(expedienteElectronico, loggedUsername,
              documentoTC);

      // Actualizo el expediente por que ya no es mas cabecera.
      expedienteElectronico.setEsCabeceraTC(false);
      // QUITO CEROS PARA GRABAR

      // Actualizo la tabla.
      for (ExpedienteAsociadoEntDTO expedienteAsociado : listaExpAsociados) {
        this.expedienteAsociadoService.deleteExpedienteAsociado(expedienteAsociado);
      }
    }

    // CREANDO EL PASE
    this.expedienteElectronicoService.generarPaseExpedienteElectronico(workingTask,
        expedienteElectronico, loggedUsername, ESTADO_GUARDA_TEMPORAL, motivo, detalles);

    // CAMBIO 6 - FIX EXPEDIENTES DESAPARECIDOS EN PRODUCCION
    detalles.put(KEY_USUARIO_SELECCIONADO, null);
    detalles.put(KEY_GRUPO_SELECCIONADO, ESTADO_GUARDA_TEMPORAL);
    detalles.put(KEY_ID_EXPEDIENTE_ELECTRONICO, expedienteElectronico.getId().toString());

    if (logger.isDebugEnabled()) {
      logger.debug(
          "paseGuardaTemporal(ExpedienteElectronico, Task, String, Map<String,String>, String, String) - end");
    }
  }

  public void paseAutoGuardaTemporal(ExpedienteElectronicoDTO ee, Task workingTask,
      String loggedUsername, Map<String, String> detalles, String estadoAnterior, String motivo,
      Map<String, ExpedienteElectronicoDTO> mapEE,
      Map<String, ExpedienteElectronicoDTO> mapEEProcesados) {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "paseAutoGuardaTemporal(ee={}, workingTask={}, loggedUsername={}, detalles={}, estadoAnterior={}, motivo={}, mapEE={}, mapEEProcesados={}) - start",
          ee, workingTask, loggedUsername, detalles, estadoAnterior, motivo, mapEE,
          mapEEProcesados);
    }

    List<ExpedienteAsociadoEntDTO> expFus = this.expedienteService
        .obtenerListaEnFusionados(ee.getId());
    List<ExpedienteAsociadoEntDTO> listAux = new ArrayList<>();
    boolean expFusDefinitivo = false;

    if (ee.getListaExpedientesAsociados() != null) {

      List<ExpedienteAsociadoEntDTO> listaExpAsociados = ee.getListaExpedientesAsociados();
      Iterator<ExpedienteAsociadoEntDTO> it = listaExpAsociados.iterator();

      // Valido si es fusion confirmada
      if (!expFus.isEmpty()) {

        for (ExpedienteAsociadoEntDTO expedienteAsociado : listaExpAsociados) {
          if (expedienteAsociado.getDefinitivo()) {
            expFusDefinitivo = true;
            break;
          }
        }
      }

      for (ExpedienteAsociadoEntDTO expedienteAsociado : listaExpAsociados) {

        String codigoEE = BusinessFormatHelper
            .formatearCaratula(expedienteAsociado.getAsNumeroSade());
        listAux.add(expedienteAsociado);

        if (mapEE.containsKey(codigoEE)) {

          mapEEProcesados.put(codigoEE, mapEE.get(codigoEE));
        }
      }

      // Si es TC confirmada genero el documento
      if (ee.getEsCabeceraTC() != null && ee.getEsCabeceraTC()
          && (expFus == null || expFus.isEmpty())) {

        ArrayList<ExpedienteAsociadoEntDTO> listaExpAsociadosAUX = new ArrayList<>();
        listaExpAsociadosAUX.addAll(ee.getListaExpedientesAsociados());

        DocumentoDTO documentoTC = tramitacionConjuntaService
            .generarDocumentoDeDesvinculacionEnTramitacionConjunta(ee,
                MOTIVO_DESVINCULACION_TRAMITACION_CONJUNTA, loggedUsername, workingTask);

        ee = this.tramitacionConjuntaService.desvincularExpedientesTramitacionConjunta(ee,
            loggedUsername, documentoTC);

        Iterator<ExpedienteAsociadoEntDTO> itAux = listAux.iterator();

        while (itAux.hasNext()) {

          ExpedienteAsociadoEntDTO expedienteAsociado = itAux.next();

          this.expedienteAsociadoService.deleteExpedienteAsociado(expedienteAsociado);
          itAux.remove();

          String codigoEE = BusinessFormatHelper
              .formatearCaratula(expedienteAsociado.getAsNumeroSade());

          if (mapEEProcesados.containsKey(codigoEE)) {

            ExpedienteElectronicoDTO exp = mapEEProcesados.get(codigoEE);

            // OBTENER TASK
            Task workingTaskHijo = null;
            try {
              workingTaskHijo = this.obtenerWorkingTask(exp);
            } catch (Exception e) {
              logger.error(MSG_LOGGER_ERROR_ADQUIRIR + exp.getCodigoCaratula(),
                  e);
            }

            if (exp.getBloqueado()) {
              exp.setBloqueado(false);
            }

            // CREANDO EL PASE A USUARIO ADMINISTRADOR
            detalles.put(KEY_DESTINATARIO, usuarioEnvioAutomaticoGT);
            detalles.put(KEY_ID_EXPEDIENTE_ELECTRONICO, String.valueOf(exp.getId()));
            this.expedienteElectronicoService.generarPaseExpedienteElectronico(workingTaskHijo,
                exp, loggedUsername, exp.getEstado(), motivo, detalles);

            // CREANDO EL PASE
            this.expedienteElectronicoService.generarPaseExpedienteElectronico(workingTaskHijo,
                exp, usuarioEnvioAutomaticoGT, ESTADO_GUARDA_TEMPORAL, motivo, detalles);

            signalExecution(ConstantesWeb.ESTADO_GUARDA_TEMPORAL, workingTaskHijo, detalles);

            if (workingTaskHijo != null) {
            	// 11-05-2020: No ejecutar el cierre, o desaparece la tarea
            	// processEngine.getExecutionService()
            	//     .signalExecutionById(workingTaskHijo.getExecutionId(), SIGNAL_CIERRE);
            } else {
              logger.info(MSG_LOGGER_NO_PUDO_CERRAR);
            }

            logger
                .info(MSG_LOGGER_ENVIADO_GUARDA + exp.getCodigoCaratula());
          }
        }

        ee.setEsCabeceraTC(false);

      } else if (!expFusDefinitivo) {

        while (it.hasNext()) {

          ExpedienteAsociadoEntDTO expedienteAsociado = it.next();

          this.expedienteAsociadoService.deleteExpedienteAsociado(expedienteAsociado);
          it.remove();

          String codigoEE = BusinessFormatHelper
              .formatearCaratula(expedienteAsociado.getAsNumeroSade());

          if (mapEEProcesados.containsKey(codigoEE)) {

            ExpedienteElectronicoDTO exp = mapEEProcesados.get(codigoEE);

            // OBTENER TASK
            Task workingTaskHijo = null;
            try {
              workingTaskHijo = this.obtenerWorkingTask(exp);
            } catch (Exception e) {
              logger.error(MSG_LOGGER_ERROR_ADQUIRIR + exp.getCodigoCaratula(),
                  e);
            }

            if (exp.getBloqueado()) {
              exp.setBloqueado(false);
            }

            // CREANDO EL PASE A USUARIO ADMINISTRADOR
            detalles.put(KEY_DESTINATARIO, usuarioEnvioAutomaticoGT);
            detalles.put(KEY_ID_EXPEDIENTE_ELECTRONICO, String.valueOf(exp.getId()));
            this.expedienteElectronicoService.generarPaseExpedienteElectronico(workingTaskHijo,
                exp, loggedUsername, exp.getEstado(), motivo, detalles);

            // CREANDO EL PASE
            this.expedienteElectronicoService.generarPaseExpedienteElectronico(workingTaskHijo,
                exp, usuarioEnvioAutomaticoGT, ESTADO_GUARDA_TEMPORAL, motivo, detalles);

            signalExecution(ConstantesWeb.ESTADO_GUARDA_TEMPORAL, workingTaskHijo, detalles);

            processEngine.getExecutionService()
                .signalExecutionById(workingTaskHijo.getExecutionId(), SIGNAL_CIERRE);

            logger
                .info(MSG_LOGGER_ENVIADO_GUARDA + exp.getCodigoCaratula());
          }
        }
      }
    }

    boolean tieneActividades = actividadExpediente.tieneActividades(ee);
    if (tieneActividades) {
      this.cancelarActividadesDe(ee, workingTask.getAssignee());
    }

    if (ee.getBloqueado()) {
      ee.setBloqueado(false);
    }

    // CREANDO EL PASE A USUARIO ADMINISTRADOR
    detalles.put(KEY_DESTINATARIO, usuarioEnvioAutomaticoGT);
    detalles.put(KEY_ID_EXPEDIENTE_ELECTRONICO, String.valueOf(ee.getId()));
    this.expedienteElectronicoService.generarPaseExpedienteElectronico(workingTask, ee,
        loggedUsername, ee.getEstado(), motivo, detalles);

    // CREANDO EL PASE
    this.expedienteElectronicoService.generarPaseExpedienteElectronico(workingTask, ee,
        usuarioEnvioAutomaticoGT, ESTADO_GUARDA_TEMPORAL, motivo, detalles);

    signalExecution(ConstantesWeb.ESTADO_GUARDA_TEMPORAL, workingTask, detalles);

    processEngine.getExecutionService().signalExecutionById(workingTask.getExecutionId(),
        SIGNAL_CIERRE);

    logger.info(MSG_LOGGER_ENVIADO_GUARDA + ee.getCodigoCaratula());
    mapEEProcesados.put(ee.getCodigoCaratula(), ee);

    detalles.put(KEY_USUARIO_SELECCIONADO, null);
    detalles.put(KEY_GRUPO_SELECCIONADO, ESTADO_GUARDA_TEMPORAL);
    detalles.put(KEY_ID_EXPEDIENTE_ELECTRONICO, ee.getId().toString());

    if (logger.isDebugEnabled()) {
      logger.debug(
          "paseAutoGuardaTemporal(ExpedienteElectronico, Task, String, Map<String,String>, String, String, Map<String,ExpedienteElectronico>, Map<String,ExpedienteElectronico>) - end");
    }
  }

  @Transactional
  public void paseGuardaTemporalHijoTC(ExpedienteElectronicoDTO ee, String loggedUsername,
      Map<String, String> detalles, String estadoAnterior, String motivo,
      Map<String, ExpedienteElectronicoDTO> mapEE,
      Map<String, ExpedienteElectronicoDTO> mapEEProcesados) {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "paseGuardaTemporalHijoTC(ee={}, loggedUsername={}, detalles={}, estadoAnterior={}, motivo={}, mapEE={}, mapEEProcesados={}) - start",
          ee, loggedUsername, detalles, estadoAnterior, motivo, mapEE, mapEEProcesados);
    }

    DocumentoDTO documentoTC;
    boolean padreEnLista = false;
    ExpedienteAsociadoEntDTO ea = this.expedienteAsociadoService
        .obtenerExpedienteAsociadoPorTC(ee.getNumero(), ee.getAnio(), true);

    ExpedienteElectronicoDTO expPadre = expedienteElectronicoService
        .buscarExpedienteElectronico(ea.getIdExpCabeceraTC());
    List<ExpedienteAsociadoEntDTO> listaExpAsociados = expPadre.getListaExpedientesAsociados();
    int cantidadExp = 0;
    Iterator<ExpedienteAsociadoEntDTO> it = listaExpAsociados.iterator();
    List<ExpedienteAsociadoEntDTO> listAux = new ArrayList<>();

    Task workingTask = null;
    try {
      workingTask = this.obtenerWorkingTask(expPadre);
    } catch (Exception e) {
      logger.error(MSG_LOGGER_ERROR_ADQUIRIR + ee.getCodigoCaratula(), e);
    }

    // Verifico si el padre esta en la lista
    if (mapEE.containsKey(expPadre.getCodigoCaratula())) {
      mapEEProcesados.put(expPadre.getCodigoCaratula(), expPadre);
      padreEnLista = true;
    }

    // Verifico si los hijos estan en la lista
    for (ExpedienteAsociadoEntDTO expedienteAsociado : listaExpAsociados) {

      String codigoEE = BusinessFormatHelper
          .formatearCaratula(expedienteAsociado.getAsNumeroSade());
      listAux.add(expedienteAsociado);

      if (mapEE.containsKey(codigoEE)) {

        mapEEProcesados.put(codigoEE, mapEE.get(codigoEE));
        cantidadExp++;
      }
    }

    // Si el padre esta en la lista
    if (padreEnLista) {

      // Valido si la TC es definitiva
      if (!ea.getDefinitivo()) {

        while (it.hasNext()) {

          ExpedienteAsociadoEntDTO expedienteAsociado = it.next();

          this.expedienteAsociadoService.deleteExpedienteAsociado(expedienteAsociado);
          it.remove();

          String codigoEE = BusinessFormatHelper
              .formatearCaratula(expedienteAsociado.getAsNumeroSade());

          if (mapEEProcesados.containsKey(codigoEE)) {

            ExpedienteElectronicoDTO exp = mapEEProcesados.get(codigoEE);

            // OBTENER TASK
            Task workingTaskHijo = null;
            try {
              workingTaskHijo = this.obtenerWorkingTask(exp);
            } catch (Exception e) {
              logger.error(MSG_LOGGER_ERROR_ADQUIRIR + exp.getCodigoCaratula(),
                  e);
            }

            if (exp.getBloqueado()) {
              exp.setBloqueado(false);
            }

            // CREANDO EL PASE A USUARIO ADMINISTRADOR
            detalles.put(KEY_DESTINATARIO, usuarioEnvioAutomaticoGT);
            detalles.put(KEY_ID_EXPEDIENTE_ELECTRONICO, String.valueOf(exp.getId()));
            this.expedienteElectronicoService.generarPaseExpedienteElectronico(workingTaskHijo,
                exp, loggedUsername, exp.getEstado(), motivo, detalles);

            // CREANDO EL PASE
            this.expedienteElectronicoService.generarPaseExpedienteElectronico(workingTaskHijo,
                exp, usuarioEnvioAutomaticoGT, ESTADO_GUARDA_TEMPORAL, motivo, detalles);

            signalExecution(ConstantesWeb.ESTADO_GUARDA_TEMPORAL, workingTaskHijo, detalles);

            if (workingTaskHijo != null) {
              // 11-05-2020: No ejecutar el cierre, o desaparece la tarea
              //processEngine.getExecutionService()
              //    .signalExecutionById(workingTaskHijo.getExecutionId(), SIGNAL_CIERRE);
            } else {
              logger.info(MSG_LOGGER_NO_PUDO_CERRAR);
            }

            logger
                .info(MSG_LOGGER_ENVIADO_GUARDA + exp.getCodigoCaratula());

          }
        }

        // Si es confirmada
      } else {

        documentoTC = tramitacionConjuntaService
            .generarDocumentoDeDesvinculacionEnTramitacionConjunta(expPadre,
                MOTIVO_DESVINCULACION_TRAMITACION_CONJUNTA, loggedUsername, workingTask);

        expPadre = this.tramitacionConjuntaService
            .desvincularExpedientesTramitacionConjunta(expPadre, loggedUsername, documentoTC);

        Iterator<ExpedienteAsociadoEntDTO> itAux = listAux.iterator();

        while (itAux.hasNext()) {

          ExpedienteAsociadoEntDTO expedienteAsociado = itAux.next();

          this.expedienteAsociadoService.deleteExpedienteAsociado(expedienteAsociado);
          itAux.remove();

          String codigoEE = BusinessFormatHelper
              .formatearCaratula(expedienteAsociado.getAsNumeroSade());

          if (mapEEProcesados.containsKey(codigoEE)) {

            ExpedienteElectronicoDTO exp = mapEEProcesados.get(codigoEE);

            // OBTENER TASK
            Task workingTaskHijo = null;
            try {
              workingTaskHijo = this.obtenerWorkingTask(exp);
            } catch (Exception e) {
              logger.error(MSG_LOGGER_ERROR_ADQUIRIR + exp.getCodigoCaratula(),
                  e);
            }

            if (exp.getBloqueado()) {
              exp.setBloqueado(false);
            }

            // CREANDO EL PASE A USUARIO ADMINISTRADOR
            detalles.put(KEY_DESTINATARIO, usuarioEnvioAutomaticoGT);
            detalles.put(KEY_ID_EXPEDIENTE_ELECTRONICO, String.valueOf(exp.getId()));
            this.expedienteElectronicoService.generarPaseExpedienteElectronico(workingTaskHijo,
                exp, loggedUsername, exp.getEstado(), motivo, detalles);

            // CREANDO EL PASE
            this.expedienteElectronicoService.generarPaseExpedienteElectronico(workingTaskHijo,
                exp, usuarioEnvioAutomaticoGT, ESTADO_GUARDA_TEMPORAL, motivo, detalles);

            signalExecution(ConstantesWeb.ESTADO_GUARDA_TEMPORAL, workingTaskHijo, detalles);
            if (workingTaskHijo != null) {
              processEngine.getExecutionService()
                  .signalExecutionById(workingTaskHijo.getExecutionId(), SIGNAL_CIERRE);
            } else {
              logger.info(MSG_LOGGER_NO_PUDO_CERRAR);
            }

            logger
                .info(MSG_LOGGER_ENVIADO_GUARDA + exp.getCodigoCaratula());
          }
        }
      }

      if (expPadre.getBloqueado()) {
        expPadre.setBloqueado(false);
      }

      // CREANDO EL PASE A DOMINGUEZRE
      detalles.put(KEY_DESTINATARIO, usuarioEnvioAutomaticoGT);
      detalles.put(KEY_ID_EXPEDIENTE_ELECTRONICO, String.valueOf(expPadre.getId()));
      this.expedienteElectronicoService.generarPaseExpedienteElectronico(workingTask, expPadre,
          loggedUsername, expPadre.getEstado(), motivo, detalles);

      // CREANDO EL PASE
      this.expedienteElectronicoService.generarPaseExpedienteElectronico(workingTask, expPadre,
          usuarioEnvioAutomaticoGT, ESTADO_GUARDA_TEMPORAL, motivo, detalles);

      signalExecution(ConstantesWeb.ESTADO_GUARDA_TEMPORAL, workingTask, detalles);
      if (workingTask != null) {
        processEngine.getExecutionService().signalExecutionById(workingTask.getExecutionId(),
            SIGNAL_CIERRE);
      } else {
        logger.info(MSG_LOGGER_NO_PUDO_CERRAR);
      }

      logger
          .info(MSG_LOGGER_ENVIADO_GUARDA + expPadre.getCodigoCaratula());
      mapEEProcesados.put(expPadre.getCodigoCaratula(), expPadre);

      // Si el padre no esta en la lista
    } else {

      // Valido si la TC es definitiva
      if (!ea.getDefinitivo()) {

        while (it.hasNext()) {

          ExpedienteAsociadoEntDTO expedienteAsociado = it.next();

          String codigoEE = BusinessFormatHelper
              .formatearCaratula(expedienteAsociado.getAsNumeroSade());

          if (mapEEProcesados.containsKey(codigoEE)) {

            this.expedienteAsociadoService.deleteExpedienteAsociado(expedienteAsociado);
            it.remove();

            ExpedienteElectronicoDTO exp = mapEEProcesados.get(codigoEE);

            // OBTENER TASK
            Task workingTaskHijo = null;
            try {
              workingTaskHijo = this.obtenerWorkingTask(exp);
            } catch (Exception e) {
              logger.error(MSG_LOGGER_ERROR_ADQUIRIR + exp.getCodigoCaratula(),
                  e);
            }

            if (exp.getBloqueado()) {
              exp.setBloqueado(false);
            }

            // CREANDO EL PASE A USUARIO ADMINISTRADORE
            detalles.put(KEY_DESTINATARIO, usuarioEnvioAutomaticoGT);
            detalles.put(KEY_ID_EXPEDIENTE_ELECTRONICO, String.valueOf(exp.getId()));
            this.expedienteElectronicoService.generarPaseExpedienteElectronico(workingTaskHijo,
                exp, loggedUsername, exp.getEstado(), motivo, detalles);

            // CREANDO EL PASE
            this.expedienteElectronicoService.generarPaseExpedienteElectronico(workingTaskHijo,
                exp, usuarioEnvioAutomaticoGT, ESTADO_GUARDA_TEMPORAL, motivo, detalles);

            signalExecution(ConstantesWeb.ESTADO_GUARDA_TEMPORAL, workingTaskHijo, detalles);

            processEngine.getExecutionService()
                .signalExecutionById(workingTaskHijo.getExecutionId(), SIGNAL_CIERRE);

            logger
                .info(MSG_LOGGER_ENVIADO_GUARDA + exp.getCodigoCaratula());
          }
        }

        // Si es confirmada
      } else {

        boolean noTC = false;
        documentoTC = tramitacionConjuntaService
            .generarDocumentoDeDesvinculacionEnTramitacionConjunta(expPadre,
                MOTIVO_DESVINCULACION_TRAMITACION_CONJUNTA, loggedUsername, workingTask);

        // Valido si estan todos los hijos en la lista
        if (cantidadExp == listaExpAsociados.size()) {
          noTC = true;
        }

        while (it.hasNext()) {

          ExpedienteAsociadoEntDTO expedienteAsociado = it.next();

          // OBTENER TASK
          Task workingTaskHijo = null;
          try {
            workingTaskHijo = expedienteElectronicoService
                .obtenerWorkingTask(expedienteAsociado.getIdTask());
          } catch (Exception e) {
            logger.error(MSG_LOGGER_ERROR_ADQUIRIR
                + expedienteAsociado.getAsNumeroSade(), e);
          }

          if (noTC) {
            it.remove();
            this.expedienteAsociadoService.deleteExpedienteAsociado(expedienteAsociado);
          }

          String codigoEE = BusinessFormatHelper
              .formatearCaratula(expedienteAsociado.getAsNumeroSade());

          if (mapEEProcesados.containsKey(codigoEE)) {

            ExpedienteElectronicoDTO expElectronico = this.expedienteElectronicoService
                .obtenerExpedienteElectronico(expedienteAsociado.getTipoDocumento(),
                    expedienteAsociado.getAnio(), expedienteAsociado.getNumero(),
                    expedienteAsociado.getCodigoReparticionUsuario());
            expElectronico.getDocumentos().add(documentoTC);

            if (!noTC) {
              it.remove();
              this.expedienteAsociadoService.deleteExpedienteAsociado(expedienteAsociado);
            }

            ExpedienteElectronicoDTO exp = mapEEProcesados.get(codigoEE);

            if (exp.getBloqueado()) {
              exp.setBloqueado(false);
            }

            // CREANDO EL PASE A USUARIO ADMINISTRADOR
            detalles.put(KEY_DESTINATARIO, usuarioEnvioAutomaticoGT);
            detalles.put(KEY_ID_EXPEDIENTE_ELECTRONICO, String.valueOf(exp.getId()));
            this.expedienteElectronicoService.generarPaseExpedienteElectronico(workingTaskHijo,
                exp, loggedUsername, exp.getEstado(), motivo, detalles);

            // CREANDO EL PASE
            this.expedienteElectronicoService.generarPaseExpedienteElectronico(workingTaskHijo,
                exp, usuarioEnvioAutomaticoGT, ESTADO_GUARDA_TEMPORAL, motivo, detalles);

            signalExecution(ConstantesWeb.ESTADO_GUARDA_TEMPORAL, workingTaskHijo, detalles);

            processEngine.getExecutionService()
                .signalExecutionById(workingTaskHijo.getExecutionId(), SIGNAL_CIERRE);

            logger
                .info(MSG_LOGGER_ENVIADO_GUARDA + exp.getCodigoCaratula());
          }
        }
      }
    }

    // Actualizo el expediente por que ya no es mas cabecera.
    if (expPadre.getListaExpedientesAsociados() == null
        || expPadre.getListaExpedientesAsociados().isEmpty()) {
      expPadre.setEsCabeceraTC(false);
    }

    this.expedienteElectronicoService.grabarExpedienteElectronico(expPadre);

    detalles.put(KEY_USUARIO_SELECCIONADO, null);
    detalles.put(KEY_GRUPO_SELECCIONADO, ESTADO_GUARDA_TEMPORAL);
    detalles.put(KEY_ID_EXPEDIENTE_ELECTRONICO, ee.getId().toString());

    if (logger.isDebugEnabled()) {
      logger.debug(
          "paseGuardaTemporalHijoTC(ExpedienteElectronico, String, Map<String,String>, String, String, Map<String,ExpedienteElectronico>, Map<String,ExpedienteElectronico>) - end");
    }
  }

  @Transactional
  public void paseGuardaTemporalHijoFusion(ExpedienteElectronicoDTO ee, String loggedUsername,
      Map<String, String> detalles, String estadoAnterior, String motivo,
      Map<String, ExpedienteElectronicoDTO> mapEE,
      Map<String, ExpedienteElectronicoDTO> mapEEProcesados) {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "paseGuardaTemporalHijoFusion(ee={}, loggedUsername={}, detalles={}, estadoAnterior={}, motivo={}, mapEE={}, mapEEProcesados={}) - start",
          ee, loggedUsername, detalles, estadoAnterior, motivo, mapEE, mapEEProcesados);
    }

    boolean padreEnLista = false;
    ExpedienteAsociadoEntDTO ea = this.expedienteAsociadoService
        .obtenerExpedienteAsociadoPorFusion(ee.getNumero(), ee.getAnio(), true);

    ExpedienteElectronicoDTO expPadre = expedienteElectronicoService
        .buscarExpedienteElectronico(ea.getIdExpCabeceraTC());
    List<ExpedienteAsociadoEntDTO> listaExpAsociados = expPadre.getListaExpedientesAsociados();
    Iterator<ExpedienteAsociadoEntDTO> it = listaExpAsociados.iterator();

    // Verifico si esta el padre en la lista
    if (mapEE.containsKey(expPadre.getCodigoCaratula())) {
      mapEEProcesados.put(expPadre.getCodigoCaratula(), expPadre);
      padreEnLista = true;
    }

    // Verifico si los hijos estan en la lista
    for (ExpedienteAsociadoEntDTO expedienteAsociado : listaExpAsociados) {

      String codigoEE = BusinessFormatHelper
          .formatearCaratula(expedienteAsociado.getAsNumeroSade());

      if (mapEE.containsKey(codigoEE)) {

        mapEEProcesados.put(codigoEE, mapEE.get(codigoEE));
      }
    }

    while (it.hasNext()) {

      ExpedienteAsociadoEntDTO expedienteAsociado = it.next();

      String codigoEE = BusinessFormatHelper
          .formatearCaratula(expedienteAsociado.getAsNumeroSade());

      if (padreEnLista) {
        this.expedienteAsociadoService.deleteExpedienteAsociado(expedienteAsociado);
        it.remove();
      }

      if (mapEEProcesados.containsKey(codigoEE)) {

        ExpedienteElectronicoDTO exp = mapEEProcesados.get(codigoEE);

        if (!padreEnLista) {
          this.expedienteAsociadoService.deleteExpedienteAsociado(expedienteAsociado);
          it.remove();
        }

        // OBTENER TASK
        Task workingTaskHijo = null;
        try {
          workingTaskHijo = this.obtenerWorkingTask(exp);
        } catch (Exception e) {
          logger.error(MSG_LOGGER_ERROR_ADQUIRIR + exp.getCodigoCaratula(), e);
        }

        if (exp.getBloqueado()) {
          exp.setBloqueado(false);
        }

        // CREANDO EL PASE A USUARIO ADMINISTRADOR
        detalles.put(KEY_DESTINATARIO, usuarioEnvioAutomaticoGT);
        detalles.put(KEY_ID_EXPEDIENTE_ELECTRONICO, String.valueOf(exp.getId()));
        this.expedienteElectronicoService.generarPaseExpedienteElectronico(workingTaskHijo, exp,
            loggedUsername, exp.getEstado(), motivo, detalles);

        // CREANDO EL PASE
        this.expedienteElectronicoService.generarPaseExpedienteElectronico(workingTaskHijo, exp,
            usuarioEnvioAutomaticoGT, ESTADO_GUARDA_TEMPORAL, motivo, detalles);

        signalExecution(ConstantesWeb.ESTADO_GUARDA_TEMPORAL, workingTaskHijo, detalles);

        if (workingTaskHijo != null) {
          // 11-05-2020: No ejecutar el cierre, o desaparece la tarea
          //processEngine.getExecutionService().signalExecutionById(workingTaskHijo.getExecutionId(),
          //    SIGNAL_CIERRE);
        } else {
          logger.info(MSG_LOGGER_NO_PUDO_CERRAR);
        }

        logger.info(MSG_LOGGER_ENVIADO_GUARDA + exp.getCodigoCaratula());

      }
    }

    if (padreEnLista) {

      // OBTENER TASK
      Task workingTask = null;
      try {
        workingTask = this.obtenerWorkingTask(expPadre);
      } catch (Exception e) {
        logger.error(MSG_LOGGER_ERROR_ADQUIRIR + expPadre.getCodigoCaratula(),
            e);
      }

      if (expPadre.getBloqueado()) {
        expPadre.setBloqueado(false);
      }

      // CREANDO EL PASE A USUARIO ADMINISTRADOR
      detalles.put(KEY_DESTINATARIO, usuarioEnvioAutomaticoGT);
      detalles.put(KEY_ID_EXPEDIENTE_ELECTRONICO, String.valueOf(expPadre.getId()));
      this.expedienteElectronicoService.generarPaseExpedienteElectronico(workingTask, expPadre,
          loggedUsername, expPadre.getEstado(), motivo, detalles);

      // CREANDO EL PASE
      this.expedienteElectronicoService.generarPaseExpedienteElectronico(workingTask, expPadre,
          usuarioEnvioAutomaticoGT, ESTADO_GUARDA_TEMPORAL, motivo, detalles);

      signalExecution(ConstantesWeb.ESTADO_GUARDA_TEMPORAL, workingTask, detalles);

      if (workingTask != null) {
        processEngine.getExecutionService().signalExecutionById(workingTask.getExecutionId(),
            SIGNAL_CIERRE);
      } else {
        logger.info(MSG_LOGGER_NO_PUDO_CERRAR);
      }

      logger
          .info(MSG_LOGGER_ENVIADO_GUARDA + expPadre.getCodigoCaratula());
      mapEEProcesados.put(expPadre.getCodigoCaratula(), expPadre);
    }

    detalles.put(KEY_USUARIO_SELECCIONADO, null);
    detalles.put(KEY_GRUPO_SELECCIONADO, ESTADO_GUARDA_TEMPORAL);
    detalles.put(KEY_ID_EXPEDIENTE_ELECTRONICO, ee.getId().toString());

    if (logger.isDebugEnabled()) {
      logger.debug(
          "paseGuardaTemporalHijoFusion(ExpedienteElectronico, String, Map<String,String>, String, String, Map<String,ExpedienteElectronico>, Map<String,ExpedienteElectronico>) - end");
    }
  }

  public Task obtenerWorkingTask(ExpedienteElectronicoDTO expedienteElectronico)
      throws ParametroIncorrectoException {
    if (logger.isDebugEnabled()) {
      logger.debug("obtenerWorkingTask(expedienteElectronico={}) - start", expedienteElectronico);
    }

    if (expedienteElectronico != null) {
      TaskQuery taskQuery = processEngine.getTaskService().createTaskQuery()
          .executionId(expedienteElectronico.getIdWorkflow());

      Task returnTask = taskQuery.uniqueResult();
      if (logger.isDebugEnabled()) {
        logger.debug("obtenerWorkingTask(ExpedienteElectronico) - end - return value={}",
            returnTask);
      }
      return returnTask;

    } else {
      throw new ParametroIncorrectoException("El expediente electrónico es nulo.", null);
    }
  }

  public void signalExecution(String nameTransition, Task workingTask,
      Map<String, String> detalles) {
    if (logger.isDebugEnabled()) {
      logger.debug("signalExecution(nameTransition={}, workingTask={}, detalles={}) - start",
          nameTransition, workingTask, detalles);
    }

    // PASO TODAS LAS VARIABLES ANTES DE REALIZAR EL SIGNAL
    setearVariablesAlWorkflow(workingTask, detalles);

    // Paso a la siguiente Tarea/Estado definida en el Workflow
    processEngine.getExecutionService().signalExecutionById(workingTask.getExecutionId(),
        nameTransition);

    if (logger.isDebugEnabled()) {
      logger.debug("signalExecution(String, Task, Map<String,String>) - end");
    }
  }

  private void setearVariablesAlWorkflow(Task workingTask, Map<String, String> detalles) {
    if (logger.isDebugEnabled()) {
      logger.debug("setearVariablesAlWorkflow(workingTask={}, detalles={}) - start", workingTask,
          detalles);
    }

    Map<String, Object> variables = new HashMap<>();
    variables.put("estadoAnterior", detalles.get("estadoAnterior"));
    variables.put("estadoAnteriorParalelo", detalles.get("estadoAnteriorParalelo"));
    variables.put(KEY_GRUPO_SELECCIONADO, detalles.get(KEY_GRUPO_SELECCIONADO));
    variables.put("tareaGrupal", detalles.get("tareaGrupal"));
    variables.put(KEY_USUARIO_SELECCIONADO, detalles.get(KEY_USUARIO_SELECCIONADO));
    variables.put(KEY_ID_EXPEDIENTE_ELECTRONICO,
        Integer.parseInt(detalles.get(KEY_ID_EXPEDIENTE_ELECTRONICO)));
    setVariablesWorkFlow(variables, workingTask);

    if (logger.isDebugEnabled()) {
      logger.debug("setearVariablesAlWorkflow(Task, Map<String,String>) - end");
    }
  }

  public void setVariablesWorkFlow(Map<String, Object> variables, Task workingTask) {
    if (logger.isDebugEnabled()) {
      logger.debug("setVariablesWorkFlow(variables={}, workingTask={}) - start", variables,
          workingTask);
    }

    workFlowService.setVariables(processEngine, workingTask.getExecutionId(), variables);

    if (logger.isDebugEnabled()) {
      logger.debug("setVariablesWorkFlow(Map<String,Object>, Task) - end");
    }
  }

  public void cancelarActividadesDe(ExpedienteElectronicoDTO ee, String assignee) {
    if (logger.isDebugEnabled()) {
      logger.debug("cancelarActividadesDe(ee={}, assignee={}) - start", ee, assignee);
    }

    List<ActividadInbox> activs = actividadExpediente
        .buscarHistoricoActividades(ee.getIdWorkflow());
    Usuario user = usuarioSadeService.getDatosUsuario(assignee);
    String asignador = usuarioSadeService.buscarAsignadorDeSectorDesestimandoUsuarios(
        user.getCodigoReparticion(), user.getCodigoSectorInterno(), new ArrayList<String>());
    String userQueCancela;
    if (asignador == null) {
      userQueCancela = assignee;
    } else {
      userQueCancela = asignador;
    }
    for (ActividadInbox act : activs) {
      ActividadDTO actividad = actividadService.buscarActividad(act.getId());
      if (act.getEstado().equals(ConstEstadoActividad.ESTADO_PENDIENTE) || act.getEstado().equals(ConstEstadoActividad.ESTADO_ABIERTA)) {
        if (actividad.getTipoActividad().getNombre()
            .equalsIgnoreCase(ConstTipoActividad.ACTIVIDAD_TIPO_SOLICITUD_SUBSANACION)) {
          actividadExpediente.eliminarActividadSubsanacion(ee, userQueCancela);
          break;
        }
        
        actividad.setEstado(ConstEstadoActividad.ESTADO_CANCELADA);
        actividad.setFechaCierre(new Date());
        actividad.setUsuarioCierre(userQueCancela);
        actividadService.actualizarActividad(actividad);
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("cancelarActividadesDe(ExpedienteElectronico, String) - end");
    }
  }
 

}