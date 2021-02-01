package com.egoveris.edt.web.admin.pl.renderers;

import org.zkoss.zk.ui.sys.ComponentsCtrl;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import com.egoveris.sharedsecurity.base.model.UsuarioReparticionHabilitadaDTO;

/**
 * 
 * @author sabianco
 *
 */
public class SeleccionReparticionItemRenderer implements ListitemRenderer {

  @Override
  public void render(Listitem item, Object data, int arg2) throws Exception {

    UsuarioReparticionHabilitadaDTO reparticionHabilitada = (UsuarioReparticionHabilitadaDTO) data;

    item.setValue(reparticionHabilitada);

    Listcell codigo = new Listcell(reparticionHabilitada.getReparticion().getCodigo());
    codigo.setParent(item);
    ComponentsCtrl.applyForward(codigo, "onClick=onSeleccionarReparticion()");

    Listcell descripcion = new Listcell(reparticionHabilitada.getReparticion().getNombre());
    descripcion.setParent(item);
    ComponentsCtrl.applyForward(descripcion, "onClick=onSeleccionarReparticion()");

    Listcell sector = new Listcell(reparticionHabilitada.getSector().getCodigo());
    sector.setParent(item);
    ComponentsCtrl.applyForward(sector, "onClick=onSeleccionarReparticion()");

    Listcell cargo = new Listcell(reparticionHabilitada.getCargo() == null ? "F"
        : reparticionHabilitada.getCargo().getCargoNombre());
    cargo.setParent(item);
    ComponentsCtrl.applyForward(cargo, "onClick=onSeleccionarReparticion()");

  }

}