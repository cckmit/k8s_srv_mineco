package com.egoveris.deo.web.satra.consulta;

import com.egoveris.deo.base.service.ComunicacionService;
import com.egoveris.deo.base.service.TareaJBPMService;
import com.egoveris.deo.model.model.ComunicacionDTO;
import com.egoveris.deo.model.model.MacroEventData;
import com.egoveris.deo.model.model.TareaBusquedaDTO;
import com.egoveris.deo.util.Constantes;
import com.egoveris.deo.web.satra.GEDOGenericForwardComposer;
import com.egoveris.deo.web.satra.InboxComposer;
import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jbpm.api.ProcessEngine;
import org.jbpm.api.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.text.DateFormats;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.SuspendNotAllowedException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Div;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;
import org.zkoss.zul.event.ZulEvents;
import org.zkoss.zul.ext.Paginal;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ConsultaComunicacionesComposer extends GEDOGenericForwardComposer {

  private static final long serialVersionUID = -5748565613562274304L;

  private static Logger logger = LoggerFactory.getLogger(ConsultaComunicacionesComposer.class);

  private static final String DESCENDING_ = "descending";
  private static final String CAMPO_INICIAL = "fechaAlta";
  private static final String NATURAL_ = "natural";
  private static final String ASCENDING_ = "ascending";

  private Window consultarCoWindow;
  private Window detalleComunicacionView;
  private Window historialCoWindowView;
  private Window consultaTareaCOWindow;
  private Listbox comunicationSendListBox;
  private Listbox comunicationsReceivedListBox;
  private Listbox comunicacionesDestinatario;
  private Listbox consultaTareasList;
  private Listbox listaCaratula;
  private Toolbarbutton listaTodasTareas;
  private Div resultadoBusqueda;
  private Div divCaratula;
  private Div divDestinatarios;
  private Div divListadoTareas;
  private AnnotateDataBinder consultarCoBinder;
  private transient Paginal coEnviadasPaginator;
  private transient Paginal coRecibidasPaginator;
  private transient Paginal coDestinatarioPaginator;
  private transient Paginal consultaTareasPaginator;
  private ComunicacionDTO selectedComunicationSend = null;
  private ComunicacionDTO selectedComunication;
  private Date fechaDesde;
  private Date fechaHasta;
  private String usuarioTareaDestino;
  private String docBusquedaTarea;
  private String tipoTareaBusqueda;

  private Window taskView;

  private String usuarioActual;
  private List<ComunicacionDTO> comunicationsSend;
  private List<ComunicacionDTO> comunicacionCaratula;
  private List<TareaBusquedaDTO> tareas;
  @WireVariable("comunicacionServiceImpl")
  private ComunicacionService comunicacionService;
  private String referencia = null;
  private String tipoBusqueda;
  private String usuarioDestino;

  private TareaBusquedaDTO tareaSeleccionada;
  @WireVariable("tareaJBPMServiceImpl")
  private TareaJBPMService tareaJBPMService;
  @WireVariable("processEngineImpl")
  private ProcessEngine processEngine;

  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    this.consultarCoBinder = new AnnotateDataBinder(consultarCoWindow);
    comp.addEventListener(Events.ON_NOTIFY, new ConsultaComunicacionListener(this));
    this.tareas = new ArrayList<>();
    this.usuarioActual = (String) Executions.getCurrent().getDesktop().getSession()
        .getAttribute(Constantes.SESSION_USERNAME);
    asigneListener();
    this.consultarCoBinder.loadAll();
  }

  public void onClick$buscarPorFecha() {
    this.openModalWindow("/co/consultas/consultaPorFechaCo.zul");
  }

  public void onClick$buscarPorCaratula() {
    this.openModalWindow("/co/consultas/consultaPorCaratulaCo.zul");
  }

  public void onClick$buscarPorRereferncia() {
    this.openModalWindow("/co/consultas/consultaPorReferenciaCo.zul");
  }

  public void onClick$buscarPorDestinatario() {
    this.openModalWindow("/co/consultas/consultaPorDestinatarioCo.zul");
  }

  public void onClick$buscarTareas() {

    iniciarListeners(this.consultaTareasList);
    Map<String, Object> parametrosConsulta = new HashMap<String, Object>();
    cargarParamtrosConsulta(parametrosConsulta, DESCENDING_, CAMPO_INICIAL);
    this.consultaTareaCOWindow = (Window) Executions.createComponents(
        "co/consultas/consultaPorFechaTareaCo.zul", this.self, parametrosConsulta);
    this.consultaTareaCOWindow.setPosition("center");
    this.consultaTareaCOWindow.doModal();
    flechitasSort(1, false, this.consultaTareasList);
  }

  private void iniciarListeners(Listbox listbox) {

    getConsultaTareasPaginator().addEventListener(ZulEvents.ON_PAGING, new PagingSortListener());
    inicializarSorts(listbox);
    flechitasSort(1, false, this.consultaTareasList);
  }

  private void inicializarSorts(Listbox listbox) {

    Listhead listhead = listbox.getListhead();
    List<?> list = listhead.getChildren();
    for (Object object : list) {
      if (object instanceof Listheader) {
        Listheader lheader = (Listheader) object;
        if ((lheader.getSortAscending() != null || lheader.getSortDescending() != null))
          lheader.addEventListener(Events.ON_SORT, new PagingSortListener());
      }
    }
  }

  private void flechitasSort(int indexColumnSort, boolean ascending, Listbox consultaList) {
    Listhead listhead = consultaList.getListhead();
    List<?> list = listhead.getChildren();
    for (Object object : list) {
      if (object instanceof Listheader) {
        Listheader lheader = (Listheader) object;
        if (lheader.getColumnIndex() == indexColumnSort) {
          if (ascending) {
            lheader.setSortDirection(ASCENDING_);
          } else {
            lheader.setSortDirection(DESCENDING_);
          }
        } else {
          lheader.setSortDirection(NATURAL_);
        }
      }
    }
  }

  private void buscarTareas(String orden, String criterio, int columnOrden, boolean isAscending) {

    Map<String, Object> parametrosConsulta = new HashMap<String, Object>();
    cargarParamtrosConsulta(parametrosConsulta, orden, criterio);
    parametrosConsulta.put("fechaDesde", this.fechaDesde);
    parametrosConsulta.put("fechaHasta", this.fechaHasta);
    parametrosConsulta.put("tipoTarea", getTipoTareaBusqueda());
    parametrosConsulta.put("usuarioDestino", getUsuarioTareaDestino());
    parametrosConsulta.put("tipoDocumento", getDocBusquedaTarea());
    flechitasSort(columnOrden, isAscending, this.consultaTareasList);
    this.tareas = this.tareaJBPMService.buscarTareasPorUsuarioInvolucrado(parametrosConsulta);
    this.consultaTareasPaginator.setTotalSize(
        this.tareaJBPMService.cantidadTotalTareasPorUsuarioInvolucrado(parametrosConsulta));
    this.consultarCoBinder.loadComponent(this.consultaTareasList);
  }

  private void cargarParamtrosConsulta(Map<String, Object> parametrosConsulta, String orden,
      String criterio) {

    parametrosConsulta.put("usuario", this.usuarioActual);
    parametrosConsulta.put("esComunicable", true);
    parametrosConsulta.put("inicioCarga",
        this.consultaTareasPaginator.getActivePage() * this.consultaTareasPaginator.getPageSize());
    parametrosConsulta.put("cantidadResultados", this.consultaTareasPaginator.getPageSize());
    parametrosConsulta.put("orden", orden);
    parametrosConsulta.put("criterio", criterio);
  }

  public void openModalWindow(String zulFile) {
    Window parametrosConsultaWindow = (Window) Executions.createComponents(zulFile, this.self,
        null);
    parametrosConsultaWindow.setParent(this.consultarCoWindow);
    parametrosConsultaWindow.setPosition("center");
    parametrosConsultaWindow.setVisible(true);

    parametrosConsultaWindow.doModal();
  }

  private void asigneListener() {
    this.coEnviadasPaginator.addEventListener("onPaging", new ConsultaComunicacionListener(this));
    this.coRecibidasPaginator.addEventListener("onPaging", new ConsultaComunicacionListener(this));
    this.coDestinatarioPaginator.addEventListener("onPaging",
        new ConsultaComunicacionListener(this));
  }

  private void loadParametersSend(Map<String, Object> parametrosConsulta) {
    Integer tamanoPaginacion = this.coEnviadasPaginator.getPageSize();
    Integer inicioCarga = this.coEnviadasPaginator.getActivePage()
        * this.coEnviadasPaginator.getPageSize();

    parametrosConsulta.put("inicioCarga", inicioCarga);
    parametrosConsulta.put("tamanoPaginacion", tamanoPaginacion);
  }

  private void loadParametersReceived(Map<String, Object> parametrosConsulta) {
    Integer tamanoPaginacion = this.coRecibidasPaginator.getPageSize();
    Integer inicioCarga = this.coRecibidasPaginator.getActivePage()
        * this.coRecibidasPaginator.getPageSize();

    parametrosConsulta.put("inicioCarga", inicioCarga);
    parametrosConsulta.put("tamanoPaginacion", tamanoPaginacion);
  }

  private void loadParametersDestinatario(Map<String, Object> parametrosConsulta) {
    Integer tamanoPaginacion = this.coDestinatarioPaginator.getPageSize();
    Integer inicioCarga = this.coDestinatarioPaginator.getActivePage()
        * this.coDestinatarioPaginator.getPageSize();

    parametrosConsulta.put("inicioCarga", inicioCarga);
    parametrosConsulta.put("tamanoPaginacion", tamanoPaginacion);
  }

  public void reloadComunicationSend() {
    Map<String, Object> parametrosConsulta = new HashMap<String, Object>();
    loadParametersSend(parametrosConsulta);
    this.comunicationsSend = comunicacionService.buscarComunicacionPorFechaEnviados(
        this.fechaDesde, this.fechaHasta, usuarioActual, parametrosConsulta);
    this.consultarCoBinder.loadComponent(this.comunicationSendListBox);
  }

  public void reloadComunicationReceived() {
    Map<String, Object> parametrosConsulta = new HashMap<String, Object>();
    loadParametersReceived(parametrosConsulta);
    this.consultarCoBinder.loadComponent(this.comunicationsReceivedListBox);
  }

  public void onExecuteDetailRecieved() throws InterruptedException {

    if (this.detalleComunicacionView != null) {
      this.detalleComunicacionView.detach();
      Map<String, Object> variables = new HashMap<String, Object>();

      variables.put("origen", "DETALLE");
      this.detalleComunicacionView = (Window) Executions
          .createComponents("/co/detalleDocumentoCo.zul", this.self, variables);
      this.detalleComunicacionView.setClosable(true);
      try {
        this.detalleComunicacionView.setPosition("center");
        this.detalleComunicacionView.doModal();
      } catch (SuspendNotAllowedException snae) {
        LoggerFactory.getLogger(InboxComposer.class)
            .error("Error al intentar abrir GUI de la tarea", snae);
      }
    } else {
      Messagebox.show(Labels.getLabel("gedo.supervisadosComposer.msg.noPosibleIniciarVista"),
          Labels.getLabel("gedo.supervisadosComposer.msg.errorComunicacion"), Messagebox.OK,
          Messagebox.ERROR);
    }
  }

  public void onExecuteDetailSend() throws InterruptedException {

    if (this.detalleComunicacionView != null) {
      this.detalleComunicacionView.detach();
      Map<String, Object> variables = new HashMap<String, Object>();
      variables.put("selectedComunicationSend", this.selectedComunicationSend);
      variables.put("origen", "DETALLEENVIADO");

      this.detalleComunicacionView = (Window) Executions
          .createComponents("/co/detalleDocumentoCo.zul", this.self, variables);
      this.detalleComunicacionView.setClosable(true);
      try {
        this.detalleComunicacionView.setPosition("center");
        this.detalleComunicacionView.doModal();
      } catch (SuspendNotAllowedException snae) {
        LoggerFactory.getLogger(InboxComposer.class)
            .error("Error al intentar abrir GUI de la tarea", snae);
      }
    } else {
      Messagebox.show(Labels.getLabel("gedo.supervisadosComposer.msg.noPosibleIniciarVista"),
          Labels.getLabel("gedo.supervisadosComposer.msg.errorComunicacion"), Messagebox.OK,
          Messagebox.ERROR);
    }
  }

  public void onExecuteHistorialRecieved() throws InterruptedException {

    if (this.historialCoWindowView != null) {
      this.historialCoWindowView.detach();
      Map<String, Object> variables = new HashMap<String, Object>();

      this.historialCoWindowView = (Window) Executions
          .createComponents("/co/historialDocumentoCo.zul", this.self, variables);
      this.historialCoWindowView.setClosable(true);
      try {
        this.historialCoWindowView.setPosition("center");
        this.historialCoWindowView.doModal();
      } catch (SuspendNotAllowedException snae) {
        LoggerFactory.getLogger(InboxComposer.class)
            .error("Error al intentar abrir GUI de la tarea", snae);
      }
    } else {
      Messagebox.show(Labels.getLabel("gedo.supervisadosComposer.msg.noPosibleIniciarVista"),
          Labels.getLabel("gedo.supervisadosComposer.msg.errorComunicacion"), Messagebox.OK,
          Messagebox.ERROR);
    }
  }

  public void onExecuteHistorialSend() throws InterruptedException {

    if (this.historialCoWindowView != null) {
      this.historialCoWindowView.detach();
      Map<String, Object> variables = new HashMap<String, Object>();
      variables.put("selectedComunicationSend", this.selectedComunicationSend);

      this.historialCoWindowView = (Window) Executions
          .createComponents("/co/historialDocumentoCo.zul", this.self, variables);
      this.historialCoWindowView.setClosable(true);
      try {
        this.historialCoWindowView.setPosition("center");
        this.historialCoWindowView.doModal();
      } catch (SuspendNotAllowedException snae) {
        LoggerFactory.getLogger(InboxComposer.class)
            .error("Error al intentar abrir GUI de la tarea", snae);
      }
    } else {
      Messagebox.show(Labels.getLabel("gedo.supervisadosComposer.msg.noPosibleIniciarVista"),
          Labels.getLabel("gedo.supervisadosComposer.msg.errorComunicacion"), Messagebox.OK,
          Messagebox.ERROR);
    }
  }

  public void onExecuteDetailConsultaCaratula() throws InterruptedException {

    if (this.detalleComunicacionView != null) {
      this.detalleComunicacionView.detach();
      Map<String, Object> variables = new HashMap<String, Object>();
      variables.put("selectedComunicationConsulta", this.selectedComunicationSend);
      variables.put("origen", "DETALLE");

      this.detalleComunicacionView = (Window) Executions
          .createComponents("/co/detalleDocumentoCo.zul", this.self, variables);
      this.detalleComunicacionView.setClosable(true);
      try {
        this.detalleComunicacionView.setPosition("center");
        this.detalleComunicacionView.doModal();
      } catch (SuspendNotAllowedException snae) {
        LoggerFactory.getLogger(InboxComposer.class)
            .error("Error al intentar abrir GUI de la tarea", snae);
      }
    } else {
      Messagebox.show(Labels.getLabel("gedo.supervisadosComposer.msg.noPosibleIniciarVista"),
          Labels.getLabel("gedo.supervisadosComposer.msg.errorComunicacion"), Messagebox.OK,
          Messagebox.ERROR);
    }
  }

  public void reloadComunicationReferenciaSend() {
    Map<String, Object> parametrosConsulta = new HashMap<String, Object>();
    loadParametersSend(parametrosConsulta);
    this.comunicationsSend = comunicacionService
        .buscarComunicacionPorReferenciaEnviados(referencia, usuarioActual, parametrosConsulta);
    this.consultarCoBinder.loadComponent(this.comunicationSendListBox);
  }

  public void reloadComunicationReferenciaReceived() {
    Map<String, Object> parametrosConsulta = new HashMap<String, Object>();
    loadParametersReceived(parametrosConsulta);
    this.consultarCoBinder.loadComponent(this.comunicationsReceivedListBox);
  }

  public void reloadComunicationDestinatarios() {
    Map<String, Object> parametrosConsulta = new HashMap<String, Object>();
    loadParametersDestinatario(parametrosConsulta);
    this.consultarCoBinder.loadComponent(this.comunicacionesDestinatario);
  }

  public void onClick$listaTodasTareas() throws InterruptedException {
    Map<String, Object> mapa = new HashMap<String, Object>();
    mapa.put("usuario", this.usuarioActual);
    mapa.put("fechaDesde", getFechaDesde());
    mapa.put("fechaHasta", getFechaHasta());
    mapa.put("tipoTarea", getTipoTareaBusqueda());
    mapa.put("usuarioDestino", getUsuarioTareaDestino());
    mapa.put("tipoDocumento", getDocBusquedaTarea());
    mapa.put("esComunicable", true);
    mapa.put("orden", DESCENDING_);
    mapa.put("criterio", CAMPO_INICIAL);
    List<TareaBusquedaDTO> listaTareasSinFiltrar = this.tareaJBPMService
        .buscarTareasPorUsuarioInvolucradoSinFiltrar(mapa);
    if (listaTareasSinFiltrar.isEmpty()) {
      Messagebox.show(Labels.getLabel("gedo.general.noHayResultados"),
          Labels.getLabel("gedo.general.advertencia"), Messagebox.OK, Messagebox.EXCLAMATION);
    } else {
      byte[] excel = ExcelConverter.convert3(listaTareasSinFiltrar);
      if (excel != null) {
        String fnlm = Labels.getLabel("gedo.consDocumentos.flnm.tareasUsuario") + " "
            + this.usuarioActual + "_" + (DateFormats.format(new Date(), true)) + ".xls";
        Filedownload.save(excel, "application/excel", fnlm);
      } else {
        Messagebox.show(Labels.getLabel("gedo.general.noHayResultados"),
            Labels.getLabel("gedo.general.advertencia"), Messagebox.OK, Messagebox.EXCLAMATION);
      }
    }
  }

  public AnnotateDataBinder getConsultarCoBinder() {
    return consultarCoBinder;
  }

  public Task obtenerTareaSeleccionadaPorWorkflowId() {
    return this.processEngine.getTaskService().createTaskQuery()
        .executionId(this.tareaSeleccionada.getWorkfloworigen()).uniqueResult();
  }

  public void setConsultarCoBinder(AnnotateDataBinder consultarCoBinder) {
    this.consultarCoBinder = consultarCoBinder;
  }

  public void loadComunicationSend(List<ComunicacionDTO> listaSend) {
    this.comunicationsSend = listaSend;
  }

  public void loadPaginalSend(Date fecha1, Date fecha2) {
    this.coEnviadasPaginator.setTotalSize(this.comunicacionService
        .numeroComunicacionesEnviadasPorFecha(fecha1, fecha2, usuarioActual));
    this.coEnviadasPaginator.setActivePage(0);
  }

  public void loadPaginalReceived(Date fecha1, Date fecha2) {

    this.coRecibidasPaginator.setActivePage(0);
  }

  public void loadPaginalReferenciaSend() {
    this.coEnviadasPaginator.setTotalSize(this.comunicacionService
        .numeroComunicacionesEnviadasPorReferencia(referencia, usuarioActual));

    this.coEnviadasPaginator.setActivePage(0);
  }

  public void loadPaginalReferenciaReceived() {

    this.coRecibidasPaginator.setActivePage(0);
  }

  public void loadPaginalDestinatarioSend() {

    this.coDestinatarioPaginator.setActivePage(0);
  }

  public void setComunicationSendListBox(Listbox comunicationSendListBox) {
    this.comunicationSendListBox = comunicationSendListBox;
  }

  public Listbox getComunicationSendListBox() {
    return comunicationSendListBox;
  }

  public void setSelectedComunicationSend(ComunicacionDTO selectedComunicationSend) {
    this.selectedComunicationSend = selectedComunicationSend;
  }

  public ComunicacionDTO getSelectedComunicationSend() {
    return selectedComunicationSend;
  }

  public void setComunicationsSend(List<ComunicacionDTO> comunicationsSend) {
    this.comunicationsSend = comunicationsSend;
  }

  public List<ComunicacionDTO> getComunicationsSend() {
    return comunicationsSend;
  }

  public Div getResultadoBusqueda() {
    return resultadoBusqueda;
  }

  public Date getFechaDesde() {
    return fechaDesde;
  }

  public void setFechaDesde(Date fechaDesde) {
    this.fechaDesde = fechaDesde;
  }

  public Date getFechaHasta() {
    return fechaHasta;
  }

  public void setFechaHasta(Date fechaHasta) {
    this.fechaHasta = fechaHasta;
  }

  public void setResultadoBusqueda(Div resultadoBusqueda) {
    this.resultadoBusqueda = resultadoBusqueda;
  }

  public Listbox getComunicationsReceivedListBox() {
    return comunicationsReceivedListBox;
  }

  public void setComunicationsReceivedListBox(Listbox comunicationsRecievedListBox) {
    this.comunicationsReceivedListBox = comunicationsRecievedListBox;
  }

  // public List<DestinatarioCO> getComunicationsReceived() {
  // return comunicationsReceived;
  // }

  // public void setComunicationsReceived(List<DestinatarioCO>
  // comunicationsReceived) {
  // this.comunicationsReceived = comunicationsReceived;
  // }

  public void setCoRecibidasPaginator(Paginal coRecibidasPaginator) {
    this.coRecibidasPaginator = coRecibidasPaginator;
  }

  public Paginal getCoRecibidasPaginator() {
    return coRecibidasPaginator;
  }

  public void setListaCaratula(Listbox listaCaratula) {
    this.listaCaratula = listaCaratula;
  }

  public Listbox getListaCaratula() {
    return listaCaratula;
  }

  public void setComunicacionCaratula(List<ComunicacionDTO> comunicacionCaratula) {
    this.comunicacionCaratula = comunicacionCaratula;
  }

  public List<ComunicacionDTO> getComunicacionCaratula() {
    return comunicacionCaratula;
  }

  public void setDivCaratula(Div divCaratula) {
    this.divCaratula = divCaratula;
  }

  public Div getDivListadoTareas() {
    return divListadoTareas;
  }

  public void setDivListadoTareas(Div divListadoTareas) {
    this.divListadoTareas = divListadoTareas;
  }

  public Div getDivCaratula() {
    return divCaratula;
  }

  public ComunicacionDTO getSelectedComunication() {
    return selectedComunication;
  }

  public void setSelectedComunication(ComunicacionDTO selectedComunication) {
    this.selectedComunication = selectedComunication;
  }

  // public DestinatarioCO getSelectedComunicationReceieved() {
  // return selectedComunicationReceieved;
  // }

  // public void setSelectedComunicationReceieved(
  // DestinatarioCO selectedComunicationReceieved) {
  // this.selectedComunicationReceieved = selectedComunicationReceieved;
  // }

  public void setTipoBusqueda(String tipoBusqueda) {
    this.tipoBusqueda = tipoBusqueda;
  }

  public String getTipoBusqueda() {
    return tipoBusqueda;
  }

  public String getReferencia() {
    return referencia;
  }

  public void setReferencia(String referencia) {
    this.referencia = referencia;
  }

  public void setComunicacionesDestinatario(Listbox comunicacionesDestinatario) {
    this.comunicacionesDestinatario = comunicacionesDestinatario;
  }

  public Listbox getComunicacionesDestinatario() {
    return comunicacionesDestinatario;
  }

  public void setUsuarioDestino(String usuarioDestino) {
    this.usuarioDestino = usuarioDestino;
  }

  public String getUsuarioDestino() {
    return usuarioDestino;
  }

  public void setDivDestinatarios(Div divDestinatarios) {
    this.divDestinatarios = divDestinatarios;
  }

  public Div getDivDestinatarios() {
    return divDestinatarios;
  }

  public Listbox getConsultaTareasList() {
    return consultaTareasList;
  }

  public void setConsultaTareasList(Listbox consultaTareasList) {
    this.consultaTareasList = consultaTareasList;
  }

  public void setCoDestinatarioPaginator(Paginal coDestinatarioPaginator) {
    this.coDestinatarioPaginator = coDestinatarioPaginator;
  }

  public Paginal getCoDestinatarioPaginator() {
    return coDestinatarioPaginator;
  }

  public List<TareaBusquedaDTO> getTareas() {
    return tareas;
  }

  public void setTareas(List<TareaBusquedaDTO> tareas) {
    this.tareas = tareas;
  }

  public Paginal getConsultaTareasPaginator() {
    return consultaTareasPaginator;
  }

  public void setConsultaTareasPaginator(Paginal consultaTareasPaginator) {
    this.consultaTareasPaginator = consultaTareasPaginator;
  }

  public Window getTaskView() {
    return taskView;
  }

  public void setTaskView(Window taskView) {
    this.taskView = taskView;
  }

  public TareaBusquedaDTO getTareaSeleccionada() {
    return tareaSeleccionada;
  }

  public void setTareaSeleccionada(TareaBusquedaDTO tareaSeleccionada) {
    this.tareaSeleccionada = tareaSeleccionada;
  }

  public Toolbarbutton getListaTodasTareas() {
    return listaTodasTareas;
  }

  public void setListaTodasTareas(Toolbarbutton listaTodasTareas) {
    this.listaTodasTareas = listaTodasTareas;
  }

  public String getUsuarioTareaDestino() {
    return usuarioTareaDestino;
  }

  public void setUsuarioTareaDestino(String usuarioTareaDestino) {
    this.usuarioTareaDestino = usuarioTareaDestino;
  }

  public String getTipoTareaBusqueda() {
    return tipoTareaBusqueda;
  }

  public void setTipoTareaBusqueda(String tipoTareaBusqueda) {
    this.tipoTareaBusqueda = tipoTareaBusqueda;
  }

  public String getDocBusquedaTarea() {
    return docBusquedaTarea;
  }

  public void setDocBusquedaTarea(String docBusquedaTarea) {
    this.docBusquedaTarea = docBusquedaTarea;
  }

  private Listheader obtenerOrdenActual(Listbox consultaList) {
    Listheader headerOrden = new Listheader();
    Listhead listhead = consultaList.getListhead();
    List<?> list = listhead.getChildren();
    for (Object object : list) {
      if (object instanceof Listheader) {
        Listheader lheader = (Listheader) object;
        if (!lheader.getSortDirection().equals(NATURAL_)) {
          headerOrden = lheader;
          break;
        }
      }
    }
    return headerOrden;
  }

  public void onExecuteTask() throws InterruptedException {
    try {
      if (this.tareaSeleccionada.getUsuarioDestino().equals(this.usuarioActual)) {
        if (this.usuarioService.licenciaActiva(this.usuarioActual, new Date())) {
          Messagebox.show(Labels.getLabel("gedo.licencia.consulta.tareas"),
              Labels.getLabel("gedo.general.information"), Messagebox.OK, Messagebox.INFORMATION);
        } else {
          Task task = obtenerTareaSeleccionadaPorWorkflowId();
          if (task == null || task.getFormResourceName() == null) {
            Messagebox.show(Labels.getLabel("gedo.inbox.error.cont_tarea"),
                Labels.getLabel("gedo.inbox.error.title"), Messagebox.OK, Messagebox.ERROR);
            return;
          }

          if (this.taskView != null) {
            this.taskView.detach();
            Map<String, Object> variables = new HashMap<String, Object>();
            variables.put("selectedTask", task);

            this.self.getDesktop().setAttribute("selectedTask", task);
            this.taskView = (Window) Executions.createComponents(task.getFormResourceName(),
                this.self, variables);
            this.taskView.setParent(consultarCoWindow);
            this.taskView.setPosition("center");
            this.taskView.setVisible(true);
            try {
              this.taskView.doModal();
            } catch (SuspendNotAllowedException snae) {
              LoggerFactory.getLogger(InboxComposer.class)
                  .error("Error al intentar abrir GUI de la tarea", snae);
            }
          } else {
            Messagebox.show(Labels.getLabel("gedo.supervisadosComposer.msg.noPosibleIniciarVista"),
                Labels.getLabel("gedo.supervisadosComposer.msg.errorComunicacion"), Messagebox.OK,
                Messagebox.ERROR);
          }
        }
      } else {
        Messagebox.show(
            Labels.getLabel("gedo.consultaDocumentos.consultaTareas.noEnBuzon",
                new String[] { this.tareaSeleccionada.getUsuarioDestino() }),
            Labels.getLabel("gedo.general.information"), Messagebox.OK, Messagebox.INFORMATION);
      }
    } catch (SecurityNegocioException e) {
      logger.error("Error al consultar la licencia del usuario: " + e.getMessage(), e);
    }
  }

  public void onMostrarTareaPortafirma() throws InterruptedException {
    if (this.tareaSeleccionada.getUsuarioDestino().equals(this.usuarioActual)) {
      Messagebox.show(Labels.getLabel("gedo.consultaDocumentos.consultaTareas.portafirma"),
          Labels.getLabel("gedo.general.information"), Messagebox.OK, Messagebox.INFORMATION);
    } else {
      Messagebox.show(
          Labels.getLabel("gedo.consultaDocumentos.consultaTareas.noEnBuzon",
              new String[] { this.tareaSeleccionada.getUsuarioDestino() }),
          Labels.getLabel("gedo.general.information"), Messagebox.OK, Messagebox.INFORMATION);
    }
  }

  public void recargarPaginaActualTareas() {
    Listheader lheader = obtenerOrdenActual(consultaTareasList);
    buscarTareas(lheader.getSortDirection(), lheader.getId(), lheader.getColumnIndex(),
        lheader.getSortDirection().equals(ASCENDING_) ? true : false);
  }

  private class PagingSortListener implements EventListener {

    public void onEvent(Event event) throws Exception {

      if (event.getName().equals(Events.ON_SORT)) {
        event.stopPropagation();
        String order;
        if (tareas != null && tareas.size() > 0) {
          Listheader lh = (Listheader) event.getTarget();
          if (!lh.getSortDirection().equals(ASCENDING_)) {
            order = ASCENDING_;
          } else {
            order = DESCENDING_;
          }
          consultaTareasPaginator.setActivePage(0);
          buscarTareas(order, lh.getId(), lh.getColumnIndex(),
              order.equals(ASCENDING_) ? true : false);
        } else {
          return;
        }
      }
      if (event.getName().equals(ZulEvents.ON_PAGING)) {
        recargarPaginaActualTareas();
      }

    }
  }

}

final class ConsultaComunicacionListener implements EventListener {

  private ConsultaComunicacionesComposer composer;

  public ConsultaComunicacionListener(ConsultaComunicacionesComposer comp) {
    this.composer = comp;
  }

  @SuppressWarnings("unchecked")
  public void onEvent(Event event) throws Exception {
    if (event.getName().equals(Events.ON_NOTIFY)) {
      if (event.getData() != null) {
        if (event.getData() instanceof MacroEventData) {
          Task task = this.composer.obtenerTareaSeleccionadaPorWorkflowId();
          if (task != null) {
            // Ejecutar la tarea
            this.composer.onExecuteTask();
          }
        } else {
          Map<String, Object> map = (Map<String, Object>) event.getData();
          composer.getListaTodasTareas().setVisible(false);
          String evento = (String) map.get("tipoBusqueda");
          if (evento.compareTo("porFecha") == 0) {
            composer.loadComunicationSend((List<ComunicacionDTO>) map.get("coEnviadas"));
            // composer.loadComunicationReceived((List<DestinatarioCO>) map
            // .get("coRecibidas"));
            composer.loadPaginalSend((Date) map.get("fechaDesde"), (Date) map.get("fechaHasta"));
            composer.loadPaginalReceived((Date) map.get("fechaDesde"),
                (Date) map.get("fechaHasta"));
            composer.setTipoBusqueda("porFecha");
            composer.getConsultarCoBinder().loadAll();
            composer.setFechaDesde((Date) map.get("fechaDesde"));
            composer.setFechaHasta((Date) map.get("fechaHasta"));
            composer.getDivCaratula().setVisible(false);
            composer.getDivListadoTareas().setVisible(false);
            composer.getDivDestinatarios().setVisible(false);
            composer.getResultadoBusqueda().setVisible(true);
          } else if (evento.compareTo("porCaratula") == 0) {
            List<ComunicacionDTO> caratula = new ArrayList<ComunicacionDTO>();
            caratula.add((ComunicacionDTO) map.get("comunicacion"));
            composer.setComunicacionCaratula(caratula);
            composer.getConsultarCoBinder().loadAll();
            composer.getResultadoBusqueda().setVisible(false);
            composer.getDivDestinatarios().setVisible(false);
            composer.getDivCaratula().setVisible(true);
            composer.getDivListadoTareas().setVisible(false);
          } else if (evento.compareTo("porReferencia") == 0) {
            composer.loadComunicationSend((List<ComunicacionDTO>) map.get("coEnviadas"));
            // composer.loadComunicationReceived((List<DestinatarioCO>) map
            // .get("coRecibidas"));
            composer.setReferencia((String) map.get("referencia"));
            composer.setTipoBusqueda("porReferencia");
            composer.loadPaginalReferenciaSend();
            composer.loadPaginalReferenciaReceived();
            composer.getConsultarCoBinder().loadAll();
            composer.getDivCaratula().setVisible(false);
            composer.getDivCaratula().setVisible(false);
            composer.getDivDestinatarios().setVisible(false);
            composer.getDivListadoTareas().setVisible(false);
            composer.getResultadoBusqueda().setVisible(true);
          } else {
            if (evento.equals("porTarea")) {
              composer.getListaTodasTareas().setVisible(true);
              composer.setTareas((List<TareaBusquedaDTO>) map.get("listaTareas"));
              composer.setFechaDesde((Date) map.get("fechaDesde"));
              composer.setFechaHasta((Date) map.get("fechaHasta"));
              composer.setUsuarioTareaDestino((String) map.get("usuarioDestino"));
              composer.setDocBusquedaTarea((String) map.get("tipoDocumento"));
              composer.setTipoTareaBusqueda((String) map.get("tipoTarea"));
              composer.getConsultaTareasPaginator().setTotalSize((Integer) map.get("totalTareas"));
              composer.getConsultaTareasPaginator().setActivePage(0);
              composer.getConsultarCoBinder().loadAll();
              composer.getDivCaratula().setVisible(false);
              composer.getDivDestinatarios().setVisible(false);
              composer.getResultadoBusqueda().setVisible(false);
              composer.getDivListadoTareas().setVisible(true);
            } else {
              composer.loadComunicationSend((List<ComunicacionDTO>) map.get("coRecibidas"));
              composer.setUsuarioDestino((String) map.get("usuarioDestino"));
              composer.loadPaginalDestinatarioSend();
              composer.setTipoBusqueda("porDestinatario");
              composer.getConsultarCoBinder().loadAll();
              composer.getDivCaratula().setVisible(false);
              composer.getDivCaratula().setVisible(false);
              composer.getDivListadoTareas().setVisible(false);
              composer.getResultadoBusqueda().setVisible(false);
              composer.getDivDestinatarios().setVisible(true);
            }
          }
        }
      } else {
        composer.getResultadoBusqueda().setVisible(false);
        composer.getDivCaratula().setVisible(false);
        if (composer.getTareas().isEmpty()) {
          composer.getDivListadoTareas().setVisible(false);
          composer.getListaTodasTareas().setVisible(false);
        } else {
          composer.recargarPaginaActualTareas();
        }
        composer.getDivDestinatarios().setVisible(false);
      }
    } else {
      if (event.getName().equals(ZulEvents.ON_PAGING)) {
        if (composer.getTipoBusqueda().compareTo("porFecha") == 0) {
          if (event.getTarget().getId().equals("coRecibidasPaginator")) {
            composer.reloadComunicationReceived();
          } else {
            composer.reloadComunicationSend();
          }
        } else if (composer.getTipoBusqueda().compareTo("porReferencia") == 0) {
          if (event.getTarget().getId().equals("coRecibidasPaginator")) {
            composer.reloadComunicationReferenciaReceived();
          } else {
            composer.reloadComunicationReferenciaSend();
          }
        } else {
          if (event.getTarget().getId().equals("coDestinatarioPaginator")) {
            composer.reloadComunicationDestinatarios();
          }
        }
      }
    }
  }

}