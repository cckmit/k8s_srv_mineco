package com.egoveris.deo.web.satra;

import com.egoveris.deo.base.service.ArchivosEmbebidosService;
import com.egoveris.deo.base.service.BuscarDocumentosGedoService;
import com.egoveris.deo.base.service.GestionArchivosWebDavService;
import com.egoveris.deo.model.model.ArchivoEmbebidoDTO;
import com.egoveris.deo.model.model.RequestGenerarDocumento;

import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Window;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class DescargaDocumentoComposer extends GEDOGenericForwardComposer {

  private static final long serialVersionUID = 1968714188431815192L;

  @WireVariable("archivosEmbebidosServiceImpl")
  private ArchivosEmbebidosService archivosEmbebidosService;
  @WireVariable("gestionArchivosWebDavServiceImpl")
  private GestionArchivosWebDavService gestionArchivosWebDavService;
  @WireVariable("buscarDocumentosGedoServiceImpl")
  private BuscarDocumentosGedoService buscarDocGedoSer;

  private Window descargaWindow;
  private Label informacionVentanaPadre;
  private String numeroDocumento;
  private String mensajeVentanaPadre;
  private String titulo;
  private Boolean contieneEmbebidos;
  private String workflowOrigen;
  private String idGuardaDocumental;
  private Row buttonsRow;
  private Row buttonRow;
  private Row buttonOk;
  private RequestGenerarDocumento request;
  private static final Logger logger = LoggerFactory.getLogger(DescargaDocumentoComposer.class);

  public void doAfterCompose(Component component) throws Exception {
    super.doAfterCompose(component);
    Selectors.wireVariables(page, this, Selectors.newVariableResolvers(getClass(), null));
    HashMap<String, Object> mensajes = (HashMap<String, Object>) component.getDesktop()
        .getAttribute("mensajes");
    this.mensajeVentanaPadre = (String) mensajes.get("informacionPadre");
    this.numeroDocumento = (String) mensajes.get("numeroDocumento");
    this.titulo = (String) mensajes.get("titulo");
    this.contieneEmbebidos = (Boolean) mensajes.get("contieneEmbebidos");
    this.informacionVentanaPadre.setValue(mensajeVentanaPadre);
    this.descargaWindow.setTitle(this.titulo);
    this.request = (RequestGenerarDocumento) mensajes.get("request");
    this.setWorkflowOrigen((String) component.getDesktop().getAttribute("workFlowOrigen"));
    if (contieneEmbebidos != null && contieneEmbebidos) {
      buttonsRow.setVisible(false);
      buttonRow.setVisible(true);
    } else {
      buttonRow.setVisible(false);
      buttonsRow.setVisible(true);

      new Thread(new Runnable() {

        @Override
        public void run() {
          try {// BORRA EL EMBEBIDO DE WEBDAV Y BASE
            List<ArchivoEmbebidoDTO> listaArchivosEmbebidos = archivosEmbebidosService
                .buscarArchivosEmbebidosPorProceso(workflowOrigen);
            for (ArchivoEmbebidoDTO archivoEmbebido : listaArchivosEmbebidos) {

              // WEBDAV
              archivosEmbebidosService.borrarArchivoEmbebidoWebDav(
                  archivoEmbebido.getPathRelativo(), archivoEmbebido.getNombreArchivo());

              archivosEmbebidosService.eliminarAchivoEmbebido(archivoEmbebido);
            }
          } catch (Exception ex) {
            logger.error("Error al borrar archivos embebidos." + ex.getMessage(), ex);
          }
        }
      }).start();
    }

    if (mensajes.get("comunicable") != null) {
      this.buttonRow.setVisible(false);
      this.buttonsRow.setVisible(false);
      this.buttonOk.setVisible(true);
      this.descargaWindow.setHeight("110px");
    }
  }

  /**
   * Permite descargar el documento.
   * 
   * @throws InterruptedException
   */

  public void onClick$descargar() throws InterruptedException {
    this.idGuardaDocumental = this.buscarDocGedoSer.buscarDocumentoPorNumero(this.numeroDocumento)
        .getIdGuardaDocumental();
    try {

      // WEBDAV
      Filedownload.save(
          gestionArchivosWebDavService.obtenerDocumento(this.numeroDocumento).getFileAsStream(),
          null, this.numeroDocumento.concat(".pdf"));

    } catch (Exception e) {
      logger.error("Error al obtener archivo del repositorio" + e, e);
    }
    super.closeAndNotifyAssociatedWindow(null);
  }

  public void onClick$descargarConEmbebidos() {

    this.idGuardaDocumental = this.request.getIdGuardaDocumental();
    try {

      // WEBDAV
      Filedownload.save(
          gestionArchivosWebDavService
              .obtenerRecursoTemporalWebDavAsStream(request.getNombreArchivo()).getFileAsStream(),
          null, "previsualizacion.pdf");

    } catch (Exception e) {
      logger.error("Mensaje de error", e);
    }
    super.closeAndNotifyAssociatedWindow(null);
  }

  public void onClick$ok() {
    this.descargaWindow.detach();
  }

  /**
   * Cerrar la ventana que informa si el proceso fue exitoso.
   * 
   * @throws InterruptedException
   */
  public void onClick$cerrar() throws InterruptedException {
    super.closeAndNotifyAssociatedWindow(null);
  }

  public String getNumeroDocumento() {
    return numeroDocumento;
  }

  public void setNumeroDocumento(String numeroDocumento) {
    this.numeroDocumento = numeroDocumento;
  }

  public String getMensajeVentanaPadre() {
    return mensajeVentanaPadre;
  }

  public void setMensajeVentanaPadre(String mensajeVentanaPadre) {
    this.mensajeVentanaPadre = mensajeVentanaPadre;
  }

  public String getWorkflowOrigen() {
    return workflowOrigen;
  }

  public void setWorkflowOrigen(String workflowOrigen) {
    this.workflowOrigen = workflowOrigen;
  }

  public String getTitulo() {
    return titulo;
  }

  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  public String getIdGuardaDocumental() {
    return idGuardaDocumental;
  }

  public void setIdGuardaDocumental(String idGuardaDocumental) {
    this.idGuardaDocumental = idGuardaDocumental;
  }

}
