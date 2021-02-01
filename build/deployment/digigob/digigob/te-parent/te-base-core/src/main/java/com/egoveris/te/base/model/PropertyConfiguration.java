package com.egoveris.te.base.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "PROPERTY_CONFIGURATION")
public class PropertyConfiguration implements Serializable {

  private static final long serialVersionUID = -1517628973436536102L;

  @Id
  private PropertyConfigurationPK pk;

  @Column(name = "CLAVE", insertable = false, updatable = false)
  private String clave;

  @Column(name = "VALOR")
  private String valor;

  @Column(name = "CONFIGURACION", insertable = false, updatable = false)
  private String configuracion;

  public PropertyConfigurationPK getPk() {
    return pk;
  }

  public void setPk(PropertyConfigurationPK pk) {
    this.pk = pk;
  }

  public String getClave() {
    return clave;
  }

  public void setClave(String clave) {
    this.clave = clave;
  }

  public String getValor() {
    return valor;
  }

  public void setValor(String valor) {
    this.valor = valor;
  }

  public String getConfiguracion() {
    return configuracion;
  }

  public void setConfiguracion(String configuracion) {
    this.configuracion = configuracion;
  }

}