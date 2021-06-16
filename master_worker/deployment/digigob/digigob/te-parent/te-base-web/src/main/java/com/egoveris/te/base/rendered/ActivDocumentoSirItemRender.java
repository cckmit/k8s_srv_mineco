package com.egoveris.te.base.rendered;

import com.egoveris.te.base.exception.DocumentoOArchivoNoEncontradoException;
import com.egoveris.te.base.model.DocumentoDTO;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class ActivDocumentoSirItemRender extends GenericItemRender implements ListitemRenderer {

  DocumentoDTO documento;

  public ActivDocumentoSirItemRender() {
    super();
  }

  public void render(Listitem item, Object data, int arg1) {

    final String data1 = (String) data;
    String expr = data1;
    String delims = "-"; // so the delimiters are: + - * / ^ space
    String[] tokens = expr.split(delims);

    if (tokens[3].length() < 3) {
      tokens[3] = "   ";
    }

    StringBuilder numeroDocumento = new StringBuilder();
    numeroDocumento.append(tokens[0]);
    numeroDocumento.append("-");
    numeroDocumento.append(tokens[1]);
    numeroDocumento.append("-");
    numeroDocumento.append(tokens[2]);
    numeroDocumento.append("-");
    numeroDocumento.append(tokens[3]);
    numeroDocumento.append("-");
    numeroDocumento.append(tokens[4]);

    documento = super.getDocumentoGedoService().obtenerDocumentoPorNumeroEstandar(numeroDocumento.toString());

    if (documento == null) {
      throw new DocumentoOArchivoNoEncontradoException(
          "No se pudo obtener el documento " + numeroDocumento, null);
    }

    if (documento.getNombreArchivo() == null) {
      StringBuilder nombreArchivo = new StringBuilder();
      nombreArchivo.append(documento.getNumeroSade());
      nombreArchivo.append(".pdf");
      documento.setNombreArchivo(nombreArchivo.toString());
    }

    // Nro Documento
    Listcell currentCell = new Listcell(numeroDocumento.toString());
    currentCell.setParent(item);

    // accion
    currentCell = new Listcell();
    currentCell.setParent(item);
    Label label = new Label(Labels.getLabel("ee.act.label.descargar.gedo"));
    Hbox hbox = new Hbox();
    Image runImage = new Image("/imagenes/ver_expediente.png");
    runImage.setParent(hbox);
    label.setParent(hbox);
    hbox.setParent(currentCell);
    hbox.addEventListener(Events.ON_CLICK, new EventListener() {
      @Override
      public void onEvent(Event event) throws Exception {
        DescargaArchivo.descargar(documento);
      }
    });
  }
}
