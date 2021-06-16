package com.egoveris.te.base.rendered;

import com.egoveris.te.base.composer.SupervisadosInboxComposer;
import com.egoveris.te.base.model.Tarea;

import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class SupervisadosInboxItemRenderer implements ListitemRenderer {

  SupervisadosInboxComposer composer;

  public SupervisadosInboxItemRenderer(SupervisadosInboxComposer supervisadosInboxComposer) {
    composer = supervisadosInboxComposer;
  }

  public void render(Listitem item, Object data, int arg1) throws Exception {
    Tarea task = (Tarea) data;

    Listcell cell = new Listcell("");
    Checkbox check = new Checkbox();
    check.setAttribute("model", task);

    check.addEventListener(Events.ON_CHECK, new EventListener() {

      @Override
      public void onEvent(Event event) throws Exception {
        Checkbox check = (Checkbox) event.getTarget();
        if (check.isChecked()) {
          agregarALista((Tarea) check.getAttribute("model"));
        }
        if (!check.isChecked()) {
          removerDeLista((Tarea) check.getAttribute("model"));
        }
      }
    });

    if (existeEnLista(task)) {
      check.setChecked(true);
    }

    check.setParent(cell);
    cell.setParent(item);

    // Nombre Tarea
    new Listcell(task.getNombreTarea()).setParent(item);
    // Ultima Modificacion
    new Listcell(task.getFechaModificacion()).setParent(item);
    // Codigo Expediente
    new Listcell(task.getCodigoExpediente()).setParent(item);
    // Codigo Trata
    new Listcell(task.getCodigoTrata()).setParent(item);
    // Descripcion
    new Listcell(task.getDescripcionTrata()).setParent(item);
    // Motivo
    String motivo = task.getMotivo();
    Listcell listCellMotivo = new Listcell(motivoParseado(motivo));
    listCellMotivo.setTooltiptext(motivo);
    listCellMotivo.setParent(item);
    // Usuario Anterior
    new Listcell(task.getUsuarioAnterior()).setParent(item);
    // icono de visualizacion

    Listcell currentCell;
    currentCell = new Listcell();
    currentCell.setParent(item);
    Hbox hbox = new Hbox();
    if (task.getCodigoExpediente() != null) {
      Image runImage = new Image("/imagenes/ver_expediente.png");
      Label lbl = new Label("Visualizar");
      org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(runImage,
          "onClick=inboxWindow$InboxComposer.onVerExpediente");
      org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(lbl,
          "onClick=inboxWindow$InboxComposer.onVerExpediente");
      hbox.setTooltiptext("Ver expediente");
      runImage.setParent(hbox);
      lbl.setParent(hbox);
    } else {
      Label lab = new Label("No hay accion");
      lab.setParent(hbox);
    }

    hbox.setParent(currentCell);

  }

  private void agregarALista(Tarea tarea) {
    composer.agregarALista(tarea);
  }

  private void removerDeLista(Tarea tarea) {
    composer.removerDeLista(tarea);
  }

  private boolean existeEnLista(Tarea tarea) {
    return composer.existeEnLista(tarea);
  }

  private String motivoParseado(String motivo) {
    int cantidadCaracteres = 10;
    String substringMotivo = "";

    if (motivo != null && !"null".equalsIgnoreCase(motivo) && !"".equalsIgnoreCase(motivo)) {
      if (motivo.length() > cantidadCaracteres) {
        substringMotivo = motivo.substring(0, cantidadCaracteres) + "...";
      } else {
        substringMotivo = motivo.substring(0, motivo.length());
      }
    }
    return substringMotivo;
  }
}
