package com.egoveris.te.base.composer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.ProcessInstance;
import org.jbpm.api.TaskQuery;
import org.jbpm.api.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.util.CollectionUtils;
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
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Longbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.egoveris.ffdd.model.exception.NotFoundException;
import com.egoveris.plugins.manager.PluginManager;
import com.egoveris.sharedorganismo.base.model.ReparticionBean;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.te.base.exception.VariableWorkFlowNoExisteException;
import com.egoveris.te.base.exception.external.TeException;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.ExpedienteMetadataDTO;
import com.egoveris.te.base.model.IngresoSolicitudExpedienteDTO;
import com.egoveris.te.base.model.MetadataDTO;
import com.egoveris.te.base.model.SolicitudExpedienteDTO;
import com.egoveris.te.base.model.TipoDocumentoDTO;
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
import com.egoveris.te.base.util.ScriptType;
import com.egoveris.te.base.util.TipoDocumentoPosible;
import com.egoveris.te.base.util.TipoDocumentoUtils;
import com.egoveris.te.base.util.WorkFlowScriptUtils;
import com.egoveris.te.base.vm.SolicitanteDireccionVM;

import junit.framework.AssertionFailedError;


/**
 * Composer asociado a la ventana modal que da la opcion de caratular
 * 
 * @author Juan Pablo Norverto
 * 
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class GenerarNuevoExpedienteComposer extends ValidarFormularioControladoComposer {
  /**
  * 
  */
  private static final long serialVersionUID = -1808440325113746775L;
  private static final String INICIAR_EXPEDIENTE = "Iniciar Expediente";
  private final static Logger logger = LoggerFactory
      .getLogger(GenerarNuevoExpedienteComposer.class);
  @Autowired
  private Window nuevoExpedienteWindow;
  @Autowired
  private Textbox motivoExpediente;
  @Autowired
  private Radio expedienteInterno;
  @Autowired
  private Radio expedienteExterno;
  private List<String> tiposDocumentos;
  @Autowired
  private Bandbox codigoTrata;
  private TrataDTO selectedTrata;
  
  private boolean existe;
  private List<TrataDTO> tratas;
  private List<TrataDTO> tratasSeleccionadas;
  private AnnotateDataBinder binder;
  
  @Autowired
  private Textbox descripcion;
  //@Autowired
  //private Combobox tipoDocumento;
  @Autowired
  private Textbox numeroDocumento;
  @Autowired
  private Textbox apellido;
  @Autowired
  private Textbox razonSocial;
  @Autowired
  private Textbox nombre;

  @Autowired
  private Textbox segundoApellido;

  @Autowired
  private Textbox tercerApellido;

  @Autowired
  private Textbox segundoNombre;

  @Autowired
  private Textbox tercerNombre;

  @Autowired
  private Textbox email;
  @Autowired
  private Textbox telefono;

  @Autowired
  private Longbox cuitCuilTipo;

  @Autowired
  private Longbox cuitCuilDocumento;

  @Autowired
  private Longbox cuitCuilVerificador;

  @Autowired
  private Checkbox noDeclaraNoPosee;

  @Autowired
  private Textbox direccion;

  @Autowired
  private Textbox piso;

  @Autowired
  private Textbox departamento;

  @Autowired
  private Textbox codigoPostal;

  @Autowired
  private Grid domicilioGrid;

  @Autowired
  private Label domicilioLabel;

  @Autowired
  private Window datosPropiosWindow;
  @Autowired
  private Button datosPropios = new Button();
  protected Task workingTask = null;
  @WireVariable(ConstantesServicios.PROCESS_ENGINE_SERVICE)
  protected ProcessEngine processEngine;
  private List<ExpedienteMetadataDTO> expedienteMetadata;
  private boolean esEE = false;
  private boolean permiso = false;
  private ExpedienteElectronicoDTO expedienteElectronico;
  private Window formularioControladoWindows;

  private String usuarioLicenciado;
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
  @WireVariable(ConstantesServicios.OBTENER_REPARTICION_SERVICE)
  private ObtenerReparticionServices obtenerReparticionService;
  @WireVariable(ConstantesServicios.TRATA_REPARTICION_SERVICE)
  private TrataReparticionService trataReparticionService;
  @WireVariable(ConstantesServicios.USUARIOS_SADE_SERVICE)
  private UsuariosSADEService usuariosSADEService;
  @WireVariable(ConstantesServicios.WORKFLOW_SERVICE)
  private WorkFlowService workFlowService;

  @Autowired
  private Window envioRechazoWindow;
  private String usuarioSolicitante = null;
  public String usuarioApoderado = null;
  private ReparticionBean rep;

  private Usuario user;
  
  
  @WireVariable(ConstantesServicios.PLUGIN_MANAGER)
  private PluginManager pm;

  @WireVariable(ConstantesServicios.WORKFLOW_SERVICE)
  private WorkFlowService workflowService;
  

  public void doAfterCompose(Component component) throws Exception {
    super.doAfterCompose(component);
    this.binder = new AnnotateDataBinder(component);
    
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    component.addEventListener(Events.ON_USER,
        new GenerarNuevoExpedienteOnNotifyWindowListener(this));
    this.datosPropios.setDisabled(true);
    
    // Se instancian como New porque no están en la vista.
 	// Es para evitar el Nullpointer
    this.cuitCuilTipo = new Longbox();
    this.cuitCuilDocumento = new Longbox();
    this.cuitCuilVerificador = new Longbox();
    this.noDeclaraNoPosee = new Checkbox();
    
    this.existe = true;
    this.tratas = this.trataService.buscarTratasByTipoExpediente(true);
    this.tratasSeleccionadas = new ArrayList<>(this.tratas);

    String username = Executions.getCurrent().getSession()
        .getAttribute(ConstantesWeb.SESSION_USERNAME).toString();

    user = usuariosSADEService.getDatosUsuario(username);

    this.rep = obtenerReparticionService.buscarReparticionByUsuario(username);
    component.addEventListener(Events.ON_NOTIFY,
        new GenerarNuevoExpedienteOnNotifyWindowListener(this));
    this.setWorkingTask((Task) component.getDesktop().getAttribute("selectedTask"));
    workingTask = this.getWorkingTask();

    if (workingTask.getActivityName().equals(INICIAR_EXPEDIENTE)
        || !workingTask.getActivityName().toLowerCase().contains("solicitud")) {
      Long idSolicitud = (Long) getVariableWorkFlow("idSolicitud");
      this.solicitud = solicitudExpedienteService.obtenerSolitudByIdSolicitud(idSolicitud);
    } else {
      this.solicitud = this.expedienteElectronico.getSolicitudIniciadora();
    }

    this.usuarioSolicitante = this.solicitud.getUsuarioCreacion();
    String motivo;
    if (this.solicitud.getMotivo() == null || this.solicitud.getMotivo().isEmpty()) {
      motivo = this.solicitud.getMotivoExterno();
    } else {
      motivo = this.solicitud.getMotivo();
    }
    String tipoExpediente;
    boolean esExpedienteInterno = this.solicitud.isEsSolicitudInterna();
    if (esExpedienteInterno) {
      tipoExpediente = "expedienteInterno";
    } else {
      tipoExpediente = "expedienteExterno";
    }
    this.motivoExpediente.setValue(motivo);
    this.motivoExpediente.setReadonly(true);
    String telefono = this.solicitud.getSolicitante().getTelefono();
    String email = this.solicitud.getSolicitante().getEmail();
    
    // Precarga direccion
 	SolicitanteDireccionVM.precargaDireccion(this.solicitud.getSolicitante().getId());
 	
 	// Habilita readOnly direccion
 	SolicitanteDireccionVM.setReadOnlyDireccion(true);
    
    if (this.expedienteExterno.getId().equals(tipoExpediente)) {
      this.expedienteExterno.setChecked(true);
      this.expedienteExterno.setDisabled(true);
      this.expedienteInterno.setChecked(false);
      this.expedienteInterno.setDisabled(true);
      String tipoDocumentoTemp = this.solicitud.getSolicitante().getDocumento().getTipoDocumento();
      String tipoDocumento = null;
      
      if (tipoDocumentoTemp != null && !tipoDocumentoTemp.equals("")) {
        if (tipoDocumentoTemp.equals(ConstantesWeb.CU_VALOR)) {
          tipoDocumento = ConstantesWeb.CU_VALOR + "-" + ConstantesWeb.CU_DESCRIPCION;
        } else {

          tipoDocumento = TipoDocumentoPosible.valueOf(tipoDocumentoTemp).getDescripcionCombo();
        }
      }

      //Long numeroDocumento = null;
      String numeroDocumentoTemp = this.solicitud.getSolicitante().getDocumento()
          .getNumeroDocumento();
      //if (numeroDocumentoTemp != null && !numeroDocumentoTemp.equals("")) {
      //  numeroDocumento = Long.parseLong(numeroDocumentoTemp);
      //}
      ////
      String razonSocial = this.solicitud.getSolicitante().getRazonSocialSolicitante();
      String apellido = this.solicitud.getSolicitante().getApellidoSolicitante();
      String nombre = this.solicitud.getSolicitante().getNombreSolicitante();

      String segundoNombre = this.solicitud.getSolicitante().getSegundoNombreSolicitante();
      String tercerNombre = this.solicitud.getSolicitante().getTercerNombreSolicitante();

      String segundoApellido = this.solicitud.getSolicitante().getSegundoApellidoSolicitante();
      String tercerApellido = this.solicitud.getSolicitante().getTercerApellidoSolicitante();
      //this.tipoDocumento.setValue(tipoDocumento);
      //this.tipoDocumento.setReadonly(true);
      //this.tipoDocumento.setDisabled(true);
      this.numeroDocumento.setValue(numeroDocumentoTemp);
      this.numeroDocumento.setReadonly(true);
      this.razonSocial.setValue(razonSocial);
      this.nombre.setValue(nombre);
      this.apellido.setValue(apellido);
      this.razonSocial.setReadonly(true);
      this.apellido.setReadonly(true);
      this.nombre.setReadonly(true);
      this.email.setValue(email);
      this.email.setReadonly(true);
      this.telefono.setValue(telefono);
      this.telefono.setReadonly(true);

      this.segundoNombre.setReadonly(true);
      this.segundoNombre.setValue(segundoNombre);

      this.tercerNombre.setReadonly(true);
      this.tercerNombre.setValue(tercerNombre);

      this.segundoApellido.setReadonly(true);
      this.segundoApellido.setValue(segundoApellido);

      this.tercerApellido.setReadonly(true);
      this.tercerApellido.setValue(tercerApellido);

      String cuitCuil = this.solicitud.getSolicitante().getCuitCuil();

      if (cuitCuil != null) {
        this.cuitCuilTipo.setValue(new Long(cuitCuil.substring(0, 2)));
        this.cuitCuilDocumento.setValue(new Long(cuitCuil.substring(2, 10)));
        this.cuitCuilVerificador.setValue(new Long(cuitCuil.substring(10, 11)));
      }
      
      this.cuitCuilDocumento.setReadonly(true);
      this.cuitCuilTipo.setReadonly(true);
      this.cuitCuilVerificador.setReadonly(true);

      this.solicitud.getSolicitante().getPiso();
      this.solicitud.getSolicitante().getDepartamento();
      this.solicitud.getSolicitante().getCodigoPostal();
      // this.solicitud.getSolicitante().getBarrio();
      // this.solicitud.getSolicitante().getComuna();

      this.direccion.setReadonly(true);
      this.direccion.setValue(this.solicitud.getSolicitante().getDomicilio());

      this.piso.setReadonly(true);
      this.piso.setValue(this.solicitud.getSolicitante().getPiso());

      this.departamento.setReadonly(true);
      this.departamento.setValue(this.solicitud.getSolicitante().getDepartamento());

      this.codigoPostal.setReadonly(true);
      this.codigoPostal.setValue(this.solicitud.getSolicitante().getCodigoPostal());

      // this.barrio.setReadonly(true);
      // this.barrio.setValue(this.solicitud.getSolicitante().getBarrio());

      // this.comuna.setReadonly(true);
      // this.comuna.setValue(this.solicitud.getSolicitante().getComuna());

    } else {
      this.expedienteExterno.setChecked(false);
      this.expedienteInterno.setChecked(true);
      this.expedienteExterno.setDisabled(true);
      this.expedienteInterno.setDisabled(true);
      //this.tipoDocumento.setDisabled(true);
      this.numeroDocumento.setDisabled(true);
      this.razonSocial.setReadonly(true);
      this.apellido.setReadonly(true);
      this.nombre.setReadonly(true);
      this.email.setValue(email);
      this.email.setReadonly(true);
      this.telefono.setValue(telefono);
      this.telefono.setReadonly(true);

      this.segundoNombre.setDisabled(true);
      this.tercerNombre.setDisabled(true);

      this.segundoApellido.setDisabled(true);
      this.tercerApellido.setDisabled(true);

      this.cuitCuilDocumento.setDisabled(true);
      this.cuitCuilTipo.setDisabled(true);
      this.cuitCuilVerificador.setDisabled(true);

      this.direccion.setDisabled(true);

      this.piso.setDisabled(true);

      this.departamento.setDisabled(true);

      this.codigoPostal.setDisabled(true);

      this.domicilioGrid.setVisible(false);

      Long idTrataSugerida = this.solicitud.getIdTrataSugerida();
      this.selectedTrata = trataService.obtenerTrataByIdTrataSugerida(idTrataSugerida);
      
      if (this.selectedTrata != null) {
        /*this.codigoTrata.setValue(this.trataService
            .formatoToStringTrata(this.selectedTrata.getCodigoTrata(), this.trataService
                .obtenerDescripcionTrataByCodigo(this.selectedTrata.getCodigoTrata())));*/
    	  this.codigoTrata.setValue(this.selectedTrata.getCodigoTrata().toUpperCase());
        // Se coloca aqui para que los metadatos puedan ser cambiados
        // sin necesidad de caratular
        List<MetadataDTO> metadatas = selectedTrata.getDatoVariable();
        expedienteMetadata = new ArrayList<>(metadatas.size());
        for (int i = 0; metadatas.size() > i; i++) {
          ExpedienteMetadataDTO temp = new ExpedienteMetadataDTO();
          temp.setNombre(metadatas.get(i).getNombre());
          temp.setObligatoriedad(metadatas.get(i).isObligatoriedad());
          temp.setOrden(metadatas.get(i).getOrden());
          temp.setTipo(metadatas.get(i).getTipo());
          expedienteMetadata.add(temp);
        }
        this.datosPropios.setDisabled(false);
      }

      // Si trata no tiene caratula entonces pregunto si tiene datos
      // propios - BEGIN
      if (null != this.selectedTrata) {
    	  if(this.selectedTrata.getAcronimoDocumento() == null){
    		  this.datosPropios.setDisabled(false);
    	  } else {
	        TipoDocumentoDTO tipoDocumentoCaratulaDeEE = this.tipoDocumentoService
	            .obtenerTipoDocumento(this.selectedTrata.getAcronimoDocumento());
	        if (null == tipoDocumentoCaratulaDeEE) {
	          if (!CollectionUtils.isEmpty(this.selectedTrata.getDatoVariable())) {
	            this.datosPropios.setDisabled(false);
	          }
	        } else {
	          this.datosPropios.setDisabled(true);
	        }
    	  }
      }
      // Si trata no tiene caratula entonces pregunto si tiene datos
      // propios - END
    }
    // Obtengo IdTrataSugerida de la Solicitud
    Long idTrataSugerida = this.solicitud.getIdTrataSugerida();
    this.selectedTrata = trataService.obtenerTrataByIdTrataSugerida(idTrataSugerida);

    if (this.selectedTrata != null) {
      /*this.codigoTrata.setValue(this.trataService.formatoToStringTrata(
          this.selectedTrata.getCodigoTrata(),
          this.trataService.obtenerDescripcionTrataByCodigo(this.selectedTrata.getCodigoTrata())));*/
    	this.codigoTrata.setValue(this.selectedTrata.getCodigoTrata().toUpperCase());
    }
    usuarioLicenciado = (String) this.processEngine.getExecutionService()
        .getVariable(workingTask.getExecutionId(), "usuarioLicenciado");
    formularioControladoWindows.addEventListener(Events.ON_NOTIFY,
        new GenerarNuevoExpedienteOnNotifyWindowListener(this));
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

  public void onClickExpedienteInterno() {
    //this.tipoDocumento.setDisabled(true);
    this.numeroDocumento.setDisabled(true);
    this.apellido.setDisabled(true);
  }

  public void onClickExpedienteExterno() {
    //this.tipoDocumento.setDisabled(false);
    this.numeroDocumento.setDisabled(false);
    this.apellido.setDisabled(false);
  }

  public Textbox getMotivoExpediente() {
    return motivoExpediente;
  }

  public void setMotivoExpediente(Textbox motivoExpediente) {
    this.motivoExpediente = motivoExpediente;
  }

  public List<String> getTiposDocumentos() {

    if (this.tiposDocumentos == null) {
      this.tiposDocumentos = TipoDocumentoUtils.getTiposDocumentos();

    }
    return this.tiposDocumentos;

  }

  public void setTiposDocumentos(List<String> tiposDocumentos) {
    this.tiposDocumentos = tiposDocumentos;
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

  public Radio getExpedienteInterno() {
    return expedienteInterno;
  }

  public void setExpedienteInterno(Radio expedienteInterno) {
    this.expedienteInterno = expedienteInterno;
  }

  public Radio getExpedienteExterno() {
    return expedienteExterno;
  }

  public void setExpedienteExterno(Radio expedienteExterno) {
    this.expedienteExterno = expedienteExterno;
  }

  public Bandbox getCodigoTrata() {
    return codigoTrata;
  }

  public void setCodigoTrata(Bandbox codigoTrata) {
    this.codigoTrata = codigoTrata;
  }

  public Textbox getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(Textbox descripcion) {
    this.descripcion = descripcion;
  }
  /*
  public Combobox getTipoDocumento() {
    return tipoDocumento;
  }

  public void setTipoDocumento(Combobox tipoDocumento) {
    this.tipoDocumento = tipoDocumento;
  }
  */

  public Textbox getNumeroDocumento() {
    return numeroDocumento;
  }

  public void setNumeroDocumento(Textbox numeroDocumento) {
    this.numeroDocumento = numeroDocumento;
  }

  public Textbox getApellido() {
    return apellido;
  }

  public void setApellido(Textbox apellido) {
    this.apellido = apellido;
  }

  public Textbox getNombre() {
    return nombre;
  }

  public void setNombre(Textbox nombre) {
    this.nombre = nombre;
  }

  public Textbox getRazonSocial() {
    return razonSocial;
  }

  public void setRazonSocial(Textbox razonSocial) {
    this.razonSocial = razonSocial;
  }

  public List<TrataDTO> getTratas() {
    return this.getTratasServices();
  }

  public List<TrataDTO> getTratasServices() {
    return this.trataService.buscarTratasByEstadoYTipoCaratulacion(TrataDTO.ACTIVO,
        this.getSolicitud().isEsSolicitudInterna());
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

  public Window getEnvioRechazoWindow() {
    return envioRechazoWindow;
  }

  public void setEnvioRechazoWindow(Window envioRechazoWindow) {
    this.envioRechazoWindow = envioRechazoWindow;
  }

  public void onGuardarExpediente() throws InterruptedException {
    Clients.showBusy("Procesando...");
    Events.echoEvent(Events.ON_USER, this.self, "guardar");
  }

  public void guardar() throws InterruptedException {

    // HG - PRUEBA - BEGIN
    try {

      String codigoExpedienteElectronico = "";
      String estadoExpedienteElectronico = "";
      TipoDocumentoDTO tipoDocumentoCaratulaDeEE = null;
      ExpedienteElectronicoDTO expedienteElectronico;
      IngresoSolicitudExpedienteDTO ingresoSolicitudExpediente = null;

      if (!this.permiso) {
        validarDatosDelFormulario();
        this.usuarioSolicitante = this.solicitud.getUsuarioCreacion();
        // VALIDO SI EL USUARIO QUE REALIZO LA SOLICITUD ESTA DE
        // VACACIONES

        if (usuariosSADEService.licenciaActiva(usuarioSolicitante)) {
          this.usuarioApoderado = this.validarLicencia(usuarioSolicitante);
        }
        if (this.usuarioApoderado != null) {
          Messagebox.show(
              Labels.getLabel("ee.licencia.question.value",
                  new String[] { usuarioSolicitante, this.usuarioApoderado }),
              Labels.getLabel("ee.licencia.question.title"), Messagebox.YES | Messagebox.NO,
              Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
                public void onEvent(Event evt) throws InterruptedException {
                  switch (((Integer) evt.getData()).intValue()) {
                  case Messagebox.YES:
                    continuarConGeneracionDelExpediente(usuarioApoderado, user.getUsername());
                    break;
                  case Messagebox.NO:
                    alert(
                        "Acepte el usuario designado por el solicitante o espere que este termine su periodo de licencia.	");
                    break;
                  }
                }
              });
        } else {
          // Si el usuario al que se le envió la solicitud está de
          // licencia
          // y el apoderado es el que esta caratulando.. se debe
          // caratular con la reparticion del usuario que se fue de
          // licencia
          // MANTIS: 80920
          if (null != usuarioLicenciado) {
            Usuario usuarioLicencia = usuariosSADEService.getDatosUsuario(usuarioLicenciado);
            if (!trataReparticionService.validarPermisoReparticion(this.selectedTrata,
                usuarioLicencia.getCodigoReparticion(), usuarioLicencia)) {
              this.permiso = true;
              throw new WrongValueException(this.codigoTrata,
                  Labels.getLabel("ee.iniciarCaratulaInterna.sinAutorizacion",
                      new String[] { usuarioLicencia.getCodigoReparticion().trim() }));
            } else {
              continuarConGeneracionDelExpediente(usuarioSolicitante, usuarioLicenciado);
            }
          } else {
            continuarConGeneracionDelExpediente(usuarioSolicitante, user.getUsername());
          }
        }

        if (null != this.selectedTrata) {
          tipoDocumentoCaratulaDeEE = this.validarTrata(tipoDocumentoCaratulaDeEE);

        }

        ingresoSolicitudExpediente = new IngresoSolicitudExpedienteDTO();
        ingresoSolicitudExpediente.setExpedienteElectronico(this.getExpedienteElectronico());
        ingresoSolicitudExpediente.setSolicitudExpediente(this.solicitud);

        if (null != tipoDocumentoCaratulaDeEE) {

          Executions.getCurrent().setAttribute("usuarioLicenciado", usuarioLicenciado);
          crearFormularioControlado(ingresoSolicitudExpediente,
              (Window) nuevoExpedienteWindow.getParent(), this.selectedTrata);

          // Cierro la ventana de Caratular
          nuevoExpedienteWindow.detach();
        }

      } else {
        Clients.clearBusy();
        if (usuarioLicenciado != null) {
          Usuario usuarioLicencia = usuariosSADEService.getDatosUsuario(usuarioLicenciado);
          Messagebox.show(
              Labels.getLabel("ee.iniciarCaratulaInterna.sinAutorizacion",
                  new String[] { usuarioLicencia.getCodigoReparticion().trim() }),
              Labels.getLabel("ee.general.information"), Messagebox.OK, Messagebox.ERROR);

        } else {
          Messagebox.show(
              Labels.getLabel("ee.iniciarCaratulaInterna.sinAutorizacion",
                  new String[] { rep.getCodigo().trim() }),
              Labels.getLabel("ee.general.information"), Messagebox.OK, Messagebox.ERROR);
        }
        return;
      }
    } catch (NotFoundException e) {
      logger.error("Se produjo un error obteniendo el formulario controlado: " + e.getMessage());
    } finally {
      Clients.clearBusy();

    }
  }

  /**
   * @throws WrongValueException
   * @throws InterruptedException
   */
  private void continuarConGeneracionDelExpediente(String usuarioSolicitante,
      String usuarioCreador) throws WrongValueException, InterruptedException {

    String codigoExpedienteElectronico = "";
    String estadoExpedienteElectronico = "";
    TipoDocumentoDTO tipoDocumentoCaratulaDeEE = null;
    ExpedienteElectronicoDTO expedienteElectronico = null;
    IngresoSolicitudExpedienteDTO ingresoSolicitudExpediente = null;

    try {
      if (this.selectedTrata != null) {

        String workflowSeleccionado = this.selectedTrata.getWorkflow();
        String workflowSugerido = this.workingTask.getExecutionId().substring(0,
            this.workingTask.getExecutionId().indexOf("."));

        if (!workflowSeleccionado.equalsIgnoreCase("solicitud")) {
          String oldExecutionId = this.workingTask.getExecutionId();
          Map<String, Object> variables = workFlowService.getVariables(oldExecutionId);
          ProcessInstance processIns =  workFlowService.startWorkFlowAndReturnInstance(getProcessEngine(), 
        		  		selectedTrata.getWorkflow(), variables);
          TaskQuery taskQuery = getProcessEngine().getTaskService().createTaskQuery()
        		        .executionId(processIns.getId());

          Task returnTask = taskQuery.uniqueResult();
          setWorkingTask(returnTask);
          workFlowService.removeProcessInstance(oldExecutionId);
        }

        expedienteElectronico = this.expedienteElectronicoService.generarExpedienteElectronico(
            this.workingTask, this.solicitud, this.selectedTrata, this.descripcion.getValue(),
            expedienteMetadata, usuarioCreador, this.motivoExpediente.getValue(),
            usuarioSolicitante);
        this.setExpedienteElectronico(expedienteElectronico);
        
        // Save direccion (No deberia ser necesario aqui)
	    // SolicitanteDireccionVM.saveDireccion(expedienteElectronico.getSolicitudIniciadora().getSolicitante().getId());
        this.esEE = true;

        mensajeOk(expedienteElectronico);
        this.closeAssociatedWindow();

      } else {
        throw new TeException(" Error al seleccionar la trata ", null);
      }

    } catch (RemoteAccessException e) {
      logger.error(e.getMessage(), e);
      Messagebox.show(e.getMessage(), Labels.getLabel("ee.general.advertencia"), Messagebox.OK, Messagebox.ERROR);
      this.closeAssociatedWindow();
    } catch (Exception e) {
      logger.error("Error al caratular.", e);
      Messagebox.show(Labels.getLabel("ee.login.error.caratular") + e.getMessage() + ")", "ERROR", Messagebox.OK,
          Messagebox.ERROR);
      this.closeAssociatedWindow();
    }
  }

  private void mensajeOk(ExpedienteElectronicoDTO expedienteElectronico)
      throws InterruptedException {
    Messagebox.show(
    		 Labels.getLabel("ee.caratularExtComp.msgbox.seGeneroExp") + expedienteElectronico.getCodigoCaratula()
            + Labels.getLabel("te.base.composer.enviocomposer.generic.seenviousuario") + this.usuarioSolicitante,
        Labels.getLabel("ee.tramitacion.titulo.nuevoExpediente"), Messagebox.OK,
        Messagebox.INFORMATION);
  }

  /**
   * Valida correcta comunicacion con el Servicio "tipoDocumentoService"
   * 
   * @return
   * @throws InterruptedException
   */
  private TipoDocumentoDTO validarTrata(TipoDocumentoDTO tipoDocumentoCaratulaDeEE)
      throws InterruptedException {

    try {
      if (null != this.selectedTrata && this.getSelectedTrata().getAcronimoDocumento() != null){
	        tipoDocumentoCaratulaDeEE = this.tipoDocumentoService
	            .obtenerTipoDocumento(this.getSelectedTrata().getAcronimoDocumento());
      }

    } catch (Exception e) {

      logger.error("Error al consultar Servicio de trata", e);
      throw new InterruptedException(
          "Error al consultar Servicio de TipoDocumento (" + e.getMessage() + ")");

    }
    return tipoDocumentoCaratulaDeEE;
  }

  /**
   * Valida lo datos requeridos para generar el Expediente.
   */

  private void validarDatosDelFormulario() {
    if ((this.codigoTrata.getValue() == null) || (this.codigoTrata.getValue().equals(""))) {
      this.codigoTrata.focus();
      throw new WrongValueException(this.codigoTrata,
          Labels.getLabel("ee.nuevoexpediente.faltacodigotrata"));
    }
    else {
    	verificarExistencia();
    	
    	if (!this.existe) {
    		throw new WrongValueException(this.codigoTrata, "Debe seleccionar una Trata válida.");
        }
    }

    if (this.trataService.buscarTrataByCodigo(this.selectedTrata.getCodigoTrata()) == null) {
      throw new WrongValueException(this.codigoTrata,
          Labels.getLabel("ee.nuevoexpediente.tratainactiva"));
    }

    if (expedienteMetadata == null) {
      this.datosPropios.setDisabled(false);

      List<MetadataDTO> metadatas = selectedTrata.getDatoVariable();

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

    if ((this.descripcion.getValue() == null) || (this.descripcion.getValue().equals(""))) {
      this.descripcion.focus();
      throw new WrongValueException(this.descripcion,
          Labels.getLabel("ee.nuevoexpediente.faltadescripciontrata"));
    }

    // Si trata no tiene caratula entonces pregunto si tiene datos propios -
    // BEGIN
    if(this.selectedTrata.getAcronimoDocumento() == null){
    	this.datosPropios.setDisabled(false);
    } else {
	    TipoDocumentoDTO tipoDocumentoCaratulaDeEE = this.tipoDocumentoService
	        .obtenerTipoDocumento(this.selectedTrata.getAcronimoDocumento());
	    if (null == tipoDocumentoCaratulaDeEE) {
	      if (!CollectionUtils.isEmpty(this.selectedTrata.getDatoVariable())) {
	        for (ExpedienteMetadataDTO expMetadata : expedienteMetadata) {
	          if ((expMetadata.isObligatoriedad())
	              && ((expMetadata.getValor() == null) || expMetadata.getValor().equals(""))) {
	            throw new WrongValueException(this.datosPropios,
	                "Debe completar los Datos Propios requeridos.");
	          }
	        }
	
	        this.datosPropios.setDisabled(false);
	      }
	    } else {
	      this.datosPropios.setDisabled(true);
	    }

    }
    // Si trata no tiene caratula entonces pregunto si tiene datos propios -
    // END

  }

  public void onDatosPropios() {
    if ((this.codigoTrata.getValue() == null) || (this.codigoTrata.getValue().equals(""))) {
      this.codigoTrata.focus();
      throw new WrongValueException(this.codigoTrata,
          Labels.getLabel("ee.nuevoexpediente.faltacodigotrata"));
    }

    Executions.getCurrent().getDesktop().setAttribute("selectedTask", this.workingTask);
    HashMap<String, Object> hm = new HashMap<>();
    hm.put(DatosPropiosTrataCaratulaComposer.METADATOS, this.expedienteMetadata);
    hm.put(DatosPropiosTrataCaratulaComposer.ES_SOLO_LECTURA, this.esEE);
    this.datosPropiosWindow = (Window) Executions
        .createComponents("/expediente/datosPropiosDeTrataCaratula.zul", this.self, hm);
    this.datosPropiosWindow.setClosable(true);
    // Se agrega el invalidate para recargar el toolbarbutton y que se vaya
    // el mensaje de error.
    this.datosPropios.invalidate();
    this.datosPropiosWindow.doModal();
  }

  public void onRechazar() {

    String username = Executions.getCurrent().getSession()
        .getAttribute(ConstantesWeb.SESSION_USERNAME).toString();

    String usuarioSolicitante = (String) getVariableWorkFlow("usuarioSolicitante");

    Map<String, Object> variables = new HashMap<String, Object>();

    variables.put("idSolicitud", this.solicitud.getId());
    variables.put("usuarioSolicitante", usuarioSolicitante);
    variables.put("usuarioCandidato", username);

    this.envioRechazoWindow = (Window) Executions.createComponents("/solicitud/envioRechazo.zul",
        null, new HashMap<String, Object>());
    this.envioRechazoWindow.setParent(this.nuevoExpedienteWindow);
    this.envioRechazoWindow.setPosition("center");
    this.envioRechazoWindow.setAttribute("variables", variables);
    this.envioRechazoWindow.setClosable(true);

    this.envioRechazoWindow.doModal();
  }

  public void onCancelar() {
    this.closeAssociatedWindow();
  }

  public Window getNuevoExpedienteWindow() {
    return nuevoExpedienteWindow;
  }

  public void setNuevoExpedienteWindow(Window nuevoExpedienteWindow) {
    this.nuevoExpedienteWindow = nuevoExpedienteWindow;
  }

  public void setWorkingTask(Task workingTask) {
    this.workingTask = workingTask;
  }

  public Task getWorkingTask() {
    return workingTask;
  }

  public void onSelectTipoDocumento() {

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

  public CaratulacionService getCaratulador() {
    return caratulador;
  }

  public void setCaratulador(CaratulacionService caratulador) {
    this.caratulador = caratulador;
  }

  public ExpedienteElectronicoDTO getExpedienteElectronico() {
    return this.expedienteElectronico;
  }

  public void setExpedienteElectronico(ExpedienteElectronicoDTO expedienteElectronico) {
    this.expedienteElectronico = expedienteElectronico;
  }

  // Se modifica por incidencia 4469

  public void onChange$codigoTrata() {

    this.permiso = false;

    if (null != usuarioLicenciado) {
      Usuario usuarioLicencia = usuariosSADEService.getDatosUsuario(usuarioLicenciado);
      if (!trataReparticionService.validarPermisoReparticion(this.selectedTrata,
          usuarioLicencia.getCodigoReparticion(), usuarioLicencia)) {
        this.permiso = true;
        throw new WrongValueException(this.codigoTrata,
            Labels.getLabel("ee.iniciarCaratulaInterna.sinAutorizacion",
                new String[] { usuarioLicencia.getCodigoReparticion().trim() }));
      }
    }

    if (!this.permiso && !trataReparticionService.validarPermisoReparticion(this.selectedTrata,
        this.rep.getCodigo(), this.user)) {
      this.permiso = true;
      throw new WrongValueException(this.codigoTrata, Labels.getLabel(
          "ee.iniciarCaratulaInterna.sinAutorizacion", new String[] { rep.getCodigo().trim() }));
    } else {
      List<MetadataDTO> metadatas = selectedTrata.getDatoVariable();
      expedienteMetadata = new ArrayList<>(metadatas.size());
      for (int i = 0; metadatas.size() > i; i++) {
        ExpedienteMetadataDTO temp = new ExpedienteMetadataDTO();
        temp.setNombre(metadatas.get(i).getNombre());
        temp.setObligatoriedad(metadatas.get(i).isObligatoriedad());
        temp.setOrden(metadatas.get(i).getOrden());
        temp.setTipo(metadatas.get(i).getTipo());
        expedienteMetadata.add(temp);
      }

      // Si trata no tiene caratula entonces pregunto si tiene datos
      // propios - BEGIN
      if(this.selectedTrata.getAcronimoDocumento() == null){
    	  this.datosPropios.setDisabled(false);
      } else {
	      TipoDocumentoDTO tipoDocumentoCaratulaDeEE = this.tipoDocumentoService
	          .obtenerTipoDocumento(this.selectedTrata.getAcronimoDocumento());
	      if (null == tipoDocumentoCaratulaDeEE) {
	        if (!CollectionUtils.isEmpty(this.selectedTrata.getDatoVariable())) {
	          this.datosPropios.setDisabled(false);
	        }
	      } else {
	        this.datosPropios.setDisabled(true);
	      }
      }

      // Si trata no tiene caratula entonces pregunto si tiene datos
      // propios - END
    }
  }

  public void onCreate$nuevoExpedienteWindow() {
    this.permiso = false;
    if (null != usuarioLicenciado) {
      Usuario usuarioLicencia = usuariosSADEService.getDatosUsuario(usuarioLicenciado);
      if (!trataReparticionService.validarPermisoReparticion(this.selectedTrata,
          usuarioLicencia.getCodigoReparticion(), usuarioLicencia)) {
        this.permiso = true;
        throw new WrongValueException(this.codigoTrata,
            Labels.getLabel("ee.iniciarCaratulaInterna.sinAutorizacion",
                new String[] { usuarioLicencia.getCodigoReparticion().trim() }));
      }
    } else if (!trataReparticionService.validarPermisoReparticion(this.selectedTrata,
        this.rep.getCodigo(), this.user)) {
      this.permiso = true;
      this.datosPropios.setDisabled(true);
      throw new WrongValueException(this.codigoTrata, Labels.getLabel(
          "ee.iniciarCaratulaInterna.sinAutorizacion", new String[] { rep.getCodigo().trim() }));
    } else {
      this.datosPropios.setDisabled(false);
      if (selectedTrata != null) {
        List<MetadataDTO> metadatas = selectedTrata.getDatoVariable();
        expedienteMetadata = new ArrayList<>(metadatas.size());
        for (int i = 0; metadatas.size() > i; i++) {
          ExpedienteMetadataDTO temp = new ExpedienteMetadataDTO();
          temp.setNombre(metadatas.get(i).getNombre());
          temp.setObligatoriedad(metadatas.get(i).isObligatoriedad());
          temp.setOrden(metadatas.get(i).getOrden());
          temp.setTipo(metadatas.get(i).getTipo());
          expedienteMetadata.add(temp);
        }

        // Si trata no tiene caratula entonces pregunto si tiene datos
        // propios - BEGIN
        if(this.selectedTrata.getAcronimoDocumento() == null){
        	this.datosPropios.setDisabled(false);
        } else  {
	        TipoDocumentoDTO tipoDocumentoCaratulaDeEE = this.tipoDocumentoService
	            .obtenerTipoDocumento(this.selectedTrata.getAcronimoDocumento());
	        if (null == tipoDocumentoCaratulaDeEE) {
	          if (!CollectionUtils.isEmpty(this.selectedTrata.getDatoVariable())) {
	            this.datosPropios.setDisabled(false);
	          }
	        } else {
	          this.datosPropios.setDisabled(true);
	        }
	
	        // Si trata no tiene caratula entonces pregunto si tiene datos
	        // propios - END
        }
      }
    }
  }
  
  public void onBlur$codigoTrata() {
    TrataDTO trata = null;

    if (this.codigoTrata.getValue() != null && !this.codigoTrata.getValue().trim().equals("")) {
      trata = trataService.buscarTrataByCodigo(this.codigoTrata.getValue());
    }

    //this.verificarExistencia();
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
      
      /*if (!trata.getEsInterno()) {
        throw new WrongValueException(this.codigoTrata, "La trata \"" + trata.getCodigoTrata()
            + "\" tiene que caratularse como Carátula Externa.");
      }
      */
    }

  }
  
  public void onChanging$codigoTrata(InputEvent e) {
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

  public void onSelect$trataListbox() {
    try {
      if (this.selectedTrata != null) {
        String trataSel = this.selectedTrata.getCodigoTrata();
        this.codigoTrata.setValue(trataSel.toUpperCase());
        this.binder.loadAll();
        this.codigoTrata.close();
        this.onBlur$codigoTrata();
      }
    } catch (AssertionFailedError assertionFailedError) {
      logger.error(assertionFailedError.getMessage());
    } finally {
      //Clients.clearBusy();
    }
  }
  
  public HistorialOperacionService getHistorialOperacionService() {
    return historialOperacionService;
  }

  public void setHistorialOperacionService(HistorialOperacionService historialOperacionService) {
    this.historialOperacionService = historialOperacionService;
  }

  final class GenerarNuevoExpedienteOnNotifyWindowListener implements EventListener {
    private GenerarNuevoExpedienteComposer composer;

    public GenerarNuevoExpedienteOnNotifyWindowListener(
        GenerarNuevoExpedienteComposer generarNuevoExpedienteComporser) {
      this.composer = generarNuevoExpedienteComporser;
    }

    public void onEvent(Event event) throws Exception {
      if (event.getName().equals(Events.ON_NOTIFY)) {
        if (event.getData() != null) {
          Map<String, Object> map = (Map<String, Object>) event.getData();
          Boolean origenClose = Boolean.FALSE;
          Boolean origenCancel = Boolean.FALSE;

          String origen = (String) map.get("origen");
          if (StringUtils.equals(origen, ConstantesWeb.COMPOSER)) {

          }
          origenClose = (Boolean) map.get(Events.ON_CLOSE);
          origenCancel = (Boolean) map.get(Events.ON_CANCEL);

          if (origenClose == Boolean.TRUE) {
            Events.sendEvent(this.composer.getNuevoExpedienteWindow(), new Event(Events.ON_CLOSE));
          }

          if (origenCancel == Boolean.TRUE) {
            Events.sendEvent(this.composer.getNuevoExpedienteWindow(), new Event(Events.ON_CLOSE));
          }
        } else {
          this.composer.closeAssociatedWindow();
        }
      }
      if (event.getName().equals(Events.ON_USER)) {
        if ("guardar".equals((String) event.getData())) {
          this.composer.guardar();
          WorkFlowScriptUtils.getInstance().executeScript(ScriptType.START, expedienteElectronico, null, null);
        }
      }

    }
  }

  private String validarLicencia(String username) {
    return this.usuariosSADEService.getDatosUsuario(username).getApoderado();
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

  public void setNoDeclaraNoPosee(Checkbox noDeclaraNoPosee) {
    this.noDeclaraNoPosee = noDeclaraNoPosee;
  }

  public Checkbox getNoDeclaraNoPosee() {
    return noDeclaraNoPosee;
  }

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

  public void setSegundoApellido(Textbox segundoApellido) {
    this.segundoApellido = segundoApellido;
  }

  public Textbox getSegundoApellido() {
    return segundoApellido;
  }

  public void setTercerApellido(Textbox tercerApellido) {
    this.tercerApellido = tercerApellido;
  }

  public Textbox getTercerApellido() {
    return tercerApellido;
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

  public void setDomicilioLabel(Label domicilioLabel) {
    this.domicilioLabel = domicilioLabel;
  }

  public Label getDomicilioLabel() {
    return domicilioLabel;
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
}
