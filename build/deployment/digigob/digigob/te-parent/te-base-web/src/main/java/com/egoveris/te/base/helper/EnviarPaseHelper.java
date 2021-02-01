package com.egoveris.te.base.helper;

import org.jbpm.api.task.Task;
import org.zkforge.fckez.FCKeditor;
import org.zkoss.bind.BindUtils;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zul.Window;

import com.egoveris.te.base.util.TramitacionHelper;
import com.egoveris.te.web.ee.satra.pl.helpers.states.IVisualState;

public class EnviarPaseHelper {
  
  final TramitacionHelper tramitacionHelper;
  final Window sendWindow;
  final Window tramWindow;
  
  /**
   * Constructor de EnviarPaseHelper
   * 
   * @param tramitacionHelper Helper de tramitacion
   * @param sendWindow Ventana de envio de pase
   * @param tramWindow Ventana de tramitacion
   */
  public EnviarPaseHelper(TramitacionHelper tramitacionHelper, Window sendWindow, Window tramWindow) {
    this.tramitacionHelper = tramitacionHelper;
    this.sendWindow = sendWindow;
    this.tramWindow = tramWindow;
  }
  
  /**
   * Listener de evento cuando se envia la tramitacion
   * 
   * @return Evento
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public EventListener<Event> onUserEvent() {
    return new EventListener() {
      @Override
      public void onEvent(Event event) {
        if ("addDocumentoPase".equals(event.getData())) {
          Task workingTask = null;
          
          if (Executions.getCurrent().getDesktop().hasAttribute("selectedTask")) {
            workingTask = (Task) Executions.getCurrent().getDesktop().getAttribute("selectedTask");
          }
          
          String motivePase = ((FCKeditor) sendWindow.getFellow("motivoExpediente")).getValue();
          IVisualState activeState = tramitacionHelper.getActiveState();
          //activeState.generateDocumentOfPase(motivePase); // En rigor no deberia ser necesaria esta linea
          
          // Se comenta porque el update de variables tambien se hace dentro de la logica
          // de envio de pase
          //tramitacionHelper.updateVariables(tramWindow.getFellow("incStatesNombramiento"), workingTask);
          activeState.stopReject();
        }
        
        BindUtils.postGlobalCommand(null, null, "closeTramitacionWindow", null);
      }
    };
  }
  
  /**
   * Listener de evento cuando se cancela o cierra la ventana
   * de  envio de pase
   * 
   * @return Evento
   */
  @SuppressWarnings({ "unchecked", "rawtypes" })
  public EventListener<Event> onCancelOrExitEvent() {
    return new EventListener() {
      @Override
      public void onEvent(Event event) {
        tramitacionHelper.getActiveState().stopReject();
      }
    };
  }
}
