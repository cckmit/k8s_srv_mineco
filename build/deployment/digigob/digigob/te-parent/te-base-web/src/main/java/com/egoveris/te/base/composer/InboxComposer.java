package com.egoveris.te.base.composer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jbpm.api.ExecutionService;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.TaskQuery;
import org.jbpm.api.task.Participation;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.identity.spi.IdentitySession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.CollectionUtils;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;
import org.zkoss.zul.ext.Paginal;

import com.egoveris.ffdd.model.exception.NotFoundException;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.sharedsecurity.conf.service.SecurityUtil;
import com.egoveris.te.base.exception.NegocioException;
import com.egoveris.te.base.helper.ValidacionSistemasExternosHelper;
import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.model.ExpedienteAsociadoEntDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.HistorialOperacionDTO;
import com.egoveris.te.base.model.IngresoSolicitudExpedienteDTO;
import com.egoveris.te.base.model.Tarea;
import com.egoveris.te.base.model.TareaParaleloDTO;
import com.egoveris.te.base.model.TipoDocumentoDTO;
import com.egoveris.te.base.model.TrataDTO;
import com.egoveris.te.base.service.FusionService;
import com.egoveris.te.base.service.HistorialOperacionService;
import com.egoveris.te.base.service.TareaParaleloService;
import com.egoveris.te.base.service.TipoDocumentoService;
import com.egoveris.te.base.service.TramitacionConjuntaService;
import com.egoveris.te.base.service.TrataService;
import com.egoveris.te.base.service.UsuariosSADEService;
import com.egoveris.te.base.service.expediente.ExpedienteAsociadoService;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.service.iface.IActividadExpedienteService;
import com.egoveris.te.base.service.iface.ITaskViewService;
import com.egoveris.te.base.util.ConfiguracionInicialModuloEEFactory;
import com.egoveris.te.base.util.ConstantesServicios;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.ExternalPagingSorting;
import com.egoveris.te.base.util.IrASistemaOrigenLinkHandler;
import com.egoveris.te.base.util.Task2TareaTransformer;
import com.egoveris.te.base.util.Utils;
import com.egoveris.te.model.exception.ParametroIncorrectoException;


@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class InboxComposer extends InboxGeneralComposer {
  /**
   *
   */
  private static final long serialVersionUID = -1421481592013737596L;
  private static final String TRAMITACION_EN_PARALELO = "Paralelo";
  private static String PENDIENTE_INICIACION = "Pendiente Iniciacion";
  private static String INICIACION = "Iniciacion";

  @WireVariable(ConstantesServicios.FUSION_SERVICE)
  private FusionService fusionService;
  private Window inboxWindow;
  private Window generarSolicitudWindow;
  private Window caratularInternoWindow;
  private Window caratularExternoWindow;
  private Listheader fechaModificacion;
  private Toolbarbutton caratularExterno;
  private Toolbarbutton caratularInterno;
  private Listbox solicitudesLb;
  @WireVariable(ConstantesServicios.PROCESS_ENGINE_SERVICE)
  private ProcessEngine processEngine;
  private ExecutionService executionService;
  private List<Tarea> tasks = null;
  private Tarea selectedTask = null;
  private TrataDTO trata;
  private Window taskView;
  private Listbox tasksList;
  private AnnotateDataBinder binder;
  @WireVariable(ConstantesServicios.TRATA_SERVICE)
  private TrataService trataService;
  @WireVariable(ConstantesServicios.TASK_VIEW_SERVICE)
  private ITaskViewService taskViewService;
  @WireVariable(ConstantesServicios.TAREA_PARALELO_SERVICE)
  private TareaParaleloService tareaParaleloService;

  private String username;
  private Paginal taskPaginator;
  private ExternalPagingSorting extPagSort = null;
  private ExternalPagingSorting extTaskPagSort = null;
  private IdentitySession identityTask;
  @WireVariable(ConstantesServicios.EXP_ELECTRONICO_SERVICE)
  private ExpedienteElectronicoService expedienteElectronicoService;
  @WireVariable(ConstantesServicios.TIPO_DOCUMENTO_SERVICE)
  private TipoDocumentoService tipoDocumentoService;
  private Window formularioControladoWindows;

  @WireVariable(ConstantesServicios.USUARIOS_SADE_SERVICE)
  private UsuariosSADEService usuariosSADEService;
  @WireVariable(ConstantesServicios.EXP_ASOCIADO_SERVICE)
  private ExpedienteAsociadoService expedienteAsociadoService;
  @WireVariable(ConstantesServicios.HISTORIAL_OPERACION_SERVICE)
  private HistorialOperacionService historialService;

  @Autowired
  private ConfiguracionInicialModuloEEFactory configuracionInicialModuloEEFactory;
  
  private final static Logger logger = LoggerFactory.getLogger(InboxComposer.class);

  public void doAfterCompose(Component c) throws Exception {
    super.doAfterCompose(c);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    this.binder = new AnnotateDataBinder(c);

    formularioControladoWindows.addEventListener(Events.ON_NOTIFY,
        new InboxOnNotifyWindowListener(this));
    c.addEventListener(Events.ON_USER, new InboxOnNotifyWindowListener(this));
    c.addEventListener(Events.ON_NOTIFY, new InboxOnNotifyWindowListener(this));

    username = Executions.getCurrent().getSession().getAttribute(ConstantesWeb.SESSION_USERNAME)
        .toString();

    Boolean sadeInterno = (Boolean) SecurityUtil.isAllGranted(ConstantesWeb.ROL_CARAT_SADE_INTERNO);
    Boolean sadeExterno = (Boolean) SecurityUtil.isAllGranted(ConstantesWeb.ROL_CARAT_SADE_EXTERNO);

    caratularInterno.setVisible(sadeInterno);
    caratularExterno.setVisible(sadeExterno);
    executionService = processEngine.getExecutionService();
    initTaskPagSort();
    populateTasks();
    obtainTaskAndRedirect();
    fechaModificacion.sort(true, true);
  }

  public ConfiguracionInicialModuloEEFactory getConfiguracionInicialModuloEEFactory() {
    return this.configuracionInicialModuloEEFactory;
  }

  public void setConfiguracionInicialModuloEEFactory(
      ConfiguracionInicialModuloEEFactory configuracionInicialModuloEEFactory) {
    this.configuracionInicialModuloEEFactory = configuracionInicialModuloEEFactory;
  }

  /**
   * Inicializa el helper de paginacion-ordenamiento de Tasks. Adiciona al
   * ListBox un listener para escuhar eventos enviados desde el helper.
   *
   */
  public void initTaskPagSort() {
    extTaskPagSort = new ExternalPagingSorting(tasksList, taskPaginator);
    extTaskPagSort.setObjetoModelo(this.taskViewService);
    extTaskPagSort.setNombreMetodo("buscarTareasPorUsuario");

    Map<String, Object> parametrosConsulta = new HashMap<String, Object>();
    parametrosConsulta.put("usuario", username);

    /*
     * JIRAs: BISADE-5005: https://quark.everis.com/jira/browse/BISADE-5005,
     * BISADE-2515: https://quark.everis.com/jira/browse/BISADE-2515 - Se quita
     * la funcionalidad de cacheQueries - Se transforman las Task en
     * TareaRowMapper
     */
    if (Executions.getCurrent().getDesktop().getAttribute("cacheQueries") != null) {
      Executions.getCurrent().getDesktop().setAttribute("cacheQueries", "true");
      parametrosConsulta.put("cacheQueries",
          Executions.getCurrent().getDesktop().getAttribute("cacheQueries"));
    }
    extTaskPagSort.setParametrosConsulta(parametrosConsulta);

    Boolean flag = false;
    if (tasks != null && tasks.size() > 0) {
      for (Tarea t : tasks) {
        if (t != null && t.getNombreTarea() != null
            && t.getNombreTarea().equals("Guarda Temporal")) {
          expedienteElectronicoService.actualizarWorkflowsEnGuardaTemporalCallaBleStatement();
          flag = true;

        }
      }
    }
    if (flag) {
      extTaskPagSort.setParametrosConsulta(parametrosConsulta);
    }

    tasksList.addEventListener(Events.ON_NOTIFY, new EventListener() {
      @SuppressWarnings("unchecked")
      public void onEvent(Event event) throws Exception {
        if (event.getName().equals(Events.ON_NOTIFY)) {

          tasks = (List<Tarea>) extTaskPagSort.getDatos();
          Boolean flag = false;
          for (Tarea t : tasks) {
            if (t != null && t.getNombreTarea() != null
                && t.getNombreTarea().equals("Guarda Temporal")) {
              expedienteElectronicoService.actualizarWorkflowsEnGuardaTemporalCallaBleStatement();
              flag = true;

            }

            if (flag) {
              tasks = (List<Tarea>) extTaskPagSort.getDatos();
            }
            binder.loadComponent(tasksList);
          }
        }
      }
    });
  }

  public void onGenerarNuevaSolicitud() {
    this.generarSolicitudWindow = (Window) Executions.createComponents(
        "/solicitud/nuevaSolicitud.zul", this.self, new HashMap<String, Object>());
    this.generarSolicitudWindow.setClosable(true);

    this.generarSolicitudWindow.doModal();
  }

  public void onCaratularInterno() {
    this.caratularInternoWindow = (Window) Executions.createComponents(
        "/solicitud/caratularInterno.zul", this.self, new HashMap<String, Object>());
    this.caratularInternoWindow.setClosable(true);

    this.caratularInternoWindow.doModal();
  }

  @SuppressWarnings("unchecked")
  public void onCreate$inboxWindow() throws InterruptedException {
    Map<String, Object> pathMap = (Map<String, Object>) Executions.getCurrent().getSession()
        .getAttribute("PathMap");

    if (!CollectionUtils.isEmpty(pathMap)) {
      String keyWord = (String) pathMap.get(ConstantesWeb.KEYWORD);

      if (StringUtils.isNotEmpty(keyWord) && keyWord.equals(ConstantesWeb.KEYWORD_TAREAS)) {
        if (((Boolean) pathMap.get(ConstantesWeb.REDIRECT) != null)
            && !(Boolean) pathMap.get(ConstantesWeb.REDIRECT)) {
          Executions.getCurrent().getSession().removeAttribute(ConstantesWeb.PATHMAP);

          String taskId = (String) pathMap.get("ID1");

          if ((taskId != null) && !taskId.trim().isEmpty()) {
            this.redireccionarATareaPorURL(taskId);
          }
        }
      }
    }
  }

  /**
   * Método que redirecciona a la tarea cuyo id llega como parametro por medio
   * de la url ejemplo: "/expedientes-web/u/tareas/1"
   * 
   * @author lfishkel
   * @param parameter
   * @throws InterruptedException
   */
  public void redireccionarATareaPorURL(String parameter) throws InterruptedException {
    boolean encontro = false;

    for (Tarea task : this.tasks) {
      if (task.getTask().getId().equals(parameter)) {
        encontro = true;
        this.selectedTask = task;

        break;
      }
    }

    if (encontro) {
      this.onExecuteTask();
    } else {
			Messagebox.show(Labels.getLabel("ee.consultaExpElecComp.msgbox.errorTarea"),
					Labels.getLabel("ee.general.information"), Messagebox.OK, Messagebox.EXCLAMATION);
    }
  }

  public void onCaratularExterno() throws InterruptedException {
    this.caratularExternoWindow = (Window) Executions.createComponents(
        "/solicitud/caratularExterno.zul", this.self, new HashMap<String, Object>());
    this.caratularExternoWindow.setClosable(true);

    this.caratularExternoWindow.doModal();
  }

  public void onIniciarOperacion() {
    this.caratularExternoWindow = (Window) Executions.createComponents(
        "/tipoOperaciones/iniciarOperacion.zul", this.self, new HashMap<String, Object>());
    this.caratularExternoWindow.setClosable(true);

    this.caratularExternoWindow.doModal();
  }

  public void onVerifyTipoDocumentoCaratulaDeEE() throws InterruptedException {

    // DG_DF-EE-Diseño_Funcional -Modificaciones_En_EE_Formularios_Controlados -
    // BEGIN - hgiudatt - 20140904
    String codigoExpedienteElectronico = "";
    String estadoExpedienteElectronico = "";
    TipoDocumentoDTO tipoDocumentoCaratulaDeEE = null;
    ExpedienteElectronicoDTO expedienteElectronico;
    IngresoSolicitudExpedienteDTO ingresoSolicitudExpediente;
    boolean trataDocAsoc = Boolean.FALSE;

    codigoExpedienteElectronico = (String) executionService
        .getVariable(this.selectedTask.getTask().getExecutionId(), "codigoExpediente");

    if ("" != this.selectedTask.getCodigoTrata()) {
      this.setTrata(this.trataService.buscarTrataByCodigo(this.selectedTask.getCodigoTrata()));

      if (this.getTrata().getAcronimoDocumento() != null) {
	      tipoDocumentoCaratulaDeEE = this.tipoDocumentoService
	          .obtenerTipoDocumento(this.getTrata().getAcronimoDocumento());
      }
    }

    if (null != tipoDocumentoCaratulaDeEE) {

      try {
        expedienteElectronico = this.expedienteElectronicoService
            .obtenerExpedienteElectronicoPorCodigo(codigoExpedienteElectronico);
        estadoExpedienteElectronico = expedienteElectronico.getEstado();

        ingresoSolicitudExpediente = new IngresoSolicitudExpedienteDTO();
        ingresoSolicitudExpediente.setExpedienteElectronico(expedienteElectronico);
        ingresoSolicitudExpediente
            .setSolicitudExpediente(expedienteElectronico.getSolicitudIniciadora());

        Boolean tieneCaratulaVariable = false;
        for (DocumentoDTO d : expedienteElectronico.getDocumentos()) {
          if (d.getIdTransaccionFC() != null) {
            tieneCaratulaVariable = true;
          }

        }
        int cantDocs = expedienteElectronico.getDocumentos().size();
        if (cantDocs < 2) {
          estadoExpedienteElectronico = "Pendiente Iniciacion";
          tieneCaratulaVariable = false;
        }

        if ((estadoExpedienteElectronico.equals(PENDIENTE_INICIACION)
            || (expedienteElectronico.getTrata().getAcronimoDocumento() != null
                && !tieneCaratulaVariable && cantDocs < 2))
            || (estadoExpedienteElectronico.equals(INICIACION)
                && expedienteElectronico.getDocumentos().size() == 4
                && fueRehabilitado(expedienteElectronico.getId())
                && expedienteElectronico.getTrata().getAcronimoDocumento() != null
                && !tieneCaratulaVariable)) {
          try {
            crearFormularioControlado(ingresoSolicitudExpediente);
          } catch (NotFoundException e) {
            logger.error("Se produjo un error creando formulario controlado de expediente: "
                + codigoExpedienteElectronico + " - " + e.getMessage());
          }
        } else {
          onExecuteTask();
        }

      } catch (ParametroIncorrectoException e) {
        logger
            .error("Se produjo un error al intentar obtener el expediente electronico con codigo: "
                + codigoExpedienteElectronico + " - " + e.getMessage());
      }

    } else {
      try {
        onExecuteTask();
      } catch (InterruptedException e) {
        logger.debug(e.getMessage());
      }
    }
  }

  private boolean fueRehabilitado(Long idExpedienteElectronico) {
    List<HistorialOperacionDTO> hist = historialService
        .buscarHistorialporExpediente(idExpedienteElectronico);
    if (hist != null) {
      for (HistorialOperacionDTO h : hist) {
        if (h.getEstado().equalsIgnoreCase("Guarda Temporal")) {
          return true;
        }
      }
    }
    return false;
  }

  public void onExecuteTask() throws InterruptedException {
    if (this.selectedTask != null) {
      if (this.selectedTask.getTask().getFormResourceName() == null) {
        Messagebox.show(Labels.getLabel("ee.inbox.error.noview"),
            Labels.getLabel("ee.errorMessageBox.title"), Messagebox.OK, Messagebox.ERROR);

        return;
      }
    } else {
      Messagebox.show(Labels.getLabel("ee.inbox.error.grave"),
          Labels.getLabel("ee.errorMessageBox.title"), Messagebox.OK, Messagebox.ERROR);

      return;
    }

    if (this.taskView != null) {
      this.taskView.detach();

      try {
        String codigoExpedienteElectronico = (String) executionService
            .getVariable(this.selectedTask.getTask().getExecutionId(), "codigoExpediente");

        // Tengo que validar si el expediente seleccionado --> SERVICIO
        TramitacionConjuntaService tramitacionConjuntaService = (TramitacionConjuntaService) SpringUtil
            .getBean(ConstantesServicios.TRAMITACION_CONJUNTA_SERVICE);

        if (validarFusionOTramitacion(fusionService, tramitacionConjuntaService,
            codigoExpedienteElectronico, false)) {
          return;
        }

        Map<String, Object> variables = new HashMap<>();
        variables.put("selectedTask", this.selectedTask.getTask());

        TrataDTO trata = this.trataService.buscarTrataByCodigo(this.selectedTask.getCodigoTrata());
        this.inboxWindow.getDesktop().setAttribute("selectedTask", this.selectedTask.getTask());
        variables.put("Trata", trata);

        String zulPath = this.selectedTask.getTask().getFormResourceName();

        if (zulPath == null || zulPath.isEmpty() || codigoExpedienteElectronico == null
            || codigoExpedienteElectronico.isEmpty()) {
          if (!this.selectedTask.getTask().getActivityName().toLowerCase().contains("anular")) {
            zulPath = "/expediente/nuevoExpediente.zul";
          }
        }

        this.taskView = (Window) Executions.createComponents(Utils.formatZulPath(zulPath),
            this.self, variables);
        this.taskView.setParent(inboxWindow);
        this.taskView.setPosition("center,top");
        this.taskView.setVisible(true);
        this.taskView.setClosable(true);
        this.taskView.doModal();
        Executions.getCurrent().getDesktop().setAttribute("codigoExpedienteElectronico",
            codigoExpedienteElectronico);

        // Información de trabajos realizados pendientes en una
        // tramitación paralela....

        // Por el momento esta es una solución alternativa de la incidencia
        // #4045, ya que se necesita entregar una versión de compromiso

        // Fin Información de trabajos realizados pendientes en una
        // tramitación paralela....
      } catch (SuspendNotAllowedException e) {
        logger.error(e.getMessage());
      }
    } else {
      Messagebox.show(Labels.getLabel("ee.consultaExpedienteComposer.msgbox.noPosibleInicializarVista"),
    		  Labels.getLabel("ee.consultaExpedienteComposer.msgbox.errorComunicacion"), Messagebox.OK, Messagebox.ERROR);
    }
  }

  public boolean validarFusionOTramitacion(FusionService fusionService,
      TramitacionConjuntaService tcService, String ee, boolean labelErrorSistemaBAC)
      throws InterruptedException {
    String errorFusion = null;
    String errorTC = null;
    if (!labelErrorSistemaBAC) {
      errorFusion = "ee.inbox.error.expedienteEnFusion";
      errorTC = "ee.inbox.error.expedienteEnTramitacionConjunta";
    } else {
      errorFusion = "ee.inbox.error.sistemaExterno.expedienteEnFusion";
      errorTC = "ee.inbox.error.sistemaExterno.expedienteEnTramitacionConjunta";
    }

    if ((ee != null) && tcService.esExpedienteEnProcesoDeTramitacionConjunta(ee)) {
      String codigoExpedienteElectronicoCabecera = tcService
          .obtenerExpedienteElectronicoCabecera(ee);
      Messagebox.show(
          Labels.getLabel(errorTC, new String[] { ee, codigoExpedienteElectronicoCabecera }),
          Labels.getLabel("ee.general.information"), Messagebox.OK, Messagebox.EXCLAMATION);
      return true;
    }

    if ((ee != null) && fusionService.esExpedienteEnProcesoDeFusion(ee)) {
      String codigoExpedienteElectronicoCabecera = tcService
          .obtenerExpedienteElectronicoCabecera(ee);
      Messagebox.show(
          Labels.getLabel(errorFusion, new String[] { ee, codigoExpedienteElectronicoCabecera }),
          Labels.getLabel("ee.general.information"), Messagebox.OK, Messagebox.EXCLAMATION);

      return true;
    }
    return false;

  }

  public void onEjecutarExpediente() throws InterruptedException {
    Events.echoEvent("onUser", this.self, "ejecutarExpediente");
  }

  public void ejecutarExpediente(IngresoSolicitudExpedienteDTO solicitud)
      throws NotFoundException {
    crearFormularioControlado(solicitud);
  }

  public void onDevolverTarea() throws InterruptedException {
    this.processEngine.getTaskService().assignTask(this.selectedTask.getTask().getId(), null);

    String codigoExpedienteElectronico = (String) executionService
        .getVariable(this.selectedTask.getTask().getExecutionId(), "codigoExpediente");

    List<Participation> participations = this.processEngine.getTaskService()
        .getTaskParticipations(this.selectedTask.getTask().getId());

    String grupoStr = null;

    for (Participation grupo : participations) {
      grupoStr = grupo.getGroupId();
    }

    if (this.selectedTask.getTask().getActivityName().equals(TRAMITACION_EN_PARALELO)) {
      TareaParaleloDTO tareaParalelo = this.tareaParaleloService
          .buscarTareaEnParaleloByIdTask(this.selectedTask.getTask().getExecutionId());
      tareaParalelo.setEstado("Pendiente");
      tareaParalelo.setUsuarioGrupoAnterior(username);
      tareaParalelo.setTareaGrupal(true);
      this.tareaParaleloService.modificar(tareaParalelo);

      grupoStr = tareaParalelo.getUsuarioGrupoDestino();
    }

    generarTareaHistorico(codigoExpedienteElectronico, "Devolución", username, grupoStr,
        this.selectedTask.getTask());
    this.refreshInbox();
  }

  public void refreshInbox() throws InterruptedException {
    this.populateTasks();
    this.binder.loadComponent(this.tasksList);
    this.binder.loadAll();
  }

  @SuppressWarnings("unchecked")
  public List<Tarea> populateTasks() throws InterruptedException {
    Map<String, Object> parametrosConsulta = new HashMap<String, Object>();
    parametrosConsulta.put("usuario", username);
    // ***********************************************************************
    // ** JIRA BISADE-2515: https://quark.everis.com/jira/browse/BISADE-2515
    // ** - Se crea el parámetro "cacheQueries" para la consulta de inbox no
    // cachee cambios de statemachine.
    // ***********************************************************************
    if (Executions.getCurrent().getDesktop().getAttribute("cacheQueries") != null) {
      parametrosConsulta.put("cacheQueries",
          Executions.getCurrent().getDesktop().getAttribute("cacheQueries"));
      Executions.getCurrent().getDesktop().setAttribute("cacheQueries", null);
      extTaskPagSort.setParametrosConsulta(parametrosConsulta);
    } else {
      parametrosConsulta.put("cacheQueries", new Boolean(true));
      extTaskPagSort.setParametrosConsulta(parametrosConsulta);
    }
    extTaskPagSort.setCriterio(TaskQuery.PROPERTY_CREATEDATE);
    extTaskPagSort.setOrden("descending");
    tasks = (List<Tarea>) extTaskPagSort.cargarDatos();

    return tasks;
  }

  public void onIrASistemaExterno() throws InterruptedException {
    String codigoEE = this.selectedTask.getCodigoExpediente();
    String mensaje = Labels.getLabel("ee.inbox.enviarSistemaExterno", new String[] { codigoEE });
    ExpedienteElectronicoDTO ee = expedienteElectronicoService
        .buscarExpedienteElectronicoByIdTask(this.selectedTask.getTask().getExecutionId());
    TramitacionConjuntaService tramitacionConjuntaService = (TramitacionConjuntaService) SpringUtil
        .getBean(ConstantesServicios.TRAMITACION_CONJUNTA_SERVICE);

    // EN CASO DE ANULAR EL REQUERIMIENTO DE EVOLUCION DE SISTEMA DE INTEGRACION
    // COMENTAR EL IF
    if (ee != null) {
      for (ExpedienteAsociadoEntDTO aso : ee.getListaExpedientesAsociados()) {
        if (aso.getEsExpedienteAsociadoTC() != null && aso.getEsExpedienteAsociadoTC()) {
          Messagebox.show(
        		  Labels.getLabel("ee.consultaExpedienteComposer.error.TC"),
              Labels.getLabel("ee.general.information"), Messagebox.OK, Messagebox.EXCLAMATION);
          return;
        }
      }
    }
    if (ee != null
        && (ee.getSistemaApoderado().equals(ConstantesWeb.SISTEMA_BAC)
            || ee.getSistemaCreador().equals(ConstantesWeb.SISTEMA_BAC))
        && validarFusionOTramitacion(fusionService, tramitacionConjuntaService, codigoEE, true)) {
      return;
    }

    if (this.selectedTask.getTask().getActivityName().equals(ConstantesWeb.ESTADO_PARALELO)) {
      Messagebox.show(
          Labels.getLabel("ee.inbox.envioSistemaExterno.paralelo", new String[] { codigoEE }),
          Labels.getLabel("ee.general.information"), Messagebox.OK, Messagebox.EXCLAMATION);
      return;
    }

    for (ExpedienteAsociadoEntDTO ec : ee.getListaExpedientesAsociados()) {
      if (ec.getEsExpedienteAsociadoFusion() != null && ec.getEsExpedienteAsociadoFusion()
          && !ec.getDefinitivo()) {
        Messagebox.show(
        		Labels.getLabel("ee.consultaExpedienteComposer.error.procesoFusion"),
            Labels.getLabel("ee.general.information"), Messagebox.OK, Messagebox.EXCLAMATION);
        return;

      } else if (ec.getEsExpedienteAsociadoTC() == null
          && ec.getEsExpedienteAsociadoFusion() == null && !ec.getDefinitivo()) {
        Messagebox.show(
        		 Labels.getLabel("ee.consultaExpedienteComposer.error.finalizarFusion"),
            Labels.getLabel("ee.general.information"), Messagebox.OK, Messagebox.EXCLAMATION);
        return;
      }
    }

    IActividadExpedienteService actividadExpedienteService = (IActividadExpedienteService) SpringUtil
        .getBean(ConstantesServicios.ACTIVIDAD_EXPEDIENTE_SERVICE);
    if (actividadExpedienteService.tieneActividades(ee)) {
      Messagebox.show(Labels.getLabel("te.base.composer.enviocomposer.generic.elexpediente") + ee.getCodigoCaratula() + 
    		  Labels.getLabel("te.base.composer.enviocomposer.generic.poseeActividades")
          + Labels.getLabel("te.base.composer.enviocomposer.generic.finalizar"));
      return;
    }

    String titulo = Labels.getLabel("ee.inbox.enviarSistemaExterno.tituloPopUp");

    Messagebox.show(mensaje, titulo, Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
        new org.zkoss.zk.ui.event.EventListener() {
          public void onEvent(Event evt) throws InterruptedException {
            switch (((Integer) evt.getData()).intValue()) {
            case Messagebox.YES:
              mostrarForegroundBloqueante();
              Events.echoEvent(Events.ON_USER, (Component) getInboxWindow(), "irASistemaExterno");
              break;
            case Messagebox.NO:
              break;
            }
          }
        });
  }

  private boolean tienePermisoIntegracionSistemasExternos(Usuario usuario) {
    for (GrantedAuthority auth : usuario.getAuthorities()) {

      if (ConstantesWeb.PERMISO_SIGA_MESA.equals(auth.getAuthority())) {
        return true;
      }
    }
    return false;
  }

  public void irASistemaExterno() throws InterruptedException {
    String mensaje = Labels.getLabel("ee.inbox.enviarSistemaExterno.mensajeEnvio");
    String titulo = Labels.getLabel("ee.inbox.enviarSistemaExterno.tituloPopUp");

    boolean envioExitoso = false;
    String info = Messagebox.INFORMATION;
    ExpedienteElectronicoDTO ee = expedienteElectronicoService
        .buscarExpedienteElectronicoByIdTask(this.selectedTask.getTask().getExecutionId());
    String usuarioActual = null;
    Task task = null;
    try {
      task = obtenerWorkingTask(ee);
      usuarioActual = task.getAssignee();

      if (usuarioActual != null) {
        ee.setBloqueado(true);
        ee.setSistemaApoderado(ConstantesWeb.SISTEMA_BAC);
        expedienteElectronicoService.modificarExpedienteElectronico(ee);
        processEngine.getTaskService().assignTask(task.getId(), usuarioActual + ".bloqueado");
      }

      IrASistemaOrigenLinkHandler irASistemaOrigenLink = IrASistemaOrigenLinkHandler.getInstance();
      irASistemaOrigenLink.setSelectedTask(this.selectedTask);
      envioExitoso = irASistemaOrigenLink.onClick(this.selectedTask);
    } catch (InterruptedException e) {

      if (usuarioActual != null) {
        ee.setBloqueado(false);
        ee.setSistemaApoderado(ConstantesWeb.SISTEMA_EE);
        expedienteElectronicoService.modificarExpedienteElectronico(ee);
        processEngine.getTaskService().assignTask(task.getId(), usuarioActual);
      }

      logger.error(e.getMessage());
      mensaje = e.getMessage();
      info = Messagebox.EXCLAMATION;
      Clients.clearBusy();
      Messagebox.show(mensaje, titulo, Messagebox.OK, info);
    } catch (NegocioException e) {

      if (usuarioActual != null) {
        ee.setBloqueado(false);
        ee.setSistemaApoderado(ConstantesWeb.SISTEMA_EE);
        expedienteElectronicoService.modificarExpedienteElectronico(ee);
        processEngine.getTaskService().assignTask(task.getId(), usuarioActual);
      }

      Clients.clearBusy();
      Messagebox.show(
          Labels.getLabel("ee.envio.sistemas.externos.afjg.question",
              new String[] { selectedTask.getCodigoExpediente(), e.getMessage(), "AFJG" }),
          Labels.getLabel("ee.envio.sistemas.externos.afjg.titulo"),
          Messagebox.YES | Messagebox.NO, Messagebox.EXCLAMATION, new EventListener() {
            public void onEvent(Event evt) throws InterruptedException {
              switch (((Integer) evt.getData()).intValue()) {
              case Messagebox.YES:

                mostrarForegroundBloqueante();
                Events.echoEvent(Events.ON_USER, (Component) getInboxWindow(),
                    "actualizarDocumentosYEnviar");
                break;
              case Messagebox.NO:
                break;
              }
            }
          });
    } catch (ParametroIncorrectoException e) {
      Messagebox.show(Labels.getLabel("te.base.composer.error.adquirir") + ee.getCodigoCaratula(),
          "Error", Messagebox.OK, Messagebox.ERROR);

    }

    envioExitosoASistemaExterno(mensaje, titulo, envioExitoso, info);

  }

  protected void actualizarDocumentosYEnviar() throws InterruptedException {
    String titulo = Labels.getLabel("ee.inbox.enviarSistemaExterno.tituloPopUp");
    try {

      ValidacionSistemasExternosHelper
          .actualizarDocumentosNoDefinitivos(selectedTask.getCodigoExpediente());
      IrASistemaOrigenLinkHandler.getInstance().envioExpedienteAFJG();
      Clients.clearBusy();
      String mensaje = Labels.getLabel("ee.inbox.enviarSistemaExterno.mensajeEnvio");
      envioExitosoASistemaExterno(mensaje, titulo, true, Messagebox.INFORMATION);
    } catch (InterruptedException e) {
      Clients.clearBusy();
      Messagebox.show(e.getMessage(), titulo, Messagebox.OK, Messagebox.EXCLAMATION);
    }

  }

  protected void envioExitosoASistemaExterno(String mensaje, String titulo, boolean envioExitoso,
      String info) throws InterruptedException {

    if (envioExitoso) {
      Clients.clearBusy();
      Messagebox.show(mensaje, titulo, Messagebox.OK, info,
          new org.zkoss.zk.ui.event.EventListener() {
            public void onEvent(Event evt) throws InterruptedException {
              refreshInbox();
            }
          });
    }
  }

  private void mostrarForegroundBloqueante() {
    Clients.showBusy(Labels.getLabel("ee.tramitacion.enviarExpedienteElectronico.value"));
  }

  public ExternalPagingSorting getExtPagSort() {
    return extPagSort;
  }

  public void setExtPagSort(ExternalPagingSorting extPagSort) {
    this.extPagSort = extPagSort;
  }

  public ExternalPagingSorting getExtTaskPagSort() {
    return extTaskPagSort;
  }

  public void setExtTaskPagSort(ExternalPagingSorting extTaskPagSort) {
    this.extTaskPagSort = extTaskPagSort;
  }

  public Paginal getTaskPaginator() {
    this.taskPaginator.setTotalSize(taskViewService.numeroTotalTareasPorUsuario(this.username));
    return taskPaginator;
  }

  public void setTaskPaginator(Paginal taskPaginator) {
    this.taskPaginator = taskPaginator;
  }

  public IdentitySession getIdentityTask() {
    return identityTask;
  }

  public void setIdentityTask(IdentitySession identityTask) {
    this.identityTask = identityTask;
  }

  public ITaskViewService getTaskViewService() {
    return taskViewService;
  }

  public void setTaskViewService(ITaskViewService taskViewService) {
    this.taskViewService = taskViewService;
  }

  public void onGenerarSolicitud() {
  }

  public Window getGenerarSolicitudWindow() {
    return generarSolicitudWindow;
  }

  public void setGenerarSolicitudWindow(Window generarSolicitudWindow) {
    this.generarSolicitudWindow = generarSolicitudWindow;
  }

  public Window getInboxWindow() {
    return inboxWindow;
  }

  public void setInboxWindow(Window inboxWindow) {
    this.inboxWindow = inboxWindow;
  }

  public Listbox getSolicitudesLb() {
    return solicitudesLb;
  }

  public void setSolicitudesLb(Listbox solicitudesLb) {
    this.solicitudesLb = solicitudesLb;
  }

  public ProcessEngine getProcessEngine() {
    return processEngine;
  }

  public void setProcessEngine(ProcessEngine processEngine) {
    this.processEngine = processEngine;
  }

  public List<Tarea> getTasks() throws InterruptedException {
    return tasks;
  }

  public void setTasks(List<Tarea> tasks) {
    this.tasks = tasks;
  }

  public Tarea getSelectedTask() {
    return selectedTask;
  }

  public void setSelectedTask(Tarea selectedTask) {
    this.selectedTask = selectedTask;
  }

  public Window getTaskView() {
    return taskView;
  }

  public void setTaskView(Window taskView) {
    this.taskView = taskView;
  }

  public Listbox getTasksList() {
    return tasksList;
  }

  public void setTasksList(Listbox tasksList) {
    this.tasksList = tasksList;
  }

  public AnnotateDataBinder getBinder() {
    return binder;
  }

  public void setBinder(AnnotateDataBinder binder) {
    this.binder = binder;
  }

  public ExpedienteElectronicoService getExpedienteElectronicoService() {
    return this.expedienteElectronicoService;
  }

  public void setExpedienteElectronicoService(
      ExpedienteElectronicoService expedienteElectronicoService) {
    this.expedienteElectronicoService = expedienteElectronicoService;
  }

  public TrataService getTrataService() {
    return this.trataService;
  }

  public void setTrataService(TrataService trataService) {
    this.trataService = trataService;
  }

  public TrataDTO getTrata() {
    return this.trata;
  }

  public void setTrata(TrataDTO trata) {
    this.trata = trata;
  }

  public ExpedienteAsociadoService getExpedienteAsociadoService() {
    return expedienteAsociadoService;
  }

  public void setExpedienteAsociadoService(ExpedienteAsociadoService expedienteAsociadoService) {
    this.expedienteAsociadoService = expedienteAsociadoService;
  }

  private void crearFormularioControlado(IngresoSolicitudExpedienteDTO ingresoSolicitudExpediente)
      throws NotFoundException {

    TipoDocumentoDTO tipoDocumentoCaratulaDeEE = this.tipoDocumentoService.obtenerTipoDocumento(
        ingresoSolicitudExpediente.getExpedienteElectronico().getTrata().getAcronimoDocumento());

    if (tipoDocumentoCaratulaDeEE == null) {
      throw new WrongValueException(Labels.getLabel("ee.nuevoexpediente.faltadocumentocaratula"));
    }

    String form = tipoDocumentoCaratulaDeEE.getIdFormulario();

    Map<String, Object> mapComp = new HashMap<String, Object>();
    mapComp.put("doBeforeExecuteTask", Boolean.TRUE);
    mapComp.put("inboxComposer", this);
    mapComp.put("nombreFormulario", form);
    mapComp.put("solicitud", ingresoSolicitudExpediente);
    mapComp.put("numeroSade",
        ingresoSolicitudExpediente.getExpedienteElectronico().getCodigoCaratula());
    mapComp.put("expElect", ingresoSolicitudExpediente.getExpedienteElectronico());
    mapComp.put("tipoDoc", tipoDocumentoCaratulaDeEE);
    mapComp.put("selectedTask", this.selectedTask.getTask());

    this.formularioControladoWindows = (Window) Executions.createComponents(
        "/expediente/macros/formularioControlado.zul",
        // this.caratularExternoWindow,
        this.self, mapComp);
    this.formularioControladoWindows.doModal();
    this.formularioControladoWindows.setPosition("center");
    this.formularioControladoWindows.setClosable(true);
  }

  final class InboxOnNotifyWindowListener implements EventListener {
    private InboxComposer composer;

    public InboxOnNotifyWindowListener(InboxComposer comp) {
      this.composer = comp;
    }

    public void onEvent(Event event) throws Exception {

      if (event.getName().equals(Events.ON_NOTIFY)) {
        if (event.getData() != null) {
          Map<String, Object> map = (Map<String, Object>) event.getData();

          String origen = (String) map.get("origen");
          if (StringUtils.equals(origen, ConstantesWeb.COMPOSER)) {
            Boolean metodoOrigen = (Boolean) map.get("metodoOrigen");

            if (!StringUtils.isBlank((String) map.get("acronimo"))) {

              Messagebox.show((String) map.get("msg"),
                  Labels.getLabel("ee.tramitacion.titulo.pase"), Messagebox.OK,
                  Messagebox.INFORMATION);

              Events.sendEvent(this.composer.getInboxWindow(), new Event(Events.ON_CANCEL));
              this.composer.refreshInbox();
              return;
            }

            if (null != (Boolean) map.get("fulfilledForm")) {
              if (!(Boolean) map.get("fulfilledForm")) {
                Messagebox.show((String) map.get("msg"),
                    Labels.getLabel("ee.tramitacion.titulo.pase"), Messagebox.OK,
                    Messagebox.INFORMATION);
              }
            }

            if ((Boolean) map.get("beforeExecuteTask")) {

              if ((Boolean) map.get("fulfilledForm")) {

                String codigoExpedienteElectronico = (String) executionService.getVariable(
                    composer.selectedTask.getTask().getExecutionId(), "codigoExpediente");
                alert("Se completó el formulario del expediente: " + codigoExpedienteElectronico
                    + " de manera exitosa");
              }

            } else {
              if (null != (Boolean) map.get("fromGenerarNuevoExpediente")) {
                if ((Boolean) map.get("fromGenerarNuevoExpediente")) {
                  Messagebox.show((String) map.get("msg"),
                      Labels.getLabel("ee.tramitacion.titulo.pase"), Messagebox.OK,
                      Messagebox.INFORMATION);
                }
              }
            }

            if (metodoOrigen == Boolean.FALSE) {
              Messagebox.show((String) map.get("msg"),
                  Labels.getLabel("ee.tramitacion.titulo.pase"), Messagebox.OK,
                  Messagebox.INFORMATION);
            }

          }

        }

        this.composer.refreshInbox();
      }
      if (event.getName().equals(Events.ON_CANCEL)) {
        Events.sendEvent(this.composer.getInboxWindow(), new Event(Events.ON_CANCEL));
        this.composer.refreshInbox();
      }
      if (event.getName().equals(Events.ON_CLOSE)) {

        if (event.getData() != null) {
          Map<String, Object> map = (Map<String, Object>) event.getData();

          String origen = (String) map.get("origen");
          if (StringUtils.equals(origen, ConstantesWeb.COMPOSER)) {
            Boolean metodoOrigen = (Boolean) map.get("metodoOrigen");

            if ((Boolean) map.get("fulfilledForm")) {

              ExecutionService executionService = ((ProcessEngine) SpringUtil
                  .getBean(ConstantesServicios.PROCESS_ENGINE_SERVICE)).getExecutionService();
              String codigoExpedienteElectronico = (String) executionService.getVariable(
                  composer.selectedTask.getTask().getExecutionId(), "codigoExpediente");
              alert("Se completó el formulario del expediente: " + codigoExpedienteElectronico
                  + " de manera exitosa");
            }
          }
        }

      }
      if (event.getName().equals(Events.ON_USER)) {
        if (event.getData().equals("irASistemaExterno")) {
          this.composer.irASistemaExterno();
        }

      }
      if (event.getName().equals(Events.ON_USER)) {
        if (event.getData().equals("actualizarDocumentosYEnviar")) {
          this.composer.actualizarDocumentosYEnviar();
        }

      }
    }
  }

  public Task obtenerWorkingTask(ExpedienteElectronicoDTO expedienteElectronico)
      throws ParametroIncorrectoException {

    if (expedienteElectronico != null) {
      TaskQuery taskQuery = processEngine.getTaskService().createTaskQuery()
          .executionId(expedienteElectronico.getIdWorkflow());

      return taskQuery.uniqueResult();

    } else {
      throw new ParametroIncorrectoException("El expediente electrónico es nulo.", null);
    }
  }

	public void obtainTaskAndRedirect() throws InterruptedException {
		if (Executions.getCurrent().getParameter("taskId") != null) {
			String idTask = Executions.getCurrent().getParameter("taskId");
			
			Task jbpm4Task = null;
			Tarea transformedTask = null;
			
			try {
				jbpm4Task = processEngine.getTaskService().createTaskQuery().executionId(idTask).uniqueResult(); 
			}
			catch (Exception e) {
				logger.debug("Error in InboxComposer.obtainTaskAndRedirect() - getTask: ", e);
			}
			
			if (jbpm4Task != null && jbpm4Task.getAssignee().equalsIgnoreCase(
					Executions.getCurrent().getSession().getAttribute(ConstantesWeb.SESSION_USERNAME).toString())) {
				Task2TareaTransformer transformer = new Task2TareaTransformer(
						trataService, tareaParaleloService, processEngine,
						historialService, null);
				
				try {
					transformedTask = (Tarea) transformer.transform(jbpm4Task);
				}
				catch (Exception e) {
					logger.debug("Error in InboxComposer.obtainTaskAndRedirect() - transformTask: ", e);
				}
			}
			
			if (transformedTask != null) {
				if (Executions.getCurrent().getParameter("goBackDb") != null
						&& Executions.getCurrent().getParameter("goBackDb").equals(Boolean.TRUE.toString())) {
					Executions.getCurrent().getSession().setAttribute("goBackDb", true);
				}
				
				this.selectedTask = transformedTask;
				this.onExecuteTask();
			}
			else {
				Messagebox.show(Labels.getLabel("ee.consultaExpElecComp.msgbox.errorTarea"),
						Labels.getLabel("ee.general.information"), Messagebox.OK, Messagebox.EXCLAMATION);
			}
		}
	}
  
}
