package com.egoveris.edt.web.admin.pl.renderers;

import com.egoveris.edt.base.model.eu.feriado.FeriadoDTO;
import com.egoveris.edt.base.util.zk.UtilsDate;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.sys.ComponentsCtrl;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

/**
 * 
 * @author lfishkel
 *
 */
public class FeriadosItemRenderer implements ListitemRenderer {

  @Override
  public void render(Listitem item, Object data, int arg2) throws Exception {

    FeriadoDTO feriado = (FeriadoDTO) data;

    item.setValue(feriado);

    Listcell lcMotivo = new Listcell(feriado.getMotivo());
    lcMotivo.setParent(item);

    Listcell lcFecha = new Listcell(UtilsDate.convertDateToFormalString(feriado.getFecha()));
    lcFecha.setParent(item);

    Listcell lcAccion = new Listcell();
    lcAccion.setParent(item);
    Hbox hb = new Hbox();
    hb.setParent(lcAccion);

    // modificar
    Image modificar = new Image("/imagenes/pencil.png");
    modificar.setParent(hb);
    ComponentsCtrl.applyForward(modificar, "onClick=onModificarFeriado()");
    Label modificarLabel = new Label(Labels.getLabel("eu.abm.configuracion.feriados.modificar"));
    ComponentsCtrl.applyForward(modificarLabel, "onClick=onModificarFeriado()");
    modificarLabel.setParent(hb);

    // eliminar
    Image eliminar = new Image("/imagenes/Eliminar.png");
    eliminar.setParent(hb);
    ComponentsCtrl.applyForward(eliminar, "onClick=onEliminarFeriado()");
    Label eliminarLabel = new Label(Labels.getLabel("eu.abm.configuracion.feriados.eliminar"));
    ComponentsCtrl.applyForward(eliminarLabel, "onClick=onEliminarFeriado()");
    eliminarLabel.setParent(hb);

  }

}
