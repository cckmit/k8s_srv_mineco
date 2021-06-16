package com.egoveris.te.base.composer;

import com.egoveris.te.base.model.TipoDocumentoDTO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Bandbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Tree;

public class TipoDocumentoExpedienteBandboxComposer extends TipoDocumentoExpedienteComposer {

  private static final long serialVersionUID = -4957596228380348602L;
  // Variables y Componentes
  // componentes
  @Autowired
  private Bandbox tiposDocumentoBandbox;
  @Autowired
  private Textbox textoTipoDocumento2;
  @Autowired
  private Tree documentTypeTree;
  // variables
  private List<TipoDocumentoDTO> listaTiposDocumentoCompleta;
  private List<TipoDocumentoDTO> listaTiposDocumentoFiltrada;
  @Autowired
  private AnnotateDataBinder binder;

  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
//    this.listaTiposDocumentoCompleta = super.tipoDocumentoService.obtenerTiposDocumento();
    this.listaTiposDocumentoCompleta = super.tipoDocumentoService.obtenerTiposDocumentoGedo();
    filtro(listaTiposDocumentoCompleta, documentTypeTree);
    recargarComponente(documentTypeTree, binder);
  }

  @SuppressWarnings("unchecked")
  public void onSelect$documentTypeTree() throws InterruptedException {
    String idTipoDocumento = documentTypeTree.getSelectedItem().getTreerow().getId();
    List<Component> treeSelectedItem = (List<Component>) documentTypeTree.getSelectedItem()
        .getTreerow().getChildren();
    if (idTipoDocumento != null && !idTipoDocumento.equals("")) {
      String valorBandbox = contenidoSelectedItem(treeSelectedItem);
      tiposDocumentoBandbox.setText(idTipoDocumento);
      tiposDocumentoBandbox.setValue(valorBandbox);
      InputEvent e = new InputEvent("onChanging", textoTipoDocumento2, "",
          textoTipoDocumento2.getValue());
      this.onChanging$textoTipoDocumento(e);
      recargarComponente(textoTipoDocumento2, binder);
      recargarComponente(tiposDocumentoBandbox, binder);
      tiposDocumentoBandbox.close();

    }
  }

  public void onChanging$tiposDocumentoBandbox(InputEvent e) {
    InputEvent ev = new InputEvent("onChanging", textoTipoDocumento2, e.getValue(),
        textoTipoDocumento2.getValue());
    this.textoTipoDocumento2.setValue(e.getValue());
    this.onChanging$textoTipoDocumento(ev);
  }

  public void onChanging$textoTipoDocumento(InputEvent e) {
    this.textoTipoDocumento2.clearErrorMessage();
    String matchingText = e.getValue().trim();
    if (!matchingText.equals("") && (matchingText.length() >= 2)
        || validarTipoDocumento(matchingText)) {
      this.listaTiposDocumentoFiltrada = super.cargarlistaTiposDocumentosFiltrada(
          this.listaTiposDocumentoCompleta, matchingText.toLowerCase());
      if (!this.listaTiposDocumentoFiltrada.isEmpty()) {
        filtro(this.listaTiposDocumentoFiltrada, documentTypeTree);
        recargarComponente(documentTypeTree, binder);
      } else {
        throw new WrongValueException(this.textoTipoDocumento2,
            Labels.getLabel("ee.busquedaTipoDocumento.textoInexistente"));
      }
    } else if (matchingText.equals("")) {
      filtro(this.listaTiposDocumentoCompleta, documentTypeTree);
      recargarComponente(documentTypeTree, binder);
    }
  }

  public void onBlur$tiposDocumentoBandbox() {
    if (validarTipoDocumento(this.tiposDocumentoBandbox.getValue())) {
      this.tiposDocumentoBandbox
          .setValue(autocompletarTipoDocumento(this.tiposDocumentoBandbox.getValue()));
    } else if (!this.tiposDocumentoBandbox.getValue().trim().isEmpty()) {
      throw new WrongValueException(this.tiposDocumentoBandbox,
          Labels.getLabel("ee.busquedaTipoDocumento.textoInexistente"));
    }

  }

}
