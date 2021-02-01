package com.egoveris.deo.web.satra.produccion;

import com.egoveris.deo.base.exception.ArchivoDuplicadoException;
import com.egoveris.deo.base.exception.FormatoInvalidoException;
import com.egoveris.deo.base.exception.TamanoInvalidoException;
import com.egoveris.deo.base.exception.VariableWorkFlowNoExisteException;
import com.egoveris.deo.base.service.ArchivosEmbebidosService;
import com.egoveris.deo.model.model.ArchivoEmbebidoDTO;
import com.egoveris.deo.model.model.TipoDocumentoDTO;
import com.egoveris.deo.model.model.TipoDocumentoEmbebidosDTO;
import com.egoveris.deo.util.Constantes;
import com.egoveris.deo.web.satra.GEDOGenericForwardComposer;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.jbpm.api.ProcessEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.image.AImage;
import org.zkoss.util.media.AMedia;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
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
import org.zkoss.zul.Paging;
import org.zkoss.zul.Window;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class ArchivosEmbebidosComposer extends GEDOGenericForwardComposer {
  private static final long serialVersionUID = -2184689443240999033L;
  private static final Logger logger = LoggerFactory.getLogger(ArchivosEmbebidosComposer.class);
  public static final String ARCHIVOS_EMBEBIDOS = "archivosEmbebidos";
  public Fileupload uploadArchivoEmbebido;
  private Window archivoEmbebidoWindow;
  private String gestorDocumental;
  @WireVariable("archivosEmbebidosServiceImpl")
  private ArchivosEmbebidosService archivosEmbebidosService;
  public Listbox archivosEmbebidosLb;
  public List<ArchivoEmbebidoDTO> listaArchivosEmbebidos;
  public List<TipoDocumentoEmbebidosDTO> listaTipoDocumentoEmbebidos;
  public ArchivoEmbebidoDTO archivoEmbebido, selectedArchivoEmbebido;
  private TipoDocumentoDTO tipoDocumento;
  public Button guardar, volver;
  public byte[] dataFile;
  public String workflowOrigen = null;
  public String pathRelativo;
  private AnnotateDataBinder binder;
  @WireVariable("processEngine")
  public ProcessEngine processEngine;
  private String loggerUserName;
  private String maxSizeArchivos;
  private Paging pagingArchivoEmbebido;

  public void doAfterCompose(Component component) throws Exception {
    super.doAfterCompose(component);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    loggerUserName = Executions.getCurrent().getSession().getAttribute(Constantes.SESSION_USERNAME)
        .toString();

    listaArchivosEmbebidos = (List<ArchivoEmbebidoDTO>) Executions.getCurrent().getArg()
        .get(ARCHIVOS_EMBEBIDOS);
    this.tipoDocumento = (TipoDocumentoDTO) Executions.getCurrent().getArg().get("tipoDocumento");

  //TODO: se comenta hasta descubrir para que buscar los embebidos si tipoDocumento, si tiene embebidos, ya los trae seteados
//    this.tipoDocumento.setTipoDocumentoEmbebidos(this.archivosEmbebidosService.buscarTipoDocEmbebidos(this.tipoDocumento));

    this.listaTipoDocumentoEmbebidos = new ArrayList<TipoDocumentoEmbebidosDTO>(
        this.tipoDocumento.getTipoDocumentoEmbebidos());
    this.gestorDocumental = (String) SpringUtil.getBean("gestorDocumentalName");

    this.setWorkflowOrigen((String) component.getDesktop().getAttribute("workFlowOrigen"));
    component.getDesktop().removeAttribute("workFlowOrigen");
    this.binder = new AnnotateDataBinder(component);
    this.binder.getAllBindings();
    this.binder.loadAll();
  }

  public void onVisualizarArchivosEmbebido() throws Exception {
    try {
      byte[] contenidoArchivo = null;
      ArchivoEmbebidoDTO archivoEmbebido = this.selectedArchivoEmbebido;

//      if (this.selectedArchivoEmbebido.getIdGuardaDocumental() != null) {
        // WEBDAV
        contenidoArchivo = this.archivosEmbebidosService.obtenerArchivoEmbebidoWebDav(
            archivoEmbebido.getPathRelativo(), archivoEmbebido.getNombreArchivo());
//      }

      Filedownload.save(contenidoArchivo, null, archivoEmbebido.getNombreArchivo());
    } catch (Exception e) {
      logger.error("Error al visualizar archivo embebido " + e.getMessage(), e);
      Messagebox.show(Labels.getLabel("gedo.error.visualizarArchivoRepositorio"),
          Labels.getLabel("gedo.error.obteniendoArchivoRepositorio"), Messagebox.OK,
          Messagebox.EXCLAMATION);
    }
  }

  public void onEliminarArchivoEmbebido() throws Exception {

    ArchivoEmbebidoDTO archivoEmbebido = this.listaArchivosEmbebidos
        .get(archivosEmbebidosLb.getSelectedIndex());

    if (archivoEmbebido.getIdGuardaDocumental() != null) {

      // WEBDAV
      archivosEmbebidosService.borrarArchivoEmbebidoWebDav(archivoEmbebido.getPathRelativo(),
          archivoEmbebido.getNombreArchivo());
    }

    archivosEmbebidosService.eliminarAchivoEmbebido(archivoEmbebido);
    this.listaArchivosEmbebidos.remove(archivoEmbebido);
    binder.loadComponent(archivosEmbebidosLb);
  }

  public void onUpload$uploadArchivoEmbebido(UploadEvent event) throws Exception {

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
        Reader initialReader = media.getReaderData();
        dataFile = IOUtils.toByteArray(initialReader);
      }
    }

    try {
      this.archivosEmbebidosService.verificarArchivo(dataFile, tipoDocumento);
      this.archivoEmbebido = new ArchivoEmbebidoDTO();
      // Valida que se llame desde inicio documento
      if (noInicioDocumento()) {
        pathRelativo = (String) getVariableWorkFlow(Constantes.VAR_ARCHIVO_TEMPORAL_FIRMA);
        this.archivoEmbebido.setIdTask(this.workflowOrigen);
      }
      this.archivoEmbebido.setNombreArchivo(event.getMedia().getName());
      this.archivoEmbebido.setDataArchivo(dataFile);
      this.archivoEmbebido.setUsuarioAsociador(loggerUserName);
      this.archivoEmbebido.setFechaAsociacion(new Date());
      this.archivoEmbebido.setMimetype(this.archivosEmbebidosService.getMimetype(dataFile));
      this.archivoEmbebido.setPeso(this.archivoEmbebido.getDataArchivo().length);

//      if (!Constantes.GESTOR_DOCUMENTAL_FILENET.equals(gestorDocumental)) {
        // Elimina la extension .pdf
        eliminarExtensionPath();
//      }
      this.archivoEmbebido.setPathRelativo(pathRelativo);

      if (listaArchivosEmbebidos == null) {
        listaArchivosEmbebidos = new ArrayList<ArchivoEmbebidoDTO>();
      }
      // Validacion de mismo nombre de archivo embebido
      archivosEmbebidosService.validarNombre(this.listaArchivosEmbebidos, this.archivoEmbebido);
      archivoEmbebido.setId(this.archivosEmbebidosService.guardarArchivoEmbebido(archivoEmbebido));
//      if (Constantes.GESTOR_DOCUMENTAL_FILENET.equals(gestorDocumental)) {
        // WEBDAV
        archivosEmbebidosService.subirArchivoEmbebidoTemporal(archivoEmbebido);
//      }
      this.archivosEmbebidosService.guardarArchivoEmbebido(archivoEmbebido);

      this.archivoEmbebido.setDataArchivo(null);
      this.listaArchivosEmbebidos.add(archivoEmbebido);
      archivosEmbebidosLb.setVisible(true);
      this.binder.loadComponent(archivosEmbebidosLb);
      if (pagingArchivoEmbebido.getActivePage() + 1 != pagingArchivoEmbebido.getPageCount()) {
        pagingArchivoEmbebido.setActivePage(pagingArchivoEmbebido.getPageCount() - 1);
      }
    } catch (FormatoInvalidoException fie) {
      logger.error("Error al cargar archivo de trabajo " + fie.getMessage(), fie);
      Messagebox.show(fie.getMessage(), Labels.getLabel("gedo.general.advertencia"), Messagebox.OK,
          Messagebox.EXCLAMATION);
    } catch (TamanoInvalidoException tie) {
      logger.error("Error al cargar archivo de trabajo " + tie.getMessage(), tie);
      Messagebox.show(tie.getMessage(), Labels.getLabel("gedo.general.advertencia"), Messagebox.OK,
          Messagebox.EXCLAMATION);
    } catch (ArchivoDuplicadoException ade) {
      logger.error("Error al cargar archivo de trabajo " + ade.getMessage(), ade);
      Messagebox.show(ade.getMessage(), Labels.getLabel("gedo.general.advertencia"), Messagebox.OK,
          Messagebox.EXCLAMATION);
    }
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

  private boolean noInicioDocumento() {
    return this.workflowOrigen != null;
  }

  private void eliminarExtensionPath() {
    String espacios[] = pathRelativo.split("-");
    String idAleatorioConExtension = espacios[3];
    String idAleatorioSplit[] = idAleatorioConExtension.split("\\.");

    String idAleatorio = idAleatorioSplit[0];
    StringBuilder url = new StringBuilder("");
    for (int i = 0; i < espacios.length - 1; i++) {
      url.append(espacios[i] + "-");
    }
    url.append(idAleatorio);
    pathRelativo = url.toString();
  }

  public List<ArchivoEmbebidoDTO> getListaArchivosEmbebidos() {
    return listaArchivosEmbebidos;
  }

  public void setListaArchivosEmbebidos(List<ArchivoEmbebidoDTO> listaArchivosEmbebidos) {
    this.listaArchivosEmbebidos = listaArchivosEmbebidos;
  }

  public String getLoggerUserName() {
    return loggerUserName;
  }

  public void setLoggerUserName(String loggerUserName) {
    this.loggerUserName = loggerUserName;
  }

  public Listbox getArchivosEmbebidosLb() {
    return archivosEmbebidosLb;
  }

  public void setArchivosEmbebidosLb(Listbox archivosEmbebidosLb) {
    this.archivosEmbebidosLb = archivosEmbebidosLb;
  }

  public String getWorkflowOrigen() {
    return workflowOrigen;
  }

  public void setWorkflowOrigen(String workflowOrigen) {
    this.workflowOrigen = workflowOrigen;
  }

  public ArchivoEmbebidoDTO getSelectedArchivoEmbebido() {
    return selectedArchivoEmbebido;
  }

  public void setSelectedArchivoEmbebido(ArchivoEmbebidoDTO selectedArchivoEmbebido) {
    this.selectedArchivoEmbebido = selectedArchivoEmbebido;
  }

  public TipoDocumentoDTO getTipoDocumento() {
    return tipoDocumento;
  }

  public void setTipoDocumento(TipoDocumentoDTO tipoDocumento) {
    this.tipoDocumento = tipoDocumento;
  }

  public Window getArchivosEmbebidosWindow() {
    return archivoEmbebidoWindow;
  }

  public void setArchivosEmbebidosWindow(Window archivosEmbebidosWindow) {
    this.archivoEmbebidoWindow = archivosEmbebidosWindow;
  }

  public AnnotateDataBinder getBinder() {
    return binder;
  }

  public void setBinder(AnnotateDataBinder binder) {
    this.binder = binder;
  }

}
