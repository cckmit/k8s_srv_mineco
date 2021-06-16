package com.egoveris.deo.web.satra.macros;

import com.egoveris.deo.base.service.IPrevisualizacionDocumentoService;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.zkoss.util.media.AMedia;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.HtmlMacroComponent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.select.annotation.VariableResolver;
import org.zkoss.zk.ui.select.annotation.WireVariable;
import org.zkoss.zul.Filedownload;
import org.zkoss.zul.Iframe;
import org.zkoss.zul.Label;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Toolbarbutton;

@VariableResolver(org.zkoss.zkplus.spring.DelegatingVariableResolver.class)
public class PrevisualizacionDocumentoComponente extends HtmlMacroComponent {

  /**
  * 
  */
  private static final long serialVersionUID = -7331500638814342665L;
  private static final Logger logger = LoggerFactory
      .getLogger(PrevisualizacionDocumentoComponente.class);

  private Toolbarbutton volver;
  private Label leyendaPrevisualizacion;
  private Iframe imagePreviewer;
  private Toolbarbutton descargarArchivo;

  private byte[] previewBytesPDF;

  @WireVariable("previsualizacionDocumentoServiceImpl")
  private IPrevisualizacionDocumentoService previsualizacionDocumentoService;

  @Override
  public void afterCompose() {
    super.afterCompose();
    
    inicializacionComponentesTemplate();
    inicializarListenersTemplate();
    this.previewBytesPDF = (byte[]) Executions.getCurrent().getArg()
        .get(PrevisualizacionDocumento.VAR_ARCHIVO_PREVISUALIZACION);
    if (this.previewBytesPDF != null) {
      crearPdfPrevisualizacion();
    }
  }

  /**
   * Inicializa los componentes del template
   */
  private void inicializacionComponentesTemplate() {
    this.volver = (Toolbarbutton) getFellow("volver");
    this.imagePreviewer = (Iframe) getFellow("imagePreviewer");
    this.descargarArchivo = (Toolbarbutton) getFellow("descargarArchivo");
    this.leyendaPrevisualizacion = (Label) getFellow("leyendaPrevisualizacion");
  }

  /**
   * Inicializa los listeners de los eventos requeridos por los componentes
   * definidos en el template.
   */
  private void inicializarListenersTemplate() {
    this.volver.addEventListener(Events.ON_CLICK, new PrevisualizarListener(this, this.volver, 0));
    this.descargarArchivo.addEventListener(Events.ON_CLICK,
        new PrevisualizarListener(this, this.descargarArchivo, 0));
  }

  private void crearPdfPrevisualizacion() {
    try {
      int maxPreview = this.previsualizacionDocumentoService.obtenerMaximoPrevisualizacion();
      this.leyendaPrevisualizacion
          .setValue(Labels.getLabel("gedo.firmarDocumento.leyendaPrevisualizacion",
              new String[] { String.valueOf(maxPreview) }));
      AMedia amedia = new AMedia("previsualizacion.pdf", "pdf", "application/pdf",
          this.previsualizacionDocumentoService
              .obtenerPrevisualizacionDocumentoReducidaBytes(previewBytesPDF));
      this.imagePreviewer.setContent(amedia);
    } catch (Exception e) {
      logger.error("Error al obtener la previsualizacion del documento", e);
      String mensaje = e.getMessage();
      Messagebox.show(
          Labels.getLabel("gedo.firmarDocumento.error.previsualizar", new String[] { mensaje }),
          Labels.getLabel("gedo.general.error"), Messagebox.OK, Messagebox.ERROR);
    }

  }

  /**
   * Cierra la ventana padre.
   */
  public void onClickVolver() {
    this.imagePreviewer.setContent(null);
    Events.sendEvent(new Event(Events.ON_CLOSE, this.getParent(), null));
  }

  /**
   * Permite descargar el archivo que se está previsualizando.
   * 
   * @throws IOException
   */
  public void onClickDescargarArchivo() throws IOException {
    try {
      Filedownload.save(this.previewBytesPDF, "application/pdf", "previsualizacion.pdf");
    } catch (Exception e) {
      logger.error("Error al descargar el archivo en previsualización", e);
    }
  }

  /**
   * Redirecciona la acción que debe realizarse ante la ocurrencia del evento
   * onClick, dependiendo del componente que lo disparo.
   * 
   * @param data
   * @throws Exception
   */
  public void manejarEventoOnClick(Object data) throws Exception {
    if (data.equals(this.volver))
      this.onClickVolver();
    if (data.equals(this.descargarArchivo))
      this.onClickDescargarArchivo();

  }

  /**
   * Actualiza el ancho del componente, dado por el parámetro arg.width
   */
  public void setWidth(String width) {
    setDynamicProperty("width", width);
  }

  /**
   * Obtiene el parámetro arg.width
   */
  public String getWidth() {
    return (String) getDynamicProperty("width");
  }

  /**
   * Actualiza el ancho del componente, dado por el parámetro arg.height
   */
  public void setHeight(String height) {
    setDynamicProperty("height", height);
  }

  /**
   * Obtiene el parámetro arg.height
   */
  public String getHeight() {
    return (String) getDynamicProperty("height");
  }

}

/**
 * 
 * @author bfontana
 * 
 */
final class PrevisualizarListener implements EventListener {
  private PrevisualizacionDocumentoComponente componente;
  private Object data;

  public PrevisualizarListener(PrevisualizacionDocumentoComponente componente, Object data,
      int arg2) {
    this.componente = componente;
    this.data = data;
  }

  public void onEvent(Event event) throws Exception {

    if (event.getName().equals(Events.ON_CLICK)) {
      componente.manejarEventoOnClick(data);
    }

  }

}