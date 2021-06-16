package com.egoveris.deo.web.satra.renderers;

import com.egoveris.sharedsecurity.base.model.Usuario;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.sys.ComponentsCtrl;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class UsuariosAgregadosItemRenderer implements ListitemRenderer {

  @Override
  public void render(Listitem item, Object data, int arg2) throws Exception {

    Usuario datosUsuario = (Usuario) data;

    Listcell listCell = new Listcell();
    listCell.setLabel(datosUsuario.getNombreApellido());
    listCell.setTooltiptext(datosUsuario.getNombreApellido());
    listCell.setParent(item);

    Listcell listCell2 = new Listcell();
    listCell2.setLabel(datosUsuario.getUsuarioRevisor());
    listCell2.setTooltiptext(datosUsuario.getUsuarioRevisor());
    listCell2.setParent(item);

    Listcell listCell3 = new Listcell();

    Hbox hbox = new Hbox();
    hbox.setAlign("center");
    hbox.setParent(listCell3);

    Image image = new Image();
    image.setSrc("/imagenes/arrow_large_up.png");
    image.setTooltiptext(Labels.getLabel("gedo.firmaConjunta.helpSubir"));
    ComponentsCtrl.applyForward(image,
        "onClick=agregarUsuariosFirmaConjuntaWindow$composer.onSubir");
    image.setParent(hbox);

    Image image2 = new Image();
    image2.setSrc("/imagenes/arrow_large_down.png");
    image2.setTooltiptext(Labels.getLabel("gedo.firmaConjunta.Bajar"));
    ComponentsCtrl.applyForward(image2,
        "onClick=agregarUsuariosFirmaConjuntaWindow$composer.onBajar");
    image2.setParent(hbox);

    Image image3 = new Image();
    image3.setSrc("/imagenes/decline.png");
    image3.setTooltiptext(Labels.getLabel("gedo.firmaConjunta.helpDelete"));
    ComponentsCtrl.applyForward(image3,
        "onClick=agregarUsuariosFirmaConjuntaWindow$composer.onEliminarUsuario");
    image3.setParent(hbox);

    //Se deja abierto a también editar revisor.
//    if (datosUsuario.getUsuarioRevisor() == null || datosUsuario.getUsuarioRevisor().isEmpty() || datosUsuario.getUsuarioRevisor().equals("")) {
	    Image image4 = new Image();
	    image4.setSrc("/imagenes/pencil.png");
	    image4.setTooltiptext(Labels.getLabel("gedo.firmaConjunta.helpModificarRevisor"));
	    ComponentsCtrl.applyForward(image4,
	        "onClick=agregarUsuariosFirmaConjuntaWindow$composer.onModificarRevisor");
	    image4.setParent(hbox);
//    }

    listCell3.setParent(item);

  }

}
