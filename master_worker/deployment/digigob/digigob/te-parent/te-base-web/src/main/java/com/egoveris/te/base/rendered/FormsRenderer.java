package com.egoveris.te.base.rendered;

import com.egoveris.te.base.util.UtilsDate;
import com.egoveris.te.model.model.ExpedienteFormularioDTO;

import org.jbpm.api.ProcessEngine;
import org.jbpm.api.task.Task;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class FormsRenderer implements ListitemRenderer {
  ProcessEngine processEngine;
  Task workingTask;
  String loggedUsername = new String("");

  public void render(Listitem item, Object data, int arg1) throws Exception {

    ExpedienteFormularioDTO expedienteFormulario = (ExpedienteFormularioDTO) data;

    Listcell currentCell;

    Listbox lista2 = (Listbox) item.getParent();
    int cantDocumentos = lista2.getItemCount();
    int numOrden = cantDocumentos - (item.getIndex());
    String numeroDeOrden = Integer.toString(numOrden);
    // Numero de Posicion
    new Listcell(numeroDeOrden).setParent(item);

    // Nombre Formulario
    new Listcell(expedienteFormulario.getFormName()).setParent(item);

    // Fecha Creacion con formato
    if (expedienteFormulario.getDateCration() != null) {
      new Listcell(UtilsDate.formatearFechaHora(expedienteFormulario.getDateCration()))
          .setParent(item);
    } else {
      new Listcell(" ").setParent(item);
    }

    // Usuario creador
    if (expedienteFormulario.getUserCreation() != null) {
      new Listcell(expedienteFormulario.getUserCreation()).setParent(item);
    } else {
      new Listcell(" ").setParent(item);
    }

    currentCell = new Listcell();

    currentCell.setParent(item);
    Hbox hboxpadre = new Hbox();
    Hbox hbox = new Hbox();
    hbox.setParent(hboxpadre);
    Image runImage = new Image("/imagenes/Descargar.png");

    Label visualizar = new Label("Visualizar");

    runImage.setParent(hbox);
    visualizar.setParent(hbox);

    org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(visualizar,
        "onClick=tramitacionWindow$composer.onViewForm");
    org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(runImage,
        "onClick=tramitacionWindow$composer.onViewForm");

    if (expedienteFormulario.getIsDefinitive() == null
        || !expedienteFormulario.getIsDefinitive().equals(1)) {

      Hbox hboxEditar = new Hbox();
      hboxEditar.setParent(hboxpadre);
      Image runImage2 = new Image("/imagenes/pencil.png");

      Label editar = new Label("Editar");

      runImage2.setParent(hboxEditar);
      editar.setParent(hboxEditar);

      org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(editar,
          "onClick=tramitacionWindow$composer.onChangeForm");
      org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(runImage2,
          "onClick=tramitacionWindow$composer.onChangeForm");

      Hbox hboxeliminar = new Hbox();
      hboxeliminar.setParent(hboxpadre);

      Image runImage1 = new Image("/imagenes/eliminar.png");

      runImage1.setParent(hboxeliminar);

      Label delete = new Label("Eliminar");

      org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(delete,
          "onClick=tramitacionWindow$composer.onDeleteForm");
      org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(runImage1,
          "onClick=tramitacionWindow$composer.onDeleteForm");
      runImage1.setParent(hboxeliminar);

      delete.setParent(hboxeliminar);
    } else {

      Hbox hboxClone = new Hbox();
      hboxClone.setParent(hboxpadre);
      Image runImage2 = new Image("/imagenes/clone.png");

      Label clone = new Label("Clonar");

      runImage2.setParent(hboxClone);
      clone.setParent(hboxClone);

      org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(clone,
          "onClick=tramitacionWindow$composer.onCloneForm");
      org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(runImage2,
          "onClick=tramitacionWindow$composer.onCloneForm");

    }
    hboxpadre.setParent(currentCell);
  }
}
