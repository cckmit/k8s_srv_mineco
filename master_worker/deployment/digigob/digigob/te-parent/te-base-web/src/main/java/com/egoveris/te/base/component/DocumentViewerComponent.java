/**
 * 
 */
package com.egoveris.te.base.component;

import com.egoveris.deo.model.model.RequestExternalConsultaDocumento;
import com.egoveris.deo.ws.exception.DocumentoNoExisteException;
import com.egoveris.deo.ws.exception.ErrorConsultaDocumentoException;
import com.egoveris.deo.ws.exception.ParametroInvalidoConsultaException;
import com.egoveris.deo.ws.exception.SinPrivilegiosException;
import com.egoveris.deo.ws.service.IExternalConsultaDocumentoService;
import com.egoveris.plugins.tools.ZkUtil;
import com.egoveris.te.base.exception.DocumentoOArchivoNoEncontradoException;
import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.rendered.DocumentViewerItemRenderer;
import com.egoveris.te.base.service.iface.IAccesoWebDavService;
import com.egoveris.te.base.service.iface.IDelegable;
import com.egoveris.te.base.util.BusinessFormatHelper;
import com.egoveris.te.base.util.ConstantesWeb;

import java.io.BufferedInputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;

import org.jbpm.api.ProcessEngine;
import org.jbpm.api.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Components;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listfooter;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Paging;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

/**
 * @author difarias
 *
 */
@SuppressWarnings("serial")
public class DocumentViewerComponent extends DelegableMacroComponent
    implements IDelegable, EventListener {
  private static transient Logger logger = LoggerFactory.getLogger(DocumentViewerComponent.class);

  private static final String TRAMITACION_EN_PARALELO = "Paralelo";
  protected static String MEMORANDUM = "ME";
  protected static String NOTA = "NO";

  private static final String NOMBRE_TXT_FILTRO = "Filtro.txt";

  private Window windowViewer;
  private Listbox listboxDocumentos;

  private AnnotateDataBinder binder;

  private DocumentoDTO selectedDocument;

  private Component childWindow;

  private final String EVENT_PATTERN = "%s$%s_%s"; // pepe$onClick_prueba();

  // services
  @WireVariable("accesoWebDavServicesImpl")
  protected IAccesoWebDavService visualizaDocumentoService;
  @WireVariable("consultaDocumento3Service")
  private IExternalConsultaDocumentoService consultaDocumentoService;

  private ProcessEngine processEngine;

  public DocumentViewerComponent() {
    childWindow = Executions.createComponents(getMacroURI(), this, null);
    Components.wireVariables(childWindow, this, '$', true, true);
    Components.addForwards(childWindow, this, '$');
  }

  @SuppressWarnings("unchecked")
  public List<Component> swapChilds() {
    List<Component> lstComp = new ArrayList<>();
    lstComp.addAll(getChildren());
    lstComp.remove(childWindow);

    for (Component compToRemove : lstComp) {
      removeChild(compToRemove);
    }

    return lstComp;
  }

  private EventListener getGenericButtonListener() {
    return new EventListener() {
      @Override
      public void onEvent(Event event) throws Exception {
        String compName = event.getTarget().getId();
        String partialName = compName.substring(0, 1).toLowerCase()
            + compName.substring(1, compName.length());
        String methodToCall = String.format(EVENT_PATTERN, event.getName(), getId(), partialName);

        derivateEvent(getCallbackObject(), methodToCall,
            windowViewer.getFellow("listboxDocumentos"));
      }
    };
  }

  private void addToKeyPad(Component button) {
    Component keypad = windowViewer.getFellow("buttonPanel");
    keypad.appendChild(button);

    button.addEventListener(Events.ON_CLICK, getGenericButtonListener());
  }

  private void redraw(List<Component> comps) {
    for (Component comp : comps) {
      if ((comp instanceof Button) || (comp instanceof Toolbarbutton)) {
        addToKeyPad(comp);
      }
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.zkoss.zk.ui.HtmlMacroComponent#afterCompose()
   */
  @Override
  public void afterCompose() {
    List<Component> compToRedraw = swapChilds();
    super.afterCompose();
 

    windowViewer = (Window) getFellow("vistaDocumentosViewer");

    windowViewer.setTitle((String) getDynamicProperty("title"));

    Listbox comp = (Listbox) windowViewer.getFellow("listboxDocumentos");
    this.binder = new AnnotateDataBinder(comp);

    redraw(compToRedraw);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * com.egoveris.te.web.common.componentes.documents.DelegableMacroComponent#
   * initialize()
   */
  @Override
  public void initialize() {
    super.initialize();

    ExpedienteElectronicoDTO data = callMethodFromParameter("loadMethod");
    String filter = callMethodFromParameter("filter");

    Paging paging = (Paging) ZkUtil.findById(this.windowViewer, "pagingDocumento");
    String pageSize = (String) getDynamicProperty("pageSize");
    if (pageSize != null) {
      paging.setPageSize(new Integer(pageSize));
    }

    if (data != null) {

      final Listbox listbox = (Listbox) windowViewer.getFellow("listboxDocumentos");
      boolean hasSelectionCheck = (getDynamicProperty("selectionCheck") != null
          && ((String) getDynamicProperty("selectionCheck")).toLowerCase()
              .equalsIgnoreCase("true"));
      listbox.addEventListener(Events.ON_SELECT, new EventListener() {
        @Override
        public void onEvent(Event paramEvent) throws Exception {
          selectedDocument = (DocumentoDTO) ((Listbox) paramEvent.getTarget()).getSelectedItem()
              .getValue();
        }
      });

      final AnnotateDataBinder usedBinder = this.binder;
      listbox.addEventListener(Events.ON_USER, new EventListener() {
        @Override
        public void onEvent(Event event) throws Exception {

          if (event.getName().equals(Events.ON_USER)) {
            if (event.getData().equals("reload")) {
              event.stopPropagation();
              ExpedienteElectronicoDTO data = callMethodFromParameter("loadMethod");
              String filter = callMethodFromParameter("filter");
              listbox
                  .setModel(new ListModelList(getFilteredDocuments(data.getDocumentos(), filter)));
              usedBinder.loadComponent(listbox);
              event.stopPropagation();
            }
          }
        }
      });

      listbox.setItemRenderer(new DocumentViewerItemRenderer(this, data, hasSelectionCheck));
      List<DocumentoDTO> docs = getFilteredDocuments(data.getDocumentos(), filter);
      Collections.sort(docs, new Comparator<DocumentoDTO>() {

        @Override
        public int compare(DocumentoDTO o1, DocumentoDTO o2) {
          return o1.getFechaCreacion().compareTo(o2.getFechaAsociacion());
        }

      });
      listbox.setModel(new ListModelList(docs));

      Listfooter listfooter = (Listfooter) windowViewer.getFellow("footerSize");
      listfooter.setLabel("" + docs.size());
    }
  }

  /**
   * Method to filter documents by acronym
   * 
   * @param docs
   * @return
   */
  private List<DocumentoDTO> getFilteredDocuments(List<DocumentoDTO> docs, String filterStr) {
    if (filterStr != null && !filterStr.isEmpty()) {
      String filters[] = filterStr.split(",");

      if (filters != null && filters.length > 0) {
        List<DocumentoDTO> result = new ArrayList<>();
        for (DocumentoDTO documento : docs) {
          for (String filter : filters) {

            if (documento.getTipoDocAcronimo().trim().equalsIgnoreCase(filter.trim())) {

              result.add(documento);
            }
          }
        }

        return result;

      }
    }

    return docs;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * org.zkoss.zk.ui.event.EventListener#onEvent(org.zkoss.zk.ui.event.Event)
   */
  @Override
  public void onEvent(Event event) throws Exception {
    String compName = event.getTarget().getId();
    String partialName = compName.substring(0, 1).toLowerCase()
        + compName.substring(1, compName.indexOf("_"));

    derivateEvent(this, partialName, selectedDocument);
  }

  /**
   * Method to view a document
   * 
   * @param doc
   * @throws InterruptedException
   */
  /*
   * public void visualizarDocumento(Documento doc) throws InterruptedException
   * { HashMap<String, Boolean> hm = new HashMap<String, Boolean>();
   * hm.put(ParametrosVisualizacionDocumento.PARAMETRO_PREVISUALIZAR_DOCUMENTO,
   * false); try { VisualizacionDocumentoPopup.show(this.windowViewer,
   * doc.getNumeroSade().toString(), hm); } catch (Exception e) { Messagebox.
   * show("Error al generar la previsualización del documento de GEDO, error al comunicarse con servicios externos de GEDO, por favor, consulte con su administrador "
   * , doc.getNumeroSade().toString().concat(".pdf").toString(), Messagebox.OK,
   * Messagebox.ERROR); } }
   */

  /**
   * Method to view a document
   * 
   * @param doc
   * @throws InterruptedException
   */
  public void visualizarDocumento(DocumentoDTO doc) throws InterruptedException {
    Map<String, Object> datos = new HashMap<>();

    ExpedienteElectronicoDTO data = callMethodFromParameter("loadMethod");
  

    Task selectedTask = processEngine.getTaskService().createTaskQuery()
        .executionId(data.getIdWorkflow()).uniqueResult();

    String loggedUsername = (String) Executions.getCurrent().getDesktop().getSession()
        .getAttribute(ConstantesWeb.SESSION_USERNAME);

    if (data.getEsReservado() || selectedTask.getAssignee() != null
        && selectedTask.getAssignee().equals(loggedUsername)) {
      datos.put("assignee", loggedUsername);
    }

    datos.put("nombreArchivo", doc.getNumeroSade().toString());

    Window win = (Window) Executions.createComponents("/consultas/ppVisualizarGedo.zul",
        this.windowViewer, datos);

    win.doModal();

  }

  /**
   * Método para mostrar cartel de NO VISUALIZAR el DOCUMENTO
   */
  public void noVisualizarDocumentos() throws InterruptedException {
    Messagebox.show(
        Labels.getLabel("ee.tramitacion.documentoConfidencialNoVisualizable") + "\n"
            + Labels.getLabel("ee.tramitacion.disculpeLasMolestias"),
        Labels.getLabel("ee.general.information"), Messagebox.OK, Messagebox.EXCLAMATION);

  }

  /**
   * Metodo que visuliza un documento oficial
   * 
   * @autor IES
   * 
   *        Servicios utilizados:IAccesoWebDavService visualizaDocumentoService
   *        Métodos utilizados del servicio:visualizarDocumento(String path)
   * 
   *        Variables importantes utilizadas:
   * 
   * @param String
   *          path :Cadena que se usa como parámetro en el método
   *          BufferedInputStream visualizarDocumento(String path) para busqueda
   *          del Archivo de trabajo, la cual se completa con:
   * 
   *          ° pathDocumentoDeTrabajo .- Ubicación del Documento de Trabajo. °
   *          nombreSpaceWebDav .- Nombre del Espacio WebDav. °
   *          archivoDeTrabajo.getNombreArchivo() .- Nombre del Archivo.
   * 
   * @param File
   *          fichero :Fichero utilizado para obtener el tipo de
   *          fichero(MimeType).
   * @param String
   *          tipoFichero : Tipo de fichero del Archivo.
   * @param BufferedInputStream
   *          file : Variable que recibe el resultado tipo Inputstream del
   *          Servicio IAccesoWebDavService visualizaDocumentoService.
   * 
   * 
   * 
   */
  public void descargarDocumento(DocumentoDTO documento) throws Exception {
    String loggedUsername = (String) Executions.getCurrent().getDesktop().getSession()
        .getAttribute(ConstantesWeb.SESSION_USERNAME);
    try {
      puedeDescargarDocumento(documento, loggedUsername);

      String numeroSadeConEspacio = documento.getNumeroSade();
      String pathDocumento = "SADE";
      String fileName;

      String pathGedo = BusinessFormatHelper.nombreCarpetaWebDavGedo(numeroSadeConEspacio);
      fileName = pathDocumento + "/" + pathGedo + "/" + documento.getNombreArchivo();
      File fichero = new File(documento.getNombreArchivo());
      String tipoFichero = new MimetypesFileTypeMap().getContentType(fichero);
      String nombreArchivo = documento.getNombreArchivo();
      visualizaDocumentoService = (IAccesoWebDavService) SpringUtil.getBean("accesoWebDavServicesImpl");
      BufferedInputStream file = this.visualizaDocumentoService.visualizarDocumento(fileName);
      Filedownload.save(file, tipoFichero, nombreArchivo);

    } catch (SinPrivilegiosException ex) {
      logger.error("error en metodo: descargarDocumento() - SinPrivilegiosException", ex);
      Messagebox.show(Labels.getLabel("ee.tramitacion.documentoReservadoNoVisualizable"),
          Labels.getLabel("ee.tramitacion.informacion.documentoNoExiste"), Messagebox.OK,
          Messagebox.EXCLAMATION);
    } catch (DocumentoOArchivoNoEncontradoException e) {
      logger.error("error en metodo: descargarDocumento() - DocumentoOArchivoNoEncontradoException", e);
      Messagebox.show(Labels.getLabel("ee.tramitacion.documentoNoExisteEnRepositorio"),
          Labels.getLabel("ee.tramitacion.informacion.documentoNoExiste"), Messagebox.OK,
          Messagebox.EXCLAMATION);
    } catch (Exception e) {
      logger.error("Error al descargar documentos : ", e);
      Messagebox.show(Labels.getLabel("ee.tramitacion.errorDescargaDocumento"),
          Labels.getLabel("ee.tramitacion.titulo.pase"), Messagebox.OK, Messagebox.ERROR);

    }
  }

  /***
   * Valida si es posible descargar el documento GEDO, verifica si es reservado
   * y si el usuario tienen permisos para ver documentos confidenciales *
   * 
   * @param documento
   * @param loggedUsername
   * @throws SinPrivilegiosException
   * @throws ParametroInvalidoConsultaException
   * @throws DocumentoNoExisteException
   * @throws ErrorConsultaDocumentoException
   */
  private void puedeDescargarDocumento(DocumentoDTO documento, String loggedUsername)
      throws SinPrivilegiosException, ParametroInvalidoConsultaException,
      DocumentoNoExisteException, ErrorConsultaDocumentoException {

    RequestExternalConsultaDocumento request = new RequestExternalConsultaDocumento();
    request.setNumeroDocumento(documento.getNumeroSade());
    request.setUsuarioConsulta(loggedUsername);
    this.consultaDocumentoService.consultarDocumentoPorNumero(request);
    verificarDocumentoSubsanado(documento, loggedUsername);
  }

  private void verificarDocumentoSubsanado(DocumentoDTO documento, String loggedUsername)
      throws SinPrivilegiosException {
    if (documento.isSubsanado()) {
      if (!documento.getUsuarioSubsanador().equals(loggedUsername)) {
        throw new SinPrivilegiosException(loggedUsername);
      }
    }
  }

  public void onDescargarTodos() {
  }

  public List<DocumentoDTO> getSelectedDocuments() {
    Listbox listbox = ZkUtil.findById(windowViewer, "listboxDocumentos");
    List<DocumentoDTO> result = new ArrayList<>();

    if (listbox != null) {
      for (Object obj : listbox.getItems()) {
        Listitem item = (Listitem) obj;
        List<Checkbox> lstFounded = ZkUtil.findByType(item, Checkbox.class);
        if (lstFounded.size() == 1 && lstFounded.get(0).isChecked()) {
          result.add((DocumentoDTO) item.getValue());
        }
      }
    }

    return result;
  }

}
