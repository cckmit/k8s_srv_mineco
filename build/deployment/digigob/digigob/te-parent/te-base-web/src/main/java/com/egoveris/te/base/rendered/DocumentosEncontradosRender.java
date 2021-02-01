package com.egoveris.te.base.rendered;

import com.egoveris.te.base.composer.BuscarPorMetadatoComposer;
import com.egoveris.te.base.model.DocumentoDTO;
import com.egoveris.te.base.model.TipoDocumentoDTO;
import com.egoveris.te.base.service.TipoDocumentoService;
import com.egoveris.te.base.util.UtilsDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Separator;

/**
 * Render utilizado para mostrar los documentos encontrados en un grid
 * 
 * @author MAGARCES
 *
 */
public class DocumentosEncontradosRender implements ListitemRenderer {

  @Autowired
  private BuscarPorMetadatoComposer composer;

  protected TipoDocumentoService tipoDocumentoService;

  protected static String MEMORANDUM = "ME";
  protected static String NOTA = "NO";

  public DocumentosEncontradosRender(BuscarPorMetadatoComposer composer) {
    super();
    this.composer = composer;
  }

  @Override
  public void render(Listitem item, Object data, int arg1) throws Exception {

    item.setValue(data);

    DocumentoDTO documento = (DocumentoDTO) data;

    this.tipoDocumentoService = ((TipoDocumentoService) Executions.getCurrent().getDesktop()
        .getAttribute("tipoDocumentoService"));
    TipoDocumentoDTO tipoDeDocumento = tipoDocumentoService
        .obtenerTipoDocumento(documento.getTipoDocAcronimo());

    addListcell(item, tipoDeDocumento.getDescripcionTipoDocumentoSade());
    addListcell(item, documento.getNumeroSade());
    addListcell(item, documento.getMotivo());
    addListcell(item, UtilsDate.formatearFechaHora(documento.getFechaCreacion()));
    addCheckcell(item, documento);

  }

  private void addCheckcell(Listitem item, final DocumentoDTO documento) {

    Hbox hbox = new Hbox();

    Listcell lc = new Listcell();

    final Checkbox seleccionDocumento = new Checkbox();
    seleccionDocumento.setParent(hbox);
    seleccionDocumento.addEventListener("onCheck", new EventListener() {

      @Override
      public void onEvent(Event event) throws Exception {
        Checkbox checkBox = (Checkbox) event.getTarget();
        if (checkBox.isChecked()) {

          Hbox hb = (Hbox) checkBox.getParent();
          Listcell lc = (Listcell) hb.getParent();
          Listitem li = (Listitem) lc.getParent();
          DocumentoDTO a = (DocumentoDTO) li.getValue();
          composer.getDocumentosSelected().put(a.getNombreArchivo(), a);

        } else {
          Hbox hb = (Hbox) checkBox.getParent();
          Listcell lc = (Listcell) hb.getParent();
          Listitem li = (Listitem) lc.getParent();
          DocumentoDTO a = (DocumentoDTO) li.getValue();
          composer.getDocumentosSelected().remove(a.getNombreArchivo());

        }

      }
    });

    final Image documentoImage;
    Image documentoNoImage;

    documentoImage = new Image("/imagenes/page_text.png");

    documentoImage.setTooltiptext("Visualizar documento.");

    documentoImage.addEventListener("onClick", new EventListener() {

      @Override
      public void onEvent(Event event) throws Exception {
        composer.setSelectedDocumento(documento);
        org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(documentoImage,
            "onClick=onVisualizarDocumento");
      }
    });

    Separator separarDocumento = new Separator();
    separarDocumento.setParent(hbox);

    documentoImage.setParent(hbox);

    final Image visualizarImage;
    visualizarImage = new Image("/imagenes/download_documento.png");

    visualizarImage.addEventListener("onClick", new EventListener() {

      @Override
      public void onEvent(Event event) throws Exception {
        composer.setSelectedDocumento(documento);
        org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(visualizarImage,
            "onClick=onDescargarDocumento");
      }
    });

    visualizarImage.setTooltiptext("Descargar el documento a su disco local.");
    visualizarImage.setParent(hbox);
    hbox.setParent(lc);
    lc.setParent(item);

    // Esta iteracion se realiza para cuando se pasa de pagina en pagina se
    // mantengan seleccionados
    // los documentos que fueron seleccionados en otra pagina

    for (DocumentoDTO documentos : composer.getDocumentosSelected().values()) {
      if (documentos.getNombreArchivo().compareTo(documento.getNombreArchivo()) == 0) {
        seleccionDocumento.setChecked(true);
      }
    }

  }

  private void addListcell(Listitem listitem, String value) {
    Listcell lc = new Listcell();
    Label lb = new Label(value);
    lb.setParent(lc);
    lc.setParent(listitem);
  }

}
