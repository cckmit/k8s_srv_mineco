package com.egoveris.te.base.util;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

public class AskBeforeCloseWindow extends Window {

  /**
  * 
  */
  private static final long serialVersionUID = -1284383682711886838L;

  @Override
  /**
   * Sobreescribe el método onClose para preguntar si desea abandonar la ventana
   * al presionar el botón close. Problema: Pregunta siempre. Workaround: Setear
   * antes de llamar al evento onClose programáticamente el atributo
   * dontAskBeforeClose a true.
   */
  public void onClose() {
    Boolean dontAskBeforeClosingObj = (Boolean) this.getAttribute("dontAskBeforeClose");
    boolean dontAskBeforeClosing = false;
    if (dontAskBeforeClosingObj != null) {
      dontAskBeforeClosing = dontAskBeforeClosingObj;
    }
    // Si antes de invocar al evento onClose se seteó la propiedad de no
    // preguntar entonces hacer eso.
    if (!(dontAskBeforeClosing)) {
      Messagebox.show(Labels.getLabel("gedo.general.cerrarVentana"),
          Labels.getLabel("gedo.general.question"), Messagebox.YES | Messagebox.NO,
          Messagebox.QUESTION, new EventListener() {
            public void onEvent(Event evt) {
              switch (((Integer) evt.getData()).intValue()) {
              case Messagebox.YES:
                closeWindow();
                break;
              case Messagebox.NO:
                return;
              }
            }
          });
    } else {
      this.closeWindow();
    }
  }

  private void closeWindow() {
    if (this.getParent() != null) {
      Events.sendEvent(this.getParent(), new Event(Events.ON_NOTIFY));
    }
    super.onClose();
  }
}
