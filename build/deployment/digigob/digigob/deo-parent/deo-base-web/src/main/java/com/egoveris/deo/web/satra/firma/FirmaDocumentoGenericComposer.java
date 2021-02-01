package com.egoveris.deo.web.satra.firma;

import com.egoveris.commons.databaseconfiguration.propiedades.AppProperty;
import com.egoveris.deo.base.service.GenerarDocumentoService;
import com.egoveris.deo.base.service.GestionArchivosWebDavService;
import com.egoveris.deo.base.service.IPrevisualizacionDocumentoService;
import com.egoveris.deo.base.service.PdfService;
import com.egoveris.deo.base.service.TipoDocumentoService;
import com.egoveris.deo.model.model.DocumentoMetadataDTO;
import com.egoveris.deo.model.model.RequestGenerarDocumento;
import com.egoveris.deo.model.model.TipoDocumentoDTO;
import com.egoveris.deo.util.Constantes;
import com.egoveris.deo.web.satra.ValidarApoderamientoComposer;
import com.egoveris.sharedsecurity.base.exception.SecurityNegocioException;
import com.egoveris.sharedsecurity.base.model.Usuario;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.media.AMedia;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Toolbarbutton;
import org.zkoss.zul.Window;

/**
 * @author pfolgar Esta clase FirmaDocumentoGenericComposer, agrupa
 *         funcionalidades comunes a la Firmar documento Las clases
 *         ValidarApoderamientoComposer y AbstractWorkFlowComposer, tienen
 *         funcionalidades relacionadas con eventos de ZK y el flujo JBPM,
 * 
 * 
 *
 */
@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class FirmaDocumentoGenericComposer extends ValidarApoderamientoComposer {

  /**
  * 
  */
  private static final long serialVersionUID = 6252438328890399455L;

  private static transient Logger logger = LoggerFactory
      .getLogger(FirmaDocumentoGenericComposer.class);

  protected static final int FACTOR_CONVERSION = 1024;

  protected TipoDocumentoDTO tipoDocumento;
  protected Iframe imagePreviewer;
  protected Checkbox solicitudAvisoFirma;
  protected Label leyendaPrevisualizacion;
  protected Checkbox solicitudEnvioCorreo;
  protected byte[] previewBytesPDF;
  protected byte[] contenidoTemporal;
  protected List<String> previewPages;
  protected int currentPreviewPage = -1;
  protected Button firmarButton;
  protected Button revisarButton;
  protected Button firmarConCertificado;
  
  protected Button firmarConAutoFirma;
  
  protected Toolbarbutton rechazarFirmaButton;

  @WireVariable("generarDocumentoServiceImpl")
  protected GenerarDocumentoService generarDocumentoService;
  @WireVariable("gestionArchivosWebDavServiceImpl")
  protected GestionArchivosWebDavService gestionArchivosWebDavService;
  @WireVariable("tipoDocumentoServiceImpl")
  protected TipoDocumentoService tipoDocumentoService;
  @WireVariable("pdfServiceImpl")
  protected PdfService pdfService;
  @WireVariable("previsualizacionDocumentoServiceImpl")
  protected IPrevisualizacionDocumentoService previsualizacionDocumentoService;

  // FIXME este boton no deberia usarse
  protected Toolbarbutton importarDocumentoButton;

  protected Combobox usuarioRevisor;
  protected Button exportPDF;
  protected Button selfRevision;
  protected Button closeWindow;
  protected Window firmaDocumento;
  protected Textbox mensajeARevisor;
  protected Textbox usuariosAgregadosTxt;
  protected Textbox usuariosRevisoresAgregados;
  protected Image verFirmantes;
  protected Image verRevisores;
  protected Grid revisionGrid;
  protected Window archivosDeTrabajoWindow;
  protected Window archivosEmbebidosWindow;
  protected Toolbarbutton archivosDeTrabajo;
  protected String acronimo;
  protected String motivo;
  protected String contenido = "";;
  protected String usuarioFirmante;
  protected List<DocumentoMetadataDTO> documentoMetadata;
  protected String numeroSadePapel;
  protected String nombreArchivoTemporal;
  protected String idGuardaDocumentalTemporal;
  AppProperty appProperty = (AppProperty) SpringUtil.getBean("dBProperty");

  public void doAfterCompose(Component component) throws Exception {
    super.doAfterCompose(component);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
  }

  /**
   * Muestra mensaje preguntando al usuario si desea realizar la descarga del
   * archivo que se ha firmado.
   */
  protected void mostrarMensajeDescarga(String numeroDocumento, String mensaje, Window window)
      throws InterruptedException {
    Map<String, Object> mensajes = new HashMap<String, Object>();
    mensajes.put("numeroDocumento", numeroDocumento);
    mensajes.put("informacionPadre", mensaje);

    if (this.tipoDocumento.getEsComunicable()) {
      mensajes.put("comunicable", "comunicable");
    }

    this.self.getDesktop().setAttribute("mensajes", mensajes);
    Window descargaModal;
    descargaModal = (Window) Executions.createComponents("inbox/descargaDocumento.zul", this.self,
        null);
    descargaModal.setParent(window.getParent());
    descargaModal.setVisible(true);

    descargaModal.doModal();
  }

  protected boolean generarPreviewInicial() throws InterruptedException {
    boolean result = true;
    try {
      int maxPreview = this.previsualizacionDocumentoService.obtenerMaximoPrevisualizacion();
      this.leyendaPrevisualizacion
          .setValue(Labels.getLabel("gedo.firmarDocumento.leyendaPrevisualizacion",
              new String[] { String.valueOf(maxPreview) }));
      this.previewBytesPDF = pdfService.crearDocumentoPDFPrevisualizacion(contenidoTemporal,
          tipoDocumento);
      crearPdfPrevisualizacion();
    } catch (Exception e) {
      if (e instanceof SQLException) {
        logger.error("Error al obtener el numero maximo de hojas para visualizar");
      }
      logger.error("Error al generar el preview", e);
      String mensaje = e.getMessage();
      Messagebox.show(
          Labels.getLabel("gedo.firmarDocumento.error.previsualizar", new String[] { mensaje }),
          Labels.getLabel("gedo.general.error"), Messagebox.OK, Messagebox.ERROR);
      result = false;
    }
    return result;
  }

  private void crearPdfPrevisualizacion() throws Exception {
    AMedia amedia = new AMedia("previsualizacion.pdf", "pdf", "application/pdf",
        this.previsualizacionDocumentoService
            .obtenerPrevisualizacionDocumentoReducidaBytes(previewBytesPDF));
    
    this.imagePreviewer.setContent(amedia);
    this.imagePreviewer.setRenderdefer(100);
    imagePreviewer.setWidth("734px");
    imagePreviewer.setHeight("253px");
  }

  protected final void closeAndNotifyAssociatedWindow(Object eventData) {
    liberarRecursos();
    super.closeAndNotifyAssociatedWindow(eventData);
  }

  public void onClick$exportPDF() throws Exception {
    if (previewBytesPDF == null) {
      previewBytesPDF = pdfService.crearDocumentoPDFPrevisualizacion(contenidoTemporal,
          tipoDocumento);
    }
    Filedownload.save(previewBytesPDF, "application/pdf", "previsualizacion.pdf");
  }

  protected void restaurarVentanaFirma() {
    Clients.clearBusy();
  }

  protected RequestGenerarDocumento createAndSetRequest() {
    RequestGenerarDocumento request = new RequestGenerarDocumento();
    request.setNombreAplicacion(Constantes.NOMBRE_APLICACION);
    request.setTipoDocumentoGedo(this.tipoDocumento);
    request.setUsuario(this.usuarioFirmante);
    request.setMotivo(this.motivo);
    // FILENET
    request.setIdGuardaDocumental(this.idGuardaDocumentalTemporal);
    // ESTO NO ES NECESARIO EN LA INSTANCIA DE FIRMA
    // request.setContenido(this.contenido);
    request.setTieneToken(this.tipoDocumento.getTieneToken());
    request.setDocumentoMetadata(this.documentoMetadata);
    Usuario dub = null;
    try {
      dub = getUsuarioService().obtenerUsuario(this.usuarioFirmante);
      request.setCodigoReparticion(dub.getCodigoReparticion());
      request.setData(this.contenidoTemporal);
    } catch (SecurityNegocioException e) {
      logger.error("Mensaje de error", e);
    }

    request.setNumeroSadePapel(this.numeroSadePapel);
    // Esta variable no existe cuando es firma externa, dado que la firma
    // externa no està asociada a ningún flujo
    request.setWorkflowId(
        this.workingTask == null ? StringUtils.EMPTY : this.workingTask.getExecutionId());
    request.setNombreArchivo(this.nombreArchivoTemporal);
    return request;
  }

  public void onClick$closeWindow() {
    liberarRecursos();
    this.closeAndNotifyAssociatedWindow(null);
  }

  private void liberarRecursos() {
    if (this.previewPages != null) {
      for (String file : previewPages) {
        this.pdfService.borrarImagenesTemporales(file);
      }
    }
  }

  @Override
  protected void asignarTarea() throws InterruptedException, Exception {

  }

  @Override
  protected void enviarBloqueoPantalla(Object data) {

  }

}
