package com.egoveris.deo.web.satra;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.image.AImage;
import org.zkoss.util.media.AMedia;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.UploadEvent;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Radio;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

import com.egoveris.commons.databaseconfiguration.propiedades.AppProperty;
import com.egoveris.deo.base.exception.PDFConversionException;
import com.egoveris.deo.base.exception.ValidacionContenidoException;
import com.egoveris.deo.base.service.FirmaConjuntaService;
import com.egoveris.deo.base.service.GenerarDocumentoService;
import com.egoveris.deo.base.service.GestionArchivosWebDavService;
import com.egoveris.deo.base.service.HistorialService;
import com.egoveris.deo.base.service.PdfService;
import com.egoveris.deo.base.service.TipoDocumentoService;
import com.egoveris.deo.base.util.GeneradorDocumentoFactory;
import com.egoveris.deo.model.model.DocumentoMetadataDTO;
import com.egoveris.deo.model.model.RequestGenerarDocumento;
import com.egoveris.deo.model.model.TipoDocumentoDTO;
import com.egoveris.deo.util.Constantes;
import com.egoveris.deo.web.satra.macros.PrevisualizacionDocumento;
import com.egoveris.sharedsecurity.base.model.Usuario;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public abstract class ImportacionDocumentoComposer extends ValidarApoderamientoComposer {

  /**
  * 
  */
  private static final long serialVersionUID = 3139264099281735872L;

  private static transient Logger logger = LoggerFactory
      .getLogger(ImportacionDocumentoComposer.class);

  protected Window agregarUsuariosFirmaConjuntaView;
  protected Grid firmaGrid;
  protected Grid firmaConjuntaGrid;
  protected Textbox usuariosAgregadosTxt;
  protected Toolbarbutton usuariosFirmaConjunta;
  protected Combobox usuarioFirmante;
  protected Textbox motivo;
  protected Radio enviarFirmar;
  protected Radio firmar;
  protected Checkbox solicitudAvisoFirma;
  protected Label nombreArchivoLabel;
  protected Toolbarbutton uploadDocumento;
  protected Toolbarbutton previsualizar;
  protected Toolbarbutton importar;
  protected Checkbox solicitudEnvioCorreo;

  protected TipoDocumentoDTO selectedTipoDocumento;
  protected List<Usuario> usuariosFirmantes;
  protected List<DocumentoMetadataDTO> listaDocMetadata;
  protected AnnotateDataBinder importarBinder;
  @WireVariable("tipoDocumentoServiceImpl")
  protected TipoDocumentoService tipoDocumentoService;
  @WireVariable("firmaConjuntaServiceImpl")
  protected FirmaConjuntaService firmaConjuntaService;
  @WireVariable("pdfServiceImpl")
  protected PdfService pdfService;
  @WireVariable("generarDocumentoServiceImpl")
  protected GenerarDocumentoService generarDocumentoService;
  protected GeneradorDocumentoFactory generadorDocumentoFactory;
  @WireVariable("gestionArchivosWebDavServiceImpl")
  protected GestionArchivosWebDavService gestionArchivosWebDavService;
  @WireVariable("historialServiceImpl")
  protected HistorialService historialService;

  protected byte[] dataFile;
  protected String pathFileTemporal;
  protected String maxSizeArchivos;
  protected String maximoPrevisualizacion;
  protected String maximoArchivos;

  protected Window adjuntarDocumento;

  /**
   * Obtiene el identificador de la ventana padre, necesario para las ventanas
   * modales.
   * 
   * @return
   */
  protected abstract Window getVentanaPadre();

  protected abstract void resetControles();

  public void doAfterCompose(Component component) throws Exception {
    super.doAfterCompose(component);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    AppProperty appProperty = (AppProperty) SpringUtil.getBean("dBProperty");
    this.maximoPrevisualizacion = appProperty.getString("gedo.maximoPrevisualizacion");
    this.maximoArchivos = appProperty.getString("gedo.maximoArchivos");
    this.uploadDocumento.setDisabled(true);
    this.importarBinder = new AnnotateDataBinder(component);
    this.importarBinder.loadAll();

  }

  /**
   * Reiniciar componentes visuales
   */
  protected void reiniciarControles() {
    this.importar.setDisabled(true);
    this.previsualizar.setDisabled(true);
    this.enviarFirmar.setSelected(false);
    this.firmar.setSelected(false);
    this.enviarFirmar.setDisabled(true);
    this.firmar.setDisabled(true);
    this.usuarioFirmante.setDisabled(true);
    this.usuarioFirmante.setSelectedItem(null);
    this.motivo.setText(null);
    this.dataFile = null;
    this.solicitudAvisoFirma.setChecked(true);
    this.firmaConjuntaGrid.setVisible(false);
    this.firmaGrid.setVisible(false);
    this.usuariosAgregadosTxt.setValue(Labels.getLabel("gedo.general.noUsuariosFirmantes"));
    this.usuariosFirmantes = null;
    this.uploadDocumento.setDisabled(true);
    this.uploadDocumento.setUpload("false");
    this.uploadDocumento.invalidate();
    this.nombreArchivoLabel.setValue(Labels.getLabel("gedo.digitalizarDocumento.ningunArchivo"));
    this.selectedTipoDocumento = null;
  }

  /**
   * Actualiza estado de componentes dependiendo de sí el tipo de documento
   * soporta firma conjunta
   * 
   * @throws InterruptedException
   */
  protected void prepararComponentesFirma() {
    if (this.selectedTipoDocumento.getEsFirmaConjunta()) {
      this.firmaGrid.setVisible(false);
      this.firmaConjuntaGrid.setVisible(true);
      this.usuariosFirmantes = null;
      this.usuariosAgregadosTxt.setValue(Labels.getLabel("gedo.general.noUsuariosFirmantes"));
      this.solicitudAvisoFirma.setLabel(Labels.getLabel("gedo.general.envioAvisoFirma"));
      this.solicitudEnvioCorreo.setDisabled(false);
      this.previsualizar.setDisabled(true);
      this.importar.setDisabled(true);
    } else {
      if (!this.selectedTipoDocumento.getEsFirmaExterna()) {
        this.firmaGrid.setVisible(true);
        this.firmaConjuntaGrid.setVisible(false);
        this.solicitudAvisoFirma.setLabel(Labels.getLabel("gedo.general.envioAvisoFirma"));
        this.solicitudEnvioCorreo.setDisabled(false);
        this.previsualizar.setDisabled(true);
        this.importar.setDisabled(true);
      } else {
        this.solicitudAvisoFirma.setLabel(Labels.getLabel("gedo.general.importar"));
        this.solicitudEnvioCorreo.setDisabled(true);
        this.firmaGrid.setVisible(false);
        this.firmaConjuntaGrid.setVisible(false);
        this.previsualizar.setDisabled(false);
        this.importar.setDisabled(false);
      }
    }
  }

  /**
   * Obtiene la instancia de la clase responsable de la generación del documento
   */
  private void inicializarGeneradorDocumento() {
    GeneradorDocumentoFactory generadorDocumentoFactory = (GeneradorDocumentoFactory) SpringUtil
        .getBean("generadorDocumentoFactory");
    this.generarDocumentoService = generadorDocumentoFactory
        .obtenerGeneradorDocumento(this.selectedTipoDocumento);
  }

  /**
   * Prepara las variables que se van a almacenar en el workflow.
   * 
   * @return
   */
  protected Map<String, Object> prepararVariablesWorkFlow() {
    Map<String, Object> variables = new HashMap<>();
    variables.put(Constantes.VAR_USUARIO_CREADOR, getCurrentUser());
    variables.put(Constantes.VAR_MOTIVO, motivo.getValue());
    variables.put(Constantes.VAR_TIPO_DOCUMENTO, this.selectedTipoDocumento.getId().toString());
    variables.put(Constantes.VAR_DOCUMENTO_DATA, this.listaDocMetadata == null
        ? new ArrayList<DocumentoMetadataDTO>() : this.listaDocMetadata);
    // Adicionar receptores de aviso de firma si así lo solicita el usuario.
    List<String> receptores = new ArrayList<>();
    if (this.solicitudAvisoFirma.isChecked())
      receptores.add(getCurrentUser());
    variables.put(Constantes.VAR_RECEPTORES_AVISO_FIRMA, receptores);
    variables.put(Constantes.VAR_ARCHIVO_TEMPORAL_FIRMA, this.pathFileTemporal);
    variables.put(Constantes.VAR_USUARIO_PRODUCTOR, getCurrentUser());
    variables.put(Constantes.VAR_USUARIO_DERIVADOR, getCurrentUser());
    variables.put(Constantes.VAR_SOLICITUD_ENVIO_MAIL, solicitudEnvioCorreo.isChecked());
    variables.put(Constantes.VAR_NUMERO_SADE_PAPEL, "");
    if (this.selectedTipoDocumento.getEsFirmaConjunta()) {
      variables.put(Constantes.VAR_NUMERO_FIRMAS, this.usuariosFirmantes.size());
      variables.put(Constantes.VAR_USUARIO_FIRMANTE, this.usuariosFirmantes.get(0).getUsername());
    } else {
      String usuario;
      if (!this.enviarFirmar.isChecked())
        usuario = getCurrentUser();
      else {
        usuario = ((Usuario) this.usuarioFirmante.getSelectedItem().getValue()).getUsername();
      }
      variables.put(Constantes.VAR_USUARIO_FIRMANTE, usuario);
    }
    return variables;
  }

  /**
   * Invoca ventana para agregación de usuarios que participan en firma
   * conjunta.
   * 
   * @throws InterruptedException
   */
  public void onAgregarUsuariosFirmaConjunta() throws InterruptedException {
    if (this.agregarUsuariosFirmaConjuntaView != null) {
      Map<String, Object> datos = new HashMap<>();
      datos.put("usuariosAgregados", this.usuariosFirmantes);
      datos.put("tipoDocumento", this.selectedTipoDocumento);
      this.agregarUsuariosFirmaConjuntaView.invalidate();
      this.agregarUsuariosFirmaConjuntaView = (Window) Executions
          .createComponents("agregarUsuariosFirmaConjunta.zul", this.self, datos);
      this.agregarUsuariosFirmaConjuntaView.setParent(this.getVentanaPadre());
      this.agregarUsuariosFirmaConjuntaView.setPosition("center");
      this.agregarUsuariosFirmaConjuntaView.setVisible(true);
      try {
        this.agregarUsuariosFirmaConjuntaView.doModal();
      } catch (Exception e) {
        logger.error("Error al abrir GUI", e);
        Messagebox.show(Labels.getLabel("gedo.supervisadosComposer.msg.noPosibleIniciarVista"),
        		Labels.getLabel("gedo.general.titulo.error.comunicacion"), Messagebox.OK, Messagebox.ERROR);
      }
    } else {
      Messagebox.show(Labels.getLabel("gedo.supervisadosComposer.msg.noPosibleIniciarVista"),
    		  Labels.getLabel("gedo.general.titulo.error.comunicacion"), Messagebox.OK, Messagebox.ERROR);
    }
  }

  /**
   * Actualiza el TextBox correspondiente a los usuarios agregados para firmar
   */
  protected void actualizarUsuariosFirmantes(List<Usuario> usuariosFirmaConjunta)
      throws InterruptedException {
    if (usuariosFirmaConjunta != null && !usuariosFirmaConjunta.isEmpty()) {
      setUsuariosFirmantes(usuariosFirmaConjunta);
      this.usuariosAgregadosTxt.setMultiline(true);
      this.usuariosAgregadosTxt.setRows(this.usuariosFirmantes.size() + 1);
      StringBuilder listaUsuarios = new StringBuilder();
      for (Usuario datosUsuarioBean : usuariosFirmaConjunta) {
        listaUsuarios.append(datosUsuarioBean.getNombreApellido());
        if (usuariosFirmaConjunta.indexOf(datosUsuarioBean) == usuariosFirmaConjunta.size() - 1) {
          listaUsuarios.append("(" + datosUsuarioBean.getUsername() + ")");
        } else {
          listaUsuarios.append("(" + datosUsuarioBean.getUsername() + "),\n");
        }
      }
      this.usuariosAgregadosTxt.setValue(listaUsuarios.toString());
    } else
      this.usuariosAgregadosTxt.setValue(Labels.getLabel("gedo.general.noUsuariosFirmantes"));
    this.importarBinder.loadComponent(this.usuariosAgregadosTxt);
  }

  /**
   * Adjunta el archivo que se desea importar y lo convierte a .pdf.
   * 
   * @param event
   * @throws InterruptedException
   * @throws IOException
   */
  public void onUploadFile(UploadEvent event) throws Exception {
    if (event.getMedia() instanceof AImage) {
      dataFile = IOUtils.toByteArray(((AImage) event.getMedia()).getStreamData());
    } else {
      org.zkoss.util.media.AMedia media = (AMedia) event.getMedia();
      if (media.isBinary()) {
        try {
          dataFile = IOUtils.toByteArray(media.getStreamData());
        } catch (IOException ioe) {
          logger.error("Error uploading file", ioe);
          Messagebox.show(Labels.getLabel("gedo.general.errorUpload"),
              Labels.getLabel("gedo.general.error"), Messagebox.OK, Messagebox.ERROR);
        }
      } else {
        dataFile = media.getStringData().getBytes();
      }
    }
    String nombreArchivoUpload = event.getMedia().getName();
    String tipoContenido = null;
    inicializarGeneradorDocumento();
    try {
      tipoContenido = FilenameUtils.getExtension(nombreArchivoUpload.toLowerCase());
      this.generarDocumentoService.validarContenidoDocumento(tipoContenido);

      if ("pdf".equals(tipoContenido) && (this.selectedTipoDocumento.getEsFirmaExterna())) {
        this.importar.setDisabled(false);
        this.previsualizar.setDisabled(false);
      } else {
        if ("pdf".equals(tipoContenido)
            && this.pdfService.estaFirmadoOConEspaciosDeFirma(dataFile)) {

          Messagebox.show(
              Labels.getLabel("gedo.importarDocumento.documentoFirmadoOPendienteDeFirma"),
              Labels.getLabel("gedo.general.information"), Messagebox.OK, Messagebox.INFORMATION);

          // TODO refactorizar el show del if anidado
          this.importar.setDisabled(true);
          if (!this.selectedTipoDocumento.getEsFirmaConjunta()) {
            this.enviarFirmar.setDisabled(true);
            this.firmar.setDisabled(true);
          }
        } else {
          this.importar.setDisabled(false);
          if (!this.selectedTipoDocumento.getEsFirmaConjunta()) {
            this.enviarFirmar.setDisabled(false);
            this.firmar.setDisabled(false);
          }

        }
      }
      nombreArchivoLabel.setValue(nombreArchivoUpload);
      this.previsualizar.setDisabled(false);
    } catch (ValidacionContenidoException vce) {
      logger.error("Error al subir documento importado " + vce.getMessage(), vce);
      this.importar.setDisabled(true);
      this.nombreArchivoLabel.setValue(Labels.getLabel("gedo.digitalizarDocumento.ningunArchivo"));
      this.dataFile = null;
      Messagebox.show(Labels.getLabel("gedo.importarDocuento.archivoNoSoportado"),
          Labels.getLabel("gedo.error.carga"), Messagebox.OK, Messagebox.ERROR);
    } catch (Exception e) {
      logger.error("Error en validación de contenido en importación", e);
      throw new Exception("Error en validación de contenido en importación", e);
    }
    Clients.clearBusy();
  }

  /**
   * Envía evento para iniciar proceso de subida de archivo.
   * 
   * @param event
   */
  public void onUpload$uploadDocumento(UploadEvent event) {

    Map<String, Object> datos = new HashMap<>();
    datos.put("funcion", "upload");
    datos.put("datos", event);
    Clients.showBusy(Labels.getLabel("gedo.importarDocumento.procesandoUpload"));
    Events.echoEvent("onUser", this.self, datos);
  }

  /**
   * Envía evento para iniciar previsualización del documento.
   * 
   * @throws InterruptedException
   */
  public void onClick$previsualizar() throws InterruptedException {
    Map<String, Object> datos = new HashMap<>();
    datos.put("funcion", "previsualizar");
    datos.put("datos", null);
    Clients.showBusy(Labels.getLabel("gedo.importarDocumento.procesandoPrevisualizacion"));
    Events.echoEvent("onUser", this.self, datos);
  }

  /**
   * Invoca a la ventana de previsualización.
   * 
   * @throws IOException
   * @throws Exception
   */
  public void previsualizarDocumento() throws InterruptedException {
    try {
      int maxSizePrevisualizacion = Integer.parseInt(maximoPrevisualizacion);
      int maxSizePrevisualizacionByteslength = maxSizePrevisualizacion
          * Constantes.FACTOR_CONVERSION;
      int tamanoDocumentoAFirmar = dataFile.length + Constantes.TAMANO_HOJA_FIRMA;
      if (tamanoDocumentoAFirmar > maxSizePrevisualizacionByteslength) {
        Messagebox.show(Labels.getLabel("gedo.importarDocumento.tamanoMaximo"),
            Labels.getLabel("gedo.general.information"), Messagebox.OK, Messagebox.INFORMATION);
        return;
      }
      byte[] contenidoTemporal = this.pdfService.crearDocumentoPDFPrevisualizacion(
          nombreArchivoLabel.getValue(), dataFile, this.selectedTipoDocumento);

      PrevisualizacionDocumento.window(contenidoTemporal, Boolean.TRUE, this.self);

    } catch (PDFConversionException e) {
      logger.error("Error en conversión de archivo pdf ", e);
      Messagebox.show(Labels.getLabel("gedo.error.coversionAPDF"),
          Labels.getLabel("gedo.general.error"), Messagebox.OK, Messagebox.ERROR);
      this.resetControles();
    } catch (Exception e) {
      logger.error("Error en previsualización de archivo pdf ", e);
      Messagebox.show(Labels.getLabel("gedo.previsualizar.error"),
          Labels.getLabel("gedo.general.error"), Messagebox.OK, Messagebox.ERROR);
      this.resetControles();
    } finally {
      Clients.clearBusy();
    }
  }

  /**
   * Actualización de componentes gráficos en el caso de enviar a firmar a otro
   * usuario.
   * 
   * @throws InterruptedException
   */
  public void onCheck$enviarFirmar() throws InterruptedException {
    this.usuarioFirmante.setDisabled(false);
    this.importar.setDisabled(false);
  }

  /**
   * Actualización de componentes gráficos en el caso de que el usuario actual
   * decida firmar el mismo el documento.
   * 
   * @throws InterruptedException
   */
  public void onCheck$firmar() throws InterruptedException {
    this.usuarioFirmante.setDisabled(true);
    this.importar.setDisabled(false);
  }

  public void setUsuariosFirmantes(List<Usuario> usuariosFirmantes) {
    this.usuariosFirmantes = usuariosFirmantes;
  }

  public List<DocumentoMetadataDTO> getListaDocMetadata() {
    return listaDocMetadata;
  }

  public void setListaDocMetadata(List<DocumentoMetadataDTO> listaDocMetadata) {
    this.listaDocMetadata = listaDocMetadata;
  }

  public TipoDocumentoDTO getSelectedTipoDocumento() {
    return selectedTipoDocumento;
  }

  public void setSelectedTipoDocumento(TipoDocumentoDTO selectedTipoDocumento) {
    this.selectedTipoDocumento = selectedTipoDocumento;
  }

  protected void mostrarMensajeEnvioMail(String processInstanceId) throws InterruptedException {
    Boolean falloEnvioSolicitudMail = (Boolean) processEngine.getExecutionService()
        .getVariable(processInstanceId, Constantes.VAR_SOLICITUD_ENVIO_MAIL_FAIL);
    if (falloEnvioSolicitudMail != null && falloEnvioSolicitudMail) {

      Messagebox.show(Labels.getLabel("gedo.error.enviarCorreo"),
          Labels.getLabel("gedo.general.information"), Messagebox.OK, Messagebox.EXCLAMATION);
    }
  }

  /**
   * Establece el máximo de bytes soportados para subir archivos al servidor.
   */
  protected void setMaxSize() {
    this.maxSizeArchivos = maximoArchivos;
    StringBuilder upload = new StringBuilder("true,maxsize=");
    upload.append(maxSizeArchivos);
    upload.append(",native");
    uploadDocumento.setUpload(upload.toString());
  }

  protected void crearDocumentoPDF(byte[] contenido, String tipoArchivo) throws Exception {
    Integer numeroFirmas;
    if (this.selectedTipoDocumento.getEsFirmaConjunta()) {
      numeroFirmas = this.usuariosFirmantes.size();
    } else {
      numeroFirmas = Integer.valueOf(1);
    }
    RequestGenerarDocumento request = new RequestGenerarDocumento();
    request.setTipoDocumentoGedo(this.selectedTipoDocumento);
    request.setMotivo(motivo.getValue());
    request.setTipoArchivo(tipoArchivo);
    request.setData(contenido);
    request.setWorkflowId(this.workingTask.getExecutionId());
    this.generarDocumentoService.generarDocumentoPDF(request, numeroFirmas, true);
    this.pathFileTemporal = request.getNombreArchivo();
  }
}
