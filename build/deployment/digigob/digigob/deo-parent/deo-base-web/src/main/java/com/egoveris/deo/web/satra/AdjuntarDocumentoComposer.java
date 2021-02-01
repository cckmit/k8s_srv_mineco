package com.egoveris.deo.web.satra;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.jbpm.api.ProcessEngine;
import org.jbpm.api.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.image.AImage;
import org.zkoss.util.media.AMedia;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Toolbarbutton;

import com.egoveris.deo.base.exception.ValidacionContenidoException;
import com.egoveris.deo.base.service.DocumentoAdjuntoService;
import com.egoveris.deo.base.service.GenerarDocumentoService;
import com.egoveris.deo.base.service.PdfService;
import com.egoveris.deo.base.util.GeneradorDocumentoFactory;
import com.egoveris.deo.model.model.DocumentoAdjuntoDTO;
import com.egoveris.deo.model.model.TipoDocumentoDTO;
import com.egoveris.deo.util.Constantes;
import com.egoveris.shareddocument.base.exception.WebDAVResourceNotFoundException;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class AdjuntarDocumentoComposer extends GenericForwardComposer {

  private static final long serialVersionUID = 3788368145420747969L;
  private static final Logger LOGGER = LoggerFactory.getLogger(AdjuntarDocumentoComposer.class);

  @WireVariable("processEngine")
  private ProcessEngine processEngine;
  private Task workingTask = null;
  @WireVariable("documentoAdjuntoServiceImpl")
  private DocumentoAdjuntoService documentoAdjuntoService;
  @WireVariable("generarDocumentoServiceImpl")
  private GenerarDocumentoService generarDocumentoService;
  private byte[] dataFile;
  @WireVariable("pdfServiceImpl")
  private PdfService pdfService;
  private TipoDocumentoDTO tipoDocumento;
  private Button uploadDocumento;
  private Toolbarbutton eliminarDocumento;
  private Label nombreArchivoLabel;

  private DocumentoAdjuntoDTO documentoAdjunto = null;
  private DocumentoAdjuntoDTO documentoAdjuntoLevantado = null;

  protected String pathFileTemporal;
  protected String maxSizeArchivos;
  protected String maximoArchivos;

  private String gestorDocumental;

  @Override
  public void doAfterCompose(Component comp) throws Exception {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("doAfterCompose(Component) - start"); //$NON-NLS-1$
    }

    super.doAfterCompose(comp);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    comp.addEventListener(Events.ON_NOTIFY, new AdjuntarDocumentoComposerListener(this));

    this.setWorkingTask((Task) this.arg.get("workingTask"));

    tipoDocumento = (TipoDocumentoDTO) this.arg.get("tipoDocumento");

    armarPantalla();

    this.maximoArchivos = String.valueOf(tipoDocumento.getSizeImportado());
    this.setMaxSize();

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("doAfterCompose(Component) - end"); //$NON-NLS-1$
    }
  }

  /**
   * Establece el máximo de bytes soportados para subir archivos al servidor.
   */
  private void setMaxSize() {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("setMaxSize() - start"); //$NON-NLS-1$
    }

    this.maxSizeArchivos = maximoArchivos;
    StringBuilder upload = new StringBuilder("true,maxsize=");
    upload.append(maxSizeArchivos);
    upload.append(",native");
    uploadDocumento.setUpload(upload.toString());

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("setMaxSize() - end"); //$NON-NLS-1$
    }
  }

  private void armarPantalla() throws InterruptedException {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("armarPantalla() - start"); //$NON-NLS-1$
    }

    List<DocumentoAdjuntoDTO> documentosAdjuntos = this.documentoAdjuntoService
        .buscarArchivosDeTrabajoPorProceso(this.workingTask.getExecutionId());

    /**
     * FIXME Problema para agregar un imagen en un Toolbarbutton Esto sucede en
     * todas las versiones de ZK. Se adjunta info de como solucionarlo para las
     * versiones anteriores a la 6. Hay que setear un ancho fijo (Width) al
     * botón. Para las versiones 6 en adelante hay un tag especial. Ver info
     * adjunta
     * 
     * http://tracker.zkoss.org/browse/ZK-520
     */

    this.eliminarDocumento.setImage("/imagenes/EliminarDocumentoAdjunto.png");

    if (!documentosAdjuntos.isEmpty()) {
      nombreArchivoLabel.setValue(documentosAdjuntos.get(0).getNombreArchivo());
      documentoAdjuntoLevantado = documentosAdjuntos.get(0);
      this.uploadDocumento.setUpload("false");
      this.uploadDocumento.setDisabled(true);
      this.uploadDocumento.invalidate();
      this.eliminarDocumento.setVisible(true);

      try {
        // WEBDAV
        this.dataFile = this.documentoAdjuntoService.obtenerDocumentoAdjuntoTemporalWebDav(
            documentoAdjuntoLevantado.getPathRelativo(),
            documentoAdjuntoLevantado.getNombreArchivo());

        if (this.dataFile != null) {
          notificarPadre();
        }
      } catch (WebDAVResourceNotFoundException e) {
        LOGGER.error("Error al armar pantalla de documentos adjuntos " + e.getMessage(), e);
        this.documentoAdjuntoService.eliminarDocumentoAdjuntoBD(documentosAdjuntos.get(0));

        resetControles();

        Messagebox.show(Labels.getLabel("gedo.importarDocumento.errorAlRecuperarDocumento"),
            Labels.getLabel("gedo.general.advertencia"), Messagebox.OK, Messagebox.ERROR);
      } catch (Exception e) {
        if (LOGGER.isDebugEnabled()) {
          LOGGER.debug("Error al obtener el archivo del repositorio"); //$NON-NLS-1$
        }
        LOGGER.error("Mensaje de error", e);
      }
    } else {
      resetControles();
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("armarPantalla() - end"); //$NON-NLS-1$
    }
  }

  /**
   * Adjunta el archivo que se desea importar y lo convierte a .pdf.
   * 
   * @param event
   * @throws InterruptedException
   * @throws IOException
   */
  public void onUpload$uploadDocumento(UploadEvent event) throws Exception {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("onUpload$uploadDocumento(UploadEvent) - start"); //$NON-NLS-1$
    }

    Clients.showBusy(Labels.getLabel("gedo.importarDocumento.procesandoUpload"));

    if (event.getMedia() instanceof AImage) {
      dataFile = IOUtils.toByteArray(((AImage) event.getMedia()).getStreamData());
    } else {
      org.zkoss.util.media.AMedia media = (AMedia) event.getMedia();
      if (media.isBinary()) {
        try {
          dataFile = IOUtils.toByteArray(media.getStreamData());
        } catch (IOException ioe) {
          LOGGER.error("Mensaje de error", ioe);
          Messagebox.show(Labels.getLabel("gedo.general.errorUpload"),
              Labels.getLabel("gedo.general.error"), Messagebox.OK, Messagebox.ERROR);
        }
      } else {
        dataFile = media.getStringData().getBytes();
      }
    }

    String nombreArchivoUpload = event.getMedia().getName();

    llenarDocumentoAdjunto(nombreArchivoUpload);

    String tipoContenido = null;
    inicializarGeneradorDocumento();
    try {
      tipoContenido = FilenameUtils.getExtension(nombreArchivoUpload.toLowerCase());
      if ("pdf".equals(tipoContenido)) {

        this.generarDocumentoService.validarContenidoDocumento(tipoContenido);

        if (tipoDocumento.getEsFirmaExterna()) {
          if (!"pdf".equals(tipoContenido)) {
            Messagebox.show(Labels.getLabel("gedo.importarDocumento.formatoArchivoInvalido"),
                Labels.getLabel("gedo.general.information"), Messagebox.OK,
                Messagebox.INFORMATION);
            resetControles();
          } else if ("pdf".equals(tipoContenido) && !this.pdfService.estaFirmado(dataFile)) {
            Messagebox.show(Labels.getLabel("gedo.importarDocumento.documentoNoFirmado"),
                Labels.getLabel("gedo.general.information"), Messagebox.OK,
                Messagebox.INFORMATION);
            resetControles();
          } else {
            this.nombreArchivoLabel.setValue(nombreArchivoUpload);
            this.eliminarDocumento.setVisible(true);
            this.uploadDocumento.setDisabled(true);
            this.uploadDocumento.setUpload("false");
            this.uploadDocumento.invalidate();
          }
        } else {
          if ("pdf".equals(tipoContenido)
              && this.pdfService.estaFirmadoOConEspaciosDeFirma(dataFile)) {
            Messagebox.show(
                Labels.getLabel("gedo.importarDocumento.documentoFirmadoOPendienteDeFirma"),
                Labels.getLabel("gedo.general.information"), Messagebox.OK,
                Messagebox.INFORMATION);
            nombreArchivoLabel
                .setValue(Labels.getLabel("gedo.digitalizarDocumento.ningunArchivo"));
            this.dataFile = null;
          } else {
            this.nombreArchivoLabel.setValue(nombreArchivoUpload);
            this.eliminarDocumento.setVisible(true);
            this.uploadDocumento.setDisabled(true);
            this.uploadDocumento.setUpload("false");
            this.uploadDocumento.invalidate();
          }
        }

      } else {
        Messagebox.show(Labels.getLabel("gedo.importarDocumento.formatoDeArchivoNoValido"),
            Labels.getLabel("gedo.error.cargaFormatoArchivo"), Messagebox.OK,
            Messagebox.EXCLAMATION);

      }
    } catch (ValidacionContenidoException vce) {
      LOGGER.error("Error al subir documento adjunto " + vce.getMessage(), vce);
      this.nombreArchivoLabel.setValue(Labels.getLabel("gedo.digitalizarDocumento.ningunArchivo"));
      this.dataFile = null;
      Messagebox.show(Labels.getLabel("gedo.importarDocumento.formatoArchivoInvalido"),
          Labels.getLabel("gedo.error.carga"), Messagebox.OK, Messagebox.ERROR);
    } catch (Exception e) {
      LOGGER.error("Error en validación de contenido en importación", e);
      throw new Exception(
          Labels.getLabel("gedo.adjuntarDocumentoComposer.msgbox.errorValidacionDeContenido"), e);
    }
    Clients.clearBusy();
    notificarPadre();

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("onUpload$uploadDocumento(UploadEvent) - end"); //$NON-NLS-1$
    }
  }

  private void llenarDocumentoAdjunto(String nombreArchivoUpload) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("llenarDocumentoAdjunto(String) - start"); //$NON-NLS-1$
    }

    String pathRelativo;
    this.documentoAdjunto = new DocumentoAdjuntoDTO();

    pathRelativo = (String) processEngine.getExecutionService()
        .getVariable(this.workingTask.getExecutionId(), Constantes.VAR_ARCHIVO_TEMPORAL_FIRMA);
    this.documentoAdjunto.setIdTask(this.workingTask.getExecutionId());
    this.documentoAdjunto.setNombreArchivo(nombreArchivoUpload);
    this.documentoAdjunto.setDataArchivo(dataFile);
    this.documentoAdjunto.setUsuarioAsociador(
        Executions.getCurrent().getSession().getAttribute(Constantes.SESSION_USERNAME).toString());
    this.documentoAdjunto.setFechaAsociacion(new Date());
    this.documentoAdjunto.setDefinitivo(false);

    if (!Constantes.GESTOR_DOCUMENTAL_FILENET.equals(gestorDocumental)) {
      // Elimina la extension .pdf
      pathRelativo = eliminarExtensionPath(pathRelativo);
    }
    this.documentoAdjunto.setPathRelativo(pathRelativo);

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("llenarDocumentoAdjunto(String) - end"); //$NON-NLS-1$
    }
  }

  public void impactarCambios() {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("impactarCambios() - start"); //$NON-NLS-1$
    }

    // HABIA UN DOCUMENTO GUARDADO Y SE CARGO UN DOCUMENTO NUEVO
    if (this.documentoAdjunto != null && this.documentoAdjuntoLevantado != null) {

      // Elimino el documento de la base
      this.documentoAdjuntoService.eliminarDocumentoAdjuntoBD(documentoAdjuntoLevantado);

      // WEBDAV
      this.documentoAdjuntoService.borrarDocumentoAdjuntoTemporalWebDav(
          documentoAdjuntoLevantado.getPathRelativo(),
          documentoAdjuntoLevantado.getNombreArchivo());

      grabarDatosArchivo(this.documentoAdjunto);
      
    } else if (this.documentoAdjunto != null) {
    	grabarDatosArchivo(this.documentoAdjunto);
    }

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("impactarCambios() - end"); //$NON-NLS-1$
    }
  }

  private void grabarDatosArchivo(DocumentoAdjuntoDTO documentoAdjunto) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("grabarDatosArchivo(DocumentoAdjuntoDTO) - start"); //$NON-NLS-1$
    }

    // WEBDAV
    // Impacto el documento en la base
    Integer documentoAdjuntoId = this.documentoAdjuntoService.grabarDocumentoAdjuntoBD(documentoAdjunto);
    documentoAdjunto.setId(documentoAdjuntoId);
    // Impacto el documento en la WebDav
    this.documentoAdjuntoService.subirDocumentoAdjuntoWebDav(documentoAdjunto);

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("grabarDatosArchivo(DocumentoAdjuntoDTO) - end"); //$NON-NLS-1$
    }
  }

  private String eliminarExtensionPath(String pathRelativo) {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("eliminarExtensionPath(String) - start"); //$NON-NLS-1$
    }

    String pathRel = pathRelativo;
    String[] espacios = pathRel.split("-");
    String idAleatorioConExtension = espacios[3];
    String[] idAleatorioSplit = idAleatorioConExtension.split("\\.");

    String idAleatorio = idAleatorioSplit[0];
    StringBuilder url = new StringBuilder("");
    for (int i = 0; i < espacios.length - 1; i++) {
      url.append(espacios[i] + "-");
    }
    url.append(idAleatorio);
    pathRel = url.toString();

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("eliminarExtensionPath(String) - end"); //$NON-NLS-1$
    }
    return pathRel;
  }

  /**
   * Al eliminar un documento cargado, se resetean los controles para que se
   * realice una nueva subida.
   */
  public void onClick$eliminarDocumento() {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("onClick$eliminarDocumento() - start"); //$NON-NLS-1$
    }

    Messagebox.show(Labels.getLabel("gedo.producirDocumento.seguroDeEliminar"),
        Labels.getLabel("gedo.adjuntarDocumentoComposer.msgbox.borrarArchivo"),
        Messagebox.YES | Messagebox.NO, Messagebox.QUESTION, new EventListener() {
          public void onEvent(Event evt) {
            if (LOGGER.isDebugEnabled()) {
              LOGGER.debug("$EventListener.onEvent(Event) - start"); //$NON-NLS-1$
            }

            switch (((Integer) evt.getData()).intValue()) {
            case Messagebox.YES:
              resetControles();

              notificarPadre();

              if (documentoAdjunto == null) {
                // Se pide eliminar el documento que esta
                // grabada en la base de datos
                // documentoAdjuntoLevantado = null;
              } else {
                // Se pide eliminar un documento todavia no
                // grabado
                documentoAdjunto = null;
              }

              break;
            case Messagebox.NO:
              return;
            }

            if (LOGGER.isDebugEnabled()) {
              LOGGER.debug("$EventListener.onEvent(Event) - end"); //$NON-NLS-1$
            }
          }
        });

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("onClick$eliminarDocumento() - end"); //$NON-NLS-1$
    }
  }

  /**
   * Resetea los controles de las pantallas y los deja inicializados.
   */
  private void resetControles() {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("resetControles() - start"); //$NON-NLS-1$
    }

    this.dataFile = null;
    nombreArchivoLabel.setValue(Labels.getLabel("gedo.digitalizarDocumento.ningunArchivo"));
    eliminarDocumento.setVisible(false);
    uploadDocumento.setDisabled(false);
    uploadDocumento.setUpload("true");
    this.setMaxSize();

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("resetControles() - end"); //$NON-NLS-1$
    }
  }

  /**
   * Envia el evento al padre para indicarle la subida o la eliminacion de un
   * archivo.
   */
  private void notificarPadre() {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("notificarPadre() - start");  //$NON-NLS-1$
    }

    Map<String, Object> datos = new HashMap<>();
    datos.put("dataFile", this.dataFile);
    datos.put("nombreArchivoLabel", this.nombreArchivoLabel);
    Events.sendEvent(new Event(Events.ON_UPLOAD, this.self.getParent(), datos));

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("notificarPadre() - end"); //$NON-NLS-1$
    }
  }

  /**
   * Obtiene la instancia de la clase responsable de la generación del documento
   */
  private void inicializarGeneradorDocumento() {
    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("inicializarGeneradorDocumento() - start"); //$NON-NLS-1$
    }

    GeneradorDocumentoFactory generadorDocumentoFactory = (GeneradorDocumentoFactory) SpringUtil
        .getBean("generadorDocumentoFactory");
    this.generarDocumentoService = generadorDocumentoFactory
        .obtenerGeneradorDocumento(this.tipoDocumento);

    if (LOGGER.isDebugEnabled()) {
      LOGGER.debug("inicializarGeneradorDocumento() - end"); //$NON-NLS-1$
    }
  }

  public void setWorkingTask(Task workingTask) {
    this.workingTask = workingTask;
  }

  public Task getWorkingTask() {
    return workingTask;
  }
}

final class AdjuntarDocumentoComposerListener implements EventListener {
  /**
   * Logger for this class
   */
  private static final Logger logger = LoggerFactory
      .getLogger(AdjuntarDocumentoComposerListener.class);

  private AdjuntarDocumentoComposer composer;

  public AdjuntarDocumentoComposerListener(AdjuntarDocumentoComposer comp) {
    this.composer = comp;
  }

  @Override
  public void onEvent(Event event) throws Exception {
    if (logger.isDebugEnabled()) {
      logger.debug("onEvent(Event) - start"); //$NON-NLS-1$
    }

    if (event.getName().equals(Events.ON_NOTIFY)) {
      composer.impactarCambios();
    }

    if (logger.isDebugEnabled()) {
      logger.debug("onEvent(Event) - end"); //$NON-NLS-1$
    }
  }
}
