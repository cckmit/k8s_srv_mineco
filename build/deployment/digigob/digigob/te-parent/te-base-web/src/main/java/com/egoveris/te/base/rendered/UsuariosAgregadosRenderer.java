package com.egoveris.te.base.rendered;

import com.egoveris.te.base.model.DatosEnvioParalelo;

import org.jbpm.api.ProcessEngine;
import org.jbpm.api.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zk.ui.sys.ComponentsCtrl;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Separator;


public class UsuariosAgregadosRenderer implements ListitemRenderer {

  @Autowired
  ProcessEngine processEngine;

  protected Task workingTask = null;

  public Task getWorkingTask() {
    return workingTask;
  }

  public void setWorkingTask(Task workingTask) {
    this.workingTask = workingTask;
  }

  public void render(Listitem item, Object data, int arg1) throws Exception {

    DatosEnvioParalelo usuario = (DatosEnvioParalelo) data;

    if (usuario.getUser() != null) {
      // USUARIO
      Listcell Celdausuario = new Listcell();
      Label label1 = new Label();
      label1.setId(usuario.getUser());

      String usuarioMofif;
      if (usuario.getUserApoderado() != null && !usuario.getUserApoderado().equals("")) {
        usuarioMofif = usuario.getApellidoYNombre() + " (" + usuario.getUser() + ")"
            + " - enviado a " + usuario.getUserApoderado() + " por licencia";
      } else {
        usuarioMofif = usuario.getApellidoYNombre() + " (" + usuario.getUser() + ")";
      }
      label1.setValue(usuarioMofif);

      label1.setParent(Celdausuario);
      Celdausuario.setParent(item);

      // MOTIVO DE PASE

      Listcell CeldaMotivo = new Listcell();
      Label label3 = new Label();
      String motivo;
      if (usuario.getMotivo() != null) {
        motivo = motivoParseado(usuario.getMotivo());
      } else {
        motivo = "";
      }
      label3.setValue(motivo);
      label3.setTooltiptext(usuario.getMotivo());
      label3.setParent(CeldaMotivo);
      CeldaMotivo.setParent(item);
      // *****

      // ACCIÓN
      Listcell CeldaEliminar = new Listcell();
      Hbox hbox2 = new Hbox();

      Image imagen = new Image("/imagenes/comment-add.png");
      imagen.setTooltiptext(
          "El usuario recibirá el expediente con el motivo de pase, pero si lo desea puede ingresar un motivo personalizado para el usuario seleccionado.");
      ComponentsCtrl.applyForward(imagen, "onClick=onGuardarMotivo()");
      imagen.setAttribute("destinatario", usuario);
      Separator separar = new Separator();
      Separator separar1 = new Separator();
      separar.setWidth("15px");

      separar1.setParent(hbox2);
      imagen.setParent(hbox2);
      separar.setParent(hbox2);

      Image eliminar = new Image("/imagenes/eliminar.png");
      eliminar.setTooltiptext("Eliminar el usuario de la lista de destinatarios.");
      ComponentsCtrl.applyForward(eliminar, "onClick=onEliminarUsuario()");

      eliminar.setParent(hbox2);
      eliminar.setAttribute("nombre", usuario);
      hbox2.setParent(CeldaEliminar);
      CeldaEliminar.setParent(item);

    }
  }

  private String motivoParseado(String motivo) {
    int cantidadCaracteres = 20;
    String substringMotivo;
    if (motivo.length() > cantidadCaracteres) {
      substringMotivo = motivo.substring(0, cantidadCaracteres) + "...";
    } else {
      substringMotivo = motivo.substring(0, motivo.length());
    }
    return substringMotivo;
  }
}
