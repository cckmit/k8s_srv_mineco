package com.egoveris.te.base.model;

import java.io.Serializable;

public class PropertyConfigurationDTO implements Serializable {

  private static final long serialVersionUID = -2176258147332837580L;

  private PropertyConfigurationPKDTO pk;
  private String clave;
  private String valor;
  private String configuracion;
  
  // Fields not mapped to entity but used in view. Please don't delete
  private boolean selected;
  private String valorAux;

	public PropertyConfigurationPKDTO getPk() {
		return pk;
	}

	public void setPk(PropertyConfigurationPKDTO pk) {
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

  public boolean isSelected() {
    return selected;
  }

  public void setSelected(boolean selected) {
    this.selected = selected;
  }

  public String getValorAux() {
    return valorAux;
  }

  public void setValorAux(String valorAux) {
    this.valorAux = valorAux;
  }
  
}
