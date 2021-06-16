package com.egoveris.te.base.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PropertyConfigurationPK implements Serializable {

  private static final long serialVersionUID = -7918527046733688724L;

  @Column(name = "CLAVE")
  private String clave;

  @Column(name = "CONFIGURACION")
  private String configuracion;

  public PropertyConfigurationPK() {
    // Default constructor
  }
  
  public PropertyConfigurationPK(String clave, String configuracion) {
    this.clave = clave;
    this.configuracion = configuracion;
  }
  
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
