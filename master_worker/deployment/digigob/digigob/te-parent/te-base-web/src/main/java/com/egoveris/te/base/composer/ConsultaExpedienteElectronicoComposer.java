package com.egoveris.te.base.composer;

import com.egoveris.ffdd.model.exception.NotFoundException;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.sharedsecurity.base.model.UsuarioReducido;
import com.egoveris.te.base.composer.EnvioComposer.EnvioExpedienteElectronicoOnNotifyWindowListener;
import com.egoveris.te.base.composer.genrico.BandBoxUsuarioComposer;
import com.egoveris.te.base.exception.external.TeRuntimeException;
import com.egoveris.te.base.model.AuditoriaDeConsultaDTO;
import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoConSuspensionDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.model.ExpedienteTrack;
import com.egoveris.te.base.model.HistorialOperacionDTO;
import com.egoveris.te.base.model.IngresoSolicitudExpedienteDTO;
import com.egoveris.te.base.model.Tarea;
import com.egoveris.te.base.model.TipoDocumentoDTO;
import com.egoveris.te.base.service.ConsultaExpedienteEnArchService;
import com.egoveris.te.base.service.FusionService;
import com.egoveris.te.base.service.HistorialOperacionService;
import com.egoveris.te.base.service.TipoDocumentoService;
import com.egoveris.te.base.service.TramitacionConjuntaService;
import com.egoveris.te.base.service.UsuariosSADEService;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoConSuspensionService;
import com.egoveris.te.base.service.expediente.ExpedienteElectronicoService;
import com.egoveris.te.base.service.iface.IAuditoriaService;
import com.egoveris.te.base.service.iface.IExpediente;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.ConstantesServicios;
import com.egoveris.te.base.util.SearchResultData;
import com.egoveris.te.base.util.Task2TareaTransformer;
import com.egoveris.te.base.util.Utils;
import com.egoveris.te.model.exception.ParametroIncorrectoException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dozer.DozerBeanMapper;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.util.CollectionUtils;
import org.zkoss.spring.SpringUtil;
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
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Groupbox;
import org.zkoss.zul.Include;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.ListModels;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Menupopup;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Popup;
import org.zkoss.zul.Window;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ConsultaExpedienteElectronicoComposer extends ValidarFormularioControladoComposer {

  private Logger logger = LoggerFactory.getLogger(ConsultaExpedienteElectronicoComposer.class);

  /**
  * 
  */
  private static final long serialVersionUID = -5748565613562274304L;

  private DozerBeanMapper mapper = new DozerBeanMapper();
  private Window parametrosConsultaWindow;
  private Window consultaExpedientesWindow;
  private Listbox consultaExpedientesList;
  private static String PENDIENTE_INICIACION = "Pendiente Iniciacion";
  private Task selectedTask = null;
  
  private UsuarioReducido usuarioSeleccionado;

  private IExpediente selectedExpedienteElectronico;

  @WireVariable(ConstantesServicios.PROCESS_ENGINE_SERVICE)
  private ProcessEngine processEngine;

  private List<IExpediente> expedienteElectronico;
  private AnnotateDataBinder binder;
  private String reparticionUsuario;
  @WireVariable(ConstantesServicios.USUARIOS_SADE_SERVICE)
  private UsuariosSADEService usuariosSADEService;

  @WireVariable(ConstantesServicios.AUDITORIA_SERVICE)
  private IAuditoriaService auditoriaService;

  @WireVariable(ConstantesServicios.EXP_ELECTRONICO_SERVICE)
  private ExpedienteElectronicoService expedienteElectronicoService;

  @WireVariable(ConstantesServicios.EXP_ELECTRONICO_SUSP_SERVICE)
  ExpedienteElectronicoConSuspensionService eeConSuspensionService;

  @WireVariable(ConstantesServicios.FUSION_SERVICE)
  private FusionService fusionService;

  @WireVariable(ConstantesServicios.TRAMITACION_CONJUNTA_SERVICE)
  TramitacionConjuntaService tramitacionConjuntaService;

  @Autowired
  private ConsultaExpedienteEnArchService consultaExpedienteEnArch;
  @Autowired
  private HistorialOperacionService historialOperacionService;
  private Window formularioControladoWindows;
  @Autowired
  private TipoDocumentoService tipoDocumentoService;
  @Autowired
  private String cargosArchivoDefinitivo;
  private Popup usuarioPopUp;
  private Groupbox idGroupbox;
  //private Combobox usuario;
  private Include	inclBandboxUsuario;
  private Usuario usuarioArchivador;

  static private String GUARDATEMPORAL = "GUARDATEMPORAL";

  public HistorialOperacionService getHistorialOperacionService() {
    return historialOperacionService;
  }

  public void setHistorialOperacionService(HistorialOperacionService historialOperacionService) {
    this.historialOperacionService = historialOperacionService;
  }

  public ConsultaExpedienteEnArchService getConsultaExpedienteEnArch() {
    return consultaExpedienteEnArch;
  }

  public void setConsultaExpedienteEnArch(ConsultaExpedienteEnArchService consultaExpedienteArch) {
    this.consultaExpedienteEnArch = consultaExpedienteArch;
  }

  public String getCargosArchivoDefinitivo() {
    return cargosArchivoDefinitivo;
  }

  public void setCargosArchivoDefinitivo(String cargosArchivoDefinitivo) {
    this.cargosArchivoDefinitivo = cargosArchivoDefinitivo;
  }

  public UsuariosSADEService getUsuariosSADEService() {
    return usuariosSADEService;
  }

  public void setUsuariosSADEService(UsuariosSADEService usuariosSADEService) {
    this.usuariosSADEService = usuariosSADEService;
  }

  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    comp.addEventListener(Events.ON_NOTIFY, new UpdateResultadosListener(this));
    comp.addEventListener(Events.ON_USER, new UpdateResultadosListener(this));
    comp.addEventListener(Events.ON_USER, new SolicitarArchivoListener(this));
    reparticionUsuario = (String) Executions.getCurrent().getDesktop().getSession()
        .getAttribute(ConstantesWeb.SESSION_USER_REPARTICION); 
    

		configurarBandboxUsuario(comp, true);
		
//    usuario.setModel(ListModels.toListSubModel(
//        new ListModelList(this.getUsuariosSADEService().getTodosLosUsuarios()),
//        new UsuariosComparatorConsultaExpediente(), 30));
    
    obtainTaskAndRedirect();
    this.binder = new AnnotateDataBinder();
//    binder.loadComponent(usuario);

  }
  
	private void configurarBandboxUsuario(Component component, boolean deshabilitado) {
    inclBandboxUsuario.setDynamicProperty(BandBoxUsuarioComposer.DISABLED_BANDBOX, deshabilitado);
    inclBandboxUsuario.setDynamicProperty(BandBoxUsuarioComposer.COMPONENTE_PADRE, component);
    inclBandboxUsuario.setSrc(BandBoxUsuarioComposer.SRC);
    
		component.addEventListener(BandBoxUsuarioComposer.ON_SELECT_USUARIO,
				new ConsultarEEListener());

    
	}

  @SuppressWarnings("unchecked")
  public void onCreate$consultaExpedientesWindow(Event event) throws InterruptedException {
    this.binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder", true);
    Map<String, Object> hm = (Map<String, Object>) Executions.getCurrent().getSession()
        .getAttribute(ConstantesWeb.PATHMAP);
    if (!CollectionUtils.isEmpty(hm)
        && hm.get(ConstantesWeb.KEYWORD).equals(ConstantesWeb.KEYWORD_EXPEDIENTES)) {
      Executions.getCurrent().getSession().removeAttribute(ConstantesWeb.PATHMAP);
      if (hm.get("ID1") != null) {
        this.buscarExpedienteParametrizado(hm);
      }
    }
  }

  public void buscarExpedienteParametrizado(Map<String, Object> hm) throws InterruptedException {
    String actuacion = (String) hm.get("actuacion");
    Integer anio = Integer.valueOf((String) hm.get("anio"));
    Integer numero = Integer.valueOf((String) hm.get("numero"));
    String reparticionActuacion = (String) hm.get("reparticionActuacion");
    String reparticionUsuario = (String) hm.get("reparticionUsuario");
    // Auditoria de consulta
    grabarAuditoriaDeConsulta(actuacion, anio, numero, reparticionActuacion, reparticionUsuario);
    ExpedienteElectronicoDTO result = new ExpedienteElectronicoDTO();
    try {
      result = (ExpedienteElectronicoDTO) expedienteElectronicoService
          .obtenerExpedienteElectronico(actuacion, anio, numero, reparticionUsuario);
    } catch (WrongValueException e) {

      throw new WrongValueException(Labels.getLabel("ee.errorConsultaExpedientes.sinResultados"));
    }

    if (result == null) {
      Messagebox.show(Labels.getLabel("ee.consultaExpedientes.sinResultados"),
          Labels.getLabel("ee.general.information"), Messagebox.OK, Messagebox.EXCLAMATION);
    } else {
      this.selectedExpedienteElectronico = (IExpediente) result;
      this.onVerExpediente();
    }
  }

  private void grabarAuditoriaDeConsulta(String tipoActuacion, Integer anio, Integer numero,
      String reparticionActuacion, String reparticionUsuario) {

    AuditoriaDeConsultaDTO auditoriaDeConsulta = new AuditoriaDeConsultaDTO();

    auditoriaDeConsulta.setTipoActuacion(tipoActuacion);
    auditoriaDeConsulta.setAnioActuacion(anio);
    auditoriaDeConsulta.setNumeroActuacion(numero);
    auditoriaDeConsulta.setReparticionActuacion(reparticionActuacion);
    auditoriaDeConsulta.setReparticionUsuario(reparticionUsuario);
    String usuario = (String) Executions.getCurrent().getSession()
        .getAttribute(ConstantesWeb.SESSION_USERNAME);
    auditoriaDeConsulta.setUsuario(usuario);
    auditoriaDeConsulta.setFechaConsulta(new Date());

    auditoriaService
        .grabarAuditoriaDeConsulta(mapper.map(auditoriaDeConsulta, AuditoriaDeConsultaDTO.class));
  }

  public void refreshResultado() {
    this.binder.loadComponent(this.consultaExpedientesList);
  }

  public void openModalWindow(String zulFile, String tipo) {
    if (this.parametrosConsultaWindow != null) {
      this.parametrosConsultaWindow.detach();
      // Le pasamos el mismo composer a la ventana modal para poder
      // comunicarla con la ventana principal
      Map<String, Object> values = new HashMap<String, Object>();
      values.put("parentComposer", this);
      values.put("tipo", tipo);

      if (tipo != null && tipo.equals(ConstantesWeb.ESTADO_GUARDA_TEMPORAL)) {
        if (zulFile.contains("consultaPorDomicilio.zul"))
          values.put("titulo", "Consulta por Domicilio Guarda Temporal");
        if (zulFile.contains("consultaGeneradosPorMi.zul"))
          values.put("titulo", "Expedientes creados por mí Guarda Temporal");
        if (zulFile.contains("consultaTramitadosPorMi.zul"))
          values.put("titulo", "Expedientes tramitados por mí Guarda Temporal");
        if (zulFile.contains("consultaGeneradosEnMiReparticion.zul"))
          values.put("titulo", "Documentos generados en mi Repartición Guarda Temporal");
      } else {
        if (zulFile.contains("consultaPorDomicilio.zul"))
          values.put("titulo", "Consulta por Domicilio");
        if (zulFile.contains("consultaGeneradosPorMi.zul"))
          values.put("titulo", "Expedientes creados por mí");
        if (zulFile.contains("consultaTramitadosPorMi.zul"))
          values.put("titulo", "Expedientes tramitados por mí");
        if (zulFile.contains("consultaGeneradosEnMiReparticion.zul"))
          values.put("titulo", "Documentos generados en mi Repartición");
      }

      this.parametrosConsultaWindow = (Window) Executions
          .createComponents(Utils.formatZulPath(zulFile), this.self, values);
      this.parametrosConsultaWindow.setParent(this.consultaExpedientesWindow);
      this.parametrosConsultaWindow.setPosition("center");
      this.parametrosConsultaWindow.setVisible(true);
      this.parametrosConsultaWindow.doModal();
    } else {
      Messagebox.show(
          Labels.getLabel("ee.consultaExpedienteComposer.msgbox.noPosibleInicializarVista"),
          Labels.getLabel("ee.consultaExpedienteComposer.msgbox.errorComunicacion"), Messagebox.OK,
          Messagebox.ERROR);
    }
  }

  public void onClick$generadosPorMiGT() {
    this.openModalWindow("consultas/consultaGeneradosPorMi.zul",
        ConstantesWeb.ESTADO_GUARDA_TEMPORAL);
  }

  public void onClick$tramitadosPorMiGT() {
    this.openModalWindow("consultas/consultaTramitadosPorMi.zul",
        ConstantesWeb.ESTADO_GUARDA_TEMPORAL);
  }

  public void onClick$busquedaPorDomicilioGT() {
    this.openModalWindow("consultas/consultaPorDomicilio.zul", ConstantesWeb.ESTADO_GUARDA_TEMPORAL);
  }

  public void onClick$generadosEnReparticionGT() {
    this.openModalWindow("consultas/consultaGeneradosEnMiReparticion.zul",
        ConstantesWeb.ESTADO_GUARDA_TEMPORAL);
  }

  public void onClick$porNumeroSADEGT() {
    this.openModalWindow("consultas/consultaPorNumeroSADE.zul", ConstantesWeb.ESTADO_GUARDA_TEMPORAL);
  }

  public void onClick$generadosPorMi() {
    this.openModalWindow("consultas/consultaGeneradosPorMi.zul", null);
  }

  public void onClick$tramitadosPorMi() {
    this.openModalWindow("consultas/consultaTramitadosPorMi.zul", null);
  }

  public void onClick$busquedaPorDomicilio() {
    this.openModalWindow("consultas/consultaPorDomicilio.zul", null);
  }

  public void onClick$generadosEnReparticion() {
    this.openModalWindow("consultas/consultaGeneradosEnMiReparticion.zul", null);
  }

  public void onClick$porNumeroSADE() {
    this.openModalWindow("consultas/consultaPorNumeroSADE.zul", null);
  }

  public void onClick$consultas() {
    this.setExpedienteElectronico(new ArrayList<IExpediente>());
    this.refreshResultado();
  }

  public void onVerExpediente() throws SuspendNotAllowedException, InterruptedException {
    Long idExpedienteElectronico = 0l;

    idExpedienteElectronico = this.selectedExpedienteElectronico.getId();
    ExpedienteElectronicoService expedienteElectronicoService = (ExpedienteElectronicoService) SpringUtil
        .getBean(ConstantesServicios.EXP_ELECTRONICO_SERVICE);

    ExpedienteElectronicoDTO e = expedienteElectronicoService
        .buscarExpedienteElectronico(this.selectedExpedienteElectronico.getId());
    Executions.getCurrent().getDesktop().setAttribute("idExpedienteElectronico",
        idExpedienteElectronico);

    // **********************************************************************************
    // ** JIRA: https://quark.everis.com/jira/browse/BISADE-4352
    // ** Se actualiza el workflow con idexpediente consultado antes de
    // ejecutar su trámitacion
    // **********************************************************************************
    if (e != null) {
      if (!"Guarda Temporal".equalsIgnoreCase(e.getEstado())
          && !ConstantesWeb.ESTADO_SOLICITUD_ARCHIVO.equals(e.getEstado())
          && !ConstantesWeb.ESTADO_ARCHIVO.equals(e.getEstado())) {
        this.processEngine.getExecutionService().setVariable(
            selectedExpedienteElectronico.getIdWorkflow(), "idExpedienteElectronico",
            this.selectedExpedienteElectronico.getId());
        this.processEngine.getExecutionService().setVariable(
            selectedExpedienteElectronico.getIdWorkflow(), "codigoExpediente",
            this.selectedExpedienteElectronico.getCodigoCaratula());
      }

      DetalleExpedienteElectronicoComposer.openInModal(this.self,
          this.selectedExpedienteElectronico.getCodigoCaratula());
    } else {
      Messagebox.show(
          Labels.getLabel("ee.consultaExpedienteComposer.msgbox.noPuedeVisualizarDetalle"),
          Labels.getLabel("ee.general.information"), Messagebox.OK, Messagebox.EXCLAMATION);
    }
  }

  public List<IExpediente> getExpedienteElectronico() {
    return expedienteElectronico;
  }

  public void setExpedienteElectronico(List<IExpediente> expedienteElectronico) {
    this.expedienteElectronico = expedienteElectronico;
  }

  public String getReparticionUsuario() {
    return reparticionUsuario;
  }

  public void setReparticionUsuario(String reparticionUsuario) {
    this.reparticionUsuario = reparticionUsuario;
  }

  public IExpediente getSelectedExpedienteElectronico() {
    return selectedExpedienteElectronico;
  }

  public void setSelectedExpedienteElectronico(IExpediente selectedExpedienteElectronico) {
    this.selectedExpedienteElectronico = selectedExpedienteElectronico;
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

    mapComp.put("selectedTask", this.selectedTask);

    this.formularioControladoWindows = (Window) Executions.createComponents(
        "/expediente/macros/formularioControladoEjecucionDesdeConsulta.zul", this.self, mapComp);
    this.formularioControladoWindows.doModal();
    this.formularioControladoWindows.setPosition("center");
    this.formularioControladoWindows.setClosable(true);
  }

  /**
   * Ejecutar tarea actual si expediente se encuentra asignado al usuario
   */
  public void onEjecutarTarea() throws InterruptedException {
    Boolean flagCv = false;
    ExpedienteElectronicoConSuspensionService eeConSuspensionService = (ExpedienteElectronicoConSuspensionService) SpringUtil
        .getBean(ConstantesServicios.EXP_ELECTRONICO_SUSP_SERVICE);
    ExpedienteElectronicoService expedienteElectronicoService = (ExpedienteElectronicoService) SpringUtil
        .getBean(ConstantesServicios.EXP_ELECTRONICO_SERVICE);
    ExpedienteElectronicoDTO eeAux = new ExpedienteElectronicoDTO();

    try {
      eeAux = expedienteElectronicoService.obtenerExpedienteElectronicoPorCodigo(
          this.selectedExpedienteElectronico.getCodigoCaratula());
    } catch (ParametroIncorrectoException e2) {

    }

    Boolean tieneCaratulaVariable = true;
    for (DocumentoDTO d : eeAux.getDocumentos()) {
      if (d.getIdTransaccionFC() != null) {
        tieneCaratulaVariable = true;
      }

    }

    if (eeAux.getDocumentos().size() < 2) {

      tieneCaratulaVariable = false;
    }
    if (!tieneCaratulaVariable) {
      try {
        this.selectedTask = processEngine.getTaskService().createTaskQuery()
            .executionId(eeAux.getIdWorkflow()).uniqueResult();
      } catch (Exception e) {
      }
    }
    if (this.selectedTask != null) {

      if (eeAux.getEstado().equals(PENDIENTE_INICIACION)
          || (eeAux.getTrata().getAcronimoDocumento() != null && !tieneCaratulaVariable)) {
        try {
          flagCv = true;
          String workflowId = this.selectedExpedienteElectronico.getIdWorkflow();
          String usuarioActual = (String) Executions.getCurrent().getSession()
              .getAttribute(ConstantesWeb.SESSION_USERNAME);
          IngresoSolicitudExpedienteDTO ingresoSolicitudExpediente = new IngresoSolicitudExpedienteDTO();
          String codigoExpedienteElectronico = "";
          ingresoSolicitudExpediente.setSolicitudExpediente(eeAux.getSolicitudIniciadora());
          ingresoSolicitudExpediente.setExpedienteElectronico(eeAux);
          codigoExpedienteElectronico = this.selectedExpedienteElectronico.getCodigoCaratula();
          if (validarCondicionesExpediente(codigoExpedienteElectronico)) {
            List<Task> tareasAsignadas = processEngine.getTaskService().createTaskQuery()
                .executionId(workflowId).assignee(usuarioActual).list();
            if (tareasAsignadas.size() == 1) {
              if (eeAux.getTrata().getAcronimoDocumento() != null) {
                crearFormularioControlado(ingresoSolicitudExpediente, consultaExpedientesWindow,
                    eeAux.getTrata());
              } else {
                Messagebox.show(
                    Labels
                        .getLabel("ee.consultaExpedienteComposer.msgbox.expedienteTrataInvalida"),
                    Labels.getLabel("ee.general.error"), Messagebox.OK, Messagebox.ERROR);
              }
            } else if (tareasAsignadas.size() == 0) {
              Messagebox.show(
                  Labels.getLabel(
                      "ee.consultaExpedientes.ejecucionTarea.error.tareaNoAsignadaUsuario"),
                  Labels.getLabel("ee.general.error"), Messagebox.OK, Messagebox.ERROR);
            }
          }

        } catch (Exception e) {
          logger.error("Se produjo un error creando formulario controlado de expediente: "
              + eeAux.getCodigoCaratula() + " - " + e.getMessage());
        }
      }
    }

    if (!flagCv) {
      ArrayList<ExpedienteElectronicoConSuspensionDTO> eeConSuspensions = null;
      Iterator eeConSuspensionsIt = null;
      String codigoExpedienteElectronico = "";
      ExpedienteElectronicoDTO ee = null;
      ExpedienteElectronicoConSuspensionDTO eecs = null;
      String workflowId = this.selectedExpedienteElectronico.getIdWorkflow();
      String usuarioActual = (String) Executions.getCurrent().getSession()
          .getAttribute(ConstantesWeb.SESSION_USERNAME);

      codigoExpedienteElectronico = (String) processEngine.getExecutionService()
          .getVariable(workflowId, "codigoExpediente");

      try {
        eeConSuspensions = new ArrayList<>(eeConSuspensionService.obtenerAllEEConSuspension());
      } catch (ParametroIncorrectoException e) {
        throw new TeRuntimeException(e.getMessage(), e);
      }

      if (!CollectionUtils.isEmpty(eeConSuspensions)) {
        eeConSuspensionsIt = eeConSuspensions.iterator();
        while (eeConSuspensionsIt.hasNext()) {
          eecs = (ExpedienteElectronicoConSuspensionDTO) eeConSuspensionsIt.next();
          try {
            ee = expedienteElectronicoService
                .obtenerExpedienteElectronicoPorCodigo(codigoExpedienteElectronico);

            if (ee.getId().equals(eecs.getEe().getId())) {
              Messagebox.show(
                  Labels.getLabel("ee.consultaExpedientes.ejecucionTarea.info.suspension.tv"),
                  Labels.getLabel("ee.consultaExpedienteComposer.msgbox.informacion"),
                  Messagebox.OK, Messagebox.INFORMATION);
              return;
            }
          } catch (ParametroIncorrectoException e) {
            throw new TeRuntimeException(e.getMessage(), e);
          }
        }
      }

      try {
        codigoExpedienteElectronico = this.selectedExpedienteElectronico.getCodigoCaratula();
        if (validarCondicionesExpediente(codigoExpedienteElectronico)) {
          List<Task> tareasAsignadas = processEngine.getTaskService().createTaskQuery()
              .executionId(workflowId).assignee(usuarioActual).list();
          if (tareasAsignadas.size() == 1) {
            // TODO Utilizar una sola estrategia para enviar los
            // parámetros en el request...
            Task tareaAsignada = tareasAsignadas.get(0);
            Map<String, Object> variables = new HashMap<String, Object>();
            Executions.getCurrent().getDesktop().setAttribute("selectedTask", tareaAsignada);
            // **********************************************************************************
            // ** JIRA:
            // https://quark.everis.com/jira/browse/BISADE-4352
            // ** Se actualiza el workflow con idexpediente
            // consultado antes de ejecutar su trámitacion
            // **********************************************************************************
            this.processEngine.getExecutionService().setVariable(tareaAsignada.getExecutionId(),
                "idExpedienteElectronico", this.selectedExpedienteElectronico.getId());
            this.processEngine.getExecutionService().setVariable(tareaAsignada.getExecutionId(),
                "codigoExpediente", codigoExpedienteElectronico);

            variables.put("Trata", this.selectedExpedienteElectronico.getTrata());
            Window taskView = (Window) Executions
                .createComponents(tareaAsignada.getFormResourceName(), this.self, variables);
            taskView.setParent(this.self);
            taskView.setPosition("center,top");
            taskView.setVisible(true);
            taskView.setClosable(true);
            taskView.doModal();
          } else if (tareasAsignadas.isEmpty()) {
            Messagebox.show(
                Labels.getLabel(
                    "ee.consultaexpedientes.ejecucionTarea.error.tareaNoAsignadaUsuario"),
                Labels.getLabel("ee.general.error"), Messagebox.OK, Messagebox.ERROR);
          }
        }
      } catch (Exception e) {
        Messagebox.show(
            Labels.getLabel("ee.consultaExpedienteComposer.msgbox.erroEjecucion") + e.getMessage(),
            Labels.getLabel("ee.general.error"), Messagebox.OK, Messagebox.ERROR);
        LoggerFactory.getLogger(ConsultaExpedienteElectronicoComposer.class).error(
            "Error al ejecutar expediente ID " + this.selectedExpedienteElectronico.getId(), e);
      }
    }
  }

  /**
   * Valida las condiciones del expediente. - Si tiene tareas en paralelo. - Si
   * es un expediente hijo en tramitación Conjunta. - Si es un expediente en
   * proceso de fusión.
   * 
   * @return
   * @throws InterruptedException
   */
  private boolean validarCondicionesExpediente(String codigoExpedienteElectronico)
      throws InterruptedException {
    boolean enCondiciones = false;
    String mensajeError = null;
    FusionService fusionService = (FusionService) SpringUtil.getBean(ConstantesServicios.FUSION_SERVICE);
    TramitacionConjuntaService tramitacionConjuntaService = (TramitacionConjuntaService) SpringUtil
        .getBean("tramitacionConjuntaServiceImpl");

    if (this.selectedExpedienteElectronico.getEstado()
        .equalsIgnoreCase(ConstantesWeb.ESTADO_PARALELO)) {
      mensajeError = Labels
          .getLabel("ee.consultaExpedientes.ejecucionTarea.error.tramitacionParalela");
    } else if (tramitacionConjuntaService
        .esExpedienteEnProcesoDeTramitacionConjunta(codigoExpedienteElectronico)) {
      String codigoExpedienteElectronicoCabecera = tramitacionConjuntaService
          .obtenerExpedienteElectronicoCabecera(codigoExpedienteElectronico);
      mensajeError = Labels.getLabel("ee.inbox.error.expedienteEnTramitacionConjunta",
          new String[] { codigoExpedienteElectronico, codigoExpedienteElectronicoCabecera });
    } else if (fusionService.esExpedienteEnProcesoDeFusion(codigoExpedienteElectronico)) {
      String codigoExpedienteElectronicoCabecera = tramitacionConjuntaService
          .obtenerExpedienteElectronicoCabecera(codigoExpedienteElectronico);
      mensajeError = Labels.getLabel("ee.inbox.error.expedienteEnFusion",
          new String[] { codigoExpedienteElectronico, codigoExpedienteElectronicoCabecera });
    } else {
      enCondiciones = true;
    }
    if (!enCondiciones) {
      Messagebox.show(mensajeError, Labels.getLabel("Messagebox.show"), Messagebox.OK,
          Messagebox.ERROR);
    }
    return enCondiciones;
  }

  /**
   * Adquirir tarea actual si ésta se encuentra en el buzon grupal del usuario
   * actual.
   */
  public void onAdquirirTarea() throws InterruptedException {
    ExpedienteElectronicoConSuspensionService eeConSuspensionService = (ExpedienteElectronicoConSuspensionService) SpringUtil
        .getBean(ConstantesServicios.EXP_ELECTRONICO_SUSP_SERVICE);
    ExpedienteElectronicoService expedienteElectronicoService = (ExpedienteElectronicoService) SpringUtil
        .getBean(ConstantesServicios.EXP_ELECTRONICO_SERVICE);
    ArrayList<ExpedienteElectronicoConSuspensionDTO> eeConSuspensions = null;
    Iterator eeConSuspensionsIt = null;
    String codigoExpedienteElectronico = "";
    ExpedienteElectronicoDTO ee = null;
    ExpedienteElectronicoConSuspensionDTO eecs = null;
    String workflowId = this.selectedExpedienteElectronico.getIdWorkflow();
    String usuarioActual = (String) Executions.getCurrent().getSession()
        .getAttribute(ConstantesWeb.SESSION_USERNAME);

    codigoExpedienteElectronico = (String) processEngine.getExecutionService()
        .getVariable(workflowId, "codigoExpediente");

    try {
      eeConSuspensions = new ArrayList<>(eeConSuspensionService.obtenerAllEEConSuspension());
    } catch (ParametroIncorrectoException e) {
      throw new TeRuntimeException(e.getMessage(), e);
    }

    if (!CollectionUtils.isEmpty(eeConSuspensions)) {
      eeConSuspensionsIt = eeConSuspensions.iterator();
      while (eeConSuspensionsIt.hasNext()) {
        eecs = (ExpedienteElectronicoConSuspensionDTO) eeConSuspensionsIt.next();
        try {
          ee = expedienteElectronicoService
              .obtenerExpedienteElectronicoPorCodigo(codigoExpedienteElectronico);

          if (ee.getId().equals(eecs.getEe().getId())) {
            Messagebox.show(
                Labels.getLabel("ee.consultaExpedientes.ejecucionTarea.info.suspension.tv"),
                Labels.getLabel("ee.consultaExpedienteComposer.msgbox.informacion"), Messagebox.OK,
                Messagebox.INFORMATION);
            return;
          }
        } catch (ParametroIncorrectoException e) {
          throw new TeRuntimeException(e.getMessage(), e);
        }
      }
    }

    try {
      List<Task> tareasAdquirir = processEngine.getTaskService().createTaskQuery()
          .executionId(workflowId).candidate(usuarioActual).list();
      if (tareasAdquirir.size() == 1) {
        this.processEngine.getTaskService().takeTask(tareasAdquirir.get(0).getId(), usuarioActual);
        Executions.getCurrent().getDesktop().setAttribute("codigoExpediente",
            codigoExpedienteElectronico);
        Window confirmacionEjecutarTareaView = (Window) Executions
            .createComponents("/consultas/confirmacionEjecucionTarea.zul", this.self, null);
        confirmacionEjecutarTareaView.setParent(this.self);
        confirmacionEjecutarTareaView.setPosition("center,top");
        confirmacionEjecutarTareaView.setVisible(true);
        confirmacionEjecutarTareaView.setClosable(true);
        confirmacionEjecutarTareaView.doModal();
      } else if (tareasAdquirir.size() == 0) {
        Messagebox.show(
            Labels.getLabel("ee.consultaexpedientes.ejecucionTarea.error.tareaNoAsignadaGrupo"),
            Labels.getLabel("ee.general.error"), Messagebox.OK, Messagebox.ERROR);
      }
    } catch (Exception e) {
      String messageAux = e.getMessage();
      String message = messageAux.substring(21);
      Messagebox.show(Labels.getLabel("ee.buzonGrupal.errorTareaTomada") + message,
          Labels.getLabel("ee.buzonGrupal.tituloTareaTomada"), Messagebox.OK, Messagebox.ERROR);
    }
  }

  // la invocacion de este metodo la invoca el renderer del composer
  public void onVerPopUp() {

    Menupopup menu = new Menupopup();
    Menuitem menuitemAdquirirTarea = new Menuitem();
    Menuitem menuitemEjecutarTarea = new Menuitem();

    menuitemEjecutarTarea.setImage("/imagenes/play.png");
    menuitemEjecutarTarea.setLabel("Ejecutar Tarea");
    menuitemEjecutarTarea.addForward("onClick", this.self, "onEjecutarTarea");

    menuitemAdquirirTarea.setImage("/imagenes/egovReturn.png");
    menuitemAdquirirTarea.setLabel("Adquirir Tarea");
    menuitemAdquirirTarea.addForward("onClick", this.self, "onAdquirirTarea");

    menu.appendChild(menuitemEjecutarTarea);
    menu.appendChild(menuitemAdquirirTarea);
    menu.setParent(this.consultaExpedientesList.getSelectedItem().getLastChild());

    menu.open(menu.getParent());

  }

  private boolean validarCargos(Usuario usuario, String cargosArchivoDefinitivo) {
    String[] cargos = cargosArchivoDefinitivo.split(",");
    for (String cargo : cargos) {
      if (cargo.trim().equalsIgnoreCase(usuario.getCargo()))
        return true;
    }
    return false;
  }

  public void onSolicitarArchivo() throws InterruptedException, ParametroIncorrectoException {
    pruguntarUsuario();
  }

  private void pruguntarUsuario() {
  	this.cleanBandboxUsuario();
    //this.usuario.setValue("");
    this.usuarioPopUp.open(idGroupbox);
    this.binder.loadComponent(this.usuarioPopUp);
  }

  private boolean tienePermisoArchivador(Usuario usuario) {

    for (GrantedAuthority auth : usuario.getAuthorities()) {
      if (ConstantesWeb.PERMISO_ARCHIVADOR.equals(auth.getAuthority())) {
        return true;
      }
    }
    return false;
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

  public void onClick$enviarUsuario() {

    if (usuarioSeleccionado == null) {
    	mensajeValidadorBandboxUsuario("Debe seleccionar un Usuario.");
     // throw new WrongValueException(this.usuario, "Debe seleccionar un Usuario.");
    }

    //item = (Usuario) this.usuario.getSelectedItem().getValue();
    usuarioArchivador = usuariosSADEService.getDatosUsuario(usuarioSeleccionado.getUsername());

    if (usuarioArchivador == null) {
    	this.mensajeValidadorBandboxUsuario("Debe seleccionar un usuario válido.");
      //throw new WrongValueException(this.usuario, "Debe seleccionar un usuario válido.");
    }
    if (!tienePermisoArchivador(usuarioArchivador)) {
      this.mensajeValidadorBandboxUsuario("Debe seleccionar un usuario con permiso Archivador.");
    	//throw new WrongValueException(this.usuario,
      //    "Debe seleccionar un usuario con permiso Archivador.");
    }
    try {
      preguntarEnvioArchivo(selectedExpedienteElectronico.getCodigoCaratula());
    } catch (InterruptedException e) {

    }
  }

  private void preguntarEnvioArchivo(String codCaratula) throws InterruptedException {
    String[] param = new String[1];
    param[0] = codCaratula;
    Messagebox.show(Labels.getLabel("ee.envio.SolicitudArchivo.question.value", param),
        Labels.getLabel("ee.envio.SolicitudArchivo.question"), Messagebox.YES | Messagebox.NO,
        Messagebox.QUESTION, new org.zkoss.zk.ui.event.EventListener() {
          public void onEvent(Event evt) throws InterruptedException {
            switch (((Integer) evt.getData()).intValue()) {
            case Messagebox.YES:
              // Esto se coloca para bloquear la ventana de atras
              // mientras se procesa el pase
              mostrarForegroundBloqueante();
              Events.echoEvent("onUser", self, "archivar");

              break;

            case Messagebox.NO:
              break;
            }
          }
        });
  }

  public void archivar() throws InterruptedException, ParametroIncorrectoException {
    try {
      String usuarioActual = (String) Executions.getCurrent().getSession()
          .getAttribute(ConstantesWeb.SESSION_USERNAME);
      Usuario usuario = usuariosSADEService.obtenerUsuarioActual();
      List<HistorialOperacionDTO> list = historialOperacionService
          .buscarHistorialporExpediente(selectedExpedienteElectronico.getId());
      // Collections.sort(list, new ComparadorHistorial());
      String reparticion = obtenerReparticionDe(list.get(1).getDestinatario());

      // Valido si tiene el cargo necesario para archivar el EE
      if (!validarCargos(usuario, getCargosArchivoDefinitivo())) {
        Messagebox.show(Labels.getLabel("ee.solicitudArchivoCargo.error"),
            Labels.getLabel("ee.general.information"), Messagebox.OK, Messagebox.EXCLAMATION);
        return;
      }

      if (!reparticion.equals(usuario.getCodigoReparticion()) && !usuario.getCodigoReparticion()
          .equals(selectedExpedienteElectronico.getCodigoReparticionUsuario())) {
        alert(Labels.getLabel("ee.solicitudArchivo.error"));
        return;
      }

      boolean bool = consultaExpedienteEnArch.enviarASolicitudArchivo(
          selectedExpedienteElectronico.getCodigoCaratula(), usuarioArchivador.getUsername(),
          usuarioActual);
      if (!bool) {
        Messagebox.show(Labels.getLabel("ee.consultaExpElecComp.msgbox.errorProsSolic"),
            Labels.getLabel("ee.consultaExpElecComp.msgbox.errorComun"), Messagebox.OK,
            Messagebox.ERROR);
      } else {
        logger.info("Se ha solicitado el envio a archivo del expediente: "
            + selectedExpedienteElectronico.getCodigoCaratula() + "de manera exitosa");
        alert("Se ha solicitado el envio a Archivo del expediente: "
            + selectedExpedienteElectronico.getCodigoCaratula());
        expedienteElectronico.remove(selectedExpedienteElectronico);
        ExpedienteElectronicoDTO ee = expedienteElectronicoService
            .obtenerExpedienteElectronicoPorCodigo(
                selectedExpedienteElectronico.getCodigoCaratula());
        expedienteElectronico.add((IExpediente) ee);
        refreshResultado();
      }
    } finally {
      Clients.clearBusy();
    }
  }

  private void mostrarForegroundBloqueante() {
    Clients.showBusy(Labels.getLabel("ee.tramitacion.enviarExpedienteASolicitudArchivo"));
  }

  private String obtenerReparticionDe(String destinatario) {
    if (destinatario.contains("-")) {
      return destinatario.split("-")[0];
    }
    Usuario usuario = usuariosSADEService.getDatosUsuario(destinatario);
    if (usuario != null) {
      return usuario.getCodigoReparticion();
    }
    // si no entra a los if es porque el destinatario ya es una reparticion
    return destinatario;
  }

  public void mostrarMensajeAdquisicion() {

  }

  public void onVerHistorialExpedienteTrack() throws InterruptedException {
    try {
      String repBusqueda = (String) Executions.getCurrent().getSession()
          .getAttribute("repActuacionBusqueda");
      String repUsuarioBusqueda = (String) Executions.getCurrent().getSession()
          .getAttribute("repUsuarioBusqueda");
      Map<String, Object> map = new HashMap<>();
      map.put("expedienteTrack", selectedExpedienteElectronico);
      map.put("reparticionBusqueda", repBusqueda);
      map.put("reparticionUsuarioBusqueda", repUsuarioBusqueda);
      Window detExp = (Window) Executions
          .createComponents("/expediente/consultaMovimientoExpedienteTrack.zul", this.self, map);
      detExp.setPosition("center");
      detExp.setWidth("100%");
      detExp.doModal();
    } catch (Exception e) {
      logger.error(e.getMessage());
      Messagebox.show(Labels.getLabel("ee.consultaExpElecComp.msgbox.errorVisualExp"),
          Labels.getLabel("ee.consultaExpElecComp.msgbox.errorVisual"), Messagebox.OK,
          Messagebox.ERROR);
    }
  }

  public void alert(String msg) {
    super.alert(msg);
  }

  public void onVerDetalleExpedienteTrack() throws InterruptedException {

    try {
      Map<String, IExpediente> map = new HashMap<>();
      map.put("expedienteTrack", selectedExpedienteElectronico);
      Window detExp = (Window) Executions
          .createComponents("/expediente/expedienteTrackDetalle.zul", this.self, map);
      detExp.setPosition("center");
      detExp.setWidth("90%");
      detExp.doModal();
    } catch (Exception e) {
      logger.error(e.getMessage());
      Messagebox.show(Labels.getLabel("ee.consultaExpElecComp.msgbox.errorVisualExp"),
          Labels.getLabel("ee.consultaExpElecComp.msgbox.errorVisual"), Messagebox.OK,
          Messagebox.ERROR);
    }
  }

  public ExpedienteElectronicoService getExpedienteElectronicoService() {
    return expedienteElectronicoService;
  }

  public Window getFormularioControladoWindows() {
    return formularioControladoWindows;
  }

  public void setExpedienteElectronicoService(
      ExpedienteElectronicoService expedienteElectronicoService) {
    this.expedienteElectronicoService = expedienteElectronicoService;
  }

  public void setFormularioControladoWindows(Window formularioControladoWindows) {
    this.formularioControladoWindows = formularioControladoWindows;
  }

  public TipoDocumentoService getTipoDocumentoService() {
    return tipoDocumentoService;
  }

  public void setTipoDocumentoService(TipoDocumentoService tipoDocumentoService) {
    this.tipoDocumentoService = tipoDocumentoService;
  }

	public UsuarioReducido getUsuarioSeleccionado() {
		return usuarioSeleccionado;
	}

	public void setUsuarioSeleccionado(UsuarioReducido usuarioSeleccionado) {
		this.usuarioSeleccionado = usuarioSeleccionado;
	}

	public void obtainTaskAndRedirect() throws InterruptedException {
		if (Executions.getCurrent().getParameter("taskId") != null) {
			String idTask = Executions.getCurrent().getParameter("taskId");
			
			Task jbpm4Task = null;
			Tarea transformedTask = null;
			this.selectedExpedienteElectronico = expedienteElectronicoService.buscarExpedienteElectronicoByIdTask(idTask);
			
			if (selectedExpedienteElectronico != null && selectedExpedienteElectronico.getUsuarioCreador()!=Executions.getCurrent().getSession().getAttribute(ConstantesWeb.SESSION_USERNAME)
			        .toString()) {
				if (Executions.getCurrent().getParameter("goBackDb") != null
						&& Executions.getCurrent().getParameter("goBackDb").equals(Boolean.TRUE.toString())) {
					Executions.getCurrent().getSession().setAttribute("goBackDb", true);
				}
				onVerExpediente();
			}
			else {
				Messagebox.show(Labels.getLabel("ee.consultaExpElecComp.msgbox.errorTarea"),
						Labels.getLabel("ee.general.information"), Messagebox.OK, Messagebox.EXCLAMATION);
			}
		}
	}
	
	public class ConsultarEEListener implements EventListener<Event>{

		@Override
		public void onEvent(Event event) throws Exception {
			if(event.getName().equals(BandBoxUsuarioComposer.ON_SELECT_USUARIO)) {
				setUsuarioSeleccionado((UsuarioReducido) event.getData());
			}
		}
		
	}
	
}

class SolicitarArchivoListener implements EventListener {

  private ConsultaExpedienteElectronicoComposer composer;

  public SolicitarArchivoListener(ConsultaExpedienteElectronicoComposer composer) {
    this.composer = composer;
  }

  public void onEvent(Event event) throws Exception {
    if (event.getName().equals(Events.ON_USER)) {
      if (event.getData() != null) {
        if (event.getData().equals("archivar")) {
          this.composer.archivar();
        }
      }
    }
  }
}

class UpdateResultadosListener implements EventListener {
  private ConsultaExpedienteElectronicoComposer composer;

  public UpdateResultadosListener(ConsultaExpedienteElectronicoComposer composer) {
    this.composer = composer;
  }

  public void onEvent(Event event) throws Exception {
    if (event.getName().equals(Events.ON_NOTIFY)) {
      if (event.getData() != null) {
        if (event.getData() instanceof SearchResultData) {
          SearchResultData data = (SearchResultData) event.getData();
          List<IExpediente> resultado = data.toInterfaz();
          this.composer.setExpedienteElectronico(resultado);

        } else if (event.getData() instanceof Boolean) {
          this.composer.setExpedienteElectronico(new ArrayList<IExpediente>());
        }
        if (event.getData() instanceof Map) {
          Map<String, Object> dataSender = (Map<String, Object>) event.getData();
          String funcion = (String) dataSender.get("operacion");
          if (funcion != null
              && funcion.equalsIgnoreCase(ConstantesWeb.CONFIRMACION_EJECUCION_TAREA)) {
            this.composer.onEjecutarTarea();
          }

          String origen = (String) dataSender.get("origen");
          if (StringUtils.equals(origen, ConstantesWeb.COMPOSER)) {
            Boolean metodoOrigen = (Boolean) dataSender.get("metodoOrigen");

            if ((Boolean) dataSender.get("fulfilledForm")) {
              String codigoExpedienteElectronico = composer.getSelectedExpedienteElectronico()
                  .getCodigoCaratula();
              composer.alert("Se completó el formulario del expediente: "
                  + codigoExpedienteElectronico + " de manera exitosa");
            }

          }
        }
        if (event.getData() instanceof ExpedienteTrack) {
          IExpediente ie = (IExpediente) event.getData();
          List<IExpediente> list = new ArrayList<IExpediente>();
          list.add(ie);
          this.composer.setExpedienteElectronico(list);
        }
      }
      this.composer.refreshResultado();
    }
    if (event.getName().equals(Events.ON_CANCEL)) {

      this.composer.refreshResultado();
    }
    if (event.getName().equals(Events.ON_CLOSE)) {

      if (event.getData() != null) {
        Map<String, Object> map = (Map<String, Object>) event.getData();

        String origen = (String) map.get("origen");
        if (StringUtils.equals(origen, ConstantesWeb.COMPOSER)) {
          Boolean metodoOrigen = (Boolean) map.get("metodoOrigen");

          if ((Boolean) map.get("beforeExecuteTask")) {
            if ((Boolean) map.get("fulfilledForm")) {

              String codigoExpedienteElectronico = composer.getSelectedExpedienteElectronico()
                  .getCodigoCaratula();
              composer.alert("Se completó el formulario del expediente: "
                  + codigoExpedienteElectronico + " de manera exitosa");
            }

          }
        }
      }

    }
  }

}

class UsuariosComparatorConsultaExpediente implements Comparator {
  public int compare(Object o1, Object o2) {
    String userText = null;

    if (o1 instanceof String) {
      userText = ((String) o1).trim();
    }

    if (userText != null && (o2 instanceof Usuario) && (StringUtils.isNotEmpty(userText))
        && (userText.length() > 2)) {
      Usuario dub = (Usuario) o2;
      String nomApe = dub.getNombreApellido();
      String userName = dub.getUsername();

      if (nomApe != null && userName != null) {
        if (nomApe.toLowerCase().contains(userText.toLowerCase()) || userName.toLowerCase().contains(userText.toLowerCase())) {
          return 0;
        }
      }
    }

    return 1;
  }
  
  
}