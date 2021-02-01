/**
 * 
 */
package com.egoveris.te.base.rendered;

import com.egoveris.te.base.model.ArchivoDeTrabajoDTO;

import org.jbpm.api.task.Task;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;

/**
 * @author jnorvert
 *
 */
public class GenericListaDocumentoTrabajoItemRenderer extends GenericListitemRenderer {

  public void render(Listitem item, Object data, int arg1) throws Exception {

    ArchivoDeTrabajoDTO archivoDeTrabajo = (ArchivoDeTrabajoDTO) data;
    Listcell currentCell;
    
    workingTask = (Task) item.getDesktop().getAttribute("selectedTask");

    int ordenIncial = 1;
    int numOrden = item.getIndex() + ordenIncial;
    String numeroDeOrden = Integer.toString(numOrden);

    // Numero de Posicion
    new Listcell(numeroDeOrden).setParent(item);

    // Archivo a subir
    new Listcell(archivoDeTrabajo.getNombreArchivo()).setParent(item);
    currentCell = new Listcell();
    currentCell.setParent(item);
    Hbox hbox = new Hbox();
    Label visualizar = new Label("Visualizar");
    visualizar.setParent(hbox);
    org.zkoss.zk.ui.sys.ComponentsCtrl.applyForward(visualizar,
        "onClick=documentoDeTrabajoWindow$composer.onVisualizarArchivosDeTrabajo");
    hbox.setParent(currentCell);
  }

}
