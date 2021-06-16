package com.egoveris.te.base.composer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.task.Participation;
import org.jbpm.api.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.remoting.RemoteAccessException;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.databind.BindingListModelList;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Row;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.egoveris.sharedorganismo.base.model.ReparticionBean;
import com.egoveris.sharedorganismo.base.model.SectorInternoBean;
import com.egoveris.sharedorganismo.base.service.SectorInternoServ;
import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.sharedsecurity.base.model.UsuarioReducido;
import com.egoveris.te.base.composer.genrico.BandBoxUsuarioComposer;
import com.egoveris.te.base.exception.VariableWorkFlowNoExisteException;
import com.egoveris.te.base.model.DatosEnvioParalelo;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.ParametrosSistemaExternoDTO;
import com.egoveris.te.base.model.TrataDTO;
import com.egoveris.te.base.service.UsuariosSADEService;
import com.egoveris.te.base.service.WorkFlowService;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.util.ConfiguracionInicialModuloEEFactory;
import com.egoveris.te.base.util.ConstantesServicios;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.ZkUtil;


@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class EnvioParaleloComposer extends EnvioExpedienteComposer {

  private static final long serialVersionUID = 6266287038479709703L;

  @Autowired
  private Window envio;

  @Autowired
  private Textbox estado;
//  @Autowired
//  private Combobox usuario;
  
  private Include inclBandboxUsuario;
  
  @Autowired
  private Combobox reparticion;
  @Autowired
  private Combobox sector;
  @Autowired
  private Combobox reparticionSector;
  @Autowired
  private Bandbox sectorReparticionBusquedaSADE;
  @Autowired
  private AnnotateDataBinder binder;
  @Autowired
  private Listbox sectorReparticionListbox;
  @Autowired
  private Listbox UsuariosListbox;

  @Autowired
  private Grid grillaDestino;
  @Autowired
  private Bandbox sectorBusquedaSADE;
  private ExpedienteElectronicoDTO expedienteElectronico;
  @Autowired
  private Label destino;
  private List<DatosEnvioParalelo> usuariosDestinatarios = new ArrayList<>();
  private List<DatosEnvioParalelo> reparticionesDestinatarios = new ArrayList<>();
  private DatosEnvioParalelo usuariosAgregadoSelected;
  private DatosEnvioParalelo reparticionesAgregadasSelected;
  
  private UsuarioReducido usuarioSeleccionado;
  
  private String estadoSeleccionado = null;
  private String loggedUsername;
  private SectorInternoBean sectorMesaVirtual;

  @WireVariable(ConstantesServicios.PROCESS_ENGINE_SERVICE)
  private ProcessEngine processEngine;

  protected Task workingTask = null;

  @WireVariable(ConstantesServicios.USUARIOS_SADE_SERVICE)
  private UsuariosSADEService usuariosSADEService;

  @WireVariable(ConstantesServicios.SECTOR_INTERNO_SERVICE)
  private SectorInternoServ sectorInternoServ;

  @WireVariable(ConstantesServicios.WORKFLOW_SERVICE)
  private WorkFlowService workFlowService;

  private PredicateGrillaReparticion reparticionPredicate = new PredicateGrillaReparticion();
  private PredicateGrillaUsuarios usuarioPredicate = new PredicateGrillaUsuarios();
  private List<ReparticionBean> listaSectorReparticionSADESeleccionada;

  private static final String TRAMITACION_EN_PARALELO = "Paralelo";
  private static Logger logger = LoggerFactory.getLogger(EnvioParaleloComposer.class);

  private int maxUserToAdd;
  private int maxSectorToAdd;
  private int minUserToAdd;
  private int minSectorToAdd;
  private boolean exclusiveMode;

  private static final int UNLIMITED = -1;
  private static final int DEFAULT_MIN = 2;

  /**
   * Defino los servicios que voy a utilizar
   */

  @WireVariable(ConstantesServicios.EXP_ELECTRONICO_SERVICE)
  private ExpedienteElectronicoService expedienteElectronicoService;

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

  public Textbox getEstado() {
    return estado;
  }

  public void setEstado(Textbox estado) {
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
    return getUsuariosGEDO();
  }

  public Combobox getReparticion() {
    return reparticion;
  }

  public void setReparticion(Combobox reparticion) {
    this.reparticion = reparticion;
  }

  public Combobox getSector() {
    return sector;
  }

  public void setSector(Combobox sector) {
    this.sector = sector;
  }

  public Combobox getReparticionSector() {
    return reparticionSector;
  }

  public void setReparticionSector(Combobox reparticionSector) {
    this.reparticionSector = reparticionSector;
  }

  public ExpedienteElectronicoDTO getExpedienteElectronico() {
    return expedienteElectronico;
  }

  public Listbox getUsuariosListbox() {
    return UsuariosListbox;
  }

  public void setUsuariosListbox(Listbox usuariosListbox) {
    UsuariosListbox = usuariosListbox;
  }

  public void setExpedienteElectronico(ExpedienteElectronicoDTO expedienteElectronico) {
    this.expedienteElectronico = expedienteElectronico;
  }

  public DatosEnvioParalelo getReparticionesAgregadasSelected() {
    return reparticionesAgregadasSelected;
  }

  public void setReparticionesAgregadasSelected(
      DatosEnvioParalelo reparticionesAgregadasSelected) {
    this.reparticionesAgregadasSelected = reparticionesAgregadasSelected;
  }

  /**
   * @return the maxUserToAdd
   */
  public int getMaxUserToAdd() {
    return maxUserToAdd;
  }

  /**
   * @param maxUserToAdd
   *          the maxUserToAdd to set
   */
  public void setMaxUserToAdd(int maxUserToAdd) {
    this.maxUserToAdd = maxUserToAdd;
  }

  /**
   * @return the maxSectorToAdd
   */
  public int getMaxSectorToAdd() {
    return maxSectorToAdd;
  }

  /**
   * @param maxSectorToAdd
   *          the maxSectorToAdd to set
   */
  public void setMaxSectorToAdd(int maxSectorToAdd) {
    this.maxSectorToAdd = maxSectorToAdd;
  }

  /**
   * @return the minUserToAdd
   */
  public int getMinUserToAdd() {
    return minUserToAdd;
  }

  /**
   * @param minUserToAdd
   *          the minUserToAdd to set
   */
  public void setMinUserToAdd(int minUserToAdd) {
    this.minUserToAdd = minUserToAdd;
  }

  /**
   * @return the minSectorToAdd
   */
  public int getMinSectorToAdd() {
    return minSectorToAdd;
  }

  /**
   * @return the exclusiveMode
   */
  public boolean isExclusiveMode() {
    return exclusiveMode;
  }

  /**
   * @param exclusiveMode
   *          the exclusiveMode to set
   */
  public void setExclusiveMode(boolean exclusiveMode) {
    this.exclusiveMode = exclusiveMode;
  }

  /**
   * @param minSectorToAdd
   *          the minSectorToAdd to set
   */
  public void setMinSectorToAdd(int minSectorToAdd) {
    this.minSectorToAdd = minSectorToAdd;
  }

  public void setMaxLimits(Integer users, Integer sectors) {
    setMaxUserToAdd(users != null ? users : UNLIMITED);
    setMaxSectorToAdd(sectors != null ? sectors : UNLIMITED);
  }

  public void setMinLimits(Integer users, Integer sectors) {
    setMinUserToAdd(users != null ? users : DEFAULT_MIN);
    setMinSectorToAdd(sectors != null ? sectors : DEFAULT_MIN);
  }

  public UsuarioReducido getUsuarioSeleccionado() {
		return usuarioSeleccionado;
	}

	public void setUsuarioSeleccionado(UsuarioReducido usuarioSeleccionado) {
		this.usuarioSeleccionado = usuarioSeleccionado;
	}

	/**
   * Según el estado en el que este, cargo la lista de estados con los estados a
   * los que se puede pasar. Esto se logra con el outcomes de jbpm, que devuelve
   * todas las salidas (transiciones) que tiene la tarea acctual
   * 
   * @exception se
   *              utiliza throws Exception por el doAfterCompose de ZK el cual
   *              esta contenido en el GenericForwardComposer
   */
  public void doAfterCompose(Component component) throws Exception {
    super.doAfterCompose(component);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    component.addEventListener(Events.ON_NOTIFY, new EnvioParaleloOnNotifyWindowListener(this));
    component.addEventListener(Events.ON_USER, new EnvioParaleloOnNotifyWindowListener(this));

    this.setSectorInternoServ(sectorInternoServ);
    this.setWorkingTask((Task) component.getDesktop().getAttribute("selectedTask"));

    if ((this.sectorReparticionBusquedaSADE.getValue() != "")
        && (this.sectorReparticionBusquedaSADE.getValue() != null)) {
      this.sectorBusquedaSADE.setDisabled(false);
    } else {
      this.sectorBusquedaSADE.setDisabled(true);
    }

    loggedUsername = (String) Executions.getCurrent().getDesktop().getSession()
        .getAttribute(ConstantesWeb.SESSION_USERNAME);

		configurarBandboxUsuario(component, true);
		
//    usuario.setModel(ListModels.toListSubModel(
//        new ListModelList(this.getUsuariosSADEService().getTodosLosUsuarios()),
//        new UsuariosComparatorConsultaExpediente(), 30));
 
    this.estado.setValue(TRAMITACION_EN_PARALELO);

    expedienteElectronico = (ExpedienteElectronicoDTO) Executions.getCurrent().getArg()
        .get("expedienteElectronico");
    Map<String, Object> variables = new HashMap<>();

    if (expedienteElectronico == null) {
      Long idExpedienteElectronico = (Long) getVariableWorkFlow("idExpedienteElectronico");
      expedienteElectronico = expedienteElectronicoService
          .buscarExpedienteElectronico(idExpedienteElectronico);
    }
    variables.put("idExpedienteElectronico", expedienteElectronico.getId());
    setVariablesWorkFlow(variables);
  }

  private void configurarBandboxUsuario(Component component, boolean deshabilitado) {
		
    inclBandboxUsuario.setDynamicProperty(BandBoxUsuarioComposer.DISABLED_BANDBOX, deshabilitado);
    inclBandboxUsuario.setDynamicProperty(BandBoxUsuarioComposer.COMPONENTE_PADRE, component);
    inclBandboxUsuario.setSrc(BandBoxUsuarioComposer.SRC);
    
		component.addEventListener(BandBoxUsuarioComposer.ON_SELECT_USUARIO,
				new EnvioParaleloOnNotifyWindowListener(this));

	}

	/**
   * Al presionar el botón enviar se realiza el PASE ELECTRONICO del Expediente
   * Electrónico.
   */

  public void mostrarMensajeDeAdvertenciaSistemaExternoBAC(final EnvioParaleloComposer comp,
      String codigoSistemaExterno) throws InterruptedException {
    Messagebox.show(
        Labels.getLabel("ee.envioComp.msgbox.deberaFinalizarProceso")
            + codigoSistemaExterno.toUpperCase(),
        Labels.getLabel("ee.general.advertencia"), Messagebox.OK, Messagebox.EXCLAMATION,
        new EventListener() {

          @Override
          public void onEvent(Event evt) throws Exception {
            switch ((Integer) evt.getData()) {
            case Messagebox.OK: {
              comp.mostrarForegroundBloqueanteToken();
              Events.echoEvent("onUser", comp.self, "enviarEnParalelo");
              ;
            }
            default:
              return;
            }

          }

        });
  }

  public void onEnviar() throws InterruptedException {
    super.definirMotivo();
    super.validarMotivo();

    if (((this.usuariosDestinatarios.size() >= getMinUserToAdd())
        && (this.reparticionesDestinatarios.size() >= getMinSectorToAdd()))
        || (this.usuariosDestinatarios.size() >= getMinUserToAdd())
        || (this.reparticionesDestinatarios.size() >= getMinSectorToAdd())
        || (this.usuariosDestinatarios.size()
            + this.reparticionesDestinatarios.size()) >= getMinUserToAdd()) {
      TrataDTO t = expedienteElectronico.getTrata();
      ParametrosSistemaExternoDTO params = ConfiguracionInicialModuloEEFactory
          .obtenerParametrosPorTrata(t.getId());
      if (expedienteElectronico.getSistemaCreador().equals(ConstantesWeb.SISTEMA_BAC)
          || ((t.getIntegracionSisExt() || t.getIntegracionAFJG()) && params != null)) {
        decidirMensajeDependiendoDeSistemaExterno(params, t, expedienteElectronico);
      } else {
        this.mostrarForegroundBloqueanteToken();
        Events.echoEvent("onUser", this.self, "enviarEnParalelo");
      }
    } else {
      // "Debe agregar más de un destino para realizar un pase
      // Múltiple.");
      String custom = String.format("%d usuarios ó %s reparticiones-sectores", getMinUserToAdd(),
          getMinSectorToAdd());
      throw new WrongValueException(this.destino,
          "Debe indicar al menos " + custom + " como destino para realizar un pase Múltiple.");
    }
  }

  private void decidirMensajeDependiendoDeSistemaExterno(ParametrosSistemaExternoDTO p, TrataDTO t,
      ExpedienteElectronicoDTO e) throws InterruptedException {
    if (e.getSistemaCreador().equals(ConstantesWeb.SISTEMA_BAC)) {
      mostrarMensajeDeAdvertenciaSistemaExternoBAC(this, ConstantesWeb.SISTEMA_BAC);
      return;
    }
    if ((t.getIntegracionSisExt() || t.getIntegracionAFJG()) && p != null) {
      mostrarMensajeDeAdvertenciaSistemaExternoBAC(this, p.getCodigo());

    }
  }

  private void mostrarForegroundBloqueanteToken() {
    Clients.showBusy(Labels.getLabel("enviarParalelo.value"));
  }

  public void enviar() throws InterruptedException {

    this.estadoSeleccionado = this.estado.getValue();

    sectorMesaVirtual = null;

    // SE DEBE ENVIAR EL PASE A TODOS LOS USUARIOS DE LA LISTA
    this.usuariosDestinatarios.addAll(this.reparticionesDestinatarios);
    /**
     * La lista destinos es la lista de todos los destinos finales que tomará el
     * fork para crear todas las tareas paralelas
     */
    List<String> destinos = new ArrayList<String>();

    /**
     * Las variables reparticiones y usuarios son variables que contatenan todos
     * los nombres para luego mostrar el informe final luego de realizar todas
     * las tareas paralelas
     */

    String reparticionesConcatenadas = "";
    String usuariosConcatenados = "";

    for (DatosEnvioParalelo destinosAEnviar : this.usuariosDestinatarios) {
      if (destinosAEnviar.getUser() != null) {
        if (destinosAEnviar.getUserApoderado() != null
            && !destinosAEnviar.getUserApoderado().equals("")) {

          usuariosConcatenados = usuariosConcatenados + destinosAEnviar.getUserApoderado()
              + " en reemplazo por la licencia de " + destinosAEnviar.getUser() + ", ";

          destinos.add(destinosAEnviar.getUserApoderado());
        } else {
          usuariosConcatenados = usuariosConcatenados + destinosAEnviar.getUser() + ", ";
          destinos.add(destinosAEnviar.getUser());
        }

      } else {
        // SI EL USER ES NULL ES POR QUE ES UNA REPARTICION.
        reparticionesConcatenadas = reparticionesConcatenadas
            + destinosAEnviar.getReparticionesSectores() + ", ";
        destinos.add(destinosAEnviar.getReparticionesSectores());

      }

    }

    if (!usuariosConcatenados.equals("")) {
      usuariosConcatenados = usuariosConcatenados.substring(0, usuariosConcatenados.length() - 2);
    }

    if (!reparticionesConcatenadas.equals("")) {
      reparticionesConcatenadas = reparticionesConcatenadas.substring(0,
          reparticionesConcatenadas.length() - 2);
    }

    enviarEnParalelo(destinos, reparticionesConcatenadas, usuariosConcatenados,
        this.estadoSeleccionado, loggedUsername, sectorMesaVirtual);
  }

  private void enviarEnParalelo(List<String> destinos, String reparticionesConcatenadas,
      String usuariosConcatenados, String estadoSeleccionado, String loggedUsername,
      SectorInternoBean sectorMesaVirtual) throws WrongValueException, InterruptedException {

    try {
      definirMotivo();
      String taskId = this.getWorkingTask().getId();
      String estadoAnterior = (String) getVariableWorkFlow("estadoAnterior");
      this.expedienteElectronicoService.generarPaseParaleloExpedienteElectronico(workingTask,
          this.expedienteElectronico, loggedUsername, estadoSeleccionado, estadoAnterior,
          this.motivoExpedienteStr, this.usuariosDestinatarios);

      Map<String, Object> variables = new HashMap<String, Object>();
      variables.put("destinos", destinos);

      Integer cantUser = destinos.size();

      variables.put("cant", cantUser.toString());
      variables.put("usuarioSolicitante", loggedUsername);
      variables.put("estadoAnteriorParalelo", this.estadoSeleccionado);
      variables.put("idExpedienteElectronico", this.expedienteElectronico.getId());
      variables.put("tareaGrupal", "noEsTareaGrupal");
      setVariablesWorkFlow(variables);

      processEngine.getExecutionService().signalExecutionById(
          this.processEngine.getTaskService().getTask(taskId).getExecutionId(), "forkEach");

      String mensaje = "Se generó el pase del expediente: "
          + expedienteElectronico.getCodigoCaratula();

      if (!usuariosConcatenados.equals("")) {
        mensaje = mensaje + "\n\n Se envió a los usuarios: " + usuariosConcatenados;

      }
      if (!reparticionesConcatenadas.equals("")) {

        mensaje = mensaje + "\n\n Se envió a los sectores: " + reparticionesConcatenadas;

      }
      Messagebox.show(mensaje, Labels.getLabel("ee.general.information"), Messagebox.OK,
          Messagebox.INFORMATION);
      Executions.getCurrent().getDesktop().setAttribute("selectedTask", null);
    } catch (RemoteAccessException e) {
      logger.error(e.getMessage(), e);
      Messagebox.show(e.getMessage(), Labels.getLabel("ee.general.advertencia"), Messagebox.OK,
          Messagebox.ERROR);
    } catch (Exception e) {
      logger.error("Error al realizar la tramitación en Paralelo", e);
      Messagebox.show(Labels.getLabel("ee.envioParaleloComp.msgbox.errorTramParalelo"), 
          Labels.getLabel("ee.subsanacion.msg.title.errorMayus"), Messagebox.OK,
          Messagebox.ERROR);
    } finally {
      Clients.clearBusy();
      this.closeAssociatedWindow();
    }
  }

  public void onCancelar() {
    super.onCancelar();

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

  @SuppressWarnings("unchecked")
	public void onClick$agregarUsuario() throws InterruptedException {

    Usuario usuSelec = new Usuario();

    if (this.usuarioSeleccionado == null) {
    	
    	this.mensajeValidadorBandboxUsuario("Debe ingresar un usuario para agregar a la lista.");
//      throw new WrongValueException(this.usuario,
//          "Debe ingresar un usuario para agregar a la lista.");
    }

    usuSelec = usuariosSADEService.getDatosUsuario(usuSelec.getUsername());

    if ((usuSelec == null) || (usuSelec.getUsername().equals(""))) {
    		
    	this.mensajeValidadorBandboxUsuario("Debe ingresar un usuario para agregar a la lista.");
//      throw new WrongValueException(this.usuario,
//          "Debe ingresar un usuario para agregar a la lista.");
    } else if (usuSelec.getUsername().equals(loggedUsername)) {
    	this.mensajeValidadorBandboxUsuario("No puede agregar su usuario a la lista. Seleccione otro destino.");
//      throw new WrongValueException(this.usuario,
//          "No puede agregar su usuario a la lista. Seleccione otro destino.");
    }

    DatosEnvioParalelo usernameEnviarParalelo = new DatosEnvioParalelo();
    usernameEnviarParalelo.setApellidoYNombre(usuSelec.getNombreApellido());
    usernameEnviarParalelo.setUser(usuSelec.getUsername());

    String userApoderado = validarLicencia(usernameEnviarParalelo.getUser());

    usernameEnviarParalelo.setUserApoderado(userApoderado);

    validarAsignacionUsuario(usernameEnviarParalelo);

    if (userApoderado != null) {

      Messagebox.show(
          Labels.getLabel("ee.licencia.questionParalelo.value",
              new String[] { usernameEnviarParalelo.getUser(), userApoderado }) + "\n"
              + Labels.getLabel("ee.licencia.questionParalelo.value2"),
          Labels.getLabel("ee.licencia.question.title"), Messagebox.YES | Messagebox.NO,
          Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {

            public void onEvent(Event evt) throws Exception {

              switch (((Integer) evt.getData()).intValue()) {
              case Messagebox.YES:
                DatosEnvioParalelo usernameEnviarParalelo = new DatosEnvioParalelo();
                usernameEnviarParalelo.setApellidoYNombre(usuarioSeleccionado.getNombreApellido());
                usernameEnviarParalelo.setUser(usuarioSeleccionado.getUsername());
                String userApoderado = validarLicencia(usernameEnviarParalelo.getUser());
                usernameEnviarParalelo.setUserApoderado(userApoderado);
                try {
                  asignarUsuario(usernameEnviarParalelo);
                } catch (Exception e) {
                }
                cleanBandboxUsuario();
                break;
              case Messagebox.NO:
              	cleanBandboxUsuario();
                break;
              }

            }
          });

    } else {

      asignarUsuario(usernameEnviarParalelo);
      cleanBandboxUsuario();
      //usuario.setValue("");
    }

    verifyUsersAndSectorCounter();
  }

  public void verifyUsersAndSectorCounter() {
    Hbox addUser = ZkUtil.findById(this.envioWindow, "addUser");
    Hbox addSector = ZkUtil.findById(this.envioWindow, "addSector");

    addUser.setVisible(
        getMaxUserToAdd() == UNLIMITED || this.usuariosDestinatarios.size() < getMaxUserToAdd());
    addSector.setVisible(getMaxSectorToAdd() == UNLIMITED
        || this.reparticionesDestinatarios.size() < getMaxSectorToAdd());

    if (isExclusiveMode()) {
      Row rowUser = ZkUtil.findById(this.envioWindow, "rowUser");
      Row rowSector = ZkUtil.findById(this.envioWindow, "rowSector");

      boolean allEmpty = this.usuariosDestinatarios.isEmpty()
          && this.reparticionesDestinatarios.isEmpty();
      rowUser.setVisible(allEmpty
          || (this.usuariosDestinatarios.size() > this.reparticionesDestinatarios.size()));
      rowSector.setVisible(allEmpty
          || (this.usuariosDestinatarios.size() < this.reparticionesDestinatarios.size()));
    }
  }

  private void validarAsignacionUsuario(DatosEnvioParalelo usernameEnviarParalelo) {

    if (usuariosDestinatarios.size() > 0) {

      for (DatosEnvioParalelo username : usuariosDestinatarios) {

        if (usernameEnviarParalelo.getUserApoderado() != null) {
          if (usernameEnviarParalelo.getUserApoderado().equals(username.getUser())) {
          	this.mensajeValidadorBandboxUsuario("El usuario " + usernameEnviarParalelo.getUser()
                    + " se encuentra de licencia y su apoderado "
                    + usernameEnviarParalelo.getUserApoderado()
                    + " ya se encuentra en la lista de destinatarios. Seleccione otro destino.");
//            throw new WrongValueException(usuario,
//                "El usuario " + usernameEnviarParalelo.getUser()
//                    + " se encuentra de licencia y su apoderado "
//                    + usernameEnviarParalelo.getUserApoderado()
//                    + " ya se encuentra en la lista de destinatarios. Seleccione otro destino.");
          } else if (usernameEnviarParalelo.getUserApoderado()
              .equals(username.getUserApoderado())) {
          	
          	this.mensajeValidadorBandboxUsuario("El usuario " + usernameEnviarParalelo.getUser()
                    + " se encuentra de licencia y su apoderado "
                    + usernameEnviarParalelo.getUserApoderado()
                    + " ya se encuentra en la lista de destinatarios como apoderado del usuario "
                    + username.getUser() + ". Seleccione otro destino.");
          	
//            throw new WrongValueException(usuario,
//                "El usuario " + usernameEnviarParalelo.getUser()
//                    + " se encuentra de licencia y su apoderado "
//                    + usernameEnviarParalelo.getUserApoderado()
//                    + " ya se encuentra en la lista de destinatarios como apoderado del usuario "
//                    + username.getUser() + ". Seleccione otro destino.");
          }
        } else if (usernameEnviarParalelo.getUser().equals(username.getUser())) {
        	
        	this.mensajeValidadorBandboxUsuario("El usuario " + usernameEnviarParalelo.getUser()
              + " se encuentra en la lista de destinatarios. Seleccione otro destino.");
        	
//          throw new WrongValueException(usuario, "El usuario " + usernameEnviarParalelo.getUser()
//              + " se encuentra en la lista de destinatarios. Seleccione otro destino.");

        } else if (usernameEnviarParalelo.getUser().equals(username.getUserApoderado())) {

        	this.mensajeValidadorBandboxUsuario( "El usuario " + usernameEnviarParalelo.getUser()
                  + " se encuentra en la lista de destinatarios como apoderado de "
                  + username.getUser() + ". Seleccione otro destino.");
        	
//        	throw new WrongValueException(usuario,
//              "El usuario " + usernameEnviarParalelo.getUser()
//                  + " se encuentra en la lista de destinatarios como apoderado de "
//                  + username.getUser() + ". Seleccione otro destino.");
        }

      }

    }

  }

  private void asignarUsuario(DatosEnvioParalelo usernameEnviarParalelo) {
    this.usuariosDestinatarios.add(usernameEnviarParalelo);
    this.UsuariosListbox.setModel(new BindingListModelList(this.usuariosDestinatarios, true));
    // this.binder.loadComponent(this.UsuariosListbox);

  }

  public void onClick$agregarSectorReparticion() {

    if ((this.sectorReparticionBusquedaSADE.getValue() == null)
        || (this.sectorReparticionBusquedaSADE.getValue().equals(""))) {
      throw new WrongValueException(this.sectorReparticionBusquedaSADE,
          "Debe ingresar una Repartición.");
    }
    if ((this.sectorBusquedaSADE.getValue() == null)
        || (this.sectorBusquedaSADE.getValue().equals(""))) {
      throw new WrongValueException(this.sectorBusquedaSADE, "Debe ingresar un Sector.");
    }

    DatosEnvioParalelo datosEnvioParalelo = new DatosEnvioParalelo();
    datosEnvioParalelo
        .setReparticionesSectores(this.sectorReparticionBusquedaSADE.getValue().trim() + "-"
            + this.sectorBusquedaSADE.getValue().trim());

    boolean estaEnLista;

    if (this.reparticionesDestinatarios.size() == 0) {
      this.reparticionesDestinatarios.add(datosEnvioParalelo);
      this.sectorReparticionListbox
          .setModel(new BindingListModelList(this.reparticionesDestinatarios, true));
      estaEnLista = false;
      // this.binder.loadComponent(sectorReparticionListbox);
    } else {
      estaEnLista = true;
      for (DatosEnvioParalelo secRep : this.reparticionesDestinatarios) {
        if (datosEnvioParalelo.getReparticionesSectores()
            .equals(secRep.getReparticionesSectores())) {
          estaEnLista = true;
          break;
        } else {
          estaEnLista = false;

        }
      }

      if (!estaEnLista) {
        this.reparticionesDestinatarios.add(datosEnvioParalelo);
        this.sectorReparticionListbox
            .setModel(new BindingListModelList(this.reparticionesDestinatarios, true));
        // setVariableWorkFlow("grupoSeleccionado",datosEnvioParalelo.getReparticionesSectores());
        // setVariableWorkFlow("usuarioSeleccionado", null);
        // setVariableWorkFlow("tareaGrupal", "esTareaGrupal");
        // this.sectorReparticionSelected = sectorReparticion;
        // this.binder.loadComponent(sectorReparticionListbox);

      } else {
        throw new WrongValueException(this.sectorReparticionBusquedaSADE,
            "La Repartición y el Sector ya se ha agregado a la lista. Seleccione otro destino.");
      }

    }
    this.sectorReparticionBusquedaSADE.setValue("");
    this.sectorBusquedaSADE.setValue("");
    this.sectorBusquedaSADE.setDisabled(true);

    verifyUsersAndSectorCounter();

  }

  public void onEliminarUsuario(Event evt) {

    // The event is a ForwardEvent...
    ForwardEvent fe = (ForwardEvent) evt;
    // Getting the original Event
    Event event = fe.getOrigin();
    // Getting the component that triggered the original event (i.e. the
    // button)
    Image btn = (Image) event.getTarget();

    DatosEnvioParalelo nombre = (DatosEnvioParalelo) btn.getAttribute("nombre");

    for (int i = 0; usuariosDestinatarios.size() > i; i++) {

      if (usuariosDestinatarios.get(i).getUser().equals(nombre.getUser())) {
        usuariosDestinatarios.remove(i);
        this.UsuariosListbox.setModel(new BindingListModelList(this.usuariosDestinatarios, true));
        break;

      }
    }

    this.binder.loadComponent(UsuariosListbox);

    verifyUsersAndSectorCounter();
  }

  public void onGuardarMotivo(Event evt) {

    // The event is a ForwardEvent...
    ForwardEvent fe = (ForwardEvent) evt;
    // Getting the original Event
    Event event = fe.getOrigin();
    // Getting the component that triggered the original event (i.e. the
    // button)
    Image btn = (Image) event.getTarget();

    DatosEnvioParalelo user = (DatosEnvioParalelo) btn.getAttribute("destinatario");

    if (user.getApellidoYNombre() != null) {
      for (DatosEnvioParalelo destinos : this.usuariosDestinatarios) {

        if (user.getUser() != null) {
          if (user.getUser().equals(destinos.getUser())) {
            crearComponente(destinos);
          }
        } else {
          if (user.getReparticionesSectores().equals(destinos.getReparticionesSectores())) {
            crearComponente(destinos);
          }
        }
      }

    } else {
      for (DatosEnvioParalelo destinos : this.reparticionesDestinatarios) {

        if (user.getUser() != null) {
          if (user.getUser().equals(destinos.getUser())) {
            crearComponente(destinos);
          }
        } else {
          if (user.getReparticionesSectores().equals(destinos.getReparticionesSectores())) {
            crearComponente(destinos);
          }
        }
      }

    }

  }

  private void crearComponente(DatosEnvioParalelo destino) {
    HashMap<String, Object> hm = new HashMap<String, Object>();
    hm.put("destinatario", destino);

    this.envio = (Window) Executions.createComponents("/expediente/motivoPropio.zul", null, hm);

    this.envio.setParent(this.envioWindow);
    this.envio.setPosition("center");
    this.envio.setClosable(true);

    this.envio.doModal();
  }

  public void onEliminarSectorReparticion(Event evt) {

    // The event is a ForwardEvent...
    ForwardEvent fe = (ForwardEvent) evt;
    // Getting the original Event
    Event event = fe.getOrigin();
    // Getting the component that triggered the original event (i.e. the
    // button)
    Image btn = (Image) event.getTarget();

    String SectorRep = (String) btn.getAttribute("nombre");

    for (int i = 0; reparticionesDestinatarios.size() > i; i++) {

      if (reparticionesDestinatarios.get(i).getUser() == null) {
        if (reparticionesDestinatarios.get(i).getReparticionesSectores().equals(SectorRep)) {
          reparticionesDestinatarios.remove(i);
          this.sectorReparticionListbox
              .setModel(new BindingListModelList(this.reparticionesDestinatarios, true));
        }
      }
    }

    this.binder.loadComponent(sectorReparticionListbox);

    verifyUsersAndSectorCounter();
  }

  public ExpedienteElectronicoService getExpedienteElectronicoService() {
    return expedienteElectronicoService;
  }

  public void setExpedienteElectronicoService(
      ExpedienteElectronicoService expedienteElectronicoService) {
    this.expedienteElectronicoService = expedienteElectronicoService;
  }

  public Bandbox getSectorReparticionBusquedaSADE() {
    return sectorReparticionBusquedaSADE;
  }

  public void setSectorReparticionBusquedaSADE(Bandbox sectorReparticionBusquedaSADE) {
    this.sectorReparticionBusquedaSADE = sectorReparticionBusquedaSADE;
  }

  public List<Usuario> getUsuariosGEDO() {
    try {
      return this.usuariosSADEService.getTodosLosUsuarios();
    } catch (SecurityNegocioException e) {
      logger.error(e.getMessage());
      throw new WrongValueException(Labels.getLabel("ee.error.usuario.label.invalidos"));
    }
  }

  public UsuariosSADEService getUsuariosSADEService() {
    return usuariosSADEService;
  }

  public void setUsuariosSADEService(UsuariosSADEService usuariosSADEService) {
    this.usuariosSADEService = usuariosSADEService;
  }

  public List<ReparticionBean> getListaSectorReparticionSADESeleccionada() {
    return listaSectorReparticionSADESeleccionada;
  }

  public void setListaSectorReparticionSADESeleccionada(
      List<ReparticionBean> listaSectorReparticionSADESeleccionada) {
    this.listaSectorReparticionSADESeleccionada = listaSectorReparticionSADESeleccionada;
  }

  /**
   * 
   * @param name:
   *          nombre de la variable que quiero setear
   * @param value:
   *          valor de la variable
   */
  @Deprecated
  public void setVariableWorkFlow(String name, Object value) {
    this.processEngine.getExecutionService().setVariable(this.workingTask.getExecutionId(), name,
        value);
  }

  public void setVariablesWorkFlow(Map<String, Object> variables) {
    workFlowService.setVariables(processEngine, this.workingTask.getExecutionId(), variables);
  }

  public void refreshInbox() {
    this.UsuariosListbox.setModel(new BindingListModelList(this.usuariosDestinatarios, true));
    this.sectorReparticionListbox
        .setModel(new BindingListModelList(this.reparticionesDestinatarios, true));
    this.binder.loadAll();
  }

  /**
   * 
   * @param nameTransition:
   *          nombre de la transición que voy a usar para la proxima tarea
   * @param usernameDerivador:
   *          usuario que va a tener asignada la tarea
   */
  public void signalExecution(String nameTransition, String usernameDerivador) {
    processEngine.getExecutionService().signalExecutionById(this.workingTask.getExecutionId(),
        nameTransition);
  }

  /**
   * 
   * @param name:
   *          nombre de la variable del WF que quiero encontrar
   * @return objeto guardado en la variable
   */
  public Object getVariableWorkFlow(String name) {
    Object obj = this.processEngine.getExecutionService()
        .getVariable(this.workingTask.getExecutionId(), name);
    if (obj == null) {
      throw new VariableWorkFlowNoExisteException("No existe la variable para el id de ejecucion. "
          + this.workingTask.getExecutionId() + ", " + name, null);
    }
    return obj;
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

  public Grid getGrillaDestino() {
    return grillaDestino;
  }

  public void setGrillaDestino(Grid grillaDestino) {
    this.grillaDestino = grillaDestino;
  }

  private String validarLicencia(String username) {
    return this.usuariosSADEService.getDatosUsuario(username).getApoderado();
  }

  public List<DatosEnvioParalelo> getUsuariosDestinatarios() {
    return usuariosDestinatarios;
  }

  @SuppressWarnings("unchecked")
  public List<DatosEnvioParalelo> getUsuariosDestinatariosFiltrado() {
    return (List<DatosEnvioParalelo>) CollectionUtils.select(usuariosDestinatarios,
        usuarioPredicate);
  }

  @SuppressWarnings("unchecked")
  public List<DatosEnvioParalelo> getReparticionDestinatariosFiltrado() {
    return (List<DatosEnvioParalelo>) CollectionUtils.select(usuariosDestinatarios,
        reparticionPredicate);
  }

  public void setUsuariosDestinatarios(List<DatosEnvioParalelo> usuariosDestinatarios) {
    this.usuariosDestinatarios = usuariosDestinatarios;
  }

  public DatosEnvioParalelo getUsuariosAgregadoSelected() {
    return usuariosAgregadoSelected;
  }

  public void setUsuariosAgregadoSelected(DatosEnvioParalelo usuariosAgregadoSelected) {
    this.usuariosAgregadoSelected = usuariosAgregadoSelected;
  }

  private class PredicateGrillaUsuarios implements Predicate {

    public boolean evaluate(Object arg0) {
      DatosEnvioParalelo datosEnvio = (DatosEnvioParalelo) arg0;
      if (datosEnvio.getUser() != null) {
        return true;
      } else {
        return false;
      }
    }

  }

  private class PredicateGrillaReparticion implements Predicate {

    public boolean evaluate(Object arg0) {
      DatosEnvioParalelo datosEnvio = (DatosEnvioParalelo) arg0;
      if (datosEnvio.getReparticionesSectores() != null) {
        return true;
      } else {
        return false;
      }
    }

  }

  final class EnvioParaleloOnNotifyWindowListener implements EventListener {
    private EnvioParaleloComposer composer;

    public EnvioParaleloOnNotifyWindowListener(EnvioParaleloComposer comp) {
      this.composer = comp;
    }

    public void onEvent(Event event) throws Exception {
      if (event.getName().equals(Events.ON_NOTIFY)) {
        this.composer.refreshInbox();
      }
      if (event.getName().equals(Events.ON_USER)) {
        if (event.getData().equals("enviarEnParalelo")) {
          this.composer.enviar();
        }
      }else if(event.getName().equals(BandBoxUsuarioComposer.ON_SELECT_USUARIO)) {
				this.composer.setUsuarioSeleccionado((UsuarioReducido) event.getData());
			}
    }

  }

  public Window getEnvio() {
    return envio;
  }

  public void setEnvio(Window envio) {
    this.envio = envio;
  }

  public void setSectorInternoServ(SectorInternoServ sectorInternoServ) {
    this.sectorInternoServ = sectorInternoServ;
  }

  public SectorInternoServ getSectorInternoServ() {
    return sectorInternoServ;
  }

  public List<DatosEnvioParalelo> getReparticionesDestinatarios() {
    return reparticionesDestinatarios;
  }

  public void setReparticionesDestinatarios(List<DatosEnvioParalelo> reparticionesDestinatarios) {
    this.reparticionesDestinatarios = reparticionesDestinatarios;
  }

}
