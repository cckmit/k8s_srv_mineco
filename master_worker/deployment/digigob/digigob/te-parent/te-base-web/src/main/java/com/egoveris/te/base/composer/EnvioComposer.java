package com.egoveris.te.base.composer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.TaskQuery;
import org.jbpm.api.task.Participation;
import org.jbpm.api.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Include;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

import com.egoveris.edt.ws.service.IExternalPeriodoLicenciaService;
import com.egoveris.plugins.manager.PluginManager;
import com.egoveris.sharedorganismo.base.model.ReparticionBean;
import com.egoveris.sharedorganismo.base.model.SectorInternoBean;
import com.egoveris.sharedorganismo.base.service.ReparticionServ;
import com.egoveris.sharedorganismo.base.service.SectorInternoServ;
import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.sharedsecurity.base.model.UsuarioReducido;
import com.egoveris.te.base.composer.genrico.BandBoxUsuarioComposer;
import com.egoveris.te.base.exception.VariableWorkFlowNoExisteException;
import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.model.ExpedienteAsociadoEntDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.HistorialOperacionDTO;
import com.egoveris.te.base.model.PropertyConfigurationDTO;
import com.egoveris.te.base.model.SubProcesoOperacionDTO;
import com.egoveris.te.base.model.TareasUsuario;
import com.egoveris.te.base.model.TrataTipoResultadoDTO;
import com.egoveris.te.base.service.ControlTransaccionService;
import com.egoveris.te.base.service.FusionService;
import com.egoveris.te.base.service.HistorialOperacionService;
import com.egoveris.te.base.service.IPropertyConfigurationService;
import com.egoveris.te.base.service.PaseExpedienteService;
import com.egoveris.te.base.service.SubprocessService;
import com.egoveris.te.base.service.SupervisadosService;
import com.egoveris.te.base.service.TramitacionConjuntaService;
import com.egoveris.te.base.service.UsuariosSADEService;
import com.egoveris.te.base.service.WorkFlowService;
import com.egoveris.te.base.service.expediente.ExpedienteAsociadoService;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.service.iface.IOperacionDocumentoService;
import com.egoveris.te.base.util.ConstantesCommon;
import com.egoveris.te.base.util.ConstantesServicios;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.MantainerPrefix;
import com.egoveris.te.base.util.ScriptType;
import com.egoveris.te.base.util.WorkFlow;
import com.egoveris.te.base.util.WorkFlowScriptUtils;

@SuppressWarnings({"deprecation","unused"})
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class EnvioComposer extends EnvioExpedienteComposer {

	private static final long serialVersionUID = 6266287038479709703L;

	private static final String ESTADO_GUARDA_TEMPORAL = "Guarda Temporal";
	private static final String TRAMITACION = "Tramitacion";
	private static final String COMUNICACION = "Comunicacion";
	private static final String EJECUCION = "Ejecucion";
	private static final String CIERRE = "Cierre";
	private static final String FORK = "forkEach";
	private static Logger logger = LoggerFactory.getLogger(EnvioComposer.class);
	private static final String MOTIVO_DESVINCULACION_TRAMITACION_CONJUNTA = "Desvinculación en Tramitacion Conjunta";
	private boolean esConComunicacion = false;
	private AnnotateDataBinder binder;
	private List<String> destinatarioList;
	@Autowired
	private Combobox estado;
//	@Autowired
//	private Combobox usuario;
	@Autowired
	private Include inclBandboxUsuario;
	
	@Autowired
	private Include inclBandboxOrganismo;
	
	@Autowired	   
	private Combobox reparticion;
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
	private Grid grillaDestino;
	@Autowired
	private Bandbox sectorBusquedaSADE;
	private ExpedienteElectronicoDTO expedienteElectronico;
	private String usuarioApoderado = null;
	private Usuario usuarioProductorInfo;
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Map<String, String> detalles = new HashMap();
	private String estadoAnterior;
	private String loggedUsername;
	
	private UsuarioReducido usuarioSeleccionado;
			   
	private ReparticionBean organismoSeleccionado;
	private SectorInternoBean sectorSeleccionado;
	
	private SectorInternoBean sectorMesaVirtual;
	@WireVariable(ConstantesServicios.PROCESS_ENGINE_SERVICE)
	private ProcessEngine processEngine;
	@WireVariable(ConstantesServicios.SUPERVISADOS_SERVICE)
	private SupervisadosService supervisadosService;
	protected Task workingTask = null;
	private HistorialOperacionDTO historialOperacion = new HistorialOperacionDTO();
	private ExpedienteAsociadoService expedienteAsociadoService;
	@WireVariable(ConstantesServicios.FUSION_SERVICE)
	private FusionService fusionService;
	private List<ReparticionBean> listaSectorReparticionSADESeleccionada;
	@Autowired
	private Listbox sectoresReparticionesBusquedaSADEListbox;
	@Autowired
	private Textbox textoSectorBusquedaSADE;
	@Autowired
	private Textbox textoReparticionBusquedaSADE;

	// windows

	@WireVariable(ConstantesServicios.PLUGIN_MANAGER)
	private PluginManager pm;

	@WireVariable(ConstantesServicios.WORKFLOW_SERVICE)
	private WorkFlowService workflowService;

	@Autowired
	private Window realizarPaseyComunicarComposer;

	/**
	 * Defino los servicios que voy a utilizar
	 */
	@WireVariable(ConstantesServicios.EXP_ELECTRONICO_SERVICE)
	private ExpedienteElectronicoService expedienteElectronicoService;

	@WireVariable(ConstantesServicios.HISTORIAL_OPERACION_SERVICE)
	private HistorialOperacionService historialOperacionService;

	@WireVariable(ConstantesServicios.USUARIOS_SADE_SERVICE)
	private UsuariosSADEService usuariosSADEService;

	@WireVariable(ConstantesServicios.EXT_PERIODO_LICENCIA_SERVICE)
	private IExternalPeriodoLicenciaService periodoLicenciaService;

	@WireVariable(ConstantesServicios.SECTOR_INTERNO_SERVICE)
	private SectorInternoServ sectorInternoServ;

	@WireVariable(ConstantesServicios.TRAMITACION_CONJUNTA_SERVICE)
	private TramitacionConjuntaService tramitacionConjuntaService;

	@WireVariable(ConstantesServicios.PASE_EXPEDIENTE_SERVICE)
	private PaseExpedienteService paseExpedienteService;

	@WireVariable(ConstantesServicios.REPARTICION_SERVICE)
	private ReparticionServ reparticionServ;

	@WireVariable(ConstantesServicios.WORKFLOW_SERVICE)
	private WorkFlowService workFlowService;

	@Autowired
	private String reparticionGT;

	@WireVariable(ConstantesServicios.CONTROL_TRANSACCION_SERVICE)
	private ControlTransaccionService controlTransaccionService;

	@WireVariable(ConstantesServicios.SUBPROCESS_SERVICE)
	private SubprocessService subprocessService;

	@WireVariable(ConstantesServicios.OPERACION_DOC_SERVICE)
	private IOperacionDocumentoService operacionDocumentoService;

	@Wire("#tbxIdTransaction")
	private Textbox tbxIdTransaction;

	@Wire("#tbxNameForm")
	private Textbox tbxNameForm;
	
	@WireVariable(ConstantesServicios.PROPERTY_CONF_SERVICE)
	private IPropertyConfigurationService propertyConfigurationService;

	// INI TIPO DE RESULTADO
	private Combobox cboResultType;
	private List<PropertyConfigurationDTO> resultTypes;
	// FIN TIPO DE RESULTADO
	private Window tramitacionWindow;

	public static final String ONSEND_REPARTICION_BEAN = "onSendReparticionBean";
	/**
	 * Según el estado en el que este, cargo la lista de estados con los estados
	 * a los que se puede pasar. Esto se logra con el outcomes de jbpm, que
	 * devuelve todas las salidas (transiciones) que tiene la tarea acctual
	 *
	 * @exception se
	 *                utiliza throws Exception por el doAfterCompose de ZK el
	 *                cual esta contenido en el GenericForwardComposer
	 */
	public void doAfterCompose(Component component) throws Exception {
		super.doAfterCompose(component);
		component.addEventListener(Events.ON_USER, new EnvioExpedienteElectronicoOnNotifyWindowListener(this));
		component.addEventListener(Events.ON_NOTIFY, new EnvioExpedienteElectronicoOnNotifyWindowListener(this));
		
		Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));

		this.setWorkingTask((Task) component.getDesktop().getAttribute("selectedTask"));
		tramitacionWindow = (Window) Executions.getCurrent().getArg().get("tramitacionWindow");
		this.usuarioRadio.setSelected(false);
		this.sectorRadio.setSelected(false);
		this.reparticionRadio.setSelected(false);
		
		configurarBandboxUsuario(component, true);
		configurarBandboxOrganismo(component, true);
		
//		this.usuario.setDisabled(true);
		this.sectorBusquedaSADE.setDisabled(true);

		this.reparticionBusquedaSADE.setDisabled(true);

		this.expedienteElectronico = (ExpedienteElectronicoDTO) Executions.getCurrent().getArg()
				.get("expedienteElectronico");
		
		String estadoDefault = null;

		String taskId = this.getWorkingTask().getId();
		Set<String> outcomes = this.processEngine.getTaskService().getOutcomes(taskId);
		
		outcomes.remove(ConstantesCommon.ESTADO_SUBSANACION);
		if(!outcomes.isEmpty()) {
			estadoDefault = outcomes.stream().findFirst().get();
		}
		
		/*
		 * En la ventana de enviar pase, si la tarea esta en estado archivo.
		 * solo debe visualizarce el estado archivo en el combo. Si la tarea
		 * esta en otro estado debe tener disponibles las demas opciones de
		 * estado en el combo. Ademas no de visualizarce la opcion cierre que es
		 * el fin del workflow, no un estado.
		 */
		// ArrayList<UsuarioReducido> a =new
		// ArrayList<UsuarioReducido>(getUsuariosSADEService().getTodosLosUsuarios());
//		usuario.setModel(
//				ListModels.toListSubModel(new ListModelList(this.getUsuariosSADEService().getTodosLosUsuarios()),
//						new UsuariosComparatorConsultaExpediente(), 30));
//		this.binder = new AnnotateDataBinder(usuario);
//		binder.loadComponent(usuario);
		
		if (this.getWorkingTask().getActivityName().equals(ESTADO_GUARDA_TEMPORAL)) {
			for (String transicion : outcomes) {
				if (transicion.equals(ESTADO_GUARDA_TEMPORAL)) {
					this.estado.appendItem(transicion);
				}
			}
		} else {
			for (String transicion : outcomes) {
				if (!transicion.equals(CIERRE) && !(transicion.equals(FORK))) {
					this.estado.appendItem(transicion);
				}
			}
		}
		if(outcomes.contains(this.getWorkingTask().getActivityName())){			
			this.estado.setValue(this.getWorkingTask().getActivityName());
		}
		
		if(StringUtils.isBlank(this.estado.getValue())) {
			this.estado.setValue(estadoDefault);
		}
		
		this.estadoAnterior = (String) Executions.getCurrent().getArg().get("estadoAnterior");

		// INI TIPO DE RESULTADO
		resultTypes = new ArrayList<>();

		// Load the combo values
		if (expedienteElectronico != null && expedienteElectronico.getTrata() != null) {
			for (TrataTipoResultadoDTO trataTipoResultadoDTO : expedienteElectronico.getTrata()
					.getTipoResultadosTrata()) {
				if (trataTipoResultadoDTO.getProperty() != null) {
					resultTypes.add(trataTipoResultadoDTO.getProperty());
				}
			}

			// Empty property
			if (!resultTypes.isEmpty()) {
				PropertyConfigurationDTO emptyProperty = new PropertyConfigurationDTO();
				emptyProperty.setValor(" ");
				resultTypes.add(0, emptyProperty);
			}
		}
		// FIN TIPO DE RESULTADO
	}

	private void configurarBandboxUsuario(Component component, boolean deshabilitado) {
    inclBandboxUsuario.setDynamicProperty(BandBoxUsuarioComposer.DISABLED_BANDBOX, deshabilitado);
    inclBandboxUsuario.setDynamicProperty(BandBoxUsuarioComposer.COMPONENTE_PADRE, component);
    inclBandboxUsuario.setSrc(BandBoxUsuarioComposer.SRC);
    
		component.addEventListener(BandBoxUsuarioComposer.ON_SELECT_USUARIO,
				new EnvioExpedienteElectronicoOnNotifyWindowListener(this));

    
	}

	private void configurarBandboxOrganismo(Component component, boolean deshabilitado) {
		inclBandboxOrganismo.setDynamicProperty(BandboxOrganismoComposer.DISABLED_BANDBOX, deshabilitado);
		inclBandboxOrganismo.setDynamicProperty(BandboxOrganismoComposer.COMPONENTE_PADRE, component);
		inclBandboxOrganismo.setSrc("/generico/bandboxOrganismo.zul");
    
		component.addEventListener(BandboxOrganismoComposer.ON_SELECT_ORGANISMO,
				new EnvioExpedienteElectronicoOnNotifyWindowListener(this));
		
    
	}
	
	public void onEnviar()  {
		//TODO: validar el valor none
		if (usuarioSeleccionado!= null && usuarioSeleccionado.getUsername() == "NONE") {
			Map<String, Object> map = new HashMap<>();
			map.put(ConstantesCommon.SCRIPT_ID_TRANS_FFCC, tbxIdTransaction.getValue());
			map.put(ConstantesCommon.SCRIPT_NAME_FFCC, tbxNameForm.getValue());
			map.put(ConstantesCommon.SCRIPT_WINDOW_PASE, this.getEnvioWindow());
			try { 
				Map<String, Object> map2 = WorkFlowScriptUtils.getInstance().executeScript(ScriptType.DECISION,
						expedienteElectronico, map, Executions.getCurrent().getRemoteUser());
				String nexState = (String) map2.get(ConstantesWeb.RESULT);
				estado.setValue(nexState);
				logger.warn("ConstantesWeb.SESSION_USERNAME: ", ConstantesWeb.SESSION_USERNAME);
				logger.warn("SESSION: ", Executions.getCurrent().getDesktop().getSession().getAttributes().toString());
				loggedUsername = (String) Executions.getCurrent().getDesktop().getSession()
						.getAttribute(ConstantesWeb.SESSION_USERNAME); 
				logger.warn("loggedUsername: ", loggedUsername);
				Integer tipoCarga = this.expedienteElectronico.getTrata().getTipoCarga(); 
				Integer uno = 1;
				if (tipoCarga.equals(uno)) {
					asignacionPorCarga();
				} 
				enviar(); 
			} catch (Exception e) {
				logger.error("", e);
				Messagebox.show(Labels.getLabel("ee.nuevasolicitud.error.informacion"), Labels.getLabel("ee.tramitacion.titulo.pase"),
						Messagebox.OK, Messagebox.ERROR);
			}
		} else {
			super.definirMotivo();
			validarDatosEnvio();
			detalles = new HashMap<>();
			detalles.put("estadoAnterior", this.estadoAnterior);
			// este es obtenible también desde el estado actual del expediente
			// electrónico.
			detalles.put("estadoAnteriorParalelo", null);
			detalles.put("destinatario", reparticionGT);
			loggedUsername = (String) Executions.getCurrent().getDesktop().getSession()
					.getAttribute(ConstantesWeb.SESSION_USERNAME);
			detalles.put(ConstantesWeb.LOGGED_USERNAME, loggedUsername);

			// New logic - Result
			detalles.put("resultLabel", null);
			detalles.put("resultValue", null);

			if (cboResultType != null && cboResultType.getSelectedItem() != null
					&& !StringUtils.isBlank(cboResultType.getSelectedItem().getLabel())) {
				detalles.put("resultLabel", cboResultType.getSelectedItem().getLabel());
				detalles.put("resultValue", cboResultType.getSelectedItem().getValue());
			}

			/**
			 * Si el estado seleccionado es archivo, se envía al jefe para
			 * aprobar. Actualmente se envía al grupo "Archivo" porque no esta
			 * la funcionalidad para saber quien es el jefe de quien
			 */
			if (this.estado.getValue().equals(ESTADO_GUARDA_TEMPORAL)) {
				// New logic - Result type validation
//				if (cboResultType == null || cboResultType.getSelectedItem() == null
//						|| StringUtils.isBlank(cboResultType.getSelectedItem().getLabel())) {
//					throw new WrongValueException(cboResultType, "Debe seleccionar un Tipo de Resultado");
//				} else {
					if ((this.expedienteElectronico.getEsCabeceraTC() != null)
							&& !this.expedienteElectronico.getEsCabeceraTC()) {
						try {
							preguntarEnvioArchivo();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} else {
						try {
							preguntarEnvioArchivoEnConjunta();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
//				}
			} else if (this.usuarioRadio.isChecked()) {

				/*
				 * Valido si el usuario está de vacaciones y quien es el
				 * apoderado.
				 */
				boolean licenciaActiva = periodoLicenciaService.licenciaActiva(usuarioProductorInfo.getUsername());

				if (licenciaActiva) {
					usuarioApoderado = periodoLicenciaService
							.obtenerApoderadoUsuarioLicencia(usuarioProductorInfo.getUsername());
				}

				if (licenciaActiva && usuarioApoderado != null) {
					/*
					 * Si esta de licencia debo preguntar si desea enviarlo al
					 * apoderado.
					 */

					// CAMBIO 2 - FIX EXPEDIENTES DESAPARECIDOS EN PRODUCCION
					detalles.put("destinatario", usuarioApoderado);
					detalles.put(ConstantesWeb.USUARIO_SELECCIONADO, usuarioApoderado);
					detalles.put("grupoSeleccionado", null);
					detalles.put("tareaGrupal", "noEsTareaGrupal");
					try {
						preguntarEnvioApoderado();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				} else if (licenciaActiva) {
					// El usuario esta de licencia, pero los apoderados tambien
					// estan de licencia
					Messagebox.show(
							Labels.getLabel("ee.licencia.sinApoderado.value", new String[] { usuarioProductorInfo.getUsername() }),
							Labels.getLabel("ee.licencia.question.title"), Messagebox.OK, Messagebox.EXCLAMATION);
				} else {
					/*
					 * Si el usuario no está de licencia se realizara un pase
					 * común.
					 */
					try {
						enviar();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} else {
				/**
				 * Si el estado seleccionado no es "Archivo" y no es un usuario
				 * se realizara un pase común.
				 */
				try {
					enviar();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			// ***********************************************************************
			// ** JIRA BISADE-2515:
			// https://quark.everis.com/jira/browse/BISADE-2515
			// ** - Se crea el parámetro "cacheQueries" para la consulta de
			// inbox no
			// cachee cambios de statemachine.
			// ***********************************************************************
			Executions.getCurrent().getDesktop().setAttribute("cacheQueries", new Boolean(false));
		}

	}

	/**
	 * 
	 */
	private void asignacionPorCarga() {
		TareasUsuario usuarioSiguiente = null;
		String sector = this.sectorRadio.getValue();
		List<TareasUsuario> tareas = new ArrayList<TareasUsuario>();
		tareas = supervisadosService.obtenerUsuarioPorSector(sector);
		Collections.sort(tareas);
		for (TareasUsuario mapTareas : tareas) {
			// se valida si esta de vacaciones
			boolean licenciaActiva = periodoLicenciaService
					.licenciaActiva(mapTareas.getDatosUsuario().getUsername());
			if (licenciaActiva) {
				usuarioSiguiente = mapTareas;
			} else {
				usuarioSiguiente = mapTareas;
				break;
			}
		}
		usuarioRadio.setValue(loggedUsername);
		usuarioRadio.setChecked(true);
		if (null != usuarioSiguiente) {
			usuarioProductorInfo = usuariosSADEService
					.getDatosUsuario(usuarioSiguiente.getDatosUsuario().getUsername());
		}else{
			usuarioProductorInfo = usuariosSADEService
					.getDatosUsuario(loggedUsername);
		}
			
	}

	/**
	 * @throws InterruptedException
	 *             Abre un popup para completar los datos de la comunicacion
	 */

	public synchronized void onRealizarPaseyComunicar() {

		super.definirMotivo();
		esConComunicacion = true;

		validarDatosEnvio();

		HashMap<String, Object> hma = new HashMap<>();
		hma.put("expedienteElectronico", this.expedienteElectronico);
		this.destinatarioList = new ArrayList<String>();
		hma.put("destinatarioList", this.destinatarioList);

		this.realizarPaseyComunicarComposer = (Window) Executions.createComponents("/pase/realizarPaseyComunicar.zul",
				null, hma);
		this.realizarPaseyComunicarComposer.setParent(this.envioWindow);
		this.realizarPaseyComunicarComposer.setPosition("center");
		this.realizarPaseyComunicarComposer.setClosable(true);
		this.realizarPaseyComunicarComposer.doModal();
	}

	private void mostrarForegroundBloqueante() {
		Clients.showBusy(Labels.getLabel("ee.tramitacion.enviarExpedienteElectronico.value"));
	}

	/**
	 * Al presionar el botón enviar se realiza el PASE ELECTRONICO del
	 * Expediente Electrónico.
	 */
	public void enviar() throws InterruptedException {

		if (this.usuarioRadio.isChecked()) {
			sectorMesaVirtual = null;
			//INI - Recuperar usuario
			if(null == usuarioProductorInfo || null == usuarioProductorInfo.getUsername()){
				logger.warn("usuarioProductorInfo.getUsername() = null: ");
				usuarioProductorInfo = usuariosSADEService
				.getDatosUsuario((String) Executions.getCurrent().getDesktop().getSession()
						.getAttribute(ConstantesWeb.SESSION_USERNAME));
				logger.warn("usuarioProductorInfo.getUsername(): ",usuarioProductorInfo.getUsername());
			}
			//FIN - Recuperar usuario
			detalles.put("destinatario", usuarioProductorInfo.getUsername());

			// CAMBIO 3 - FIX EXPEDIENTES DESAPARECIDOS EN PRODUCCION
			detalles.put(ConstantesWeb.USUARIO_SELECCIONADO, usuarioProductorInfo.getUsername());
			detalles.put("grupoSeleccionado", null);
			detalles.put("tareaGrupal", "noEsTareaGrupal");

			if ((this.expedienteElectronico.getEsCabeceraTC() != null)
					&& !this.expedienteElectronico.getEsCabeceraTC()) {
				// Esto se coloca para bloquear la ventana de atras mientras se
				// procesa el pase.
				this.mostrarForegroundBloqueante();
				Events.echoEvent(Events.ON_USER, this.self, "enviarExpedienteElectronico");
			} else {
				this.mostrarForegroundBloqueante();
				Events.echoEvent(Events.ON_USER, this.self, "enviarTramitacionConjunta");
			}
		} else if (this.reparticionRadio.isChecked()) {
			detalles.put("destinatario", this.reparticionBusquedaSADE.getValue());
			detalles.put("grupoSeleccionado", this.reparticionBusquedaSADE.getValue().trim() + "-"
					+ this.sectorMesaVirtual.getCodigo().trim().toUpperCase());
			detalles.put(ConstantesWeb.USUARIO_SELECCIONADO, null);
			detalles.put("tareaGrupal", "esTareaGrupal");

			// Esto se coloca para bloquear la ventana de atras mientras se
			// procesa el pase
			if ((this.expedienteElectronico.getEsCabeceraTC() != null)
					&& !this.expedienteElectronico.getEsCabeceraTC()) {
				this.mostrarForegroundBloqueante();
				Events.echoEvent(Events.ON_USER, this.self, "enviarExpedienteElectronico");
			} else {
				this.mostrarForegroundBloqueante();
				Events.echoEvent(Events.ON_USER, this.self, "enviarTramitacionConjunta");
			}
		} else if (this.sectorRadio.isChecked()) {
			// Obtengo repartición por medio de su código
			sectorMesaVirtual = null;
			detalles.put("destinatario",
					this.organismoSeleccionado.getCodigo().trim() + "-" + this.sectorBusquedaSADE.getValue());
			detalles.put("grupoSeleccionado", (!organismoSeleccionadoIsNull()?this.organismoSeleccionado.getCodigo().trim():"") + "-"
					+ this.sectorBusquedaSADE.getValue().trim());
			detalles.put(ConstantesWeb.USUARIO_SELECCIONADO, null);
			detalles.put("tareaGrupal", "esTareaGrupal");

			// Esto se coloca para bloquear la ventana de atras mientras se
			// procesa el pase
			if ((this.expedienteElectronico.getEsCabeceraTC() != null)
					&& !this.expedienteElectronico.getEsCabeceraTC()) {
				this.mostrarForegroundBloqueante();
				Events.echoEvent(Events.ON_USER, this.self, "enviarExpedienteElectronico");
			} else {
				this.mostrarForegroundBloqueante();
				Events.echoEvent(Events.ON_USER, this.self, "enviarTramitacionConjunta");
			}
		} else {
			Messagebox.show(Labels.getLabel("ee.nuevasolicitud.envio"),
					Labels.getLabel("ee.subsanacion.msg.title.errorMayus"), Messagebox.OK, Messagebox.ERROR);
		}
	}

	/**
	 * @param usuarioProductorInfo
	 * @param estadoSeleccionado
	 * @param detalles
	 * @param estadoAnterior
	 * @param loggedUsername
	 * @param sectorMesaVirtual2
	 * @throws WrongValueException
	 * @throws InterruptedException
	 */
	@Transactional(propagation = Propagation.REQUIRED)
	private void generarPaseExpedienteElectronico(Usuario usuarioProductorInfo, String estadoSeleccionado,
			Map<String, String> detalles, String estadoAnterior, String loggedUsername,
			SectorInternoBean sectorMesaVirtual2) throws WrongValueException, InterruptedException {
		try {
			super.definirMotivo();

			if (esConComunicacion) {
				StringBuffer destinatarios = new StringBuffer();
				destinatarios.append("Se envió una comunicación a: ");

				for (String destinatario : this.destinatarioList) {
					destinatarios.append(destinatario);
					destinatarios.append(", ");
				}

				destinatarios.append(" se vinculará a la brevedad. ");
				detalles.put("idListDestinatarios", destinatarios.toString());
			}

			detalles.put("WEBCONTEXTPASE", "SI");

			this.controlTransaccionService.save(getProcessEngine(), workingTask, expedienteElectronico, loggedUsername,
					this.estado.getValue(), this.motivoExpedienteStr, detalles);

			String caratula = expedienteElectronico.getCodigoCaratula();

			/**
			 * Si el estado seleccionado es distinto al estado anterior avanza
			 * la task en el workflow. No se realiza el assignne de la task por
			 * que el mismo se hace en el handler asignarTarea.java.
			 */
			String mensaje = "";

			String asignacionUsuario;
			String asignacionGrupo;

			if (this.usuarioRadio.isChecked()) {
				if (usuarioApoderado != null) {
					asignacionUsuario = usuarioApoderado;
					mensaje = "Se generó el pase del expediente: " + caratula + ". Se envió al usuario apoderado: "
							+ asignacionUsuario + " en reemplazo por la licencia de "
							+ usuarioProductorInfo.getUsername();
				} else {
					asignacionUsuario = usuarioProductorInfo.getUsername();
					mensaje = "Se generó el pase del expediente: " + caratula + " , se envió al usuario: "
							+ asignacionUsuario;
				}
			} else {
				if (this.sectorRadio.isChecked()) {
					asignacionGrupo = (!organismoSeleccionadoIsNull()?this.organismoSeleccionado.getCodigo().trim():"") + "-"
					+ this.sectorBusquedaSADE.getValue();
					mensaje = "Se generó el pase del expediente: " + caratula + " , se envió al sector: "
							+ asignacionGrupo;
				} else if (this.reparticionRadio.isChecked()) {
					asignacionGrupo = this.reparticionBusquedaSADE.getValue().trim() + "-"
							+ sectorMesaVirtual2.getCodigo().trim().toUpperCase();
					mensaje = "Se generó el pase del expediente: " + caratula + " , se envió a la repartición: "
							+ asignacionGrupo;
				}
			}

			if (estadoSeleccionado.equals(ESTADO_GUARDA_TEMPORAL)) {
				mensaje = "El expediente: " + caratula + " , se ha guardado temporalmente de forma correcta.";
			}

			Messagebox.show(mensaje, Labels.getLabel("ee.general.information"), Messagebox.OK, Messagebox.INFORMATION);
			
			// 16-04-2020: The wash returns
			if (tramitacionWindow != null) {
				tramitacionWindow.onClose();
				Events.sendEvent(Events.ON_USER, tramitacionWindow, "refreshInbox");
			}
		} catch (RemoteAccessException e) {
			logger.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), Labels.getLabel("ee.general.advertencia"), Messagebox.OK, Messagebox.ERROR);
		} catch (Exception e) {
			logger.error("Error al realizar el pase del expediente electrónico.", e);
			Messagebox.show(Labels.getLabel("ee.docTadinbox.msgbox.errorRealizarEE"),
					Labels.getLabel("ee.general.error"), Messagebox.OK, Messagebox.ERROR);
		} finally {
			Clients.clearBusy();
			this.closeAssociatedWindow();
		}
	}

	private void generarPaseTramitacionConjunta(Usuario usuarioProductorInfo, String estadoSeleccionado,
			Map<String, String> detalles, String estadoAnterior, String loggedUsername,
			SectorInternoBean sectorMesaVirtual2) throws WrongValueException, InterruptedException {
		try {
			String destino = null;

			if (this.usuarioRadio.isChecked()) {
				if (usuarioApoderado != null) {
					destino = usuarioApoderado;
				} else {
					destino = usuarioProductorInfo.getUsername();
				}
			} else {
				if (this.sectorRadio.isChecked()) {
					destino = (!organismoSeleccionadoIsNull()?this.organismoSeleccionado.getCodigo().trim():"") + "-"
							+ this.sectorBusquedaSADE.getValue();
				} else if (this.reparticionRadio.isChecked()) {
					destino = this.reparticionBusquedaSADE.getValue().trim() + "-"
							+ sectorMesaVirtual2.getCodigo().trim().toUpperCase();
				}
			}
			boolean bandera = false;
			if (estadoSeleccionado.equals(ConstantesWeb.ESTADO_GUARDA_TEMPORAL) && !this.fusionService
					.esExpedienteFusion(expedienteElectronico.getListaExpedientesAsociados().get(0).getNumero())) {
				DocumentoDTO documentoTCDesvinculacion;
				expedienteElectronico.setEsCabeceraTC(false);

				TaskQuery taskQuery = this.processEngine.getTaskService().createTaskQuery()
						.executionId(expedienteElectronico.getIdWorkflow());
				Task task = taskQuery.uniqueResult();
				ArrayList<ExpedienteAsociadoEntDTO> aux = new ArrayList<>(
						expedienteElectronico.getListaExpedientesAsociados());
				String motivo = MOTIVO_DESVINCULACION_TRAMITACION_CONJUNTA;
				documentoTCDesvinculacion = this.tramitacionConjuntaService
						.generarDocumentoDeDesvinculacionEnTramitacionConjunta(expedienteElectronico, motivo,
								loggedUsername, task);
				expedienteElectronico = this.tramitacionConjuntaService.desvincularExpedientesTramitacionConjunta(
						expedienteElectronico, loggedUsername, documentoTCDesvinculacion);
				expedienteElectronicoService.modificarExpedienteElectronico(expedienteElectronico);

				for (ExpedienteAsociadoEntDTO expedienteAsociado : aux) {
					this.expedienteAsociadoService.deleteExpedienteAsociado(expedienteAsociado);
				}
			} else {
				// Genero el pase y el avance de la task de cada expediente
				// adjunto
				expedienteElectronico.hacerDefinitivosArchivosDeTrabajo();
				expedienteElectronico.hacerDocsDefinitivos();
				expedienteElectronico.hacerDefinitivosAsociados();
				this.tramitacionConjuntaService.actualizarArchivosDeTrabajoEnTramitacionConjunta(expedienteElectronico,
						loggedUsername);
				this.tramitacionConjuntaService
						.actualizarExpedientesAsociadosEnTramitacionConjunta(expedienteElectronico);
//				this.tramitacionConjuntaService.actulizarDocumentosEnTramitacionConjunta(expedienteElectronico);
//				bandera = true;
			}

			// Genero el pase y el avance de la task del expediente
			// cabecera.
			String indexResult; 
			if (detalles.get("resultValue") == null) {
				indexResult = "1";
			} else {
				indexResult = String.valueOf(cboResultType.getSelectedIndex());
			}
			List<PropertyConfigurationDTO> props = propertyConfigurationService
					.getPropertiesWithPrefix(MantainerPrefix.RESULT_TYPE+indexResult);
			expedienteElectronico.setPropertyResultado(props.get(0));
			detalles.remove("resultValue");
			detalles.remove("resultLabel");
			this.generarPaseExpedienteElectronico(usuarioProductorInfo, estadoSeleccionado, detalles, estadoAnterior,
					loggedUsername, sectorMesaVirtual2);

			if (bandera) {
				this.tramitacionConjuntaService.movimientoTramitacionConjunta(expedienteElectronico, loggedUsername,
						detalles, estadoAnterior, estadoSeleccionado, this.motivoExpedienteStr, destino);
			}
		} catch (RemoteAccessException e) {
			logger.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), Labels.getLabel("ee.general.advertencia"), Messagebox.OK, Messagebox.ERROR);

		} catch (Exception e) {
			logger.error("Error al realizar el pase de la tramitacion conjunta.", e);
			Messagebox.show(Labels.getLabel("ee.envioComp.msgbox.errorRealizarPase"),
					Labels.getLabel("ee.subsanacion.msg.title.errorMayus"), Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void refreshComboEstado() {
		this.estado.getItems().clear();

		this.setWorkingTask((Task) this.envioWindow.getDesktop().getAttribute("selectedTask"));
		String taskId = this.getWorkingTask().getId();

		Set<String> outcomes = this.processEngine.getTaskService().getOutcomes(taskId);
		outcomes.remove(ConstantesCommon.ESTADO_SUBSANACION);
		Task task = this.processEngine.getTaskService().getTask(taskId);

		if (task.getActivityName().equals(ESTADO_GUARDA_TEMPORAL)) {
			for (String transicion : outcomes) {
				if (transicion.equals(ESTADO_GUARDA_TEMPORAL)) {
					this.estado.appendItem(transicion);
				}
			}
		} else {

			for (String transicion : outcomes) {
				if (!transicion.equals(CIERRE) && !(transicion.equals(FORK))) {
					this.estado.appendItem(transicion);
				}
			}
		}
		this.estado.setValue(task.getActivityName());
	}

	/**
	 * Valida los datos antes de realizar el pase del expediente electrónico.
	 */
	public void validarDatosEnvio() {
		super.validarMotivo();
		
		if(StringUtils.isBlank(this.estado.getValue())) {
			throw new WrongValueException(
					this.estado, Labels.getLabel("ee.pase.envio.validar.estado"));
		}
		
		if ((this.usuarioRadio.isChecked() == false) && (this.reparticionRadio.isChecked() == false)
				&& (this.sectorRadio.isChecked() == false)
				&& !(this.estado.getValue().equals(ESTADO_GUARDA_TEMPORAL))) {
			throw new WrongValueException(destino, "Debe seleccionar un Usuario, Sector u Organismo");
		}

		this.usuarioProductorInfo = new Usuario();

		if (!this.estado.getValue().equals(ESTADO_GUARDA_TEMPORAL)) {
			if (this.usuarioRadio.isChecked()) {
				logger.info("Usuario: " + usuarioSeleccionado);
				if (this.usuarioSeleccionado == null) {
					throw new WrongValueException(this.inclBandboxUsuario, "Debe seleccionar un Usuario.");
//					this.mensajeValidadorBandboxUsuario("Debe seleccionar un Usuario.");
				}
				usuarioProductorInfo = usuariosSADEService.getDatosUsuario(usuarioSeleccionado.getUsername());

				if (usuarioProductorInfo == null) {
//					this.mensajeValidadorBandboxUsuario("Debe seleccionar un usuario válido.");
					throw new WrongValueException(this.inclBandboxUsuario, "Debe seleccionar un usuario válido.");
				}
			}

			if (this.reparticionRadio.isChecked()) {
				if ((this.reparticionBusquedaSADE.getValue() == null) || (this.reparticionBusquedaSADE.getValue() == "")
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

				List<SectorInternoBean> sectores = sectorInternoServ.buscarSectoresPorReparticion(reparticion.getId());

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
			if (organismoSeleccionadoIsNull()) {
				mensajeValidadorBandboxOrgamismo("Debe seleccionar una Repartición válida.");
			}	
				

				if ((this.sectorBusquedaSADE == null) || (this.sectorBusquedaSADE.getValue() == "")
						|| this.sectorBusquedaSADE.getValue().isEmpty()) {
					throw new WrongValueException(this.sectorBusquedaSADE,
							"Debe seleccionar un Sector Interno válido.");
				}
			}
		}
	}
	
	private Boolean organismoSeleccionadoIsNull() {
		
		Boolean resp = false;
		if ((this.organismoSeleccionado == null)
		|| (this.organismoSeleccionado.getCodigo() == "")
		|| this.organismoSeleccionado.getCodigo().isEmpty()) {
			resp = true;
		}	
		
		return resp;
	}

	/**
	 * @param usuarioProductorInfo
	 * @throws InterruptedException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void preguntarEnvioApoderado() throws InterruptedException {
		Messagebox.show(
				Labels.getLabel("ee.licencia.question.value",
						new String[] { usuarioProductorInfo.getUsername(), this.usuarioApoderado }),
				Labels.getLabel("ee.licencia.question.title"), Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					public void onEvent(Event evt) throws InterruptedException {
						switch (((Integer) evt.getData()).intValue()) {
						case Messagebox.YES:
							setVariableWorkFlow(ConstantesWeb.USUARIO_SELECCIONADO, usuarioApoderado);
							// Esto se coloca para bloquear la ventana de atras
							// mientras se procesa el pase
							mostrarForegroundBloqueante();
							Events.echoEvent(Events.ON_USER, self, "enviarExpedienteElectronico");
							break;
						case Messagebox.NO:
							alert("Seleccione otro destino.");
							break;
						}
					}
				});
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void preguntarEnvioArchivo() throws InterruptedException {
		Messagebox.show(Labels.getLabel("ee.envio.archivo.question.value"),
				Labels.getLabel("ee.envio.archivo.question"), Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
				new org.zkoss.zk.ui.event.EventListener() {
					public void onEvent(Event evt) throws InterruptedException {
						switch (((Integer) evt.getData()).intValue()) {
						case Messagebox.YES:
							// Esto se coloca para bloquear la ventana de atras
							// mientras se procesa el pase
							mostrarForegroundBloqueante();
							Events.echoEvent(Events.ON_USER, self, "archivar");

							break;

						case Messagebox.NO:
							break;
						}
					}
				});
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void preguntarEnvioArchivoEnConjunta() throws InterruptedException {
		if (!this.fusionService
				.esExpedienteFusion(expedienteElectronico.getListaExpedientesAsociados().get(0).getNumero())) {
			Messagebox.show(Labels.getLabel("ee.envio.archivo.questionEnConjunta.value"),
					Labels.getLabel("ee.envio.archivo.question"), Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
					new org.zkoss.zk.ui.event.EventListener() {
						public void onEvent(Event evt) throws InterruptedException {
							switch (((Integer) evt.getData()).intValue()) {
							case Messagebox.YES:
								// Esto se coloca para bloquear la ventana de
								// atras
								// mientras se procesa el pase
								mostrarForegroundBloqueante();
								Events.echoEvent(Events.ON_USER, self, "enviarTramitacionConjunta");

								break;

							case Messagebox.NO:
								break;
							}
						}
					});
		} else {
			Messagebox.show(Labels.getLabel("ee.envio.archivo.question.value"),
					Labels.getLabel("ee.envio.archivo.question"), Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
					new org.zkoss.zk.ui.event.EventListener() {
						public void onEvent(Event evt) throws InterruptedException {
							switch (((Integer) evt.getData()).intValue()) {
							case Messagebox.YES:
								// Esto se coloca para bloquear la ventana de
								// atras
								// mientras se procesa el pase
								mostrarForegroundBloqueante();
								Events.echoEvent(Events.ON_USER, self, "enviarTramitacionConjunta");

								break;

							case Messagebox.NO:
								break;
							}
						}
					});
		}
	}

	public void onCancelar() {
		this.envioWindow.detach();
	}

	private synchronized void archivar() throws InterruptedException {
		try {
			// Servicio de Pase de Guarda Temporal
			this.paseExpedienteService.paseGuardaTemporal(this.expedienteElectronico, this.workingTask,
					this.loggedUsername, this.detalles, estadoAnterior, this.motivoExpedienteStr);

			// Avanza la tarea en el workflow
			// signalExecution(estado.getValue());

			// Vuelve a avanzar la tarea en el workflow para cerrar la misma.
			// processEngine.getExecutionService().signalExecutionById(this.workingTask.getExecutionId(),
			// "Cierre");

			this.expedienteElectronico.setEstado(ESTADO_GUARDA_TEMPORAL);
			WorkFlow workFlow = workFlowService.createWorkFlow(expedienteElectronico.getId(), estado.getValue());
			workFlow.initParameters(detalles);
			workFlow.execute(workFlow.getWorkingTask(), this.processEngine);

			// Previo al cierre, si este expediente corresponde a un subproceso
			// se ejecutara el script de cierre, si lo posee
			SubProcesoOperacionDTO subProcesoOperacionDTO = subprocessService
					.getSubprocessFromEE(expedienteElectronico.getId());

			if (subProcesoOperacionDTO != null && subProcesoOperacionDTO.getSubproceso() != null) {
				// Vinculacion tipo documentos expediente con propiedad
				// "resultado" a la operacion
				operacionDocumentoService.copiarDocsResultadoFromEE(expedienteElectronico.getId(),
						subProcesoOperacionDTO.getOperacion().getId());

				WorkFlowScriptUtils.getInstance().executeSubprocessScript(
						subProcesoOperacionDTO.getSubproceso().getScriptEnd(), expedienteElectronico.getId(), null,
						Executions.getCurrent().getRemoteUser());
			}

			// 11-05-2020: No ejecutar el cierre, o desaparece la tarea
			
			// workFlow =
			// workFlowService.createWorkFlow(expedienteElectronico.getId(),
			// "Cierre");
			// workFlow.execute(workFlow.getWorkingTask(), this.processEngine);
			//processEngine.getExecutionService().signalExecutionById(workFlow.getWorkingTask().getExecutionId(),
			//		"Cierre");
			// this.expedienteElectronicoService.grabarExpedienteElectronico(expedienteElectronico);

			Messagebox.show(
					Labels.getLabel("ee.envioComp.msgbox.exp") + " " + expedienteElectronico.getCodigoCaratula() + ", "
							+ Labels.getLabel("ee.envioComp.msgbox.gurdadoTempCorrecto"),
					Labels.getLabel("ee.tramitacion.titulo.pase"), Messagebox.OK, Messagebox.INFORMATION);
			
			if (tramitacionWindow != null) {
				tramitacionWindow.onClose();
				Events.sendEvent(Events.ON_USER, tramitacionWindow, "refreshInbox");
			}
		} catch (RemoteAccessException e) {
			logger.error(e.getMessage(), e);
			Messagebox.show(e.getMessage(), Labels.getLabel("ee.general.advertencia"), Messagebox.OK, Messagebox.ERROR);
		} catch (Exception e) {
			logger.error("Error al archivar el expediente", e);
			Messagebox.show(Labels.getLabel("ee.envioComp.msgbox.errorArchivaEE"),
					Labels.getLabel("ee.subsanacion.msg.title.error"), Messagebox.OK, Messagebox.ERROR);
		} finally {
			Clients.clearBusy();
			closeAssociatedWindow();
		}
	}

	/**
	 * Se guarda el estado que se encontraba seleccionado antes de seleccionar
	 * otro y este último y se los setea en variables del WF para poder mantener
	 * una lógica con la pantalla anterior.
	 */
	public void onSelectEstado() {
		this.estado.setValue(this.estado.getSelectedItem().getLabel());

		// Solo se puede habilitar el boton "realizar pase y comunicar"
		// si el estado del expediente es alguno de los siguientes: TRAMITACION,
		// COMUNICACION, EJECUCION

		if (this.estado.getValue().equals(ESTADO_GUARDA_TEMPORAL)) {
			this.usuarioRadio.setDisabled(true);

			if (this.usuarioRadio.isChecked()) {
				this.usuarioRadio.setChecked(false);
				
				
				this.cleanBandboxUsuario();
				this.disabledBandbox(true);
				//this.usuario.setValue(null);
				//this.usuario.setDisabled(true);
			}

			this.sectorRadio.setDisabled(true);

			if (this.sectorRadio.isChecked()) {
				this.sectorRadio.setChecked(false);
				this.sectorBusquedaSADE.setValue(null);
				this.sectorBusquedaSADE.setDisabled(true);
				cleanBandboxOrgamismo();
				disabledBandboxOrgamismo(true);
			}

			this.reparticionRadio.setDisabled(true);

			if (this.reparticionRadio.isChecked()) {
				this.reparticionRadio.setChecked(false);
				this.reparticionBusquedaSADE.setValue(null);
				this.reparticionBusquedaSADE.setDisabled(true);
			}
		} else {
			this.usuarioRadio.setDisabled(false);
			this.sectorRadio.setDisabled(false);
			this.reparticionRadio.setDisabled(false);
		}
	}

	/**
	 * Se habilitan y deshabilitan los campos según que opción se eliga.
	 */
	public void onUsuarioClick() {
		this.disabledBandbox(false);
		//this.usuario.setDisabled(false);
		this.sectorBusquedaSADE.setDisabled(true);
		this.sectorRadio.setChecked(false);
		this.cleanBandboxOrgamismo();
		this.disabledBandboxOrgamismo(true);
		
		this.reparticionRadio.setChecked(false);
		this.reparticionBusquedaSADE.setDisabled(true);
		this.reparticionBusquedaSADE.setValue(null);
		this.sectorBusquedaSADE.setValue(null);
	}

	public void onSectorClick() {
		// se agrega if por incidencia 4488.
		if ((this.sectorRadio.isChecked()) && ( this.organismoSeleccionado != null ) && (this.organismoSeleccionado.getCodigo() != "")
				&& (this.organismoSeleccionado.getCodigo() != null)) {
				this.sectorBusquedaSADE.setDisabled(false);
		} else {
			this.sectorBusquedaSADE.setDisabled(true);
		}

		// se comenta por incidencia 4488.
		
		this.cleanBandboxUsuario();
		this.disabledBandbox(true);
		//this.usuario.setDisabled(true);
		//this.usuario.setValue(null);
		this.usuarioRadio.setChecked(false);
		this.disabledBandboxOrgamismo(false);
		this.reparticionRadio.setChecked(false);
		this.reparticionBusquedaSADE.setDisabled(true);
		this.reparticionBusquedaSADE.setValue(null);
	}

	public void onReparticionClick() {
		this.reparticionBusquedaSADE.setDisabled(false);
		this.sectorBusquedaSADE.setDisabled(true);
		this.sectorRadio.setChecked(false);
		
		this.cleanBandboxOrgamismo();
		this.disabledBandboxOrgamismo(true);
		
		this.cleanBandboxUsuario();
		this.disabledBandbox(true);
		//this.usuario.setValue(null);
		//this.usuario.setDisabled(true);
		
		this.usuarioRadio.setChecked(false);
		this.sectorBusquedaSADE.setValue(null);
	}

	/**
	 *
	 * @param name
	 *            : nombre de la variable que quiero setear
	 * @param value
	 *            : valor de la variable
	 */
	public void setVariableWorkFlow(String name, Object value) {
		this.processEngine.getExecutionService().setVariable(this.workingTask.getExecutionId(), name, value);
	}

	/**
	 * Se debería usar el <code>WorkFlowService</code> para hacer transiciones
	 * entre estados.
	 * 
	 * @param nameTransition
	 *            : nombre de la transición que voy a usar para la proxima tarea
	 * @deprecated
	 */
	public void signalExecution(String nameTransition) {
		// PASO TODAS LAS VARIABLES ANTES DE REALIZAR EL SIGNAL
		setearVariablesAlWorkflow();

		// Paso a la siguiente Tarea/Estado definida en el Workflow
		processEngine.getExecutionService().signalExecutionById(this.workingTask.getExecutionId(), nameTransition);
	}

	/**
	 * Se debería usar el <code>WorkFlowService</code> seteo de los parametros
	 * iniciales.
	 * 
	 * @throws NumberFormatException
	 * @deprecated
	 */
	private void setearVariablesAlWorkflow() throws NumberFormatException {
		Map<String, Object> mapVariables = new HashMap<String, Object>();

		mapVariables.put(ConstantesWeb.ESTADO_ANTERIOR, detalles.get(ConstantesWeb.ESTADO_ANTERIOR));
		mapVariables.put(ConstantesWeb.ESTADO_ANTERIOR_PARALELO, detalles.get(ConstantesWeb.ESTADO_ANTERIOR_PARALELO));
		mapVariables.put(ConstantesWeb.TAREA_GRUPAL, detalles.get(ConstantesWeb.TAREA_GRUPAL));
		mapVariables.put(ConstantesWeb.ID_EXPEDIENTE_ELECTRONICO,
				Integer.parseInt(detalles.get(ConstantesWeb.ID_EXPEDIENTE_ELECTRONICO)));
		mapVariables.put(ConstantesWeb.GRUPO_SELECCIONADO, detalles.get(ConstantesWeb.GRUPO_SELECCIONADO));
		mapVariables.put(ConstantesWeb.USUARIO_SELECCIONADO, detalles.get(ConstantesWeb.USUARIO_SELECCIONADO));

		this.processEngine.getExecutionService().setVariables(workingTask.getExecutionId(), mapVariables);
	}

	/**
	 *
	 * @param name
	 *            : nombre de la variable del WF que quiero encontrar
	 * @return objeto guardado en la variable
	 */
	public Object getVariableWorkFlow(String name) {
		Object obj = this.processEngine.getExecutionService().getVariable(this.workingTask.getExecutionId(), name);

		if (obj == null) {
			throw new VariableWorkFlowNoExisteException(
					"No existe la variable para el id de ejecucion. " + this.workingTask.getExecutionId() + ", " + name,
					null);
		}

		return obj;
	}

	public Set<String> extractParticipatingGroupIds(List<Participation> taskParticipations, String participationType) {
		Set<String> groupIds = new HashSet<>();

		for (Participation participation : taskParticipations) {
			if (participationType.equals(participation.getType())) {
				if (participation.getGroupId() != null) {
					groupIds.add(participation.getGroupId());
				}
			}
		}

		return groupIds;
	}
	
	public void onOpen$sectorBusquedaSADE() {
		textoSectorBusquedaSADE.setValue(sectorBusquedaSADE.getRawValue().toString());
	}
	
	public void onOpen$reparticionBusquedaSADE() {
		textoReparticionBusquedaSADE.setValue(reparticionBusquedaSADE.getRawValue().toString());
	}

	// ********************************************
	// *** ACCESSOR'S
	// ********************************************

	public Bandbox getReparticionBusquedaSADE() {
		return reparticionBusquedaSADE;
	}

	public void setReparticionBusquedaSADE(Bandbox reparticionBusquedaSADE) {
		this.reparticionBusquedaSADE = reparticionBusquedaSADE;
	}

	public ExpedienteElectronicoService getExpedienteElectronicoService() {
		return expedienteElectronicoService;
	}

	public void setExpedienteElectronicoService(ExpedienteElectronicoService expedienteElectronicoService) {
		this.expedienteElectronicoService = expedienteElectronicoService;
	}

	public List<Usuario> getUsuariosGEDO() {
		try {
			return this.usuariosSADEService.getTodosLosUsuarios();
		} catch (SecurityNegocioException e) {
			logger.error(e.getMessage());
			throw new WrongValueException(Labels.getLabel("ee.error.usuario.label.invalidos"));
		}
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

	private void disabledBandboxOrgamismo(Boolean disabled) {
		Events.sendEvent(BandboxOrganismoComposer.EVENT_DISABLED, inclBandboxOrganismo.getChildren().get(0), disabled);
	}
	
	private void cleanBandboxOrgamismo() {
		Events.sendEvent(BandboxOrganismoComposer.EVENT_CLEAN, inclBandboxOrganismo.getChildren().get(0), null);
	}
	
	private void mensajeValidadorBandboxOrgamismo(String mensaje) {
		Events.sendEvent(BandboxOrganismoComposer.EVENT_VALIDAR, inclBandboxOrganismo.getChildren().get(0), mensaje);
	}
	
	
	
	public UsuarioReducido getUsuarioSeleccionado() {
		return usuarioSeleccionado;
	}

	public void setUsuarioSeleccionado(UsuarioReducido usuarioSeleccionado) {
		this.usuarioSeleccionado = usuarioSeleccionado;
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

	public void setSectoresReparticionesBusquedaSADEListbox(Listbox sectoresReparticionesBusquedaSADEListbox) {
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

	public Window getEnvioWindow() {
		return envioWindow;
	}

	public void setEnvioWindow(Window envioWindow) {
		this.envioWindow = envioWindow;
	}

	public Combobox getEstado() {
		return estado;
	}

	public void setEstado(Combobox estado) {
		this.estado = estado;
	}

//	public Combobox getUsuario() {
//		return usuario;
//	}
//
//	public void setUsuario(Combobox usuario) {
//		this.usuario = usuario;
//	}

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

	public String getReparticionGT() {
		return reparticionGT;
	}

	public void setReparticionGT(String reparticionGT) {
		this.reparticionGT = reparticionGT;
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

	public Combobox getCboResultType() {
		return cboResultType;
	}

	public void setCboResultType(Combobox cboResultType) {
		this.cboResultType = cboResultType;
	}

	public List<PropertyConfigurationDTO> getResultTypes() {
		return resultTypes;
	}

	public void setResultTypes(List<PropertyConfigurationDTO> resultTypes) {
		this.resultTypes = resultTypes;
	}

	public Textbox getTbxIdTransaction() {
		return tbxIdTransaction;
	}

	public void setTbxIdTransaction(Textbox tbxIdTransaction) {
		this.tbxIdTransaction = tbxIdTransaction;
	}

	public Textbox getTbxNameForm() {
		return tbxNameForm;
	}

	public void setTbxNameForm(Textbox tbxNameForm) {
		this.tbxNameForm = tbxNameForm;
	}

	final class EnvioExpedienteElectronicoOnNotifyWindowListener implements EventListener<Event> {
		private EnvioComposer composer;

		public EnvioExpedienteElectronicoOnNotifyWindowListener(EnvioComposer comp) {
			this.composer = comp;
		}

		public void onEvent(Event event) throws Exception {
			String user = Executions.getCurrent().getRemoteUser();
			if (event.getName().equals(Events.ON_USER)) {
				if (event.getData().equals("enviarExpedienteElectronico")) {
					// Events.sendEvent(Events.ON_USER, event.getTarget(),
					// "addDocumentoPase");
					Map<String, Object> map = new HashMap<>();
					map.put("idTransactionFFCC", tbxIdTransaction.getValue());
					// WorkFlowScriptUtils.getInstance().executeScript(ScriptType.DECISION,
					// expedienteElectronico , map, user);
					this.composer.generarPaseExpedienteElectronico(usuarioProductorInfo, estado.getValue(), detalles,
							estadoAnterior, loggedUsername, sectorMesaVirtual);
					WorkFlowScriptUtils.getInstance().executeScript(ScriptType.START, expedienteElectronico, null,
							user);
					BindUtils.postGlobalCommand(null, null, "closeTramitacionWindow", null);
				}

				if (event.getData().equals("enviarTramitacionConjunta")) {
					this.composer.generarPaseTramitacionConjunta(usuarioProductorInfo, estado.getValue(), detalles,
							estadoAnterior, loggedUsername, sectorMesaVirtual);
				}

				if (event.getData().equals("archivar")) {
					this.composer.archivar();
				}

				if (event.getData().equals("onEnviar")) {
					this.composer.onEnviar();
				}

			}else if(event.getName().equals(BandBoxUsuarioComposer.ON_SELECT_USUARIO)) {
				this.composer.setUsuarioSeleccionado((UsuarioReducido) event.getData());
			}else if(event.getName().equals(BandboxOrganismoComposer.ON_SELECT_ORGANISMO)) {
				this.composer.setOrganismoSeleccionado((ReparticionBean ) event.getData());
				if(!organismoSeleccionadoIsNull()) {
					sectorBusquedaSADE.setDisabled(false);
					
					if (event.getData() instanceof ReparticionBean) {
						Events.sendEvent(ONSEND_REPARTICION_BEAN,sectorBusquedaSADE.getChildren().get(0), (ReparticionBean) event.getData());
					}
					
				} else {
					sectorBusquedaSADE.setDisabled(true);
				}
			} else if(event.getName().equals(Events.ON_NOTIFY)) {
				composer.definirMotivo();
				composer.validarDatosEnvio();
			}
		}
	}

	public SectorInternoBean getSectorSeleccionado() {
		return sectorSeleccionado;
	}

	public void setSectorSeleccionado(SectorInternoBean sectorSeleccionado) {
		this.sectorSeleccionado = sectorSeleccionado;
	}

	public final ReparticionBean getOrganismoSeleccionado() {
		return organismoSeleccionado;
	}

	public final void setOrganismoSeleccionado(ReparticionBean organismoSeleccionado) {
		this.organismoSeleccionado = organismoSeleccionado;
	}
	
}
