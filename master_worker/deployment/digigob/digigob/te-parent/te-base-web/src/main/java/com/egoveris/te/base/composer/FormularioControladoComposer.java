package com.egoveris.te.base.composer;

import com.egoveris.deo.model.model.RequestExternalGenerarDocumento;
import com.egoveris.deo.model.model.ResponseExternalGenerarDocumento;
import com.egoveris.deo.ws.exception.CantidadDatosNoSoportadaException;
import com.egoveris.deo.ws.exception.ClavesFaltantesException;
import com.egoveris.deo.ws.exception.ErrorGeneracionDocumentoException;
import com.egoveris.deo.ws.exception.ParametroInvalidoException;
import com.egoveris.deo.ws.service.IExternalGenerarDocumentoService;
import com.egoveris.ffdd.model.exception.DynFormException;
import com.egoveris.ffdd.render.service.IFormManager;
import com.egoveris.ffdd.render.service.IFormManagerFactory;
import com.egoveris.te.base.exception.NegocioException;
import com.egoveris.te.base.exception.external.TeRuntimeException;
import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.IngresoSolicitudExpedienteDTO;
import com.egoveris.te.base.model.TipoDocumentoDTO;
import com.egoveris.te.base.service.DocumentoService;
import com.egoveris.te.base.service.TipoDocumentoService;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.service.iface.IActividadExpedienteService;
import com.egoveris.te.base.service.iface.ISolrService;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.ConstantesServicios;
import com.egoveris.te.base.util.DocumentoCaratulaGedoTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Div;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class FormularioControladoComposer extends EEGenericForwardComposer {

  final class EnvioExpedienteElectronicoOnNotifyWindowListener implements EventListener {
    Map<String, Object> params = new HashMap<>();

    private final FormularioControladoComposer composer;

    public EnvioExpedienteElectronicoOnNotifyWindowListener(
        final FormularioControladoComposer comp) {
      this.composer = comp;
    }

    @Override
    public void onEvent(final Event event) throws Exception {

      if (event.getName().equals(Events.ON_NOTIFY)) {
        if (event.getData() != null) {
          final Map<String, Object> map = (Map<String, Object>) event.getData();
          final String origen = (String) map.get("origen");

          if (StringUtils.equals(origen, ConstantesWeb.ESTADO_GUARDA_TEMPORAL)) {
            params.put("origen", ConstantesWeb.COMPOSER);
            params.put("msg", map.get("msg"));
            params.put("beforeExecuteTask", Boolean.FALSE);
            params.put("fulfilledForm", Boolean.TRUE);
            this.composer.closeAndNotifyAssociatedWindow(params);
          }
        }
      }
    }

  }

  private static final long serialVersionUID = 1241392901353641742L;
  private final static Logger logger = LoggerFactory.getLogger(FormularioControladoComposer.class);
  private static String CARATULA_VARIABLE = "CARATULA";
  private static final String INICIACION = "Iniciacion";
  private static final String PENDIENTE_INICIACION = "Pendiente Iniciacion";
  private static final String TRAMITACION_EN_PARALELO = "Paralelo";
  private static final String ANULAR_MODIFICAR = "Anular/Modificar Solicitud";
  
  @WireVariable("generarDocumentoService")
  private IExternalGenerarDocumentoService generarDocumentoService;
  
  @WireVariable(ConstantesServicios.TIPO_DOCUMENTO_SERVICE)
  private TipoDocumentoService tipoDocumentoService;
  
  @WireVariable(ConstantesServicios.EXP_ELECTRONICO_SERVICE)
  private ExpedienteElectronicoService eEService; 
  
  @WireVariable("documentoServiceImpl")
  private DocumentoService documentoService;
  
  @WireVariable(ConstantesServicios.SOLR_SERVICE)
  private ISolrService solrService;
  
  @WireVariable(ConstantesServicios.DOC_CARATULA_GEDO_TEMPLATE)
  private DocumentoCaratulaGedoTemplate documentoCaratulaGedoTemplate;
  
  private ExpedienteElectronicoDTO ee;
  private RequestExternalGenerarDocumento request;
  private String sade = "";
  private TipoDocumentoDTO tipoDocumento;
  private IngresoSolicitudExpedienteDTO solicitud;
  private boolean beforeExecuteTask;
  private boolean fromGenerarNuevoExpediente;
  private boolean emptyForm;
  private Window taskView;
  private Window envio;
  private Window motivoGuardaTemporalWindow;
  private InboxComposer inboxComposer;
  private Task task;
  private Combobox usuarioDestino;
  
  @WireVariable(ConstantesServicios.PROCESS_ENGINE_SERVICE)
  private ProcessEngine processEngine;
  
  private String usuarioLicenciado;

  @Autowired
  private Button guardar;
  public Div div;
  
  @WireVariable(ConstantesServicios.FORM_MANAGER_FACTORY)
  private IFormManagerFactory<IFormManager<Component>> formManagerFact;

  private IFormManager<Component> manager;
  
  @WireVariable(ConstantesServicios.ACTIVIDAD_EXPEDIENTE_SERVICE)
  private IActividadExpedienteService actividadExpedienteService;

  private RequestExternalGenerarDocumento armarRequestParaGenerarCaratulaEnGedo(
      final ExpedienteElectronicoDTO ee, final IngresoSolicitudExpedienteDTO solicitud,
      final Integer idTrans) throws RuntimeException {

    TipoDocumentoDTO tipoDocumentoCaratulaDeEE;

    tipoDocumentoCaratulaDeEE = this.tipoDocumentoService
        .buscarTipoDocumentoByUso(CARATULA_VARIABLE);

    if (tipoDocumentoCaratulaDeEE == null) {
      throw new TeRuntimeException(
          "El administrador debe seleccionar un documento Caratula para poder generar el trámite.", null);
    }
    final String loggedUsername = (String) Executions.getCurrent().getDesktop().getSession()
        .getAttribute(ConstantesWeb.SESSION_USERNAME);

    final RequestExternalGenerarDocumento request = new RequestExternalGenerarDocumento();
    request.setData(documentoCaratulaGedoTemplate.generarCaratulaComoByteArray(ee, solicitud.getSolicitudExpediente()));
    request.setReferencia("Carátula Variable " + ee.getCodigoCaratula());
    request.setIdTransaccion(idTrans);
    request.setAcronimoTipoDocumento(this.getTipoDocumento().getAcronimo());
    if (StringUtils.isNotEmpty(usuarioLicenciado)) {
      request.setUsuario(usuarioLicenciado);
    } else {
      request.setUsuario(loggedUsername);
    }
    request.setSistemaOrigen(ConstantesWeb.SIGLA_MODULO_ORIGEN);

    return request;
  }

  @SuppressWarnings("unchecked")
  @Override
  public void doAfterCompose(final Component comp) throws Exception {
    super.doAfterCompose(comp);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    
    final String formComp = (String) Executions.getCurrent().getArg().get("nombreFormulario");
    usuarioLicenciado = (String) Executions.getCurrent().getAttribute("usuarioLicenciado");
    Executions.getCurrent().setAttribute("usuarioLicenciado", null);
    this.setSade((String) Executions.getCurrent().getArg().get("numeroSade"));
    this.setEe((ExpedienteElectronicoDTO) Executions.getCurrent().getArg().get("expElect"));
    this.setTipoDocumento((TipoDocumentoDTO) Executions.getCurrent().getArg().get("tipoDoc"));
    setSolicitud((IngresoSolicitudExpedienteDTO) Executions.getCurrent().getArg().get("solicitud"));
    this.setBeforeExecuteTask(
        (Boolean) Executions.getCurrent().getArg().get("doBeforeExecuteTask"));
    this.setInboxComposer((InboxComposer) Executions.getCurrent().getArg().get("inboxComposer"));
    this.setTask((Task) Executions.getCurrent().getArg().get("selectedTask"));
    if (null != (Boolean) Executions.getCurrent().getArg().get("fromGenerarNuevoExpediente")) {
      this.setFromGenerarNuevoExpediente(
          (Boolean) Executions.getCurrent().getArg().get("fromGenerarNuevoExpediente"));
    }

    comp.addEventListener(Events.ON_NOTIFY,
        new EnvioExpedienteElectronicoOnNotifyWindowListener(this));

    try {
      this.manager = formManagerFact.create(formComp);
      manager.getFormComponent().setParent(div);
    } catch (final DynFormException e) {
      onCancelar();
      throw new WrongValueException(e.getMessage());
    } catch (final Exception e) {
      this.guardar.setDisabled(true);

      if (this.getInboxComposer() == null) {
        // Avisa a la ventana padre que debe refrescar su contenido.
        final HashMap eventData = new HashMap();
        eventData.put("beforeExecuteTask", Boolean.TRUE);
        Events.sendEvent(new Event(Events.ON_NOTIFY, this.self.getParent(), eventData));
      }
      final String error = Labels.getLabel("ee.servicio.dynform.errorComunicacionDynform");
      logger.error(error + e.getStackTrace());
      throw new WrongValueException(error);
    }

  }

  private DocumentoDTO generarDocumento(final ExpedienteElectronicoDTO ee,
      final String usuarioCreador, final ResponseExternalGenerarDocumento response) {

    final DocumentoDTO documento = new DocumentoDTO();
    documento.setFechaCreacion(ee.getFechaCreacion());
    documento.setFechaAsociacion(new Date());
    documento.setNombreUsuarioGenerador(usuarioCreador);
    documento.setDefinitivo(true);
    // TODO revisar necesidad de almacenar nombre de archivo tal como se
    // hace aquí (sin ruta absoluta, asumiendo .pdf)
    documento.setNombreArchivo(response.getNumero() + ".pdf");
    documento.setNumeroSade(response.getNumero());
    documento.setUsuarioAsociador(usuarioCreador);
    if (ee.getFechaModificacion() == null) {
      documento.setMotivo(ConstantesWeb.MOTIVO_CARATULA);
    } else {
      documento.setMotivo(ConstantesWeb.MOTIVO_MODIFICACION_CARATULA);
    }
    return documento;
  }

  public boolean getBeforeExecuteTask() {
    return this.beforeExecuteTask;
  }

  public DocumentoService getDocumentoService() {
    return this.documentoService;
  }

  public ExpedienteElectronicoDTO getEe() {
    return this.ee;
  }

  public ExpedienteElectronicoService geteEService() {
    return this.eEService;
  }

  public boolean getEmptyForm() {
    return this.emptyForm;
  }

  public boolean getFromGenerarNuevoExpediente() {
    return this.fromGenerarNuevoExpediente;
  }

  public IExternalGenerarDocumentoService getGenerarDocumentoService() {
    return this.generarDocumentoService;
  }

  public Button getGuardar() {
    return guardar;
  }

  public InboxComposer getInboxComposer() {
    return inboxComposer;
  }

 


  public RequestExternalGenerarDocumento getRequest() {
    return this.request;
  }

  public String getSade() {
    return this.sade;
  }

  public IngresoSolicitudExpedienteDTO getSolicitud() {
    return this.solicitud;
  }

  public Task getTask() {
    return this.task;
  }

  public Window getTaskView() {
    return taskView;
  }

  public TipoDocumentoDTO getTipoDocumento() {
    return this.tipoDocumento;
  }

  public TipoDocumentoService getTipoDocumentoService() {
    return this.tipoDocumentoService;
  }

  public int guardarExpedienteElectronico(final boolean fromCancelar, final Map eventData)
      throws ParametroInvalidoException {

    Integer idTransaccion = null;
    Map<String, Object> camposFc = new HashMap<String, Object>();
    boolean fulfilledForm = Boolean.TRUE;

    try {

      try {
        try {
          if (this.manager != null) {
            if(ee != null && ee.getIdOperacion() != null){
              idTransaccion = this.manager.saveValues(ee.getIdOperacion());
            }else{
              idTransaccion = this.manager.saveValues();
            }
          }
        } catch (final DynFormException e) {

          logger.error(e.getMessage());
        }
      } catch (final WrongValueException wVExc) {
        fulfilledForm = Boolean.FALSE;
        logger.error("El formulario controlado no ha sido completado. ", wVExc);
      }

      if (fromCancelar) {
        fulfilledForm = Boolean.FALSE;
      }

      // CU01 - Flujo Alternativo: El usuario no completa el formulario
      // controlado
      if (!fulfilledForm) {

        if (!this.getBeforeExecuteTask()) {
          eventData.put("beforeExecuteTask", Boolean.FALSE);
          eventData.put("msg", "Se generó el expediente: " + this.getEe().getCodigoCaratula());
        } else {
          // se esta ejecutando el ee
          eventData.put("beforeExecuteTask", Boolean.TRUE);
        }

        // 8. El sistema deja el expedienteElectronico inhabilitado para
        // ser tramitado.
        // 9. Avisar al usuario que el EE quedara bloqueado.
        this.getEe().setEstado(PENDIENTE_INICIACION);
        this.registrarExpedienteElectronico(this.getEe());

        if (this.getInboxComposer() == null) {

          // Volver a inbox
          if (fromCancelar) {
            eventData.put(Events.ON_CANCEL, Boolean.TRUE);
          } else {
            eventData.put(Events.ON_CLOSE, Boolean.TRUE);
          }

          eventData.put("origen", ConstantesWeb.COMPOSER);
          eventData.put("fulfilledForm", Boolean.FALSE);
          eventData.put("msg", "Se generó el expediente: " + this.getEe().getCodigoCaratula()
              + " y quedará pendiente de iniciación hasta que el formulario controlado asociado sea completado.");

        }
        super.closeAndNotifyAssociatedWindow(eventData);

        return 0;
      }
      // CU01 - 9. Habilitar expedienteElectronico para ser tramitado

      request = this.armarRequestParaGenerarCaratulaEnGedo(this.getEe(), this.getSolicitud(),
          idTransaccion);

      // CU01 - 8. Asociar al expedienteElectronico un nuevo documento
      // GEDO que contiene los datos cargados en el formulario controlado.
      try {

        ResponseExternalGenerarDocumento response;

        try {
          response = generarDocumentoService.generarDocumentoGEDO(request);

          if (response == null) {
            throw new TeRuntimeException("Se produjo un error al generar la caratula", null);
          }

          logger.debug("Url archivo generado: " + response.getUrlArchivoGenerado().toString());
          DocumentoDTO documento;
          if (usuarioLicenciado != null) {
            documento = generarDocumento(this.getEe(), usuarioLicenciado, response);
          } else {
            documento = generarDocumento(this.getEe(), this.getEe().getUsuarioCreador(), response);
          }

          documento.setTipoDocAcronimo(request.getAcronimoTipoDocumento());

          // Cuando se vincula el documento GEDO del formulario
          // controlado vamos a tener que guardar
          // el id de transacción en la BD de EE en la tabla
          // Documentos.
          documento.setIdTransaccionFC(idTransaccion.longValue());

          this.getEe().getDocumentos().add(documento);
          String execution = null;
          Task t = null;
          try {
            t = eEService.obtenerWorkingTask(this.getEe());
            execution = t.getExecutionId().split("\\.")[0];

          } catch (final Exception e) {
            this.getEe().setEstado(INICIACION);
          }

          if (execution != null && !execution.equalsIgnoreCase("solicitud")) {
            this.getEe().setEstado(t.getActivityName());
          } else {
            this.getEe().setEstado(INICIACION);
          }

          final String username = Executions.getCurrent().getSession()
              .getAttribute(ConstantesWeb.SESSION_USERNAME).toString();
          this.geteEService().actualizarReservaEnLaTramitacion(this.getEe(), username);

        } catch (final CantidadDatosNoSoportadaException e) {

          logger.error(e.getMessage());
        } catch (final ErrorGeneracionDocumentoException e) {

          logger.error(e.getMessage());
        } catch (final ClavesFaltantesException e) {

          logger.error(e.getMessage());
        }

      } catch (final ParametroInvalidoException piExc) {
        logger.error("Error en validación del contenido del documento con referencia: "
            + request.getReferencia() + ", " + piExc.getMessage());
        // eventData.put(Events.ON_CLOSE, Boolean.TRUE);
        eventData.put("origen", ConstantesWeb.COMPOSER);
        eventData.put("noPermitido", Boolean.TRUE);
        eventData.put("fulfilledForm", Boolean.FALSE);
        eventData.put("beforeExecuteTask", Boolean.FALSE);
        eventData.put("fromGenerarNuevoExpediente", Boolean.FALSE);
        eventData.put("acronimo", request.getAcronimoTipoDocumento());
        eventData.put("msg",
            "Su organismo no esta habilitada para caratular un expediente electronico con el tipo de documento que posee acronimo: "
                + request.getAcronimoTipoDocumento()
                + " el cual se encuentra asociado a la trata.");

        super.closeAndNotifyAssociatedWindow(eventData);
        return 0;
      }

      // CU01 - 10. Registrar expedienteElectronico.

      if (!this.getBeforeExecuteTask()) {
        eventData.put("fulfilledForm", Boolean.TRUE);
        eventData.put("beforeExecuteTask", Boolean.FALSE);
        eventData.put("fromGenerarNuevoExpediente", Boolean.TRUE);
        eventData.put("msg", "Se generó el expediente: " + this.getEe().getCodigoCaratula());
      } else {
        // se esta ejecutando el ee
        eventData.put("fulfilledForm", Boolean.TRUE);
        eventData.put("beforeExecuteTask", Boolean.TRUE);
      }

      if (fromCancelar) {
        eventData.put(Events.ON_CANCEL, Boolean.TRUE);
      } else {
        eventData.put(Events.ON_CLOSE, Boolean.TRUE);
      }
      eventData.put("origen", ConstantesWeb.COMPOSER);
      this.registrarExpedienteElectronico(this.getEe());

      super.closeAndNotifyAssociatedWindow(eventData);

      if (null != this.manager) {
        camposFc = this.manager.getValues();

        solrService.indexarFormularioControlado(this.getEe(), camposFc);
      }

    } catch (final NegocioException e) {
      logger.error("Se produjo un error indexando los campos del formulario controlado.");
      logger.error(e.getMessage());
      throw new TeRuntimeException(
          "Se produjo un error indexando los campos del formulario controlado.", e);
    }
    return 1;
  }

  public void mostrarCaratulaGenerada(final ExpedienteElectronicoDTO expedienteElectronico)
      throws InterruptedException {
    Messagebox.show( Labels.getLabel("ee.caratularExtComp.msgbox.seGeneroExp") + " " + this.getEe().getCodigoCaratula(),
        Labels.getLabel("ee.tramitacion.titulo.nuevoExpediente"), Messagebox.OK,
        Messagebox.INFORMATION);
  }

  public void onCancelar() {

    final boolean fromCancelar = Boolean.TRUE;
    final Map eventData = new HashMap();
    this.getEe().setEstado(PENDIENTE_INICIACION);

    try {
      this.guardarExpedienteElectronico(fromCancelar, eventData);
    } catch (final ParametroInvalidoException e) {

      logger.error(e.getMessage());
    }
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  public void onClick$enviarAGuardaTemporalButton() throws InterruptedException {

    final Task currentTask = this.getTask();

    if (!currentTask.getActivityName().equals(TRAMITACION_EN_PARALELO)) {
      if (!currentTask.getActivityName().equals(ANULAR_MODIFICAR)) {
        // Actividades pendientes
        if (tieneActividadesPendientes(currentTask.getExecutionId())) {
          Messagebox.show(Labels.getLabel("ee.act.msg.body.actPendientes"),
              Labels.getLabel("ee.general.information"), Messagebox.OK, Messagebox.EXCLAMATION);
        } else {
          final HashMap hma = new HashMap();
          hma.put("currentSelectedTask", currentTask);
          hma.put("fromDynForm", Boolean.TRUE);

          this.motivoGuardaTemporalWindow = (Window) Executions
              .createComponents("/pase/motivoguardatemporal.zul", this.self, hma);

          this.motivoGuardaTemporalWindow.setPosition("center");
          this.motivoGuardaTemporalWindow.setClosable(true);
          this.motivoGuardaTemporalWindow.doModal();
        }
      } else {
        this.usuarioDestino.setValue("");
        Messagebox.show(Labels.getLabel("ee.general.TareaAnularModificarSolicitud"),
            Labels.getLabel("ee.general.information"), Messagebox.OK, Messagebox.EXCLAMATION);
      }

    } else {
      this.usuarioDestino.setValue("");
      Messagebox.show(Labels.getLabel("ee.general.TareaParalelo"),
          Labels.getLabel("ee.general.information"), Messagebox.OK, Messagebox.EXCLAMATION);
    }

  }

  public int onGuardarExp()
      throws InterruptedException, DynFormException, ParametroInvalidoException {

    final boolean fromCancelar = Boolean.FALSE;

    int rtnValue = 0;
    final Map eventData = new HashMap();
    try {
      if(ee != null && ee.getIdOperacion() != null){
          this.manager.saveValues(ee.getIdOperacion());
      }else{
          this.manager.saveValues();
      }
      this.guardar.setDisabled(true);
    } catch (final WrongValueException wVExc) {
      logger.error("El formulario controlado no ha sido completado. " + wVExc.getMessage());
      throw new WrongValueException(wVExc.getMessage());
    }

    try {

      if (this.getBeforeExecuteTask()) {
        // Continuar con ejecucion expediente.
        rtnValue = this.guardarExpedienteElectronico(fromCancelar, eventData);
      } else {
        // Volver a inbox
        rtnValue = this.guardarExpedienteElectronico(fromCancelar, eventData);
      }
    } catch (final ParametroInvalidoException e) {
      throw new ParametroInvalidoException(e.getMessage());
    }

    return rtnValue;
  }

  /**
   * @deprecated
   *
   */
  @Deprecated
  public void onGuardarTemporal() {

    final HashMap<String, Object> hma = new HashMap<>();
    hma.put("expedienteElectronico", this.getEe());
    hma.put("estadoAnterior", " ");

    this.envio = (Window) Executions.createComponents("/pase/envio.zul", null, hma);
    this.envio.setParent(this.self);
    this.envio.setPosition("center");
    this.envio.setClosable(true);

    this.envio.doModal();

  }

  private void registrarExpedienteElectronico(final ExpedienteElectronicoDTO ee) {
    eEService.grabarExpedienteElectronico(ee);
  }

  public void setBeforeExecuteTask(final boolean beforeExecuteTask) {
    this.beforeExecuteTask = beforeExecuteTask;
  }

  public void setDocumentoService(final DocumentoService documentoService) {
    this.documentoService = documentoService;
  }

  public void setEe(final ExpedienteElectronicoDTO ee) {
    this.ee = ee;
  }

  public void seteEService(final ExpedienteElectronicoService eEService) {
    this.eEService = eEService;
  }

  public void setEmptyForm(final boolean emptyForm) {
    this.emptyForm = emptyForm;
  }

  public void setFromGenerarNuevoExpediente(final boolean fromGenerarNuevoExpediente) {
    this.fromGenerarNuevoExpediente = fromGenerarNuevoExpediente;
  }

  public void setGenerarDocumentoService(
      final IExternalGenerarDocumentoService generarDocumentoService) {
    this.generarDocumentoService = generarDocumentoService;
  }

  public void setGuardar(final Button guardar) {
    this.guardar = guardar;
  }

  public void setInboxComposer(final InboxComposer inboxComposer) {
    this.inboxComposer = inboxComposer;
  }
 

  public void setRequest(final RequestExternalGenerarDocumento request) {
    this.request = request;
  }

  public void setSade(final String sade) {
    this.sade = sade;
  }

  public void setSolicitud(final IngresoSolicitudExpedienteDTO solicitud) {
    this.solicitud = solicitud;
  }

  public void setTask(final Task task) {
    this.task = task;
  }

  public void setTaskView(final Window taskView) {
    this.taskView = taskView;
  }

  public void setTipoDocumento(final TipoDocumentoDTO tipoDocumento) {
    this.tipoDocumento = tipoDocumento;
  }

  public void setTipoDocumentoService(final TipoDocumentoService tipoDocumentoService) {
    this.tipoDocumentoService = tipoDocumentoService;
  }

  private boolean tieneActividadesPendientes(final String workflowid) {
    final List<String> workIds = new ArrayList<String>();
    workIds.add(workflowid);

    return !actividadExpedienteService.buscarActividadesVigentes(workIds).isEmpty();
  }

}
