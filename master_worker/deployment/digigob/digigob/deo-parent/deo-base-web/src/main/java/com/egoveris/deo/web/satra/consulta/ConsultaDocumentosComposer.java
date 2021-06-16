package com.egoveris.deo.web.satra.consulta;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.util.CollectionUtils;
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
import org.zkoss.zul.Listfooter;
import org.zkoss.zul.Listhead;
import org.zkoss.zul.Listheader;
import org.zkoss.zul.Menuitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;
import org.zkoss.zul.event.PagingEvent;
import org.zkoss.zul.event.ZulEvents;
import org.zkoss.zul.ext.Paginal;

import com.egoveris.cdeo.web.visualizarDocumento.VisualizacionDocumentoPopup;
import com.egoveris.deo.base.service.BuscarDocumentosGedoService;
import com.egoveris.deo.base.service.GestionArchivosWebDavService;
import com.egoveris.deo.base.service.IEcosistemaService;
import com.egoveris.deo.base.service.ObtenerNumeracionEspecialService;
import com.egoveris.deo.base.service.TareaJBPMService;
import com.egoveris.deo.base.service.TipoDocumentoService;
import com.egoveris.deo.model.model.ConsultaSolrRequest;
import com.egoveris.deo.model.model.DocumentoDTO;
import com.egoveris.deo.model.model.DocumentoSolr;
import com.egoveris.deo.model.model.DocumentosExcelDTO;
import com.egoveris.deo.model.model.MacroEventData;
import com.egoveris.deo.model.model.NumerosUsadosDTO;
import com.egoveris.deo.model.model.TareaBusquedaDTO;
import com.egoveris.deo.model.model.TipoDocumentoDTO;
import com.egoveris.deo.util.Constantes;
import com.egoveris.deo.web.satra.GEDOGenericForwardComposer;
import com.egoveris.deo.web.satra.InboxComposer;
import com.egoveris.deo.web.utils.Utilitarios;
import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.Usuario;
import com.egoveris.sharedsecurity.conf.service.SecurityUtil;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ConsultaDocumentosComposer extends GEDOGenericForwardComposer {

  private static final long serialVersionUID = -5748565613562274304L;

  private static Logger logger = LoggerFactory.getLogger(ConsultaDocumentosComposer.class);

  private static final String ASCENDING_ = "ascending";
  private static final String DESCENDING_ = "descending";
  private static final String NATURAL_ = "natural";
  private static final String CAMPO_INICIAL = "fechaAlta";

  private Window consultaDocumentosWindow;
  private Window consultaTareaWindow;
  private Listbox consultaDocumentosList;
  private Listbox consultaTareasList;
//  private Menuitem listaNumerosUsados;
  private Menuitem listaTodosRegistros;
  private Menuitem listaTodasTareas;
  private transient Paginal consultaDocumentosPaginator;
  private transient Paginal consultaTareasPaginator;
  private Listfooter foot;
  private Listfooter footTareas;
  private Div listadoDocumentos;
  private Div listadoTareas;
  protected Task workingTask = null;
  private ConsultaSolrRequest consultaSolr;
  private Date fechaDesde;
  private Date fechaHasta;
  private String usuarioTareaDestino;
  private String docBusquedaTarea;
  private String tipoTareaBusqueda;
  private Menuitem porNumeroSADE;
  private Menuitem porActuacionSade;

  private DocumentoSolr selectedDocumento;
  private Usuario usuario;
  @WireVariable("buscarDocumentosGedoServiceImpl")
  private BuscarDocumentosGedoService buscarDocGedoSer;
  @WireVariable("obtenerNumeracionEspecialServiceImpl")
  private ObtenerNumeracionEspecialService obtenerNumeracionEspecialService;
  @WireVariable("gestionArchivosWebDavServiceImpl")
  private GestionArchivosWebDavService gestionArchivosWebDavService;
  @WireVariable("ecosistemaServiceImpl")
  private IEcosistemaService ecosistemaService;
  @WireVariable("tipoDocumentoServiceImpl")
  private TipoDocumentoService  tipoDocumentoService; 
	

  private TareaBusquedaDTO tareaSeleccionada;
  private List<DocumentoSolr> documentos;
  private List<TareaBusquedaDTO> tareas;
  private AnnotateDataBinder binder;

  private Window taskView;

  @WireVariable("tareaJBPMServiceImpl")
  private TareaJBPMService tareaJBPMService;
  @WireVariable("processEngineImpl")
  private ProcessEngine processEngine;

  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    botonExcelVisible(false);
    botonListarTareasVisible(false);
    getFoot().setVisible(false);
    boolean visibilidad = SecurityUtil.isAllGranted(Constantes.ROL_ADMIN_CENTRAL);
//    this.usuario = getUsuarioService().obtenerUsuario((String) Executions.getCurrent().getDesktop()
//        .getSession().getAttribute(Constantes.SESSION_USERNAME));

    this.usuario = Utilitarios.obtenerUsuarioActual();
//    this.listaNumerosUsados.setVisible(visibilidad);
    this.tareas = new ArrayList<>();

    String codigoEcosistema = ecosistemaService.obtenerEcosistema();
    if (codigoEcosistema.trim().isEmpty()) {
      porNumeroSADE.setLabel(
          Labels.getLabel("gedo.consultaDocumentos.menupopup.menuitem.label.porNumeroSade",
              new String[] { Constantes.CODIGO_ECOSISTEMA_SADE }));
      porActuacionSade.setLabel(
          Labels.getLabel("gedo.consultaDocumentos.menupopup.menu.item.label.actuacionSade",
              new String[] { Constantes.CODIGO_ECOSISTEMA_SADE }));
    } else {
      porNumeroSADE.setLabel(
          Labels.getLabel("gedo.consultaDocumentos.menupopup.menuitem.label.porNumeroSade",
              new String[] { codigoEcosistema }));
      porActuacionSade.setLabel(
          Labels.getLabel("gedo.consultaDocumentos.menupopup.menu.item.label.actuacionSade",
              new String[] { codigoEcosistema }));
    }

    inicializarListeners();
  }

  public void inicializarListeners() {
    consultaDocumentosWindow.addEventListener(Events.ON_NOTIFY, new UpdateResultadosListener());
    getConsultaDocumentosPaginator().addEventListener(ZulEvents.ON_PAGING,
        new PagingSortListener());
    getConsultaTareasPaginator().addEventListener(ZulEvents.ON_PAGING,
        new PagingSortTareasListener());

    inicializarSorts(this.consultaDocumentosList);
    inicializarSorts(this.consultaTareasList);
  }

  private void inicializarSorts(Listbox listbox) {

    Listhead listhead = listbox.getListhead();
    List<?> list = listhead.getChildren();
    for (Object object : list) {
      if (object instanceof Listheader) {
        Listheader lheader = (Listheader) object;
        if ((lheader.getSortAscending() != null || lheader.getSortDescending() != null))
          if (listbox.getId().equals("consultaTareasList")) {
            lheader.addEventListener(Events.ON_SORT, new PagingSortTareasListener());
          } else {
            lheader.addEventListener(Events.ON_SORT, new PagingSortListener());
          }
      }
    }
  }

  @SuppressWarnings("unchecked")
  public void onCreate$consultaDocumentosWindow(Event event) throws InterruptedException {

    this.binder = (AnnotateDataBinder) event.getTarget().getAttribute("binder", true);

    Map<String, Object> hm = (Map<String, Object>) Executions.getCurrent().getSession()
        .removeAttribute(Constantes.PATHMAP);

    if (!CollectionUtils.isEmpty(hm)) {
      if (hm.get("ID1") != null) {
        this.buscarDocumentoParametrizado(hm);
      }
    }
  }

  private void buscarDocumentoParametrizado(Map<String, Object> hm) throws InterruptedException {
    VisualizacionDocumentoPopup.show(this.self, hm.get("ID1").toString(), null);
  }

  public void openModalWindow(String zulFile) {
    Window parametrosConsultaWindow = (Window) Executions.createComponents(zulFile, this.self,
        null);
    parametrosConsultaWindow.setParent(this.consultaDocumentosWindow);
    parametrosConsultaWindow.setPosition("center");
    parametrosConsultaWindow.setVisible(true);

    parametrosConsultaWindow.doModal();
  }

  public void onClick$generadosPorMi() {
    this.openModalWindow("/consultas/consultaGeneradosPorMi.zul");
  }

  public void onClick$generadosEnReparticion() {
    this.openModalWindow("/consultas/consultaGeneradosEnMiReparticion.zul");
  }

  public void onClick$busquedaTareas() {
    flechitasSort(1, false, this.consultaTareasList);
    Map<String, Object> parametrosConsulta = new HashMap<String, Object>();
    cargarParamtrosConsulta(parametrosConsulta, DESCENDING_, CAMPO_INICIAL);
    this.consultaTareaWindow = (Window) Executions.createComponents("consultas/consultaTareas.zul",
        this.self, parametrosConsulta);
    this.consultaTareaWindow.doModal();
    this.consultaTareaWindow.setPosition("center");
  }

  private void buscarTareas(String orden, String criterio, Integer columnaOrden,
      boolean isAscending) {

    Map<String, Object> parametrosConsulta = new HashMap<String, Object>();
    cargarParamtrosConsulta(parametrosConsulta, orden, criterio);
    parametrosConsulta.put("fechaDesde", this.fechaDesde);
    parametrosConsulta.put("fechaHasta", this.fechaHasta);
    parametrosConsulta.put("tipoTarea", getTipoTareaBusqueda());
    parametrosConsulta.put("usuarioDestino", getUsuarioTareaDestino());
    parametrosConsulta.put("tipoDocumento", getDocBusquedaTarea());
    flechitasSort(columnaOrden, isAscending, this.consultaTareasList);
    this.tareas = this.tareaJBPMService.buscarTareasPorUsuarioInvolucrado(parametrosConsulta);
    this.binder.loadComponent(this.consultaTareasList);
  }

  private void cargarParamtrosConsulta(Map<String, Object> parametrosConsulta, String orden,
      String criterio) {

    parametrosConsulta.put("usuario", this.usuario.getUsername());
    parametrosConsulta.put("esComunicable", false);
    parametrosConsulta.put("inicioCarga",
        this.consultaTareasPaginator.getActivePage() * this.consultaTareasPaginator.getPageSize());
    parametrosConsulta.put("cantidadResultados", this.consultaTareasPaginator.getPageSize());
    parametrosConsulta.put("orden", orden);
    parametrosConsulta.put("criterio", criterio);
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

  public void onClick$porNumeroSADE() {
    this.openModalWindow("/consultas/consultaPorNumeroSADE.zul");
  }

  public void onClick$porNumeroEspecial() {
    this.openModalWindow("/consultas/consultaPorNumeroEspecial.zul");
  }

  public void onClick$porNumeroSADEPapel() {
    this.openModalWindow("/consultas/consultaPorNumeroSADEPapel.zul");
  }

  public void onClick$porActuacionSade() {
    this.openModalWindow("/consultas/consultaPorActuacionSade.zul");
  }

  /*
   * Se elimina el llamado a consultaSecuenciaNumeros.zul, ya que no se requiere
   * filtro por fecha. Se realiza la consulta y la generación del archivo excel.
   */
  /* public void onClick$listaNumerosUsados() throws InterruptedException, SecurityNegocioException {
    int year = Calendar.getInstance().get(Calendar.YEAR);

    // FIXME multireparticion A CONFIRMAR
    // Usuario us =
    // getUsuarioService().obtenerUsuario(this.usuario.getUsername());
    List<NumerosUsadosDTO> result = obtenerNumeracionEspecialService.getNumerosUsados(year,
        this.usuario.getCodigoReparticion());
    if (result.size() == 0) {
      Messagebox.show(Labels.getLabel("gedo.general.noHayResultados"),
          Labels.getLabel("gedo.general.advertencia"), Messagebox.OK, Messagebox.EXCLAMATION);
    }
    byte[] excel = ExcelConverter.convert(result);
    if (excel != null) {
      String fnlm = "NumerosUsados " + this.usuario.getCodigoReparticion().trim() + "_"
          + (DateFormats.format(new Date(), true)) + ".xls";
      Filedownload.save(excel, "application/excel", fnlm);
    } else {
      Messagebox.show(Labels.getLabel("gedo.general.noHayResultados"),
          Labels.getLabel("gedo.general.advertencia"), Messagebox.OK, Messagebox.EXCLAMATION);
    }
  }*/

  public void onClick$listaTodosRegistros() throws InterruptedException {
    if (getConsultaSolr() != null && getDocumentos() != null && getDocumentos().size() > 0) {
      getConsultaSolr().setPageSize(getConsultaDocumentosPaginator().getTotalSize());
      getConsultaSolr().setPageIndex(0);
      List<DocumentosExcelDTO> result = this.getDocumentosExcel(
          this.buscarDocGedoSer.buscarDocumentos(getConsultaSolr()).getContent());
      if (result == null || result.size() == 0) {
        Messagebox.show(Labels.getLabel("gedo.general.noHayResultados"),
            Labels.getLabel("gedo.general.advertencia"), Messagebox.OK, Messagebox.EXCLAMATION);
      }
      byte[] excel = ExcelConverter.convert2(result, getConsultaSolr(),
          this.usuario.getUsername());
      if (excel != null) {
        String fnlm = "Registros consulta " + "_" + this.usuario.getUsername() + "_"
            + (DateFormats.format(new Date(), true)) + ".xls";
        Filedownload.save(excel, "application/excel", fnlm);
      } else {
        Messagebox.show(Labels.getLabel("gedo.general.noHayResultados"),
            Labels.getLabel("gedo.general.advertencia"), Messagebox.OK, Messagebox.EXCLAMATION);
      }
    } else {
      Messagebox.show(Labels.getLabel("gedo.general.noHayResultados"),
          Labels.getLabel("gedo.general.advertencia"), Messagebox.OK, Messagebox.EXCLAMATION);
    }
  }

  public void onClick$listaTodasTareas() throws InterruptedException {
    Map<String, Object> mapa = new HashMap<String, Object>();
    mapa.put("usuario", this.usuario.getUsername());
    mapa.put("fechaDesde", getFechaDesde());
    mapa.put("fechaHasta", getFechaHasta());
    mapa.put("tipoTarea", getTipoTareaBusqueda());
    mapa.put("usuarioDestino", getUsuarioTareaDestino());
    mapa.put("tipoDocumento", getDocBusquedaTarea());
    mapa.put("esComunicable", false);
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
            + this.usuario.getUsername() + "_" + (DateFormats.format(new Date(), true)) + ".xls";
        Filedownload.save(excel, "application/excel", fnlm);
      } else {
        Messagebox.show(Labels.getLabel("gedo.general.noHayResultados"),
            Labels.getLabel("gedo.general.advertencia"), Messagebox.OK, Messagebox.EXCLAMATION);
      }
    }
  }

  private List<DocumentosExcelDTO> getDocumentosExcel(List<DocumentoSolr> listaDocumentos) {
    List<DocumentosExcelDTO> result = null;
    if (listaDocumentos != null && !listaDocumentos.isEmpty()) {
      result = new ArrayList<DocumentosExcelDTO>();
      for (DocumentoSolr doc : listaDocumentos)
        result.add(new DocumentosExcelDTO(doc));
    }
    return result;
  }

  private void buscar() throws InterruptedException {
    getConsultaSolr().setPageSize(getConsultaDocumentosPaginator().getPageSize());
    Page<DocumentoSolr> pageSolr = ejecutarConsulta();
    flechitasSort(getConsultaSolr().getIndexSortColumn(),
        getConsultaSolr().getCriteria().equals(ConsultaSolrRequest.ORDER_ASC),
        this.consultaDocumentosList);
    getConsultaDocumentosPaginator().setTotalSize((int) pageSolr.getTotalElements());
    setearFoot(getConsultaDocumentosPaginator().getTotalSize());
    setDocumentos(pageSolr.getContent());
  }

  private Page<DocumentoSolr> ejecutarConsulta() {

    if (getConsultaSolr().getNroSade() != null) {
      if (getConsultaSolr().getActuacionAcr() != null) {
        return this.buscarDocGedoSer.buscarDocPorActuacion(getConsultaSolr().getNroSade(), 
        		getConsultaSolr().getActuacionAcr());
      } else {
        return this.buscarDocGedoSer.buscarDocPorNumero(getConsultaSolr().getNroSade(),
            getConsultaSolr().getTipoDocAcr());
      }
    } else if (getConsultaSolr().getNroSadePapel() != null) {
      return this.buscarDocGedoSer.buscarDocSadePapel(getConsultaSolr().getNroSadePapel());
    } else if (getConsultaSolr().getNroEspecialSade() != null) {
      return this.buscarDocGedoSer.buscarDocEspecial(getConsultaSolr().getNroEspecialSade());
    } else {
      return this.buscarDocGedoSer.buscarDocumentos(getConsultaSolr());
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

  private void refreshResultado() {
    this.listadoTareas.setVisible(false);
    this.listadoDocumentos.setVisible(true);
    this.binder.loadComponent(this.consultaDocumentosList);
  }

  private void setearFoot(int size) {
    getFoot().setVisible(true);
    getFoot().setLabel(
        Labels.getLabel("gedo.consDocumentos.label.cantRegistrosEncontrados") + " " + size);
    getFoot().setSpan(7);
  }

  private void setearFootTareas(int size) {
    getFootTareas().setVisible(true);
    getFootTareas().setLabel(
        Labels.getLabel("gedo.consDocumentos.label.cantRegistrosEncontrados") + " " + size);
    getFootTareas().setSpan(7);
  }

  private Task obtenerTareaSeleccionadaPorWorkflowId() {
    return this.processEngine.getTaskService().createTaskQuery()
        .executionId(this.tareaSeleccionada.getWorkfloworigen()).uniqueResult();
  }

  private void botonExcelVisible(boolean b) {
    this.listaTodosRegistros.setVisible(b);
  }

  private void botonListarTareasVisible(boolean b) {
    this.listaTodasTareas.setVisible(b);
  }

  public Listfooter getFoot() {
    return foot;
  }

  public void setFoot(Listfooter foot) {
    this.foot = foot;
  }

  public Listfooter getFootTareas() {
    return footTareas;
  }

  public void setFootTareas(Listfooter footTareas) {
    this.footTareas = footTareas;
  }

  public ConsultaSolrRequest getConsultaSolr() {
    return consultaSolr;
  }

  public void setConsultaSolr(ConsultaSolrRequest consultaSolr) {
    this.consultaSolr = consultaSolr;
  }

  public List<DocumentoSolr> getDocumentos() {
    return documentos;
  }

  public List<TareaBusquedaDTO> getTareas() {
    return tareas;
  }

  public void setTareas(List<TareaBusquedaDTO> tareas) {
    this.tareas = tareas;
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

  public void setWorkingTask(Task workingTask) {
    this.workingTask = workingTask;
  }

  public Task getWorkingTask() {
    return workingTask;
  }

  public void setDocumentos(List<DocumentoSolr> documentos) {
    this.documentos = documentos;
  }

  public Listbox getConsultaDocumentosList() {
    return consultaDocumentosList;
  }

  public void setConsultaDocumentosList(Listbox consultaDocumentosList) {
    this.consultaDocumentosList = consultaDocumentosList;
  }

  public DocumentoSolr getSelectedDocumento() {
    return selectedDocumento;
  }

  public void setSelectedDocumento(DocumentoSolr selectedDocumento) {
    this.selectedDocumento = selectedDocumento;
  }

  public Paginal getConsultaDocumentosPaginator() {
    return consultaDocumentosPaginator;
  }

  public void setConsultaDocumentosPaginator(Paginal consultaDocumentosPaginator) {
    this.consultaDocumentosPaginator = consultaDocumentosPaginator;
  }

  public Paginal getConsultaTareasPaginator() {
    return consultaTareasPaginator;
  }

  public void setConsultaTareasPaginator(Paginal consultaTareasPaginator) {
    this.consultaTareasPaginator = consultaTareasPaginator;
  }

  public TareaBusquedaDTO getTareaSeleccionada() {
    return tareaSeleccionada;
  }

  public void setTareaSeleccionada(TareaBusquedaDTO tareaSeleccionada) {
    this.tareaSeleccionada = tareaSeleccionada;
  }

  public ProcessEngine getProcessEngine() {
    return processEngine;
  }

  public void setProcessEngine(ProcessEngine processEngine) {
    this.processEngine = processEngine;
  }

  public Menuitem getListaTodasTareas() {
    return listaTodasTareas;
  }

  public void setListaTodasTareas(Menuitem listaTodasTareas) {
    this.listaTodasTareas = listaTodasTareas;
  }

  public AnnotateDataBinder getBinder() {
    return binder;
  }

  public void setBinder(AnnotateDataBinder binder) {
    this.binder = binder;
  }

  public Listbox getConsultaTareasList() {
    return consultaTareasList;
  }

  public void setConsultaTareasList(Listbox consultaTareasList) {
    this.consultaTareasList = consultaTareasList;
  }

  public String getUsuarioTareaDestino() {
    return usuarioTareaDestino;
  }

  public void setUsuarioTareaDestino(String usuarioTareaDestino) {
    this.usuarioTareaDestino = usuarioTareaDestino;
  }

  public String getDocBusquedaTarea() {
    return docBusquedaTarea;
  }

  public void setDocBusquedaTarea(String docBusquedaTarea) {
    this.docBusquedaTarea = docBusquedaTarea;
  }

  public String getTipoTareaBusqueda() {
    return tipoTareaBusqueda;
  }

  public void setTipoTareaBusqueda(String tipoTareaBusqueda) {
    this.tipoTareaBusqueda = tipoTareaBusqueda;
  }

  public void onDescargaDoc() throws InterruptedException {
    if (StringUtils.isNotEmpty(this.selectedDocumento.getNroSade())) {
      DocumentoDTO doc = this.buscarDocGedoSer
          .buscarDocumentoPorNumero(this.selectedDocumento.getNroSade());
      if(doc != null){
        if (doc.getMotivoDepuracion() == null) {
          if (this.buscarDocGedoSer.puedeVerDocumentoConfidencial(doc, this.usuario.getUsername(),
              this.usuario.getCodigoReparticion().trim())) {
            InputStream file;
            
            // WEBDAV
            file = this.gestionArchivosWebDavService
                .obtenerDocumento(this.selectedDocumento.getNroSade()).getFileAsStream();
            
            Filedownload.save(file, null, this.selectedDocumento.getNroSade().concat(".pdf"));
          } else {
            Messagebox.show(Labels.getLabel("gedo.consultaDocumentos.noDescargar"),
                Labels.getLabel("gedo.general.advertencia"), Messagebox.OK, Messagebox.EXCLAMATION);
          }
        } else {
          Messagebox.show(Labels.getLabel("gedo.consultaDocumentos.motivoDepuracion"),
              Labels.getLabel("gedo.general.advertencia"), Messagebox.OK, Messagebox.EXCLAMATION);
        }
      }else{
        Messagebox.show(Labels.getLabel("gedo.consultaDocumentos.noDisponible"),
            Labels.getLabel("gedo.general.advertencia"), Messagebox.OK, Messagebox.EXCLAMATION);
      }
    }
  }

  public void onVisualizarDoc() throws SuspendNotAllowedException, InterruptedException {
	 // TODO pasar si es del tipo template por aca
	
	  TipoDocumentoDTO documentoDTO = tipoDocumentoService
			  .buscarTipoDocumentoByAcronimo(this.selectedDocumento.getTipoDocAcr());
	
    VisualizacionDocumentoPopup.show(this.self, this.selectedDocumento.getNroSade(),
    		documentoDTO.getTieneTemplate(), null,
        this.usuario.getUsername());
  }

  public void onExecuteTask() throws InterruptedException {
    try {
      if (this.tareaSeleccionada.getUsuarioDestino().equals(this.usuario.getUsername())) {
        if (this.usuarioService.licenciaActiva(this.usuario.getUsername(), new Date())) {
          Messagebox.show(
              Labels.getLabel("gedo.licencia.consulta.tareas",
                  new String[] { this.usuario.getUsername() }),
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
            this.taskView.setParent(consultaDocumentosWindow);
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
    if (this.tareaSeleccionada.getUsuarioDestino().equals(this.usuario.getUsername())) {
      Messagebox.show(Labels.getLabel("gedo.consultaDocumentos.consultaTareas.portafirma"),
          Labels.getLabel("gedo.general.information"), Messagebox.OK, Messagebox.INFORMATION);
    } else {
      Messagebox.show(
          Labels.getLabel("gedo.consultaDocumentos.consultaTareas.noEnBuzon",
              new String[] { this.tareaSeleccionada.getUsuarioDestino() }),
          Labels.getLabel("gedo.general.information"), Messagebox.OK, Messagebox.INFORMATION);
    }
  }

  private void recargarPaginaActualTareas() {
    Listheader headerOrden = obtenerOrdenActual(consultaTareasList);
    buscarTareas(headerOrden.getSortDirection(), headerOrden.getId(), headerOrden.getColumnIndex(),
        headerOrden.getSortDirection().equals(ASCENDING_) ? true : false);
  }

  /**
   * Listener que escucha los eventos de paginación y ordenamiento.
   */
  private class PagingSortListener implements EventListener {

    public void onEvent(Event event) throws Exception {

      if (event.getName().equals(Events.ON_SORT)) {
        // asi no hace el sort con comparator
        event.stopPropagation();

        if (documentos != null && documentos.size() > 0) {
          Listheader lh = (Listheader) event.getTarget();
          if (!lh.getSortDirection().equals("ascending")) {
            getConsultaSolr().setAscCriteria();
          } else {
            getConsultaSolr().setDescCriteria();
          }
          getConsultaSolr().setSortColumn(lh.getColumnIndex());
          getConsultaSolr().setPageIndex(0);
          consultaDocumentosPaginator.setActivePage(0);
        } else {
          return;
        }
      }
      if (event.getName().equals(ZulEvents.ON_PAGING)) {
        getConsultaSolr().setPageIndex(((PagingEvent) event).getActivePage());
      }
      buscar();
      refreshResultado();
    }
  }

  private class PagingSortTareasListener implements EventListener {

    public void onEvent(Event event) throws Exception {

      String order;
      if (event.getName().equals(Events.ON_SORT)) {
        event.stopPropagation();
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
      binder.loadComponent(consultaTareasList);
    }
  }

  private class UpdateResultadosListener implements EventListener {
    @SuppressWarnings("unchecked")
    public void onEvent(Event event) throws Exception {
      if (event.getName().equals(Events.ON_NOTIFY)) {
        if (event.getData() instanceof ConsultaSolrRequest) {
          setConsultaSolr((ConsultaSolrRequest) event.getData());
          botonExcelVisible(getConsultaSolr().getTipoBusqueda() != null);
          botonListarTareasVisible(false);
          getConsultaDocumentosPaginator().setActivePage(0);
          buscar();
          refreshResultado();
        } else {
          if (event.getData() == null) {
            if (getTareas() != null && !getTareas().isEmpty()) {
              recargarPaginaActualTareas();
              getBinder().loadComponent(getConsultaTareasList());
            }

          } else {
            if (event.getData() instanceof MacroEventData) {
              Task task = obtenerTareaSeleccionadaPorWorkflowId();
              if (task != null) {
                // Ejecutar la tarea
                onExecuteTask();
              }
            } else {
              Map<String, Object> map = (Map<String, Object>) event.getData();
              if (map.get("tipoBusqueda").equals("porTarea")) {
                setTareas((List<TareaBusquedaDTO>) map.get("listaTareas"));
                Integer cantidadTareas = (Integer) map.get("totalTareas");
                getConsultaTareasPaginator().setTotalSize(cantidadTareas);
                setearFootTareas(cantidadTareas);
                binder.loadAll();
                listadoDocumentos.setVisible(false);
                listadoTareas.setVisible(true);
                setFechaDesde((Date) map.get("fechaDesde"));
                setFechaHasta((Date) map.get("fechaHasta"));
                setUsuarioTareaDestino((String) map.get("usuarioDestino"));
                setDocBusquedaTarea((String) map.get("tipoDocumento"));
                setTipoTareaBusqueda((String) map.get("tipoTarea"));
                getConsultaTareasPaginator().setActivePage(0);
                botonExcelVisible(false);
                botonListarTareasVisible(true);
              }

            }
          }
        }
      }
    }
  }
}
