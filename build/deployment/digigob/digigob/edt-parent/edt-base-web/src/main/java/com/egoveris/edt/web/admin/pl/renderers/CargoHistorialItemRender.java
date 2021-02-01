package com.egoveris.edt.web.admin.pl.renderers;

import com.egoveris.sharedsecurity.base.model.ConstantesSesion;
import com.egoveris.sharedsecurity.base.model.cargo.CargoHistoricoDTO;

import java.text.SimpleDateFormat;

import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class CargoHistorialItemRender implements ListitemRenderer {

  @Override
  public void render(Listitem item, Object data, int arg2) throws Exception {

    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

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

    // usuario creacion
    Listcell lblUsuarioCreacion = new Listcell(cargo.getUsuarioCreacion());
    lblUsuarioCreacion.setParent(item);

    // fecha creacion
    Listcell lblFechaCreacion = new Listcell(sdf.format(cargo.getFechaCreacion()));
    lblFechaCreacion.setParent(item);

    // usuario modificacion
    Listcell lblUsuarioModificacion = new Listcell(cargo.getUsuarioModificacion());
    lblUsuarioModificacion.setParent(item);

    // fecha modificacion
    Listcell lblFechaModificacion = new Listcell(sdf.format(cargo.getFechaModificacion()));
    lblFechaModificacion.setParent(item);

    // Vigente
    Checkbox chkBox = new Checkbox();

    chkBox.setChecked(cargo.getVigente());
    chkBox.setVisible(true);
    chkBox.setDisabled(true);
    chkBox.setId("chkVigente_" + cargo.getId());

    Listcell cellCheckVigente = new Listcell();
    chkBox.setParent(cellCheckVigente);
    cellCheckVigente.setParent(item);

    // operacion
    String operacion = null;
    if (cargo.getTipoRevision() == 0) {
      operacion = "ALTA";
    }
    if (cargo.getTipoRevision() == 1) {
      operacion = "MODIFICACIÓN";
    }
    if (cargo.getTipoRevision() == 2) {
      operacion = "BAJA";
    }

    Listcell lblOperacion = new Listcell(operacion);
    lblOperacion.setParent(item);
  }
}
