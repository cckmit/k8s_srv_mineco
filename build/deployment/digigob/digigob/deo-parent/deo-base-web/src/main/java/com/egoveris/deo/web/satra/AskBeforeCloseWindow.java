package com.egoveris.deo.web.satra;

import org.zkoss.util.resource.Labels;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Messagebox;
import org.zkoss.zul.Window;

import com.egoveris.deo.util.Constantes;

public class AskBeforeCloseWindow extends Window {

  /**
  * 
  */
  private static final long serialVersionUID = -1284383682711886838L;

  /**
   * Sobreescribe el método onClose para preguntar si desea abandonar la ventana
   * al presionar el botón close. Problema: Pregunta siempre. Workaround: Setear
   * antes de llamar al evento onClose programáticamente el atributo
   * dontAskBeforeClose a true.
   */
  @SuppressWarnings("unchecked")
  @Override
  public void onClose() {
    Boolean dontAskBeforeClosingObj = (Boolean) this.getAttribute("dontAskBeforeClose");
    Boolean numeroSare = (Boolean) this.getAttribute(Constantes.VAR_NUMERO_SA);
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
            @Override
            public void onEvent(Event evt) {
              switch (((Integer) evt.getData()).intValue()) {
              case Messagebox.YES: 
            	 if (null != numeroSare && numeroSare) {
            		 Clients.evalJavaScript("parent.closeIframe();");
            	  }
            	 closeWindow();
                break;
              case Messagebox.NO:
                return;
              }
            }
          });
    } else {
    	if (null != numeroSare && numeroSare) {
   		 Clients.evalJavaScript("parent.closeIframe();");
   	  } else {
   		  this.closeWindow();
  		}
	}
  }

  private void closeWindow() {
    if (this.getParent() != null) {
      Events.sendEvent(this.getParent(), new Event(Events.ON_NOTIFY));
    }

    super.onClose();
  }
}
