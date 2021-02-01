package com.egoveris.edt.web.admin.pl.renderers;

import com.egoveris.edt.base.util.Utilidades;
import com.egoveris.sharedsecurity.base.model.EstructuraDTO;
import com.egoveris.sharedsecurity.conf.service.Utilitarios;

import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class ListadoEstructuraItemRender implements ListitemRenderer {

  @Override
  public void render(Listitem item, Object data, int arg2) throws Exception {
    EstructuraDTO estructura = (EstructuraDTO) data;

    new Listcell("" + estructura.getCodigoEstructura()).setParent(item);
    new Listcell(estructura.getNombreEstructura()).setParent(item);
    new Listcell(Utilidades.formatearFechaSolr(estructura.getVigenciaDesde())).setParent(item);
    new Listcell(Utilidades.formatearFechaSolr(estructura.getVigenciaHasta())).setParent(item);


    new Listcell(estructura.getUsuarioCreacion()).setParent(item);

    Listcell currentCell;

    currentCell = new Listcell();
    currentCell.setParent(item);
    Hbox hbox = new Hbox();

    // VISUALIZAR
    Image visualizarImage = new Image("/imagenes/edit-find.png");
    org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(visualizarImage,
        "onClick=composer.onVisualizarEstructura");
    Label visualizar = new Label("Visualizar");
    org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(visualizar,
        "onClick=composer.onVisualizarEstructura");
    visualizarImage.setParent(hbox);
    visualizar.setParent(hbox);
    hbox.setParent(currentCell);

    // MODIFICAR
    if (Utilitarios.isAdministradorCentral()) {
      Image modificarImage = new Image("/imagenes/pencil.png");
      org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(modificarImage,
          "onClick=composer.onModificarEstructura");
      Label modificar = new Label("Modificar");
      org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(modificar,
          "onClick=composer.onModificarEstructura");
      modificarImage.setParent(hbox);
      modificar.setParent(hbox);
      hbox.setParent(currentCell);
    }

    // BORRAR (borrado logico)

    Image borrarImage = new Image("/imagenes/Eliminar.png");
    org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(borrarImage,
        "onClick=composer.onBorrarEstructura");
    Label borrar = new Label("Borrar");
    org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(borrar, "onClick=composer.onBorrarEstructura");
    borrarImage.setParent(hbox);
    borrar.setParent(hbox);
    hbox.setParent(currentCell);

  }

}
