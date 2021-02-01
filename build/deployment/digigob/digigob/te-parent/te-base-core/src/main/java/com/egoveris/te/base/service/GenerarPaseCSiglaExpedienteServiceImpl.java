package com.egoveris.te.base.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jbpm.api.JbpmException;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.egoveris.sharedorganismo.base.service.ReparticionServ;
import com.egoveris.sharedorganismo.base.service.SectorInternoServ;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.te.base.model.ArchivoDeTrabajoDTO;
import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.model.ExpedienteAsociadoEntDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.service.iface.IActividadExpedienteService;
import com.egoveris.te.base.service.iface.IBloqueoExpedienteService;
import com.egoveris.te.base.service.iface.IGenerarPaseEECSiglaOMigracion;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.model.exception.ExpedienteNoDisponibleException;
import com.egoveris.te.model.exception.ParametroIncorrectoException;
import com.egoveris.te.model.exception.ProcesoFallidoException;
import com.egoveris.te.model.model.PaseExpedienteRequest;

@Service
@Transactional
public class GenerarPaseCSiglaExpedienteServiceImpl extends ExternalServiceAbstract
    implements IGenerarPaseEECSiglaOMigracion {
  private static final String REPARTICION_SECTOR_ARCHIVADO = "Guarda Temporal";
  private final static Logger logger = LoggerFactory
      .getLogger(GenerarPaseCSiglaExpedienteServiceImpl.class);

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

  protected Task workingTask = null;

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public synchronized void generarPaseEESinDocumentoCambioSigla(PaseExpedienteRequest datosPase,
      String aclaracion, String tipoBloqueo, Boolean generarDoc) throws ProcesoFallidoException,
      ParametroIncorrectoException, ExpedienteNoDisponibleException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "generarPaseEESinDocumentoCambioSigla(datosPase={}, aclaracion={}, tipoBloqueo={}, generarDoc={}) - start",
          datosPase, aclaracion, tipoBloqueo, generarDoc);
    }

    datosPase = this.sanearRequest(datosPase);
    ExpedienteElectronicoDTO expedienteElectronico = expedienteElectronicoService
        .obtenerExpedienteElectronicoPorCodigo(datosPase.getCodigoEE());

    Task workingTask = obtenerWorkingTask(expedienteElectronico);

    Map<String, String> variables = this.cargarVariablesLocales(datosPase, expedienteElectronico,
        aclaracion);
    variables.put(ConstantesWeb.ESTADO_SELECCIONADO, expedienteElectronico.getEstado());  

    {
      if (tipoBloqueo.equals(ConstantesWeb.PASE_SIN_CAMBIOS)) {
        variables.put(ConstantesWeb.EE_BLOQUEADO, "1");
      }

      if (tipoBloqueo.equals(ConstantesWeb.PASE_BLOQUEO)) {
        bloqueoExpedienteService.bloquearExpediente(datosPase.getSistemaOrigen(),
            datosPase.getCodigoEE());
      }

      enviarPase(variables, expedienteElectronico, workingTask);

      if (tipoBloqueo.equals(ConstantesWeb.PASE_BLOQUEO)) {
        bloqueoExpedienteService.bloquearExpediente(datosPase.getSistemaOrigen(),
            datosPase.getCodigoEE());
      } else if (tipoBloqueo.equals(ConstantesWeb.PASE_DESBLOQUEO)) {
        bloqueoExpedienteService.desbloquearExpediente(datosPase.getSistemaOrigen(),
            datosPase.getCodigoEE());
      }
    }

    if (logger.isDebugEnabled()) {
      logger.debug(
          "generarPaseEESinDocumentoCambioSigla(PaseExpedienteRequest, String, String, Boolean) - end");
    }
  }

  private Map<String, String> setearVariablesEnHistorialOperacion(Map<String, String> variables) {
    if (logger.isDebugEnabled()) {
      logger.debug("setearVariablesEnHistorialOperacion(variables={}) - start", variables);
    }

    Map<String, String> variablesHistorialOperacion = new HashMap<>();
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

  private void enviarPase(Map<String, String> variables, ExpedienteElectronicoDTO ee,
      Task workingTask) throws ProcesoFallidoException, ParametroIncorrectoException,
      ExpedienteNoDisponibleException {
    if (logger.isDebugEnabled()) {
      logger.debug("enviarPase(variables={}, ee={}, workingTask={}) - start", variables, ee,
          workingTask);
    }

    cargarVariables(variables);

    Map<String, String> variablesEnHistorialOperacion = this
        .setearVariablesEnHistorialOperacion(variables);
    Usuario user = new Usuario();
    user.setUsername(variables.get(ConstantesWeb.LOGGED_USERNAME).toUpperCase());
    expedienteElectronicoService.generarPaseExpedienteElectronicoSinDocumentoMigracion(workingTask,
        ee, user, variables.get(ConstantesWeb.ESTADO_SELECCIONADO), variables.get(ConstantesWeb.MOTIVO),
        variablesEnHistorialOperacion);

    try {
      signalExecution(workingTask, variables.get(ConstantesWeb.ESTADO_SELECCIONADO), variables);
      ee.setEstado(variables.get(ConstantesWeb.ESTADO_SELECCIONADO));
      expedienteElectronicoService.grabarExpedienteElectronico(ee);

    } catch (JbpmException e) {
      logger.error("enviarPase(Map<String,String>, ExpedienteElectronico, Task)", e);

      throw new ParametroIncorrectoException("Debe seleccionar un estado de pase valido", e);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("enviarPase(Map<String,String>, ExpedienteElectronico, Task) - end");
    }
  }

  private void signalExecution(Task workingTask, String nameTransition,
      Map<String, String> variables) throws JbpmException {
    if (logger.isDebugEnabled()) {
      logger.debug("signalExecution(workingTask={}, nameTransition={}, variables={}) - start",
          workingTask, nameTransition, variables);
    }

    Map<String, Object> mapVariables = new HashMap<>();

    mapVariables.put(ConstantesWeb.ESTADO_ANTERIOR, variables.get(ConstantesWeb.ESTADO_ANTERIOR));

    mapVariables.put(ConstantesWeb.ESTADO_ANTERIOR_PARALELO,
        variables.get(ConstantesWeb.ESTADO_ANTERIOR_PARALELO));

    mapVariables.put(ConstantesWeb.TAREA_GRUPAL, variables.get(ConstantesWeb.TAREA_GRUPAL));

    mapVariables.put(ConstantesWeb.ID_EXPEDIENTE_ELECTRONICO,
        Integer.parseInt(variables.get(ConstantesWeb.ID_EXPEDIENTE_ELECTRONICO)));

    String sufijoBloqueo = variables.get(ConstantesWeb.EE_BLOQUEADO);

    if ((sufijoBloqueo == null) || sufijoBloqueo.equals("0")) {
      sufijoBloqueo = "";
    } else {
      sufijoBloqueo = ConstantesWeb.SUFIJO_BLOQUEADO;
    }

    if (StringUtils.isEmpty((String) variables.get(ConstantesWeb.GRUPO_SELECCIONADO))) {
      mapVariables.put(ConstantesWeb.GRUPO_SELECCIONADO, null);
    } else {
      mapVariables.put(ConstantesWeb.GRUPO_SELECCIONADO,
          variables.get(ConstantesWeb.GRUPO_SELECCIONADO) + sufijoBloqueo);
    }

    if (StringUtils.isEmpty((String) variables.get(ConstantesWeb.USUARIO_SELECCIONADO))) {
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

  private Map<String, String> cargarVariablesLocales(PaseExpedienteRequest datosPase,
      ExpedienteElectronicoDTO expedienteElectronico, String aclaracion)
      throws ProcesoFallidoException {
    if (logger.isDebugEnabled()) {
      logger.debug(
          "cargarVariablesLocales(datosPase={}, expedienteElectronico={}, aclaracion={}) - start",
          datosPase, expedienteElectronico, aclaracion);
    }

    String estadoSeleccionado = StringUtils.EMPTY;
    Map<String, String> variablesLocales = new HashMap<>();

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
    } catch (Exception e) {
      logger.error("cargarVariablesLocales(PaseExpedienteRequest, ExpedienteElectronico, String)",
          e);

      throw new ProcesoFallidoException(
          "No se ha podido recuperar el expediente sobre el cuál se desea realizar el pase.", e);
    }
  }

  private void cargarVariables(Map<String, String> variables) throws ProcesoFallidoException {
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

  public void hacerDefinitivosCambiosExpediente(ExpedienteElectronicoDTO expediente)
      throws ParametroIncorrectoException {
    if (logger.isDebugEnabled()) {
      logger.debug("hacerDefinitivosCambiosExpediente(expediente={}) - start", expediente);
    }

    if (expediente != null) {
      hacerDefinitivosDocumentosOficiales(expediente);
      hacerDefinitivosDocumentosDeTrabajo(expediente);
      hacerDefinitivosExpedientesAsociados(expediente);
      expedienteElectronicoService.modificarExpedienteElectronico(expediente);
    } else {
      throw new ParametroIncorrectoException("El expediente es nulo.", null);
    }

    if (logger.isDebugEnabled()) {
      logger.debug("hacerDefinitivosCambiosExpediente(ExpedienteElectronico) - end");
    }
  }

  private void hacerDefinitivosDocumentosOficiales(ExpedienteElectronicoDTO expediente) {
    if (logger.isDebugEnabled()) {
      logger.debug("hacerDefinitivosDocumentosOficiales(expediente={}) - start", expediente);
    }

    List<DocumentoDTO> listaDefinitivos = new ArrayList<>();

    for (DocumentoDTO documentoOficial : expediente.getDocumentos()) {
      if (documentoOficial != null) {
        documentoOficial.setDefinitivo(true);
        listaDefinitivos.add(documentoOficial);
      }
    }
    expediente.setDocumentos(listaDefinitivos);

    if (logger.isDebugEnabled()) {
      logger.debug("hacerDefinitivosDocumentosOficiales(ExpedienteElectronico) - end");
    }
  }

  private void hacerDefinitivosDocumentosDeTrabajo(ExpedienteElectronicoDTO expediente) {
    if (logger.isDebugEnabled()) {
      logger.debug("hacerDefinitivosDocumentosDeTrabajo(expediente={}) - start", expediente);
    }

    List<ArchivoDeTrabajoDTO> listaDefinitivos = new ArrayList<>();
    for (ArchivoDeTrabajoDTO documentoDeTrabajo : expediente.getArchivosDeTrabajo()) {
      if (documentoDeTrabajo != null) {
        documentoDeTrabajo.setDefinitivo(true);
        listaDefinitivos.add(documentoDeTrabajo);
      }
    }
    expediente.setArchivosDeTrabajo(listaDefinitivos);

    if (logger.isDebugEnabled()) {
      logger.debug("hacerDefinitivosDocumentosDeTrabajo(ExpedienteElectronico) - end");
    }
  }

  private void hacerDefinitivosExpedientesAsociados(ExpedienteElectronicoDTO expediente) {
    if (logger.isDebugEnabled()) {
      logger.debug("hacerDefinitivosExpedientesAsociados(expediente={}) - start", expediente);
    }

    List<ExpedienteAsociadoEntDTO> listaDefinitivos = new ArrayList<>();
    for (ExpedienteAsociadoEntDTO expedienteAsociado : expediente.getListaExpedientesAsociados()) {
      if (expedienteAsociado != null) {
        expedienteAsociado.setDefinitivo(true);
        listaDefinitivos.add(expedienteAsociado);
      }
    }
    expediente.setListaExpedientesAsociados(listaDefinitivos);

    if (logger.isDebugEnabled()) {
      logger.debug("hacerDefinitivosExpedientesAsociados(ExpedienteElectronico) - end");
    }
  }

  private PaseExpedienteRequest sanearRequest(PaseExpedienteRequest datosPase) {
    if (logger.isDebugEnabled()) {
      logger.debug("sanearRequest(datosPase={}) - start", datosPase);
    }

    datosPase.setCodigoEE(StringUtils.strip(datosPase.getCodigoEE()));
    datosPase.setReparticionDestino(StringUtils.strip(datosPase.getReparticionDestino()));
    datosPase.setSectorDestino(StringUtils.strip(datosPase.getSectorDestino()));
    if (datosPase.getUsuarioDestino() != null) {
      datosPase.setUsuarioDestino(StringUtils.strip(datosPase.getUsuarioDestino().toUpperCase()));
    }
    datosPase.setUsuarioOrigen(StringUtils.strip(datosPase.getUsuarioOrigen().toUpperCase()));
    datosPase.setSistemaOrigen(StringUtils.strip(datosPase.getSistemaOrigen()));

    if (logger.isDebugEnabled()) {
      logger.debug("sanearRequest(PaseExpedienteRequest) - end - return value={}", datosPase);
    }
    return datosPase;
  }

  public void setProcessEngine(ProcessEngine processEngine) {
    this.processEngine = processEngine;
  }

  public ProcessEngine getProcessEngine() {
    return processEngine;
  }

  public void setSectorInternoServ(SectorInternoServ sectorInternoServ) {
    this.sectorInternoServ = sectorInternoServ;
  }

  public SectorInternoServ getSectorInternoServ() {
    return sectorInternoServ;
  }

  public void setReparticionServ(ReparticionServ reparticionServ) {
    this.reparticionServ = reparticionServ;
  }

  public ReparticionServ getReparticionServ() {
    return reparticionServ;
  }

  public void setUsuariosSADEService(UsuariosSADEService usuariosSADEService) {
    this.usuariosSADEService = usuariosSADEService;
  }

  public UsuariosSADEService getUsuariosSADEService() {
    return usuariosSADEService;
  }

  public void setExpedienteElectronicoService(
      ExpedienteElectronicoService expedienteElectronicoService) {
    this.expedienteElectronicoService = expedienteElectronicoService;
  }

  public ExpedienteElectronicoService getExpedienteElectronicoService() {
    return expedienteElectronicoService;
  }

  public void setBloqueoExpedienteService(IBloqueoExpedienteService bloqueoExpedienteService) {
    this.bloqueoExpedienteService = bloqueoExpedienteService;
  }

  public IBloqueoExpedienteService getBloqueoExpedienteService() {
    return bloqueoExpedienteService;
  }

  public void setValidacionUsuario(ValidaUsuarioExpedientesService validacionUsuario) {
    this.validacionUsuario = validacionUsuario;
  }

  public ValidaUsuarioExpedientesService getValidacionUsuario() {
    return validacionUsuario;
  }

}
