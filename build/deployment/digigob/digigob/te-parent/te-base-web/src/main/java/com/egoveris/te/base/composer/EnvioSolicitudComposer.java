package com.egoveris.te.base.composer;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jbpm.api.ProcessEngine;
import org.jbpm.api.task.Participation;
import org.jbpm.api.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.bind.BindUtils;
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
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Include;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.ListModels;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.egoveris.sharedorganismo.base.model.ReparticionBean;
import com.egoveris.sharedorganismo.base.model.SectorInternoBean;
import com.egoveris.sharedorganismo.base.service.ReparticionServ;
import com.egoveris.sharedorganismo.base.service.SectorInternoServ;
import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.sharedsecurity.base.model.UsuarioReducido;
import com.egoveris.te.base.composer.EnvioComposer.EnvioExpedienteElectronicoOnNotifyWindowListener;
import com.egoveris.te.base.composer.genrico.BandBoxUsuarioComposer;
import com.egoveris.te.base.model.HistorialOperacionDTO;
import com.egoveris.te.base.model.SolicitudExpedienteDTO;
import com.egoveris.te.base.model.TrataDTO;
import com.egoveris.te.base.service.HistorialOperacionService;
import com.egoveris.te.base.service.SolicitudExpedienteService;
import com.egoveris.te.base.service.TrataService;
import com.egoveris.te.base.service.UsuariosSADEService;
import com.egoveris.te.base.util.ConstantesServicios;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.vm.SolicitanteDireccionVM;



@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class EnvioSolicitudComposer extends EEGenericForwardComposer {

  private final static Logger logger = LoggerFactory.getLogger(EnvioSolicitudComposer.class);

  /**
  * 
  */
  private static final long serialVersionUID = 3654975340948367227L;
  final static String CARATULADOR_EXTERNO = "SADE.EXTERNOS";
  final static String CARATULADOR_INTERNO = "SADE.INTERNOS";
  private Window envioSolicitudWindow;
  private Textbox motivoSolicitud;
  private Combobox estado;
  //private Combobox usuario;
  @SuppressWarnings("unused")
  private List<Usuario> usuarios;
  private Combobox reparticion;
  private List<?> reparticiones;
  private String selectedReparticiones;
  private List<?> sectores;
  private SectorInternoBean selectedSectores;
  private Combobox reparticionSector;
  private List<?> reparticionesSector;
  private String selectedReparticionesSector;
  private Radio usuarioRadio;
  private Radio sectorRadio;
  private Radio reparticionRadio;
  private Bandbox reparticionBusquedaSADE;
  private Bandbox sectorReparticionBusquedaSADE;
  private AnnotateDataBinder binder;
  private Window nuevaSolicitudWindow;
  private Grid grillaDestinoSolicitud;
  private Bandbox sectorBusquedaSADE;
  
  private Include inclBandboxUsuario;
  
  private List<ReparticionBean> listaSectorReparticionSADESeleccionada;
  @Autowired
  private Listbox sectoresReparticionesBusquedaSADEListbox;
  @WireVariable(ConstantesServicios.PROCESS_ENGINE_SERVICE)
  private ProcessEngine processEngine;
  protected Task workingTask = null;

  private String usuarioApoderado = null;
  private HistorialOperacionDTO historialOperacion;
  private Map<String, Object> variables;
  private String loggedUsername;
  
  private UsuarioReducido usuarioSeleccionado;

  private SectorInternoServ sectorInternoServ; 

  /**
   * Defino los servicios que voy a utilizar
   */
  @WireVariable(ConstantesServicios.USUARIOS_SADE_SERVICE)
  private UsuariosSADEService usuariosSADEService;
  @WireVariable(ConstantesServicios.HISTORIAL_OPERACION_SERVICE)
  private HistorialOperacionService historialOperacionService;
  private SolicitudExpedienteDTO solicitud;
  @WireVariable(ConstantesServicios.SOLICITUD_EXPEDIENTE_SERVICE)
  private SolicitudExpedienteService solicitudExpedienteService; 
  @WireVariable(ConstantesServicios.REPARTICION_SERVICE)
  private ReparticionServ reparticionServ;
  @WireVariable(ConstantesServicios.TRATA_SERVICE)
  private TrataService trataService;

  public Window getEnvioSolicitudWindow() {
    return envioSolicitudWindow;
  }

  public void setEnvioSolicitudWindow(Window envioSolicitudWindow) {
    this.envioSolicitudWindow = envioSolicitudWindow;
  }

  public Textbox getMotivoSolicitud() {
    return motivoSolicitud;
  }

  public void setMotivoSolicitud(Textbox motivoSolicitud) {
    this.motivoSolicitud = motivoSolicitud;
  }

  public Combobox getEstado() {
    return estado;
  }

  public void setEstado(Combobox estado) {
    this.estado = estado;
  }

//  public Combobox getUsuario() {
//    return usuario;
//  }
//
//  public void setUsuario(Combobox usuario) {
//    this.usuario = usuario;
//  }

  public List<Usuario> getUsuarios() {
    return getUsuariosCaratuladores();
  }

  public UsuarioReducido getUsuarioSeleccionado() {
		return usuarioSeleccionado;
	}

	public void setUsuarioSeleccionado(UsuarioReducido usuarioSeleccionado) {
		this.usuarioSeleccionado = usuarioSeleccionado;
	}

	private List<Usuario> getUsuariosCaratuladores() {
    try {
      return this.usuariosSADEService.getTodosLosUsuarios();
    } catch (SecurityNegocioException e) {
      logger.error(e.getMessage());
      throw new WrongValueException(Labels.getLabel("ee.error.usuario.label.invalidos"));
    }
  }

  public void setUsuarios(List<Usuario> usuarios) {
    this.usuarios = usuarios;
  }

  public Combobox getReparticion() {
    return reparticion;
  }

  public void setReparticion(Combobox reparticion) {
    this.reparticion = reparticion;
  }

  public List<?> getReparticiones() {
    return reparticiones;
  }

  public void setReparticiones(List<?> reparticiones) {
    this.reparticiones = reparticiones;
  }

  public String getSelectedReparticiones() {
    return selectedReparticiones;
  }

  public void setSelectedReparticiones(String selectedReparticiones) {
    this.selectedReparticiones = selectedReparticiones;
  }

  public List<?> getSectores() {
    return sectores;
  }

  public void setSectores(List<?> sectores) {
    this.sectores = sectores;
  }

  public SectorInternoBean getSelectedSectores() {
    return selectedSectores;
  }

  public void setSelectedSectores(SectorInternoBean selectedSectores) {
    this.selectedSectores = selectedSectores;
  }

  public Combobox getReparticionSector() {
    return reparticionSector;
  }

  public void setReparticionSector(Combobox reparticionSector) {
    this.reparticionSector = reparticionSector;
  }

  public List<?> getReparticionesSector() {
    return reparticionesSector;
  }

  public void setReparticionesSector(List<?> reparticionesSector) {
    this.reparticionesSector = reparticionesSector;
  }

  public String getSelectedReparticionesSector() {
    return selectedReparticionesSector;
  }

  public void setSelectedReparticionesSector(String selectedReparticionesSector) {
    this.selectedReparticionesSector = selectedReparticionesSector;
  }

  public Radio getUsuarioRadio() {
    return usuarioRadio;
  }

  public void setUsuarioRadio(Radio usuarioRadio) {
    this.usuarioRadio = usuarioRadio;
  }

  public Radio getSectorRadio() {
    return sectorRadio;
  }

  public void setSectorRadio(Radio sectorRadio) {
    this.sectorRadio = sectorRadio;
  }

  public Radio getReparticionRadio() {
    return reparticionRadio;
  }

  public void setReparticionRadio(Radio reparticionRadio) {
    this.reparticionRadio = reparticionRadio;
  }

  public Bandbox getReparticionBusquedaSADE() {
    return reparticionBusquedaSADE;
  }

  public void setReparticionBusquedaSADE(Bandbox reparticionBusquedaSADE) {
    this.reparticionBusquedaSADE = reparticionBusquedaSADE;
  }

  public Bandbox getSectorReparticionBusquedaSADE() {
    return sectorReparticionBusquedaSADE;
  }

  public void setSectorReparticionBusquedaSADE(Bandbox sectorReparticionBusquedaSADE) {
    this.sectorReparticionBusquedaSADE = sectorReparticionBusquedaSADE;
  }

  public AnnotateDataBinder getBinder() {
    return binder;
  }

  public void setBinder(AnnotateDataBinder binder) {
    this.binder = binder;
  }

  public UsuariosSADEService getUsuariosSADEService() {
    return usuariosSADEService;
  }

  public void setUsuariosSADEService(UsuariosSADEService usuariosSADEService) {
    this.usuariosSADEService = usuariosSADEService;
  }

  public ProcessEngine getProcessEngine() {
    return processEngine;
  }

  public void setProcessEngine(ProcessEngine processEngine) {
    this.processEngine = processEngine;
  }

  public Task getWorkingTask() {
    return workingTask;
  }

  public void setWorkingTask(Task workingTask) {
    this.workingTask = workingTask;
  }

  public Listbox getSectoresReparticionesBusquedaSADEListbox() {
    return sectoresReparticionesBusquedaSADEListbox;
  }

  public void setSectoresReparticionesBusquedaSADEListbox(
      Listbox sectoresReparticionesBusquedaSADEListbox) {
    this.sectoresReparticionesBusquedaSADEListbox = sectoresReparticionesBusquedaSADEListbox;
  }

  public SolicitudExpedienteDTO getSolicitud() {
    return solicitud;
  }

  public void setSolicitud(SolicitudExpedienteDTO solicitud) {
    this.solicitud = solicitud;
  }

  public SolicitudExpedienteService getSolicitudExpedienteService() {
    return solicitudExpedienteService;
  }

  public void setSolicitudExpedienteService(
      SolicitudExpedienteService solicitudExpedienteService) {
    this.solicitudExpedienteService = solicitudExpedienteService;
  }

  @SuppressWarnings("unchecked")
  public void doAfterCompose(Component component) throws Exception {
    super.doAfterCompose(component);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
 
    this.usuarioRadio.setSelected(false);
    this.sectorRadio.setSelected(false);
    this.reparticionRadio.setSelected(false);
    
		configurarBandboxUsuario(component, true);

    //this.usuario.setDisabled(true);
    
    this.sectorBusquedaSADE.setDisabled(true);
    this.reparticionBusquedaSADE.setDisabled(true);
    this.sectorReparticionBusquedaSADE.setDisabled(true);
    this.motivoSolicitud.setReadonly(true);

    variables = (Map<String, Object>) this.execution.getDesktop().getAttribute("variables");
    this.solicitud = (SolicitudExpedienteDTO) variables.get("solicitud");

    if (!this.solicitud.getMotivo().isEmpty()) {
      this.motivoSolicitud.setValue(this.solicitud.getMotivo());
    } else {
      this.motivoSolicitud.setValue(this.solicitud.getMotivoExterno());
    }
//    usuario.setModel(ListModels.toListSubModel(
//        new ListModelList(this.getUsuariosSADEService().getTodosLosUsuarios()),
//        new UsuariosComparatorConsultaExpediente(), 30));
  }

  private void configurarBandboxUsuario(Component component, boolean deshabilitado) {

    inclBandboxUsuario.setDynamicProperty(BandBoxUsuarioComposer.DISABLED_BANDBOX, deshabilitado);
    inclBandboxUsuario.setDynamicProperty(BandBoxUsuarioComposer.COMPONENTE_PADRE, component);
    inclBandboxUsuario.setSrc(BandBoxUsuarioComposer.SRC);
    
		component.addEventListener(BandBoxUsuarioComposer.ON_SELECT_USUARIO,
				new EnvioSolicitudOnNotifyWindowListener());


	}
  
	private void disabledBandbox(Boolean disabled) {
		Events.sendEvent(BandBoxUsuarioComposer.EVENT_DISABLED, inclBandboxUsuario.getChildren().get(0), disabled);
	}
	
	private void cleanBandboxUsuario() {
		Events.sendEvent(BandBoxUsuarioComposer.EVENT_CLEAN, inclBandboxUsuario.getChildren().get(0), null);
	}
	
	private void mensajeValidadorBandboxUsuario(String mensaje) {
		Events.sendEvent(BandBoxUsuarioComposer.EVENT_VALIDAR, inclBandboxUsuario.getChildren().get(0), mensaje);
	}

	public void onEnviar() throws InterruptedException {

    if (!this.usuarioRadio.isChecked() && !this.sectorRadio.isChecked()) {
      throw new WrongValueException(this.grillaDestinoSolicitud,
          "Debe seleccionar un destino para hacer el envío.");
    }

    loggedUsername = (String) Executions.getCurrent().getDesktop().getSession()
        .getAttribute(ConstantesWeb.SESSION_USERNAME);
    historialOperacion = new HistorialOperacionDTO();
    String caratulador = new String();
    if (!this.solicitud.isEsSolicitudInterna()) {
      caratulador = CARATULADOR_EXTERNO;
    } else if (this.solicitud.isEsSolicitudInterna()) {
      caratulador = CARATULADOR_INTERNO;
    }
    /**
     * Carga de datos en el historial
     */
    historialOperacion.setTipoOperacion("SOLICITUD");
    historialOperacion.setFechaOperacion(new Date());
    historialOperacion.setUsuario(loggedUsername);

    if (this.usuarioRadio.isChecked()) {
      if (usuarioSeleccionado == null) {
      	this.mensajeValidadorBandboxUsuario("Debe seleccionar un Usuario.");
        //throw new WrongValueException(this.usuario, "Debe seleccionar un Usuario.");
      }
      final Usuario usuarioProductorInfo = usuariosSADEService.getDatosUsuario(usuarioSeleccionado.getUsername());
      if (usuarioProductorInfo == null) {
      	this.mensajeValidadorBandboxUsuario("Debe seleccionar un usuario válido.");
      	// throw new WrongValueException(this.usuario, "Debe seleccionar un usuario válido.");
      }
      if (!this.solicitud.isEsSolicitudInterna()) {
        if (!usuariosSADEService.usuarioTieneRol(usuarioProductorInfo.getUsername(),
            (CARATULADOR_EXTERNO))) {
          throw new WrongValueException(
              "El usuario seleccionado no tiene permiso de caratulador externo.");
        }
      } else if (this.solicitud.isEsSolicitudInterna()) {
        if (!usuariosSADEService.usuarioTieneRol(usuarioProductorInfo.getUsername(),
            CARATULADOR_INTERNO)) {
          throw new WrongValueException(
              "El usuario seleccionado no tiene permiso de caratulador interno.");
        }
      }

      // VALIDO SI EL USUARIO QUE REALIZO LA SOLICITUD ESTA DE VACACIONES
      if (usuariosSADEService.licenciaActiva(usuarioProductorInfo.getUsername())) {
        this.usuarioApoderado = this.validarLicencia(usuarioProductorInfo.getUsername());
      }
      if (this.usuarioApoderado != null) {

        Messagebox.show(
            Labels.getLabel("ee.licencia.question.value",
                new String[] { usuarioProductorInfo.getUsername(), this.usuarioApoderado }),
            Labels.getLabel("ee.licencia.question.title"), Messagebox.YES | Messagebox.NO,
            Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
              public void onEvent(Event evt) throws InterruptedException {
                switch (((Integer) evt.getData()).intValue()) {
                case Messagebox.YES:
                  variables.put("usuarioSeleccionado", usuarioApoderado);
                  variables.put("grupoSeleccionado", null);
                  variables.put("tareaGrupal", "noEsTareaGrupal");
                  variables.put("destinatario", usuarioApoderado);
                  variables.put("usuarioLicenciado", usuarioProductorInfo.getUsername());
                  continuarEnvioSolicitud(loggedUsername, historialOperacion, variables);
                  break;
                case Messagebox.NO:
                  alert("Seleccione otro destino.");
                  break;
                }
              }
            });
      } else {
        variables.put("usuarioSeleccionado", usuarioProductorInfo.getUsername());
        variables.put("grupoSeleccionado", null);
        variables.put("tareaGrupal", "noEsTareaGrupal");
        variables.put("destinatario", usuarioApoderado);
        continuarEnvioSolicitud(loggedUsername, historialOperacion, variables);
      }
    } else if (this.sectorRadio.isChecked()) {
      if (this.sectorReparticionBusquedaSADE == null
          || this.sectorReparticionBusquedaSADE.getValue() == ""
          || this.sectorReparticionBusquedaSADE.getValue().isEmpty())
        throw new WrongValueException(this.sectorReparticionBusquedaSADE,
            "Debe seleccionar una Repartición válida.");
      if (this.sectorBusquedaSADE == null || this.sectorBusquedaSADE.getValue() == ""
          || this.sectorBusquedaSADE.getValue().isEmpty())
        throw new WrongValueException(this.sectorBusquedaSADE,
            "Debe seleccionar un Sector Interno válido.");

      if (!this.solicitud.isEsSolicitudInterna()) {
        if (!this.usuariosSADEService.hasUsuariosCaratuladoresExternosXReparticionYSector(
            this.sectorReparticionBusquedaSADE.getValue().trim(),
            this.sectorBusquedaSADE.getValue().trim())) {
          throw new WrongValueException(Labels.getLabel("ee.nuevasolicitud.error.sectorPermisoExterno"));
        }
      } else if (this.solicitud.isEsSolicitudInterna()) {
        if (!this.usuariosSADEService.hasUsuariosCaratuladoresInternosXReparticionYSector(
            this.sectorReparticionBusquedaSADE.getValue().trim(),
            this.sectorBusquedaSADE.getValue().trim())) {
          throw new WrongValueException(Labels.getLabel("ee.nuevasolicitud.error.sectorPermisoInterno"));
        }
      }
      variables.put("grupoSeleccionado", this.sectorReparticionBusquedaSADE.getValue().trim() + "-"
          + this.sectorBusquedaSADE.getValue().trim() + "-" + caratulador);
      variables.put("usuarioSeleccionado", null);
      variables.put("tareaGrupal", "esTareaGrupal");
      variables.put("destinatario", this.sectorBusquedaSADE.getValue());
      continuarEnvioSolicitud(loggedUsername, historialOperacion, variables);
    } else if (this.reparticionRadio.isChecked()) {
      variables.put("grupoSeleccionado",
          this.reparticionBusquedaSADE.getValue().trim() + "-" + caratulador);
      variables.put("usuarioSeleccionado", null);
      variables.put("tareaGrupal", "esTareaGrupal");
      variables.put("destinatario", this.reparticionBusquedaSADE.getValue());
      continuarEnvioSolicitud(loggedUsername, historialOperacion, variables);
    }

    // ***********************************************************************
    // ** JIRA BISADE-2515: https://quark.everis.com/jira/browse/BISADE-2515
    // ** - Se crea el parámetro "cacheQueries" para la consulta de inbox no
    // cachee cambios de statemachine.
    // ***********************************************************************
    Executions.getCurrent().getDesktop().setAttribute("cacheQueries", new Boolean(false));
  }

  /**
   * @param loggedUsername
   * @param historialOperacion
   * @param variables
   * @throws WrongValueException
   */
  private void continuarEnvioSolicitud(String loggedUsername,
      HistorialOperacionDTO historialOperacion, Map<String, Object> variables)
      throws WrongValueException {
    Map<String, String> detalles = new HashMap<String, String>();
    detalles.put("destinatario", (String) variables.get("destinatario"));
    detalles.put("motivo", this.motivoSolicitud.getValue());
    detalles.put("tipoOperacion", "SOLICITUD");

    /**
     * Guardo el usuario Actual para que quede como usuario Anterior al comenzar
     * el WF.
     */
    variables.put("usuarioAnterior", loggedUsername);

    historialOperacion.setDetalleOperacion(detalles);

    /**
     */
    solicitudExpedienteService.grabarSolicitud(this.solicitud, historialOperacion, false);
    historialOperacion.setIdSolicitud(this.solicitud.getId());
    solicitudExpedienteService.grabarSolicitud(this.solicitud, historialOperacion, true);

    variables.remove("solicitud");
    variables.put("idSolicitud", this.solicitud.getId());
    variables.put("motivo", this.motivoSolicitud.getValue());
    variables.put("inicio", "Iniciar Expediente");

    TrataDTO trata = trataService.buscarTrataporId(this.solicitud.getIdTrataSugerida());
    
    getProcessEngine().getExecutionService().startProcessInstanceByKey("solicitud", variables);
    
    // Save direccion
    SolicitudExpedienteDTO solicitudExpedienteDTO = solicitudExpedienteService.obtenerSolitudByIdSolicitud(this.solicitud.getId());
    SolicitanteDireccionVM.saveDireccion(solicitudExpedienteDTO.getSolicitante().getId());
    this.closeAssociatedWindow();
  }

  public void onCancelar() {
    this.envioSolicitudWindow.detach();
  }

  /**
   * Se habilitan y deshabilitan los campos según que opción se eliga.
   */
  public void onUsuarioClick() {
  	this.disabledBandbox(false);
    //this.usuario.setDisabled(false);
    this.sectorBusquedaSADE.setDisabled(true);
    this.sectorBusquedaSADE.setValue(null);
    this.sectorRadio.setChecked(false);
    this.sectorReparticionBusquedaSADE.setDisabled(true);
    this.sectorReparticionBusquedaSADE.setValue(null);
    this.reparticionRadio.setChecked(false);
    this.reparticionBusquedaSADE.setDisabled(true);
    this.reparticionBusquedaSADE.setValue(null);
  }

  public void onSectorClick() {
    this.sectorBusquedaSADE.setDisabled(true);
    this.disabledBandbox(true);
    this.cleanBandboxUsuario();
//    this.usuario.setDisabled(true);
//    this.usuario.setValue(null);
    this.usuarioRadio.setChecked(false);
    this.sectorReparticionBusquedaSADE.setDisabled(false);
    this.reparticionRadio.setChecked(false);
    this.reparticionBusquedaSADE.setDisabled(true);
  }

  public void onReparticionClick() {
    this.reparticionBusquedaSADE.setDisabled(false);
    this.sectorBusquedaSADE.setDisabled(true);
    this.sectorBusquedaSADE.setValue(null);
    this.sectorRadio.setChecked(false);
    this.sectorReparticionBusquedaSADE.setDisabled(true);
    this.sectorReparticionBusquedaSADE.setValue(null);
    this.disabledBandbox(true);
    this.cleanBandboxUsuario();
//    this.usuario.setDisabled(true);
//    this.usuario.setValue(null);
    this.usuarioRadio.setChecked(false);
  }

  /**
   * Cuando selecciono una Repartición habilito el combo de sectores y cargo los
   * sectores pertenecientes a esa Repartición
   */
  public void onOpen$sector() {
    if (this.sectores == null) {
      String reparticion = sectorReparticionBusquedaSADE.getValue();
      ReparticionBean rsb = this.reparticionServ.buscarReparticionPorCodigo(reparticion);
      this.sectores = sectorInternoServ.buscarSectoresPorReparticionOrderByMesa(rsb.getId());
      this.binder.loadComponent(this.sectorBusquedaSADE);
    }
  }

  /**
   * 
   * @param name
   *          : nombre de la variable que quiero setear
   * @param value
   *          : valor de la variable
   */
  public void setVariableWorkFlow(String name, Object value) {
    this.processEngine.getExecutionService().setVariable(this.workingTask.getExecutionId(), name,
        value);
  }

  /**
   * 
   * @param nameTransition
   *          : nombre de la transición que voy a usar para la proxima tarea
   * @param usernameDerivador
   *          : usuario que va a tener asignada la tarea
   */
  public void signalExecution(String nameTransition, String usernameDerivador) {
    processEngine.getExecutionService().signalExecutionById(this.workingTask.getExecutionId(),
        nameTransition);
  }

  public Set<String> extractParticipatingGroupIds(List<Participation> taskParticipations,
      String participationType) {
    Set<String> groupIds = new HashSet<String>();
    for (Participation participation : taskParticipations) {
      if (participationType.equals(participation.getType())) {
        if (participation.getGroupId() != null) {
          groupIds.add(participation.getGroupId());
        }
      }
    }
    return groupIds;
  }

  public void setNuevaSolicitudWindow(Window nuevaSolicitudWindow) {
    this.nuevaSolicitudWindow = nuevaSolicitudWindow;
  }

  public Window getNuevaSolicitudWindow() {
    return nuevaSolicitudWindow;
  }

  public Grid getGrillaDestinoSolicitud() {
    return grillaDestinoSolicitud;
  }

  public void setGrillaDestinoSolicitud(Grid grillaDestinoSolicitud) {
    this.grillaDestinoSolicitud = grillaDestinoSolicitud;
  }

  private String validarLicencia(String username) {
    String usuarioApoderado = this.usuariosSADEService.getDatosUsuario(username).getApoderado();
    while (usuarioApoderado != null) {
      String apoderado = this.usuariosSADEService.getDatosUsuario(usuarioApoderado).getApoderado();
      if (null == apoderado) {
        return usuarioApoderado;
      } else {
        usuarioApoderado = apoderado;
      }
    }
    return null;
  }

  public void setHistorialOperacionService(HistorialOperacionService historialOperacionService) {
    this.historialOperacionService = historialOperacionService;
  }

  public HistorialOperacionService getHistorialOperacionService() {
    return historialOperacionService;
  }
  
  public class EnvioSolicitudOnNotifyWindowListener implements EventListener<Event>{

		@Override
		public void onEvent(Event event) throws Exception {

			if(event.getName().equals(BandBoxUsuarioComposer.ON_SELECT_USUARIO)) {
				if(event.getData()!=null) {					
					setUsuarioSeleccionado((UsuarioReducido) event.getData());
				}else {
					setUsuarioSeleccionado(null);
				}
			}
		}
  	
  }

}
