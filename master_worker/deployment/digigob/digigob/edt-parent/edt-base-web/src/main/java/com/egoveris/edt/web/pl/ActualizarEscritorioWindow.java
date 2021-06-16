package com.egoveris.edt.web.pl;

import com.egoveris.edt.web.pl.Actualiza.ActualizaEvent;

import org.zkoss.zk.au.AuRequest;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;

public final class ActualizarEscritorioWindow extends AskBeforeCloseWindow {

	
  /**
  * 
  */
  private static final long serialVersionUID = -1108636307231376915L;

  @Override
  public void service(AuRequest request, boolean everError) {
    super.service(request, everError);
    if (request.getCommand().contains("onActualizar")) {
      ActualizaEvent eventActualiza = new ActualizaEvent();
      eventActualiza.setEventName(request.getCommand());
      eventActualiza.setData(request.getData());
      Event event = new Event(Events.ON_NOTIFY, this, eventActualiza);
      Events.sendEvent(event);
    }
  }

}
