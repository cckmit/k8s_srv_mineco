package com.egoveris.te.base.rendered;

import com.egoveris.te.base.exception.external.TeException;
import com.egoveris.te.base.model.ArchivoDeTrabajoDTO;
import com.egoveris.te.base.model.TareaParaleloDTO;
import com.egoveris.te.base.service.TareaParaleloService;
import com.egoveris.te.base.util.ConstantesWeb;

import org.jbpm.api.ProcessEngine;
import org.jbpm.api.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Image;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

public class ArchivosDeTrabajoItemRenderer implements ListitemRenderer {
  @Autowired
  ProcessEngine processEngine;
  Task workingTask;
  String loggedUsername = new String("");
  @Autowired
  private TareaParaleloService tareaParaleloService;
  private TareaParaleloDTO tareaParalelo = null;

  public void render(Listitem item, Object data, int arg1) throws Exception {

    ArchivoDeTrabajoDTO archivoDeTrabajo = (ArchivoDeTrabajoDTO) data;

    Listcell currentCell;

    int ordenIncial = 1;
    int numOrden = item.getIndex() + ordenIncial;

    String numeroDeOrden = Integer.toString(numOrden);

    // Numero de Posicion
    new Listcell(numeroDeOrden).setParent(item);

    // Archivo a subir
    new Listcell(archivoDeTrabajo.getNombreArchivo()).setParent(item);

    // Tipo Archivo
    if (archivoDeTrabajo.getTipoArchivoTrabajo() != null) {
      new Listcell(archivoDeTrabajo.getTipoArchivoTrabajo().getNombre()).setParent(item);
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
        "onClick=tramitacionWindow$composer.onVisualizarArchivosDeTrabajo");
    org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(runImage,
        "onClick=tramitacionWindow$composer.onVisualizarArchivosDeTrabajo");

    Hbox hboxEditar = new Hbox();
    hboxEditar.setParent(hboxpadre);
    Image runImage2 = new Image("/imagenes/pencil.png");

    Label editar = new Label("Editar");

    runImage2.setParent(hboxEditar);
    editar.setParent(hboxEditar);

    org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(editar,
        "onClick=tramitacionWindow$composer.onModificacionArchivoDeTrabajo");
    org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(runImage2,
        "onClick=tramitacionWindow$composer.onModificacionArchivoDeTrabajo");

    if (!archivoDeTrabajo.isDefinitivo()) {

      Hbox hboxeliminar = new Hbox();
      hboxeliminar.setParent(hboxpadre);

      Image runImage1 = new Image("/imagenes/eliminar.png");

      runImage1.setParent(hboxeliminar);

      Label ejecutar = new Label("Eliminar");

      org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(ejecutar,
          "onClick=tramitacionWindow$composer.onDesasociarArchivosDeTrabajo");
      org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(runImage1,
          "onClick=tramitacionWindow$composer.onDesasociarArchivosDeTrabajo");
      runImage1.setParent(hboxeliminar);

      ejecutar.setParent(hboxeliminar);
    }
    
    hboxpadre.setParent(currentCell);

    // INICIO A COLOREAR FILA MODIFICADA PENDIENTE DE DEFINIR
    
    if (this.workingTask == null) {
      workingTask = (Task) Executions.getCurrent().getDesktop().getAttribute("selectedTask");
    } 

    if (workingTask.getActivityName().equals(ConstantesWeb.ESTADO_PARALELO)) {
      // Obtengo la tarea en paralelo
      tareaParalelo = this.tareaParaleloService
          .buscarTareaEnParaleloByIdTask(workingTask.getExecutionId());
      if (tareaParalelo != null) {
        loggedUsername = Executions.getCurrent().getSession()
            .getAttribute(ConstantesWeb.SESSION_USERNAME).toString();
        if (!loggedUsername.equals("")) {
          if (tareaParalelo.getUsuarioOrigen().equals(loggedUsername)) {// ¿soy
                                                                        // el
                                                                        // usuario
                                                                        // propietario?
            if (!archivoDeTrabajo.isDefinitivo()) {
              if (!archivoDeTrabajo.getUsuarioAsociador().equals(loggedUsername)) {
                if ((archivoDeTrabajo.getFechaAsociacion().getTime()) >= (workingTask
                    .getCreateTime().getTime())) {
                  item.setStyle("background-color:" + ConstantesWeb.COLOR_ILUMINACION_FILA);
                }
              }
            }
          }
        } else {
          throw new TeException("No se ha podido recuperar el usuario loggeado.", null);
        }
      } else {
        throw new TeException("No se ha podido recuperar la tarea.", null);
      }
    }
    // FIN A COLOREAR FILA MODIFICADA PENDIENTE DE DEFINIR
  }
}
