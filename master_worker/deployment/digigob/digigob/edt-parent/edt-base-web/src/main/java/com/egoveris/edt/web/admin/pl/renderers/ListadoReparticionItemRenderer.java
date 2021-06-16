package com.egoveris.edt.web.admin.pl.renderers;

import com.egoveris.edt.base.util.Utilidades;
import com.egoveris.sharedsecurity.base.model.reparticion.ReparticionDTO;
import com.egoveris.sharedsecurity.conf.service.Utilitarios;

import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class ListadoReparticionItemRenderer implements ListitemRenderer {

  @Override
  public void render(Listitem item, Object data, int arg2) throws Exception {
    ReparticionDTO reparticion = (ReparticionDTO) data;

    new Listcell(reparticion.getCodigo()).setParent(item);
    new Listcell(reparticion.getNombre()).setParent(item);
    new Listcell(reparticion.getEstructura().getNombreEstructura()).setParent(item);
    new Listcell(Utilidades.formatearFechaSolr(reparticion.getVigenciaDesde())).setParent(item);
    new Listcell(Utilidades.formatearFechaSolr(reparticion.getVigenciaHasta())).setParent(item);
    new Listcell(reparticion.getEstadoRegistro() ? "ACTIVO" : "INACTIVO").setParent(item);

    Listcell currentCell;

    currentCell = new Listcell();
    currentCell.setParent(item);
    Hbox hbox = new Hbox();

    // VISUALIZAR
    Image visualizarImage = new Image("/imagenes/edit-find.png");
    org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(visualizarImage,
        "onClick=composer.onVisualizarReparticion");
    Label visualizar = new Label("Visualizar");
    org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(visualizar,
        "onClick=composer.onVisualizarReparticion");
    visualizarImage.setParent(hbox);
    visualizar.setParent(hbox);
    hbox.setParent(currentCell);

    // MODIFICAR
    if (Utilitarios.isAdministradorCentral()) {
      Image modificarImage = new Image("/imagenes/pencil.png");
      org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(modificarImage,
          "onClick=composer.onModificarReparticion");
      Label modificar = new Label("Modificar");
      org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(modificar,
          "onClick=composer.onModificarReparticion");
      modificarImage.setParent(hbox);
      modificar.setParent(hbox);
      hbox.setParent(currentCell);
    }

  }

}
