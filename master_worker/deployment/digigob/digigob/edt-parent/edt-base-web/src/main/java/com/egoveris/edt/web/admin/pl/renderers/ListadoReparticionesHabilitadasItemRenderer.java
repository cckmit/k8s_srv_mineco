package com.egoveris.edt.web.admin.pl.renderers;

import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import com.egoveris.sharedsecurity.base.model.UsuarioReparticionHabilitadaDTO;

public class ListadoReparticionesHabilitadasItemRenderer implements ListitemRenderer {

  @Override
  public void render(Listitem item, Object data, int arg2) throws Exception {
    UsuarioReparticionHabilitadaDTO reparticionHabilitada = (UsuarioReparticionHabilitadaDTO) data;

    new Listcell(reparticionHabilitada.getReparticion().getCodigo()).setParent(item);
    new Listcell(reparticionHabilitada.getReparticion().getNombre()).setParent(item);
    new Listcell(reparticionHabilitada.getSector().getNombre()).setParent(item);
    new Listcell(reparticionHabilitada.getCargo().getCargoNombre()).setParent(item);
    Listcell currentCell;

    currentCell = new Listcell();
    currentCell.setParent(item);
    Hbox hbox = new Hbox();

    // //ELIMINAR
    Image eliminarImage = new Image("/imagenes/Eliminar.png");
    org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(eliminarImage,
        "onClick=composer.onEliminarRH");
    Label eliminarLabel = new Label("Eliminar");
    org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(eliminarLabel,
        "onClick=composer.onEliminarRH");
    eliminarImage.setParent(hbox);
    eliminarLabel.setParent(hbox);
    hbox.setParent(currentCell);

  }

}
