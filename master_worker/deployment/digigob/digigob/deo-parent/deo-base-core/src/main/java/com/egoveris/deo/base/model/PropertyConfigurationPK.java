package com.egoveris.deo.base.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PropertyConfigurationPK implements Serializable {

  /**
   * 
   */
  private static final long serialVersionUID = 7636352590133754837L;

  @Column(name = "CLAVE")
  private String clave;

  @Column(name = "CONFIGURACION")
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
