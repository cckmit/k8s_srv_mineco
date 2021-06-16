package com.egoveris.te.base.composer;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.stream.Collectors;

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
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Image;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Window;

import com.egoveris.sharedorganismo.base.model.ReparticionBean;
import com.egoveris.sharedorganismo.base.model.SectorInternoBean;
import com.egoveris.sharedorganismo.base.service.ReparticionServ;
import com.egoveris.sharedorganismo.base.service.SectorInternoServ;
import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.sharedsecurity.base.model.UsuarioReducido;
import com.egoveris.te.base.composer.genrico.BandBoxUsuarioComposer;
import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.HistorialOperacionDTO;
import com.egoveris.te.base.service.HistorialOperacionService;
import com.egoveris.te.base.service.UsuariosSADEService;
import com.egoveris.te.base.service.WorkFlowService;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.service.iface.IBloqueoExpedienteService;
import com.egoveris.te.base.service.iface.IGenerarPaseExpedienteService;
import com.egoveris.te.base.service.iface.IRehabilitarExpedienteService;
import com.egoveris.te.base.util.ConstantesServicios;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.model.model.PaseExpedienteRequest;


@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class EnvioAdministracionComposer extends EnvioExpedienteComposer {
  
  private static final long serialVersionUID = 6266287038479709703L;
  private static final String ESTADO_GUARDA_TEMPORAL = "Guarda Temporal";
  private static Logger logger = LoggerFactory.getLogger(EnvioAdministracionComposer.class);
  private AnnotateDataBinder binder;
  
  @Autowired
  private Combobox estado;
  //@Autowired
  //private Combobox usuario;
  @Autowired
  private Combobox reparticion;
  
  private Include inclBandboxUsuario;
  
  private String selectedReparticiones;
  @Autowired
  private Combobox sector;
  @Autowired
  private Label destino;
  private SectorInternoBean selectedSectores;
  @Autowired
  private Combobox reparticionSector;
  private String selectedReparticionesSector;
  @Autowired
  private Radio usuarioRadio;
  @Autowired
  private Radio sectorRadio;
  @Autowired
  private Radio reparticionRadio;
  @Autowired
  private Bandbox reparticionBusquedaSADE;
  @Autowired
  private Bandbox sectorReparticionBusquedaSADE;
  @Autowired
  private Grid grillaDestino;
  @Autowired
  private Bandbox sectorBusquedaSADE;
  @Autowired
  private Label numeroDocumento;
  
  @Autowired
  private Image btnBorrarDoc;
  
  
  private ExpedienteElectronicoDTO expedienteElectronico;
  private List<ExpedienteElectronicoDTO> expedientesElectronicos;
  private Map<String, ExpedienteElectronicoDTO> mapResultado;
  private Usuario usuarioProductorInfo;
  private String loggedUsername;
  private SectorInternoBean sectorMesaVirtual;
  protected Task workingTask = null;
  private HistorialOperacionDTO historialOperacion = new HistorialOperacionDTO();
  private List<ReparticionBean> listaSectorReparticionSADESeleccionada;
  private UsuarioReducido usuarioSeleccionado;
  
  @Autowired
  private Listbox sectoresReparticionesBusquedaSADEListbox;
  private Label etiquetaDocAsociar;
  /**
   * Defino los servicios que voy a utilizar
   */
  @WireVariable(ConstantesServicios.EXP_ELECTRONICO_SERVICE)
  private ExpedienteElectronicoService expedienteElectronicoService;
  
  @WireVariable(ConstantesServicios.HISTORIAL_OPERACION_SERVICE)
  private HistorialOperacionService historialOperacionService;
  
  @WireVariable(ConstantesServicios.USUARIOS_SADE_SERVICE)
  private UsuariosSADEService usuariosSADEService;
  
  @WireVariable(ConstantesServicios.SECTOR_INTERNO_SERVICE)
  private SectorInternoServ sectorInternoServ; 
  
  @WireVariable(ConstantesServicios.REPARTICION_SERVICE)
  private ReparticionServ reparticionServ;
  
  @WireVariable(ConstantesServicios.WORKFLOW_SERVICE)
  private WorkFlowService workFlowService;
  
	@WireVariable("processEngine")
	private ProcessEngine processEngine;

  private PaseExpedienteRequest datosSolicitudPase;
  
  @WireVariable("generarPaseExpedienteServiceImpl")
  private IGenerarPaseExpedienteService generarPaseExpedienteService;
  
  @WireVariable("bloqueoExpedienteServiceImpl")
  private IBloqueoExpedienteService bloqueoExpedienteService;
  
  @WireVariable("rehabilitarExpedienteServiceImpl")
  private IRehabilitarExpedienteService rehabilitarExpedienteService;

  /**
   * Según el estado en el que este, cargo la lista de estados con los estados a
   * los que se puede pasar. Esto se logra con el outcomes de jbpm, que devuelve
   * todas las salidas (transiciones) que tiene la tarea acctual
   * 
   * @exception se
   *              utiliza throws Exception por el doAfterCompose de ZK el cual
   *              esta contenido en el GenericForwardComposer
   */
  @SuppressWarnings("unchecked")
	public void doAfterCompose(Component component) throws Exception {
    super.doAfterCompose(component);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    
    this.usuarioRadio.setSelected(false);
    this.sectorRadio.setSelected(false);
    this.reparticionRadio.setSelected(false);
//    this.usuario.setDisabled(true);
    this.sectorBusquedaSADE.setDisabled(true);

    this.reparticionBusquedaSADE.setDisabled(true);
    this.sectorReparticionBusquedaSADE.setDisabled(true);
//    usuario.setModel(ListModels.toListSubModel(
//        new ListModelList(this.getUsuariosSADEService().getTodosLosUsuarios()),
//        new UsuariosComparatorConsultaExpediente(), 30));
//    this.binder = new AnnotateDataBinder(usuario);
//    binder.loadComponent(usuario);
    
    inclBandboxUsuario.setDynamicProperty(BandBoxUsuarioComposer.DISABLED_BANDBOX, true);
    inclBandboxUsuario.setDynamicProperty(BandBoxUsuarioComposer.COMPONENTE_PADRE, component);
    inclBandboxUsuario.setSrc(BandBoxUsuarioComposer.SRC);
    
    this.expedientesElectronicos = new ArrayList<>();
    
		cargarListener(component);

    @SuppressWarnings("unchecked")
    final Map<String, ExpedienteElectronicoDTO> mapCheck = ((Map<String, ExpedienteElectronicoDTO>) Executions
        .getCurrent().getDesktop().getAttribute("mapCheck"));
    if(!mapCheck.isEmpty()) {
    	
    	Iterator<Entry<String, ExpedienteElectronicoDTO>> i = mapCheck.entrySet().iterator();
    	
    	while (i.hasNext()) {
    		Entry<String, ExpedienteElectronicoDTO> e = i.next();
    		this.expedientesElectronicos.add((ExpedienteElectronicoDTO) e.getValue());
    	}
    }else {
  		Map<String,Object> params = (Map<String, Object>) Executions.getCurrent().getArg();
  		this.expedientesElectronicos.add((ExpedienteElectronicoDTO) params.get("expedienteSelected"));
    }
    

  }


	private void cargarListener(Component component) {
		component.addEventListener(VincularDocumentoComposer.ON_MENSAJE_ACORDEON, 
				new EnvioAdministracionListener());
		component.addEventListener(BandBoxUsuarioComposer.ON_SELECT_USUARIO, new EnvioAdministracionListener());
	}
  
  
  public void onVincularDocumentos() {
  	
		Map<String, Object> parametros = new HashMap<>();
		
		parametros.put("ee", expedientesElectronicos.get(0));
		parametros.put("workingTask",null);
		parametros.put("rehabilitar" , "rehabilitar");
		
		Window vincularDoc = (Window) Executions
				.createComponents("/expediente/producirDocumento/vincularDocumento.zul",
						this.self,
				parametros);
		vincularDoc.doModal();
  }

  public void onEnviar() throws InterruptedException {

    super.definirMotivo();
    validarDatosEnvio();
    loggedUsername = (String) Executions.getCurrent().getDesktop().getSession()
        .getAttribute(ConstantesWeb.SESSION_USERNAME);
    mapResultado = new HashMap<>();

    String destinatario = "";

    for (int i = 0; i < this.expedientesElectronicos.size(); i++) {
      this.expedienteElectronico = this.expedientesElectronicos.get(i);
      
      this.datosSolicitudPase = new PaseExpedienteRequest();

      datosSolicitudPase.setCodigoEE(this.expedienteElectronico.getCodigoCaratula());
      datosSolicitudPase.setUsuarioOrigen(loggedUsername);

      datosSolicitudPase.setMotivoPase(motivoExpediente.getValue());
      datosSolicitudPase.setEstadoSeleccionado(this.expedienteElectronico.getEstado());
      datosSolicitudPase.setSistemaOrigen(this.expedienteElectronico.getSistemaApoderado());

      // OLD
      /*
      if (usuarioRadio.isChecked()) {
        datosSolicitudPase.setEsUsuarioDestino(true);
        usuarioProductorInfo = (Usuario) this.usuario.getSelectedItem().getValue();
        datosSolicitudPase.setUsuarioDestino(usuarioProductorInfo.getUsername().trim());

        destinatario = usuario.getValue();
      } else if (this.reparticionRadio.isChecked()) {
        datosSolicitudPase.setEsReparticionDestino(true);
        datosSolicitudPase.setReparticionDestino(this.reparticionBusquedaSADE.getValue().trim());

        destinatario = reparticionBusquedaSADE.getValue().trim();
      } else if (this.sectorRadio.isChecked()) {
        datosSolicitudPase.setEsSectorDestino(true);
        datosSolicitudPase
            .setReparticionDestino(this.sectorReparticionBusquedaSADE.getValue().trim());
        datosSolicitudPase.setSectorDestino(this.sectorBusquedaSADE.getValue().trim());

        destinatario = this.sectorReparticionBusquedaSADE.getValue().trim() + "-"
            + this.sectorBusquedaSADE.getValue().trim();
      }
      */

		Map<String, String> detalle = new HashMap<String, String>();

		if (this.usuarioRadio.isChecked()) {
			detalle.put("destinatario", usuarioProductorInfo.getUsername());
			detalle.put(ConstantesWeb.USUARIO_SELECCIONADO, usuarioProductorInfo.getUsername());
			detalle.put("grupoSeleccionado", null);
			detalle.put("tareaGrupal", "noEsTareaGrupal");
			destinatario = usuarioSeleccionado.getUsername();
		} else if (this.reparticionRadio.isChecked()) {
			detalle.put("destinatario", this.reparticionBusquedaSADE.getValue());
			detalle.put("grupoSeleccionado", this.reparticionBusquedaSADE.getValue().trim() + "-"
					+ this.sectorMesaVirtual.getCodigo().trim().toUpperCase());
			detalle.put(ConstantesWeb.USUARIO_SELECCIONADO, null);
			detalle.put("tareaGrupal", "esTareaGrupal");
			destinatario = reparticionBusquedaSADE.getValue().trim();
		} else if (this.sectorRadio.isChecked()) {
			detalle.put("destinatario",
					this.sectorReparticionBusquedaSADE.getValue().trim() + "-" + this.sectorBusquedaSADE.getValue());
			detalle.put("grupoSeleccionado", this.sectorReparticionBusquedaSADE.getValue().trim() + "-"
					+ this.sectorBusquedaSADE.getValue().trim());
			detalle.put(ConstantesWeb.USUARIO_SELECCIONADO, null);
			detalle.put("tareaGrupal", "esTareaGrupal");
			destinatario = this.sectorReparticionBusquedaSADE.getValue().trim() + "-"
		            + this.sectorBusquedaSADE.getValue().trim();
		}

      Boolean isBloqueado = this.expedienteElectronico.getBloqueado();
      
    	    if (!isBloqueado) {
				bloqueoExpedienteService.bloquearExpediente(this.expedienteElectronico.getSistemaApoderado(),
						this.expedienteElectronico.getCodigoCaratula());
			}
    	    
    	    // Atencion - el estado se setea en forma interna en el servicio
			boolean result = rehabilitarExpedienteService.desarchivarExpediente(expedienteElectronico, loggedUsername,
					null, ConstantesWeb.ESTADO_TRAMITACION_PARA_REHABILITACION, motivoExpediente.getValue(), detalle);
			
			if (result) {
				this.mapResultado.put(this.expedienteElectronico.getCodigoCaratula() + " - Pase correcto.\n",
						this.expedienteElectronico);
			}
			else {
				this.mapResultado.put(this.expedienteElectronico.getCodigoCaratula() + " - Error en el pase.\n",
						this.expedienteElectronico);
			}
			
			try {
				bloqueoExpedienteService.desbloquearExpediente("EE", expedienteElectronico.getCodigoCaratula());
			} catch (Exception e) {
				// Do nothing
			}
    }

    String mensaje = "";
    List<String> errores = new ArrayList<String>();
    List<String> noErrores = new ArrayList<String>();
    StringBuffer buf = new StringBuffer();

    Iterator<Entry<String, ExpedienteElectronicoDTO>> i = this.mapResultado.entrySet().iterator();

    while (i.hasNext()) {
      Entry<String, ExpedienteElectronicoDTO> e = i.next();
      String key = e.getKey();
      if (key.contains("Error")) {
        errores.add(key);
      } else {
        noErrores.add(e.getKey());
      }
    }

    if (noErrores.size() > 0) {
      mensaje += "\n Se envió a: " + destinatario + " ";
      mensaje += "Se generó el pase de los expedientes: \n" + toStringErrores(noErrores);
    }
    if (errores.size() > 0) {
      mensaje += "\n  Ha ocurrido un error en los siguientes expedientes: \n"
          + toStringErrores(errores);
    }

    Messagebox.show(mensaje, Labels.getLabel("ee.general.information"), Messagebox.OK,
        Messagebox.INFORMATION);

    Clients.clearBusy();
    this.closeAssociatedWindow();
  }

  private String toStringErrores(List<String> xs) {
    String resultado = "";
    for (String s : xs) {
      resultado += s;
    }

    return resultado;
  }

  /**
   * Valida los datos antes de realizar el pase del expediente electrónico.
   */
  public void validarDatosEnvio() {
    super.validarMotivo();

    if ((this.usuarioRadio.isChecked() == false) && (this.reparticionRadio.isChecked() == false)
        && (this.sectorRadio.isChecked() == false)
        /*&& !(this.expedienteElectronico.getEstado().equals(ESTADO_GUARDA_TEMPORAL))*/) {
      throw new WrongValueException(destino, "Debe seleccionar un Usuario, Sector o Repartición");
    }

    this.usuarioProductorInfo = new Usuario();

    if (this.usuarioRadio.isChecked()) {
      if (this.usuarioSeleccionado == null) {
      	this.mensajeValidadorBandboxUsuario("Debe seleccionar un Usuario.");
        //throw new WrongValueException(this.inclBandboxUsuario, "Debe seleccionar un Usuario.");
      }
      usuarioProductorInfo = usuariosSADEService.getDatosUsuario(usuarioSeleccionado.getUsername());

      if (usuarioProductorInfo == null) {
      	this.mensajeValidadorBandboxUsuario("Debe seleccionar un usuario válido.");
        //throw new WrongValueException(this.inclBandboxUsuario, "Debe seleccionar un usuario válido.");
      }
    }

    if (this.reparticionRadio.isChecked()) {
      if ((this.reparticionBusquedaSADE.getValue() == null)
          || (this.reparticionBusquedaSADE.getValue() == "")
          || this.reparticionBusquedaSADE.getValue().isEmpty()) {
        throw new WrongValueException(this.reparticionBusquedaSADE,
            "Debe seleccionar una Repartición válida.");
      }

      ReparticionBean reparticion = this.reparticionServ
          .buscarReparticionPorCodigo(this.reparticionBusquedaSADE.getValue().trim());

      if (reparticion == null || reparticion.getVigenciaHasta() == null
          || reparticion.getVigenciaHasta().before(new Date())
          || reparticion.getVigenciaDesde().after(new Date())) {
        throw new WrongValueException(this.reparticionBusquedaSADE,
            Labels.getLabel("ee.general.reparticionInvalida"));
      }

      List<SectorInternoBean> sectores = sectorInternoServ
          .buscarSectoresPorReparticion(reparticion.getId());

      for (SectorInternoBean sector : sectores) {
        if (sector.isMesaVirtual()) {
          sectorMesaVirtual = sector;
        }
      }

      if (sectorMesaVirtual == null) {
        throw new WrongValueException(this.reparticionBusquedaSADE,
            "La Repartición ingresada no tiene una mesa virtual.");
      }
    }

    if (this.sectorRadio.isChecked()) {
      if ((this.sectorReparticionBusquedaSADE == null)
          || (this.sectorReparticionBusquedaSADE.getValue() == "")
          || this.sectorReparticionBusquedaSADE.getValue().isEmpty()) {
        throw new WrongValueException(this.sectorReparticionBusquedaSADE,
            "Debe seleccionar una Repartición válida.");
      }

      if ((this.sectorBusquedaSADE == null) || (this.sectorBusquedaSADE.getValue() == "")
          || this.sectorBusquedaSADE.getValue().isEmpty()) {
        throw new WrongValueException(this.sectorBusquedaSADE,
            "Debe seleccionar un Sector Interno válido.");
      }
      

    }
    
    if(StringUtils.isBlank(numeroDocumento.getValue())) {
      throw new WrongValueException(this.etiquetaDocAsociar,
          "Debes vincular un documento para asociar al expediente.");      	
    }
  }

  public void onCancelar() {
    this.envioAdministracionWindow.detach();
  }

  /**
   * Se habilitan y deshabilitan los campos según que opción se eliga.
   */
  public void onUsuarioClick() {
  	 disabledBandbox(false);
 //   this.usuario.setDisabled(false);
    this.sectorBusquedaSADE.setDisabled(true);
    this.sectorRadio.setChecked(false);
    this.sectorReparticionBusquedaSADE.setDisabled(true);
    this.sectorReparticionBusquedaSADE.setValue(null);
    this.reparticionBusquedaSADE.setValue(null);
    this.reparticionRadio.setChecked(false);
    this.reparticionBusquedaSADE.setDisabled(true);
    this.sectorBusquedaSADE.setValue(null);
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

  public void onSectorClick() {
    if ((this.sectorRadio.isChecked()) && (this.sectorReparticionBusquedaSADE.getValue() != "")
        && (this.sectorReparticionBusquedaSADE.getValue() != null)) {
      this.sectorBusquedaSADE.setDisabled(false);
    } else {
      this.sectorBusquedaSADE.setDisabled(true);
    }

     this.disabledBandbox(true);
     this.cleanBandboxUsuario();
//    this.usuario.setValue(null);
    this.usuarioRadio.setChecked(false);
    this.sectorReparticionBusquedaSADE.setDisabled(false);
    this.reparticionRadio.setChecked(false);
    this.reparticionBusquedaSADE.setDisabled(true);
  }

  public void onReparticionClick() {
    this.reparticionBusquedaSADE.setDisabled(false);
    this.sectorBusquedaSADE.setDisabled(true);
    this.sectorReparticionBusquedaSADE.setValue(null);
    this.sectorRadio.setChecked(false);
    this.sectorReparticionBusquedaSADE.setDisabled(true);
    this.disabledBandbox(true);
    this.cleanBandboxUsuario();
//    this.usuario.setValue(null);
//    this.usuario.setDisabled(true);
    this.usuarioRadio.setChecked(false);
    this.sectorBusquedaSADE.setValue(null);
  }

  public Bandbox getReparticionBusquedaSADE() {
    return reparticionBusquedaSADE;
  }

  public void setReparticionBusquedaSADE(Bandbox reparticionBusquedaSADE) {
    this.reparticionBusquedaSADE = reparticionBusquedaSADE;
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

  public Listbox getSectoresReparticionesBusquedaSADEListbox() {
    return sectoresReparticionesBusquedaSADEListbox;
  }

  public void setSectoresReparticionesBusquedaSADEListbox(
      Listbox sectoresReparticionesBusquedaSADEListbox) {
    this.sectoresReparticionesBusquedaSADEListbox = sectoresReparticionesBusquedaSADEListbox;
  }

  public Grid getGrillaDestino() {
    return grillaDestino;
  }

  public void setGrillaDestino(Grid grillaDestino) {
    this.grillaDestino = grillaDestino;
  }

  public void setHistorialOperacionService(HistorialOperacionService historialOperacionService) {
    this.historialOperacionService = historialOperacionService;
  }

  public HistorialOperacionService getHistorialOperacionService() {
    return historialOperacionService;
  }
 

  public HistorialOperacionDTO getHistorialOperacion() {
    return historialOperacion;
  }

  public void setHistorialOperacion(HistorialOperacionDTO historialOperacion) {
    this.historialOperacion = historialOperacion;
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

  public Window getEnvioAdministracionWindow() {
    return envioAdministracionWindow;
  }

  public void setEnvioAdministracionWindow(Window envioAdministracionWindow) {
    this.envioAdministracionWindow = envioAdministracionWindow;
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
    return getUsuariosGEDO();
  }

  public Combobox getReparticion() {
    return reparticion;
  }

  public void setReparticion(Combobox reparticion) {
    this.reparticion = reparticion;
  }

  public String getSelectedReparticiones() {
    return selectedReparticiones;
  }

  public void setSelectedReparticiones(String selectedReparticiones) {
    this.selectedReparticiones = selectedReparticiones;
  }

  public Combobox getSector() {
    return sector;
  }

  public void setSector(Combobox sector) {
    this.sector = sector;
  }

  public SectorInternoBean getSelectedSectores() {
    return selectedSectores;
  }

  public void setSelectedSectores(SectorInternoBean selectedSectores) {
    this.selectedSectores = selectedSectores;
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

  public Combobox getReparticionSector() {
    return reparticionSector;
  }

  public void setReparticionSector(Combobox reparticionSector) {
    this.reparticionSector = reparticionSector;
  }

  public String getSelectedReparticionesSector() {
    return selectedReparticionesSector;
  }

  public void setSelectedReparticionesSector(String selectedReparticionesSector) {
    this.selectedReparticionesSector = selectedReparticionesSector;
  }

  public ExpedienteElectronicoDTO getExpedienteElectronico() {
    return expedienteElectronico;
  }

  public void setExpedienteElectronico(ExpedienteElectronicoDTO expedienteElectronico) {
    this.expedienteElectronico = expedienteElectronico;
  }

  public WorkFlowService getWorkFlowService() {
    return this.workFlowService;
  }

  public void setWorkFlowService(WorkFlowService workFlowService) {
    this.workFlowService = workFlowService;
  }
  
  public void onBorrarDocumentoVinculado() {
  	
  	getExpedienteElectronico().setDocumentos(
  	getExpedienteElectronico().getDocumentos()
		.stream().filter( doc-> doc.getIdTask() == null || !doc.getIdTask().equals("rehabilitar"))
		.collect(Collectors.toList()));
  	
  	//btnBorrarDoc.setDisabled(true);
  	btnBorrarDoc.setVisible(false);
  	numeroDocumento.setValue(StringUtils.EMPTY);
  }
  
	public void onChanging$sectorReparticionBusquedaSADE(InputEvent e) {
		Events.sendEvent(Events.ON_CHANGING,sectorReparticionBusquedaSADE.getChildren().get(0), e);
	}
	
	public void onChanging$sectorBusquedaSADE(InputEvent e) {
		Events.sendEvent(Events.ON_CHANGING,sectorBusquedaSADE.getChildren().get(0), e);
	}
	
  
  @SuppressWarnings("rawtypes")
	public class EnvioAdministracionListener implements EventListener{

		@SuppressWarnings("unchecked")
		@Override
		public void onEvent(Event event) throws Exception {
			
			if(event.getName().equals(VincularDocumentoComposer.ON_MENSAJE_ACORDEON)) {
				
				Map<String, Object> map = (Map<String, Object>) event.getData();
				setExpedienteElectronico((ExpedienteElectronicoDTO) map.get("ee"));
				
				numeroDocumento.setValue(getNumeroDocumento());
				btnBorrarDoc.setVisible(true);
				//btnBorrarDoc.setDisabled(false);
			}else if(event.getName().equals(BandBoxUsuarioComposer.ON_SELECT_USUARIO)) {
				usuarioSeleccionado = (UsuarioReducido) event.getData();
			}
		}

		private String getNumeroDocumento() {
			Optional<String> numeroOpt = getExpedienteElectronico().getDocumentos()
					.stream().filter( doc-> doc.getIdTask() != null && doc.getIdTask().equals("rehabilitar"))
					.map(DocumentoDTO::getNumeroSade).findFirst();
			
			return numeroOpt.isPresent() ? numeroOpt.get() : "No se pudo cargar";
		}
				
  }

}
