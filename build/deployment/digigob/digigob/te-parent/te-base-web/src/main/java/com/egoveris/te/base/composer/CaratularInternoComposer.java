package com.egoveris.te.base.composer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.exception.SQLGrammarException;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.remoting.RemoteAccessException;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.egoveris.ffdd.model.exception.DynFormException;
import com.egoveris.sharedorganismo.base.model.ReparticionBean;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.te.base.exception.VariableWorkFlowNoExisteException;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.ExpedienteMetadataDTO;
import com.egoveris.te.base.model.IngresoSolicitudExpedienteDTO;
import com.egoveris.te.base.model.MetadataDTO;
import com.egoveris.te.base.model.SolicitudExpedienteDTO;
import com.egoveris.te.base.model.TrataDTO;
import com.egoveris.te.base.service.CaratulacionService;
import com.egoveris.te.base.service.HistorialOperacionService;
import com.egoveris.te.base.service.ObtenerReparticionServices;
import com.egoveris.te.base.service.SolicitudExpedienteService;
import com.egoveris.te.base.service.TipoDocumentoService;
import com.egoveris.te.base.service.TrataReparticionService;
import com.egoveris.te.base.service.TrataService;
import com.egoveris.te.base.service.UsuariosSADEService;
import com.egoveris.te.base.service.WorkFlowService;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.util.ConstantesServicios;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.DatosSolicitud2MapTransformer;
import com.egoveris.te.base.util.ValidacionesUtils;
import com.egoveris.te.model.util.MailUtil;

import junit.framework.AssertionFailedError;

/**
 *
 * @author Sebastian Roidzaid
 *
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class CaratularInternoComposer extends ValidarFormularioControladoComposer {

  // VARIABLES - BEGIN

  private final static Logger logger = LoggerFactory.getLogger(CaratularInternoComposer.class);
  private Window caratularInternoWindow;
  private Textbox motivoInternoExpediente;
  private Textbox motivoExternoExpediente;
  private Bandbox codigoTrata;
  private TrataDTO selectedTrata;
  private Textbox descripcion;
  private Textbox email;
  private Textbox telefono;
  private Listbox trataListbox;

  private Button guardar;

  protected Task workingTask = null;
  protected ProcessEngine processEngine;
  private List<ExpedienteMetadataDTO> expedienteMetadata;
  private boolean esEE = false;
  private ReparticionBean rep;
  @WireVariable(ConstantesServicios.OBTENER_REPARTICION_SERVICE)
  private ObtenerReparticionServices obtenerReparticionService;
  private boolean permiso = false;
  private List<TrataDTO> tratas;
  private List<TrataDTO> tratasSeleccionadas;
  private AnnotateDataBinder binder;
  private String trataSel;
  private boolean existe;
  private Usuario user;

  /**
   * Defino los servicios que voy a utilizar
   */
  @WireVariable(ConstantesServicios.EXP_ELECTRONICO_SERVICE)
  private ExpedienteElectronicoService expedienteElectronicoService;
  @WireVariable(ConstantesServicios.HISTORIAL_OPERACION_SERVICE)
  private HistorialOperacionService historialOperacionService;
  @WireVariable(ConstantesServicios.SOLICITUD_EXPEDIENTE_SERVICE)
  private SolicitudExpedienteService solicitudExpedienteService;
  @WireVariable(ConstantesServicios.TRATA_SERVICE)
  private TrataService trataService;
  @WireVariable(ConstantesServicios.CARATULACION_SERVICE)
  private CaratulacionService caratulador;
  @WireVariable(ConstantesServicios.TIPO_DOCUMENTO_SERVICE)
  private TipoDocumentoService tipoDocumentoService;
  private SolicitudExpedienteDTO solicitud;
  @WireVariable(ConstantesServicios.TRATA_REPARTICION_SERVICE)
  private TrataReparticionService trataReparticionService;
  @WireVariable(ConstantesServicios.WORKFLOW_SERVICE)
  private WorkFlowService workFlowService;
  @WireVariable(ConstantesServicios.USUARIOS_SADE_SERVICE)
  private UsuariosSADEService usuariosSADEService;

  // VARIABLES - END

  final class CaratularInternoOnNotifyWindowListener implements EventListener {
    private CaratularInternoComposer composer;

    public CaratularInternoOnNotifyWindowListener(
        CaratularInternoComposer caratularInternoComposer) {
      this.composer = caratularInternoComposer;
    }

    public void onEvent(Event event) throws Exception {

      if (event.getName().equals(Events.ON_NOTIFY)) {
        if (event.getData() != null) {
          Map<String, Object> map = (Map<String, Object>) event.getData();
          Boolean origenClose = Boolean.FALSE;
          Boolean origenCancel = Boolean.FALSE;

          String origen = (String) map.get("origen");
          if (StringUtils.equals(origen, ConstantesWeb.COMPOSER)) {
            if (!(Boolean) map.get("beforeExecuteTask")) {
              Messagebox.show((String) map.get("msg"),
                  Labels.getLabel("ee.tramitacion.titulo.pase"), Messagebox.OK,
                  Messagebox.INFORMATION);
            }
          }
          origenClose = (Boolean) map.get(Events.ON_CLOSE);
          origenCancel = (Boolean) map.get(Events.ON_CANCEL);

          if (origenClose == Boolean.TRUE) {

            this.composer.getCaratularInternoWindow().onClose();
          }

          if (origenCancel == Boolean.TRUE) {
            Events.sendEvent(this.composer.getCaratularInternoWindow(),
                new Event(Events.ON_CLOSE));
          }
        }  
      }
      if (event.getName().equals(Events.ON_CLOSE)) {

        Events.sendEvent(this.composer.getCaratularInternoWindow(), new Event(Events.ON_CLOSE));

      }
      if (event.getName().equals(Events.ON_USER)) {
        if (event.getData().equals("guardarExpediente")) {
          this.composer.guardarExpediente();
        }
        if (event.getData().equals("selectTrata")) {
          this.composer.seleccionarTrata();
        }
      }
    }
  }

  // PUBLIC METHODS - BEGIN

  public void doAfterCompose(Component component) throws Exception {
    super.doAfterCompose(component);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    this.binder = new AnnotateDataBinder(component);
    component.addEventListener(Events.ON_NOTIFY, new CaratularInternoOnNotifyWindowListener(this));
    component.addEventListener(Events.ON_USER, new CaratularInternoOnNotifyWindowListener(this));

    this.existe = true;
    this.setWorkingTask((Task) component.getDesktop().getAttribute("selectedTask"));

    String username = Executions.getCurrent().getSession()
        .getAttribute(ConstantesWeb.SESSION_USERNAME).toString();

    user = usuariosSADEService.getDatosUsuario(username);

    this.tratas = this.getTratasServices();
    this.tratasSeleccionadas = new ArrayList<>(this.tratas);
    this.rep = obtenerReparticionService.buscarReparticionByUsuario(username);
    this.trataSel = "";
    this.motivoInternoExpediente.setFocus(true);
  }

  public void onBlur$codigoTrata() {

    TrataDTO trata = null;

    if (this.codigoTrata.getValue() != null && !this.codigoTrata.getValue().trim().equals("")) {
      this.trataSel = this.codigoTrata.getValue().toUpperCase();
      trata = trataService.buscarTrataByCodigo(this.codigoTrata.getValue());
    }

    this.verificarExistencia();
    this.permiso = false;

    if (trata != null) {
      if (!trataReparticionService.validarPermisoReparticion(trata, this.rep.getCodigo(),
          this.user)) {
        this.permiso = true;
        // this.datosPropios.setDisabled(true);
        Clients.clearBusy();
        throw new WrongValueException(this.codigoTrata,
            Labels.getLabel("ee.iniciarCaratulaInterna.sinAutorizacion",
                new String[] { user.getCodigoReparticion() }));
      } else {
        // this.datosPropios.setDisabled(false);

        List<MetadataDTO> metadatas = trata.getDatoVariable();
        expedienteMetadata = new ArrayList<ExpedienteMetadataDTO>(metadatas.size());

        for (int i = 0; metadatas.size() > i; i++) {
          ExpedienteMetadataDTO temp = new ExpedienteMetadataDTO();
          temp.setNombre(metadatas.get(i).getNombre());
          temp.setObligatoriedad(metadatas.get(i).isObligatoriedad());
          temp.setOrden(metadatas.get(i).getOrden());
          temp.setTipo(metadatas.get(i).getTipo());
          expedienteMetadata.add(temp);
        }
      }
      if (!trata.getEsInterno()) {
        throw new WrongValueException(this.codigoTrata, "La trata \"" + trata.getCodigoTrata()
            + "\" tiene que caratularse como Carátula Interna.");
      }
    }

  }

  public void onSelect$trataListbox() {
    mostrarForegroundBloqueanteToken();
    Events.echoEvent(Events.ON_USER, this.self, "selectTrata");
  }

  private void seleccionarTrata() {

    try {
      // Si trata no tiene caratula entonces pregunto si tiene datos
      // propios - BEGIN
      if (this.selectedTrata != null) {
        trataSel = this.selectedTrata.getCodigoTrata();
        this.codigoTrata.setValue(trataSel.toUpperCase());
        this.binder.loadAll();
        this.codigoTrata.close();
        this.onBlur$codigoTrata();
      }
    } catch (AssertionFailedError assertionFailedError) {
      logger.error(assertionFailedError.getMessage());
      this.logger("Hubo un error en el metodo onSelect$trataListbox con la excepción  "
          + new Exception(assertionFailedError.getMessage()).getMessage(), null);
    } finally {
      Clients.clearBusy();
    }
  }

  public void onGuardarExpediente() throws InterruptedException {
    if (!this.permiso) {
      validarDatosDelFormulario();
      this.mostrarForegroundBloqueanteToken();
      Events.echoEvent("onUser", this.self, "guardarExpediente");
    } else {
      Clients.clearBusy();
      Messagebox.show(
          Labels.getLabel("ee.iniciarCaratulaInterna.sinAutorizacion",
              new String[] { rep.getCodigo().trim() }),
          Labels.getLabel("ee.general.information"), Messagebox.OK, Messagebox.ERROR);

      return;
    }
  }

  public void guardarExpediente() throws InterruptedException {

    try {
      guardar.setDisabled(true);

      String username = Executions.getCurrent().getSession()
          .getAttribute(ConstantesWeb.SESSION_USERNAME).toString();
      IngresoSolicitudExpedienteDTO ingresoSolicitudExpediente = null;
      DatosSolicitud2MapTransformer transformer = new DatosSolicitud2MapTransformer();
      String motivoSolicitud = this.motivoInternoExpediente.getValue();
      TrataDTO trata = null;

      if (motivoSolicitud == null || motivoSolicitud.isEmpty()) {
        motivoSolicitud = this.motivoExternoExpediente.getValue();
      }

      if (codigoTrata.getValue() != null) {
        trata = trataService.buscarTrataByCodigo(this.codigoTrata.getValue());
      }

      ingresoSolicitudExpediente = (IngresoSolicitudExpedienteDTO) solicitudExpedienteService
          .generarSolicitudExpediente((Map<String, Object>) transformer.transform(this),
              processEngine, trata, this.descripcion.getValue(), expedienteMetadata, username,
              motivoSolicitud, false);

      if (trata != null && trata.getAcronimoDocumento() != null
          && trata.getAcronimoDocumento().length() > 0) {
        crearFormularioControlado(ingresoSolicitudExpediente,
            (Window) caratularInternoWindow.getParent(), trata);
      } else {
        ExpedienteElectronicoDTO expedienteElectronico = ingresoSolicitudExpediente
            .getExpedienteElectronico();

        if (expedienteElectronico.getEsReservado()) {
          expedienteElectronicoService.actualizarReservaEnLaTramitacion(expedienteElectronico,
              username);
        }
        if (logger.isDebugEnabled()) {
          logger.debug("on guardarExpediente request "
              + Executions.getCurrent().getNativeRequest().toString());
        }

        // ***********************************************************************
        // ** JIRA BISADE-2515:
        // https://quark.everis.com/jira/browse/BISADE-2515
        // ** - Se crea el parámetro "cacheQueries" para la consulta de
        // inbox no cachee cambios de statemachine.
        // ***********************************************************************
        Executions.getCurrent().getDesktop().setAttribute("cacheQueries", new Boolean(false));

        // DG_DF-EE-Diseño_Funcional
        // -Modificaciones_En_EE_Formularios_Controlados
        // Se mostrara la caratula generada solo despues de haber
        // completado el formulario controlado.
        this.esEE = true;
        mostrarCaratulaGenerada(expedienteElectronico);
      }
    } catch (DynFormException de) {
      logger.error("Se ha producido un error generando el documento.", de);
      Messagebox.show(Labels.getLabel("ee.caratularExtComp.msgbox.errorGenDoc"),
          Labels.getLabel("ee.general.error"), Messagebox.OK, Messagebox.ERROR);
    } catch (SQLGrammarException de) {
      logger.error("Se ha producido un error generando el documento.", de);
      Messagebox.show(Labels.getLabel("ee.caratularExtComp.msgbox.errorGenDoc"),
          Labels.getLabel("ee.general.error"), Messagebox.OK, Messagebox.ERROR);
    } catch (WrongValueException e) {
      logger.error(e.getMessage(), e);
      Messagebox.show(e.getMessage(), Labels.getLabel("ee.general.error"), Messagebox.OK,
          Messagebox.ERROR);
    } catch (RemoteAccessException e) {
      logger.error(e.getMessage(), e);
      Messagebox.show(e.getMessage(), Labels.getLabel("ee.general.advertencia"), Messagebox.OK,
          Messagebox.ERROR);
    } catch (Exception e) {
      String error = Labels.getLabel("ee.servicio.gedo.errorComunicacionGEDO");
      logger.error(error, e);
      Messagebox.show(error, Labels.getLabel("ee.general.error"), Messagebox.OK, Messagebox.ERROR);
    } finally {
      Clients.clearBusy();
      closeAssociatedWindow();
    }
  }

  public Object getVariableWorkFlow(String name) {
    Object obj = this.processEngine.getExecutionService()
        .getVariable(this.workingTask.getExecutionId(), name);

    if (obj == null) {
      throw new VariableWorkFlowNoExisteException("No existe la variable para el id de ejecucion. "
          + this.workingTask.getExecutionId() + ", " + name, null);
    }

    return obj;
  }

  public void onCancelar() {
    closeAssociatedWindow();
  }

  public void signalExecution(String nameTransition, String usernameDerivador) {
    Map<String, Object> variables = new HashMap<String, Object>();
    variables.put(ConstantesWeb.USUARIO_SOLICITANTE, usernameDerivador);
    variables.put(ConstantesWeb.USUARIO_SELECCIONADO, usernameDerivador);
    variables.put(ConstantesWeb.GRUPO_SELECCIONADO, null);
    setVariablesWorkFlow(variables);
    processEngine.getExecutionService().signalExecutionById(this.workingTask.getExecutionId(),
        nameTransition);
  }

  public void mostrarCaratulaGenerada(ExpedienteElectronicoDTO expedienteElectronico)
      throws InterruptedException {
    Messagebox.show(
        Labels.getLabel("ee.caratularExtComp.msgbox.seGeneroExp") + " "
            + expedienteElectronico.getCodigoCaratula(),
        Labels.getLabel("ee.tramitacion.titulo.nuevoExpediente"), Messagebox.OK,
        Messagebox.INFORMATION);
  }

  public void onChanging$codigoTrata(InputEvent e) {
    this.cargarTratas(e);
  }

  public void cargarTratas(InputEvent e) {
    String matchingText = e.getValue();
    this.tratasSeleccionadas.clear();

    if (!matchingText.equals("") && (matchingText.length() >= 3)) {
      if (this.tratas != null) {
        matchingText = matchingText.toUpperCase();

        Iterator<TrataDTO> iterator = this.tratas.iterator();
        TrataDTO trata = null;

        while ((iterator.hasNext())) {
          trata = iterator.next();

          if ((trata != null)) {
            if ((trata.getCodigoTrata().toUpperCase().trim().contains(matchingText.trim())
                || trataService.obtenerDescripcionTrataByCodigo(trata.getCodigoTrata())
                    .toUpperCase().trim().contains(matchingText.trim()))) {
              this.tratasSeleccionadas.add(trata);
            }
          }
        }
      }
    } else if (matchingText.trim().equals("")) {
      this.tratasSeleccionadas = new ArrayList<>(this.tratas);
    }

    this.binder.loadAll();
  }


  private void mostrarForegroundBloqueanteToken() {
    Clients.showBusy(Labels.getLabel("ee.nuevoexpediente.popup.label.caratularInterno.value"));
  }

  /**
   * Valida lo datos requeridos para generar el Expediente.
   */
  private void validarDatosDelFormulario() {

    if ((getMotivoExternoExpediente().getValue() == null)
        || (getMotivoExternoExpediente().getValue().trim().equals(""))) {
      getMotivoExternoExpediente().focus();
      throw new WrongValueException(getMotivoExternoExpediente(),
          "Debe ingresar un motivo");
    }

    verificarExistencia();

    if ((this.trataSel == null) || this.trataSel.trim().equals("")) {
      this.codigoTrata.focus();
      throw new WrongValueException(this.codigoTrata,
          Labels.getLabel("ee.nuevoexpediente.faltacodigotrata"));
    } else {
      if (!this.existe) {
        throw new WrongValueException(this.codigoTrata,
            Labels.getLabel("ee.caratulas.caratulaNoExiste"));
      }
    }

    if ((this.codigoTrata == null) || (this.codigoTrata.getValue() == "")
        || this.codigoTrata.getValue().isEmpty()) {
      throw new WrongValueException(this.codigoTrata, "Debe seleccionar una Trata válida.");
    }

    if (this.codigoTrata == null
        && this.trataService.buscarTrataByCodigo(this.selectedTrata.getCodigoTrata()) == null) {
      throw new WrongValueException(this.codigoTrata,
          Labels.getLabel("ee.nuevoexpediente.tratainactiva"));
    }

    TrataDTO trata = trataService.buscarTrataByCodigo(this.codigoTrata.getValue());
    if (!trata.getEsInterno()) {
      throw new WrongValueException(this.codigoTrata, "La trata \"" + trata.getCodigoTrata()
          + "\" tiene que caratularse como Carátula Interna.");
    }

    if ((this.descripcion.getValue() == null) || (this.descripcion.getValue().equals(""))) {
      this.descripcion.focus();
      throw new WrongValueException(this.descripcion,
          Labels.getLabel("ee.nuevoexpediente.faltadescripciontrata"));
    }

    if ((getEmail().getValue() == null) || (getEmail().getValue().equals(""))) {
    } else {
      boolean valor = MailUtil.validateMail(getEmail().getValue().toString());

      if (valor == false) {
        getEmail().focus();
        throw new WrongValueException(getEmail(), Labels.getLabel("ee.nuevasolicitud.erroremail"));
      }
    }

    if (!this.telefono.getValue().equals("")) {
      boolean valor = ValidacionesUtils.poseeSoloNumeros(this.telefono.getValue());

      if (!valor) {
        throw new WrongValueException(this.telefono,
            Labels.getLabel("ee.nuevasolicitud.errorTelefono"));
      }
    }
  }

  private void verificarExistencia() {
    this.existe = false;
    if (!(this.codigoTrata == null) || (this.codigoTrata.getValue() == "")
        || this.codigoTrata.getValue().isEmpty()) {
      for (TrataDTO aux : this.tratas) {
        if (aux.getCodigoTrata().toUpperCase().trim().equals(this.codigoTrata.getValue())) {
          this.existe = true;
          break;
        }
      }
    }
  }

  public ProcessEngine getProcessEngine() {
    return processEngine;
  }

  public void setProcessEngine(ProcessEngine processEngine) {
    this.processEngine = processEngine;
  }

  public ExpedienteElectronicoService getExpedienteElectronicoService() {
    return expedienteElectronicoService;
  }

  public void setExpedienteElectronicoService(
      ExpedienteElectronicoService expedienteElectronicoService) {
    this.expedienteElectronicoService = expedienteElectronicoService;
  }

  public SolicitudExpedienteService getSolicitudExpedienteService() {
    return solicitudExpedienteService;
  }

  public void setSolicitudExpedienteService(
      SolicitudExpedienteService solicitudExpedienteService) {
    this.solicitudExpedienteService = solicitudExpedienteService;
  }

  public Textbox getMotivoInternoExpediente() {
    return motivoInternoExpediente;
  }

  public void setMotivoInternoExpediente(Textbox motivoInternoExpediente) {
    this.motivoInternoExpediente = motivoInternoExpediente;
  }

  public Textbox getMotivoExternoExpediente() {
    return motivoExternoExpediente;
  }

  public void setMotivoExternoExpediente(Textbox motivoExternoExpediente) {
    this.motivoExternoExpediente = motivoExternoExpediente;
  }

  public Textbox getEmail() {
    return email;
  }

  public void setEmail(Textbox email) {
    this.email = email;
  }

  public Textbox getTelefono() {
    return telefono;
  }

  public void setTelefono(Textbox telefono) {
    this.telefono = telefono;
  }

  public Textbox getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(Textbox descripcion) {
    this.descripcion = descripcion;
  }

  public List<TrataDTO> getTratas() {
    return tratas;
  }

  public List<TrataDTO> getTratasServices() {
    //return this.trataService.buscarTratasManuales(true); OLD
    return this.trataService.buscarTratasByTipoExpediente(true);
  }

  public TrataDTO getSelectedTrata() {
    return selectedTrata;
  }

  public void setSelectedTrata(TrataDTO selectedTrata) {
    this.selectedTrata = selectedTrata;
  }

  public TrataService getTrataService() {
    return trataService;
  }

  public void setTrataService(TrataService trataService) {
    this.trataService = trataService;
  }

  public SolicitudExpedienteDTO getSolicitud() {
    return solicitud;
  }

  public void setSolicitud(SolicitudExpedienteDTO solicitud) {
    this.solicitud = solicitud;
  }

  public Window getCaratularInternoWindow() {
    return caratularInternoWindow;
  }

  public void setCaratularInternoWindow(Window caratularInternoWindow) {
    this.caratularInternoWindow = caratularInternoWindow;
  }

  public void setWorkingTask(Task workingTask) {
    this.workingTask = workingTask;
  }

  public Task getWorkingTask() {
    return workingTask;
  }

  @Deprecated
  public void setVariableWorkFlow(String name, Object value) {
    this.processEngine.getExecutionService().setVariable(this.workingTask.getExecutionId(), name,
        value);
  }

  public void setVariablesWorkFlow(Map<String, Object> variables) {
    workFlowService.setVariables(processEngine, this.workingTask.getExecutionId(), variables);
  }

  public CaratulacionService getCaratulador() {
    return caratulador;
  }

  public void setCaratulador(CaratulacionService caratulador) {
    this.caratulador = caratulador;
  }

  public HistorialOperacionService getHistorialOperacionService() {
    return historialOperacionService;
  }

  public void setHistorialOperacionService(HistorialOperacionService historialOperacionService) {
    this.historialOperacionService = historialOperacionService;
  }

  public void setTratas(List<TrataDTO> tratas) {
    this.tratas = tratas;
  }

  public List<TrataDTO> getTratasSeleccionadas() {
    return tratasSeleccionadas;
  }

  public void setTratasSeleccionadas(List<TrataDTO> tratasSeleccionadas) {
    this.tratasSeleccionadas = tratasSeleccionadas;
  }

  public Listbox getTrataListbox() {
    return trataListbox;
  }

  public void setTrataListbox(Listbox trataListbox) {
    this.trataListbox = trataListbox;
  }

  public String getTrataSel() {
    return trataSel;
  }

  public void setTrataSel(String trataSel) {
    this.trataSel = trataSel;
  }

  public void setGuardar(Button guardar) {
    this.guardar = guardar;
  }

  public Button getGuardar() {
    return guardar;
  }

  public void logger(String message, String toString) {
    logger.debug(" ThreadId=" + Thread.currentThread().getId() + " toString " + toString + " "
        + message + " ");
  }

  // SETTERS & GETTERS - END

}
