package com.egoveris.te.base.model;

import java.io.Serializable;

public class PropertyConfigurationPKDTO implements Serializable {

  private static final long serialVersionUID = -2176258147332837580L;

  private String clave;
  private String configuracion;

  public String getClave() {
    return clave;
  }

  public void setClave(String clave) {
    this.clave = clave;
  }

  public String getConfiguracion() {
    return configuracion;
  }

  public void setConfiguracion(String configuracion) {
    this.configuracion = configuracion;
  }
}
