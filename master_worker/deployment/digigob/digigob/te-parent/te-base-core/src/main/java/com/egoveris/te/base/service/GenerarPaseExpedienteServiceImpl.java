package com.egoveris.te.base.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections4.Predicate;
import org.apache.commons.lang.StringUtils;
import org.jbpm.api.JbpmException;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.ffdd.ws.service.ExternalTransaccionService;
import com.egoveris.plugins.manager.PluginManager;
import com.egoveris.plugins.manager.tools.ReflectionUtil;
import com.egoveris.sharedorganismo.base.service.ReparticionServ;
import com.egoveris.sharedorganismo.base.service.SectorInternoServ;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.service.iface.IActividadExpedienteService;
import com.egoveris.te.base.service.iface.IBloqueoExpedienteService;
import com.egoveris.te.base.service.iface.IGenerarPaseExpedienteService;
import com.egoveris.te.base.states.IState;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.model.exception.ExpedienteNoDisponibleException;
import com.egoveris.te.model.exception.ParametroIncorrectoException;
import com.egoveris.te.model.exception.ProcesoFallidoException;
import com.egoveris.te.model.model.PaseExpedienteRequest;

@Service
@Transactional
public class GenerarPaseExpedienteServiceImpl extends ExternalServiceAbstract
    implements IGenerarPaseExpedienteService, ApplicationContextAware {
  private static final String REPARTICION_SECTOR_ARCHIVADO = "Guarda Temporal";
  private final static Logger logger = LoggerFactory
      .getLogger(GenerarPaseExpedienteServiceImpl.class);

  private ApplicationContext ctx;
  /**
   * Defino los servicios que voy a utilizar
   */
  @Autowired
  IActividadExpedienteService actividadExpedienteService;
  @Autowired
  private ValidaUsuarioExpedientesService validacionUsuario;
  @Autowired
  private IBloqueoExpedienteService bloqueoExpedienteService;
  @Autowired
  private ExpedienteElectronicoService expedienteElectronicoService;
  @Autowired
  private UsuariosSADEService usuariosSADEService;
  @Autowired
  private ReparticionServ reparticionServ;
  @Autowired
  private SectorInternoServ sectorInternoServ;
  @Autowired
  private ProcessEngine processEngine;
  @Autowired
  private ServicioAdministracion servicioAdministracionFactory;
  @Autowired
  private String reparticionGT;
  @Autowired
  private DocumentoGedoService documentacionGedoService;
  @Autowired
  private TipoDocumentoService tipoDocumentoService;
  @Autowired
  private ExternalTransaccionService transactionFFCCService;

  private PluginManager pm;

  private void archivar(final Map<String, String> variables, final ExpedienteElectronicoDTO ee,
      final Task workingTask) throws ProcesoFallidoException {
    if (logger.isDebugEnabled()) {
      logger.debug("archivar(variables={}, ee={}, workingTask={}) - start", variables, ee,
          workingTask);
    }

    try {
      variables.put(ConstantesWeb.USUARIO_DESTINO, variables.get(ConstantesWeb.USUARIO_ORIGEN));
      variables.put(ConstantesWeb.GRUPO_SELECCIONADO, REPARTICION_SECTOR_ARCHIVADO);

      final Map<String, String> variablesEnHistorialOperacion = this
          .setearVariablesEnHistorialOperacion(variables);

      expedienteElectronicoService.generarPaseExpedienteElectronico(workingTask, ee,
          variables.get(ConstantesWeb.USUARIO_DESTINO), variables.get(ConstantesWeb.ESTADO_SELECCIONADO),
          variables.get(ConstantesWeb.MOTIVO), variablesEnHistorialOperacion);

      // Avanza la tarea en el workflow
      signalExecution(workingTask, variables.get(ConstantesWeb.ESTADO_SELECCIONADO), variables);

      // Vuelve a avanzar la tarea en el workflow para cerrar la misma.
      
      // 11-05-2020: No ejecutar el cierre, o desaparece la tarea
      //signalExecution(workingTask, "Cierre");
    } catch (final Exception e) {
      logger.error("archivar(Map<String,String>, ExpedienteElectronico, Task)", e);

      throw new ProcesoFallidoException("Error al archivar el expediente", e);
    } finally {
    }

    if (logger.isDebugEnabled()) {
      logger.debug("archivar(Map<String,String>, ExpedienteElectronico, Task) - end");
    }
  }

  private String ArmarMensaje(final PaseExpedienteRequest datosPase, final String usuarioApoderado,
      final String usuario, final String estadoSeleccionado, final String codigoEE) {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "ArmarMensaje(datosPase={}, usuarioApoderado={}, usuario={}, estadoSeleccionado={}, codigoEE={}) - start",
          datosPase, usuarioApoderado, usuario, estadoSeleccionado, codigoEE);
    }

    String mensaje = "Se generó el pase del expediente: " + codigoEE + ", se envió ";

    if (usuarioApoderado != null) {
      mensaje = mensaje + "al usuario apoderado: " + datosPase.getUsuarioDestino()
          + " en reemplazo por la licencia de " + usuario;

      if (logger.isDebugEnabled()) {
        logger.debug(
            "ArmarMensaje(PaseExpedienteRequest, String, String, String, String) - end - return value={}",
            mensaje);
      }
      return mensaje;
    } else if (!StringUtils.isBlank(datosPase.getUsuarioDestino())
        && datosPase.getEsUsuarioDestino()) {
      mensaje = mensaje + "al usuario: " + datosPase.getUsuarioDestino();

      if (logger.isDebugEnabled()) {
        logger.debug(
            "ArmarMensaje(PaseExpedienteRequest, String, String, String, String) - end - return value={}",
            mensaje);
      }
      return mensaje;
    } else if (!StringUtils.isBlank(datosPase.getReparticionDestino())
        && datosPase.getEsReparticionDestino()) {
      mensaje = mensaje + "a la repartición: " + datosPase.getReparticionDestino();

      if (logger.isDebugEnabled()) {
        logger.debug(
            "ArmarMensaje(PaseExpedienteRequest, String, String, String, String) - end - return value={}",
            mensaje);
      }
      return mensaje;
    } else if (!StringUtils.isBlank(datosPase.getSectorDestino())
        && datosPase.getEsSectorDestino()) {
      mensaje = mensaje + "al sector: " + datosPase.getReparticionDestino().trim() + "-"
          + datosPase.getSectorDestino();

      if (logger.isDebugEnabled()) {
        logger.debug(
            "ArmarMensaje(PaseExpedienteRequest, String, String, String, String) - end - return value={}",
            mensaje);
      }
      return mensaje;
    } else if (datosPase.getEsMesaDestino()) {
      mensaje = mensaje + "a la mesa de la repartición: " + datosPase.getReparticionDestino();

      if (logger.isDebugEnabled()) {
        logger.debug(
            "ArmarMensaje(PaseExpedienteRequest, String, String, String, String) - end - return value={}",
            mensaje);
      }
      return mensaje;
    } else {
      String estadoMensaje = null;
      if (ConstantesWeb.ESTADO_GUARDA_TEMPORAL.equals(estadoSeleccionado)) {
        estadoMensaje = ConstantesWeb.ESTADO_GUARDA_TEMPORAL;
      } else if (ConstantesWeb.ESTADO_SOLICITUD_ARCHIVO.equals(estadoSeleccionado)) {
        estadoMensaje = ConstantesWeb.ESTADO_SOLICITUD_ARCHIVO;
      } else if (ConstantesWeb.ESTADO_ARCHIVO.equals(estadoSeleccionado)) {
        estadoMensaje = ConstantesWeb.ESTADO_ARCHIVO;
      }
      mensaje = mensaje + "a " + estadoMensaje;

      if (logger.isDebugEnabled()) {
        logger.debug(
            "ArmarMensaje(PaseExpedienteRequest, String, String, String, String) - end - return value={}",
            mensaje);
      }
      return mensaje;
    }
  }

  private void cargarVariables(final Map<String, String> variables)
      throws ProcesoFallidoException {
    if (logger.isDebugEnabled()) {
      logger.debug("cargarVariables(variables={}) - start", variables);
    }

    if (StringUtils.equals(variables.get(ConstantesWeb.ES_USUARIO_DESTINO), "1")) {
      variables.put(ConstantesWeb.GRUPO_SELECCIONADO, StringUtils.EMPTY);
      variables.put(ConstantesWeb.TAREA_GRUPAL, "noEsTareaGrupal");
      variables.put(ConstantesWeb.DESTINATARIO, variables.get(ConstantesWeb.USUARIO_SELECCIONADO));
    } else if (variables.get(ConstantesWeb.ES_REPARTICION_DESTINO).equals("1")) {
      variables.put(ConstantesWeb.DESTINATARIO, variables.get(ConstantesWeb.REPARTICION_DESTINO));
      variables.put(ConstantesWeb.GRUPO_SELECCIONADO, variables.get(ConstantesWeb.REPARTICION_DESTINO));
      variables.put(ConstantesWeb.USUARIO_DESTINO, StringUtils.EMPTY);
      variables.put(ConstantesWeb.TAREA_GRUPAL, "esTareaGrupal");
    } else if (variables.get(ConstantesWeb.ES_SECTOR_DESTINO).equals("1")
        || variables.get(ConstantesWeb.ES_MESA_DESTINO).equals("1")) {
      variables.put(ConstantesWeb.DESTINATARIO, variables.get(ConstantesWeb.REPARTICION_DESTINO) + "-"
          + variables.get(ConstantesWeb.SECTOR_DESTINO));
      variables.put(ConstantesWeb.GRUPO_SELECCIONADO, variables.get(ConstantesWeb.REPARTICION_DESTINO)
          + "-" + variables.get(ConstantesWeb.SECTOR_DESTINO));
      variables.put(ConstantesWeb.USUARIO_DESTINO, null);
      variables.put(ConstantesWeb.TAREA_GRUPAL, "esTareaGrupal");
    } else {
      throw new ProcesoFallidoException("Debe seleccionar un destino válido para el pase.", null);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("cargarVariables(Map<String,String>) - end");
    }
  }

  private Map<String, String> cargarVariablesLocales(final PaseExpedienteRequest datosPase,
      final ExpedienteElectronicoDTO expedienteElectronico, final String aclaracion)
      throws ProcesoFallidoException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "cargarVariablesLocales(datosPase={}, expedienteElectronico={}, aclaracion={}) - start",
          datosPase, expedienteElectronico, aclaracion);
    }

    String estadoSeleccionado = StringUtils.EMPTY;
    final Map<String, String> variablesLocales = new HashMap<String, String>();

    try {
      if (StringUtils.isEmpty(datosPase.getEstadoSeleccionado())) {
        estadoSeleccionado = expedienteElectronico.getEstado();
      } else {
        estadoSeleccionado = datosPase.getEstadoSeleccionado();
      }

      /********************************************************************************************************************
       * Variables para guardar en historial de la operacion. En la versión
       * anterior es la lista "detalles" *
       ********************************************************************************************************************/
      variablesLocales.put(ConstantesWeb.ESTADO_ANTERIOR, expedienteElectronico.getEstado());
      variablesLocales.put(ConstantesWeb.ESTADO_ANTERIOR_PARALELO, null);
      variablesLocales.put(ConstantesWeb.LOGGED_USERNAME, datosPase.getUsuarioOrigen());
      // La variable de instancia motivoPase se "transforma" en la
      // variable motivo del hashmap
      variablesLocales.put(ConstantesWeb.ACLARACION, aclaracion);
      variablesLocales.put(ConstantesWeb.MOTIVO, datosPase.getMotivoPase());
      variablesLocales.put(ConstantesWeb.ESTADO, estadoSeleccionado);
      variablesLocales.put(ConstantesWeb.TAREA_GRUPAL, StringUtils.EMPTY);
      variablesLocales.put(ConstantesWeb.USUARIO_SELECCIONADO, datosPase.getUsuarioDestino());
      variablesLocales.put(ConstantesWeb.GRUPO_SELECCIONADO, StringUtils.EMPTY);
      variablesLocales.put(ConstantesWeb.ID_EXPEDIENTE_ELECTRONICO,
          expedienteElectronico.getId().toString());
      /*************************************************************************************************
       * Variables "de instancia" *
       *************************************************************************************************/
      variablesLocales.put(ConstantesWeb.ES_USUARIO_DESTINO,
          datosPase.getEsUsuarioDestino() ? "1" : "0");
      variablesLocales.put(ConstantesWeb.ES_SECTOR_DESTINO,
          datosPase.getEsSectorDestino() ? "1" : "0");
      variablesLocales.put(ConstantesWeb.ES_REPARTICION_DESTINO,
          datosPase.getEsReparticionDestino() ? "1" : "0");
      variablesLocales.put(ConstantesWeb.ES_MESA_DESTINO, datosPase.getEsMesaDestino() ? "1" : "0");
      variablesLocales.put(ConstantesWeb.ESTADO_SELECCIONADO, estadoSeleccionado);
      variablesLocales.put(ConstantesWeb.USUARIO_ORIGEN, datosPase.getUsuarioOrigen());
      variablesLocales.put(ConstantesWeb.USUARIO_DESTINO, datosPase.getUsuarioDestino());
      variablesLocales.put(ConstantesWeb.SECTOR_DESTINO, datosPase.getSectorDestino());
      variablesLocales.put(ConstantesWeb.REPARTICION_DESTINO, datosPase.getReparticionDestino());
      variablesLocales.put(ConstantesWeb.SISTEMA_APODERADO, datosPase.getSistemaOrigen());
      variablesLocales.put(ConstantesWeb.USUARIO_PRODUCTOR, datosPase.getUsuarioDestino());

      if (logger.isDebugEnabled()) {
        logger.debug(
            "cargarVariablesLocales(PaseExpedienteRequest, ExpedienteElectronico, String) - end - return value={}",
            variablesLocales);
      }
      return variablesLocales;
    } catch (final Exception e) {
      logger.error("cargarVariablesLocales(PaseExpedienteRequest, ExpedienteElectronico, String)",
          e);

      throw new ProcesoFallidoException(
          "No se ha podido recuperar el expediente sobre el cuál se desea realizar el pase.", e);
    }
  }

  private Map<String, String> cargarVariablesParaSistemasArchivadores(
      final PaseExpedienteRequest datosPase) {
    if (logger.isDebugEnabled()) {
      logger.debug("cargarVariablesParaSistemasArchivadores(datosPase={}) - start", datosPase);
    }

    final Map<String, String> resultado = new HashMap<String, String>();

    resultado.put(ConstantesWeb.MOTIVO, datosPase.getMotivoPase());
    resultado.put(ConstantesWeb.ESTADO, datosPase.getEstadoSeleccionado());
    resultado.put(ConstantesWeb.LOGGED_USERNAME, datosPase.getUsuarioOrigen());
    resultado.put(ConstantesWeb.USUARIO_ORIGEN, datosPase.getUsuarioOrigen());
    resultado.put(ConstantesWeb.SISTEMA_APODERADO, datosPase.getSistemaOrigen());
    resultado.put(ConstantesWeb.DESTINATARIO, reparticionGT);
    resultado.put(ConstantesWeb.ESTADO_SELECCIONADO, datosPase.getEstadoSeleccionado());

    if (logger.isDebugEnabled()) {
      logger.debug(
          "cargarVariablesParaSistemasArchivadores(PaseExpedienteRequest) - end - return value={}",
          resultado);
    }
    return resultado;
  }

  private void enviarPase(final Map<String, String> variables, final ExpedienteElectronicoDTO ee,
      final Task workingTask, final String numeroSadePase, final Boolean generarDoc)
      throws ProcesoFallidoException, ParametroIncorrectoException,
      ExpedienteNoDisponibleException {
	  
	  
	String startScript = null; 
    if (logger.isDebugEnabled()) {
      logger.debug(
          "enviarPase(variables={}, ee={}, workingTask={}, numeroSadePase={}, generarDoc={}) - start",
          variables, ee, workingTask, numeroSadePase, generarDoc);
    }

    // ------------------------
    final String workflowname = workingTask.getExecutionId().substring(0,
        workingTask.getExecutionId().indexOf("."));
    final String stateName = workingTask.getName();

    final List<Object> instances = getPluginManager().getInstancesOf(new Predicate<Class<?>>() {
      @Override
      public boolean evaluate(final Class<?> clazz) {
        if (logger.isDebugEnabled()) {
          logger.debug("$Predicate<Class<?>>.evaluate(clazz={}) - start", clazz);
        }
        // CLASS EVALUATOR
        final boolean returnboolean = ReflectionUtil.hasInterface(clazz, IState.class);
        if (logger.isDebugEnabled()) {
          logger.debug("$Predicate<Class<?>>.evaluate(Class<?>) - end - return value={}",
              returnboolean);
        }
        return returnboolean;
      }
    }, new Predicate<Object>() { // OBJECT EVALUATOR
      @Override
      public boolean evaluate(final Object object) {
        if (logger.isDebugEnabled()) {
          logger.debug("$Predicate<Object>.evaluate(object={}) - start", object);
        }

        final boolean returnboolean = object != null
            && ((IState) object).is(workflowname, stateName);
        if (logger.isDebugEnabled()) {
          logger.debug("$Predicate<Object>.evaluate(Object) - end - return value={}",
              returnboolean);
        }
        return returnboolean;
      }
    });

    if (instances != null && !instances.isEmpty()) { // si encuentra algun
      // estado
      // configurado
      if (instances.size() > 1) {
        throw new ParametroIncorrectoException("Multiple workflows posibles.", null);
      }

      final WebserviceState state = new WebserviceState(instances.get(0));
      state.setProcessEngine(this.processEngine);
      state.setWorkingTask(workingTask);
      state.setExpedienteElectronico(ee);
      state.setUserLogged(
          usuariosSADEService.getDatosUsuario(variables.get(ConstantesWeb.USUARIO_ORIGEN)));
      state.setVariablesEE(variables);
      state.setDocumentacionGedoService(getDocumentacionGedoService());
      state.setTipoDocumentoService(getTipoDocumentoService());
      state.setTransactionFFCCService(getTransactionFFCCService());
      startScript = state.getStartScript();
      final boolean validationResult = state.isValid();

      if (state.getForwardDesicion() != null && !state.getForwardDesicion().isEmpty()) {
        if (validationResult) {
          final String siguienteEstado = (String) state
              .takeDecision(variables.get(ConstantesWeb.ESTADO_SELECCIONADO));
          variables.put(ConstantesWeb.ESTADO_SELECCIONADO, siguienteEstado);
        }
      }
    }
    // ------------------------
    cargarVariables(variables);

    final Map<String, String> variablesEnHistorialOperacion = this
        .setearVariablesEnHistorialOperacion(variables);
    expedienteElectronicoService.generarPaseExpedienteElectronico(workingTask, ee,
        variables.get(ConstantesWeb.USUARIO_ORIGEN), variables.get(ConstantesWeb.ESTADO_SELECCIONADO),
        variables.get(ConstantesWeb.MOTIVO), variables.get(ConstantesWeb.ACLARACION),
        variablesEnHistorialOperacion, numeroSadePase, generarDoc);

    try {
      if (variables.get(ConstantesWeb.ESTADO_SELECCIONADO) == null) {
        variables.put(ConstantesWeb.ESTADO_SELECCIONADO, ee.getEstado());
      }
      signalExecution(workingTask, variables.get(ConstantesWeb.ESTADO_SELECCIONADO), variables);
   
    } catch (final JbpmException e) {
      logger.error("enviarPase(Map<String,String>, ExpedienteElectronico, Task, String, Boolean)",
          e);

      throw new ParametroIncorrectoException("Debe seleccionar un estado de pase valido", null);
    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "enviarPase(Map<String,String>, ExpedienteElectronico, Task, String, Boolean) - end");
    }
  }
  // ---------------------------------------------------

  private void enviarPaseAdministrador(final Map<String, String> variables,
      final ExpedienteElectronicoDTO ee, final Task workingTask) throws ProcesoFallidoException,
      ParametroIncorrectoException, ExpedienteNoDisponibleException {
    if (logger.isDebugEnabled()) {
      logger.debug("enviarPaseAdministrador(variables={}, ee={}, workingTask={}) - start",
          variables, ee, workingTask);
    }

    cargarVariables(variables);

    final Map<String, String> variablesEnHistorialOperacion = this
        .setearVariablesEnHistorialOperacion(variables);
    expedienteElectronicoService.generarPaseExpedienteElectronicoAdministrador(workingTask, ee,
        variables.get(ConstantesWeb.USUARIO_ORIGEN), variables.get(ConstantesWeb.ESTADO_SELECCIONADO),
        variables.get(ConstantesWeb.MOTIVO), variablesEnHistorialOperacion);

    try {
      signalExecution(workingTask, variables.get(ConstantesWeb.ESTADO_SELECCIONADO), variables);
    } catch (final Exception e) {
      logger.error("enviarPaseAdministrador(Map<String,String>, ExpedienteElectronico, Task)", e);

      throw new ParametroIncorrectoException("Debe seleccionar un estado de pase válido.", null);
    }

    if (logger.isDebugEnabled()) {
      logger
          .debug("enviarPaseAdministrador(Map<String,String>, ExpedienteElectronico, Task) - end");
    }
  }

  protected String generarPase(final PaseExpedienteRequest datosPase, final String paseSinCambios,
      final boolean esServicioLOyS) throws ProcesoFallidoException, ParametroIncorrectoException,
      ExpedienteNoDisponibleException {
    if (logger.isDebugEnabled()) {
      logger.debug("generarPase(datosPase={}, paseSinCambios={}, esServicioLOyS={}) - start",
          datosPase, paseSinCambios, esServicioLOyS);
    }

    final String returnString = this.generarPase(datosPase, null, paseSinCambios, null,
        esServicioLOyS, true);
    if (logger.isDebugEnabled()) {
      logger.debug("generarPase(PaseExpedienteRequest, String, boolean) - end - return value={}",
          returnString);
    }
    return returnString;
  }

  private String generarPase(final PaseExpedienteRequest datosPase, final String aclaracion,
      final String tipoBloqueo, final String numeroSadePase, final boolean esServicioLOyS,
      final Boolean generarDoc) throws ProcesoFallidoException, ParametroIncorrectoException,
      ExpedienteNoDisponibleException {
    logger.info("Datos de PaseExpedienteRequest: " + datosPase + " \n Numero Sade Pase: "
        + numeroSadePase);

    final boolean isOperacionBloqueante = tipoBloqueo.equals(ConstantesWeb.PASE_BLOQUEO);

    this.sanearRequest(datosPase);
    try {

      // REVISARME EE-REFACTORME (Martes 15-Marzo-2014) grinberg.
      final ExpedienteElectronicoDTO expedienteElectronico = servicioAdministracionFactory
          .crearExpedienteElectronicoParaDerivacionDeOwner(datosPase.getCodigoEE(),
              datosPase.getUsuarioOrigen(), datosPase.getUsuarioDestino(),
              datosPase.getSistemaOrigen(), datosPase.getMotivoPase(),
              datosPase.getReparticionDestino(), datosPase.getSectorDestino(),
              datosPase.getEsMesaDestino(), datosPase.getEsSectorDestino(),
              datosPase.getEsReparticionDestino(), datosPase.getEsUsuarioDestino(),
              datosPase.getEstadoSeleccionado(), isOperacionBloqueante);

      String usuarioApoderado = null;
      final String usuario = datosPase.getUsuarioDestino();

      if (usuariosSADEService.licenciaActiva(datosPase.getUsuarioOrigen())) {
        usuarioApoderado = this.validarLicencia(datosPase.getUsuarioOrigen().toUpperCase());
      }

      if (usuarioApoderado != null) {
        throw new ParametroIncorrectoException(
            "El usuario seleccionado no puede generar el pase ya que está de licencia.", null);
      }

      if (!StringUtils.isBlank(datosPase.getUsuarioDestino())) {

        if (usuariosSADEService.licenciaActiva(datosPase.getUsuarioDestino())) {
          usuarioApoderado = this.validarLicencia(datosPase.getUsuarioDestino());
        }
        if (usuarioApoderado != null) {
          datosPase.setUsuarioDestino(usuarioApoderado);
        }
      }

      final Task workingTask = obtenerWorkingTask(expedienteElectronico);
      final Map<String, String> variables = this.cargarVariablesLocales(datosPase,
          expedienteElectronico, aclaracion);
      final String estadoSeleccionado = variables.get(ConstantesWeb.ESTADO_SELECCIONADO);

      if (estadoSeleccionado.equals(ConstantesWeb.ESTADO_GUARDA_TEMPORAL)) {
        variables.put(ConstantesWeb.DESTINATARIO, reparticionGT);
        archivar(variables, expedienteElectronico, workingTask);
        bloqueoExpedienteService.desbloquearExpediente(datosPase.getSistemaOrigen(),
            datosPase.getCodigoEE());
      } else {
        if (tipoBloqueo.equals(ConstantesWeb.PASE_SIN_CAMBIOS)) {
          variables.put(ConstantesWeb.EE_BLOQUEADO, "1");
        }

        enviarPase(variables, expedienteElectronico, workingTask, numeroSadePase, generarDoc);

        if (tipoBloqueo.equals(ConstantesWeb.PASE_BLOQUEO)) {
          bloqueoExpedienteService.bloquearExpediente(datosPase.getSistemaOrigen(),
              datosPase.getCodigoEE());
        } else if (tipoBloqueo.equals(ConstantesWeb.PASE_DESBLOQUEO)) {
          bloqueoExpedienteService.desbloquearExpediente(datosPase.getSistemaOrigen(),
              datosPase.getCodigoEE());
        }
      }

      hacerDefinitivosCambiosExpediente(expedienteElectronico);
      final String mensaje = ArmarMensaje(datosPase, usuarioApoderado, usuario, estadoSeleccionado,
          expedienteElectronico.getCodigoCaratula());

      if (logger.isDebugEnabled()) {
        logger.debug(
            "generarPase(PaseExpedienteRequest, String, String, String, boolean, Boolean) - end - return value={}",
            mensaje);
      }
      return mensaje;
    } catch (final JbpmException e) {
      logger.error("Error al procesar pase: " + datosPase.getCodigoEE(), e);
      throw new ProcesoFallidoException(" Error al procesar pase.", e);
    }
  }

  private String generarPaseAdministrador(final PaseExpedienteRequest datosPase,
      final String tipoBloqueo) throws ProcesoFallidoException, ParametroIncorrectoException,
      ExpedienteNoDisponibleException {
    logger.debug("Datos de PaseExpedienteRequest: " + datosPase);

    final boolean isOperacionBloqueante = tipoBloqueo.equals(ConstantesWeb.PASE_BLOQUEO);
    final String mensaje = "Se ha realizado el pase con éxito";
    this.sanearRequest(datosPase);

    try {

      @SuppressWarnings("static-access")
      final ExpedienteElectronicoDTO expedienteElectronico = servicioAdministracionFactory
          .crearExpedienteElectronicoAdministrador(datosPase.getCodigoEE(),
              datosPase.getUsuarioOrigen(), datosPase.getUsuarioDestino(),
              datosPase.getSistemaOrigen(), datosPase.getMotivoPase(),
              datosPase.getReparticionDestino(), datosPase.getSectorDestino(),
              datosPase.getEsMesaDestino(), datosPase.getEsSectorDestino(),
              datosPase.getEsReparticionDestino(), datosPase.getEsUsuarioDestino(),
              datosPase.getEstadoSeleccionado(), isOperacionBloqueante);

      final Task workingTask = obtenerWorkingTask(expedienteElectronico);
      final Map<String, String> variables = this.cargarVariablesLocales(datosPase,
          expedienteElectronico, null);
      final String estadoSeleccionado = variables.get(ConstantesWeb.ESTADO_SELECCIONADO);

      if (estadoSeleccionado.equals(ConstantesWeb.ESTADO_GUARDA_TEMPORAL)) {
        variables.put(ConstantesWeb.DESTINATARIO, reparticionGT);
        archivar(variables, expedienteElectronico, workingTask);
        bloqueoExpedienteService.desbloquearExpediente(datosPase.getSistemaOrigen(),
            datosPase.getCodigoEE());
      } else {
        if (tipoBloqueo.equals(ConstantesWeb.PASE_SIN_CAMBIOS)) {
          variables.put(ConstantesWeb.EE_BLOQUEADO, "1");
        }

        enviarPaseAdministrador(variables, expedienteElectronico, workingTask);

        if (tipoBloqueo.equals(ConstantesWeb.PASE_BLOQUEO)) {
          bloqueoExpedienteService.bloquearExpediente(datosPase.getSistemaOrigen(),
              datosPase.getCodigoEE());
        } else if (tipoBloqueo.equals(ConstantesWeb.PASE_DESBLOQUEO)) {
          bloqueoExpedienteService.desbloquearExpediente(datosPase.getSistemaOrigen(),
              datosPase.getCodigoEE());
        }
      }

      hacerDefinitivosCambiosExpediente(expedienteElectronico);

      if (logger.isDebugEnabled()) {
        logger.debug(
            "generarPaseAdministrador(PaseExpedienteRequest, String) - end - return value={}",
            mensaje);
      }
      return mensaje;
    } catch (final JbpmException e) {
      logger.error("Error al procesar pase: " + datosPase.getCodigoEE(), e);
      throw new ProcesoFallidoException(
          "No se ha podido recuperar el expediente sobre el cuál se desea realizar el pase.",
          null);
    }
  }

  @Override
  public synchronized String generarPaseEE(final PaseExpedienteRequest datosPase)
      throws ProcesoFallidoException, ParametroIncorrectoException,
      ExpedienteNoDisponibleException {
    if (logger.isDebugEnabled()) {
      logger.debug("generarPaseEE(datosPase={}) - start", datosPase);
    }

    final String returnString = this.generarPase(datosPase, ConstantesWeb.PASE_SIN_CAMBIOS, false);
    if (logger.isDebugEnabled()) {
      logger.debug("generarPaseEE(PaseExpedienteRequest) - end - return value={}", returnString);
    }
    return returnString;
  }

  @Override
  @SuppressWarnings("static-access")
  public synchronized void generarPaseEEAArchivo(final PaseExpedienteRequest datosPase)
      throws ProcesoFallidoException, ParametroIncorrectoException,
      ExpedienteNoDisponibleException {
    if (logger.isDebugEnabled()) {
      logger.debug("generarPaseEEAArchivo(datosPase={}) - start", datosPase);
    }

    this.sanearRequest(datosPase);

    final ExpedienteElectronicoDTO expediente = servicioAdministracionFactory
        .crearExpedienteParaEnvioAArchivo(datosPase.getCodigoEE(), datosPase.getUsuarioOrigen(),
            datosPase.getSistemaOrigen(), datosPase.getMotivoPase(),
            datosPase.getEstadoSeleccionado(), datosPase.getEsMesaDestino(),
            datosPase.getEsSectorDestino(), datosPase.getEsReparticionDestino(),
            datosPase.getEsUsuarioDestino());

    String usuarioApoderado = null;

    if (usuariosSADEService.licenciaActiva(datosPase.getUsuarioOrigen())) {
      usuarioApoderado = this.validarLicencia(datosPase.getUsuarioOrigen().toUpperCase());
    }

    if (usuarioApoderado != null) {
      throw new ParametroIncorrectoException(
          "El usuario seleccionado no puede generar el pase ya que está de licencia.", null);
    }

    final Map<String, String> map = cargarVariablesParaSistemasArchivadores(datosPase);

    expedienteElectronicoService.generarPaseAArchivo(expediente, map);
    hacerDefinitivosCambiosExpediente(expediente);

    if (logger.isDebugEnabled()) {
      logger.debug("generarPaseEEAArchivo(PaseExpedienteRequest) - end");
    }
  }

  // ---------------------------------

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public synchronized String generarPaseEEAdministrador(final PaseExpedienteRequest datosPase)
      throws ProcesoFallidoException, ParametroIncorrectoException,
      ExpedienteNoDisponibleException {
    if (logger.isDebugEnabled()) {
      logger.debug("generarPaseEEAdministrador(datosPase={}) - start", datosPase);
    }

    final String returnString = this.generarPaseAdministrador(datosPase,
        ConstantesWeb.PASE_SIN_CAMBIOS);
    if (logger.isDebugEnabled()) {
      logger.debug("generarPaseEEAdministrador(PaseExpedienteRequest) - end - return value={}",
          returnString);
    }
    return returnString;
  }

  @Override
  @SuppressWarnings("static-access")
  @Transactional
  public synchronized void generarPaseEEASolicitudArchivo(final PaseExpedienteRequest datosPase)
      throws ProcesoFallidoException, ParametroIncorrectoException,
      ExpedienteNoDisponibleException {
    if (logger.isDebugEnabled()) {
      logger.debug("generarPaseEEASolicitudArchivo(datosPase={}) - start", datosPase);
    }

    this.sanearRequest(datosPase);

    final ExpedienteElectronicoDTO expediente = servicioAdministracionFactory
        .crearExpedienteParaEnvioASolicitudArchivo(datosPase.getCodigoEE(),
            datosPase.getUsuarioOrigen(), datosPase.getSistemaOrigen(), datosPase.getMotivoPase(),
            datosPase.getEstadoSeleccionado(), datosPase.getEsMesaDestino(),
            datosPase.getEsSectorDestino(), datosPase.getEsReparticionDestino(),
            datosPase.getEsUsuarioDestino());

    String usuarioApoderado = null;

    if (usuariosSADEService.licenciaActiva(datosPase.getUsuarioOrigen())) {
      usuarioApoderado = this.validarLicencia(datosPase.getUsuarioOrigen().toUpperCase());
    }

    if (usuarioApoderado != null) {
      throw new ParametroIncorrectoException(
          "El usuario seleccionado no puede generar el pase ya que está de licencia.", null);
    }

    final Map<String, String> map = cargarVariablesParaSistemasArchivadores(datosPase);
    expedienteElectronicoService.generarPaseASolicitudArchivo(expediente, map);
    hacerDefinitivosCambiosExpediente(expediente);

    if (logger.isDebugEnabled()) {
      logger.debug("generarPaseEEASolicitudArchivo(PaseExpedienteRequest) - end");
    }
  }

  @Override
  public synchronized String generarPaseEEConBloqueo(final PaseExpedienteRequest datosPase)
      throws ProcesoFallidoException, ParametroIncorrectoException,
      ExpedienteNoDisponibleException {
    if (logger.isDebugEnabled()) {
      logger.debug("generarPaseEEConBloqueo(datosPase={}) - start", datosPase);
    }

    final String returnString = this.generarPase(datosPase, ConstantesWeb.PASE_BLOQUEO, false);
    if (logger.isDebugEnabled()) {
      logger.debug("generarPaseEEConBloqueo(PaseExpedienteRequest) - end - return value={}",
          returnString);
    }
    return returnString;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public synchronized String generarPaseEEConBloqueoYAclaracion(
      final PaseExpedienteRequest datosPase, final String aclaracion)
      throws ProcesoFallidoException, ParametroIncorrectoException,
      ExpedienteNoDisponibleException {
    if (logger.isDebugEnabled()) {
      logger.debug("generarPaseEEConBloqueoYAclaracion(datosPase={}, aclaracion={}) - start",
          datosPase, aclaracion);
    }

    final String returnString = this.generarPase(datosPase, aclaracion, ConstantesWeb.PASE_BLOQUEO,
        null, false, true);
    if (logger.isDebugEnabled()) {
      logger.debug(
          "generarPaseEEConBloqueoYAclaracion(PaseExpedienteRequest, String) - end - return value={}",
          returnString);
    }
    return returnString;

  }

  @Override
  @Transactional(propagation = Propagation.NEVER)
  public synchronized String generarPaseEEConDesbloqueo(final PaseExpedienteRequest datosPase)
      throws ProcesoFallidoException, ParametroIncorrectoException,
      ExpedienteNoDisponibleException {
    if (logger.isDebugEnabled()) {
      logger.debug("generarPaseEEConDesbloqueo(datosPase={}) - start", datosPase);
    }

    final String returnString = this.generarPase(datosPase, ConstantesWeb.PASE_DESBLOQUEO, false);
    if (logger.isDebugEnabled()) {
      logger.debug("generarPaseEEConDesbloqueo(PaseExpedienteRequest) - end - return value={}",
          returnString);
    }
    return returnString;
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public synchronized String generarPaseEESinDocumento(final PaseExpedienteRequest datosPase,
      final String numeroSadePase) throws ProcesoFallidoException, ParametroIncorrectoException,
      ExpedienteNoDisponibleException {
    if (logger.isDebugEnabled()) {
      logger.debug("generarPaseEESinDocumento(datosPase={}, numeroSadePase={}) - start", datosPase,
          numeroSadePase);
    }

    final String returnString = this.generarPase(datosPase, null, ConstantesWeb.PASE_SIN_CAMBIOS,
        numeroSadePase, false, false);
    if (logger.isDebugEnabled()) {
      logger.debug(
          "generarPaseEESinDocumento(PaseExpedienteRequest, String) - end - return value={}",
          returnString);
    }
    return returnString;
  }

  public ApplicationContext getCtx() {
    return this.ctx;
  }

  /**
   * @return the documentacionGedoService
   */
  public DocumentoGedoService getDocumentacionGedoService() {
    if (logger.isDebugEnabled()) {
      logger.debug("getDocumentacionGedoService() - start");
    }

    if (documentacionGedoService == null) {
      documentacionGedoService = getCtx().getBean(DocumentoGedoServiceImpl.class);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("getDocumentacionGedoService() - end - return value={}",
          documentacionGedoService);
    }
    return documentacionGedoService;
  }

  public PluginManager getPluginManager() {
    if (logger.isDebugEnabled()) {
      logger.debug("getPluginManager() - start");
    }

    if (pm == null) {
      pm = getCtx().getBean(PluginManager.class);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("getPluginManager() - end - return value={}", pm);
    }
    return pm;
  }

  public String getReparticionGT() {
    return reparticionGT;
  }

  /**
   * @return the tipoDocumentoService
   */
  public TipoDocumentoService getTipoDocumentoService() {
    if (logger.isDebugEnabled()) {
      logger.debug("getTipoDocumentoService() - start");
    }

    if (tipoDocumentoService == null) {
      tipoDocumentoService = getCtx().getBean(TipoDocumentoService.class);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("getTipoDocumentoService() - end - return value={}", tipoDocumentoService);
    }
    return tipoDocumentoService;
  }

  /**
   * @return the transactionFFCCService
   */
  public ExternalTransaccionService getTransactionFFCCService() {
    if (logger.isDebugEnabled()) {
      logger.debug("getTransactionFFCCService() - start");
    }

    if (transactionFFCCService == null) {
      transactionFFCCService = (ExternalTransaccionService) getCtx().getBean("transaccionService");
    }

    if (logger.isDebugEnabled()) {
      logger.debug("getTransactionFFCCService() - end - return value={}", transactionFFCCService);
    }
    return transactionFFCCService;
  }

  /**
   * Método utilitario para salvar los problemas más comunes de las variables
   * del request, tal como espacios al final del código de repartición, etc
   */
  private void sanearRequest(final PaseExpedienteRequest datosPase) {
    if (logger.isDebugEnabled()) {
      logger.debug("sanearRequest(datosPase={}) - start", datosPase);
    }

    // TODO Buscar mecanismo más elegante para hacer este saneo,
    // TODO Asegurarse el resto del código propio de los servicios externos
    // no vuelva a realizar chequeos innecesarios.
    datosPase.setCodigoEE(StringUtils.strip(datosPase.getCodigoEE()));
    datosPase.setReparticionDestino(StringUtils.strip(datosPase.getReparticionDestino()));
    datosPase.setSectorDestino(StringUtils.strip(datosPase.getSectorDestino()));
    if (datosPase.getUsuarioDestino() != null) {
      datosPase.setUsuarioDestino(StringUtils.strip(datosPase.getUsuarioDestino().toUpperCase()));
    }
    if (!StringUtils.isBlank(datosPase.getSectorDestino())) {
      datosPase.setSectorDestino(datosPase.getSectorDestino().toUpperCase());
    }
    if (!StringUtils.isBlank(datosPase.getReparticionDestino())) {
      datosPase.setReparticionDestino(datosPase.getReparticionDestino().toUpperCase());
    }
    datosPase.setUsuarioOrigen(StringUtils.strip(datosPase.getUsuarioOrigen().toUpperCase()));
    datosPase.setSistemaOrigen(StringUtils.strip(datosPase.getSistemaOrigen()));

    if (logger.isDebugEnabled()) {
      logger.debug("sanearRequest(PaseExpedienteRequest) - end");
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.springframework.context.ApplicationContextAware#setApplicationContext
   * (org.springframework.context.ApplicationContext)
   */
  @Override
  public void setApplicationContext(final ApplicationContext applicationContext)
      throws BeansException {
    this.ctx = applicationContext;
  }

  /**
   * @param documentacionGedoService
   *          the documentacionGedoService to set
   */
  public void setDocumentacionGedoService(final DocumentoGedoService documentacionGedoService) {
    this.documentacionGedoService = documentacionGedoService;
  }

  /**
   *
   * @param variables{estadoAnterior,
   *          motivo, estado, tareaGrupal, usuarioSeleccionado,
   *          grupoSeleccionado
   * @return
   */
  private Map<String, String> setearVariablesEnHistorialOperacion(
      final Map<String, String> variables) {
    if (logger.isDebugEnabled()) {
      logger.debug("setearVariablesEnHistorialOperacion(variables={}) - start", variables);
    }

    final Map<String, String> variablesHistorialOperacion = new HashMap<String, String>();
    variablesHistorialOperacion.put(ConstantesWeb.ESTADO_ANTERIOR,
        variables.get(ConstantesWeb.ESTADO_ANTERIOR));
    variablesHistorialOperacion.put(ConstantesWeb.MOTIVO, variables.get(ConstantesWeb.MOTIVO));
    variablesHistorialOperacion.put(ConstantesWeb.ESTADO, variables.get(ConstantesWeb.ESTADO));

    if (StringUtils.isNotEmpty(variables.get(ConstantesWeb.TAREA_GRUPAL))) {
      variablesHistorialOperacion.put(ConstantesWeb.TAREA_GRUPAL,
          variables.get(ConstantesWeb.TAREA_GRUPAL));
    }

    if (StringUtils.isNotEmpty(variables.get(ConstantesWeb.USUARIO_SELECCIONADO))) {
      variablesHistorialOperacion.put(ConstantesWeb.USUARIO_SELECCIONADO,
          variables.get(ConstantesWeb.USUARIO_SELECCIONADO));
    }

    if (StringUtils.isNotEmpty(variables.get(ConstantesWeb.GRUPO_SELECCIONADO))) {
      variablesHistorialOperacion.put(ConstantesWeb.GRUPO_SELECCIONADO,
          variables.get(ConstantesWeb.GRUPO_SELECCIONADO));
    }

    variablesHistorialOperacion.put(ConstantesWeb.DESTINATARIO,
        variables.get(ConstantesWeb.DESTINATARIO));

    if (logger.isDebugEnabled()) {
      logger.debug(
          "setearVariablesEnHistorialOperacion(Map<String,String>) - end - return value={}",
          variablesHistorialOperacion);
    }
    return variablesHistorialOperacion;
  }

  public void setReparticionGT(final String reparticionGT) {
    this.reparticionGT = reparticionGT;
  }

  /**
   * @param tipoDocumentoService
   *          the tipoDocumentoService to set
   */
  public void setTipoDocumentoService(final TipoDocumentoService tipoDocumentoService) {
    this.tipoDocumentoService = tipoDocumentoService;
  }

  /**
   * @param transactionFFCCService
   *          the transactionFFCCService to set
   */
  public void setTransactionFFCCService(final ExternalTransaccionService transactionFFCCService) {
    this.transactionFFCCService = transactionFFCCService;
  }

  private void signalExecution(final Task workingTask, final String nameTransition)
      throws JbpmException {
    if (logger.isDebugEnabled()) {
      logger.debug("signalExecution(workingTask={}, nameTransition={}) - start", workingTask,
          nameTransition);
    }

    // Paso a la siguiente Tarea/Estado definida en el Workflow
    processEngine.getExecutionService().signalExecutionById(workingTask.getExecutionId(),
        nameTransition);

    if (logger.isDebugEnabled()) {
      logger.debug("signalExecution(Task, String) - end");
    }
  }

  /**
   * @param nameTransition
   *          : nombre de la transición que voy a usar para la proxima tarea
   */
  private void signalExecution(final Task workingTask, final String nameTransition,
      final Map<String, String> variables) throws JbpmException {
    if (logger.isDebugEnabled()) {
      logger.debug("signalExecution(workingTask={}, nameTransition={}, variables={}) - start",
          workingTask, nameTransition, variables);
    }

    final Map<String, Object> mapVariables = new HashMap<String, Object>();

    mapVariables.put(ConstantesWeb.ESTADO_ANTERIOR, variables.get(ConstantesWeb.ESTADO_ANTERIOR));

    mapVariables.put(ConstantesWeb.ESTADO_ANTERIOR_PARALELO,
        variables.get(ConstantesWeb.ESTADO_ANTERIOR_PARALELO));

    mapVariables.put(ConstantesWeb.TAREA_GRUPAL, variables.get(ConstantesWeb.TAREA_GRUPAL));

    mapVariables.put(ConstantesWeb.ID_EXPEDIENTE_ELECTRONICO,
        Integer.parseInt(variables.get(ConstantesWeb.ID_EXPEDIENTE_ELECTRONICO)));

    String sufijoBloqueo = variables.get(ConstantesWeb.EE_BLOQUEADO);

    if (sufijoBloqueo == null || sufijoBloqueo.equals("0")) {
      sufijoBloqueo = "";
    } else {
      sufijoBloqueo = ConstantesWeb.SUFIJO_BLOQUEADO;
    }

    if (StringUtils.isEmpty(variables.get(ConstantesWeb.GRUPO_SELECCIONADO))) {
      mapVariables.put(ConstantesWeb.GRUPO_SELECCIONADO, null);
    } else {
      mapVariables.put(ConstantesWeb.GRUPO_SELECCIONADO,
          variables.get(ConstantesWeb.GRUPO_SELECCIONADO) + sufijoBloqueo);
    }

    if (StringUtils.isEmpty(variables.get(ConstantesWeb.USUARIO_SELECCIONADO))) {
      mapVariables.put(ConstantesWeb.USUARIO_SELECCIONADO, null);
    } else {
      mapVariables.put(ConstantesWeb.USUARIO_SELECCIONADO,
          variables.get(ConstantesWeb.USUARIO_SELECCIONADO) + sufijoBloqueo);
    }

    // Paso a la siguiente Tarea/Estado definida en el Workflow
    processEngine.getExecutionService().signalExecutionById(workingTask.getExecutionId(),
        nameTransition, mapVariables);

    if (logger.isDebugEnabled()) {
      logger.debug("signalExecution(Task, String, Map<String,String>) - end");
    }
  }

  private String validarLicencia(final String username) {
    if (logger.isDebugEnabled()) {
      logger.debug("validarLicencia(username={}) - start", username);
    }

    String apoderado = this.usuariosSADEService.getDatosUsuario(username).getApoderado();
    while (apoderado != null) {
      final String usuario = this.usuariosSADEService.getDatosUsuario(apoderado).getApoderado();
      if (null != usuario) {
        apoderado = usuario;
      } else {
        if (logger.isDebugEnabled()) {
          logger.debug("validarLicencia(String) - end - return value={}", apoderado);
        }
        return apoderado;
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug("validarLicencia(String) - end - return value={null}");
    }
    return null;
  }

}
