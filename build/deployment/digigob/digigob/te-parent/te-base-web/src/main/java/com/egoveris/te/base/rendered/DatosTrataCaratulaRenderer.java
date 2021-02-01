
package com.egoveris.te.base.rendered;

import com.egoveris.te.base.model.DatoPropio;
import com.egoveris.te.base.model.ExpedienteMetadataDTO;
import com.egoveris.te.base.service.TrataService;

import java.util.List;

import org.jbpm.api.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zul.Hlayout;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import org.zkoss.zul.Textbox;

public class DatosTrataCaratulaRenderer implements RowRenderer {
  @Autowired
  private TrataService trataService;

  protected Task workingTask = null;
  private boolean esSoloLectura;

  public Task getWorkingTask() {
    return workingTask;
  }

  public void setWorkingTask(Task workingTask) {
    this.workingTask = workingTask;
  }

  public DatosTrataCaratulaRenderer(boolean esSoloLectura) {

    this.esSoloLectura = esSoloLectura; 
  }

  public void render(Row row, Object data, int arg1) throws Exception {

    ExpedienteMetadataDTO metadatosDeTrata = (ExpedienteMetadataDTO) data;

    row.getParent().setId("rows");

    // NOMBRE
    Label label1 = new Label();
    label1.setValue(metadatosDeTrata.getNombre());
    label1.setParent(row);

    Hlayout hbox = new Hlayout();

    List<DatoPropio> lista;
    // Tipo de metadata = 1 quiere decir que es un campo de Texto.
    if (metadatosDeTrata.getTipo() == 1) {
      Textbox textbox = new Textbox();
      textbox.setWidth("350px");
      textbox.setValue(metadatosDeTrata.getValor());
      textbox.setReadonly(this.esSoloLectura);
      textbox.setParent(hbox);
    } else {
      lista = this.trataService.buscarDatosCombo(metadatosDeTrata.getNombre());

      org.zkoss.zul.Combobox combobox = new org.zkoss.zul.Combobox();
      combobox.setWidth("350px");

      for (DatoPropio datoPropio : lista) {
        combobox.appendItem(datoPropio.getNombre().trim());
      }

      combobox.setText(metadatosDeTrata.getValor());

      combobox.setDisabled(this.esSoloLectura);
      combobox.setReadonly(true);
      combobox.setParent(hbox);
    }

    // CAMPO REQUERIDO
    Label label2 = new Label();
    if (metadatosDeTrata.isObligatoriedad()) {
      label2.setValue(" *");
      label2.setStyle("color:red;");
      label2.setParent(hbox);
    }
    hbox.setParent(row);

  }
}
