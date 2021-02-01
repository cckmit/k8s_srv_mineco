package com.egoveris.te.base.composer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.activation.MimetypesFileTypeMap;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.cmd.Command;
import org.jbpm.api.cmd.Environment;
import org.jbpm.api.task.Task;
import org.jbpm.pvm.internal.model.ActivityImpl;
import org.jbpm.pvm.internal.model.ExecutionImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Init;
import org.zkoss.image.AImage;
import org.zkoss.spring.SpringUtil;
import org.zkoss.util.media.AMedia;
import org.zkoss.util.media.Media;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Execution;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.ext.Disable;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.ComboitemRenderer;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Intbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelArray;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Row;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.egoveris.commons.databaseconfiguration.propiedades.AppProperty;
import com.egoveris.ffdd.model.exception.DynFormException;
import com.egoveris.plugins.manager.PluginManager;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.te.base.exception.DocumentoOArchivoNoEncontradoException;
import com.egoveris.te.base.exception.DocumentoOArchivoSinPermisoDeVisualizacionException;
import com.egoveris.te.base.exception.NegocioException;
import com.egoveris.te.base.exception.VariableWorkFlowNoExisteException;
import com.egoveris.te.base.model.ActividadInbox;
import com.egoveris.te.base.model.ArchivoDeTrabajoDTO;
import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.model.ExpedienteAsociadoEntDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.ExpedienteMetadataDTO;
import com.egoveris.te.base.model.ParametrosSistemaExternoDTO;
import com.egoveris.te.base.model.ReparticionParticipanteDTO;
import com.egoveris.te.base.model.SolicitanteDTO;
import com.egoveris.te.base.model.TareaParaleloDTO;
import com.egoveris.te.base.model.TipoDocumentoDTO;
import com.egoveris.te.base.model.TrataDTO;
import com.egoveris.te.base.model.TrataIntegracionReparticionDTO;
import com.egoveris.te.base.model.TrataReparticionDTO;
import com.egoveris.te.base.model.TrataTipoDocumentoDTO;
import com.egoveris.te.base.service.ArchivoDeTrabajoService;
import com.egoveris.te.base.service.CaratulacionService;
import com.egoveris.te.base.service.DiasHabilesService;
import com.egoveris.te.base.service.DocumentoManagerService;
import com.egoveris.te.base.service.PermisoVisualizacionService;
import com.egoveris.te.base.service.TareaParaleloService;
import com.egoveris.te.base.service.TipoDocumentoService;
import com.egoveris.te.base.service.TramitacionConjuntaService;
import com.egoveris.te.base.service.TrataService;
import com.egoveris.te.base.service.UsuariosSADEService;
import com.egoveris.te.base.service.WorkFlowService;
import com.egoveris.te.base.service.expediente.ExpedienteAsociadoService;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.service.expediente.ExpedienteSadeService;
import com.egoveris.te.base.service.iface.IAccesoWebDavService;
import com.egoveris.te.base.service.iface.IActividadExpedienteService;
import com.egoveris.te.base.service.iface.IExpedienteFormularioService;
import com.egoveris.te.base.service.iface.IExternalFormsService;
import com.egoveris.te.base.util.BusinessFormatHelper;
import com.egoveris.te.base.util.ConfiguracionInicialModuloEEFactory;
import com.egoveris.te.base.util.ConstantesDB;
import com.egoveris.te.base.util.ConstantesServicios;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.ConstantesWeb.VISTA;
import com.egoveris.te.base.util.FiltroEE;
import com.egoveris.te.base.util.ReflectionUtil;
import com.egoveris.te.base.util.TipoDocumentoPosible;
import com.egoveris.te.base.util.TipoDocumentoUtils;
import com.egoveris.te.base.util.TramitacionHelper;
import com.egoveris.te.base.util.TramitacionTabsConditional;
import com.egoveris.te.base.util.ValidadorDeCuit;
import com.egoveris.te.base.util.ZkUtil;
import com.egoveris.te.base.vm.SolicitanteDireccionVM;
import com.egoveris.te.model.model.ExpedienteFormularioDTO;
import com.egoveris.te.model.util.MailUtil;
import com.egoveris.te.web.ee.satra.pl.helpers.states.IVisualState;

/**
 * @author eduavega
 *
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class TramitacionComposer extends EEGenericForwardComposer {
	private static final long serialVersionUID = 6825957437695971081L;
	final static Logger logger = LoggerFactory.getLogger(TramitacionComposer.class);

	// Habilitacion o no de solapas
	public transient static boolean habilitarTabTC = true;
	public transient static boolean habilitarTabFusion = true;
	public static String MEMORANDUM = "ME";
	public static String NOTA = "NO";
	public static final String TRAMITACION_EN_PARALELO = "Paralelo";
	public static final String ESTADO_TRAMITACION = "Tramitacion";
	public static final String ESTADO_EJECUCION = "Ejecucion";
	public static final String ESTADO_SUBSANACION = "Subsanacion";
	public static final String ES_RESERVADO = " - Expediente Reservado";
	public static final String RESERVA_PARCIAL = "PARCIAL";
	public static final String RESERVA_TOTAL = "TOTAL";
	public static final String MOTIVO_QUITAR_RESERVA = "Eliminación de la reserva";
	public static final String SOLICITUD_SUBSANACION_TAD = "Solicitud de subsanación a VUC";
	public static final String SISTEMA_VUC = "VUC";
	private Button subsanar;

	@WireVariable(ConstantesServicios.TAREA_PARALELO_SERVICE)
	private TareaParaleloService tareaParaleloService;
	@WireVariable(ConstantesServicios.CONSTANTESDB)
	private ConstantesDB constantesDB;

	// bandbox
	@Autowired
	private Bandbox tiposDocumentoEspecialBandbox;
	@Autowired
	private Bandbox familiaEstructuraTree;
	@Autowired
	protected Bandbox tiposDocumentoBandbox;
	@Autowired
	protected Bandbox reparticionBusquedaDocumento;
	@Autowired
	private Bandbox reparticionBusquedaDocumentoNumeroEspecial;
	@Autowired
	private Bandbox reparticionBusquedaUsuario;
	@Autowired
	private Bandbox tipoFormBandbox;
	private List<String> tiposDocBox;

	// windows
	@Autowired
	private Component acordeon;
	@Autowired
	private Window tramitacionWindow;
	@Autowired
	private Window envio;
	@Autowired
	private Window aniadirDocumento;

	@Autowired
	private Window buscarFormularioWin;

	@Autowired
	private Window tipoArchivoDeTrabajoWindow;

	@Autowired
	private Window datosPropiosWindow;
	@Autowired
	private Window buscarPorMetadatoWindow;
	@Autowired
	private Window realizarPaseyComunicarComposer;
	@Autowired
	private Window notificarExpedienteTadWindow;

	// intbox
	@Autowired
	protected Intbox anioSADEDocumento;
	@Autowired
	protected Intbox numeroSADEDocumento;

	@Autowired
	private Combobox comboActuacion;

	private List<String> actuaciones;

	@Autowired
	private Intbox anioSADE;
	@Autowired
	private Intbox numeroSADE;
	@Autowired
	private Intbox anioSADEDocumentoNumeroEspecial;
	@Autowired
	private Intbox numeroSADEDocumentoNumeroEspecial;

	// textbox
	@Autowired
	private Textbox descripcion;
	@Autowired
	private Textbox estado;
	// @Autowired
	// private Textbox tipoExpediente;
	@Autowired
	private Textbox motivoExpediente;
	@Autowired
	private Textbox razonSocial;
	@Autowired
	private Textbox nombre;
	@Autowired
	private Textbox segundoNombre;
	@Autowired
	private Textbox tercerNombre;
	@Autowired
	private Textbox apellido;
	@Autowired
	private Textbox segundoApellido;
	@Autowired
	private Textbox tercerApellido;
	@Autowired
	private Textbox email;
	@Autowired
	private Textbox telefono;
	@Autowired
	private Textbox reparticionActuacion;
	@Autowired
	private Label leyendaLabel;
//	@Autowired
//	private Checkbox noDeclaraNoPosee;
//	@Autowired
//	private Row cuilcuit;
//	@Autowired
//	private Row cuil;
	@Autowired
	private Textbox direccion;
	@Autowired
	private Textbox piso;
	@Autowired
	private Textbox departamento;
	@Autowired
	private Textbox codigoPostal;
	// @Autowired
	// private Textbox barrio;
	// @Autowired
	// private Textbox comuna;

	@WireVariable("appProperty")
	private AppProperty appProperty;

	// listbox
	@Autowired
	private Listbox documentoTrabajoLb;
	@Autowired
	private Listbox formsLb;
	@Autowired
	private Listbox listboxExpedientes;

	// radio
	@Autowired
	private Radio expedienteInterno;
	@Autowired
	private Radio expedienteExterno;

	@Autowired
	private Radio tipoBuzonGrupal;

	@Autowired
	private Radio tipoCarga;

	@WireVariable(ConstantesServicios.TRATA_SERVICE)
	private TrataService trataService;

	// button
	private Button enviar;
	@Autowired
	private Button modificar;
	@Autowired
	private Button tramitacionParalelo;
	@Autowired
	private Button reservar;
	@Autowired
	private Button quitarReserva;
	@Autowired
	public Button guardarModificacion;
	@Autowired
	private Button cancelarModificacion;
	@Autowired
	private Button notificarExpedienteTad;

	@Autowired
	private Button inciarDocumentoButton;
	
	@Autowired
	private Button inciarDocumentoButtonD;

	@Autowired
	private Button comunicarTadButton;

	@Autowired
	private Button vinculacionDefinitiva;

	@Autowired
	private Popup clavePopUp;
	@Autowired
	private Groupbox idgroupbox;

	@Autowired
	private Button crearPaquete;
	@Autowired
	private Button cancelar;
	// combobox
	@Autowired
	private Combobox tipoDocumento;
	@Autowired
	private Combobox codigoTrata;

	// longbox
	@Autowired
	private Textbox numeroDocumento;
	@Autowired
	private Longbox cuitCuilTipo;
	@Autowired
	private Longbox cuitCuilDocumento;
	@Autowired
	private Longbox cuitCuilVerificador;
	@Autowired
	private Longbox cuilNotTipo;
	@Autowired
	private Longbox cuilNotDocumento;
	@Autowired
	private Longbox cuilNotVerificador;

	private TipoDocumentoDTO selectedTipoDocumento;
	
	// label
	@Autowired
	private Label titulo;

	// INI TIPO RESULTADO
	@Autowired
	private Hbox hboxResult;

	@Autowired
	private Label lblResult;
	// FIN TIPO RESULTADO

	// toolbarbutton
	@Autowired
	private Toolbarbutton datosPropios;

	private Button sistemaExternobtn;

	// tab
	public Tab expedienteTramitacionConjunta;
	public Tab expedienteFusion;
	public Tab datosdelacaratula;
	public Tab buzonAct;
	public Tab controlNombramiento;
	private AMedia media;
	private TipoDocumentoDTO tiposDocumentos;
	private String selectedDocumentoTrabajo;
	private String selectedCodigoTrata;
	private String loggedUsername;
	private byte[] dataFile;
	protected Task workingTask = null;
	private ArchivoDeTrabajoDTO archivodetrabajo;
	@Autowired
	protected AnnotateDataBinder binder;
	protected List<DocumentoDTO> documentosFiltrados;
	protected ExpedienteElectronicoDTO ee;

	@WireVariable(ConstantesServicios.ACCESO_WEBDAV_SERVICE)
	protected IAccesoWebDavService visualizaDocumentoService;

	@WireVariable(ConstantesServicios.EXP_FORMULARIO_SERVICE)
	protected IExpedienteFormularioService expedienteFormularioService;

	@WireVariable(ConstantesServicios.EXTERNAL_FORM_SERVICE)
	protected IExternalFormsService externalFormsService;

	@WireVariable(ConstantesServicios.PROCESS_ENGINE_SERVICE)
	protected ProcessEngine processEngine;

	@WireVariable(ConstantesServicios.TIPO_DOCUMENTO_SERVICE)
	protected TipoDocumentoService tipoDocumentoService;

	private TipoDocumentoDTO selectedTiposDocumentosNumeroEspecial;
	private String selectedTiposDocumentos;

	@WireVariable(ConstantesServicios.DIAS_HABILES_SERVICE)
	private DiasHabilesService diasHabilesService;
	
	@WireVariable(ConstantesServicios.EXP_ASOCIADO_SERVICE)
	private ExpedienteAsociadoService expedienteAsociadoService;

	private List<ArchivoDeTrabajoDTO> listaArhivosDeTrabajo = new ArrayList<>();
	private List<ExpedienteAsociadoEntDTO> listaExpedienteAsociado = new ArrayList<>();
	private List<ExpedienteMetadataDTO> metadatos = new ArrayList<ExpedienteMetadataDTO>();
	private List<ArchivoDeTrabajoDTO> archivosDeTrabajoFiltrados;
	private List<ExpedienteFormularioDTO> expedientFormsList;
	private List<ExpedienteAsociadoEntDTO> expedientesAsociadosFiltrados;
	private String reparticionUsuario;
	private ArchivoDeTrabajoDTO selectedArchivoDeTrabajo;
	private ExpedienteFormularioDTO selectedExpedientForm;
	private ExpedienteAsociadoEntDTO selectedExpedienteAsociado;
	private DocumentoDTO selectedDocumento;
	private Usuario datosUsuario;
	private HashMap<Object, Object> hm = new HashMap<Object, Object>();
	private String cuitCuil;
	private String cuilNot;
	private TrataDTO trata;
	// Permite dinamismo del botón Notificar VUC
	private Set<String> documentosNotificablesSinNotificar;

	private ExpedienteElectronicoDTO eeNombramientoVinculado;

	// Servicios
	@WireVariable(ConstantesServicios.CARATULACION_SERVICE)
	private CaratulacionService caratulador;
	@WireVariable(ConstantesServicios.DOCUMENTO_MANAGER_SERVICE)
	private DocumentoManagerService documentoManagerService;
	@WireVariable(ConstantesServicios.EXP_ELECTRONICO_SERVICE)
	private ExpedienteElectronicoService expedienteElectronicoService;
	@WireVariable(ConstantesServicios.EXP_SADE_SERVICE)
	private ExpedienteSadeService expedienteSadeService;
	@WireVariable(ConstantesServicios.ARCHIVOS_TRABAJO_SERVICE)
	private ArchivoDeTrabajoService archivoDeTrabajoService;
	@WireVariable(ConstantesServicios.TRAMITACION_CONJUNTA_SERVICE)
	private TramitacionConjuntaService tramitacionConjuntaService;
	@WireVariable(ConstantesServicios.ACTIVIDAD_EXPEDIENTE_SERVICE)
	private IActividadExpedienteService actividadExpedienteService;
	private Window documentoDetalleWindow;
	private List<TrataTipoDocumentoDTO> tiposDocumentosGEDOBD;
	@WireVariable(ConstantesServicios.USUARIOS_SADE_SERVICE)
	private UsuariosSADEService usuariosSADEService;
	private String tipoDeReserva;
	private PermisoVisualizacionService permisoVisualizacionService;

	@Autowired
	private Window vinculacionActoAdministrativoWindow;

	private TramitacionHelper tramitacionHelper;

	@WireVariable(ConstantesServicios.PLUGIN_MANAGER)
	private PluginManager pm;

	@WireVariable(ConstantesServicios.WORKFLOW_SERVICE)
	private WorkFlowService workflowService;

	private ParametrosSistemaExternoDTO params;

	private List<ArchivoDeTrabajoDTO> archivosDeTrabajoABorrar = new ArrayList<>();
	
	@Wire("disable")
	private List<Disable> allToDisable;
	
	private Window inboxWindow;

	/**
	 * Una vez que el componente se creo, cargo datos, habilito/deshabilito
	 * campos
	 */
	public List<ExpedienteAsociadoEntDTO> listaExpedienteEnFusion = new ArrayList<>();
	public List<ExpedienteAsociadoEntDTO> listaExpedienteEnTramitacionConjunta = new ArrayList<>();
 
	
	/**
	 * @return the tramitacionHelper
	 */
	public TramitacionHelper getTramitacionHelper() {
		if (tramitacionHelper == null) {
			tramitacionHelper = new TramitacionHelper(this.loggedUsername, this.ee);
		}
		return tramitacionHelper;
	}

	/**
	 * @param tramitacionHelper
	 *            the tramitacionHelper to set
	 */
	public void setTramitacionHelper(TramitacionHelper tramitacionHelper) {
		this.tramitacionHelper = tramitacionHelper;
	}

	public Label getLeyendaLabel() {
		return leyendaLabel;
	}

	public void setLeyendaLabel(Label leyendaLabel) {
		this.leyendaLabel = leyendaLabel;
	}

	public ExpedienteAsociadoEntDTO getSelectedExpedienteAsociado() {
		return selectedExpedienteAsociado;
	}

	public void setSelectedExpedienteAsociado(ExpedienteAsociadoEntDTO selectedExpedienteAsociado) {
		this.selectedExpedienteAsociado = selectedExpedienteAsociado;
	}

	public void onCreate$tramitacionWindow(Event event) {
		this.binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder", true);
	}

	public void onCreate$buscarFormularioWin(Event event) {
		this.binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder", true);
	}

	public void onCreate$tramitacionParaleloWindow(Event event) {
		this.binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder", true);
	}

	public void onCreate$expedienteEnTramitacionConjuntaWindow(Event event) {
		this.binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder", true);
	}

	/*
	@SuppressWarnings("unchecked")
	public void onSelectDocumento() {
		if (getSelectedTiposDocumentos().equals("DU - DOCUMENTO UNICO") && !getNoDeclaraNoPosee().isChecked()) {
			this.numeroDocumento.setValue(getCuitCuilDocumento().getValue());
		}
	}
	*/

	@SuppressWarnings("unchecked")
//	public void onSelectCheck() {
//		if (getNoDeclaraNoPosee().isChecked()) {
//			this.cuitCuilDocumento.setValue(null);
//			this.cuitCuilTipo.setValue(null);
//			this.cuitCuilVerificador.setValue(null);
//
//			this.cuitCuilDocumento.setDisabled(true);
//			this.cuitCuilTipo.setDisabled(true);
//			this.cuitCuilVerificador.setDisabled(true);
//		} else {
//			this.cuitCuilDocumento.setDisabled(false);
//			this.cuitCuilTipo.setDisabled(false);
//			this.cuitCuilVerificador.setDisabled(false);
//			this.cuitCuilDocumento.setReadonly(false);
//			this.cuitCuilTipo.setReadonly(false);
//			this.cuitCuilVerificador.setReadonly(false);
//		}
//	}

	/**
	 * Captura el evento de cerrar la ventana
	 */
	public void onCerrarVentana(Event e) throws Exception {
		if (this.self == null) {
			throw new IllegalAccessError("The self associated component is not present");
		}
		Executions.getCurrent().getDesktop().removeAttribute("ventanaTramitacion");
		this.enviarEventoAcordeon(Events.ON_NOTIFY, null, null);
		if (this.self.getParent() != null) {
			Events.sendEvent(this.self.getParent(), new Event(Events.ON_NOTIFY));
		}
	}

	/**
	 * Abro la ventana para AÃ±adir documento Nuevo, si presiono dicho botón.
	 */
	public void onAniadirDocumentoNuevo() {
		this.aniadirDocumento = (Window) Executions.createComponents("/expediente/nuevoDocumento.zul", null,
				new HashMap<Object, Object>());
		this.aniadirDocumento.setClosable(true);

		this.aniadirDocumento.doModal();
	}

	public void onSubsanar() {
		
		Integer numSubsanacion = this.ee.getCantidadSubsanar();
		Long cantidadMaxSubsanacion = null;
		String numSubsanar = getdBProperty().getString("ee.cantidad.subsanar");
		if (numSubsanar != null && !StringUtils.EMPTY.equals(numSubsanar)) {
			cantidadMaxSubsanacion = Long.parseLong(numSubsanar);
		} 
		if (numSubsanacion == null || cantidadMaxSubsanacion == null) {
			Executions.getCurrent().getDesktop().setAttribute(ConstantesWeb.VISTA_ACTIVIDAD, VISTA.EDICION);

			Window subsanarWin = (Window) Executions.createComponents("/expediente/subsanacionExpediente.zul", this.self,
					new HashMap<Object, Object>());
			subsanarWin.setClosable(true);

			subsanarWin.doModal();
			return;
		} else {
			if (numSubsanacion >= cantidadMaxSubsanacion) {
				Messagebox.show(Labels.getLabel("ee.subsanacion.msg.subsanacion"),
						Labels.getLabel("ee.subsanacion.msg.title.ok"), Messagebox.OK, Messagebox.EXCLAMATION);
			} else {
				Executions.getCurrent().getDesktop().setAttribute(ConstantesWeb.VISTA_ACTIVIDAD, VISTA.EDICION);

				Window subsanarWin = (Window) Executions.createComponents("/expediente/subsanacionExpediente.zul", this.self,
						new HashMap<Object, Object>());
				subsanarWin.setClosable(true);

				subsanarWin.doModal();
			}
		}  
	}

	public void onSearchForm() {
		HashMap<String, Object> hm = new HashMap<>();
		hm.put("nameForm", this.tipoFormBandbox.getValue());
		hm.put("idExpedient", ee.getId());
		hm.put("isNew", 0);
		Executions.getCurrent().getDesktop().setAttribute(ConstantesWeb.VISTA_ACTIVIDAD, VISTA.EDICION);
		this.buscarFormularioWin = (Window) Executions.createComponents("/expediente/macros/formWeb.zul", this.self,
				hm);
		buscarFormularioWin.setClosable(true);
		buscarFormularioWin.doModal();
	}

	public void onDeleteForm() {
		Messagebox.show(Labels.getLabel("ee.tramitacion.composer.deleteConfirm"),
				Labels.getLabel("ee.tramitacion.composer.delete"), Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
				new org.zkoss.zk.ui.event.EventListener() {
					public void onEvent(Event evt) throws InterruptedException {
						switch (((Integer) evt.getData()).intValue()) {
						case Messagebox.YES:
							confirmDeleteForm();
							break;
						case Messagebox.NO:
							break;
						}
					}
				});
	}

	public void confirmDeleteForm() {
		ExpedienteFormularioDTO fomrSelected = this.selectedExpedientForm;
		try {
			this.externalFormsService.eliminarFormularioFFDD(fomrSelected);
			this.expedienteFormularioService.eliminarFormulario(fomrSelected);
			this.loadExpedientForms();
		} catch (DynFormException e) {
			logger.error(e.getMessage());
		}
	}

	public void onCloneForm() throws Exception {
		ExpedienteFormularioDTO fomrSelected = this.selectedExpedientForm;
		HashMap<String, Object> hm = new HashMap<>();
		hm.put("nameForm", fomrSelected.getFormName());
		hm.put("idTransaction", fomrSelected.getIdDfTransaction());
		hm.put("idExpedient", fomrSelected.getIdEeExpedient());
		hm.put("isNew", 3);
		Executions.getCurrent().getDesktop().setAttribute(ConstantesWeb.VISTA_ACTIVIDAD, VISTA.EDICION);
		this.buscarFormularioWin = (Window) Executions.createComponents("/expediente/macros/formWeb.zul", this.self,
				hm);
		buscarFormularioWin.setClosable(true);
		buscarFormularioWin.doModal();
	}

	public void onViewForm() {
		ExpedienteFormularioDTO fomrSelected = this.selectedExpedientForm;
		HashMap<String, Object> hm = new HashMap<>();
		hm.put("nameForm", fomrSelected.getFormName());
		hm.put("idTransaction", fomrSelected.getIdDfTransaction());
		hm.put("isNew", 1);
		Executions.getCurrent().getDesktop().setAttribute(ConstantesWeb.VISTA_ACTIVIDAD, VISTA.EDICION);
		this.buscarFormularioWin = (Window) Executions.createComponents("/expediente/macros/viewFormGenerate.zul",
				this.self, hm);
		buscarFormularioWin.setClosable(true);
		buscarFormularioWin.doModal();
	}

	public void onChangeForm() {
		ExpedienteFormularioDTO fomrSelected = this.selectedExpedientForm;
		HashMap<String, Object> hm = new HashMap<>();
		hm.put("nameForm", fomrSelected.getFormName());
		hm.put("idTransaction", fomrSelected.getIdDfTransaction());
		hm.put("isNew", 2);
		Executions.getCurrent().getDesktop().setAttribute(ConstantesWeb.VISTA_ACTIVIDAD, VISTA.EDICION);
		this.buscarFormularioWin = (Window) Executions.createComponents("/expediente/macros/formWeb.zul", this.self,
				hm);
		buscarFormularioWin.setClosable(true);
		buscarFormularioWin.doModal();
	}

	public void onCancelar() {
		if (this.ee != null && this.ee.getListaExpedientesAsociados() != null
				&& this.ee.getListaExpedientesAsociados().size() > 0) {
			this.ee.getListaExpedientesAsociados().clear();
		}
		
		Executions.getCurrent().getDesktop().removeAttribute("ventanaTramitacion");
		this.closeAssociatedWindow();
	}

	public void onRechazar() {
		getTramitacionHelper().getActiveState().startReject();
		onEnviarTramitacion();
	}

	/**
	 * Cuando se presiona el botón MODIFICAR se deben habilitar los campos
	 * definidos.
	 *
	 * @throws Exception
	 */
	public void onModificar() throws Exception {
		if (!this.noHabilitarModificacion()) {
			// Quita readOnly direccion
			SolicitanteDireccionVM.setReadOnlyDireccion(false);
			
			this.email.setReadonly(false);
			this.telefono.setReadonly(false);
			this.descripcion.setReadonly(false);

			validarDatosPropiosDeTrata();

			// Si trata no tiene caratula entonces pregunto si tiene datos
			// propios - END

			hm.put(DatosPropiosTrataCaratulaComposer.ES_SOLO_LECTURA, false);

			if (this.expedienteExterno.isChecked()) {
				this.tipoDocumento.setDisabled(false);
				this.numeroDocumento.setReadonly(false);
//				this.noDeclaraNoPosee.setDisabled(false);

				// Si es persona se habilitaran para poder modificar nombre y
				// apellido.
				if (esPersona()) {
					this.apellido.setReadonly(false);
					this.nombre.setReadonly(false);
					this.segundoNombre.setReadonly(false);
					this.tercerNombre.setReadonly(false);
					this.segundoApellido.setReadonly(false);
					this.tercerApellido.setReadonly(false);
//					this.noDeclaraNoPosee.setDisabled(false);
				} else {
					this.razonSocial.setReadonly(false);
				}

//				if (this.noDeclaraNoPosee.isChecked()) {
//
//					this.cuitCuilDocumento.setReadonly(true);
//					this.cuitCuilTipo.setReadonly(true);
//					this.cuitCuilVerificador.setReadonly(true);
//				} else {
//					this.cuitCuilDocumento.setReadonly(false);
//					this.cuitCuilTipo.setReadonly(false);
//					this.cuitCuilVerificador.setReadonly(false);
//				}

				this.direccion.setReadonly(false);
				this.piso.setReadonly(false);
				this.departamento.setReadonly(false);
				this.codigoPostal.setReadonly(false);

			}

			guardarModificacion.setDisabled(false);
			cancelarModificacion.setDisabled(false);

		}
	}

	/**
	* 
	*/
	private void validarDatosPropiosDeTrata() {
		// Si trata no tiene caratula entonces pregunto si tiene datos propios -
		// BEGIN
		TipoDocumentoDTO tipoDocumentoCaratulaDeEE = null;

		if (trata != null && trata.getAcronimoDocumento() != null) {
			this.tipoDocumentoService.obtenerTipoDocumento(trata.getAcronimoDocumento());
		}

		if (null == tipoDocumentoCaratulaDeEE) {
			if (!CollectionUtils.isEmpty(this.trata.getDatoVariable())) {
				this.datosPropios.setDisabled(false);
			}
		} else {
			this.datosPropios.setDisabled(true);
		}
	}

	public void onGuardarModificacion() throws InterruptedException {
		validarDatosModificacionCaratula();

		this.mostrarForegroundBloqueante();
		Events.echoEvent("onUser", this.self, "guardarModificacion");
	}

	private void mostrarForegroundBloqueante() {
		Clients.showBusy(Labels.getLabel("ee.tramitacion.popup.modificacionCaratula"));
	}

	public void guardarModificacion() throws Exception {
		try {
			// debo modificar el Expediente con los datos que modifique y
			// persistirlo de nuevo.
			this.ee.getSolicitudIniciadora().getSolicitante().setEmail(this.email.getValue());
			this.ee.getSolicitudIniciadora().getSolicitante().setTelefono(this.telefono.getValue());
			this.ee.setDescripcion(this.descripcion.getValue());
			this.ee.setFechaModificacion(new Date());

			if (this.expedienteExterno.isChecked()) {
				/*
				String tipoDocumentoTemp = this.tipoDocumento.getValue();
				Long numeroDocumentoTemp = this.numeroDocumento.getValue();
				
				if (tipoDocumentoTemp != null && !tipoDocumentoTemp.equals("") && numeroDocumentoTemp != null
						&& !numeroDocumentoTemp.equals("")) {
					this.ee.getSolicitudIniciadora().getSolicitante().getDocumento()
							.setTipoDocumento(tipoDocumentoTemp.substring(0, 2));
					this.ee.getSolicitudIniciadora().getSolicitante().getDocumento()
							.setNumeroDocumento(numeroDocumentoTemp.toString());
				} else {
					this.ee.getSolicitudIniciadora().getSolicitante().getDocumento().setTipoDocumento(null);
					this.ee.getSolicitudIniciadora().getSolicitante().getDocumento().setNumeroDocumento(null);
				}
				*/
				
				this.ee.getSolicitudIniciadora().getSolicitante().getDocumento().setNumeroDocumento(this.numeroDocumento.getRawText());

				if (esPersona()) {
					this.ee.getSolicitudIniciadora().getSolicitante().setApellidoSolicitante(this.apellido.getValue());
					this.ee.getSolicitudIniciadora().getSolicitante().setNombreSolicitante(this.nombre.getValue());

					this.ee.getSolicitudIniciadora().getSolicitante()
							.setSegundoApellidoSolicitante(this.segundoApellido.getValue());
					this.ee.getSolicitudIniciadora().getSolicitante()
							.setSegundoNombreSolicitante(this.segundoNombre.getValue());

					this.ee.getSolicitudIniciadora().getSolicitante()
							.setTercerApellidoSolicitante(this.tercerApellido.getValue());
					this.ee.getSolicitudIniciadora().getSolicitante()
							.setTercerNombreSolicitante(this.tercerNombre.getValue());

					this.ee.getSolicitudIniciadora().getSolicitante().setDomicilio(this.direccion.getValue());
				} else {
					this.ee.getSolicitudIniciadora().getSolicitante()
							.setRazonSocialSolicitante(this.razonSocial.getValue());
				}
				/*
				if (!this.noDeclaraNoPosee.isChecked()) {
					if (this.cuitCuilTipo.getValue() != null && this.cuitCuilDocumento.getValue() != null
							&& this.cuitCuilVerificador.getValue() != null) {
						this.ee.getSolicitudIniciadora().getSolicitante().setCuitCuil(getCuitCuil());
					}
				} else {
					this.ee.getSolicitudIniciadora().getSolicitante().setCuitCuil(null);
				}*/
				
				this.ee.getSolicitudIniciadora().getSolicitante().setCuitCuil(null);
				
				this.ee.getSolicitudIniciadora().getSolicitante().setDepartamento(this.departamento.getValue());
				this.ee.getSolicitudIniciadora().getSolicitante().setPiso(this.piso.getValue());
				this.ee.getSolicitudIniciadora().getSolicitante().setCodigoPostal(this.codigoPostal.getValue());

			}

			// nuevo
			// fin nuevo
			this.email.setReadonly(true);
			this.telefono.setReadonly(true);
			this.descripcion.setReadonly(true);

			if (this.expedienteExterno.isChecked()) {
				this.tipoDocumento.setDisabled(true);
				this.numeroDocumento.setReadonly(true);

				if ((!this.apellido.getValue().equals("")) && (!this.nombre.getValue().equals(""))) {
					this.apellido.setReadonly(true);
					this.nombre.setReadonly(true);

					this.segundoApellido.setReadonly(true);
					this.segundoNombre.setReadonly(true);

					this.tercerApellido.setReadonly(true);
					this.tercerNombre.setReadonly(true);
				} else {
					this.razonSocial.setReadonly(true);
				}

//				this.cuitCuilTipo.setReadonly(true);
//				this.cuitCuilDocumento.setReadonly(true);
//				this.cuitCuilVerificador.setReadonly(true);

				this.direccion.setReadonly(true);

				this.departamento.setReadonly(true);
				this.piso.setReadonly(true);
				this.codigoPostal.setReadonly(true);

//				this.noDeclaraNoPosee.setDisabled(true);
			}

			// se setea el usuario que realiza la modificacion
			String username = Executions.getCurrent().getSession().getAttribute(ConstantesWeb.SESSION_USERNAME)
					.toString();
			this.ee.setUsuarioModificacion(username);
			guardarModificacion.setDisabled(true);
			cancelarModificacion.setDisabled(true);
			modificar.setDisabled(false);
			this.ee = caratulador.caratular(this.ee, this.ee.getSolicitudIniciadora(), username);
			
			// Se quitan los ceros para guardar el expediente en la base
			expedienteElectronicoService.modificarExpedienteElectronico(this.ee);
			
			// Save direccion
		    SolicitanteDireccionVM.saveDireccion(this.ee.getSolicitudIniciadora().getSolicitante().getId());
			
			tramitacionWindow.onClose();
			Messagebox.show(Labels.getLabel("te.base.composer.tipodocumentoscomposer.msj.guardados"), 
					Labels.getLabel("te.base.composer.tipodocumentoscomposer.msj.mensaje"), Messagebox.OK,
					Messagebox.INFORMATION, new EventListener() {
						public void onEvent(Event evt) {
						}
					});
			this.datosPropios.setDisabled(false);
		} finally {
			Clients.clearBusy();
			binder.loadComponent(tramitacionWindow);
			
			// Habilita readOnly direccion
			SolicitanteDireccionVM.setReadOnlyDireccion(true);
		}
	}

	private Integer validacionModificarDatosPropios() throws Exception {
		int salida = 0;

		if (!usuariosSADEService.usuarioTieneRol(datosUsuario.getUsername(), ConstantesWeb.PERMISO_MODIFICADOR)) {
			salida = 1;
		} else {
			if (!this.ee.getCodigoReparticionUsuario().trim().equalsIgnoreCase(reparticionUsuario)) {
				salida = 2;
			} else {
				Date hoy = new Date();
				Date fechaHabilitada = new Date(ee.getFechaCreacion().getTime());
				fechaHabilitada = this.diasHabilesService.HabilitadoHasta(fechaHabilitada);

				if (!hoy.before(fechaHabilitada)) {
					salida = 3;
				}
			}
		}

		return salida;
	}

	/**
	 * Valida lo datos requeridos para generar el Expediente.
	 */
	private void validarDatosModificacionCaratula() {
		if ((this.descripcion.getValue() == null) || (this.descripcion.getValue().equals(""))) {
			this.descripcion.focus();
			throw new WrongValueException(this.descripcion,
					Labels.getLabel("ee.nuevoexpediente.faltadescripciontrata"));
		}
		
		if (StringUtils.isEmpty(numeroDocumento.getValue())) {
			throw new WrongValueException(this.numeroDocumento,
					Labels.getLabel("ee.nuevasolicitud.faltanumerodocumento"));
		}

		/*
		if ((expedienteExterno.isChecked())
				&& ((tipoDocumento.getValue() == null) || (tipoDocumento.getValue().equals(""))
						&& (numeroDocumento.getValue() != null && !numeroDocumento.getValue().equals("")))) {
			throw new WrongValueException(this.tipoDocumento, Labels.getLabel("ee.nuevasolicitud.faltatipodocumento"));
		}
		*/

		/*
		if ((expedienteExterno.isChecked())
				&& ((numeroDocumento.getValue() == null) || (numeroDocumento.getValue().equals("")))
				&& (tipoDocumento.getValue() != null && !tipoDocumento.getValue().equals(""))) {
			throw new WrongValueException(this.numeroDocumento,
					Labels.getLabel("ee.nuevasolicitud.faltanumerodocumento"));
		}
		*/

		// TODO PASAR LAS RESTRICCIONES A LA BBDD O *AL MENOS* A UNA PROPIEDAD
		// DEL ENUM!!!!!!!!!!
		// TODO REESTRUCTURAR IF!!!!
		/*
		if ((expedienteExterno.isChecked()) && (tipoDocumento.getValue().equals("LC - LIBRETA CIVICA"))) {
			if (numeroDocumento.getValue().toString().length() > 7) {
				numeroDocumento.focus();
				throw new WrongValueException(numeroDocumento, Labels.getLabel("ee.nuevasolicitud.lcincorrecto"));
			}
		}
		*/

		if (expedienteExterno.isChecked()) {
			if (esPersona()) {
				if (StringUtils.isEmpty(this.apellido.getValue())) {
					throw new WrongValueException(this.apellido, Labels.getLabel("ee.nuevasolicitud.faltaapellido"));
				}

				if (StringUtils.isEmpty(this.nombre.getValue())) {
					throw new WrongValueException(this.nombre, Labels.getLabel("ee.nuevasolicitud.faltanombres"));
				}
			} else if (StringUtils.isEmpty(this.razonSocial.getValue())) {
				throw new WrongValueException(this.razonSocial, Labels.getLabel("ee.nuevasolicitud.faltarazonsocial"));
			}

			/*
			if (!noDeclaraNoPosee.isChecked()) {
				if (this.cuitCuilTipo.getValue() == null) {
					throw new WrongValueException(this.cuitCuilTipo,
							Labels.getLabel("ee.nuevasolicitud.lenghtcuitCuilTipo"));
				}
				if (this.cuitCuilDocumento.getValue() == null) {
					throw new WrongValueException(this.cuitCuilDocumento,
							Labels.getLabel("ee.nuevasolicitud.nocuitincorrecto"));
				}

				if (this.cuitCuilVerificador.getValue() == null) {
					throw new WrongValueException(this.cuitCuilVerificador,
							Labels.getLabel("ee.nuevasolicitud.lenghtcuitVerificador"));
				}

				if (this.cuitCuilTipo.getValue() != null && this.cuitCuilTipo.getValue().toString().length() != 2) {
					throw new WrongValueException(this.cuitCuilTipo,
							Labels.getLabel("ee.nuevasolicitud.lenghtcuitCuilTipo"));
				}

				String cadena = this.cuitCuilDocumento.getValue().toString();

				if (this.cuitCuilDocumento.getValue() != null
						&& this.cuitCuilDocumento.getValue().toString().length() != 8) {
					while (cadena.length() != 8) {
						cadena = "0" + cadena;
					}
					this.cuitCuilDocumento.setValue(new Long(cadena));
				}
				if (this.cuitCuilVerificador.getValue() != null
						&& this.cuitCuilVerificador.getValue().toString().length() != 1) {
					throw new WrongValueException(this.cuitCuilVerificador,
							Labels.getLabel("ee.nuevasolicitud.lenghtcuitVerificador"));
				}
				setCuitCuil(this.cuitCuilTipo.getValue().toString() + cadena
						+ this.cuitCuilVerificador.getValue().toString());
				ValidadorDeCuit validadorDeCuit = new ValidadorDeCuit();
				try {
					validadorDeCuit.validarNumeroDeCuit(getCuitCuil());
				} catch (NegocioException e) {
					throw new WrongValueException(this.cuilcuit, e.getMessage());
				}
			}
			*/
			
			/*
			if ((this.direccion.getValue() == null) || (this.direccion.getValue().equals(""))) {
				this.direccion.focus();
				throw new WrongValueException(this.direccion, Labels.getLabel("ee.nuevoexpediente.faltadomicilio"));
			}

			if ((this.codigoPostal.getValue() == null) || (this.codigoPostal.getValue().equals(""))) {
				this.codigoPostal.focus();
				throw new WrongValueException(this.codigoPostal,
						Labels.getLabel("ee.nuevoexpediente.faltacodigopostal"));
			}
			*/

			String direccion = this.direccion.getValue();
			this.direccion.setValue(direccion.toUpperCase());
		}

		if ((email.getValue() == null) || (email.getValue().equals(""))) {
		} else {
			boolean valor = MailUtil.validateMail(email.getValue().toString());

			if (valor == false) {
				throw new WrongValueException(this.email, Labels.getLabel("ee.nuevasolicitud.erroremail"));
			}
		}
	}

	private boolean esPersona() {
		String apellidoSolicitante = this.ee.getSolicitudIniciadora().getSolicitante().getApellidoSolicitante();
		String nombreSolicitante = this.ee.getSolicitudIniciadora().getSolicitante().getNombreSolicitante();

		return ((!StringUtils.isEmpty(apellidoSolicitante)) && (!StringUtils.isEmpty(nombreSolicitante)));
	}

	public void onCancelarModificacion() throws Exception {
		String tipoExpediente;
		boolean esExpedienteInterno = this.ee.getSolicitudIniciadora().isEsSolicitudInterna();

		if (esExpedienteInterno) {
			tipoExpediente = "expedienteInterno";
		} else {
			tipoExpediente = "expedienteExterno";
		}

		String descripcion = this.ee.getDescripcion();
		String telefono = this.ee.getSolicitudIniciadora().getSolicitante().getTelefono();
		String email = this.ee.getSolicitudIniciadora().getSolicitante().getEmail();

		if (this.expedienteExterno.getId().equals(tipoExpediente)) {

			String tipoDocumentoTemp = this.ee.getSolicitudIniciadora().getSolicitante().getDocumento()
					.getTipoDocumento();
			String tipoDocumento = null;
			if (tipoDocumentoTemp != null) {
				if (tipoDocumentoTemp.equals(ConstantesWeb.CU_VALOR)) {
					tipoDocumento = ConstantesWeb.CU_VALOR + "-" + ConstantesWeb.CU_DESCRIPCION;
				} else {
					tipoDocumento = TipoDocumentoPosible.valueOf(tipoDocumentoTemp).getDescripcionCombo();
				}
			}

			String numeroDocumentoTemp = this.ee.getSolicitudIniciadora().getSolicitante().getDocumento()
					.getNumeroDocumento();
			/*
			Long numeroDocumento = null;
			if (numeroDocumentoTemp != null && !numeroDocumentoTemp.equals("")) {
				numeroDocumento = Long.parseLong(numeroDocumentoTemp);
			}
			*/

			String razonSocial = this.ee.getSolicitudIniciadora().getSolicitante().getRazonSocialSolicitante();
			String apellido = this.ee.getSolicitudIniciadora().getSolicitante().getApellidoSolicitante();
			String nombre = this.ee.getSolicitudIniciadora().getSolicitante().getNombreSolicitante();

			String segundoApellido = this.ee.getSolicitudIniciadora().getSolicitante().getSegundoApellidoSolicitante();
			String tercerApellido = this.ee.getSolicitudIniciadora().getSolicitante().getTercerApellidoSolicitante();

			String segundoNombre = this.ee.getSolicitudIniciadora().getSolicitante().getSegundoNombreSolicitante();
			String tercerNombre = this.ee.getSolicitudIniciadora().getSolicitante().getTercerNombreSolicitante();

			String direccion = this.ee.getSolicitudIniciadora().getSolicitante().getDomicilio();
			String piso = this.ee.getSolicitudIniciadora().getSolicitante().getPiso();
			String dpto = this.ee.getSolicitudIniciadora().getSolicitante().getDepartamento();
			String codigoPostal = this.ee.getSolicitudIniciadora().getSolicitante().getCodigoPostal();

//			String cuitCuil = this.ee.getSolicitudIniciadora().getSolicitante().getCuitCuil();

			String documentoCuitCuil = null;
			String tipoCuitCuil = null;
			String verificadorCuitCuil = null;

//			if (this.ee.getSolicitudIniciadora().getSolicitante().getCuitCuil() != null) {
//				tipoCuitCuil = cuitCuil.substring(0, 2);
//				documentoCuitCuil = cuitCuil.substring(2, 10);
//				verificadorCuitCuil = cuitCuil.substring(10, 11);
//			}

			this.tipoDocumento.setValue(tipoDocumento);
			this.tipoDocumento.setReadonly(true);
			//this.numeroDocumento.setValue(numeroDocumento);
			this.numeroDocumento.setValue(numeroDocumentoTemp);
			this.numeroDocumento.setReadonly(true);
			this.razonSocial.setValue(razonSocial);
			this.nombre.setValue(nombre);
			this.apellido.setValue(apellido);
			this.razonSocial.setReadonly(true);
			this.apellido.setReadonly(true);
			this.nombre.setReadonly(true);

			this.segundoNombre.setReadonly(true);
			this.segundoNombre.setValue(segundoNombre);

			this.tercerNombre.setReadonly(true);
			this.tercerNombre.setValue(tercerNombre);

			this.segundoApellido.setReadonly(true);
			this.segundoApellido.setValue(segundoApellido);

			this.tercerApellido.setReadonly(true);
			this.tercerApellido.setValue(tercerApellido);

//			this.cuitCuilTipo.setReadonly(true);
//			if (this.ee.getSolicitudIniciadora().getSolicitante().getCuitCuil() != null) {
//				this.cuitCuilTipo.setValue(new Long(tipoCuitCuil));
//				this.cuitCuilDocumento.setValue(new Long(documentoCuitCuil));
//				this.cuitCuilVerificador.setValue(new Long(verificadorCuitCuil));
//			}
//			this.cuitCuilDocumento.setReadonly(true);
//
//			this.cuitCuilVerificador.setReadonly(true);

			this.direccion.setReadonly(true);
			this.direccion.setValue(direccion);

			this.departamento.setReadonly(true);
			this.departamento.setValue(dpto);

			this.piso.setReadonly(true);
			this.piso.setValue(piso);

			this.codigoPostal.setReadonly(true);
			this.codigoPostal.setValue(codigoPostal);

//			this.noDeclaraNoPosee.setDisabled(true);

		} else {
			this.tipoDocumento.setDisabled(true);
			this.numeroDocumento.setDisabled(true);
			this.razonSocial.setDisabled(true);
			this.apellido.setReadonly(true);
			this.nombre.setReadonly(true);

			this.segundoNombre.setReadonly(true);
			this.tercerNombre.setReadonly(true);
			this.segundoApellido.setReadonly(true);
			this.tercerApellido.setReadonly(true);
//			this.cuitCuilTipo.setReadonly(true);
//			this.cuitCuilDocumento.setReadonly(true);
//			this.cuitCuilVerificador.setReadonly(true);
			this.direccion.setReadonly(true);
			this.departamento.setReadonly(true);
			this.piso.setReadonly(true);
			this.codigoPostal.setReadonly(true);

//			this.noDeclaraNoPosee.setDisabled(true);
		}

		this.descripcion.setValue(descripcion);
		this.descripcion.setReadonly(true);
		this.email.setValue(email);
		this.email.setReadonly(true);
		this.telefono.setValue(telefono);
		this.telefono.setReadonly(true);
		this.tipoDocumento.setDisabled(true);
		// nuevo
		// fin nuevo
		guardarModificacion.setDisabled(true);
		cancelarModificacion.setDisabled(true);
		
		// Precarga direccion
		SolicitanteDireccionVM.precargaDireccion(this.ee.getSolicitudIniciadora().getSolicitante().getId());
		
		// Habilita readOnly direccion
		SolicitanteDireccionVM.setReadOnlyDireccion(true);
	}

	/**
	 * @author eduavega Metodo que permite eliminar el achivo que se subio al
	 *         respositorio alfresco y a su vez tambien ser eliminado de la base
	 *         de datos.
	 */
	public void onEliminarArchivoDeTrabajo() {
		ArchivoDeTrabajoDTO archivoDeTrabajo = this.listaArhivosDeTrabajo.get(documentoTrabajoLb.getSelectedIndex());

		if (archivoDeTrabajo.isDefinitivo()) {
			throw new WrongValueException(Labels.getLabel("ee.tramitacion.noeliminar"));
		}

		this.listaArhivosDeTrabajo.remove(archivoDeTrabajo);

		archivosDeTrabajoABorrar.add(archivoDeTrabajo);
		binder.loadComponent(documentoTrabajoLb);
	}

	private void eliminarArchivosDeTrabajo() {

		for (ArchivoDeTrabajoDTO archivoDeTrabajo : archivosDeTrabajoABorrar) {
			String nombreArchivoEliminar = archivoDeTrabajo.getNombreArchivo();

			String nombreSpace = this.ee.getCodigoCaratula();
			String nombreSpaceWebDav = nombreSpace.trim();
			// TODO borrar el archivoDeTrabajo desde Otro lado
			if (ConstantesWeb.GUARDA_DOCUMENTAL_WEBDAV
					.equalsIgnoreCase(appProperty.getString("app.sistema.gestor.documental"))) {
				archivoDeTrabajoService.eliminarArchivoDeTrabajoPorWebDav(nombreArchivoEliminar, nombreSpaceWebDav);
			}
			archivoDeTrabajoService.eliminarAchivoDeTrabajo(archivoDeTrabajo);
		}
	}

	/**
	 * @author eduavega Metodo que permite eliminar el achivo que se subio al
	 *         respositorio alfresco y a su vez tambien ser eliminado de la base
	 *         de datos.
	 */
	public void onDesasociarArchivosDeTrabajo() throws InterruptedException {
		ArchivoDeTrabajoDTO archivoDeTrabajo = this.selectedArchivoDeTrabajo;
		String nombreArchivoEliminar = archivoDeTrabajo.getNombreArchivo();

		String nombreSpace = this.ee.getCodigoCaratula();
		String nombreSpaceWebDav = nombreSpace.trim();

		this.ee.getArchivosDeTrabajo().remove(this.selectedArchivoDeTrabajo);
		archivosDeTrabajoABorrar.add(archivoDeTrabajo);

		setVariableWorkFlow("idExpedienteElectronico", ee.getId());
		this.cargarArchivosDeTrabajoFiltrados();
		this.binder.loadComponent(documentoTrabajoLb);
	}

	/**
	 * Metodo que permite visualizar un archivo de trabajo en el repositorio
	 *
	 * @autor IES
	 *
	 *        Servicios utilizados:IAccesoWebDavService
	 *        visualizaDocumentoService Métodos utilizados del
	 *        servicio:visualizarDocumento(String path)
	 *
	 *        Variables importantes utilizadas:
	 *
	 * @param String
	 *            path :Cadena que se usa como parámetro en el método
	 *            BufferedInputStream visualizarDocumento(String path) para
	 *            busqueda del Archivo de trabajo, la cual se completa con:
	 *
	 *            ° pathDocumentoDeTrabajo .- Ubicación del Documento de
	 *            Trabajo. ° nombreSpaceWebDav .- Nombre del Espacio WebDav. °
	 *            archivoDeTrabajo.getNombreArchivo() .- Nombre del Archivo.
	 *
	 * @param File
	 *            fihero :Fichero utilizado para obtener el tipo de
	 *            fichero(MimeType).
	 * @param String
	 *            tipoFichero : Tipo de fichero del Archivo.
	 * @param BufferedInputStream
	 *            file : Variable que recibe el resultado tipo Inputstream del
	 *            Servicio IAccesoWebDavService visualizaDocumentoService.
	 * @throws DocumentoOArchivoNoEncontradoException
	 * @throws Exception
	 *
	 *
	 *
	 *
	 */
	public void onVisualizarArchivosDeTrabajo() throws Exception {
		try {
			ArchivoDeTrabajoDTO archivoDeTrabajo = this.selectedArchivoDeTrabajo;
			File fichero = new File(archivoDeTrabajo.getNombreArchivo());
			String nombreArchivo = archivoDeTrabajo.getNombreArchivo();
			String tipoFichero = new MimetypesFileTypeMap().getContentType(fichero);

			String nombreSpace = BusinessFormatHelper.formarPathWebDavApache(this.ee.getCodigoCaratula());

			// Pedir a la task el assignee
			Task task = this.getWorkingTask();
			String usuarioActual = task.getAssignee();
			if (this.ee.getEsReservado() && !this.datosUsuario.getUsername().equals(usuarioActual)) {
				if (!permisoVisualizacionService.tienePermisoVisualizacion(this.datosUsuario, archivoDeTrabajo)) {
					throw new DocumentoOArchivoSinPermisoDeVisualizacionException(null);
				}
			}

			String nombreSpaceWebDav = nombreSpace.trim();
			String pathDocumentoDeTrabajo = "Documentos_De_Trabajo";
			String path = pathDocumentoDeTrabajo + "/" + nombreSpaceWebDav + "/" + archivoDeTrabajo.getNombreArchivo();
			BufferedInputStream file = this.visualizaDocumentoService.visualizarDocumento(path);
			Filedownload.save(file, tipoFichero, nombreArchivo);
		} catch (DocumentoOArchivoNoEncontradoException a) {
			Messagebox.show(Labels.getLabel("ee.tramitacion.archivoNoExisteEnRepositorio"),
					Labels.getLabel("ee.tramitacion.informacion.archivoNoExiste"), Messagebox.OK,
					Messagebox.EXCLAMATION);
		} catch (DocumentoOArchivoSinPermisoDeVisualizacionException e) {
			Messagebox.show(Labels.getLabel("ee.tramitacion.archivoNoDescargados"),
					Labels.getLabel("ee.tramitacion.informacion.archivoNoExiste"), Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
	}

	/**
	 *
	 * Metodo que permite buscar expedientes electronicos y expedientes en Sade
	 *
	 * @author eduavega
	 *
	 */
	public void onBuscarExpediente() throws InterruptedException {
		ExpedienteElectronicoDTO expedienteElectronico = new ExpedienteElectronicoDTO();
		ExpedienteAsociadoEntDTO expedienteasociadoee = new ExpedienteAsociadoEntDTO();
		validarDatosFormulario();

		String tipoExpediente = (String) this.comboActuacion.getSelectedItem().getValue();
		Integer anio = this.anioSADE.getValue();
		Integer numero = this.numeroSADE.getValue();
		String reparticionUsuario = (String) this.reparticionBusquedaUsuario.getValue().trim();

		if (validarConExpedienteActual(this.ee, anio, numero)) {
			Messagebox.show(Labels.getLabel("ee.tramitacion.expedienteActualNoSePuedeAsociar"),
					Labels.getLabel("ee.tramitacion.expedienteAsociado.titulo"), Messagebox.OK,
					Messagebox.EXCLAMATION);
		} else {
			expedienteElectronico = expedienteElectronicoService.obtenerExpedienteElectronico(tipoExpediente, anio,
					numero, reparticionUsuario);

			if ((expedienteElectronico != null) /*
												 * && (!expedienteElectronico.
												 * getEstado().equalsIgnoreCase(
												 * "guarda temporal"))
												 */) {
				// Si esta, debo preguntar: si es cabecera de una TC/fusión
				if ((expedienteElectronico.getEsCabeceraTC() == null)
						|| ((expedienteElectronico.getEsCabeceraTC() != null)
								&& !expedienteElectronico.getEsCabeceraTC())) {
					expedienteasociadoee.setTipoDocumento(expedienteElectronico.getTipoDocumento());
					expedienteasociadoee.setAnio(expedienteElectronico.getAnio());
					expedienteasociadoee.setNumero(expedienteElectronico.getNumero());
					expedienteasociadoee.setSecuencia(expedienteElectronico.getSecuencia());
					expedienteasociadoee.setDefinitivo(expedienteElectronico.getDefinitivo());
					expedienteasociadoee
							.setCodigoReparticionActuacion(expedienteElectronico.getCodigoReparticionActuacion());
					expedienteasociadoee
							.setCodigoReparticionUsuario(expedienteElectronico.getCodigoReparticionUsuario());
					expedienteasociadoee.setEsElectronico(expedienteElectronico.getEsElectronico());
					expedienteasociadoee.setIdCodigoCaratula(expedienteElectronico.getId());
					expedienteasociadoee.setFechaAsociacion(new Date());
					expedienteasociadoee.setUsuarioAsociador(loggedUsername);
					expedienteasociadoee.setIdTask(this.workingTask.getExecutionId());

					boolean estaEnLista = false;

					if (listaExpedienteAsociado.contains(expedienteasociadoee)) {
						estaEnLista = true;
					} else {
						// Busco si el expediente esta en la lista de
						// expedientes asociados TC
						for (ExpedienteAsociadoEntDTO expedienteAsociadoTC : this.ee.getListaExpedientesAsociados()) {
							if (expedienteAsociadoTC.getNumero().equals(expedienteasociadoee.getNumero())
									&& expedienteAsociadoTC.getAnio().equals(expedienteasociadoee.getAnio())) {
								estaEnLista = true;

								// Si esta en la lista, pregunto si esta
								// asociado por tramitacion Conjunta.
								if ((expedienteAsociadoTC.getEsExpedienteAsociadoTC() != null)
										&& expedienteAsociadoTC.getEsExpedienteAsociadoTC()) {
									Messagebox.show(Labels.getLabel("ee.tramitacion.expedienteAsociadoAdvertenciaTC"),
											Labels.getLabel("ee.tramitacion.expedienteAsociado.titulo"), Messagebox.OK,
											Messagebox.EXCLAMATION);

									break;
								} else if (!workingTask.getActivityName().equals(TRAMITACION_EN_PARALELO)) {
									// Puede que el expediente lo haya asociado
									// un usuario en paralelo.
									Messagebox.show(Labels.getLabel("ee.tramitacion.expedienteAsociado"),
											Labels.getLabel("ee.tramitacion.expedienteAsociado.titulo"), Messagebox.OK,
											Messagebox.EXCLAMATION);

									break;
								} else {
									// Si no es ninguna de las anteriores,
									// significa que esta asociado desde la
									// pestaÃ±a de expedientes asociados.
									Messagebox.show(Labels.getLabel("ee.tramitacion.expedienteAsociadoParalelo"),
											Labels.getLabel("ee.tramitacion.expedienteAsociado.titulo"), Messagebox.OK,
											Messagebox.EXCLAMATION);

									break;
								}
							}
						}
					}

					if (!estaEnLista) {
						listaExpedienteAsociado.add(expedienteasociadoee);
						cargarExpedientesAsociadosFiltrados();
					}

					limpiarFormulario();
				} else { // Es cabecera de una TC o bien de una Fusión o tiene
					// Expedientes Asociados

					ExpedienteAsociadoEntDTO expAsocAux = expedienteElectronico.getListaExpedientesAsociados().get(0);

					if (expAsocAux.getEsExpedienteAsociadoFusion() != null) { // es
						// cabecera
						// de
						// Fusion
						Messagebox.show(
								Labels.getLabel("ee.tramitacion.expedienteAsociado.esCabeceraDeFusion",
										new String[] { expedienteElectronico.getEstado() }),
								Labels.getLabel("ee.tramitacion.expedienteAsociado.noExiste"), Messagebox.OK,
								Messagebox.EXCLAMATION);
					} else if (expedienteElectronico.getEsCabeceraTC()) {
						Messagebox.show(
								Labels.getLabel("ee.tramitacion.expedienteAsociado.esCabeceraDeTC",
										new String[] { expedienteElectronico.getEstado() }),
								Labels.getLabel("ee.tramitacion.expedienteAsociado.noExiste"), Messagebox.OK,
								Messagebox.EXCLAMATION);
					}

					limpiarFormulario();

					return;
				}
			} else {
				// Si el expediente no lo encontro en expedientes Electronicos,
				// lo busca en SADE
//				ExpedienteAsociadoEntDTO expedienteasociadosade = expedienteSadeService
//						.obtenerExpedienteSade(tipoExpediente, anio, numero, reparticionUsuario);
//				if ((expedienteasociadosade != null)
//						&& !this.ee.getListaExpedientesAsociados().contains(expedienteasociadosade)) {
//					String trata = expedienteSadeService.obtenerCodigoTrataSADE(expedienteasociadosade);
//					expedienteasociadosade.setTrata(trata);
//					expedienteasociadosade.setEsElectronico(false);
//					expedienteasociadosade.setFechaAsociacion(new Date());
//					expedienteasociadosade.setUsuarioAsociador(loggedUsername);
//					expedienteasociadosade.setIdTask(this.workingTask.getExecutionId());

					// // Si el expediente es cabeceraTC, la tramitacion
					// // conjunta ya se ha confirmado y los expedientes que
					// se
					// // asocien a partir de ese
					// // momento tendran el id del expediente cabecera para
					// // luego poder copiarlos a los expedientes hijos de
					// la
					// // tramitacion.
//					listaExpedienteAsociado.add(expedienteasociadosade);
//					cargarExpedientesAsociadosFiltrados();
//				} else {
//					if (!workingTask.getActivityName().equals(TRAMITACION_EN_PARALELO)) {
//						Messagebox.show(Labels.getLabel("ee.tramitacion.expedienteAsociado"),
//								Labels.getLabel("ee.tramitacion.expedienteAsociado.titulo"), Messagebox.OK,
//								Messagebox.EXCLAMATION);
//					} else {
//						Messagebox.show(Labels.getLabel("ee.tramitacion.expedienteAsociadoParalelo"),
//								Labels.getLabel("ee.tramitacion.expedienteAsociado.titulo"), Messagebox.OK,
//								Messagebox.EXCLAMATION);
//					}
//				}
//
//				limpiarFormulario();
				Messagebox.show(Labels.getLabel("ee.tramitacion.expedienteAsociado.EEnoExiste"),
						Labels.getLabel("ee.tramitacion.expedienteAsociado.noExiste"), Messagebox.OK,
						Messagebox.EXCLAMATION);
			}
		}
	}

	private void validarDatosFormulario() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String fechaActual = sdf.format(new Date());
		String anioFormateado = fechaActual.substring(6, 10);
		int anioActual = Integer.parseInt(anioFormateado);
		Integer anioValido = new Integer(anioActual);

		if ((this.anioSADE.getValue() == null) || (this.anioSADE.getValue().equals(""))) {
			throw new WrongValueException(this.anioSADE, Labels.getLabel("ee.tramitacion.faltaAnio"));
		}

		if (this.anioSADE.getValue() > anioValido) {
			throw new WrongValueException(this.anioSADE, Labels.getLabel("ee.tramitacion.anioInvalido"));
		}

		if ((this.numeroSADE.getValue() == null) || (this.numeroSADE.getValue().equals(""))) {
			throw new WrongValueException(this.numeroSADE, Labels.getLabel("ee.tramitacion.faltaNumeroDeExpediente"));
		}

		if ((this.reparticionBusquedaUsuario.getValue() == null)
				|| (this.reparticionBusquedaUsuario.getValue().equals(""))) {
			throw new WrongValueException(reparticionBusquedaUsuario,
					Labels.getLabel("ee.tramitacion.faltaReparticion"));
		}
	}

	private boolean validarConExpedienteActual(ExpedienteElectronicoDTO expedienteElectronico, Integer anio,
			Integer numero) {
		String auxNumero = BusinessFormatHelper.quitaCerosNumeroActuacion(expedienteElectronico.getNumero());

		return expedienteElectronico.getAnio().equals(anio) && Integer.valueOf(auxNumero).equals(numero);
	}

	public void onExecuteAsociar() throws InterruptedException {
		if (!this.ee.getListaExpedientesAsociados().contains(this.selectedExpedienteAsociado)) {
			this.selectedExpedienteAsociado.setFechaAsociacion(new Date());
			this.selectedExpedienteAsociado.setUsuarioAsociador(loggedUsername);
			this.selectedExpedienteAsociado.setIdTask(this.workingTask.getExecutionId());

			// Si el expediente es cabecera en el expediente asociado debo
			// guardar
			// el id del padre.
			if ((this.ee.getEsCabeceraTC() != null) && this.ee.getEsCabeceraTC()) {
				this.selectedExpedienteAsociado.setIdExpCabeceraTC(this.ee.getId());
			}

			this.ee.getListaExpedientesAsociados().add(this.selectedExpedienteAsociado);
			this.ee.setFechaModificacion(new Date());
			this.listaExpedienteAsociado.remove(this.selectedExpedienteAsociado);
			// Se realiza el update del ee
			this.ee = expedienteElectronicoService.modificarExpedienteElectronico2(this.ee);
			obtenerAsociadoSeleccionado();
//			expedienteElectronicoService.modificarExpedienteElectronico(this.ee);
			Messagebox.show(Labels.getLabel("ee.tramitacion.expedienteAsociado.mensaje"),
					Labels.getLabel("ee.tramitacion.expedienteAsociado.titulo"), Messagebox.OK, Messagebox.INFORMATION);
			setVariableWorkFlow("idExpedienteElectronico", ee.getId());
		} else {
			Messagebox.show(Labels.getLabel("ee.tramitacion.expedienteAsociadoTCNoExiste"),
					Labels.getLabel("ee.tramitacion.expedienteAsociado.noExiste"), Messagebox.OK,
					Messagebox.EXCLAMATION);
			this.listaExpedienteAsociado.remove(this.selectedExpedienteAsociado);
		}

		cargarExpedientesAsociadosFiltrados();
		this.binder.loadComponent(this.listboxExpedientes);
	}

	private void obtenerAsociadoSeleccionado() {
		for (ExpedienteAsociadoEntDTO ea : this.ee.getListaExpedientesAsociados()) {
			if (ea.getAsNumeroSade().equals(this.selectedExpedienteAsociado.getAsNumeroSade())) {
				this.selectedExpedienteAsociado = ea;
				break;
			}
		}
	}

	public void onExecuteDesasociar() throws InterruptedException {
		this.listaExpedienteAsociado.remove(this.selectedExpedienteAsociado);
		this.ee.getListaExpedientesAsociados().remove(this.selectedExpedienteAsociado);
		// Se realiza el update del ee
		this.ee.setFechaModificacion(new Date());
		expedienteElectronicoService.modificarExpedienteElectronico(this.ee);
		this.expedienteAsociadoService.deleteExpedienteAsociado(this.selectedExpedienteAsociado);
		cargarExpedientesAsociadosFiltrados();
		setVariableWorkFlow("idExpedienteElectronico", ee.getId());
		alert("El Expediente: " + selectedExpedienteAsociado.getAsNumeroSade() + " se desasoció con éxito!");
		this.binder.loadComponent(this.listboxExpedientes);
	}

	public void onVerExpedienteAsociado() {
		Executions.getCurrent().getDesktop().setAttribute("codigoExpedienteElectronico",
				this.selectedExpedienteAsociado.getAsNumeroSade());

		Executions.getCurrent().getDesktop().setAttribute("esConsultaDetalle", true);

		Window openModalWindow = (Window) Executions.createComponents("/expediente/detalleExpedienteElectronico.zul",
				this.self, new HashMap<Object, Object>());
		openModalWindow.doModal();

	}

	public void onBuscarDocumentoValidar() throws InterruptedException {
		boolean esdocumento = false;
		boolean esdocumentoespecial = false;

		if ((this.tiposDocumentoBandbox.getValue() != null)
				&& (!this.tiposDocumentoBandbox.getValue().trim().isEmpty())) {
			esdocumento = true;
		}

		if ((this.tiposDocumentoEspecialBandbox.getValue() != null)
				&& (!this.tiposDocumentoEspecialBandbox.getValue().trim().isEmpty())) {
			esdocumentoespecial = true;
		}

		if (esdocumento && !esdocumentoespecial) {
			onBuscarDocumento();
		} else {
			if (esdocumentoespecial && !esdocumento) {
				onBuscarDocumentoNumeroEspecial();
			} else {
				throw new WrongValueException(this.tiposDocumentoBandbox,
						Labels.getLabel("ee.tramitacion.seleccioneUnDocumento"));
			}
		}
	}

	public void onSubsanarDocumento() throws InterruptedException {
		Window subsanarDocumentos = (Window) Executions.createComponents("/expediente/subsanacionDeDocumentos.zul",
				this.self, new HashMap<Object, Object>());

		subsanarDocumentos.doModal();
	}
	
	public void onSubirDocumentoImportado() {
		hm.put("docImportado", new Boolean(true));
		Window subirDocumento = (Window) Executions.createComponents("/expediente/subirDocumentoImportado.zul", this.self,
				hm);
		Executions.getCurrent().getDesktop().setAttribute("ee", this.ee);
		subirDocumento.doModal();
		
	}
	
	public void onVincularDocumento() {
		Map<String, Object> parametros = new HashMap<>();
		
		parametros.put("ee", this.ee);
		parametros.put("botonVuc", comunicarTadButton);
		parametros.put("workingTask",this.workingTask);
		parametros.put("docNotifi", this.documentosNotificablesSinNotificar);
		
		Window vincularDoc = (Window) Executions
				.createComponents("/expediente/producirDocumento/vincularDocumento.zul",
						this.self,
				parametros);
		vincularDoc.doModal();
	}
	
	public void onIniciarDocumento() {
		Executions.getCurrent().getDesktop().setAttribute("docImportado", new Boolean(false));
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("expediente", this.ee); 
		hm.put("docImportado", new Boolean(false));
		Window inciarDocumento = (Window) Executions.createComponents("/expediente/iniciarDocumento.zul", this.self,
				hm);
		Executions.getCurrent().getDesktop().setAttribute("ee", this.ee);
		inciarDocumento.doModal();
	}

	public void onNotificarTad() {
		HashMap<Object, Object> map = new HashMap<Object, Object>();
		map.put("expediente", this.ee);
		Window comunicarTadWindow = (Window) Executions.createComponents("/notificacion/documentosTadInbox.zul",
				this.self, map);
		comunicarTadWindow.doModal();
	}

	public void onBuscarPorMetadato() throws InterruptedException {

		HashMap<String, Object> hma = new HashMap<String, Object>();
		hma.put("expedienteElectronico", this.ee);
		hma.put("task", this.workingTask);
		hma.put("composer", this);
		this.buscarPorMetadatoWindow = (Window) Executions.createComponents("/expediente/buscarPorMetadato.zul", null,
				hma);
		this.buscarPorMetadatoWindow.setParent(this.tramitacionWindow);
		this.buscarPorMetadatoWindow.setPosition("center");
		this.buscarPorMetadatoWindow.setClosable(true);
		this.buscarPorMetadatoWindow.doModal();

	}

	public void onBuscarDocumento() throws InterruptedException {
		validarDatosBuscarDocumento();
//         Quito la ejecucion de extraerAcronimo porque ahora en tiposDocumentoBandbox se guarda solo el tipo de documento
//		String actuacionSADETipoDoc = (String) extraerAcronimo(this.tiposDocumentoBandbox.getValue());
		String actuacionSADETipoDoc = (String) this.tiposDocumentoBandbox.getValue();
		Integer anioDocumento = this.anioSADEDocumento.getValue();
		Integer numeroDocumento = this.numeroSADEDocumento.getValue();
		String reparticionDocumento = (String) this.reparticionBusquedaDocumento.getValue().trim();

		buscarDoc(actuacionSADETipoDoc, anioDocumento, numeroDocumento, reparticionDocumento);
	}

	public void buscarDoc(String actuacionSADETipoDoc, Integer anioDocumento, Integer numeroDocumento,
			String reparticionDocumento) throws InterruptedException {
		DocumentoDTO documentoEstandard = null;

		documentoEstandard = documentoManagerService.buscarDocumentoEstandar(actuacionSADETipoDoc, anioDocumento,
				numeroDocumento, reparticionDocumento);

		if (documentoEstandard != null) {

			if (documentoEstandard.getMotivoDepuracion() != null) {
				Messagebox.show(Labels.getLabel("ee.tramitacion.documentoDepurado"),
						Labels.getLabel("ee.tramitacion.informacion.documentoNoExiste"), Messagebox.OK,
						Messagebox.EXCLAMATION);
				return;
			}

			if (!this.ee.getDocumentos().contains(documentoEstandard)) {
				// TODO FALLAR SI NO SE ENCUENTRA
				TipoDocumentoDTO tipoDeDocumento = tipoDocumentoService
						.obtenerTipoDocumento(documentoEstandard.getTipoDocAcronimo());

				if (!estaHabilitado(documentoEstandard.getTipoDocAcronimo())) {
					throw new WrongValueException(this.tiposDocumentoBandbox,
							"Tipo de Documento No habilitado para la trata. Verifique los datos ingresados.");
				}

				// Habilita el botón de Notificar VUC
				if (tipoDeDocumento.getEsNotificable() && comunicarTadButton != null && comunicarTadButton.isDisabled()) {
					agregarDocNotifiSinNoti(documentoEstandard.getNumeroSade());
				}

				documentoEstandard.setTipoDocumento(tipoDeDocumento);
				documentoEstandard.setFechaAsociacion(new Date());
				documentoEstandard.setUsuarioAsociador(loggedUsername);
				documentoEstandard.setIdTask(this.workingTask.getExecutionId());

				// Si el expediente es cabeceraTC, la tramitacion conjunta ya se
				// ha confirmado y los documentos que se adjunten a partir de
				// ese
				// momento tendran el id del expediente cabecera para luego
				// poder copiarlos a los expedientes adjuntos.
				if ((this.ee.getEsCabeceraTC() != null) && this.ee.getEsCabeceraTC()) {
					documentoEstandard.setIdExpCabeceraTC(this.ee.getId());
				}
				this.ee.getDocumentos().add(documentoEstandard);
				this.ee.setFechaModificacion(new Date());
				this.enviarEventoAcordeon(Events.ON_RENDER, this.ee, false);

				if (ee.getTrata().getTipoReserva().getTipoReserva().equals(ConstantesWeb.RESERVA_TOTAL)
						&& !tipoDeDocumento.getEsConfidencial()) {
					Messagebox.show(
							Labels.getLabel("te.tramitacion.documento") + actuacionSADETipoDoc + "-" + anioDocumento.toString() + "-"
									+ BusinessFormatHelper.completarConCerosNumActuacion(numeroDocumento) + "-"
									+ reparticionDocumento + " " + Labels.getLabel("ee.tramitacion.documentoAsociado")
									+ Labels.getLabel("te.tramitacion.confidencial.question"),
							Labels.getLabel("ee.tramitacion.titulo.asociado"), Messagebox.OK, Messagebox.INFORMATION);
				}
			} else {
				if (!workingTask.getActivityName().equals(TRAMITACION_EN_PARALELO)) {
					Messagebox.show(Labels.getLabel("ee.tramitacion.documentoNoAsociado"),
							Labels.getLabel("ee.tramitacion.informacion.documentoNoAsociado"), Messagebox.OK,
							Messagebox.EXCLAMATION);
				} else {
					Messagebox.show(Labels.getLabel("ee.tramitacion.documentoNoAsociadoParalelo"),
							Labels.getLabel("ee.tramitacion.informacion.documentoNoAsociado"), Messagebox.OK,
							Messagebox.EXCLAMATION);
				}
			}
		} else {
			Messagebox.show(Labels.getLabel("ee.tramitacion.documentoNoExiste"),
					Labels.getLabel("ee.tramitacion.informacion.documentoNoExiste"), Messagebox.OK,
					Messagebox.EXCLAMATION);
		}

		this.tiposDocumentoBandbox.setValue("");
		this.anioSADEDocumento.setValue(null);
		this.numeroSADEDocumento.setValue(null);
		this.reparticionBusquedaDocumento.setValue("");

	}

	private void agregarDocNotifiSinNoti(String numeroSade) {
		comunicarTadButton.setDisabled(false);
		subsanar.setDisabled(false);
		// Agrega el documento al set del composer
		if (documentosNotificablesSinNotificar == null) {
			this.documentosNotificablesSinNotificar = new HashSet<>();
		}
		this.documentosNotificablesSinNotificar.add(numeroSade);
	}

	// TODO metodo duplicado. Extraer
	private boolean estaHabilitado(String acronimo) {
		this.tiposDocumentosGEDOBD = new ArrayList<TrataTipoDocumentoDTO>();
		this.tiposDocumentosGEDOBD = tipoDocumentoService.buscarTrataTipoDocumentoPorTrata(this.ee.getTrata());

		if ((this.tiposDocumentosGEDOBD.size() > 0) && !this.tiposDocumentosGEDOBD.get(0).getAcronimoGEDO().trim()
				.equals(ConstantesWeb.SELECCIONAR_TODOS)) {
			for (TrataTipoDocumentoDTO documentoBD : this.tiposDocumentosGEDOBD) {
				if (acronimo.trim().equals(documentoBD.getAcronimoGEDO().trim())) {
					return true;
				}
			}
		} else {
			if ((this.tiposDocumentosGEDOBD.size() > 0) && this.tiposDocumentosGEDOBD.get(0).getAcronimoGEDO().trim()
					.equals(ConstantesWeb.SELECCIONAR_TODOS)) {
				return true;
			}
		}

		return false;
	}

	public void onBuscarDocumentoNumeroEspecial() throws InterruptedException {
		validarDatosBuscarDocumentoPorNumeroEspecial();

		String actuacionSADETipoDoc = (String) extraerAcronimo(this.tiposDocumentoEspecialBandbox.getValue());
		Integer anioDocumento = this.anioSADEDocumentoNumeroEspecial.getValue();
		Integer numeroDocumento = this.numeroSADEDocumentoNumeroEspecial.getValue();
		String reparticionDocumento = (String) this.reparticionBusquedaDocumentoNumeroEspecial.getValue().trim();
		DocumentoDTO documentoEstandard = documentoManagerService.buscarDocumentoEspecial(actuacionSADETipoDoc,
				anioDocumento.toString(), numeroDocumento.toString(), reparticionDocumento);

		if (documentoEstandard != null) {

			if (documentoEstandard.getMotivoDepuracion() != null) {
				Messagebox.show(Labels.getLabel("ee.tramitacion.documentoDepurado"),
						Labels.getLabel("ee.tramitacion.informacion.documentoNoExiste"), Messagebox.OK,
						Messagebox.EXCLAMATION);
				return;
			}

			if (!this.ee.getDocumentos().contains(documentoEstandard)) {
				TipoDocumentoDTO tipoDeDocumento = tipoDocumentoService
						.obtenerTipoDocumento(documentoEstandard.getTipoDocAcronimo());

				if (!estaHabilitado(documentoEstandard.getTipoDocAcronimo())) {
					throw new WrongValueException(this.tiposDocumentoEspecialBandbox,
							"Tipo de Documento No habilitado para la trata. Verifique los datos ingresados.");
				}

				documentoEstandard.setFechaAsociacion(new Date());
				documentoEstandard.setUsuarioAsociador(loggedUsername);
				documentoEstandard.setIdTask(this.workingTask.getExecutionId());
				// nueva funcionalidad (cuando se desvincula una TC, agrega un
				// doc vinculado a todos los exp desvinculados)
				documentoEstandard.setIdExpCabeceraTC(this.ee.getId());

				this.ee.getDocumentos().add(documentoEstandard);
				enviarEventoAcordeon(Events.ON_RENDER, this.ee, false);

				if (ee.getTrata().getTipoReserva().getTipoReserva().equals(ConstantesWeb.RESERVA_TOTAL)
						&& !tipoDeDocumento.getEsConfidencial()) {
					Messagebox.show(
							Labels.getLabel("te.tramitacion.documento") + documentoEstandard.getNumeroSade() + Labels.getLabel("te.tramitacion.confidencia.numeroEspecial")
									+ actuacionSADETipoDoc + "-" + anioDocumento + "-" + numeroDocumento + "-"
									+ reparticionDocumento + " " + Labels.getLabel("ee.tramitacion.documentoAsociado")
									+ Labels.getLabel("te.tramitacion.confidencial.question"),
							Labels.getLabel("ee.tramitacion.titulo.asociado"), Messagebox.OK, Messagebox.INFORMATION);
				}
			} else {
				if (!workingTask.getActivityName().equals(TRAMITACION_EN_PARALELO)) {
					Messagebox.show(Labels.getLabel("ee.tramitacion.documentoNoAsociado"),
							Labels.getLabel("ee.tramitacion.informacion.documentoNoAsociado"), Messagebox.OK,
							Messagebox.EXCLAMATION);
				} else {
					Messagebox.show(Labels.getLabel("ee.tramitacion.documentoNoAsociadoParalelo"),
							Labels.getLabel("ee.tramitacion.informacion.documentoNoAsociado"), Messagebox.OK,
							Messagebox.EXCLAMATION);
				}
			}
		} else {
			Messagebox.show(Labels.getLabel("ee.tramitacion.documentoNoExiste"),
					Labels.getLabel("ee.tramitacion.informacion.documentoNoExiste"), Messagebox.OK,
					Messagebox.EXCLAMATION);
		}

		this.tiposDocumentoEspecialBandbox.setValue("");
		this.anioSADEDocumentoNumeroEspecial.setValue(null);
		this.numeroSADEDocumentoNumeroEspecial.setValue(null);
		this.reparticionBusquedaDocumentoNumeroEspecial.setValue("");

	}

	private void intercambiarVariablesAcordeon() {
		if (this.workingTask != null) {
			Executions.getCurrent().getDesktop().setAttribute("selectedTask", this.workingTask);
		}

		Executions.getCurrent().getDesktop().setAttribute("ventanaPadre", this.self);
		this.acordeon = (Component) Executions.getCurrent().getDesktop().getAttribute("acordeonWindow");
	}

	private void enviarEventoAcordeon(String evento, ExpedienteElectronicoDTO expedienteElectronico, Boolean inicial) {
		Map<String, Object> dataHm = new HashMap<>();
		dataHm.put("expedienteElectronico", expedienteElectronico);
		dataHm.put("inicial", inicial);
		Events.sendEvent(evento, this.acordeon, dataHm);

		actualizaDocsNotificablesSinNotificar(this.ee);
		dibujaBotonNotificarVuc();
		this.hacerVisibleLabelLeyenda();
	}

	private String extraerAcronimo(String value) {
		value = value.substring(value.indexOf("-") + 2);

		return value;
	}

	private void validarDatosBuscarDocumento() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String fechaActual = sdf.format(new Date());
		String anioFormateado = fechaActual.substring(6, 10);
		int anioActual = Integer.parseInt(anioFormateado);
		Integer anioValido = new Integer(anioActual);

		if ((this.tiposDocumentoBandbox.getValue() == null) || (this.tiposDocumentoBandbox.getValue().equals(""))) {
			throw new WrongValueException(this.tiposDocumentoBandbox,
					Labels.getLabel("ee.tramitacion.faltaTipoDocumento"));
		}

		if ((this.anioSADEDocumento.getValue() == null) || (this.anioSADEDocumento.getValue().equals(""))) {
			throw new WrongValueException(this.anioSADEDocumento, Labels.getLabel("ee.tramitacion.faltaAnio"));
		}

		if (this.anioSADEDocumento.getValue() > anioValido) {
			throw new WrongValueException(this.anioSADEDocumento, Labels.getLabel("ee.tramitacion.anioInvalido"));
		}

		if ((this.numeroSADEDocumento.getValue() == null) || (this.numeroSADEDocumento.getValue().equals(""))) {
			throw new WrongValueException(this.numeroSADEDocumento,
					Labels.getLabel("ee.tramitacion.faltaNumeroDeDocumento"));
		}

		if ((this.reparticionBusquedaDocumento.getValue() == null)
				|| (this.reparticionBusquedaDocumento.getValue().equals(""))) {
			throw new WrongValueException(this.reparticionBusquedaDocumento,
					Labels.getLabel("ee.tramitacion.faltaReparticion"));
		}

	}

	private void indiceTABVinvularDocumento() {
//		this.tiposDocumentoBandbox.setTabindex(1);
//		this.anioSADEDocumento.setTabindex(2);
//		this.numeroSADEDocumento.setTabindex(3);
//		this.reparticionBusquedaDocumento.setTabindex(4);
//		this.tiposDocumentoEspecialBandbox.setTabindex(5);
//		this.anioSADEDocumentoNumeroEspecial.setTabindex(6);
//		this.numeroSADEDocumentoNumeroEspecial.setTabindex(7);
//		this.reparticionBusquedaDocumentoNumeroEspecial.setTabindex(8);
	}

	public String buscarPathDocumentoViejo(DocumentoDTO documento) {

		String numeroSade = documento.getNumeroSade();
		String pathDocumento = "SADE";
		String pathGedo = BusinessFormatHelper.formarPathWebDavApacheSinEspacio(numeroSade);
		String nombreArchivo = documento.getNombreArchivo();

		return pathDocumento + "/" + pathGedo + "/" + nombreArchivo;

	}

	private void validarDatosBuscarDocumentoPorNumeroEspecial() {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String fechaActual = sdf.format(new Date());
		String anioFormateado = fechaActual.substring(6, 10);
		int anioActual = Integer.parseInt(anioFormateado);
		Integer anioValido = new Integer(anioActual);

		if ((this.tiposDocumentoEspecialBandbox.getValue() == null)
				|| (this.tiposDocumentoEspecialBandbox.getValue().equals(""))) {
			throw new WrongValueException(this.tiposDocumentoEspecialBandbox,
					Labels.getLabel("ee.tramitacion.faltaTipoDocumento"));
		}

		if ((this.anioSADEDocumentoNumeroEspecial.getValue() == null)
				|| (this.anioSADEDocumentoNumeroEspecial.getValue().equals(""))) {
			throw new WrongValueException(this.anioSADEDocumentoNumeroEspecial,
					Labels.getLabel("ee.tramitacion.faltaAnio"));
		}

		if (this.anioSADEDocumentoNumeroEspecial.getValue() > anioValido) {
			throw new WrongValueException(this.anioSADEDocumentoNumeroEspecial,
					Labels.getLabel("ee.tramitacion.anioInvalido"));
		}

		if ((this.numeroSADEDocumentoNumeroEspecial.getValue() == null)
				|| (this.numeroSADEDocumentoNumeroEspecial.getValue().equals(""))) {
			throw new WrongValueException(this.numeroSADEDocumentoNumeroEspecial,
					Labels.getLabel("ee.tramitacion.faltaNumeroDeDocumento"));
		}

		if ((this.reparticionBusquedaDocumentoNumeroEspecial.getValue() == null)
				|| (this.reparticionBusquedaDocumentoNumeroEspecial.getValue().equals(""))) {
			throw new WrongValueException(this.reparticionBusquedaDocumentoNumeroEspecial,
					Labels.getLabel("ee.tramitacion.faltaReparticion"));
		}
	}

	public void onDesasociarDocumentos() throws InterruptedException {
		this.ee.getDocumentos().remove(this.selectedDocumento);
		
		// Quita el documento del set del composer
		if (this.documentosNotificablesSinNotificar != null && !this.documentosNotificablesSinNotificar.isEmpty()) {
			this.documentosNotificablesSinNotificar.remove(this.selectedDocumento.getNumeroSade());
		}
		dibujaBotonNotificarVuc();

		setVariableWorkFlow("idExpedienteElectronico", ee.getId());
		
		expedienteElectronicoService.modificarExpedienteElectronico(this.ee);
		
		this.enviarEventoAcordeon(Events.ON_RENDER, this.ee, false);
	}

	public void onDetalle() throws InterruptedException {
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("documento", this.selectedDocumento);

		if (this.selectedDocumento.getArchivosDeTrabajo().isEmpty()) {
			Messagebox.show(Labels.getLabel("ee.tramitacion.error.archivoTrabajo"), Labels.getLabel("ee.tramitacion.listheader.fechivoTrabajo"), Messagebox.OK,
					Messagebox.EXCLAMATION);
		} else {
			this.documentoDetalleWindow = (Window) Executions.createComponents("/expediente/documentoDetalle.zul",
					this.self, hm);
			this.documentoDetalleWindow.setClosable(true);
			this.tramitacionWindow.invalidate();

			this.documentoDetalleWindow.doModal();
		}
	}

	/**
	 * TODO: persistir los datos del tramite (documento, expediente, etc.)
	 *
	 * Abro la ventana para hacer el envío correspondiente a otra persona,
	 * sector o repartición.
	 */
	public void onGuardarTramitacion() {

		ee.setFechaModificacion(new Date());
		ee.setUsuarioModificacion(loggedUsername);

		expedienteElectronicoService.modificarExpedienteElectronico(this.ee);

		// Si el expediente es cabecera de una tramitacion conjunta, debo
		// actualizar los expedientes hijos copiando los documentos, archivos de
		// trabajo
		// y expedientes asociados desde el cabecera a todos los expedientes de
		// la tramitacion conjunta.
		if ((this.ee.getEsCabeceraTC() != null) && this.ee.getEsCabeceraTC()) {
			this.tramitacionConjuntaService.actulizarDocumentosEnTramitacionConjunta(this.ee);
			this.tramitacionConjuntaService.actualizarArchivosDeTrabajoEnTramitacionConjunta(this.ee, null);
			this.tramitacionConjuntaService.actualizarExpedientesAsociadosEnTramitacionConjunta(this.ee);
		}
		eliminarArchivosDeTrabajo();

		setVariableWorkFlow("idExpedienteElectronico", this.ee.getId());
		/**
		 * Si se realiza sobre un expediente alguna acción, deja de ser una
		 * tarea que se puede enviar al buzón grupal
		 */
		/*
		 * BISADE-16592 EE - Reservado Paralelo - Permite Devolver al BG luego
		 * de salir sin pase
		 */
		if (TRAMITACION_EN_PARALELO.equals(this.workingTask.getActivityName())) {

			TareaParaleloDTO tareaParalelo = this.tareaParaleloService
					.buscarTareaEnParaleloByIdTask(this.workingTask.getExecutionId());

			if (tareaParalelo != null) {

				tareaParalelo.setTareaGrupal(false);
				this.tareaParaleloService.modificar(tareaParalelo);
			}
		}
		setVariableWorkFlow("tareaGrupal", "noEsTareaGrupal");
		Executions.getCurrent().getDesktop().removeAttribute("ventanaTramitacion");
		this.closeAssociatedWindow();
	}

	public void onNotificarExpedienteTad() throws InterruptedException {
		/**
		 * Si no tiene clave TAD se abre el popup Si tiene se abre directamente
		 * la nueva ventana
		 */
		if (this.ee.getTrata().getClaveTad() == null) {

			this.notificarExpedienteTad.setPopup("clavePopUp");
			this.clavePopUp.open(idgroupbox);

			this.binder.loadComponent(this.clavePopUp);

		} else {
			Executions.getCurrent().getDesktop().setAttribute(NotificableTadComposer.EXPEDIENTE, ee);
			this.notificarExpedienteTadWindow = (Window) Executions.createComponents("/expediente/notificableTad.zul",
					this.self, new HashMap<Object, Object>());
			this.notificarExpedienteTadWindow.setParent(this.tramitacionWindow);
			this.notificarExpedienteTadWindow.setPosition("center");
			this.notificarExpedienteTadWindow.setClosable(true);
			this.notificarExpedienteTadWindow.doModal();
		}
	}

	public void onClick$asignarCuil() {
//		validarCuil();
		Executions.getCurrent().getDesktop().setAttribute(NotificableTadComposer.EXPEDIENTE, ee);
		Executions.getCurrent().getDesktop().setAttribute(NotificableTadComposer.CLAVE_TAD, getCuilNot());
		this.notificarExpedienteTadWindow = (Window) Executions.createComponents("/expediente/notificableTad.zul",
				this.self, new HashMap<Object, Object>());
		this.notificarExpedienteTadWindow.setParent(this.tramitacionWindow);
		this.notificarExpedienteTadWindow.setPosition("center");
		this.notificarExpedienteTadWindow.setClosable(true);
		this.notificarExpedienteTadWindow.doModal();

	}

//	private void validarCuil() {
//
//		if (this.cuilNotTipo.getValue() == null) {
//			throw new WrongValueException(this.cuilNotTipo, Labels.getLabel("ee.nuevasolicitud.lenghtcuitCuilTipo"));
//		}
//		if (this.cuilNotDocumento.getValue() == null) {
//			throw new WrongValueException(this.cuilNotDocumento, Labels.getLabel("ee.nuevasolicitud.nocuitincorrecto"));
//		}
//
//		if (this.cuilNotVerificador.getValue() == null) {
//			throw new WrongValueException(this.cuilNotVerificador,
//					Labels.getLabel("ee.nuevasolicitud.lenghtcuitVerificador"));
//		}
//
//		if (this.cuilNotTipo.getValue() != null && this.cuilNotTipo.getValue().toString().length() != 2) {
//			throw new WrongValueException(this.cuilNotTipo, Labels.getLabel("ee.nuevasolicitud.lenghtcuitCuilTipo"));
//		}
//		String cadena = this.cuilNotDocumento.getValue().toString();
//		if (this.cuilNotDocumento.getValue() != null && this.cuilNotDocumento.getValue().toString().length() != 8) {
//			while (cadena.length() != 8) {
//				cadena = "0" + cadena;
//			}
//			this.cuilNotDocumento.setValue(new Long(cadena));
//		}
//
//		if (this.cuilNotVerificador.getValue() != null && this.cuilNotVerificador.getValue().toString().length() != 1) {
//			throw new WrongValueException(this.cuilNotVerificador,
//					Labels.getLabel("ee.nuevasolicitud.lenghtcuitVerificador"));
//		}
//		this.setCuilNot(
//				this.cuilNotTipo.getValue().toString() + cadena + this.cuilNotVerificador.getValue().toString());
//		ValidadorDeCuit validadorDeCuit = new ValidadorDeCuit();
//		try {
//			validadorDeCuit.validarNumeroDeCuit(getCuilNot());
//		} catch (NegocioException e) {
//			throw new WrongValueException(this.cuil, e.getMessage());
//		}
//	}

	public void onEnviarTramitacion() {
		// --- validacion previa a la creacion de la ventana de
		// enviarTramitacion ---
		try {
			if (!getTramitacionHelper().getActiveState().validateBeforePass()) {
				return;
			}
			HashMap<String, Object> hma = new HashMap<>();
			hma.put("expedienteElectronico", this.ee);

			if (!this.workingTask.getActivityName().equals(TRAMITACION_EN_PARALELO)) {
				hma.put("estadoAnterior", this.estado.getValue());
				Executions.getCurrent().getDesktop().setAttribute("selectedTask", getWorkingTask());
				this.envio = (Window) Executions.createComponents("/pase/envio.zul", null, hma);
			} else {
				/**
				 * acá no seteo la variable de estado anterior. Porque el estado
				 * anterior debe ser el estado de la tarea antes de paralelizarse y
				 * no el estado "paralelo".
				 */
				this.envio = (Window) Executions.createComponents("/pase/integrarTareaParalela.zul", null, hma);
			}

			this.envio.setParent(this.tramitacionWindow);
			this.envio.setPosition("center");
			this.envio.setClosable(true);

			Component cancelarBtn = (Component) this.envio.getFellow("cancelar");
			EventListener<Event> cancelOrExit = new EventListener<Event>() {
				@Override
				public void onEvent(Event paramEvent) throws Exception {

					getTramitacionHelper().getActiveState().stopReject();
				}
			};

			EventListener<Event> onUser = new EventListener<Event>() {
				@Override
				public void onEvent(Event event) throws Exception {
					if (event.getData().equals("addDocumentoPase")) {
						getTramitacionHelper().getActiveState().stopReject();
					}
				}
			};

			getTramitacionHelper().getActiveState().customizePase(envio);

			this.envio.addEventListener(Events.ON_USER, onUser);
			cancelarBtn.addEventListener(Events.ON_CLICK, cancelOrExit);
			this.envio.addEventListener(Events.ON_CLOSE, cancelOrExit);
			this.envio.doModal();
		} catch (InterruptedException e) {
			logger.error("", e);
			return;
		}
	}

	public void onEnviarTramitacionParalelo() {
		if (!this.estado.getValue().equals(TRAMITACION_EN_PARALELO)) {
			setVariableWorkFlow("estadoAnterior", this.estado.getValue());
		}

		setVariableWorkFlow("idExpedienteElectronico", this.ee.getId());
		HashMap<String, Object> params = new HashMap<>();
		params.put("expedienteElectronico", this.ee);
		this.envio = (Window) Executions.createComponents("/pase/envioParalelo.zul", null, params);
		this.envio.setParent(this.tramitacionWindow);
		this.envio.setPosition("center");
		this.envio.setClosable(true);

		EnvioParaleloComposer envioParaleloComposer = (EnvioParaleloComposer) envio
				.getAttribute("envioWindow$EnvioParaleloComposer");
		envioParaleloComposer.setMaxLimits(getTramitacionHelper().getActiveState().getParallelMaxUsers(),
				getTramitacionHelper().getActiveState().getParallelMaxSector());
		envioParaleloComposer.setMinLimits(getTramitacionHelper().getActiveState().getParallelMinUsers(),
				getTramitacionHelper().getActiveState().getParallelMinSector());
		envioParaleloComposer.setExclusiveMode(getTramitacionHelper().getActiveState().isParallelExclusive());

		this.envio.doModal();
	}

	@SuppressWarnings("unchecked")
	public void doAfterCompose(Component component) throws Exception {
		super.doAfterCompose(component);
		
		Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
		this.binder = new AnnotateDataBinder(component);
		
		// Para que no de nulpointer, por el cambio de numero documento
//		cuitCuilTipo = new Longbox();
//		cuitCuilDocumento = new Longbox();
//		cuitCuilVerificador = new Longbox();
//		noDeclaraNoPosee = new Checkbox();
		
		if (component.getParent() != null && "inboxWindow".equals(component.getParent().getId())) {
			this.inboxWindow = (Window) component.getParent();
		}
		
		this.datosUsuario = this.usuariosSADEService.obtenerUsuarioActual();

		cargarListener(component);
		
//		familiaEstructuraTree.addEventListener(Events.ON_NOTIFY,
//				new TramitacionOnNotifyWindowListener(this));
		
		indiceTABVinvularDocumento();
		
		loggedUsername = Executions.getCurrent().getSession().getAttribute(ConstantesWeb.SESSION_USERNAME).toString();
		reparticionUsuario = (String) Executions.getCurrent().getDesktop().getSession()
				.getAttribute(ConstantesWeb.SESSION_USER_REPARTICION);
		
		this.ee = null;
		Executions.getCurrent().getDesktop().setAttribute("ventanaTramitacion", this.self);
		Executions.getCurrent().getDesktop().setAttribute("expedienteElectronico", this.ee);

		reparticionActuacion.setValue(constantesDB.getNombreReparticionActuacion());

		buzonAct.addEventListener("onSelect", myTabOnSelect);

		// **********************************************************************************
		// ** JIRA: https://quark.everis.com/jira/browse/BISADE-4352
		// ** Se actualiza el workflow con idexpediente consultado antes de
		// ejecutar su trámitacion
		// **********************************************************************************

		Task task = (Task) Executions.getCurrent().getDesktop().getAttribute("selectedTask");
		
		if (task != null) {
			Executions.getCurrent().getDesktop().setAttribute("selectedTask", null);
			this.setWorkingTask(task);
		} else {
			this.setWorkingTask((Task) component.getDesktop().getAttribute("selectedTask"));
		}
		
		loadEE();

		String tipoExpediente;
		boolean esExpedienteInterno = this.ee.getSolicitudIniciadora().isEsSolicitudInterna();

		if (esExpedienteInterno) {
			tipoExpediente = "expedienteInterno";
		} else {
			tipoExpediente = "expedienteExterno";
		}

		String descripcion = this.ee.getDescripcion();
		String motivo;

		try {
			motivo = (String) getVariableWorkFlow(ConstantesWeb.MOTIVO);
			if (motivo == null) {
				motivo = " ";
			}
		} catch (VariableWorkFlowNoExisteException e) {
			motivo = " ";
		}

		String estado = this.workingTask.getActivityName();

		if (estado.equals(TRAMITACION_EN_PARALELO)) {
			/**
			 * Para este caso, si la terea es paralela, el motivo debe ser el
			 * motivo principal de pase y no el motivo propio de cada
			 * destinatario.
			 */

			try {
				motivo = (String) getVariableWorkFlow(ConstantesWeb.PASE_DE_PASE);
				if (motivo == null) {
					motivo = " ";
				}
			} catch (VariableWorkFlowNoExisteException e) {
				motivo = " ";
			}

		}

		Execution exec = Executions.getCurrent();
		Map<?, ?> map = exec.getArg();
		TrataDTO trata = (TrataDTO) map.get("Trata");
		this.setTrata(trata);
		SolicitanteDTO solicitante = this.ee.getSolicitudIniciadora().getSolicitante();
		String codigoTrata = ee.getTrata().getCodigoTrata();
		String descripcionTrata = this.trataService.obtenerDescripcionTrataByCodigo(trata.getCodigoTrata());

		if (this.ee.getEsReservado()) {
			this.tramitacionWindow.setTitle(estado + ES_RESERVADO);

			if (this.ee.getTrata().getTipoReserva().getTipoReserva().equals(ConstantesWeb.RESERVA_TOTAL)
					|| this.ee.getTrata().getTipoReserva().getTipoReserva().equals(ConstantesWeb.RESERVA_PARCIAL)) {
				if (!puedeVerSolapaCaratula()) {
					this.datosdelacaratula.setDisabled(true);
				}
			}
			vinculacionDefinitiva.setDisabled(false);
			vinculacionDefinitiva.setVisible(true);
		} else {
			this.tramitacionWindow.setTitle(estado);
			vinculacionDefinitiva.setDisabled(true);
			vinculacionDefinitiva.setVisible(false);
		}

		notificarExpedienteTad.setVisible(false);

		this.motivoExpediente.setValue(motivo);
		this.motivoExpediente.setReadonly(true);
//		this.anioSADEDocumento.setFocus(true);
		this.tipoDocumento.setDisabled(true);

		if (this.expedienteExterno.getId().equals(tipoExpediente)) {
			this.expedienteExterno.setChecked(true);
			this.expedienteExterno.setDisabled(true);
			this.expedienteInterno.setChecked(false);
			this.expedienteInterno.setDisabled(true);

			String tipoDocumento = null;
			String numeroDocumento = null;
			
			if (solicitante.getDocumento() != null) {
				numeroDocumento = solicitante.getDocumento().getNumeroDocumento();
			}
			
			/*
			if (solicitante.getDocumento().getTipoDocumento() != null) {
				if (solicitante.getDocumento().getTipoDocumento().equals(ConstantesWeb.CU_VALOR)) {
					tipoDocumento = ConstantesWeb.CU_VALOR + "-" + ConstantesWeb.CU_DESCRIPCION;
				} else {
					TipoDocumentoPosible tipoDocumentoPosible = TipoDocumentoPosible
							.fromValue(solicitante.getDocumento().getTipoDocumento());

					if (tipoDocumentoPosible != null) {
						tipoDocumento = tipoDocumentoPosible.getDescripcionCombo();
					}
				}
				numeroDocumento = Long.parseLong(solicitante.getDocumento().getNumeroDocumento());
			}
			*/

			if (this.tiposDocBox == null) {
				this.tiposDocBox = TipoDocumentoUtils.getTiposDocumentos();
			}

			this.tipoDocumento.setValue(tipoDocumento);
			this.tipoDocumento.setReadonly(true);
			this.numeroDocumento.setValue(numeroDocumento);
			this.numeroDocumento.setReadonly(true);
			this.razonSocial.setValue(solicitante.getRazonSocialSolicitante());
			this.nombre.setValue(solicitante.getNombreSolicitante());
			this.apellido.setValue(solicitante.getApellidoSolicitante());

			this.razonSocial.setReadonly(true);
			this.apellido.setReadonly(true);
			this.nombre.setReadonly(true);

			this.segundoNombre.setReadonly(true);
			this.segundoNombre.setValue(solicitante.getSegundoNombreSolicitante());

			this.tercerNombre.setReadonly(true);
			this.tercerNombre.setValue(solicitante.getTercerNombreSolicitante());

			this.segundoApellido.setReadonly(true);
			this.segundoApellido.setValue(solicitante.getSegundoApellidoSolicitante());

			this.tercerApellido.setReadonly(true);
			this.tercerApellido.setValue(solicitante.getTercerApellidoSolicitante());

//			String cuitcuil = solicitante.getCuitCuil();
			
//			if (cuitcuil != null) {
//				String tipoCuitCuil = cuitcuil.substring(0, 2);
//				String documentoCuitCuil = cuitcuil.substring(2, 10);
//				String verificadorCuitCuil = cuitcuil.substring(10, 11);
//				this.cuitCuilTipo.setValue(new Long(tipoCuitCuil));
//				this.cuitCuilDocumento.setValue(new Long(documentoCuitCuil));
//				this.cuitCuilVerificador.setValue(new Long(verificadorCuitCuil));
//			} else {
//				this.cuitCuilTipo.setValue(null);
//				this.cuitCuilDocumento.setValue(null);
//				this.cuitCuilVerificador.setValue(null);
//				this.noDeclaraNoPosee.setChecked(true);
//			}
//
//			this.cuitCuilTipo.setReadonly(true);
//			this.cuitCuilDocumento.setReadonly(true);
//			this.cuitCuilVerificador.setReadonly(true);

			this.direccion.setReadonly(true);
			this.direccion.setValue(solicitante.getDomicilio());

			this.departamento.setReadonly(true);
			this.departamento.setValue(solicitante.getDepartamento());

			this.piso.setReadonly(true);
			this.piso.setValue(solicitante.getPiso());

			this.codigoPostal.setReadonly(true);
			this.codigoPostal.setValue(solicitante.getCodigoPostal());

		} else {
			this.expedienteExterno.setChecked(false);
			this.expedienteInterno.setChecked(true);
			this.expedienteExterno.setDisabled(true);
			this.expedienteInterno.setDisabled(true);
			this.tipoDocumento.setDisabled(true);
			this.numeroDocumento.setDisabled(true);
			this.razonSocial.setDisabled(true);
			this.apellido.setReadonly(true);
			this.nombre.setReadonly(true);

			this.segundoNombre.setReadonly(true);
			this.tercerNombre.setReadonly(true);
			this.segundoApellido.setReadonly(true);
			this.tercerApellido.setReadonly(true);

//			this.cuitCuilTipo.setReadonly(true);
//			this.cuitCuilDocumento.setReadonly(true);
//			this.cuitCuilVerificador.setReadonly(true);
			this.direccion.setReadonly(true);
			this.departamento.setReadonly(true);
			this.piso.setReadonly(true);
			this.codigoPostal.setReadonly(true);
		}

//		this.noDeclaraNoPosee.setDisabled(true);
		this.descripcion.setValue(descripcion);
		this.descripcion.setReadonly(true);
		this.codigoTrata.setValue(codigoTrata + "-" + descripcionTrata);
		this.codigoTrata.setReadonly(true);
		this.codigoTrata.setDisabled(true);
		this.estado.setValue(estado);
		this.estado.setReadonly(true);
		this.email.setValue(solicitante.getEmail());
		this.email.setReadonly(true);
		this.telefono.setValue(solicitante.getTelefono());
		this.telefono.setReadonly(true);

		if (getWorkingTask().getActivityName().equals(ESTADO_TRAMITACION)
				|| getWorkingTask().getActivityName().equals(ESTADO_EJECUCION)) {
			// false
			// ************************************************************************
			// **
			// ** Feature: Habilitacion de funcionalidad tratamiento en
			// paralelo/tratamiento en conjunto.
			// ** Se modifica la pantalla de visualización de expediente para
			// que:
			// ** Sumadas a las validaciones actuales, para habilitar el botón
			// "pase múltiple", se controle que esta operación se encuentre
			// habilitada para la trata del expediente
			// ** Sumadas a las validaciones actuales, para habilitar la solapa
			// "tramitación Conjunta", se controle que esta operación se
			// encuentre habilitada para la trata del expediente
			// ************************************************************************

			TramitacionTabsConditional tramitacionTabsConditional = new TramitacionTabsConditional(this.workingTask);

			this.expedienteFusion.setDisabled(!tramitacionTabsConditional.condition(this.ee).getIsHabilitarTabFusion());
			this.expedienteTramitacionConjunta
					.setDisabled(!tramitacionTabsConditional.condition(this.ee).getIsHabilitarTabTC());

		} else {
			tramitacionParalelo.setDisabled(true);
			expedienteTramitacionConjunta.setDisabled(true);
			expedienteFusion.setDisabled(true);
		}

		if ((this.ee.getEsCabeceraTC() != null) && this.ee.getEsCabeceraTC()) {
			tramitacionParalelo.setDisabled(true);
			reservar.setDisabled(true);
			reservar.setVisible(false);
		}

		if (this.ee.getEsReservado()) {
			vinculacionDefinitiva.setDisabled(false);
			vinculacionDefinitiva.setVisible(true);
		} else {
			vinculacionDefinitiva.setDisabled(true);
			vinculacionDefinitiva.setVisible(false);
		}

		listaArhivosDeTrabajo = ee.getArchivosDeTrabajo();
		this.titulo.setValue(this.ee.getCodigoCaratula());

		// 13-06-2017: Result
		if (ee.getPropertyResultado() != null && ee.getPropertyResultado().getValor() != null) {
			hboxResult.setVisible(true);
			lblResult.setValue(ee.getPropertyResultado().getValor());
		}

		logger.debug(".toString() " + this.ee.toString());
		
		this.binder.loadAll();
		// Modificar si el usuario pertenece a la reparticion y sector
		// permitirle modificarlos
		// nuevo
		// fin nuevo
		// hm.put(DatosPropiosTrataCaratulaComposer.ES_SOLO_LECTURA, true);
		// Para poder modificar un expediente se debe tener permisos para
		// modificar y la antiguedad del expediente debe ser menor o igual a 15
		// dias.

		Executions.getCurrent().getDesktop().setAttribute("expedienteElectronico", this.ee);
		Executions.getCurrent().getDesktop().setAttribute("tramitacionParaleloButton", this.tramitacionParalelo);
		Executions.getCurrent().getDesktop().setAttribute("reservarButton", this.reservar);
		Executions.getCurrent().getDesktop().setAttribute("quitarReservaButton", this.quitarReserva);
		Executions.getCurrent().getDesktop().setAttribute("tramitacionWindow", this.tramitacionWindow);
		Executions.getCurrent().getDesktop().setAttribute("buscarFormularioWin", this.buscarFormularioWin);
		Executions.getCurrent().getDesktop().setAttribute("expedienteTramitacionConjuntaTab",
				this.expedienteTramitacionConjunta);
		Executions.getCurrent().getDesktop().setAttribute("expedienteFusionTab", this.expedienteFusion);
		// Lo necesita el Historial de Pases
		Executions.getCurrent().getDesktop().setAttribute("idExpedienteElectronico", ee.getId());
		// se comenta la llamada a este metodo porque generba conflicto con la
		// solapa tramitacion conjunta

		habilitarBotonesGeneral();

		this.intercambiarVariablesAcordeon();

		getTramitacionHelper().reinitialize(this.loggedUsername, this.ee);
		pm.getContext().put("tramitacionHelper", getTramitacionHelper());
		pm.getContext().put("workflowService", getWorkflowService());
		getTramitacionHelper().getStates().clear();
		getTramitacionHelper().getStates().addAll(ReflectionUtil.searchClasses(IVisualState.class));

		final String workflowname = task.getExecutionId().substring(0, task.getExecutionId().indexOf("."));
		final String stateName = this.getWorkingTask().getName();

		List<Object> instances = pm.getInstancesOf(new Predicate<Class<?>>() {
			@Override
			public boolean evaluate(Class<?> clazz) { // CLASS EVALUATOR
				return ReflectionUtil.hasInterface(clazz, IVisualState.class);
			}
		}, new Predicate<Object>() { // OBJECT EVALUATOR
			@Override
			public boolean evaluate(Object object) {
				return ((object != null) && ((IVisualState) object).is(workflowname, stateName));
			}
		});
		
		for (Object obj : instances) {
			getTramitacionHelper().getStates().add((IVisualState) obj);
		}
		
		getTramitacionHelper().setActiveState(this.getWorkingTask());

		this.enviarEventoAcordeon(Events.ON_RENDER, this.ee, true);

		// Activa botón Notificación VUC
		// si hay documentos notificables y no han sido notificados
		if (ee.getSistemaCreador().equals(SISTEMA_VUC)) {
			comunicarTadButton.setDisabled(false);
			subsanar.setDisabled(false);
		} else {
			getTramitacionHelper().getActiveState().disableButton(comunicarTadButton);
			getTramitacionHelper().getActiveState().disableButton(subsanar);
		}

		if (!loggedUsername.equals(this.datosUsuario.getUsername())) {

			logger.error("Usuarios distintos -> Usuario obtenido de session: " + loggedUsername + "\n"
					+ "usuario obtenido de contexto de Security: " + this.datosUsuario.getUsername()
					+ "Para la ejecucion del expediente: " + this.ee.getCodigoCaratula());
			datosUsuario = usuariosSADEService.getDatosUsuario(loggedUsername);
		}

		getTramitacionHelper().getActiveState().setUserLogged(this.datosUsuario);
		getTramitacionHelper().getActiveState().setTramitacionHelper(getTramitacionHelper());
		getTramitacionHelper().getActiveState().setExpedienteElectronico(ee);
		getTramitacionHelper().getActiveState().drawInclude(this.tramitacionWindow.getFellow("incStatesNombramiento"));

		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("inicio", "Caratular");
		variables.put("tareaGrupal", "noEsTareaGrupal");
		variables.put("estado", "Iniciacion");

		validarDatosPropiosDeTrata();

		verifyRejectBtn();
		hacerVisibleLabelLeyenda();

		loadComboActuaciones();

		habilitarBotonSistemaExterno();

		Boolean isTemp = (Boolean) workflowService.getProcessEngine().execute(new Command<Object>() {
			private static final long serialVersionUID = 1L;

			@Override
			public Object execute(final Environment env) {
				final ExecutionImpl exe = (ExecutionImpl) workflowService.getProcessEngine().getExecutionService()
						.findExecutionById(ee.getIdWorkflow());
				if (null != exe) {
					ActivityImpl act = exe.getActivity();
					if (act != null && CollectionUtils.isNotEmpty(act.getVariableDefinitions())) {
						return true;
					}
				}
				return false;
			}
		});

		if (isTemp) {
			List<Component> children = tramitacionWindow.getChildren();
			if (children != null) {
				FiltroEE.blockedChildren(children, true);
			}
			Messagebox.show(Labels.getLabel("te.tramitacion.eeBloqueado"), Labels.getLabel("ee.general.advertencia"), Messagebox.OK,
					Messagebox.EXCLAMATION);
		}
		
		tramitacionWindow.addEventListener(Events.ON_CLOSE, new EventListener<Event>() {

			@Override
			public void onEvent(Event event) throws Exception {
				if (Executions.getCurrent().getSession().getAttribute("goBackDb") != null) {
					String hostEDT = getdBProperty().getString("host.edt") != null
							? getdBProperty().getString("host.edt") : null;
					
					if (hostEDT != null) {
						Executions.getCurrent().getSession().removeAttribute("goBackDb");
						Executions.sendRedirect(hostEDT);
					}
				}
			}
		});
		
		// Precarga direccion
		SolicitanteDireccionVM.precargaDireccion(this.ee.getSolicitudIniciadora().getSolicitante().getId());
		
		// Habilita readOnly direccion
		SolicitanteDireccionVM.setReadOnlyDireccion(true);
	
	}

	@SuppressWarnings("unchecked")
	private void cargarListener(Component component) {
		component.addEventListener(Events.ON_NOTIFY, new TramitacionOnNotifyWindowListener(this));
		component.addEventListener(Events.ON_USER, new TramitacionOnNotifyWindowListener(this));
		component.addEventListener(Events.ON_CHANGE, new TramitacionOnNotifyWindowListener(this));
		component.addEventListener(Events.ON_CLIENT_INFO, new TramitacionOnNotifyWindowListener(this));
		component.addEventListener(VincularDocumentoComposer.ON_MENSAJE_ACORDEON, 
				new TramitacionOnNotifyWindowListener(this));
		component.addEventListener(VincularDocumentoComposer.ON_ADD_DOC_NOTIFICABLE,
				new TramitacionOnNotifyWindowListener(this));
	}

	private void habilitarBotonSistemaExterno() {
		TrataDTO t = ee.getTrata();
		if (!t.getIntegracionSisExt() && !t.getIntegracionAFJG()) {
			return;
		}

		if (workingTask != null) {
			String wk = workingTask.getExecutionId().split("\\.")[0];
			if (!wk.equalsIgnoreCase("solicitud")) {
				return;
			}
		}
		if (ee.getSistemaCreador().equalsIgnoreCase(ConstantesWeb.SISTEMA_BAC)) {
			return;
		}
		params = ConfiguracionInicialModuloEEFactory.obtenerParametrosPorTrata(t.getId());
		if (params != null) {
			for (TrataIntegracionReparticionDTO trata : params.getReparticionesIntegracion()) {
				if ((trata.getCodigoReparticion().equalsIgnoreCase("--TODAS--")
						|| trata.getCodigoReparticion().equalsIgnoreCase(datosUsuario.getCodigoReparticion()))
						&& trata.isHabilitada()) {
					if (!ee.getTrata().getIntegracionAFJG()) {

						sistemaExternobtn.setLabel("Ir a " + params.getCodigo().toUpperCase());
						sistemaExternobtn.setVisible(false);

					} else if (estadoHabilitadoAFJG() && tienePermisoAFJG()
							&& ee.getSistemaCreador().equals(ConstantesWeb.SISTEMA_EE)) {

						sistemaExternobtn.setLabel("Enviar a AFJG");
						sistemaExternobtn.setVisible(false);
					}
				}
			}
		}

	}

	public boolean tienePermisoAFJG() {
		for (GrantedAuthority auth : datosUsuario.getAuthorities()) {

			if (ConstantesWeb.PERMISO_SIGA_MESA.equals(auth.getAuthority())) {
				return true;
			}
		}
		return false;
	}

	private boolean estadoHabilitadoAFJG() {

		return (!this.ee.getEstado().equals(ConstantesWeb.ESTADO_COMUNICACION)
				&& !this.ee.getEstado().equals(ESTADO_EJECUCION));

	}

	public void onClick$sistemaExternobtn() throws InterruptedException {
		try {
			validacionesParaIrASistemaExterno();
		} catch (Exception e) {
			Messagebox.show(e.getMessage(), Labels.getLabel("ee.general.advertencia"), Messagebox.OK, Messagebox.EXCLAMATION);
			return;
		}
		if (ee.getTrata().getIntegracionAFJG()) {
			eventoAFJG();
		} else {
			eventoEnvioASistemaExterno();
		}
	}

	public void eventoEnvioASistemaExterno() throws InterruptedException {
		String mensaje = Labels.getLabel("ee.inbox.enviarSistemaExterno", new String[] { ee.getCodigoCaratula() });
		String titulo = Labels.getLabel("ee.inbox.enviarSistemaExterno.tituloPopUp");

		Messagebox.show(mensaje, titulo, Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
				new org.zkoss.zk.ui.event.EventListener() {
					public void onEvent(Event evt) throws InterruptedException {
						switch (((Integer) evt.getData()).intValue()) {
						case Messagebox.YES:
							Clients.showBusy("Procesando...");
							Events.echoEvent(Events.ON_USER, (Component) tramitacionWindow, "envioSistemaExterno");
							break;
						case Messagebox.NO:
							break;
						}
					}
				});
	}

	public void enviarASistemaExterno() throws InterruptedException {
		String usuarioActual = null;

		try {

			Task task = this.getWorkingTask();
			usuarioActual = task.getAssignee();
			params.setParametros("?usr=" + datosUsuario.getUsername() + "&exp=" + ee.getCodigoCaratula());

			URL url = new URL(params.getURLFull());
			URLConnection con = url.openConnection();
			con.connect();

			if (usuarioActual != null) {
				ee.setBloqueado(true);
				ee.setSistemaApoderado(params.getCodigo().toUpperCase());
				expedienteElectronicoService.modificarExpedienteElectronico(ee);
				processEngine.getTaskService().assignTask(workingTask.getId(), usuarioActual + ".bloqueado");
			}

			Executions.getCurrent().sendRedirect(params.getURLFull(), "_blank");
			Messagebox.show(Labels.getLabel("te.base.composer.enviocomposer.generic.elexpediente") + ee.getCodigoCaratula() + Labels.getLabel("te.tramitacion.enviado")
					+ params.getCodigo().toUpperCase() + Labels.getLabel("te.tramitacion.exito"), Labels.getLabel("ee.general.information"), Messagebox.OK,
					Messagebox.INFORMATION);

			this.closeAssociatedWindow();
		} catch (IOException e) {

			if (usuarioActual != null) {
				ee.setBloqueado(false);
				ee.setSistemaApoderado(ConstantesWeb.SISTEMA_EE);
				expedienteElectronicoService.modificarExpedienteElectronico(ee);
				processEngine.getTaskService().assignTask(workingTask.getId(), usuarioActual);
			}

			Messagebox.show(Labels.getLabel("te.tramitacion.errorComunicarse") + params.getCodigo().toUpperCase(), "Error",
					Messagebox.OK, Messagebox.ERROR);
		} catch (Exception e) {

			if (usuarioActual != null) {
				ee.setBloqueado(false);
				ee.setSistemaApoderado(ConstantesWeb.SISTEMA_EE);
				expedienteElectronicoService.modificarExpedienteElectronico(ee);
				processEngine.getTaskService().assignTask(workingTask.getId(), usuarioActual);
			}

			Messagebox.show(Labels.getLabel("te.tramitacion.errorComunicarse") + params.getCodigo().toUpperCase(), "Error",
					Messagebox.OK, Messagebox.ERROR);
		} finally {
			Clients.clearBusy();
		}
	}

	private void eventoAFJG() throws InterruptedException {
		String mensaje = Labels.getLabel("ee.inbox.enviarSistemaAFJG", new String[] { ee.getCodigoCaratula() });
		String titulo = Labels.getLabel("ee.inbox.enviarSistemaExterno.tituloPopUp");

		Messagebox.show(mensaje, titulo, Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
				new org.zkoss.zk.ui.event.EventListener() {
					public void onEvent(Event evt) throws InterruptedException {
						switch (((Integer) evt.getData()).intValue()) {
						case Messagebox.YES:
							Clients.showBusy("Procesando...");
							Events.echoEvent(Events.ON_USER, (Component) tramitacionWindow, "envioExpedienteAFJG");
							break;
						case Messagebox.NO:
							break;
						}
					}
				});
	}

	public void envioExpedienteAFJG() throws InterruptedException {

	}

	private void validacionesParaIrASistemaExterno() throws InterruptedException {
		for (ExpedienteAsociadoEntDTO ec : ee.getListaExpedientesAsociados()) {
			if (ec.getEsExpedienteAsociadoTC() != null && ec.getEsExpedienteAsociadoTC()) {
				throw new InterruptedException(
						"Es necesario deshacer la Tramitacion Conjunta previo a ir a un sistema externo.");
			} else if (ec.getEsExpedienteAsociadoFusion() != null && ec.getEsExpedienteAsociadoFusion()
					&& !ec.getDefinitivo()) {
				throw new InterruptedException(
						"El expediente se encuentra en proceso de fusión. Debe confirmar o desvincular esta fusión antes de enviar el expediente al sistema externo.");
			}
		}

		if (expedientesAsociadosFiltrados != null && !expedientesAsociadosFiltrados.isEmpty()) {
			for (ExpedienteAsociadoEntDTO ea : expedientesAsociadosFiltrados) {
				if (ea.getEsExpedienteAsociadoTC() == null && ea.getEsExpedienteAsociadoFusion() == null
						&& !ea.getDefinitivo()) {
					throw new InterruptedException(
							"Es necesario finalizar el proceso de asociación previo a ir a un sistema externo.");
				}
			}
		}

		List<String> workIds = new ArrayList<>();
		workIds.add(ee.getIdWorkflow());
		List<ActividadInbox> actividades = actividadExpedienteService.buscarActividadesVigentes(workIds);

		for (ActividadInbox act : actividades) {
			if (act.getEstado().equals(ConstantesWeb.ESTADO_ABIERTA)
					|| act.getEstado().equals(ConstantesWeb.ESTADO_PENDIENTE)) {
				throw new InterruptedException(
						"Es necesario finalizar las actividades previo a ir a un sistema externo.");
			}
		}

		if (workingTask.getActivityName().equals(TRAMITACION_EN_PARALELO)) {
			throw new InterruptedException(
					"Es necesario finalizar la Tramitacion Paralela previo a ir a un sistema externo.");
		}

		if (ee.getTrata().getIntegracionSisExt()) {
			List<DocumentoDTO> documentos = ee.getDocumentos();
			List<ArchivoDeTrabajoDTO> archivoTrabajo = ee.getArchivosDeTrabajo();
			if (documentos != null) {
				for (DocumentoDTO doc : documentos) {
					if (!doc.getDefinitivo()) {
						throw new InterruptedException(
								"Es necesario hacer definitivos todos los documentos previo a ir a un sistema externo.");
					}
				}
			}

			if (archivoTrabajo != null) {
				for (ArchivoDeTrabajoDTO at : archivoTrabajo) {
					if (!at.isDefinitivo()) {
						throw new InterruptedException(
								"Es necesario hacer definitivos todos los archivos de trabajo previo a ir a un sistema externo.");
					}
				}
			}
		}

		for (TrataIntegracionReparticionDTO t : params.getReparticionesIntegracion()) {
			if ((t.getCodigoReparticion().equalsIgnoreCase("--TODAS--")
					|| t.getCodigoReparticion().equalsIgnoreCase(datosUsuario.getCodigoReparticion()))
					&& t.isHabilitada()) {
				return;
			}
		}

		throw new InterruptedException("Su repartición (" + datosUsuario.getCodigoReparticion()
				+ "). No está habilitada para ir a " + params.getCodigo());
	}

	private void hacerVisibleLabelLeyenda() {
		boolean tieneActividades = tieneActividadPendienteDeSubsanacion();
		leyendaLabel.setVisible(this.ee.getSistemaCreador().equals(SISTEMA_VUC) && tieneActividades);
		if (tieneActividades) {
			FiltroEE.blockedChildren(tramitacionWindow.getChildren(), true);
			cancelar.setDisabled(false);
		}
	}
	
	public void onUpload$inciarDocumentoButtond(UploadEvent event) throws IOException {
		if (this.getSelectedTipoDocumento() == null ) {
			throw new WrongValueException(this.familiaEstructuraTree,
					Labels.getLabel("ee.general.tipoDocumentoInvalido"));
		}
		if(!this.getSelectedTipoDocumento().getTipoProduccion().equals(ConstantesWeb.TIPO_PRODUCCION_IMPORTADO)) {
			throw new WrongValueException(this.familiaEstructuraTree,"Solo se puede subir Documentos de tipo IMPORTADO");
		}
		
		int limitBytes = 5242880; // 5 Mb
		int fileSizeBytes = event.getMedia().getStreamData().available();
		if (event.getMedias() != null && fileSizeBytes <= limitBytes) {
			Media mediaUpld = event.getMedia();
			if ("pdf".equalsIgnoreCase(mediaUpld.getFormat())) {
				Messagebox.show(Labels.getLabel("te.inicioDocumento.question") + mediaUpld.getName() + Labels.getLabel("te.inicioDocumento.questions"),
						"Confirmación", Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
						(EventListener<Event>) evt -> {
							if (((Integer) evt.getData()).equals(Messagebox.YES)) {
								Usuario usuario = this.usuariosSADEService.obtenerUsuarioActual();
								String acronimoGedo = documentoManagerService.generarDocumentoGedo(mediaUpld,
										this.getSelectedTipoDocumento().getAcronimo(), usuario);
								expedienteElectronicoService.vinculacionDocumentosAExpedienteTe(acronimoGedo,
										this.ee.getCodigoCaratula(), loggedUsername);
								refreshComposer();

								Messagebox.show(Labels.getLabel("te.inicioDocumento.agregado"), Labels.getLabel("ee.general.information"), Messagebox.OK,
										Messagebox.INFORMATION);

								  // Refresh pantalla
								refrescarPantallaPostCargaDocLibre();
							}
						});
			} else {
				Messagebox.show(Labels.getLabel("te.inicioDocumento.pdf"), "Error", Messagebox.OK, Messagebox.EXCLAMATION);
			}
		} else {
			Messagebox.show(Labels.getLabel("ee.general.mensaje.fileupload"), Labels.getLabel("ee.general.advertencia"),
					Messagebox.OK, Messagebox.ERROR);
		}
		try {
			refreshComposer();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void refrescarPantallaPostCargaDocLibre() throws InterruptedException {
		Events.echoEvent(Events.ON_USER, (Component) tramitacionWindow, "actualizarTramitacionRender");
		      
		refreshComposer();
		reloadAcordeonPorMetadato();
	} 
	public void verifyRejectBtn() {
		Toolbarbutton rechazarBtn = (Toolbarbutton) this.tramitacionWindow.getFellow("rechazarBtn");

		if (getTramitacionHelper().getActiveState().acceptReject()) {
			// rechazarBtn.setVisible(true);
			// subsanar.detach(); // quito el boton de subsanar
		}
	}

	private boolean tieneActividadPendienteDeSubsanacion() {

		List<String> workIds = new ArrayList<String>();
		workIds.add(this.ee.getIdWorkflow());

		List<ActividadInbox> actividades = actividadExpedienteService.buscarActividadesVigentes(workIds);

		for (ActividadInbox act : actividades) {
			if (act.getEstado().equals(ConstantesWeb.ESTADO_ABIERTA)
					|| act.getEstado().equals(ConstantesWeb.ESTADO_PENDIENTE)) {
				return true;
			}
		}
		return false;
	}

	private ExpedienteElectronicoDTO loadEE() {
		List<DocumentoDTO> documentosEnMemoria = null;
		
		if(this.ee != null && this.ee.getDocumentos()!= null 
				&& !this.ee.getDocumentos().isEmpty() ) {
			 documentosEnMemoria = this.ee.getDocumentos()
					.stream().filter(doc -> doc.getId() == null).collect(Collectors.toList());			
		}
		
		this.ee = (ExpedienteElectronicoDTO) getVariableWorkFlowEEObject("idExpedienteElectronico");
		
		if(documentosEnMemoria!=null && !documentosEnMemoria.isEmpty()) {			
			documentosEnMemoria.forEach(doc-> this.ee.agregarDocumento(doc));
		}
		if (getTramitacionHelper().getActiveState() != null) {
			getTramitacionHelper().getActiveState().setExpedienteElectronico(this.ee);
		}
		
		getTramitacionHelper().reinitialize(this.loggedUsername, this.ee);
		
		return this.ee;
	}

	/**
	 * Actualiza la lista de documentos notificables sin notificar del composer.
	 * 
	 * 
	 * @param ee
	 */
	private void actualizaDocsNotificablesSinNotificar(ExpedienteElectronicoDTO ee) {
		boolean firstRun = (this.documentosNotificablesSinNotificar == null) ? true : false;
		if (firstRun) {
			this.documentosNotificablesSinNotificar = new HashSet<>();
		}

		for (DocumentoDTO aux : ee.getDocumentos()) {
			// Si no conoce los Tipo de Documento, los busca.
			if (firstRun && !aux.getDefinitivo()) {
				aux.setTipoDocumento(tipoDocumentoService.consultarTipoDocumentoPorAcronimo(aux.getTipoDocAcronimo()));
				// Si no es definitivo y es notificable, se agrega a la lista.
				if (aux.getTipoDocumento().getEsNotificable()) {
					this.documentosNotificablesSinNotificar.add(aux.getNumeroSade());
				}
				// Si es definitivo y aun se encuentra en la lista, se quita.
			} else if (!firstRun && aux.getDefinitivo()
					&& this.documentosNotificablesSinNotificar.contains(aux.getNumeroSade())) {
				this.documentosNotificablesSinNotificar.remove(aux.getNumeroSade());
			}
		}
	}

	/**
	 * Muestra u oculta el botón de Notificar VUC dependiendo si hay documentos
	 * notificables sin notificar.
	 */
	private void dibujaBotonNotificarVuc() {
//		if (this.documentosNotificablesSinNotificar == null || this.documentosNotificablesSinNotificar.isEmpty()) {
//			comunicarTadButton.setDisabled(true);
//		} else {
//			comunicarTadButton.setDisabled(false);
//		}
	}

	boolean tieneActividad = false;

	private void compBotonSubsanar() {

		IActividadExpedienteService actividadExpedienteService = (IActividadExpedienteService) SpringUtil
				.getBean(ConstantesServicios.ACTIVIDAD_EXPEDIENTE_SERVICE);
		List<String> workIds = new ArrayList<String>();
		workIds.add(this.ee.getIdWorkflow());

		if (!actividadExpedienteService.buscarActividadesVigentes(workIds).isEmpty()) {
			tieneActividad = true;
			subsanarAndPaseDisabled();
		} else {
			//subsanar.setDisabled(false);
			//subsanar.setTooltiptext(null);
			//INI -Habilitar cancelar
			cancelar.setDisabled(false);
			//FIN -Habilitar cancelar
		}

		if (actividadExpedienteService.buscarHistoricoActividades(this.ee.getIdWorkflow()).size() > 0)
			tieneActividad = true;

		// if (this.ee.getSistemaCreador() != null) {
		// if (!this.ee.getSistemaCreador().equals(SISTEMA_TAD)) {
		// subsanar.detach();
		// }
		//
		// } else {
		// subsanar.detach();
		// }
	}

	private void subsanarAndPaseDisabled() {
		String labelPend = Labels.getLabel("ee.tramitacion.actividadesPend.tooltip");
		//subsanar.setDisabled(false);// TODO:cambio temporal para que no se
									// bloquee
									// el boton subsanar
		subsanar.setTooltiptext(labelPend);
		crearPaquete.setDisabled(true);
		crearPaquete.setTooltiptext(labelPend);
		enviar.setDisabled(true);
		enviar.setTooltiptext(labelPend);
		tramitacionParalelo.setDisabled(true);
		tramitacionParalelo.setTooltiptext(labelPend);
	}

	private void busquedaActividadesPendientes() {
		IActividadExpedienteService actividadExpedienteService = (IActividadExpedienteService) SpringUtil
				.getBean(ConstantesServicios.ACTIVIDAD_EXPEDIENTE_SERVICE);
		List<String> workIds = new ArrayList<String>();
		workIds.add(this.ee.getIdWorkflow());

		if (!actividadExpedienteService.buscarActividadesVigentes(workIds).isEmpty()) {
			tieneActividad = true;
		} else {
			tieneActividad = false;
		}
	}

	private void compTabActividad() {
		if (!tieneActividad) {
			if ((this.ee.getSistemaCreador() == null) || !this.ee.getSistemaCreador().equals(SISTEMA_VUC)) {
				// quito el componente tab
				buzonAct.setVisible(false);
			}
		}
	}

	private void habilitarBotonesGeneral() {
		List<ExpedienteAsociadoEntDTO> listaAsociados = this.ee.getListaExpedientesAsociados();
		boolean haypendientes = false;
		int cont = 0;

		while (((cont < listaAsociados.size()) && !haypendientes)) {
			if (((listaAsociados.get(cont).getEsExpedienteAsociadoFusion() != null)
					|| (listaAsociados.get(cont).getEsExpedienteAsociadoTC() != null))
					&& !listaAsociados.get(cont).getDefinitivo()) {
				haypendientes = true;
			}

			cont++;
		}

		if (haypendientes) {
			this.enviar.setDisabled(true);
			this.tramitacionParalelo.setDisabled(true);
			this.crearPaquete.setDisabled(true);
			if (!this.ee.getEsReservado()) {
				this.reservar.setDisabled(true);
				this.reservar.setVisible(false);
				this.quitarReserva.setVisible(false);
			} else {
				this.quitarReserva.setVisible(false);
				this.quitarReserva.setDisabled(true);

			}
		} else {
			if (((getWorkingTask().getActivityName().equals(ESTADO_TRAMITACION))
					|| (getWorkingTask().getActivityName().equals(ESTADO_EJECUCION)))
					&& (!((this.ee.getEsCabeceraTC() != null) && this.ee.getEsCabeceraTC()))) {
				tramitacionParalelo.setDisabled(false);
				boolean tienePermisoGedo = usuariosSADEService.usuarioTieneRol(loggedUsername,
						ConstantesWeb.ROL_EE_CONFIDENCIAL);
				if (usuarioHabilitadoParaReservar() && tienePermisoGedo) {
					reservar.setDisabled(false);
					reservar.setVisible(true);
				}
			} else {
				tramitacionParalelo.setDisabled(true);
				reservar.setDisabled(true);
				reservar.setVisible(false);
			}

			this.enviar.setDisabled(false);
			if (workingTask.getActivityName().equals(TRAMITACION_EN_PARALELO)) {
				this.crearPaquete.setDisabled(true);
			} else {
				this.crearPaquete.setDisabled(false);
			}

			habilitarBotonesReservaExpediente();
			compBotonSubsanar();
			compTabActividad();
		}

		if (!constantesDB.getNombreEntorno().toUpperCase().equals(ConstantesWeb.MODULO_EE.toUpperCase())) {
			this.crearPaquete.setDisabled(true);
		}
	}

	private void habilitarBotonesReservaExpediente() {
		if ( // valida los tres estados posibles
		estadoValidoParaReservar()) {
			tipoDeReserva = (this.ee.getEsReservado() == false) ? ConstantesWeb.SIN_RESERVA
					: (this.ee.getTrata().getTipoReserva().getTipoReserva().equals(ConstantesWeb.RESERVA_PARCIAL)
							? ConstantesWeb.RESERVA_PARCIAL
							: (this.ee.getTrata().getTipoReserva().getTipoReserva().equals(ConstantesWeb.RESERVA_TOTAL)
									? ConstantesWeb.RESERVA_TOTAL : ConstantesWeb.RESERVA_EN_TRAMITACION));

			Boolean tienePermisoGedo = usuariosSADEService.usuarioTieneRol(this.datosUsuario.getUsername(),
					ConstantesWeb.ROL_EE_CONFIDENCIAL);

			if (tipoDeReserva.equals(ConstantesWeb.SIN_RESERVA)) {
				if (!this.ee.getTrata().getTipoReserva().getTipoReserva().equals(RESERVA_PARCIAL)
						&& !this.ee.getTrata().getTipoReserva().getTipoReserva().equals(RESERVA_TOTAL)) {
					if (((getWorkingTask().getActivityName().equals(ESTADO_TRAMITACION))
							|| (getWorkingTask().getActivityName().equals(ESTADO_EJECUCION)))) {
						if (usuarioHabilitadoParaReservar() && tienePermisoGedo
								&& !(this.ee.getEsCabeceraTC() != null && this.ee.getEsCabeceraTC())) {
							this.reservar.setDisabled(false);
							this.reservar.setVisible(true);
						}
					}
				}
			} else if (tipoDeReserva.equals(ConstantesWeb.RESERVA_PARCIAL)
					|| tipoDeReserva.equals(ConstantesWeb.RESERVA_TOTAL)) {
				this.reservar.setDisabled(false);
				this.reservar.setVisible(true);
			} else if (tipoDeReserva.equals(ConstantesWeb.RESERVA_EN_TRAMITACION)) {
				this.reservar.setVisible(false);

				if ((this.ee.getUsuarioReserva() != null) && this.ee.getUsuarioReserva().equals(loggedUsername)
						&& tienePermisoGedo && usuarioHabilitadoParaReservar()
						&& !(this.ee.getEsCabeceraTC() != null && this.ee.getEsCabeceraTC())) {
					this.quitarReserva.setVisible(true);
					this.quitarReserva.setDisabled(false);
				} else {
					this.quitarReserva.setDisabled(true);
					this.quitarReserva.setVisible(false);
				}

			}
		}
	}

	private boolean estadoValidoParaReservar() {
		return getWorkingTask().getActivityName().equals(ESTADO_EJECUCION)
				|| getWorkingTask().getActivityName().equals(ESTADO_TRAMITACION)
				|| getWorkingTask().getActivityName().equals(ESTADO_SUBSANACION);
	}

	private Boolean usuarioHabilitadoParaReservar() {
		List<TrataReparticionDTO> reparticionesRestoras = obtenerReparticionesRectora();

		Boolean usuarioPerteneceAReparticion = usuarioPerteneceAReparticionRectora(loggedUsername,
				reparticionesRestoras);

		if (usuarioPerteneceAReparticion) {
			return true;
		} else {
			return false;
		}
	}

	/***
	 *
	 * Condiciones para ver la solapa caratula en la ejecucion: 1. Usuarios con
	 * permiso GEDO Confidencial y que participo en la tramitación 2. Usuarios
	 * con permiso GEDO Confidencial y que pertenece a la repartición rectora
	 * (en la ejecución es suficiente con la condición 1 y 3) 3. Usuario que
	 * caratuló el EE
	 *
	 * @return Boolean
	 */
	private Boolean puedeVerSolapaCaratula() {
		return (usuariosSADEService.usuarioTieneRol(this.datosUsuario.getUsername(), ConstantesWeb.ROL_EE_CONFIDENCIAL)
				|| loggedUsername.equalsIgnoreCase(this.ee.getUsuarioCreador()));
	}

	private List<TrataReparticionDTO> obtenerReparticionesRectora() {
		List<TrataReparticionDTO> reparticionesRestoras = new ArrayList<>();
		for (TrataReparticionDTO repa : this.ee.getTrata().getReparticionesTrata()) {
			if (repa.getReserva()) {
				reparticionesRestoras.add(repa);
			}
		}

		return reparticionesRestoras;
	}

	private Boolean usuarioPerteneceAReparticionRectora(String loggedUsername,
			List<TrataReparticionDTO> reparticionesRestoras) {
		if (reparticionesRestoras.size() != 0) {
			if (!((this.datosUsuario == null) || (this.datosUsuario.getCodigoReparticion() == null))) {
				for (int i = 0; i < reparticionesRestoras.size(); i++) {
					if (reparticionesRestoras.get(i).getCodigoReparticion().trim()
							.equals(ConstantesWeb.TODAS_REPARTICIONES_HABILITADAS)
							|| reparticionesRestoras.get(i).getCodigoReparticion().trim()
									.equals(this.datosUsuario.getCodigoReparticion())) {
						return true;
					}
				}
			}
		}

		return false;
	}

	@SuppressWarnings("static-access")
	private void habilitarTCvsFusion() {
		TramitacionTabsConditional tramitacionTabsConditional = new TramitacionTabsConditional(this.workingTask);
		// ************************************************************************
		// **
		// ** Feature: Habilitacion de funcionalidad tratamiento en
		// paralelo/tratamiento en conjunto.
		// ** Se modifica la pantalla de visualización de expediente para que:
		// ** Sumadas a las validaciones actuales, para habilitar el botón "pase
		// múltiple", se controle que esta operación se encuentre habilitada
		// para la trata del expediente
		// ** Sumadas a las validaciones actuales, para habilitar la solapa
		// "tramitación Conjunta", se controle que esta operación se encuentre
		// habilitada para la trata del expediente
		// ************************************************************************
		this.expedienteFusion.setDisabled(tramitacionTabsConditional.condition(this.ee).getIsFunsion());
		this.expedienteTramitacionConjunta
				.setDisabled(tramitacionTabsConditional.condition(this.ee).getIsTramitacionConjunta());
	}

	public void onUpload$uploadArchivoDeTrabajo(UploadEvent event) throws InterruptedException, IOException {
		boolean estaEnlista = false;
		for (ArchivoDeTrabajoDTO archivoDeTrabajo : this.ee.getArchivosDeTrabajo()) {
			if (archivoDeTrabajo.getNombreArchivo().equals(event.getMedia().getName())) {
				estaEnlista = true;

				break;
			}
		}

		if (!estaEnlista) {
			if (event.getMedia() instanceof AImage) {
				dataFile = IOUtils.toByteArray(((AImage) event.getMedia()).getStreamData());
			} else {
				media = (AMedia) event.getMedia();

				if (media.isBinary()) {
					try {
						dataFile = IOUtils.toByteArray(media.getStreamData());
					} catch (IOException e) {
						logger.error(e.getMessage());
					}
				} else {
					dataFile = IOUtils.toByteArray(media.getReaderData());
				}
			}

			archivodetrabajo = new ArchivoDeTrabajoDTO();

			archivodetrabajo.setNombreArchivo(event.getMedia().getName());

			onAgregarTipoArchivo(archivodetrabajo);

		} else {
			if (!workingTask.getActivityName().equals(TRAMITACION_EN_PARALELO)) {
				Messagebox.show(Labels.getLabel("ee.tramitacion.archivoDetrabajo.value"),
						Labels.getLabel("ee.tramitacion.informacion.documentoNoAsociado"), Messagebox.OK,
						Messagebox.EXCLAMATION);
			} else {
				Messagebox.show(Labels.getLabel("ee.tramitacion.archivoDetrabajoParalelo.value"),
						Labels.getLabel("ee.tramitacion.informacion.documentoNoAsociado"), Messagebox.OK,
						Messagebox.EXCLAMATION);
			}
		}
	}

	public void onAgregarTipoArchivo() {
		ArchivoDeTrabajoDTO archivoDeTrabajoActual = this.selectedArchivoDeTrabajo;
		onAgregarTipoArchivo(archivoDeTrabajoActual);
	}

	/**
	 * Abro la ventana para AÃ±adir el tipo de archivo subido
	 */
	public void onAgregarTipoArchivo(ArchivoDeTrabajoDTO archivodetrabajo) {
		Map<String, Object> map = new HashMap<>();
		map.put("expediente", this.ee);
		map.put("archivoDeTrabajo", archivodetrabajo);
		this.tipoArchivoDeTrabajoWindow = (Window) Executions
				.createComponents("/expediente/macros/tiposArchivosDeTrabajo.zul", null, map);
		this.tipoArchivoDeTrabajoWindow.setClosable(true);

		this.tipoArchivoDeTrabajoWindow.doModal();
	}

	private void mostrarForegroundBloqueanteToken() {
		Clients.showBusy(Labels.getLabel("ee.tramitacion.subirarchivo"));
	}

	/**
	 * @author eduavega Metodo que carga un arhivo de trabajo, se envian los
	 *         parametros archivodetrabajo y nombreSpaceAlfresco para que pueda
	 *         ser subido dicho archivo al repositorio Alfresco, se persiste el
	 *         archivo en la tabla
	 * @param event
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public void uploadArchivoDeTrabajo() throws InterruptedException, IOException {
		try {
			archivodetrabajo.setDataArchivo(dataFile);
			archivodetrabajo.setUsuarioAsociador(loggedUsername);
			archivodetrabajo.setIdTask(this.workingTask.getExecutionId());

			if ((this.ee.getEsCabeceraTC() != null) && this.ee.getEsCabeceraTC()) {
				archivodetrabajo.setIdExpCabeceraTC(this.ee.getId());
			}

			archivodetrabajo.setFechaAsociacion(new Date());

			ReparticionParticipanteDTO repPart = new ReparticionParticipanteDTO();
			repPart.setReparticion(this.datosUsuario.getCodigoReparticion());
			repPart.setTipoOperacion("ALTA");
			repPart.setFechaModificacion(new Date());
			repPart.setUsuario(loggedUsername);

			archivodetrabajo.getReparticionesParticipantes().add(repPart);

			String nombreSpace = this.ee.getCodigoCaratula();
			String idFileNet = this.archivoDeTrabajoService.subirArchivoDeTrabajo(archivodetrabajo, nombreSpace);
			archivodetrabajo.setIdGuardaDocumental(idFileNet);
			this.ee.getArchivosDeTrabajo().add(archivodetrabajo);
			this.cargarArchivosDeTrabajoFiltrados();
			// Nuevo
			documentoTrabajoLb.setVisible(true);
			binder.loadComponent(documentoTrabajoLb);

			// Nuevo
		} finally {
			Clients.clearBusy();
		}
	}

	public void onClick$modificacionArchivoDeTrabajo() throws InterruptedException, IOException {
		this.onModificacionArchivoDeTrabajo();
	}

	public void onModificacionArchivoDeTrabajo() throws InterruptedException, IOException {
		try {
			ArchivoDeTrabajoDTO archivoDeTrabajo = this.selectedArchivoDeTrabajo;

			for (ArchivoDeTrabajoDTO archivo : this.ee.getArchivosDeTrabajo()) {
				if (archivo.getNombreArchivo().equals(archivoDeTrabajo.getNombreArchivo())) {
					archivo.setTipoArchivoTrabajo(archivoDeTrabajo.getTipoArchivoTrabajo());
					modificarTipoArchivo(archivo);
				}
			}
		} finally {
			Clients.clearBusy();
			binder.loadAll();
		}
	}

	public void actualizarArchivoDeTrabajo() throws InterruptedException, IOException {
		try {
			ArchivoDeTrabajoDTO archivoDeTrabajo = this.selectedArchivoDeTrabajo;

			for (ArchivoDeTrabajoDTO archivo : this.ee.getArchivosDeTrabajo()) {
				if (archivo.getNombreArchivo().equals(archivoDeTrabajo.getNombreArchivo())) {
					archivo.setTipoArchivoTrabajo(archivoDeTrabajo.getTipoArchivoTrabajo());
				}
			}
		} finally {
			Clients.clearBusy();
			binder.loadAll();
		}
	}

	/**
	 * Abro la ventana para AÃ±adir el tipo de archivo subido
	 */
	public void modificarTipoArchivo(ArchivoDeTrabajoDTO archivodetrabajo) {
		Map<String, Object> map = new HashMap<>();
		map.put("expediente", this.ee);
		map.put("archivoDeTrabajo", archivodetrabajo);
		this.tipoArchivoDeTrabajoWindow = (Window) Executions
				.createComponents("/expediente/macros/tiposArchivosDeTrabajo.zul", null, map);
		this.tipoArchivoDeTrabajoWindow.setClosable(true);

		this.tipoArchivoDeTrabajoWindow.doModal();
	}

	public void onClick$asociarexpediente() {
		this.cargarExpedientesAsociadosFiltrados();
	}

	public void onClick$documentos() {
		this.enviarEventoAcordeon(Events.ON_RENDER, this.ee, true);
	}

	public void onClick$documentosdetrabajo() {
		this.cargarArchivosDeTrabajoFiltrados();
	}

	public void onClick$formularios() {
		this.loadExpedientForms();
	}

	/**
	 * @return the eeNombramientoVinculado
	 */
	public ExpedienteElectronicoDTO getEeNombramientoVinculado() {

		{
			if (this.ee.getListaExpedientesAsociados() != null && !this.ee.getListaExpedientesAsociados().isEmpty()) {
				ExpedienteAsociadoEntDTO ea = this.ee.getListaExpedientesAsociados().get(0); // tomo
				// solamente
				// el
				// primer
				// documento
				// asociado
				eeNombramientoVinculado = this.expedienteElectronicoService.obtenerExpedienteElectronico(
						ea.getTipoDocumento(), ea.getAnio(), ea.getNumero(), ea.getCodigoReparticionUsuario());
				return eeNombramientoVinculado;
			}
		}
		return null;
	}

	/**
	 * @param eeNombramientoVinculado
	 *            the eeNombramientoVinculado to set
	 */
	public void setEeNombramientoVinculado(ExpedienteElectronicoDTO eeNombramientoVinculado) {
		this.eeNombramientoVinculado = eeNombramientoVinculado;
	}

	public void onClick$controlNombramiento() {

	}

	public void onClick$datosPropios() throws Exception {
		Executions.getCurrent().getDesktop().setAttribute("tramitacionGuardar", this.guardarModificacion);
		this.metadatos = this.ee.getMetadatosDeTrata();
		hm.put(DatosPropiosTrataCaratulaComposer.METADATOS, this.metadatos);
		hm.put(DatosPropiosTrataCaratulaComposer.ES_SOLO_LECTURA,
				((this.validacionModificarDatosPropios() > 0) ? true : false));

		this.datosPropiosWindow = (Window) Executions.createComponents("/expediente/datosPropiosDeTrataCaratula.zul",
				this.self, hm);
		this.datosPropiosWindow.setClosable(true);

		this.datosPropiosWindow.doModal();
	}

	/**
	 * Limpia el formulario
	 */
	private void limpiarFormulario() {
		this.anioSADE.setText("");
		this.numeroSADE.setText("");
		this.reparticionBusquedaUsuario.setText("");
	}

	/**
	 * Habilita el boton de modificacion del Expediente.
	 *
	 * @throws Exception
	 */
	public boolean noHabilitarModificacion() throws Exception {
		int validar = this.validacionModificarDatosPropios();

		if (validar == 1) {
			Messagebox.show(Labels.getLabel("te.tramitacion.permisoModificador"));
		} else {
			if (validar == 2) {
				Messagebox.show(
						Labels.getLabel("te.tramitacion.pertenecerReparticion"));
			} else {
				if (validar == 3) {
					Messagebox.show(
							Labels.getLabel("te.tramitacion.diasHabiles"));
				}
			}
		}

		return validar > 0;
	}

	public void cargarExpedientesAsociadosFiltrados() {
		expedientesAsociadosFiltrados = new ArrayList<ExpedienteAsociadoEntDTO>();

		if (!workingTask.getActivityName().equals(TRAMITACION_EN_PARALELO)) {
			for (ExpedienteAsociadoEntDTO expedienteAsociado : this.ee.getListaExpedientesAsociados()) {
				if (((expedienteAsociado.getEsExpedienteAsociadoTC() == null)
						|| !expedienteAsociado.getEsExpedienteAsociadoTC())
						&& ((expedienteAsociado.getEsExpedienteAsociadoFusion() == null)
								|| !expedienteAsociado.getEsExpedienteAsociadoFusion())) {
					this.expedientesAsociadosFiltrados.add(expedienteAsociado);
				}
			}
		} else {
			for (ExpedienteAsociadoEntDTO expedienteAsociado : this.ee.getListaExpedientesAsociados()) {
				if (((expedienteAsociado.getEsExpedienteAsociadoTC() == null)
						|| !expedienteAsociado.getEsExpedienteAsociadoTC())
						&& ((expedienteAsociado.getEsExpedienteAsociadoFusion() == null)
								|| !expedienteAsociado.getEsExpedienteAsociadoFusion())) {
					if ((workingTask.getCreateTime().compareTo(expedienteAsociado.getFechaAsociacion()) >= 0)
							|| (expedienteAsociado.getIdTask().equals(this.workingTask.getExecutionId()))) {
						this.expedientesAsociadosFiltrados.add(expedienteAsociado);
					}
				}
			}
		}

		// A la lista de filtrados le agrego la lista que esta en memoria.
		this.expedientesAsociadosFiltrados.addAll(listaExpedienteAsociado);

		this.binder.loadComponent(listboxExpedientes);
	}

	public void cargarArchivosDeTrabajoFiltrados() {
		archivosDeTrabajoFiltrados = new ArrayList<ArchivoDeTrabajoDTO>();

		if (!workingTask.getActivityName().equals(TRAMITACION_EN_PARALELO)) {
			String reparticionUsuario = this.datosUsuario.getCodigoReparticion();
			List<TrataReparticionDTO> reparticionesRestoras = obtenerReparticionesRectora();

			this.archivosDeTrabajoFiltrados.addAll(this.ee.getArchivosDeTrabajo());
		} else {
			for (ArchivoDeTrabajoDTO archivoDeTrabajo : this.ee.getArchivosDeTrabajo()) {
				/*
				 * Es posible que haya archivos de trabajo asociados a un
				 * expediente antes de realizar los cambios en las columnas
				 * usuarioAsociador y fechaDeAsociacion en la base de datos. en
				 * tal caso se va a setear el usario con valor vacio y la fecha
				 * de asociacion con la fecha del dia.
				 */
				if (archivoDeTrabajo.getUsuarioAsociador() == null) {
					archivoDeTrabajo.setUsuarioAsociador("");
				}

				if (archivoDeTrabajo.getFechaAsociacion() == null) {
					archivoDeTrabajo.setFechaAsociacion(new Date());
				}

				/*
				 * Esto se coloca por que al momento de adquirir una tarea
				 * paralela. El usuario que al adquiere (usuario que origino la
				 * tarea paralela), debe poder visualizar y modificar las
				 * acciones que realizo el usuario al cual se la habia asignado
				 */
				if ((workingTask.getCreateTime().compareTo(archivoDeTrabajo.getFechaAsociacion()) >= 0)
						|| (archivoDeTrabajo.getIdTask().equals(this.workingTask.getExecutionId()))) {
					this.archivosDeTrabajoFiltrados.add(archivoDeTrabajo);
				}
			}
		}

		binder.loadComponent(documentoTrabajoLb);
	}

	private void obtenerArchivosDeTrabajoPorUsuarioAsociador(String reparticionUsuario) {
		List<ArchivoDeTrabajoDTO> archivosDeTrabajoFiltradosAux = new ArrayList<ArchivoDeTrabajoDTO>();
		archivosDeTrabajoFiltradosAux.addAll(this.ee.obtenerArchivosDeTrabajoAcumulado(reparticionUsuario));

		for (ArchivoDeTrabajoDTO archivoDeTrabajo : archivosDeTrabajoFiltradosAux) {
			if (archivoDeTrabajo.getUsuarioAsociador().equals(loggedUsername)) {
				this.archivosDeTrabajoFiltrados.add(archivoDeTrabajo);
			}
		}
	}

	public void agregarArchivosDeTrabajoFiltrados() {
		archivosDeTrabajoFiltrados = new ArrayList<ArchivoDeTrabajoDTO>();

		if (!workingTask.getActivityName().equals(TRAMITACION_EN_PARALELO)) {
			// setArchivosDeTrabajoFiltrados(this.ee.getArchivosDeTrabajo());
			this.archivosDeTrabajoFiltrados.addAll(this.ee.getArchivosDeTrabajo());
		} else {
			for (ArchivoDeTrabajoDTO archivoDeTrabajo : this.ee.getArchivosDeTrabajo()) {
				/*
				 * Es posible que haya archivos de trabajo asociados a un
				 * expediente antes de realizar los cambios en las columnas
				 * usuarioAsociador y fechaDeAsociacion en la base de datos. en
				 * tal caso se va a setear el usario con valor vacio y la fecha
				 * de asociacion con la fecha del dia.
				 */
				if (archivoDeTrabajo.getUsuarioAsociador() == null) {
					archivoDeTrabajo.setUsuarioAsociador("");
				}

				if (archivoDeTrabajo.getFechaAsociacion() == null) {
					archivoDeTrabajo.setFechaAsociacion(new Date());
				}

				/*
				 * Esto se coloca por que al momento de adquirir una tarea
				 * paralela. El usuario que al adquiere (usuario que origino la
				 * tarea paralela), debe poder visualizar y modificar las
				 * acciones que realizo el usuario al cual se la habia asignado
				 */
				if ((workingTask.getCreateTime().compareTo(archivoDeTrabajo.getFechaAsociacion()) >= 0)
						|| (archivoDeTrabajo.getIdTask().equals(this.workingTask.getExecutionId()))) {
					this.archivosDeTrabajoFiltrados.add(archivoDeTrabajo);
				}
			}
		}

		binder.loadComponent(documentoTrabajoLb);
	}

	/**
	 *
	 * @param name
	 *            : nombre de la variable del WF que quiero encontrar
	 * @return objeto guardado en la variable
	 */
	public Object getVariableWorkFlowEEObject(String name) {
		Object obj = this.processEngine.getExecutionService().getVariable(this.workingTask.getExecutionId(), name);

		if (obj == null) {
			String auxCodExpediente = "codigoExpediente";
			String codigoEx = (String) this.processEngine.getExecutionService()
					.getVariable(this.workingTask.getExecutionId(), auxCodExpediente);
			try {
				this.ee = this.expedienteElectronicoService.obtenerExpedienteElectronicoPorCodigo(codigoEx);
			} catch (Exception e) {
			}

			if (codigoEx == null) {

				throw new VariableWorkFlowNoExisteException("No existe la variable para el id de ejecucion. "
						+ this.workingTask.getExecutionId() + ", " + name, null);
			}
		} else {
			Number id = (Number) obj;
			this.ee = expedienteElectronicoService.buscarExpedienteElectronico(id.longValue());
		}

		return this.ee;
	}

	public Object getVariableWorkFlow(String name) {
		Object obj = this.processEngine.getExecutionService().getVariable(this.workingTask.getExecutionId(), name);

		if (obj == null && !name.equals("idExpedienteElectronico")) {

			throw new VariableWorkFlowNoExisteException(
					"No existe la variable para el id de ejecucion. " + this.workingTask.getExecutionId() + ", " + name,
					null);
		}

		return obj;
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
	 *
	 * @param nameTransition
	 *            : nombre de la transición que voy a usar para la proxima tarea
	 * @param usernameDerivador
	 *            : usuario que va a tener asignada la tarea
	 */
	public void signalExecution(String nameTransition, String usernameDerivador) {
		processEngine.getExecutionService().signalExecutionById(this.workingTask.getExecutionId(), nameTransition);
	}

	public void vinculacionDefinitiva(boolean rta) throws InterruptedException {
		if (rta) {
			Messagebox.show(Labels.getLabel("ee.tramitacion.vinculacionDefinitiva.confirmacion"),
					Labels.getLabel("ee.tramitacion.titulo.asociado"), Messagebox.OK, Messagebox.INFORMATION);
		} else {
			Messagebox.show(Labels.getLabel("ee.tramitacion.vinculacionDefinitiva.confirmacion.negativa"),
					Labels.getLabel("ee.tramitacion.titulo.asociado"), Messagebox.OK, Messagebox.ERROR);
		}
	}

	public void onVinculacionDefinitiva() throws InterruptedException {
		String mensaje;
		if (this.ee.getEsReservado()) {
			mensaje = Labels.getLabel("ee.tramitacion.vinculacionDefinitiva.reservado.question");
		} else {
			mensaje = Labels.getLabel("ee.tramitacion.vinculacionDefinitiva.question");
		}
		Messagebox.show(mensaje, Labels.getLabel("ee.tramitacion.reserva.titulo"), Messagebox.YES | Messagebox.NO,
				Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					public void onEvent(Event evt) throws InterruptedException {
						switch (((Integer) evt.getData()).intValue()) {
						case Messagebox.YES:
							realizarVinculacionDefinitiva();
							break;
						case Messagebox.NO:
							break;
						}
					}
				});
	}

	private void realizarVinculacionDefinitiva() {
		boolean rta = false;
		if (workingTask.getName().equals("Paralelo") || workingTask.getActivityName().equals("Paralelo")) {
			rta = this.expedienteElectronicoService.realizarVinculacionDefinitivaVinculadosPor(this.ee,
					this.loggedUsername);
		} else {
			rta = this.expedienteElectronicoService.realizarVinculacionDefinitiva(this.ee, this.loggedUsername);
		}

		try {
			this.vinculacionDefinitiva(rta);
			if (rta) {
				this.enviarEventoAcordeon(Events.ON_RENDER, loadEE(), false);
			}
		} catch (InterruptedException e) {
			logger.error(e.getMessage());
		}
	}

	public void reservar() throws InterruptedException {
		Messagebox.show(Labels.getLabel("ee.tramitacion.reserva.confirmacion"),
				Labels.getLabel("ee.tramitacion.titulo.asociado"), Messagebox.OK, Messagebox.INFORMATION);
		vinculacionDefinitiva.setDisabled(false);
		vinculacionDefinitiva.setVisible(true);
	}

	public void quitarReserva() throws InterruptedException {
		Messagebox.show(Labels.getLabel("ee.tramitacion.quitar.reserva.confirmacion"),
				Labels.getLabel("ee.tramitacion.titulo.asociado"), Messagebox.OK, Messagebox.INFORMATION);
		vinculacionDefinitiva.setDisabled(true);
		vinculacionDefinitiva.setVisible(false);
	}

	public void subsanacionDeDocumentos() throws InterruptedException {
		Messagebox.show(Labels.getLabel("ee.tramitacion.subsanacion.confirmacion"),
				Labels.getLabel("ee.tramitacion.titulo.asociado"), Messagebox.OK, Messagebox.INFORMATION);
	}

	public void onReservarExpediente() {
		vincularActoAdministrativo(new String("ALTA"));
	}

	private void vincularActoAdministrativo(String tipo) {
		HashMap<String, Object> hma = new HashMap<String, Object>();
		hma.put("ModuleMainController", this);
		hma.put("expedienteElectronico", this.ee);
		hma.put("tipoOperacion", tipo);

		this.vinculacionActoAdministrativoWindow = (Window) Executions
				.createComponents("/expediente/vinculacionActoAdministrativo.zul", this.self, hma);
		this.vinculacionActoAdministrativoWindow.setClosable(true);

		this.vinculacionActoAdministrativoWindow.doModal();
	}

	public void onQuitarReservaExpediente() throws InterruptedException {
		Messagebox.show(Labels.getLabel("ee.tramitacion.quitarReserva.question"),
				Labels.getLabel("ee.tramitacion.quitarReserva.titulo"), Messagebox.YES | Messagebox.NO,
				Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
					public void onEvent(Event evt) throws InterruptedException {
						switch (((Integer) evt.getData()).intValue()) {
						case Messagebox.YES:
							vincularActoAdministrativo(new String("BAJA"));
							break;

						case Messagebox.NO:
							break;
						}
					}
				});
	}

	public void onConfirmarPaquete() {
		Messagebox.show(Labels.getLabel("ee.tramitacion.question.Paquete"), Labels.getLabel("ee.general.question"),
				Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EventListener() {
					public void onEvent(Event evt) {
						switch (((Integer) evt.getData()).intValue()) {

						case Messagebox.YES:
							Clients.showBusy(Labels.getLabel("ee.tramitacion.confirmarPaquete.value"));
							Events.echoEvent("onUser", self, "confirmarPaquete");
							break;

						case Messagebox.NO:
							break;
						}
					}
				});
	}

	/**
	 * crear paquete desde EE al modulo Arch
	 * 
	 * @throws NegocioException
	 */
	private void crearPaquete() throws InterruptedException, NegocioException {
		int result = 0;
		try {

			this.expedienteElectronicoService.realizarVinculacionDefinitiva(this.ee, this.loggedUsername);
			try {
				result = this.actividadExpedienteService.generarActividadCrearPaqueteArch(this.ee.getCodigoCaratula(),
						this.loggedUsername);
			} catch (Exception n) {
				Clients.clearBusy();
				Messagebox.show(Labels.getLabel("te.tramitacion.errorComunicarseArchivo"), Labels.getLabel("ee.act.msg.title.ok"),
						Messagebox.OK, Messagebox.EXCLAMATION, new EventListener() {
							public void onEvent(Event evt) {
							}
						});
			}
			if (result != 0) {

				if (result != 0) {
					Messagebox.show(
							Labels.getLabel("te.tramitacion.solicitud.archivo"),
							Labels.getLabel("ee.act.msg.title.ok"), Messagebox.OK, Messagebox.INFORMATION, new EventListener() {
								public void onEvent(Event evt) {
								}
							});
				}
				Events.echoEvent(Events.ON_USER, (Component) this.tramitacionWindow, "actualizarTramitacionRender");
			}
			Clients.clearBusy();

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new InterruptedException(e.getMessage());
		}
	}

	public void cargarTipoDocumento(TipoDocumentoDTO data) {
		this.familiaEstructuraTree.setText(data.getAcronimo().toUpperCase());
		this.familiaEstructuraTree.close();
		setSelectedTipoDocumento(data);
		//onSelectTipoDocumento();
		this.binder.loadAll();
	}
	
	 
	/**
	 * validar crear paquete desde EE al modulo Arch
	 * 
	 * @throws NegocioException
	 */
	private void validarCrearPaquete() throws InterruptedException, NegocioException {

		List<DocumentoDTO> documentos = this.ee.getDocumentos();

		int cantidadDocu = documentos.size();
		int cantidadDocDefinitivos = 0;
		for (DocumentoDTO documento : documentos) {
			if (documento.getDefinitivo()) {
				cantidadDocDefinitivos++;
			}
		}

		boolean tieneDocumentosPendientes = false;

		if (cantidadDocu == cantidadDocDefinitivos) {
			tieneDocumentosPendientes = false;
		} else {
			tieneDocumentosPendientes = true;
		}

		if (tieneDocumentosPendientes) {
			Clients.clearBusy();
			Messagebox.show(Labels.getLabel("ee.tramitacion.question.Paquete.vinculacion"),
					Labels.getLabel("ee.general.question"), Messagebox.YES | Messagebox.NO, Messagebox.QUESTION,
					new EventListener() {
						public void onEvent(Event evt) {
							switch (((Integer) evt.getData()).intValue()) {

							case Messagebox.YES:
								Clients.showBusy(Labels.getLabel("ee.tramitacion.confirmarPaquete.value"));
								Events.echoEvent("onUser", self, "crearPaquete");
								break;

							case Messagebox.NO:
								return;
							}
						}
					});
		} else {
			crearPaquete();
		}
	}

	
	public void setSelectedTipoDocumento(TipoDocumentoDTO selectedTipoDocumento) {
		this.selectedTipoDocumento = selectedTipoDocumento;
	}
	public AMedia getMedia() {
		return media;
	}

	public void setMedia(AMedia media) {
		this.media = media;
	}

	public TipoDocumentoDTO getSelectedTiposDocumentosNumeroEspecial() {
		return selectedTiposDocumentosNumeroEspecial;
	}

	public void setSelectedTiposDocumentosNumeroEspecial(TipoDocumentoDTO selectedTiposDocumentosNumeroEspecial) {
		this.selectedTiposDocumentosNumeroEspecial = selectedTiposDocumentosNumeroEspecial;
	}

	public Task getWorkingTask() {
		return workingTask;
	}

	public void setWorkingTask(Task workingTask) {
		this.workingTask = workingTask;
	}

	public ProcessEngine getProcessEngine() {
		return processEngine;
	}

	public void setProcessEngine(ProcessEngine processEngine) {
		this.processEngine = processEngine;
	}

	public String getSelectedDocumentoTrabajo() {
		return selectedDocumentoTrabajo;
	}

	public void setSelectedDocumentoTrabajo(String selectedDocumentoTrabajo) {
		this.selectedDocumentoTrabajo = selectedDocumentoTrabajo;
	}

	public String getSelectedCodigoTrata() {
		return selectedCodigoTrata;
	}

	public void setSelectedCodigoTrata(String selectedCodigoTrata) {
		this.selectedCodigoTrata = selectedCodigoTrata;
	}

	/**
	 * @return the tipoBuzonGrupal
	 */
	public Radio getTipoBuzonGrupal() {
		return tipoBuzonGrupal;
	}

	/**
	 * @param tipoBuzonGrupal
	 *            the tipoBuzonGrupal to set
	 */
	public void setTipoBuzonGrupal(Radio tipoBuzonGrupal) {
		this.tipoBuzonGrupal = tipoBuzonGrupal;
	}

	public TipoDocumentoDTO getSelectedTipoDocumento() {
		return selectedTipoDocumento;
	}
	
	/**
	 * @return the tipoCarga
	 */
	public Radio getTipoCarga() {
		return tipoCarga;
	}

	/**
	 * @param tipoCarga
	 *            the tipoCarga to set
	 */
	public void setTipoCarga(Radio tipoCarga) {
		this.tipoCarga = tipoCarga;
	}

	public DocumentoDTO getSelectedDocumento() {
		return selectedDocumento;
	}

	public void setSelectedDocumento(DocumentoDTO selectedDocumento) {
		this.selectedDocumento = selectedDocumento;
	}

	public ArchivoDeTrabajoDTO getSelectedArchivoDeTrabajo() {
		return selectedArchivoDeTrabajo;
	}

	public void setSelectedArchivoDeTrabajo(ArchivoDeTrabajoDTO selectedArchivoDeTrabajo) {
		this.selectedArchivoDeTrabajo = selectedArchivoDeTrabajo;
	}

	public String getSelectedTiposDocumentos() {
		return selectedTiposDocumentos;
	}

	public void setSelectedTiposDocumentos(String selectedTiposDocumentos) {
		this.selectedTiposDocumentos = selectedTiposDocumentos;
	}

	public List<DocumentoDTO> getDocumentosFiltrados() {
		return documentosFiltrados;
	}

	public void setDocumentosFiltrados(List<DocumentoDTO> documentosFiltrados) {
		this.documentosFiltrados = documentosFiltrados;
	}

	public List<ArchivoDeTrabajoDTO> getArchivosDeTrabajoFiltrados() {
		return archivosDeTrabajoFiltrados;
	}

	public void setArchivosDeTrabajoFiltrados(List<ArchivoDeTrabajoDTO> archivosDeTrabajoFiltrados) {
		this.archivosDeTrabajoFiltrados = archivosDeTrabajoFiltrados;
	}

	public List<ExpedienteFormularioDTO> getExpedientFormsList() {
		return expedientFormsList;
	}

	public void setExpedientFormsList(List<ExpedienteFormularioDTO> expedientFormsList) {
		this.expedientFormsList = expedientFormsList;
	}

	public List<ExpedienteAsociadoEntDTO> getExpedientesAsociadosFiltrados() {
		return expedientesAsociadosFiltrados;
	}

	public void setExpedientesAsociadosFiltrados(List<ExpedienteAsociadoEntDTO> expedientesAsociadosFiltrados) {
		this.expedientesAsociadosFiltrados = expedientesAsociadosFiltrados;
	}

	public TipoDocumentoDTO getTiposDocumentos() {
		return tiposDocumentos;
	}

	public void setTiposDocumentos(TipoDocumentoDTO tiposDocumentos) {
		this.tiposDocumentos = tiposDocumentos;
	}

	public ExpedienteElectronicoDTO getEe() {
		return ee;
	}

	public void setEe(ExpedienteElectronicoDTO ee) {
		this.ee = ee;
	}

	public TrataService getTrataService() {
		return trataService;
	}

	public void setTrataService(TrataService trataService) {
		this.trataService = trataService;
	}

	public List<String> getTiposDocBox() {
		return tiposDocBox;
	}

	public void setTiposDocBox(List<String> tiposDocBox) {
		this.tiposDocBox = tiposDocBox;
	}

	public void refreshComposer() throws InterruptedException {
		this.binder.loadAll();
	}

	public void setSegundoNombre(Textbox segundoNombre) {
		this.segundoNombre = segundoNombre;
	}

	public Textbox getSegundoNombre() {
		return segundoNombre;
	}

	public void setTercerNombre(Textbox tercerNombre) {
		this.tercerNombre = tercerNombre;
	}

	public Textbox getTercerNombre() {
		return tercerNombre;
	}

	public void setCuitCuilTipo(Longbox cuitCuilTipo) {
		this.cuitCuilTipo = cuitCuilTipo;
	}

	public Longbox getCuitCuilTipo() {
		return cuitCuilTipo;
	}

	public void setCuitCuilDocumento(Longbox cuitCuilDocumento) {
		this.cuitCuilDocumento = cuitCuilDocumento;
	}

	public Longbox getCuitCuilDocumento() {
		return cuitCuilDocumento;
	}

	public void setCuitCuilVerificador(Longbox cuitCuilVerificador) {
		this.cuitCuilVerificador = cuitCuilVerificador;
	}

	public Longbox getCuitCuilVerificador() {
		return cuitCuilVerificador;
	}

//	public void setNoDeclaraNoPosee(Checkbox noDeclaraNoPosee) {
//		this.noDeclaraNoPosee = noDeclaraNoPosee;
//	}
//
//	public Checkbox getNoDeclaraNoPosee() {
//		return noDeclaraNoPosee;
//	}
//
//	public void setCuilcuit(Row cuilcuit) {
//		this.cuilcuit = cuilcuit;
//	}
//
//	public Row getCuilcuit() {
//		return cuilcuit;
//	}
//
//	public Row getCuil() {
//		return cuil;
//	}
//
//	public void setCuil(Row cuil) {
//		this.cuil = cuil;
//	}

	public void setDireccion(Textbox direccion) {
		this.direccion = direccion;
	}

	public Textbox getDireccion() {
		return direccion;
	}

	public void setPiso(Textbox piso) {
		this.piso = piso;
	}

	public Textbox getPiso() {
		return piso;
	}

	public void setDepartamento(Textbox departamento) {
		this.departamento = departamento;
	}

	public Textbox getDepartamento() {
		return departamento;
	}

	public void setCodigoPostal(Textbox codigoPostal) {
		this.codigoPostal = codigoPostal;
	}

	public Textbox getCodigoPostal() {
		return codigoPostal;
	}

	public void setCuitCuil(String cuitCuil) {
		this.cuitCuil = cuitCuil;
	}

	public String getCuitCuil() {
		return cuitCuil;
	}

	private void reloadAcordeonPorMetadato() {

		this.enviarEventoAcordeon(Events.ON_RENDER, this.ee, false);
	}

	public TrataDTO getTrata() {
		return this.trata;
	}

	public void setTrata(TrataDTO trata) {
		this.trata = trata;
	}

	public AppProperty getAppProperty() {
		return appProperty;
	}

	public void setAppProperty(AppProperty appProperty) {
		this.appProperty = appProperty;
	}

	public Popup getClavePopUp() {
		return clavePopUp;
	}

	public void setClavePopUp(Popup clavePopUp) {
		this.clavePopUp = clavePopUp;
	}

	public Longbox getCuilNotTipo() {
		return cuilNotTipo;
	}

	public void setCuilNotTipo(Longbox cuilNotTipo) {
		this.cuilNotTipo = cuilNotTipo;
	}

	public Longbox getCuilNotDocumento() {
		return cuilNotDocumento;
	}

	public void setCuilNotDocumento(Longbox cuilNotDocumento) {
		this.cuilNotDocumento = cuilNotDocumento;
	}

	public Longbox getCuilNotVerificador() {
		return cuilNotVerificador;
	}

	public void setCuilNotVerificador(Longbox cuilNotVerificador) {
		this.cuilNotVerificador = cuilNotVerificador;
	}

	public String getCuilNot() {
		return cuilNot;
	}

	public void setCuilNot(String cuilNot) {
		this.cuilNot = cuilNot;
	}

	/**
	 * @return the actuaciones
	 */
	public List<String> getActuaciones() {
		if (actuaciones == null) {
			this.actuaciones = TramitacionHelper.findActuaciones();
		}
		return actuaciones;
	}

	private void loadComboActuaciones() {
		comboActuacion.setModel(new ListModelArray(this.getActuaciones()));
		comboActuacion.setItemRenderer(new ComboitemRenderer() {

			@Override
			public void render(Comboitem item, Object data, int arg1) throws Exception {
				String actuacion = (String) data;
				item.setLabel(actuacion);
				item.setValue(actuacion);

				if (actuacion.equals(ConstantesWeb.ACTUACION_EX)) {
					comboActuacion.setSelectedItem(item);
				}
			}
		});
	}

	final class TramitacionOnNotifyWindowListener implements EventListener {
		private TramitacionComposer composer;

		public TramitacionOnNotifyWindowListener(TramitacionComposer tramitacionComposer) {
			this.composer = tramitacionComposer;
		}

		@SuppressWarnings("unchecked")
		public void onEvent(Event event) throws Exception {
			if (event.getName().equals(Events.ON_NOTIFY)) {
				//this.composer.closeAssociatedWindow();
				if (event.getData() != null) {

					if ((event.getData() instanceof TipoDocumentoDTO)) {
						TipoDocumentoDTO data = (TipoDocumentoDTO) event.getData();
						this.composer.cargarTipoDocumento(data);
					} else {
						Map<String, Object> map = (Map<String, Object>) event
								.getData();
						String origen = (String) map.get("origen");
					}
				}
			}

			if (event.getName().equals(Events.ON_CHANGE)) {
				this.composer.enviarEventoAcordeon(Events.ON_RENDER, loadEE(), true);
			}

			if (event.getName().equals(Events.ON_USER)) {
				if (event.getData().equals("envioExpedienteAFJG")) {
					composer.envioExpedienteAFJG();
				}

				if (event.getData().equals("envioSistemaExterno")) {
					this.composer.enviarASistemaExterno();
				}
				if (event.getData().equals("uploadArchivoDeTrabajo")) {
					this.composer.uploadArchivoDeTrabajo();
				} else if (event.getData().equals("actualizarArchivoDeTrabajo")) {
					this.composer.actualizarArchivoDeTrabajo();
				} else if (event.getData().equals("updateListForm")) {
					this.composer.loadExpedientForms();
				} else if (event.getData().equals("modificacionArchivoDeTrabajo")) {
					this.composer.onModificacionArchivoDeTrabajo();
				} else if (event.getData().equals("guardarModificacion")) {
					this.composer.guardarModificacion();
				} else if (event.getData().equals("cerroVisualizacion")) {
					this.composer.enviarEventoAcordeon(Events.ON_RENDER, this.composer.getEe(), false);
				} else if (event.getData() instanceof DocumentoDTO) {
					this.composer.setSelectedDocumento((DocumentoDTO) event.getData());
					this.composer.onDesasociarDocumentos();
				} else if (event.getData().equals("quitarReservaExpediente")) {
					quitarReserva();
				} else if (event.getData().equals("modificarTituloReservaExpediente")) {
					reservar();
				} else if (event.getData().equals("actualizarListaDocumentos")) {
					this.composer.enviarEventoAcordeon(Events.ON_RENDER, loadEE(), true);
					subsanacionDeDocumentos();
				} else if (event.getData().equals("subsRealizada")) {
					// cierro la pantalla de tramitacion
					this.composer.closeAssociatedWindow();
				} else if (event.getData().equals("aprobDocCerrado")) {
					compBotonSubsanar();
					reloadAcordeon();
				} else if (event.getData().equals("aprobGuardaTemp")) {
					// si aprobo el pase a guarda temp del exp cierro la
					// pantalla de tramitacion
					this.composer.closeAssociatedWindow();
				} else if (event.getData().equals("crearPaquete")) {
					crearPaquete();
					compBotonSubsanar();
					reloadAcordeon();
				} else if (event.getData().equals("confirmarPaquete")) {
					validarCrearPaquete();
				} else if (event.getData().equals("elimActividad")) {
					// this.composer.closeAssociatedWindow();
					// busquedaActividadesPendientes();
					refreshComposer();
					habilitarBotonesGeneral();
					reloadAcordeon();
					hacerVisibleLabelLeyenda();
				} else if (event.getData().equals("actualizarTramitacionRender")) {
					refreshComposer();
					habilitarBotonesGeneral();
					reloadAcordeon();
					loadEE();
					Component acti = ZkUtil.findById(composer.tramitacionWindow, "actividadHistorico");
					if (acti != null) {
						List<Window> actWin = ZkUtil.findByType(acti, Window.class);
						HistoricoActivComposer actComposer = (HistoricoActivComposer) actWin.get(0)
								.getAttribute("histActWindow$composer");
						actComposer.refreshWindows();
					}
					buzonAct.setVisible(true);

				} else if (event.getData().equals("actualizar")) {

					reloadAcordeonPorMetadato();
				} else if ("refreshInbox".equals(event.getData())) {
					if (inboxWindow != null) {
						Events.sendEvent(Events.ON_NOTIFY, inboxWindow, null);
					}
				} 
			} else if(event.getName().equals(Events.ON_CLIENT_INFO)){
						this.composer.refrescarPantallaPostCargaDocLibre();
			
			}else if(event.getName().equals(VincularDocumentoComposer.ON_MENSAJE_ACORDEON)) {
				
				Map<String, Object> map = (Map<String, Object>) event.getData();
				this.composer.setEe((ExpedienteElectronicoDTO) map.get("ee"));
				this.composer.enviarEventoAcordeon((String) map.get("event"),
						(ExpedienteElectronicoDTO) map.get("ee"), (Boolean) map.get("incial"));
			
			}else if(event.getName().equals(VincularDocumentoComposer.ON_ADD_DOC_NOTIFICABLE)) {
				this.composer.agregarDocNotifiSinNoti((String) event.getData());
			}
		}

		private void reloadAcordeon() {
			// reload expediente y enviar evento al acordeon
			this.composer.enviarEventoAcordeon(Events.ON_RENDER, loadEE(), false);
		}
	}

	final class DocumentoComparator implements Comparator<DocumentoDTO> {
		/**
		 * Compara dos instancias de Documento y devuelve la comparación usando
		 * el criterio de más reciente primero en el orden.
		 *
		 * @param t1
		 *            Primer tarea a comparar
		 * @param t2
		 *            Segunda tarea a comparar con la primera
		 */
		public int compare(DocumentoDTO t1, DocumentoDTO t2) {
			return t2.getFechaAsociacion().compareTo(t1.getFechaAsociacion());
		}
	}

	EventListener myTabOnSelect = new EventListener() {
		public void onEvent(Event myEvent) {

		}
	};

	public void loadExpedientForms() {
		expedientFormsList = new ArrayList<ExpedienteFormularioDTO>();
		ExpedienteFormularioDTO expedienteFormulario = new ExpedienteFormularioDTO();
		expedienteFormulario.setIdEeExpedient(ee.getId());
		logger.info("id de expediente " + ee.getId());
		try {
			expedientFormsList = expedienteFormularioService.buscarFormulariosPorExpediente(expedienteFormulario);
		} catch (Exception e) {
			logger.error(e.toString());
		}
		binder.loadComponent(formsLb);
	}

	/**
	 * @return the selectedExpedientFomr
	 */
	public ExpedienteFormularioDTO getSelectedExpedientForm() {
		return selectedExpedientForm;
	}

	/**
	 * @param selectedExpedientFomr
	 *            the selectedExpedientFomr to set
	 */
	public void setSelectedExpedientForm(ExpedienteFormularioDTO selectedExpedientForm) {
		this.selectedExpedientForm = selectedExpedientForm;
	}

	public WorkFlowService getWorkflowService() {

		return workflowService;
	}

	public void setWorkflowService(WorkFlowService workflowService) {
		this.workflowService = workflowService;
	}

	public Hbox getHboxResult() {
		return hboxResult;
	}

	public void setHboxResult(Hbox hboxResult) {
		this.hboxResult = hboxResult;
	}

	public Label getLblResult() {
		return lblResult;
	}

	public void setLblResult(Label lblResult) {
		this.lblResult = lblResult;
	}

	public Window getInboxWindow() {
		return inboxWindow;
	}

	public void setInboxWindow(Window inboxWindow) {
		this.inboxWindow = inboxWindow;
	}
	
}
