package com.egoveris.edt.web.pl;

import org.springframework.beans.factory.annotation.Autowired;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Window;

public class TareasComposer extends GenericForwardComposer {

	
  /**
  * 
  */

  @Autowired
  private Tabbox tareasTabs;
  private static final long serialVersionUID = -2212296597932962544L;

  public void onSelect$tareasTabs() {
    Window currentWindow = (Window) tareasTabs.getSelectedPanel().getFirstChild().getFirstChild();
    Tabbox tab = tareasTabs;
    Event event = new Event(Events.ON_NOTIFY, currentWindow, tab);
    Events.sendEvent(event);
  }
}
