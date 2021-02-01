package com.egoveris.te.base.rendered;

import com.egoveris.te.base.composer.ABMTrataComposer;
import com.egoveris.te.base.model.TrataDTO;
import com.egoveris.te.base.util.ConstantesWeb;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class TrataABMItemRenderer implements ListitemRenderer {

  private ABMTrataComposer composer;

  public TrataABMItemRenderer(ABMTrataComposer composer) {
    super();
    this.composer = composer;
  }

  @Override
  public void render(Listitem item, Object data, int arg1) throws Exception {

    TrataDTO trata = (TrataDTO) data;

    Listcell codigoTrata = new Listcell(trata.getCodigoTrata());
    codigoTrata.setParent(item);

    String Desc = trata.getDescripcion();
    Listcell descripcion = new Listcell(Desc);
    descripcion.setParent(item);

    Listcell ActuacionSADE = new Listcell(formatoActuacion(trata.getCodigoTrata(), Desc));
    ActuacionSADE.setParent(item);

    Checkbox reservaParcial = new Checkbox();
    if (trata != null && trata.getTipoReserva() != null
        && trata.getTipoReserva().getTipoReserva() != null) {
      reservaParcial
          .setChecked(trata.getTipoReserva().getTipoReserva().equals(ConstantesWeb.RESERVA_PARCIAL));
    } else {
      reservaParcial.setChecked(false);
    }
    ;
    reservaParcial.setDisabled(true);

    Listcell reservaParcialCell = new Listcell();
    reservaParcial.setParent(reservaParcialCell);
    reservaParcialCell.setParent(item);

    Checkbox reservaTotal = new Checkbox();
    if (trata != null && trata.getTipoReserva() != null
        && trata.getTipoReserva().getTipoReserva() != null) {
      reservaTotal
          .setChecked(trata.getTipoReserva().getTipoReserva().equals(ConstantesWeb.RESERVA_TOTAL));
    } else {
      reservaTotal.setChecked(false);
    }
    reservaTotal.setDisabled(true);

    Listcell reservaTotalCell = new Listcell();
    reservaTotal.setParent(reservaTotalCell);
    reservaTotalCell.setParent(item);
    // jpg interno
    Checkbox esInterno = new Checkbox();
    if (trata.getEsInterno() != null) {
      esInterno.setChecked(trata.getEsInterno());
    } else {
      esInterno.setChecked(false);
    }
    esInterno.setDisabled(true);
    Listcell esInternoCell = new Listcell();
    esInterno.setParent(esInternoCell);
    esInternoCell.setParent(item);
    // jpg Externo
    Checkbox esExterno = new Checkbox();
    if (trata.getEsExterno() != null)
      esExterno.setChecked(trata.getEsExterno());
    else
      esExterno.setChecked(false);

    esExterno.setDisabled(true);

    Listcell esExternoCell = new Listcell();
    esExterno.setParent(esExternoCell);
    esExternoCell.setParent(item);
    // fin jp
    Checkbox habilitacion = new Checkbox();
    if (trata != null && trata.getHabilitado() != null) {
      habilitacion.setChecked(trata.getHabilitado());
    } else {
      habilitacion.setChecked(false);
    }
    habilitacion.setDisabled(false);

    Listcell habilitadoCell = new Listcell();
    habilitacion.setParent(habilitadoCell);
    habilitadoCell.setParent(item);
    habilitacion.addEventListener("onCheck", new TrataListener(this.composer, trata));

    Listcell currentCell;
    currentCell = new Listcell();
    currentCell.setParent(item);

    Hbox hboxGrande = new Hbox();
    Hbox hboxReparticion = new Hbox();
    Image runImage = new Image("/imagenes/building.png");
    org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(runImage, "onClick=onRepTrata");
    runImage.setParent(hboxReparticion);
    Label runLabel = new Label("Organismos");
    runLabel.setStyle("font-size:11px;font-family: Arial;text-decoration: none;");
    runLabel.setParent(hboxReparticion);
    org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(runLabel, "onClick=onRepTrata");
    hboxReparticion.setTooltiptext("Organismos de la Trata");
    hboxReparticion.setAlign("center");
    hboxReparticion.setParent(hboxGrande);

    // Hbox hboxDatos = new Hbox();
    // Image runImageDatos = new Image("/imagenes/pencil.png");
    // org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(runImageDatos,"onClick=onDatosVariables");
    // runImageDatos.setParent(hboxDatos);
    // Label runLabelDatos = new Label("Datos Variables");
    // runLabelDatos.setStyle("font-size:11px;font-family:
    // Arial;text-decoration: none;");
    // runLabelDatos.setParent(hboxDatos);
    // org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(runLabelDatos,"onClick=onDatosVariables");
    // hboxDatos.setTooltiptext("Datos Variables de la Trata");

    // hboxDatos.setAlign("center");
    // hboxDatos.setParent(hboxGrande);

    Hbox hboxPage = new Hbox();
    Image runImagePage = new Image("/imagenes/page_text.png");
    org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(runImagePage, "onClick=onTiposDocs");
    runImagePage.setParent(hboxPage);
    Label runLabelPage = new Label("Tipos Doc");
    runLabelPage.setStyle("font-size:11px;font-family: Arial;text-decoration: none;");
    runLabelPage.setParent(hboxPage);
    org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(runLabelPage, "onClick=onTiposDocs");
    hboxPage.setTooltiptext("Tipos documentos habilitados de la trata");
    hboxPage.setAlign("center");
    hboxPage.setParent(hboxGrande);

    Hbox hboxPage2 = new Hbox();
    Image runImagePage2 = new Image("/imagenes/page_text.png");
    org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(runImagePage2, "onClick=onDetalle");
    runImagePage2.setParent(hboxPage2);
    Label runLabelPage2 = new Label("Editar/Detalle");
    runLabelPage2.setStyle("font-size:11px;font-family: Arial;text-decoration: none;");
    runLabelPage2.setParent(hboxPage2);
    org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(runLabelPage2, "onClick=onDetalle");
    hboxPage2.setTooltiptext("Detalle de la Trata");
    hboxPage2.setAlign("center");
    hboxPage2.setParent(hboxGrande);

    Hbox hboxDecline = new Hbox();
    Image runImageDecline = new Image("/imagenes/decline.png");
    org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(runImageDecline, "onClick=onEliminar");
    runImageDecline.setParent(hboxDecline);
    Label runLabelDecline = new Label("Eliminar");
    runLabelDecline.setStyle("font-size:11px;font-family: Arial;text-decoration: none;");
    runLabelDecline.setParent(hboxDecline);
    org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(runLabelDecline, "onClick=onEliminar");
    hboxDecline.setTooltiptext("Eliminar Trata");
    hboxDecline.setAlign("center");
    hboxDecline.setParent(hboxGrande);

    hboxGrande.setAlign("center");
    hboxGrande.setParent(currentCell);

  }

  private String formatoActuacion(String codigo, String descrip) {
    return codigo + (descrip.isEmpty() ? descrip : " - " + descrip);
  }

  final class TrataListener implements EventListener {
    private ABMTrataComposer composer;
    private TrataDTO trata;

    public TrataListener(ABMTrataComposer composer, TrataDTO trata) {
      super();
      this.trata = trata;
      this.composer = composer;
    }

    @Override
    public void onEvent(Event event) throws Exception {
      if (event.getName().equals(Events.ON_CHECK)) {
        Checkbox checkBox = (Checkbox) event.getTarget();

        if (checkBox.isChecked()) {
          this.composer.onChecked(trata);
        } else {
          this.composer.onUnChecked(trata);
        }
      }

    }
  }

}
