/**
 * 
 */
package com.egoveris.te.base.composer;

import com.egoveris.deo.model.model.RequestExternalConsultaDocumento;
import com.egoveris.deo.ws.exception.DocumentoNoExisteException;
import com.egoveris.deo.ws.exception.ErrorConsultaDocumentoException;
import com.egoveris.deo.ws.exception.ParametroInvalidoConsultaException;
import com.egoveris.deo.ws.exception.SinPrivilegiosException;
import com.egoveris.deo.ws.service.IExternalConsultaDocumentoServiceExt;
import com.egoveris.te.base.exception.DocumentoOArchivoNoEncontradoException;
import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.model.DocumentoGedoDTO;
import com.egoveris.te.base.model.ExpedienteElectronicoDTO;
import com.egoveris.te.base.service.DocumentoGedoService;
import com.egoveris.te.base.service.UsuariosSADEService;
import com.egoveris.te.base.service.iface.IAccesoWebDavService;
import com.egoveris.te.base.util.BusinessFormatHelper;
import com.egoveris.te.base.util.ConstantesWeb;
import com.egoveris.te.base.util.ConstantesServicios;
import com.egoveris.te.base.util.Utils;
import com.egoveris.te.base.util.Zip;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.activation.MimetypesFileTypeMap;

import org.jbpm.api.Execution;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Window;

/**
 * @author lfishkel
 * 
 */
@SuppressWarnings("serial")
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class GenericDocumentoComposer extends GenericForwardComposer {

  private static transient Logger logger = LoggerFactory.getLogger(GenericDocumentoComposer.class);
  private static final String TRAMITACION_EN_PARALELO = "Paralelo";
  private static final String NOMBRE_TXT_FILTRO = "Filtro.txt";
  protected Task workingTask = null;

  @Autowired
  private Tabbox tabBoxDoc;

  @Autowired
  private Tab conPase;

  @Autowired
  private AnnotateDataBinder binder;
  
  @Autowired
  private Window vistaDocumentosWindow;
  private Window documentoDetalleWindow;
  private Component ventanaAbierta;

  protected ExpedienteElectronicoDTO expedienteElectronico;
  public DocumentoDTO selectedDocumento;
  public List<DocumentoDTO> listaDocumento;

  // services
  
  @WireVariable(ConstantesServicios.ACCESO_WEBDAV_SERVICE)
  protected IAccesoWebDavService visualizaDocumentoService;
  
  @WireVariable(ConstantesServicios.CONSULTA_DOC_EXTERNAL_SERVICE)
  private IExternalConsultaDocumentoServiceExt consultaDocumentoService;
  
  @WireVariable(ConstantesServicios.USUARIOS_SADE_SERVICE)
  private UsuariosSADEService usuariosSADEService;

  private Boolean soloLectura;
  
  @WireVariable(ConstantesServicios.PROCESS_ENGINE_SERVICE)
  private ProcessEngine processEngine;

  private Boolean esReserva = false;
  private String origen = null;
  
  @WireVariable(ConstantesServicios.DOCUMENTO_GEDO_SERVICE)
  private DocumentoGedoService documentoGedoService;

  @Override
  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    this.soloLectura = (Boolean) Executions.getCurrent().getDesktop().getAttribute("soloLectura");
    Executions.getCurrent().getDesktop().setAttribute("acordeonWindow",
        this.vistaDocumentosWindow);
    comp.addEventListener(Events.ON_RENDER, new GenericDocumentoEventListener(this));
    comp.addEventListener(Events.ON_NOTIFY, new GenericDocumentoEventListener(this));
    esReserva = (Boolean) Executions.getCurrent().getArg().get("reservado"); 
  }

  /**
   * setea el model a los listbox para su actualizacion
   * 
   * @param listbox
   * @param model
   */
  public void refreshList(Listbox listbox, List<?> model) {
    listbox.setModel(new ListModelList(model));
    binder.loadComponent(listbox);
  }

  /**
   * refresca la lista de documentos para las 3 solapas del acordeón
   * 
   * @param inicial
   * @throws Exception
   */
  public void initializeDocumentsList(boolean inicial) throws Exception {
    // this.expedienteElectronico=(ExpedienteElectronico);
    if (this.expedienteElectronico != null) {
      this.ventanaAbierta = (Component) Executions.getCurrent().getDesktop()
          .getAttribute("ventanaAbierta");
      this.cargarDocumentosFiltrados();
      notificarActualizacionListas();
      Executions.getCurrent().getDesktop().setAttribute("eeAcordeon", this.expedienteElectronico);

      if (inicial && this.conPase != null) {

        Events.sendEvent(this.conPase, new Event(Events.ON_SELECT));
        Events.sendEvent(this.conPase, new Event(Events.ON_CLICK));
      }
    }
  }

  public List<DocumentoDTO> cargarDocumentosFiltrados() {
    this.listaDocumento = new ArrayList<>();
    workingTask = (Task) Executions.getCurrent().getDesktop().getAttribute("selectedTask");
    if (workingTask != null) {
      if (!workingTask.getActivityName().equals(TRAMITACION_EN_PARALELO)) {
        for (DocumentoDTO docAsociado : this.expedienteElectronico.getDocumentos()) {
          if (soloLectura != null && soloLectura) {
            if (docAsociado.getDefinitivo()) {
              listaDocumento.add(docAsociado);
            }
          } else {
            listaDocumento.add(docAsociado);
          }
        }

      } else {
        for (DocumentoDTO documentoAsociado : this.expedienteElectronico.getDocumentos()) {
          // Si no tiene idTask es un documento que deben ver todos.
          // Puede
          // ser un documento viejo o una modificacion de caratula.
          if (documentoAsociado.getIdTask() != null) {
            if ((workingTask.getCreateTime()
                .compareTo(documentoAsociado.getFechaAsociacion()) >= 0)
                || (documentoAsociado.getIdTask().equals(this.workingTask.getExecutionId()))) {
              if (soloLectura != null && soloLectura) {
                if (documentoAsociado.getDefinitivo()) {
                  this.listaDocumento.add(documentoAsociado);
                }
              } else {
                this.listaDocumento.add(documentoAsociado);
              }
            }
          } else {
            this.listaDocumento.add(documentoAsociado);
          }
        } // listaDocumento = ordenarDocumentos(listaDocumento);
      }
    } else {
      for (DocumentoDTO docAsociado : this.expedienteElectronico.getDocumentos()) {
        if (docAsociado.getDefinitivo()) {
          listaDocumento.add(docAsociado);
        }
      }
    }

    Collections.reverse(listaDocumento);
    return listaDocumento;
  }

  /**
   * lanza evento que notifica a las listas de documentos que deben actualizarse
   * enviando como parametro la lista solo a las pestañas que hayan sido
   * abiertas
   */
  private void notificarActualizacionListas() {

    Map<String, Object> hm = new HashMap<String, Object>();
    hm.put("model", this.listaDocumento);
    if (ventanaAbierta != null) {
      Events.sendEvent(Events.ON_CLICK, this.ventanaAbierta, hm);
    }
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
   * @throws ErrorConsultaDocumentoException
   * @throws SinPrivilegiosException
   * @throws DocumentoNoExisteException
   * @throws ParametroInvalidoConsultaException
   * 
   * 
   * 
   */

  private byte[] buscarContenidoDeDocumento(DocumentoDTO d, String usuarioLogueado)
      throws ParametroInvalidoConsultaException, DocumentoNoExisteException,
      SinPrivilegiosException, ErrorConsultaDocumentoException {
    RequestExternalConsultaDocumento request = new RequestExternalConsultaDocumento();

    request.setNumeroDocumento(d.getNumeroSade());
    request.setUsuarioConsulta(usuarioLogueado);
    return consultaDocumentoService.consultarDocumentoPdf(request);
  }

  public void onDescargarDocumento() throws Exception {
    descargarDocumento();
  }

  public void descargarDocumento() throws Exception {
    DocumentoDTO documento = this.selectedDocumento;

    String loggedUsername = (String) Executions.getCurrent().getDesktop().getSession()
        .getAttribute(ConstantesWeb.SESSION_USERNAME);
    try {
      puedeDescargarDocumento(documento, loggedUsername);

      byte[] content = this.buscarContenidoDeDocumento(documento, loggedUsername);
      File fichero = new File(documento.getNombreArchivo());
      String tipoFichero = new MimetypesFileTypeMap().getContentType(fichero);
      String nombreArchivo = documento.getNombreArchivo();
      Filedownload.save(content, tipoFichero, nombreArchivo);

    } catch (SinPrivilegiosException ex) {
      Messagebox.show(Labels.getLabel("ee.tramitacion.documentoReservadoNoVisualizable"),
          Labels.getLabel("ee.tramitacion.informacion.documentoNoExiste"), Messagebox.OK,
          Messagebox.EXCLAMATION);
    } catch (DocumentoOArchivoNoEncontradoException e) {
      Messagebox.show(Labels.getLabel("ee.tramitacion.documentoNoExisteEnRepositorio"),
          Labels.getLabel("ee.tramitacion.informacion.documentoNoExiste"), Messagebox.OK,
          Messagebox.EXCLAMATION);

    } catch (Exception e) {
      logger.error("Error al descargar documentos : ", e);
      Messagebox.show(Labels.getLabel("ee.tramitacion.errorDescargaDocumento"),
          Labels.getLabel("ee.tramitacion.titulo.pase"), Messagebox.OK, Messagebox.ERROR);

    }
  }

  public String buscarPathDocumentoViejo(DocumentoDTO documento) {

    String numeroSade = documento.getNumeroSade();
    String pathDocumento = "SADE";
    String pathGedo = BusinessFormatHelper.formarPathWebDavApacheSinEspacio(numeroSade);
    String nombreArchivo = documento.getNombreArchivo();

    return pathDocumento + "/" + pathGedo + "/" + nombreArchivo;
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

    ExpedienteElectronicoDTO ee = this.getExpedienteElectronico();

    Execution auxex = processEngine.getExecutionService().findExecutionById(ee.getIdWorkflow());
    List<Task> listaTareasAux = null;
    if (auxex == null) {
      listaTareasAux = processEngine.getTaskService().createTaskQuery()
          .executionId(ee.getIdWorkflow()).list();
    } else {
      listaTareasAux = processEngine.getTaskService().createTaskQuery()
          .processInstanceId(auxex.getProcessInstance().getId()).list();
    }

    boolean consultaDocumentoPorNumero = true;

    for (Task selectedTask : listaTareasAux) {
      if (selectedTask.getAssignee() != null
          && selectedTask.getAssignee().equals(loggedUsername)) {
        consultaDocumentoPorNumero = calcularLlamadoDeReserva(loggedUsername, documento);
        break;
      }
    }
    if (consultaDocumentoPorNumero) {
      // Acceso a la tabla GEDO_HIST_VISU
      this.consultaDocumentoService.consultarDocumentoPorNumero(request);
    } else {
      // Logica GEDO
      this.consultaDocumentoService.consultarPorNumeroReservaTipoDoc(request);
    }
    
    verificarDocumentoSubsanado(documento, loggedUsername);
  }

  public boolean calcularLlamadoDeReserva(String loggedUsername, DocumentoDTO doc) {
    ExpedienteElectronicoDTO ee = getExpedienteElectronico();
    DocumentoGedoDTO docGedo = documentoGedoService
        .obtenerDocumentoGedoPorNumeroEstandar(doc.getNumeroSade());
    if (!ee.getEsReservado()) {
      return true;
    }

    if (!(ee.getTrata().getTipoReserva().getTipoReserva().equals("PARCIAL"))
        && !(ee.getTrata().getTipoReserva().getTipoReserva().equals("TOTAL"))
        && doc.getDefinitivo() && ee.getEsReservado()
        && (docGedo != null && docGedo.esReservado())) {
      return true;
    }

    if (ee.getEsReservado() && doc.getDefinitivo()) {
      return false;
    }
    // el expediente es reservado y el documento no es definitivo;
    return true;
  }

  private void verificarDocumentoSubsanado(DocumentoDTO documento, String loggedUsername)
      throws SinPrivilegiosException {
    if (documento.isSubsanado()) {
      if (!documento.getUsuarioSubsanador().equals(loggedUsername)) {
        throw new SinPrivilegiosException(loggedUsername);
      }
    }
  }

  /**
   * Ordena la lista de documentos de acuerdo al criterio del más reciente
   * primero.
   * 
   * @param tasks
   *          Lista de tareas a ordenar
   * @return La lista de tareas ordenada de acuerdo al criterio enunciado más
   *         arriba.
   */
  protected List<DocumentoDTO> ordenarDocumentos(final List<DocumentoDTO> documentosFiltrados) {
    Collections.sort(documentosFiltrados, new DocumentoComparator());
    return documentosFiltrados;
  }

  /**
   * Método para mostrar cartel de NO VISUALIZAR el DOCUMENTO
   */
  public void onNoVisualizarDocumentos() throws InterruptedException {

    Messagebox.show(
        Labels.getLabel("ee.tramitacion.documentoConfidencialNoVisualizable") + "\n"
            + Labels.getLabel("ee.tramitacion.disculpeLasMolestias"),
        Labels.getLabel("ee.general.information"), Messagebox.OK, Messagebox.EXCLAMATION);

  }

  final class DocumentoComparator implements Comparator<DocumentoDTO> {
    /**
     * Compara dos instancias de Documento y devuelve la comparación usando el
     * criterio de más reciente primero en el orden.
     * 
     * @param t1
     *          Primer tarea a comparar
     * @param t2
     *          Segunda tarea a comparar con la primera
     */
    public int compare(DocumentoDTO t1, DocumentoDTO t2) {
      return t2.getFechaAsociacion().compareTo(t1.getFechaAsociacion());

    }
  }

  /**
   * muestra el detalle de un documento muesta cartel en caso de error
   * 
   * @throws InterruptedException
   */
  public void onDetalle() throws InterruptedException {
    HashMap<String, Object> hm = new HashMap<String, Object>();
    hm.put("documento", this.selectedDocumento);
    if (this.selectedDocumento.getArchivosDeTrabajo().isEmpty()) {
      Messagebox.show(Labels.getLabel("ee.tramitacion.error.archivoTrabajo"), Labels.getLabel("ee.tramitacion.listheader.fechivoTrabajo"),
          Messagebox.OK, Messagebox.EXCLAMATION);
    } else {
      this.documentoDetalleWindow = (Window) Executions
          .createComponents("/expediente/documentoDetalle.zul", this.self, hm);
      this.documentoDetalleWindow.setClosable(true);
      this.self.getParent().invalidate();
      try {
        this.documentoDetalleWindow.doModal();
      } catch (SuspendNotAllowedException e) {
        logger.error(e.getMessage());
      }
    }
  }

  public void onVisualizarDocumento() throws Exception {

    try {

      String loggedUsername = (String) Executions.getCurrent().getDesktop().getSession()
          .getAttribute(ConstantesWeb.SESSION_USERNAME);
      puedeDescargarDocumento(this.selectedDocumento, loggedUsername);

      Map<String, Object> datos = new HashMap<>();

      ExpedienteElectronicoDTO ee = this.getExpedienteElectronico();

      Execution auxex = processEngine.getExecutionService().findExecutionById(ee.getIdWorkflow());

      List<Task> listaTareasAux = null;
      if (auxex == null) {
        listaTareasAux = processEngine.getTaskService().createTaskQuery()
            .executionId(ee.getIdWorkflow()).list();
      } else {
        listaTareasAux = processEngine.getTaskService().createTaskQuery()
            .processInstanceId(auxex.getProcessInstance().getId()).list();
      }

      for (Task selectedTask : listaTareasAux) {
        if (ee.getEsReservado() || selectedTask.getAssignee() != null
            && selectedTask.getAssignee().equals(loggedUsername)) {
          datos.put("assignee", loggedUsername);
          break;
        }
      }

      datos.put("nombreArchivo", this.selectedDocumento.getNumeroSade().toString());

      Window win = (Window) Executions.createComponents("/consultas/ppVisualizarGedo.zul",
          this.self, datos);

      win.doModal();

    } catch (SinPrivilegiosException e) {

      Messagebox.show(Labels.getLabel("ee.tramitacion.documentoReservadoNoVisualizable"),
          Labels.getLabel("ee.tramitacion.informacion.documentoNoExiste"), Messagebox.OK,
          Messagebox.EXCLAMATION);
    } catch (Exception e) {
      Messagebox.show(
    		  Labels.getLabel("ee.tramitacion.error.previsualizacion"),
          this.selectedDocumento.getNumeroSade().toString().concat(".pdf").toString(),
          Messagebox.OK, Messagebox.ERROR);
    }
  }

  public void onNoVisualizarDocumento() throws SuspendNotAllowedException, InterruptedException {
    Messagebox.show(Labels.getLabel("ee.tramitacion.documentoNoExisteEnRepositorio"),
        Labels.getLabel("ee.tramitacion.informacion.documentoNoExiste"), Messagebox.OK,
        Messagebox.EXCLAMATION);
  }

  /**
   * metodo que se lanza al apretar el boton de desasociar documento delega el
   * trabajo mandandole un evento a tramitacion
   */
  public void onDesasociarDocumentos() {
    Window ventanaPadre = (Window) Executions.getCurrent().getDesktop()
        .getAttribute("ventanaPadre");
    if (ventanaPadre == null || !ventanaPadre.getId().equals("tramitacionWindow")) {
      ventanaPadre = (Window) Executions.getCurrent().getDesktop()
          .getAttribute("ventanaTramitacion");
    }
    
    Events.sendEvent(Events.ON_USER, ventanaPadre, this.selectedDocumento);
    binder.loadAll();
  }

  public void onCambioDeVentana() throws Exception {
    Component ventanaAbierta = (Component) tabBoxDoc.getSelectedPanel().getFirstChild();
    if (ventanaAbierta != null) {
      this.setearVentanaAbierta(ventanaAbierta);
      this.initializeDocumentsList(false);
    }

  }
 
  public void setearVentanaAbierta(Component self) {
    Executions.getCurrent().getDesktop().setAttribute("ventanaAbierta", self);
  }

  /**
   * Método descargar para DocumentoSinPaseComposer, DocumentoConPaseComposer,
   * DocumentosFiltroComposer
   * 
   * @param nombre
   *          Nombre del archivo ZIP
   * @param listaDocumentos
   *          listado de documentos que van adentro del archivo zip
   * @param tipoDescarga
   *          Tipo de descarga que se va a realizar (ejemplos, Sin pase, con
   *          pase, filtro, sale de Constantes.java de ee-web)
   * @param contenido
   *          se crea un archivo filtro.txt con la información de contenido,
   *          este archivo se coloca dentro del archivo zip
   * @throws IOException
   * @throws InterruptedException
   */
  public void onDescargarTodos(String nombre, List<DocumentoDTO> listaDocumentos,
      String tipoDescarga, byte[] contenido) throws IOException, InterruptedException {
    Collections.reverse(listaDocumentos);
    try {
      Zip zip = new Zip(nombre + " " + tipoDescarga);

      if (contenido != null) {
        zip.agregarEntrada(contenido, NOMBRE_TXT_FILTRO);
      }

      String loggedUsername = (String) Executions.getCurrent().getDesktop().getSession()
          .getAttribute(ConstantesWeb.SESSION_USERNAME);

      String nrosDocNoDescargados = new String();
      String docsNoDisponibles = new String();

      int i = 0;
      for (DocumentoDTO doc : listaDocumentos) {

        try {
          puedeDescargarDocumento(doc, loggedUsername);

        } catch (SinPrivilegiosException e) {

          if (!doc.isSubsanadoLimitado() || (doc.getUsuarioSubsanador() != null
              && doc.getUsuarioSubsanador().equals(loggedUsername))) {
            nrosDocNoDescargados += doc.getNumeroSade() + ", ";
          } else {
            nrosDocNoDescargados += doc.getNumeroSade().substring(0, 12) + "XXXX"
                + doc.getNumeroSade().substring(16, 22) + "XXXX" + ", ";
          }

          continue;
        }

        DocumentoDTO documento = doc;
        String numeroSadeConEspacio = documento.getNumeroSade();
        byte[] content = null;

        try {
          content = this.buscarContenidoDeDocumento(documento, loggedUsername);

        } catch (Exception e) {
          docsNoDisponibles += numeroSadeConEspacio + " ";

        }
        if (content != null) {
          i++;
          zip.agregarEntrada(content,
              Utils.toStringValue(i) + " - " + documento.getNombreArchivo());
        }
      }
      Filedownload.save(zip.getZip(), null, nombre + " " + tipoDescarga + ".zip");

      if (!nrosDocNoDescargados.isEmpty()) {
        Messagebox.show(
            Labels.getLabel("ee.tramitacion.documentosNoDescargados",
                new String[] {
                    nrosDocNoDescargados.substring(0, nrosDocNoDescargados.length() - 2) }),
            Labels.getLabel("ee.tramitacion.titulo.pase"), Messagebox.OK, Messagebox.INFORMATION);
      }
      if (!docsNoDisponibles.isEmpty()) {
        Messagebox
            .show(
                Labels.getLabel("ee.tramitacion.documentosNoDisponibles",
                    new String[] {
                        docsNoDisponibles.substring(0, docsNoDisponibles.length() - 2) }),
                Labels.getLabel("ee.tramitacion.titulo.pase"), Messagebox.OK,
                Messagebox.INFORMATION);
      }
    } catch (Exception e) {
      logger.error("Error al descargar documentos : " + nombre, e);
      Messagebox.show(Labels.getLabel("ee.tramitacion.errorDescargaDocumento"),
          Labels.getLabel("ee.tramitacion.titulo.pase"), Messagebox.OK, Messagebox.ERROR);
    }

  }

  public void borrarVariablesDeContexto() {
    Executions.getCurrent().getDesktop().removeAttribute("eeAcordeon");
    Executions.getCurrent().getDesktop().removeAttribute("ventanaAbierta");
    Executions.getCurrent().getDesktop().removeAttribute("acordeonWindow");
    Executions.getCurrent().getDesktop().removeAttribute("ventanaPadre");
  }

  /**
   * GETTERS Y SETTERS
   */
  public DocumentoDTO getSelectedDocumento() {
    return selectedDocumento;
  }

  public void setSelectedDocumento(DocumentoDTO selectedDocumento) {
    this.selectedDocumento = selectedDocumento;
  }

  public ExpedienteElectronicoDTO getExpedienteElectronico() {
    return expedienteElectronico;
  }

  public void setExpedienteElectronico(ExpedienteElectronicoDTO expedienteElectronico) {
    this.expedienteElectronico = expedienteElectronico;
  }

  public List<DocumentoDTO> getListaDocumento() {
    return listaDocumento;
  }

  public void setListaDocumento(List<DocumentoDTO> listaDocumento) {
    this.listaDocumento = listaDocumento;
  }

  public Task getWorkingTask() {
    return workingTask;
  }

  public void setWorkingTask(Task workingTask) {
    this.workingTask = workingTask;
  }

  public IAccesoWebDavService getVisualizaDocumentoService() {
    return visualizaDocumentoService;
  }

  public void setVisualizaDocumentoService(IAccesoWebDavService visualizaDocumentoService) {
    this.visualizaDocumentoService = visualizaDocumentoService;
  }

  public AnnotateDataBinder getBinder() {
    return binder;
  }

  public void setBinder(AnnotateDataBinder binder) {
    this.binder = binder;
  }

  public Component getVentanaAbierta() {
    return ventanaAbierta;
  }

  public void setVentanaAbierta(Component ventanaAbierta) {
    this.ventanaAbierta = ventanaAbierta;
  }
  
}

final class GenericDocumentoEventListener implements EventListener {

  private GenericDocumentoComposer composer;

  public GenericDocumentoEventListener(GenericDocumentoComposer composer) {
    this.composer = composer;
  }

  @SuppressWarnings("unchecked")
  @Override
  public void onEvent(Event event) throws Exception {
    if (event.getName().equals(Events.ON_RENDER)) {
      ExpedienteElectronicoDTO ee = (ExpedienteElectronicoDTO) ((HashMap<String, Object>) event
          .getData()).get("expedienteElectronico");
      Boolean inicial = (Boolean) ((HashMap<String, Object>) event.getData()).get("inicial");
      if (ee != null) {
        this.composer.setExpedienteElectronico(ee);
        this.composer.initializeDocumentsList(true);
      }
    } else if (event.getName().equals(Events.ON_NOTIFY)) {
      this.composer.borrarVariablesDeContexto();
    }
  }

}
