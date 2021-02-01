package com.egoveris.deo.base.model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "PROPERTY_CONFIGURATION")
public class PropertyConfiguration {
	
	@EmbeddedId
	PropertyConfigurationPK propertyConfigurationPK;
	
	@Column(name = "VALOR")
	private String valor;
	
	@Column(name="clave")
	private String clave;
	
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public PropertyConfigurationPK getPropertyConfigurationPK() {
		return propertyConfigurationPK;
	}
	public void setPropertyConfigurationPK(PropertyConfigurationPK propertyConfigurationPK) {
		this.propertyConfigurationPK = propertyConfigurationPK;
	}
  public String getClave() {
    return clave;
  }
  public void setClave(String clave) {
    this.clave = clave;
  }
	
}
