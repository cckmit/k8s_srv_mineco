package com.egoveris.te.base.rendered;

import com.egoveris.te.base.model.MetadataDTO;

import org.jbpm.api.ProcessEngine;
import org.jbpm.api.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zk.ui.sys.ComponentsCtrl;
import org.zkoss.zul.Button;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;

/**
 *
 * @author jnorvert
 *
 */

public class DatosTrataRenderer implements RowRenderer {
  @Autowired
  ProcessEngine processEngine;

  protected Task workingTask = null;

  public Task getWorkingTask() {
    return workingTask;
  }

  public void setWorkingTask(Task workingTask) {
    this.workingTask = workingTask;
  }

  public void render(Row row, Object data, int arg1) throws Exception {
    /**
     * Metadatos de la Trata. Se cargan los campos y sus valores que se
     * encuentren en la data.
     */

    MetadataDTO metadatosDeTrata = (MetadataDTO) data;
    row.getParent().setId("rows");

    // NOMBRE
    Label label1 = new Label();
    label1.setId(metadatosDeTrata.getNombre());
    label1.setValue(metadatosDeTrata.getNombre());
    label1.setParent(row);

    // OBLIGATORIEDAD
    Checkbox checkbox = new Checkbox();
    checkbox.setId(metadatosDeTrata.getNombre() + "_obligatoriedad");
    checkbox.setChecked(metadatosDeTrata.isObligatoriedad());
    checkbox.setParent(row);
    ComponentsCtrl.applyForward(checkbox, "onClick=onCheck()");
    checkbox.setAttribute("nombre", metadatosDeTrata.getNombre());

    // ACCIÓN
    Hbox hbox = new Hbox();
    Button botonEliminar = new Button();
    botonEliminar.setTooltiptext("Se elimina el dato de la trata.");
    botonEliminar.setImage("/imagenes/decline.png");
    ComponentsCtrl.applyForward(botonEliminar, "onClick=onEliminar()");
    Label label2 = new Label();
    label2.setValue("Eliminar");
    botonEliminar.setParent(hbox);
    botonEliminar.setAttribute("nombre", metadatosDeTrata.getNombre());
    label2.setParent(hbox);
    hbox.setParent(row);

  }

}
