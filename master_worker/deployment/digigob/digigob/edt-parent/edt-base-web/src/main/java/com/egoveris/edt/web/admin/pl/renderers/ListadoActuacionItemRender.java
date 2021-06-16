package com.egoveris.edt.web.admin.pl.renderers;

import com.egoveris.edt.base.model.eu.actuacion.ActuacionDTO;
import com.egoveris.edt.base.util.Utilidades;
import com.egoveris.sharedsecurity.conf.service.Utilitarios;

import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class ListadoActuacionItemRender implements ListitemRenderer {

  @Override
  public void render(Listitem item, Object data, int arg2) throws Exception {
    ActuacionDTO actuacion = (ActuacionDTO) data;

    new Listcell("" + actuacion.getCodigoActuacion()).setParent(item);
    new Listcell(actuacion.getNombreActuacion()).setParent(item);
    new Listcell(Utilidades.formatearFechaSolr(actuacion.getVigenciaDesde())).setParent(item);
    new Listcell(Utilidades.formatearFechaSolr(actuacion.getVigenciaHasta())).setParent(item);
    new Listcell(actuacion.getUsuarioCreacion()).setParent(item);

    Listcell currentCell;

    currentCell = new Listcell();
    currentCell.setParent(item);
    Hbox hbox = new Hbox();

    // VISUALIZAR
    Image visualizarImage = new Image("/imagenes/edit-find.png");
    org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(visualizarImage,
        "onClick=composer.onVisualizarActuacion");
    Label visualizar = new Label("Visualizar");
    org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(visualizar,
        "onClick=composer.onVisualizarActuacion");
    visualizarImage.setParent(hbox);
    visualizar.setParent(hbox);
    hbox.setParent(currentCell);

    // MODIFICAR
    if (Utilitarios.isAdministradorCentral()) {
      Image modificarImage = new Image("/imagenes/pencil.png");
      org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(modificarImage,
          "onClick=composer.onModificarActuacion");
      Label modificar = new Label("Modificar");
      org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(modificar,
          "onClick=composer.onModificarActuacion");
      modificarImage.setParent(hbox);
      modificar.setParent(hbox);
      hbox.setParent(currentCell);
    }

    // BORRAR (borrado logico)

    Image borrarImage = new Image("/imagenes/Eliminar.png");
    org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(borrarImage,
        "onClick=composer.onBorrarActuacion");
    Label borrar = new Label("Borrar");
    org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(borrar, "onClick=composer.onBorrarActuacion");
    borrarImage.setParent(hbox);
    borrar.setParent(hbox);
    hbox.setParent(currentCell);

  }

}
