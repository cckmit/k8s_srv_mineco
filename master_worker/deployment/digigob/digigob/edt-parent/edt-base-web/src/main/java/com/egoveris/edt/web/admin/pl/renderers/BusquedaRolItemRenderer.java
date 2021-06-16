package com.egoveris.edt.web.admin.pl.renderers;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.sys.ComponentsCtrl;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import com.egoveris.edt.base.model.eu.usuario.RolDTO;

public class BusquedaRolItemRenderer implements ListitemRenderer {

  @Override
  public void render(Listitem item, Object data, int arg2) throws Exception {
    RolDTO rol = (RolDTO) data;
    item.setValue(rol);

    Listcell lblRol = new Listcell(rol.getRolNombre());
    lblRol.setParent(item);

    // Vigente
    Checkbox chkBox = new Checkbox();

    chkBox.setChecked(rol.getEsVigente());
    chkBox.setVisible(true);
    chkBox.setDisabled(true);
    chkBox.setId("chkVigente_" + rol.getId());

    Listcell cellCheckVigente = new Listcell();
    chkBox.setParent(cellCheckVigente);
    cellCheckVigente.setParent(item);

    Listcell lcAccion = new Listcell();
    lcAccion.setParent(item);
    Hbox hb = new Hbox();
    hb.setParent(lcAccion);

    // modificar
    Image modificar = new Image("/imagenes/pencil.png");
    modificar.setParent(hb);
    ComponentsCtrl.applyForward(modificar, "onClick=onModificarRol");
    modificar.setTooltiptext(Labels.getLabel("eu.abmNovedades.acciones.modificar"));

    // eliminar
    Image eliminar = new Image("/imagenes/Eliminar.png");
    eliminar.setParent(hb);
    ComponentsCtrl.applyForward(eliminar, "onClick=onEliminarRol()");
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
