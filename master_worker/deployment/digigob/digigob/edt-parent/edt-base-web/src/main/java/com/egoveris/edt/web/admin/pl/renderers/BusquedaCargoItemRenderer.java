package com.egoveris.edt.web.admin.pl.renderers;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.sys.ComponentsCtrl;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import com.egoveris.edt.base.util.Utilidades;
import com.egoveris.sharedsecurity.base.model.cargo.CargoDTO;

public class BusquedaCargoItemRenderer implements ListitemRenderer {

  @Override
  public void render(Listitem item, Object data, int arg2) throws Exception {

    CargoDTO cargo = (CargoDTO) data;

    item.setValue(cargo);

    Listcell lblCargo = new Listcell(cargo.getCargoNombre());
    lblCargo.setParent(item);

    String srtBox = cargo.getFechaModificacion() != null 
    		? Utilidades.darFomatoFecha(cargo.getFechaModificacion(),
    				"dd/MM/yyyy HH:mm:ss") : "";

    Listcell lblEsBaja = new Listcell(srtBox);
    lblEsBaja.setParent(item);

    // Restringido
    /*
     * int restringido=cargo.getEsTipoBaja();
     * 
     * Listcell res = new Listcell(Integer.toString(restringido));
     * res.setParent(item);
     */

    // Vigente
    Checkbox chkBox = new Checkbox();

    chkBox.setChecked(cargo.getVigente());
    chkBox.setVisible(true);
    chkBox.setDisabled(true);
    chkBox.setId("chkVigente_" + cargo.getId());

    Listcell cellCheckVigente = new Listcell();
    chkBox.setParent(cellCheckVigente);
    cellCheckVigente.setParent(item);

    boolean restringido = cargo.isEsTipoBaja();

    String cad_restringido;
    if (restringido) {
      cad_restringido = "SI";
    } else {
      cad_restringido = "NO";
    }
    Listcell res = new Listcell(cad_restringido);
    res.setParent(item);

    Listcell lcAccion = new Listcell();
    lcAccion.setParent(item);
    Hbox hb = new Hbox();
    hb.setParent(lcAccion);

    // modificar
    Image modificar = new Image("/imagenes/pencil.png");
    modificar.setParent(hb);
    ComponentsCtrl.applyForward(modificar, "onClick=onModificarCargo()");
    modificar.setTooltiptext(Labels.getLabel("eu.abmNovedades.acciones.modificar"));
    // Label modificarLabel = new
    // Label(Labels.getLabel("eu.abmNovedades.acciones.modificar"));
    // ComponentsCtrl.applyForward(modificarLabel,
    // "onClick=onModificarCargo()");
    // modificarLabel.setParent(hb);

    // eliminar
    Image eliminar = new Image("/imagenes/Eliminar.png");
    eliminar.setParent(hb);
    ComponentsCtrl.applyForward(eliminar, "onClick=onEliminarCargo()");
    eliminar.setTooltiptext(Labels.getLabel("eu.abmNovedades.acciones.eliminar"));
    // Label eliminarLabel = new
    // Label(Labels.getLabel("eu.abmNovedades.acciones.eliminar"));
    // ComponentsCtrl.applyForward(eliminarLabel, "onClick=onEliminarCargo()");
    // eliminarLabel.setParent(hb);

    // ver historial

    // Image verHistorial = new Image("/imagenes/edit-find-auditoria.png");
    // verHistorial.setParent(hb);
    // ComponentsCtrl.applyForward(verHistorial,
    // "onClick=onVerHistorialCargo()");
    // verHistorial.setTooltiptext(Labels.getLabel("eu.abmNovedades.acciones.historial.boton"));

    // Label verHistorialLabel = new
    // Label(Labels.getLabel("eu.abmNovedades.acciones.historial.boton"));
    // ComponentsCtrl.applyForward(verHistorialLabel,
    // "onClick=onVerHistorialCargo()");
    // verHistorialLabel.setParent(hb);

  }

}