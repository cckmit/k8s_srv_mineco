package com.egoveris.edt.web.admin.pl.renderers;

import com.egoveris.sharedsecurity.base.model.ConstantesSesion;
import com.egoveris.sharedsecurity.base.model.cargo.CargoHistoricoDTO;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.sys.ComponentsCtrl;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class CargosHistItemRender implements ListitemRenderer {

  @Override
  public void render(Listitem item, Object data, int arg2) throws Exception {

    CargoHistoricoDTO cargo = (CargoHistoricoDTO) data;

    // cargo
    item.setValue(cargo);

    String textoCargo;
    if (cargo.getCargo().trim().length() <= 50) {
      textoCargo = cargo.getCargo();
    } else {
      textoCargo = cargo.getCargo().trim().substring(0, 50);
    }
    Listcell lblCargo = new Listcell(textoCargo);
    lblCargo.setTooltiptext(cargo.getCargo());
    lblCargo.setParent(item);

    // tipo
    String srtBox;
    if (cargo.getEsTipoBaja()) {
      srtBox = ConstantesSesion.CARGO_TIPO_BAJA;
    } else {
      srtBox = ConstantesSesion.CARGO_TIPO_ALTO;
    }

    Listcell lblEsBaja = new Listcell(srtBox);
    lblEsBaja.setParent(item);

    // Vigente
    Checkbox chkBox = new Checkbox();

    chkBox.setChecked(cargo.getVigente());
    chkBox.setVisible(true);
    chkBox.setDisabled(true);
    chkBox.setId("chkVigente_" + cargo.getId());

    Listcell cellCheckVigente = new Listcell();
    chkBox.setParent(cellCheckVigente);
    cellCheckVigente.setParent(item);

    // accion
    Listcell lcAccion = new Listcell();
    lcAccion.setParent(item);
    Hbox hb = new Hbox();
    hb.setParent(lcAccion);

    // historial
    Image modificar = new Image("/imagenes/find.png");
    modificar.setParent(hb);
    ComponentsCtrl.applyForward(modificar, "onClick=onVerHistorialNovedad()");
    Label modificarLabel = new Label(Labels.getLabel("eu.abmNovedades.acciones.historial"));
    ComponentsCtrl.applyForward(modificarLabel, "onClick=onVerHistorialNovedad()");
    modificarLabel.setParent(hb);

  }
}
