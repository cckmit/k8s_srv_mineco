package com.egoveris.edt.web.pl.Actualiza;

import java.util.Map;

public class ActualizaEvent {
  private String eventName;

  public String getEventName() {
    return eventName;
  }

  public void setEventName(String eventName) {
    this.eventName = eventName;
  }

  public Map getData() {
    return data;
  }

  public void setData(Map data) {
    this.data = data;
  }

  private Map data;

}
