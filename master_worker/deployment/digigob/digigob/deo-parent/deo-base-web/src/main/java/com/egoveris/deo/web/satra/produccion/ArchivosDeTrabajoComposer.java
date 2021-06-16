package com.egoveris.deo.web.satra.produccion;

import com.egoveris.commons.databaseconfiguration.propiedades.AppProperty;
import com.egoveris.deo.base.exception.VariableWorkFlowNoExisteException;
import com.egoveris.deo.base.service.ArchivoDeTrabajoService;
import com.egoveris.deo.model.model.ArchivoDeTrabajoDTO;
import com.egoveris.deo.util.Constantes;
import com.egoveris.deo.web.satra.GEDOGenericForwardComposer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.jbpm.api.ProcessEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;
import org.zkoss.image.AImage;
import org.zkoss.util.media.AMedia;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Fileupload;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ArchivosDeTrabajoComposer extends GEDOGenericForwardComposer {

  private static final long serialVersionUID = 5383365599848543153L;
  private static final Logger logger = LoggerFactory.getLogger(ArchivosDeTrabajoComposer.class);
  public static final String MOSTRAR_UPLOAD = "mostrarUpload";
  public static final String WORK_FLOW_ORIGEN = "workFlowOrigen";
  private byte[] dataFile;
  private ArchivoDeTrabajoDTO archivoDeTrabajo;
  private String loggerUserName;
  private ArchivoDeTrabajoDTO selectedArchivoDeTrabajo;
  private List<ArchivoDeTrabajoDTO> listaArhivosDeTrabajo = null;
  private String pathRelativo;
  @WireVariable("processEngine")
  private ProcessEngine processEngine;
  private String maxSizeArchivos;
  @WireVariable("archivoDeTrabajoServiceImpl")
  private ArchivoDeTrabajoService archivoDeTrabajoService;
  private Listbox archivosTrabajoLb;
  private Fileupload uploadArchivoDeTrabajo;
  private AnnotateDataBinder binder;
  private Window archivoTrabajoWindow;
  private String workflowOrigen = null;
  private String maximoArchivos;
  private Button volver;
  private String gestorDocumental;

  public static final String TIPO_PROD = "tipoProd";

  public String getWorkflowOrigen() {
    return workflowOrigen;
  }

  public void setWorkflowOrigen(String workflowOrigen) {
    this.workflowOrigen = workflowOrigen;
  }

  public void doAfterCompose(Component component) throws Exception {
    super.doAfterCompose(component);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    AppProperty appProperty = (AppProperty) SpringUtil.getBean("dBProperty");
    this.maximoArchivos = appProperty.getString("gedo.maximoArchivos");
    this.gestorDocumental = (String) SpringUtil.getBean("gestorDocumentalName");
    this.setWorkflowOrigen((String) Executions.getCurrent().getArg().get(WORK_FLOW_ORIGEN));
    // TODO implementar
    pathRelativo = (String) Executions.getCurrent().getArg()
        .get(ArchivoDeTrabajoDTO.PATH_RELATIVO);
    listaArhivosDeTrabajo = (List<ArchivoDeTrabajoDTO>) Executions.getCurrent().getArg()
        .get(ArchivoDeTrabajoDTO.ARCHIVOS_DE_TRABAJO);
    // Valida que se llame desde inicio documento
    loggerUserName = Executions.getCurrent().getSession().getAttribute(Constantes.SESSION_USERNAME)
        .toString();

    setMaxSize();

    String tipoProd = (String) Executions.getCurrent().getArg()
        .get(ArchivosDeTrabajoComposer.TIPO_PROD);
    if (tipoProd != null && "IMPORTADO".equals(tipoProd)) {
      this.archivosTrabajoLb.setHeight("230px");
    }

    this.binder = new AnnotateDataBinder(component);
    this.binder.loadAll();
  }

  private void setMaxSize() {
    this.maxSizeArchivos = maximoArchivos;
    StringBuilder upload = new StringBuilder("true,maxsize=");
    upload.append(maxSizeArchivos);
    upload.append(",native");
    uploadArchivoDeTrabajo.setUpload(upload.toString());
  }

  /**
   * Si lista listaArhivosDeTrabajo es vacia, seguro vengo de Inicio documento y
   * el wfOringen es null Si vengo con la lista con datos,
   * 
   * @return
   */
  private boolean noInicioDocumento() {
    return this.workflowOrigen != null;
  }

  public byte[] getDataFile() {
    return dataFile;
  }

  public void setDataFile(byte[] dataFile) {
    this.dataFile = dataFile;
  }

  public String getLoggerUserName() {
    return loggerUserName;
  }

  public void setLoggerUserName(String loggerUserName) {
    this.loggerUserName = loggerUserName;
  }

  public ArchivoDeTrabajoDTO getSelectedArchivoDeTrabajo() {
    return selectedArchivoDeTrabajo;
  }

  public void setSelectedArchivoDeTrabajo(ArchivoDeTrabajoDTO selectedArchivoDeTrabajo) {
    this.selectedArchivoDeTrabajo = selectedArchivoDeTrabajo;
  }

  public AnnotateDataBinder getBinder() {
    return binder;
  }

  public void setBinder(AnnotateDataBinder binder) {
    this.binder = binder;
  }

  public Window getArchivoTrabajoWindow() {
    return archivoTrabajoWindow;
  }

  public void setArchivoTrabajoWindow(Window archivoTrabajoWindow) {
    this.archivoTrabajoWindow = archivoTrabajoWindow;
  }

  public Button getVolver() {
    return volver;
  }

  public void setVolver(Button volver) {
    this.volver = volver;
  }

  public void onClick$volver() {
    if (this.workflowOrigen != null) {
      if ("consultaDocumentosWindow".equals(this.self.getParent().getId().toString()))
        this.archivoTrabajoWindow.detach();
      else
        this.closeAndNotifyAssociatedWindow(null);

    } else {
      Map<String, Object> map = new HashMap<>();
      map.put("origen", Constantes.EVENTO_ARCHIVO_TRABAJO);
      map.put("datos", listaArhivosDeTrabajo);
      this.closeAndNotifyAssociatedWindow(map);
    }
  }

  public ArchivoDeTrabajoDTO getArchivoDeTrabajo() {
    return archivoDeTrabajo;
  }

  public void setArchivoDeTrabajo(ArchivoDeTrabajoDTO archivoDeTrabajo) {
    this.archivoDeTrabajo = archivoDeTrabajo;
  }

  public void onUpload$uploadArchivoDeTrabajo(UploadEvent event) throws Exception {
    if (event.getMedia() instanceof AImage) {
      dataFile = IOUtils.toByteArray(((AImage) event.getMedia()).getStreamData());
    } else {
      AMedia media = (AMedia) event.getMedia();
      if (media.isBinary()) {
        try {
          dataFile = IOUtils.toByteArray(media.getStreamData());
        } catch (IOException e) {
          logger.error("Mensaje de error", e);
        }
      } else {
        dataFile = media.getStringData().getBytes();
      }
    }
    this.archivoDeTrabajo = new ArchivoDeTrabajoDTO();
    // Valida que se llame desde inicio documento
    if (noInicioDocumento()) {
      pathRelativo = (String) getVariableWorkFlow(Constantes.VAR_ARCHIVO_TEMPORAL_FIRMA);
      this.archivoDeTrabajo.setIdTask(this.workflowOrigen);
    }
    this.archivoDeTrabajo.setNombreArchivo(event.getMedia().getName());
    this.archivoDeTrabajo.setDataArchivo(dataFile);
    this.archivoDeTrabajo.setUsuarioAsociador(loggerUserName);
    this.archivoDeTrabajo.setFechaAsociacion(new Date());
    this.archivoDeTrabajo.setPeso(archivoDeTrabajo.getDataArchivo().length);

//    if (!Constantes.GESTOR_DOCUMENTAL_FILENET.equals(gestorDocumental)) {
//      // Elimina la extension .pdf
//      eliminarExtensionPath();
//    }

    this.archivoDeTrabajo.setPathRelativo(pathRelativo);

    if (listaArhivosDeTrabajo == null) {
      listaArhivosDeTrabajo = new ArrayList<>();
    }
    // Validacion de mismo nombre de archivo de trabajo
    if (!(CollectionUtils.isEmpty(listaArhivosDeTrabajo))
        && listaArhivosDeTrabajo.contains(archivoDeTrabajo)) {
      Messagebox.show(
          Labels.getLabel("gedo.archivosDeTrabajoComposer.msgbox.existeArchivo") + " "
              + archivoDeTrabajo.getNombreArchivo(),
          Labels.getLabel("gedo.archivosDeTrabajo.window.titulo"), Messagebox.OK,
          Messagebox.EXCLAMATION);
      return;
    }
    archivoDeTrabajo.setId(this.archivoDeTrabajoService.grabarArchivoDeTrabajo(archivoDeTrabajo));

    this.archivoDeTrabajoService.subirArchivoDeTrabajoTemporal(archivoDeTrabajo);
    
    this.archivoDeTrabajoService.grabarArchivoDeTrabajo(archivoDeTrabajo);
    this.listaArhivosDeTrabajo.add(archivoDeTrabajo);
    archivosTrabajoLb.setVisible(true);
    this.binder.loadComponent(archivosTrabajoLb);

    Map<String, Object> map = new HashMap<>();
    map.put("origen", Constantes.EVENTO_ARCHIVO_TRABAJO);
    map.put("datos", listaArhivosDeTrabajo);
    Events.sendEvent(new Event(Events.ON_NOTIFY, this.self.getParent(), map));
  }

  private void eliminarExtensionPath() {
    String[] espacios = pathRelativo.split("-");
    String idAleatorioConExtension = espacios[3];
    String[] idAleatorioSplit = idAleatorioConExtension.split("\\.");

    String idAleatorio = idAleatorioSplit[0];
    StringBuilder url = new StringBuilder("");
    for (int i = 0; i < espacios.length - 1; i++) {
      url.append(espacios[i] + "-");
    }
    url.append(idAleatorio);
    pathRelativo = url.toString();
  }

  public List<ArchivoDeTrabajoDTO> getListaArhivosDeTrabajo() {
    return this.listaArhivosDeTrabajo;
  }

  public void setListaArhivosDeTrabajo(List<ArchivoDeTrabajoDTO> listaArhivosDeTrabajo) {
    this.listaArhivosDeTrabajo = listaArhivosDeTrabajo;
  }

  private Object getVariableWorkFlow(String name) {
    Object obj = this.processEngine.getExecutionService().getVariable(this.workflowOrigen, name);
    if (obj == null) {
      throw new VariableWorkFlowNoExisteException(
          Labels.getLabel("gedo.archivosEmbebidosComposer.exception.noVariable")
              + this.workflowOrigen + ", " + name);
    }
    return obj;
  }

  public void onEliminarArchivoDeTrabajo() throws Exception {

    archivoDeTrabajo = this.listaArhivosDeTrabajo.get(archivosTrabajoLb.getSelectedIndex());

    if (archivoDeTrabajo.isDefinitivo()) {
      throw new WrongValueException(Labels.getLabel("ee.tramitacion.noeliminar"));
    }

    String idGuardaDocumental = this.selectedArchivoDeTrabajo.getIdGuardaDocumental();

    archivoDeTrabajoService.borrarArchivoDeTrabajoTemporal(archivoDeTrabajo.getPathRelativo(),
        archivoDeTrabajo.getNombreArchivo());

    archivoDeTrabajoService.eliminarAchivoDeTrabajo(archivoDeTrabajo);
    this.listaArhivosDeTrabajo.remove(archivoDeTrabajo);
    binder.loadComponent(archivosTrabajoLb);
  }

  /**
   * Descarga un archivo de trabajo
   * 
   */
  public void onVisualizarArchivosDeTrabajo() throws InterruptedException {

    try {
      byte[] contenidoArchivo;
      archivoDeTrabajo = this.selectedArchivoDeTrabajo;
      String idFilenet = this.selectedArchivoDeTrabajo.getIdGuardaDocumental();
      if (archivoDeTrabajo.isDefinitivo()) {

        // WEBDAV
        contenidoArchivo = this.archivoDeTrabajoService.obtenerArchivoDeTrabajoWebDav(
            archivoDeTrabajo.getPathRelativo(), archivoDeTrabajo.getNombreArchivo());

      } else {

        // WEBDAV
        contenidoArchivo = this.archivoDeTrabajoService.obtenerArchivoDeTrabajoTemporalWebDav(
            archivoDeTrabajo.getPathRelativo(), archivoDeTrabajo.getNombreArchivo());

      }
      Filedownload.save(contenidoArchivo, null, archivoDeTrabajo.getNombreArchivo());
    } catch (Exception e) {
      logger.error("Error al visualizar archivo de trabajo " + e.getMessage(), e);
      Messagebox.show(Labels.getLabel("ee.tramitacion.archivoNoExisteEnRepositorio"),
          Labels.getLabel("ee.tramitacion.informacion.archivoNoExiste"), Messagebox.OK,
          Messagebox.EXCLAMATION);
    }
  }
}
