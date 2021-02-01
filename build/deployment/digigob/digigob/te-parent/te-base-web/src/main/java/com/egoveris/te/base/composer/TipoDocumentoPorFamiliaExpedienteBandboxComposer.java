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

public class TipoDocumentoPorFamiliaExpedienteBandboxComposer
    extends TipoDocumentoExpedienteComposer {

  private static final long serialVersionUID = 5712501002999884499L;

  public static String ACTO_ADMINISTRATIVO = "Acto Administrativo";

  // componentes y variables
  // componentes
  @Autowired
  private Bandbox tiposDocumentoActoAdministrativoBandbox;
  @Autowired
  private Textbox textoTipoDocumentoActoAdministrativo;
  @Autowired
  private Tree actoAdmDocumentTypeTree;
  // variables
  private List<TipoDocumentoDTO> listaTiposDocumentoPorFamiliaCompleta;
  private List<TipoDocumentoDTO> listaTiposDocumentoPorFamiliaFiltrada;
  @Autowired
  private AnnotateDataBinder binder;

  public void doAfterCompose(Component comp) throws Exception {
    super.doAfterCompose(comp);
    this.listaTiposDocumentoPorFamiliaCompleta = super.tipoDocumentoService
        .obtenerTiposDocumentoGEDOPorFamilia(ACTO_ADMINISTRATIVO);
    filtro(listaTiposDocumentoPorFamiliaCompleta, actoAdmDocumentTypeTree);
    recargarComponente(actoAdmDocumentTypeTree, binder);
  }

  @SuppressWarnings("unchecked")
  public void onSelect$actoAdmDocumentTypeTree() throws InterruptedException {
    String idTipoDocumento = actoAdmDocumentTypeTree.getSelectedItem().getTreerow().getId();

    if (idTipoDocumento != null && !idTipoDocumento.equals("")) {
      String valorBandbox = super.tipoDocumentoService
          .obtenerTiposDocumentoGEDO(Long.valueOf(idTipoDocumento))
          .getCodigoTipoDocumentoSade();
      tiposDocumentoActoAdministrativoBandbox.setTooltip(idTipoDocumento);
      tiposDocumentoActoAdministrativoBandbox.setValue(valorBandbox);
      InputEvent e = new InputEvent("onChanging", textoTipoDocumentoActoAdministrativo, "",
          textoTipoDocumentoActoAdministrativo.getValue());
      this.onChanging$textoTipoDocumentoActoAdministrativo(e);
      recargarComponente(textoTipoDocumentoActoAdministrativo, binder);
      recargarComponente(tiposDocumentoActoAdministrativoBandbox, binder);
      this.tiposDocumentoActoAdministrativoBandbox.close();
    }
  }

  public void onChanging$tiposDocumentoActoAdministrativoBandbox(InputEvent e) {
    InputEvent ev = new InputEvent("onChanging", textoTipoDocumentoActoAdministrativo,
        e.getValue(), textoTipoDocumentoActoAdministrativo.getValue());
    this.textoTipoDocumentoActoAdministrativo.setValue(e.getValue());
    this.onChanging$textoTipoDocumentoActoAdministrativo(ev);
  }

  public void onChanging$textoTipoDocumentoActoAdministrativo(InputEvent e) {
    this.textoTipoDocumentoActoAdministrativo.clearErrorMessage();
    String matchingText = e.getValue().trim();
    if (!matchingText.equals("")
        && (matchingText.length() >= 2 || (validarTipoDocumento(matchingText)))) {
      this.listaTiposDocumentoPorFamiliaFiltrada = super.cargarlistaTiposDocumentosFiltrada(
          this.listaTiposDocumentoPorFamiliaCompleta, matchingText.toLowerCase());
      if (!this.listaTiposDocumentoPorFamiliaFiltrada.isEmpty()) {
        filtro(this.listaTiposDocumentoPorFamiliaFiltrada, actoAdmDocumentTypeTree);
        recargarComponente(actoAdmDocumentTypeTree, binder);
      } else {
        throw new WrongValueException(this.textoTipoDocumentoActoAdministrativo,
            Labels.getLabel("ee.busquedaTipoDocumentoActoAdm.textoInexistente"));
      }
    } else if (matchingText.equals("")) {
      filtro(this.listaTiposDocumentoPorFamiliaCompleta, actoAdmDocumentTypeTree);
      recargarComponente(actoAdmDocumentTypeTree, binder);
    }
  }

}
